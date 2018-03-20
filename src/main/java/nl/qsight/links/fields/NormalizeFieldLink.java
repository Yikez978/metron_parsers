package nl.qsight.links.fields;

import nl.qsight.chainlink.ChainLink;
import nl.qsight.utils.StringUtils;
import org.json.simple.JSONObject;

/**
 * A link for normalizing field names.
 */
public class NormalizeFieldLink extends ChainLink {

    /**
     *
     *
     * @param data Input data.
     * @return Data with only whitelisted fields.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONObject parse(JSONObject data) {
        JSONObject result = new JSONObject();

        for (Object keyObject : data.keySet()) {
            if (keyObject instanceof String) {
                String key = (String) keyObject;
                String newKey = StringUtils.normalize(key);
                // Remove trailing and beginning underscore
                if (newKey == null || newKey.length() == 0) {
                    newKey = null;
                }
                if (result.containsKey(newKey)) throw new IllegalStateException("Duplicate normalized keys.");
                result.put(newKey, data.get(keyObject));
            }
        }

        return result;
    }

}
