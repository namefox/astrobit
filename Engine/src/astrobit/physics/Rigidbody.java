package astrobit.physics;

import astrobit.objects.Module;
import astrobit.other.Time;
import astrobit.other.Vector2;

public class Rigidbody extends Module {

    private Vector2 velocity;
    public Vector2 acceleration;
    public Vector2 deceleration;
    public double gravity;
    private double g;
    public double TERMINAL_VELOCITY;
    private boolean collided;

    public Rigidbody() {
        this(1);
    }

    public Rigidbody(double gravity) {
        this.acceleration = new Vector2(0, 0);
        this.velocity = Vector2.zero;
        this.deceleration = Vector2.zero;
        this.gravity = gravity;
        this.TERMINAL_VELOCITY = gravity*0.5;
    }

    @Override
    public void awake() {
        Collider collider = (Collider) parent.module(Collider.class);
        if (collider != null) {
            collider.onCollide(other -> {
                g = 0;
                velocity = Vector2.zero;
                collided = true;
            });

            collider.onCollideExit(other -> collided = false);
        }
    }

    @Override
    public void update() {
        g -= gravity;
        if (g < -TERMINAL_VELOCITY) g = -TERMINAL_VELOCITY;
        if (collided) g = 0;

        deceleration = deceleration.multiply(0.5);
        acceleration = acceleration.subtract(deceleration);

        velocity = velocity.add(acceleration.multiply(Time.delta));
        velocity = velocity.add(new Vector2(0, g * Time.delta));
        parent.position = parent.position.add(velocity);
    }

    public void addForce(Vector2 force, boolean impulse) {
        if (!impulse) {
            velocity = velocity.add(force);
        } else {
            acceleration = acceleration.add(force);
            deceleration = deceleration.add(force);
        }
    }
}