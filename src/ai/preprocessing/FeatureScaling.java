package ai.preprocessing;

/**
** this class only works on 2d arrays
** note: these methods will perform operations on arrays that you pass in and not that the class holds
*/

public class FeatureScaling
{

	//Computes and returns the mean from a 2D array, it's the mean OF ONE COLUMN - usually it will be ONE FEATURE VARIABLE, LIKE TEMPERATURE
	//Useful for putting the data on a normalized distribution
	public double computeMean(double[][]xArray, int col)
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
	public double computeSD(double[][]xArray, int col)
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
	public void standardScaler(double[][]xTrainArray, double[][]xTestArray)
	{
		double zScore1 = 0;
		double zScore2 = 0;
		double mean = 0;
		double sd = 0;

    for (int col = 0; col < xTrainArray[0].length; col++)
    {
      mean = computeMean(xTrainArray, col);
      sd = computeSD(xTrainArray, col);

  		for (int i = 0; i < xTrainArray.length; i++)
  		{
  			zScore1 = ((xTrainArray[i][col] - mean ) / sd);
  			xTrainArray[i][col] = zScore1;
  		}

      for (int i = 0; i < xTestArray.length; i++)
      {
        zScore2 = ((xTestArray[i][col] - mean) / sd);
        xTestArray[i][col] = zScore2;
      }
    }



		//System.out.println ("Done converting columns.");
	}//endzscore



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


  //Min-Max normalization; we could test with this way
  //of normalizing too. puts everything on a scale [0,1]
  public static void minMaxScaler(double[][]xTrainArray, double[][]xTestArray)
  {
    double max = 0;
    double min = 0;

    for(int col = 0; col < xTrainArray[0].length; col++)
    {
      max = getMax(xTrainArray,col);
      min = getMin(xTrainArray,col);

      for(int i = 0; i < xTrainArray.length; i++)
      {
        xTrainArray[i][col] = (xTrainArray[i][col] - min) / (max-min);
      }
    }

    for(int col = 0; col < xTestArray[0].length; col++)
    {
      max = getMax(xTrainArray,col);
      min = getMin(xTrainArray,col);

      for(int i = 0; i < xTestArray.length; i++)
      {
        xTestArray[i][col] = (xTestArray[i][col] - min) / (max-min);
      }
    }

  }

}
