package nl.qsight.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * Extracts (key, value) pairs from a string efficiently.
     *
     * @param message Message to find (key, value) pairs in.
     * @param keyValueDelimiter Regular expression for the delimiter between the key and the value (for example: "=").
     * @param pairDelimiters Regular expression for the delimiter between pairs (for example: " ").
     * @param validKeyChars Valid characters for the keys (e.g. "a-zA-Z").
     * @return A map in which the (key, value) pairs are stored.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseKeyValuePairs(String message, String keyValueDelimiter,
                                                         String pairDelimiters, String validKeyChars) {
        Map<String, String> keyValues = new HashMap<>();
        StringBuilder splitRegex = new StringBuilder("(?<!\\\\)(");
        for (int index = 0; index < pairDelimiters.length(); index++) {
            String pairDelimiter = String.valueOf(pairDelimiters.charAt(index));
            splitRegex.append(Pattern.quote(pairDelimiter)).append("|");
        }
        splitRegex = new StringBuilder(splitRegex.substring(0, splitRegex.length() - 1));
        splitRegex.append(")");

        for (String chunk : message.split(splitRegex.toString())) {
            String[] keyValuePair = chunk.split(Pattern.quote(keyValueDelimiter), 2);
            if (keyValuePair.length < 2) continue;
            String key = keyValuePair[0];
            String value = keyValuePair[1];

            Pattern pattern = Pattern.compile("[" + validKeyChars + "]+$");
            Matcher matcher = pattern.matcher(key);
            String lastSubkey = null;
            if (matcher.find()) {
                lastSubkey = matcher.group(0);
            }

            if (!value.isEmpty()) keyValues.put(lastSubkey, value);
        }

        return keyValues;
    }

    public static boolean isNumerical(Object number) {
        if (number instanceof String) {
            try {
                int result = Integer.parseInt((String) number);
                assert result >= -999999;
                return true;
            } catch (Exception e) {
                return false;
            }
        } else return number instanceof Integer;
    }

    public static int toInteger(Object number) {
        if (!StringUtils.isNumerical(number)) return -1;
        if (number instanceof String) {
            return Integer.parseInt((String) number);
        } else if (number instanceof Integer) {
            return (int) number;
        }
        return -1;
    }

    public static String normalize(String key) {
        String result = org.apache.commons.lang3.StringUtils.stripAccents(key);
        result = result.replaceAll("[^A-Za-z_0-9]+", "_");
        result = result.replaceAll("[_]+", "_");
        result = result.replaceAll("^[_]*(.*)[_]*$", "$1");
        result = result.toLowerCase();
        return result;
    }

}