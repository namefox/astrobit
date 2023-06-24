package com.astrobit.hub;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class MainWindow extends JFrame {

    private static JPanel page;
    private static MainWindow instance;

    public MainWindow() {
        setTitle("Astrobit Hub");
        setSize(1152, 648);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        instance = this;

        add(new SideBar(), BorderLayout.WEST);
        setPage(ProjectsPage.class);

        setVisible(true);
    }

    public static void setPage(Class<? extends JPanel> panel) {
        if (page != null) {
            if (page.getClass() == panel) return;
            instance.remove(page);
        }

        try {
            page = panel.getConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        instance.add(page);
        instance.revalidate();
        instance.repaint();
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        SwingUtilities.invokeLater(MainWindow::new);
    }
}