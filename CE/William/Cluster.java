import ai.models.*;



public class Cluster
{
  public static void main(String[] args)
  {
    int numberOfClusters = 3;
    String fileLocation = "dataset/fullwineNoLabels.txt";

    KMeansCluster kmean = new KMeansCluster(fileLocation, numberOfClusters);
    int[] numRandC = kmean.returnRowandCol(fileLocation);
    int row = numRandC[0];
    int col = numRandC[1];
    double[][] temp1 = new double[numberOfClusters][col];
    double[][] temp2 = new double[numberOfClusters][col];


    kmean.initialCentroids();
    temp1 = kmean.returnCentroids();
    kmean.printCentroids();


    do
    {
      temp1 = deepCopy2D(kmean.returnCentroids());

      kmean.cluster();
      kmean.getCentroids();
      //kmean.printCentroids();
      temp2 = deepCopy2D(kmean.returnCentroids());
      System.out.println("clustering");

    } while (kmean.checkConvergence(temp1, temp2) == false);


    System.out.println("cluster done.");
    kmean.printCentroids();
    System.out.println();
    kmean.printClassCounts();

  }


  public static double[][] deepCopy2D(double[][] x)
  {
    double[][] y = new double [x.length][x[0].length];

    for (int i = 0; i < x.length; i++)
    {
      for (int j = 0; j< x[i].length; j++)
      {
        y[i][j] = x[i][j];
        //System.out.println(x.length);
      }
    }

    return y;
  }
}
