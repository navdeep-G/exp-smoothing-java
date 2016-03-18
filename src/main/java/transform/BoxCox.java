package transform;

import util.Stats;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.univariate.BrentOptimizer;
import org.apache.commons.math3.optimization.univariate.UnivariateOptimizer;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Iterator;
import java.util.List;

/**Box Cox Transformation
 *
 * @author navdeepgill
 */
public class BoxCox {


    /**
     *Find the optimal lambda for a given time series data set and conduct transformation
     *
     *@param  data a List<Double> of time series data
     *
     *@return  Time series List<Double> with optimal Box Cox lambda transformation
     */
    public static List<Double> transform(List<Double> data) {
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
     public static List<Double> transform(List<Double> data, double lam) {
        List<Double> transform = new ArrayList<Double>();

        if(lam == 0){
            for (int i = 0; i < data.size(); i++) {
                transform.add(Math.log(data.get(i)));
            }
        }
        else{
            for (int i = 0; i < data.size(); i++) {
                transform.add((Math.pow(data.get(i), lam) - 1.0) / lam);
            }
        }
        return transform;
    }

    /**
     *Find the optimal lambda for a given time series data set with default lower/upper bounds for lambda search
     *
     *@param  data a List<Double> of time series data
     *
     *@return  Time series List<Double> with optimal Box Cox lambda transformation
     */
    public static double lambdaSearch(final List<Double> data) {
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
    public static double lambdaSearch(final List<Double> data, double lower, double upper){
        UnivariateOptimizer solver = new BrentOptimizer(1e-10, 1e-14);
        double lambda = solver.optimize(100,new UnivariateFunction(){
            public double value(double x){
                return lambdaCV(data, x);
            }
        }, GoalType.MINIMIZE,lower,upper).getPoint();
        return lambda;
    }

    /**
     * Compute the coefficient of variation
     *
     * @param data a List<Double> of time series data
     * @param lam lambda
     *
     * @return Coefficient of Variation
     */
    private static double lambdaCV(List<Double> data, double lam){
        Iterator<Double> iter = data.iterator();
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
        return Stats.standardDeviation(result)/Stats.average(result);
    }
}
