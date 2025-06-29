package collect;

import util.*;

import java.io.*;
import java.lang.*;
import java.util.List;

/**Capture relevant metrics from a time series dataset
 *
 * @author navdeepgill
 */

public class Collect {

    private final String _filepath;
    private final int _k;
    private final int _n;
    protected final List<Double> _data;

    public Collect(String filepath, int k, int n) throws IOException {
        _filepath = filepath;
        _k = k;
        _n = n;
        _data = ReadFile();
    }

    /**
     * Read in time series dataset
     *
     * @return time series array list
     */
    public List<Double> ReadFile() throws IOException {
        return Util.ReadFile(_filepath);
    }


    /**
     * Get average
     *
     * @return Average of the time series
     */
    public double getAverage() {
        return Stats.average(_data);
    }

    /**
     * Get variance
     *
     * @return Variance
     */
    public double getVariance() {
        return Stats.variance(_data);
    }

    /**
     * Get Standard Deviation
     *
     * @return Standard Deviation
     */
    public double getStandardDeviation() {
        return Stats.standardDeviation(_data);
    }

    /**
     * Get Minimum Value Index
     *
     * @return Minimum Value Index
     */
    public int getMinIndex() {
        return Stats.getMinimumIndex(_data);
    }

    /**
     * Get Maximum Value Index
     *
     * @return Maximum Value Index
     */
    public int getMaxIndex() {
        return Stats.getMaximumIndex(_data);
    }

    /**
     * Get Minimum Value Based On Index
     *
     * @return Minimum Value
     */
    public double getMin() {
        return Stats.getMinimum(_data);
    }

    /**
     * Get Maximum Value Based On Index
     *
     * @return Maximum Value
     */
    public double getMax() {
        return Stats.getMaximum(_data);
    }

    /**
     * Get auto-covariance
     *
     * @return Auto-Covariance
     */
    public double getAutocovariance() {
        return Stats.getAutoCovariance(_data, _k);
    }

    /**
     * Get auto-correlation
     *
     * @return Auto-correlation
     */
    public double getAutocorrelation() {
        return Stats.getAutoCorrelation(_data, _k);
    }

    /**
     * Get ACF
     *
     * @param n lag
     * @return ACF values
     */
    public double[] acf(int n) {
        return Stats.getAcf(_data, _n);
    }

    /**
     * Get PACF
     *
     * @return PACF values
     */
    public double[] pacf() {
        return Stats.getPacf(_data, _n);
    }
}




