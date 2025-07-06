package movingaverage;

import java.util.ArrayList;
import java.util.List;

/**
 * Exponential Moving Average (EMA)
 * Applies smoothing with decay factor alpha in (0, 1].
 *
 * Example:
 *   ExponentialMovingAverage ema = new ExponentialMovingAverage(0.3);
 *   List<Double> result = ema.compute(data);
 *
 * Author: navdeepgill
 */
public class ExponentialMovingAverage implements MovingAverage {

    private final double alpha;
    private Double oldValue;

    public ExponentialMovingAverage(double alpha) {
        if (alpha <= 0 || alpha > 1) {
            throw new IllegalArgumentException("Alpha must be in (0, 1]");
        }
        this.alpha = alpha;
    }

    /**
     * Computes the EMA for a list of values.
     * Does not modify the original list.
     *
     * @param data the input data
     * @return EMA-smoothed data
     */
    @Override
    public List<Double> compute(List<Double> data) {
        List<Double> emaList = new ArrayList<>(data.size());
        reset();  // Reset internal state for fresh computation

        for (double val : data) {
            emaList.add(compute(val));
        }
        return emaList;
    }

    /**
     * Compute next EMA value given the current input.
     *
     * @param value new data point
     * @return updated EMA
     */
    public double compute(double value) {
        if (oldValue == null) {
            oldValue = value;
        } else {
            oldValue = oldValue + alpha * (value - oldValue);
        }
        return oldValue;
    }

    /**
     * Resets internal state (for reuse).
     */
    @Override
    public void reset() {
        oldValue = null;
    }
}
