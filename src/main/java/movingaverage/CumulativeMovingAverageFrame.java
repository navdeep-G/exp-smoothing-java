package movingaverage;

import transform.TransformFrame;
import water.fvec.Frame;
import water.fvec.Vec;

import java.util.concurrent.ThreadLocalRandom;

/**Cumulative Moving Average (CMA)
 *
 * @author navdeepgill
 */

public class CumulativeMovingAverageFrame implements TransformFrame {
    int n = 0;
    double average = 0.0;

    @Override
    public Frame transform(Frame data){
        for(Vec v : data.vecs()){
            for (long x = 0; x < v.length(); ++x) {
                v.set(x,add(v.at(x)));
                //System.out.println(v.at(x));
            }
        }
        return data;
    }

    public double add(double x) {
        return average += (x - average) / ++n;
    }
}
