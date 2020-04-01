package org.kmsg.cv.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.kmsg.cv.model.CVStatsHistory;
import org.springframework.jdbc.core.RowMapper;

public class CVStatsHistoryMapper implements RowMapper<CVStatsHistory>
{

	@Override
	public CVStatsHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
		CVStatsHistory hist = new CVStatsHistory();
		hist.setDate(rs.getString("cv_date"));
		hist.setTotalDeaths(rs.getInt("total_deaths"));
		hist.setTotalCases(rs.getInt("total_cases"));
		return hist;
	}

}
