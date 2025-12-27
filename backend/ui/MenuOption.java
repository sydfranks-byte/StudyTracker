package ui;

/**
 * Type-safe menu options enum.
 * Maps user input to application actions.
 */
public enum MenuOption {
    START_SESSION(1, "Start a study session"),
    END_SESSION(2, "End study session"),
    VIEW_TOTAL(3, "View total study time"),
    EXIT(4, "Exit"),
    INVALID(-1, "Invalid option");

    private final int value;
    private final String description;

    MenuOption(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static MenuOption fromChoice(int choice) {
        for (MenuOption option : values()) {
            if (option.value == choice) {
                return option;
            }
        }
        return INVALID;
    }

    public static int getMinValue() {
        return 1;
    }

    public static int getMaxValue() {
        return 4;
    }
}
