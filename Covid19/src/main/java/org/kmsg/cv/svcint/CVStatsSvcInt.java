package org.kmsg.cv.svcint;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface CVStatsSvcInt {

	Map<String, Object> getAllStats(Map<String, String> params, HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> getCountryStats(Map<String, String> params, HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> getCurrentStats(Map<String, String> params, HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response);

	Map<String, Object> getAllHistoryStats(Map<String, String> params, HttpSession httpSession,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> UpdateHistoricalData(Map<String, String> params, HttpSession httpSession,
			HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> getCountryHistory(Map<String, String> params, HttpSession httpSession,
			HttpServletRequest request, HttpServletResponse response);

}
