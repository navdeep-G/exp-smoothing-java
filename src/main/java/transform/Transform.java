package transform;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.univariate.BrentOptimizer;
import org.apache.commons.math3.optimization.univariate.UnivariateOptimizer;

/**
 * Methods to Transform Time Series (log, root, and Box-Cox transformations)
 *
 * @author navdeepgill
 */
public class Transform {

    public static List<Double> log(List<Double> data) {
        validatePositive(data);
        List<Double> t_list = new ArrayList<>(data.size());
        for (double val : data) {
            t_list.add(Math.log(val));
        }
        return t_list;
    }

    public static List<Double> sqrt(List<Double> data) {
        validatePositive(data);
        List<Double> t_list = new ArrayList<>(data.size());
        for (double val : data) {
            t_list.add(Math.sqrt(val));
        }
        return t_list;
    }

    public static List<Double> cbrt(List<Double> data) {
        List<Double> t_list = new ArrayList<>(data.size());
        for (double val : data) {
            t_list.add(Math.cbrt(val));
        }
        return t_list;
    }

    public static List<Double> root(List<Double> data, double root) {
        List<Double> t_list = new ArrayList<>(data.size());
        for (double val : data) {
            t_list.add(Math.pow(val, 1.0 / root));
        }
        return t_list;
    }

    /**
     * Perform Box-Cox Transformation with automatically chosen optimal lambda
     *
     * @param data A List<Double> of time series data (must be strictly positive)
     * @return Transformed time series with optimal lambda
     */
    public static List<Double> boxCox(List<Double> data) {
        return boxCox(data, boxCoxLambdaSearch(data));
    }

    /**
     * Perform Box-Cox Transformation with a given lambda
     *
     * @param data A List<Double> of time series data (must be strictly positive)
     * @param lam Desired lambda
     * @return Transformed time series
     */
    public static List<Double> boxCox(List<Double> data, double lam) {
        validatePositive(data);
        List<Double> transform = new ArrayList<>(data.size());
        for (double val : data) {
            transform.add(lam == 0 ? Math.log(val) : (Math.pow(val, lam) - 1.0) / lam);
        }
        return transform;
    }

    /**
     * Find the optimal lambda for Box-Cox Transformation using default bounds
     *
     * @param data A List<Double> of time series data (must be strictly positive)
     * @return Optimal lambda
     */
    public static double boxCoxLambdaSearch(List<Double> data) {
        return boxCoxLambdaSearch(data, -1, 2);
    }

    /**
     * Find the optimal lambda for Box-Cox Transformation using custom bounds
     *
     * @param data  A List<Double> of time series data (must be strictly positive)
     * @param lower Lower bound for lambda
     * @param upper Upper bound for lambda
     * @return Optimal lambda
     */
    public static double boxCoxLambdaSearch(final List<Double> data, double lower, double upper) {
        validatePositive(data);
        UnivariateOptimizer solver = new BrentOptimizer(1e-10, 1e-14);
        return solver.optimize(100, new UnivariateFunction() {
            public double value(double lam) {
                return boxCoxNegLogLikelihood(data, lam);
            }
        }, GoalType.MINIMIZE, lower, upper).getPoint();
    }

    /**
     * Negative log-likelihood function for Box-Cox transformation.
     * Minimizes log of variance of transformed values.
     *
     * @param data Time series data
     * @param lam  Lambda parameter
     * @return Log-likelihood objective value (lower is better)
     */
    private static double boxCoxNegLogLikelihood(List<Double> data, double lam) {
        double[] transformed = new double[data.size()];
        double sum = 0.0;

        for (int i = 0; i < data.size(); i++) {
            double x = data.get(i);
            if (x <= 0) return Double.POSITIVE_INFINITY;  // Box-Cox not defined
            transformed[i] = (lam == 0) ? Math.log(x) : (Math.pow(x, lam) - 1.0) / lam;
            sum += transformed[i];
        }

        double mean = sum / data.size();
        double variance = 0.0;
        for (double v : transformed) {
            variance += Math.pow(v - mean, 2);
        }
        variance /= data.size();

        return Math.log(variance);
    }

    /**
     * Validates that all input values are strictly positive.
     *
     * @param data A List<Double> to check
     */
    private static void validatePositive(List<Double> data) {
        for (double val : data) {
            if (val <= 0) {
                throw new IllegalArgumentException("All values must be > 0 for log/Box-Cox transforms.");
            }
        }
    }
}
