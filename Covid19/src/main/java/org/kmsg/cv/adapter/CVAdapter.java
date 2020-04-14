package org.kmsg.cv.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.kmsg.cv.common.Constants;
import org.kmsg.cv.common.DaoHandler;
import org.kmsg.cv.common.SvcStatus;
import org.kmsg.cv.common.Util;
import org.kmsg.cv.daoimpl.ProcessDaoImpl;
import org.kmsg.cv.model.CVCountryCurrentFinalStats;
import org.kmsg.cv.model.CVCurrentCountryStats;
import org.kmsg.cv.model.CVCurrentFinalStats;
import org.kmsg.cv.model.CVCurrentStats;
import org.kmsg.cv.model.CVStatsHistory;
import org.kmsg.cv.model.CVStatsHistoryObject;
import org.kmsg.cv.model.Historical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class CVAdapter 
{
	@Autowired
	ProcessDaoImpl dao;

	public Map<String, Object> getAllStats() 
	{
		return dao.selectStatsList();
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getHistoryStats() 
	{
		Map<String,Object> data = new HashMap<>();
		data = dao.selectStatsHistoryList();
		
		if(data.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			List<CVStatsHistory> list = (List<CVStatsHistory>) data.get("lstData");
			return getHistoryData(list);
		}
		return data;
	}
	
	public Map<String, Object> getCurrentStats() 
	{
		Map<String,Object> data = dao.selectCurrentStats();
		CVCurrentStats stats = new CVCurrentStats();
		
		if(data.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
			stats = (CVCurrentStats) data.get("currentStats");
		else
		{
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,"Not able to fetch data");
			return data;
		}

		String totalCases = "", totalDeaths = "", totalRecovery = "";
		int count = 0;
		for(int i = String.valueOf(stats.getTotalCases()).length() - 1; i >= 0 ; i--)
		{
			count++;
			if(count % 3 == 0)
			{
				totalCases = String.valueOf(stats.getTotalCases()).charAt(i) + totalCases;
				totalCases = "," + totalCases;
			}
			else
				totalCases = String.valueOf(stats.getTotalCases()).charAt(i) + totalCases;
		}
		count = 0;
		
		for(int i = String.valueOf(stats.getTotalDeaths()).length() - 1; i >= 0 ; i--)
		{
			count++;
			if(count % 3 == 0)
			{
				totalDeaths = String.valueOf(stats.getTotalDeaths()).charAt(i) + totalDeaths;
				totalDeaths = "," + totalDeaths;
			}
			else
				totalDeaths = String.valueOf(stats.getTotalDeaths()).charAt(i) + totalDeaths;
		}
		count = 0;
		
		for(int i = String.valueOf(stats.getTotalRecovery()).length() - 1; i >= 0 ; i--)
		{
			count++;
			if(count % 3 == 0)
			{
				totalRecovery = String.valueOf(stats.getTotalRecovery()).charAt(i) + totalRecovery;
				totalRecovery = "," + totalRecovery;
			}
			else
				totalRecovery = String.valueOf(stats.getTotalRecovery()).charAt(i) + totalRecovery;
		}
		
		if(totalRecovery.charAt(0)==',')
			totalRecovery = totalRecovery.substring(1);
		
		if(totalCases.charAt(0)==',')
			totalCases = totalCases.substring(1);
		
		if(totalDeaths.charAt(0)==',')
			totalDeaths = totalDeaths.substring(1);
		
		CVCurrentFinalStats newStats = new CVCurrentFinalStats();
		
		newStats.setTotalCases(totalCases);
		newStats.setTotalDeaths(totalDeaths);
		newStats.setTotalRecovery(totalRecovery);
		newStats.setTodayCases(String.valueOf(stats.getTodayCases()));
		newStats.setTodayDeaths(String.valueOf(stats.getTodayDeaths()));
		newStats.setMaxTotalCases(String.valueOf(stats.getMaxTotalCases()));
		newStats.setMaxTotalDeaths(String.valueOf(stats.getMaxTotalDeaths()));
		
		data.remove("currentStats");
		data.put(Constants.STATUS,Constants.SUCCESS);
		data.put("currentStats",newStats);
		return data;
	}

	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getCountryHistoryStats(String country) 
	{
		Map<String,Object> data = new HashMap<>();
		data = dao.selectStatsCountryHistory(country);
		
		if(data.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
		{
			List<CVStatsHistory> list = (List<CVStatsHistory>) data.get("lstData");
			return getHistoryData(list);
		}
		return data;
	}

	public Map<String, Object> getCountryCurrentStats(String country) 
	{
		Map<String,Object> data = dao.selectCountryCurrentStats(country);
		
		CVCurrentCountryStats stats = (CVCurrentCountryStats) data.get("currentStats");

		String totalCases = "", totalDeaths = "", totalRecovery = "";
		int count = 0;
		
		for(int i = String.valueOf(stats.getTotalCases()).length() - 1; i >= 0 ; i--)
		{
			count++;
			if(count % 3 == 0)
			{
				totalCases = String.valueOf(stats.getTotalCases()).charAt(i) + totalCases;
				totalCases = "," + totalCases;
			}
			else
				totalCases = String.valueOf(stats.getTotalCases()).charAt(i) + totalCases;
		}
		count = 0;
		
		for(int i = String.valueOf(stats.getTotalDeaths()).length() - 1; i >= 0 ; i--)
		{
			count++;
			if(count % 3 == 0)
			{
				totalDeaths = String.valueOf(stats.getTotalDeaths()).charAt(i) + totalDeaths;
				totalDeaths = "," + totalDeaths;
			}
			else
				totalDeaths = String.valueOf(stats.getTotalDeaths()).charAt(i) + totalDeaths;
		}
		count = 0;
		
		for(int i = String.valueOf(stats.getTotalRecovery()).length() - 1; i >= 0 ; i--)
		{
			count++;
			if(count % 3 == 0)
			{
				totalRecovery = String.valueOf(stats.getTotalRecovery()).charAt(i) + totalRecovery;
				totalRecovery = "," + totalRecovery;
			}
			else
				totalRecovery = String.valueOf(stats.getTotalRecovery()).charAt(i) + totalRecovery;
		}
		
		if(totalRecovery.charAt(0)==',')
			totalRecovery = totalRecovery.substring(1);
		
		if(totalCases.charAt(0)==',')
			totalCases = totalCases.substring(1);
		
		if(totalDeaths.charAt(0)==',')
			totalDeaths = totalDeaths.substring(1);
		
		CVCountryCurrentFinalStats newStats = new CVCountryCurrentFinalStats();
		
		newStats.setTotalCases(totalCases);
		newStats.setTotalDeaths(totalDeaths);
		newStats.setTotalRecovery(totalRecovery);
		newStats.setTodayCases(String.valueOf(stats.getTodayCases()));
		newStats.setTodayDeaths(String.valueOf(stats.getTodayDeaths()));
		
		data.remove("currentStats");
		data.put(Constants.STATUS,Constants.SUCCESS);
		data.put("currentStats",newStats);
		return data;
	}

	public Map<String, Object> UpdateHistoricalData() throws IOException 
	{
		DaoHandler dh = new DaoHandler();
		dh.start(new Object(){}.getClass().getEnclosingMethod().getName());
		Map<String,Object> data = new HashMap<>();
		if(dao.deleteHistoricalData() == 0)
		{
			dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,"Not Able to delete historical data");
			return data;
		}
		InputStream is = new URL(Constants.URL).openStream();
		try {
			  BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			  String jsonText = Util.readAll(rd);
			  rd.close();
			  JSONObject json = new JSONObject(jsonText);
			  Gson gson = new Gson();
			  for(int i = 0; i < json.getJSONArray("records").length(); i++)
			  {
				  Historical hist = gson.fromJson(json.getJSONArray("records").getJSONObject(i).toString() , Historical.class);
				  if(!dao.updateHistoricalData(hist))
				  {
					  dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
					  data.put(Constants.STATUS,Constants.FAILURE);
					  data.put(Constants.MESSAGE,"Not Able to update historical data");
					  return data;
				  }
			  }
			  dh.commit(new Object(){}.getClass().getEnclosingMethod().getName());
			  data.put(Constants.STATUS,Constants.SUCCESS);
			  data.put(Constants.MESSAGE,"Historical data updated");
			  return data;
		} 
		finally {
			is.close();
		}
	}
	
	private Map<String, Object> getHistoryData(List<CVStatsHistory> list)
	{
		Map<String,Object> data = new HashMap<>();
		
		CVStatsHistoryObject obj = new CVStatsHistoryObject();
		List<String> cvDates = new ArrayList<>();
		List<Integer> cvCases = new ArrayList<>();
		List<Integer> cvDeaths = new ArrayList<>();
		
		for(int i = 0; i < list.size(); i++)
		{
			cvDates.add(list.get(i).getDate());
			cvCases.add(list.get(i).getTotalCases());
			cvDeaths.add(list.get(i).getTotalDeaths());
		}
		obj.setCvCases(cvCases);
		obj.setCvDates(cvDates);
		obj.setCvDeaths(cvDeaths);
		
		data.remove("lstData");
		data.put(Constants.STATUS,Constants.SUCCESS);
		data.put("data",obj);
		return data;
	}
	
}
