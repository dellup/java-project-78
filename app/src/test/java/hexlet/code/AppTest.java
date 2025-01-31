package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest<K, V> {
    Validator v;
    StringSchema schemaStr;
    NumberSchema schemaNum;
    MapSchema<K, V> schemaMap;
    @BeforeEach
    public void beforeEach() {
        v = new Validator();
        schemaStr = v.string();
        schemaNum = v.number();
        schemaMap = v.map();
    }
    @ParameterizedTest
    @NullAndEmptySource
    public void testStrValidByDefault(String input) {
        assertTrue(schemaStr.isValid(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hexlet", "what does the fox say"})
    public void testStrRequiredValid(String input) {
        schemaStr.required();
        assertTrue(schemaStr.isValid(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testStrRequiredInvalid(String input) {
        schemaStr.required();
        assertFalse(schemaStr.isValid(input));
    }

    @ParameterizedTest
    @CsvSource({
        "what, what does the fox say, true",
        "wh, what does the fox say, true",
        "whatthe, what does the fox say, false"
    })
    public void testStrContains(String subStr, String input, boolean expected) {
        schemaStr.required();
        schemaStr.contains(subStr);
        assertEquals(schemaStr.isValid(input), expected);
    }

    @ParameterizedTest
    @CsvSource({
        "10, Hexlet, false",
        "4, Hexlet, true"
    })
    public void testStrMinLength(int minLength, String input, boolean expected) {
        schemaStr.required();
        schemaStr.minLength(minLength);
        assertEquals(schemaStr.isValid(input), expected);
    }

    @ParameterizedTest
    @NullSource
    public void testNumValidByDefault(Number input) {
        assertTrue(schemaNum.isValid(input));
        assertTrue(schemaNum.positive().isValid(input));
    }
    @ParameterizedTest
    @NullSource
    public void testNumValidRequired(Number input) {
        schemaNum.required();
        assertFalse(schemaNum.isValid(input));
    }
    @ParameterizedTest
    @ValueSource(ints = {5, 10})
    public void testValidNumbersByDefault(int input) {
        assertTrue(schemaNum.isValid(input));
    }
    @ParameterizedTest
    @CsvSource({
        "5, true",
        "10, true",
        "-10, false",
        "0, false"
    })
    public void testRequiredNumbers(int input, boolean expected) {
        schemaNum.required();
        schemaNum.positive();
        assertEquals(schemaNum.isValid(input), expected);
    }
    @ParameterizedTest
    @CsvSource({
        "5, true",
        "10, true",
        "4, false",
        "11, false"
    })
    public void testNumberRange(int num, boolean expected) {
        schemaNum.required();
        schemaNum.range(5, 10);
        assertEquals(schemaNum.isValid(num), expected);
    }
    @ParameterizedTest
    @NullSource
    public void testMapValidByDefault(Map<K, V> input) {
        assertTrue(schemaMap.isValid(input));
    }
    @ParameterizedTest
    @NullSource
    public void testMapValidRequired(Map<K, V> input) {
        schemaMap.required();
        assertFalse(schemaMap.isValid(input));
    }
    @ParameterizedTest
    @MethodSource("provideMapsForValidation")
    public void testMapOfValues(Map<K, V> map, boolean expected) {
        schemaMap.required();
        assertEquals(schemaMap.isValid(map), expected);
    }
    @ParameterizedTest
    @MethodSource("provideMapsForSizeValidation")
    public void testMapSizeValidation(Map<K, V> map, boolean expected) {
        schemaMap.required();
        schemaMap.sizeof(2);
        assertEquals(schemaMap.isValid(map), expected);
    }
    @ParameterizedTest
    @MethodSource("provideMapsForShapeValidation")
    public void testMapShapeValidation(Map<K, V> map, boolean expected) {
        var schema = v.<String, String>map();

        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required());
        schemas.put("lastName", ((StringSchema) v.string().required()).minLength(2));
        schema.shape(schemas);
        assertEquals(schema.isValid((Map<String, String>) map), expected);
    }
    private static Stream<Arguments> provideMapsForValidation() {
        Map<String, String> validMap = new HashMap<>();
        validMap.put("key1", "value1");

        return Stream.of(
                Arguments.of(new HashMap<>(), true),
                Arguments.of(validMap, true),
                Arguments.of(null, false)
        );
    }
    private static Stream<Arguments> provideMapsForSizeValidation() {
        Map<String, String> smallMap = new HashMap<>();
        smallMap.put("key1", "value1");

        Map<String, String> validMap = new HashMap<>(smallMap);
        validMap.put("key2", "value2");

        return Stream.of(
                Arguments.of(smallMap, false),
                Arguments.of(validMap, true)
        );
    }
    private static Stream<Arguments> provideMapsForShapeValidation() {
        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");

        return Stream.of(
                Arguments.of(human1, true),
                Arguments.of(human2, false),
                Arguments.of(human3, false)
        );
    }

}
