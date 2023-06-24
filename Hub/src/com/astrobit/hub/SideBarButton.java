package com.astrobit.hub;

import javax.swing.*;
import java.awt.*;

public class SideBarButton extends JButton {

    private boolean selected;
    private final Color background;

    public SideBarButton(String text) {
        super(text);
        setPreferredSize(new Dimension(100, 50));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        setSize(getPreferredSize());

        background = new Color(0x3C3F41).darker();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;

        if (selected)
            setBackground(background.darker());
        else
            setBackground(background);
    }
}