package com.astrobit.shared;

import javax.swing.*;

public final class Debug {

    public static void log(Object... prints) {
        for (Object object : prints) {
            System.out.print("[+] " + object + ' ');
        }
        System.out.println();
    }

    public static void logWarning(Object... prints) {
        for (Object object : prints) {
            System.out.print("[*] " + object + ' ');
        }
        System.out.println();
    }

    public static void logError(Object... prints) {
        for (Object object : prints) {
            System.err.print("[-] " + object + ' ');
        }
        System.out.println();
    }

    public static void error(Exception e) {
        JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        System.exit(1);
    }
}