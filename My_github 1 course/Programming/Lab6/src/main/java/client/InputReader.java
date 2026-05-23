package client;

import common.domain.Coordinates;
import common.domain.Location;

/**
 * Класс для чтения и валидации составных объектов из пользовательского ввода.
 *
 * Использует {@link InputManager} как источник данных (консоль или скрипт) и преобразует строки во внутренние объекты модели.
 *
 * Отвечает за корректный парсинг числовых значений и повторный запрос ввода при ошибках.
 *
 * @author Эльдар
 * @version 1.0
 */
public class InputReader {
    /**
     * Считывает объект {@link Location} из ввода.
     * <p>
     * Последовательно запрашивает:
     * x (float), y (Integer), z (double).
     * При некорректном вводе повторяет запрос.
     *
     * @param input менеджер ввода
     * @return созданный объект Location
     */
    public Location readLocation(InputManager input) {
        float xf;
        Integer yf;
        double zf;
        while (true) {
            String x = input.readLine("Введите x(float): ");
            try {
                xf = Float.parseFloat(x);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Ошибка, Введите x(float) заново: ");
            }
        }
        while (true) {
            String y = input.readLine("Введите y(Integer): ");
            try {
                yf = Integer.parseInt(y);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Ошибка, Введите y(Integer) заново: ");
            }
        }
        while (true) {
            String z = input.readLine("Введите z(double): ");
            try {
                zf = Double.parseDouble(z);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Ошибка, Введите z(double) заново: ");
            }
        }
        return new Location(xf, yf, zf);
    }

    /**
     * Считывает объект {@link Coordinates} из ввода.
     * <p>
     * Запрашивает:
     * x (Integer), y (Integer).
     * При ошибке парсинга или некорректных значениях повторяет ввод.
     *
     * @param input менеджер ввода
     * @return созданный объект Coordinates
     */
    public Coordinates readCoordinates(InputManager input) {
        Integer x;
        Integer y;
        while (true) {
            String xi = input.readLine("Введите x(Integer): ");
            try {
                x = Integer.parseInt(xi);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Ошибка, Введите x(Integer) заново: ");
            }
        }
        while (true) {
            String yi = input.readLine("Введите y(Integer): ");
            try {
                y = Integer.parseInt(yi);
                if (y > 780) {
                    throw new IllegalArgumentException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Ошибка, Введите y(Integer) заново: ");
            } catch (IllegalArgumentException e) {
                System.out.print("Ошибка y не может превышать 780, Введите y(Integer) заново: ");
            }
        }
        return new Coordinates(x, y);
    }
}
