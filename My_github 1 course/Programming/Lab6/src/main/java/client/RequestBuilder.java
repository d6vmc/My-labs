package client;

import common.CommandType;
import common.PersonData;
import common.domain.Location;
import common.ArgumentWithKey;
import common.network.Request;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RequestBuilder {
    private final PersonReader personReader;
    private final InputReader inputReader;
    private final InputManager inputManager;

    public RequestBuilder(PersonReader personReader, InputReader inputReader, InputManager inputManager) {
        this.personReader = personReader;
        this.inputReader = inputReader;
        this.inputManager = inputManager;
    }

    public Request build(String line) {
        String[] parts = line.trim().split("\\s+");
        String commandName = parts[0].toUpperCase();
        CommandType type;
        try {
            type = CommandType.valueOf(commandName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неизвестная команда: " + parts[0]);
        }

        return switch (type) {
            case CLEAR, HELP, INFO, SHOW, EXIT, MAX_BY_CREATION_DATE ->
                    new Request(type, null);

            case REMOVE_GREATER -> {
                PersonData data = personReader.readPersonData();
                yield new Request(type, data);
            }

            case COUNT_GREATER_THAN_LOCATION -> {
                Location location = inputReader.readLocation(inputManager);
                yield new Request(type, location);
            }

            case FILTER_BY_BIRTHDAY -> {
                requireArgs(parts, "filter_by_birthday <YYYY-MM-DD>");
                try {
                    LocalDate date = LocalDate.parse(parts[1]);
                    yield new Request(type, date);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Дата должна быть в формате YYYY-MM-DD");
                }
            }

            case REMOVE_GREATER_KEY, REMOVE_KEY -> {
                requireArgs(parts, parts[0] + " <key>");
                long key = parseLong(parts[1], "key должен быть числом");
                yield new Request(type, key);
            }

            case INSERT, REPLACE_IF_LOWER, UPDATE -> {
                requireArgs(parts, parts[0] + " <key>");
                long key = parseLong(parts[1], "key должен быть числом");
                PersonData data = personReader.readPersonData();
                yield new Request(type, new ArgumentWithKey(key, data));
            }
            default -> throw new IllegalArgumentException(
                    "Команда пока не обработана на клиенте: " + type
            );
        };
    }
    private void requireArgs(String[] parts, String usage) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Использование: " + usage);
        }
    }
    private long parseLong(String value, String errorMessage) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}