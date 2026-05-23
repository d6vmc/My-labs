package core.commands;

import core.collection.CollectionManager;
/**
 * Команда удаления элементов, ключ которых превышает заданный.
 *
 * Удаляет из коллекции все элементы, у которых ключ больше указанного.
 *
 * @author Эльдар
 * @version 1.0
 */
public class RemoveGreaterKeyCommand implements Command {
    /**
     * Возвращает имя команды.
     *
     * @return "remove_greater_key"
     */
    public String getName() {
        return "remove_greater_key";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Удаляет из коллекции все элементы, ключ которых превышает заданный";
    }
    /**
     * Выполняет команду remove_greater_key.
     *
     * Ожидает один аргумент — ключ.
     * Удаляет все элементы, у которых ключ больше заданного.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (args[0] — ключ)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, String[] args) {
        if (args.length != 1) {
            return new CommandResult(false, "У команды remove_greater_key должен быть 1 аргумент", false);
        }
        Long key;
        try {
            key = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            return new CommandResult(false, "Ключ должен быть числом", false);
        }
        long cnt;
        try {
            cnt = manager.removeGreaterKey(key);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage(), false);
        }
        return new CommandResult(true, cnt + "элемент/ов/а удалены", false);
    }
}
