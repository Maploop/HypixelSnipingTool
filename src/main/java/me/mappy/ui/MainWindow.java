package me.mappy.ui;

import me.mappy.LoginListener;
import me.mappy.Main;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final static Color background = Main.background;
    private final static Color foreground = Main.foreground;
    private final static Color btnBg = Main.accentColor;

    private JPanel contentPane;

    private JLabel title;
    private JLabel textFieldTitle;
    public static OutputDevice outputDevice;

    private JButton configure = new JButton("Configure");
    private JButton start = new JButton("Start");
    private JButton stop = new JButton("Stop");

    private JPopupMenu contextMenu;
    private JMenuItem copy;
    private JMenuItem settings;

    public MainWindow() {
        super("Hypixel Login Checker");
        contentPane = new JPanel();

        outputDevice = new OutputDevice();

        setResizable(false);

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLayout(null);
        setLocationRelativeTo(null);

        setSize(800, 600);
        contentPane.setBackground(background);
        contentPane.setForeground(background);

        title = new JLabel("Login Checker");
        title.setBounds(300, 10, 200, 30);
        title.setFont(new java.awt.Font("Tahoma", 1, 24));
        title.setForeground(foreground);
        contentPane.add(title);

        configure.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/assets/settings.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        configure.setBounds(20, 525, 160, 30);
        configure.setForeground(background);
        configure.setBackground(btnBg);
        configure.setBorderPainted(false);
        configure.setBorder(BorderFactory.createLineBorder(Main.foreground));
        configure.setOpaque(true);
        configure.setCursor(new Cursor(Cursor.HAND_CURSOR));
        configure.addActionListener(
                e -> {
                    new ConfigWindow();
                }
        );
        contentPane.add(configure);

        stop.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/assets/stop.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        stop.setBounds(380, 525, 160, 30);
        stop.setForeground(background);
        stop.setBackground(btnBg);
        stop.setBorderPainted(false);
        stop.setBorder(BorderFactory.createLineBorder(Main.foreground));
        stop.setOpaque(true);
        stop.setCursor(new Cursor(Cursor.HAND_CURSOR));
        stop.addActionListener(e -> {
            outputDevice.write("Terminating processes...");
            LoginListener.terminate();
            outputDevice.write("Stopped.");
            start.setEnabled(true);
            stop.setEnabled(false);
        });
        contentPane.add(stop);
        stop.setEnabled(false);

        start.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/assets/start.png")).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        start.setBounds(200, 525, 160, 30);
        start.setForeground(background);
        start.setBackground(btnBg);
        start.setBorderPainted(false);
        start.setBorder(BorderFactory.createLineBorder(Main.foreground));
        start.setOpaque(true);
        start.setCursor(new Cursor(Cursor.HAND_CURSOR));

        start.addActionListener(e -> {
            if (LoginListener.running) {
                JOptionPane.showMessageDialog(null, "Already running!");
                return;
            }

            outputDevice.write("Starting application...");

            outputDevice.write("Re-checking license key validation...");
            if (!Main.validKey) {
                outputDevice.write("Invalid license key!");
                outputDevice.write("Stopping...");
                JOptionPane.showMessageDialog(null, "Invalid license key!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            new Thread(() -> {
                LoginListener.main(null);
            }).start();

            start.setEnabled(false);
            stop.setEnabled(true);
        });
        contentPane.add(start);

        textFieldTitle = new JLabel("Output");
        textFieldTitle.setBounds(10, 50, 200, 20);
        textFieldTitle.setFont(new java.awt.Font("Tahoma", 1, 14));
        textFieldTitle.setForeground(foreground);
        contentPane.add(textFieldTitle);

        outputDevice.setBounds(10, 70, 750, 250);
        contentPane.add(outputDevice);

        JScrollPane scrollPane = new JScrollPane(outputDevice, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(10, 70, 750, 450);
        contentPane.add(scrollPane);

        setupContextMenu();

        setFocusable(true);

        setVisible(true);

        requestFocus();
    }

    private void setupContextMenu() {
        contextMenu = new JPopupMenu();
        copy = new JMenuItem("Copy");
        settings = new JMenuItem("Settings");
        contextMenu.add(copy);
        contextMenu.add(settings);
        outputDevice.add(contextMenu);

        settings.setBackground(new Color(57, 57, 57, 255));
        settings.setForeground(new Color(220, 220, 220, 255));
        copy.setBackground(new Color(57, 57, 57, 255));
        copy.setForeground(new Color(220, 220, 220, 255));

        copy.addActionListener(e -> {
            outputDevice.copy();
            outputDevice.write("Copied text.");
        });

        settings.addActionListener(e -> {
            new ConfigWindow();
        });

        outputDevice.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == 3) {
                    contextMenu.show(outputDevice, evt.getX(), evt.getY());
                }
            }
        });
    }
}
