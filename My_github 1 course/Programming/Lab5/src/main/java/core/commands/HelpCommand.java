package core.commands;

import core.collection.CollectionManager;
/**
 * Команда вывода списка всех доступных команд.
 *
 * Перебирает зарегистрированные команды в {@link CommandManager}
 * и выводит их имена и описания.
 *
 * @author Эльдар
 * @version 1.0
 */
public class HelpCommand implements Command {
    /** Менеджер команд */
    private final CommandManager commandManager;
    /**
     * Создаёт команду help.
     *
     * @param commandManager менеджер команд
     */
    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    /**
     * Возвращает имя команды.
     *
     * @return "help"
     */
    public String getName() {
        return "help";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Выводит список всех доступных команд с описаниями их работы";
    }
    /**
     * Выполняет команду help.
     *
     * Не принимает аргументов.
     * Выводит список всех команд и их описания.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (должны отсутствовать)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, String[] args) {
        if (args.length != 0) {
            return new CommandResult(false, "У команды help не может быть аргументов", false);
        }
        String text = "";
        for (Command command : commandManager.getCommands().values()) {
            text += command.getName() + " : " + command.getDescription() + "\n";
        }
        return new CommandResult(true, text, false);
    }
}
