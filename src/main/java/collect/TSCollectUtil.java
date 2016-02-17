package main.java.collect;


import java.util.HashMap;
import java.util.Map;
/**
 * Time series class
 *
 * @author navdeepgill
 */

public class TSCollectUtil {

    /**
     * Cache results of computation.
     */
    protected Map<String, Double> memo;

    /**
     * Time points
     */
    protected double[] points;

    /**
     * Number of points
     */
    protected int number;

    /**
     * Constructor
     *
     * @param _number
     *            Number of points
     */

    public TSCollectUtil(int _number) {
        if (_number <= 0) {
            this.number = 128;

            //throw.warning("Number of points must greater than zero，use the default value 128");
        } else {
            this.number = _number;
        }
        this.points = new double[this.number];

        this.memo = new HashMap<>();
    }

    /**
     * Set time point value
     *
     * @param index
     *            Point index
     * @param value
     *            Time point value
     */
    public void setTimePoint(int index, double value) {
        if (index < 0 || index >= this.number) {
            throw new IllegalArgumentException("Index or length out of range");
        }

        this.points[index] = value;

        if (this.memo.size() > 0) {
            this.memo.clear();
        }
    }

    /**
     * Get average
     *
     * @return Average
     */
    public double getAverage() {
        if (!this.memo.containsKey("avg")) {
            double total = 0D;

            for (double tp : this.points) {
                total += tp;
            }

            this.memo.put("avg", total / this.number);
        }

        return this.memo.get("avg");
    }

    /**
     * Get variance
     *
     * @return Variance
     */
    public double getVariance() {
        if (!this.memo.containsKey("var")) {
            double avg = this.getAverage();
            double total = 0D;

            for (double tp : this.points) {
                total += (tp - avg) * (tp - avg);
            }

            this.memo.put("var", total / this.number);
        }

        return this.memo.get("var");
    }

    /**
     * Get auto-covariance
     *
     * @param k
     *            Lag
     * @return Auto-covariance
     */
    public double getAutocovariance(int k) {
        if (!this.memo.containsKey("acov" + k)) {
            if (k < 0 || k >= this.number) {
                throw new IllegalArgumentException("Index or length out of range");
            }

            if (k == 0) {
                return this.getVariance();
            }

            double total = 0D;
            double avg = this.getAverage();
            for (int i = k; i < this.number; i++) {
                total += (this.points[i - k] - avg) * (this.points[i] - avg);
            }

            this.memo.put("acov" + k, total / this.number);
        }

        return this.memo.get("acov" + k);
    }

    /**
     * Get auto-correlation
     *
     * @param k
     *            Lag
     * @return Auto-correlation
     */
    public double getAutocorrelation(int k) {
        if (!this.memo.containsKey("acor" + k)) {
            this.memo.put("acor" + k,
                    this.getAutocovariance(k) / this.getVariance());
        }

        return this.memo.get("acor" + k);
    }

    /**
     * Get ACF
     *
     * @param n
     *            lag
     * @return ACF values
     */
    public double[] acf(int n) {
        if (n <= 0) {
            n = 10;
        }
        double[] acfValues = new double[n + 1];

        for (int i = 0; i <= n; i++) {
            acfValues[i] = this.getAutocorrelation(i);
        }

        return acfValues;
    }

    /**
     * Get PACF
     *
     * @param n
     *            lag
     * @return PACF values
     */
    public double[] pacf(int n) {
        if (n <= 0) {
            n = 10;
        }
        double[] pacfValues = new double[n + 1];
        double[][] phi = new double[n + 1][n + 1];

        pacfValues[0] = phi[0][0] = 1D;
        pacfValues[1] = phi[1][1] = this.getAutocorrelation(1);

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i - 1; j++) {
                phi[i - 1][j] = phi[i - 2][j] - phi[i - 1][i - 1]
                        * phi[i - 2][i - 1 - j];
            }

            double a = 0D, b = 0D;
            for (int j = 1; j < i; j++) {
                a += phi[i - 1][j] * this.getAutocorrelation(i - j);
                b += phi[i - 1][j] * this.getAutocorrelation(j);
            }

            pacfValues[i] = phi[i][i] = (this.getAutocorrelation(i) - a)
                    / (1 - b);
        }

        return pacfValues;
    }

}


