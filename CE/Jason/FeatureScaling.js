module.exports = {
    standardScaler: function(xTrainArray, xTestArray){
        var zScore1 = 0;
        var zScore2 = 0;
        var mean = 0;
        var sd = 0;
    
        for (var col = 0; col < xTrainArray[0].length; col++) {
            mean = computeMean(xTrainArray, col);
            sd = standardDeviation(xTrainArray, col);
    
                for (var i = 0; i < xTrainArray.length; i++) {
                    zScore1 = ((xTrainArray[i][col] - mean ) / sd);
                    xTrainArray[i][col] = zScore1;
                }
    
            for (var i = 0; i < xTestArray.length; i++) {
                zScore2 = ((xTestArray[i][col] - mean) / sd);
                xTestArray[i][col] = zScore2;
            }
        }
    },
}

function computeMean(xArray, col){
    var sum = 0;

    for(var i = 0; i < xArray.length; i++){
        sum += xArray[i][col];
    }

    return sum/xArray.length;
}

function standardDeviation(xArray, col){
    var meanDiff = 0;
    var sumMeanDiff = 0;
    var sd = 0;
    var mean = computeMean(xArray, col);

    for (var i = 0; i < xArray.length; i++){
        meanDiff = Math.pow((xArray[i][col] - mean),2);

        sumMeanDiff = sumMeanDiff + meanDiff;
    }

    sd = Math.sqrt(sumMeanDiff / (xArray.length));

    return sd;
}

/*
function standardScaler(xTrainArray, xTestArray){
    var zScore1 = 0;
    var zScore2 = 0;
    var mean = 0;
    var sd = 0;

    for (var col = 0; col < xTrainArray[0].length; col++) {
        mean = computeMean(xTrainArray, col);
        sd = standardDeviation(xTrainArray, col);

            for (var i = 0; i < xTrainArray.length; i++) {
                zScore1 = ((xTrainArray[i][col] - mean ) / sd);
                xTrainArray[i][col] = zScore1;
            }

        for (var i = 0; i < xTestArray.length; i++) {
            zScore2 = ((xTestArray[i][col] - mean) / sd);
            xTestArray[i][col] = zScore2;
        }
    }
}
*/

function max(x, col){
    var max = x[0][col];

    for(var i = 0; i < x.length; i++) {
        if(x[i][col] > max) {
            max = x[i][col];
        }
    }

    return max;
}

function min(x, col){
    var min = x[0][col];

    for(var i = 0; i < x.length; i++) {
        if(x[i][col] < min) {
            min = x[i][col];
        }
    }

    return min;
}

function minMaxScaler(xTrainArray, xTestArray){
    var max = 0;
    var min = 0;

    for(var col = 0; col < xTrainArray[0].length; col++) {
        max = max(xTrainArray,col);
        min = min(xTrainArray,col);

        for(var i = 0; i < xTrainArray.length; i++){
            xTrainArray[i][col] = (xTrainArray[i][col] - min) / (max-min);
        }
    }

    for(var col = 0; col < xTestArray[0].length; col++) {
        max = max(xTrainArray,col);
        min = min(xTrainArray,col);

        for(var i = 0; i < xTestArray.length; i++) {
            xTestArray[i][col] = (xTestArray[i][col] - min) / (max-min);
        }
    }
}