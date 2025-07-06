package tslib.movingaverage;

import java.util.List;

public interface MovingAverage {
    /**
     * Compute the moving average for the given time series.
     * @param data input series
     * @return list of moving average values
     */
    List<Double> compute(List<Double> data);

    /**
     * Reset internal state (if applicable).
     */
    void reset();
}
