package models;

import java.util.List;

public class SolutionDES {
	private double smoothingCoefficient;
	private double trendCoefficient;
	private double error;
	private List<RowDES> data;
	
	public double getSmoothingCoefficient() {
		return smoothingCoefficient;
	}
	
	public void setSmoothingCoefficient(double smoothingCoefficient) {
		this.smoothingCoefficient = smoothingCoefficient;
	}
	
	public double getTrendCoefficient() {
		return trendCoefficient;
	}
	
	public void setTrendCoefficient(double trendCoefficient) {
		this.trendCoefficient = trendCoefficient;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public List<RowDES> getData() {
		return data;
	}

	public void setData(List<RowDES> data) {
		this.data = data;
	}
}
