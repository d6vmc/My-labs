package server.services;

import common.domain.Coordinates;
import common.domain.Country;
import common.domain.Location;
import common.domain.Person;
import common.validation.Validator;

import java.time.LocalDate;
/**
 * Класс-фабрика для создания и обновления объектов {@link Person}.
 *
 * Использует {@link IdGenerator} для генерации уникальных идентификаторов.
 * Отделяет логику создания объектов от остальной части программы.
 *
 * @author Эльдар
 * @version 1.0
 */
public class PersonFactory {
    /** Генератор уникальных идентификаторов */
    private final Validator validator;
    /**
     * Создаёт фабрику с заданным генератором id.
     *
     * @param generator генератор уникальных идентификаторов
     */
    public PersonFactory(Validator validator) {
        this.validator = validator;
    }
    /**
     * Создаёт новый объект {@link Person}.
     *
     * Генерирует новый id и устанавливает текущую дату создания.
     *
     * @param name имя
     * @param coordinates координаты
     * @param height рост
     * @param birthday дата рождения
     * @param passportId паспорт
     * @param nationality национальность
     * @param location локация
     * @return новый объект Person
     */
    public Person create(long id, String name, Coordinates coordinates, java.util.Date creationDate, double height, LocalDate birthday, String passportId, Country nationality, Location location) {

        validator.ValidatePerson(id, name, coordinates, creationDate, height, birthday, passportId, nationality, location);
        Person person = new Person(id, name, coordinates, creationDate, height, birthday, passportId, nationality, location);
        return person;
    }
    /**
     * Обновляет объект {@link Person}, сохраняя его id и дату создания.
     *
     * Использует данные из newPerson, но переносит неизменяемые поля
     * (id и creationDate) из oldPerson.
     *
     * @param newPerson новый объект с обновлёнными данными
     * @param oldPerson старый объект
     * @return обновлённый объект Person
     * @throws IllegalArgumentException если один из объектов равен null
     */
    public Person updatePerson(Person newPerson, Person oldPerson) {
        if (newPerson == null || oldPerson == null) {
            throw new IllegalArgumentException("Person не может быть null");
        }
        String name = newPerson.getName();
        Coordinates coordinates = newPerson.getCoordinates();
        double height = newPerson.getHeight();
        LocalDate birthday = newPerson.getBirthday();
        String passportId = newPerson.getPassportID();
        Country nationality = newPerson.getNationality();
        Location location = newPerson.getLocation();
        java.util.Date  creationDate = oldPerson.getCreationDate();
        long id = oldPerson.getId();

        validator.ValidatePerson(id, name, coordinates, creationDate, height, birthday, passportId, nationality, location);
        Person person = new Person(id, name, coordinates, creationDate, height, birthday, passportId, nationality, location);
        return person;
    }
}
