package com.astrobit;

import astrobit.animation.Animation;
import astrobit.init.Astrobit;
import astrobit.objects.GameObject;
import astrobit.physics.Collider;
import astrobit.physics.Rigidbody;
import astrobit.render.ShapeRenderer;
import astrobit.other.Vector2;
import astrobit.render.SpriteRenderer;
import astrobit.scenes.Scene;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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