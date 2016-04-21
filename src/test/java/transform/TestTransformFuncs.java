package transform;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import transform.frame.TransformFuncsFrame;
import util.TestUtil;
import water.fvec.Frame;
import org.junit.BeforeClass;
import water.fvec.Vec;
import water.util.ArrayUtils;

import static org.junit.Assert.assertEquals;
/**
 * Created by nkalonia1 on 3/21/16.
 */
@RunWith(JUnitParamsRunner.class)
public class TestTransformFuncs extends TestUtil {
    //TODO Add way to manually insert expected values for tests
    @BeforeClass public static void stall() { stall_till_cloudsize(1); }

    static Frame[] frames = {
            parse_test_file("data/hotel.txt"),
            ArrayUtils.frame(ard(
                    ard(1, 1),
                    ard(2, 2),
                    ard(3, 3),
                    ard(4, 4),
                    ard(5, 5)))
    };
    private Frame test_fr, expected_fr;

    private static final Object[] data() {
        return frames;
    }

    private void initFrames(Frame fr) {
        test_fr = fr.deepCopy(null);
        expected_fr = fr.deepCopy(null);
    }

    @Test
    @Parameters(method = "data")
    public void testLogTransform(Frame fr) {
        initFrames(fr);
        (new TransformFuncsFrame.Log()).transform(test_fr);

        // Create expected result
        for (int c = 0; c < expected_fr.numCols(); ++c) {
            Vec v = expected_fr.vec(c);
            for (long r = 0; r < expected_fr.numRows(); ++r) {
                v.set(r, Math.log(v.at(r)));
            }
        }
        assertEqualFrames(expected_fr, test_fr, 0.0001);
    }

    @Test
    @Parameters(method = "data")
    public void testRootTransform(Frame fr) {
        initFrames(fr);
        (new TransformFuncsFrame.Root(5)).transform(test_fr);

        // Create expected result
        for (int c = 0; c < expected_fr.numCols(); ++c) {
            Vec v = expected_fr.vec(c);
            for (long r = 0; r < expected_fr.numRows(); ++r) {
                v.set(r, Math.pow(v.at(r), 0.2));
            }
        }
        assertEqualFrames(expected_fr, test_fr, 0.0001);
    }

    @Test
    @Parameters(method = "data")
    public void testSqrtTransform(Frame fr) {
        initFrames(fr);
        (new TransformFuncsFrame.Sqrt()).transform(test_fr);

        // Create expected result
        for (int c = 0; c < expected_fr.numCols(); ++c) {
            Vec v = expected_fr.vec(c);
            for (long r = 0; r < expected_fr.numRows(); ++r) {
                v.set(r, Math.sqrt(v.at(r)));
            }
        }
        assertEqualFrames(expected_fr, test_fr, 0.0001);
    }

    @Test
    @Parameters(method = "data")
    public void testCbrtTransform(Frame fr) {
        initFrames(fr);
        (new TransformFuncsFrame.Cbrt()).transform(test_fr);

        // Create expected result
        for (int c = 0; c < expected_fr.numCols(); ++c) {
            Vec v = expected_fr.vec(c);
            for (long r = 0; r < expected_fr.numRows(); ++r) {
                v.set(r, Math.cbrt(v.at(r)));
            }
        }
        assertEqualFrames(expected_fr, test_fr, 0.0001);
    }

    @After
    public void clearFrame() {
        test_fr.delete();
        expected_fr.delete();
    }

    public static void assertEqualFrames(Frame expected, Frame actual, double threshold) {
        assertEquals("Mismatched number of columns in Frame", expected.numCols(), actual.numCols());
        for (int c = 0; c < expected.numCols(); ++c) {
            Vec v_e = expected.vec(c);
            Vec v_a = actual.vec(c);
            assertEquals("Mismatched length of Vec at column " + c, v_e.length(), v_a.length());
            for (long r = 0; r < v_e.length(); ++r) {
                assertEquals("Mismatched value at ["+r+","+c+"]", v_e.at(r), v_a.at(r), threshold);
            }
        }
    }
}
