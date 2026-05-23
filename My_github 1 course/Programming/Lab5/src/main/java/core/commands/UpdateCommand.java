package core.commands;

import core.collection.CollectionManager;
import core.domain.Person;
import core.services.PersonReader;
/**
 * Команда обновления элемента коллекции по id.
 *
 * Запрашивает новые данные объекта {@link Person} и заменяет существующий
 * элемент с указанным идентификатором.
 *
 * Использует {@link PersonReader} для ввода данных.
 *
 * @author Эльдар
 * @version 1.0
 */
public class UpdateCommand implements Command {
    /** Ридер для создания нового объекта Person */
    private final PersonReader reader;
    /**
     * Создаёт команду обновления.
     *
     * @param reader ридер для ввода данных Person
     */
    public UpdateCommand(PersonReader reader) {
        this.reader = reader;
    }
    /**
     * Возвращает имя команды.
     *
     * @return имя команды
     */
    public String getName() {
        return "update";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Обновляет элемент с заданным id";
    }
    /**
     * Выполняет команду обновления.
     *
     * Ожидает один аргумент — id элемента.
     * Считывает новый объект Person и обновляет элемент в коллекции.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (args[0] — id)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, String[] args) {
        if (args.length != 1) {
            return new CommandResult(false, "У команды update должен быть 1 аргумент", false);
        }
        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            return new CommandResult(false, "Id должен быть числом", false);
        }
        Person newPerson = reader.readPerson();
        try {
            manager.updateById(id, newPerson);
            return new CommandResult(true, "Элемент обновлен", false);
        } catch (IllegalArgumentException e) {
            return  new CommandResult(false, e.getMessage(), false);
        }
    }
}
