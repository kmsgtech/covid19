package org.kmsg.cv.model;

public class CVStats 
{
	private String country;
	private int totalCases;
	private int totalDeaths;
	private int totalRecovery;
	private int todayCases;
	private int todayDeaths;
	private String casesSummary;
	private String deathSummary;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
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
	public String getCasesSummary() {
		return casesSummary;
	}
	public void setCasesSummary(String casesSummary) {
		this.casesSummary = casesSummary;
	}
	public String getDeathSummary() {
		return deathSummary;
	}
	public void setDeathSummary(String deathSummary) {
		this.deathSummary = deathSummary;
	}
}
