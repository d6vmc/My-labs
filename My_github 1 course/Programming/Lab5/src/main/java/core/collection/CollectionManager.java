package core.collection;

import java.time.LocalDate;
import java.util.*;

import core.domain.Location;
import core.services.PersonFactory;

import core.domain.Person;
/**
 * Менеджер коллекции типа {@code HashMap<Long, Person>}.
 *
 * Отвечает за хранение, добавление, удаление, поиск, обновление
 * и другие операции над коллекцией объектов {@link Person}.
 *
 * @author Эльдар
 * @version 1.0
 */
public class CollectionManager {
    private Date initDate;
    private HashMap<Long, Person> map;
    private final PersonFactory factory;
    /**
     * Создаёт менеджер коллекции.
     *
     * @param map коллекция элементов
     * @param factory фабрика объектов Person
     */
    public CollectionManager(HashMap<Long, Person> map, PersonFactory factory) {
        this.initDate = new Date();
        this.map = map;
        this.factory = factory;
    }
    /**
     * Возвращает количество элементов в коллекции.
     *
     * @return размер коллекции
     */
    public int size() {
        return map.size();
    }
    /**
     * Добавляет новый элемент в коллекцию по заданному ключу.
     *
     * @param key ключ элемента
     * @param person добавляемый объект Person
     * @throws IllegalArgumentException если key или person равны null, либо ключ уже занят
     */
    public void insert(Long key, Person person) {
        if (key == null || person == null) {
            throw new IllegalArgumentException("ключ или person не может быть null");
        }
        else if (map.containsKey(key)) {
            throw new IllegalArgumentException("Ключ уже занят");
        } else {
            map.put(key, person);
        }
    }
    /**
     * Удаляет элемент из коллекции по ключу.
     *
     * @param key ключ удаляемого элемента
     * @throws IllegalArgumentException если ключ равен null или отсутствует в коллекции
     */
    public void removeKey(Long key) {
        if (key == null) {
            throw new IllegalArgumentException("ключ не может быть null");
        }
        else if (map.containsKey(key)) {
            map.remove(key);
        } else{
            throw new IllegalArgumentException("Нет такого ключа");
        }
    }
    /**
     * Возвращает все элементы коллекции.
     *
     * @return коллекция объектов Person
     */
    public Collection<Person> show() {
        return map.values();
    }

    /**
     * Возвращает информацию о коллекции.
     *
     * @return строка с типом коллекции, датой инициализации и количеством элементов
     */
    public String info() {
        return "Тип: " + map.getClass().getSimpleName() +
                "\nДата инициализации: " + initDate.toString() +
                "\nКоличество Элементов: " + map.size();
    }

    public void clear() {
        map.clear();
    }
    /**
     * Обновляет элемент коллекции по id.
     *
     * Находит объект с указанным id и заменяет его,
     * сохраняя старые id и дату создания.
     *
     * @param id идентификатор обновляемого объекта
     * @param person новый объект
     * @throws IllegalArgumentException если объект с таким id не найден
     */
    public void updateById(long id, Person person) {
        for (Map.Entry<Long, Person> entry : map.entrySet()) {
            Long key = entry.getKey();
            Person value = entry.getValue();
            if (value.getId() == id) {
                Person updatedPerson = factory.updatePerson(person, value);
                map.put(key, updatedPerson);
                return;
            }
        }
        throw new IllegalArgumentException("Не найден нужный id");
    }
    /**
     * Удаляет все элементы, превышающие заданный.
     *
     * Сравнение выполняется через {@link Person#compareTo(Person)}.
     *
     * @param person объект для сравнения
     * @return количество удалённых элементов
     * @throws IllegalArgumentException если person равен null
     */
    public long removeGreater(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("person не может быть null");
        }
        List<Long> keys = new ArrayList<>();
        for (Map.Entry<Long, Person> entry : map.entrySet()) {
            if (entry.getValue().compareTo(person) > 0) {
                keys.add(entry.getKey());
            }
        }
        long counter = keys.size();
        for (Long key : keys) {
            removeKey(key);
        }
        return counter;
    }
    /**
     * Удаляет все элементы, ключ которых больше заданного.
     *
     * @param key ключ для сравнения
     * @return количество удалённых элементов
     * @throws IllegalArgumentException если key равен null
     */
    public long removeGreaterKey(Long key) {
        if (key == null) {
            throw new IllegalArgumentException("key не может быть null");
        }
        List<Long> keys = new ArrayList<>();
        for (Map.Entry<Long, Person> entry : map.entrySet()) {
            if (entry.getKey() > key) {
                keys.add(entry.getKey());
            }
        }
        long counter = keys.size();
        for (Long i : keys) {
            removeKey(i);
        }
        return counter;
    }
    /**
     * Заменяет элемент по ключу, если новый объект меньше старого.
     *
     * Сравнение выполняется через {@link Person#compareTo(Person)}.
     *
     * @param key ключ элемента
     * @param newPerson новый объект
     * @return true, если замена выполнена, иначе false
     * @throws IllegalArgumentException если key или newPerson равны null,
     *                                  либо элемент по ключу отсутствует
     */
    public boolean replaceIfLower(Long key, Person newPerson) {
        if (key == null || newPerson == null) {
            throw new IllegalArgumentException("key || newPerson не может быть null");
        }
        Person oldPerson = map.get(key);
        if (oldPerson == null) {
            throw new IllegalArgumentException("oldPerson не может быть null");
        }
        if (newPerson.compareTo(oldPerson) < 0) {
            map.put(key, newPerson);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Возвращает объект с максимальной датой создания.
     *
     * @return объект Person с максимальным creationDate
     * @throws IllegalArgumentException если коллекция пуста
     */
    public Person maxByCreationDate() {
        if (map.isEmpty()) {
            throw new IllegalArgumentException("Коллекция пуста");
        }
        Person first = map.values().iterator().next();
        Date maxCrDate = first.getCreationDate();
        Person maxPerson = first;
        for (Map.Entry<Long, Person> entry : map.entrySet()) {
            Person value = entry.getValue();
            Date creationDate = value.getCreationDate();
            if (creationDate.compareTo(maxCrDate) > 0) {
                maxCrDate = creationDate;
                maxPerson = value;
            }
        }
        return maxPerson;
    }
    /**
     * Считает количество элементов, location которых больше заданного.
     *
     * Сравнение выполняется по сумме координат location.
     *
     * @param location объект Location для сравнения
     * @return количество подходящих элементов
     * @throws IllegalArgumentException если location равен null
     */
    public long countGreaterThanLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location не может быть null");
        }
        long counter = 0;
        for (Person value : map.values()) {
            if (value.getLocation().getSum() >  location.getSum()) {
                counter++;
            }
        }
        return counter;
    }
    /**
     * Возвращает список элементов, у которых birthday совпадает с заданной датой.
     *
     * @param birthday дата рождения
     * @return список найденных объектов Person
     * @throws IllegalArgumentException если birthday равен null
     */
    public List<Person> filterByBirthday(LocalDate birthday) {
        if (birthday == null) {
            throw new IllegalArgumentException("Birthday date не может быть null");
        }
        List<Person> persons = new ArrayList<>();
        for (Person person : map.values()) {
            if (person.getBirthday().equals(birthday)) {
                persons.add(person);
            }
        }
        return persons;
    }
    /**
     * Возвращает внутреннюю коллекцию.
     *
     * @return HashMap с элементами коллекции
     */
    public HashMap<Long, Person> getMap() {
        return map;
    }
}
