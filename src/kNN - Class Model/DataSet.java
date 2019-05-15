import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;

/**
  Dataset class Author: S. Bhatnagar
  -mostly static methods which operate on the data set
 */
public class DataSet {

  //////////////////////////////////////////////////////////////////////////////////////////////////
  // method that creates a list of dataPoints
  public static List<DataPoint> readDataSet(String file) throws FileNotFoundException {

    List<DataPoint> dataset = new ArrayList<DataPoint>();
    Scanner scanner = new Scanner(new File(file));

    String line;
    String[] columns;
    String label;

    while (scanner.hasNextLine()) {
      line = scanner.nextLine();
      columns = line.split(",");

      // feature vector will append 1 as x_0, and then take in all
      // but the last column (which is assigned as label)
      double[] X = new double[columns.length];

      X[0] = 1;
      for (int i = 1; i < columns.length; i++) {
        // check if feature is numeric
        if (isNumeric(columns[i - 1])) {
          X[i] = Double.parseDouble(columns[i - 1]);
        } else {
          // code to convert nominal X to numeric
        }
      }

      label = columns[columns.length - 1];

      // special fix of label for handwritten digits data set: label "10" switched to "0"
      if (label.equals("10")) {
        label = "0";
      }

      DataPoint dataPoint = new DataPoint(label, X);

      dataPoint.setTestOrTrain("held_out");
      dataset.add(dataPoint);
    }
    scanner.close();

    return dataset;
  }
  ///////////////////////////////////////////////////////////////////////////////////

  //////////////////////////////////////////////////////////////////////////////////////////////////
  // method that creates a list of dataPoints with higher order polynomial X upto to user defined
  // degree
  public static List<DataPoint> readDataSetHigherOrderFeatures(String file, int degree)
      throws FileNotFoundException {

    List<DataPoint> dataset = new ArrayList<DataPoint>();
    Scanner scanner = new Scanner(new File(file));

    String line;
    String[] columns;
    String label;

    // all dataPoints in dataset given dummy labelAsDouble, which will be changed
    // from with call to Logistic.train, based on target label
    double labelAsDouble = -1.;

    while (scanner.hasNextLine()) {
      line = scanner.nextLine();
      columns = line.split(",");

      // feature vector will append 1 as x_0, and then take in all
      // but the last column (which is assigned as label)
      double[] X = new double[columns.length];

      X[0] = 1;
      for (int i = 1; i < columns.length; i++) {
        // check if feature is numeric
        if (isNumeric(columns[i - 1])) {
          X[i] = Double.parseDouble(columns[i - 1]);
        } else {
          // code to convert nominal X to numeric
        }
      }

      label = columns[columns.length - 1];

      // add higher order X
      ArrayList<Double> higherOrderX = new ArrayList<Double>();

      for (int n = 0; n <= degree; n++) {
        for (int k = 0; k <= n; k++) {
          double xnk = Math.pow(X[1], n - k) * Math.pow(X[2], k);
          higherOrderX.add(xnk);
        }
      }

      // convert list to array
      double[] allX = new double[higherOrderX.size()];
      for (int i = 0; i < higherOrderX.size(); i++) {
        allX[i] = higherOrderX.get(i);
      }

      DataPoint dataPoint = new DataPoint(label, allX);

      dataPoint.setTestOrTrain("held_out");
      dataset.add(dataPoint);
    }
    scanner.close();

    System.out.print("Each data point now has the feature vector: ");
    for (int n = 0; n <= degree; n++) {
      for (int k = 0; k <= n; k++) {
        System.out.print(", x1^" + (n - k) + "*x2^" + k);
      }
    }
    System.out.println();

    return dataset;
  }
  ///////////////////////////////////////////////////////////////////////////////////

  ///////////////////////////////////////////////////////////////////////////////////
  // check is data entry is nominal or numeric
  public static boolean isNumeric(String str) {
    return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
  }
  ///////////////////////////////////////////////////////////////////////////////////

