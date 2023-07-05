package com.astrobit.shared;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

public final class Configuration {

    private static HashMap<String, Object> config;
    private static File file;

    @SuppressWarnings("unchecked")
    public static void setup(String name) {
        file = new File(System.getProperty("user.home") + File.separator + ".astrobit" + File.separator + name);
        if (!file.getParentFile().exists()) {
            try {
                Files.createDirectories(file.getParentFile().toPath());
            } catch (IOException e) {
                Debug.error(e);
            }
        }

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            config = (HashMap<String, Object>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            config = new HashMap<>();
        }
    }

    public static Object get(String name, Object defaultValue) {
        return config.getOrDefault(name, defaultValue);
    }

    public static void set(String name, Object value) {
        config.put(name, value);

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(config);
        } catch (IOException e) {
            Debug.error(e);
        }
    }
}