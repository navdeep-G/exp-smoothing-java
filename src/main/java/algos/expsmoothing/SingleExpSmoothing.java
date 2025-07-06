package algos.expsmoothing;

import java.util.ArrayList;
import java.util.List;

/**
 * Single Exponential Smoothing with forecast extension.
 */
public class SingleExpSmoothing implements ExponentialSmoothing {

    private final double alpha;

    public SingleExpSmoothing(double alpha) {
        if (alpha <= 0 || alpha > 1) {
            throw new IllegalArgumentException("Alpha must be in (0, 1]");
        }
        this.alpha = alpha;
    }

    @Override
    public List<Double> forecast(List<Double> data, int steps) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Input data must not be null or empty.");
        }

        int n = data.size();
        List<Double> result = new ArrayList<>(n + steps);

        // Initialization
        double y = data.get(0);
        result.add(y);

        // Apply exponential smoothing to existing data
        for (int i = 1; i < n; i++) {
            y = alpha * data.get(i - 1) + (1 - alpha) * y;
            result.add(y);
        }

        // Forecast steps using last smoothed value
        for (int i = 0; i < steps; i++) {
            result.add(y);
        }

        return result;
    }
}
