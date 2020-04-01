package org.kmsg.cv.svcimpl;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kmsg.cv.adapter.CVAdapter;
import org.kmsg.cv.svcint.CVStatsSvcInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/org/kmsg/corona")
public class CVStatsSvcImpl implements CVStatsSvcInt
{
	@Autowired
	CVAdapter adapter;
	
	@Override
	@RequestMapping(value="/lstallstats", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getAllStats(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		return adapter.getAllStats();
	}
	
	@Override //To be used later
	@RequestMapping(value="/lstcountrystats", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getCountryStats(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		String country = params.get("country");
		return adapter.getCountryStats(country.toUpperCase());
	}
	
	@Override
	@RequestMapping(value="/history", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getAllHistoryStats(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		return adapter.getHistoryStats();
	}
	
	@Override
	@RequestMapping(value="/current_stats", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> getCurrentStats(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		return adapter.getCurrentStats();
	}
	
	@Override	//One time use
	@RequestMapping(value="/update_historical_data", method = RequestMethod.POST, headers="Accept=application/json")
	public Map<String, Object> UpdateHistoricalData(@RequestParam Map<String, String> params, HttpSession httpSession,HttpServletRequest request, HttpServletResponse response) 
	{
		try {
			return adapter.UpdateHistoricalData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
