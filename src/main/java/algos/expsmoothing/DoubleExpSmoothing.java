package algos.expsmoothing;

import java.util.ArrayList;
import java.util.List;

public class DoubleExpSmoothing implements ExponentialSmoothing {

    private final double alpha;
    private final double gamma;
    private final int initializationMethod;

    public DoubleExpSmoothing(double alpha, double gamma, int initializationMethod) {
        if (alpha <= 0 || alpha > 1 || gamma <= 0 || gamma > 1) {
            throw new IllegalArgumentException("Alpha and gamma must be in (0, 1].");
        }
        if (initializationMethod < 0 || initializationMethod > 2) {
            throw new IllegalArgumentException("Initialization method must be 0, 1, or 2.");
        }
        this.alpha = alpha;
        this.gamma = gamma;
        this.initializationMethod = initializationMethod;
    }

    @Override
    public List<Double> forecast(List<Double> data, int steps) {
        if (data == null || data.size() < 2) {
            throw new IllegalArgumentException("Data must contain at least 2 points.");
        }

        int n = data.size();
        double[] y = new double[n + steps];
        double[] s = new double[n];
        double[] b = new double[n];

        // Initialization
        s[0] = y[0] = data.get(0);
        switch (initializationMethod) {
            case 0 -> b[0] = data.get(1) - data.get(0);
            case 1 -> b[0] = (n > 4) ? (data.get(3) - data.get(0)) / 3 : data.get(1) - data.get(0);
            case 2 -> b[0] = (data.get(n - 1) - data.get(0)) / (n - 1);
        }

        // Smoothing
        for (int i = 1; i < n; i++) {
            s[i] = alpha * data.get(i) + (1 - alpha) * (s[i - 1] + b[i - 1]);
            b[i] = gamma * (s[i] - s[i - 1]) + (1 - gamma) * b[i - 1];
            y[i] = s[i - 1] + b[i - 1]; // forecast at time i based on previous state
        }

        // Forecast
        for (int j = 0; j < steps; j++) {
            y[n + j] = s[n - 1] + (j + 1) * b[n - 1];
        }

        // Convert array to list
        List<Double> forecast = new ArrayList<>(y.length);
        for (double v : y) {
            forecast.add(v);
        }
        return forecast;
    }
}
