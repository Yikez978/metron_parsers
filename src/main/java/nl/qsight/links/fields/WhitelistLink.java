package nl.qsight.links.fields;

import nl.qsight.chainlink.ChainLink;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * A link which whitelists keys.
 */
public class WhitelistLink extends ChainLink {

    private List<String> fields;

    public void configure(Map<String, Object> config) {
        this.fields = null;
        if (config.containsKey("fields")) {
            assert config.get("fields") instanceof List;
            this.setFields((List<String>) config.get("fields"));
        }
    }

    /**
     * Get the fields.
     *
     * @return The fields.
     */
    public List<String> getFields() {
        return fields;
    }

    /**
     * Set the fields.
     *
     * @param fields The fields which will be whitelisted.
     */
    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    /**
     * Whitelist all the specified fields and filter out fields not in the list.
     *
     * @param data Input data.
     * @return Data with only whitelisted fields.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONObject parse(JSONObject data) {
        if (this.getFields() == null) throw new IllegalStateException("The whitelisted fields should be specified.");
        JSONObject result = new JSONObject();

        for (Object keyObject : data.keySet()) {
            if (keyObject instanceof String) {
                String key = (String) keyObject;
                if (this.getFields().contains(key)) {
                    result.put(keyObject, data.get(keyObject));
                }
            }
        }

        return result;
    }

}
