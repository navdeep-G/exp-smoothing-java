package tslib.collect;

import org.junit.Test;
import tslib.model.expsmoothing.SingleExpSmoothing;
import tslib.model.expsmoothing.TripleExpSmoothing;
import tslib.movingaverage.*;
import tslib.tests.AugmentedDickeyFuller;
import tslib.transform.Transform;
import tslib.util.Util;

import java.util.List;

import static org.junit.Assert.*;

public class CollectTest {

    private final String pathToData = "data/hotel.txt";
    private final int lag = 1;
    private final double lambda = 1.6;

    @Test
    public void testForecastAndStatsRun() throws Exception {
        List<Double> file = Util.readFile(pathToData);
        assertNotNull(file);
        assertFalse(file.isEmpty());

        Collect collect = new Collect(pathToData, lag, lag);

        // Basic statistical checks
        assertTrue(collect.getAverage() > 0);
        assertTrue(collect.getVariance() > 0);

        // Transformations
        List<Double> fileBoxCox = Transform.boxCox(file, lambda);
        assertEquals(file.size(), fileBoxCox.size());

        // ADF Test
        AugmentedDickeyFuller adf = new AugmentedDickeyFuller(file);
        assertNotNull(adf);
        assertTrue(adf.getLag() >= 1);

        // SMA
        SimpleMovingAverage sma = new SimpleMovingAverage(2);
        List<Double> smaResult = sma.compute(file);
        assertEquals(file.size(), smaResult.size());

        // CMA
        CumulativeMovingAverage cma = new CumulativeMovingAverage();
        List<Double> cmaResult = cma.compute(file);
        assertEquals(file.size(), cmaResult.size());

        // EMA
        ExponentialMovingAverage ema = new ExponentialMovingAverage(0.3);
        List<Double> emaResult = ema.compute(file);
        assertEquals(file.size(), emaResult.size());

        // SES
        SingleExpSmoothing ses = new SingleExpSmoothing(0.5);
        List<Double> sesForecast = ses.forecast(file, 2);
        assertEquals(file.size() + 2, sesForecast.size());

        // TES
        TripleExpSmoothing tes = new TripleExpSmoothing(0.5411, 0.0086, 1e-04, 12, false);
        List<Double> tesForecast = tes.forecast(file, 2);
        assertEquals(file.size() + 2, tesForecast.size());
    }
}
