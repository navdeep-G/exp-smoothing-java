package transform;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import water.fvec.Frame;
import water.fvec.Vec;

/**Methods to Transform Time Series (log and power transformations)
 *
 * @author navdeepgill
 */

public class TransformFrame {

    public static Frame log(Frame data) {
        long rows = data.numRows();
        for (Vec v : data.vecs()) {
            for (long r = 0; r < rows; ++r) {
                v.set(r, Math.log(v.at(r)));
            }
        }
        return data;
    }

    public static Frame sqrt(Frame data){
        long rows = data.numRows();
        for (Vec v : data.vecs()) {
            for (long r = 0; r < rows; ++r) {
                v.set(r, Math.sqrt(v.at(r)));
            }
        }
        return data;
    }

    public static Frame cbrt(Frame data){
        long rows = data.numRows();
        for (Vec v : data.vecs()) {
            for (long r = 0; r < rows; ++r) {
                v.set(r, Math.cbrt(v.at(r)));
            }
        }
        return data;
    }

    public static Frame root(Frame data, double root){
        long rows = data.numRows();
        for (Vec v : data.vecs()) {
            for (long r = 0; r < rows; ++r) {
                v.set(r, Math.pow(v.at(r), 1.0/root));
            }
        }
        return data;
    }
}