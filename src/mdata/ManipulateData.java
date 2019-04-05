package mdata;

import java.util.Scanner;
import java.io.*;

//some functions that may be useful in manipulating the data

public class ManipulateData
{
	/////////////////////////////////////////////////////////////////////////////////////
	//the makeArrays methods reads in the dataset file, which should be in a CSV file format
	//it takes in two arrays, where the yArray should hold all the y-labels.
	//PUT THE Y LABELS IN THE LAST COLUMN.
	//ALL VALUES ONLY NUMBERS SEPARATED BY ONE COMMA. MAKE SURE NOTHING IS MISSING
	/////////////////////////////////////////////////////////////////////////////////////
	
	public static void makeArrays(double[][]xArray, double[]yArray)
	{
		//Create the array to hold the data first
		String[] inputArray;
		
		//Create other variables
		Scanner scanIn = null;
		String inputRow = "";
		String fileLocation = "mdata/dataset.txt"; //<<INSERT FILE LOCATION HERE
		int row = 0;
		int col = 0;
		
		System.out.println("I'm reading the dataset.txt and setting up the arrays...");
		
		
		try
		{
			scanIn = new Scanner(new BufferedReader(new FileReader(fileLocation)));
			
			while (scanIn.hasNextLine())
			{
				inputRow = scanIn.nextLine(); //read row from dataset.txt
				//System.out.println("Reading row");
				
				inputArray = inputRow.split(","); //store that row into array
				//System.out.println("Splitting and storing into array");
				
				//System.out.println("Copying row into main arrays..");
				for (int i = 0; i < ((inputArray.length)-1); i++) //Copying that array into our xarray
				{
					xArray[row][i] = Double.parseDouble(inputArray[i]);
				}
				
				yArray[row] = Double.parseDouble(inputArray[inputArray.length-1]); //Copy the y label into a separate yArray
				
				row++; //Move to next row in arrays
				
				//System.out.println("Done.");
			}
		}
		
		catch (Exception e)
		{
			System.out.println("Something went wrong. Make sure the array sizes are correctly defined.");
			System.out.println(e);
		}
		
		
		System.out.println("Finished making arrays. There is an xArray with " + xArray.length + " rows and " + xArray[0].length + " columns, and a yArray (for labels) with " +  yArray.length + " rows.");
	}//end makeArrays
	
	
	/////////////////////////////////////////////////////
	//the following three methods prints a two dimensional array or one dim array; used for testing stuff
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void printAllArrays(double[][]xArray, double []yArray)
	{
		for(int i = 0; i < xArray.length;i++)
		{
			for(int j = 0; j < xArray[i].length; j++)
			{
				System.out.print(xArray[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
		
		for (int i = 0; i < yArray.length; i++)
		{
			System.out.print(yArray[i] + " ");
		}
	}//end printAllArrays
	
	//print any 2d array
	public static void print2DArray(double [][]TwoDArray)
	{
		for(int i = 0; i < TwoDArray.length;i++)
		{
			for(int j = 0; j < TwoDArray[i].length; j++)
			{
				System.out.print(TwoDArray[i][j] + " ");
			}
			System.out.println();
		}
	}//end print2darray
	
	//print any 1d array
	public static void printArray (double [] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			System.out.print(array[i] + " ");
		}
	}//end printarray
	
	
	
	//////////////////////////////////////////////////
	//makeYLabels will look at the precipitation values and assign a 0 for no prcp or 1 for prcp
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void makeYLabels(double[]yArray)
	{
		for (int i = 0; i < yArray.length; i++)
		{
			if (yArray[i] != 0)
			{
				yArray[i] = 1.0;
			}
		}
		
		System.out.println("Turned yArray values into binary outputs.");
		
	}//end makeYLabels
	
	
	
	////////////////////////////////////////////////////////////////////////
	/////not the best way of randomizing position of data points but ok
	/////takes in a twoD array and a normal array and randomizes the points.
	/////in the case of the twoD array it will move the rows around.
	///////////////////////////////////////////////////////////////////////////////////
	public static void shuffleData(double[][]xArray, double[]yArray)
	{
		int max = (xArray.length-1);
		int min = 0;
		int rollRange = (max - min) + 1;
		int roll = 0;
		double[][]temp = new double [xArray.length][xArray[0].length];
		double[]tempY = new double [yArray.length];
		
		
		System.out.println("Shuffling data...");
		
		try
		{
			for (int i = 0; i < xArray.length; i++)
			{
				roll = (int) ((Math.random() * rollRange) + min);//Generate the row to swap with the ith row.
				
				tempY[i] = yArray[i]; //store the ith value in a temp array.
				
				yArray[i] = yArray[roll]; //ith row array is replaced by the value to swap.
				
				yArray[roll] = tempY[i]; //the value to swap is replaced by the temp array.
				
				for (int j = 0; j < xArray[0].length;j++)
				{
					
					temp[i][j] = xArray[i][j]; //Store the ith row in a temp array.
					
					xArray[i][j] = xArray[roll][j]; //ith row array is replaced by the row to swap.
					
					xArray[roll][j] = temp[i][j]; //the row to swap is replaced by the temp array.
					
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
	
	
	
	////////////////////////////////////////
	//Computes and returns the mean from a 2D array, it's the mean OF ONE COLUMN.
	//Useful for putting the temperatures on a normalized distribution, for example
	////////////////////////////////////////////////////////////////////////////////
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
	
	///////////////////////////////////////////
	//Computes and returns the standard deviation from a 2D array, its the sd OF ONE COLUMN.
	/////////////////////////////////////////////////////////////
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
			
	
	////////////////////////////////////////////////////////////////
	/////Standardize data - just taking the z-score
	/////CONVERTS ONE COLUMN INTO Z-SCORES
	/////////////////////////////////////////////////////////////////////////////
	public static void convertToZScore(double[][]xArray, int col)
	{
		double zScore = 0;
		double mean = computeMean(xArray, col);
		double sd = computeSD(xArray, col);
		
		System.out.println ("Converting column " + col + " to Z scores...");
		for (int i = 0; i < xArray.length; i++)
		{
			zScore = ((xArray[i][col] - mean ) / sd);
			xArray[i][col] = zScore;
		}
		
		
		System.out.println ("Done converting column.");
	}//endzscore
		
	////////////////////////////////////////////////
	///Splits data selon a certain percent level
	///Will take in first the TARGET ARRAY, then the INITIAL ARRAY, then the INDEX OF WHERE YOU WANT TO STOP, then THE STARTING INDEX.
	///Will copy rows like this: always starts from INDEX startingIndex, THEN GOES UP UNTIL IT REACHES THAT INDEX OF THE INITIAL ARRAY STOPSPLIT.
	/////////////////////////////////////////////////////////
	public static void dataSplit2D(double[][]targetArray, double[][]iniArray, int stopSplit, int startingIndex)
	{
		
		for (int i = 0; i < (stopSplit-startingIndex);i++)
		{
			for(int j = 0; j < targetArray[0].length; j++)
			{
				targetArray[i][j] = iniArray[startingIndex+i][j];
			}
		}
		
	}//end dataSplit2D
	
	public static void dataSplitNormal(double [] targetArray, double []iniArray, int stopSplit, int startingIndex)
	{
		
		for (int i = 0; i < (stopSplit-startingIndex); i++)
		{
			targetArray[i] = iniArray[startingIndex+i];
		}
		
	}//end dataSplitNormal
	
	
}
		
		
				
				
				
		
		