package com.astrobit.hub.components;

import com.astrobit.hub.Debug;
import com.astrobit.hub.Project;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class ProjectComponent extends JButton {
    
    private boolean nullify;

    public ProjectComponent(Project project) {
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            getBorder()
        ));
        setLayout(new GridLayout(2, 3));

        Project p = project;
        if (p == null) {
            p = new Project("", "", new Date());
            nullify = true;
        }

        String[] split = p.path().split("/");
        String projectName = split[split.length - 1];

        add(infoLabel("Name"));
        add(infoLabel("Editor"));
        add(infoLabel("Modified"));
        add(label(projectName));
        add(label(p.editorVersion()));
        add(label(p.modified().toString()));

        if (!nullify) addActionListener(e -> {
            try {
                Files.write(Paths.get(System.getProperty("user.home") + File.separator + ".astrobit" + File.separator + "lastOpenProject"), project.path().getBytes());

                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File("./editor.exe"));

                System.exit(0);
            } catch (IOException e1) {
                Debug.error(e1);
            }
        });
    }

    private JLabel infoLabel(String text) {
        JLabel label = new JLabel(!nullify ? text : "       ");
        label.setForeground(new Color(label.getForeground().getRGB() + 0xFF000000, true));
        return label;
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(!nullify ? text : "       ");
        label.setFont(label.getFont().deriveFont(label.getFont().getSize() + 5f).deriveFont(Font.BOLD));
        return label;
    }
}