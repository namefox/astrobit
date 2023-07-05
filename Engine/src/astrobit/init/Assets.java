package astrobit.init;

import astrobit.scenes.Scene;
import astrobit.sound.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

public final class Assets {

    public static String root = "/";
    private final static HashMap<String, Object> cache = new HashMap<>();

    public static BufferedImage sprite(String path) {return sprite(path, true);}
    public static String text(String path) {return text(path, true);}
    public static Scene scene(String path) {return scene(path, true);}
    public static Sound sound(String path) {return sound(path, true);}
    public static Font font(String path) {return font(path, true);}

    public static BufferedImage sprite(String path, boolean canCache) {
        if (cache.containsKey(path)) return (BufferedImage) cache.get(path);

        try {
            InputStream in = Assets.class.getResourceAsStream(root + path);
            if (in == null) throw new IOException("Resource does not exist");

            BufferedImage img = ImageIO.read(in);
            if (canCache) cache.put(path, img);

            in.close();
            return img;
        } catch (IOException e) {
            Astrobit.error(e);
        }

        return null;
    }

    public static String text(String path, boolean canCache) {
        if (cache.containsKey(path)) return (String) cache.get(path);

        try {
            InputStream in = Assets.class.getResourceAsStream(root + path);
            if (in == null) throw new IOException("Resource does not exist");

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (builder.length() > 0) builder.append('\n');
                builder.append(line);
            }

            reader.close();

            if (canCache) cache.put(path, builder.toString());
            return builder.toString();
        } catch (IOException e) {
            Astrobit.error(e);
        }

        return null;
    }

    public static Scene scene(String path, boolean canCache) {
        if (cache.containsKey(path)) return (Scene) cache.get(path);

        try {
            InputStream in = Assets.class.getResourceAsStream(root + path);
            if (in == null) throw new IOException("Resource does not exist");

            ObjectInputStream obj = new ObjectInputStream(in);

            Scene scene = (Scene) obj.readObject();
            if (canCache) cache.put(path, scene);

            obj.close();
            return scene;
        } catch (IOException | ClassNotFoundException e) {
            Astrobit.error(e);
        }

        return null;
    }

    public static Sound sound(String path, boolean canCache) {
        if (cache.containsKey(path)) return (Sound) cache.get(path);

        try {
            Sound sound = new Sound(Assets.class.getResourceAsStream(root + path));
            if (canCache) cache.put(path, sound);
            return sound;
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            Astrobit.error(e);
        }

        return null;
    }

    public static Font font(String path, boolean canCache) {
        if (cache.containsKey(path)) return (Font) cache.get(path);

        try {
            InputStream in = Assets.class.getResourceAsStream(root + path);
            if (in == null) throw new IOException("Path does not exist.");

            Font font = Font.createFont(Font.TRUETYPE_FONT, in);
            if (canCache) cache.put(path, font);

            in.close();

            return font;
        } catch (IOException | FontFormatException e) {
            Astrobit.error(e);
        }

        return null;
    }
}