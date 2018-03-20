package nl.qsight.links.io;

import nl.qsight.chainlink.ChainLinkIO;
import nl.qsight.utils.StringUtils;
import org.json.simple.JSONObject;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A link for splitting strings.
 */
public class SplitLink extends ChainLinkIO<String> {

    private String delimiter;
    private boolean delimiterIsRegex;
    private Map<String, Object> selector;

    public String getDelimiter() {
        return delimiter;
    }

    public boolean isDelimiterRegex() {
        return this.delimiterIsRegex;
    }

    public void setDelimiter(String delimiter) {
        this.setDelimiter(delimiter, false);
    }

    public void setDelimiter(String delimiter, boolean isRegex) {
        this.delimiter = delimiter;
        this.delimiterIsRegex = isRegex;
    }

    @SuppressWarnings("unchecked")
    public void setSelector(Map selector) {
        this.selector = selector;
    }

    public Map<String, Object> getSelector() {
        return this.selector;
    }

    public void configure(Map<String, Object> config) {
        if (config.containsKey("delimiter")) {
            assert config.get("delimiter") instanceof String;
            this.setDelimiter((String) config.get("delimiter"));
        }
        if (config.containsKey("selector")) {
            assert config.get("selector") instanceof Map;
            this.setSelector((Map) config.get("selector"));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object parseInputField(String input) {
        if (this.getDelimiter() == null) throw new IllegalStateException("Delimiter is not set.");
        if (this.getDelimiter() == null) throw new IllegalStateException("Delimiter RegEx boolean is not set.");
        if (this.getSelector() == null) throw new IllegalStateException("Selector is not set.");

        String delimiter = this.getDelimiter();
        if (!this.isDelimiterRegex()) {
            delimiter = Pattern.quote(delimiter);
        }

        String[] parts = input.split("(" + delimiter + ")");

        JSONObject result = new JSONObject();
        for (Object positionObject : this.selector.keySet()) {
            int position = StringUtils.toInteger(positionObject);
            boolean isPositionSet = StringUtils.isNumerical(positionObject);
            if (!isPositionSet) throw new IllegalStateException("Position is not numerical.");
            if (position < 0) {
                position += parts.length;
            }
            if (position < parts.length) {
                String positionLabel = (String) this.selector.get(positionObject);
                String value = parts[position];
                result.put(positionLabel, value);
            }
        }

        return result;
    }

}
