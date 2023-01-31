package fr.swynn.creators;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import fr.swynn.utils.Logger;
import fr.swynn.utils.State;
import org.apache.maven.model.*;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.Xpp3Dom;

public class MavenProjectCreator {

    public MavenProjectCreator(String version, String name, String groupId, boolean isServerLinked) {
        Model model = this.setupPomXML(groupId, name, version, isServerLinked);
        MavenXpp3Writer writer = new MavenXpp3Writer();

        try {
            Files.createDirectory(Paths.get(name));
            writer.write(new FileWriter(name + "/pom.xml"), model);
            this.createArchitectures(groupId, name);
        } catch (IOException e) {
            Logger.log(State.ERROR,"Error while creating the pom.xml file");
        }
    }

    /**
     * This method is used to set up the pom.xml file
     * 
     * @param groupId The groupId of the project - String
     * @param name The name of the project - String
     * @param version The version of the Spigot - String
     * @return The model of the pom.xml
     */
    private Model setupPomXML(String groupId, String name, String version, boolean isServerLinked) {
        Model model = new Model();
        model.setModelVersion("4.0.0");
        model.setGroupId(groupId);
        model.setArtifactId(name);
        model.setVersion("1.0");

        Properties properties = new Properties();
        properties.setProperty("maven.compiler.source", "17");
        properties.setProperty("maven.compiler.target", "17");
        model.setProperties(properties);

        Repository repository = new Repository();
        repository.setId("spigot-repo");
        repository.setUrl("https://hub.spigotmc.org/nexus/content/repositories/snapshots/");
        model.addRepository(repository);

        Dependency dependency = new Dependency();
        dependency.setGroupId("org.spigotmc");
        dependency.setArtifactId("spigot-api");
        dependency.setVersion(version + "-R0.1-SNAPSHOT");
        dependency.setScope("provided");
        model.addDependency(dependency);

        if (isServerLinked) {
            Build build = new Build();
            build.setFinalName(name);

            Plugin assemblyPlugin = new Plugin();
            assemblyPlugin.setGroupId("org.apache.maven.plugins");
            assemblyPlugin.setArtifactId("maven-assembly-plugin");
            assemblyPlugin.setVersion("3.3.0");

            Xpp3Dom config = new Xpp3Dom("configuration");
            Xpp3Dom descriptorRefs = new Xpp3Dom("descriptorRefs");
            Xpp3Dom descriptorRef = new Xpp3Dom("descriptorRef");

            descriptorRef.setValue("jar-with-dependencies");
            descriptorRefs.addChild(descriptorRef);
            config.addChild(descriptorRefs);

            Xpp3Dom outputDirectory = new Xpp3Dom("outputDirectory");
            outputDirectory.setValue("./server/plugins/");
            config.addChild(outputDirectory);

            Xpp3Dom archive = new Xpp3Dom("archive");
            Xpp3Dom manifest = new Xpp3Dom("manifest");
            Xpp3Dom addClasspath = new Xpp3Dom("addClasspath");
            Xpp3Dom mainClass = new Xpp3Dom("mainClass");
            addClasspath.setValue("true");
            mainClass.setValue(groupId + ".Main");
            manifest.addChild(addClasspath);
            manifest.addChild(mainClass);
            archive.addChild(manifest);
            config.addChild(archive);

            assemblyPlugin.setConfiguration(config);

            PluginExecution execution = new PluginExecution();
            execution.setId("make-assembly");
            execution.setPhase("package");
            execution.addGoal("single");

            assemblyPlugin.addExecution(execution);

            build.addPlugin(assemblyPlugin);
            model.setBuild(build);
        }

        return model;
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
        };

        for (String path : pack) {
            folders[0] = folders[0].resolve(path);
        }

        for (Path path : folders) {
            try {
                DirectoryCreator.create(Paths.get("./").resolve(path));
            } catch (IllegalArgumentException e) {
                Logger.log(State.ERROR, e.getMessage());
            }
        }

        return folders;
    }

    /**
     * This method is used to create the architectures of the project
     * 
     * @param groupId The groupId of the project - String
     * @param name The name of the project - String
     */
    private void createArchitectures(String groupId, String name) {
        
        Path[] folders = this.createDirectories(groupId, name);

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

        try {
            FileCreator file = new FileCreator("Main.java", folders[0]);
            file.write(mainJava);

            file = new FileCreator("plugin.yml", folders[1]);
            file.write(yaml);
        } catch (IllegalArgumentException e) {
            Logger.log(State.ERROR, e.getMessage());
        }
    }
}
