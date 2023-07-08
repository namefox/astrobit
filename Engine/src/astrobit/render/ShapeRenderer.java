package astrobit.render;

import astrobit.objects.Module;
import astrobit.other.UnitConverter;
import astrobit.other.Vector2;

import java.awt.*;

public class ShapeRenderer extends Module {

    public enum Shape {
        RECT,
        OVAL,
    }

    public Color color;
    public Shape shape;

    public ShapeRenderer(Color color, Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);

        Vector2[] vec2s = UnitConverter.unitToPixel(parent.position, parent.size);
        Vector2 position = vec2s[0];
        Vector2 size = vec2s[1];

        switch (shape) {
            case OVAL:
                g.fillOval((int)position.x, (int)position.y, (int)size.x, (int)size.y);
                break;
            case RECT:
                g.fillRect((int)position.x, (int)position.y, (int)size.x, (int)size.y);
                break;
        }
    }
}