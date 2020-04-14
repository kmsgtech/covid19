package org.kmsg.cv.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.kmsg.cv.model.CVCurrentCountryStats;
import org.springframework.jdbc.core.RowMapper;

public class CVCountryCurrentStatsMapper implements RowMapper<CVCurrentCountryStats>
{

	@Override
	public CVCurrentCountryStats mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		CVCurrentCountryStats stat = new CVCurrentCountryStats();
		
		stat.setTodayCases(rs.getInt("today_cases"));
		stat.setTodayDeaths(rs.getInt("today_deaths"));
		stat.setTotalCases(rs.getInt("total_cases"));
		stat.setTotalDeaths(rs.getInt("total_deaths"));
		stat.setTotalRecovery(rs.getInt("total_recovery"));
		
		return stat;
	}

}
