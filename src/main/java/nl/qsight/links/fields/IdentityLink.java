package nl.qsight.links.fields;

import nl.qsight.chainlink.ChainLink;
import org.json.simple.JSONObject;

/**
 * The IdentityLink returns a copy of the input as output.
 */
public class IdentityLink extends ChainLink {

    /**
     * Parse a JSONObject using the IdentityLink class.
     *
     * @param input The JSONObject used as input.
     * @return The same JSONObject.
     */
    @Override
    public JSONObject parse(JSONObject input) {
        return input;
    }

}
