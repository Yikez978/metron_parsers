package nl.qsight.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestJSONUtils {

    @Test
    public void testJSONStringToMap() throws IOException {
        String json = "{\"a\": \"b\"}";
        Map<String, Object> result = JSONUtils.JSONToMap(json);
        assertTrue(result.containsKey("a"));
        assertEquals(1, result.size());
        assertTrue(result.get("a") instanceof String);
        assertEquals("b", result.get("a"));
    }

    @Test
    public void testJSONObjectToMap() throws ParseException, IOException {
        String json = "{\"a\": \"b\"}";
        JSONObject jsonObject = JSONUtils.stringToJSON(json);
        Map<String, Object> result = JSONUtils.JSONToMap(jsonObject);
        assertTrue(result.containsKey("a"));
        assertEquals(1, result.size());
        assertTrue(result.get("a") instanceof String);
        assertEquals("b", result.get("a"));
    }

    @Test
    public void testStringToJSON() throws ParseException {
        String json = "{\"a\": \"b\"}";
        JSONObject jsonObject = JSONUtils.stringToJSON(json);
        assertTrue(jsonObject.containsKey("a"));
        assertTrue(jsonObject.get("a") instanceof String);
        assertEquals(jsonObject.get("a"), "b");
    }

    @Test
    public void testMapToJSON() throws IOException {
        String json = "{\"a\": \"b\"}";
        Map<String, Object> map = JSONUtils.JSONToMap(json);
        JSONObject jsonObject = JSONUtils.mapToJSON(map);
        assertTrue(jsonObject.containsKey("a"));
        assertTrue(jsonObject.get("a") instanceof String);
        assertEquals(jsonObject.get("a"), "b");
    }

}
