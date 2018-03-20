package nl.qsight.chainlink;

import nl.qsight.chainparser.ChainParser;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.Map;

/**
 * A ChainLink is an atomic unit for parsing which has a JSONObject as input and produces a JSONObject as output.
 */
public abstract class ChainLink implements Serializable {

    // The next ChainLink in the DAG (when null, there is no next ChainLink)
    private ChainLink next = null;

    /**
     * Method for loading the configuration.
     *
     * @param config The configuration to use.
     */
    public void configure(Map<String, Object> config) {}

    /**
     * This method parses the given input JSONObject and produces an updated JSONObject.
     *
     * @param input The JSONObject used as input.
     * @return The updated JSONObject.
     */
    public abstract JSONObject parse(JSONObject input);

    /**
     * Get the next link of the ChainLink DAG.
     *
     * @return The next ChainLink.
     * @see ChainParser
     */
    public ChainLink getNextLink() {
        return next;
    }

    /**
     * Set the next ChainLink in the ChainLink DAG.
     *
     * @param next The next ChainLink.
     * @see ChainParser
     */
    public void setNextLink(ChainLink next) {
        assert next != this : "The next link must no equal the current link.";
        this.next = next;
    }

    /**
     * Determines whether the DAG has a next ChainLink.
     *
     * @return True when there is a next ChainLink, false otherwise.
     */
    public boolean hasNextLink() {
        return this.next != null;
    }

}
