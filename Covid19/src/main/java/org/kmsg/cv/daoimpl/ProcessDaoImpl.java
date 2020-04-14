package org.kmsg.cv.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kmsg.cv.common.CVLogger;
import org.kmsg.cv.common.Constants;
import org.kmsg.cv.common.Util;
import org.kmsg.cv.daoint.ProcessDaoInt;
import org.kmsg.cv.mapper.CVAllStatsMapper;
import org.kmsg.cv.mapper.CVCountryCurrentStatsMapper;
import org.kmsg.cv.mapper.CVCurrentStatsMapper;
import org.kmsg.cv.mapper.CVStatsHistoryMapper;
import org.kmsg.cv.model.CVCurrentCountryStats;
import org.kmsg.cv.model.CVCurrentStats;
import org.kmsg.cv.model.CVStats;
import org.kmsg.cv.model.CVStatsHistory;
import org.kmsg.cv.model.Historical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProcessDaoImpl implements ProcessDaoInt, CVLogger
{
	@Autowired 
	JdbcTemplate template;
	
	@Override
	public int insertNewReadings(CVStats dtls, int scrapId) 
	{	
		final String SQL = "INSERT INTO cv_stats (scrap_id,country,today_cases, today_deaths, total_cases, total_deaths, total_recovery) VALUES (?,?,?,?,?,?,?)";

	    int count = 0;
		
		try {
			 count = template.update(SQL,new Object[]{
						 scrapId,
						 dtls.getCountry(),
						 dtls.getTodayCases(),
						 dtls.getTodayDeaths(),
						 dtls.getTotalCases(),
						 dtls.getTotalDeaths(),
						 dtls.getTotalRecovery(),
					 });
				
		}
		
		catch(Exception e) {
			e.printStackTrace();
			errorLogs.error("insertNewReading Exception occured :" + e.getMessage());
			return 0;
		}
		
		if (count == 0 ) {
			errorLogs.error("insertNewReading New Readings can't be inserted");
			return 0;
		}
		else
			return 1;
	}
	
	@Override
	public int deleteHistoricalData() 
	{	
		final String SQL = "TRUNCATE TABLE cv_stats_history;";

	    int count = 0;
		
		try {
			 count = template.update(SQL);
		}
		
		catch(Exception e) {
			e.printStackTrace();
			errorLogs.error("deleteHistory Exception occured :" + e.getMessage());
			return 0;
		}
		
		if (count == 0 ) {
			return 1;
		}
		else
		{
			errorLogs.error("Historical Data can't be deleted");
			return 0;
		}
	}
	
	public Map<String, Object> insertNewScrap(int status) 
	{
		Map<String,Object> data = new HashMap<>();
		final String SQL = "INSERT INTO scraps (scrap_dt_tm, scrap_status) VALUES (?,?)";

	    int count = 0;
		KeyHolder holder = new GeneratedKeyHolder();
		try {
			 count = template.update (
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						PreparedStatement ps = conn.prepareStatement(
													SQL,
													Statement.RETURN_GENERATED_KEYS
																	);
						ps.setString( 1, Util.Now() );
						ps.setInt( 2, status);
						return ps ;
					}
				}, holder ) ;
		}
		catch(Exception e) {
			errorLogs.error("Exception occured in scraping:" + e.getMessage());
			data.put(Constants.MESSAGE, "Error occured in inserting scraping");
			data.put(Constants.STATUS, Constants.FAILURE);
			return data;
		}
		
		if (count > 0 ) {
			errorLogs.info("New Scrap Inserted");
			data.put( "scrapId", holder.getKey().intValue());
			data.put(Constants.STATUS, Constants.SUCCESS);
			return data;
		}
		else {
			data.put(Constants.MESSAGE, "Scrap Could not be Inserted");
			data.put(Constants.STATUS, Constants.FAILURE);
			return data;
		}
	}
	
	@Override
	public Map<String, Object> selectStatsHistoryList() 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = "Select SUM(total_cases) as total_cases" + 
				",SUM(total_deaths) as total_deaths" + 
				",DATE_FORMAT(cv_date,'%d-%m-%Y') as cv_date" + 
				" FROM cv_stats_history" + 
				" GROUP BY cv_date";
				
		try {
			List<CVStatsHistory> list = template.query(SQL,new CVStatsHistoryMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Data Exist");
				return data;
			}

			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstData",list);
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}
	
	@Override
	public Map<String, Object> selectStatsCountryHistory(String country) 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = " Select total_cases as total_cases  " + 
				" ,total_deaths as total_deaths  " + 
				" ,DATE_FORMAT(cv_date,'%d-%m-%Y') as cv_date  " + 
				" FROM cv_stats_history" + 
				" WHERE country = ?" + 
				" GROUP BY cv_date;";
				
		try {
			List<CVStatsHistory> list = template.query(SQL,new Object[] {country},new CVStatsHistoryMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Data Exist");
				return data;
			}

			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstData",list);
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}
	
	@Override
	public Map<String, Object> selectStatsList() 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = "Select " + 
				" IFNULL((SELECT CAST(avg(total_cases) AS SIGNED) FROM cv_stats_history csh WHERE csh.country = cs.country),0) as avg_total_cases" + 
				" ,IFNULL((SELECT CAST(avg(total_deaths) AS SIGNED) FROM cv_stats_history csh WHERE csh.country = cs.country),0) as avg_total_deaths" + 
				" ,IFNULL((SELECT CAST(avg(total_cases) AS SIGNED) FROM cv_stats_history csh WHERE csh.country = cs.country AND csh.cv_date BETWEEN DATE_SUB(current_date() , INTERVAL 5 DAY) AND DATE_SUB(current_date() , INTERVAL 1 DAY)),0) as avg_total_cases_last5" + 
				" ,IFNULL((SELECT CAST(avg(total_deaths) AS SIGNED) FROM cv_stats_history csh WHERE csh.country = cs.country AND csh.cv_date BETWEEN DATE_SUB(current_date() , INTERVAL 5 DAY) AND DATE_SUB(current_date() , INTERVAL 1 DAY)),0) as avg_total_deaths_last5" + 
				" ,country  " + 
				" ,total_cases  " + 
				" ,today_cases  " + 
				" ,total_deaths  " + 
				" ,today_deaths  " + 
				" ,total_recovery   " + 
				" FROM cv_stats cs  " + 
				" JOIN scraps s ON s.scrap_id = cs.scrap_id WHERE country <> 'TOTAL' AND s.scrap_dt_tm = (SELECT max(scrap_dt_tm) FROM scraps WHERE scrap_status = 1) ORDER BY total_deaths desc;";
				
		try {
			List<CVStats> list = template.query(SQL,new CVAllStatsMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Data Available!!");
				return data;
			}

			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("lstAllStats",list);
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}
	
	@Override
	public Map<String, Object> selectCountryCurrentStats(String country) 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = "SELECT " + 
				" total_cases  " + 
				" ,today_cases  " + 
				" ,total_deaths  " + 
				" ,today_deaths  " + 
				" ,total_recovery   " + 
				" FROM cv_stats cs  " + 
				" JOIN scraps s ON s.scrap_id = cs.scrap_id WHERE country = ? AND s.scrap_dt_tm = (SELECT max(scrap_dt_tm) FROM scraps WHERE scrap_status = 1);";
				
		try {
			List<CVCurrentCountryStats> list = template.query(SQL,new Object[]{country}, new CVCountryCurrentStatsMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Data Available!!");
				return data;
			}

			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("currentStats",list.get(0));
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}

	public Map<String, Object> selectCurrentStats() 
	{
		Map<String,Object> data = new HashMap<>();
		
		String SQL = "SELECT " + 
				" (SELECT cs.country FROM cv_stats cs JOIN scraps s ON s.scrap_id = cs.scrap_id WHERE cs.country <> 'TOTAL'  " + 
				" AND s.scrap_dt_tm = (SELECT max(scrap_dt_tm) FROM scraps WHERE scrap_status = 1) AND cs.today_cases = (SELECT MAX(today_cases) FROM cv_stats cs JOIN scraps s ON s.scrap_id = cs.scrap_id WHERE cs.country <> 'TOTAL' AND s.scrap_dt_tm = (SELECT max(scrap_dt_tm) FROM scraps WHERE scrap_status = 1)) LIMIT 1) as max_today_cases  " + 
				" ,(SELECT cs.country FROM cv_stats cs JOIN scraps s ON s.scrap_id = cs.scrap_id WHERE cs.country <> 'TOTAL'  " + 
				" AND s.scrap_dt_tm = (SELECT max(scrap_dt_tm) FROM scraps WHERE scrap_status = 1) AND cs.today_deaths = (SELECT MAX(today_deaths) FROM cv_stats cs JOIN scraps s ON s.scrap_id = cs.scrap_id WHERE cs.country <> 'TOTAL' AND s.scrap_dt_tm = (SELECT max(scrap_dt_tm) FROM scraps WHERE scrap_status = 1)) LIMIT 1) as max_today_deaths" + 
				" ,total_cases  " + 
				" ,today_cases  " + 
				" ,total_deaths  " + 
				" ,today_deaths  " + 
				" ,total_recovery   " + 
				" FROM cv_stats cs  " + 
				" JOIN scraps s ON s.scrap_id = cs.scrap_id WHERE country = 'TOTAL' AND s.scrap_dt_tm = (SELECT max(scrap_dt_tm) FROM scraps WHERE scrap_status = 1);";
				
		try {
			List<CVCurrentStats> list = template.query(SQL,new CVCurrentStatsMapper());
			
			if(list.size()==0)
			{
				data.put(Constants.STATUS,Constants.FAILURE);
				data.put(Constants.MESSAGE,"No Data Available!!");
				return data;
			}

			data.put(Constants.STATUS,Constants.SUCCESS);
			data.put("currentStats",list.get(0));
			return data;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			data.put(Constants.STATUS,Constants.FAILURE);
			data.put(Constants.MESSAGE,e);
			return data;
		}
	}

	public void insertNewHistoricalData() 
	{
		final String SQL = "INSERT INTO cv_stats_history" + 
				" (" + 
				"  country," + 
				"  total_cases" + 
				"  ,total_deaths" + 
				"  ,cv_date" + 
				" ) " + 
				" SELECT" + 
				"		cs.country," + 
				"		cs.today_cases," + 
				"		cs.today_deaths" + 
				"		,DATE_FORMAT(scrap_dt_tm,'%Y-%m-%d')" + 
				"	   FROM cv_stats cs" + 
				"	   JOIN scraps s ON s.scrap_id = cs.scrap_id WHERE country <> 'TOTAL' AND s.scrap_dt_tm = (SELECT max(scrap_dt_tm) FROM scraps WHERE scrap_status = 1)" ;

	    int count = 0;
		
		try {
			 count = template.update(SQL);
		}
		catch(Exception e) {
			e.printStackTrace();
			errorLogs.error("insertNewHistorical Exception occured :" + e.getMessage());
		}
		
		if (count == 0 ) {
			errorLogs.error("New Historical Data can't be inserted");
		}
		else
			logger.info("New Historical Data Inserted");
	}

	public boolean updateHistoricalData(Historical hist) 
	{
		final String SQL = "INSERT INTO cv_stats_history (country, total_cases, total_deaths, cv_date) VALUES (?,?,?,STR_TO_DATE(?,'%d-%m-%Y'))";

	    int count = 0;
		
		try {
			 count = template.update(SQL,new Object[]{
					  	 hist.getCountriesAndTerritories().toUpperCase()
						 ,hist.getCases()
						 ,hist.getDeaths()
						 ,hist.getDateRep().replaceAll("/", "-")
					 });
				
		}
		
		catch(Exception e) {
			e.printStackTrace();
			errorLogs.error("insertNewReading Exception occured :" + e.getMessage());
			return false;
		}
		
		if (count == 0 ) {
			errorLogs.error("insertNewReading New Readings can't be inserted");
			return false;
		}
		else
			return true;
	}
}
