package server.commands;

import server.collection.CollectionManager;
import server.db.PersonDAO;

/**
 * Команда вывода информации о коллекции.
 *
 * Возвращает тип коллекции, дату инициализации и количество элементов.
 *
 * @author Эльдар
 * @version 1.0
 */
public class InfoCommand implements Command {
    /**
     * Возвращает имя команды.
     *
     * @return "info"
     */
    public String getName() {
        return "info";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Выводит информацию о коллекции";
    }
    /**
     * Выполняет команду info.
     *
     * Не принимает аргументов.
     * Возвращает информацию о коллекции.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (должны отсутствовать)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, Object argument, int userId, PersonDAO personDAO) {
        if (argument != null) {
            return new CommandResult(false, "У команды info не может быть аргументов", false);
        }
        return new CommandResult(true, manager.info(), false);
    }
}
