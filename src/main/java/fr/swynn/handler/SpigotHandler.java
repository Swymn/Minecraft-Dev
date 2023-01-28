package fr.swynn.handler;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.gson.Gson;

import fr.swynn.utils.Command;

public class SpigotHandler {

    private final String URL = "cdn.getbukkit.org/spigot/";
    private static final File FILE = new File("data/versions.json");
    private static final HashMap<String, String> OLD_VERSIONS = new HashMap<>() {
        {
            put("1.4.6", "1.4.6-R0.4-SNAPSHOT.jar");
            put("1.4.7", "1.4.7-R1.1-SNAPSHOT.jar");
            put("1.5.1", "1.5.1-R0.1-SNAPSHOT.jar");
            put("1.5.2", "1.5.2-R1.1-SNAPSHOT.jar");
            put("1.6.2", "1.6.2-R1.1-SNAPSHOT.jar");
            put("1.6.4", "1.6.4-R2.1-SNAPSHOT.jar");
            put("1.7.2", "1.7.2-R0.4-SNAPSHOT-1339.jar");
            put("1.7.5", "1.7.5-R0.1-SNAPSHOT-1387.jar");
            put("1.7.8", "1.7.8-R0.1-SNAPSHOT.jar");
            put("1.7.9", "1.7.9-R0.2-SNAPSHOT.jar");
            put("1.7.10", "1.7.10-SNAPSHOT-b1657.jar");
            put("1.8", "1.8-R0.1-SNAPSHOT-latest.jar");
            put("1.8.3", "1.8.3-R0.1-SNAPSHOT-latest.jar");
            put("1.8.4", "1.8.4-R0.1-SNAPSHOT-latest.jar");
            put("1.8.5", "1.8.5-R0.1-SNAPSHOT-latest.jar");
            put("1.8.6", "1.8.6-R0.1-SNAPSHOT-latest.jar");
            put("1.8.7", "1.8.7-R0.1-SNAPSHOT-latest.jar");
            put("1.8.8", "1.8.8-R0.1-SNAPSHOT-latest.jar");
            put("1.9", "1.9-R0.1-SNAPSHOT-latest.jar");
            put("1.9.2", "1.9.2-R0.1-SNAPSHOT-latest.jar");
            put("1.9.4", "1.9.4-R0.1-SNAPSHOT-latest.jar");
            put("1.10", "1.10-R0.1-SNAPSHOT-latest.jar");
            put("1.10.2", "1.10.2-R0.1-SNAPSHOT-latest.jar");
        }
    };

    /**
     * Download a Spigot version
     *
     * @param version The version to download - String
     * @param path    The path to storage the server - String
     */
    public SpigotHandler(String version, String path) {
        // TODO: The Spigot file isn't working.
        String name = "spigot-" + version + ".jar";
        String url = this.URL + name;
        String oldVersion = getOldVersion(version);

        if (oldVersion != null) {
            name = oldVersion;
            url = this.URL + oldVersion;
        }

        System.out.println(url);

        Command install = new Command("wget " + url);
        Command mv = new Command("mv " + name + " " + path);

        String PREFIX = "[INFO] : ";
        System.out.println(PREFIX + "Téléchargement de Spigot " + version + (install.isSuccessful() ? " réussi" : " échoué"));
        System.out.println(PREFIX + "Configuration du server Spigot" + (mv.isSuccessful() ? " réussi" : " échoué"));
    }

    /**
     * Create a JSON file with all old versions
     */
    private static void createJSON() {

        Version[] versions = new Version[OLD_VERSIONS.size()];
        for (int i = 0; i < OLD_VERSIONS.size(); i++) {
            String version = (String) OLD_VERSIONS.keySet().toArray()[i];
            versions[i] = (new Version(version, OLD_VERSIONS.get(version)));
        }

        try {
            FileWriter writer = new FileWriter("data/versions.json");

            new Gson().toJson(versions, writer);

            writer.close();
        } catch (Exception e) {
            System.out.println("Impossible de créer le fichier versions.json");
        }
    }

    /**
     * Create a JSON file if it doesn't exist
     */
    public static void createJSONIfNotExists() {
        Path path = Paths.get("data");
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (Exception e) {
                System.out.println("Impossible de créer le dossier data");
            }
        }
        if (!Files.exists(Paths.get("data/versions.json"))) {
            createJSON();
        }
    }

    /**
     * Get the old version of a Spigot version
     *
     * @param version The version to get - String
     * @return The old version - String
     */
    public static String getOldVersion(String version) {
        if (FILE.exists()) {
            try {
                FileReader reader = new FileReader(FILE);
                Version[] versions = new Gson().fromJson(reader, Version[].class);
                for (Version v : versions) {
                    if (v.version.equals(version)) {
                        return v.name;
                    }
                }

                reader.close();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private static class Version {
        private String version;
        private String name;

        public Version(String version, String name) {
            this.version = version;
            this.name = name;
        }
    }
}
