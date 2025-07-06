package algos.expsmoothing;

import java.util.List;

/**
 * Single Exponential Smoothing with forecast extension.
 */
public class SingleExpSmoothing {

    public static double[] singleExponentialForecast(List<Double> data, double alpha, int numForecasts) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Input data must not be null or empty.");
        }
        if (alpha <= 0 || alpha > 1) {
            throw new IllegalArgumentException("Alpha must be in (0, 1]");
        }

        int n = data.size();
        double[] y = new double[n + numForecasts];

        // Initialization: set first smoothed value to first actual value
        y[0] = data.get(0);

        // Compute smoothed values for existing data
        for (int i = 1; i < n; i++) {
            y[i] = alpha * data.get(i - 1) + (1 - alpha) * y[i - 1];
        }

        // Forecast beyond data (constant forecast using last smoothed value)
        for (int i = n; i < n + numForecasts; i++) {
            y[i] = y[i - 1];
        }

        return y;
    }
}
