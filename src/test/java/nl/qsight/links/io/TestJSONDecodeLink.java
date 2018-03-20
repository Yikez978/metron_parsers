package nl.qsight.links.io;

import nl.qsight.common.Constants;
import nl.qsight.links.fields.WhitelistLink;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestJSONDecodeLink {

    private JSONDecoderLink link;

    @Before
    public void setUp() {
        this.link = new JSONDecoderLink();
    }

    @After
    public void tearDown() {
        this.link = null;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testJSONDecode() {
        String input = "{\"a\": {\"b\": \"c\"}, \"d\": \"e\"}";

        Object outputObject = this.link.parseInputField(input);
        assertTrue(outputObject instanceof JSONObject);
        JSONObject output = (JSONObject) outputObject;

        assertEquals(2, output.size());
        assertTrue(output.containsKey("a.b"));
        assertTrue(output.containsKey("d"));
        assertEquals("c", output.get("a.b"));
        assertEquals("e", output.get("d"));
    }

    @Test(expected = IllegalStateException.class)
    public void testParseError() {
        String input = "non-json";
        this.link.parseInputField(input);
    }

}
