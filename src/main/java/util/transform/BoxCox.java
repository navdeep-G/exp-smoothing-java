package main.java.util.transform;

import main.java.util.TSUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;
import java.util.List;

/**Box Cox Transformation
 *
 * @author navdeepgill
 */
public class BoxCox {

    /**
     * Get Box Cox Transformation
     *
     * @return Time Series with desired Box Cox transformation
     */
    public static ArrayList<Double> getBoxCox(List<Double> data, int lam) throws IOException {
        ArrayList<Double> transform = new ArrayList<Double>();

        if(lam == 0){
            for (int i = 0; i < data.size(); i++) {
                transform.add(Math.log(data.get(i)));
            }
        }
        else{
            for (int i = 0; i < data.size(); i++) {
                transform.add((Math.pow(data.get(i), lam) - 1.0) / lam);
            }
        }
        return transform;
    }
}
