package common;

public enum CommandType {
    CLEAR,
    COUNT_GREATER_THAN_LOCATION,
    EXECUTE_SCRIPT,
    EXIT,
    FILTER_BY_BIRTHDAY,
    HELP,
    INFO,
    INSERT,
    MAX_BY_CREATION_DATE,
    REMOVE_GREATER,
    REMOVE_GREATER_KEY,
    REMOVE_KEY,
    REPLACE_IF_LOWER,
    SHOW,
    UPDATE;
    public static CommandType fromString(String value) {
        try {
            return CommandType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
