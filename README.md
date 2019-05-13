# 360-420 Intro to Programming


**Modeling Classification Problems in Machine Learning: Logistic Regression**

William Tang and Jason Dinh


Section: 02

Professors: Jean-François Brière, Jonathan Sumner, Sameer Bhatnagar

# 0. Project Structure
*CE* : Independent study projects, by Jason and William. Extension of term project. Supervisor: Jean-François Brière

*python* : sklearn logisitic regression implementation.

*report* : contains final report, preliminary report.

*resources* : all graphs, images, data generated from project. Also contains the original datasets used in the regression.

*src* : source folder for all the code.

*UCI_wine_data_exploratory.ipynb* : jupyter notebook for some exploratory data.

# 1. Logistic Regression

**To read about this project, consult the [report](/report) folder. The main dataset we look at in this project is the UCI wine dataset, classifying wine 1 vs. wine 2.**

Also look at the jupyter notebook for some exploratory data analysis on the wine dataset: in the last section, some scatter plots are presented in the case wine 1 vs. wine 2 classification.

**Instructions**: This is a binomial logistic regression, it only classifies between two classes at a time. To run the main program (one instance of the logistic regression), go to the src folder, and compile and run Classification.java.

To run multiple instances of the logistic regression and obtain the average accuracy over a certain number of tries, compile and run MultipleRuns.java.

The program uses command line arguments. Type in the path to the file you want to perform a logistic regression on. In MultipleRuns.java, you also have to indicate how many runs to do. Usually you would use the datasets provided [here](/src/dataset).

There are also some things to create graphs. You can run AccuracyVsAlphagraph or AccuracyVsTolerancegraph to get a sense of how a parameter is affecting the accuracy of your model for one single instance. There is also the possibility of creating a ROC curve and a recall-precision graph, which may useful in choosing the prediction threshold and evaluating the performance of the model.

Concerning reproducibility, read the [final report](/report/FinalReport) for parameters that we used to generate our results.

For more information on using the program, make sure to read the classes description found here and then consult [this short tutorial](/src).


#### Validation

Use the methods provided in the [metrics](/src/ai/metrics) to check the results of the program with the real class labels.

Also, we checked if our implementation of logistic regression was working correctly by testing it against a few other datasets.
We used some other combinations of the wine dataset, the breast cancer data as well as some school exam marks.

You can change what the program classifies by changing the file path in the command line argument.

