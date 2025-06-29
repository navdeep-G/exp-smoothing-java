package transform;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.univariate.BrentOptimizer;
import org.apache.commons.math3.optimization.univariate.UnivariateOptimizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Box-Cox Transformation Utility
 * Applies the Box-Cox transformation to time series data and finds optimal lambda using maximum likelihood.
 */
public class BoxCox {

    /**
     * Apply Box-Cox transformation with automatically selected optimal lambda.
     *
     * @param data A List<Double> of strictly positive time series values
     * @return Transformed time series data
     */
    public static List<Double> transform(List<Double> data) {
        return transform(data, lambdaSearch(data));
    }

    /**
     * Apply Box-Cox transformation with a specified lambda.
     *
     * @param data A List<Double> of strictly positive time series values
     * @param lam  Desired lambda value
     * @return Transformed time series data
     */
    public static List<Double> transform(List<Double> data, double lam) {
        validateInput(data);

        List<Double> transformed = new ArrayList<>();
        for (double x : data) {
            if (lam == 0) {
                transformed.add(Math.log(x));
            } else {
                transformed.add((Math.pow(x, lam) - 1.0) / lam);
            }
        }
        return transformed;
    }

    /**
     * Automatically find the optimal lambda in the default range [-1, 2].
     *
     * @param data A List<Double> of strictly positive time series values
     * @return Optimal lambda value
     */
    public static double lambdaSearch(final List<Double> data) {
        return lambdaSearch(data, -1, 2);
    }

    /**
     * Find the optimal lambda within a custom range by minimizing the log-likelihood-based objective.
     *
     * @param data  A List<Double> of strictly positive time series values
     * @param lower Lower bound of lambda search
     * @param upper Upper bound of lambda search
     * @return Optimal lambda value
     */
    public static double lambdaSearch(final List<Double> data, double lower, double upper) {
        validateInput(data);

        UnivariateOptimizer solver = new BrentOptimizer(1e-10, 1e-14);
        double lambda = solver.optimize(100, new UnivariateFunction() {
            @Override
            public double value(double x) {
                return boxCoxNegLogLikelihood(data, x);
            }
        }, GoalType.MINIMIZE, lower, upper).getPoint();

        return lambda;
    }

    /**
     * Box-Cox objective function: negative log-likelihood approximation.
     *
     * @param data A List<Double> of strictly positive time series values
     * @param lam  Lambda value
     * @return Log variance of transformed values (proxy for likelihood)
     */
    private static double boxCoxNegLogLikelihood(List<Double> data, double lam) {
        int n = data.size();
        double[] transformed = new double[n];
        double sum = 0.0;

        for (int i = 0; i < n; i++) {
            double x = data.get(i);
            if (x <= 0) return Double.POSITIVE_INFINITY;
            transformed[i] = (lam == 0) ? Math.log(x) : (Math.pow(x, lam) - 1.0) / lam;
            sum += transformed[i];
        }

        double mean = sum / n;
        double variance = 0.0;
        for (double v : transformed) {
            variance += Math.pow(v - mean, 2);
        }
        variance /= n;

        return Math.log(variance);  // Lower is better
    }

    /**
     * Ensures all input data values are strictly positive (required for Box-Cox).
     *
     * @param data A List<Double> of time series data
     */
    private static void validateInput(List<Double> data) {
        for (double x : data) {
            if (x <= 0) {
                throw new IllegalArgumentException("Box-Cox transformation requires all input values to be > 0.");
            }
        }
    }
}
