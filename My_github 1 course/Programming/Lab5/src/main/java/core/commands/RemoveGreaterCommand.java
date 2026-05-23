package core.commands;

import core.collection.CollectionManager;
import core.domain.Person;
import core.services.PersonReader;
/**
 * Команда удаления элементов, превышающих заданный.
 *
 * Считывает объект {@link Person} и удаляет из коллекции все элементы,
 * которые больше него согласно {@link Person#compareTo(Person)}.
 *
 * Использует {@link PersonReader} для ввода объекта.
 *
 * @author Эльдар
 * @version 1.0
 */
public class RemoveGreaterCommand implements Command {
    /** Ридер для создания объекта Person */
    private final PersonReader reader;
    /**
     * Создаёт команду remove_greater.
     *
     * @param reader ридер для ввода данных Person
     */
    public RemoveGreaterCommand(PersonReader reader) {
        this.reader = reader;
    }
    /**
     * Возвращает имя команды.
     *
     * @return "remove_greater"
     */
    public String getName() {
        return "remove_greater";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Удаляет из коллекции все элементы, превышающие заданный";
    }
    /**
     * Выполняет команду remove_greater.
     *
     * Не принимает аргументов.
     * Считывает объект Person и удаляет все элементы,
     * которые больше него по сравнению.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (должны отсутствовать)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, String[] args) {
        if (args.length != 0) {
            return new CommandResult(false, "У команды remove_greater не должно быть аргументов", false);
        }
        Person newPerson = reader.readPerson();
        try {
            long cnt = manager.removeGreater(newPerson);
            return new CommandResult(true, "Элементов удалено: " + cnt, false);
        } catch (IllegalArgumentException e) {
            return  new CommandResult(false, e.getMessage(), false);
        }
    }
}
