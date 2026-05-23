package server.commands;

import common.ArgumentWithKey;
import common.PersonData;
import common.domain.Person;
import server.collection.CollectionManager;
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

    public CommandResult execute(CollectionManager manager, Object argument) {

        if (!(argument instanceof ArgumentWithKey arg)) {
            return new CommandResult(false, "Для replace_if_lower нужен аргумент key + PersonData", false);
        }

        long key = arg.key();
        PersonData data = arg.data();
        try {
            Person newPerson = personFactory.create(data.name(), data.coordinates(), data.height(), data.birthday(), data.passportId(), data.nationality(), data.location());
            boolean replaced = manager.replaceIfLower(key, newPerson);
            if (replaced) {
                return new CommandResult(true, "Замена произведена", false);
            }
            return new CommandResult(true, "Замена не произведена, новый элемент не меньше старого", false);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage(), false);
        }
    }
}