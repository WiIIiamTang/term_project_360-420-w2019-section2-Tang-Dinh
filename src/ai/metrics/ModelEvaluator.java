package ai.metrics;

/** Unfinished.
** maybe add confusion matrix, cost function graph, roc curve, recall/precision table...
**
**
*/


public class ModelEvaluator
{
  public double getAccuracy(double[]yLabels, double[]predictedYLabels)
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
}
