package movingaverage;

import transform.TransformFrame;
import water.fvec.Frame;
import water.fvec.Vec;

import java.util.LinkedList;
import java.util.Queue;

/**Simple Moving Average (SMA)
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
                //System.out.println(v.at(x));
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

