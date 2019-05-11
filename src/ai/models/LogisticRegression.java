package ai.models;

/*
**
** construct the object by passing in the arrays first. then you can do logistic regression.
**
*/

public class LogisticRegression
{
  private double[] beta;
  private double[][] x_train;
  private double[][] x_test;
  private double[] y_train;
  private double[] y_test;


  //constructor
  public LogisticRegression(double[][]xTrainArray, double[][] xTestArray, double [] yTrainArray, double [] yTestArray)
  {
    beta = new double[xTrainArray[0].length+1];
    x_train = xTrainArray;
    x_test = xTestArray;
    y_train = yTrainArray;
    y_test = yTestArray;
  }

  //some methods to return the arrays again..
  public double[] returnWeights()
  {
    return beta;
  }

  public double[][] returnXTrainArray()
  {
    return x_train;
  }

  public double[] returnYTrainArray()
  {
    return y_train;
  }

  public double[][] returnXTestArray()
  {
    return x_test;
  }

  public double[] returnYTestArray()
  {
    return y_test;
  }

  //then some stuff to print out arrays
  public void printWeights()
  {
    for (int i = 0; i < beta.length; i++)
    {
      System.out.print(beta[i] + " ");
    }
  }

  public void printXTrain()
  {
    for(int i = 0; i < x_train.length;i++)
    {
      for(int j = 0; j < x_train[i].length; j++)
      {
        System.out.print(x_train[i][j] + " ");
      }
      System.out.println();
    }
  }

  public void printYTrain()
  {
    for (int i = 0; i < y_train.length; i++)
    {
      System.out.print(y_train[i] + " ");
    }
  }

  public void printXTest()
  {
    for(int i = 0; i < x_test.length;i++)
    {
      for(int j = 0; j < x_test[i].length; j++)
      {
        System.out.print(x_test[i][j] + " ");
      }
      System.out.println();
    }
  }

  public void printYTest()
  {
    for (int i = 0; i < y_test.length; i++)
    {
      System.out.print(y_test[i] + " ");
    }
  }




  public double linearSum(double[][] x, double[] beta, int row)
  {
      double sum = 0.;

      sum = sum + beta[0];

      for (int i = 1; i < beta.length; i++)
      {
          sum = sum + (beta[i] * x[row][i-1]);
      }

      return sum;
  }

  ///////////////////////////////////////////
  ///Computes the value of the hypothesis func
  ////////////////////////////////////////////

  public double hypothesis(double[][] x, double[] beta, int row)
  {
      double sigmoid = 0.;

      sigmoid = 1/(1+Math.pow(Math.E,-linearSum(x, beta, row)));

      return sigmoid;
  }

  //////////////////////////////////////////
  ///Computes the value of the costFunction
  ////////////////////////////////////////////

  public double costFunction(double[][] x, double[] y, double[] beta)
  {
    int m = x.length;
    double cost = 0.;
    double sum = 0.;
    int n = beta.length;

    for (int i = 0; i < m; i++)
    {
        if (y[i] == 1.0)
        {
          sum = sum + (Math.log(hypothesis(x, beta, i)));
        }
        else
        {
          sum = sum + Math.log(1 - hypothesis(x, beta, i));
        }
    }


    cost= -(sum/m);

    return cost;
  }

  //////////////////////////////////////////////
  ///Computes the value of the partial derivative
  /////////////////////////////////////////////////

  public double gradient(double[][] x, double[] y, double[] beta, int betaNum)
  {
    double sum = 0.;
    double toAdd = 0;
    double m = x.length;


    for(int i =0; i < m; i++)
    {
        toAdd = 0;

        toAdd += (hypothesis(x, beta, i) - y[i]);

        if(betaNum != 0)
        {
          toAdd *= x[i][betaNum-1];
        }

        sum += toAdd;
    }
    sum = sum/m;

    return sum;
  }

  ////////////////////////////////////////////
  ///Performs the gradient descent method
  ///and returns the theta array containing
  ///the optimal parameters
  /////////////////////////////////////////////

