package server.commands;

import server.collection.CollectionManager;
import common.domain.Person;
/**
 * Команда вывода элемента с максимальным значением creationDate.
 *
 * Находит и возвращает объект {@link Person} с наибольшей датой создания.
 *
 * @author Эльдар
 * @version 1.0
 */
public class MaxByCreationDateCommand implements Command {
    /**
     * Возвращает имя команды.
     *
     * @return "max_by_creation_date"
     */
    public String getName() {
        return "max_by_creation_date";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Выводит любой объект из коллекции, значение поля creationDate которого является максимальным";
    }
    /**
     * Выполняет команду max_by_creation_date.
     *
     * Не принимает аргументов.
     * Возвращает объект с максимальной датой создания.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (должны отсутствовать)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, Object argument) {
        if (argument != null) {
            return new CommandResult(false, "У команды max_by_creation_date не может быть аргументов", false);
        }
        Person person;
        try {
            person = manager.maxByCreationDate();
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage(), false);
        }
        return new CommandResult(true, person.toString(), false);
    }
}
