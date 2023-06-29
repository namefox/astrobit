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
    private int sortBy;
    private String tab;

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
            String path = chooser.getSelectedFile().getAbsolutePath();

            ArrayList<Project> list = lists.getOrDefault(null, new ArrayList<>());

            boolean notFound = true;
            for (Project project: list) {
                if (project.path().equals(path)) {
                    project.modify();
                    notFound = false;
                    break;
                }
            }

            if (notFound)
                list.add(new Project(path, (String) HubConfiguration.get("latestEditorVersion", "Unknown")));

            lists.put(null, list);
            HubConfiguration.set("projects", lists);

            Files.write(Paths.get(System.getProperty("user.home") + File.separator + ".astrobit" + File.separator + "lastOpenProject"), path.getBytes());

            String p = (String) HubConfiguration.get("installPath", System.getProperty("user.home") + File.separator + ".astrobit" + File.separator + "editors");
            p += File.separator + HubConfiguration.get("latestEditorVersion", "Unknown");

            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(p + File.separator + "bin" + File.separator + "editor.exe"));

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

        int min = 10;
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

            if (entry.getKey() == null) continue;

            if (tab.getComponents().length < min) {
                for (int i = 0; i < min - tab.getComponents().length; i++)
                    tab.add(new ProjectComponent(null));
            }

            tabbedPane.addTab(entry.getKey(), new JScrollPane(tab));
        }

        if (all.getComponents().length < min) {
            for (int i = 0; i < min - all.getComponents().length; i++)
                all.add(new ProjectComponent(null));
        }

        int t = 0;
        int i = 0;
        for (Map.Entry<String, ArrayList<Project>> entry: sort.entrySet()) {
            i++;

            if (entry.getKey() == null) continue;

            if (entry.getKey().equals(tab)) {
                t = i;
                break;
            }
        }

        tabbedPane.setSelectedIndex(t);

        tabbedPane.revalidate();
        tabbedPane.repaint();
    }

    @Override
    public void reset() {
        HubConfiguration.set("projects", lists);

        tab = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        tabbedPane.removeAll();
    }
}