package core.services;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import core.domain.Coordinates;
import core.domain.Location;
import core.domain.Person;
import core.validation.Validator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
/**
 * Класс для загрузки и сохранения коллекции в JSON-файл.
 *
 * @author Эльдар
 * @version 1.0
 */
public class FileManager {
    private final String filename;
    private final Validator validator;
    private final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.toString());
            }
        })
        .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                return LocalDate.parse(json.getAsString());
            }
        })
        .create();
    public FileManager(String filename, Validator validator) {
        this.filename = filename;
        this.validator = validator;
    }
    /**
     * Загружает коллекцию из файла.
     *
     * @return загруженная коллекция или пустая HashMap, если файл пуст или недоступен
     */
    public HashMap<Long, Person> load() {
        File file = new File(filename);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }
            if (json.isEmpty()) {
                return new HashMap<>();
            }
            Type type = new TypeToken<HashMap<Long, Person>>() {}.getType();
            HashMap<Long, Person> map = gson.fromJson(json.toString(), type);

            if (map == null) {
                return new HashMap<>();
            }
            validateLoadedMap(map);
            return map;
        } catch (JsonParseException e) {
            System.out.println("Ошибка парсинга JSON: " + e.getMessage());
            return new HashMap<>();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка валидации данных: " + e.getMessage());
            return new HashMap<>();
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return new HashMap<>();
        }
    }
    /**
     * Сохраняет коллекцию в файл.
     *
     * @param map коллекция для сохранения
     */
    public void save(HashMap<Long, Person> map) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            String json = gson.toJson(map);
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }

    private void validateLoadedMap(HashMap<Long, Person> map) {
        validator.ValidateMap(map);

    }
}
