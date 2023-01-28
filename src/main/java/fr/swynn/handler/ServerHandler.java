package fr.swynn.handler;

import fr.swynn.manager.FileManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerHandler {

    private final String version;
    private final Path path;

    public ServerHandler(String name, String version) {
        this(name, version, "server");
    }

    public ServerHandler(String name, String version, String path) {
        this(name, version, Paths.get(path == null ? "" : path));
    }

    public ServerHandler(String name, String version, Path path) {
        this.version = version;
        this.path = Paths.get(name).resolve(path);
    }

    // Create the server folder
    // Download spigot
    // Create the EULA.txt files with the write content
    // maybe start the server once ?

    /**
     * This method create the server folder, download the spigot version and create the EULA.txt file
     */
    public void create() {
        this.createServerFolder();
        this.downloadSpigot();
        this.createEULA();
        this.createStartScript();
    }

    /**
     * Create the server folder
     */
    private void createServerFolder() {
        if (Files.exists(path)) return;
        try {
            Files.createDirectory(path);
        } catch (Exception e) {
            System.out.println("Error while creating the server folder");
        }
    }


    /**
     * Download the spigot version
     */
    private void downloadSpigot() {
        new SpigotHandler(version, "./" + path.toString());
    }

    /**
     * Create the EULA.txt file
     */
    private void createEULA() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        String formattedDate = formatter.format(date);
        String eulaContent =
                "#By changing the setting below to TRUE you are indicating your agreement to our EULA (https://aka.ms/MinecraftEULA).\n" +
                "#" + formattedDate + "\n" +
                "eula=true"
        ;
        FileManager eula = new FileManager("eula.txt", Paths.get("./").resolve(path));
        eula.write(eulaContent);
    }

    /**
     * Create the start script
     */
    private void createStartScript() {
        String shellLine = "#!/bin/sh";
        String oldVersion = SpigotHandler.getOldVersion(version);
        String javaFile = oldVersion == null ? "spigot-" + version + ".jar" : "spigot-" + oldVersion + ".jar";

        String startScriptContent =
                shellLine + "\n" +
                "java -Xms1G -Xmx2G -jar " + javaFile + "\n"
        ;
        FileManager startScript = new FileManager("start.sh", Paths.get("./").resolve(path));
        startScript.write(startScriptContent);
    }
}
