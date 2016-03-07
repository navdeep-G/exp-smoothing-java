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
     * Calculate a Box Cox Transformation for a given lambda
     *
     *@param  data a List<Double> of time series data
     * @param lam desired lambda for transformation
     *
     * @return  Time series List<Double> with desired Box Cox transformation
     */
    public static List<Double> getBoxCox(List<Double> data, int lam) throws IOException {
        List<Double> transform = new ArrayList<Double>();

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
