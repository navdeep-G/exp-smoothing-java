package algos.ar;

import Jama.Matrix;

/**
 *
 * Class to build an autoregressive model and calculate the AR coefficients
 *
 * @author navdeepgill
 *
 */

public class AutoRegression {

    /**
     * Calculates the autoregressive coefficients for the input samples
     * @param inputseries input samples
     * @param order order of the model
     * @param removeMean remove the mean value
     * @return AR coefficients
     * @throws Exception
     */
    public static double[] calculateARCoefficients(double[] inputseries, int order, boolean removeMean) throws Exception{
        double[] w = null;
        if(removeMean){
            w = removeMean(inputseries);
        }
        else{
            w = inputseries;
        }


        //use the least squares method
        return calcLeastSquare(w,order);

        //use max entropy method of burg
        //return calcMaxEntropy(w,order);
    }

    /**
     * Calculate the Root Mean Square Error
     */
    public static double calculateRMSE(double[] inputseries, double[] arCoefficients, boolean removeMean){
        int length = inputseries.length;

        double[] w = null;
        if(removeMean){
            w = removeMean(inputseries);
        }
        else{
            w = inputseries;
        }

        int order = arCoefficients.length;
        double rmse = 0.0;
        for(int i=0; i<length; i++){
            double est = 0.0;
            double value = w[i];
            for(int j=0;j<order;j++){
                est += arCoefficients[j]*w[i];
            }
            rmse += Math.pow((value-est), 2.0);
        }
        rmse = rmse / (length-order);
        rmse =  Math.sqrt(rmse);
        return rmse;
    }


    public static double[] calculateEstimation(double[] inputseries, double[] arCoefficients, boolean removeMean){
        int length = inputseries.length;

        double[] w = null;
        if(removeMean){
            w = removeMean(inputseries);
        }
        else{
            w = inputseries;
        }

        int order = arCoefficients.length;

        double[] estimation = new double[length];
        for(int i=0; i<length; i++){
            double est = 0.0;
            for(int j=0;j<order;j++){
                est += arCoefficients[j]*w[i];
                //Printing for investigation purposes. Will be removed later.
                System.out.println("Estimation at order " + j + " and length " + i + ": " + est);
            }
            System.out.println("Estimation after implementing calculations at all orders: " + est);
            estimation[i] = est;
        }
        return estimation;
    }

    private static double[] removeMean(double[] inputseries){

        int length = inputseries.length;

        //calculate the mean of the timeseries and substract it from the sample values
        double[] w = new double[length];
        double mean =0.0;
        for (int t=0;t<length;t++){
            mean += inputseries[t];
        }

        mean /= (double)length;

        for (int t=0;t<length;t++){
            w[t] = inputseries[t] - mean;
        }
        return w;
    }


    private static double[] calcLeastSquare(double[] inputseries, int order) throws Exception{

        int length = inputseries.length;

        double ar[] = null;
        double[] coef = new double[order];
        double[][] mat = new double[order][order];

        //create a symetric matrix of covariance values for the past timeseries elements
        //and a vector with covariances between the past timeseries elements and the timeseries element to estimate.
        //start at "degree"-th sampel and repeat this for the length of the timeseries
        for(int i=order-1;i<length-1;i++) {
            for (int j=0;j<order;j++) {
                coef[j] += inputseries[i+1]*inputseries[i-j];
                for (int k=j;k<order;k++){ //start with k=j due to symmetry of the matrix...
                    mat[j][k] += inputseries[i-j]*inputseries[i-k];
                }
            }
        }

        //calculate the mean values for the matrix and the coefficients vector according to the length of the timeseries
        for (int i=0;i<order;i++) {
            coef[i] /= (length - order);
            for (int j=i;j<order;j++) {
                mat[i][j] /= (length - order);
                mat[j][i] = mat[i][j]; //use the symmetry of the matrix
            }
        }

        Matrix matrix = new Matrix(mat);
        Matrix coefficients = new Jama.Matrix(order,1);
        for(int i=0;i<order;i++){
            coefficients.set(i, 0, coef[i]);
        }

        //solve the equation "matrix * X = coefficients", where x is the solution vector with the AR-coeffcients
        try {
            ar = matrix.solve(coefficients).getRowPackedCopy();
        }
        catch(RuntimeException e){
            System.out.println("Matrix is singular");
        }
        return ar;
    }

    /**
     * Maximum-Entropie-Methode von Burg
     * @param inputseries
     * @param order
     * @return
     */
    private static double[] calcMaxEntropy(double[] inputseries, int order){

        int length = inputseries.length;
        double[] per = new double[length+1];
        double[] pef = new double[length+1];
        double[] h = new double[order+1];
        double[] g = new double[order+2];
        double[] coef = new double[order];
        double[][] ar = new double[order+1][order+1];

        double t1,t2;
        int n;

        for (n=1;n<=order;n++) {

            double sn = 0.0;
            double sd = 0.0;
            int j;
            int jj = length - n;

            for (j=0;j<jj;j++) {
                t1 = inputseries[j+n] + pef[j];
                t2 = inputseries[j] + per[j];
                sn -= 2.0 * t1 * t2;
                sd += (t1 * t1) + (t2 * t2);
            }

            g[n] = sn / sd;
            t1 = g[n];

            if (n != 1) {
                for (j=1;j<n;j++){
                    h [j] = g [j] + t1 * g [n - j];
                }
                for (j=1;j<n;j++){
                    g[j] = h[j];
                }
                jj--;
            }

            for (j=0;j<jj;j++) {
                per [j] += t1 * pef [j] + t1 * inputseries [j + n];
                pef [j] = pef [j + 1] + t1 * per [j + 1] + t1 * inputseries [j + 1];
            }

            for (j = 0; j < n; j++)
                ar [n][j] = g [j + 1];
        }

        for (int i=0;i<order;i++)
            coef[i] = -ar[order][i];

        return coef;
    }

}
