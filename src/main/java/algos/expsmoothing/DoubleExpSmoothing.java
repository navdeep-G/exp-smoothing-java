package algos.expsmoothing;

import java.util.List;

//Double Exponential Smoothing
public class DoubleExpSmoothing {
    public static double[] doubleExponentialForecast(List<Double> data, double alpha, double gamma, int initializationMethod, int numForecasts) {
        double[] y = new double[data.size() + numForecasts];
        double[] s = new double[data.size()];
        double[] b = new double[data.size()];
        s[0] = y[0] = data.get(0);

        if(initializationMethod==0) {
            b[0] = data.get(1)-data.get(0);
        } else if(initializationMethod==1 && data.size()>4) {
            b[0] = (data.get(3) - data.get(0)) / 3;
        } else if(initializationMethod==2) {
            b[0] = (data.get(data.size() - 1) - data.get(0))/(data.size() - 1);
        }

        int i = 1;
        y[1] = s[0] + b[0];
        for (i = 1; i < data.size(); i++) {
            s[i] = alpha * data.get(i) + (1 - alpha) * (s[i - 1]+b[i - 1]);
            b[i] = gamma * (s[i] - s[i - 1]) + (1-gamma) * b[i-1];
            y[i+1] = s[i] + b[i];
        }

        for (int j = 0; j < numForecasts ; j++, i++) {
            y[i] = s[data.size()-1] + (j+1) * b[data.size()-1];
        }

        return y;
    }
}
