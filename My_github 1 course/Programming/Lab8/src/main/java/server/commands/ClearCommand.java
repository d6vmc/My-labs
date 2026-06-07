package server.commands;

import server.collection.CollectionManager;
import server.db.PersonDAO;

import java.util.List;

/**
 * Команда очистки коллекции.
 *
 * Удаляет из коллекции все элементы, принадлежащие текущему пользователю.
 *
 * @author Эльдар
 * @version 1.0
 */
public class ClearCommand implements Command {

    public String getName() {
        return "clear";
    }
    public String getDescription() {
        return "Очищает коллекцию от элементов текущего пользователя";
    }
    public CommandResult execute(CollectionManager manager, Object argument, int userId, PersonDAO personDAO) {
        if (argument != null) {
            return new CommandResult(false, "У команды clear не может быть аргументов", false);
        }
        try {
            List<Long> removedIds = personDAO.clearByOwner(userId);
            if (removedIds.isEmpty()) {
                return new CommandResult(true, "У вас нет элементов для удаления", false);
            }
            for (Long id : removedIds) {
                manager.removeKey(id);
            }
            return new CommandResult(true, "Удалено элементов: " + removedIds.size(), false);
        } catch (Exception e) {
            return new CommandResult(false, "Ошибка очистки коллекции: " + e.getMessage(), false);
        }
    }
}