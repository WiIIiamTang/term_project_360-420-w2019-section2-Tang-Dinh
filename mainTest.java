


public class mainTest
{
	public static void main (String [] args)
		{
			double[][] xArray = new double[4][9];
			double[] yArray = new double[4];
			
			ImportToArray object = new ImportToArray();
			
			object.makeCVSArray(xArray, yArray);
			
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
		}//end main
}