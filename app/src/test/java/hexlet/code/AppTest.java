package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AppTest {
    @Test
    public void defaultTestStr() {
        var v = new Validator();
        var schema = v.string();
        var schema1 = v.string();

        assertTrue(schema.isValid("")); // true
        assertTrue(schema.isValid(null)); // true

        schema.required();

        assertFalse(schema.isValid(null)); // false
        assertFalse(schema.isValid("")); // false
        assertTrue(schema.isValid("what does the fox say")); // true
        assertTrue(schema.isValid("hexlet")); // true
        assertTrue(schema.contains("wh").isValid("what does the fox say")); // true
        assertTrue(schema.contains("what").isValid("what does the fox say")); // true
        assertFalse(schema.contains("whatthe").isValid("what does the fox say")); // false
        assertFalse(schema.isValid("what does the fox say")); // false
        assertTrue(schema1.minLength(10).minLength(4).isValid("Hexlet")); // true
    }

    @Test
    public void defaultTestNum() {
        var v = new Validator();
        var schema = v.number();

        assertTrue(schema.isValid(5)); // true
        assertTrue(schema.isValid(null)); // true
        assertTrue(schema.positive().isValid(null)); // true

        schema.required();

        assertFalse(schema.isValid(null)); // false
        assertTrue(schema.isValid(10)); // true
        assertFalse(schema.isValid(-10)); // false
        assertFalse(schema.isValid(0)); // false

        schema.range(5, 10);

        assertTrue(schema.isValid(5)); // true
        assertTrue(schema.isValid(10)); // true
        assertFalse(schema.isValid(4)); // false
        assertFalse(schema.isValid(11)); // false
    }

    @Test
    public void defaultTestMap() {
        var v = new Validator();
        var schema = v.<String, String>map();

        assertTrue(schema.isValid(null)); // true

        schema.required();

        assertFalse(schema.isValid(null)); // false
        assertTrue(schema.isValid(new HashMap<>())); // true
        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        assertTrue(schema.isValid(data)); // true

        schema.sizeof(2);

        assertFalse(schema.isValid(data));  // false
        data.put("key2", "value2");
        assertTrue(schema.isValid(data)); // true
    }

    @Test
    public void defaultTestShape() {
        var v = new Validator();
        var schema = v.<String, String>map();

        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", ((StringSchema) v.string().required()).minLength(2));
        schema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(schema.isValid(human1)); // true

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(schema.isValid(human2)); // false

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(schema.isValid(human3)); // false
    }

    @Test
    public void myTestStr() {
        var v = new Validator();
        var schema = v.string().minLength(3).contains("aba")
                .minLength(8).contains("ababa");
        assertTrue(schema.isValid("ababa!!!!s"));
        assertFalse(schema.isValid("aba!Hj"));
    }
    @Test
    public void myTestNum() {
        var v = new Validator();
        var schema = v.number().positive().range(-10, 55)
                .range(-100, 66);
        assertFalse(schema.isValid(-80));
        assertTrue(schema.isValid(65));
    }

}
