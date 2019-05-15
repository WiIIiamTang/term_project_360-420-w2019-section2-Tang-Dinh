import java.util.List;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.io.*;
import java.util.*;



public class kNNMain
{

  public static void main(String... args) throws FileNotFoundException
  {
    double[] accuracyArray = new double[1000];
    double[] baseAccArray = new double[1000];
    double [] recall = new double[1000];
    double [] precision = new double[1000];
    double mean1 = 0;
    double sd1 = 0;
    double mean2 = 0;
    double sd2 = 0;
    double mean3 = 0;
    double sd3 = 0;
    double mean4 = 0;
    double sd4 = 0;

    for (int t = 0; t< 1000; t++)
    {

      // TASK 1: Use command line arguments to point DataSet.readDataSet method to
      // the desired file. Choose a given DataPoint, and print its features and label
      List<DataPoint> points = new ArrayList<DataPoint>();
      List<DataPoint> training_set = new ArrayList<DataPoint>();
      List<DataPoint> test_set = new ArrayList<DataPoint>();

      String filelocation = new String(args[0]);

      points = DataSet.readDataSet(filelocation);

      //DataSet.printDataSet(points);
      //System.out.println("\nThe labels are " + DataSet.getLabels(points));

      //System.out.println("\nExample point: take the first DataPoint in the list.");
      //System.out.println(Arrays.toString(points.get(0).getX()) + " with label = " + points.get(0).getLabel());




      //TASK 2:Use the DataSet class to split the fullDataSet into Training and Held Out Test Dataset
      training_set = DataSet.getTrainingSet(points, 0.70);
      test_set = DataSet.getTestSet(points, 0.30);
      baseAccArray[t] = DataSet.printLabelFrequenciesAndReturnBaseAcc(test_set);

      // TASK 4: write a new method in DataSet.java which takes as arguments two DataPoint objects,
      // and returns the Euclidean distance between those two points (as a double)



      // TASK 5: Use the KNNClassifier class to determine the k nearest neighbors to a given DataPoint,
      // and make a print a predicted target label
      int numNeighbours = 5;
      KNNClassifier classifier = new KNNClassifier(numNeighbours);
/*
      DataPoint[] nearest = classifier.getNearestNeighbors(training_set, test_set.get(0));

      System.out.println("\nTest: the " + numNeighbours + " nearest neighbours to the first point of the test set are: ");

      for(int i = 0; i < nearest.length; i++)
      {
        for (int j = 0; j < nearest[i].x.length; j++)
        {
            System.out.print(nearest[i].x[j] + " ");
        }
        System.out.println(nearest[i].getLabel());

      }



      System.out.println("This will predict " + classifier.predict(training_set, test_set.get(0)) + "\n");


      // TASK 6: loop over the datapoints in the held out test set, and make predictions for Each
      // point based on nearest neighbors in training set. Calculate accuracy of model.
**/
      double good = 0;
      double bad = 0;
      double acc = 0;
      double tp = 0;
      double fp = 0;
      double fn = 0;
      double tn = 0;


      for(int i = 0; i < test_set.size(); i++)
      {
        if(test_set.get(i).getLabel().equalsIgnoreCase(classifier.predict(training_set, test_set.get(i))) && test_set.get(i).getLabel().equals("1"))
        {
          //System.out.println(test_set.get(i).getLabel() + "\tgood\t" + classifier.predict(training_set, test_set.get(i)));
          good++;
          tp++;
        }
        else if (test_set.get(i).getLabel().equalsIgnoreCase(classifier.predict(training_set, test_set.get(i))) && test_set.get(i).getLabel().equals("0"))
        {
          good++;
          tn++;
        }
        else if (test_set.get(i).getLabel().equals("1"))
        {
          bad++;
          fn++;
        }
        else
        {
          //System.out.println(test_set.get(i).getLabel() + "\tbad\t" + classifier.predict(training_set, test_set.get(i)));
          bad++;
          fp++;
        }
      }

        acc = 100*(good/(bad+good));
        accuracyArray[t] = acc;

        recall[t] = tp/(fn+tp);
        precision[t] = tp/(tp+fp);


    //System.out.printf("\nPredicted on test set, got an average accuracy of %.2f percent over %d tries\n", averageAcc, repeats);
    }

    mean1 = mean(accuracyArray);
    sd1 = standardDeviation(accuracyArray);

    System.out.println("Average acc = " + mean1);
    System.out.println("Standard Deviation = " + sd1);

    System.out.println("***BaseLine Comparison***");
    mean2 = mean(baseAccArray);
    sd2 = standardDeviation(baseAccArray);

    System.out.println("Average acc = " + mean2);
    System.out.println("Standard Deviation = " + sd2);

    mean3 = mean(recall);
    sd3 = standardDeviation(recall);

    System.out.println("Recall average acc = " + mean3);
    System.out.println("Standard Deviation = " + sd3);

    mean4 = mean(precision);
    sd4 = standardDeviation(precision);

    System.out.println("Precision average acc = " + mean4);
    System.out.println("Standard Deviation = " + sd4);
}

  public static double mean(double[] arr){
    /*
    Method that takes as an argument an array of doubles
    Returns: average of the elements of array, as a double
    */
    double sum = 0.0;

    for (double a : arr){
      sum += a;
    }
    return (double)sum/arr.length;
  }

  public static double standardDeviation(double[] arr){
    /*
    Method that takes as an argument an array of doubles
    Returns: standard deviation of the elements of array, as a double
    Dependencies: requires the *mean* method written above
    */
    double avg = mean(arr);
    double sum = 0.0;
    for (double a : arr){
      sum += Math.pow(a-avg,2);
    }
    return (double)sum/arr.length;
  }

}