  ///////////////////////////////////////////////////////////////////////////////////
  // "split off" testSet by setting testOrTrain variable for each dataPoint based on fraction input
  // by user
  public static List<DataPoint> getTestSet(List<DataPoint> fullDataSet, double fractionTestSet) {

    // Random rnd = new Random(123);
    // Collections.shuffle(fullDataSet, rnd);
    Collections.shuffle(fullDataSet);

    List<DataPoint> testSet = new ArrayList<DataPoint>();

    // shuffle dataSet and split into test and training sets by setting
    // testOrTrain variable for each dataPoint
    for (int i = 0; i < fractionTestSet * fullDataSet.size(); i++) {
      fullDataSet.get(i).setTestOrTrain("test_set");
      testSet.add(fullDataSet.get(i));
    }

    return testSet;
  }
  //////////////////////////////////////////////////////////////////////////////////////

  ///////////////////////////////////////////////////////////////////////////////////
  // "split off" trainingSet by setting testOrTrain variable for each dataPoint based on fraction
  // input by user
  public static List<DataPoint> getTrainingSet(
      List<DataPoint> fullDataSet, double fractionTrainingSet) {

    // Random rnd = new Random(123);
    // Collections.shuffle(fullDataSet);
    Collections.shuffle(fullDataSet);

    List<DataPoint> trainingSet = new ArrayList<DataPoint>();

    int count = 0;
    int i = 0;
    while (count < fractionTrainingSet * fullDataSet.size() && i < fullDataSet.size()) {
      String currentSetting = fullDataSet.get(i).getTestOrTrain();

      if (currentSetting.equals("training_set")) {
        trainingSet.add(fullDataSet.get(i));
        count++;
      } else if (!currentSetting.equals("test_set")) {
        fullDataSet.get(i).setTestOrTrain("training_set");
        trainingSet.add(fullDataSet.get(i));
        count++;
      }

      i++;
    }

    return trainingSet;
  }
  ///////////////////////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////////////////////////
  // count & print frequencies of different labels
  public static double printLabelFrequenciesAndReturnBaseAcc(List<DataPoint> fullDataSet) {


    List<Double> freq = new ArrayList<Double>();
    double max = 0;
    double baseLineAcc = 0;
    double points = 0;

    HashMap<String, Integer> labelFrequencies = new HashMap<String, Integer>();

    List<String> labels = new ArrayList<String>();

    for (DataPoint i : fullDataSet) {
      labels.add(i.getLabel());
    }

    Set<String> uniqueSet = new HashSet<String>(labels);

    for (String temp : uniqueSet) {
      labelFrequencies.put(temp, Collections.frequency(labels, temp));
      System.out.println(temp + " " + Collections.frequency(labels, temp) + " dataPoints");
      freq.add((double)Collections.frequency(labels,temp));
    }

    max = freq.get(0);
    for (int i = 0; i < freq.size(); i++)
    {
      max = Math.max(max, freq.get(i));
    }
    for (int i = 0; i < freq.size(); i++)
    {
      points = points + freq.get(i);
    }
    baseLineAcc = max/points;

    return baseLineAcc;
  }
  ///////////////////////////////////////////////////////////////////////////////////////

  ////////////////////////////////////////////////////////////////////////////
  // get list (set) of unique labels
  public static Set<String> getLabels(List<DataPoint> fullDataSet) {

    List<String> labels = new ArrayList<String>();

    for (DataPoint i : fullDataSet) {
      labels.add(i.getLabel());
    }

    Set<String> uniqueSet = new HashSet<String>(labels);

    return uniqueSet;
  }
  ////////////////////////////////////////////////////////////////////////////

  ////////////////////////////////////////////////////////////////////////////
  //
  public static void printDataSet(List<DataPoint> fullDataSet) {
    for (DataPoint i : fullDataSet) {
      System.out.println(
          "X = "
              + Arrays.toString(i.getX())
              + ", label = "
              + i.getLabel()
              + ", label as vector: "
              + Arrays.toString(i.getX()));
    }
  }
  ////////////////////////////////////////////////////////////////////////////

  ////////////////////////////////////////////////////////////////////////////
  // TASK 4: make a method here called distanceEuclid

  public static double distanceEuclid(DataPoint point1, DataPoint point2)
  {
    double distance = 0;

    distance = Math.sqrt(Math.pow(point1.x[0] - point2.x[0], 2) + Math.pow(point1.x[1] - point2.x[1], 2) + Math.pow(point1.x[2] - point2.x[2], 2) + Math.pow(point1.x[3] - point2.x[3],2));
    return distance;
  }

  ////////////////////////////////////////////////////////////////////////////


}
