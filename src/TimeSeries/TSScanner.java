package TimeSeries;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TSScanner {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("/Users/navdeepgill/Desktop/Git/Stats-Math-Computing/Time Series/data/birth.txt"));
        scanner.useDelimiter(" ");
        while(scanner.hasNext()){
            System.out.print(scanner.next()+"|");
        }
        scanner.close();
    }

}
