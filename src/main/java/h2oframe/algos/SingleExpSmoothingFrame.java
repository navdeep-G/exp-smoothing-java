package h2oframe.algos;

import water.fvec.Frame;
import water.fvec.Vec;

//Single Exponential Smoothing
public class SingleExpSmoothingFrame {
    public static double[] singleExponentialForecastFrame(Frame data, double alpha, int numForecasts) {
        double[] y = new double[0];
        for (Vec v : data.vecs()) {
            int n = (int) v.length();
            y = new double[n + numForecasts];
            y[0] = 0;
            y[1] = v.at(0);
            int i = 2;
            for (i = 2; i < v.length(); i++) {
                y[i] = alpha * v.at(i - 1) + (1 - alpha) * y[i - 1];
            }

            for (int j = 0; j < numForecasts; j++, i++) {
                y[i] = alpha * v.at(v.length() - 1) + (1 - alpha) * y[i - 1];
            }
        }
        return y;
    }
}

