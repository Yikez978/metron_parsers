package nl.qsight.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JSONUtils {

    public static Map<String, Object> JSONToMap(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        return mapper.readValue(json, type);
    }

    public static Map<String, Object> JSONToMap(JSONObject json) throws IOException {
        return JSONUtils.JSONToMap(json.toString());
    }

    public static JSONObject stringToJSON(String json) throws ParseException {
        JSONParser parseJSON = new JSONParser();
        return (JSONObject) parseJSON.parse(json);
    }

    public static JSONObject mapToJSON(Map<String, Object> data) {
        return new JSONObject(data);
    }

}
