package tstest;

import java.util.Random;
import java.util.ArrayList;
import tests.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit Test for Augmented Dickey Fuller
 * @author navdeepgill
 */
public class AugmentedDickeyFullerTest {

    @Test
    public void testLinearTrend() {
        Random rand = new Random();
        ArrayList<Double> x = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            x.add(i, (i + 1) + 5 * rand.nextDouble());
            }
        AugmentedDickeyFuller adf = new AugmentedDickeyFuller(x);
        assertTrue(adf.isNeedsDiff());
        }

    @Test
    public void testLinearTrendWithOutlier() {
        Random rand = new Random();
        ArrayList<Double> x = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            x.add(i,(i + 1) + 5 * rand.nextDouble());
            }
        x.add(50, 100.0);
        AugmentedDickeyFuller adf = new AugmentedDickeyFuller(x);
        assertTrue(adf.isNeedsDiff());
        }
    }
