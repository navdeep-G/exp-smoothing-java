package tslib.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions for time series analysis.
 */
public class Util {

    /**
     * Reads a plain text file with one numeric value per line.
     *
     * @param filepath Path to file
     * @return List of Doubles
     * @throws IOException if file cannot be read
     */
    public static List<Double> readFile(String filepath) throws IOException {
        List<Double> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    if (!line.isBlank()) {
                        data.add(Double.parseDouble(line.trim()));
                    }
                } catch (NumberFormatException ignored) {
                    // Skip malformed lines
                }
            }
        }
        return data;
    }

    /**
     * Reads a CSV file with numeric columns.
     *
     * @param filepath Path to CSV file
     * @return 2D List of Doubles (each sublist = a column)
     * @throws IOException if file cannot be read
     */
    public static List<List<Double>> readCSV(String filepath) throws IOException {
        List<List<Double>> columns = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split(",");
                if (isFirstLine) {
                    for (int i = 0; i < tokens.length; i++) {
                        columns.add(new ArrayList<>());
                    }
                    isFirstLine = false;
                }

                for (int i = 0; i < tokens.length; i++) {
                    try {
                        columns.get(i).add(Double.parseDouble(tokens[i]));
                    } catch (NumberFormatException | IndexOutOfBoundsException ignored) {
                        // Skip non-numeric or malformed values
                    }
                }
            }
        }

        return columns;
    }
}
