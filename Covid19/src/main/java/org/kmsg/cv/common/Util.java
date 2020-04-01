package org.kmsg.cv.common;
import java.io.IOException;
import java.io.Reader;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.Random;

public final class Util 
{
	public final static String Now()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return df.format(date);
	}
	
	public final static String Today()
	{
		Date date = new Date();
		return new SimpleDateFormat("dd-MMM-yy").format(date);
	}
	
	public final static String Today_YYYYMMDD()
	{
		Date date = new Date();
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	public final static String CurrentDate()
	{
		DateFormat dfc = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dfc.format(date);
	}

	public final static int CurTime()
	{
		DateFormat dfc = new SimpleDateFormat("HHmmss");
		Date date = new Date();
		String currentTime = dfc.format(date);
		return Integer.parseInt(currentTime);
	}
	
	public final static Date convertStrToDt(String strDt)
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
		try {
			return dateFormat.parse(strDt);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public final static String convertDateFormat(final String inputDt, final String inFormat, final String outFormat) {
		DateFormat inDateFormat = new SimpleDateFormat( inFormat );
		Date dt = new Date();
		
		try {
			dt = inDateFormat.parse(inputDt);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		DateFormat outDateFormat = new SimpleDateFormat( outFormat );
		String currentDate = outDateFormat.format(dt);
		return currentDate;
	}

	public final static Date getCurrentDtddMMyy() {
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
		String strDate = sdf.format(cal.getTime());
		
		try {
			return sdf.parse(strDate);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


	public final static Date convertStrToDt(String strDt, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date dt = new Date();
		
		try {
			dt = dateFormat.parse(strDt);
		}
		catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return dt;
	}
	
	public final static String convertDtToStr(Date dt)
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
		return dateFormat.format(dt);
	}
	
	public final static String getStrTimeStamp()
	{
		DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
		Date date = new Date();
		return new String(df.format(date));
	}
	
	public final static String getStrTimeStampdelete()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return new String(df.format(date));
	}
	
	public final static String getStrDate()
	{
		DateFormat df = new SimpleDateFormat("dd-MM-yy");
		Date date = new Date();
		return new String(df.format(date));
	}	
	
	public final static String convertDtToNextDtStr(Date dt)
	{
		dt.setTime(dt.getTime() + 1 * 24 * 60 * 60 * 1000 ); // Add one day to date
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
		return dateFormat.format(dt);
	}

	public final static String getTomorrow()
	{
		Date date = new Date();		
		date.setTime(date.getTime() + 1 * 24 * 60 * 60 * 1000 ); // Add one day to date
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
		return dateFormat.format(date);
	}
	
	public final static int getNextIntYYYYMth(final int inputYrMth, String format) {
		String strDt = String.format("%6d01", inputYrMth);
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		
		Date dt;
		try {
			dt = dateFormat.parse(strDt);
			cal.setTime( dt );
			cal.add(Calendar.MONTH, 1);
			int yyyy = cal.get( Calendar.YEAR ) ;
			int mm = cal.get(Calendar.MONTH ) + 1  ;
			return yyyy * 100 + mm ;
		}
		catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	// Random Password Generation code starts*************************************************
	
	private static final Random RANDOM = new SecureRandom();
		
	  /// Length of password.
	public static final int PASSWORD_LENGTH = 8;

	  public static String generateRandomPassword()
	  {
	      String letters = "0123456789abcdefghijklmnopqrstuvwxyz";

	      String pw = "";
	      
	      for (int i=0; i < PASSWORD_LENGTH; i++)
	      {
	          int index = (int)(RANDOM.nextDouble() * letters.length());
	          pw += letters.substring(index, index+1);
	      }
	      return pw;
	  }
	// ends here*******************************************************************************
	  
	// OTP Generation code starts**************************************************************
		
		  /// Length of password.
		public static final int OTP_Length = 6;

		  public static String genOTP()
		  {
		      String letters = "0123456789";

		      String otp = "";
		      
		      for (int j=0; j < OTP_Length; j++)
		      {
		          int index = (int)(RANDOM.nextDouble() * letters.length());
		          otp += letters.substring(index, index+1);
		      }
		      return otp;
		  }
		// ends here*****************************************************************************
	  
	// below function is for SMS Service*********************************************************

	public static String currencyFormat(final int inVal) {
		int val = inVal;
		String res = ""; // 11,23,000 210000
		
		if ( val < 1000)
			return String.format("%03d", val);
		
		res = "," + String.format("%03d", val%1000);
		val = val/1000;
		
		if ( val < 100)
			return String.format("%02d", val) + res ;
		
		res = "," + String.format("%02d", val%100) + res ;
		val = val/100;
		
		return String.format("%2d", val) + res ;
	}
	
	 public static String readAll(Reader rd) throws IOException 
	 {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
	 }
	
}

	// Ends Here********************************************************************************************
