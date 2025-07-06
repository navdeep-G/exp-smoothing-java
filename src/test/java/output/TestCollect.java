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
 */
public class TestCollect {

    public static int lag = 1;
    public static String pathToData = "data/hotel.txt";
    public static double lambda = 1.6;

    public static void main(String[] args) throws IOException {
        List<Double> file = Util.ReadFile(pathToData);
        Collect _tm = new Collect(pathToData, lag, lag);

        List<Double> fileLog = Transform.sqrt(file);
        double optimalLam = BoxCox.lambdaSearch(file);
        List<Double> fileBoxCox = BoxCox.transform(file, lambda);

        System.out.println("Optimal Lambda: " + optimalLam + "\n");
        System.out.println("Box Cox transformation (λ = " + lambda + "):");
        fileBoxCox.forEach(System.out::println);
        System.out.println();

        System.out.println("Log (sqrt) transformation:");
        fileLog.forEach(System.out::println);
        System.out.println();

        System.out.println("First 10 values of file:");
        file.stream().limit(10).forEach(System.out::println);
        System.out.println("Total rows: " + file.size() + "\n");

        System.out.println("Average: " + _tm.getAverage());
        System.out.println("Variance: " + _tm.getVariance());
        System.out.println("Std Dev: " + _tm.getStandardDeviation());
        System.out.println("Min Index: " + _tm.getMinIndex());
        System.out.println("Min Value: " + _tm.getMin());
        System.out.println("Max Index: " + _tm.getMaxIndex());
        System.out.println("Max Value: " + _tm.getMax());
        System.out.println("Autocovariance: " + _tm.getAutocovariance());
        System.out.println("Autocorrelation (lag " + lag + "): " + _tm.getAutocorrelation());

        System.out.println("ACF:");
        for (double v : _tm.acf(2)) System.out.println(v);
        System.out.println("PACF:");
        for (double v : _tm.pacf()) System.out.println(v);

        System.out.println("\nAugmented Dickey-Fuller Test:");
        AugmentedDickeyFuller adf = new AugmentedDickeyFuller(file);
        System.out.println("Needs Differencing? " + adf.isNeedsDiff());
        System.out.println("ADF Statistic: " + adf.getAdfStat());
        System.out.println("ADF Lag: " + adf.getLag());

        System.out.println("\nSimple Moving Average:");
        SimpleMovingAverage sma = new SimpleMovingAverage(2);
        System.out.println(sma.compute(file));

        System.out.println("\nCumulative Moving Average:");
        CumulativeMovingAverage cma = new CumulativeMovingAverage();
        System.out.println(cma.getCMA(file));

        System.out.println("\nExponential Moving Average:");
        ExponentialMovingAverage ema = new ExponentialMovingAverage(0.3);
        System.out.println(ema.compute(file));

        System.out.println("\nSingle Exponential Smoothing:");
        SingleExpSmoothing ses = new SingleExpSmoothing(0.5);
        List<Double> singleForecast = ses.forecast(file, 2);
        singleForecast.forEach(System.out::println);

        System.out.println("\nTriple Exponential Smoothing:");
        int period = 12;
        int m = 2;
        double alpha = 0.5411;
        double beta = 0.0086;
        double gamma = 1e-04;
        boolean debug = false;

        TripleExpSmoothing tes = new TripleExpSmoothing(alpha, beta, gamma, period, debug);
        List<Double> tripleForecast = tes.forecast(file, m);
        tripleForecast.forEach(System.out::println);
    }
}
