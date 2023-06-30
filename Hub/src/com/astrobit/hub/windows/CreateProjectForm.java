package com.astrobit.hub.windows;

import com.astrobit.hub.Project;
import com.astrobit.shared.Configuration;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateProjectForm extends JDialog {

    private JTextField name, path, group;

    public CreateProjectForm() {
        setTitle("Create Project");
        setResizable(false);
        setSize(640, 480);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(Main.instance);
        setIconImage(Main.instance.getIconImage());

        JPanel left = new JPanel();
        left.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JPanel center = new JPanel();
        center.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JPanel right = new JPanel();
        right.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        JPanel bottom = new JPanel();

        JButton proceed = new JButton("Continue");
        proceed.addActionListener(e -> proceed());
        proceed.setBackground(new Color(0x3c83c5));
        proceed.setForeground(Color.white);
        bottom.add(proceed);

        JButton exit = new JButton("Cancel");
        exit.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        bottom.add(exit);

        setupLeft(left);
        setupRight(right);
        setupCenter(center);

        setLayout(new BorderLayout());
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);
        add(center);
        setVisible(true);
    }

    private void proceed() {
        @SuppressWarnings("unchecked")
        HashMap<String, ArrayList<Project>> lists = (HashMap<String, ArrayList<Project>>) Configuration.get("projects", new HashMap<>());;

        String g = group.getText();
        if (g.isBlank()) g = null;

        ArrayList<Project> list = lists.get(g);
        if (list == null)
            list = new ArrayList<>();

        list.add(new Project(path.getText() + File.separator + name.getText(), (String) Configuration.get("latestEditorVersion", "Unknown")));
        lists.put(g, list);

        Configuration.set("projects", lists);

        Main.resetPage();

        setVisible(false);
        dispose();
    }

    private JTextField field(String text) {
        JTextField field = new JTextField(text);
        field.setMaximumSize(new Dimension(field.getPreferredSize().width * 5, 22));
        return field;
    }

    private JSeparator separator() {
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(separator.getPreferredSize().width, 22));
        return separator;
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setMaximumSize(new Dimension(label.getPreferredSize().width, 22));
        label.setMinimumSize(new Dimension(label.getPreferredSize().width, 22));
        return label;
    }

    private void setupLeft(JPanel left) {
        left.add(label("Name: "));
        left.add(label("Path: "));
        left.add(label("Group: "));
    }

    private void setupRight(JPanel right) {
        right.add(separator());

        JButton browse = new JButton("Browse");
        browse.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (chooser.showOpenDialog(this) != 0) return;
            path.setText(chooser.getSelectedFile().getAbsolutePath());
        });
        right.add(browse);

        right.add(separator());
    }

    private void setupCenter(JPanel center) {
        name = field("New Project");
        path = field((String) Configuration.get("projectPath", System.getProperty("user.home")));

        group = field("");
        group.setToolTipText("Leave blank for no group");

        center.add(name);
        center.add(path);
        center.add(group);
    }
}