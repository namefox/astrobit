package com.astrobit.hub.pages;

import com.astrobit.hub.HubConfiguration;
import com.astrobit.hub.components.Page;
import com.astrobit.hub.windows.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SettingsPage extends Page {

    private final JTextField defaultProjectPath;
    private final JTextField defaultInstallPath;
    private final JCheckBox darkMode;

    public SettingsPage() {
        setLayout(new BorderLayout());

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        defaultProjectPath = new JTextField();
        defaultInstallPath = new JTextField();
        darkMode = new JCheckBox("Dark Mode");

        box.add(formPanel(new JLabel("Default Project Path: "), defaultProjectPath, browse(defaultProjectPath)));
        box.add(formPanel(new JLabel("Default Install Path: "), defaultInstallPath, browse(defaultInstallPath)));
        box.add(formPanel(null, darkMode, null));

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
        HubConfiguration.set("projectPath", defaultProjectPath.getText());
        HubConfiguration.set("installPath", defaultInstallPath.getText());
        HubConfiguration.set("dark", darkMode.isSelected());

        Main.setTheme(darkMode.isSelected() ? "Dark" : "Light");
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
        defaultProjectPath.setText((String) HubConfiguration.get("projectPath", System.getProperty("user.home")));
        defaultInstallPath.setText((String) HubConfiguration.get("installPath", System.getProperty("user.home") + File.separator + ".astrobit" + File.separator + "editors"));
        darkMode.setSelected((Boolean) HubConfiguration.get("dark", false));
    }

    @Override public void reset() {}
}