package fr.swynn.utils;

public enum State {
    INFO(Color.BLUE, "INFO"),
    WARNING(Color.YELLOW, "WARNING"),
    ERROR(Color.RED, "ERROR"),
    SUCCESS(Color.GREEN, "SUCCESS");

    private final Color color;
    private final String prefix;

    State(Color color, String prefix) {
        this.color = color;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public Color getColor() {
        return color;
    }

    public enum Color {
        BLUE("36"),
        YELLOW("33"),
        RED("31"),
        GREEN("32"),
        RESET("0");

        private final String color;

        Color(String color) {
            this.color = color;
        }

        public String getStringColor() {
            return color;
        }
    }
}
