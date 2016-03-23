package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**Utility functions for time series analysis
 *
 * @author navdeepgill
 */
public class Util {

    /**
     * Read in time series dataset
     *
     * @return A list of data that is read from the file path
     */
    public static List<Double> ReadFile(String filepath) throws IOException {
        FileReader fileReader = new FileReader(filepath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<Double> data = new ArrayList<Double>();
        String line;

        while ((line = bufferedReader.readLine()) != null)
        {
            data.add(Double.parseDouble(line));
        }

        bufferedReader.close();
        return data;
    }

    /**
     * Read in a time series dataset with multiple columns
     *
     * @return A 2d list of data that is read from the file path
     */
    public static List<List<Double>> ReadCSV(String filepath) throws IOException {
        FileReader fileReader = new FileReader(filepath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<List<Double>> data = new ArrayList<List<Double>>();
        String line;
        Scanner sc;
        if ((line = bufferedReader.readLine()) != null) {
            sc = new Scanner(line);
            sc.useDelimiter(",");
            while (sc.hasNextDouble()) {
                List<Double> newList = new ArrayList<Double>();
                data.add(newList);
                newList.add(sc.nextDouble());
            }
            sc.close();
        }
        while ((line = bufferedReader.readLine()) != null) {
            sc = new Scanner(line);
            sc.useDelimiter(",");
            for (int i = 0; sc.hasNextDouble(); i += 1) {
                data.get(i).add(sc.nextDouble());
            }
            sc.close();
        }
        return data;
    }
}
