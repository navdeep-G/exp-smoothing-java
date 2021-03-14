package h2oframe.algos;
import java.util.List;
import java.util.ArrayList;
import water.fvec.Frame;
import water.fvec.Vec;

/**
 * Given a time series, say a complete monthly data for 12 months, the
 * Triple Exponential smoothing and forecasting technique is built on the following
 * formula (multiplicative version):
 *
 * St[i] = alpha * y[i] / It[i - period] + (1.0 - alpha) * (St[i - 1] + Bt[i - 1])
 * Bt[i] = gamma * (St[i] - St[i - 1]) + (1 - gamma) * Bt[i - 1]
 * It[i] = beta * y[i] / St[i] + (1.0 - beta) * It[i - period]
 * Ft[i + m] = (St[i] + (m * Bt[i])) * It[i - period + m]
 *
 *
 * @author navdeepgill
 *
 */
public class TripleExpSmoothingFrame {

    /**
     * This method is the entry point. It calculates the initial values and
     * returns the forecast for the future m periods.
     *
     * @param y - Time series data.
     * @param alpha - Exponential smoothing coefficients for level, trend,
     *            seasonal components.
     * @param beta - Exponential smoothing coefficients for level, trend,
     *            seasonal components.
     * @param gamma - Exponential smoothing coefficients for level, trend,
     *            seasonal components.
     * @param period - A complete season's data consists of L periods. And we need
     *            to estimate the trend factor from one period to the next. To
     *            accomplish this, it is advisable to use two complete seasons;
     *            that is, 2L periods.
     * @param m - Extrapolated future data points.
     *          - 4 quarterly,
     *          - 7 weekly,
     *          - 12 monthly
     *
     * @param debug - Print debug values. Useful for testing.
     *
     */
    public static List<Double> forecast(Frame y, double alpha, double beta,
                                        double gamma, int period, int m, boolean debug) {

        validateArguments(y, alpha, beta, gamma, period, m);

        List<Double> forecast = null;
        for (Vec v : y.vecs()) {
            int n = (int) v.length();
            int seasons = n / period;
            double a0 = calculateInitialLevel(v);
            double b0 = calculateInitialTrend(v, period);
            List<Double> initialSeasonalIndices = calculateSeasonalIndices(v, period,
                    seasons);

            if (debug) {
                System.out.println(String.format(
                        "Total observations: %d, Seasons %d, Periods %d", n,
                        seasons, period));
                System.out.println("Initial level value a0: " + a0);
                System.out.println("Initial trend value b0: " + b0);
                printArray("Seasonal Indices: ", initialSeasonalIndices);
            }

            forecast = calculateHoltWinters(v, a0, b0, alpha, beta, gamma,
                    initialSeasonalIndices, period, m, debug);

            if (debug) {
                printArray("Forecast", forecast);
            }
        }

        return forecast;
    }

    //public static List<Double> forecast(Frame y, double alpha, double beta,
    //                                    double gamma, int period, int m) {
    //    return forecast(y, alpha, beta, gamma, period, m, false);
    //}

    /**
     * Validate input.
     *
     * @param y
     * @param alpha
     * @param beta
     * @param gamma
     * @param m
     */
    private static void validateArguments(Frame y, double alpha, double beta,
                                          double gamma, int period, int m) {
        if (y == null) {
            throw new IllegalArgumentException("Value of y should be not null");
        }

        if(m <= 0){
            throw new IllegalArgumentException("Value of m must be greater than 0.");
        }

        if(m > period){
            throw new IllegalArgumentException("Value of m must be <= period.");
        }

        if((alpha < 0.0) || (alpha > 1.0)){
            throw new IllegalArgumentException("Value of Alpha should satisfy 0.0 <= alpha <= 1.0");
        }

        if((beta < 0.0) || (beta > 1.0)){
            throw new IllegalArgumentException("Value of Beta should satisfy 0.0 <= beta <= 1.0");
        }

        if((gamma < 0.0) || (gamma > 1.0)){
            throw new IllegalArgumentException("Value of Gamma should satisfy 0.0 <= gamma <= 1.0");
        }
    }

