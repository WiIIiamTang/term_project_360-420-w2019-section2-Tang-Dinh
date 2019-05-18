var beta = new Array(14);

module.exports = {
    fit: function(xTrain, yTrain, learningRate, maxIterations, randomize){
        var alpha = learningRate;
        var betaNew = new Array(beta.length);
        var iterations = 0;
    
        if(randomize === true){
            assignRandom(beta);
        }
    
        while (iterations < maxIterations){
    
            for(var i = 0; i < beta.length; i++){
                betaNew[i] = beta[i] - (alpha * gradient(xTrain, yTrain, beta, i));
            }
    
            for(var i = 0; i < beta.length;i++) {
                beta[i] = betaNew[i];
            }
    
            if(iterations % 1000 === 0) {
                console.log("Cost at " + costFunction(xTrain, yTrain, beta));
                console.log(gradient(xTrain, yTrain, beta, 0));
            }
            iterations++;
    
        }
        console.log("The loop was terminated. Total iterations: " + iterations);
    },
    
    predictTrainSet: function(xTrain, predictionThreshold) {
        var predicted = new Array (xTrain.length);
        var threshold = predictionThreshold;
    
        for(var i = 0; i < predicted.length; i++){
            console.log(hypothesis(xTrain, beta, i));
    
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
            console.log(hypothesis(xTest, beta, i));
    
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

    sum = sum + beta[0];

    for (var i = 1; i < beta.length; i++) {
        sum = sum + (beta[i] * xTrain[row][i-1]);
    }

    return sum;
}

function hypothesis(xTrain, beta, row) {
    var sigmoid = 0.;
    sigmoid = 1/1+(Math.pow(Math.E,-linearSum(xTrain,beta,row)));
    return sigmoid;
}

function costFunction(xTrain, yTrain, beta) {
    var m = xTrain.length;
    var cost = 0.;
    var sum = 0.;
    var n = beta.length;

    for(var i = 0; i < m; i++) {
        if(yTrain[i] === 1.0){
            sum = sum + (Math.log(hypothesis(xTrain, beta, i)));
        } else {
            sum = sum + Math.log(1 - hypothesis(xTrain, beta, i));
        }
    }
        
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

/*
function fit(xTrain, yTrain, learningRate, maxIterations, randomize){
    var alpha = learningRate;
    var betaNew = new Array(beta.length);
    var iterations = 0;

    if(randomize === true){
        assignRandom(beta);
    }

    while (iterations < maxIterations){

        for(var i = 0; i < beta.length; i++){
            betaNew[i] = beta[i] - (alpha * gradient(xTrain, yTrain, beta, i));
        }

        for(var i = 0; i < beta.length;i++) {
            beta[i] = betaNew[i];
        }

        if(iterations % 1000 === 0) {
            console.log("Cost at " + costFunction(xTrain, yTrain, beta));
            console.log(gradient(xTrain, yTrain, beta, 0));
        }
        iterations++;

    }
    console.log("The loop was terminated. Total iterations: " + iterations);
}
*/

function assignRandom(array){
    var max = 1.0;
    var min = 0.0;
    var roll = 0;

    for(var i = 0; i < array.length; i++){
        roll = ((Math.random() * (max - min)) + min);
        array[i] = roll;
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

/*
function predictTrainSet(xTrain, predictionThreshold) {
    var predicted = new Array (xTrain.length);
    var threshold = predictionThreshold;

    for(var i = 0; i < predicted.length; i++){
        console.log(hypothesis(xTrain, beta, i));

        if(hypothesis(xTrain, beta, i) > threshold) {
            predicted[i] = 1.0;
        } else {
            predicted[i] = 0.0;
        }

    }

    return predicted;
}

function predictTestSet(xTest, predictionThreshold) {
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
*/