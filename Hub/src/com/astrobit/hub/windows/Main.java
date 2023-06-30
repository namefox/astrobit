package com.astrobit.hub.windows;

import com.astrobit.hub.components.Page;
import com.astrobit.hub.pages.ExtensionsTabbedPane;
import com.astrobit.hub.pages.InstallsPage;
import com.astrobit.hub.pages.ProjectsPage;
import com.astrobit.hub.pages.SettingsPage;
import com.astrobit.shared.Configuration;
import com.astrobit.shared.Debug;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                page.reset();
            }
        });
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

    public static void resetPage() {
        instance.page.reset();
        instance.page.onShow();
    }

    public static void main(String[] args) {
        Configuration.setup("hub");

        setTheme((String) Configuration.get("theme", "Light"));

        SwingUtilities.invokeLater(Main::new);
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static void setTheme(String name) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.Flat" + name + "Laf");
            if (instance != null)
                SwingUtilities.updateComponentTreeUI(instance);
        } catch (Exception e) {
            Debug.error(e);
        }
    }
}