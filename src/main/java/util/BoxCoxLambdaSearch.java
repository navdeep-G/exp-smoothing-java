package main.java.util;

import main.java.collect.TSCollect;
import main.java.util.TSUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;
import java.util.List;
import java.util.ListIterator;

//import org.apache.commons.math3.optimization.univariate.*;

/**Extract the Box-Cox parameter, lambda, using Guerrero's method (1993)
 *
 * @author navdeepgill
 */
public class BoxCoxLambdaSearch extends TSCollect {

    private final int lower;
    private final int upper;

    public BoxCoxLambdaSearch(String filepath, int k, int n, int lowLam, int upperLam) throws IOException{
        super(filepath, k, n);
        lower = lowLam;
        upper = upperLam;
    }

    public double guer_cv(int lam) throws IOException {
        Iterator<Double> iter = _data.iterator();
        List<Double> avg = new ArrayList<Double>();
        List<Double> result = new ArrayList<Double>();
        while(iter.hasNext()) {
            List<Double> l = new ArrayList<Double>();
            l.add(iter.next());
            if(iter.hasNext()) l.add(iter.next());
            avg.add(TSUtil.average(l));
            result.add(TSUtil.standardDeviation(l));
        }
        for (int i = 0; i < result.size(); i+=1) {
            result.set(i, result.get(i) / Math.pow(avg.get(i), 1 - lam));
        }
        return TSUtil.standardDeviation(result)/TSUtil.average(result);
    }
}
