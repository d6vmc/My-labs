package server.commands;

import common.ArgumentWithKey;
import common.PersonData;
import server.collection.CollectionManager;
import common.domain.Person;
import server.services.PersonFactory;

/**
 * Команда добавления нового элемента в коллекцию.
 *
 * @author Эльдар
 * @version 1.0
 */
public class InsertCommand implements Command {
    private final PersonFactory personFactory;
    public  InsertCommand(PersonFactory personFactory) {
        this.personFactory = personFactory;
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
    public CommandResult execute(CollectionManager manager, Object argument) {
        if (!(argument instanceof ArgumentWithKey arg)) {
            return new CommandResult(false, "Для insert нужен аргумент key + PersonData", false);
        }
        PersonData data = arg.data();
        long key = arg.key();
        try {
            Person person = personFactory.create(data.name(), data.coordinates(), data.height(), data.birthday(), data.passportId(), data.nationality(), data.location());
            manager.insert(key, person);
            return new CommandResult(true, "Элемент добавлен", false);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage(), false);
        }
    }
}
