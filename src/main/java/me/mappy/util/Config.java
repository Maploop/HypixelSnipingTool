package me.mappy.util;

import me.mappy.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

    public static void set(String k, Object v) {
        if (k.contains(".")) {
            String[] keys = k.split("[.]");
            JSONParser parser = new JSONParser();
            try {
                Object o = parser.parse(new FileReader(file));
                JSONObject object = (JSONObject) o;
                JSONObject current = object;
                for (int i = 0; i < keys.length - 1; i++) {
                    if (current.get(keys[i]) == null) {
                        current.put(keys[i], new JSONObject());
                    }
                    current = (JSONObject) current.get(keys[i]);
                }

                current.put(keys[keys.length - 1], v);
                FileWriter writer = new FileWriter(file);
                writer.write(object.toJSONString());
                writer.flush();
                writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            Object o = parser.parse(reader);
            JSONObject object = (JSONObject) o;
            object.put(k, v);
            FileWriter writer = new FileWriter(file);
            writer.write(object.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void loadColors() {
        if (!file.canRead())
            file.setReadable(true);

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            Object o = parser.parse(reader);
            JSONObject object = (JSONObject) o;
            JSONObject accent = (JSONObject) object.get("accent");
            int accentR = Integer.parseInt(accent.get("color").toString().split(",")[0]);
            int accentG = Integer.parseInt(accent.get("color").toString().split(",")[1]);
            int accentB = Integer.parseInt(accent.get("color").toString().split(",")[2]);
            int accentA = Integer.parseInt(accent.get("color").toString().split(",")[3]);

            int backgroundR = Integer.parseInt(accent.get("background").toString().split(",")[0]);
            int backgroundG = Integer.parseInt(accent.get("background").toString().split(",")[1]);
            int backgroundB = Integer.parseInt(accent.get("background").toString().split(",")[2]);
            int backgroundA = Integer.parseInt(accent.get("background").toString().split(",")[3]);

            int foregroundR = Integer.parseInt(accent.get("foreground").toString().split(",")[0]);
            int foregroundG = Integer.parseInt(accent.get("foreground").toString().split(",")[1]);
            int foregroundB = Integer.parseInt(accent.get("foreground").toString().split(",")[2]);
            int foregroundA = Integer.parseInt(accent.get("foreground").toString().split(",")[3]);

            Main.accentColor = new Color(accentR, accentG, accentB, accentA);
            Main.background = new Color(backgroundR, backgroundG, backgroundB, backgroundA);
            Main.foreground = new Color(foregroundR, foregroundG, foregroundB, foregroundA);

            System.out.println("Accent colors loaded.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
