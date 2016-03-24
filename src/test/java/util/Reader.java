package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import algos.*;
import collect.Collect;
import movingaverage.*;
import transform.*;
import util.*;
import tests.*;
import water.H2O;
import water.fvec.Frame;
import util.TestUtil;
import water.fvec.Vec;

public class Reader extends TestUtil {
    //Define initial inputs for Collect
    public static int lag = 2;
    public static  String pathToData = "data/hotel.txt";
    public static double lambda = 1.6;

    public static void main (String[] args){
        //Make some objects that will show relevant output:
        System.out.println("Starting H2O...");
        H2O.main(new String[] {});
        //Setting up a dataset as a List<Double> for later use:
        System.out.println("Reading File to List...");
        List<Double> file = new ArrayList<Double>();
        try {
            file = Util.ReadFile(pathToData);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }

        System.out.println("****Reading File to Frame...");
        Frame fr = parse_test_file(pathToData);
        for(Vec v : fr.vecs()){
            for(long i = 0; i < v.length(); ++i){
                System.out.println(v.at(i));
            }
        }

        System.out.println("****Reading File...");
            for(int i = 0; i < file.size(); ++i){
                System.out.println(file.get(i));
            }
    }

}
