package org.kmsg.cv.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.kmsg.cv.model.CVCurrentStats;
import org.springframework.jdbc.core.RowMapper;

public class CVCurrentStatsMapper implements RowMapper<CVCurrentStats>
{
	@Override
	public CVCurrentStats mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		CVCurrentStats stat = new CVCurrentStats();
		stat.setTodayCases(rs.getInt("today_cases"));
		stat.setTodayDeaths(rs.getInt("today_deaths"));
		stat.setTotalCases(rs.getInt("total_cases"));
		stat.setTotalDeaths(rs.getInt("total_deaths"));
		stat.setTotalRecovery(rs.getInt("total_recovery"));
		stat.setMaxTotalCases(rs.getString("max_today_cases"));
		stat.setMaxTotalDeaths(rs.getString("max_today_deaths"));
		return stat;
	}
}
