package h2oframe.movingaverage;

import h2oframe.transform.TransformFrame;
import water.fvec.Frame;
import water.fvec.Vec;

import java.util.LinkedList;
import java.util.Queue;

/**Simple Moving Average (SMA)
 *
 * a simple moving average (SMA) is the unweighted mean of the previous n data. However, in science and engineering
 * the mean is normally taken from an equal number of data on either side of a central value. This ensures that
 * variations in the mean are aligned with the variations in the data rather than being shifted in time.
 * An example of a simple equally weighted running mean for a n-day sample of closing price is the mean of
 * the previous n days' closing prices.
 *
 * @author navdeepgill
 */

public class SimpleMovingAverageFrame implements TransformFrame {
    Queue<Double> window = new LinkedList<Double>();
    private final int period;
    private double sum;

    @Override
    public Frame transform(Frame data){
        for(Vec v : data.vecs()){
            for (long x = 0; x < v.length(); ++x) {
                newNum(v.at(x));
                v.set(x,getAvg());
            }
        }
        return data;
    }

    public SimpleMovingAverageFrame(int period) {
        assert period > 0 : "Period must be a positive integer!";
        this.period = period;
    }

    public void newNum(double num) {
        sum += num;
        window.add(num);
        if (window.size() > period) {
            sum -= window.remove();
        }
    }

    public double getAvg() {
        if (window.isEmpty()) return 0; // technically the average is undefined
        return sum / window.size();
    }

}

