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


### 2. Intro to Unsupervised Learning: K-Means Clustering

Everything that we've done up till now in machine learning was "supervised"; I knew what the answer should be for a given datapoint. In the wine dataset, I had the class values with me. I could, in a sense, *supervise* my program because I know if it's doing poorly or not by measuring its accuracy against the known values!

Let's now consider the hypothetical situation where a wine-maker (brewer?) lost all the class labels for the wine. He was transporting a shipment to a major retailer, and the manager wants all of them labelled by tonight, in order to get them on the shelves of his store by tomorrow morning. However, the wine-guy doesn't have the wine types! It would take far too long to taste/inspect all of them individually.

Fortunately, the wines are still numbered. And for each number, is listed, in his log book, a bunch of thier numerical characteristics. This is where unsupervised learning can come in handy. Why not teach a program to recognize patterns in the data, and *let it make the classes itself?* That way, even if you don't know the type of wine, we can at least sort them into distinct categories, which makes it way more easy to classify.

This is what the next section will be about. I'll introduce one the most simplest forms of unsupervised machine learning, k-means clustering.

K-Means clustering is an algorithm that **clusters** data into K classes based on feature similarity. That is, every data point in a cluster is assigned the same class. The result of this algorithm are of course the labels for the datapoints, as well as the **centroids** of the clusters, which can be used to classify similar data!

K-Means clustering isn't just useful for when your dad loses his wine collection. The field of unsupervised machine learning has applications in defining personas based on interests (think youtube recommendations), bot detection in social media, and inventory categorization in business to detect useful trends/patterns.


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
  temp2 = deepCopy2D(kmean.returnCentroids());
  iterations++;
  
} while (kmean.checkConvergence(temp1, temp2) == false && iterations <10);
```
And that's k-Means clustering! Done.

...Except not really. If we think about it, we are starting at some random points in the dataset. Depending on what points start out as the *intital centroids*, the clusters we end up at convergence could be different. So knowing this, one solution is simply redo the k-means clustering many many times, and take the best clusters!

So now I'll have to implement a system that runs the k-means clustering lots of times, while keeping track of the best solution.
First, I'll set the initial "best" solutions form my first run:
```java
storageBestCentroids = kmean.returnCentroids();
currentBestDistance = kmean.computeAverageDistanceToCentroids();
classCountBest = kmean.returnClassCounts();
```
Now I'll implement a loop that repeats the whole program again. At the end, I'll have to check if the solution I found is better the previous one. One way to do this is to just compare the summed average distance of the datapoints to their respective centroids. We'll say that a better solution is found when this average distance is lower; this just means the clusters are more tightly packed, which should be good. Putting all of this together:
```java
for(int i = 0; i < 100; i++)
  {
    kmean = new KMeansCluster(fileLocation, numberOfClusters);
    kmean.initialCentroids();
    iterations = 0;

    do
    {
      temp1 = deepCopy2D(kmean.returnCentroids());
      kmean.cluster();
      kmean.getCentroids();
      temp2 = deepCopy2D(kmean.returnCentroids());
      iterations++;

    } while (kmean.checkConvergence(temp1, temp2) == false && iterations <10);

    if (kmean.computeAverageDistanceToCentroids() < currentBestDistance)
    {
      storageBestCentroids = kmean.returnCentroids();
      currentBestDistance = kmean.computeAverageDistanceToCentroids();
      classCountBest = kmean.returnClassCounts();
    }
  }
