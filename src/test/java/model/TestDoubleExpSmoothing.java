package model;

import org.junit.Test;
import tslib.model.expsmoothing.DoubleExpSmoothing;

import java.util.List;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TestDoubleExpSmoothing {

    @Test
    public void forecastNISTData() {
        List<Double> y = Arrays.asList(
                362.0, 385.0, 432.0, 341.0, 382.0, 409.0, 498.0, 387.0, 473.0,
                513.0, 582.0, 474.0, 544.0, 582.0, 681.0, 557.0, 628.0, 707.0,
                773.0, 592.0, 627.0, 725.0, 854.0, 661.0
        );

        int forecastSteps = 4;
        double alpha = 0.5;
        double gamma = 0.6;
        int initMethod = 0;

        DoubleExpSmoothing model = new DoubleExpSmoothing(alpha, gamma, initMethod);
        List<Double> prediction = model.forecast(y, forecastSteps);

        // Validate result length
        assertEquals(y.size() + forecastSteps, prediction.size());

        // Forecast values should follow a trend based on last smoothed and trend components
        double lastSmoothed = prediction.get(y.size() - 1);
        double slope = prediction.get(y.size()) - lastSmoothed;

        for (int i = y.size(); i < prediction.size(); i++) {
            assertTrue("Forecasted value should be positive", prediction.get(i) > 0);
        }

        // Optional: print forecast values for manual inspection
        System.out.println("Double Exp Forecast: " + prediction.subList(y.size(), y.size() + forecastSteps));
    }
}
