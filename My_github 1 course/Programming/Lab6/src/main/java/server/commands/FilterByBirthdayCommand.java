package server.commands;

import server.collection.CollectionManager;
import common.domain.Person;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
/**
 * Команда фильтрации элементов по дате рождения.
 *
 * Выводит все объекты {@link Person}, у которых поле birthday
 * совпадает с заданным значением.
 *
 * @author Эльдар
 * @version 1.0
 */
public class FilterByBirthdayCommand implements Command {
    /**
     * Возвращает имя команды.
     *
     * @return "filter_by_birthday"
     */
     public String getName() {
        return "filter_by_birthday";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Выводит элементы, значение поля birthday которых равно заданному";
    }
    /**
     * Выполняет команду filter_by_birthday.
     *
     * Ожидает один аргумент — дату в формате ISO (YYYY-MM-DD).
     * Возвращает все элементы с совпадающей датой рождения.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (args[0] — дата)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, Object argument) {
        if (argument == null) {
            return new CommandResult(false, "У команды filter_by_birthday должен быть 1 аргумент", false);
        }
        LocalDate birthday;
        String date = (String) argument;
        try {
            birthday = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return new CommandResult(false, e.getMessage(), false);
        }
        List<Person> persons;
        try {
            persons = manager.filterByBirthday(birthday);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage(), false);
        }
        if (persons.isEmpty()) {
            return new CommandResult(true, "Ничего не найдено", false);
        }
        StringBuilder text = new StringBuilder();
        for (Person person : persons) {
            text.append(person).append("\n");
        }
        return new CommandResult(true, text.toString(), false);
    }
}
