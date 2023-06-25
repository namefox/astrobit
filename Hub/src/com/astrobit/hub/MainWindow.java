package com.astrobit.hub;

import com.astrobit.hub.pages.ExtensionsTabbedPane;
import com.astrobit.hub.pages.InstallsPage;
import com.astrobit.hub.pages.ProjectsPage;
import com.astrobit.hub.pages.SettingsPage;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class MainWindow extends JFrame {

    private Page page;
    private static MainWindow instance;

    public MainWindow() {
        setTitle("Astrobit Hub");
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

        SwingUtilities.invokeLater(MainWindow::new);
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