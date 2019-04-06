

/**This is the class that contains the main method for the program
**The PredictPrecipitation will take a dataset and perform a logistic regression
**The LAST COLUMN OF THE DATASET always represents the Y LABELS, while the rest are the feature variables.
**Use the ManipulateData class to get the dataset ready
**Use the LogRegress class to do the logistic regression and evaluate accuracy at the end
**/

public class PredictPrecipitation
{
	public static void main (String [] args)
		{
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			///////////Setting up the data//////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			//Creating the arrays to hold the data points
			//x holds the feature variables
			//y holds the labels: 0 or 1
			int rows = 12505;
			int columns = 7;
			double[][] xArray = new double[rows][columns];
			double[] yArray = new double[rows];
			
			//Creating objects
			ManipulateData md = new ManipulateData();
			LogRegress lr = new LogRegress();
			
			
			//now you can put the dataset into the arrays and move them around
			md.makeArrays(xArray, yArray);
			md.makeYLabels(yArray);
			md.shuffleData(xArray, yArray);
			
			//standardize some data columns if u want (try with/without this method)
			md.convertToZScore(xArray, 1); //maybe TMAX, TMIN, wind speed
			md.convertToZScore(xArray, 2);
			
			//Creating the test set and training set
			double trainingSplitPercent = 0.80; //modify how much is training/test
			int splitIndex = (int) (xArray.length * trainingSplitPercent);
			double [][]xTrainArray = new double [splitIndex][columns];
			double []yTrainArray = new double [splitIndex];
			double [][]xTestArray = new double [xArray.length - splitIndex][columns];
			double []yTestArray = new double [yArray.length - splitIndex];
			
			System.out.println("Splitting up data...");
			md.dataSplit2D(xTrainArray, xArray, splitIndex, 0);
			md.dataSplit2D(xTestArray, xArray, (xArray.length), splitIndex);
			md.dataSplitNormal(yTrainArray, yArray, splitIndex, 0);
			md.dataSplitNormal(yTrainArray, yArray, (yArray.length), splitIndex);
			System.out.println ("Done.");
			System.out.println("xTrainArray has " + xTrainArray.length + " rows; xTestArray has " + xTestArray.length + " rows");
			System.out.println("yTrainArray has " + yTrainArray.length + " rows; yTrainArray has " + yTestArray.length + " rows");
			//Done creating the test set and training set.
		
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			///////////Logistic Regression//////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			
		}
}
