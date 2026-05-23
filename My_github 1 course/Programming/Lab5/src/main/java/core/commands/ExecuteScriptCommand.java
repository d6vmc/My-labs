package core.commands;

import core.collection.CollectionManager;
import core.services.InputManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
/**
 * Команда выполнения скрипта из файла.
 *
 * Считывает команды из указанного файла и выполняет их так же,
 * как если бы они вводились пользователем в интерактивном режиме.
 * Поддерживает вложенные скрипты и предотвращает рекурсивные вызовы.
 *
 * @author Эльдар
 * @version 1.0
 */
public class ExecuteScriptCommand implements Command {
    private final CommandManager commandManager;
    private final InputManager input;
    private final List<String> activeScripts;
    /**
     * Конструктор команды.
     *
     * @param commandManager менеджер команд
     * @param input менеджер ввода
     * @param activeScripts список выполняемых скриптов (для защиты от рекурсии)
     */
    public ExecuteScriptCommand(CommandManager commandManager, InputManager input, List<String> activeScripts) {
        this.commandManager = commandManager;
        this.input = input;
        this.activeScripts = activeScripts;
    }
    /**
     * Возвращает имя команды.
     *
     * @return "execute_script"
     */
    public String getName() {
        return "execute_script";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "считывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
    /**
     * Выполняет скрипт из файла.
     *
     * Проверяет аргументы, наличие файла и предотвращает рекурсивный вызов.
     * Переключает источник ввода на файл и исполняет команды построчно.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (имя файла)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, String[] args) {
        if (args.length != 1) {
            return new CommandResult(false, "У команды execute_script должен быть 1 аргумент", false);
        }
        String filename = args[0];
        File scriptFile = new File(filename).getAbsoluteFile();
        String absolutePath = scriptFile.getAbsolutePath();
        if (activeScripts.contains(absolutePath)) {
            return new CommandResult(false, "Обнаружена рекурсия", false);
        }
        Scanner scriptScanner = null;
        try {
            scriptScanner = new Scanner(scriptFile);
            activeScripts.add(absolutePath);
        } catch (IllegalArgumentException | FileNotFoundException e){
            activeScripts.remove(absolutePath);
            input.useConsole();
            return new CommandResult(false, e.getMessage(), false);
        }
        input.useScript(scriptScanner);
        while (true) {
            String line = input.readLine("~ ");
            if (line == null) {
                input.useConsole();
                break;
            }
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            String name = parts[0];
            String[] cmdArgs = new String[parts.length - 1];
            for (int i = 1; i < parts.length; i++) {
                cmdArgs[i - 1] = parts[i];
            }
            if (name.equals("execute_script")) {
                if (cmdArgs.length != 1) {
                    activeScripts.remove(absolutePath);
                    return new CommandResult(false, "У команды execute_script должен быть 1 аргумент", false);
                }
                String nestedAbsolutePath = new File(cmdArgs[0]).getAbsoluteFile().getAbsolutePath();
                if (activeScripts.contains(nestedAbsolutePath)) {
                    activeScripts.remove(absolutePath);
                    input.useConsole();
                    return new CommandResult(false, "Обнаружена рекурсия", false);
                }
            }
            CommandResult result = commandManager.execute(name, cmdArgs, manager);
            if (result.getMessage() != null) {
                System.out.println(result.getMessage());
            }
            if (result.isExit()) {
                input.useConsole();
                break;
            }
        }
        activeScripts.remove(absolutePath);
        input.useConsole();
        return new CommandResult(true, "Скрипт выполнен успешно", false);
    }
}
