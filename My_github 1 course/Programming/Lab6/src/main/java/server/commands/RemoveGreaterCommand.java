package server.commands;

import common.ArgumentWithKey;
import common.PersonData;
import server.collection.CollectionManager;
import common.domain.Person;
import server.services.PersonFactory;

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
    private final PersonFactory personFactory;
    /**
     * Создаёт команду remove_greater.
     *
     * @param reader ридер для ввода данных Person
     */
    public RemoveGreaterCommand(PersonFactory personFactory) {
        this.personFactory = personFactory;
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
    public CommandResult execute(CollectionManager manager, Object argument) {
        if (!(argument instanceof PersonData data)) {
            return new CommandResult(false, "Для remove_greater нужен объект PersonData", false);
        }
        try {
            Person person = personFactory.create(data.name(), data.coordinates(), data.height(), data.birthday(), data.passportId(), data.nationality(), data.location());
            long cnt = manager.removeGreater(person);
            return new CommandResult(true, "Элементов удалено: " + cnt, false);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage(), false);
        }
    }
}
