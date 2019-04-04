import mdata.*;


public class PredictPrecipitation
{
	public static void main (String [] args)
		{
			double[][] xArray = new double[12505][7];
			double[] yArray = new double[12505];
			
			ManipulateData md = new ManipulateData();
			
			md.makeArrays(xArray, yArray);
			
		}
}