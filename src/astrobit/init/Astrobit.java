package astrobit.init;

import astrobit.other.Debug;
import astrobit.other.GameData;
import astrobit.scenes.SceneManager;

import javax.swing.*;
import java.io.IOException;

public class Astrobit {

    private static void init(String t, String c) {
        try {
            Debug.init(t);
            GameData.init(c, t);
        } catch (IOException e) {
            error(e);
        }
        SceneManager.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Debug.flush();
                GameData.save();
            } catch (IOException e) {
                Astrobit.error(e);
            }
        }));
    }

    public static void window(String c, String t, int w, int h, int s) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        init(t, c);

        GameWindow.start(t, w, h, s);
    }

    public static GamePanel panel(String c, String t, int w, int h, int s) {
        init(t, c);

        // Can be used inside any frame
        return new GamePanel(w, h, s);
    }

    public static void error(Exception e) {
        e.printStackTrace();

        Debug.err(e);
        JOptionPane.showMessageDialog(null, String.format("%s\nLogs located in %s", e.toString(), Debug.path()), "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}