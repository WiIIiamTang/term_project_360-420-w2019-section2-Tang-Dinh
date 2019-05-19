# Intro to Computer Programming CE

## Logistic Regression in JavaScript

#### Original Java code by William Tang and Jason Dinh.
#### Translated to JavaScript by Jason Dinh.

#

For my CE, I translate my term project code from Java to JavaScript.
The theory and numerical methods are the same as our term project.

The difference between our term project and my CE is I use functions in my CE and we use classes in our term project code.

The main file is Classification.js, running the file will call functions from the 'Methods' folder.

I run and test my code by using Node.js

The datasets used can be found in folder /term_project_360-420-w2019-section2-Tang-Dinh/CE/Jason/dataset/

Here is the result of one of my run test:

The parameters used in this run were:

- Dataset file: wine1vs2.csv
- Step size = 0.1
- Number of iterations = 5000

The run had a 97.44% accuracy.

<img src="https://github.com/WiIIiamTang/term_project_360-420-w2019-section2-Tang-Dinh/blob/master/CE/Jason/results/result-accuracy.PNG" />

The R-Squared value for the fitted line was 0.9402706
and here is the confusion matrix, r-square values, and the top 5 featured variable weights.

<img src="https://github.com/WiIIiamTang/term_project_360-420-w2019-section2-Tang-Dinh/blob/master/CE/Jason/results/result-evaluation.PNG" />
