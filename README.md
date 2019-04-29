# 360-420 Intro to Programming


**Term Project: Logistic Regression in Wine Classification**

William Tang and Jason Dinh


Section: 02

Professors: Jean-François Brière, Jonathan Sumner, Sameer Bhatnagar


## Changelog

### 26-04-2019
- first "working" version LogisticWine1 + LogisticCancer
- prelim report

### 29-04-2019
- gradient method: fixed an issue in the partial derivative where the total sum so far would be multiplied by the value of the weight instead of multiplying one specific instance of the difference between the sigmoid and label by the value of the weight. Partial derivative now calculates correctly.
- added a regularization parameter to the gradient and cost function (still in testing)
- Changed readme info in scr folder: added "Validation" section for breast cancer + school marks; removed "results" section, logwine2 and logwine3 for now
- added python implementation of logistic regression
- added marks.csv in resources
