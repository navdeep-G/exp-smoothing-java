package algos.expsmoothing;

import java.util.List;
import java.util.ArrayList;

/**
 * Triple Exponential Smoothing (Holt-Winters Multiplicative Method).
 * Supports seasonal decomposition of time series with trend and seasonal adjustment.
 *
 * @author navdeep
 */
public class TripleExpSmoothing implements ExponentialSmoothing {

    private final double alpha;
    private final double beta;
    private final double gamma;
    private final int period;
    private final boolean debug;

    public TripleExpSmoothing(double alpha, double beta, double gamma, int period, boolean debug) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.period = period;
        this.debug = debug;
    }

    @Override
    public List<Double> forecast(List<Double> y, int m) {
        return forecast(y, alpha, beta, gamma, period, m, debug);
    }

    public static List<Double> forecast(List<Double> y, double alpha, double beta,
                                        double gamma, int period, int m, boolean debug) {

        validateArguments(y, alpha, beta, gamma, period, m);

        int seasons = y.size() / period;
        double a0 = calculateInitialLevel(y);
        double b0 = calculateInitialTrend(y, period);
        List<Double> initialSeasonalIndices = calculateSeasonalIndices(y, period, seasons);

        if (debug) {
            System.out.printf("Total observations: %d, Seasons: %d, Periods: %d%n", y.size(), seasons, period);
            System.out.println("Initial level value a0: " + a0);
            System.out.println("Initial trend value b0: " + b0);
            printArray("Seasonal Indices:", initialSeasonalIndices);
        }

        List<Double> forecast = calculateHoltWinters(y, a0, b0, alpha, beta, gamma,
                initialSeasonalIndices, period, m, debug);

        if (debug) {
            printArray("Final Forecast:", forecast);
        }

        return forecast;
    }

    private static void validateArguments(List<Double> y, double alpha, double beta,
                                          double gamma, int period, int m) {
        if (y == null || y.isEmpty()) {
            throw new IllegalArgumentException("Input time series must not be null or empty.");
        }
        if (m <= 0 || m > period) {
            throw new IllegalArgumentException("Forecast horizon m must be > 0 and <= period.");
        }
        if (alpha < 0.0 || alpha > 1.0) {
            throw new IllegalArgumentException("Alpha must be between 0.0 and 1.0.");
        }
        if (beta < 0.0 || beta > 1.0) {
            throw new IllegalArgumentException("Beta must be between 0.0 and 1.0.");
        }
        if (gamma < 0.0 || gamma > 1.0) {
            throw new IllegalArgumentException("Gamma must be between 0.0 and 1.0.");
        }
    }

    private static List<Double> calculateHoltWinters(List<Double> y, double a0, double b0,
                                                     double alpha, double beta, double gamma,
                                                     List<Double> initialSeasonalIndices, int period, int m,
                                                     boolean debug) {

        int n = y.size();
        List<Double> St = new ArrayList<>();
        List<Double> Bt = new ArrayList<>();
        List<Double> It = new ArrayList<>();
        List<Double> Ft = new ArrayList<>();

        for (int i = 0; i < n + m; i++) Ft.add(0.0);
        for (int i = 0; i < n; i++) {
            St.add(0.0);
            Bt.add(0.0);
            It.add(i < period ? initialSeasonalIndices.get(i) : 1.0);
        }

        St.set(1, a0);
        Bt.set(1, b0);

        for (int i = 2; i < n; i++) {
            if (i - period >= 0) {
                St.set(i, alpha * y.get(i) / It.get(i - period) + (1 - alpha) * (St.get(i - 1) + Bt.get(i - 1)));
            } else {
                St.set(i, alpha * y.get(i) + (1 - alpha) * (St.get(i - 1) + Bt.get(i - 1)));
            }

            Bt.set(i, gamma * (St.get(i) - St.get(i - 1)) + (1 - gamma) * Bt.get(i - 1));

            if (i - period >= 0) {
                It.set(i, beta * y.get(i) / St.get(i) + (1 - beta) * It.get(i - period));
            }

            if (i + m >= period) {
                int fIdx = i + m;
                int seasonIdx = i - period + m;
                if (seasonIdx < It.size()) {
                    Ft.set(fIdx, (St.get(i) + m * Bt.get(i)) * It.get(seasonIdx));
                }
            }

            if (debug) {
                System.out.printf("i = %d, y = %.2f, S = %.4f, Bt = %.4f, It = %.4f, F = %.4f%n",
                        i, y.get(i), St.get(i), Bt.get(i), It.get(i),
                        (i + m < Ft.size()) ? Ft.get(i + m) : 0.0);
            }
        }

        return Ft;
    }

    private static double calculateInitialLevel(List<Double> y) {
        return y.get(0);
    }

    private static double calculateInitialTrend(List<Double> y, int period) {
        double sum = 0.0;
        for (int i = 0; i < period; i++) {
            sum += (y.get(period + i) - y.get(i));
        }
        return sum / (period * period);
    }

    private static List<Double> calculateSeasonalIndices(List<Double> y, int period, int seasons) {
        double[] seasonalAverages = new double[seasons];
        double[] seasonalIndices = new double[period];
        double[] averagedObservations = new double[y.size()];

        for (int i = 0; i < seasons; i++) {
            for (int j = 0; j < period; j++) {
                seasonalAverages[i] += y.get(i * period + j);
            }
            seasonalAverages[i] /= period;
        }

        for (int i = 0; i < seasons; i++) {
            for (int j = 0; j < period; j++) {
                averagedObservations[i * period + j] = y.get(i * period + j) / seasonalAverages[i];
            }
        }

        for (int i = 0; i < period; i++) {
            for (int j = 0; j < seasons; j++) {
                seasonalIndices[i] += averagedObservations[j * period + i];
            }
            seasonalIndices[i] /= seasons;
        }

        List<Double> seasonalIndexList = new ArrayList<>();
        for (double si : seasonalIndices) {
            seasonalIndexList.add(si);
        }

        return seasonalIndexList;
    }

    private static void printArray(String label, List<Double> values) {
        System.out.println(label);
        for (Double val : values) {
            System.out.printf("%.4f%n", val);
        }
    }
}
