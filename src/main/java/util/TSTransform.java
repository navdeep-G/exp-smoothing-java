package main.java.util;

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

    static TSTransformFunction getFunction(Type t) {
        switch(t) {
            case LOG:
                return new Log();
            case ROOT_2:
                return new Root_2();
            case ROOT_3:
                return new Root_3();
            case ROOT_4:
                return new Root_4();
            default:
                throw new IllegalArgumentException("Transform function of type '" + t.name() + "' doesn't exist");
        }
    }
}

interface TSTransformFunction {
    List<Double> exec(List<Double> l);
}

class Log implements TSTransformFunction {
    public List<Double> exec (List<Double> l) {
        List<Double> t_list = new ArrayList<Double>();
        for (double i : l) {
            t_list.add(Math.log(i));
        }
        return t_list;
    }
}

class Root_2 implements TSTransformFunction {
    public List<Double> exec (List<Double> l) {
        List<Double> t_list = new ArrayList<Double>();
        for (double i : l) {
            t_list.add(Math.sqrt(i));
        }
        return t_list;
    }
}

class Root_3 implements TSTransformFunction {
    public List<Double> exec (List<Double> l) {
        List<Double> t_list = new ArrayList<Double>();
        for (double i : l) {
            t_list.add(Math.cbrt(i));
        }
        return t_list;
    }
}

class Root_4 implements TSTransformFunction{
    public List<Double> exec (List<Double> l) {
        List<Double> t_list = new ArrayList<Double>();
        for(double i : l){
            t_list.add(Math.pow(i,1.0/4));
        }
        return t_list;
    }
}