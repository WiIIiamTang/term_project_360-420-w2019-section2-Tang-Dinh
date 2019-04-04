import java.util.Scanner;
import java.io.*;

/*CSV TO ARRAY TEST
**wILIAM
*/

public class ImportToArray
{
	public static void makeCVSArray(double[][]xArray, double[]yArray)
	{
		//Create the array to hold the data first
		String[] inputArray;
		
		//Create other variables
		Scanner scanIn = null;
		String inputRow = "";
		String fileLocation = "data.txt"; //<<INSERT FILE LOCATION HERE
		int row = 0;
		int col = 0;
		
		System.out.println("**************Setting up the Array***************");
		
		
		try
		{
			scanIn = new Scanner(new BufferedReader(new FileReader(fileLocation)));
			
			while (scanIn.hasNextLine())
			{
				inputRow = scanIn.nextLine(); //This reads the whole row from file
				System.out.println("Reading row");
				
				inputArray = inputRow.split(","); //Now store that row into an array after splitting up all the elements
				System.out.println("Splitting and storing into array");
				
				System.out.println("Copying row into main arrays..");
				for (int i = 0; i < ((inputArray.length)-1); i++) //Copying that array into our xarray
				{
					xArray[row][i] = Double.parseDouble(inputArray[i]);
				}
				
				yArray[row] = Double.parseDouble(inputArray[inputArray.length-1]); //Copy the y label into a separate yArray
				
				row++; //Move to next row in arrays
				
				System.out.println("Done.");
			}
		}
		
		catch (Exception e)
		{
			System.out.println("Something went wrong.");
		}
	}//end makeCVSArray
	
}
		
		
				
				
				
		
		