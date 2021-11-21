package me.mappy;

import me.mappy.bot.DiscordBot;
import me.mappy.util.Config;
import me.mappy.util.Util;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Scanner;

public class LoginListener {
    private static Thread main;
    public static boolean running = false;

    public static void main(String[] args) {
        running = true;

        DiscordBot.start();

        main = new Thread(() -> {

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
                    System.out.println("Error occurred while sending request: " + ex.getMessage());
                    System.out.println("Terminating process.");
                    terminate();
                }
            }

        });

        main.start();
    }

    public static void terminate() {
        main.interrupt();
        main.stop();

        running = false;
    }
}
