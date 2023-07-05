package astrobit.other;

import astrobit.init.Astrobit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public final class Debug {

    private static File file;
    private static FileWriter handle;

    public static void init(String name) throws IOException {
        file = Files.createTempFile("astrobit-" + name, ".log").toFile();
        handle = new FileWriter(file, true);
    }

    public static void log(Object... prints) {
        for (Object object : prints) {
            System.out.print("[+] " + object + ' ');

            try {
                handle.write("[+] " + object + ' ');
            } catch (IOException e) {
                Astrobit.error(e);
            }
        }

        System.out.println();
        try {
            handle.write('\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void warn(Object... prints) {
        for (Object object : prints) {
            System.out.print("[*] " + object + ' ');

            try {
                handle.write("[*] " + object + ' ');
            } catch (IOException e) {
                Astrobit.error(e);
            }
        }

        System.out.println();
        try {
            handle.write('\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void err(Object... prints) {
        for (Object object : prints) {
            System.err.print("[-] " + object + ' ');

            try {
                handle.write("[-] " + object + ' ');
            } catch (IOException e) {
                Astrobit.error(e);
            }
        }

        System.out.println();
        try {
            handle.write('\n');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void flush() throws IOException {
        handle.flush();
        handle.close();
    }

    public static String path() {
        return file.getAbsolutePath();
    }
}