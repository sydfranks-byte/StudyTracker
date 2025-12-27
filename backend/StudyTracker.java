import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class StudyTracker {
    // total minutes stored as text in a file
    private static final String SAVE_FILE = "study_data.txt";
    private static final double GOAL_HOURS = 10000.0;
    private static final double GOAL_MINUTES = GOAL_HOURS * 60.0;

    public static void main(String[] args) throws IOException {
        Scanner scnr = new Scanner(System.in);
        // session state 
        boolean running = true;
        long startTimeMs = 0;
        boolean sessionActive = false;

        // time tracked in minutes
        double timeTrackedMin = loadPreviousTotalMinutes(); // in minutes
        double sessionTimeMin = 0.0; // in minutes

        printStartup(timeTrackedMin);

        while (running == true) {
            printMenu();
            int choice = readMenuChoice(scnr);

            switch (choice) {
                case 1: // start session
                    if (timeTrackedMin >= GOAL_MINUTES) {
                        System.out.println("🎉🎉🎉🎉🎉🎉🎉🎉🎉");
                        System.out.println("Goal reached: 10,000 hours completed!");
                        System.out.println("No further sessions can be started.");
                    } else if (sessionActive) {
                        System.out.println("Session already active. End it first.");
                    } else {
                        startTimeMs = System.currentTimeMillis();
                        sessionActive = true;
                        System.out.println("Starting session...");
                    }
                    break;
                case 2: // end session
                    if (!sessionActive) {
                        System.out.println("No session to end.");
                    } else {
                        long endTimeMs = System.currentTimeMillis();
                        long sessionMs = endTimeMs - startTimeMs;

                        sessionTimeMin = sessionMs / 60000.0;
                        timeTrackedMin += sessionTimeMin;
                        if (timeTrackedMin > GOAL_MINUTES) {
                            timeTrackedMin = GOAL_MINUTES;
                        }

                        sessionActive = false;
                        startTimeMs = 0;

                        int hours = (int)sessionTimeMin / 60;
                        int minutes = (int)sessionTimeMin % 60;
                        System.out.println("Ending session...");
                        System.out.println("You studied for " + formatHoursMinutes(sessionTimeMin));
                        System.out.println("Your updated total is " + formatHoursMinutes(timeTrackedMin));
                    }
                    break;
                case 3: // view total
                    System.out.println("Total study time tracked: " + formatHoursMinutes(timeTrackedMin));
                    double progressPercent = (timeTrackedMin / GOAL_MINUTES) * 100.0;
                    System.out.printf("Progress: %.2f%% of 10,000 hours\n", progressPercent);
                    break;
                case 4: // exit and save
                    if (sessionActive) {
                        System.out.println("You have an active session. Please end before exiting.");
                    } else {
                        saveTotalMinutes(timeTrackedMin);
                        System.out.println("Saved total to " + SAVE_FILE + ". You're " + sessionTimeMin + " minutes closer to mastery!");
                        running = false;
                    }
                    break;
                default:
                    System.out.println("Please enter a valid number (1-4).");
                    break;
            }
            System.out.println();
        }
        scnr.close();
    }

    private static double loadPreviousTotalMinutes() throws IOException {
        File file = new File(SAVE_FILE);

        if (!file.exists()) {
            return 0.0;
        }

        Scanner fileScanner = new Scanner(file);

        double previousTotal = 0.0;
        if (fileScanner.hasNextDouble()) {
            previousTotal = fileScanner.nextDouble();
        }

        fileScanner.close();
        return previousTotal;
    }

    public static void saveTotalMinutes(double timeTrackedMin) throws IOException {
        try (PrintWriter fileWriter = new PrintWriter("study_data.txt")) {
            fileWriter.println(timeTrackedMin);
        }
    }

    public static double getTotalMinutes() throws IOException {
        File file = new File("study_data.txt");
        if (!file.exists()) return 0.0;
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextDouble()) {
                return scanner.nextDouble();
            } else {
                return 0.0;
            }
        }
    }

    // --- Menu & Input --- //
    private static void printStartup(Double timeTrackedMin) {
        System.out.println("==================================");
        System.out.println("Welcome to the Study Time Tracker");
        System.out.println("==================================");
        System.out.println("It takes 10,000 hours to master a skill.\nAre you ready to start working?\n");
        System.out.println("Your previous study hours: " + formatHoursMinutes(timeTrackedMin));
        System.out.println();
    }
    private static void printMenu() {
        System.out.println("---------");
        System.out.println(" M E N U ");
        System.out.println("---------");
        System.out.println("1 - Start a study session");
        System.out.println("2 - End study session");
        System.out.println("3 - View total study time");
        System.out.println("4 - Exit");
    }
    private static int readMenuChoice(Scanner scnr) {
         System.out.println("Please choose an option from the menu (1-4)");

         while (!scnr.hasNextInt()) {
            System.out.print("Please enter a number (1-4)");
            scnr.next();
         }
         int choice = scnr.nextInt();
         return choice;
    }
    private static String formatHoursMinutes(double minutesTotal) {
        int hours = (int)(minutesTotal / 60.0);
        int minutes = (int)(minutesTotal % 60.0);

        if (hours == 0 && minutes == 0 && minutesTotal > 0.0) {
            return "under 1 minute.";
        }

        return hours + "hours and " + minutes + " minutes";
    }
}