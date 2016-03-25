package movingaverage;

import java.util.List;

/**Cumulative Moving Average (CMA)
 *
 * @author navdeepgill
 */

public class CumulativeMovingAverage {
    int n = 0;
    double average = 0.0;

    public List<Double> getCMA(List<Double> data){
        for (int i=0;i<data.size();i++) {
            data.set(i,add(data.get(i)));
        }
        return data;
    }

    public double add(double x) {
        return average += (x - average) / ++n;
    }
}
