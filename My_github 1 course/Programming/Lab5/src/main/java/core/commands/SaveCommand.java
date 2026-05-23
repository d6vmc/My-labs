package core.commands;

import core.collection.CollectionManager;
import core.services.FileManager;
/**
 * Команда сохранения коллекции в файл.
 *
 * Использует {@link FileManager} для записи текущего состояния
 * коллекции в файл.
 *
 * @author Эльдар
 * @version 1.0
 */
public class SaveCommand implements Command {
    /** Менеджер работы с файлами */
    private final FileManager fileManager;
    /**
     * Создаёт команду сохранения.
     *
     * @param fileManager менеджер для записи данных в файл
     */
    public SaveCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }
    /**
     * Возвращает имя команды.
     *
     * @return "save"
     */
    public String getName() {
        return "save";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Сохраняет коллекцию в файл";
    }
    /**
     * Выполняет команду save.
     *
     * Не принимает аргументов.
     * Сохраняет текущую коллекцию в файл.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (должны отсутствовать)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, String[] args) {
        if (args.length != 0) {
            return new CommandResult(false, "У команды save не должно быть аргументов", false);
        }
        fileManager.save(manager.getMap());
        return new CommandResult(true, "Коллекция сохранена", false);
    }
}