package astrobit.ui;

import astrobit.input.Code;
import astrobit.input.Input;
import astrobit.other.Vector2;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Button extends UIObject {

    public String text;
    public Font font;
    public Runnable event;

    public Color normal = Color.RED;
    public Color hover = normal.darker();
    public Color press = hover.darker();
    public Color textColor = Color.white;

    private boolean activated = false;

    public Button(String name, Vector2 position, Vector2 size, String text, Font font, Runnable click) {
        super(name, position, size);
        this.text = text;
        this.font = font;
        this.event = click;
    }

    @Override
    public void tick(Graphics2D g) {
        // --- UPDATE --- //
        Rectangle mouse = new Rectangle((int)Input.mousePositionPixel.x + 10, (int)Input.mousePositionPixel.y + 10, 10, 16);
        Rectangle bounds = new Rectangle((int)position.x, (int)position.y, (int)size.x, (int)size.y);

        Color activeColor = normal;
        if (mouse.intersects(bounds)) {
            if (Input.mousePressed(Code.MOUSE_LEFT)) {
                activeColor = press;

                if (!activated) {
                    event.run();
                    activated = true;
                }
            } else {
                activeColor = hover;
                activated = false;
            }
        }

        // --- RENDER --- //
        g.setColor(Color.red);
        g.fill(mouse);

        g.setColor(activeColor);
        g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 50, 50);

        g.setFont(font);
        g.setColor(textColor);

        FontMetrics fm = g.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(text, g);
        int x = (int)((position.x - r.getWidth()) / 2);
        int y = (int)((position.y - r.getHeight()) / 2 + fm.getAscent());
        g.drawString(text, x + (int)(position.x + size.x) / 2, y + (int)(position.y + size.y) / 2);
    }
}