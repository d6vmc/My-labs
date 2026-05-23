package server.commands;

import server.collection.CollectionManager;
/**
 * Команда очистки коллекции.
 *
 * Удаляет все элементы из коллекции.
 *
 * @author Эльдар
 * @version 1.0
 */
public class ClearCommand implements Command {
    /**
     * Возвращает имя команды.
     *
     * @return "clear"
     */
    public String getName() {
        return "clear";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Очищает коллекцию";
    }
    /**
     * Выполняет очистку коллекции.
     *
     * Не принимает аргументов.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (должны отсутствовать)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, Object argument) {
        if (argument != null) {
            return new CommandResult(false, "У команды clear не может быть аргументов", false);
        }
        manager.clear();
        return new CommandResult(true, "Коллекция очищена", false);
    }
}
