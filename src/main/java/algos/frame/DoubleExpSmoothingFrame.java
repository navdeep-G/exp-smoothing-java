package algos.frame;

import water.fvec.Frame;
import water.fvec.Vec;

//Double Exponential Smoothing for an H2O Frame
public class DoubleExpSmoothingFrame {
    public static double[] doubleExponentialForecast(Frame data, double alpha, double gamma, int initializationMethod, int numForecasts) {
        double[] y = new double[0];

        for (Vec v : data.vecs()) {
            //Initialize vec length
            int n = (int) v.length();

            y = new double[n + numForecasts];
            double[] s = new double[n];
            double[] b = new double[n];
            s[0] = y[0] = v.at(0);

            if (initializationMethod == 0) {
                b[0] = v.at(1) - v.at(0);
            } else if (initializationMethod == 1 && n > 4) {
                b[0] = (v.at(3) - v.at(0)) / 3;
            } else if (initializationMethod == 2) {
                b[0] = (v.at(n - 1) - v.at(0)) / (n - 1);
            }

            int i = 1;
            y[1] = s[0] + b[0];
            for (i = 1; i < n; i++) {
                s[i] = alpha * v.at(i) + (1 - alpha) * (s[i - 1] + b[i - 1]);
                b[i] = gamma * (s[i] - s[i - 1]) + (1 - gamma) * b[i - 1];
                y[i + 1] = s[i] + b[i];
            }

            for (int j = 0; j < numForecasts; j++, i++) {
                y[i] = s[n - 1] + (j + 1) * b[n - 1];
            }
        }
        return y;
    }
}
