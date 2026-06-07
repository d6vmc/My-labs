package server;

import common.CommandType;
import common.network.Request;
import common.network.Response;
import server.auth.AuthService;
import server.collection.CollectionManager;
import server.commands.CommandManager;
import server.commands.CommandResult;
import server.db.PersonDAO;

import java.sql.SQLException;

public class CommandProcessor {

    private final CommandManager commandManager;
    private final CollectionManager collectionManager;
    private final AuthService authService;
    private final PersonDAO personDAO;

    public CommandProcessor(
            CommandManager commandManager,
            CollectionManager collectionManager,
            AuthService authService, PersonDAO personDAO
    ) {
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
        this.authService = authService;
        this.personDAO = personDAO;
    }

    public Response process(Request request) {
        try {
            if (request.commandType() == CommandType.REGISTER) {
                authService.register(request.login(), request.password());
                return new Response(true, "Регистрация успешна", false, null);
            }

            if (request.commandType() == CommandType.LOGIN) {
                Integer userId = authService.authenticate(request.login(), request.password());

                if (userId == null) {
                    return new Response(false, "Неверный логин или пароль", false, null);
                }

                return new Response(true, "Вход выполнен", false, null);
            }

            Integer userId = authService.authenticate(request.login(), request.password());

            if (userId == null) {
                return new Response(false, "Неверный логин или пароль", false, null);
            }

            String name = request.commandType().name().toLowerCase();
            Object argument = request.argument();

            CommandResult result = commandManager.execute(name, argument, collectionManager, userId, personDAO);

            return new Response(result.isSuccess(), result.getMessage(), result.isExit(), null);

        } catch (IllegalArgumentException e) {
            return new Response(false, e.getMessage(), false, null);
        } catch (SQLException e) {
            return new Response(false, "Ошибка базы данных: " + e.getMessage(), false, null);
        } catch (Exception e) {
            return new Response(false, "Ошибка выполнения команды: " + e.getMessage(), false, null);
        }
    }
}