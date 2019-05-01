# Datasets

Raw data here. The datasets were gathered from the [UCI Machine Learning Repository](http://archive.ics.uci.edu/ml/index.php). The ones in this folder are from this website, 
with the exception of marks.csv. We got this dataset from a machine learning workshop by ConcordAI on 29/03/2019.

## Changes made
The [wine](http://archive.ics.uci.edu/ml/datasets/Wine) data originally had three types of wine to classify, but we modified it
so that we could do a binary logistic regression.

For the [breast cancer](https://archive.ics.uci.edu/ml/datasets/breast+cancer+wisconsin+(original)) data, we had to remove some data points 
that had missing values in them (indicated with a "?" symbol).

The modified datasets can be found [here](/src/dataset/).
