package astrobit.ui;

import astrobit.other.Vector2;

import java.awt.*;

public class Text extends UIObject {

    public String text;
    public Color color;
    public Font font;

    public Text(String name, Vector2 position, String text, Font font) {
        super(name, position, Vector2.zero);
        this.text = text;
        this.font = font;
        this.color = Color.white;
    }

    @Override
    public void tick(Graphics2D g) {
        g.setColor(color);
        g.setFont(font);

        g.drawString(text, (int) position.x, (int) position.y);
    }
}