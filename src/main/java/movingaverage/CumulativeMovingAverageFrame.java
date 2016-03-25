package movingaverage;

import water.fvec.Frame;
import water.fvec.Vec;

/**Cumulative Moving Average (CMA)
 *
 * @author navdeepgill
 */

public class CumulativeMovingAverageFrame {
    int n = 0;
    double average = 0.0;

    public Frame getCMA(Frame data){
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
