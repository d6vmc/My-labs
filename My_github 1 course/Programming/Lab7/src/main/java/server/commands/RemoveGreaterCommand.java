package server.commands;

import common.PersonData;
import common.domain.Person;
import server.collection.CollectionManager;
import server.db.PersonDAO;
import server.services.PersonFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemoveGreaterCommand implements Command {
    private final PersonFactory personFactory;

    public RemoveGreaterCommand(PersonFactory personFactory) {
        this.personFactory = personFactory;
    }

    public String getName() {
        return "remove_greater";
    }

    public String getDescription() {
        return "Удаляет из коллекции все элементы, превышающие заданный";
    }

    public CommandResult execute(CollectionManager manager, Object argument, int userId, PersonDAO personDAO) {
        if (!(argument instanceof PersonData data)) {
            return new CommandResult(false, "Для remove_greater нужен объект PersonData", false);
        }
        try {
            Person comparedPerson = personFactory.create(
                    1L,
                    data.name(),
                    data.coordinates(),
                    new java.util.Date(),
                    data.height(),
                    data.birthday(),
                    data.passportId(),
                    data.nationality(),
                    data.location()
            );
            List<Long> idsToRemove = new ArrayList<>();
            for (Map.Entry<Long, Person> entry : manager.getMap().entrySet()) {
                if (entry.getValue().compareTo(comparedPerson) > 0) {
                    idsToRemove.add(entry.getKey());
                }
            }
            if (idsToRemove.isEmpty()) {
                return new CommandResult(true, "Элементов для удаления не найдено", false);
            }
            List<Long> removedIds = personDAO.removeByIds(idsToRemove, userId);
            if (removedIds.isEmpty()) {
                return new CommandResult(false, "Подходящие элементы не найдены или не принадлежат вам", false);
            }
            for (Long id : removedIds) {
                manager.removeKey(id);
            }
            return new CommandResult(true, "Элементов удалено: " + removedIds.size(), false);
        } catch (Exception e) {
            return new CommandResult(false, "Ошибка удаления элементов: " + e.getMessage(), false);
        }
    }
}