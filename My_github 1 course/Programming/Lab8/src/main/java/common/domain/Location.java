package common.domain;

import java.io.Serializable;

/**
 * Класс, представляющий координаты местоположения.
 *
 * Содержит три значения:
 * x (float), y (Integer), z (Double).
 * Поля y и z не могут быть null.
 *
 * Используется как часть объекта {@link Person}.
 *
 * @author Эльдар
 * @version 1.0
 */
public class Location implements Serializable {
    /** Координата X */
    private float x;
    /** Координата Y (не может быть null) */
    private Integer y;
    /** Координата Z (не может быть null) */
    private Double z;
    /**
     * Создаёт объект Location.
     *
     * @param x координата X
     * @param y координата Y
     * @param z координата Z
     * @throws IllegalArgumentException если y или z равны null
     */
    public Location(float x, Integer y, Double z) {
        if (y == null) {
            throw new IllegalArgumentException("y не может быть null");
        }
        if (z == null) {
            throw new IllegalArgumentException("z не может быть null");
        }
        this.x = x;
        this.y = y;
        this.z = z;
    }
    /**
     * Возвращает значение координаты X.
     *
     * @return x
     */
    public float getX() {
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
     * Возвращает значение координаты Z.
     *
     * @return z
     */
    public Double getZ() {
        return z;
    }
    /**
     * Возвращает строковое представление объекта.
     *
     * @return строка в формате "{x, y, z}"
     */
    public String toString() {
        return "{" + x + ", " + y + ", " + z + "}";
    }
    /**
     * Возвращает сумму координат.
     *
     * @return сумма x + y + z
     */
    public double getSum() {
        return (double) x + y + z;
    }
}
