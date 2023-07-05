package com.astrobit.hub.components;

import com.astrobit.hub.Project;
import com.astrobit.shared.Configuration;
import com.astrobit.shared.Debug;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

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
            p = new Project("", "");
            nullify = true;
        }

        String[] split = p.path().split(Pattern.quote(File.separator));
        String projectName = split[split.length - 1];

        add(infoLabel("Name"));
        add(infoLabel("Editor"));
        add(infoLabel("Modified"));
        add(label(projectName));
        add(label(p.editorVersion()));
        add(label(p.modified().toString()));

        if (!nullify) addActionListener(e ->  {
            try {
                Files.write(Paths.get(System.getProperty("user.home") + File.separator + ".astrobit" + File.separator + "lastOpenProject"), project.path().getBytes());

                String path = (String) Configuration.get("installPath", System.getProperty("user.home") + File.separator + ".astrobit" + File.separator + "editors");
                path += File.separator + Configuration.get("latestEditorVersion", "Unknown");

                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File(path + File.separator + "bin" + File.separator + "editor.exe"));

                System.exit(0);
            } catch (IOException e1) {
                Debug.error(e1);
            }
        });
    }

    private JLabel infoLabel(String text) {
        JLabel label = new JLabel(!nullify ? text : "       ");
        label.setForeground(new Color(label.getForeground().getRGB() - 0x30000000, true));
        return label;
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(!nullify ? text : "       ");
        label.setFont(label.getFont().deriveFont(label.getFont().getSize() + 5f).deriveFont(Font.BOLD));
        return label;
    }
}