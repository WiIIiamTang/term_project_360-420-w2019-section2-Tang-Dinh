package math;

import LogRegress;

public class Evaluator
{
	public static double[] computePredictions(double[][]xArray)
	{
		LogRegress lr = new LogRegress;
		
		
	}
	
	
	public static double getAccuracy(double[]yLabels, double[]predictedYLabels);
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