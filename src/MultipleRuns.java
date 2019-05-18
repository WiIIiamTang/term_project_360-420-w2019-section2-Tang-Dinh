import ai.preprocessing.Dataloader;
import ai.preprocessing.FeatureScaling;
import ai.models.LogisticRegression;
import ai.metrics.ModelEvaluator;
import java.util.*;


/**
** Logistic Regression for multiple runs
**
** prints average acc + standard deviation at the end
** also displays the confusion matrix
*/


public class MultipleRuns
{
  public static void main (String[] args)
  {

    Dataloader data = new Dataloader();

    ArrayList<Double> allTrueLabels = new ArrayList<Double>();
    ArrayList<Double> allPredictions = new ArrayList<Double>();
    ArrayList<Double> allAccuracies = new ArrayList<Double>();

    //This is just normal logistic regression, repeated for however many times you want. see Classification.java for the comments on what is happening.
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


      classifier.fit(0.1,5000,false); //alpha, maxiterations, randomize intial weights or not, check for tolerance level

      double[] predictionsOnTrainSet = classifier.predictTrainSet(0.5);
      double[] predictionsOnTestSet = classifier.predictTestSet(0.5);

      for(int j = 0; j < y_test.length; j++)
      {
        allTrueLabels.add(y_test[j]);
        allPredictions.add(predictionsOnTestSet[j]);
      }

      System.out.println("Running... " + i );

      allAccuracies.add(ModelEvaluator.getAccuracy(y_test, predictionsOnTestSet));
    }

    double[] allLabels = new double[allTrueLabels.size()];
    double[] allPredictedLabels = new double[allPredictions.size()];


    //we'll move all the accuracies, labels, and predictions to some normal arrays.
    for(int x = 0; x < allLabels.length; x++)
    {
      allLabels[x] = allTrueLabels.get(x);
      allPredictedLabels[x] = allPredictions.get(x);
    }

    //average and s.d. can just be obtained from calling a few methods on the acc. array.
    System.out.println("The average accuracy is " + average(allAccuracies));
    System.out.println("The standard deviation is " + sd(allAccuracies));

    System.out.println("\nHere is the confusion matrix: ");

    //we needed the labels and predictions stored to create the confustion matrix after.
    ModelEvaluator.confusionMatrix(allLabels, allPredictedLabels);




  }


  public static double average(ArrayList<Double> x)
  {
    double sum = 0;

    for (int i = 0; i < x.size(); i++)
    {
      sum = sum + x.get(i);
    }

    return sum/x.size();
  }

  public static double sd (ArrayList<Double> x)
  {
    double average = average(x);
    double sum = 0;

    for (int i = 0; i < x.size(); i++)
    {
      sum = sum + Math.pow(x.get(i) - average, 2);
    }

    return sum/x.size();
  }
}
