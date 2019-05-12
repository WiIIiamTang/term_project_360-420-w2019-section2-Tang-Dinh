import ai.preprocessing.Dataloader;
import ai.preprocessing.FeatureScaling;
import ai.models.LogisticRegression;
import ai.metrics.ModelEvaluator;
import java.util.*;


/**
** Machine learning program - Logistic Regression
** This shows how we can classify a dataset with logistic regression with a few machine learning tools we made
**
** authors: William Tang and Jason Dinh
** check the github(@WiIIiamTang) for full documentation on the classes
*/


public class MultipleRuns
{
  public static void main (String[] args)
  {

    Dataloader data = new Dataloader();

    ArrayList<Double> allTrueLabels = new ArrayList<Double>();
    ArrayList<Double> allPredictions = new ArrayList<Double>();

    for (int i = 0; i < Integer.parseInt(args[1]); i++)
    {
      data.makeArrays(args[0]);
      data.shuffleData();
      data.trainTestSplit(0.70);

      double [][] x_train, x_test;
      double [] y_train, y_test;

      x_train = data.returnXTrainArray();
      x_test = data.returnXTestArray();
      y_train = data.returnYTrainArray();
      y_test = data.returnYTestArray();

      FeatureScaling.standardScaler(x_train, x_test);

      LogisticRegression classifier = new LogisticRegression(x_train, x_test, y_train, y_test);


      classifier.fit(0.01,10000,false); //alpha, maxiterations, randomize intial weights or not, check for tolerance level

      double[] predictionsOnTrainSet = classifier.predictTrainSet(0.5);
      double[] predictionsOnTestSet = classifier.predictTestSet(0.5);

      for(int j = 0; j < y_test.length; j++)
      {
        allTrueLabels.add(y_test[j]);
        allPredictions.add(predictionsOnTestSet[j]);
      }

      System.out.println("Running... " + i );
    }

    double[] allLabels = new double[allTrueLabels.size()];
    double[] allPredictedLabels = new double[allPredictions.size()];

    for(int x = 0; x < allLabels.length; x++)
    {
      allLabels[x] = allTrueLabels.get(x);
      allPredictedLabels[x] = allPredictions.get(x);
    }

    ModelEvaluator.confusionMatrix(allLabels, allPredictedLabels);




  }
}
