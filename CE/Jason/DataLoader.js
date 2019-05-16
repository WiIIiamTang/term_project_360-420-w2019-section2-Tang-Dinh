class Dataloader {
    _allX;
    _allY;
    _xTrain;
    _yTrain;
    _xTest;
    _xTrain;

    get FullXArray(){
        return this._allX;
    }

    get FullYArray() {
        return this._allY;
    }

    get XTrainArray() {
        return this._xTrain;
    }

    get YTrainArray() {
        return this._yTrain;
    }

    get XTestArray() {
        return this._xTest;
    }

    get YTestArray() {
        return this._yTest;
    }

    printallX() {
        for(var i = 0; i < allX.length;i++) {
            for(var j = 0; j < allX[i].length; j++){
                console.log(allX[i][j] + " ");
            }
        }
    }

    printallY() {
        for(var i = 0; i < allY.length;i++) {
            for(var j = 0; j < allY[i].length; j++){
                console.log(allY[i][j] + " ");
            }
        }
    }

    printXTrain() {
        for(var i = 0; i < xTrain.length;i++) {
            for(var j = 0; j < xTrain[i].length; j++){
                console.log(xTrain[i][j] + " ");
            }
        }
    }

    printYTrain() {
        for(var i = 0; i < yTrain.length;i++) {
            for(var j = 0; j < yTrain[i].length; j++){
                console.log(yTrain[i][j] + " ");
            }
        }
    }

    printXTest() {
        for(var i = 0; i < xTest.length;i++) {
            for(var j = 0; j < xTest[i].length; j++){
                console.log(xTest[i][j] + " ");
            }
        }
    }

    printYTest() {
        for(var i = 0; i < yTest.length;i++) {
            for(var j = 0; j < yTest[i].length; j++){
                console.log(yTest[i][j] + " ");
            }
        }
    }

    setAllX(x){
        this._allX = x;
    }

    setAllY(y){
        this._allY = y;
    }

    setTrainX(x){
        xTrain = x;
    }

    setTrainY(y){
        yTrain = y;
    }

    setTestX(x){
        xTest = x;
    }

    setTestY(y){
        yTest = y;
    }

    //returnNumDataPoints(location)?

    //makeArrays()

    //returnRowandCol()

    makeYLabels(checkIfThis, assignThis, assignThisOtherwise){
        for(var i = 0; i < allY.length; i++){
            if (allY[i] != checkIfThis) {
                allY[i] = assignThisOtherwise;
            } else {
                allY[i] = assignThis;
            }
        }
    }

    shuffleData(){
        var max = (allX.length - 1)
        var min = 0;
        var randRange = (max - min) + 1;
        var roll = 0;
        var temp = [];
        var tempY = [];
    
        for(var i = 0; i< allX.length; i++){
            roll = Math.round((Math.random() * randRange) + min);
            tempY[i] = allY[i];
            allY[i] = allY[roll];
            allY[roll] = tempY[i];

            for(var j = 0; j < allX[0].length; j++){
                temp[i][j] = allX[i][j];
                allX[i][j] = allX[roll][j];
                allX[roll][j] = temp[i][j];
            }
        }
    }

    trainTestSplit(trainingSplitPercent){
        var splitIndex = Math.round(allX.length * trainingSplitPercent);
        xTrain;// = new double(splitIndex)[allX[0].length];
        xTest;// = new double [allX.length - splitIndex][allX[0].length];
        yTrain = new double(splitIndex);
        yTest = new double (allY.length - splitIndex);
        var countrow = 0;

        for (var i = 0; i < splitIndex; i++) {
            for(var j = 0; j < xTrain[0].length; j++) {
                xTrain[i][j] = allX[i][j];
            }
        }

        for (var i = splitIndex; i < allX.length; i++) {
            for(var j = 0; j < xTest[0].length; j++) {
                xTest[countrow][j] = allX[i][j];
            }
            countrow++;
        }

        for (var i = 0; i < splitIndex; i++) {
            yTrain[i] = allY[i];
        }

        countrow = 0;
        for (var i = splitIndex; i < allY.length; i++) {
            yTest[countrow] = allY[i];
            countrow++;
        }
    }
}