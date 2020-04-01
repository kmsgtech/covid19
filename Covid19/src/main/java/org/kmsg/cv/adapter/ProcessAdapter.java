package org.kmsg.cv.adapter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.kmsg.cv.common.CVLogger;
import org.kmsg.cv.common.Constants;
import org.kmsg.cv.common.DaoHandler;
import org.kmsg.cv.common.SvcStatus;
import org.kmsg.cv.daoimpl.ProcessDaoImpl;
import org.kmsg.cv.model.CVStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProcessAdapter implements CVLogger
{
	@Autowired
	ProcessDaoImpl dao;
	
	@Scheduled(fixedDelay = 600000, initialDelay = 3000)
	public void GetReadingData() throws IOException
	{
		String startContent = "main_table_countries_today";
		String endContent = "</table>";
		String startRow = "<tr style=\"\">";
		String startTotalRow = "total_row";
		boolean start = false,end = false, insideRow = false, insideTotalRow = false;
		int columnCount = 0 ;

		URL servlet = new URL(Constants.STAT_URL);
		HttpsURLConnection conn = (HttpsURLConnection) servlet.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setRequestProperty("Content-type", "text/plain");

		DataOutputStream out = new DataOutputStream( conn.getOutputStream() );

		out.flush();
		out.close();
		
		BufferedReader reader = new BufferedReader( new InputStreamReader(conn.getInputStream()));
		String rowData = "";
		List<CVStats> listStats = new ArrayList<>();
		CVStats stats = new CVStats();
		
		while ((rowData = reader.readLine()) != null) 
		{
			if ( rowData.trim().isEmpty()) 
				continue;
			
			rowData = rowData.replace("&nbsp;"," ");
			
			if(rowData.contains(endContent))
			{
				end = true;
				break;
			}
			
			if(rowData.contains(startContent))
				start = true;
			
			if(start)
			{
				if(rowData.contains(startRow))
				{
					insideRow = true;
					continue;
				}
				
				if(rowData.contains(startTotalRow))
				{
					insideTotalRow = true;
					continue;
				}

				if(insideRow)
				{
					columnCount++;
					String content = rowData.substring(rowData.lastIndexOf("\">")+2,rowData.indexOf("</")).trim();
					
					switch(columnCount)
					{
						case 1 : stats.setCountry(content.toUpperCase());
						break;
						
						case 2 : stats.setTotalCases((content.replaceAll(",","").length()!=0) ? Integer.parseInt(content.replaceAll(",","")) : 0);
						break;
						
						case 3 : stats.setTodayCases((content.replaceAll(",","").length()!=0) ? Integer.parseInt(content.replaceAll(",","").substring(1)) : 0);
						break;
						
						case 4 : stats.setTotalDeaths((content.replaceAll(",","").length()!=0) ? Integer.parseInt(content.replaceAll(",","")) : 0);
						break;
						
						case 5 : stats.setTodayDeaths((content.replaceAll(",","").length()!=0) ? Integer.parseInt(content.replaceAll(",","").substring(1)) : 0);
						break;
						
						case 6 : stats.setTotalRecovery((content.replaceAll(",","").length()!=0) ? Integer.parseInt(content.replaceAll(",","")) : 0);
						break;
						
						case 7 : {
							insideRow = false;
							columnCount = 0;
							listStats.add(stats);
							stats = new CVStats();
						}
						break;
					}
				}
				
				if(insideTotalRow)
				{
					columnCount++;
					String content = rowData.substring(rowData.indexOf(">")+1,rowData.indexOf("</")).trim();
					String countryContent = rowData.substring(rowData.indexOf("g>")+2,rowData.indexOf("</")).trim();
					
					switch(columnCount)
					{
						case 1 : stats.setCountry(countryContent.substring(0,countryContent.length()-1).toUpperCase());
						break;
						
						case 2 : stats.setTotalCases((content.replaceAll(",","").length()!=0) ? Integer.parseInt(content.replaceAll(",","")) : 0);
						break;
						
						case 3 : stats.setTodayCases((content.replaceAll(",","").length()!=0) ? Integer.parseInt(content.replaceAll(",","").substring(1)) : 0);
						break;
						
						case 4 : stats.setTotalDeaths((content.replaceAll(",","").length()!=0) ? Integer.parseInt(content.replaceAll(",","")) : 0);
						break;
						
						case 5 : stats.setTodayDeaths((content.replaceAll(",","").length()!=0) ? Integer.parseInt(content.replaceAll(",","").substring(1)) : 0);
						break;
						
						case 6 : stats.setTotalRecovery((content.replaceAll(",","").length()!=0) ? Integer.parseInt(content.replaceAll(",","")) : 0);
						break;
						
						case 7 : {
							insideTotalRow = false;
							columnCount = 0;
							listStats.add(stats);
							stats = new CVStats();
						}
						break;
					}
				}
			}
		}
		DaoHandler dh = new DaoHandler();
		dh.start(new Object(){}.getClass().getEnclosingMethod().getName());
		
		if(end)
		{
			Map<String, Object> map = new HashMap<>();
			
			map = dao.insertNewScrap(1);
			if(map.get(SvcStatus.STATUS).equals(SvcStatus.SUCCESS))
			{
				int count = 0;
				int scrapId = (int)map.get("scrapId");
				for(int i = 0; i < listStats.size();i++)
				{
					if(dao.insertNewReadings(listStats.get(i), scrapId) == 0)
					{
						count++;
						dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
						break;
					}
				}
				if(count == 0)
				{
					dh.commit(new Object(){}.getClass().getEnclosingMethod().getName());
					logger.info("Stats Updated !");
				}
			}
			else
			{
				dh.rollback(new Object(){}.getClass().getEnclosingMethod().getName());
				logger.info(""+map.get(SvcStatus.MSG));
			}
		}
		else
		{
			dao.insertNewScrap(2);
			dh.commit(new Object(){}.getClass().getEnclosingMethod().getName());
		}
	}
	
	@Scheduled(cron = "0 59 23 * * *")
	public void insertNewHistorical()
	{
		dao.insertNewHistoricalData();
	}
}
