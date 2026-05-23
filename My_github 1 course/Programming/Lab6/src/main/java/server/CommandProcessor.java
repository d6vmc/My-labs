package server;

import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;
import server.commands.CommandManager;
import server.commands.CommandResult;

public class CommandProcessor {

    private final CommandManager commandManager;
    private final CollectionManager collectionManager;

    public CommandProcessor(
            CommandManager commandManager,
            CollectionManager collectionManager
    ) {
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
    }

    public Response process(Request request) {

        try {
            String name = request.commandType().name().toLowerCase();
            Object argument = request.argument();
            CommandResult result = commandManager.execute(name, argument, collectionManager);

            return new Response(result.isSuccess(), result.getMessage(), result.isExit(), null);
        } catch (Exception e) {
            return new Response(false, "Ошибка выполнения команды: " + e.getMessage(),false,null);
        }
    }
}