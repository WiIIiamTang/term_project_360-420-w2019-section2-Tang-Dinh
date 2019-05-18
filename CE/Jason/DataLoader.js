module.exports = {
	printXArray: function(array) {
		for(var i = 0; i < array.length;i++) {
			for(var j = 0; j < array[i].length; j++){
				console.log(array[i][j]);
			}
		}
	},

	printYArray: function(array) {
		for(var i = 0; i < array.length;i++) {
			console.log(array[i]);
		}
	},

	makeXArrays: function(fileLocation){
		var inputArray;
		var inputRow;
		var allX;

		var fs = require("fs");
		var text = fs.readFileSync(fileLocation, "utf-8");
		var textByLine = text.split("\n");      //textByLine is an array with containing all the data from the .csv file

		allX = [];                              //set 1st dimension array of allX
		
		for(var i = 0; i < textByLine.length-1; i++){
			inputRow = textByLine[i];           //inputRow is each row of the .csv file
			inputArray = inputRow.split(",")    //inputArray is an array with each column as a variable
			
			allX[i] = [];                       //set 2nd dimension array of allX

			for(var j = 0; j < (inputArray.length - 1); j++){
				allX[i][j] = parseFloat(inputArray[j]);                 //copy and convert to float
			}
			
		}
		return allX;
	},

	makeYArrays: function(fileLocation){
		var inputArray;
		var inputRow;
		var allY;

		var fs = require("fs");
		//var text = fs.readFileSync("./wine1vs2.txt", "utf-8");
		var text = fs.readFileSync(fileLocation, "utf-8");
		var textByLine = text.split("\n");      //textByLine is an array with containing all the data from the .csv file
		
		allY = [];                              //set allY as array
		
		for(var i = 0; i < textByLine.length-1; i++){
			inputRow = textByLine[i];           //inputRow is each row of the .csv file
			inputArray = inputRow.split(",")    //inputArray is an array with each column as a variable
			
			allY[i] = parseFloat(inputArray[inputArray.length-1]);      //copy and convert to float
		}
		return allY;
	},

	makeYLabels: function(allY, checkIfThis, assignThis, assignThisOtherwise){
		for(var i = 0; i < allY.length; i++){
			if (allY[i] != checkIfThis) {
				allY[i] = assignThisOtherwise;
			} else {
				allY[i] = assignThis;
			}
		}
		return allY;
	},

	shuffleData: function(allX, allY){
		var max = (allX.length - 1)
		var min = 0;
		var randRange = (max - min) + 1;
		var roll = 0;
		var temp = [];
		var tempY = [];

		for(var i = 0; i< allX.length; i++){
			roll = Math.round((Math.random() * randRange) + min);
			temp[i] = [];
			allX[i] = [];
			allX[roll] = [];
			tempY[i] = allY[i];
			allY[i] = allY[roll];
			allY[roll] = tempY[i];

			for(var j = 0; j < allX[0].length; j++){
				temp[i][j] = allX[i][j];
				allX[i][j] = allX[roll][j];
				allX[roll][j] = temp[i][j];
			}
		}
	},

	splitXTrain: function(allX, trainingSplitPercent){
		var splitIndex = Math.round(allX.length * trainingSplitPercent);
		var xTrain = new Array(splitIndex);

		for (var i = 0; i < splitIndex; i++) {
			xTrain[i] = new Array(allX[0].length);

			for(var j = 0; j < xTrain[0].length; j++) {
				xTrain[i][j] = allX[i][j];
			}
		}
		return xTrain;
	},

	splitXTest: function(allX, trainingSplitPercent){
		var splitIndex = Math.round(allX.length * trainingSplitPercent);
		var xTest = new Array (allX.length - splitIndex)
		var countrow = 0;

		for (var i = splitIndex; i < allX.length; i++) {
			xTest[countrow] = new Array(allX[0].length);
			for(var j = 0; j < xTest[countrow].length; j++) {
				xTest[countrow][j] = allX[i][j];
			}
			countrow++;
		}
		
		return xTest;
	},

	splitYTrain: function(allY, trainingSplitPercent){
		var splitIndex = Math.round(allY.length * trainingSplitPercent);
		var yTrain = new Array(splitIndex);
		
		for (var i = 0; i < splitIndex; i++) {
			yTrain[i] = allY[i];
		}
		return yTrain;
	},

	splitYTest: function(allY, trainingSplitPercent){
		var splitIndex = Math.round(allY.length * trainingSplitPercent);
		var yTest = new Array (allY.length - splitIndex);
		var countrow = 0;
		
		for (var i = splitIndex; i < allY.length; i++) {
			yTest[countrow] = allY[i];
			countrow++;
		}
		
		return yTest;
	},
}