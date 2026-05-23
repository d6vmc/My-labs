package server.commands;

import server.collection.CollectionManager;
import common.domain.Person;

import java.util.Collection;
/**
 * Команда вывода всех элементов коллекции.
 *
 * Получает все объекты {@link Person} из {@link CollectionManager}
 * и возвращает их строковое представление.
 *
 * Если коллекция пуста, сообщает об этом.
 *
 * @author Эльдар
 * @version 1.0
 */
public class ShowCommand implements Command{
    /**
     * Возвращает имя команды.
     *
     * @return "show"
     */
    public String getName() {
        return "show";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Выводит все элементы коллекции";
    }
    /**
     * Выполняет команду show.
     *
     * Не принимает аргументов.
     * Возвращает список всех элементов коллекции в виде строки.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (должны отсутствовать)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, Object argument) {
        if (argument != null) {
            return new CommandResult(false, "У команды show не может быть аргументов", false);
        }
        Collection<Person> persons = manager.show();
        if (persons.isEmpty()) {
            return new CommandResult(true, "Коллекция пустая", false);
        }
        StringBuilder text = new StringBuilder();
        for (Person person : persons) {
            text.append(person).append("\n");
        }
        return new CommandResult(true, text.toString(), false);
    }
}
