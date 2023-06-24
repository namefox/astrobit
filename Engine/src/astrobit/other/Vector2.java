package astrobit.other;

public class Vector2 implements java.io.Serializable {

    public double x, y;

    public static final Vector2 zero = new Vector2(0, 0);
    public static final Vector2 one = new Vector2(1, 1);

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(double v) {
        return new Vector2(x + v, y + v);
    }

    public Vector2 add(Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    public Vector2 subtract(double v) {
        return new Vector2(x - v, y - v);
    }

    public Vector2 subtract(Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }

    public Vector2 multiply(double v) {
        return new Vector2(x * v, y * v);
    }

    public Vector2 multiply(Vector2 v) {
        return new Vector2(x * v.x, y * v.y);
    }

    public Vector2 divide(double v) {
        return new Vector2(x / v, y / v);
    }

    public Vector2 divide(Vector2 v) {
        return new Vector2(x / v.x, y / v.y);
    }

    @Override
    public String toString() {
        return '(' + String.valueOf(x) + ',' + y + ')';
    }
}