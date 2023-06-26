package com.astrobit.hub.pages;

import com.astrobit.hub.*;
import com.astrobit.hub.windows.Main;
import com.astrobit.hub.components.Page;
import com.astrobit.hub.components.ProjectComponent;
import com.astrobit.hub.windows.CreateProjectForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ProjectsPage extends Page {

    private HashMap<String, ArrayList<Project>> lists;
    private final JTabbedPane tabbedPane;
    private int sortBy, tab;

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

        JComboBox<String> sortBox = new JComboBox<>();
        sortBox.setModel(new DefaultComboBoxModel<>(new String[] {"Sort by", "Alphabetical Order", "Last Modified", "Editor Version"}));
        sortBox.addItemListener(e -> {
            sortBy = sortBox.getSelectedIndex();
            reset();
            onShow();
        });
        east.add(sortBox);

        JComboBox<String> multiplierBox = new JComboBox<>();
        multiplierBox.setModel(new DefaultComboBoxModel<>(new String[] {"Lowest to Highest", "Highest to Lowest"}));
        multiplierBox.addItemListener(e -> {
            Sorts.MULTIPLIER = multiplierBox.getSelectedIndex() == 0 ? -1 : 1;
            reset();
            onShow();
        });
        east.add(multiplierBox);

        top.add(east, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        add(tabbedPane);
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
        lists = (HashMap<String, ArrayList<Project>>) HubConfiguration.get("projects", new HashMap<>());

        JPanel all = new JPanel();
        all.setLayout(new BoxLayout(all, BoxLayout.Y_AXIS));
        tabbedPane.addTab("All", new JScrollPane(all));

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        TreeMap<String, ArrayList<Project>> sort = new TreeMap<>(Sorts.alphabeticalMap());
        sort.putAll(lists);

        for (Map.Entry<String, ArrayList<Project>> entry: sort.entrySet()) {
            JPanel tab;

            if (entry.getKey() == null) {
                tab = all;
            } else {
                tab = new JPanel();
                tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
            }

            ArrayList<Project> sorted = (ArrayList<Project>) entry.getValue().clone();
            sorted.sort(Sorts.get(sortBy));

            for (Project project: sorted) {
                tab.add(new ProjectComponent(project));
                if (!tab.equals(all))
                    all.add(new ProjectComponent(project));
            }

            tabbedPane.addTab(entry.getKey(), new JScrollPane(tab));
        }

        tabbedPane.setSelectedIndex(tab);

        tabbedPane.revalidate();
        tabbedPane.repaint();
    }

    @Override
    public void reset() {
        HubConfiguration.set("projects", lists);

        tab = tabbedPane.getSelectedIndex();
        tabbedPane.removeAll();
    }
}