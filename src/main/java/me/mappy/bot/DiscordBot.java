package me.mappy.bot;

import me.mappy.util.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class DiscordBot {
    public static JDA jda;

    public static void start() {
        try {
            JDABuilder builder = JDABuilder.createDefault(Config.getString("token"));

            builder.setActivity(Activity.watching("loli hentai :3"));

            jda = builder.build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
