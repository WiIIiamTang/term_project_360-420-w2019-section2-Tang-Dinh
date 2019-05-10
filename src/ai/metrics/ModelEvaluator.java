package ai.metrics;

/** Unfinished.
** maybe add confusion matrix, cost function graph, roc curve, recall/precision table...
**
**
*/


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
        System.out.println(yLabels[i] + "  good   "  + predictedYLabels[i]);
      }
      else
      {
        bad++;
        System.out.println(yLabels[i] +"  bad   "  + predictedYLabels[i]);
      }
    }




    accuracy = (good/(good+bad))*100;
    return accuracy;
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
}
