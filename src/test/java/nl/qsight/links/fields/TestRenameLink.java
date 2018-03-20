package nl.qsight.links.fields;

import nl.qsight.links.fields.RenameLink;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRenameLink {

    private RenameLink link;

    @Before
    public void setUp() {
        this.link = new RenameLink();
    }

    @After
    public void tearDown() {
        this.link = null;
    }

    @Test
    public void testGetSetRenames() {
        Map<String, String> renames = new HashMap<>();
        renames.put("from", "to");
        this.link.setRenames(renames);
        assertEquals(renames, this.link.getRenames());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRename() {
        JSONObject input = new JSONObject();
        input.put("variable1", "test1");
        input.put("variable2", "test2");
        input.put("variable3", "test3");

        Map<String, String> renames = new HashMap<>();
        renames.put("variable1", "variable3");
        renames.put("variable2", "variable4");
        this.link.setRenames(renames);

        JSONObject output = this.link.parse(input);

        assertTrue(output.containsKey("variable3"));
        assertTrue(output.containsKey("variable4"));
        assertEquals(2, output.size());
        assertEquals(output.get("variable3"), "test1");
        assertEquals(output.get("variable4"), "test2");
    }

}