There is also a [python implementation](/python) using sklearn that we use to compare our model with a premade machine learning library. Finally, we can compare our model against the [kNN classifier done in class](https://github.com/WiIIiamTang/w19-360420-machine-learning/tree/wine).
_____________________________________________________________________________________________________________________
# 2. Data

In the [resources/originaldata](/resources/originaldata) folder, you'll see the original sets of data. Most of the datasets were gathered from the [UCI Machine Learning Repository](http://archive.ics.uci.edu/ml/index.php), with the exception of marks.csv. We got this dataset from a machine learning workshop by ConcordAI on 29/03/2019.

#### Changes made
The [wine](http://archive.ics.uci.edu/ml/datasets/Wine) data originally had three types of wine to classify, but we modified it
so that we could do a binary logistic regression.

For the [breast cancer](https://archive.ics.uci.edu/ml/datasets/breast+cancer+wisconsin+(original)) data, we had to manually remove some data points that had missing values in them (indicated with a "?" symbol).

The modified datasets can be found [here](/src/dataset/).


_____________________________________________________________________________________________________________________
# 3. Classes description: ai folder

The [/ai](/ai) directory contains useful classes for the binominal logistic regression. If you want to recreate the project or make your own logistic regression program or something, you can use these classes for yourself. See below for details:

## Dataloader.java

- a dataloader object contains the intial x and y array, as well and the training/test sets. You always have the option of returning, printing, or directly setting the arrays with the appropriate method (eg. dataloader.returnXTrainArray(), dataloader.printXTrainArray()).
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

#### makeYLabels

```java
public void makeYLabels(double checkIfThis, double assignThis, double assignThisOtherwise)

//this method is something that we might need to use to get all the y-labels to ONLY zeroes and ones, for a binary logistic regression.
//this means that we'll check for one class label (only numerical for now!), and all the others get assigned something else (assignThisOtherwise)
//the label we're checking for is assigned (assignThis). this methods works on the allY array ONLY.
// Here's an example usage; say we want to get a dataset with wine 2 vs. other wines (type 1, 3):
// Dataloader makeYLabels (2, 1, 0);
// We check if the label is 2 here, and assign it to 1. Everything else is 0.
```

#### shuffleData
```java
public void shuffleData()

//nothing needs to passed in to this method for it to work
//just make sure you have already make the initial arrays allX and allY beforehand
```

#### trainTestSplit
```java
public void trainTestSplit(double trainingSplitPercent)

//this will split the allX and allY into the training and test sets
//indicate the proportion of the datapoints that should go into the training set in percentage terms.
```

## FeatureScaling.java
- This is more or less an optional step, but it's always good to normalize your data.
- Pass in your arrays through the class to scale them

#### standardScaler
```java
public static void standardScaler(double[][] xTrainArray, double[][] xTestArray)

//pass in the two x-arrays that you should have made already. It will scale all values to z-scores, ie. every feature 
//variable will have a mean of 0 and standard deviation of 1 in the dataset.
//test set data is scaled with the training mean and standard deviation.
```
#### minMaxScaler
```java
public static void minMaxScaler(double[][] xTrainArray, double[][] xTestArray)

//pass in the two x-arrays that you should have made already. It will scale all values to a range in between zero and one.
//test set data is scaled with the training max and min.
```

### LogisticRegression.java

- This is what you would use if you want to perform a logistic regression on your dataset. You always have the option of returning or printing the arrays with the appropriate method (eg. logisticregression.returnXTrainArray(), logisticregression.printXTrainArray()).
- construct the object first by passing in the 4 training/test sets like so:
```java
LogisticRegression regression = new LogisticRegression(xtrain, xtest, ytrain, ytest);
```

- then it is as simple as calling a few methods for the rest:

#### fit
```java
public void fit(double learningRate, double maxIterations, double regularizationParameter, boolean randomize, double checkforDifference)

//this method will compute the optimal weights for your training set and save them in the beta array.
//you can change the learning rate alpha, the max number of iterations it should run for, how much regularization you want.
//indicate true in the boolean if you want the initial beta weights to be assigned random values from 0 to 1.
//finally, you can also indicate the tolerance level. At each iteration of the gradient descent, the difference is calculated between //each element of the beta array and value it had before. 
//If all of the differences are below your tolerance level, the loop is terminated.
//If you don't want to check for this tolerance level, you can omit the checkForDifference variable while calling the method, and it will still work.
```

#### predictTrainSet
```java
public double[] predictTrainSet(double predictionThreshold)

//returns an array with the predictions based on the x-training set.
//it will use the beta array that contains the weights to make the prediction, so make sure you did the gradient descent already.
//indicate what the prediction threshold should be (usually it's 0.5)
```

#### predictTestSet
```java
public double[] predictTestSet(double predictionThreshold)

//this is the same as the previous method but for the x-test set.
```
#### getPredictionsProbabilityTrain
```java
public double[] getPredictionsProbabilityTrain()

//returns an array with the predictions based on the x-training set, BUT IN PROBABILITIES.
//it will use the beta array that contains the weights to make the prediction, so make sure you did the gradient descent already.
```
#### getPredictionsProbabilityTest
```java
public double[] getPredictionsProbabilityTest()

//returns an array with the predictions based on the x-test set, BUT IN PROBABILITIES.
//it will use the beta array that contains the weights to make the prediction, so make sure you did the gradient descent already.
```

#### resetWeights
```java
public void resetWeights()

//resets weights array to all 0s.
```

## ModelEvaluator.java

- After having trained your model, you can check your results with this class
- You will need an array ready with predicted values

#### getAccuracy
```java
public static double getAccuracy(double[] yLabels, double[] predictedYLabels)

//pass in your y-training set or your y-test set followed by the array that has the predictions in it.
//the method will compare all the elements and return an accuracy in percentage form
```

#### getBaseLineAcc
```java
public static double getBaseLineAcc(double[] yLabels)

//returns baseline accuracy using the zero rule algorithm
```

#### confusionMatrix
```java
public static void confusionMatrix(double[] yLabels, double[] predictedYLabels)

//makes and prints the confusion matrix for the logistic regression
```

#### mcfaddenRSquared
```java
public static double mcfaddenRSquared(double[][]x, double[] yLabels, double[]beta)

//takes in the feature variables, the class labels, and the weights as arrays and returns McFadden's R-squared value.
```

#### rankWeights
```java
public static void rankWeights(double[] array)

//ranks weights based on their abs. value from largest to smallest.
//Standardized data is recommended before doing this, or else the results from this method won't be very meaningful.
```

_____________________________________________________________________________________________________________________

# 4. Changelog

### 26-04-2019
- first "working" version LogisticWine1 + LogisticCancer
- prelim report

### 29-04-2019
- gradient method: fixed an issue in the partial derivative where the total sum so far would be multiplied by the value of the weight instead of multiplying one specific instance of the difference between the sigmoid and label by the value of the weight. Partial derivative now calculates correctly.
- added a regularization parameter to the gradient and cost function (still in testing)
- Changed readme info in scr folder: added "Validation" section for breast cancer, school marks, python; removed "results" section, logwine2 and logwine3
- added python implementation of logistic regression
- added marks.csv in resources, added marks txt file in src/dataset folder

### 01-05-2019
Classes Update
  - created src/ai directory to hold machine learning classes. This moves all methods out of the main class we had before and organizes them into other classes that we use based on their function.
  - This update makes our program much more general and not only limited to classifying wine. The ai/ classes are reusable in other machine learning contexts
  - preprocessing: contains methods useful for loading in the data, train/test split, feature scaling (also the program counts the num of rows and columns by itself now)
  - models: contains the logistic regression model (gradient descent)
  - metrics: contains methods useful for evaulating accuracy of model's predictions
  - made MachineLearn.java: this is the main class to run the program now. It would be necessary to import the ai/ classes first.
  - added deprecated folder to move our old version into (will be removed after a while)
  - added subdirectories in the resources folder to organize content better
  - readme description in src directory made more clearer, and added instructions on how to run the program.
  - readme description in resources/originaldata edited to be more clearer
 
 ### 02-05-2019
 - added classes description in readme file in /src
 
 ### 11-05-2019
 - changed the methods in the feature scaling class and the model evaluator class to static; no objects will need to be created to use them now
 - model evaluator:
     1. added baseline acc calculator 
     2. added confusion matrix and associated measures of error
     3. added McFadden's R squared value
     4. added ranking of weights by abs. value
 - added jmathplot library to src
 - added new java file ROCgraph.java that will generate the ROC curve of a logistic regression model
 - added new java file RecallPrecisiongraph.java that will generate the recall-precision curve of a logistic regression model
 - removed regularization parameter
 - removed print statements in logistic regression classes (Dataloader, LogisticRegression, FeatureScaling, ModelEvaluator)
 - added command line args in MachineLearn.java
 - cleaned up some unused code in the logistic regression class
 
 ### 12-05-2019
 - added new java file AccuracyVsAlphagraph.java, ToleranceVsAlpha.java, MultipleRuns.java (gives confusion matrix after certain number of tries)
 - exploratory data jupyter notebook for wine dataset added
 - most README descriptions moved to main page to gather information all in one place
 - instructions updated
 
 ### 13-05-2019
 - MultipleRuns.java now correctly displays average + standard deviation of accuracy
 - added additional instructions, example usage in src README file
 - requirements.txt in python
