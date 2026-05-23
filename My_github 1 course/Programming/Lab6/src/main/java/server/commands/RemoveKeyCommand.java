package server.commands;

import server.collection.CollectionManager;

public class RemoveKeyCommand implements Command {

    public String getName() {
        return "remove_key";
    }

    public String getDescription() {
        return "Удаляет элемент из коллекции по его ключу";
    }

    public CommandResult execute(CollectionManager manager, Object argument) {
        if (!(argument instanceof Long key)) {
            return new CommandResult(false, "Для remove_key нужен аргумент типа Long", false);
        }
        try {
            manager.removeKey(key);
            return new CommandResult(true, "Элемент удален", false);

        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage(), false);
        }
    }
}