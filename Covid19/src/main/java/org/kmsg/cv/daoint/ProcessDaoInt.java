package org.kmsg.cv.daoint;

import java.util.Map;

import org.kmsg.cv.model.CVStats;

public interface ProcessDaoInt {

	int insertNewReadings(CVStats dtls, int scrapId);

	Map<String, Object> selectStatsHistoryList();

	Map<String, Object> selectStatsList();

	int deleteHistoricalData();

}
