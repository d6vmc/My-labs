package core.services;
/**
 * Реализация интерфейса {@link IdGenerator} для генерации
 * последовательных идентификаторов типа long.
 *
 * Каждый новый id увеличивается на 1 относительно предыдущего.
 * Используется для обеспечения уникальности идентификаторов объектов {@link core.domain.Person}.
 *
 * @author Эльдар
 * @version 1.0
 */
public class PersonIdGenerator implements IdGenerator {
    /** Текущее максимальное значение идентификатора */
    private long currentMaxId;
    /**
     * Создаёт генератор с заданным начальным значением id.
     *
     * @param currentMaxId начальное значение (обычно максимальный id из загруженной коллекции)
     */
    public PersonIdGenerator(long currentMaxId) {
        this.currentMaxId = currentMaxId;
    }
    /**
     * Генерирует следующий уникальный идентификатор.
     *
     * @return новый уникальный id
     */
    @Override
    public long nextId() {
        currentMaxId++;
        return currentMaxId;
    }
    /**
     * Устанавливает текущее максимальное значение id.
     *
     * Используется при загрузке данных, чтобы новые id не пересекались
     * с уже существующими.
     *
     * @param currentMaxId максимальный id
     * @throws IllegalArgumentException если значение меньше 0
     */
    @Override
    public void setCurrentMaxId(long currentMaxId) {
        if (currentMaxId < 0) {
            throw new IllegalArgumentException("currentMaxId must be >= 0");
        }
        this.currentMaxId = currentMaxId;
    }
}
