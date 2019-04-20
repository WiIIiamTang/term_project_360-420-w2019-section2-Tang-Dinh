import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Logistic
{

	/** Methods
	 **/

	/////////////////////////////////////////////////////////////////////////////////////
	//the makeArrays method reads in the dataset file, which should be in a CSV file format
	//it takes in two arrays, where the yArray should hold all the y-labels.
	//PUT THE Y LABELS IN THE LAST COLUMN.
	//ALL VALUES ONLY NUMBERS SEPARATED BY ONE COMMA
	/////////////////////////////////////////////////////////////////////////////////////

	public static void makeArrays(double[][]xArray, double[]yArray)
	{
		//Create the array to hold the data first
		String[] inputArray;

		//Create other variables
		Scanner scanIn = null;
		String inputRow = ""; //this will be the variable that holds the row!
		String fileLocation = "data/dataset.txt"; //<<INSERT FILE LOCATION HERE
		int row = 0;

		System.out.println("Reading the dataset.txt and setting up the arrays...");


	try
	{
		scanIn = new Scanner(new BufferedReader(new FileReader(fileLocation)));

		while (scanIn.hasNextLine())
		{
			inputRow = scanIn.nextLine(); //read row from dataset.txt


			inputArray = inputRow.split(","); //store that row into array

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
	}

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
	}

	//print any 1d array
	public static void printArray (double [] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			System.out.print(array[i] + " ");
		}
	}



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

	}



	////////////////////////////////////////////////////////////////////////
	/////some way of randomizing position of data points
	/////takes in a twoD array and a normal array and randomizes the points; ONE WHOLE ROW with its corresponding YLABEL must be swapped with another ROW and its YLABEL
	/////in the case of the twoD array it will move the rows around, and in the yArray it just shuffles the values around
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
	//Computes and returns the mean from a 2D array, it's the mean OF ONE COLUMN - usually it will be ONE FEATURE VARIABLE, LIKE TEMPERATURE
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
	//Computes and returns the standard deviation from a 2D array, its the sd OF ONE COLUMN
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


	////////////////////////////////////////////////////////////////
	//Find max and min of one feature variable in all the data (one column)
	//The next two methods takes in [][] 2d array and returns a double
	/////////////////////////////////////////////////////////////////////////

	public static double getMax(double[][]x, int col)
	{
		double max = x[0][col];

		for(int i = 0; i < x.length; i++)
		{
			if(x[i][col] > max)
			{
				max = x[i][col];
			}

		}

		return max;
	}

	public static double getMin(double[][]x, int col)
	{
		double min = x[0][col];

		for(int i = 0; i < x.length; i++)
		{
			if(x[i][col] < min)
			{
				min = x[i][col];
			}
		}

		return min;
	}

	////////////////////////////////////////////////////////
	//Min-Max normalization; we should test with this way
	//of normalizing too because i dont think temperature
	//is normally distributed...
	///////////////////////////////////////////////////

	public static void minMaxNormal(double[][]x, int col)
	{
		double max = getMax(x,col);
		double min = getMin(x, col);

		for(int i = 0; i < x.length; i++)
		{
			x[i][col] = (x[i][col] - min) / (max-min);
		}

	}


	////////////////////////////////////////////////
	///Splits data with a certain percent level
	///Will take in first the TARGET ARRAY, then the INITIAL ARRAY, then the INDEX OF WHERE YOU WANT TO STOP, then THE STARTING INDEX.
	///Will copy rows like this: always starts from INDEX startingIndex, THEN GOES UP UNTIL IT REACHES THAT INDEX OF THE INITIAL ARRAY: stopSplit.
	///make sure the target array is the one you want to copy values INTO, and the iniArray is the array you want to copy values FROM.
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

	}

	public static void dataSplitNormal(double [] targetArray, double []iniArray, int stopSplit, int startingIndex)
	{

		for (int i = 0; i < (stopSplit-startingIndex); i++)
		{
			targetArray[i] = iniArray[startingIndex+i];
		}

	}


	////////////////////////////////////////////////////////////////////////
	///Computes the sum of the initial linear equation y = theta0 + theta1 * x1 + ...
	////////////////////////////////////////////////////////////////////////////

    public static double linearSum(double[][] x, double[] beta, int row)
    {
        double sum = 1.;

        sum = beta[0];

        for (int i = 1; i < x[0].length; i++)
        {
            sum = sum + (beta[i] * x[row][i]);
        }

        return sum;
    }

    ///////////////////////////////////////////
    ///Computes the value of the hypothesis func
    ////////////////////////////////////////////

    public static double hypothesis(double[][] x, double[] beta, int row)
    {
        double sigmoid = 0.;

        sigmoid = 1/(1+Math.pow(Math.E,-linearSum(x, beta, row)));

        return sigmoid;
    }

    //////////////////////////////////////////
    ///Computes the value of the costFunction
    ////////////////////////////////////////////

    public static double costFunction(double[][] x, double[] y, double[] beta)
    {
        int m = x.length;
        double cost = 0.;
        double sum = 0.;

        for (int i = 0; i < m; i++)
        {
            sum = sum + (y[i] * Math.log(hypothesis(x, beta, i)) + (1-y[i])*Math.log(1 - hypothesis(x, beta, i)));
        }

        cost= -sum/m;

        return cost;
    }

    //////////////////////////////////////////////
    ///Computes the value of the partial derivative
    /////////////////////////////////////////////////

    public static double gradient(double[][] x, double[] y, double[] beta, int betaNum)
    {
        double sum = 0.;
        double m = x.length;


        for(int i =0; i < m; i++)
        {
            sum += (hypothesis(x, beta, i) - y[i]);

            if(betaNum != 0)
            {
            	sum *= x[i][betaNum-1];
            }

        }

        return sum/m;
    }

    ////////////////////////////////////////////
    ///Performs the gradient descent method
    ///and returns the theta array containing
    ///the optimal parameters
    /////////////////////////////////////////////

    public static void gradientDescent(double[][]x, double[] y, double[] beta)
    {
        double alpha = 0.00001;
        double[] betaNew = new double[beta.length];
        double difference[] = new double[beta.length];
        double tolerance = 0.000001;
        int iterations = 0;
        boolean checkDifference = true;

        while (checkDifference)
        {

            for(int i = 0; i < beta.length; i++)
            {
               betaNew[i] = beta[i] - alpha * gradient(x, y, beta, i);
               difference[i] = Math.abs(betaNew[i] - beta[i]);
               beta[i] = betaNew[i];
            }

           for(int i = 0; i < difference.length; i++)
           {
                if (difference[i]>tolerance)
                {
                    break;
                }
            checkDifference = false;
           }

					 //Print cost function every few iterations
           if(iterations % 50 == 0)
           {
        	   System.out.println("Cost at " + costFunction(x, y, beta));
           }
        }

    }





	/**Main
	 *
	 * Note: Before starting the program a CSV file with the name dataset.txt should be in the right place
	 * For now the number of rows and columns needs to be input manually; CHANGE rows AND col
	 * If the dataset changes. Excel can show the number of rows.
	 * One "row" corresponds to one data point (like one day), the "columns" hold the feature variable data(temp, wind speed, snow,etc.)
	**/

	public static void main(String [] args)
	{
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////Setting up the data//////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////////////

		//Creating the arrays to hold the data points
		//x holds the feature variables
		//y holds the labels
		int rows = 12505; // <<check if good
		int columns = 7; // <<check if good
		double[][] xArray = new double[rows][columns];
		double[] yArray = new double[rows];


		//put the dataset into the arrays and move them around
		makeArrays(xArray, yArray);
		makeYLabels(yArray);
		shuffleData(xArray, yArray);


		//Creating the test set and training set
		double trainingSplitPercent = 0.80; //modify how much is training/test
		int splitIndex = (int) (xArray.length * trainingSplitPercent);
		double [][]xTrainArray = new double [splitIndex][columns];
		double []yTrainArray = new double [splitIndex];
		double [][]xTestArray = new double [xArray.length - splitIndex][columns];
		double []yTestArray = new double [yArray.length - splitIndex];

		System.out.println("Splitting up data...");
		dataSplit2D(xTrainArray, xArray, splitIndex, 0);
		dataSplit2D(xTestArray, xArray, (xArray.length), splitIndex);
		dataSplitNormal(yTrainArray, yArray, splitIndex, 0);
		dataSplitNormal(yTrainArray, yArray, (yArray.length), splitIndex);
		System.out.println ("Done.");
		System.out.println("xTrainArray has " + xTrainArray.length + " rows; xTestArray has " + xTestArray.length + " rows");
		System.out.println("yTrainArray has " + yTrainArray.length + " rows; yTestArray has " + yTestArray.length + " rows");
		//Done creating the test set and training set.


		//standardize some data columns if u want (try with/without this method; min-max or zscore)
		convertToZScore(xTrainArray, 0);
		convertToZScore(xTrainArray, 1);
		convertToZScore(xTrainArray, 2);
		//printAllArrays(xTrainArray,yTrainArray); //test print

		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////Logistic Regression//////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////////////


		//Create beta array, holds the coefficients of the linear equation y = theta0 + theta1*x1 + ...
		double [] beta = new double[xTrainArray[0].length + 1];

		//Do gradient Descent
		System.out.println("Initial cost at " + costFunction(xTrainArray, yTrainArray, beta));
		gradientDescent(xTrainArray, yTrainArray, beta);

		System.out.println("Optimal coefficients found at ");
		for (double i: beta)
		{
			System.out.println(i);
		}

		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		///////////Model Evaluation//////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////////////

	}//end main


}//end class
