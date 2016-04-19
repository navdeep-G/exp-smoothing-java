package algos.expsmoothing;

import java.util.List;

//Single Exponential Smoothing
public class SingleExpSmoothing{
    public static double[] singleExponentialForecast(List<Double> data, double alpha, int numForecasts) {
        double[] y = new double[data.size() + numForecasts];
        y[0] = 0;
        y[1] = data.get(0);
        int i = 2;
        for (i = 2; i < data.size(); i++) {
            y[i] = alpha * data.get(i - 1) + (1 - alpha) * y[i - 1];
        }

        for (int j = 0; j < numForecasts; j++, i++) {
            y[i] = alpha * data.get(data.size() - 1) + (1 - alpha) * y[i - 1];
        }
        return y;
    }
}

