package com.astrobit.hub.pages;

import com.astrobit.hub.Install;
import com.astrobit.hub.Sorts;
import com.astrobit.hub.components.InstallComponent;
import com.astrobit.hub.components.Page;
import com.astrobit.hub.windows.InstallForm;
import com.astrobit.hub.windows.Main;
import com.astrobit.shared.Configuration;
import com.astrobit.shared.Debug;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class InstallsPage extends Page {

    private ArrayList<Install> installs;
    private final JPanel panel;

    public InstallsPage() {
        setLayout(new BorderLayout());

        JPanel north = new JPanel();
        north.setLayout(new BorderLayout());

        JPanel east = new JPanel();

        JButton install = new JButton("Install");
        install.addActionListener(e -> new InstallForm());
        install.setBackground(new Color(0x3c83c5));
        install.setForeground(Color.white);
        east.add(install);

        JButton add = new JButton("Add");
        add.addActionListener(e -> add());
        east.add(add);

        north.add(east, BorderLayout.EAST);
        add(north, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(new JScrollPane(panel));
    }

    private void add() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Installs", "hub"));
        if (chooser.showOpenDialog(Main.instance) != 0) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()))) {
            Install install = (Install) in.readObject();
            installs.add(install);

            panel.add(new InstallComponent(install));
            panel.revalidate();
            panel.repaint();
        } catch (IOException | ClassNotFoundException e) {
            Debug.error(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onShow() {
        installs = (ArrayList<Install>) Configuration.get("installs", new ArrayList<>());
        installs.sort(Sorts.editorVersionInstall());

        for (Install install: installs)
            panel.add(new InstallComponent(install));

        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void reset() {
        Configuration.set("installs", installs);
        panel.removeAll();
    }
}