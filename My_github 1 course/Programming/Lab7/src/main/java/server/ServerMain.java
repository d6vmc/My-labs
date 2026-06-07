package server;

import common.domain.Person;
import common.validation.Validator;
import server.auth.AuthService;
import server.auth.PasswordHasher;
import server.collection.CollectionManager;
import server.commands.*;
import server.db.DatabaseManager;
import server.db.PersonDAO;
import server.db.UserDAO;
import server.services.PersonFactory;

import java.util.HashMap;
import java.util.Map;

public class ServerMain {

    public static void main(String[] args) {
        try {
            Validator validator = new Validator();

            PersonFactory personFactory = new PersonFactory(validator);

            DatabaseManager databaseManager = new DatabaseManager(
                    "s505343",
                    "ztKBvfrN2LKWzUXB"
            );

            UserDAO userDAO = new UserDAO(databaseManager);
            PasswordHasher passwordHasher = new PasswordHasher();
            AuthService authService = new AuthService(userDAO, passwordHasher);

            PersonDAO personDAO = new PersonDAO(databaseManager);

            Map<Long, Person> loadedMap = personDAO.loadCollection(personFactory);
            CollectionManager collectionManager = new CollectionManager(
                    new HashMap<>(loadedMap),
                    personFactory
            );

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

            CommandProcessor commandProcessor = new CommandProcessor(
                    commandManager,
                    collectionManager,
                    authService,
                    personDAO
            );

            UdpServer server = new UdpServer(
                    5555,
                    commandProcessor,
                    collectionManager
            );
            server.start();

        } catch (Exception e) {
            System.out.println("Ошибка сервера: " + e.getMessage());
            e.printStackTrace();
        }
    }
}