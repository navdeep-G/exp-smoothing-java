package transform;

import org.junit.Test;
import util.Util;
import util.TestUtil;
import water.fvec.Frame;
import org.junit.BeforeClass;
import water.fvec.Vec;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
/**
 * Created by nkalonia1 on 3/21/16.
 */
public class TestTransform extends TestUtil {
    //TODO Remove String path and make Frame manually for test.
    @BeforeClass public static void stall() { stall_till_cloudsize(1); }

    static String path = "data/hotel.txt";
    static Frame fr;
    static List<List<Double>> correct;

    @BeforeClass public static void init() {
        fr = parse_test_file(path);
        try {
            correct = Util.ReadCSV(path);
        } catch (IOException ioe) {
            System.err.println("Couldn't read file: " + path);
        }
    }

    //TODO Add correct output for test.
    @Test
    public void testLogTransform() {
        Frame log_fr = (new TransformFuncsFrame.Log()).transform((fr.deepCopy(null)));
        long rows = log_fr.numRows();
        assertEquals("Mismatched number of columns in Frame", correct.size(), log_fr.numCols());
        for (int c = 0; c < log_fr.numCols(); ++c) {
            Vec v = log_fr.vec(c);
            List<Double> l = Transform.log(correct.get(c));
            assertEquals("Mismatched length of Vec at column " + c, l.size(), rows);
            for (long r = 0; r < rows; ++r) {
                assertEquals("Mismatched value at ["+r+","+c+"]", l.get((int) r), v.at(r), 0.0001);
            }
        }
        log_fr.delete();
    }

    @Test
    public void testRootTransform() {
        Frame root_fr = (new TransformFuncsFrame.Root(5)).transform(fr.deepCopy(null));
        long rows = root_fr.numRows();
        assertEquals("Mismatched number of columns in Frame", correct.size(), root_fr.numCols());
        for (int c = 0; c < root_fr.numCols(); ++c) {
            Vec v = root_fr.vec(c);
            List<Double> l = Transform.root(correct.get(c), 5);
            assertEquals("Mismatched length of Vec at column " + c, l.size(), rows);
            for (long r = 0; r < rows; ++r) {
                assertEquals("Mismatched value at ["+r+","+c+"]", l.get((int) r), v.at(r), 0.0001);
            }
        }
        root_fr.delete();
    }

    @Test
    public void testSqrtTransform() {
        Frame sqrt_fr = (new TransformFuncsFrame.Sqrt()).transform(fr.deepCopy(null));
        long rows = sqrt_fr.numRows();
        assertEquals("Mismatched number of columns in Frame", correct.size(), sqrt_fr.numCols());
        for (int c = 0; c < sqrt_fr.numCols(); ++c) {
            Vec v = sqrt_fr.vec(c);
            List<Double> l = Transform.sqrt(correct.get(c));
            assertEquals("Mismatched length of Vec at column " + c, l.size(), rows);
            for (long r = 0; r < rows; ++r) {
                assertEquals("Mismatched value at ["+r+","+c+"]", l.get((int) r), v.at(r), 0.0001);
            }
        }
        sqrt_fr.delete();
    }

    @Test
    public void testCbrtTransform() {
        Frame cbrt_fr = (new TransformFuncsFrame.Cbrt()).transform(fr.deepCopy(null));
        long rows = cbrt_fr.numRows();
        assertEquals("Mismatched number of columns in Frame", correct.size(), cbrt_fr.numCols());
        for (int c = 0; c < cbrt_fr.numCols(); ++c) {
            Vec v = cbrt_fr.vec(c);
            List<Double> l = Transform.cbrt(correct.get(c));
            assertEquals("Mismatched length of Vec at column " + c, l.size(), rows);
            for (long r = 0; r < rows; ++r) {
                assertEquals("Mismatched value at ["+r+","+c+"]", l.get((int) r), v.at(r), 0.0001);
            }
        }
        cbrt_fr.delete();
    }
}
