# 360-420 Intro to Programming


**Term Project: Logistic Regression in Wine Classification**

William Tang and Jason Dinh


Section: 02

Professors: Jean-François Brière, Jonathan Sumner, Sameer Bhatnagar


## Changelog

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
