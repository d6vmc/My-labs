package server.commands;

import common.ArgumentWithKey;
import common.PersonData;
import server.collection.CollectionManager;
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
    public CommandResult execute(CollectionManager manager, Object argument) {
        if (!(argument instanceof Long key)) {
            return new CommandResult(false, "Для remove_greater_key нужен аргумент типа Long", false);
        }
        try {
            long cnt = manager.removeGreaterKey(key);
            return new CommandResult(true, cnt + " элемент/ов удалено", false);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage(), false);
        }
    }
}
