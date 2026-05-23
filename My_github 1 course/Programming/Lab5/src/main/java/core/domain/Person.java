package core.domain;

import java.time.LocalDate;
/**
 * Класс, представляющий человека.
 *
 * Содержит основные характеристики объекта, включая уникальный идентификатор,
 * имя, координаты, дату создания, рост, дату рождения, паспортные данные,
 * национальность и местоположение.
 *
 * Объект является сравнимым по росту (height), а при равенстве — по id.
 *
 * Ограничения:
 * - id > 0 и уникален
 * - name не может быть null или пустым
 * - coordinates не может быть null
 * - creationDate не может быть null
 * - height > 0
 * - birthday не может быть null
 * - passportID длиной не более 45 символов (может быть null)
 * - location не может быть null
 *
 * @author Эльдар
 * @version 1.0
 */
public class Person implements  Comparable<Person> {
    /** Уникальный идентификатор (генерируется автоматически, > 0) */
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /** Имя (не может быть null или пустым) */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /** Координаты (не могут быть null) */
    private Coordinates coordinates; //Поле не может быть null
    /** Дата создания (генерируется автоматически, не может быть null) */
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /** Рост (> 0) */
    private double height; //Значение поля должно быть больше 0
    /** Дата рождения (не может быть null) */
    private LocalDate birthday; //Поле не может быть null
    /** Паспорт (не более 45 символов, может быть null) */
    private String passportID; //Длина строки не должна быть больше 45, Поле может быть null
    /** Национальность (может быть null) */
    private Country nationality; //Поле может быть null
    /** Местоположение (не может быть null) */
    private Location location; //Поле не может быть null
    /**
     * Создаёт объект Person.
     *
     * @param id уникальный идентификатор (> 0)
     * @param name имя (не null и не пустое)
     * @param coordinates координаты (не null)
     * @param creationDate дата создания (не null)
     * @param height рост (> 0)
     * @param birthday дата рождения (не null)
     * @param passportID паспорт (не более 45 символов, может быть null)
     * @param nationality национальность (может быть null)
     * @param location местоположение (не null)
     *
     * @throws IllegalArgumentException если нарушены ограничения полей
     */
    public Person(long id, String name, Coordinates coordinates, java.util.Date creationDate, double height, LocalDate birthday, String passportID, Country nationality, Location location) {
        if (id <= 0){
            throw new IllegalArgumentException("id должен быть больше 0");
        }
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("name не может быть пустым или null");
        }
        if (coordinates == null){
            throw new IllegalArgumentException("coordinates не может быть null");
        }
        if (creationDate == null){
            throw new IllegalArgumentException("creationdate не может быть null");
        }
        if (height <= 0){
            throw new IllegalArgumentException("height должен быть больше 0");
        }
        if (birthday == null){
            throw new IllegalArgumentException("birthday не может быть null");
        }
        if (passportID != null && passportID.length() > 45){
            throw new IllegalArgumentException("passportID не может быть больше 45 символов");
        }
        if (location == null){
            throw new IllegalArgumentException("location не может быть null");
        }

        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.height = height;
        this.birthday = birthday;
        this.passportID = passportID;
        this.nationality = nationality;
        this.location = location;
    }
    /** @return id */
    public long getId() {
        return id;
    }
    /** @return имя */
    public String getName() {
        return name;
    }
    /** @return координаты */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    /** @return дата создания */
    public java.util.Date getCreationDate() {
        return creationDate;
    }
    /** @return рост */
    public double getHeight() {
        return height;
    }
    /** @return дата рождения */
    public LocalDate getBirthday() {
        return birthday;
    }
    /** @return паспорт */
    public String getPassportID() {
        return passportID;
    }
    /** @return национальность */
    public Country getNationality() {
        return nationality;
    }
    /** @return местоположение */
    public Location getLocation() {
        return location;
    }
    /**
     * Возвращает строковое представление объекта.
     *
     * @return строка с полями объекта
     */
    @Override
    public String toString() {
        return "Person{" +
        "id=" + id +
        ", name='" + name + "'" +
        ", coordinates=" + coordinates +
        ", creationDate=" + creationDate +
        ", height=" + height +
        ", birthday=" + birthday +
        ", passportID=" + passportID +
        ", nationality=" + nationality +
        ", location=" + location +
        "}";
    }
    /**
     * Сравнивает объекты Person.
     *
     * Сначала по росту (height), при равенстве — по id.
     *
     * @param other другой объект Person
     * @return отрицательное число, 0 или положительное число
     */
    @Override
    public int compareTo(Person other) {
        int result = Double.compare(this.height, other.height);
        if (result != 0) {
            return result;
        }else {
            return Long.compare(this.id, other.id);
        }
    }
}
