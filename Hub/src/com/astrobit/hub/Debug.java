package com.astrobit.hub;

import javax.swing.*;

public final class Debug {

    public static void error(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, e.getMessage(), e.getClass().getTypeName(), JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}