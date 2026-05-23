package server.commands;

import common.ArgumentWithKey;
import common.PersonData;
import common.domain.Person;
import server.collection.CollectionManager;
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

    public CommandResult execute(CollectionManager manager, Object argument) {
        if (!(argument instanceof ArgumentWithKey arg)) {
            return new CommandResult(false, "Для update нужен аргумент id + PersonData", false);
        }
        long id = arg.key();
        PersonData data = arg.data();
        try {
            Person newPerson = personFactory.create(data.name(), data.coordinates(), data.height(), data.birthday(), data.passportId(), data.nationality(), data.location());
            manager.updateById(id, newPerson);
            return new CommandResult(true, "Элемент обновлен", false);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage(), false);
        }
    }
}