package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<?, ?>> {
    private int size = -1;

    public void sizeof(int sizeMap) {
        size = sizeMap;
    }
    public boolean isValid(Map<?, ?> map) {
        if (!super.isValid(map)) {
            return false;
        } else {
            if (map == null) {
                return true;
            }
        }
        if (size == -1) {
            return true;
        }
        return map.size() == size;
    }
}
