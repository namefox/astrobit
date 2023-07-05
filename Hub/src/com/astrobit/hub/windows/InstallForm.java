package com.astrobit.hub.windows;

import com.astrobit.shared.Debug;

import javax.swing.*;
import java.awt.*;

public class InstallForm extends JDialog {

    public InstallForm() {
        setTitle("Install");
        setLayout(new BorderLayout());
        setSize(640, 480);
        setIconImage(Main.instance.getIconImage());

        JPanel center = new JPanel();

        JPanel bottom = new JPanel();

        JButton ok = new JButton("Continue");
        ok.setBackground(new Color(0x3c83c5));
        ok.addActionListener(e -> Debug.log("TODO"));
        bottom.add(ok);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        bottom.add(cancel);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}