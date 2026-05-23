package server;

import common.domain.Person;
import common.validation.Validator;
import server.collection.CollectionManager;
import server.commands.*;
import server.services.FileManager;
import server.services.IdGenerator;
import server.services.PersonFactory;
import server.services.PersonIdGenerator;

import java.util.HashMap;

public class ServerMain {

    public static void main(String[] args) {

        try {
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

            commandManager.register(new HelpCommand(commandManager));
            commandManager.register(new InfoCommand());
            commandManager.register(new ShowCommand());
            commandManager.register(new ExitCommand());
            commandManager.register(new ClearCommand());
            commandManager.register(new RemoveKeyCommand());
            commandManager.register(new RemoveGreaterKeyCommand());
            commandManager.register(new MaxByCreationDateCommand());
            commandManager.register(new InsertCommand(personFactory));
            commandManager.register(new UpdateCommand(personFactory));
            commandManager.register(new RemoveGreaterCommand(personFactory));
            commandManager.register(new ReplaceIfLowerCommand(personFactory));
            commandManager.register(new FilterByBirthdayCommand());
            commandManager.register(new CountGreaterThanLocationCommand());

            CommandProcessor commandProcessor = new CommandProcessor(commandManager, collectionManager);
            UdpServer server = new UdpServer(5555, commandProcessor, fileManager, collectionManager);
            server.start();
        } catch (Exception e) {
            System.out.println("Ошибка сервера: " + e.getMessage());
        }
    }
}