package main.java.util.transform;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.lang.IllegalArgumentException;

/**Methods to Transform Time Series (log and power transformations)
 *
 * @author navdeepgill
 */

public class TSTransform {

    public enum Type {
        LOG, ROOT_2, ROOT_3,ROOT_4;
    }
    public static List<Double> getTransform(List<Double> data,Type t) throws IOException {
        switch(t) {
            case LOG:
                return Log(data);
            case ROOT_2:
                return Root_2(data);
            case ROOT_3:
                return Root_3(data);
            case ROOT_4:
                return Root_4(data);
            default:
                throw new IllegalArgumentException("Transform function of type '" + t.name() + "' doesn't exist");
        }
    }

    public static List<Double> Log(List<Double> data){
        List<Double> t_list = new ArrayList<>();
        for (double i : data) {
            t_list.add(Math.log(i));
        }
        return t_list;
    }

    public static List<Double> Root_2(List<Double> data){
        List<Double> t_list = new ArrayList<>();
        for (double i : data) {
            t_list.add(Math.sqrt(i));
        }
        return t_list;
    }

    public static List<Double> Root_3(List<Double> data){
        List<Double> t_list = new ArrayList<>();
        for (double i : data) {
            t_list.add(Math.cbrt(i));
        }
        return t_list;
    }

    public static List<Double> Root_4(List<Double> data){
        List<Double> t_list = new ArrayList<>();
        for (double i : data) {
            t_list.add(Math.pow(i,1.0/4));
        }
        return t_list;
    }

}