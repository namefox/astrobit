package com.astrobit.hub.pages;

import com.astrobit.hub.HubConfiguration;
import com.astrobit.hub.Page;
import com.astrobit.hub.Project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ProjectsPage extends Page {

    private ArrayList<Project> projects;

    public ProjectsPage() {
        JPanel top = new JPanel();
        JPanel east = new JPanel();

        top.setLayout(new BorderLayout());

        JButton create = new JButton("Create");
        create.setBackground(new Color(0x3c83c5));
        create.setForeground(Color.white);
        east.add(create);

        JButton open = new JButton("Open");
        east.add(open);

        JLabel label = new JLabel("Projects");
        label.setFont(label.getFont().deriveFont(30f).deriveFont(Font.BOLD));
        label.setHorizontalAlignment(JLabel.CENTER);

        top.add(east, BorderLayout.EAST);
        top.add(label);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onShow() {
        projects = (ArrayList<Project>) HubConfiguration.get("projects", new ArrayList<Project>());

        // TODO: Load project elements
    }

    @Override
    public void reset() {
        HubConfiguration.set("projects", projects);
    }
}