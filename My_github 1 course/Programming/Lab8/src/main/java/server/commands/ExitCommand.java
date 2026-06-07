package server.commands;

import server.collection.CollectionManager;
import server.db.PersonDAO;

/**
 * Команда завершения программы.
 *
 * Прекращает выполнение приложения.
 *
 * @author Эльдар
 * @version 1.0
 */
public class ExitCommand implements Command {
    /**
     * Возвращает имя команды.
     *
     * @return "exit"
     */
    public String getName() {
        return "exit";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Завершает программу";
    }
    /**
     * Выполняет команду exit.
     *
     * Не принимает аргументов.
     * Возвращает результат с флагом завершения программы.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (должны отсутствовать)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, Object argument, int userId, PersonDAO personDAO) {
        if (argument != null) {
            return new CommandResult(false, "У команды exit не может быть аргументов", false);
        }
        return new CommandResult(true, "Завершение программы", true);
    }
}
