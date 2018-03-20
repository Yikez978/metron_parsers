package nl.qsight.links.io;

import nl.qsight.chainlink.ChainLinkIO;
import nl.qsight.utils.StringUtils;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexLink extends ChainLinkIO<String> {

    private Map<String, Object> selector;
    private String pattern;

    @SuppressWarnings("unchecked")
    public void setSelector(Map selector) {
        this.selector = selector;
    }

    public Map<String, Object> getSelector() {
        return this.selector;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void configure(Map<String, Object> config) {
        if (config.containsKey("pattern")) {
            assert config.get("pattern") instanceof String;
            this.setPattern((String) config.get("pattern"));
        }
        if (config.containsKey("selector")) {
            assert config.get("selector") instanceof Map;
            this.setSelector((Map) config.get("selector"));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object parseInputField(String input) {
        Pattern pattern = Pattern.compile(this.pattern);
        Matcher matcher = pattern.matcher(input);

        JSONObject result = null;

        if (matcher.find()) {
            result = new JSONObject();

            for (String selectorKey : this.selector.keySet()) {
                result.put(selectorKey, "");
                Object positionObject = this.selector.get(selectorKey);

                int position = StringUtils.toInteger(positionObject);
                boolean isPositionSet = StringUtils.isNumerical(positionObject);
                if (!isPositionSet) throw new IllegalStateException("Position is not numerical.");

                String value = matcher.group(position);
                if (value != null) result.put(selectorKey, value);
            }
        }

        return result;
    }

}
