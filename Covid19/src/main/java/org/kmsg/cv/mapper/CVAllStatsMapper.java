package org.kmsg.cv.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.kmsg.cv.common.StatSummary;
import org.kmsg.cv.model.CVStats;
import org.springframework.jdbc.core.RowMapper;

public class CVAllStatsMapper implements RowMapper<CVStats>
{
	@Override
	public CVStats mapRow(ResultSet rs, int rowNum) throws SQLException 
	{	
		CVStats stat = new CVStats();
		stat.setCountry(rs.getString("country"));
		stat.setTotalCases(rs.getInt("total_cases"));
		stat.setTotalDeaths(rs.getInt("total_deaths"));
		stat.setTodayCases(rs.getInt("today_cases"));
		stat.setTodayDeaths(rs.getInt("today_deaths"));
		stat.setTotalRecovery(rs.getInt("total_recovery"));
		
		if((rs.getInt("avg_total_cases") - (8 * rs.getInt("avg_total_cases"))/100) > rs.getInt("avg_total_cases_last5"))
			stat.setCasesSummary(StatSummary.getBookingStatus(20));
		else
		{
			if((rs.getInt("avg_total_cases") + (8 * rs.getInt("avg_total_cases"))/100) < rs.getInt("avg_total_cases_last5"))
				stat.setCasesSummary(StatSummary.getBookingStatus(30));
			else
				stat.setCasesSummary(StatSummary.getBookingStatus(10));
		}
		
		if((rs.getInt("avg_total_deaths") - (8 * rs.getInt("avg_total_deaths"))/100) > rs.getInt("avg_total_deaths_last5"))
			stat.setDeathSummary(StatSummary.getBookingStatus(20));
		else
		{
			if((rs.getInt("avg_total_deaths") + (8 * rs.getInt("avg_total_deaths"))/100) < rs.getInt("avg_total_deaths_last5"))
				stat.setDeathSummary(StatSummary.getBookingStatus(30));
			else
				stat.setDeathSummary(StatSummary.getBookingStatus(10));
		}
		
		return stat;
	}
}
