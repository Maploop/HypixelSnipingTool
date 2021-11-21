package me.mappy.ui;

import me.mappy.Main;
import me.mappy.util.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ConfigWindow extends JFrame {
    private JButton chooseColors = new JButton("Choose");
    private JLabel colorAccent = new JLabel("Accent Color");
    private JButton save = new JButton("Save");

    private JButton addBtn;

    private JColorChooser colorChooser;
    private JPanel colorPanel;

    private JLabel botToken = new JLabel("Discord bot token");
    private JTextField token = new JTextField();
    private JLabel botChannel = new JLabel("Discord channel");
    private JTextField channel = new JTextField();
    private JLabel apiKey = new JLabel("API key");
    private JTextField api = new JTextField();
    private JLabel license = new JLabel("Activation License");
    private JTextField licenseKey = new JTextField();

    private List<String> uuids = Config.getList("checks");

    public ConfigWindow() {
        setTitle("Login Listener Config");
        setSize(600, 700);
        setLocationRelativeTo(null);

        setLayout(null);
        setResizable(false);
        setFocusable(true);
        requestFocus();

        getContentPane().setBackground(Main.background);
        getContentPane().setForeground(Main.background);

        colorAccent.setBounds(10, 60, 100, 20);
        colorAccent.setFont(new Font("Arial", Font.BOLD, 16));
        colorAccent.setForeground(Main.foreground);
        add(colorAccent);

        save.setBounds(10, 600, 200, 50);
        save.setFont(new Font("Arial", Font.BOLD, 24));
        save.setBackground(Main.accentColor);
        save.setForeground(Main.background);
        save.setBorder(BorderFactory.createLineBorder(Main.foreground));
        save.setIcon(new ImageIcon(new ImageIcon(Main.class.getResource("/assets/save.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        save.setFocusPainted(false);
        add(save);

        save.addActionListener(e -> {
            try {
                Config.set("channel-id", Long.parseLong(channel.getText()));
            } catch (NumberFormatException foramtException) {
                JOptionPane.showMessageDialog(null, "Invalid channel ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Config.set("checks", uuids);
            Config.set("accent.color", Main.accentColor.getRed() + "," + Main.accentColor.getGreen() + "," + Main.accentColor.getBlue() + "," + Main.accentColor.getAlpha());
            Config.set("accent.background", Main.background.getRed() + "," + Main.background.getGreen() + "," + Main.background.getBlue() + "," + Main.background.getAlpha());
            Config.set("accent.foreground", Main.foreground.getRed() + "," + Main.foreground.getGreen() + "," + Main.foreground.getBlue() + "," + Main.foreground.getAlpha());
            Config.set("token", token.getText());
            Config.set("license", licenseKey.getText());
            Config.set("api-key", api.getText());

            dispose();
        });

        chooseColors.setBounds(120, 55, 100, 20);
        chooseColors.setBackground(Main.accentColor);
        chooseColors.setForeground(Main.background);
        chooseColors.setBorder(BorderFactory.createLineBorder(Main.foreground));
        chooseColors.setFocusPainted(false);
        getContentPane().add(chooseColors);

        chooseColors.addActionListener(e -> {
            colorChooser = new JColorChooser();
            colorChooser.setPreviewPanel(new JPanel());
            colorChooser.setColor(Main.accentColor);
            colorChooser.getSelectionModel().addChangeListener(e1 -> {
                Main.accentColor = colorChooser.getColor();
                colorPanel.setBackground(Main.accentColor);
                colorPanel.setForeground(Main.background);
            });
        });

        JLabel label = new JLabel("Login Listener Config");
        label.setForeground(Main.foreground);
        label.setBounds(10, 10, 200, 20);
        add(label);

        botToken.setBounds(10, 100, 200, 20);
        botToken.setFont(new Font("Arial", Font.BOLD, 16));
        botToken.setForeground(Main.foreground);
        add(botToken);

        token.setBounds(10, 120, 200, 20);
        token.setFont(new Font("Arial", Font.BOLD, 16));
        token.setText(Config.getString("token"));
        token.setForeground(Main.background);
        add(token);

        botChannel.setBounds(10, 150, 200, 20);
        botChannel.setFont(new Font("Arial", Font.BOLD, 16));
        botChannel.setForeground(Main.foreground);
        add(botChannel);

        channel.setBounds(10, 170, 200, 20);
        channel.setFont(new Font("Arial", Font.BOLD, 16));
        channel.setText(Config.getString("channel-id"));
        channel.setForeground(Main.background);
        add(channel);

        apiKey.setBounds(10, 200, 200, 20);
        apiKey.setFont(new Font("Arial", Font.BOLD, 16));
        apiKey.setForeground(Main.foreground);
        add(apiKey);

        api.setBounds(10, 220, 200, 20);
        api.setFont(new Font("Arial", Font.BOLD, 16));
        api.setText(Config.getString("api-key"));
        api.setForeground(Main.background);
        add(api);

        license.setBounds(10, 250, 200, 20);
        license.setFont(new Font("Arial", Font.BOLD, 16));
        license.setForeground(Main.foreground);
        add(license);

        licenseKey.setBounds(10, 270, 200, 20);
        licenseKey.setFont(new Font("Arial", Font.BOLD, 16));
        licenseKey.setForeground(Main.background);
        licenseKey.setText(Config.getString("license"));
        add(licenseKey);

        JLabel uuidLable = new JLabel("UUIDs");
        uuidLable.setForeground(Main.foreground);
        uuidLable.setBounds(10, 300, 200, 20);
        add(uuidLable);

        refreshUuids();

        setVisible(true);
    }

    private List<JLabel> labels = new ArrayList<>();
    private List<JButton> removes = new ArrayList<>();

    private void refreshUuids() {
        if (addBtn != null) {
            addBtn.setVisible(false);
            remove(addBtn);
        }

        for (JLabel label : labels) {
            remove(label);
            label.setVisible(false);
        }

        for (JButton button : removes) {
            remove(button);
            button.setVisible(false);
        }

        for (String uuid : uuids) {
            JLabel label1 = new JLabel(uuid);
            label1.setForeground(Main.foreground);
            label1.setBounds(10, 320 + (uuids.indexOf(uuid) * 20), 200, 20);
            add(label1);
            labels.add(label1);

            JButton remove = new JButton("Remove");
            remove.setBounds(label1.getWidth() + 50, label1.getY(), 100, 20);
            remove.setBackground(Main.accentColor);
            remove.setForeground(Main.background);
            remove.setBorder(BorderFactory.createLineBorder(Main.foreground));
            remove.setFocusPainted(false);
            remove.addActionListener(e -> {
                uuids.remove(uuid);
                remove.setVisible(false);
                label1.setVisible(false);
                refreshUuids();
            });
            add(remove);
            removes.add(remove);
        }

        JButton addUUID = new JButton("Add");

        addUUID.setBounds(10, 320 + (uuids.size() * 20), 100, 20);
        addUUID.setBackground(Main.accentColor);
        addUUID.setForeground(Main.background);
        addUUID.setBorder(BorderFactory.createLineBorder(Main.foreground));
        addUUID.setFocusPainted(false);
        addUUID.addActionListener(e -> {
            String uuid = JOptionPane.showInputDialog(null, "Enter UUID");
            if (uuid != null) {
                uuids.add(uuid);
                refreshUuids();
            }
        });
        add(addUUID);
        addBtn = addUUID;

        update(this.getGraphics());
    }
}
