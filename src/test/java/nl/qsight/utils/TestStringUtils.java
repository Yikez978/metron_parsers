package nl.qsight.utils;

import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

// @todo add tests

public class TestStringUtils {

    @Test
    public void testParseKeyValuePairs() {
        Map<String, String> pairs = StringUtils.parseKeyValuePairs("hello: world, test: message", ": ", ",", "a-z");

        assertEquals(2, pairs.size());
        assertTrue(pairs.containsKey("test"));
        assertTrue(pairs.containsKey("hello"));
        assertEquals("world", pairs.get("hello"));
        assertEquals("message", pairs.get("test"));
    }

}
