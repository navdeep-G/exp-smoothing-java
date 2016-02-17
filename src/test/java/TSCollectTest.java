package test.java;

import java.io.IOException;
import java.util.ArrayList;
import main.java.collect.TSCollect;
/**
 * @author navdeepgill
 */

public class TSCollectTest {

    /**
     Quick check of output from previous methods.
     */
    public static void main (String[] args) throws IOException
    {
        TSCollect _tm = new TSCollect("data/birth.txt",2,2);

        ArrayList<Double> file = _tm.ReadFile();
        for(int i = 0; i < file.size(); i++) {
            System.out.println(file.get(i));
        }

        double average = _tm.getAverage();
        System.out.println(average);

        double var = _tm.getVariance();
        System.out.println(var);

        double autocovar = _tm.getAutocovariance(2);
        System.out.println(autocovar);

        double autocor = _tm.getAutocorrelation(2);
        System.out.println(autocor);

        double[] acf= _tm.acf(2);
        for(int i = 0; i < acf.length; i++) {
            System.out.println(acf[i]);
        }

        double[] pacf= _tm.pacf(2);
        for(int i = 0; i < pacf.length; i++) {
            System.out.println(pacf[i]);
        }
    }
}
