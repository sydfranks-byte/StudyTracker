package app;

import model.Session;
import model.StudyProgress;
import repository.FileRepository;
import ui.ConsoleUI;
import ui.MenuOption;

/**
 * Application controller that orchestrates the main program flow.
 * Coordinates between UI, business logic, and persistence layers.
 */
public class Application {
    private final ConsoleUI ui;
    private final StudyProgress progress;
    private final FileRepository repository;
    private final Session session;

    public Application(ConsoleUI ui, StudyProgress progress, FileRepository repository) {
        this.ui = ui;
        this.progress = progress;
        this.repository = repository;
        this.session = new Session();
    }

    public void run() {
        ui.showWelcome(progress.getTotalMinutes());

        boolean running = true;
        while (running) {
            ui.showMenu();
            MenuOption option = ui.readMenuChoice();
            running = handleOption(option);
            ui.printBlankLine();
        }
    }

    private boolean handleOption(MenuOption option) {
        switch (option) {
            case START_SESSION:
                handleStartSession();
                return true;

            case END_SESSION:
                handleEndSession();
                return true;

            case VIEW_TOTAL:
                handleViewProgress();
                return true;

            case EXIT:
                return !handleExit();

            case INVALID:
            default:
                ui.showError("Please enter a valid number (1-4).");
                return true;
        }
    }

    private void handleStartSession() {
        if (progress.isGoalReached()) {
            ui.showGoalReached();
            return;
        }

        if (session.isActive()) {
            ui.showError("Session already active. End it first.");
            return;
        }

        session.start();
        ui.showSessionStarted();
    }

    private void handleEndSession() {
        if (!session.isActive()) {
            ui.showError("No session to end.");
            return;
        }

        double sessionMinutes = session.end();
        progress.addMinutes(sessionMinutes);
        ui.showSessionEnded(sessionMinutes, progress.getTotalMinutes());
    }

    private void handleViewProgress() {
        ui.showProgress(progress.getTotalMinutes(), progress.getProgressPercent());
    }

    private boolean handleExit() {
        if (session.isActive()) {
            ui.showError("You have an active session. Please end before exiting.");
            return false;
        }

        boolean saved = repository.save(progress.getTotalMinutes());
        if (saved) {
            ui.showSaved(repository.getFileName());
        }
        return true;
    }
}
