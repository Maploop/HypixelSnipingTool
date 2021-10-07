package me.mappy.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Util {
    public static String getNameFromUUID(String uuid) {
        String result = "null";

        try {
            URL url = new URL("https://api.mojang.com/user/profiles/" + uuid + "/names");
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0");
            connection.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            connection.addRequestProperty("Pragma", "no-cache");

            String json = new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JSONParser parser = new JSONParser();
            Object o = parser.parse(json);

            JSONArray array = (JSONArray) o;
            JSONObject obj = (JSONObject) array.get(array.size() - 1);

            result = (String) obj.get("name");

            connection.getInputStream().close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
