package main.java.util.transform;

import main.java.collect.TSCollect;
import main.java.util.TSUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.univariate.*;

/**Extract the Box-Cox parameter, lambda, using Guerrero's method (1993)
 *
 * @author navdeepgill
 */
public class BoxCoxLambdaSearch {
    
    private static double guer_cv(List<Double> data, double lam){
        Iterator<Double> iter = data.iterator();
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

    public static double guerrero(final List<Double> data, double lower, double upper, int n_length){
        UnivariateOptimizer solver = new BrentOptimizer(1e-10, 1e-14);
        double lambda = solver.optimize(100,new UnivariateFunction(){
            public double value(double x){
                return guer_cv(data,x);
            }
        }, GoalType.MINIMIZE,lower, upper).getPoint();
        return lambda;
    }
}
