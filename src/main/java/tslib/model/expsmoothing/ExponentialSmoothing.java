package tslib.model.expsmoothing;

import java.util.List;

public interface ExponentialSmoothing {
    /**
     * Forecasts values based on time series data.
     *
     * @param data the time series input
     * @param steps number of future periods to forecast
     * @return list of forecasted values (original data + forecast)
     */
    List<Double> forecast(List<Double> data, int steps);
}
