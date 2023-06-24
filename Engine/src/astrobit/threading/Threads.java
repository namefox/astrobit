package astrobit.threading;

public final class Threads {

    public static void branch(ThreadOperation operation) {
        new Thread(operation::start).start();
    }
}