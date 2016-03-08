
import algos.MovingAverage;
import collect.TSCollect;
import java.util.ArrayList;

import java.io.IOException;


/**Simple way to check output of MovingAverage.
 *
 * @author navdeepgill
 */
public class MovingAverageTest {
    public static int lag = 2;
    public static  String pathToData = "data/birth.txt";

    public static void main(String[] args) throws IOException {
        TSCollect _tm = new TSCollect(pathToData,lag,lag);
        ArrayList<Double> testData = _tm.ReadFile();
        int[] windowSizes = {2};
        for (int windSize : windowSizes) {
            MovingAverage ma = new MovingAverage(windSize);
            for (double x : testData) {
                ma.newNum(x);
                System.out.println("Next number = " + x + ", SMA = " + ma.getAvg());
            }
        }
    }
}
