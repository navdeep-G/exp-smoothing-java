package algos;

import algos.expsmoothing.SingleExpSmoothing;
import org.junit.Test;

import java.util.List;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TestSingleExpSmoothing {

    @Test
    public void forecastNISTData() {
        List<Double> y = Arrays.asList(
                362.0, 385.0, 432.0, 341.0, 382.0, 409.0, 498.0, 387.0, 473.0, 513.0, 582.0, 474.0,
                544.0, 582.0, 681.0, 557.0, 628.0, 707.0, 773.0, 592.0, 627.0, 725.0, 854.0, 661.0
        );

        int forecastSteps = 4;
        double alpha = 0.5;

        // Instantiate the smoothing model
        SingleExpSmoothing ses = new SingleExpSmoothing(alpha);
        List<Double> prediction = ses.forecast(y, forecastSteps);

        // Simple sanity checks
        assertEquals(y.size() + forecastSteps, prediction.size());
        assertEquals(prediction.get(y.size() - 1), prediction.get(y.size()), 1e-6); // First forecast equals last smoothed

        // Optionally, print result for manual inspection
        System.out.println("Forecast: " + prediction.subList(y.size(), y.size() + forecastSteps));
    }
}
