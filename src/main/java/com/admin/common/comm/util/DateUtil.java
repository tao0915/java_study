package com.admin.common.comm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

/**
 * ��¥ ���� ��ƿ
 *
 */
public class DateUtil {
	/**
	 * ���� �ð��� ������ ���ڿ��� ��ȯ��.
	 * 
	 * @return
	 */
	public static String getDateStr(){		
		return FastDateFormat.getInstance("yyyy.MM.dd HH:mm:ss").format(new Date());
	}
	
	
	/**
	 * ���� ������ �ش� ���Ͽ� �°� ������.
	 * 	- ������ FastDateFormat�� ������ Ȯ���� ��
	 * @param length
	 * @return
	 */
	public static String getDateStr(String pattern){
		return FastDateFormat.getInstance(pattern).format(new Date());
	}
	
	
	/**
	 * �ش� �ð��� �ش� ������ ���ڿ��� ��ȯ��.
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
	 * ���� ���� ��ȯ
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
