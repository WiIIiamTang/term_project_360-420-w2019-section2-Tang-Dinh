# Intro to Programming - Independent Study

## Supervisor: Jean-François Brière


### 1. Multinominal Logisitic Regression: the One vs. All Strategy

  In the term project, we saw that a logistic regression was capable of predicting between two different classes at a time. It uses the gradient descent method in order to find the optimal weights that allows it to make a prediction based on the characteristic of a class.
  
  Now I would like to see how to classify more than 2 classes at a time, but still using logistic regression. One way of achieving this is through the one vs. all strategy, which I talk about in this first section.
  
  #### One vs. All, or One vs. the Rest
  
  Let's go back to the original wine dataset, which had 3 types of wine. I would like to use logistic regression, but to classify all three wines now.
  
  The **one vs. all strategy consists of building *n* independent binary logistic regression models**, and running all at the same time to get the best prediction. The number of models *n* that I need to make is equal to the number of classes to sort through. For the wine dataset, then, I will have to make three.
  
  The idea behind one vs. all is that I can make a binary classifier for logistic regression that will classify, say, wine 1 vs. everything else (wine 2 + wine 3). Then, I can also make a binary classifier for wine 2 vs. everything else, and so on and so forth. If I take a single datapoint with all of its characteristics, and run it through each of my classifiers, **each classifier will return the probability that that datapoint is of a certain class**. I will then need to compare these probabilities; **the final class the datapoint is assigned will be the class whose appropriate classifier obtained the highest accuracy.** Simple.
  
  Here's a really nice graphical representation of this method by Andrew Ng, from his [Machine Learning Course](https://www.youtube.com/watch?v=ZvaELFv5IpM).
  
  <img src="https://i.stack.imgur.com/zKpJy.jpg" />
  
  Unfortunately, this strategy would not be very viable for classification problems that have lots of classes, and so is limited to only a handful of multi-class classifiers. In any case, for our wine dataset, there is not much of problem.
  
  
  #### Code - Multinominal.java
  
  Now we know what to do to get the multi-class logistic regression working. I'll start by creating 4 dataloader objects. One of them, called original, loads the data from the csv. All data is shuffled around.
  
  ```java
  //make datasets
Dataloader original = new Dataloader();
Dataloader data1 = new Dataloader();
Dataloader data2 = new Dataloader();
Dataloader data3 = new Dataloader();

//make arrays in the original and shuffle around.
original.makeArrays("dataset/fullwine.txt");
original.shuffleData();
```
For the other three objects, I'll first get the X Array (feature variables) and the Y Array (class labels) from my original dataloader. Then I'll use the set methods to copy these arrays into them. All three dataloader objects should have the same initial data.

```java
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
```
Notice that I create separate arrays for each data object (x1, y1, x2, y2, ...). I copied over the values of the initial X arrays and Y arrays instead of just pointing these arrays to the same reference in memory. This is important if I want the makeYLabels method to work.

```java
data1.makeYLabels(1,1,0);
data2.makeYLabels(2,1,0);
data3.makeYLabels(3,1,0);
 ```
 This is where the datasets change for each object. You'll see that each data object will only classify for only one type of wine. That is why I apply a method that will look for a given label and assign it a positive value (1) while everything else is turned into the negative value (0). From here, I can proceed with the regular stuff you would do in a logistic regression.
 
 ```java
original.trainTestSplit(0.70);
data1.trainTestSplit(0.70);
data2.trainTestSplit(0.70);
data3.trainTestSplit(0.70);

double[][] x_train1, x_test1, x_train2, x_test2, x_train3, x_test3;

x_train1 = data1.returnXTrainArray();
x_test1 = data1.returnXTestArray();
x_train2 = data2.returnXTrainArray();
x_test2 = data2.returnXTestArray();
x_train3 = data3.returnXTrainArray();
x_test3 = data3.returnXTestArray();


FeatureScaling.standardScaler(x_train1, x_test1);
FeatureScaling.standardScaler(x_train2, x_test2);
FeatureScaling.standardScaler(x_train3, x_test3);

LogisticRegression classifier1 = new LogisticRegression(x_train1, x_test1, data1.returnYTrainArray(), data1.returnYTestArray());
LogisticRegression classifier2 = new LogisticRegression(x_train2, x_test2, data2.returnYTrainArray(), data2.returnYTestArray());
LogisticRegression classifier3 = new LogisticRegression(x_train3, x_test2, data3.returnYTrainArray(), data3.returnYTestArray());

classifier1.fit(0.1, 5000, false);
classifier2.fit(0.1, 5000, false);
classifier3.fit(0.1, 5000, false);
```
Self-explanatory, it's just a logistic regression, but repeated three times. At the end, I've done gradient descent three times. **Each classifier object contains its own trained, optimal weights.**

Now I have to grab the predictions, in probability, for each classifier, for a given point. I need to repeat this for all points in the training/test set. Let's start with the training set. First get the predictions in probability:
```java
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
```

Then take the *highest probability out the three, for a given data point.* Repeat for whole dataset:
```java
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
```

Ok, so everything is done. A similar procedure is followed to get the predictions of the test set, so I won't go over that. Now let's verify whether the program actually worked! We'll calculate the accuracy by comparing the actual class values to the predicted class values. For this, I'll use the ModelEvaluator class and its getAccuracy method.
```java
ModelEvaluator me = new ModelEvaluator();

double acc1 = me.getAccuracy(original.returnYTrainArray(), bestClassTrain);
System.out.println();
double acc2 = me.getAccuracy(original.returnYTestArray(), bestClassTest);

System.out.println("\nThe accuracy for the training set is " + acc1 + " percent");
System.out.println("\nThe accuracy for the test set is " + acc2 + " percent");
```
And that's a simple multi-class logistic regression. In the next section, a give a short report on the results.

