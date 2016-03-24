package movingaverage;

import java.util.ArrayList;
import java.util.List;
import water.fvec.Frame;
import water.fvec.Vec;

/**Exponential Moving Average (EMA)
 *
 * @author navdeepgill
 */

public class ExponentialMovingAverageFrame {
    private double alpha;
    private Double oldValue;

    public ExponentialMovingAverageFrame(double alpha) {
        this.alpha = alpha;
    }

    public Frame getEMA(Frame data){
        for(Vec v : data.vecs()){
            for (long x = 0; x < v.length(); ++x) {
                v.set(x,average(v.at(x)));
            }
        }
        return data;
    }

    public double average(double value) {
        if (oldValue == null) {
            oldValue = value;
            return value;
        }
        double newValue = oldValue + alpha * (value - oldValue);
        oldValue = newValue;
        return newValue;
    }
}