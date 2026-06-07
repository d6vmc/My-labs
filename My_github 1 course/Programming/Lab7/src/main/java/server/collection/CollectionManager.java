package server.collection;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;

import common.domain.Location;
import server.services.PersonFactory;

import common.domain.Person;
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
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
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
        lock.writeLock().lock();
        try {
            if (map.containsKey(key)) {
                throw new IllegalArgumentException("Ключ уже занят");
            }
            map.put(key, person);
        } finally {
            lock.writeLock().unlock();
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
        lock.writeLock().lock();
        try {
            Person removed = map.remove(key);
            if (removed == null) {
                throw new IllegalArgumentException("Нет такого ключа");
            }
        } finally {
            lock.writeLock().unlock();
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
        lock.writeLock().lock();
        try {
            map.clear();
        } finally {
            lock.writeLock().unlock();
        }
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
        if (person == null) {
            throw new IllegalArgumentException("person не может быть null");
        }

        lock.writeLock().lock();
        try {
            if (!map.containsKey(id)) {
                throw new IllegalArgumentException("Не найден нужный id");
            }

            map.put(id, person);
        } finally {
            lock.writeLock().unlock();
        }
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
        lock.writeLock().lock();
        try {
            List<Long> keys = new ArrayList<>();

            for (Map.Entry<Long, Person> entry : map.entrySet()) {
                if (entry.getValue().compareTo(person) > 0) {
                    keys.add(entry.getKey());
                }
            }

            for (Long key : keys) {
                map.remove(key);
            }

            return keys.size();
        } finally {
            lock.writeLock().unlock();
        }
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
        lock.writeLock().lock();
        try {
            List<Long> keysToRemove = map.keySet().stream()
                    .filter(k -> k > key)
                    .toList();
            for (Long k : keysToRemove) {
                map.remove(k);
            }
            return keysToRemove.size();
        } finally {
            lock.writeLock().unlock();
        }
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

        lock.writeLock().lock();
        try {
            Person oldPerson = map.get(key);

            if (oldPerson == null) {
                throw new IllegalArgumentException("oldPerson не может быть null");
            }

            if (newPerson.compareTo(oldPerson) < 0) {
                map.put(key, newPerson);
                return true;
            }

            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }
    /**
     * Возвращает объект с максимальной датой создания.
     *
     * @return объект Person с максимальным creationDate
     * @throws IllegalArgumentException если коллекция пуста
     */
    public Person maxByCreationDate() {
    return map.values().stream()
            .max(Comparator.comparing(Person::getCreationDate))
            .orElseThrow(() -> new IllegalArgumentException("Коллекция пуста"));
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

        return map.values().stream()
                .filter(person -> person.getLocation().getSum() > location.getSum())
                .count();
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
