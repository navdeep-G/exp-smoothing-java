package collect;

import util.StatsFrame;
import util.Util;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import water.H2O;
import water.fvec.Frame;

/**Capture relevant metrics from a time series dataset
 *
 * @author navdeepgill
 */

public class CollectFrame {
    
    private final int _k;
    private final int _n;
    protected final Frame _data;

    public CollectFrame(Frame data, int k, int n) throws IOException {
        _data = data;
        _k = k;
        _n = n;
    }


    /**
     * Get average
     *
     * @return Average of the time series
     */
    public double[] getAverage() {
        return StatsFrame.average(_data);
    }

    /**
     * Get variance
     *
     * @return Variance
     */
    public double[] getVariance() {
        return StatsFrame.variance(_data);
    }

    /**
     * Get Standard Deviation
     *
     * @return Standard Deviation
     */
    public double[] getStandardDeviation() {
        return StatsFrame.standardDeviation(_data);
    }

    /**
     * Get Minimum Value Based On Index
     *
     * @return Minimum Value
     */
    public double[] getMin() {
        return StatsFrame.getMin(_data);
    }

    /**
     * Get Maximum Value Based On Index
     *
     * @return Maximum Value
     */
    public double[] getMax() {
        return StatsFrame.getMax(_data);
    }

    /**
     * Get auto-covariance
     *
     * @return Auto-Covariance
     */
    public double[] getAutocovariance() {
        return StatsFrame.getAutoCovariance(_data, _k);
    }

    /**
     * Get auto-correlation
     *
     * @return Auto-correlation
     */
    public double[] getAutocorrelation() {
        return StatsFrame.getAutoCorrelation(_data, _k);
    }

    /**
     * Get ACF
     *
     * @param n lag
     * @return ACF values
     */
    public double[][] acf(int n) {
        return StatsFrame.getAcf(_data, _n);
    }

    /**
     * Get PACF
     *
     * @return PACF values
     */
    public double[][] pacf() {
        return StatsFrame.getPacf(_data, _n);
    }
}




