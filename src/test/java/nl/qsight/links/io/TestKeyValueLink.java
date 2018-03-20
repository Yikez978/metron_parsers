package nl.qsight.links.io;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestKeyValueLink {

    private KeyValueLink link;

    @Before
    public void setUp() {
        this.link = new KeyValueLink();
    }

    @After
    public void tearDown() {
        this.link = null;
    }

    @Test
    public void testKeyValueLink() {
        String input = "hello=world|message=test";

        this.link.setKeyValueDelimiter("=");
        this.link.setPairDelimiter("|");
        this.link.setValidKeyChars("a-z");

        Object outputObject = this.link.parseInputField(input);
        assertTrue(outputObject instanceof JSONObject);
        JSONObject output = (JSONObject) outputObject;

        assertEquals(2, output.size());
        assertTrue(output.containsKey("hello"));
        assertTrue(output.containsKey("message"));
        assertEquals("world", output.get("hello"));
        assertEquals("test", output.get("message"));
    }

    @Test(expected = IllegalStateException.class)
    public void testNoKeyValueDelimiter() {
        String input = "hello=world|message=test";
        this.link.setPairDelimiter("|");
        this.link.setValidKeyChars("a-z");
        this.link.parseInputField(input);
    }

    @Test(expected = IllegalStateException.class)
    public void testNoPairDelimiter() {
        String input = "hello=world|message=test";
        this.link.setKeyValueDelimiter("=");
        this.link.setValidKeyChars("a-z");
        this.link.parseInputField(input);
    }

    @Test(expected = IllegalStateException.class)
    public void testNoValidCharacters() {
        String input = "hello=world|message=test";
        this.link.setKeyValueDelimiter("=");
        this.link.setPairDelimiter("|");
        this.link.parseInputField(input);
    }

}
