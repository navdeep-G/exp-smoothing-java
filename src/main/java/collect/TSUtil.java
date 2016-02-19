package main.java.collect;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author navdeepgill
 */
public class TSUtil {

    /**
     * Read in time series dataset
     *
     * @return An array list of data that is read in from the file path
     */
    public static ArrayList<Double> ReadFile(String filepath) throws IOException {
        FileReader fileReader = new FileReader(filepath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<Double> data = new ArrayList<>();
        String line;

        while ((line = bufferedReader.readLine()) != null)
        {
            data.add(Double.parseDouble(line));
        }

        bufferedReader.close();
        return data;
    }
}
