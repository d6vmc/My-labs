package server.commands;

import server.collection.CollectionManager;

import java.util.HashMap;
import java.util.Map;
/**
 * Менеджер команд.
 *
 * Хранит и управляет зарегистрированными командами,
 * а также отвечает за их выполнение по имени.
 *
 * @author Эльдар
 * @version 1.0
 */
public class CommandManager {
    private final Map<String, Command> commands;
    /**
     * Создаёт новый менеджер команд.
     */
    public CommandManager() {
        this.commands = new HashMap<>();
    }
    /**
     * Регистрирует новую команду.
     *
     * @param command команда для регистрации
     */
    public void register(Command command) {
        this.commands.put(command.getName(), command);
    }
    /**
     * Возвращает все зарегистрированные команды.
     *
     * @return карта команд (имя → команда)
     */
    public Map<String, Command> getCommands() {
        return commands;
    }
    /**
     * Выполняет команду по имени.
     *
     * Если команда не найдена — возвращает ошибку.
     *
     * @param name имя команды
     * @param args аргументы команды
     * @param manager менеджер коллекции
     * @return результат выполнения команды
     */
    public CommandResult execute(String name, Object argument, CollectionManager manager) {
        Command command = commands.get(name);
        if (command == null) {
            return new CommandResult(false, "Неизвестная команда", false);
        }
        return command.execute(manager, argument);
    }
}
