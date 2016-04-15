package movingaverage;

import transform.TransformFrame;
import water.fvec.Frame;
import water.fvec.Vec;

import java.util.concurrent.ThreadLocalRandom;

/**Cumulative Moving Average (CMA)
 *
 * In a cumulative moving average, the data arrive in an ordered datum stream, and the user would like to get the
 * average of all of the data up until the current datum point. For example, an investor may want the average price
 * of all of the stock transactions for a particular stock up until the current time. As each new transaction occurs,
 * the average price at the time of the transaction can be calculated for all of the transactions up to that point using
 * the cumulative average, typically an equally weighted average of the sequence of n values x_1...x_n up to the
 * current time.
 *
 * @author navdeepgill
 */

public class CumulativeMovingAverageFrame implements TransformFrame {
    int n = 0;
    double average = 0.0;

    @Override
    public Frame transform(Frame data){
        for(Vec v : data.vecs()){
            for (long x = 0; x < v.length(); ++x) {
                v.set(x,add(v.at(x)));
                //System.out.println(v.at(x));
            }
        }
        return data;
    }

    public double add(double x) {
        return average += (x - average) / ++n;
    }
}
