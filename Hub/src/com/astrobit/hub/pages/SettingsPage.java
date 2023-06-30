package com.astrobit.hub.pages;

import com.astrobit.hub.components.Page;
import com.astrobit.hub.windows.Main;
import com.astrobit.shared.Configuration;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SettingsPage extends Page {

    private final JTextField defaultProjectPath;
    private final JTextField defaultInstallPath;
    private final JComboBox<String> theme;

    public SettingsPage() {
        setLayout(new BorderLayout());

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        defaultProjectPath = new JTextField();
        defaultInstallPath = new JTextField();
        theme = new JComboBox<>();
        theme.setModel(new DefaultComboBoxModel<>(new String[] {"Light", "Dark", "IntelliJ", "Darcula"}));

        box.add(formPanel(new JLabel("Default Project Path: "), defaultProjectPath, browse(defaultProjectPath)));
        box.add(formPanel(new JLabel("Default Install Path: "), defaultInstallPath, browse(defaultInstallPath)));
        box.add(formPanel(new JLabel("Theme: "), theme, null));

        add(box);

        JPanel bottom = new JPanel();

        JButton save = new JButton("Save");
        save.setBackground(new Color(0x3c83c5));
        save.setForeground(Color.white);
        save.addActionListener(e -> save());
        bottom.add(save);

        JButton reset = new JButton("Reset");
        reset.addActionListener(e -> onShow());
        bottom.add(reset);

        add(bottom, BorderLayout.SOUTH);
    }

    private void save() {
        Configuration.set("projectPath", defaultProjectPath.getText());
        Configuration.set("installPath", defaultInstallPath.getText());
        Configuration.set("theme", theme.getSelectedItem());

        Main.setTheme((String) theme.getSelectedItem());
    }

    private JPanel formPanel(Component west, Component center, Component east) {
        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BorderLayout());

        if (west != null)
            panel.add(west, BorderLayout.WEST);
        if (center != null)
            panel.add(center);
        if (east != null)
            panel.add(east, BorderLayout.EAST);

        return panel;
    }

    private JButton browse(JTextField textField) {
        JButton button = new JButton("Browse");
        button.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (chooser.showOpenDialog(Main.instance) != 0) return;
            textField.setText(chooser.getSelectedFile().getAbsolutePath());
        });
        return button;
    }

    @Override
    public void onShow() {
        defaultProjectPath.setText((String) Configuration.get("projectPath", System.getProperty("user.home")));
        defaultInstallPath.setText((String) Configuration.get("installPath", System.getProperty("user.home") + File.separator + ".astrobit" + File.separator + "editors"));
        theme.setSelectedItem(Configuration.get("theme", "Light"));
    }

    @Override public void reset() {}
}