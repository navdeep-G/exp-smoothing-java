package main.java.util.transform;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

/**Methods to Transform Time Series (log and power transformations)
 *
 * @author navdeepgill
 */

public class TSTransform {

    public static List<Double> log(List<Double> data) {
        List<Double> t_list = new ArrayList<>(data.size());
        for (double i : data) {
            t_list.add(Math.log(i));
        }
        return t_list;
    }

    public static List<Double> sqrt(List<Double> data){
        List<Double> t_list = new ArrayList<>(data.size());
        for (double i : data) {
            t_list.add(Math.sqrt(i));
        }
        return t_list;
    }

    public static List<Double> cbrt(List<Double> data){
        List<Double> t_list = new ArrayList<>(data.size());
        for (double i : data) {
            t_list.add(Math.cbrt(i));
        }
        return t_list;
    }

    public static List<Double> root(List<Double> data, double root){
        List<Double> t_list = new ArrayList<>(data.size());
        for (double i : data) {
            t_list.add(Math.pow(i,1.0/root));
        }
        return t_list;
    }

    public static List<Double> boxCox(List<Double> data, double lambda) {
        return BoxCox.getBoxCox(data, lambda);
    }

}