package server.commands;

import server.collection.CollectionManager;
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
    /**
     * Менеджер команд
     */
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
     * <p>
     * Не принимает аргументов.
     * Выводит список всех команд и их описания.
     *
     * @param manager менеджер коллекции
     * @param args    аргументы команды (должны отсутствовать)
     * @return результат выполнения команды
     */
//    public CommandResult execute(CollectionManager manager, Object argument) {
//        if (argument != null) {
//            return new CommandResult(false, "У команды help не может быть аргументов", false);
//        }
//        String text = "";
//        for (Command command : commandManager.getCommands().values()) {
//            text += command.getName() + " : " + command.getDescription() + "\n";
//        }
//        return new CommandResult(true, text, false);
//    }
//}
    public CommandResult execute(CollectionManager manager, Object argument) {
        if (argument != null) {
            return new CommandResult(false, "У команды help не может быть аргументов", false);
        }

        String text = """
                Команды:
                help
                info
                show
                insert <key>
                update <id>
                remove_key <key>
                remove_greater
                remove_greater_key <key>
                replace_if_lower <key>
                filter_by_birthday <YYYY-MM-DD>
                count_greater_than_location
                max_by_creation_date
                clear
                execute_script <file>
                exit
                """;

        return new CommandResult(true, text, false);
    }
}