package movingaverage;

import java.util.ArrayList;
import java.util.List;

/**Cumulative Moving Average (CMA)
 *
 * @author navdeepgill
 */

public class CumulativeMovingAverage {
    int n = 0;
    double average = 0.0;

    public List<Double> getCMA(List<Double> data){
        List<Double> cma_data = new ArrayList<Double>(data.size());

        for (int i=0;i<data.size();i++) {
            cma_data.add(add(data.get(i)));
        }
        return cma_data;
    }

    public double add(double x) {
        return average += (x - average) / ++n;
    }
}
