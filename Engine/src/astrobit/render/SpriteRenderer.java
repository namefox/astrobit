package astrobit.render;

import astrobit.init.Assets;
import astrobit.objects.Module;
import astrobit.other.UnitConverter;
import astrobit.other.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteRenderer extends Module {

    public Color color;
    public String image;
    public transient BufferedImage img;

    public SpriteRenderer(Color color, String image) {
        this.color = color;
        this.image = image;
        this.img = Assets.sprite(this.image);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);

        Vector2[] vec2s = UnitConverter.unitToPixel(parent.position, parent.size);
        Vector2 position = vec2s[0];
        Vector2 size = vec2s[1];

        if (img == null) img = tint(Assets.sprite(image), color);
        g.drawImage(img, (int)position.x, (int)position.y, (int)size.x, (int)size.y, null);
    }

    public void setImage(String image) {
        this.image = image;
        this.img = tint(Assets.sprite(image), color);
    }

    public void setColor(Color color) {
        this.color = color;
        this.img = tint(this.img, color);
    }

    private BufferedImage tint(BufferedImage sprite, Color color) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        BufferedImage tintedSprite = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics2D graphics = tintedSprite.createGraphics();
        graphics.drawImage(sprite, 0, 0, null);
        graphics.dispose();

        for (int i = 0; i < tintedSprite.getWidth(); i++) {
            for (int j = 0; j < tintedSprite.getHeight(); j++) {
                int ax = tintedSprite.getColorModel().getAlpha(tintedSprite.getRaster().getDataElements(i, j, null));
                int rx = tintedSprite.getColorModel().getRed(tintedSprite.getRaster().getDataElements(i, j, null));
                int gx = tintedSprite.getColorModel().getGreen(tintedSprite.getRaster().getDataElements(i, j, null));
                int bx = tintedSprite.getColorModel().getBlue(tintedSprite.getRaster().getDataElements(i, j, null));
                rx *= r;
                gx *= g;
                bx *= b;
                ax *= a;
                tintedSprite.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
            }
        }
        return tintedSprite;
    }
}