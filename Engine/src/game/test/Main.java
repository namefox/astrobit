package game.test;

import astrobit.animation.Animation;
import astrobit.init.Assets;
import astrobit.init.Astrobit;
import astrobit.objects.GameObject;
import astrobit.physics.Collider;
import astrobit.physics.Rigidbody;
import astrobit.render.ShapeRenderer;
import astrobit.other.Vector2;
import astrobit.render.SpriteRenderer;
import astrobit.scenes.Scene;
import astrobit.scenes.SceneManager;
import astrobit.ui.Button;
import astrobit.ui.Canvas;
import astrobit.ui.Text;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Main {

    public static void main(String[] args) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("res/objects/0.scn"))) {
            outputStream.writeObject(new Scene(
                    0,
                    new GameObject(
                            "Test",
                            Vector2.zero,
                            new Vector2(1, 1),
                            new SpriteRenderer(Color.white, "sprite_0.png"),
                            new Animation(2, "sprite_0.png", "sprite_1.png", "sprite_2.png", "sprite_3.png", "sprite_4.png"),
                            new Rigidbody(),
                            new Collider(),
                            new Player()
                    ),
                    new GameObject(
                            "Ground",
                            new Vector2(0, -5),
                            new Vector2(16, 5),
                            new ShapeRenderer(new Color(0x008000), ShapeRenderer.Shape.RECT),
                            new Collider()
                    ),
                    new Canvas(
                            "Canvas",
                            new Text("TestText", Vector2.one.multiply(100).add(new Vector2(0, 100)), "Test", new Font("Arial", Font.PLAIN, 100)),
                            new Button("Test2", Vector2.one.multiply(100).add(new Vector2(0, 200)), new Vector2(400, 200), "Test", new Font("Arial", Font.PLAIN, 50), (Runnable & Serializable) () -> SceneManager.load(1))
                    )
            ));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Launch Astrobit Game
        Astrobit.window("Astrobit", "Example", 16, 9, 80);
    }
}