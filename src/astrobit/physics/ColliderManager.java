package astrobit.physics;

import java.util.ArrayList;

public final class ColliderManager {

    public final static ArrayList<Collider> colliders = new ArrayList<>();

    public static void register(Collider c) {
        colliders.add(c);
    }

    public static void unregister(Collider c) {
        colliders.remove(c);
    }

    public static void update() {
        for (int i = 0; i < colliders.size(); i++) {
            Collider c1 = colliders.get(i);
            for (int j = 0; j < colliders.size(); j++) {
                Collider c2 = colliders.get(j);
                if (c2 == c1) continue;

                if (c1.rectangle.intersects(c2.rectangle)) {
                    c1.collide(c2);
                    c2.collide(c1);
                } else {
                    c1.collideExit(c2);
                    c2.collideExit(c1);
                }
            }
        }
    }
}