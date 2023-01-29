package fr.swynn.creators;

import fr.swynn.utils.Logger;
import fr.swynn.utils.SpigotDownloader;
import fr.swynn.utils.State;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MinecraftServerCreator {

    private final String version;
    private final Path path;

    public MinecraftServerCreator(String name, String version) {
        this(name, version, (String) null);
    }

    public MinecraftServerCreator(String name, String version, String path) {
        this(name, version, Paths.get(path == null ? "" : path));
    }

    public MinecraftServerCreator(String name, String version, Path path) {
        this.version = version;
        this.path = Paths.get(name).resolve(path);
    }

    /**
     * This methode is used to get the path of the server
     */
    public Path getPath() {
        return path;
    }

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
            Logger.log(State.ERROR,"Error while creating the server folder");
        }
    }


    /**
     * Download the spigot version
     */
    private void downloadSpigot() {
        new SpigotDownloader(version, "./" + path.toString());
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
        FileCreator eula = new FileCreator("eula.txt", Paths.get("./").resolve(path));
        eula.write(eulaContent);
    }

    /**
     * Create the start script
     */
    private void createStartScript() {
        String shellLine = "#!/bin/sh";
        String javaFile = SpigotDownloader.getName(version);

        String startScriptContent =
                shellLine + "\n" +
                "java -Xms1G -Xmx2G -jar " + javaFile + "\n"
        ;

        String fileName = System.getProperty("os.name").toLowerCase().contains("windows") ? "start.bat" : "start.sh";
        FileCreator startScript = new FileCreator(fileName, Paths.get("./").resolve(path));
        startScript.write(startScriptContent);
    }
}
