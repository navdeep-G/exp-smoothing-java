package collect;

import util.TSUtil;

import java.io.*;
import java.util.ArrayList;
import java.lang.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**Capture relevant metrics from a time series dataset.
 * These metrics can guide algorithm implementation in the future.
 *
 * @author navdeepgill
 */

public class TSCollect {

    private final String _filepath;
    private final int _k;
    private final int _n;
    protected final ArrayList<Double> _data;

    public TSCollect(String filepath, int k, int n) throws IOException {
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
    public ArrayList<Double> ReadFile() throws IOException {
        return TSUtil.ReadFile(_filepath);
    }


    /**
     * Get average
     *
     * @return Average of the time series
     */
    public double getAverage() {
        return TSUtil.average(_data);
    }

    /**
     * Get variance
     *
     * @return Variance
     */
    public double getVariance() {
        return TSUtil.variance(_data);
    }

    /**
     * Get Standard Deviation
     *
     * @return Standard Deviation
     */
    public double getStandardDeviation() {
        return TSUtil.standardDeviation(_data);
    }

    /**
     * Get Minimum Value Index
     *
     * @return Minimum Value Index
     */
    public int getMinIndex() {
        return TSUtil.getMinimumIndex(_data);
    }

    /**
     * Get Maximum Value Index
     *
     * @return Maximum Value Index
     */
    public int getMaxIndex() {
        return TSUtil.getMaximumIndex(_data);
    }

    /**
     * Get Minimum Value Based On Index
     *
     * @return Minimum Value
     */
    public double getMin() {
        return TSUtil.getMinimum(_data);
    }

    /**
     * Get Maximum Value Based On Index
     *
     * @return Maximum Value
     */
    public double getMax() {
        return TSUtil.getMaximium(_data);
    }

    /**
     * Get auto-covariance
     *
     * @return Auto-Covariance
     */
    public double getAutocovariance() {
        return TSUtil.getAutoCovariance(_data, _k);
    }

    /**
     * Get auto-correlation
     *
     * @return Auto-correlation
     */
    public double getAutocorrelation() {
        return TSUtil.getAutoCorrelation(_data, _k);
    }

    /**
     * Get ACF
     *
     * @param n lag
     * @return ACF values
     */
    public double[] acf(int n) {
        return TSUtil.getAcf(_data, _n);
    }

    /**
     * Get PACF
     *
     * @return PACF values
     */
    public double[] pacf() {
        return TSUtil.getPacf(_data, _n);
    }
}




