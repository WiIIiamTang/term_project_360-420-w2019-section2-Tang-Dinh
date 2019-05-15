# Report: Error Analysis
## Intro to Programming 360-420: Section 02
## William Tang and Jason Dinh

## Distributions of Model Accuracy, k = 3, over 1000 iterations

| Model      | Accuracy (%)  | Standard Deviation |
| ---------- | ------------- | ------------------ |
| kNN        | 96.52854      | 1.30806            |
| Baseline   | 65.23073      | 8.09477E-4         |

### Baseline comparison
  A sensible baseline to compare our model's performance to is just to count the most frequent class in the set, and use that as a prediction every single time. Also called the [zero rule algorithm](https://machinelearningcatalogue.com/algorithm/alg_zero-rule.html), this baseline is a better than just going 50/50 every time for a prediction. For example, if 60% of the points in the set are of one class, the zero rule algorithm supposes that every other point in the set has that class label as well.
  
  As the results show, the baseline accuracy is actually around 65%. The kNN classifier got an average accuracy of around 96.5% with a standard deviation of 1.31. This is a significant increase in accuracy from the baseline.
  
## Analysis of different error types
  A **false positive** is when the classifier predicts a positive value when the actual answer was false. A **false negative** is when the classifier predicts a negative value when the actual answer was true.

### Another trial looking at model error types, k = 3, over 1000 iterations
  
| Error Type | Score    (%)  | Standard Deviation |
| ---------- | ------------- | ------------------ |
| Recall     | 96.8955       | 1.83488E-4         |
| Precision  | 97.6475       | 1.49401E-4         |

### Baseline comparison

For a baseline comparison, we will consider a random classifier in this case. Suppose that a random classifier on a binary classification will output the postive class with a probability *p*. Let *TP* and *FP* denote the number of true postives and false postives respectively. Then the precision would be

<img src="https://latex.codecogs.com/gif.latex?precision&space;=&space;\frac{TP}{TP&plus;FP}&space;=&space;\frac{p\cdot&space;P}{p\cdot&space;P&space;&plus;&space;p\cdot&space;N}&space;=&space;\frac{P}{P&plus;N}" title="precision = \frac{TP}{TP+FP} = \frac{p\cdot P}{p\cdot P + p\cdot N} = \frac{P}{P+N}" />

where capital P is the number of postive points, and capital N is the number of negative points.

Now let *FN* be the number of false negatives. The recall is

<img src="https://latex.codecogs.com/gif.latex?recall&space;=&space;\frac{TP}{TP&plus;FN}&space;=&space;\frac{p\cdot&space;P}{p\cdot&space;P&space;&plus;&space;(1-p)\cdot&space;P}&space;=&space;\frac{p\cdot&space;P}{P}=p" title="recall = \frac{TP}{TP+FN} = \frac{p\cdot P}{p\cdot P + (1-p)\cdot P} = \frac{p\cdot P}{P}=p" />

And so the recall should be constant *p* for a random classifier. **In a precision-recall graph**, this would be represented as a **horizontal line** with *y* as *P/(P+N)* and serves to estimate a good/bad performance of the model.

## The k parameter

As we said, we can build a precision-recall graph to look at how the k hyperparameter affects our results. First we make a table gathering the data of **recall** and **precision** average scores (over 1000 times) for a given k:

| k          | Recall        | Precision          |
| ---------- | ------------- | ------------------ |
| 1          | 0.97901       | 0.98290            |
| 3          | 0.96931       | 0.97684            |
| 5          | 0.96592       | 0.97292            |
| 7          | 0.96711       | 0.96941            |
| 9          | 0.96666       | 0.96670            |
| 11         | 0.96778       | 0.96463            |
| 15         | 0.96898       | 0.96181            |
| 21         | 0.97257       | 0.95364            |
| 35         | 0.98192       | 0.93445            |
| 51         | 0.98942       | 0.89244            |

Then graph it:


<img src="https://docs.google.com/spreadsheets/d/e/2PACX-1vTb1e3_07DDH_Cl5FtOUwRuyU-fBdoozHl5d0939yAvUuprk8EsEAFHFy5IB6ImqXY51znd6EQ8wTRD/pubchart?oid=1234554255&amp;format=image">

It's expected that there is always a trade-off between recall and precision. So to choose the hyperparameter, sometimes it really depends on the case you're dealing with. For rare diseases and cancer and stuff, you would really want to avoid false negatives! So in this case, a higher recall is preferred.

