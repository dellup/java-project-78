package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    protected boolean required = false;
    public abstract BaseSchema<T> required();
    public boolean isValid(T value) {
        if (required && (value == null || value.toString().isEmpty())) {
            return false;
        } else if (!(required) && (value == null || value.toString().isEmpty())) {
            return true;
        }
        return true;
    }
}
