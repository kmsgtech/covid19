package org.kmsg.cv.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.kmsg.cv.common.Constants;
import org.kmsg.cv.common.DaoHandler;
import org.kmsg.cv.common.Util;
import org.kmsg.cv.daoimpl.ProcessDaoImpl;
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
	
	public Map<String, Object> getHistoryStats() 
	{
		return dao.selectStatsHistoryList();
	}

	public Map<String, Object> getCountryStats(String country) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> getCurrentStats() 
	{
		return dao.selectCurrentStats();
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
			  JSONObject json = new JSONObject(jsonText.substring(1));
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
	
	
	
}
