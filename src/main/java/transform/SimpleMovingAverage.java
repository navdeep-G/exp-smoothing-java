package transform;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.lang.*;
import java.util.List;

/**Simple Moving Average (SMA)
 *
 * @author navdeepgill
 */

public class SimpleMovingAverage {
    Queue<Double> window = new LinkedList<Double>();
    private final int period;
    private double sum;

    public List<Double> getMA(List<Double> data){
        List<Double> ma_data = new ArrayList<Double>(data.size());
            for (double x : data) {
                newNum(x);
                ma_data.add(getAvg());
        }
        return ma_data;
    }

    public SimpleMovingAverage(int period) {
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

