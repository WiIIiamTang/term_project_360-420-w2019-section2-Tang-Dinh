/*
KNNClassifier.java
Author: Sameer Bhatnagar
*/

import java.util.List;
import java.util.Arrays;
import java.util.Set;

public class KNNClassifier{
  /*
  Class that implements k-Nearest Neighbor Classifier, where k is the number of
  closest neighbors that are used to determine the class of a datapoint

  - Dependencies:
    - DataPoint.java
    - DataSet.java for Euclidean distance

  methods:
    - KNNClassifier (constructor)
    - getNearestNeighbors
    - predict
  */
  private int k;


  /////////////////////////////////////////////////////////////////////////
  // Constructor
  /////////////////////////////////////////////////////////////////////////
  public KNNClassifier(int k){
    this.k = k;
  }
  /////////////////////////////////////////////////////////////////////////


  /////////////////////////////////////////////////////////////////////////
  public DataPoint[] getNearestNeighbors(List<DataPoint> allPoints, DataPoint dp){
  /*
  Arguments:
    - List of DataPoint objects
    - Target DataPoint

  Returns:
    - array holding DataPoint objects, in ascending order of distance to Target DataPoint
  */
    DataPoint[] nearestNeighbors = new DataPoint[this.k];
    Double[] nearestDistances = new Double[this.k];

    // initialize to some very large distances
    for (int i = 0;i<this.k; i++){
      nearestDistances[i] = 100000.;
    }

    Arrays.sort(nearestDistances);

    // Scan through all dataPoints to find the k closest neighbors
    for (DataPoint p:allPoints){

      double d = DataSet.distanceEuclid(p,dp);

      //iterate through the sorted  list of nearest neighbors/distances
      checkNearestNeighbors:
      for (int j=0; j< nearestDistances.length; j++){
          //if distance is less than current point p, move list items up in queue, and swap in current p
          if (d < nearestDistances[j]){
            //if we are not at farthest near neighbour yet seen
            if (j < nearestDistances.length - 1){
              nearestDistances[j+1]=nearestDistances[j];
              nearestNeighbors[j+1]=nearestNeighbors[j];
            } // if we are at end of array, simply replace the last element with current one

            nearestDistances[j]=d;
            nearestNeighbors[j]=p;
            break checkNearestNeighbors; // stop checking other elements if inserted this p already
          }
        }
      }

    return nearestNeighbors;
  }
  /////////////////////////////////////////////////////////////////////////



  /////////////////////////////////////////////////////////////////////////
  public int getLabelFrequency(String targetLabel, DataPoint[] allPoints){

    int count = 0;

    for (DataPoint p : allPoints ){
      if (p.getLabel().equals(targetLabel)){
        count++;
      }
    }

    return count;
  }
  /////////////////////////////////////////////////////////////////////////



// https://stackoverflow.com/questions/22911722/how-to-find-array-index-of-largest-value
public int getIndexOfLargest( int[] array )
{
  if ( array == null || array.length == 0 ) return -1; // null or empty

  int largest = 0;
  for ( int i = 1; i < array.length; i++ )
  {
      if ( array[i] > array[largest] ) largest = i;
  }
  return largest; // position of the first largest found
}




  /////////////////////////////////////////////////////////////////////////
  public String predict(List<DataPoint> allPoints, DataPoint dp){
  /*
  Arguments:
    - DataPoint on which to predict target label

  Returns:
    - String Target label
  */

  DataPoint[] nearestNeighbors = getNearestNeighbors(allPoints, dp);

  Set<String> labelsSet =  DataSet.getLabels(allPoints);
  String[] labelsArray = labelsSet.toArray(new String[labelsSet.size()]);
  int[] labelsCounts = new int[labelsArray.length];


  for (int i=0; i<labelsArray.length;i++){
      labelsCounts[i] = getLabelFrequency(labelsArray[i],nearestNeighbors);
  }

  int idx = getIndexOfLargest(labelsCounts);
  String predictedLabel = labelsArray[idx];

  return predictedLabel;
  }
  /////////////////////////////////////////////////////////////////////////

}
