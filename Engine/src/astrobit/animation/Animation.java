package astrobit.animation;

import astrobit.init.Assets;
import astrobit.init.Astrobit;
import astrobit.objects.Module;
import astrobit.other.Time;
import astrobit.render.SpriteRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Animation extends Module {

    private ArrayList<String> frames;
    private SpriteRenderer renderer;
    private final double timeBetweenFrames;
    private double timer;
    private int frame;

    public Animation(int fps, String... frames) {
        this.frames = new ArrayList<>();
        timeBetweenFrames = 1.0 / fps;
        this.frames.addAll(Arrays.asList(frames));
    }

    @Override
    public void awake() {
        renderer = (SpriteRenderer) parent.module(SpriteRenderer.class);
        if (renderer == null) Astrobit.error(new IllegalAccessException("Animation requires a SpriteRenderer"));
    }

    @Override
    public void update() {
        timer += Time.delta;
        if (timer >= timeBetweenFrames) {
            timer = 0;

            frame++;
            if (frame >= frames.size()) frame = 0;

            renderer.setImage(frames.get(frame));
        }
    }
}