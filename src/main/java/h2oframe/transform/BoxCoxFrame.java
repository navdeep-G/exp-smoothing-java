package h2oframe.transform;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.univariate.BrentOptimizer;
import org.apache.commons.math3.optimization.univariate.UnivariateOptimizer;

import water.fvec.Frame;
import water.fvec.Vec;
import water.util.MathUtils.BasicStats;

/**Box Cox Transformation on a Frame
 *
 * @author navdeepgill
 */
public class BoxCoxFrame implements TransformFrame {

    private double[] lambda;

    public BoxCoxFrame(double[] lambda) {
        this.lambda = lambda;
    }

    public BoxCoxFrame() {
        this(null);
    }

    public Frame transform(Frame data) {
        return lambda == null ? transform(data, lambdaSearch(data)) : transform(data, lambda);
    }

    /**
     *Calculate a Box Cox Transformation for a given lambda
     *
     *@param  data a Frame of time series data
     *@param lam desired lambda for transformation
     *
     *@return  Time series Frame with desired Box Cox transformation
     */
     public Frame transform(Frame data, final double[] lam) {
         for (int c = 0; c < data.numCols(); ++c) {
             final double lambda = lam[c];
             SimpleTransformFrame t;
             if (lambda == 0) {
                 t = new SimpleTransformFrame() {
                     @Override
                     public double transform(double d) {
                         return (Math.log(d));
                     }
                 };
             } else {
                 t = new SimpleTransformFrame() {
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
     *@param  data a Frame of time series data
     *
     *@return  Time series Frame with optimal Box Cox lambda transformation
     */
    public static double[] lambdaSearch(final Frame data) {
        return lambdaSearch(data, -1, 2);
    }

    /**
     *Find the optimal lambda for a given time series data set given lower/upper bounds for lambda search
     *
     *@param  data a Frame of time series data
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
     * @param v a Vec of time series data
     * @param lam lambda
     *
     * @return Coefficient of Variation
     */
    private static double lambdaCV(Vec v, double lam) {
        BasicStats stats = new BasicStats(1);
        for (long i = 0; i < v.length() - 1; i += 2) {
            double mean = (v.at(i) + v.at(i+1)) / 2;
            double sd = Math.sqrt(Math.pow(v.at(i) - mean, 2) + Math.pow(v.at(i+1) - mean, 2));
            sd /= Math.pow(mean, 1 - lam);
            stats.add(sd, 1, 0);
        }
        long len = v.length() % 2 == 0 ? v.length()/2 : v.length()/2 + 1;
        stats.fillInZeros(len,len);
        return stats.sigma(0) / stats.mean(0);
    }
}
