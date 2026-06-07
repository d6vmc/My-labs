package server.commands;

import common.ArgumentWithKey;
import common.PersonData;
import common.domain.Person;
import server.collection.CollectionManager;
import server.db.PersonDAO;
import server.services.PersonFactory;

public class UpdateCommand implements Command {
    private final PersonFactory personFactory;

    public UpdateCommand(PersonFactory personFactory) {
        this.personFactory = personFactory;
    }

    public String getName() {
        return "update";
    }

    public String getDescription() {
        return "Обновляет элемент с заданным id";
    }

    public CommandResult execute(CollectionManager manager, Object argument, int userId, PersonDAO personDAO) {
        if (!(argument instanceof ArgumentWithKey arg)) {
            return new CommandResult(false, "Для update нужен аргумент id + PersonData", false);
        }
        long id = arg.key();
        PersonData data = arg.data();
        try {
            Person oldPerson = manager.getMap().get(id);
            if (oldPerson == null) {
                return new CommandResult(false, "Элемент с таким id не найден", false);
            }
            boolean updated = personDAO.update(id, data, userId);
            if (!updated) {
                return new CommandResult(false, "Элемент не найден или не принадлежит вам", false);
            }
            Person newPerson = personFactory.create(
                    oldPerson.getId(),
                    data.name(),
                    data.coordinates(),
                    oldPerson.getCreationDate(),
                    data.height(),
                    data.birthday(),
                    data.passportId(),
                    data.nationality(),
                    data.location()
            );
            manager.updateById(id, newPerson);
            return new CommandResult(true, "Элемент обновлен", false);
        } catch (Exception e) {
            return new CommandResult(false, "Ошибка обновления элемента: " + e.getMessage(), false);
        }
    }
}