package nl.qsight.links.fields;

import nl.qsight.chainlink.ChainLink;
import nl.qsight.common.Constants;
import org.json.simple.JSONObject;

import java.util.Map;

/**
 * A link which selects a field an puts it into the input marker field.
 */
public class SelectLink extends ChainLink {

    private String field;

    /**
     * Get the field to select.
     *
     * @return The field.
     */
    public String getField() {
        return field;
    }

    /**
     * Set the field to select.
     *
     * @param field The field to select.
     */
    public void setField(String field) {
        this.field = field;
    }

    public void configure(Map<String, Object> config) {
        if (config.containsKey("field")) {
            assert config.get("field") instanceof String;
            this.setField((String) config.get("field"));
        }
    }

    /**
     * Copy the selected field to select to the input marker field.
     *
     * @param data Input data.
     * @return Data with an additional input marker field.
     */
    @Override
    @SuppressWarnings("unchecked")
    public JSONObject parse(JSONObject data) {
        if (this.getField() == null) throw new IllegalStateException("The field to select should be specified.");

        data.put(Constants.INPUT_MARKER, data.get(this.getField()));

        return data;
    }

}
