package model;

/**
 * Manages a single study session.
 * Tracks session start/end times and calculates duration.
 */
public class Session {
    private static final double MS_PER_MINUTE = 60000.0;

    private long startTimeMs;
    private boolean active;

    public Session() {
        this.startTimeMs = 0;
        this.active = false;
    }

    public void start() {
        if (active) {
            throw new IllegalStateException("Session is already active");
        }
        this.startTimeMs = System.currentTimeMillis();
        this.active = true;
    }

    public double end() {
        if (!active) {
            throw new IllegalStateException("No active session to end");
        }

        long endTimeMs = System.currentTimeMillis();
        long durationMs = endTimeMs - startTimeMs;

        this.startTimeMs = 0;
        this.active = false;

        return durationMs / MS_PER_MINUTE;
    }

    public boolean isActive() {
        return active;
    }
}
