package nl.qsight.links.fields;

import nl.qsight.chainlink.ChainLink;
import nl.qsight.utils.StringUtils;
import org.json.simple.JSONObject;

/**
 * A link for normalizing field names.
 */
public class TrimValueLink extends ChainLink {

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
            Object valueObject = data.get(keyObject);
            if (valueObject instanceof String) {
                String value = (String) valueObject;
                String newValue = value.trim();
                result.put(keyObject, newValue);
            }
            else {
                result.put(keyObject, valueObject);
            }
        }
        return result;
    }

}
