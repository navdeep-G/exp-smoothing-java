package movingaverage;

import java.util.ArrayList;
import java.util.List;

class ExponentialMovingAverage {
    private double alpha;
    private Double oldValue;

    public ExponentialMovingAverage(double alpha) {
        this.alpha = alpha;
    }

    public List<Double> getEMA(List<Double> data){
        List<Double> ema_data = new ArrayList<Double>(data.size());

        for (int i=0;i<data.size();i++) {
            ema_data.add(average(data.get(i)));
        }
        return ema_data;
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