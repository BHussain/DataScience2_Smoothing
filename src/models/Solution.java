package models;

import java.util.List;

public class Solution {
    double coefficient;
    double squaredError;
    List<Double> smoothedValues;

    public Solution(double coefficient, double squaredError, List<Double> smoothedValues){
        this.coefficient = coefficient;
        this.squaredError = squaredError;
        this.smoothedValues = smoothedValues;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }


    public double getSquaredError() {
        return squaredError;
    }

    public void setSquaredError(double squaredError) {
        this.squaredError = squaredError;
    }

    public List<Double> getSmoothedValues() {
        return smoothedValues;
    }

    public void setSmoothedValues(List<Double> smoothedValues) {
        this.smoothedValues = smoothedValues;
    }
}
