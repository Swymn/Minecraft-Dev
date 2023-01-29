package fr.swynn;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import fr.swynn.handler.MavenHandler;
import fr.swynn.handler.ServerHandler;
import fr.swynn.handler.SpigotHandler;
import fr.swynn.manager.DirectoryManager;

enum Type {
    PLUGIN,
    SERVER
}

public class App {

    private static Scanner scanner;
    private static final ArrayList<String> posAnswer = new ArrayList<>() {
        {
            add("y");
            add("yes");
            add("oui");
            add("o");
            add("true");
        }
    };
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
        if (args.length < 2) {
            System.out.println("Usage: java -jar SpigotDownloader.jar <version> <plugin/server> <name(optional)>");
            System.exit(-1);
        }

        SpigotHandler.createJSONIfNotExists();
        scanner = new Scanner(System.in);

        Type type = getType(args[1]);
        String version = args[0];
        String name = args.length > 2 ? args[2] : names[new Random().nextInt(names.length)];

        if (DirectoryManager.exists(name)) {
            System.out.println("The project '" + name + "' already exists.");
            System.exit(-1);
        }

        System.out.println("[Version]: \t" + version);
        System.out.println("[Type]: \t" + type);
        System.out.println("[Name]: \t" + name);

        switch (type) {
            case PLUGIN -> {
                String groupId = ask("Enter the groupId of your plugin (ex: com.example):");
                new MavenHandler(version, name, groupId);

                String wantServer = ask("Do you want to download the server too? (y/n)");
                if (!(posAnswer.contains(wantServer.toLowerCase()))) return;
                ServerHandler server = new ServerHandler(name, version);
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

    public static String ask(String question) {
        return ask(question, false);
    }

    public static String ask(String question, boolean canBeEmpty) {
        System.out.print(question + "\n-> ");
        String input = "";
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!(canBeEmpty)) {
                if (line.isEmpty() || line.isBlank()) {
                    System.out.print("->");
                    continue;
                }
            }

            input = line;
            break;
        }

        return input.equals("") ? null : input;
    }
}