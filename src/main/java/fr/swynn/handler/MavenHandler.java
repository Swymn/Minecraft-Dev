package fr.swynn.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;

public class MavenHandler {
    
    public MavenHandler(String version, String name, String groupId) {
        Model model = this.setupPomXML(groupId, name, version);
        MavenXpp3Writer writer = new MavenXpp3Writer();

        try {
            Files.createDirectory(Paths.get(name));
            writer.write(new FileWriter(name + "/pom.xml"), model);
            this.createArchitectures(groupId, name);
        } catch (IOException e) {
            System.out.println("Error while creating the pom.xml file");
        }
    }

    /**
     * This method is used to setup the pom.xml file
     * 
     * @param groupId The groupId of the project - String
     * @param name The name of the project - String
     * @param version The version of the Spigot - String
     * @return The model of the pom.xml
     */
    private Model setupPomXML(String groupId, String name, String version) {
        Model model = new Model();
        model.setModelVersion("4.0.0");
        model.setGroupId(groupId);
        model.setArtifactId(name);
        model.setVersion("1.0");

        Properties properties = new Properties();
        properties.setProperty("maven.compiler.source", "17");
        properties.setProperty("maven.compiler.target", "17");
        model.setProperties(properties);

        Dependency dependency = new Dependency();
        dependency.setGroupId("org.spigotmc");
        dependency.setArtifactId("spigot-api");
        dependency.setVersion(version + "-R0.1-SNAPSHOT");
        dependency.setScope("provided");
        model.addDependency(dependency);

        return model;
    }

    /**
     * This method is used to write a file
     * 
     * @param path The path of the file - String
     * @param filename The name of the file - String
     * @param filecontent The content of the file - String
     * @return void
     */
    private void writeFile(Path path, String filename, String filecontent) {
        if (Files.exists(path.resolve(filename))) return;

        try {
            File file = new File(Files.createFile(path.resolve(filename)).toString());
            FileWriter writer = new FileWriter(file);

            writer.write(filecontent);

            writer.close();
        } catch(IOException e) {
            System.out.println("Error while creating the file " + filename);
        }
    }

    /**
     * This method is used to create the directories of the project
     * 
     * @param groupId The groupId of the project - String
     * @return The array of the paths of the directories
     */
    private Path[] createDirectories(String groupId, String name) {
        String[] pack = groupId.replace(".", "/").split("/");
        Path[] folders = {
            Paths.get(name, "src", "main", "java"),
            Paths.get(name, "src", "main", "resources"),
            Paths.get(name, "src", "test", "java")
        };

        for (String path : pack) {
            folders[0] = folders[0].resolve(path);
        }

        try {
            for (Path path : folders) {
                if (!Files.exists(path))
                    Files.createDirectories(path);
            }
        } catch (IOException e) {
            System.out.println("Error while creating the directories");
        }

        return folders;
    }

    /**
     * This method is used to create the architectures of the project
     * 
     * @param groupId The groupId of the project - String
     * @param name The name of the project - String
     * @return void
     */
    private void createArchitectures(String groupId, String name) {
        
        Path folders[] = this.createDirectories(groupId, name);

        // Write the main class
        String mainJava = 
            "package " + groupId + ";\r" +
            "\r" + 
            "import org.bukkit.plugin.java.JavaPlugin;\r" +
            "\r" +
            "public class Main extends JavaPlugin {\r" +
            "\r" + 
            "    @Override\r" +
            "    public void onEnable() {\r" +
            "        System.out.println(\"Hello World!\");\r" +
            "    }\r" +
            "\r" +
            "    @Override\r" +
            "    public void onDisable() {\r" +
            "        System.out.println(\"Goodbye World!\");\r" +
            "    }\r" +
            "}"
        ;

        // Write the plugin.yml
        String yaml = 
            "name: \"" + name + "\"\r" +
            "version: 1.0\r" +
            "main: \"" + groupId + ".Main\""
        ;

        this.writeFile(folders[0], "Main.java", mainJava);
        this.writeFile(folders[1], "plugin.yml", yaml);
    }
}
