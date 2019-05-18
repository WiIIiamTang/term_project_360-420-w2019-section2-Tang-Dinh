///////////////////////////////////////////////////////////////////////////////////////////////
/*
Wine Classification

Original Java code by William Tang and Jason Dinh
Translated to JavaScript by Jason Dinh
*/
///////////////////////////////////////////////////////////////////////////////////////////////
var file = "wine1vs2.csv";
var DataLoader = require("./DataLoader.js");
var FeatureScaling = require("./FeatureScaling.js");
var LinearRegression = require("./LinearRegression.js");
var ModelEvaluator = require("./ModelEvaluator.js");

var makeXArrays = DataLoader.makeXArrays;
var makeYArrays = DataLoader.makeYArrays;
var shuffleData = DataLoader.shuffleData;
var splitXTrain = DataLoader.splitXTrain;
var splitXTest = DataLoader.splitXTest;
var splitYTrain = DataLoader.splitYTrain;
var splitYTest = DataLoader.splitYTest;
var standardScaler = FeatureScaling.standardScaler;
var fit = LinearRegression.fit;
var predictTrainSet = LinearRegression.predictTrainSet;
var predictTestSet = LinearRegression.predictTestSet;
var getAccuracy = ModelEvaluator.getAccuracy;
var getBaselineAcc = ModelEvaluator.getBaselineAcc;
var confusionMatrix = ModelEvaluator.confusionMatrix;
var mcFaddenRSquared = ModelEvaluator.mcFaddenRSquared;
var rankWeights = ModelEvaluator.rankWeights;

var allX = makeXArrays(file);
var allY = makeYArrays(file);

shuffleData(allX, allY);

var xTrain = splitXTrain(allX, 0.70);
var xTest = splitXTest(allX,0.70);
var yTrain = splitYTrain(allY, 0.70);
var yTest = splitYTest(allY, 0.70);
var beta = new Array(xTrain[0].length+1);

standardScaler(xTrain, xTest)

fit(xTrain, yTrain, 0.1,5000,false);

var predictionsOnTrainSet = predictTrainSet(0.5);
var predictionsOnTestSet = predictTestSet(0.5);


var acc1 = 0;
var acc2 = 0;

acc1 = getAccuracy(yTrain, predictionsOnTrainSet);
acc2 = getAccuracy(yTest, predictionsOnTestSet);

console.log("Model finished with " + acc1 + " accuracy on the training set.");
console.log("It got " + acc2 + " accuracy on the test set.");
console.log("Baseline accuracy of test set at " + getBaselineAcc(yTest));
confusionMatrix(yTest, predictionsOnTestSet);

console.log("R-squared value on the test set = " + mcFaddenRSquared(xTest, yTest, beta));
console.log("R-squared value on the training set = " + mcFaddenRSquared(xTrain, yTrain, beta));

rankWeights(beta);