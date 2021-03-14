package h2oframe.util;

import water.fvec.Frame;
import water.fvec.Vec;

/**Collect relevant statistics about a time series:
 *  -Mean
 *  -Standard Deviation
 *  -Variance
 *  -Minimum
 *  -Maximum
 *  -AutoCovariance
 *  -AutoCorrelatin
 *  -AutoCorrelationFunction
 *  -PartialAutoCorrelationFunction
 *
 * @author navdeepgill
 */
public class StatsFrame {

    public static double[] average(Frame data) {
        return data.means();
    }

    public static double[] standardDeviation(Frame data) {
        double[] vec = new double[data.numCols()];

        for (int i = 0; i < vec.length; i++) {
            vec[i] = data.vec(i).sigma();
        }
        return vec;
    }

    public static double[] variance(Frame data) {
        double[] vec = new double[data.numCols()];

        for (int i = 0; i < vec.length; i++) {
            vec[i] = Math.pow(data.vec(i).sigma(),2);
        }
        return vec;
    }

    public static double[] getMin(Frame data) {
        double[] vec = new double[data.numCols()];

        for (int i = 0; i < vec.length; i++) {
            vec[i] = data.vec(i).min();
        }
        return vec;
    }

    public static double[] getMax(Frame data) {
        double[] vec = new double[data.numCols()];

        for (int i = 0; i < vec.length; i++) {
            vec[i] = data.vec(i).max();
        }
        return vec;
    }

    public static double [] getAutoCovariance(Frame data, int k){
        long nrow = data.numRows();

        if (k == 0) {
            return variance(data);
        }

        double[] auto_cov = new double[data.numCols()];
        double[] avg = average(data);

        for(int v = 0; v < data.numCols(); ++v){
            Vec vec = data.vec(v);
            for (long i = k; i < nrow; ++i) {
                auto_cov[v] += (vec.at(i - k) - avg[v]) * (vec.at(i) - avg[v]);
            }
            auto_cov[v]/=nrow;
        }

        return auto_cov;

    }

    public static double[] getAutoCorrelation(Frame data, int k){
        double autocovariance[] = getAutoCovariance(data,k);
        double variance[] = variance(data);
        double autocorrelation[] = new double[autocovariance.length];

        for(int j = 0; j < autocorrelation.length; ++j){
            autocorrelation[j] = autocovariance[j]/variance[j];
        }

        return autocorrelation;
    }


    public static double[][] getAcf(Frame data, int n){

        double[][] acfValues = new double[data.numCols()][n + 1];

        for (int i = 0; i <= n; i++) {
            double[] acf = getAutoCorrelation(data,i);
            for(int j = 0; j < data.numCols(); ++j){
                acfValues[j][i] = acf[j];
            }
        }

        return acfValues;
    }

    public static double[][] getPacf(Frame data, int n){

        double[][] pacfValues = new double[data.numCols()][n + 1];
        double[][][] phi = new double[data.numCols()][n + 1][n + 1];
        double [] autocov = getAutoCorrelation(data, 1);

        for(int c = 0; c < data.numCols(); ++c){
            pacfValues[c][0] = phi[c][0][0] = 1D;
            pacfValues[c][1] = phi[c][1][1] = autocov[c];
            for (int i = 2; i <= n; i++) {
                for (int j = 1; j < i - 1; j++) {
                    phi[c][i - 1][j] = phi[c][i - 2][j] - phi[c][i - 1][i - 1]
                            * phi[c][i - 2][i - 1 - j];
                }

                double a = 0D, b = 0D;
                for (int j = 1; j < i; j++) {
                    a += phi[c][i - 1][j] * getAutoCorrelation(data, i - j)[c];
                    b += phi[c][i - 1][j] * getAutoCorrelation(data, j)[c];
                }

                pacfValues[c][i] = phi[c][i][i] = (getAutoCorrelation(data, i)[c] - a) / (1 - b);
            }
        }

        return pacfValues;
    }

}
