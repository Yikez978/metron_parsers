package nl.qsight.links.fields;

import nl.qsight.links.fields.BlacklistLink;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBlacklistLink {

    private BlacklistLink link;

    @Before
    public void setUp() {
        this.link = new BlacklistLink();
    }

    @After
    public void tearDown() {
        this.link = null;
    }

    @Test
    public void testGetSetFields() {
        List<String> fields = new ArrayList<>();
        fields.add("field");
        this.link.setFields(fields);
        assertEquals(fields, this.link.getFields());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testBlacklistKeys() {
        JSONObject input = new JSONObject();
        input.put("variable1", "test1");
        input.put("variable2", "test2");
        input.put("variable3", "test3");

        List<String> fields = new ArrayList<>();
        fields.add("variable2");
        fields.add("variable3");
        fields.add("variable4");
        this.link.setFields(fields);

        JSONObject output = this.link.parse(input);

        assertTrue(output.containsKey("variable1"));
        assertEquals(1, output.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testNoFields() {
        JSONObject input = new JSONObject();
        this.link.parse(input);
    }

}
