

public class LogRegress
{
	public static double computeHypothesis()
	{
		double hypothesisValue = 0;
		
		return hypothesisValue;
	}
	
	public static double computeCostFunc()
	{
		double costValue = 0;
		
		
		return costValue;
	}
	
	public static double computePartial()
	{
		double partialDerive = 0;
		
		return partialDerive;
	}
	
	
	
	
	public static double[] gradientDescent(double[][]xArray)
	{
		double [] parameters = new double[xArray[0].length];
		
		return parameters;
	}
	
	
	
	public static double[] computePredictions(double[][]xArray, double[] yArray)
	{
		double[] predictions = new double[yArray.length];
		
		
		return predictions;
	}
	
	
	public static double getAccuracy(double[]yLabels, double[]predictedYLabels)
	{
		double good = 0;
		double bad = 0;
		double accuracy = 0;
		
		for (int i = 0; i < yLabels.length; i++)
		{
			if (yLabels[i] == predictedYLabels[i])
			{
				good++;
			}
			else
			{
				bad++;
			}
		}
		
		
		
		
		accuracy = (good/(good+bad));
		return accuracy;
	}
	
}