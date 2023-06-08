package com.astrobit;

import astrobit.init.Assets;
import astrobit.input.Code;
import astrobit.input.Input;
import astrobit.objects.Module;
import astrobit.other.Time;
import astrobit.other.Vector2;
import astrobit.physics.Collider;
import astrobit.physics.Rigidbody;

public class Player extends Module {

    private static final double jumpTime = .1;
    private static final double jumpForce = 1.0;

    private double time;
    private Rigidbody rb;

    @Override
    public void awake() {
        ((Collider)parent.module(Collider.class)).onCollide(other -> time = 0);
        rb = (Rigidbody) parent.module(Rigidbody.class);
    }

    @Override
    public void update() {
        double speed = 5.0;

        if (Input.keyPressed(Code.LEFT))
            parent.position = parent.position.subtract(new Vector2(speed * Time.delta, 0));
        if (Input.keyPressed(Code.RIGHT))
            parent.position = parent.position.add(new Vector2(speed * Time.delta, 0));

        if (Input.keyPressed(Code.UP) && time < jumpTime) {
            time += Time.delta;
            rb.addForce(new Vector2(0, jumpForce), true);
            Assets.sound("example-sound.wav").play();
        }
    }
}
