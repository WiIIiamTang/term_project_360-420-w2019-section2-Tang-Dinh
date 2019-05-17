package ai.models;

import java.io.*;
import java.util.Scanner;

/* in unsupervised machine learning, you would not know the real class labels.
** When you create the k means clustering object, the file you load in should
** contain all the feature variables but no class labels
**/

public class KMeansCluster
{
  private double[][] dataPoints;
  private double[] realClassLabels;
  private double[][] centroids;
  private double[] clusteredClassLabels;
  private int[] classCounts;

  private int numOfClusters;


  public KMeansCluster(String fileLocation, int clusters)
  {
    makeArrays(fileLocation);
    numOfClusters = clusters;
    centroids = new double[numOfClusters][];
    classCounts = new int[numOfClusters];
  }


  public double[][] returnCentroids()
  {
    return centroids;
  }

  public void printCentroids()
  {
    for(int i = 0; i < centroids.length; i++)
    {
      System.out.print("\nCentroid " + i + ": [");
      for (int j = 0; j < centroids[i].length; j++)
      {
        System.out.print(centroids[i][j] + " ");
      }
      System.out.print("]");
    }
  }

  public void printClassCounts()
  {
    for(int i = 0; i < classCounts.length; i++)
    {
      System.out.print("Class counts " + i + ":" + classCounts[i] + " ");
    }
  }

  public void printAllClustered()
  {
    System.out.print("Point\tClustered Class\n");
    for (int i = 0; i < dataPoints.length; i++)
    {
      for(int j = 0; j < dataPoints[i].length; j++)
      {
        System.out.printf("%6.3f ", dataPoints[i][j]);
      }
      System.out.printf("\t\t%6.1f\n",clusteredClassLabels[i]);
    }
  }

  public int[] returnClassCounts()
  {
    return classCounts;
  }

  public void setCentroids(double[][]x)
  {
    centroids = x;
  }

  public void setClusteredClassestoReal(String fileLocation)
  {
    //Create the temporary array to hold the data first
    String[] inputArray;

    //Create other variables
    Scanner scanIn = null;
    String inputRow = ""; //this will be the variable that holds the row!
    int[] numRandC = returnRowandCol(fileLocation);
    int row = numRandC[0];
    int col = numRandC[1];
    int currentScanrow = 0;

    //make the initial arrays
    double[] realStuff = new double[row];
    //System.out.println(row);



    try
    {
      scanIn = new Scanner(new BufferedReader(new FileReader(fileLocation))); //read the file

      //this loop stores the data in arrays
      while (scanIn.hasNextLine())
      {
        inputRow = scanIn.nextLine(); //read row from dataset.txt

        inputArray = inputRow.split(""); //store that row into array

        realStuff[currentScanrow] = Double.parseDouble(inputArray[0]);
        //System.out.println(realStuff[i]);

        currentScanrow++;
      }


      for(int i = 0; i < realStuff.length; i++)
      {
        clusteredClassLabels[i] = realStuff[i];
        //System.out.println(realStuff[i]);
      }
    }

    catch (Exception e)
    {
      System.out.println(e);
    }

  }




  public void makeArrays(String fileLocation)
  {
    //Create the temporary array to hold the data first
    String[] inputArray;

    //Create other variables
    Scanner scanIn = null;
    String inputRow = ""; //this will be the variable that holds the row!
    int[] numRandC = returnRowandCol(fileLocation);
    int row = numRandC[0];
    int col = numRandC[1];
    int currentScanrow = 0;
    clusteredClassLabels = new double[row];
    centroids = new double[row][col];

    //System.out.println("Reading the dataset.txt and setting up the arrays...");
    //System.out.println(new File(".").getAbsoluteFile());

    //make the initial arrays
    dataPoints = new double[row][col];



    try
    {
      scanIn = new Scanner(new BufferedReader(new FileReader(fileLocation))); //read the file

      //this loop stores the data in arrays
      while (scanIn.hasNextLine())
      {
        inputRow = scanIn.nextLine(); //read row from dataset.txt

        inputArray = inputRow.split(","); //store that row into array

        for (int i = 0; i < ((inputArray.length)); i++) //Copying that array into our xarray
        {
          dataPoints[currentScanrow][i] = Double.parseDouble(inputArray[i]);
        }

        currentScanrow++; //go onto the next line
      }
    }

    catch (Exception e)
    {
      System.out.println(e);
    }



  }//end makeArrays


  //This method is kind of the same as the previous one except its to find numb of row/colLength
  public int[] returnRowandCol(String fileLocation)
  {


    String[] inputArray;
    int[] toReturn = new int[2];
    Scanner scanIn = null;
    String inputRow = "";
    int row = 0;
    int col = 0;

    try
    {
      scanIn = new Scanner(new BufferedReader(new FileReader(fileLocation))); //ok this reads the file
      scanIn.useDelimiter(","); //because it should be a csv file.


      // So we need to count the number of rows and columns first to make the arrays.
      while(scanIn.hasNextLine())
      {
        inputRow = scanIn.nextLine();
        String[] colLength = inputRow.split(",");
        row++;
        col = colLength.length; //all the columns should have the same length if no values are missing!
      }

    }
    catch (Exception e)
    {
      System.out.println(e);
    }

    toReturn[0] = row;
    toReturn[1] = col;

    return toReturn;
  }// end returnRowandCol

  //Shuffle datapoints around

