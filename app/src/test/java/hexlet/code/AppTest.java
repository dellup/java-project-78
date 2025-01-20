package hexlet.code;

import org.junit.Test;

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
