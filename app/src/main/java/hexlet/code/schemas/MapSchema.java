package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<?, ?>> {
    private int size = -1;
    private Map<?, ? extends BaseSchema<?>> shapeMap;

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
        for (var key : shapeMap.keySet()) {
            var schema = shapeMap.get(key);
            if (!schema.isValid(map.get(key))) {
                return false;
            }
        }
        return map.size() == size;
    }
    public void shape(Map<?, ? extends BaseSchema<?>> map) {
        shapeMap = map;
    }
}
