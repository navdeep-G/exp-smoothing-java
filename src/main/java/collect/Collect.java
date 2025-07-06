package collect;

import util.*;
import transform.Transform;
import tests.AugmentedDickeyFuller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Capture relevant metrics from a time series dataset.
 * Includes statistics, transforms, stationarity checks, and more.
 */
public class Collect {

    private final String _filepath;
    private final int _k;
    private final int _n;
    protected final List<Double> _data;

    public Collect(String filepath, int k, int n) throws IOException {
        this._filepath = filepath;
        this._k = k;
        this._n = n;
        this._data = readFile();
    }

    /**
     * Private file reader method
     */
    public List<Double> readFile() throws IOException {
        return Util.readFile(_filepath);
    }

    // === Summary Stats ===

    public double getAverage() {
        return Stats.average(_data);
    }

    public double getVariance() {
        return Stats.variance(_data);
    }

    public double getStandardDeviation() {
        return Stats.standardDeviation(_data);
    }

    public int getMinIndex() {
        return Stats.getMinimumIndex(_data);
    }

    public int getMaxIndex() {
        return Stats.getMaximumIndex(_data);
    }

    public double getMin() {
        return Stats.getMinimum(_data);
    }

    public double getMax() {
        return Stats.getMaximum(_data);
    }

    // === Autocorrelation Metrics ===

    public double getAutocovariance() {
        return Stats.getAutoCovariance(_data, _k);
    }

    public double getAutocorrelation() {
        return Stats.getAutoCorrelation(_data, _k);
    }

    public double[] acf(int n) {
        return Stats.getAcf(_data, n); // Fixed to use argument
    }

    public double[] pacf() {
        return Stats.getPacf(_data, _n);
    }

    // === Transformations ===

    public List<Double> getLogTransformed() {
        return Transform.log(_data);
    }

    public List<Double> getBoxCoxTransformed() {
        return Transform.boxCox(_data);
    }

    public List<Double> getFirstDifference() {
        List<Double> diff = new ArrayList<>();
        for (int i = 1; i < _data.size(); i++) {
            diff.add(_data.get(i) - _data.get(i - 1));
        }
        return diff;
    }

    public List<Double> getRollingAverage(int windowSize) {
        List<Double> rolling = new ArrayList<>();
        for (int i = 0; i <= _data.size() - windowSize; i++) {
            List<Double> window = _data.subList(i, i + windowSize);
            rolling.add(Stats.average(window));
        }
        return rolling;
    }

    // === Stationarity Checks ===

    public double getADFStat() {
        return new AugmentedDickeyFuller(_data).getAdfStat();
    }

    public boolean isStationary() {
        return new AugmentedDickeyFuller(_data).isStationary();
    }

    // === Summary ===

    @Override
    public String toString() {
        return String.format(
                "Collect Summary:\n" +
                        "- Mean: %.4f\n" +
                        "- Std Dev: %.4f\n" +
                        "- Min: %.4f at index %d\n" +
                        "- Max: %.4f at index %d\n" +
                        "- ADF Stat: %.4f (Stationary: %b)",
                getAverage(),
                getStandardDeviation(),
                getMin(), getMinIndex(),
                getMax(), getMaxIndex(),
                getADFStat(), isStationary()
        );
    }
}
