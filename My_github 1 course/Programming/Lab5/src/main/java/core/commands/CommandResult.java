package core.commands;
/**
 * Результат выполнения команды.
 *
 * Содержит информацию об успешности выполнения,
 * сообщение для вывода пользователю и флаг завершения программы.
 *
 * @author Эльдар
 * @version 1.0
 */
public class CommandResult {
    private final boolean success;
    private final String message;
    private final boolean exit;
    /**
     * Конструктор результата команды.
     *
     * @param success успешность выполнения команды
     * @param message сообщение для пользователя (может быть null)
     * @param exit флаг завершения программы
     */
    public CommandResult(boolean success, String message, boolean exit) {
        this.success = success;
        this.message = message;
        this.exit = exit;
    }
    /**
     * Возвращает успешность выполнения команды.
     *
     * @return true если команда выполнена успешно
     */
    public boolean isSuccess() {
        return success;
    }
    /**
     * Возвращает сообщение команды.
     *
     * @return сообщение для вывода пользователю
     */
    public String getMessage() {
        return message;
    }
    /**
     * Возвращает флаг завершения программы.
     *
     * @return true если требуется завершить программу
     */
    public boolean isExit() {
        return exit;
    }
}
