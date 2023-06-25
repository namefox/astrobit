package com.astrobit.hub.windows;

import javax.swing.*;

public class CreateProjectForm extends JDialog {

    public CreateProjectForm() {
        setTitle("Create Project");
        setSize(640, 480);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(Main.instance);
        setIconImage(Main.instance.getIconImage());
        setVisible(true);

        // TODO
    }
}