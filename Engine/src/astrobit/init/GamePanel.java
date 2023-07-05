package astrobit.init;

import astrobit.input.Input;
import astrobit.other.Time;
import astrobit.physics.ColliderManager;
import astrobit.scenes.SceneManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements ActionListener {

    public static final Color BACKGROUND_COLOR = new Color(0x1d1d1d);
    public static int width, height, scale;
    public static final int TARGET_FPS = 60;
    private final Timer timer;
    private long last;

    public static int WIDTH, HEIGHT;

    public GamePanel(int w, int h, int s) {
        width = w;
        height = h;
        scale = s;

        WIDTH = w*s;
        HEIGHT = h*s;

        setPreferredSize(new Dimension(w * s, h * s));

        last = System.currentTimeMillis();
        timer = new Timer(1000 / (TARGET_FPS * 2), this);
        timer.start();
    }

    @Override
    public void paint(Graphics g1) {
        try {
            BufferedImage image = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) image.getGraphics();

            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(BACKGROUND_COLOR);
            g.fillRect(0, 0, width * scale, height * scale);

            SceneManager.render(g);

            g.dispose();

            int w = closestInteger(WIDTH, width);
            int h = closestInteger(HEIGHT, height);

            g1.setColor(BACKGROUND_COLOR.darker());
            g1.fillRect(0, 0, WIDTH, HEIGHT);
            g1.drawImage(image, WIDTH / 2 - w / 2, HEIGHT / 2 - h / 2, w, h, null);
        } catch (Exception e) {
            timer.stop();
            Astrobit.error(e);
        }
    }

    private int closestInteger(int a, int b) {
        int c1 = a - (a % b);
        int c2 = (a + b) - (a % b);

        if (a - c1 > c2 - a)
            return c2;
        else
            return c1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double delta = (System.currentTimeMillis() - last) * 0.001;

        Time.frames++;
        Time.delta = delta;
        Time.time += delta;
        Time.timeSinceStart += delta;

        try {
            ColliderManager.update();
            SceneManager.update();
            repaint();
        } catch (Exception e1) {
            timer.stop();
            Astrobit.error(e1);
        }

        last = System.currentTimeMillis();
    }
}
