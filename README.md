# Time Series (Exponential Smoothing/Moving Averages)

An implementation of exponential smoothing and moving averages in Java and [H2O](https://github.com/h2oai/h2o-3)

## In Java:
### Statistical Calculations:
- [Stats.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/Stats.java)

### Transformation Algorithms:
- [BoxCox.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/transform/BoxCox.java)
- [Transform.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/transform/Transform.java)

### Moving Average Algorithms:
- [SimpleMovingAverage.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/SimpleMovingAverage.java)
- [CumulativeMovingAverage.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/CumulativeMovingAverage.java)
- [ExponentialMovingAverage.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/ExponentialMovingAverage.java)

### Stationarity Tests:
- [AugmentedDickeyFuller.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/tests/AugmentedDickeyFuller.java) 

### Forecasting Algorithms:
- [TripleExponentialSmoothing.java](https://github.com/navdeep-G/timeseries-java/tree/master/src/main/java/algos/expsmoothing/TripleExpSmoothing.java)
- [DoubleExponentialSmoothing.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/algos/expsmoothing/DoubleExpSmoothing.java)
- [SingleExponentialSmoothing.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/algos/expsmoothing/SingleExpSmoothing.java)

## In H2O:
### Statistical Calculations:
- [StatsFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/frame/StatsFrame.java)

### Transformations: 
- [TransformFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/transform/frame/TransformFuncsFrame.java)
- [BoxCoxFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/transform/frame/BoxCoxFrame.java)

### Moving Averages:
- [SimpleMovingAverageFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/frame/SimpleMovingAverageFrame.java) 
- [CumulativeMovingAverageFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/frame/CumulativeMovingAverageFrame.java)
- [ExponentialMovingAverageFrame .java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/frame/ExponentialMovingAverageFrame.java)

### Forecasting Algorithms:
- [SingleExpSmoothingFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/algos/frame/SingleExpSmoothingFrame.java)
- [DoubleExpSmoothingFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/algos/frame/DoubleExpSmoothingFrame.java)
- [TripleExpSmoothingFrame.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/algos/frame/TripleExpSmoothingFrame.java)
