package fr.swynn.handler;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import fr.swynn.utils.Command;

public class SpigotHandler {

    private static final File FILE = new File("data/versions.json");
    private static final ArrayList<String> versions = new ArrayList<>() {{
        add("1.4.6");
        add("1.4.7");
        add("1.5.1");
        add("1.5.2");
        add("1.6.2");
        add("1.6.4");
        add("1.7.2");
        add("1.7.5");
        add("1.7.9");
        add("1.8");
        add("1.8.3");
        add("1.8.8");
        add("1.9");
        add("1.9.2");
        add("1.9.4");
        add("1.10");
        add("1.10.2");
        add("1.11");
        add("1.11.2");
        add("1.12");
        add("1.12.1");
        add("1.12.2");
        add("1.13");
        add("1.13.1");
        add("1.13.2");
        add("1.14");
        add("1.14.1");
        add("1.14.2");
        add("1.14.3");
        add("1.14.4");
        add("1.15");
        add("1.15.1");
        add("1.15.2");
        add("1.16.1");
        add("1.16.2");
        add("1.16.3");
        add("1.16.4");
        add("1.16.5");
    }};
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
    private final String version;

    /**
     * Download a Spigot version
     *
     * @param version The version to download - String
     * @param path    The path to storage the server - String
     */
    public SpigotHandler(String version, String path) {
        this(version, Paths.get(path));
    }

    /**
     * Download a Spigot version
     *
     * @param version The version to download - String
     * @param path    The path to storage the server - Path
     */
    public SpigotHandler(String version, Path path) {
        // TODO: The Spigot file isn't working.
        this.version = version;
        String url = getURL(version);
        String PREFIX = "[INFO] : ";

        System.out.println(PREFIX + "Téléchargement du server Spigot " + version + "...");
        download(url);
        System.out.println(PREFIX + "Téléchargement du server Spigot " + version + " réussi");

        Command mv = new Command("mv " + getBukkitName(version) + " " + path + "/" + getName(version));
        System.out.println(PREFIX + "Configuration du server Spigot" + (mv.isSuccessful() ? " réussi" : " échoué"));
    }

    private void download(String url) {
        try (FileOutputStream fos = new FileOutputStream(getBukkitName(version))) {
            URL website = new URL(url);
            InputStream in = website.openStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (MalformedURLException e) {
            System.out.println("Error while downloading the spigot server, the URL is malformed.");
            System.exit(-2);
        } catch (IOException e) {
            System.out.println("Error while downloading the spigot server, this version may not exist.");
            System.exit(-2);
        }
    }

    /**
     * Get the version of the name of the spigot file
     *
     * @param version The version of the server
     * @return The name of the spigot file
     */
    public static String getName(String version) {
        return "spigot-" + version + ".jar";
    }

    /**
     * Get the version of the name of the spigot file from Bukkit
     *
     * @param version - The version of the server
     * @return The name of the spigot file from Bukkit
     */
    public static String getBukkitName(String version) {
        return "spigot-" + getVersion(version);
    }

    /**
     * Get the URL to download the spigot file
     *
     * @param version - The version of the server
     * @return The URL of the spigot file to download
     */
    public static String getURL(String version) {
        return "https://" + (versions.contains(version) ? "cdn" : "download") + ".getbukkit.org/spigot/" + getBukkitName(version);
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
    public static String getVersion(String version) {
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
                return version + ".jar";
            }
        }
        return version + ".jar";
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