  public void shuffleData()
  {
    int max = (dataPoints.length);
    int min = 0;
    int rollRange = (max - min) + 1;
    int roll = 0;
    double[][]temp = new double [dataPoints.length][dataPoints[0].length];


    //System.out.println("Shuffling data...");

    try
    {
      for (int i = 0; i < dataPoints.length; i++)
      {
        roll = (int) ((Math.random() * rollRange) + min);//Generate the row to swap with the ith row.

        for (int j = 0; j < dataPoints[0].length;j++)
        {

          temp[i][j] = dataPoints[i][j]; //Store the ith row in a temp array.

          dataPoints[i][j] = dataPoints[roll][j]; //ith row array is replaced by the row to swap.

          dataPoints[roll][j] = temp[i][j]; //the row to swap is replaced by the temp array.

        }
      }//repeats for all rows i.
    }
    catch (Exception e)
    {
      System.out.println("Something went wrong.");
      System.out.println(e);
    }

    //System.out.println("Finished randomizing positions of data points.");

  }//end shuffleData

  //Computes and returns the mean from a 2D array, it's the mean OF ONE COLUMN - usually it will be ONE FEATURE VARIABLE, LIKE TEMPERATURE
  //Useful for putting the data on a normalized distribution
  public static double computeMean(double[][]xArray, int col)
  {
    double sum = 0;
    double average = 0;

    for (int i = 0; i < xArray.length; i++)
    {
      sum = sum + xArray[i][col];
    }

    average = (sum / xArray.length);

    return average;
  } //end computeMean


  //Computes and returns the standard deviation from a 2D array, its the sd OF ONE COLUMN
  public static double computeSD(double[][]xArray, int col)
  {
    double meanDiff = 0;
    double sumMeanDiff = 0;
    double sd = 0;
    double mean = computeMean(xArray, col);

    for (int i = 0; i < xArray.length; i++)
    {
      meanDiff = Math.pow((xArray[i][col] - mean),2);

      sumMeanDiff = sumMeanDiff + meanDiff;
    }

    sd = Math.sqrt(sumMeanDiff / (xArray.length));

    return sd;
  }//end sd



  /////Standardize data - just taking the z-score
  /////you put in the training array first, then the test array. All the columns will be standardized to the mean and sd of the training set.
  public void standardScaler()
  {
    double zScore = 0;
    double mean = 0;
    double sd = 0;

    for (int col = 0; col < dataPoints[0].length; col++)
    {
      mean = computeMean(dataPoints, col);
      sd = computeSD(dataPoints, col);

      for (int i = 0; i < dataPoints.length; i++)
      {
        zScore = ((dataPoints[i][col] - mean ) / sd);
        dataPoints[i][col] = zScore;
      }

    }



    //System.out.println ("Done converting columns.");
  }//endzscore


  public void initialCentroids()
  {
    int max = 0;
    int min = -(int)(dataPoints.length/numOfClusters);
    int rollRange = (max - min);
    int roll = 0;

    for(int i = 0; i < centroids.length; i++)
    {
      max += (int)(dataPoints.length/numOfClusters);
      min += (int)(dataPoints.length/numOfClusters);
      if (max >= dataPoints.length)
        max = dataPoints.length;

      rollRange = (max - min);
      roll = (int) ((Math.random() * rollRange) + min);
      centroids[i] = dataPoints[roll];
    }
  }

  public double euclidDistance(double[] point1, double[]point2)
  {
    double distance = 0;
    double diff = 0;

    for (int i = 0; i < point1.length; i++)
    {
      diff = Math.pow(point1[i] - point2[i], 2);
      distance = distance + diff;
    }

    return Math.sqrt(distance);
  }


  public void cluster()
  {
    double minDistance = 999999;
    int closestIndex = 0;

    for (int i = 0; i < dataPoints.length; i++)
    {
      minDistance = euclidDistance(dataPoints[i], centroids[0]);
      closestIndex = 0;

      for (int j = 0; j < centroids.length; j++)
      {

        if (euclidDistance(dataPoints[i],centroids[j]) < minDistance)
        {
          minDistance = euclidDistance(dataPoints[i],centroids[j]);
          closestIndex = j;
        }

        clusteredClassLabels[i] = closestIndex;

      }
    }

  }

  public double computeAverageDistanceToCentroids()
  {
    double[] newPoint = new double[dataPoints[0].length];
    double[] sums = new double[centroids.length];
    int count = 0;
    double totalSum = 0;

    for(int i = 0; i < numOfClusters; i++)
    {
      count = 0;

      for (int j = 0; j < dataPoints.length; j++)
      {
        if (clusteredClassLabels[j] == i)
        {
          sums[i] = sums[i] + euclidDistance(dataPoints[j], centroids[i]);
          count++;
        }
      }

      sums[i] = sums[i]/Math.max(1,count);
    }

    for(double i: sums)
    {
      totalSum = totalSum + i;
    }

    return totalSum;


  }


  public void getCentroids()
  {
    double[] newPoint = new double[dataPoints[0].length];
    int count = 0;

    for(int i = 0; i < numOfClusters; i++)
    {
      count = 0;

      for (int j = 0; j < dataPoints.length; j++)
      {
        if (clusteredClassLabels[j] == i)
        {
          for (int k = 0; k < newPoint.length; k++)
          {
            newPoint[k] = newPoint[k] + dataPoints[j][k];
          }
          count++;
        }
      }

      for (int a = 0; a < newPoint.length; a++)
      {
        newPoint[a] = newPoint[a]/Math.max(1,count);
        //System.out.println(count);
      }

      for (int b = 0; b < newPoint.length; b++)
      {
        centroids[i][b] = newPoint[b];
      }

      classCounts[i] = count;
    }

  }

  public boolean checkConvergence(double[][] point1, double[][] point2)
  {
    boolean check = true;

    for(int i = 0; i < point1.length; i++)
    {
      for (int j = 0; j < point1[i].length; j++)
      {
        if (point1[i][j] == point2[i][j])
        {
          check = true;
          //System.out.println("hey");
        }
        else check = false;
      }
    }
    return check;
  }




}
