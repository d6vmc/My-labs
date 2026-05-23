package server.commands;

import common.domain.Location;
import server.collection.CollectionManager;

public class CountGreaterThanLocationCommand implements Command {

    public String getName() {
        return "count_greater_than_location";
    }

    public String getDescription() {
        return "Выводит количество элементов, значение поля location которых больше заданного";
    }

    public CommandResult execute(CollectionManager manager, Object argument) {

        if (!(argument instanceof Location location)) {
            return new CommandResult(false, "Для count_greater_than_location нужен объект Location", false);
        }

        try {
            long count = manager.countGreaterThanLocation(location);
            return new CommandResult(true, "Элементов больше: " + count, false);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, e.getMessage(), false
            );
        }
    }
}