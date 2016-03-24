package movingaverage;

import java.util.ArrayList;
import java.util.List;

/**Exponential Moving Average (MA)
 *
 * @author navdeepgill
 */

public class ExponentialMovingAverage {
    private double alpha;
    private Double oldValue;

    public ExponentialMovingAverage(double alpha) {
        this.alpha = alpha;
    }

    public List<Double> getEMA(List<Double> data){
        //List<Double> ema_data = new ArrayList<>(data.size());

        for(int i=0;i<data.size();++i) {
            data.set(i,average(data.get(i)));
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