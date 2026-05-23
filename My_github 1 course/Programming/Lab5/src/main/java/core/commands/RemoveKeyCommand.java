package core.commands;

import core.collection.CollectionManager;
/**
 * Команда удаления элемента из коллекции по ключу.
 *
 * Удаляет объект из коллекции {@link CollectionManager} по заданному ключу.
 *
 * @author Эльдар
 * @version 1.0
 */
public class RemoveKeyCommand  implements Command {
    /**
     * Возвращает имя команды.
     *
     * @return "remove_key"
     */
     public String getName() {
        return "remove_key";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Удаляет элемент из коллекции по его ключу";
    }
    /**
     * Выполняет команду remove_key.
     *
     * Ожидает один аргумент — ключ элемента.
     * Удаляет элемент из коллекции по этому ключу.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (args[0] — ключ)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, String[] args) {
        if (args.length != 1) {
            return new CommandResult(false, "У команды remove_key должен быть 1 аргумент", false);
        }
        Long key;
        try {
            key = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            return new CommandResult(false, "Ключ должен быть числом", false);
        }
        try {
            manager.removeKey(key);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage(), false);
        }
        return new CommandResult(true, "Элемент удален", false);
    }
}