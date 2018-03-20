package nl.qsight.links.fields;

import nl.qsight.chainlink.ChainLink;
import org.json.simple.JSONObject;

import java.util.Map;

/**
 * A link which renames keys.
 */
public class RenameLink extends ChainLink {

    private Map<String, String> renames;

    /**
     * Get the renames.
     *
     * @return All the renames.
     */
    public Map<String, String> getRenames() {
        return renames;
    }

    /**
     * Set the renames.
     *
     * @param renames The key-value pairs where the keys are to original keys and the values are the field names after
     *                the renames.
     */
    public void setRenames(Map<String, String> renames) {
        this.renames = renames;
    }

    @SuppressWarnings("unchecked")
    public void configure(Map<String, Object> config) {
        if (config.containsKey("rename")) {
            assert config.get("rename") instanceof Map;
            this.setRenames((Map<String, String>) config.get("rename"));
        }
    }

    /**
     * Rename fields using the rename rules.
     *
     * @param data Input data.
     * @return Data with renamed keys.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONObject parse(JSONObject data) {
        if (this.getRenames() == null || this.getRenames().size() == 0)
            throw new IllegalStateException("No renames specified");

        JSONObject store = new JSONObject();
        for (String key : this.getRenames().keySet()) {
            if (data.containsKey(key)) {
                store.put(key, data.get(key));
            }
        }
        for (String key : this.getRenames().keySet()) {
            if (store.containsKey(key) && data.containsKey(key)) {
                String outputKey = getRenames().get(key);
                data.put(outputKey, store.get(key));
                data.remove(key);
            }
        }

        return data;
    }

}
