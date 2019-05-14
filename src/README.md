# How to use

Make sure you have a good dataset in csv format beforehand. [Read about some additional requirements here](/src/dataset).

For logistic regression, you can start off by running the code we already made, Classification.java and MultipleRuns.java.
When you run the program, you'll have to indicate the file path. For example:

```
java Classification dataset/wine1vs2.txt
```

In MultipleRuns.java, you'll also have to indicate the number of iterations you want it to run for:

```
java MultipleRuns dataset/wine1vs2.txt 1000
```

There are additional java files that allow you make graphs. To use them, follow the same syntax as before. Because these graphs only run on one single instance of a logistic regression and not an average over a couple hundred runs, we wouldn't recommend using them to represent the general model; they can help, however, to get a sense of how the regression is performing.

Do not run AccVSAlphaIteration.java, it is only there to show what code we ran to gather data and plot the heatmap in excel.

See the [final report](/report/FinalReport) for information on our results, reproducing our results, and the parameters we used.


### Creating a classifier: a simple example

If you haven't done so already, see the classes description for more information on how to use these methods and the parameters that they require.


In general, you would want to first create a dataloader object and load your csv file in using the makeArrays method.

```java
Dataloader data1 = new Dataloader()
data1.makeArrays(args[0])
// OR YOU CAN WRITE data1.makeArrays("your_file_path_here");
```
A dataloader object contains a 2D array that holds all the feature variables and their values, as well as a 1D array that holds all the class labels.
It also contains all the training and test set arrays. At this point, you could shuffle the data around and then split it into training/test sets:

```java
data1.shuffleData();
data1.trainTestSplit(0.70);
```
The object now has the training/test splits ready. If you want to scale your features (with a standardization to z-scores, for example), you can
do so with the FeatureScaling class. For the standardScaler method, you would want to pass in the training set first, and then the test set. If you do this step, you would need to get the training and test sets from the dataloader object first (use the return methods).

```java
FeatureScaling.standardScaler(xTrainArray, xTestArray);
```

Now all the data is ready. Create a LogisticRegression object with the training/test sets and use the fit method to run the gradient descent.

```java
LogisticRegression model = new LogisticRegression(xTrainArray, xTestArray, yTrainArray, yTestArray);
model.fit(0.1,50000,false,0.0000001);
```
After you've run the gradient descent, the weights should have optimized. The LogisticRegression class also has a couple methods that return the
predicted label based on these weights:

```java
double [] predictions = model.predictTestSet(0.5)
```
As a final step, we now need to evaluate the model and how it performed. An easy way to do this is to look at the accuracy. Call the getAccuracy method
from the ModelEvaluator class:

```java
System.out.println("test acc = " + ModelEvaluator.getAccuracy(y_test, predictions);
```

And that's how you can make a simple classifier.
