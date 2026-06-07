package client;

import common.PersonData;
import common.domain.Coordinates;
import common.domain.Country;
import common.domain.Location;
import common.domain.Person;
import common.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
/**
 * Класс для поэтапного чтения объекта Person из пользовательского ввода.
 *
 * @author Эльдар
 * @version 1.0
 */
public class PersonReader {
    private final InputManager input;
    private final InputReader reader;
    private final Validator validator;

    public PersonReader(InputManager input, InputReader reader, Validator validator) {
        this.input = input;
        this.reader = reader;
        this.validator = validator;
    }
    /**
     * Считывает поля объекта Person и создаёт новый экземпляр через PersonFactory.
     *
     * @return новый объект Person
     */
    public PersonData readPersonData() {
        String name;
        while (true) {
            name = input.readLine("Введите имя: ");
            name = name.trim();
            if (name.isEmpty()) {
                System.out.print("Ошибка, Введите имя заново: ");
            } else{
                break;
            }
        }
        Coordinates coordinates = reader.readCoordinates(input);
        double height;
        while (true) {
            String h = input.readLine("Введите height (double): ");
            try {
                height = Double.parseDouble(h);
            } catch (NumberFormatException e) {
                System.out.print("Ошибка, введите рост заново: ");
                continue;
            }
            if (height <= 0) {
                System.out.print("Ошибка, рост должен быть больше 0 ");
            } else  {
                break;
            }
        }

        LocalDate birthday;
        while (true) {
            String b = input.readLine("birthday(YYYY-MM-DD): ");
            b = b.trim();
            if (b.isEmpty()) {
                System.out.print("Ошибка, день рождения не должен быть null ");
                continue;
            }
            try {
                birthday = LocalDate.parse(b);
                break;
            } catch (DateTimeParseException e) {
                System.out.print("Ошибка, введите день рождения заново: ");
            }
        }

        String passportID;
        while (true) {
            passportID = input.readLine("passportID: ");
            passportID = passportID.trim();
            if (passportID.isEmpty()) {
                passportID = null;
                break;
            }
            else if (passportID.length() > 45) {
                System.out.print("PassportId не может быть длиннее 45 символов");
                continue;
            }
            else {
                break;
            }
        }

        Country nationality;
        if (input.isInteractive()) {
                System.out.println(Arrays.toString(Country.values()));
            }
        while (true) {

            String s = input.readLine("nationality (пусто = null): ");
            s = s.trim();
            if (s.isEmpty()) {
                nationality = null;
                break;
            }
            try {
                nationality = Country.valueOf(s.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.print("Ошибка, введите одно из: RUSSIA, USA, VATICAN, THAILAND: ");
            }
        }

        Location location = reader.readLocation(input);
        PersonData data = new PersonData(name, coordinates, height, birthday, passportID, nationality, location);
        validator.ValidatePersonData(data);
        return data;
    }
}
