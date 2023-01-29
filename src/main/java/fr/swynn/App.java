package fr.swynn;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import fr.swynn.creators.MavenProjectCreator;
import fr.swynn.creators.MinecraftServerCreator;
import fr.swynn.utils.Logger;
import fr.swynn.utils.SpigotDownloader;
import fr.swynn.creators.DirectoryCreator;
import fr.swynn.utils.State;

enum Type {
    PLUGIN,
    SERVER
}

public class App {

    private static final String[] names = {
        "Super Tools",
        "Ender Expansions",
        "Block Wizard",
        "Redstone Revolution",
        "The Lucky",
        "Block Mod",
        "Mob Battles",
        "The Miners",
        "Handbook The",
        "Dimension Door The Adventurer's",
        "Guild The",
        "Crafting Companion",
        "The Furnace",
        "Master The",
        "Ore Scanner",
        "The Mob",
        "Grinder The",
        "Magic Chest",
        "The Builders",
        "Helper The Farmer's",
        "Friend The",
        "Mob Spawner",
        "Enhancer The",
        "Enchanted Sword",
        "The Ender",
        "Dragon Slayer",
        "The TNT",
        "Master The",
        "Mob Disguise",
        "The Mob",
        "Trainer The",
        "Enchanted Bow",
        "The Mob",
        "Arena The",
        "Mob Hunter",
        "The Teleporter",
        "The Mob",
        "Trader The",
        "NPC Creator",
        "The Mob",
        "Spawn Egg",
        "Generator The",
        "Mob Enchantment",
        "The Mob Boss Mod",
    };
    public static Scanner scanner;

    public static void main(String[] args) {
        if (args.length < 2) {
            Logger.log(State.WARNING,"Usage: java -jar minecraft_dev.jar <version> <type> [name]");
            System.exit(-1);
        }

        SpigotDownloader.createJSONIfNotExists();
        scanner = new Scanner(System.in);

        Type type = getType(args[1]);
        String version = args[0];
        String name = args.length > 2 ? args[2] : names[new Random().nextInt(names.length)];

        name = name.replace(" ", "-").toLowerCase();

        if (DirectoryCreator.exists(name)) {
            Logger.log(State.ERROR,"The project '" + name + "' already exists.");
            System.exit(-1);
        }

        System.out.println("[Version]: \t" + version);
        System.out.println("[Type]: \t" + type);
        System.out.println("[Name]: \t" + name);

        switch (type) {
            case PLUGIN -> {
                String groupId = Logger.input("Enter the groupId of your plugin (ex: com.example):");
                String wantServer = Logger.input("Do you want to download the server too? (y/n)");

                new MavenProjectCreator(version, name, groupId, Logger.isPositiveAnswer(wantServer));
                if (Logger.isPositiveAnswer(wantServer)) {
                    MinecraftServerCreator server = new MinecraftServerCreator(Paths.get(name).resolve("server").toString(), version);
                    server.create();

                    Logger.log(State.SUCCESS, "Server created in " + server.getPath() + " folder.");
                    Logger.log(State.INFO, "You can now go to the server folder using the commande 'cd " + server.getPath() + "', or juste by using your file explorer tool.");
                    Logger.log(State.INFO, "Don't forget to use the good version of Java, to run your server.");
                    Logger.log(State.INFO, "You can now launch your server by typing the commande 'sh start.sh' (for Linux & MacOS users)\n or 'start.bat' (for Windows users).");
                }

                Logger.log(State.SUCCESS, "Project created in " + name + " folder.");
                Logger.log(State.INFO, "You can now go to the project folder using the commande 'cd " + name + "', or juste by using your file explorer tool.");
                Logger.log(State.INFO, "Don't forget to use the good version of Java, by default, the project is configured to use Java 17. \n(you can change it in the pom.xml file, in the properties section, change both maven.compiler.source and maven.compiler.target with the version you want).");
                Logger.log(State.INFO, "If there is a problem with the specified version of spigot inside the pom.xml file, you can change it by changing the version in the dependency section \n(see https://hub.spigotmc.org/nexus/content/repositories/snapshots/org/spigotmc/spigot-api/ to see all the spigot versions available).");
                Logger.log(State.INFO, "You can now run 'mvn clean install' to build your plugin and the server. \n(you need to have maven installed on your computer.");
                Logger.log(State.INFO, "You can also run 'mvn clean install -DskipTests' to build your plugin and the server without running the tests. \n(you need to have maven installed on your computer).");
            }
            case SERVER -> {
                MinecraftServerCreator server = new MinecraftServerCreator(name, version);
                server.create();
                Logger.log(State.SUCCESS, "Server created in " + server.getPath() + " folder.");
                Logger.log(State.INFO, "You can now go to the server folder using the commande 'cd " + server.getPath() + "', or juste by using your file explorer tool.");
                Logger.log(State.INFO, "Don't forget to use the good version of Java, to run your server.");
                Logger.log(State.INFO, "You can now launch your server by typing the commande 'sh start.sh' (for Linux & MacOS users)\n or 'start.bat' (for Windows users).");
            }
            default -> Logger.log(State.ERROR, "Type must be 'plugin' or 'server', not '" + type + "'");
        }
        
        scanner.close();
    }

    public static Type getType(String type) {
        try {
            return Type.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Type must be 'plugin' or 'server', not '" + type + "'");
            System.exit(-1);
            return null;
        }
    }

    public static boolean isWindowsOS() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}