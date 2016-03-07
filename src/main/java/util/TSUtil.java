package main.java.util;

import main.java.util.transform.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**Utility functions for time series analysis
 *
 * @author navdeepgill
 */
public class TSUtil {

    /**
     * Read in time series dataset
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

    public static int getMinimumIndex(List<Double> data){
        return data.indexOf(Collections.min(data));
    }

    public static int getMaximumIndex(List<Double> data){
        return data.indexOf(Collections.max(data));
    }

    public static double getMinimum(List<Double> data){
        int MinIndex = getMinimumIndex(data);
        return data.get(MinIndex);
    }

    public static double getMaximium(List<Double> data){
        int MaxIndex = getMaximumIndex(data);
        return data.get(MaxIndex);
    }

    public static double getAutoCovariance(List<Double> data, int k){
        double nrow = data.size();

        if (k == 0) {
            return variance(data);
        }

        double total = 0;
        double avg = average(data);
        for (int i = k; i < data.size(); i++) {
            total += (data.get(i - k) - avg) * (data.get(i) - avg);
        }
        double autocovariance = total/nrow;
        return autocovariance;

    }

    public static double getAutoCorrelation(List<Double> data, int k){
        double autocovariance = getAutoCovariance(data,k);
        double variance = variance(data);
        double autocorrelation = autocovariance/variance;
        return autocorrelation;
    }

    public static double[] getAcf(List<Double> data, int n){

        double[] acfValues = new double[n + 1];

        for (int i = 0; i <= n; i++) {
            acfValues[i] = getAutoCorrelation(data, i);
        }

        return acfValues;
    }

    public static double[] getPacf(List<Double> data, int n){

        double[] pacfValues = new double[n + 1];
        double[][] phi = new double[n + 1][n + 1];

        pacfValues[0] = phi[0][0] = 1D;
        pacfValues[1] = phi[1][1] = getAutoCorrelation(data, 1);

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i - 1; j++) {
                phi[i - 1][j] = phi[i - 2][j] - phi[i - 1][i - 1]
                        * phi[i - 2][i - 1 - j];
            }

            double a = 0D, b = 0D;
            for (int j = 1; j < i; j++) {
                a += phi[i - 1][j] * getAutoCorrelation(data, i - j);
                b += phi[i - 1][j] * getAutoCorrelation(data, j);
            }

            pacfValues[i] = phi[i][i] = (getAutoCorrelation(data, i) - a)
                    / (1 - b);
        }

        return pacfValues;
    }
}
