package nl.qsight.links.fields;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTrimValueLink {

    private TrimValueLink link;

    @Before
    public void setUp() {
        this.link = new TrimValueLink();
    }

    @After
    public void tearDown() {
        this.link = null;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testTrimValueFields() {
        JSONObject input = new JSONObject();
        input.put("whitespace test", "    test 1  ");

        JSONObject result = this.link.parse(input);
        assertTrue(result.containsKey("whitespace test"));
        assertEquals("test 1", result.get("whitespace test"));
        assertTrue(result.size() == 1);
    }
}
