package astrobit.other;

public final class Time {

    public static double time;
    public static double timeSinceStart;
    public static double delta;
    public static int frames;

    public static int toFrames(double seconds) {
        return (int) (seconds * (1 / delta));
    }

    public static double toSeconds(int frames) {
        return frames / (1 / delta);
    }

    public static long toMilliseconds(double seconds) {
        return (long) (seconds * 1000);
    }

    public static double toSeconds(long milliseconds) {
        return milliseconds * 0.001;
    }
}