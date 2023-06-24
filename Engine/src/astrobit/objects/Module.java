package astrobit.objects;

import java.awt.*;

public abstract class Module implements java.io.Serializable {

    public transient GameObject parent;
    public boolean enabled = true;

    public void init(GameObject parent) {
        this.parent = parent;
    }

    public void update() {}
    public void render(Graphics2D g) {}
    public void awake() {}
    public void unload() {}
    public void onEnable() {}
    public void onDisable() {}

    public void setActive(boolean active) {
        enabled = active;
        if (active)
            onEnable();
        else
            onDisable();
    }
}