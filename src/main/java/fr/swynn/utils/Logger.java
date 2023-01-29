package fr.swynn.utils;

import fr.swynn.App;

import java.util.ArrayList;

public class Logger {

    public static final ArrayList<String> POS_ANSWER = new ArrayList<>() {
        {
            add("y");
            add("yes");
            add("oui");
            add("o");
            add("true");
        }
    };

    public static void log(State state, String message) {
        System.out.println("\033[" + state.getColor().getStringColor() + "m[" + state.getPrefix() + "]:\033[" + State.Color.RESET.getStringColor() + "m " + message);
    }

    public static String input(String question) {
        return input(question, false);
    }

    public static String input(String question, boolean canBeEmpty) {
        System.out.print(question + "\n-> ");
        String input = "";

        while (App.scanner.hasNextLine()) {
            String line = App.scanner.nextLine();
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
