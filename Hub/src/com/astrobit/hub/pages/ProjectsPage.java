package com.astrobit.hub.pages;

import com.astrobit.hub.Debug;
import com.astrobit.hub.HubConfiguration;
import com.astrobit.hub.windows.Main;
import com.astrobit.hub.components.Page;
import com.astrobit.hub.Project;
import com.astrobit.hub.components.ProjectComponent;
import com.astrobit.hub.windows.CreateProjectForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ProjectsPage extends Page {

    private ArrayList<Project> projects;
    private final JPanel list;

    public ProjectsPage() {
        JPanel top = new JPanel();
        JPanel east = new JPanel();

        top.setLayout(new BorderLayout());

        JButton create = new JButton("Create");
        create.addActionListener(e -> new CreateProjectForm());
        create.setBackground(new Color(0x3c83c5));
        create.setForeground(Color.white);
        east.add(create);

        JButton open = new JButton("Open");
        open.addActionListener(this::open);
        east.add(open);

        JLabel label = new JLabel("Projects");
        label.setFont(label.getFont().deriveFont(30f).deriveFont(Font.BOLD));
        label.setHorizontalAlignment(JLabel.CENTER);

        top.add(east, BorderLayout.EAST);
        top.add(label);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);

        list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        add(new JScrollPane(list));
    }

    private void open(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(Main.instance) != 0) return;

        try {
            Files.write(Paths.get(System.getProperty("user.home") + File.separator + ".astrobit" + File.separator + "lastOpenProject"), chooser.getSelectedFile().getAbsolutePath().getBytes());

            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File("./editor.exe"));

            System.exit(0);
        } catch (IOException e1) {
            Debug.error(e1);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onShow() {
        projects = (ArrayList<Project>) HubConfiguration.get("projects", new ArrayList<Project>());

        for (Project project: projects)
            list.add(new ProjectComponent(project));

        list.revalidate();
        list.repaint();
    }

    @Override
    public void reset() {
        HubConfiguration.set("projects", projects);

        Component[] components = list.getComponents();
        for (Component component: components)
            if (component instanceof ProjectComponent)
                list.remove(component);
    }
}