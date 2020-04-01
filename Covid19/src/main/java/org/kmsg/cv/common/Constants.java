package org.kmsg.cv.common;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Constants {
	
	public static String MESSAGE = "SvcMsg";
	public static String STATUS  = "SvcStatus";
	public static String SUCCESS = "Success";
	public static String FAILURE = "Failure";
	public static String URL = "https://opendata.ecdc.europa.eu/covid19/casedistribution/json/";
	public static String STAT_URL = "https://www.worldometers.info/coronavirus/";
	public static DataSource dataSource = new DriverManagerDataSource();

	//public static final BuyerType[] = [{"buyerTypeId":1,"Buyer"},{"buyerTypeId"=2,"buyerType":"Merchant"},{"buyerTypeId":3,"buyerType":"Factory"}]
	public static String MSG_OOPS = "Oops! Something bad happened. Contact system administrator";
	
	@Autowired
	private Environment environment;
	
	public static Map<Integer,String> status = new HashMap<>();
		
		static 
		{
			status.put(1,"PENDING");
			status.put(2,"SUCCESS");
			status.put(3,"FAILURE");
			status.put(4,"CANCELLED");
		}
	
	public String profile = this.environment.getActiveProfiles()[0];
	
	

}
