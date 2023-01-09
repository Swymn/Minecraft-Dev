package fr.swynn;

import java.util.Random;
import java.util.Scanner;

import fr.swynn.handler.MavenHandler;
import fr.swynn.handler.SpigotHandler;

enum Type {
    PLUGIN,
    SERVER;
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

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java -jar SpigotDownloader.jar <version> <plugin/server> <name(optional)>");
            System.exit(-1);
        }

        SpigotHandler.createJSONIfNotExists();

        Type type = getType(args[1]);
        String version = args[0];
        String name = args.length > 2 ? args[2] : names[new Random().nextInt(names.length)];

        System.out.println("[Version]: \t" + version);
        System.out.println("[Type]: \t" + type);
        System.out.println("[Name]: \t" + name);

        switch(type) {
            case PLUGIN:
                System.out.println("Enter the groupId of your plugin (ex: fr.swynn):");
                Scanner scanner = new Scanner(System.in);
                String groupId = "";
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.isEmpty() || line.isBlank()) {
                        System.out.println("groupId cannot be empty");
                        continue;
                    }

                    groupId = line;
                    break;
                }

                scanner.close();

                new MavenHandler(version, name, groupId);
                break;
            
            default:
                System.out.println("Type not implemented yet");
                break;
        }
        

        // new SpigotHandler(version);
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