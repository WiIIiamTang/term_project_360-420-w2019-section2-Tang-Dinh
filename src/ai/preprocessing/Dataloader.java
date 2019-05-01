package ai.preprocessing;

import java.io.*;
import java.util.Scanner;

/*
** info here
**
**
*/



public class Dataloader
{

  private double[][] allX;
  private double[] allY;
  private double[][] xTrain;
  private double[] yTrain;
  private double[][] xTest;
  private double[] yTest;

  //first some methods to return the arrays if you want

  public double[][] returnFullXArray()
  {
    return allX;
  }

  public double[] returnFullYArray()
  {
    return allY;
  }

  public double[][] returnXTrainArray()
  {
    return xTrain;
  }

  public double[] returnYTrainArray()
  {
    return yTrain;
  }

  public double[][] returnXTestArray()
  {
    return xTest;
  }

  public double[] returnYTestArray()
  {
    return yTest;
  }



  //then some methods to print them if you want to view the arrays

  public void printallX()
  {
    for(int i = 0; i < allX.length;i++)
    {
      for(int j = 0; j < allX[i].length; j++)
      {
        System.out.print(allX[i][j] + " ");
      }
      System.out.println();
    }
  }


  public void printallY()
  {
    for (int i = 0; i < allY.length; i++)
    {
      System.out.print(allY[i] + " ");
    }
  }

  public void printXTrain()
  {
    for(int i = 0; i < xTrain.length;i++)
    {
      for(int j = 0; j < xTrain[i].length; j++)
      {
        System.out.print(xTrain[i][j] + " ");
      }
      System.out.println();
    }
  }

  public void printYTrain()
  {
    for (int i = 0; i < yTrain.length; i++)
    {
      System.out.print(yTrain[i] + " ");
    }
  }

  public void printXTest()
  {
    for(int i = 0; i < xTest.length;i++)
    {
      for(int j = 0; j < xTest[i].length; j++)
      {
        System.out.print(xTest[i][j] + " ");
      }
      System.out.println();
    }
  }

  public void printYTest()
  {
    for (int i = 0; i < yTest.length; i++)
    {
      System.out.print(yTest[i] + " ");
    }
  }


  ////Ok now the actual stuff



  //this returns total number of data points in your cvs file
  // it counts the number of lines, so you can use it to verify if your arrays are initializing correctly
  public int returnNumDataPoints(String location)
  {
    File fileLocation = new File(location);
    int num = 0;

    try
    {
      FileReader fr = new FileReader(fileLocation);
      LineNumberReader lnr = new LineNumberReader(fr);


      while (lnr.readLine() != null)
      {
        num++;
      }

      //System.out.println(num);
      lnr.close();
    }
    catch (Exception e)
    {
      System.out.println(e);
    }

    return num;
  }


  //the makeArrays method should be the first thing you use. It will load the dataset onto the xFull and yFull arrays.

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

    System.out.println("Reading the dataset.txt and setting up the arrays...");
    //System.out.println(new File(".").getAbsoluteFile());

    //make the initial arrays
    allX = new double[row][col-1];
    allY = new double[row];


    try
    {
      scanIn = new Scanner(new BufferedReader(new FileReader(fileLocation))); //read the file

      //this loop stores the data in arrays
      while (scanIn.hasNextLine())
      {
        inputRow = scanIn.nextLine(); //read row from dataset.txt

        inputArray = inputRow.split(","); //store that row into array

        for (int i = 0; i < ((inputArray.length)-1); i++) //Copying that array into our xarray
        {
          allX[currentScanrow][i] = Double.parseDouble(inputArray[i]);
        }

        allY[currentScanrow] = Double.parseDouble(inputArray[inputArray.length-1]); //Copy the y label into a separate yArray

        //System.out.println("Done.");

        currentScanrow++; //go onto the next line
      }
    }

  catch (Exception e)
  {
    System.out.println(e);
  }


    System.out.println("Finished making arrays. There is an xArray with " + allX.length + " rows and " + allX[0].length + " columns, and a yArray (for labels) with " +  allY.length + " rows.");
  }//end makeArrays



  public int[] returnRowandCol(String fileLocation)
  {
    //This method is kind of the same as the previous one except its to find numb of row/colLength

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



  //this method is something that we might need to use to get all the y-labels to ONLY zeroes and ones, for a binary logistic regression.
  //this means that we'll check for one class label (only numerical for now!), and all the others get assigned something else (assignThisOtherwise)
  //the label we're checking for is assigned (assignThis). this methods works on the allY array ONLY.
  // Here's an example usage; say we want to get a dataset with wine 2 vs. other wines (type 1, 3):
  // Dataloader makeYLabels (2, 1, 0);
  // We check if the label is 2 here, and assign it to 1. Everything else is 0.

  public void makeYLabels(double checkIfThis, double assignThis, double assignThisOtherwise)
  {
    for (int i = 0; i < allY.length; i++)
    {
      if (allY[i] != checkIfThis)
      {
        allY[i] = assignThisOtherwise;
      }
      else
      {
        allY[i] = assignThis;
      }
    }

  }


  //Shuffle datapoints around

  public void shuffleData()
  {
    int max = (allX.length-1);
    int min = 0;
    int rollRange = (max - min) + 1;
    int roll = 0;
    double[][]temp = new double [allX.length][allX[0].length];
    double[]tempY = new double [allY.length];


    System.out.println("Shuffling data...");

    try
    {
      for (int i = 0; i < allX.length; i++)
      {
        roll = (int) ((Math.random() * rollRange) + min);//Generate the row to swap with the ith row.

        tempY[i] = allY[i]; //store the ith value in a temp array.

        allY[i] = allY[roll]; //ith row array is replaced by the value to swap.

        allY[roll] = tempY[i]; //the value to swap is replaced by the temp array.

        for (int j = 0; j < allX[0].length;j++)
        {

          temp[i][j] = allX[i][j]; //Store the ith row in a temp array.

          allX[i][j] = allX[roll][j]; //ith row array is replaced by the row to swap.

          allX[roll][j] = temp[i][j]; //the row to swap is replaced by the temp array.

        }
      }//repeats for all rows i.
    }
    catch (Exception e)
    {
      System.out.println("Something went wrong.");
      System.out.println(e);
    }

    System.out.println("Finished randomizing positions of data points.");

  }//end shuffleData



  ///Splits data with a certain percent level
  public void trainTestSplit(double trainingSplitPercent)
  {
    //initialize all arrays now
		int splitIndex = (int) (allX.length * trainingSplitPercent);
    xTrain = new double[splitIndex][allX[0].length];
    xTest = new double [allX.length - splitIndex][allX[0].length];
    yTrain = new double[splitIndex];
    yTest = new double [allY.length - splitIndex];
    int countrow = 0;

    //copy xTrain array
    for (int i = 0; i < splitIndex; i++)
    {
      for(int j = 0; j < xTrain[0].length; j++)
      {
        xTrain[i][j] = allX[i][j];
      }
    }

    //copy xTest array
    for (int i = splitIndex; i < allX.length; i++)
    {
      for(int j = 0; j < xTest[0].length; j++)
      {
        xTest[countrow][j] = allX[i][j];
      }
      countrow++;
    }

    //copy yTrain array
    for (int i = 0; i < splitIndex; i++)
    {
      yTrain[i] = allY[i];
    }

    //copy yTest array
    countrow = 0;
    for (int i = splitIndex; i < allY.length; i++)
    {
      yTest[countrow] = allY[i];
      countrow++;
    }

  }



}
