package nl.qsight.links.fields;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestNormalizeFieldLink {

    private NormalizeFieldLink link;

    @Before
    public void setUp() {
        this.link = new NormalizeFieldLink();
    }

    @After
    public void tearDown() {
        this.link = null;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizeKeys() {
        JSONObject input = new JSONObject();
        input.put("Ĉool.keyCamelIPCase23TestI", "test1");

        JSONObject result = this.link.parse(input);
        assertTrue(result.containsKey("cool_keycamelipcase23testi"));
        assertEquals("test1", result.get("cool_keycamelipcase23testi"));
        assertTrue(result.size() == 1);
    }

    @Test(expected = IllegalStateException.class)
    @SuppressWarnings("unchecked")
    public void testDuplicateKeys() {
        // Test whether an exception is thrown when two fields are normalized to the same key which can lead to
        // dangerous situations
        JSONObject input = new JSONObject();
        input.put("Test", "test1");
        input.put("test", "test1");

        this.link.parse(input);
    }

}
