# Logistic Regression

**Instructions**: To run the program, compile and run MachineLearn.java. This is set by default to classify the wine dataset (dataset_wine1.txt holds label 1: wine 1 and label 0: wine 2).

Modify parameters or whatever you want in this java file. We will indicate in our report the parameters that we used.
The datasets are found in the dataset folder.

We'd recommend not changing anything in the src/ai directory.


## Validation against other datasets

Just to see if the results we get aren't based on luck, we checked if our actual implementation of logistic regression was working by testing it against another dataset.
We used the breast cancer data as well as some school exam marks.

You can change what the program classifies by changing the file path in the code.

There is also a [python implementation](/python_implementation) using sklearn that we use to compare our model with a premade machine learning library.


## The ai directory

This contains useful classes for machine learning and data analysis. See below for details:

### preprocessing - Dataloader.java

- a dataloader object contains the intial x and y array, as well and the training/test sets. You always have the option of returning or printing the arrays with the appropriate method (eg. dataloader.returnXTrainArray(), dataloader.printXTrainArray()).
- we list some other methods that you might need:

#### returnNumDataPoints

```java
public int returnNumDataPoints(String location)

//returns the number of data points in your file by counting the number of lines.
//pass in the file path as a string (eg. "dataset/marks.txt");
```

#### makeArrays

```java
public void makeArrays(String filelocation)

//makes the allX and allY arrays that contain all the datapoints from your dataset.
//the data will be stored in a 2D array for the x, and 1D array for the y.
//pass in the file path as a string (eg. "dataset/marks.txt");
```

### makeYLabels

```java
public void makeYLabels(double checkIfThis, double assignThis, double assignThisOtherwise)

//this method is something that we might need to use to get all the y-labels to ONLY zeroes and ones, for a binary logistic regression.
//this means that we'll check for one class label (only numerical for now!), and all the others get assigned something else (assignThisOtherwise)
//the label we're checking for is assigned (assignThis). this methods works on the allY array ONLY.
// Here's an example usage; say we want to get a dataset with wine 2 vs. other wines (type 1, 3):
// Dataloader makeYLabels (2, 1, 0);
// We check if the label is 2 here, and assign it to 1. Everything else is 0.
```

### shuffleData
```java
public void shuffleData()

//nothing needs to passed in to this method for it to work
//just make sure you have already make the initial arrays allX and allY beforehand
```

### trainTestSplit
```java
public void trainTestSplit(double trainingSplitPercent)

//this will split the allX and allY into the training and test sets
//indicate the proportion of the datapoints that should go into the training set in percentage terms.
```

## preprocessing - FeatureScaling.java
- This is more or less an optional step, but it's always good to normalize your data.
- Because of this, the feature scaling is in a class of its own; pass in your arrays through the class to scale them

### standardScaler
```java
public void standardScaler(double[][] xTrainArray, double[][] xTestArray)

//pass in the two x-arrays that you should have made already. It will scale all values to z-scores, ie. every feature 
//variable will have a mean of 0 and standard deviation of 1 in the dataset.
//test set data is scaled with the training mean and standard deviation.
```
### minMaxScaler
```java
public void minMaxScaler(double[][] xTrainArray, double[][] xTestArray)

//pass in the two x-arrays that you should have made already. It will scale all values to a range in between zero and one.
//test set data is scaled with the training max and min.
```

## models - LogisticRegression.java

- This is what you would use if you want to perform a logistic regression on your dataset. You always have the option of returning or printing the arrays with the appropriate method (eg. logisticregression.returnXTrainArray(), logisticregression.printXTrainArray()).
- construct the object first by passing in the 4 training/test sets like so:
```java
LogisticRegression regression = new LogisticRegression(xtrain, xtest, ytrain, ytest);
```

- then it is as simple as calling a few methods for the rest:

### fit
```java
public void fit(double learningRate, double maxIterations, double regularizationParameter, boolean randomize, double checkforDifference)

//this method will compute the optimal weights for your training set and save them in the beta array.
//you can change the learning rate alpha, the max number of iterations it should run for, how much regularization you want.
//indicate true in the boolean if you want the initial beta weights to be assigned random values from 0 to 1.
//finally, you can also indicate the tolerance level. At each iteration of the gradient descent, the difference is calculated between //each element of the beta array and value it had before. If all of the differences are below your tolerance level, the loop is //terminated.
//If you don't want to check for this tolerance level, you can omit the checkForDifference variable while calling the method, and it //will still work.
```

### predictTrainSet
```java
public double[] predictTrainSet(double predictionThreshold)

//returns an array with the predictions based on the x-training set.
//it will use the beta array that contains the weights to make the prediction, so make sure you did the gradient descent already.
//indicate what the prediction threshold should be (usually it's 0.5)
```

### predictTestSet
```java
public double[] predictTestSet(double predictionThreshold)

//this is the same as the previous method but for the x-test set.
```

## metrics - ModelEvaluator.java

- After having trained your model, you can check your results with this class
- You will need an array ready with predicted values

### getAccuracy
```java
public double getAccuracy(double[] yLabels, double[] predictedYLabels)

//pass in your y-training set or your y-test set followed by the array that has the predictions in it.
//the method will compare all the elements and return an accuracy in percentage form
```



