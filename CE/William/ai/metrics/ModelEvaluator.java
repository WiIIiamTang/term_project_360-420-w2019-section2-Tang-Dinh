package ai.metrics;

import java.util.*;


public class ModelEvaluator
{
  public static double getAccuracy(double[]yLabels, double[]predictedYLabels)
  {
    double good = 0;
    double bad = 0;
    double accuracy = 0;

    for (int i = 0; i < predictedYLabels.length; i++)
    {
      if (yLabels[i] == predictedYLabels[i])
      {
        good++;
        //System.out.println(yLabels[i] + "  good   "  + predictedYLabels[i]);
      }
      else
      {
        bad++;
        //System.out.println(yLabels[i] +"  bad   "  + predictedYLabels[i]);
      }
    }




    accuracy = (good/(good+bad))*100;
    return accuracy;
  }

  public static double getBaselineAcc(double[] yLabels)
  {
    int one = 0;
    int zero = 0;
    double mostfreq = 0;

    for (int i = 0; i < yLabels.length; i++)
    {
      if(yLabels[i] == 1.0)
      {
        one++;
      }
      else
      {
        zero++;
      }
    }

    if (one > zero)
    {
      mostfreq = one;
    }
    else
    {
      mostfreq = zero;
    }

    return mostfreq/(one+zero);

  }

  //c.m. for a binary situation. everything should be 0 or 1.
  public static void confusionMatrix(double[] yLabels, double[] predictedYLabels)
  {
    int tp = 0; //true positives
    int tn = 0; //true negatives
    int fp = 0; //false positives
    int fn = 0; //false negatives
    int actualZero = 0;
    int actualOne = 0;
    int predictedOne = 0;

    for(int i = 0; i < predictedYLabels.length; i++)
    {
      //true positive
      if ((yLabels[i] == predictedYLabels[i]) && (yLabels[i] == 1))
      {
        tp++;
        actualOne++;
        predictedOne++;
      }
      //true negatives
      else if ((yLabels[i] == predictedYLabels[i]) && (yLabels[i] == 0))
      {
        tn++;
        actualZero++;
      }
      //false positives
      else if ((yLabels[i] == 0) && (predictedYLabels[i] == 1))
      {
        fp++;
        actualZero++;
        predictedOne++;
      }
      //false negatives
      else if ((yLabels[i] == 1) && (predictedYLabels[i] == 0))
      {
        fn++;
        actualOne++;
      }
    }

    System.out.print("\n*****Confusion Matrix*****\n");
    System.out.print("\tPredicted:0\tPredicted:1\n");
    System.out.print("Actual:0\t" + tn +"\t" + fp + "\n");
    System.out.print("Actual:1\t" + fn +"\t" + tp + "\n");
    System.out.println("\nAccuracy =\t\t" + ((double)(tp+tn)/(tp+tn+fp+fn)));
    System.out.println("Error Rate =\t\t" + ((double)(fp+fn)/(tp+tn+fp+fn)));
    System.out.println("Sensitivity/Recall =\t" + ((double)tp/actualOne));
    System.out.println("False Positive Rate =\t" + ((double)fp/actualZero));
    System.out.println("Specificity =\t\t" + ((double)tn/actualZero));
    System.out.println("Precision =\t\t" + ((double)tp/predictedOne));
    System.out.println("Prevalence =\t\t" + ((double)actualOne/(tp+tn+fp+fn)));

  }

  public static double linearSum(double[][] x, double[] beta, int row)
  {
      double sum = 0.;

      sum = sum + beta[0];

      for (int i = 1; i < beta.length; i++)
      {
          sum = sum + (beta[i] * x[row][i-1]);
      }

      return sum;
  }

  public static double hypothesis(double[][] x, double[] beta, int row)
  {
      double sigmoid = 0.;

      sigmoid = 1/(1+Math.pow(Math.E,-linearSum(x, beta, row)));

      return sigmoid;
  }

  public static double logLikelihood(double[][] x, double[] y, double[] beta)
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

    cost= (sum/m);

    return cost;
  }

  public static double mcfaddenRSquared(double[][] x, double[] yLabels, double[] beta)
  {
    double rSquare = 0;
    double ones = 0;
    double zeroes = 0;
    double overallProb = 0;
    double[] overallBeta = new double[1];
    double logLikeliOverall = 0;
    double logLikeliModel = 0;

    for (int i = 0; i < yLabels.length; i++)
    {
      if (yLabels[i] == 1)
      {
        ones++;
      }
      else
      {
        zeroes++;
      }
    }

    overallProb = ones/(zeroes+ones);
    overallBeta[0] = overallProb;

    logLikeliOverall = logLikelihood(x, yLabels, overallBeta);
    logLikeliModel = logLikelihood(x, yLabels, beta);

    rSquare = (logLikeliOverall - logLikeliModel) / logLikeliOverall;



    return rSquare;
  }

  public static void rankWeights(double[] array)
  {
    double temp = 0;
    double max = 0.;
    int maxIndex = 0;
    int count = 0;

    double[] original = new double[array.length];

    for (int i = 0; i < original.length; i++)
    {
      original[i] = array[i];
    }

    System.out.println("\nWeight Ranking\nCoefficient\t\tFeatureNumber");


		for (int j = 0; j < array.length; j++)
		{
			max = array[count];

			for (int i = count; i < array.length; i++)
			{
				if (Math.abs(array[i]) >= Math.abs(max))
				{
					max = array[i];
					maxIndex = i;
				}
			}
			//System.out.print(max + "\t" + maxIndex + "\n");
			temp = array[count];
			array[count] = array[maxIndex];
			array[maxIndex] = temp;

			count++;
    }

    for(int i = 0; i < array.length; i++)
    {
      System.out.print(array[i] + "\t" + findOccurence(original, array[i]) + "\n");
    }


  }

  public static int findOccurence(double[] array, double search)
  {
    for (int i = 0; i < array.length; i++)
    {
      if(array[i] == search)
      {
        return i;
      }
    }
    return -1;
  }
}
