package repository;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Handles data persistence to file system.
 * Responsible for saving and loading study progress.
 */
public class FileRepository {
    private final String fileName;

    public FileRepository(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        this.fileName = fileName;
    }

    public double load() {
        File file = new File(fileName);

        if (!file.exists()) {
            return 0.0;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            if (fileScanner.hasNextDouble()) {
                double value = fileScanner.nextDouble();
                return Math.max(0, value);
            }
            return 0.0;
        } catch (IOException e) {
            System.err.println("Warning: Could not load data from " + fileName);
            return 0.0;
        }
    }

    public boolean save(double totalMinutes) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println(totalMinutes);
            return true;
        } catch (IOException e) {
            System.err.println("Error: Could not save data to " + fileName);
            return false;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public boolean fileExists() {
        return new File(fileName).exists();
    }
}
