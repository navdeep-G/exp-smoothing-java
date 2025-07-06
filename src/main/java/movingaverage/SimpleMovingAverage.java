package movingaverage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Simple Moving Average (SMA) calculator.
 * Computes the average of a sliding window over a numeric series.
 *
 * Example usage:
 *   SimpleMovingAverage sma = new SimpleMovingAverage(5);
 *   List<Double> result = sma.compute(data);
 *
 * Returns `null` for the first (period - 1) elements, until the window is full.
 *
 * Author: navdeep
 */
public class SimpleMovingAverage implements MovingAverage {

    private final Deque<Double> window;
    private final int period;
    private double sum;

    public SimpleMovingAverage(int period) {
        if (period <= 0) {
            throw new IllegalArgumentException("Period must be a positive integer!");
        }
        this.period = period;
        this.window = new ArrayDeque<>(period);
        this.sum = 0.0;
    }

    /**
     * Computes the simple moving average (SMA) over the input data.
     * Returns null for values where the moving average window is not yet full.
     *
     * @param data input time series
     * @return list of SMA values (same length as input)
     */
    @Override
    public List<Double> compute(List<Double> data) {
        if (data == null || data.isEmpty()) return List.of();

        List<Double> maData = new ArrayList<>(data.size());
        reset();

        for (double value : data) {
            add(value);
            if (window.size() < period) {
                maData.add(null); // Not enough data yet
            } else {
                maData.add(getAverage());
            }
        }
        return maData;
    }

    /**
     * Adds a new number to the window and updates the rolling sum.
     */
    public void add(double value) {
        sum += value;
        window.addLast(value);
        if (window.size() > period) {
            sum -= window.removeFirst();
        }
    }

    /**
     * Returns the current average of the window.
     */
    private double getAverage() {
        return sum / period;
    }

    /**
     * Resets the moving average state.
     */
    @Override
    public void reset() {
        window.clear();
        sum = 0.0;
    }
}
