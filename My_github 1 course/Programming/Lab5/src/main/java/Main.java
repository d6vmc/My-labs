import core.collection.CollectionManager;
import core.commands.*;
import core.domain.Person;
import core.services.*;
import core.validation.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Главный класс приложения.
 *
 * Отвечает за загрузку коллекции из файла, инициализацию всех необходимых компонентов, регистрацию команд и запуск интерактивного режима работы программы.
 *
 * @author Эльдар
 * @version 1.0
 */

public class Main {
     /**
     * Точка входа в программу.
     *
     * Загружает данные из JSON-файла, настраивает генератор идентификаторов,
     * создаёт менеджеры и запускает цикл обработки пользовательских команд.
     *
     * @param args аргументы командной строки, где args[0] — путь к файлу с данными
     */
    public  static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Не передано имя файла");
            System.exit(1);
        }
        String filename = args[0];
        Validator validator = new Validator();
        FileManager fileManager = new FileManager(filename, validator);
        IdGenerator idGenerator = new PersonIdGenerator(0);
        PersonFactory personFactory = new PersonFactory(idGenerator, validator);
        HashMap<Long, Person> loadedMap = fileManager.load();
        long MaxId = 0;
        for (Person person : loadedMap.values()) {
            if (MaxId < person.getId()) {
                MaxId = person.getId();
            }
        }
        idGenerator.setCurrentMaxId(MaxId);
        CollectionManager collectionManager = new CollectionManager(loadedMap, personFactory);
        CommandManager commandManager = new CommandManager();
        Scanner scanner = new Scanner(System.in);
        InputManager input = new InputManager(scanner);
        InputReader reader = new InputReader();
        PersonReader personReader = new PersonReader(input, reader, personFactory);
        List<String> activeScripts = new ArrayList<>();

        commandManager.register(new HelpCommand(commandManager));
        commandManager.register(new InfoCommand());
        commandManager.register(new ShowCommand());
        commandManager.register(new ExitCommand());
        commandManager.register(new ClearCommand());
        commandManager.register(new RemoveKeyCommand());
        commandManager.register(new RemoveGreaterKeyCommand());
        commandManager.register(new MaxByCreationDateCommand());
        commandManager.register(new InsertCommand(personReader));
        commandManager.register(new UpdateCommand(personReader));
        commandManager.register(new RemoveGreaterCommand(personReader));
        commandManager.register(new ReplaceIfLowerCommand(personReader));
        commandManager.register(new FilterByBirthdayCommand());
        commandManager.register(new CountGreaterThanLocationCommand(reader, input));
        commandManager.register(new SaveCommand(fileManager));
        commandManager.register(new ExecuteScriptCommand(commandManager, input, activeScripts));
        while (true) {
            String line = input.readLine("~ ");
            if (line == null) {
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

            CommandResult result = commandManager.execute(name, cmdArgs, collectionManager);
            if (result.getMessage() != null) {
                System.out.println(result.getMessage());
            }
            if (result.isExit()) {
                break;
            }
        }
    }
}