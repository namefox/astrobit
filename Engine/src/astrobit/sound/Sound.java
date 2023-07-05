package astrobit.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class Sound {

    private final Clip clip;

    public Sound(InputStream in) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream ais = AudioSystem.getAudioInputStream(in);
        clip = AudioSystem.getClip();
        clip.open(ais);
    }

    public void play() {
        if (clip.getFramePosition() >= clip.getFrameLength())
            clip.setFramePosition(0);
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public void loop() {
        loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void loop(int count) {
        if (clip.getFramePosition() >= clip.getFrameLength())
            clip.setFramePosition(0);
        clip.loop(count);
    }
}