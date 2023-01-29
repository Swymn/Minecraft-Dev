package fr.swynn;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import fr.swynn.creators.MavenProjectCreator;
import fr.swynn.creators.MinecraftServerCreator;
import fr.swynn.utils.Logger;
import fr.swynn.utils.SpigotDownloader;
import fr.swynn.creators.DirectoryCreator;

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
            System.out.println("Usage: java -jar SpigotDownloader.jar <version> <plugin/server> <name(optional)>");
            System.exit(-1);
        }

        SpigotDownloader.createJSONIfNotExists();
        scanner = new Scanner(System.in);

        Type type = getType(args[1]);
        String version = args[0];
        String name = args.length > 2 ? args[2] : names[new Random().nextInt(names.length)];

        if (DirectoryCreator.exists(name)) {
            System.out.println("The project '" + name + "' already exists.");
            System.exit(-1);
        }

        System.out.println("[Version]: \t" + version);
        System.out.println("[Type]: \t" + type);
        System.out.println("[Name]: \t" + name);

        switch (type) {
            case PLUGIN -> {
                String groupId = Logger.input("Enter the groupId of your plugin (ex: com.example):");
                new MavenProjectCreator(version, name, groupId);

                String wantServer = Logger.input("Do you want to download the server too? (y/n)");
                if (!(Logger.POS_ANSWER.contains(wantServer.toLowerCase()))) return;
                MinecraftServerCreator server = new MinecraftServerCreator(name, version);
                server.create();
            }
            case SERVER -> System.out.println("Type not implemented yet.");
            default -> System.out.println("Type not found.");
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
}