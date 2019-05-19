///////////////////////////////////////////////////////////////////////////////////////////////
/*
Classification in JavaScript

Original Java code by William Tang and Jason Dinh
Translated to JavaScript by Jason Dinh

This program can classify between 2 objects
The files for the datasets are in folder /term_project_360-420-w2019-section2-Tang-Dinh/CE/Jason/dataset
*/
///////////////////////////////////////////////////////////////////////////////////////////////

//you can change the file name here
var filename = "wine1vs2.csv";

//you can change the parameter values here
var alpha = 0.1;                    //step size
var iterations = 5000;              //number of iterations
var splitingPercentage = 0.70;      //percentage for spliting data into train and test sets


//import methods from other files
var DataLoader = require("./Methods/DataLoader.js");
var FeatureScaling = require("./Methods/FeatureScaling.js");
var LinearRegression = require("./Methods/LinearRegression.js");
var ModelEvaluator = require("./Methods/ModelEvaluator.js");

//DataLoader.js
var makeXArrays = DataLoader.makeXArrays;
var makeYArrays = DataLoader.makeYArrays;
var shuffleData = DataLoader.shuffleData;
var splitXTrain = DataLoader.splitXTrain;
var splitXTest = DataLoader.splitXTest;
var splitYTrain = DataLoader.splitYTrain;
var splitYTest = DataLoader.splitYTest;
//FeartureScaling.js
var standardScaler = FeatureScaling.standardScaler;
//LinearRegression.js
var fit = LinearRegression.fit;
var predictTrainSet = LinearRegression.predictTrainSet;
var predictTestSet = LinearRegression.predictTestSet;
//ModelEvaluator.js
var getAccuracy = ModelEvaluator.getAccuracy;
var getBaselineAcc = ModelEvaluator.getBaselineAcc;
var confusionMatrix = ModelEvaluator.confusionMatrix;
var mcFaddenRSquared = ModelEvaluator.mcFaddenRSquared;
var rankWeights = ModelEvaluator.rankWeights;

//dataset name
var file = "./dataset/" + filename;

//create arrays from dataset
var allX = makeXArrays(file);
var allY = makeYArrays(file);

//shuffle the data
shuffleData(allX, allY);

//start
//splitting train and test set
var xTrain = splitXTrain(allX, splitingPercentage);
var xTest = splitXTest(allX,splitingPercentage);
var yTrain = splitYTrain(allY, splitingPercentage);
var yTest = splitYTest(allY, splitingPercentage);
var beta = new Array(allX[0].length + 1);

//scale to z scores using the feature scaler:
standardScaler(xTrain, xTest);

//logistic regression
beta = fit(xTrain, yTrain, beta, alpha, iterations, false);

//get the predictions using the trained model:
var predictionsOnTrainSet = predictTrainSet(xTrain, beta, 0.5);
var predictionsOnTestSet = predictTestSet(xTest, beta, 0.5);

//evaluate results
//accuracy
var acc1 = getAccuracy(yTrain, predictionsOnTrainSet);
var acc2 = getAccuracy(yTest, predictionsOnTestSet);

console.log("\n**********Result**********");
console.log("Model finished with " + acc1 + " accuracy on the training set.");
console.log("It got " + acc2 + " accuracy on the test set.");
console.log("\nBaseline accuracy of test set at " + getBaselineAcc(yTest));
console.log();

//confusion matrix
confusionMatrix(yTest, predictionsOnTestSet);
console.log();

//mcFadden R-squared calculation
console.log("R-squared value on the training set = " + mcFaddenRSquared(xTrain, yTrain, beta));
console.log("R-squared value on the test set = " + mcFaddenRSquared(xTest, yTest, beta));
console.log();

//ranking top 5 featured characteristics weights
rankWeights(beta);