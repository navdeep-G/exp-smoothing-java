package test.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.java.collect.TSCollect;
import main.java.util.transform.BoxCox;
import main.java.util.transform.BoxCoxLambdaSearch;
import main.java.util.transform.TSTransform;
import main.java.util.TSUtil;

/**
 * @author navdeepgill
 */

public class TSCollectTest {

    //Define initial inputs for TSCollect
    public static int lag = 1;
    public static  String pathToData = "data/birth.txt";
    public static int lambda = 0;

     //Quick check of output from previous methods.
    public static void main (String[] args) throws IOException
    {
        TSCollect _tm  = new TSCollect(pathToData,lag,lag);
        List<Double> file = TSUtil.ReadFile(pathToData);
        List<Double> fileLog = TSTransform.sqrt(file);
        double optimalLam = BoxCoxLambdaSearch.guerrero(file,1,2,2);

        System.out.println(optimalLam);

        System.out.println("Log data of Time Series: " + pathToData);
        for(int i = 0; i < fileLog.size(); i++) {
            System.out.println(fileLog.get(i));
        }
        System.out.println("\n");

        System.out.println("First 10 Rows of Time Series Dataset: " + pathToData);
        for(int i = 0; i < 10; i++) {
            System.out.println(file.get(i));
        }
        System.out.println("\n");

        System.out.println("Number of rows for " + pathToData + " = " + file.size());
        System.out.println("\n");

        System.out.println("Average of Time Series:");
        double average = _tm.getAverage();
        System.out.println(average);
        System.out.println("\n");

        System.out.println("Variance of Time Series:");
        double var = _tm.getVariance();
        System.out.println(var);
        System.out.println("\n");

        System.out.println("Standard Deviation of Time Series:");
        double sd = _tm.getStandardDeviation();
        System.out.println(sd);
        System.out.println("\n");

        System.out.println("Index of Time Series Minimum Value:");
        double min = _tm.getMinIndex();
        System.out.println(min);
        System.out.println("\n");

        System.out.println("Minimum Value of Time Series Based on Index:");
        double minVal = _tm.getMin();
        System.out.println(minVal);
        System.out.println("\n");

        System.out.println("Index of Time Series Maximum Value:");
        double max = _tm.getMaxIndex();
        System.out.println(max);
        System.out.println("\n");

        System.out.println("Maximum Value of Time Series Based on Index:");
        double maxVal = _tm.getMax();
        System.out.println(maxVal);
        System.out.println("\n");

        System.out.println("Autocovariance of Time Series:");
        double autocovar = _tm.getAutocovariance();
        System.out.println(autocovar);
        System.out.println("\n");

        System.out.println("Autocorrelation of Time Series with lag " + lag + ":");
        double autocor = _tm.getAutocorrelation();
        System.out.println(autocor);
        System.out.println("\n");

        System.out.println("Autocorrelation Function of Time Series with lag " + lag + ":");
        double[] acf= _tm.acf(2);
        for(int i = 0; i < acf.length; i++) {
            System.out.println(acf[i]);
        }
        System.out.println("\n");

        System.out.println("Partial Autocorrelation Function of Time Series with lag " +  lag + ":");
        double[] pacf= _tm.pacf();
        for(int i = 0; i < pacf.length; i++) {
            System.out.println(pacf[i]);
        }
    }
}
