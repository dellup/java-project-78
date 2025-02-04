package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Number> {

    private boolean positive = false;
    private int minBorder = Integer.MIN_VALUE;
    private int maxBorder = Integer.MAX_VALUE;

    public NumberSchema positive() {
        positive = true;
        return this;
    }
    public NumberSchema required() {
        required = true;
        return this;
    }
    public NumberSchema range(int minimBorder, int maximBorder) {
        minBorder = minimBorder;
        maxBorder = maximBorder;
        return this;
    }
    public boolean isValid(Number num) {
        if (!super.isValid(num)) {
            return false;
        } else {
            if (num == null) {
                return true;
            }
        }
        if ((positive) && num.intValue() <= 0) {
            return false;
        }
        return (num.intValue() >= minBorder) && (num.intValue() <= maxBorder);
    }
}
