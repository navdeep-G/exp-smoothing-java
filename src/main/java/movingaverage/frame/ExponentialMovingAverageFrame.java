package movingaverage.frame;

import transform.frame.TransformFrame;
import water.fvec.Frame;
import water.fvec.Vec;

/**Exponential Moving Average (EMA)
 *
 * An exponential moving average (EMA), also known as an exponentially weighted moving average (EWMA), is a
 * type of infinite impulse response filter that applies weighting factors which decrease exponentially.
 * The weighting for each older datum decreases exponentially, never reaching zero.
 *
 * @author navdeepgill
 */

public class ExponentialMovingAverageFrame implements TransformFrame {
    private double alpha;
    private Double oldValue;

    public ExponentialMovingAverageFrame(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public Frame transform(Frame data){
        for(Vec v : data.vecs()){
            for (long x = 0; x < v.length(); ++x) {
                v.set(x,average(v.at(x)));
                //System.out.println(v.at(x));
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