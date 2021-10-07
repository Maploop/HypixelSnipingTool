package me.mappy;

import me.mappy.bot.DiscordBot;
import me.mappy.cache.Cache;
import me.mappy.util.Config;
import me.mappy.util.Util;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

// Made by rae with love <3
public class Main {
    private static FileWriter fw = null;

    public static void main(String[] args) throws Exception {
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("---------------------------------------");
        System.out.println("Hypixel Login listener v1.0-DEV");
        System.out.println("Created by [REDACTED]");
        System.out.println("---------------------------------------");
        System.out.println("");
        System.out.println("The app will start in 5 seconds.");

        Thread.sleep(5000);

        File f = new File("./config.json");
        if (!f.canRead())
            f.setReadable(true);

        if (!f.canWrite())

        if (!f.exists()) {
            f.createNewFile();

            JSONObject object = new JSONObject();
            object.put("token", "");
            object.put("channel-id", 0);
            object.put("api-key", "");

            JSONArray array = new JSONArray();
            array.add("f32740f7503d4771860c287fc6376442");

            object.put("checks", array);

            try {
                fw = new FileWriter(f);

                fw.write(object.toJSONString());
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(-1);
            } finally {
                assert fw != null;
                fw.flush();
                fw.close();
            }

            System.out.println("Please fill in the config.json file and re-run the app.");
            System.exit(0);
        }

        DiscordBot.start();

        Thread main = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);

                    for (String uuid : Config.getList("checks")) {
                        URI uri = new URI("https://api.hypixel.net/status");

                        URI newuri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), "uuid=" + uuid, uri.getFragment());

                        URL url = newuri.toURL();

                        URLConnection urc = url.openConnection();
                        urc.addRequestProperty("User-Agent", "Mozilla/5.0");
                        urc.addRequestProperty("key", Config.getString("api-key"));
                        urc.addRequestProperty("API-Key", Config.getString("api-key"));
                        urc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
                        urc.addRequestProperty("Pragma", "no-cache");

                        String json = new Scanner(urc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
                        JSONParser parser = new JSONParser();
                        Object obj = parser.parse(json);

                        JSONObject defaultObject = (JSONObject) obj;
                        JSONObject targetObject = (JSONObject) defaultObject.get("session");

                        boolean online = (boolean) targetObject.get("online");

                        if (online) {

                            MessageEmbed embed = new MessageEmbed("https://api.hypixel.net/status",
                                    "Someone is online!", Util.getNameFromUUID(uuid) + " is on hypixel! SHEEEEESH",
                                    null, null, 69, null, null, null, null, null, null,
                                    Arrays.asList(
                                            new MessageEmbed.Field("Game Type", targetObject.get("gameType").toString(), false),
                                            new MessageEmbed.Field("Game Mode", targetObject.get("mode").toString(), false)
                                    ));

                            DiscordBot.jda.getTextChannelById(Config.getLong("channel-id")).sendMessage(embed).queue();
                        }

                        System.out.println(uuid + "://online=" + online + ",mode=" + targetObject.get("mode") + ",type=" + targetObject.get("gameType"));
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        main.start();
    }
}
