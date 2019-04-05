import mdata.*;
import math.*;


public class PredictPrecipitation
{
	public static void main (String [] args)
		{
			///////////////////////////////////////////////////////
			//Creating the arrays to hold the data points
			//x holds the feature variables
			//y holds the labels: 0 or 1
			//////////////////////////////////////////////////
			int rows = 12505;
			int columns = 7;
			double[][] xArray = new double[rows][columns];
			double[] yArray = new double[rows];
			
			//Creating object
			ManipulateData md = new ManipulateData();
			
			
			//now you can put the dataset into the arrays and move them around
			md.makeArrays(xArray, yArray);
			md.makeYLabels(yArray);
			md.shuffleData(xArray, yArray);
			
			//standardize some data columns if u want
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
			
			
		}
}