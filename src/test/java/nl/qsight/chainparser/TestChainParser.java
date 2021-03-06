package nl.qsight.chainparser;

import nl.qsight.chainlink.ChainLink;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.qsight.common.Constants.ORIGINAL_STRING;
import static nl.qsight.common.Constants.TIMESTAMP;
import static org.junit.Assert.*;

public class TestChainParser {

    private ChainParser parser;

    private class FieldRemoveLink extends ChainLink {

        String fieldToRemove = "";

        @Override
        public JSONObject parse(JSONObject input) {
            input.remove(this.fieldToRemove);
            return input;
        }
    }

    @Before
    public void setUp() {
        this.parser = new ChainParser();
    }

    @After
    public void tearDown() {
        this.parser = null;
    }

    @Test
    public void testRequiredFields() {
        // Even without a specified link, the required fields should be set
        byte[] exampleMessage = "example_message".getBytes();
        List<JSONObject> resultSet = this.parser.parse(exampleMessage);

        // Should contain 1 message
        assertEquals(1, resultSet.size());

        // Therefore, it is possible to access the first element of the result
        JSONObject state = resultSet.get(0);

        // Check whether the state contains the required "original_string" message
        assertTrue("The parsed JSONObject should contain \"original_string\" field.",
                state.containsKey(ORIGINAL_STRING));
        assertTrue("The parsed JSONObject should contain \"timestamp\" field.",
                state.containsKey(TIMESTAMP));
    }

    @Test
    public void testGetSetInitialLink() {
        FieldRemoveLink link = new FieldRemoveLink();
        this.parser.setInitialLink(link);
        ChainLink initialLink = this.parser.getInitialLink();
        assertEquals("The getter of the initial link should equal the item set by its setter.", link, initialLink);
    }

    @Test(expected = IllegalStateException.class)
    public void testNoOriginalString() {
        // An exception should be thrown when a link removes the "original_string" field
        FieldRemoveLink link = new FieldRemoveLink();
        link.fieldToRemove = ORIGINAL_STRING;
        this.parser.setInitialLink(link);
        this.parser.parse("".getBytes());
    }

    @Test(expected = IllegalStateException.class)
    public void testNoTimestamp() {
        // An exception should be thrown when a link removes the "timestamp" field
        FieldRemoveLink link = new FieldRemoveLink();
        link.fieldToRemove = TIMESTAMP;
        this.parser.setInitialLink(link);
        this.parser.parse("".getBytes());
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalEncoding() {
        this.parser.setEncoding("unknown");
        this.parser.parse("".getBytes());
    }

    @Test
    public void testEncodingGetterAndSetter() {
        this.parser.setEncoding("encoding");
        assertEquals("encoding", this.parser.getEncoding());
    }

    @Test
    public void testConfiguration() {
        Map<String, String> link_1_config = new HashMap<>();
        Map<String, Object> link_2_config = new HashMap<>();
        link_1_config.put("class", "nl.qsight.links.fields.IdentityLink");
        link_2_config.put("class", "nl.qsight.links.io.SplitLink");
        Map<String, String> selector = new HashMap<>();
        selector.put("0", "first");
        link_2_config.put("delimiter", "|");
        link_2_config.put("selector", selector);
        Map<String, Object> parser_config = new HashMap<>();
        Map<String, Object> links_config = new HashMap<>();
        links_config.put("identity", link_1_config);
        links_config.put("split", link_2_config);
        parser_config.put("parsers", links_config);
        List<String> chain = new ArrayList<>();
        chain.add("identity");
        chain.add("split");
        parser_config.put("chain", chain);

        this.parser.configure(parser_config);
        List<JSONObject> output = this.parser.parse("1|2|3".getBytes());
        assertTrue(output.size() == 1);
        JSONObject message = output.get(0);
        assertTrue(message.containsKey("first"));
        assertEquals("1", message.get("first"));
    }

}
