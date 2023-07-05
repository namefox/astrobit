package astrobit.ui;

import astrobit.objects.GameObject;
import astrobit.other.Vector2;

import java.awt.*;

public abstract class UIObject extends GameObject {

    public UIObject(String name, Vector2 position, Vector2 size) {
        super(name, position, size);
    }

    public abstract void tick(Graphics2D g);
}