#### Results

On individual trials, the training set consistently gets 100% accuracy, which is pretty good. However, the most important thing to look at is the accuracy of the test set, which hovered in the mid-90s. To get a better sense of the accuracy of the multi-class model, I made a loop that ran the whole program 100 times. The test set accuracy is stored in an array everytime. After the 100 runs are over, I can compute the average accuracy. The following results were obtained with a logistic regression, **step size 0.1 and 5000 iterations, all weights starting at zero, all features normalized using the z-score standardization.** There is no prediction threshold here unlike the binary classifier.

| Test Set, 100 runs| Value(%)        |
|------------------ | --------------- |
| Accuracy          | 98.27777777777  |
| Standard Deviation| 2.006515775034  |

Accuracy is around 98%, which means the classifier is good at sorting through the wines.


### Intro to Unsupervised Learning: K-Means Clustering

Everything that we've done up till now in machine learning was "supervised"; I knew what the answer should be for a given datapoint. In the wine dataset, I had the class values with me. I could, in a sense, *supervise* my program because I know if it's doing poorly or not by measuring its accuracy against the known values!

Let's now consider the hypothetical situation where a wine-maker (brewer?) lost all the class labels for the wine. He was transporting a shipment to a major retailer, and the manager wants all of them labelled by tonight, in order to get them on the shelves of his store by tomorrow morning. However, the wine-guy doesn't have the wine types! It would take far too long to taste/inspect all of them individually.

Fortunately, the wines are still numbered. And for each number, is listed, in his log book, a bunch of thier numerical characteristics. This is where unsupervised learning can come in handy. Why not teach a program to recognize patterns in the data, and *let it make the classes itself?* That way, even if you don't know the type of wine, we can at least sort them into distinct categories, which makes it way more easy to classify.

This is what the next section will be about. I'll introduce one the most simplest forms of unsupervised machine learning, k-means clustering.

K-Means clustering is an algorithm that **clusters** data into K classes based on feature similarity. That is, every data point in a cluster is assigned the same class. The result of this algorithm are of course the labels for the datapoints, as well as the **centroids** of the clusters, which can be used to classify similar data! I won't be touching on this last point here, but in the conclusion I'll give a text-based explanation of how we can implement k-means clustering in a k-Nearest Neighours model, for example.

K-Means clustering isn't just useful for when your dad loses his wine collection. The field of unsupervised machine learning has applications in defining personas based on interests (think youtube reccomendations!), bot detection in social media, and inventory categorization in business to detect useful trends/patterns.


#### Code - Cluster.java and KMeansCluster.java

KMeansCluster.java is a new class that I added to the ai/models folder. It contains useful methods for the K-Means Clustering, and I'll need to use it to create my model, in Cluster.java.

I'll avoid the wine dataset for now and work with something simpler, the iris dataset from the UCI machine learning repository. It has 3 types of flowers to classify. For this clustering program, I took the dataset and stripped out the class labels from the csv file. The goal is to cluster the points by feature similiarity for k = 3 classes.

We'll start by creating the object of class KMeansCluster. From there, I'll have to create a few more variables, which I'll explain a bit later.
```java
  int numberOfClusters = 3;
  String fileLocation = "dataset/iris.txt";
KMeansCluster kmean = new KMeansCluster(fileLocation, numberOfClusters);

int[] numRandC = kmean.returnRowandCol(fileLocation);
int row = numRandC[0];
int col = numRandC[1];
double[][] temp1 = new double[numberOfClusters][col];
double[][] temp2 = new double[numberOfClusters][col];
double[][] storageBestCentroids = new double[numberOfClusters][col];
double currentBestDistance = 0;
int [] classCountBest = new int[numberOfClusters];
int iterations = 0;
```
The first step in k-means clustering is to start with *three* random points in the dataset. **These will be the initial "centroids"**.
```java
kmean.initialCentroids();
```
Now, we'll have to 1) cluster points by distance to the centroids; the points are assigned to the **nearest centroid** by euclidean distance, and 2) get the **new centroids by computing the mean of all the points in a cluster.** Then we *repeat* until convergence. But what is convergence?

Another thing to do is to track the position of the new centroids relative to the previous centroids. This is what the temp1 and temp2 arrays that I made earlier are for. They store the positions of the centroids before and after the clustering operation. I'll need this to decide the stopping condition for the loop that I described in the previous paragraph. If the centroids do not change position, then I can stop the loop. Just to not make the simulation too long, I put a max limit of 10 iterations in the clustering loop. This is what we get:
```java
do
{
  temp1 = deepCopy2D(kmean.returnCentroids());
  kmean.cluster();
  kmean.getCentroids();
  //kmean.printCentroids();
  temp2 = deepCopy2D(kmean.returnCentroids());
  iterations++;
  
} while (kmean.checkConvergence(temp1, temp2) == false && iterations <10);
```
And that's k-Means clustering! Done.

...Except not really. If we think about it, we are starting at some random points in the dataset. Depending on what points start out as the *intital centroids*, the clusters we end up at convergence could be different. So knowing this, one solution is simply redo the k-means clustering many many times, and take the best clusters!

So now I'll have to implement a system that runs the k-means clustering lots of times, while keeping track of the best solution.



  
