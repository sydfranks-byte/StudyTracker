import java.util.Scanner;

import app.Application;
import model.StudyProgress;
import repository.FileRepository;
import ui.ConsoleUI;

/**
 * Main entry point for the Study Time Tracker application.
 * Responsible for dependency wiring and application bootstrap.
 */
public class StudyTracker {
    private static final String DATA_FILE = "study_data.txt";

    public static void main(String[] args) {
        FileRepository repository = new FileRepository(DATA_FILE);
        StudyProgress progress = new StudyProgress(repository.load());

        try (Scanner scanner = new Scanner(System.in)) {
            ConsoleUI ui = new ConsoleUI(scanner);
            Application app = new Application(ui, progress, repository);
            app.run();
        }
    }
}
