package transform;

import util.Stats;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.univariate.BrentOptimizer;
import org.apache.commons.math3.optimization.univariate.UnivariateOptimizer;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;

/**
 * Methods to Transform Time Series (log, root, and Box-Cox transformations)
 *
 * @author navdeepgill
 */
public class Transform {

    public static List<Double> log(List<Double> data) {
        List<Double> t_list = new ArrayList<Double>(data.size());
        for (double i : data) {
            t_list.add(Math.log(i));
        }
        return t_list;
    }

    public static List<Double> sqrt(List<Double> data) {
        List<Double> t_list = new ArrayList<Double>(data.size());
        for (double i : data) {
            t_list.add(Math.sqrt(i));
        }
        return t_list;
    }

    public static List<Double> cbrt(List<Double> data) {
        List<Double> t_list = new ArrayList<Double>(data.size());
        for (double i : data) {
            t_list.add(Math.cbrt(i));
        }
        return t_list;
    }

    public static List<Double> root(List<Double> data, double root) {
        List<Double> t_list = new ArrayList<Double>(data.size());
        for (double i : data) {
            t_list.add(Math.pow(i, 1.0 / root));
        }
        return t_list;
    }

    /**
     * Perform Box-Cox Transformation with automatically chosen optimal lambda
     *
     * @param data A List<Double> of time series data
     * @return Transformed time series with optimal lambda
     */
    public static List<Double> boxCox(List<Double> data) {
        return boxCox(data, boxCoxLambdaSearch(data));
    }

    /**
     * Perform Box-Cox Transformation with a given lambda
     *
     * @param data A List<Double> of time series data
     * @param lam Desired lambda
     * @return Transformed time series
     */
    public static List<Double> boxCox(List<Double> data, double lam) {
        List<Double> transform = new ArrayList<Double>(data.size());
        if (lam == 0) {
            for (double val : data) {
                transform.add(Math.log(val));
            }
        } else {
            for (double val : data) {
                transform.add((Math.pow(val, lam) - 1.0) / lam);
            }
        }
        return transform;
    }

    /**
     * Find the optimal lambda for Box-Cox Transformation using default bounds
     *
     * @param data A List<Double> of time series data
     * @return Optimal lambda
     */
    public static double boxCoxLambdaSearch(final List<Double> data) {
        return boxCoxLambdaSearch(data, -1, 2);
    }

    /**
     * Find the optimal lambda for Box-Cox Transformation using custom bounds
     *
     * @param data  A List<Double> of time series data
     * @param lower Lower bound for lambda
     * @param upper Upper bound for lambda
     * @return Optimal lambda
     */
    public static double boxCoxLambdaSearch(final List<Double> data, double lower, double upper) {
        UnivariateOptimizer solver = new BrentOptimizer(1e-10, 1e-14);
        return solver.optimize(100, new UnivariateFunction() {
            public double value(double x) {
                return lambdaCV(data, x);
            }
        }, GoalType.MINIMIZE, lower, upper).getPoint();
    }

    /**
     * Compute the coefficient of variation for a given lambda
     *
     * @param data A List<Double> of time series data
     * @param lam  Lambda
     * @return Coefficient of Variation
     */
    private static double lambdaCV(List<Double> data, double lam) {
        Iterator<Double> iter = data.iterator();
        List<Double> avg = new ArrayList<Double>();
        List<Double> result = new ArrayList<Double>();

        while (iter.hasNext()) {
            List<Double> l = new ArrayList<Double>();
            l.add(iter.next());
            if (iter.hasNext()) l.add(iter.next());
            avg.add(Stats.average(l));
            result.add(Stats.standardDeviation(l));
        }

        for (int i = 0; i < result.size(); i++) {
            result.set(i, result.get(i) / Math.pow(avg.get(i), 1 - lam));
        }

        return Stats.standardDeviation(result) / Stats.average(result);
    }
}
