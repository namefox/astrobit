package astrobit.init;

import astrobit.input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public final class GameWindow {

    public static JFrame frame;

    public static void start(String t, int w, int h, int s) {
        frame = new JFrame(t);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                Insets insets = frame.getInsets();
                GamePanel.WIDTH = frame.getWidth() - (insets.left + insets.right);
                GamePanel.HEIGHT = frame.getHeight() - (insets.top + insets.bottom);
            }
        });

        Input in = new Input();
        frame.addKeyListener(in);
        frame.addMouseListener(in);
        frame.addMouseMotionListener(in);
        frame.addMouseWheelListener(in);

        frame.add(new GamePanel(w, h, s));
        frame.pack();
        frame.setResizable(false);
        frame.setIconImage(Assets.sprite("icon.png", false));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static Insets insets() {
        if (frame != null) return frame.getInsets();
        return new Insets(0, 0, 0, 0);
    }
}