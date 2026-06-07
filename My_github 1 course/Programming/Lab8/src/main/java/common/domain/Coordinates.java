package common.domain;

import java.io.Serializable;

/**
 * Класс, представляющий координаты объекта.
 *
 * Содержит два целочисленных значения:
 * x — не может быть null
 * y — не может быть null и не должен превышать 780
 *
 * @author Эльдар
 * @version 1.0
 */
public class Coordinates implements Serializable {
    /** Координата X (не может быть null) */
    private Integer x;
    /** Координата Y (не может быть null, максимум 780) */
    private Integer y;
    /**
     * Создаёт объект координат.
     *
     * @param x значение координаты X
     * @param y значение координаты Y
     * @throws IllegalArgumentException если x или y равны null,
     *                                  либо y > 780
     */
    public Coordinates(Integer x, Integer y) {
       if (x == null) {
           throw new IllegalArgumentException("x не может быть null");
       }
       if (y == null) {
           throw new IllegalArgumentException("y не может быть null");
       }
       if (y > 780) {
           throw new IllegalArgumentException("y не может быть больше 780");
       }
       this.x = x;
       this.y = y;
    }
    /**
     * Возвращает значение координаты X.
     *
     * @return x
     */
    public Integer getX() {
        return x;
    }
    /**
     * Возвращает значение координаты Y.
     *
     * @return y
     */
    public Integer getY() {
        return y;
    }
    /**
     * Возвращает строковое представление координат.
     *
     * @return строка в формате "{x, y}"
     */
    public String toString() {
        return "{" + x + ", " + y + "}";
    }
}
