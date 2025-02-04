package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    protected boolean required = false;
    public abstract BaseSchema<T> required();

    /**
     * Проверяет, является ли переданное значение корректным согласно правилам валидации.
     *
     * <p>При реализации этого метода в подклассах необходимо соблюдать следующие условия:
     * <ul>
     *   <li>Если {@code required} равно {@code true} и значение равно {@code null}
     *   или его строковое представление пустое,
     *       метод должен вернуть {@code false}.</li>
     *   <li>Если {@code required} равно {@code false} и значение равно {@code null} или пустой строке,
     *       метод должен вернуть {@code true}.</li>
     *   <li>Подклассы могут расширять проверку, добавляя дополнительные условия валидации.</li>
     * </ul>
     * </p>
     *
     * @param value значение для проверки
     * @return {@code true} если значение валидно, иначе {@code false}
     */
    public boolean isValid(T value) {
        if (required && (value == null || value.toString().isEmpty())) {
            return false;
        } else if (!(required) && (value == null || value.toString().isEmpty())) {
            return true;
        }
        return true;
    }
}
