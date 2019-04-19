public class Functions {
    public static double hypothesis(double[][] x, double[] beta, int row){
        double sigmoid = 0.;

        sigmoid = 1/(-Math.pow(Math.E,-linearSum(x, beta, row)));

        return sigmoid;
    }

    public static double linearSum(double[][] x, double[] beta, int row){
        double sum = 1.;

        sum = beta[0];
        for (int i = 1; i < x.length; i++){
            sum += beta[i] * x[row][i];
        }
    return sum;
    }

    public static double costFunction(double[][] x, double[] y, double[] beta){
        int m = x.length;
        double cost = 0.;
        double sum = 0.;

        for (int i = 0; i < m; i++){
            sum = y[i] * Math.log(hypothesis(x, beta, i)) + (1-y[i])*Math.log(1 - hypothesis(x, beta, i));
        }

        cost= -1/m * sum;

        return cost;
    }

    public static double gradient(int[][] x, int[] y, int betaNum){
        double delta = 0.;
        double sum = 0.;
        int m = x.length;

        for(int j = 0; j < x.length; j++){ 
            
            for(int i =0; i < m; i++){
                sum += hypothesis(x, beta, row) - y[i]; 
                sum *= x[i][j]
            }
            
        }

        delta = 1/m * sum;

        return delta;
    }

    public static double gradientDescent(double[][]x, double[] y, double[] beta){
        double alpha = 0.01;
        double[] betaNew = new double[beta.length];
        double difference[] = new double[beta.length];
        double tolerance = 0.01;
        boolean checkDifference = true;

        while (checkDifference){

            for(int i = 0; i < beta.length; i++){
               betaNew[i] -= alpha * gradient(x, y, i);
               difference = Math.abs(betaNew[i] - beta[i]);
               beta[i] = betaNew[i];
            }

           for(int i = 0; i < difference.length; i++){
                if (difference[i]>tolerance){
                    break;
                }
            checkDifference = false;
           }
        }

        return beta;
        
    }
}