```
At the end of this loop, I will have hopefully obtained a solution that makes sense. Let's print the results:
```java
System.out.println("\n\n********Best solution found is: ");
printBestCentroids(storageBestCentroids);
System.out.println("\nSummed average distance of points to their respective centroids: " + currentBestDistance);
for (int i: classCountBest)
{
  System.out.println("Class Count: " + i);
}
```
And now we're finally done. One thing to remember here is that this is unsupervised learning, so you wouldn't know the *real* solution in a acutal application. The whole point of k-means clustering is to detect patterns and trends in data that would've gone unnoticed otherwise. If the implementation of the model is good, then this information that you get from the clustering can prove to be very useful, like I said before.

But since we're working on the iris dataset, we *do* have the real class labels. So we *can* verify whether or not the centroids are close to the real centroids of the dataset based on this.

Before that, let's make something that will print out the clustered points of the best solution (KMeansCluster class provides a few classes for this):
```java
System.out.println("\n\n************Clustered classes :");
KMeansCluster optimal = new KMeansCluster(fileLocation, 3);
optimal.initialCentroids();
optimal.setCentroids(storageBestCentroids);
optimal.cluster();
optimal.printAllClustered();
 ```

From here, we can even compare the exact points it classified. This can prove to be not that useful, however: class values in the iris dataset were labelled 0,1,2 for the species of flowers (each number corresponded to a certain type of flower), but the k-means clustering simply assigned classes to the datapoints, it has *no way of knowing the type of flower associated with a certain cluster*. That's why I'll compare the centroids:
```java
String fileLocation2 = "dataset/irisLabels.txt";

System.out.println("\n********Real centroids at:");

