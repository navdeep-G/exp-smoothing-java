package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**Utility functions for time series analysis
 *
 * @author navdeepgill
 */
public class Util {

    /**
     * Read in time series dataset
     *
     * @return An array list of data that is read in from the file path
     */
    public static ArrayList<Double> ReadFile(String filepath) throws IOException {
        FileReader fileReader = new FileReader(filepath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<Double> data = new ArrayList<Double>();
        String line;

        while ((line = bufferedReader.readLine()) != null)
        {
            data.add(Double.parseDouble(line));
        }

        bufferedReader.close();
        return data;
    }
}
