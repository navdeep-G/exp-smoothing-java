package boxcox;

import org.junit.Test;
import transform.Transform;
import transform.frame.BoxCoxFrame;
import transform.frame.TransformFuncsFrame;
import util.Util;
import util.TestUtil;
import util.frame.StatsFrame;
import water.Key;
import water.Lockable;
import water.fvec.Frame;
import org.junit.BeforeClass;
import water.fvec.Vec;
import water.parser.ParseDataset;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit Test for BoxCox
 *
 * @author navdeepgill
 */
public class TestBoxCox extends TestUtil {
    @BeforeClass
    public static void stall() {
        stall_till_cloudsize(1);
    }

    static String path = "data/hotel_ten.txt";
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
    public void testBoxCox(){
        BoxCoxFrame bc = new BoxCoxFrame(new double[] {1.4});
        Frame test_fr = bc.transform(fr.deepCopy(null));
        Key raw = Key.make("bc_key");
        Key expected = Key.make("exp_key");
        makeByteVec(raw,"4301.032\n4145.576\n4337.138\n5254.264\n4839.082\n5954.205\n7257.936\n" +
                "7216.094\n5343.578\n4801.825");
        Frame exp = ParseDataset.parse(expected,raw);

        for(int i = 0; i < test_fr.numCols(); ++i){
            for(long j = 0; j < test_fr.numRows(); ++j){
                assertEquals(test_fr.vec(i).at(j),exp.vec(i).at(j), 0.01);
            }
        }
        Lockable.delete(raw);
        Lockable.delete(expected);
        test_fr.delete();
    }

    @Test
    public void testLambdaSearch(){
        double[] ls = BoxCoxFrame.lambdaSearch(fr);
        for(int i = 0; i < ls.length; ++i){
            assertEquals(-0.9999242,ls[i],0.01);
        }
    }
}
