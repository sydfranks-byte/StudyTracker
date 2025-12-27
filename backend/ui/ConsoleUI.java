package ui;

import java.util.Scanner;

/**
 * Handles all console input/output operations.
 * Separates presentation logic from business logic.
 */
public class ConsoleUI {
    private final Scanner scanner;

    public ConsoleUI(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner cannot be null");
        }
        this.scanner = scanner;
    }

    public void showWelcome(double totalMinutes) {
        printSeparator();
        System.out.println("Welcome to the Study Time Tracker");
        printSeparator();
        System.out.println("It takes 10,000 hours to master a skill.");
        System.out.println("Are you ready to start working?");
        printBlankLine();
        System.out.println("Your previous study time: " + formatTime(totalMinutes));
        printBlankLine();
    }

    public void showMenu() {
        System.out.println("---------");
        System.out.println(" M E N U ");
        System.out.println("---------");

        for (MenuOption option : MenuOption.values()) {
            if (option != MenuOption.INVALID) {
                System.out.println(option.getValue() + " - " + option.getDescription());
            }
        }
    }

    public MenuOption readMenuChoice() {
        System.out.print("Choose an option (1-4): ");

        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number (1-4): ");
            scanner.next();
        }

        return MenuOption.fromChoice(scanner.nextInt());
    }

    public void showSessionStarted() {
        System.out.println("Starting session...");
    }

    public void showSessionEnded(double sessionMinutes, double totalMinutes) {
        System.out.println("Ending session...");
        System.out.println("You studied for " + formatTime(sessionMinutes));
        System.out.println("Your updated total is " + formatTime(totalMinutes));
    }

    public void showGoalReached() {
        System.out.println("Goal reached: 10,000 hours completed!");
        System.out.println("No further sessions can be started.");
    }

    public void showProgress(double totalMinutes, double progressPercent) {
        System.out.println("Total study time tracked: " + formatTime(totalMinutes));
        System.out.printf("Progress: %.2f%% of 10,000 hours%n", progressPercent);
    }

    public void showSaved(String fileName) {
        System.out.println("Progress saved to " + fileName + ". Keep going!");
    }

    public void showInfo(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void printBlankLine() {
        System.out.println();
    }

    private void printSeparator() {
        System.out.println("==================================");
    }

    private String formatTime(double totalMinutes) {
        int hours = (int) (totalMinutes / 60.0);
        int minutes = (int) (totalMinutes % 60.0);

        if (hours == 0 && minutes == 0 && totalMinutes > 0.0) {
            return "under 1 minute";
        }

        String hourLabel = hours == 1 ? "hour" : "hours";
        String minuteLabel = minutes == 1 ? "minute" : "minutes";

        return hours + " " + hourLabel + " and " + minutes + " " + minuteLabel;
    }
}
