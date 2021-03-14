package stats.h2oframe;

import org.junit.Test;
import util.Util;
import util.TestUtil;
import h2oframe.util.StatsFrame;
import water.fvec.Frame;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**Unit Test for StatsFrame.java
 *
 * @author navdeepgill
 */
public class TestStatsFrame extends TestUtil {
    @BeforeClass
    public static void stall() {
        stall_till_cloudsize(1);
    }

    static String path = "data/hotel.txt";
    static Frame fr;
    static List<List<Double>> correct;

    @BeforeClass
    public static void init() {
        fr = parse_test_file(path);
        try {
            correct = Util.ReadCSV(path);
        } catch (IOException ioe) {
            System.err.println("Couldn't read file: " + path);
        }
    }

    @Test
    public void testAvg() {
        double[] avg = StatsFrame.average(fr);
        //Loop for now as this test might compare double[] in the future...
        for (int a = 0; a < avg.length; ++a) {
            assertEquals("Mismatched average value at [" + a + "]", 722.2976, avg[a], 0.0001);
        }
    }

    @Test
    public void testStandardDeviation() {
        double[] sd = StatsFrame.standardDeviation(fr);
        //Loop for now as this test might compare double[] in the future...
        for (int s = 0; s < sd.length; ++s) {
            assertEquals("Mismatched standard deviation value at [" + s + "]", 142.6569, sd[s], 0.0001);
        }
    }

    @Test
    public void testVariance() {
        double[] var = StatsFrame.variance(fr);
        //Loop for now as this test might compare double[] in the future...
        for (int s = 0; s < var.length; ++s) {
            assertEquals("Mismatched variance value at [" + s + "]", 20351.00071285, var[s], 0.0001);
        }
    }

    @Test
    public void testMin() {
        double[] min = StatsFrame.getMin(fr);
        //Loop for now as this test might compare double[] in the future...
        for (int s = 0; s < min.length; ++s) {
            assertEquals("Mismatched minimum value at [" + s + "]", 480, min[s], 0.0001);
        }
    }

    @Test
    public void testMax() {
        double[] max = StatsFrame.getMax(fr);
        //Loop for now as this test might compare double[] in the future...
        for (int s = 0; s < max.length; ++s) {
            assertEquals("Mismatched maximum value at [" + s + "]", 1125, max[s], 0.0001);
        }
    }

    @Test
    public void testAutocovariance() {
        double[] acov = StatsFrame.getAutoCovariance(fr, 2);
        for (int s = 0; s < acov.length; ++s) {
            assertEquals("Mismatched autocovariance value at [" + s + "]", 11310.545005601445, acov[s], 0.0001);
        }
    }

    @Test
    public void testAutoCorrelation() {
        double[] autocorrelation = StatsFrame.getAutoCorrelation(fr, 2);
        for (int s = 0; s < autocorrelation.length; ++s) {
            assertEquals("Mismatched autocorrelationfunction value at [" + s + "]", .5557, autocorrelation[s], 0.0001);
        }
    }

    @Test
    public void testACF() {
        double[][] acf = StatsFrame.getAcf(fr, 2);
        double[][] expected = {{1.0, 0.7767511408674456, 0.5557734071747243}};
        //Loop for now as this test might compare double[] in the future...
        for (int s = 0; s < acf.length; ++s) {
            for (int i = 0; i < expected[s].length; ++i) {
                assertEquals("Mismatched value at [" + s + "," + i + "]", expected[s][i], acf[s][i], 0.0001);
            }
        }
    }

    @Test
    public void testPACF() {
        double[][] pacf = StatsFrame.getPacf(fr, 2);
        double[][] expected = {{1.0, 0.7767511408674456, -0.11992438780889675}};
        //Loop for now as this test might compare double[] in the future...
        for (int s = 0; s < pacf.length; ++s) {
            for (int i = 0; i < expected[s].length; ++i) {
                assertEquals("Mismatched value at [" + s + "," + i + "]", expected[s][i], pacf[s][i], 0.0001);
            }
        }
    }
}
