package server.commands;

import common.PersonData;
import common.domain.Person;
import server.collection.CollectionManager;
import server.db.PersonDAO;
import server.services.PersonFactory;

/**
 * Команда добавления нового элемента в коллекцию.
 *
 * @author Эльдар
 * @version 1.0
 */
public class InsertCommand implements Command {
    private final PersonFactory personFactory;

    public InsertCommand(PersonFactory personFactory) {
        this.personFactory = personFactory;
    }

    public String getName() {
        return "insert";
    }

    public String getDescription() {
        return "Добавляет новый элемент в коллекцию";
    }

    public CommandResult execute(CollectionManager manager, Object argument, int userId, PersonDAO personDAO) {
        if (!(argument instanceof PersonData data)) {
            return new CommandResult(false, "Для insert нужен аргумент PersonData", false);
        }

        try {
            java.util.Date creationDate = new java.util.Date();

            long id = personDAO.insert(data, userId, creationDate);

            Person person = personFactory.create(
                    id,
                    data.name(),
                    data.coordinates(),
                    creationDate,
                    data.height(),
                    data.birthday(),
                    data.passportId(),
                    data.nationality(),
                    data.location()
            );

            manager.insert(id, person);

            return new CommandResult(true, "Элемент добавлен с id = " + id, false);
        } catch (Exception e) {
            return new CommandResult(false, "Ошибка добавления элемента: " + e.getMessage(), false);
        }
    }
}