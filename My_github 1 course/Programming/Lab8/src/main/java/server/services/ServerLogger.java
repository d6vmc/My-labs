package server.services;

import java.io.IOException;
import java.util.logging.*;

public class ServerLogger {

    private static final Logger logger =
            Logger.getLogger("ServerLogger");

    static {
        logger.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        try {
            FileHandler fileHandler = new FileHandler("server.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            System.out.println("Ошибка создания логгера: " + e.getMessage());
        }
        logger.setLevel(Level.ALL);
    }
    public static Logger getLogger() {
        return logger;
    }
}