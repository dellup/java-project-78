package hexlet.code.schemas;

import java.util.Map;

public class MapSchema<K, V> extends BaseSchema<Map<K, V>> {
    private int size = -1;
    private Map<K, BaseSchema<V>> shapeMap;
    public MapSchema<K, V> required() {
        required = true;
        return this;
    }

    public void sizeof(int sizeMap) {
        size = sizeMap;
    }
    public boolean isValid(Map<K, V> map) {
        if (!super.isValid(map)) {
            return false;
        } else {
            if (map == null) {
                return true;
            }
        }
        if (shapeMap != null) {
            for (K key : map.keySet()) {
                BaseSchema<V> schema = shapeMap.get(key);
                if (!schema.isValid(map.get(key))) {
                    return false;
                }
            }
        }
        if (size == -1) {
            return true;
        }
        return map.size() == size;
    }
    public void shape(Map<K, BaseSchema<V>> map) {
        shapeMap = map;
    }
}
