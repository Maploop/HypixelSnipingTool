package me.mappy.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private static final File file = new File("./config.json");

    public static String getString(String path) {
        String result = "null";

        if (!file.canRead())
            file.setReadable(true);

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            Object o = parser.parse(reader);
            JSONObject object = (JSONObject) o;
            result = object.get(path).toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static long getLong(String path) {
        long result = 0;

        if (!file.canRead())
            file.setReadable(true);

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            Object o = parser.parse(reader);
            JSONObject object = (JSONObject) o;
            result = Long.parseLong(object.get(path).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static <T> List<String> getList(String path) {
        List<String> result = new ArrayList<>();

        if (!file.canRead())
            file.setReadable(true);

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            Object o = parser.parse(reader);
            JSONObject object = (JSONObject) o;
            JSONArray array = (JSONArray) object.get(path);

            result.addAll(array);

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
