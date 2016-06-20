package main;

import models.SolutionSES;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.List;

public class SES {
    private List<String[]> data;
    private List<Double> smoothedValues;
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

    public SES(List<String[]> data){
        this.data = data;
    }

     public void createOriginal(){
        for(int i = 0; i < data.size(); i++) {
            dataSet.setValue(Double.parseDouble(data.get(i)[1]), "Original", data.get(i)[0]);
        }
    }

    public void smooth(double coefficient){
        smoothedValues = new ArrayList<>();

        double total = 0.0;
        int i;
        for(i=0; i<12; i++){
            total += Double.parseDouble(data.get(i)[1]);
        }

        double average = total/12;

        // coefficient = a
        for(i = 1; i<data.size(); i++){
            if(smoothedValues.isEmpty()){
                smoothedValues.add(i-1, average);
            }
            double value = (coefficient * Double.parseDouble(data.get(i-1)[1]))
                    + (1-coefficient) * smoothedValues.get(i-1);

            smoothedValues.add(i, value);
        }

        for(i = 0; i<data.size(); i++){
            dataSet.setValue(smoothedValues.get(i), "Smoothed", data.get(i)[0]);
        }
    }

    public SolutionSES smoothWithBestCoefficient(){
        SolutionSES solution = calculateBestSolution();
        for(int i = 0; i<data.size(); i++){
            dataSet.setValue(solution.getSmoothedValues().get(i), "Smoothed", data.get(i)[0]);
        }
        return solution;
    }

    public double squaredError(){
        double error = 0.0;
        for(int i= 0;i < data.size(); i++){
            error += Math.pow(smoothedValues.get(i) - Double.parseDouble(data.get(i)[1]), 2);
        }

        return Math.sqrt(error/(data.size()-1));
    }

    public SolutionSES calculateBestSolution(){
        SolutionSES bestSolution = null;
        for(double i = 0.0; i<=1.0; i+=0.1){
            smooth(i);
            double squaredError = squaredError();
            if(bestSolution==null || bestSolution.getSquaredError()>squaredError){
                bestSolution = new SolutionSES(i, squaredError, smoothedValues);
            }
        }
        return bestSolution;
    }

    public void draw(){
        JFreeChart chart = ChartFactory.createLineChart("Smoothing", "Months", "Demand", dataSet);
        ChartFrame frame = new ChartFrame("Line Chart", chart);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
