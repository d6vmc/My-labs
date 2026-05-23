package core.validation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import core.domain.Coordinates;
import core.domain.Location;
import core.domain.Person;
import java.util.Date;
import core.domain.Country;

public class Validator {
    public void ValidateCoordinates(Integer x, Integer y) {
        if (x == null) {
           throw new IllegalArgumentException("x не может быть null");
       }
       if (y == null) {
           throw new IllegalArgumentException("y не может быть null");
       }
       if (y > 780) {
           throw new IllegalArgumentException("y не может быть больше 780");
       }
    }

    public void ValidateLocation(float x, Integer y, Double z) {
        if (y == null) {
            throw new IllegalArgumentException("y не может быть null");
        }
        if (z == null) {
            throw new IllegalArgumentException("z не может быть null");
        }
    }

    public void ValidatePerson(long id, String name, Coordinates coordinates, Date creationDate, double height, LocalDate birthday, String passportID, Country nationality, Location location) {
        if (id <= 0){
            throw new IllegalArgumentException("id должен быть больше 0");
        }
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("name не может быть пустым или null");
        }
        if (coordinates == null){
            throw new IllegalArgumentException("coordinates не может быть null");
        }
        ValidateCoordinates(coordinates.getX(), coordinates.getY());
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
        ValidateLocation(location.getX(), location.getY(), location.getZ());
    }

    public void ValidateMap(HashMap<Long, Person> map) {
        Set<Long> ids = new HashSet<>();
        for (Map.Entry<Long, Person> entry : map.entrySet()) {
            Long key = entry.getKey();
            Person person = entry.getValue();

            if (key == null) {
                throw new IllegalArgumentException("Ключ коллекции не может быть null");
            }
            if (person == null) {
                throw new IllegalArgumentException("Файл содержит null вместо объекта Person");
            }
            long id = person.getId();
            if (!ids.add(id)) {
                throw new IllegalArgumentException("Файл содержит неуникальный id: " + id);
            }
            ValidatePerson(person.getId(), person.getName(), person.getCoordinates(), person.getCreationDate(), person.getHeight(), person.getBirthday(), person.getPassportID(), person.getNationality(), person.getLocation());
        }
    }
}
