package movingaverage;

/**Cumulative Moving Average (CMA)
 *
 * @author navdeepgill
 */

class CumulativeMovingAverage {
    int n = 0;
    double average = 0.0;

    public double add(double x) {
        return average += (x - average) / ++n;
    }
}
