package core.commands;

import core.collection.CollectionManager;
import core.domain.Location;
import core.services.InputManager;
import core.services.InputReader;
/**
 * Команда подсчёта элементов с location больше заданного.
 *
 * Запрашивает у пользователя объект Location и выводит количество
 * элементов коллекции, у которых значение location больше введённого.
 *
 * @author Эльдар
 * @version 1.0
 */
public class CountGreaterThanLocationCommand implements Command {
    private final InputReader reader;
    private final InputManager input;
    /**
     * Конструктор команды.
     *
     * @param reader объект для чтения данных
     * @param input менеджер ввода
     */
    public CountGreaterThanLocationCommand(InputReader reader, InputManager input) {
        this.reader = reader;
        this.input = input;
    }
    /**
     * Возвращает имя команды.
     *
     * @return "count_greater_than_location"
     */
    public String getName() {
        return "count_greater_than_location";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Выводит количество элементов, значение поля location которых больше заданного";
    }
    /**
     * Выполняет команду подсчёта.
     *
     * Не принимает аргументов.
     * Считывает location и считает количество элементов,
     * превышающих его.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (должны отсутствовать)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, String[] args) {
        if (args.length != 0) {
            return new CommandResult(false, "У команды count_greater_than_location не должно быть аргументов", false);
        }
        Location location = reader.readLocation(input);
        try {
            long count = manager.countGreaterThanLocation(location);
            return new CommandResult(true, "Элементов больше: " + count, false);
        } catch (IllegalArgumentException e) {
            return  new CommandResult(false, e.getMessage(), false);
        }
    }
}
