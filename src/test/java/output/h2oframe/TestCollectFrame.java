package output.h2oframe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import algos.ar.AutoRegression;
import util.*;
import water.H2O;
import water.fvec.Frame;
import util.TestUtil;
import algos.expsmoothing.SingleExpSmoothing;

/**Output relevant calculations from a time series dataset.
 * Used as verification of calculations for now.
 *
 * @author navdeepgill
 */

public class TestCollectFrame extends TestUtil {

    //Define initial inputs for Collect
    public static int lag = 2;
    public static  String pathToData = "data/hotel.txt";
    public static double lambda = 1.6;

    //Quick check of output from previous methods.
    public static void main (String[] args) throws Exception
    {
        //Make some objects that will show relevant output:
        System.out.println("Starting H2O...");
        H2O.main(new String[] {});
        //Setting up a dataset as a List<Double> for later use:
        System.out.println("Reading File to List...");
        List<Double> file = new ArrayList<Double>();
        List<Double> file_ses = new ArrayList<Double>();
        try {
            file = Util.ReadFile(pathToData);
            file_ses = Util.ReadFile(pathToData);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }

//        System.out.println("Reading File to Frame...");
//        Frame fr = parse_test_file(pathToData);
//        Frame fr_sma = parse_test_file(pathToData);
//        Frame fr_ema = parse_test_file(pathToData);
//        Frame fr_cma = parse_test_file(pathToData);
//        Frame fr_ses = parse_test_file(pathToData);

//        //Calling on Collect to get a bunch of metrics:
//        //Collect _tm  = new Collect(pathToData,lag,lag);
//
//        //Test out a transformation on the dataset:
//        List<Double> fileLog = Transform.sqrt(file);
//
//         //Get the optimal lambda transform for a particular dataset:
//         double optimalLam = BoxCox.lambdaSearch(file);
//
//         //Give lambda manually
//         List<Double> fileBoxCox = BoxCox.transform(file,lambda);
//
//         //Output of calculations and verify:
//         System.out.println("Optimal Lambda for the time series: " + pathToData + " is " + optimalLam);
//         System.out.println("\n");
//
//         System.out.println("Manually calculated Box Cox transformation of data with lambda = " + lambda);
//         for(int i = 0; i < fileBoxCox.size(); i++) {
//         System.out.println(fileBoxCox.get(i));
//         }
//         System.out.println("\n");
//
//         double[] lam_opt = BoxCoxFrame.lambdaSearch(fr);
//
//        System.out.print("Optimal Lambdas for the time series: " + pathToData + " is " );
//        System.out.print("[ ");
//        for (double d : lam_opt) {
//            System.out.print(d + " ");
//        }
//        System.out.println("]");
//        System.out.println("\n");
//        Frame fr_boxcox = fr.deepCopy(null);
//        fr_boxcox = BoxCoxFrame.transform(fr_boxcox,new double[] {lambda});
//
//        System.out.println("Manually calculated Frame Box Cox transformation of data with lambda = " + lambda);
//        System.out.println(fr_boxcox);
//        System.out.println("\n");
//
//        fr_boxcox.delete();
//
//
//         System.out.println("Log data of Time Series: " + pathToData);
//         for (double i : Transform.log(file)) {
//             System.out.println(i);
//         }
//         System.out.println("\n");
//
//         System.out.println("Log data of Time Series with Frame: " + pathToData);
//         Frame log_fr = TransformFuncsFrame.log(fr.deepCopy(null));
//         for (long i = 0; i < fr.numRows(); ++i) {
//             System.out.println(log_fr.vec(0).at(i));
//         }
//         System.out.println("\n");
//
//
//        System.out.println("Average of Time Series:");
//        double average = Stats.average(file);
//        System.out.println(average);
//        System.out.println("\n");
//
//        System.out.println("Average of Time Series Using Frame:");
//        double[] averageFrame = StatsFrame.average(fr);
//        System.out.println(averageFrame[0]);
//        System.out.println("\n");
//
//        System.out.println("Variance of Time Series:");
//        double variance = Stats.variance(file);
//        System.out.println(variance);
//        System.out.println("\n");
//
//        System.out.println("Variance of Time Series Using Frame:");
//        double[] varFrame = StatsFrame.variance(fr);
//        System.out.println(varFrame[0]);
//        System.out.println("\n");
//
//        System.out.println("Standard Deviation of Time Series:");
//        double sd = Stats.standardDeviation(file);
//        System.out.println(sd);
//        System.out.println("\n");
//
//        System.out.println("Standard Deviation of Time Series Using Frame:");
//        double[] sdFrame = StatsFrame.standardDeviation(fr);
//        System.out.println(sdFrame[0]);
//        System.out.println("\n");
//
//        System.out.println("Minimum Value of Time Series:");
//        double minVal = Stats.getMinimum(file);
//        System.out.println(minVal);
//        System.out.println("\n");
//
//        System.out.println("Minimum Value of Time Series Using Frame:");
//        double[] minValFrame = StatsFrame.getMin(fr);
//        System.out.println(minValFrame[0]);
//        System.out.println("\n");
//
//        System.out.println("Maximum Value of Time Series:");
//        double maxVal = Stats.getMaximium(file);
//        System.out.println(maxVal);
//        System.out.println("\n");
//
//        System.out.println("Maximum Value of Time Series Using Frame:");
//        double[] maxValFrame = StatsFrame.getMax(fr);
//        System.out.println(maxValFrame[0]);
//        System.out.println("\n");
//
//        System.out.println("Autocovariance of Time Series:");
//        double autocovar = Stats.getAutoCovariance(file, 1);
//        System.out.println(autocovar);
//        System.out.println("\n");
//
//        System.out.println("Autocovariance of Time Series Using Frame:");
//        double[] autocovarFrame = StatsFrame.getAutoCovariance(fr,1);
//        System.out.println(autocovarFrame[0]);
//        System.out.println("\n");
//
//        System.out.println("Autocorrelation of Time Series with lag " + lag + ":");
//        double autocor = Stats.getAutoCorrelation(file, lag);
//        System.out.println(autocor);
//        System.out.println("\n");
//
//        System.out.println("Autocorrelation of Time Series Using Frame with lag " + lag + ":");
//        double[] autocorFrame = StatsFrame.getAutoCorrelation(fr,lag);
//        System.out.println(autocorFrame[0]);
//        System.out.println("\n");
//
//        System.out.println("Autocorrelation Function of Time Series with lag " + lag + ":");
//        double[] acf= Stats.getAcf(file,lag);
//        for(int i = 0; i < acf.length; i++) {
//            System.out.println(acf[i]);
//        }
//        System.out.println("\n");
//
//        System.out.println("Autocorrelation Function of Time Series Using Frame with lag " + lag + ":");
//        double[][] acfFrame= StatsFrame.getAcf(fr,lag);
//        System.out.println(Arrays.deepToString(acfFrame));
//        System.out.println("\n");
//
//        System.out.println("Partial Autocorrelation Function of Time Series with lag " +  lag + ":");
//        double[] pacf= Stats.getPacf(file,lag);
//        for(int i = 0; i < pacf.length; i++) {
//            System.out.println(pacf[i]);
//        }
//        System.out.println("\n");
//
//        System.out.println("Partial Autocorrelation Function of Time Series Using Frame with lag " +  lag + ":");
//        double[][] pacfFrame= StatsFrame.getPacf(fr,lag);
//        System.out.println(Arrays.deepToString(pacfFrame));
//        System.out.println("\n");
//
//        System.out.println("\n");
//
//        System.out.println("SMA");
//        SimpleMovingAverage movingAverage = new SimpleMovingAverage(2);
//        System.out.println(movingAverage.getMA(file));
//
//        System.out.println("\n");
//
//        System.out.println("SMA (Frame)");
//        SimpleMovingAverageFrame movingAverageFrame = new SimpleMovingAverageFrame(2);
//        System.out.println(movingAverageFrame.getMA(fr_sma));
//
//
//        System.out.println("\n");
//        System.out.println("Exponential Moving Average:");
//
//        ExponentialMovingAverage exponentialMovingAverage = new ExponentialMovingAverage(0.2);
//
//        System.out.println(exponentialMovingAverage.getEMA(file));
//
//        System.out.println("\n");
//        System.out.println("Exponential MA (Frame)");
//        ExponentialMovingAverageFrame exponentialMovingAverageFrame = new ExponentialMovingAverageFrame(0.2);
//        System.out.println(exponentialMovingAverageFrame.getEMA(fr_ema));
//
//        System.out.println("\n");
//        System.out.println("Cumulative Moving Average:");
//
//        CumulativeMovingAverage cumulativeMovingAverage = new CumulativeMovingAverage();
//
//        System.out.println(cumulativeMovingAverage.getCMA(file));
//
//        System.out.println("\n");
//        System.out.println("Cumulative Moving Average with a Frame:");
//
//        CumulativeMovingAverageFrame cumulativeMovingAverageFrame = new CumulativeMovingAverageFrame();
//
//        System.out.println(cumulativeMovingAverageFrame.getCMA(fr_cma));


         /*


         System.out.println("Augmented Dickey-Fuller Test");
         AugmentedDickeyFuller adf = new AugmentedDickeyFuller(file);
         System.out.println(adf.isNeedsDiff());
         System.out.println(adf.getAdfStat());
         System.out.println(adf.getLag());

         System.out.println("\n");

         SimpleMovingAverage movingAverage = new SimpleMovingAverage(2);
         System.out.println(movingAverage.getMA(file));

         System.out.println("\n");
        */
//         System.out.println("Starting Triple Exp Smoothing; ");
//         int period = 12;
//         int m = 2;
//         double alpha =  0.5411;
//         double beta =  0.0086;
//         double gamma = 1e-04;
//         boolean debug = true;
//
//         List<Double> prediction = TripleExpSmoothing.forecast(file, alpha, beta, gamma, period, m, debug);
//         System.out.println(prediction.size());
//
//         List<Double> prediction_fr = TripleExpSmoothingFrame.forecast(fr, alpha, beta, gamma, period, m, debug);
//         System.out.println(prediction_fr.size());

         //Checking out autoregression
//        double[] x = { 13.5, 18.4, 19.6, 21.4, 25, 26};
//        int ar_order = 2;
//        double[] ar = {ar_order};
//        double[] armodel = AutoRegression.calculateARCoefficients(x, ar_order, false, "LS");
//        for(int i =0; i < armodel.length; ++i){
//            System.out.println("AR coefficient " + i + " is " + armodel[i]);
//        }
//
//        double[] est = AutoRegression.calculateEstimation(x,armodel,false,5);
//        for(int i =0; i < est.length; ++i){
//            System.out.println("AR estimation for index " + i + " is " + est[i]);
//        }
//
//        double rmse = AutoRegression.calculateRMSE(x,armodel,false);
//        System.out.println("RMSE: " + rmse);

         /*
         System.out.println("\n");
         System.out.println("Cumulative Moving Average:");

         CumulativeMovingAverage cumulativeMovingAverage = new CumulativeMovingAverage();

         System.out.println(cumulativeMovingAverage.getCMA(file));

         System.out.println("\n");
         System.out.println("Exponential Moving Average:");

         ExponentialMovingAverage exponentialMovingAverage = new ExponentialMovingAverage(0.5);

         System.out.println(exponentialMovingAverage.getEMA(file));

         System.out.println("\n");
         System.out.println("Single EWMA:");
         */

         SingleExpSmoothing singleExpSmoothing = new SingleExpSmoothing();

         double[] fcast = singleExpSmoothing.singleExponentialForecast(file_ses,.5,2);
         for(int i = 0; i < fcast.length; i++){
            System.out.println(fcast[i]);
         }

//        SingleExpSmoothingFrame singleExpSmoothingFrame = new SingleExpSmoothingFrame();
//
//        double[] fcastFrame = singleExpSmoothingFrame.singleExponentialForecastFrame(fr_ses, .5, 2);
//        for(int i = 0; i < fcastFrame.length; i++){
//            System.out.println(fcastFrame[i]);
//        }

        H2O.closeAll();

    }
}
