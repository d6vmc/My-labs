package server.commands;

import common.ArgumentWithKey;
import common.PersonData;
import common.domain.Person;
import server.collection.CollectionManager;
import server.db.PersonDAO;
import server.services.PersonFactory;

public class ReplaceIfLowerCommand implements Command {
    private final PersonFactory personFactory;

    public ReplaceIfLowerCommand(PersonFactory personFactory) {
        this.personFactory = personFactory;
    }

    public String getName() {
        return "replace_if_lower";
    }

    public String getDescription() {
        return "Заменяет значение по ключу, если новое значение меньше старого";
    }

    public CommandResult execute(CollectionManager manager, Object argument, int userId, PersonDAO personDAO) {
        if (!(argument instanceof ArgumentWithKey arg)) {
            return new CommandResult(false, "Для replace_if_lower нужен аргумент key + PersonData", false);
        }
        long key = arg.key();
        PersonData data = arg.data();
        try {
            Person oldPerson = manager.getMap().get(key);
            if (oldPerson == null) {
                return new CommandResult(false, "Элемент с таким ключом не найден", false);
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
            if (newPerson.compareTo(oldPerson) >= 0) {
                return new CommandResult(true, "Замена не произведена, новый элемент не меньше старого", false);
            }
            boolean updated = personDAO.update(key, data, userId);
            if (!updated) {
                return new CommandResult(false, "Элемент не найден или не принадлежит вам", false);
            }
            manager.updateById(key, newPerson);
            return new CommandResult(true, "Замена произведена", false);
        } catch (Exception e) {
            return new CommandResult(false, "Ошибка замены элемента: " + e.getMessage(), false);
        }
    }
}