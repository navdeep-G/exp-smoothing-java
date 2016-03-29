package transform;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.univariate.BrentOptimizer;
import org.apache.commons.math3.optimization.univariate.UnivariateOptimizer;
import util.Stats;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import water.fvec.Frame;
import water.fvec.Vec;

/**Box Cox Transformation
 *
 * @author navdeepgill
 */
public class BoxCoxFrame {


    /**
     *Find the optimal lambda for a given time series data set and conduct transformation
     *
     *@param  data a List<Double> of time series data
     *
     *@return  Time series List<Double> with optimal Box Cox lambda transformation
     */
    public static Frame transform(Frame data) {
        return transform(data, lambdaSearch(data));
    }

    /**
     *Calculate a Box Cox Transformation for a given lambda
     *
     *@param  data a List<Double> of time series data
     *@param lam desired lambda for transformation
     *
     *@return  Time series List<Double> with desired Box Cox transformation
     */
     public static Frame transform(Frame data, final double[] lam) {
         for (int c = 0; c < data.numCols(); ++c) {
             final double lambda = lam[c];
             TransformFrame t;
             if (lambda == 0) {
                 t = new TransformFrame() {
                     @Override
                     public double transform(double d) {
                         return (Math.log(d));
                     }
                 };
             } else {
                 t = new TransformFrame() {
                     @Override
                     public double transform(double d) {
                         return (Math.pow(d, lambda) - 1.0) / lambda;
                     }
                 };
             }
             t.transform(data.vec(c));
         }
         return data;
     }

    /**
     *Find the optimal lambda for a given time series data set with default lower/upper bounds for lambda search
     *
     *@param  data a List<Double> of time series data
     *
     *@return  Time series List<Double> with optimal Box Cox lambda transformation
     */
    public static double[] lambdaSearch(final Frame data) {
        return lambdaSearch(data, -1, 2);
    }

    /**
     *Find the optimal lambda for a given time series data set given lower/upper bounds for lambda search
     *
     *@param  data a List<Double> of time series data
     *@param lower lower bound for lambda search
     *@param upper upper bound for lambda search
     *
     * @return  Time series List<Double> with optimal Box Cox lambda transformation
     */
    public static double[] lambdaSearch(final Frame data, double lower, double upper){
        UnivariateOptimizer solver = new BrentOptimizer(1e-10, 1e-14);
        double[] lambda = new double[data.numCols()];
        for (int c = 0; c < data.numCols(); ++c) {
            final Vec v = data.vec(c);
            lambda[c] = solver.optimize(100, new UnivariateFunction() {
                public double value(double x) {
                    return lambdaCV(v, x);
                }
            }, GoalType.MINIMIZE, lower, upper).getPoint();
        }
        return lambda;
    }

    /**
     * Compute the coefficient of variation
     *
     * @param v a List<Double> of time series data
     * @param lam lambda
     *
     * @return Coefficient of Variation
     */
    private static double lambdaCV(Vec v, double lam) {
        long l = v.length();
        l = l % 2 == 0 ? l : l + 2;
        double avg = 0;
        for (long i = 0; i < v.length(); ++i) {
            avg += v.at(i);
        }
        avg /= l;
        return avg;
        /*Iterator<Double> iter = v.iterator();
        List<Double> avg = new ArrayList<Double>();
        List<Double> result = new ArrayList<Double>();
        while(iter.hasNext()) {
            List<Double> l = new ArrayList<Double>();
            l.add(iter.next());
            if(iter.hasNext()) l.add(iter.next());
            avg.add(Stats.average(l));
            result.add(Stats.standardDeviation(l));
        }
        for (int i = 0; i < result.size(); i+=1) {
            result.set(i, result.get(i) / Math.pow(avg.get(i), 1 - lam));
        }
        return Stats.standardDeviation(result)/Stats.average(result);*/
    }
}
