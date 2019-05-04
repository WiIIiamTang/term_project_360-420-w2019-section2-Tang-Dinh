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
  - preprocessing: contains methods useful for loading in the data, train/test split, feature scaling
  - models: contains the logistic regression model (gradient descent)
  - metrics: contains methods useful for evaulating accuracy of model's predictions
  - made MachineLearn.java: this is the main class to run the program now. It would be necessary to import the ai/ classes first.
  - added deprecated folder to move our old version into (will be removed after a while)
  - added dataset "fullwine.txt" that contains all three types of wines in the src/dataset folder
  - added subdirectories in the resources folder to organize content better
  - readme description in src directory made more clearer, and added instructions on how to run the program.
  - readme description in resources/originaldata edited to be more clearer
 
 ### 02-05-2019
 - added classes description in readme file in /src
