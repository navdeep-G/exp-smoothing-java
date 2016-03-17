package algos;
import java.util.List;

public class HoltWinters {

    /**
     * Smooth supplied timeline internalData 3 ways - overall, by trend and by season.
     *
     * @param data - list of internalData
     * @param season_length - the number of entries that represent a 'season'. example = 7
     * @param alpha - internalData smoothing factor. example = 0.2
     * @param beta - trend smoothing factor. example = 0.01
     * @param gamma - seasonality smoothing factor. example = 0.01
     * @param dev_gamma - smoothing factor for deviations. example = 0.1
     * @return
     */
    public static double holtWintersSmoothing(List<Double> data, int season_length, double alpha, double beta, double gamma, double dev_gamma) {
        int n = data.size();
        // Calculate an initial trend level
        double trend1 = 0.0;
        for(int i = 0; i < season_length; ++i) {
            trend1 += data.get(i);
        }
        trend1 /= season_length;

        double trend2 = 0.0;
        for(int i = season_length; i < 2*season_length; ++i) {
            trend2 += data.get(i);
        }
        trend2 /= season_length;

        double initial_trend = (trend2 - trend1) / season_length;

        // Take the first value as the initial level
        double initial_level = data.get(0);

        // Build index
        double[] index = new double[n];
        for(int i = 0; i<n; ++i) {
            double val = data.get(i);
            index[i] = val / (initial_level + (i + 1.0) * initial_trend);
        }

        // Build season buffer
        double[] season = new double[n+season_length];
        double sum=0.0;
        for(int i = 0; i < season_length; ++i) {
            season[i] = (index[i] + index[i+season_length]) / 2;
            sum+=season[i];
        }
        index=null;

        // Normalise season
        double season_factor = season_length / sum;
        for(int i = 0; i < season_length; ++i) {
            season[i] *= season_factor;
        }

        double alpha_level = initial_level;
        double beta_trend = initial_trend;
        int i = 0;
        for(; i < n; ++i) {
            double value = data.get(i);
            double temp_level = alpha_level;
            double temp_trend = beta_trend;

            alpha_level = alpha * value / season[i] + (1.0 - alpha) * (temp_level + temp_trend);
            beta_trend = beta * (alpha_level - temp_level) + ( 1.0 - beta ) * temp_trend;

            season[i + season_length] = gamma * value / alpha_level + (1.0 - gamma) * season[i];
        }

        double holtWinterTplus1=alpha_level + beta_trend * season[i + 1];

        return holtWinterTplus1;
    }

}