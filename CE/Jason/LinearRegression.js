var beta;
var x_train;
var x_test;
var y_train;
var y_test;

function LogisticRegression(xTrainArray, xTestArray, yTrainArray, yTestArray) {
    beta = new Array(xTrainArray[0].length+1);
    x_train = xTrainArray;
    x_test = xTestArray;
    y_train = yTrainArray;
    y_test = yTestArray;
}

function linearSum(x, beta, row) {
        var sum = 0.;

        sum = sum + beta[0];

        for (var i = 1; i < beta.length; i++) {
            sum = sum + (beta[i] * x[row][i-1]);
        }

        return sum;
}

function hypothesis(x, beta, row) {
    var sigmoid = 0.;
    sigmoid = 1/1+(Math.pow(Math.E,-linearSum(x,beta,row)));
    return sigmoid;
}

function costFunction(x, y, beta, c) {
    var m = x.length;
    var cost = 0.;
    var sum = 0.;
    var regularizationSum = 0.;
    var n = beta.length;

    for(var i = 0; i < m; i++) {
        if(y[i] === 1.0){
            sum = sum + (Math.log(hypothesis(x, beta, i)));
        } else {
            sum = sum + Math.log(1 - hypothesis(x, beta, i));
        }
    }

    for(var j = 0; j < n; j++){
        regularizationSum += (beta[j]*beta[j]);
    }

    cost= -(sum/m) + (c/(2*m)) * regularizationSum;

    return cost;
}

function gradient(x, y, beta, betaNum, c) {
    var sum = 0.;
    var toAdd = 0.;
    var m = x.length;

    for(var i = 0; i < m; i++) {
        toAdd += (hypothesis(x, beta, i) - y[i]);

        if(betaNum != 0)
        {
          toAdd *= x[i][betaNum-1];
        }

        sum += toAdd;
        toAdd = 0;
    }
    sum /= m;
    
    if (betaNum != 0) {
        sum += ((c/m) * beta[betaNum]);
    }

    return sum;
}

function fit (learningRate, maxIterations, regularizationSum, randomize){
    var alpha = learningRate;
    var betaNew = new Array(beta.length);
    var iterations = 0;
    var currentCost = 0;

    if(randomize === true){
        assignRandom(beta);
    }

    while (iterations < maxIterations){
        currentCost = costFunction(x_train, y_train, beta, regularizationSum);

        for(var i = 0; i < beta.length; i++){
            betaNew[i] = beta[i] - (alpha * gradient(x_train, y_train, beta, i, regularizationParameter));
        }

        for(var i = 0; i < beta.length;i++) {
            beta[i] = betaNew[i];
        }

        if(iterations % 1000 === 0) {
            console.log("Cost at " + costFunction(x_train, y_train, beta));
            console.log(gradient(x,y,beta,0));
        }
        iterations++;

    }
    console.log("The loop was terminated. Total iterations: " + iterations);
}

function fit(learningRate, maxIterations, randomize, checkForDifference) {
    var alpha = learningRate;
    var betaNew = new Array(beta.length);
    var difference = new Array(beta.length);
    var tolerance = checkForDifference;
    var iterations = 0;
    var checkDifference = true;
    var bestCost = 1000000;
    var currentCost = costFunction(x_train, y_train, beta);

    if (randomize === true) {
        assignRandom(beta);
    }

    while (iterations < maxIterations && checkDifference === true) {
        currentCost = costFunction(x_train,y_train,beta);

        for(var i = 0; i < beta.length; i++) {
            betaNew[i] = beta[i] - (alpha * gradient(x_train, y_train, beta, i));
            difference[i] = Math.abs(beta[i] - betaNew[i]);
        }

        for(var i = 0; i < beta.length;i++) {
            beta[i] = betaNew[i];
        }


        for(var i = 0; i < difference.length; i++) {
            if (difference[i] > tolerance) {
                break;
            }
            checkDifference = false;
        }

        if(iterations % 1000 === 0) {
            console.log("Cost at " + costFunction(x_train, y_train, beta, regularizationParameter));
            console.log(gradient(x,y,beta,0));
        }
        iterations++;

    }
    console.log("The loop was terminated. Total iterations: " + iterations);
    return iterations;
}

function assignRandom(array){
    var max = 1.0;
    var min = 0.0;
    var roll = 0;

    for(var i = 0; i < array.length; i++){
        roll = ((Math.random() * (max - min)) + min);
        array[i] = roll;
    }
}

function getPredictionsProbabilityTrain(){
    var prob = new Array(x_train.length);

    for(var i = 0; i < x_train.length; i++) {
        prob[i] = hypothesis(x_train, beta, i);
    }
    return prob;
}

function getPredictionsProbabilityTest(){
    var prob = new Array(x_test.length);

    for(var i = 0; i < x_test.length; i++) {
        prob[i] = hypothesis(x_test, beta, i);
    }
    return prob;
}

function predictTrainSet(predictionThreshold) {
    var predicted = new Array (x_train.length);
    var threshold = predictionThreshold;

    for(var i = 0; i < x_train.length; i++){
        console.log(hypothesis(x_train, beta, i));

        if(hypothesis(x_train, beta, i) > threshold) {
            predicted[i] = 1.0;
        } else {
            predicted[i] = 0.0;
        }

    }

    return predicted;
}

function predictTestSet(predictionThreshold) {
    var predicted = new Array(x_test.length);
    var threshold = predictionThreshold;

    for(var i = 0; i < x_test.length; i++){
        console.log(hypothesis(x_test, beta, i));

        if(hypothesis(x_test, beta, i) > threshold) {
            predicted[i] = 1.0;
        } else {
            predicted[i] = 0.0;
        }

    }

    return predicted;
}