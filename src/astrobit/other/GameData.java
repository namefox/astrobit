package astrobit.other;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

public final class GameData {

    private static HashMap<String, Object> pairs;
    private static File file;

    @SuppressWarnings("unchecked")
    public static void init(String companyName, String name) throws IOException {
        file = new File(System.getProperty("user.home") + File.separator + ".astrobit" + File.separator + companyName + File.separator + name);
        if (!file.getParentFile().exists()) Files.createDirectories(file.getParentFile().toPath());

        if (!file.exists()) {
            Files.createFile(file.toPath());
            pairs = new HashMap<>();
        } else {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                pairs = (HashMap<String, Object>) in.readObject();
            } catch (Exception e) {
                pairs = new HashMap<>();
            }
        }
    }

    public static void save() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(pairs);
        }
    }
}