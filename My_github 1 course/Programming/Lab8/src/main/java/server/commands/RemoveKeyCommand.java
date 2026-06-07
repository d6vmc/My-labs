package server.commands;

import server.collection.CollectionManager;
import server.db.PersonDAO;

public class RemoveKeyCommand implements Command {

    public String getName() {
        return "remove_key";
    }

    public String getDescription() {
        return "Удаляет элемент из коллекции по его ключу";
    }

    public CommandResult execute(CollectionManager manager, Object argument, int userId, PersonDAO personDAO) {
        if (!(argument instanceof Long key)) {
            return new CommandResult(false, "Для remove_key нужен аргумент типа Long", false);
        }
        try {
            boolean removed = personDAO.removeById(key, userId);
            if (!removed) {
                return new CommandResult(false, "Элемент не найден или не принадлежит вам", false);
            }
            manager.removeKey(key);
            return new CommandResult(true, "Элемент удален", false);
        } catch (Exception e) {
            return new CommandResult(false, "Ошибка удаления элемента: " + e.getMessage(), false);
        }
    }
}