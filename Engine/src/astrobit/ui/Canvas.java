package astrobit.ui;

import astrobit.init.GamePanel;
import astrobit.objects.GameObject;
import astrobit.other.Vector2;

import java.awt.*;
import java.util.Map;

public class Canvas extends UIObject {

    public Canvas(String name, UIObject... children) {
        super(name, Vector2.zero, new Vector2(GamePanel.width, GamePanel.height));
        for (GameObject child: children) {
            addChild(child);
        }
    }

    @Override
    @SuppressWarnings({"unchecked", "ForLoopReplaceableByForEach"})
    public void tick(Graphics2D g) {
        Map.Entry<String, GameObject>[] set = children.entrySet().toArray(new Map.Entry[0]);
        for (int i = 0; i < set.length; i++) {
            if (!set[i].getValue().enabled) continue;
            ((UIObject)set[i].getValue()).tick(g);
        }
    }

    @Override
    public void addChild(GameObject child) {
        if (!(child instanceof UIObject)) return;
        super.addChild(child);
    }
}