package com.astrobit.hub.windows;

import com.astrobit.hub.Debug;
import com.astrobit.hub.HubConfiguration;
import com.astrobit.hub.components.Page;
import com.astrobit.hub.pages.ExtensionsTabbedPane;
import com.astrobit.hub.pages.InstallsPage;
import com.astrobit.hub.pages.ProjectsPage;
import com.astrobit.hub.pages.SettingsPage;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class Main extends JFrame {

    private Page page;
    public static Main instance;

    public Main() {
        setTitle("Astrobit Hub");
        try {
            InputStream in = getClass().getResourceAsStream("/icon.png");
            if (in != null)
                setIconImage(ImageIO.read(in));
        } catch (IOException e) {
            Debug.error(e);
        }
        setSize(1152, 648);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        page = new ProjectsPage();
        page.onShow();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);

        tabbedPane.addTab("Projects", page);
        tabbedPane.addTab("Extensions", new ExtensionsTabbedPane());
        tabbedPane.addTab("Settings", new SettingsPage());
        tabbedPane.addTab("Installs", new InstallsPage());

        tabbedPane.addChangeListener(e -> {
            page.reset();
            page = (Page) tabbedPane.getSelectedComponent();
            page.onShow();
        });

        add(tabbedPane);
        setVisible(true);

        instance = this;
    }

    public static void main(String[] args) {
        HubConfiguration.setup();

        if ((boolean)HubConfiguration.get("dark", false))
            FlatDarkLaf.setup();
        else
            FlatLightLaf.setup();

        SwingUtilities.invokeLater(Main::new);
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static void setTheme(String name) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.Flat" + name + "Laf");
            SwingUtilities.updateComponentTreeUI(instance);
        } catch (Exception e) {
            Debug.error(e);
        }
    }
}