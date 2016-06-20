package models;

public class RowDES {
	private int month;
	private double original;
	private double smoothed;
	private double trend;
	private double forecast;
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	public double getOriginal() {
		return original;
	}
	
	public void setOriginal(double original) {
		this.original = original;
	}
	
	public double getSmoothed() {
		return smoothed;
	}
	
	public void setSmoothed(double smoothed) {
		this.smoothed = smoothed;
	}
	
	public double getTrend() {
		return trend;
	}
	
	public void setTrend(double trend) {
		this.trend = trend;
	}
	
	public double getForecast() {
		return forecast;
	}
	
	public void setForecast(double forecast) {
		this.forecast = forecast;
	}
	
	public String toString(){
		String output = "Month:"+ month+" , original:"+original+" , smoothed value:"+ smoothed +" , trend value:" + trend
				+" , forecasted value:" + forecast;
		return output;
	}

	
}
