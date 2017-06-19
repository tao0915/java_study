package com.admin.common.comm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

/**
 * 날짜 관련 유틸
 *
 */
public class DateUtil {
	/**
	 * 현재 시각의 정보를 문자열로 반환함.
	 * 
	 * @return
	 */
	public static String getDateStr(){		
		return FastDateFormat.getInstance("yyyy.MM.dd HH:mm:ss").format(new Date());
	}
	
	
	/**
	 * 현재 시작을 해당 패턴에 맞게 리턴함.
	 * 	- 패턴은 FastDateFormat의 정보를 확인할 것
	 * @param length
	 * @return
	 */
	public static String getDateStr(String pattern){
		return FastDateFormat.getInstance(pattern).format(new Date());
	}
	
	
	/**
	 * 해당 시각을 해당 패턴을 문자열로 반환함.
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDateStr(Date date, String pattern){
		return FastDateFormat.getInstance(pattern).format(date);
	}
	
	
	
	
	
	public static String getDay(int days, String pattern) throws ParseException {
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(cal.getTime());
	}
	
	
	public static String getDay(String strDate, int days, String pattern) throws ParseException {
		if(StringUtils.isEmpty(strDate) || strDate.length() < 8)return getDay(days,pattern);
		String strPattern = "yyyyMMddHHmmss";				
		
		Date date = new SimpleDateFormat(strPattern.substring(0,strDate.length())).parse(strDate);		
		Calendar cal = Calendar.getInstance();		
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(cal.getTime());
	}	
	

	
	
	/**
	 * 오늘 날자 반환
	 * @param 
	 * @return
	 * @throws Exception
	**/
	public static String getToday () {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMdd");
		Date currDay = new Date();
		return mSimpleDateFormat.format(currDay);
	}

}