    /**
     * This method realizes the Holt-Winters equations.
     *
     * @param v
     * @param a0
     * @param b0
     * @param alpha
     * @param beta
     * @param gamma
     * @param initialSeasonalIndices
     * @param period
     * @param m
     * @param debug
     * @return - Forecast for m periods.
     */
    private static List<Double> calculateHoltWinters(Vec v, double a0, double b0,
                                                     double alpha, double beta, double gamma,
                                                     List<Double> initialSeasonalIndices, int period, int m, boolean debug) {
            int n = (int) v.length();
            
            List<Double> St = new ArrayList<Double>(n);
            while(St.size()<n) St.add(0.0);

            List<Double> Bt = new ArrayList<Double>(n);
            while(Bt.size()<n) Bt.add(0.0);

            List<Double> It = new ArrayList<Double>(n);
            while(It.size()<n) It.add(0.0);

            List<Double> Ft = new ArrayList<Double>(n + m);
            while(Ft.size()<n + m) Ft.add(0.0);

            // Initialize base values
            St.add(1, a0);
            Bt.add(1, b0);

            for (int i = 0; i < period; i++) {
                It.set(i,initialSeasonalIndices.get(i));
            }

            // Start calculations
            for (int i = 2; i < n; i++) {

                // Calculate overall smoothing
                if ((i - period) >= 0) {
                    St.set(i, alpha * v.at(i) / It.get(i - period) + (1.0 - alpha)
                            * (St.get(i - 1) + Bt.get(i - 1)));
                } else {
                    St.set(i, alpha * v.at(i) + (1.0 - alpha) * (St.get(i - 1) + Bt.get(i - 1)));
                }

                // Calculate trend smoothing
                Bt.set(i, gamma * (St.get(i) - St.get(i - 1)) + (1 - gamma) * Bt.get(i - 1));

                // Calculate seasonal smoothing
                if ((i - period) >= 0) {
                    It.set(i, beta * v.at(i) / St.get(i) + (1.0 - beta) * It.get(i - period));
                }

                // Calculate forecast
                if (((i + m) >= period)) {
                    Ft.set(i+m, (St.get(i) + m * Bt.get(i)) * It.get(i - period + m));
                }

                if (debug) {
                    System.out.println(String.format(
                            "i = %d, y = %d, S = %f, Bt = %f, It = %f, F = %f", i,
                            Math.round(v.at(i)), St.get(i), Bt.get(i), It.get(i), Ft.get(i)));
                }
            }


        return Ft;
    }

    /**
     * See: http://robjhyndman.com/researchtips/hw-initialization/ 1st period's
     * average can be taken. But y[0] works better.
     *
     * @return - Initial Level value i.e. St[1]
     */
    private static double calculateInitialLevel(Vec v) {
        return v.at(0);
    }

    /**
     * See: http://www.itl.nist.gov/div898/handbook/pmc/section4/pmc435.htm
     *
     * @return - Initial trend - Bt[1]
     */
    private static double calculateInitialTrend(Vec v, int period) {

        double sum = 0;

        for (int i = 0; i < period; i++) {
            sum += (v.at(period + i) - v.at(i));
        }

        return sum / (period * period);
    }

    /**
     * See: http://www.itl.nist.gov/div898/handbook/pmc/section4/pmc435.htm
     *
     * @return - Seasonal Indices.
     */
    private static List<Double> calculateSeasonalIndices(Vec v, int period,
                                                         int seasons) {

        int n = (int) v.length();
        double[] seasonalAverage = new double[seasons];
        double[] seasonalIndices = new double[period];

        double[] averagedObservations = new double[n];

        for (int i = 0; i < seasons; i++) {
            for (int j = 0; j < period; j++) {
                seasonalAverage[i] += v.at((i * period) + j);
            }
            seasonalAverage[i] /= period;
        }

        for (int i = 0; i < seasons; i++) {
            for (int j = 0; j < period; j++) {
                averagedObservations[(i * period) + j] = v.at((i * period) + j)
                        / seasonalAverage[i];
            }
        }

        for (int i = 0; i < period; i++) {
            for (int j = 0; j < seasons; j++) {
                seasonalIndices[i] += averagedObservations[(j * period) + i];
            }
            seasonalIndices[i] /= seasons;
        }

        ArrayList<Double> list = new ArrayList<Double>(seasonalIndices.length);
        for(double d : seasonalIndices) list.add(d);
        return list;
    }

    /**
     * Utility method to print array values.
     *
     * @param description
     * @param data
     */
    private static void printArray(String description, List<Double> data) {
        System.out.println(description);
        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }
    }
}