package main.java.util;

import main.java.collect.TSCollect;
import main.java.util.TSUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;

import org.apache.commons.math3.optimization.univariate.*;

/**Extract the Box-Cox parameter, lambda, using Guerrero's method (1993)
 *
 * @author navdeepgill
 */
public class BoxCoxLambdaSearch extends TSCollect {

    private final int lower;
    private final int upper;
    private final int nonseasonalLength = 2;

    public BoxCoxLambdaSearch(String filepath, int k, int n, int lowLam, int upperLam) throws IOException{
        super(filepath, k, n);
        lower = lowLam;
        upper = upperLam;
    }

    public double guer_cv(int lam) throws IOException{
        int period = Math.round(Math.max(nonseasonalLength, _data.size()));
        int nobsf = ReadFile().size();
        int nyr = (int)Math.floor(nobsf/period);
        int nobst = (int)Math.floor(nyr/period);

        Iterator<Double> i = _data.iterator();
        while(i.hasNext()){
            double guer_mean = i.next();
            double guer_sd = guer_mean;
            if(i.hasNext()){
                double next = i.next();
                guer_mean = getAverage(i.next() + guer_mean)/2;
                guer_sd = guer_mean ;
            }

        }

    }
}
