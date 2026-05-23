package core.commands;

import core.collection.CollectionManager;
import core.domain.Person;
import core.services.PersonReader;
/**
 * Команда добавления нового элемента в коллекцию.
 *
 * @author Эльдар
 * @version 1.0
 */
public class InsertCommand implements Command {
    private final PersonReader reader;
    public  InsertCommand(PersonReader reader) {
        this.reader = reader;
    }
    public String getName() {
        return "insert";
    }
    public String getDescription() {
        return "Добавляет новый элемент с заданным ключом";
    }
    /**
     * Выполняет команду insert.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды, где args[0] — ключ
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, String[] args) {
        if (args.length != 1) {
            return new CommandResult(false, "У команды insert должен быть 1 аргумент", false);
        }
        Long key;
        try {
            key = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            return new CommandResult(false, "Ключ должен быть числом", false);
        }
        Person person = reader.readPerson();
        try {
            manager.insert(key, person);
            return new CommandResult(true, "Элемент добавлен", false);
        } catch (IllegalArgumentException e) {
            return  new CommandResult(false, e.getMessage(), false);
        }
    }
}
