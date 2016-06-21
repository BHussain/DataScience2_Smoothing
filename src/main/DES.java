package main;

import com.sun.javafx.css.CalculatedValue;

import models.RowDES;
import models.SolutionDES;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.List;

public class DES {
    private List<String[]> data;
    private List<RowDES> orderedData;
    private DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
    
    private SolutionDES bestSolution;
    
    private double smoothingCoefficient;
    private double trendCoefficient;
    
    public DES(List<String[]> data){
        this.data = data;
        this.smoothingCoefficient = 0.0;
        this.trendCoefficient = 0.0;
    }
    
    public void prepareDataSet(){
    	orderedData = new ArrayList<>();
    	for(String[] d:data){
    		RowDES row = new RowDES();
    		row.setMonth(Integer.parseInt(d[0]));
    		row.setOriginal(Double.parseDouble(d[1]));
    		row.setSmoothed(0.0);
    		row.setTrend(0.0);
    		row.setForecast(0.0);
    		orderedData.add(row);
    	}
    }
    
    public void initialize(){
    	orderedData.get(1).setSmoothed(orderedData.get(1).getOriginal());
    	orderedData.get(1).setTrend(orderedData.get(1).getOriginal()-orderedData.get(0).getOriginal());
    }
    
    /**
     * 
     * Calculated the smoothed value.
     * st = factor*currentOriginalValue + (1-factor)*(previousSmoothedValue+previousTrend)
	 *
     * @return
     */
    public double calculateSmoothed(int index){
    	double originalValue = orderedData.get(index).getOriginal();
    	double smoothedValue = orderedData.get(index-1).getSmoothed();
    	double trendValue = orderedData.get(index-1).getTrend();
    	
    	double value = (smoothingCoefficient * originalValue)
                + (1-smoothingCoefficient) * (smoothedValue+trendValue);
    	return value;
    }

	public void createOriginal(){
		for(int i = 0; i < data.size(); i++) {
			dataSet.setValue(Double.parseDouble(data.get(i)[1]), "Original", data.get(i)[0]);
		}
	}
    
    public double calculateTrend(int index){
    	double value = ((orderedData.get(index).getSmoothed() - orderedData.get(index-1).getSmoothed()) * trendCoefficient)
                + (1-trendCoefficient)*(orderedData.get(index-1).getTrend());
    	return value;
    }
    
    public double calculateForecast(int index){
    	double value = orderedData.get(index-1).getSmoothed()+orderedData.get(index-1).getTrend();
    	return value;
    }
    
    public double calculateError(){
    	double error = 0.0;
    	for(RowDES row:orderedData){
    		if(row.getForecast()!=0.0){
    			error+= Math.pow(row.getForecast()-row.getOriginal(), 2);
    		}
    	}
		// -4 instead of -2 because the forecasted values have 2 less values
    	return Math.sqrt(error/(orderedData.size()-4));
    }
    
    public void run(){
    	prepareDataSet();
    	initialize();
    	/**For every possible value of the smoothing coefficient calculate the values */
    	for(double i=0.1;i<1.0;i+=0.1){
    		smoothingCoefficient = i;
    		/**For every possible combination of the trendCoefficient with the above smoothing coefficient */
    		for(double j=0.1;j<1.0;j+=0.1){

    			trendCoefficient = j;
    			/**For every row in the data set calculate the values */
    			for(int k=2;k<data.size();k++){

    				RowDES row = orderedData.get(k);
    				row.setSmoothed(calculateSmoothed(k));
    				row.setTrend(calculateTrend(k));
    				row.setForecast(calculateForecast(k));
    	    	}
    			
    			/**Compare the found solution with the currently best solution */
	    		double error = calculateError();
	    		if(bestSolution==null ||bestSolution.getError()>error){
    				SolutionDES sol = new SolutionDES();
    				sol.setError(error);
    				//sol.setForecastedValues(forecastedValues);
    				sol.setData(copyOrderedData(orderedData));
    				sol.setSmoothingCoefficient(smoothingCoefficient);
    				sol.setTrendCoefficient(trendCoefficient);
    				bestSolution = sol;
	    		}
    		}
    	}
    	
    	Double finalTrend = bestSolution.getData().get(bestSolution.getData().size()-1).getTrend();
    	for(int i=0;i<12;i++){
    		RowDES lastRow = bestSolution.getData().get(bestSolution.getData().size()-1);
    		RowDES row = new RowDES();
    		row.setMonth(lastRow.getMonth()+1);
    		if(i==0){
    			row.setForecast(lastRow.getSmoothed()+finalTrend);
    		}else{
    			row.setForecast(lastRow.getForecast()+finalTrend);
    		}
    		bestSolution.getData().add(row);
    	}

    	List<RowDES> rows = bestSolution.getData();
    	
    	for(int i=0;i<rows.size();i++){
    		if(i<2){
    			continue;
    		}
    		dataSet.setValue(rows.get(i).getForecast(), "Forecasted", rows.get(i).getMonth()+"");
    	}
    	
    	draw();
    }
    
    public List<RowDES> copyOrderedData(List<RowDES> toCopy){
    	List<RowDES> result = new ArrayList<>();
    	for(RowDES row:toCopy){
    		RowDES copy = new RowDES();
    		copy.setMonth(row.getMonth());
    		copy.setOriginal(row.getOriginal());
    		copy.setSmoothed(row.getSmoothed());
    		copy.setTrend(row.getTrend());
    		copy.setForecast(row.getForecast());
    		result.add(copy);
    	}
    	return result;
    }

    public void draw(){
        JFreeChart chart = ChartFactory.createLineChart("Smoothing", "Months", "Demand", dataSet);
        ChartFrame frame = new ChartFrame("Line Chart", chart);
        frame.setSize(1200, 800);
        frame.setVisible(true);
    }

	public SolutionDES getBestSolution() {
		return bestSolution;
	}

	public void setBestSolution(SolutionDES bestSolution) {
		this.bestSolution = bestSolution;
	}
    
}
