package com.astrobit.hub.components;

import com.astrobit.hub.Install;
import com.astrobit.hub.Project;
import com.astrobit.hub.windows.Main;
import com.astrobit.shared.Configuration;
import com.astrobit.shared.Debug;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class InstallComponent extends JButton {

    public InstallComponent(Install install) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                getBorder()
        ));
        setLayout(new BorderLayout());

        JPanel grid = new JPanel();
        grid.setOpaque(true);
        grid.setBackground(new Color(0x00000000, true));
        grid.setLayout(new GridLayout(2, 2));

        grid.add(infoLabel("Version"));
        grid.add(infoLabel("Path"));
        grid.add(label(install.version()));
        grid.add(label(install.path()));

        add(grid);

        JLabel icon = new JLabel();
        try {
            InputStream in = getClass().getResourceAsStream("/icon64x64.png");
            if (in != null)
                icon.setIcon(new ImageIcon(ImageIO.read(in)));
        } catch (IOException e) {
            Debug.error(e);
        }
        add(icon, BorderLayout.WEST);

        JPanel extensions = new JPanel();
        extensions.setOpaque(true);
        extensions.setBackground(grid.getBackground());

        for (String extension : install.extensions()) {
            JButton button = new JButton(extension);
            extensions.add(button);
        }

        add(extensions, BorderLayout.SOUTH);
    }

    private JLabel infoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(label.getForeground().getRGB() - 0x30000000, true));
        return label;
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(label.getFont().getSize() + 5f).deriveFont(Font.BOLD));
        return label;
    }
}