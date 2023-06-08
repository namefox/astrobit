package astrobit.physics;

import astrobit.objects.Module;
import astrobit.other.UnitConverter;
import astrobit.other.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class Collider extends Module {

    public interface Collision {
        void activate(Collider other);
    }

    public Rectangle rectangle;
    public final ArrayList<Collision> onCollide = new ArrayList<>();
    public final ArrayList<Collision> onCollideExit = new ArrayList<>();

    @Override
    public void awake() {
        Vector2[] v2s = UnitConverter.unitToPixel(parent.position, parent.size);
        rectangle = new Rectangle((int)v2s[0].x, (int)v2s[0].y, (int)v2s[1].x, (int)v2s[1].y);

        ColliderManager.register(this);
    }

    @Override
    public void update() {
        Vector2[] v2s = UnitConverter.unitToPixel(parent.position, parent.size);

        rectangle.x = (int)v2s[0].x;
        rectangle.y = (int)v2s[0].y;
        rectangle.width = (int)v2s[1].x;
        rectangle.height = (int)v2s[1].y;
    }

    public void onCollide(Collision collision) {
        onCollide.add(collision);
    }

    public void onCollideExit(Collision collision) {
        onCollideExit.add(collision);
    }

    public void collide(Collider other) {
        for (int i = 0; i < onCollide.size(); i++) {
            onCollide.get(i).activate(other);
        }
    }

    public void collideExit(Collider other) {
        for (int i = 0; i < onCollideExit.size(); i++) {
            onCollideExit.get(i).activate(other);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.white);
        g.draw(rectangle);
    }

    @Override
    public void unload() {
        ColliderManager.unregister(this);
    }
}