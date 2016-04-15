package transform;

import java.lang.Math;
import water.fvec.Frame;

/**Methods to Transform Time Series (log and power transformations)
 *
 * @author navdeepgill
 */

public class TransformFuncsFrame {

    public static Frame log(Frame data) {
        SimpleTransformFrame log = new SimpleTransformFrame() {
            @Override
            public double transform(double d) {
                return Math.log(d);
            }
        };
        return log.transform(data);
    }

    public static Frame sqrt(Frame data){
        SimpleTransformFrame sqrt = new SimpleTransformFrame() {
            @Override
            public double transform(double d) {
                return Math.sqrt(d);
            }
        };
        return sqrt.transform(data);
    }

    public static Frame cbrt(Frame data) {
        SimpleTransformFrame cbrt = new SimpleTransformFrame() {
            @Override
            public double transform(double d) {
                return Math.cbrt(d);
            }
        };
        return cbrt.transform(data);
    }

    public static Frame root(Frame data, final double r){
        SimpleTransformFrame root = new SimpleTransformFrame() {
            @Override
            public double transform(double d) {
                return Math.pow(d, 1.0 / r);
            }
        };
        return root.transform(data);
    }
}