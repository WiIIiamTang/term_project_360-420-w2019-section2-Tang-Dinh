class LogisticRegression{
    constructor(xTrainArray, xTestArray, yTrainArray, yTestArray) {
        this._beta = new Array(xTrainArray[0].length+1);
        this._xTrain = xTrainArray;
        this._xTest = xTestArray;
        this._yTrain = yTrainArray;
        this._yTest = yTestArray;
    }

    linearSum(x, beta, row) {
        var sum = 0.;

        sum = sum + beta[0];

        for (var i = 1; i < beta.length; i++) {
            sum = sum + (beta[i] * x[row][i-1]);
        }

        return sum;
    }

    hypothesis(x, beta, row) {
        var sigmoid = 0.;
        sigmoid = 1/1+(Math.pow(Math.E,-linearSum(x,beta,row)));
        return sigmoid;
    }

    costFunction(x, y, beta, c) {
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

    gradient(x, y, beta, betaNum, c) {
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

    fit (learningRate, maxIterations, regularizationSum, randomize){
        var alpha = learningRate;
        var betaNew = new Array(beta.length);
        var iterations = 0;
        var currentCost = 0;
    
        if(randomize === true){
            assignRandom(beta);
        }
    
        while (iterations < maxIterations){
            currentCost = costFunction(xTrain, yTrain, beta, regularizationSum);
    
            for(var i = 0; i < beta.length; i++){
                betaNew[i] = beta[i] - (alpha * gradient(xTrain, yTrain, beta, i, regularizationParameter));
            }
    
            for(var i = 0; i < beta.length;i++) {
                beta[i] = betaNew[i];
            }
    
            if(iterations % 1000 === 0) {
                console.log("Cost at " + costFunction(xTrain, yTrain, beta));
                console.log(gradient(x,y,beta,0));
            }
            iterations++;
    
        }
        console.log("The loop was terminated. Total iterations: " + iterations);
    }

    fit(learningRate, maxIterations, randomize, checkForDifference) {
        var alpha = learningRate;
        var betaNew = new Array(beta.length);
        var difference = new Array(beta.length);
        var tolerance = checkForDifference;
        var iterations = 0;
        var checkDifference = true;
        var bestCost = 1000000;
        var currentCost = costFunction(xTrain, yTrain, beta);
    
        if (randomize === true) {
            assignRandom(beta);
        }
    
        while (iterations < maxIterations && checkDifference === true) {
            currentCost = costFunction(xTrain,yTrain,beta);
    
            for(var i = 0; i < beta.length; i++) {
                betaNew[i] = beta[i] - (alpha * gradient(xTrain, yTrain, beta, i));
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
                console.log("Cost at " + costFunction(xTrain, yTrain, beta, regularizationParameter));
                console.log(gradient(x,y,beta,0));
            }
            iterations++;
    
        }
        console.log("The loop was terminated. Total iterations: " + iterations);
        return iterations;
    }
    
    assignRandom(array){
        var max = 1.0;
        var min = 0.0;
        var roll = 0;
    
        for(var i = 0; i < array.length; i++){
            roll = ((Math.random() * (max - min)) + min);
            array[i] = roll;
        }
    }

    getPredictionsProbabilityTrain(){
        var prob = new Array(xTrain.length);
    
        for(var i = 0; i < xTrain.length; i++) {
            prob[i] = hypothesis(xTrain, beta, i);
        }
        return prob;
    }

    getPredictionsProbabilityTest(){
        var prob = new Array(xTest.length);
    
        for(var i = 0; i < xTest.length; i++) {
            prob[i] = hypothesis(xTest, beta, i);
        }
        return prob;
    }

    predictTrainSet(predictionThreshold) {
        var predicted = new Array (xTrain.length);
        var threshold = predictionThreshold;
    
        for(var i = 0; i < xTrain.length; i++){
            console.log(hypothesis(xTrain, beta, i));
    
            if(hypothesis(xTrain, beta, i) > threshold) {
                predicted[i] = 1.0;
            } else {
                predicted[i] = 0.0;
            }
    
        }
    
        return predicted;
    }

    predictTestSet(predictionThreshold) {
        var predicted = new Array(xTest.length);
        var threshold = predictionThreshold;
    
        for(var i = 0; i < xTest.length; i++){
            console.log(hypothesis(xTest, beta, i));
    
            if(hypothesis(xTest, beta, i) > threshold) {
                predicted[i] = 1.0;
            } else {
                predicted[i] = 0.0;
            }
    
        }
    
        return predicted;
    }
}