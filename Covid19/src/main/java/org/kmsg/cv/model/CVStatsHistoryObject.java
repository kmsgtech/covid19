package org.kmsg.cv.model;

import java.util.List;

public class CVStatsHistoryObject 
{
	List<String> cvDates;
	List<Integer> cvCases;
	List<Integer> cvDeaths;
	
	public List<String> getCvDates() {
		return cvDates;
	}
	public void setCvDates(List<String> cvDates) {
		this.cvDates = cvDates;
	}
	public List<Integer> getCvCases() {
		return cvCases;
	}
	public void setCvCases(List<Integer> cvCases) {
		this.cvCases = cvCases;
	}
	public List<Integer> getCvDeaths() {
		return cvDeaths;
	}
	public void setCvDeaths(List<Integer> cvDeaths) {
		this.cvDeaths = cvDeaths;
	}
	
	
}
