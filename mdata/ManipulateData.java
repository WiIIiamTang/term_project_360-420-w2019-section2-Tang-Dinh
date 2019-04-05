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
		String fileLocation = "Datasets/dataset.txt"; //<<INSERT FILE LOCATION HERE
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
	
	
}
		
		
				
				
				
		
		