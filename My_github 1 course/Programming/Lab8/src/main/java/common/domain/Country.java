package common.domain;

import java.io.Serializable;

/**
 * Перечисление стран.
 *
 * Используется как значение поля nationality в классе {@link Person}.
 *
 * Доступные значения:
 * RUSSIA, USA, VATICAN, THAILAND.
 *
 * @author Эльдар
 * @version 1.0
 */
public enum Country implements Serializable {
    RUSSIA,
    USA,
    VATICAN,
    THAILAND;
}
