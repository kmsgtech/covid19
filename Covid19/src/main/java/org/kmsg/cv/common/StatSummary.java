package org.kmsg.cv.common;

public class StatSummary 
{
	final static int SAME 	= 10;
	final static int BETTER  = 20;
	final static int WORST = 30;
	
	public static String getBookingStatus(int statusId)
	{
		String status = "";
		switch(statusId)
		{
			case SAME: status = "SAME";
			break;
			
			case BETTER: status = "BETTER";
			break;
			
			case WORST: status = "WORST";
			break;
		}
		
		return status;
	}
}
