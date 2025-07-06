package movingaverage;

import java.util.ArrayList;
import java.util.List;

/**
 * Cumulative Moving Average (CMA)
 * Calculates the running average of all values seen so far.
 *
 * Example:
 *   CumulativeMovingAverage cma = new CumulativeMovingAverage();
 *   List<Double> result = cma.getCMA(data);
 *
 * Author: navdeepgill
 */
public class CumulativeMovingAverage {
    private int n = 0;
    private double average = 0.0;

    /**
     * Computes the cumulative moving average for the input data.
     * Does not modify the original list.
     *
     * @param data the input time series
     * @return a new list of CMA values
     */
    public List<Double> getCMA(List<Double> data) {
        List<Double> result = new ArrayList<>(data.size());
        reset();  // reset state before each run

        for (double value : data) {
            result.add(add(value));
        }
        return result;
    }

    /**
     * Updates the running average with a new value.
     */
    public double add(double x) {
        average += (x - average) / ++n;
        return average;
    }

    /**
     * Resets the internal state for reuse.
     */
    public void reset() {
        n = 0;
        average = 0.0;
    }
}
