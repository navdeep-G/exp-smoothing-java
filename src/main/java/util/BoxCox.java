package main.java.util;

import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

/**Box Cox Transformation
 *
 * @author navdeepgill
 */
public class BoxCox {

    private final String _filepath;
    private final int _lambda;
    private final ArrayList<Double> _data;

    public BoxCox(String filepath,int lambda) throws IOException {
        _filepath = filepath;
        _lambda = lambda;
        _data = TSUtil.ReadFile(_filepath);
    }

    /**
     * Get Box Cox Transformation
     *
     * @return Time Series with desired Box Cox transformation
     */
    public ArrayList<Double> getBoxCox() throws IOException {
        ArrayList<Double> file = _data;
        ArrayList<Double> transform = new ArrayList<Double>();

        if(_lambda == 0){
            for (int i = 0; i < file.size(); i++) {
                transform.add(Math.log(file.get(i)));
            }
        }
        else{
            for (int i = 0; i < file.size(); i++) {
                transform.add((Math.pow(file.get(i), _lambda) - 1.0) / _lambda);
            }
        }
        return transform;
    }
}
