import org.junit.Test;
import algos.HoltWinters;
import util.Util;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import static org.junit.Assert.*;

public class TestHoltWinters {

    @Test
    public void forecastNISTData() throws IOException {
        String pathToData = "data/hotel.txt";
        List<Double> file = Util.ReadFile(pathToData);
        int period = 12;
        int m = 12;
        boolean debug = true;
        double alpha = 0.5;
        double beta = 0.4;
        double gamma = 0.6;

        List<Double> prediction = HoltWinters.forecast(file, alpha, beta, gamma,
                period, m, debug);

        // These are the expected results
        List<Double> expected = Arrays.asList(819.5);
        assertEquals(expected, prediction);
    }
}
