package transform;

import java.lang.Math;
import water.fvec.Frame;

/**Methods to Transform Time Series (log and power transformations)
 *
 * @author navdeepgill
 */

public class TransformFuncsFrame {

    public static class Log extends SimpleTransformFrame{
        @Override
        public double transform(double d) {
            return Math.log(d);
        }
    }

    public static class Sqrt extends SimpleTransformFrame{
        @Override
        public double transform(double d) {
            return Math.sqrt(d);
        }
    }

    public static class Cbrt extends SimpleTransformFrame{
        @Override
        public double transform(double d) {
            return Math.cbrt(d);
        }
    }

    public static class Root extends SimpleTransformFrame{
        double r;

        public Root(double r){
            this.r = r;
        }
        @Override
        public double transform(double d) {
            return Math.pow(d, 1.0 / r);
        }
    }

}