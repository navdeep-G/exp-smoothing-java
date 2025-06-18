# Forecast Models in Java: Exponential Smoothing & Moving Averages

## Overview

This repository provides robust Java implementations of key time series forecasting methods, including **Exponential Smoothing** and **Moving Average** models. These techniques are widely used across finance, economics, operations, and other domains to model trends and forecast future values by smoothing time series data.

---

## 🚀 Features

- Core time series forecasting algorithms  
- Preprocessing utilities for transformation and statistical analysis  
- Tools for stationarity testing and data smoothing

---

## 🧠 Core Components

### 📊 Statistical Utilities

- **[Stats.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/util/Stats.java)**  
  Utility methods for statistical calculations essential to time series preprocessing and model evaluation.

---

### 🔄 Data Transformation

- **[BoxCox.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/transform/BoxCox.java)**
  Implements the Box-Cox power transformation to stabilize variance and approximate normality.

- **[Transform.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/transform/Transform.java)** 
  Collection of preprocessing transformations for time series data.

---

### 📈 Moving Average Models

- **[SimpleMovingAverage.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/SimpleMovingAverage.java)** 
  Calculates the Simple Moving Average (SMA) across a fixed window.

- **[CumulativeMovingAverage.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/CumulativeMovingAverage.java)**
  Computes the Cumulative Moving Average (CMA), updating progressively as new data arrives.

- **[ExponentialMovingAverage.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/movingaverage/ExponentialMovingAverage.java)**
  Implements the Exponential Moving Average (EMA), weighting recent observations more heavily.

---

### 🧪 Stationarity Testing

- **[AugmentedDickeyFuller.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/tests/AugmentedDickeyFuller.java)**
  Implements the Augmented Dickey-Fuller (ADF) test to assess stationarity—key for validating forecasting assumptions.

---

### 🔁 Exponential Smoothing Models

- **[SingleExponentialSmoothing.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/algos/expsmoothing/SingleExpSmoothing.java)**  
  Suitable for series without trend or seasonality.

- **[DoubleExponentialSmoothing.java](https://github.com/navdeep-G/timeseries-java/blob/master/src/main/java/algos/expsmoothing/DoubleExpSmoothing.java)**  
  Captures level and trend with two smoothing parameters.

- **[TripleExponentialSmoothing.java](https://github.com/navdeep-G/timeseries-java/tree/master/src/main/java/algos/expsmoothing/TripleExpSmoothing.java)** 
  Implements the Holt-Winters method to model level, trend, and seasonality.

---

## 🤝 Contributing

Found a bug or want to contribute improvements? Submit an issue or open a pull request!
