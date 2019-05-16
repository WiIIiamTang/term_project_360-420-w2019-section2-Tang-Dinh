import ai.preprocessing.*;
import ai.models.*;
import ai.metrics.*;

//Three types of wine.

public class Multinominal
{
  public static void main(String [] args)
  {
    //make datasets
    Dataloader original = new Dataloader();
    Dataloader data1 = new Dataloader();
    Dataloader data2 = new Dataloader();
    Dataloader data3 = new Dataloader();

    //make arrays in the original and shuffle around.
    original.makeArrays("dataset/fullwine.txt");
    original.shuffleData();


    //All three data objects should have the same allX and allY arrays.
    //put them in with the set method.
    //it's important to not just to a shallow copy of the allX and allY arrays here
    //if you do the manipulation of the y array in the makeYLabels won't work.
    int [] rowCol = new int[2];
    rowCol = original.returnRowandCol("dataset/fullwine.txt");
    int row = rowCol[0];
    int col = rowCol[1]-1;


    double[][] x1 = new double[row][col];
    double[] y1 = new double[row];
    double[][] x2 = new double[row][col];
    double[] y2 = new double[row];
    double[][] x3 = new double[row][col];
    double[] y3 = new double[row];
    double [][] toCopyX = original.returnFullXArray();
    double [] toCopyY = original.returnFullYArray();

    for(int i = 0; i < x1.length; i++)
    {
      for(int j = 0; j < x1[0].length; j++)
      {
        x1[i][j] = toCopyX[i][j];
        x2[i][j] = toCopyX[i][j];
        x3[i][j] = toCopyX[i][j];
      }

      y1[i] = toCopyY[i];
      y2[i] = toCopyY[i];
      y3[i] = toCopyY[i];

    }

    data1.setAllX(x1);
    data1.setAllY(y1);

    data2.setAllX(x2);
    data2.setAllY(y2);

    data3.setAllX(x3);
    data3.setAllY(y3);

    //Each data set needs to have different y labels. One will classify wine 1, other one will do wine 2, then last wine 3.
    data1.makeYLabels(1,1,0);
    data2.makeYLabels(2,1,0);
    data3.makeYLabels(3,1,0);

    //Make the training, test sets.
    original.trainTestSplit(0.70);
    data1.trainTestSplit(0.70);
    data2.trainTestSplit(0.70);
    data3.trainTestSplit(0.70);

    //Scale data.
    double[][] x_train1, x_test1, x_train2, x_test2, x_train3, x_test3;

    x_train1 = data1.returnXTrainArray();
    x_test1 = data1.returnXTestArray();
    x_train2 = data2.returnXTrainArray();
    x_test2 = data2.returnXTestArray();
    x_train3 = data3.returnXTrainArray();
    x_test3 = data3.returnXTestArray();

    FeatureScaling scale = new FeatureScaling();

    scale.standardScaler(x_train1, x_test1);
    scale.standardScaler(x_train2, x_test2);
    scale.standardScaler(x_train3, x_test3);

    //Make 3 different classifiers.
    LogisticRegression classifier1 = new LogisticRegression(x_train1, x_test1, data1.returnYTrainArray(), data1.returnYTestArray());
    LogisticRegression classifier2 = new LogisticRegression(x_train2, x_test2, data2.returnYTrainArray(), data2.returnYTestArray());
    LogisticRegression classifier3 = new LogisticRegression(x_train3, x_test2, data3.returnYTrainArray(), data3.returnYTestArray());

    //gradient descent.
    classifier1.fit(0.1, 5000, false);
    classifier2.fit(0.1, 5000, false);
    classifier3.fit(0.1, 5000, false);

    //You have all three classifiers. Now we need to get the predictions of each one in probability.
    //predictions for the training set:
    double[][] allPredictions = new double[x_train1.length][3];
    double[] temp = classifier1.getPredictionsProbabilityTrain();

    for (int i = 0; i < allPredictions.length; i++)
    {
      allPredictions[i][0] = temp[i];
    }

    temp = classifier2.getPredictionsProbabilityTrain();

    for(int i = 0; i < allPredictions.length; i++)
    {
      allPredictions[i][1] = temp[i];
    }

    temp = classifier3.getPredictionsProbabilityTrain();

    for(int i = 0; i < allPredictions.length; i++)
    {
      allPredictions[i][2] = temp[i];
    }

    //the predictions are now contained in the 2D array allPredictions.
    //Column 1 is wine 1, col 2 wine 2, col 3 wine 3. Take the best prediction out of each ROW:
    double[] bestClassTrain = new double[allPredictions.length];
    double[] bestClassLabelsTrain = {1,2,3};
    double max = 0;

    for(int i = 0; i < allPredictions.length; i++)
    {
      max = allPredictions[i][0];
      bestClassTrain[i] = bestClassLabelsTrain[0];

      for (int j = 0; j < allPredictions[i].length; j++)
      {
        if(allPredictions[i][j] > max)
        {
          max = allPredictions[i][j];
          bestClassTrain[i] = bestClassLabelsTrain[j];
        }
      }
    }

    //We need to do the same procedure and get the predictions of the TEST set now.
    //Start by, again, getting the predictions from each of the three classifier instances:

    allPredictions = new double[x_test1.length][3]; //reusing the allPredictions array from before

    temp = classifier1.getPredictionsProbabilityTest(); //reusing the temp array from before

    for (int i = 0; i < allPredictions.length; i++)
    {
      allPredictions[i][0] = temp[i];
    }

    temp = classifier2.getPredictionsProbabilityTest();

    for(int i = 0; i < allPredictions.length; i++)
    {
      allPredictions[i][1] = temp[i];
    }

    temp = classifier3.getPredictionsProbabilityTest();

    for(int i = 0; i < allPredictions.length; i++)
    {
      allPredictions[i][2] = temp[i];
    }

    //Now the allPredictions array holds the predictions for the test set.
    //get the class votes and make the prediction:
    double[] bestClassTest = new double[allPredictions.length];
    double[] bestClassLabelsTest = {1,2,3};
    max = 0; //reusing max from before

    for(int i = 0; i < allPredictions.length; i++)
    {
      max = allPredictions[i][0];
      bestClassTest[i] = bestClassLabelsTest[0];

      for (int j = 0; j < allPredictions[i].length; j++)
      {
        if(allPredictions[i][j] > max)
        {
          max = allPredictions[i][j];
          bestClassTest[i] = bestClassLabelsTest[j];
        }
      }
      //bestClassTest hold the predictions for the test set.
    }

    //Now we have arrays holding the predictions of the model (1, 2 or 3).
    //Let's get them and compare with the actual labels.
    ModelEvaluator me = new ModelEvaluator();

    double acc1 = me.getAccuracy(original.returnYTrainArray(), bestClassTrain);
    System.out.println();
    double acc2 = me.getAccuracy(original.returnYTestArray(), bestClassTest);

    //display the results.
    System.out.println("\nThe accuracy for the training set is " + acc1 + " percent");
    System.out.println("\nThe accuracy for the test set is " + acc2 + " percent");
  }
}
