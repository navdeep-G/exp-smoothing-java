package main.java.collect;

import java.util.LinkedList;
import java.util.Queue;
import java.lang.*;


/**Simple Moving Average (SMA)
 *
 * @author navdeepgill
 */

public class MovingAverage {
    Queue<Double> window = new LinkedList<>();
    private final int period;
    private double sum;

    public MovingAverage(int period) {
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
        System.out.println(window.size()); //Added for testing purposes. Will remove later.
        System.out.println(sum);           //Added for testing purposes. Will remove later.
        return sum / window.size();
    }
}

