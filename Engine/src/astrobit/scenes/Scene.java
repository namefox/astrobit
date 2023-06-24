package astrobit.scenes;

import astrobit.objects.GameObject;
import astrobit.ui.Canvas;

import java.util.HashMap;
import java.util.Map;

public class Scene implements java.io.Serializable {

    public final int index;
    public final HashMap<String, GameObject> gameObjects;
    public Canvas canvas;

    public Scene(int index, GameObject... objects) {
        this.index = index;
        gameObjects = new HashMap<>();
        for (GameObject object: objects) {
            object.init(this);
            if (object instanceof Canvas obj) {
                canvas = obj;
                continue;
            }

            gameObjects.put(object.name, object);
        }
    }

    public void awake() {
        for (Map.Entry<String, GameObject> entry: gameObjects.entrySet()) {
            entry.getValue().init(this);
            entry.getValue().awake();
        }
    }

    public GameObject find(String name) {
        return gameObjects.get(name);
    }

    public void add(GameObject... objects) {
        for (GameObject object: objects) {
            object.init(this);
            gameObjects.put(object.name, object);
        }
    }

    public void remove(GameObject... objects) {
        for (GameObject object: objects) {
            object.init(null);
            gameObjects.remove(object.name, object);
        }
    }

    public void unload() {
        for (Map.Entry<String, GameObject> entry: gameObjects.entrySet()) {
            entry.getValue().unload();
        }
    }
}