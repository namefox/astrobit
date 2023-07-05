package astrobit.objects;

import astrobit.other.Vector2;
import astrobit.scenes.Scene;

import java.util.HashMap;
import java.util.Map;

public class GameObject implements java.io.Serializable {

    public String name;
    public transient Scene scene;
    public Vector2 position;
    public Vector2 size;
    public final HashMap<String, GameObject> children;
    public final HashMap<Class<? extends Module>, Module> modules;
    public boolean enabled;

    public GameObject(String name, Vector2 position, Vector2 size, Module... components) {
        this.name = name;
        this.position = position;
        this.size = size;
        this.enabled = true;

        this.children = new HashMap<>();
        this.modules = new HashMap<>();
        for (Module module: components) {
            module.init(this);
            modules.put(module.getClass(), module);
        }
    }

    public void awake() {
        for (Map.Entry<Class<? extends Module>, Module> entry : modules.entrySet()) {
            entry.getValue().init(this);
            entry.getValue().awake();
        }
    }

    public void unload() {
        for (Map.Entry<Class<? extends Module>, Module> entry : modules.entrySet()) {
            entry.getValue().unload();
        }
    }

    public void setActive(boolean active) {
        enabled = active;
        for (Map.Entry<Class<? extends Module>, Module> entry: modules.entrySet()) {
            entry.getValue().setActive(active);
        }
    }

    public Module module(Class<? extends Module> module) {
        return modules.get(module);
    }

    public void removeModule(Class<? extends Module> module) {
        modules.remove(module);
    }

    public void addModule(Module module) {
        module.init(this);
        modules.put(module.getClass(), module);
    }

    public GameObject child(String name) {
        return children.get(name);
    }

    public void removeChild(String name) {
        children.remove(name);
    }

    public void addChild(GameObject child) {
        child.init(scene);
        children.put(child.name, child);
    }

    public void init(Scene scene) {
        this.scene = scene;
    }
}