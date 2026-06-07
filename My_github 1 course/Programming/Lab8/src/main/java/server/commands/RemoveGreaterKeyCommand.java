package server.commands;

import server.collection.CollectionManager;
import server.db.PersonDAO;

/**
 * Команда удаления элементов, ключ которых превышает заданный.
 *
 * Удаляет из коллекции все элементы, у которых ключ больше указанного.
 *
 * @author Эльдар
 * @version 1.0
 */
public class RemoveGreaterKeyCommand implements Command {

    public String getName() {
        return "remove_greater_key";
    }

    public String getDescription() {
        return "Удаляет из коллекции все элементы, ключ которых превышает заданный";
    }

    public CommandResult execute(CollectionManager manager, Object argument, int userId, PersonDAO personDAO) {
        if (!(argument instanceof Long key)) {
            return new CommandResult(false, "Для remove_greater_key нужен аргумент типа Long", false);
        }
        try {
            int cnt = personDAO.removeGreaterKey(key, userId);
            if (cnt == 0) {
                return new CommandResult(true, "Элементов для удаления не найдено", false);
            }
            manager.removeGreaterKey(key);
            return new CommandResult(true, cnt + " элемент/ов удалено", false);
        } catch (Exception e) {
            return new CommandResult(false, "Ошибка удаления элементов: " + e.getMessage(), false);
        }
    }
}