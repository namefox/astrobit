package astrobit.other;

import static astrobit.init.GamePanel.*;

public final class UnitConverter {

    public static Vector2[] unitToPixel(Vector2 pos, Vector2 sz) {
        Vector2 size = new Vector2(
            sz.x * scale,
            sz.y * scale
        );

        Vector2 position = new Vector2(
            (width * scale) / 2d - size.x / 2 + pos.x * scale,
            (height * scale) / 2d - size.y / 2 - pos.y * scale
        );

        return new Vector2[]{position, size};
    }

    public static Vector2[] pixelToUnit(Vector2 pos, Vector2 sz) {
        Vector2 size = new Vector2(
            sz.x / scale,
            sz.y / scale
        );

        Vector2 position = new Vector2(
            (pos.x - ((width * scale) / 2d - (size.x * scale) / 2d)) / scale,
            -(pos.y - ((height * scale) / 2d - (size.y * scale) / 2d)) / scale
        );

        return new Vector2[]{position, size};
    }
}
