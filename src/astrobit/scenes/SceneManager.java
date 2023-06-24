package astrobit.scenes;

import astrobit.init.Assets;
import astrobit.init.Astrobit;
import astrobit.objects.GameObject;
import astrobit.objects.Module;
import astrobit.other.Time;

import java.awt.*;
import java.util.Map;

public class SceneManager {

    public static Scene active;

    public static void start() {
        load(0);
    }

    public static void load(int index) {
        if (active != null) active.unload();

        active = Assets.scene("objects/" + index + ".scn", false);
        if (active == null) Astrobit.error(new NullPointerException("Scene does not exist"));

        Time.time = 0;
        active.awake();
    }

    @SuppressWarnings({"unchecked","ForLoopReplaceableByForEach"})
    public static void render(Graphics2D g) {
        Map.Entry<String, GameObject>[] set = active.gameObjects.entrySet().toArray(new Map.Entry[0]);
        for (int i = 0; i < set.length; i++) {
            if (!set[i].getValue().enabled) continue;
            Map.Entry<Class<? extends Module>, Module>[] modules = set[i].getValue().modules.entrySet().toArray(new Map.Entry[0]);
            for (int j = 0; j < modules.length; j++) {
                if (!modules[j].getValue().enabled) continue;
                modules[j].getValue().render(g);
            }
        }

        if (active.canvas != null)
            active.canvas.tick(g);
    }

    @SuppressWarnings({"unchecked","ForLoopReplaceableByForEach"})
    public static void update() {
        Map.Entry<String, GameObject>[] set = active.gameObjects.entrySet().toArray(new Map.Entry[0]);
        for (int i = 0; i < set.length; i++) {
            if (!set[i].getValue().enabled) continue;
            Map.Entry<Class<? extends Module>, Module>[] modules = set[i].getValue().modules.entrySet().toArray(new Map.Entry[0]);
            for (int j = 0; j < modules.length; j++) {
                if (!modules[j].getValue().enabled) continue;
                modules[j].getValue().update();
            }
        }
    }
}