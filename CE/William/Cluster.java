import ai.models.*;



public class Cluster
{
  public static void main(String[] args)
  {
    int numberOfClusters = 7;
    String fileLocation = "dataset/iris.txt";
    //String fileLocation = "dataset/fullwineNoLabels.txt";
    //String fileLocation = "dataset/marks.txt";

    KMeansCluster kmean = new KMeansCluster(fileLocation, numberOfClusters);
    //kmean.standardScaler();
    int[] numRandC = kmean.returnRowandCol(fileLocation);
    int row = numRandC[0];
    int col = numRandC[1];
    double[][] temp1 = new double[numberOfClusters][col];
    double[][] temp2 = new double[numberOfClusters][col];
    double[][] storageBestCentroids = new double[numberOfClusters][col];
    double currentBestDistance = 0;
    int [] classCountBest = new int[numberOfClusters];
    int iterations = 0;



    System.out.println("Initial Centroids at");
    kmean.initialCentroids();
    kmean.printCentroids();

    System.out.println();
    do
    {
      temp1 = deepCopy2D(kmean.returnCentroids());
      kmean.cluster();
      kmean.getCentroids();
      kmean.printCentroids();
      temp2 = deepCopy2D(kmean.returnCentroids());
      System.out.println("clustering");
      iterations++;

    } while (kmean.checkConvergence(temp1, temp2) == false && iterations <10);


    System.out.println("cluster done.");
    kmean.printCentroids();
    System.out.println();
    kmean.printClassCounts();
    System.out.println(kmean.computeAverageDistanceToCentroids());

    storageBestCentroids = kmean.returnCentroids();
    currentBestDistance = kmean.computeAverageDistanceToCentroids();
    classCountBest = kmean.returnClassCounts();




    for(int i = 0; i < 1000; i++)
    {
      kmean = new KMeansCluster(fileLocation, numberOfClusters);
      //kmean.standardScaler();
      System.out.println("Initial Centroids at");
      kmean.initialCentroids();
      kmean.printCentroids();
      iterations = 0;

      System.out.println();
      do
      {
        temp1 = deepCopy2D(kmean.returnCentroids());
        kmean.cluster();
        kmean.getCentroids();
        kmean.printCentroids();
        temp2 = deepCopy2D(kmean.returnCentroids());
        System.out.println("clustering");
        iterations++;

      } while (kmean.checkConvergence(temp1, temp2) == false && iterations <10);


      System.out.println("cluster done.");
      kmean.printCentroids();
      System.out.println();
      kmean.printClassCounts();
      System.out.println(kmean.computeAverageDistanceToCentroids());

      if (kmean.computeAverageDistanceToCentroids() < currentBestDistance)
      {
        storageBestCentroids = kmean.returnCentroids();
        currentBestDistance = kmean.computeAverageDistanceToCentroids();
        classCountBest = kmean.returnClassCounts();
      }
    }


    System.out.println("\n\n********Best solution found is: ");
    printBestCentroids(storageBestCentroids);
    System.out.println("\nSummed average distance of points to their respective centroids: " + currentBestDistance);
    for (int i: classCountBest)
    {
      System.out.println("Class Count: " + i);
    }


    //compare with real centroids.we can use the KMeansCluster class but directly input the clustered classes labels.
    String fileLocation2 = "dataset/irisLabels.txt";
    //String fileLocation2 = "dataset/marksLabels.txt";
    //String fileLocation2 = "dataset/fullwineLabels.txt";

    System.out.println("\n********Real centroids at:");

    KMeansCluster realClasses = new KMeansCluster(fileLocation, numberOfClusters);
    realClasses.setClusteredClassestoReal(fileLocation2);
  //realClasses.standardScaler();
    realClasses.initialCentroids();
    realClasses.getCentroids();
    realClasses.printCentroids();



    //if you want to see what points the algorithm clustered.
    System.out.println("\n\n************Clustered classes :");
    KMeansCluster optimal = new KMeansCluster(fileLocation, 3);
    //optimal.standardScaler();
    optimal.initialCentroids();
    optimal.setCentroids(storageBestCentroids);
    optimal.cluster();
    optimal.printAllClustered();

  }


  public static void printBestCentroids(double[][] best)
  {
    for(int i = 0; i < best.length; i++)
    {
      System.out.print("\nCentroid " + i + ": [");
      for (int j = 0; j < best[i].length; j++)
      {
        System.out.print(best[i][j] + " ");
      }
      System.out.print("]");
    }
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
