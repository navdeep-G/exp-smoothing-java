package tslib.movingaverage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Weighted Moving Average (WMA) calculator.
 * Computes the weighted average of a sliding window over a numeric series.
 * More recent values in the window receive higher weights.
 *
 * Example usage:
 *   WeightedMovingAverage wma = new WeightedMovingAverage(5);
 *   List<Double> result = wma.compute(data);
 *
 * Returns `null` for the first (period - 1) elements, until the window is full.
 * Weights are assigned linearly: most recent value gets weight 'period', 
 * second most recent gets weight 'period-1', etc.
 *
 * Author: navdeep
 */
public class WeightedMovingAverage implements MovingAverage {

    private final Deque<Double> window;
    private final int period;
    private final double weightSum;

    public WeightedMovingAverage(int period) {
        if (period <= 0) {
            throw new IllegalArgumentException("Period must be a positive integer!");
        }
        this.period = period;
        this.window = new ArrayDeque<>(period);
        // Calculate sum of weights: 1 + 2 + 3 + ... + period = period * (period + 1) / 2
        this.weightSum = period * (period + 1) / 2.0;
    }

    /**
     * Computes the weighted moving average (WMA) over the input data.
     * Returns null for values where the moving average window is not yet full.
     *
     * @param data input time series
     * @return list of WMA values (same length as input)
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
                maData.add(getWeightedAverage());
            }
        }
        return maData;
    }

    /**
     * Adds a new number to the window.
     */
    public void add(double value) {
        window.addLast(value);
        if (window.size() > period) {
            window.removeFirst();
        }
    }

    /**
     * Returns the current weighted average of the window.
     * Most recent value gets weight 'period', second most recent gets weight 'period-1', etc.
     */
    private double getWeightedAverage() {
        double weightedSum = 0.0;
        int weight = 1;
        
        for (double value : window) {
            weightedSum += value * weight;
            weight++;
        }
        
        return weightedSum / weightSum;
    }

    /**
     * Resets the moving average state.
     */
    @Override
    public void reset() {
        window.clear();
    }
} 