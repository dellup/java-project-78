package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    boolean required = false;

    public BaseSchema<T> required() {
        required = true;
        return this;
    }
    public boolean isValid(T value) {
        if (required && (value == null || value.toString().isEmpty())) {
            return false;
        } else if (!(required) && (value == null || value.toString().isEmpty())) {
            return true;
        }
        return true;
    }
}
