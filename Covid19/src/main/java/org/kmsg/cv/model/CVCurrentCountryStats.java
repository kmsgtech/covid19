package org.kmsg.cv.model;

public class CVCurrentCountryStats 
{
	private int totalCases;
	private int totalDeaths;
	private int totalRecovery;
	private int todayCases;
	private int todayDeaths;
	
	public int getTotalCases() {
		return totalCases;
	}
	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}
	public int getTotalDeaths() {
		return totalDeaths;
	}
	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}
	public int getTotalRecovery() {
		return totalRecovery;
	}
	public void setTotalRecovery(int totalRecovery) {
		this.totalRecovery = totalRecovery;
	}
	public int getTodayCases() {
		return todayCases;
	}
	public void setTodayCases(int todayCases) {
		this.todayCases = todayCases;
	}
	public int getTodayDeaths() {
		return todayDeaths;
	}
	public void setTodayDeaths(int todayDeaths) {
		this.todayDeaths = todayDeaths;
	}
}
