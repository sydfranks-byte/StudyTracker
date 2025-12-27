package model;

/**
 * Manages study time tracking and goal progress.
 * Contains business logic for time accumulation and goal checking.
 */
public class StudyProgress {
    private static final double GOAL_HOURS = 10000.0;
    private static final double GOAL_MINUTES = GOAL_HOURS * 60.0;

    private double totalMinutes;

    public StudyProgress(double initialMinutes) {
        this.totalMinutes = Math.max(0, initialMinutes);
    }

    public void addMinutes(double minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes cannot be negative");
        }
        totalMinutes = Math.min(totalMinutes + minutes, GOAL_MINUTES);
    }

    public double getTotalMinutes() {
        return totalMinutes;
    }

    public double getTotalHours() {
        return totalMinutes / 60.0;
    }

    public double getProgressPercent() {
        return (totalMinutes / GOAL_MINUTES) * 100.0;
    }

    public double getRemainingMinutes() {
        return GOAL_MINUTES - totalMinutes;
    }

    public boolean isGoalReached() {
        return totalMinutes >= GOAL_MINUTES;
    }

    public static double getGoalMinutes() {
        return GOAL_MINUTES;
    }

    public static double getGoalHours() {
        return GOAL_HOURS;
    }
}
