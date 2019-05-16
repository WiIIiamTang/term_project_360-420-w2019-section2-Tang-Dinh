//class ModelEvaluator{
    function getAccuracy(labels, predictedLabels){
        var good = 0;
        var total = 0;
        var accuracy = 0;
    
        for(var i = 0; i < predictedLabels.length; i++){
            if (labels[i] === predictedLabels[i]){
                good++;
                total++;
            } else {
                total++;
            }
        }
        accuracy = (good / total) * 100;
        return accuracy;
    }

    function confusionMatrix(labels, predictedLabels){
        var truePos = 0;    //true positives
        var trueNeg = 0;    //true negatives
        var falPos = 0;     //false positives
        var falNeg = 0;     //false negatives
        var actual1 = 0;
        var actual0 = 0;
        var predicted1= 0;
    
        for(var i = 0; i < predictedLabels.length; i++){
            //true positive
            if(labels[i] === predictedLabels[i] && labels[i] === 1){
                truePos++;
                actual1++;
                predicted1++;
            //true negative
            } else if (labels[i] === predictedLabels[i] && labels === 0){
                trueNeg++;
                actual0++;
            //false positive
            } else if (labels[i] === 0 && predictedLabels[i] === 1){
                falPos++;
                actual0++;
                predicted1++;
            //false negative
            } else if (labels[i] === 1 && predictedLabels[i] === 0){
                falNeg++;
                actual1++;
            }
            console.log("*****Confusion Matrix*****");
            console.log("\tPredicted:0\tPredicted:1");
            console.log("Actual:0\t" + trueNeg +"\t" + falPos);
            console.log("Actual:1\t" + falNeg +"\t" + truePos);
            console.log("Accuracy =\t\t" + ((truePos + trueNeg) / (truePos + trueNeg + falPos + falNeg)));
            console.log("Error Rate =\t\t" + ((falPos + falNeg) / (truePos + trueNeg + falPos + falNeg)));
            console.log("Sensitivity/Recall =\t" + (truePos / actual1));
            console.log("False Positive Rate =\t" + (falPos / actual0));
            console.log("Specificity =\t\t" + (trueNeg / actual0));
            console.log("Precision =\t\t" + (truePos / predicted1));
            console.log("Prevalence =\t\t" + (actual1 / (truePos + trueNeg + falPos + falNeg)));
        }
    }

    function getBaselineAcc(labels){
        var one = 0;
        var zero = 0;
        var mostFreq = 0;
    
        for(var i = 0; i < labels.length; i++){
            if(labels[i] === 1){
                one++;
            } else {
                zero++;
            }
        }
    
        if (one > zero) {
          mostFreq = one;
        } else {
          mostFreq = zero;
        }
    
        return mostFreq/(one+zero);
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

    function logLikelihood(x, y, beta){
        var m = x.length;
        var cost = 0;
        var sum = 0;
        var n = beta.length;
    
        for(var i = 0; i < m; i++) {
            if(y[i] === 1.0){
                sum = sum + (Math.log(hypothesis(x, beta, i)));
            } else {
                sum = sum + Math.log(1 - hypothesis(x, beta, i));
            }
        }
    
        cost = sum/m;
        return cost;
    }

    function mcFaddenRSquared(x, labels, beta){
        var rSquare = 0;
        var ones = 0;
        var zeroes = 0;
        var overallProb = 0;
        var overallBeta = new Array(1);
        var logLikeliOverall = 0;
        var logLikeliModel = 0;
    
        for (var i = 0; i < yLabels.length; i++) {
            if (labels[i] == 1) {
                ones++;
            } else {
                zeroes++;
            }
        }
    
        overallProb = ones/(zeroes+ones);
        overallBeta[0] = overallProb;
    
        logLikeliOverall = logLikelihood(x, yLabels, overallBeta);
        logLikeliModel = logLikelihood(x, yLabels, beta);
    
        rSquare = (logLikeliOverall - logLikeliModel) / logLikeliOverall;
    
        return rSquare;
    }
    
    function rankWeights(array){
        var temp = 0;
        var max = 0.;
        var maxIndex = 0;
        var count = 0;
        var original = new Array(array.length);
    
        for (var i = 0; i < original.length; i++)
        {
          original[i] = array[i];
        }
    
        console.log("Weight Ranking\nCoefficient\t\tFeatureNumber");
    
    
            for (var i = 0; i < array.length; i++) {
                max = array[count];
    
                for (var j = count; j < array.length; j++) {
                    if (Math.abs(array[j]) >= Math.abs(max)) {
                        max = array[j];
                        maxIndex = j;
                    }
                }
                //console.log(max + "\t" + maxIndex);
                temp = array[count];
                array[count] = array[maxIndex];
                array[maxIndex] = temp;
    
                count++;
        }
    
        for(var i = 0; i < array.length; i++) {
          console.log(array[i] + "\t" + findOccurence(original, array[i]));
        }
    }
    
    function findOccurence(array, num){
        for(var i = 0; i < array.length; i++){
            if (array[i] === num){
                return i;
            }
        }
        return -1;
    }
//}