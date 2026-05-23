package core.commands;

import core.collection.CollectionManager;
import core.domain.Person;
import core.services.PersonReader;
/**
 * Команда замены элемента по ключу, если новое значение меньше старого.
 *
 * Сравнение выполняется с использованием {@link Person#compareTo(Person)}.
 * Если новый объект меньше существующего, происходит замена.
 *
 * Использует {@link PersonReader} для ввода нового объекта.
 *
 * @author Эльдар
 * @version 1.0
 */
public class ReplaceIfLowerCommand implements Command {
    /** Ридер для создания нового объекта Person */
    private final PersonReader reader;
    /**
     * Создаёт команду replace_if_lower.
     *
     * @param reader ридер для ввода данных Person
     */
    public ReplaceIfLowerCommand(PersonReader reader) {
        this.reader = reader;
    }
    /**
     * Возвращает имя команды.
     *
     * @return "replace_if_lower"
     */
    public String getName() {
        return "replace_if_lower";
    }
    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return "Заменяет значение по ключу, если новое значение меньше старого";
    }

    /**
     * Выполняет команду replace_if_lower.
     *
     * Ожидает один аргумент — ключ элемента.
     * Считывает новый объект Person и заменяет старый,
     * если новый меньше по сравнению.
     *
     * @param manager менеджер коллекции
     * @param args аргументы команды (args[0] — ключ)
     * @return результат выполнения команды
     */
    public CommandResult execute(CollectionManager manager, String[] args) {
        if (args.length != 1) {
            return new CommandResult(false, "У команды replace_if_lower должен быть 1 аргумент", false);
        }
        Long key;
        try {
            key = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            return new CommandResult(false, "Key должен быть числом", false);
        }
        Person newPerson = reader.readPerson();
        try {
            boolean check = manager.replaceIfLower(key, newPerson);
            if (check) {
                return new CommandResult(true, "Замена произведена", false);
            }else {
                return new CommandResult(true, "Замена не произведена, новый элемент не меньше старого", false);
            }
        } catch (IllegalArgumentException e) {
            return  new CommandResult(false, e.getMessage(), false);
        }
    }
}
