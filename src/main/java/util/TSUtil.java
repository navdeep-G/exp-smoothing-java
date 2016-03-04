package main.java.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**Utility functions for time series.
 *  Handles reading in data. Currently only taking in csv files.
 * @author navdeepgill
 */
public class TSUtil {

    /**
     * Read in time series dataset and sent to ArrayList<double>
     *
     * @return An array list of data that is read in from the file path
     */
    public static ArrayList<Double> ReadFile(String filepath) throws IOException {
        FileReader fileReader = new FileReader(filepath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<Double> data = new ArrayList<>();
        String line;

        while ((line = bufferedReader.readLine()) != null)
        {
            data.add(Double.parseDouble(line));
        }

        bufferedReader.close();
        return data;
    }

    public static double average(List<Double> data) {
        double total = 0;
        double nrow = data.size();

        for (double item : data) {
            total += item;
        }

        double average = total/nrow;
        return average;
    }

    public static double variance(List<Double> data) {
        double avg = average(data);
        double total = 0;
        double nrow = data.size();
        if (nrow == 1) return 0.0;

        for (double item : data) {
            total += (item - avg) * (item - avg);
        }

        double variance = total/(nrow-1);
        return variance;
    }

    public static double standardDeviation(List<Double> data) {
        return Math.sqrt(variance(data));
    }

    public static List<Double> transform(List<Double> l, TSTransform.Type t) {
        return TSTransform.getFunction(t).exec(l);
    }
}
