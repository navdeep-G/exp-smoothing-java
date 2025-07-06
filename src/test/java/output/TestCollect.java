package output;

import java.io.IOException;
import java.util.List;

import algos.expsmoothing.SingleExpSmoothing;
import algos.expsmoothing.TripleExpSmoothing;
import collect.Collect;
import movingaverage.*;
import transform.*;
import util.*;
import tests.*;

/**
 * Output relevant calculations from a time series dataset.
 * Used as verification of calculations for now.
 *
 * @author navdeepgill
 */
public class TestCollect {

    // Define initial inputs for Collect
    public static int lag = 1;
    public static String pathToData = "data/hotel.txt";
    public static double lambda = 1.6;

    // Quick check of output from previous methods.
    public static void main(String[] args) throws IOException {
        List<Double> file = Util.ReadFile(pathToData);

        Collect _tm = new Collect(pathToData, lag, lag);

        List<Double> fileLog = Transform.sqrt(file);

        double optimalLam = BoxCox.lambdaSearch(file);
        List<Double> fileBoxCox = BoxCox.transform(file, lambda);

        System.out.println("Optimal Lambda for the time series: " + pathToData + " is " + optimalLam + "\n");

        System.out.println("Manually calculated Box Cox transformation of data with lambda = " + lambda);
        fileBoxCox.forEach(System.out::println);
        System.out.println();

        System.out.println("Log data of Time Series: " + pathToData);
        fileLog.forEach(System.out::println);
        System.out.println();

        System.out.println("First 10 Rows of Time Series Dataset: " + pathToData);
        file.stream().limit(10).forEach(System.out::println);
        System.out.println();

        System.out.println("Number of rows for " + pathToData + " = " + file.size());
        System.out.println();

        System.out.println("Average of Time Series:");
        System.out.println(_tm.getAverage());
        System.out.println();

        System.out.println("Variance of Time Series:");
        System.out.println(_tm.getVariance());
        System.out.println();

        System.out.println("Standard Deviation of Time Series:");
        System.out.println(_tm.getStandardDeviation());
        System.out.println();

        System.out.println("Index of Time Series Minimum Value:");
        System.out.println(_tm.getMinIndex());
        System.out.println();

        System.out.println("Minimum Value of Time Series Based on Index:");
        System.out.println(_tm.getMin());
        System.out.println();

        System.out.println("Index of Time Series Maximum Value:");
        System.out.println(_tm.getMaxIndex());
        System.out.println();

        System.out.println("Maximum Value of Time Series Based on Index:");
        System.out.println(_tm.getMax());
        System.out.println();

        System.out.println("Autocovariance of Time Series:");
        System.out.println(_tm.getAutocovariance());
        System.out.println();

        System.out.println("Autocorrelation of Time Series with lag " + lag + ":");
        System.out.println(_tm.getAutocorrelation());
        System.out.println();

        System.out.println("Autocorrelation Function of Time Series with lag " + lag + ":");
        for (double v : _tm.acf(2)) System.out.println(v);
        System.out.println();

        System.out.println("Partial Autocorrelation Function of Time Series with lag " + lag + ":");
        for (double v : _tm.pacf()) System.out.println(v);
        System.out.println();

        System.out.println("Augmented Dickey-Fuller Test");
        AugmentedDickeyFuller adf = new AugmentedDickeyFuller(file);
        System.out.println(adf.isNeedsDiff());
        System.out.println(adf.getAdfStat());
        System.out.println(adf.getLag());
        System.out.println();

        System.out.println("Simple Moving Average:");
        SimpleMovingAverage movingAverage = new SimpleMovingAverage(2);
        System.out.println(movingAverage.getMA(file));
        System.out.println();

        int period = 12;
        int m = 2;
        double alpha = 0.5411;
        double beta = 0.0086;
        double gamma = 1e-04;
        boolean debug = true;

        List<Double> prediction = TripleExpSmoothing.forecast(file, alpha, beta, gamma, period, m, debug);
        System.out.println("Triple Exponential Smoothing Forecast:");
        prediction.forEach(System.out::println);
        System.out.println();

        System.out.println("Cumulative Moving Average:");
        CumulativeMovingAverage cumulativeMovingAverage = new CumulativeMovingAverage();
        System.out.println(cumulativeMovingAverage.getCMA(file));
        System.out.println();

        System.out.println("Exponential Moving Average:");
        ExponentialMovingAverage exponentialMovingAverage = new ExponentialMovingAverage(0.3);
        System.out.println(exponentialMovingAverage.getEMA(file));
        System.out.println();

        System.out.println("Single Exponential Smoothing Forecast:");
        SingleExpSmoothing singleExpSmoothing = new SingleExpSmoothing(0.5);
        List<Double> fcast = singleExpSmoothing.forecast(file, 2);
        fcast.forEach(System.out::println);
    }
}
