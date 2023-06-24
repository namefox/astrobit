package com.astrobit.hub;

import javax.swing.*;
import java.awt.*;

public class SideBar extends JPanel {

    private SideBarButton selected;

    public SideBar() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBackground(getBackground().darker());

        add(button("Projects", ProjectsPage.class));
        add(button("Installs", InstallsPage.class));
        add(button("Modules", ModulesPage.class));
        add(button("Settings", SettingsPage.class));
    }

    private SideBarButton button(String text, Class<? extends JPanel> page) {
        SideBarButton button = new SideBarButton(text);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setBackground(getBackground());
        button.addActionListener(e -> {
            if (selected != null) {
                selected.setSelected(false);
            }

            MainWindow.setPage(page);
            button.setSelected(true);
            selected = button;
        });
        return button;
    }
}