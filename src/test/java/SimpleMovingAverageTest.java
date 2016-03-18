import collect.Collect;
import movingaverage.SimpleMovingAverage;
import java.util.ArrayList;
import java.io.IOException;


/**Simple way to check output of SimpleMovingAverage.
 *
 * @author navdeepgill
 */
public class SimpleMovingAverageTest {
    public static int lag = 2;
    public static  String pathToData = "data/hotel.txt";

    public static void main(String[] args) throws IOException {
        Collect _tm = new Collect(pathToData,lag,lag);
        ArrayList<Double> testData = _tm.ReadFile();
        int[] windowSizes = {2};
        for (int windSize : windowSizes) {
            SimpleMovingAverage ma = new SimpleMovingAverage(windSize);
            for (double x : testData) {
                ma.newNum(x);
                System.out.println("Next number = " + x + ", SMA = " + ma.getAvg());
            }
        }
    }
}
