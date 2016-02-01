package TimeSeries;

//Not a real test...
public class TSCollectTest {

    private static TSCollect ts;

    public static void main(String args[])  {

        int num_points = 10;
        ts = new TSCollect(num_points);

        //Set time points
        for (int i = 0; i < num_points; i++) {
            ts.setTimePoint(i, i + 1);
        }

        //Get some parameters to print out
        double pacf [] = ts.pacf(2); //lag 2
        double acf[] = ts.acf(9); //lag 2

        System.out.println("PACF values at lag 2 for a time series with 10 data points (0,1,2,...,9):");
        for (int j = 0; j < pacf.length; j++){
            System.out.println(pacf[j]);
        }
        System.out.println("\n");

        System.out.println("ACF values at lag 2 for a time series with 10 data points (0,1,2,...,9):");
        for (int j = 0; j < acf.length; j++){
            System.out.println(acf[j]);
        }
        System.out.println("\n");

        //Output some calculations. Made into tests later...
        System.out.println("Average of time series with 10 data points (0,1,2,...,9): " + ts.getAverage());
        System.out.println("Variance of time series with 10 data points (0,1,2,...,9): " + ts.getVariance());
        System.out.println("AutoCovariance of time series with 10 data points (0,1,2,...,9): " + ts.getAutocovariance(2)); //lag 2
        System.out.println("AutoCorrelation of time series with 10 data points (0,1,2,...,9): " + ts.getAutocorrelation(2));//lag 2

    }


}
