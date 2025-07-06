package tstest;

import java.util.ArrayList;
import tslib.tests.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit Test for Augmented Dickey Fuller
 * Verifies detection of non-stationarity.
 */
public class AugmentedDickeyFullerTest {

    @Test
    public void testClearLinearTrend() {
        ArrayList<Double> x = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            x.add(i * 1.5);  // Strong linear trend, no randomness
        }
        AugmentedDickeyFuller adf = new AugmentedDickeyFuller(x);
        System.out.println("ADF stat (linear trend): " + adf.getAdfStat());
        assertFalse("Expected non-stationary series", adf.isNeedsDiff());
    }

    @Test
    public void testLinearTrendWithOutlier() {
        ArrayList<Double> x = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            x.add(i * 1.5);
        }
        x.set(50, 1000.0);  // Inject an outlier
        AugmentedDickeyFuller adf = new AugmentedDickeyFuller(x);
        System.out.println("ADF stat (trend + outlier): " + adf.getAdfStat());
        assertFalse("Expected non-stationary series with outlier", adf.isNeedsDiff());
    }
}
