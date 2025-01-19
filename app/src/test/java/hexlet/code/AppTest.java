package hexlet.code;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AppTest {
    @Test
    public void defaultTest() {
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
    public void myTest() {
        var v = new Validator();
        var schema = v.string().minLength(3).contains("aba")
                .minLength(8).contains("ababa");
        assertTrue(schema.isValid("ababa!!!!s"));
        assertFalse(schema.isValid("aba!Hj"));
    }
}
