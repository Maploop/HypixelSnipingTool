package me.mappy;

import me.mappy.bot.DiscordBot;
import me.mappy.cache.Cache;
import me.mappy.ui.MainWindow;
import me.mappy.ui.OutputDevice;
import me.mappy.util.Config;
import me.mappy.util.Util;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;

// Made by rae with love <3
public class Main {
    public static Color accentColor;
    public static Color background;
    public static Color foreground;

    public static boolean validKey = false;
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

//        Thread.sleep(5000);

        File f = new File("./config.json");
        if (!f.canRead())
            f.setReadable(true);

        if (!f.canWrite())

        if (!f.exists()) {
            f.createNewFile();

            try {
                Files.copy(Main.class.getResourceAsStream("/config.json"), f.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception ex) {

            } finally {
                System.out.println("Config file created.");
            }

            System.out.println("Please fill in the config.json file and re-run the app.");
            System.exit(0);
        }

        Config.loadColors();

        new MainWindow();
        MainWindow.outputDevice.write("Loading license key...");

        System.setOut(new PrintStream(System.out) {
            @Override
            public void println(String x) {
                MainWindow.outputDevice.write(x);
                super.println(x);
            }
        });

        try {
            boolean resultBoolean = Config.getString("license").equals("O5wZzoAhh11gY8zbvV8V0CzaY1jXjN4Av1H9PLhcs");
            if (resultBoolean) {
                MainWindow.outputDevice.write("License key is valid.");
                validKey = true;
            } else {
                MainWindow.outputDevice.write("License key is invalid! please change it in the config!");
                validKey = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to load license key.");
        }
    }
}