  public void fit(double learningRate, double maxIterations, boolean randomize)
  {
      double alpha = learningRate;
      double[] betaNew = new double[beta.length];
      double iterations = 0;
      double bestCost = 10000000;
      double currentCost = costFunction(x_train,y_train,beta);

      if (randomize == true)
      {
        assignRandom(beta);
      }

      while (iterations < maxIterations)
      {
          currentCost = costFunction(x_train,y_train,beta);

          for(int i = 0; i < beta.length; i++)
          {
             betaNew[i] = beta[i] - (alpha * gradient(x_train, y_train, beta, i));
          }

          for(int i = 0; i < beta.length;i++)
          {
            beta[i] = betaNew[i];
          }

         //Print cost function every few iterations

         if(iterations % 1000 == 0)
         {
           //System.out.println("Cost at " + costFunction(x_train, y_train, beta));
           //System.out.println(gradient(x,y,beta,0));
         }
         iterations++;

      }
      //System.out.println("The loop was terminated. Total iterations: " + iterations + "\n");

  }


  public void fit(double learningRate, double maxIterations, boolean randomize, double checkForDifference)
  {
      double alpha = learningRate;
      double[] betaNew = new double[beta.length];
      double difference[] = new double[beta.length];
      double tolerance = checkForDifference;
      double iterations = 0;
      boolean checkDifference = true;
      double bestCost = 1000000;
      double currentCost = costFunction(x_train,y_train,beta);

      if (randomize == true)
      {
        assignRandom(beta);
    }

      while (iterations < maxIterations && checkDifference == true)
      {
          currentCost = costFunction(x_train,y_train,beta);

          for(int i = 0; i < beta.length; i++)
          {
             betaNew[i] = beta[i] - (alpha * gradient(x_train, y_train, beta, i));
             difference[i] = Math.abs(beta[i] - betaNew[i]);
          }

          for(int i = 0; i < beta.length;i++)
          {
            beta[i] = betaNew[i];
          }


           for(int i = 0; i < difference.length; i++)
           {
                if (difference[i] > tolerance)
                {
                    break;
                }
            checkDifference = false;
          }

         //Print cost function every few iterations
         /*
         if(iterations % 1000 == 0)
         {
           System.out.println("Cost at " + costFunction(x_train, y_train, beta, regularizationParameter));
           //System.out.println(gradient(x,y,beta,0));
         } **/
         iterations++;

      }
      //System.out.println("The loop was terminated. Total iterations: " + iterations + "\n");

  }


  public void assignRandom(double[]array)
  {
    double max = 1.0;
    double min = 0.;
    double rollRange = (max - min);
    double roll = 0;

    for(int i = 0; i < array.length; i++)
    {
      roll = ((Math.random() * rollRange) + min);
      array[i] = roll;
    }
  }

  public double[] getPredictionsProbabilityTrain()
  {
    double[] prob = new double[x_train.length];

    for(int i = 0; i < prob.length; i++)
    {
      prob[i] = hypothesis(x_train, beta, i);
    }

    return prob;
  }

  public double[] getPredictionsProbabilityTest()
  {
    double[] prob = new double[x_test.length];

    for(int i = 0; i < prob.length; i++)
    {
      prob[i] = hypothesis(x_test, beta, i);
    }

    return prob;
  }


  public double[] predictTrainSet(double predictionThreshold)
  {
    double [] predicted = new double[x_train.length];

    double threshold = predictionThreshold; //change the prediction threshold here

    for(int i = 0; i<predicted.length; i++)
    {
      //System.out.println(hypothesis(x_train, beta, i));
      if(hypothesis(x_train, beta, i) > threshold)
      {
        predicted[i] = 1.0;
      }
      else
      {
        predicted[i] = 0.0;
      }

    }

    return predicted;

  }

  public double[] predictTestSet(double predictionThreshold)
  {
    double [] predicted = new double[x_test.length];

    double threshold = predictionThreshold; //change the prediction threshold here

    for(int i = 0; i<predicted.length; i++)
    {
      //System.out.println(hypothesis(xArray, beta, i));
      if(hypothesis(x_test, beta, i) > threshold)
      {
        predicted[i] = 1.0;
      }
      else
      {
        predicted[i] = 0.0;
      }

    }

    return predicted;

  }
}