KMeansCluster realClasses = new KMeansCluster(fileLocation, numberOfClusters);
realClasses.setClusteredClassestoReal(fileLocation2);
realClasses.initialCentroids();
realClasses.getCentroids();
realClasses.printCentroids();
```
Using a couple other methods provided in the KMeansCluster class, I can get the position of the real centroids as seen above. Note that I had to manually create a new csv, with only one column: the class values. With this, the setClusteredClassestoReal method is used to load those classes directly into the object, and compute the centroids from them.

#### Results

I'll show some screenshots of the final results of my program. I run my program for 1000 loops, and pick the best solution at the end for the iris dataset. I choose to make the program create 3 clusters.

<img src="https://github.com/WiIIiamTang/term_project_360-420-w2019-section2-Tang-Dinh/blob/ce_Tang/CE/William/results1.PNG" />

This was the optimal solution found. The original iris dataset had 50 flowers of each type. It looks like it was able to sort through one class easily enough. The two others are a bit harder; this is expected. As seen in class from Sameer's part of the course, the iris-setosa flower was easily separable from the others, while the two other species of flowers overlapped in a lot of their traits, making it harder to classify. Now let's look at the real centroids of the dataset, just to compare.

<img src="https://github.com/WiIIiamTang/term_project_360-420-w2019-section2-Tang-Dinh/blob/ce_Tang/CE/William/results2.PNG" />

The first centroids are pretty close! The algorithm almost got it down perfectly. The other two are alright, but not as close.

Here's a full list of the clustered classes for the optimal solution (since we know the real class labels, the real answers should be 50 0's, followed by 50 1's, and then 50 2's):

************Clustered classes :
Point                                Clustered Class
 5.100  3.500  1.400  0.200                0.0
 4.900  3.000  1.400  0.200                0.0
 4.700  3.200  1.300  0.200                0.0
 4.600  3.100  1.500  0.200                0.0
 5.000  3.600  1.400  0.200                0.0
 5.400  3.900  1.700  0.400                0.0
 4.600  3.400  1.400  0.300                0.0
 5.000  3.400  1.500  0.200                0.0
 4.400  2.900  1.400  0.200                0.0
 4.900  3.100  1.500  0.100                0.0
 5.400  3.700  1.500  0.200                0.0
 4.800  3.400  1.600  0.200                0.0
 4.800  3.000  1.400  0.100                0.0
 4.300  3.000  1.100  0.100                0.0
 5.800  4.000  1.200  0.200                0.0
 5.700  4.400  1.500  0.400                0.0
 5.400  3.900  1.300  0.400                0.0
 5.100  3.500  1.400  0.300                0.0
 5.700  3.800  1.700  0.300                0.0
 5.100  3.800  1.500  0.300                0.0
 5.400  3.400  1.700  0.200                0.0
 5.100  3.700  1.500  0.400                0.0
 4.600  3.600  1.000  0.200                0.0
 5.100  3.300  1.700  0.500                0.0
 4.800  3.400  1.900  0.200                0.0
 5.000  3.000  1.600  0.200                0.0
 5.000  3.400  1.600  0.400                0.0
 5.200  3.500  1.500  0.200                0.0
 5.200  3.400  1.400  0.200                0.0
 4.700  3.200  1.600  0.200                0.0
 4.800  3.100  1.600  0.200                0.0
 5.400  3.400  1.500  0.400                0.0
 5.200  4.100  1.500  0.100                0.0
 5.500  4.200  1.400  0.200                0.0
 4.900  3.100  1.500  0.200                0.0
 5.000  3.200  1.200  0.200                0.0
 5.500  3.500  1.300  0.200                0.0
 4.900  3.600  1.400  0.100                0.0
 4.400  3.000  1.300  0.200                0.0
 5.100  3.400  1.500  0.200                0.0
 5.000  3.500  1.300  0.300                0.0
 4.500  2.300  1.300  0.300                0.0
 4.400  3.200  1.300  0.200                0.0
 5.000  3.500  1.600  0.600                0.0
 5.100  3.800  1.900  0.400                0.0
 4.800  3.000  1.400  0.300                0.0
 5.100  3.800  1.600  0.200                0.0
 4.600  3.200  1.400  0.200                0.0
 5.300  3.700  1.500  0.200                0.0
 5.000  3.300  1.400  0.200                0.0
 7.000  3.200  4.700  1.400                1.0
 6.400  3.200  4.500  1.500                1.0
 6.900  3.100  4.900  1.500                1.0
 5.500  2.300  4.000  1.300                1.0
 6.500  2.800  4.600  1.500                1.0
 5.700  2.800  4.500  1.300                1.0
 6.300  3.300  4.700  1.600                1.0
 4.900  2.400  3.300  1.000                1.0
 6.600  2.900  4.600  1.300                1.0
 5.200  2.700  3.900  1.400                1.0
 5.000  2.000  3.500  1.000                1.0
 5.900  3.000  4.200  1.500                1.0
 6.000  2.200  4.000  1.000                1.0
 6.100  2.900  4.700  1.400                1.0
 5.600  2.900  3.600  1.300                1.0
 6.700  3.100  4.400  1.400                1.0
 5.600  3.000  4.500  1.500                1.0
 5.800  2.700  4.100  1.000                1.0
 6.200  2.200  4.500  1.500                1.0
 5.600  2.500  3.900  1.100                1.0
 5.900  3.200  4.800  1.800                1.0
 6.100  2.800  4.000  1.300                1.0
 6.300  2.500  4.900  1.500                1.0
 6.100  2.800  4.700  1.200                1.0
 6.400  2.900  4.300  1.300                1.0
 6.600  3.000  4.400  1.400                1.0
 6.800  2.800  4.800  1.400                1.0
 6.700  3.000  5.000  1.700                1.0
 6.000  2.900  4.500  1.500                1.0
 5.700  2.600  3.500  1.000                1.0
 5.500  2.400  3.800  1.100                1.0
 5.500  2.400  3.700  1.000                1.0
 5.800  2.700  3.900  1.200                1.0
 6.000  2.700  5.100  1.600                1.0
 5.400  3.000  4.500  1.500                1.0
 6.000  3.400  4.500  1.600                1.0
 6.700  3.100  4.700  1.500                1.0
 6.300  2.300  4.400  1.300                1.0
 5.600  3.000  4.100  1.300                1.0
 5.500  2.500  4.000  1.300                1.0
 5.500  2.600  4.400  1.200                1.0
 6.100  3.000  4.600  1.400                1.0
 5.800  2.600  4.000  1.200                1.0
 5.000  2.300  3.300  1.000                1.0
 5.600  2.700  4.200  1.300                1.0
 5.700  3.000  4.200  1.200                1.0
 5.700  2.900  4.200  1.300                1.0
 6.200  2.900  4.300  1.300                1.0
 5.100  2.500  3.000  1.100                1.0
 5.700  2.800  4.100  1.300                1.0
 6.300  3.300  6.000  2.500                2.0
 5.800  2.700  5.100  1.900                1.0
 7.100  3.000  5.900  2.100                2.0
 6.300  2.900  5.600  1.800                2.0
 6.500  3.000  5.800  2.200                2.0
 7.600  3.000  6.600  2.100                2.0
 4.900  2.500  4.500  1.700                1.0
 7.300  2.900  6.300  1.800                2.0
 6.700  2.500  5.800  1.800                2.0
 7.200  3.600  6.100  2.500                2.0
 6.500  3.200  5.100  2.000                2.0
 6.400  2.700  5.300  1.900                2.0
 6.800  3.000  5.500  2.100                2.0
 5.700  2.500  5.000  2.000                1.0
 5.800  2.800  5.100  2.400                1.0
 6.400  3.200  5.300  2.300                2.0
 6.500  3.000  5.500  1.800                2.0
 7.700  3.800  6.700  2.200                2.0
 7.700  2.600  6.900  2.300                2.0
 6.000  2.200  5.000  1.500                1.0
 6.900  3.200  5.700  2.300                2.0
 5.600  2.800  4.900  2.000                1.0
 7.700  2.800  6.700  2.000                2.0
 6.300  2.700  4.900  1.800                1.0
 6.700  3.300  5.700  2.100                2.0
 7.200  3.200  6.000  1.800                2.0
 6.200  2.800  4.800  1.800                1.0
 6.100  3.000  4.900  1.800                1.0
 6.400  2.800  5.600  2.100                2.0
 7.200  3.000  5.800  1.600                2.0
 7.400  2.800  6.100  1.900                2.0
 7.900  3.800  6.400  2.000                2.0
 6.400  2.800  5.600  2.200                2.0
 6.300  2.800  5.100  1.500                1.0
 6.100  2.600  5.600  1.400                1.0
 7.700  3.000  6.100  2.300                2.0
 6.300  3.400  5.600  2.400                2.0
 6.400  3.100  5.500  1.800                2.0
 6.000  3.000  4.800  1.800                1.0
 6.900  3.100  5.400  2.100                2.0
 6.700  3.100  5.600  2.400                2.0
 6.900  3.100  5.100  2.300                2.0
 5.800  2.700  5.100  1.900                1.0
 6.800  3.200  5.900  2.300                2.0
 6.700  3.300  5.700  2.500                2.0
 6.700  3.000  5.200  2.300                2.0
 6.300  2.500  5.000  1.900                1.0
 6.500  3.000  5.200  2.000                2.0
 6.200  3.400  5.400  2.300                2.0
 5.900  3.000  5.100  1.800                1.0

Again, this shows that the first class 0 in the iris dataset was clustered pretty well. The program is able to recognize that there is a category of flower here. In classes 1 and 2, however, it has a much harder time clustering the right points.


How can we improve this? Maybe normalizing our data would help - we had a whole discussion on this in our logistic regression. Let's try running it after standardizing all our data to z-scores using the standardScaler method:
```java
kmean.standardScaler();
```
Just call the method on the object after everytime you create it, like above.

Now we run the same thing again, 1000 runs, and see how it performed.

<img src="https://github.com/WiIIiamTang/term_project_360-420-w2019-section2-Tang-Dinh/blob/ce_Tang/CE/William/results3.PNG" />

Okay, so the classes are close to the 50/50/50 split now, which is nice. But let's look and compare with the real centroids for a moment:

<img src="https://github.com/WiIIiamTang/term_project_360-420-w2019-section2-Tang-Dinh/blob/ce_Tang/CE/William/results4.PNG" />

The first position of the centroid is alright again, but the others don't quite match that well. And if we look at the class clusters, it doesn't look that good:

************Clustered classes :
Point                             Clustered Class
-0.901  1.019 -1.340 -1.315                0.0
-1.143 -0.132 -1.340 -1.315                0.0
-1.385  0.328 -1.397 -1.315                0.0
-1.507  0.098 -1.283 -1.315                0.0
-1.022  1.249 -1.340 -1.315                0.0
-0.537  1.940 -1.170 -1.052                0.0
-1.507  0.789 -1.340 -1.184                0.0
-1.022  0.789 -1.283 -1.315                0.0
-1.749 -0.362 -1.340 -1.315                0.0
-1.143  0.098 -1.283 -1.447                0.0
-0.537  1.479 -1.283 -1.315                0.0
-1.264  0.789 -1.227 -1.315                0.0
-1.264 -0.132 -1.340 -1.447                0.0
-1.870 -0.132 -1.511 -1.447                0.0
-0.053  2.170 -1.454 -1.315                0.0
-0.174  3.091 -1.283 -1.052                0.0
-0.537  1.940 -1.397 -1.052                0.0
-0.901  1.019 -1.340 -1.184                0.0
-0.174  1.710 -1.170 -1.184                0.0
-0.901  1.710 -1.283 -1.184                0.0
-0.537  0.789 -1.170 -1.315                0.0
-0.901  1.479 -1.283 -1.052                0.0
-1.507  1.249 -1.568 -1.315                0.0
-0.901  0.559 -1.170 -0.921                0.0
-1.264  0.789 -1.056 -1.315                0.0
-1.022 -0.132 -1.227 -1.315                0.0
-1.022  0.789 -1.227 -1.052                0.0
-0.780  1.019 -1.283 -1.315                0.0
-0.780  0.789 -1.340 -1.315                0.0
-1.385  0.328 -1.227 -1.315                0.0
-1.264  0.098 -1.227 -1.315                0.0
-0.537  0.789 -1.283 -1.052                0.0
-0.780  2.400 -1.283 -1.447                0.0
-0.416  2.630 -1.340 -1.315                0.0
-1.143  0.098 -1.283 -1.315                0.0
-1.022  0.328 -1.454 -1.315                0.0
-0.416  1.019 -1.397 -1.315                0.0
-1.143  1.249 -1.340 -1.447                0.0
-1.749 -0.132 -1.397 -1.315                0.0
-0.901  0.789 -1.283 -1.315                0.0
-1.022  1.019 -1.397 -1.184                0.0
-1.628 -1.743 -1.397 -1.184                0.0
-1.749  0.328 -1.397 -1.315                0.0
-1.022  1.019 -1.227 -0.789                0.0
-0.901  1.710 -1.056 -1.052                0.0
-1.264 -0.132 -1.340 -1.184                0.0
-0.901  1.710 -1.227 -1.315                0.0
-1.507  0.328 -1.340 -1.315                0.0
-0.658  1.479 -1.283 -1.315                0.0
-1.022  0.559 -1.340 -1.315                0.0
 1.402  0.328  0.535  0.264                2.0
 0.675  0.328  0.422  0.396                2.0
 1.280  0.098  0.649  0.396                2.0
-0.416 -1.743  0.138  0.133                1.0
 0.796 -0.592  0.479  0.396                1.0
-0.174 -0.592  0.422  0.133                1.0
 0.553  0.559  0.535  0.527                2.0
-1.143 -1.513 -0.260 -0.262                1.0
 0.917 -0.362  0.479  0.133                2.0
-0.780 -0.823  0.081  0.264                1.0
-1.022 -2.434 -0.147 -0.262                1.0
 0.069 -0.132  0.251  0.396                1.0
 0.190 -1.974  0.138 -0.262                1.0
 0.311 -0.362  0.535  0.264                1.0
-0.295 -0.362 -0.090  0.133                1.0
 1.038  0.098  0.365  0.264                2.0
-0.295 -0.132  0.422  0.396                1.0
-0.053 -0.823  0.194 -0.262                1.0
 0.432 -1.974  0.422  0.396                1.0
-0.295 -1.283  0.081 -0.131                1.0
 0.069  0.328  0.592  0.791                2.0
 0.311 -0.592  0.138  0.133                1.0
 0.553 -1.283  0.649  0.396                1.0
 0.311 -0.592  0.535  0.001                1.0
 0.675 -0.362  0.308  0.133                1.0
 0.917 -0.132  0.365  0.264                2.0
 1.159 -0.592  0.592  0.264                2.0
 1.038 -0.132  0.706  0.659                2.0
 0.190 -0.362  0.422  0.396                1.0
-0.174 -1.053 -0.147 -0.262                1.0
-0.416 -1.513  0.024 -0.131                1.0
-0.416 -1.513 -0.033 -0.262                1.0
-0.053 -0.823  0.081  0.001                1.0
 0.190 -0.823  0.763  0.527                1.0
-0.537 -0.132  0.422  0.396                1.0
 0.190  0.789  0.422  0.527                2.0
 1.038  0.098  0.535  0.396                2.0
 0.553 -1.743  0.365  0.133                1.0
-0.295 -0.132  0.194  0.133                1.0
-0.416 -1.283  0.138  0.133                1.0
-0.416 -1.053  0.365  0.001                1.0
 0.311 -0.132  0.479  0.264                1.0
-0.053 -1.053  0.138  0.001                1.0
-1.022 -1.743 -0.260 -0.262                1.0
-0.295 -0.823  0.251  0.133                1.0
-0.174 -0.132  0.251  0.001                1.0
-0.174 -0.362  0.251  0.133                1.0
 0.432 -0.362  0.308  0.133                1.0
-0.901 -1.283 -0.431 -0.131                1.0
-0.174 -0.592  0.194  0.133                1.0
 0.553  0.559  1.274  1.712                2.0
-0.053 -0.823  0.763  0.922                1.0
 1.523 -0.132  1.217  1.186                2.0
 0.553 -0.362  1.047  0.791                2.0
 0.796 -0.132  1.161  1.317                2.0
 2.129 -0.132  1.615  1.186                2.0
-1.143 -1.283  0.422  0.659                1.0
 1.765 -0.362  1.445  0.791                2.0
 1.038 -1.283  1.161  0.791                2.0
 1.644  1.249  1.331  1.712                2.0
 0.796  0.328  0.763  1.054                2.0
 0.675 -0.823  0.876  0.922                2.0
 1.159 -0.132  0.990  1.186                2.0
-0.174 -1.283  0.706  1.054                1.0
-0.053 -0.592  0.763  1.580                1.0
 0.675  0.328  0.876  1.449                2.0
 0.796 -0.132  0.990  0.791                2.0
 2.250  1.710  1.672  1.317                2.0
 2.250 -1.053  1.786  1.449                2.0
 0.190 -1.974  0.706  0.396                1.0
 1.280  0.328  1.104  1.449                2.0
-0.295 -0.592  0.649  1.054                1.0
 2.250 -0.592  1.672  1.054                2.0
 0.553 -0.823  0.649  0.791                1.0
 1.038  0.559  1.104  1.186                2.0
 1.644  0.328  1.274  0.791                2.0
 0.432 -0.592  0.592  0.791                1.0
 0.311 -0.132  0.649  0.791                2.0
 0.675 -0.592  1.047  1.186                2.0
 1.644 -0.132  1.161  0.527                2.0
 1.886 -0.592  1.331  0.922                2.0
 2.492  1.710  1.502  1.054                2.0
 0.675 -0.592  1.047  1.317                2.0
 0.553 -0.592  0.763  0.396                1.0
 0.311 -1.053  1.047  0.264                1.0
 2.250 -0.132  1.331  1.449                2.0
 0.553  0.789  1.047  1.580                2.0
 0.675  0.098  0.990  0.791                2.0
 0.190 -0.132  0.592  0.791                1.0
 1.280  0.098  0.933  1.186                2.0
 1.038  0.098  1.047  1.580                2.0
 1.280  0.098  0.763  1.449                2.0
-0.053 -0.823  0.763  0.922                1.0
 1.159  0.328  1.217  1.449                2.0
 1.038  0.559  1.104  1.712                2.0
 1.038 -0.132  0.820  1.449                2.0
 0.553 -1.283  0.706  0.922                1.0
 0.796 -0.132  0.820  1.054                2.0
 0.432  0.789  0.933  1.449                2.0
 0.069 -0.132  0.763  0.791                1.0
 
 Classes 1 and 2 are really mixed up! And upon inspection, even though the clustered classes have a better distribution, the datapoints it classified seem to be worse. So really, from what I see, I cannot say with certainty that normalizing the data produces better results, at least when we check the classification with the real answers.
 
 One more question remains. What would happen if we didn't know the number of clusters? Remember, in conventional unsupervised machine learning, we are only presented with the data, and the program would have to cluster points together. There wouldn't be any possibility of checking our results with the acutal answers like I did here; in reality, we would just have to trust that the algorithm clustered a number of classes correctly. We would not know the real class values, and because of that, we would not know the number of clusters K either! So how do we determine the number of clusters that algorithm should use?
 
 Well, one way is just to experiment and try different values of K. Let's do it for the iris dataset, and plot out the summed average distance of points to their respective centroids with respect to K.
 
 <img src="https://github.com/WiIIiamTang/term_project_360-420-w2019-section2-Tang-Dinh/blob/ce_Tang/CE/William/results5.PNG" />
 
 And what we get is a very interesting graph. Past 4 or more clusters, the distance does not seem to change that much. However, we spot an abnormality at K = 2, the distance becomes much smaller. But then, at K = 3, the distance jumps back up. This means that it would be better to use K = 2 over K =3. With 2 clusters, the k-means algorithm would cluster two types of flowers together in one class because of the similarity of their features. This makes sense, when you think back to all those graphs we did with Sameer: the first flower was easily classified, while the other two overlapped a bit and did not have clearly defined borders. Of course the k-means algorithm would find that two clusters would be better.
 
 This is where I can observe one weakness of this clustering method: it groups datapoints that have similar features together. But in doing so, it can incorrectly group together incorrect points or outliers.
 
 On a related note, what I *was* hoping for in the distance vs. K graph was a "elbow point". Here's an example that I got from Andrea Trevino in his  [article about K-Means Clustering:](https://www.datascience.com/blog/k-means-clustering)
 
 <img src="https://github.com/WiIIiamTang/term_project_360-420-w2019-section2-Tang-Dinh/blob/ce_Tang/CE/William/example1.PNG" />
 
 At some point, the distance would stop decreasing at a fast rate, and we would pick the K whose designated point is where the curve plateaus a bit, the "elbow point". On my graph, this *would* have been the case, but there was an abnormality, like I said, because the features for two types of flowers were rather similar.
 
 #### Conclusion
 
 K-Means Clustering is great when you want to sort through some data, without knowing what the classes actually are. This is unsupervised learning. The algorithm can spot patterns and cluster together groups that you would not expect; this can prove to be beneficial or detrimental depending on the case. Also, the data itself can affect the clustering; this algorithm is sensitive to outliers and overlapping of classes. When we don't know the number of clusters, we can create an average distance to centroids vs. K graph, and experimentally determine the best number of clusters.
 
