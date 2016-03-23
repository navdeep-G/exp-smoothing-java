package movingaverage;

import water.fvec.Frame;
import water.fvec.Vec;

import java.util.LinkedList;
import java.util.Queue;

/**Simple Moving Average (SMA)
 *
 * @author navdeepgill
 */

public class SimpleMovingAverageFrame {
    Queue<Double> window = new LinkedList<Double>();
    private final int period;
    private double sum;

    public Frame getMA(Frame data){
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

