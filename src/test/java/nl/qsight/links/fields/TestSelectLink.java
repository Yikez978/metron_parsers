package nl.qsight.links.fields;

import nl.qsight.common.Constants;
import nl.qsight.links.fields.SelectLink;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSelectLink {

    private SelectLink link;

    @Before
    public void setUp() {
        this.link = new SelectLink();
    }

    @After
    public void tearDown() {
        this.link = null;
    }

    @Test
    public void testGetSetField() {
        String template = "test";
        this.link.setField(template);
        assertEquals(template, this.link.getField());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSelectLink() {
        JSONObject input = new JSONObject();
        input.put("var1", "test1");
        input.put("var2", "test2");

        this.link.setField("var1");
        JSONObject result = this.link.parse(input);
        assertEquals(3, result.size());
        assertTrue(result.containsKey("var1"));
        assertTrue(result.containsKey("var2"));
        assertTrue(result.containsKey(Constants.INPUT_MARKER));
        assertEquals("test1", result.get("var1"));
        assertEquals("test2", result.get("var2"));
        assertEquals("test1", result.get(Constants.INPUT_MARKER));
    }

    @Test(expected = IllegalStateException.class)
    public void testNoField() {
        JSONObject input = new JSONObject();
        this.link.parse(input);
    }

}
