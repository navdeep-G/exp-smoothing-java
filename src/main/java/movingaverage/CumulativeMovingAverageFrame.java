package movingaverage;

import java.util.ArrayList;
import java.util.List;
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
            for (long i = 0; i < v.length(); ++i) {
                v.set(i,add(v.at(i)));
            }
        }
        return data;
    }


    public double add(double x) {
        return average += (x - average) / ++n;
    }
}
