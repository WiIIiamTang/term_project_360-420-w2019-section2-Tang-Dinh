var beta = new Array(14);

module.exports = {
    fit: function(xTrain, yTrain, learningRate, maxIterations, randomize){
        var alpha = learningRate;
        var betaNew = new Array(beta.length);
        var iterations = 0;
        
        if(randomize === true){
            assignRandom(beta);
        } else {
            for(var i = 0; i < beta.length; i++){
                beta[i] = 0;
            }
        }
    
        while (iterations < maxIterations){
    
            for(var i = 0; i < beta.length; i++){
                betaNew[i] = beta[i] - (alpha * gradient(xTrain, yTrain, beta, i));
            }
    
            for(var i = 0; i < beta.length;i++) {
                beta[i] = betaNew[i];
            }
    
            if(iterations % 500 === 0) {
                console.log("At " + iterations + " iterations," + " cost = " + costFunction(xTrain, yTrain, beta));
                //console.log(gradient(xTrain, yTrain, beta, 0));
            }
            iterations++;
    
        }
        console.log("The loop was terminated. Total iterations: " + iterations);
    },
    
    predictTrainSet: function(xTrain, predictionThreshold) {
        var predicted = new Array (xTrain.length);
        var threshold = predictionThreshold;
    
        for(var i = 0; i < predicted.length; i++){
    
            if(hypothesis(xTrain, beta, i) > threshold) {
                predicted[i] = 1.0;
            } else {
                predicted[i] = 0.0;
            }
    
        }
    
        return predicted;
    },
    
    predictTestSet: function(xTest, predictionThreshold) {
        var predicted = new Array(xTest.length);
        var threshold = predictionThreshold;
    
        for(var i = 0; i < xTest.length; i++){
    
            if(hypothesis(xTest, beta, i) > threshold) {
                predicted[i] = 1.0;
            } else {
                predicted[i] = 0.0;
            }
    
        }
    
        return predicted;
    },
}

function linearSum(xTrain, beta, row) {
    var sum = 0.;

    sum += beta[0];

    for (var i = 1; i < beta.length; i++) {
        sum += (beta[i] * xTrain[row][i-1]);
    }

    return sum;
}

function hypothesis(xTrain, beta, row) {
    var sigmoid = 0.;
    sigmoid = 1 / (1 + Math.pow(Math.E, -linearSum(xTrain, beta, row)));
    return sigmoid;
}

function costFunction(xTrain, yTrain, beta) {
    var m = xTrain.length;
    var cost = 0.;
    var sum = 0.;

    for(var i = 0; i < m; i++) {
        if(yTrain[i] === 1.0){
            sum += Math.log(hypothesis(xTrain, beta, i));
        } else {
            sum += Math.log(1 - hypothesis(xTrain, beta, i));
        }
    }

    cost = -(sum/m);

    return cost;
}

function gradient(xTrain, yTrain, beta, betaNum) {
    var sum = 0.;
    var toAdd = 0.;
    var m = xTrain.length;

    for(var i = 0; i < m; i++) {
        toAdd += (hypothesis(xTrain, beta, i) - yTrain[i]);

        if(betaNum != 0) {
            toAdd *= xTrain[i][betaNum-1];
        }

        sum += toAdd;
        toAdd = 0;
    }

    sum /= m;

    return sum;
}

function assignRandom(array){
    for(var i = 0; i < array.length; i++){
        array[i] = Math.random();
    }
}

function getPredictionsProbabilityTrain(xTrain){
    var prob = new Array(xTrain.length);

    for(var i = 0; i < xTrain.length; i++) {
        prob[i] = hypothesis(xTrain, beta, i);
    }
    return prob;
}

function getPredictionsProbabilityTest(xTest){
    var prob = new Array(xTest.length);

    for(var i = 0; i < xTest.length; i++) {
        prob[i] = hypothesis(xTest, beta, i);
    }
    return prob;
}