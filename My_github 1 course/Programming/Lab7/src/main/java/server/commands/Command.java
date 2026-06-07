package server.commands;

import server.collection.CollectionManager;
import server.db.PersonDAO;

/**
 * Интерфейс для всех команд программы.
 *
 * @author Эльдар
 * @version 1.0
 */
public interface Command {
    /**
     * Возвращает имя команды.
     *
     * @return имя команды
     */
    String getName();
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    String getDescription();
    /**
     * Выполняет команду.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды
     * @return результат выполнения команды
     */
    CommandResult execute(CollectionManager manager, Object argument, int userId, PersonDAO personDAO);
}
