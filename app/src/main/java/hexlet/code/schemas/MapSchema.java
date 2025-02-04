package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<?, ?>> {
    private int size = -1;
    private Map<?, BaseSchema<?>> shapeMap;

    public void sizeof(int sizeMap) {
        size = sizeMap;
    }
    public MapSchema required() {
        required = true;
        return this;
    }
    public boolean isValid(Map<?, ?> map) {
        if (!super.isValid(map)) {
            return false;
        } else {
            if (map == null) {
                return true;
            }
        }
        if (shapeMap != null) {
            for (Object key : map.keySet()) {
                BaseSchema<?> schema = shapeMap.get(key);
                if (!testSchema(schema, map.get(key))) {
                    return false;
                }
            }
        }
        if (size == -1) {
            return true;
        }
        return map.size() == size;
    }
    @SuppressWarnings("unchecked")
    public void shape(Map<?, ? extends BaseSchema<?>> map) {
        shapeMap = (Map<?, BaseSchema<?>>) map;
    }
    @SuppressWarnings("unchecked")
    private static <T> boolean testSchema(BaseSchema<T> schema, Object value) {
        return schema.isValid((T) value);
    }
}
