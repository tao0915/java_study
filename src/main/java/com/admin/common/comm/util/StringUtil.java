package com.admin.common.comm.util;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 문자열 관련 유틸
 *
 */
public class StringUtil {
	private static final Logger log = LoggerFactory.getLogger(StringUtil.class);
	
	
	public static void main(String[] args) throws Exception{
	}
	
	
	
	/**
	 * 해당 오브젝트를 JSON 문자열로 반환함.
	 * @param obj
	 * @return
	 */
	public static String getJsonStr(Object obj){
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("IOException : {}", e.getMessage());
			return null;
		}
	}
	
	/**
	 * 해당 json 형식에 문자열을 List<Map<String, Object>> 형태로 반환함
	 * @param values
	 * @return
	 */
	public static List<Map<String, Object>> getJsonMap(String values){
		ObjectMapper om = new ObjectMapper();
		try{
			
			List<Map<String, Object>> delFileInfoList		= om.readValue(om.readTree(values).traverse(), new TypeReference<List<Map<String, Object>>>() {});
			return delFileInfoList;
		}catch(IOException e){
			log.error("IOException : {}", e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * 해당 파일 경로문자열에서 파일 확장자 문자열을 반환함
	 * @param path
	 * @return
	 */
	public static String getFileExt(String path){
		if(StringUtils.isEmpty(path)){ return ""; }
		
		if(StringUtils.lastIndexOf(path, ".") != -1){
			return StringUtils.substringAfterLast(path, ".");
		}
		
		
		return "";
	}
	
	
	/**
	 * 해당 길이 많큼의 문자열을 랜덤으로 반환함.
	 * @param length
	 * @return
	 */
	public static String getRandomStr(int length){
		StringBuffer sb = new StringBuffer();
		
		for(int i=0 ; i < length ; i++){
			sb.append((char)((Math.random() * 94) + 33));
		}
		
		return sb.toString();
	}
	

	/**
	 * 해당 패스워드의 유효성을 체크함.
	 * 	- 유효성 체크 규칙은 영문대/소문자 + 숫자 + 특수문자 + 8~20자리
	 * @param pw
	 * @return
	 */
	public static boolean isValidatePassword(String pw){
		String regex	= "((?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%!]).{8,20})";
		return isValidatePassword(pw, regex);
	}

	
	/**
	 * 해당 패스워드의 유효성을 체크함.
	 * @param pw
	 * @param regex
	 * @return
	 */
	public static boolean isValidatePassword(String pw, String regex){
		return Pattern.matches(regex, pw);
	}
	
	
	/**
	 * 해당 값들중에 빈값이 있는지 여부를 체크함
	 * 	- 빈값이 있는 항목의 인덱스를 반환함.
	 * @param val
	 * @return
	 */
	public static int isEmpty(String ... val){
		for(int i=0 ; i < val.length ; i++){
			if(StringUtils.isEmpty(val[i])){ return i; }
		}
		
		return -1;
	}

	/**
	 * 날짜 형식 데이터에서 숫자외의 문자열을 제거해서 반환함.
	 * @param dateStr
	 * @param suffix
	 * @return
	 */
	public static String convertDateFormat(String dateStr, int length, String suffix){
		if(StringUtils.isEmpty(dateStr)){ return ""; }
		
		int cnt = 0;
		StringBuffer rv = new StringBuffer();
		for(int i=0 ; i < dateStr.length() ; i++){
			if(NumberUtils.isNumber(dateStr.substring(i, i+1))){
				rv.append(dateStr.substring(i, i+1));
				cnt++;
				
				if(cnt == length){ break; }
			}
		}
		
		return rv.toString() + suffix;
	}

	/**
	 * 숫자를 ###,### 패턴으로 반환
	 * @param value
	 * @return
	 */
	public static String numberFormat(Object obj){
		if(obj instanceof String){					
			try{
				return NumberFormat.getInstance().format(Integer.parseInt((String)obj));	
			}catch(Exception e){
				return obj.toString();
			}			
		}else{
			return NumberFormat.getInstance().format(obj);	
		}
		
	}
	
	/**
	 * 문자열로 해당 패턴에 맞게 반환 (ex: stringDateFormat("20150119143459","yyyy.mm.dd") -> 2015.01.19
	 * @param value
	 * @param pattern
	 * @return
	 */
	public static String stringDateFormat(String value, String pattern){
		String tmpStr = "";		
        if((value != null) && !value.equals("") && (pattern != null) && !pattern.equals(""))
        {            
        	if(Pattern.matches("[0-9]+", value)){			//숫자만 허용        		
	            char[] valueChar = value.toCharArray();
	            char[] patternChar = pattern.toCharArray();	
	            if((valueChar != null) && (valueChar.length > 0) && (patternChar != null) && (patternChar.length > 0))
	            {
	                int i = 0;
	                int k = 0;
	
	                while (k < patternChar.length)
	                {
	                    if(patternChar[k] == '#')
	                    {
	                        if(i < valueChar.length)
	                        {
	                            tmpStr += valueChar[i];
	                            i++;
	                        }
	
	                        k++;
	                    }
	                    else
	                    {
	                        while ((k < patternChar.length) && (patternChar[k] != '#'))
	                        {
	                            tmpStr += patternChar[k];
	                            k++;
	                        }
	                    }
	                }
	            }  
        	}else{
        		tmpStr = value;
        	}
        }		
		return tmpStr;
	}
	
	
	/**
	 * 전화번호 변환 (xx-xxx-xxx, xxx-xxx-xxxx, xxx-xxxx-xxxx)
	 * @param value
	 * @return
	 */
	public static String telNumFormat(String value, String pattern) {
		
		if (StringUtils.isEmpty(value)) {
			return "";
		}
		
		String regex = "^(01\\d{1}|02|0\\d{1,2})(\\d{3,4})(\\d{4})";
		
		return value.replaceAll(regex, "$1" + pattern + "$2" + pattern + "$3");
	}
	
	
	/**
	 * 전화번호 "-" 기준 배열 반환
	 * @param value
	 * @return
	 */
	public static String[] getTelNumArray(String value) {
		
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		
		if (StringUtils.indexOf(value, "-") > -1) {
			return value.split("-");
		}
		
		return StringUtil.telNumFormat(value, "-").split("-");
	}

	/**
	 * email "@" 기준 배열 반환
	 * @param value
	 * @return
	 */
	public static String[] getEmailArray(String value)	{
		if(StringUtils.isEmpty(value)){
			return null;
		}
		return value.split("@");
	}
	
	
	/**
	 * 우편번호 반환 (123456 => 123-456)
	 * @param value
	 * @return
	 */
	public static String zipcodeFormat(String value) {
		
		if (StringUtils.isEmpty(value)) {
			return "";
		}
		
		String regex = "(\\d{3})(\\d{3})";
		
		return value.replaceAll(regex, "$1-$2");
	}
	
	/**
	 * message의 {?}해당값 치환 
	 * @param msg
	 * @param val
	 * @return
	 */
	public static String getMsgReplace(String msg, String ... val){
		if(StringUtils.isEmpty(msg))return "";
		
		if(val != null){
			for(int i=0; i<val.length; i++){
				msg = msg.replace("{"+i+"}", val[i]);
			}			
		}
		
		return msg;
	}
	
	
	/**
	 * 해당값이 배열에 있는지 유무 확인
	 * @param val
	 * @param searchArray
	 * @return
	 */
	public static boolean containsArray(String val, String[] searchArray){
		boolean result = false;
		if(StringUtils.isEmpty(val) || searchArray == null)return result;
		for(String val2: searchArray){
			if(StringUtils.equals(val, val2)){
				result = true;
				break;
			}
		}		
		return result;
	}
	
	/**
	 * 해당값이 배열에 없는지 유무 체크 
	 * @param val
	 * @param searchArray
	 * @return
	 */
	public static boolean containsArrayAny(String val, String[] searchArray,String separator){
		boolean result = true;		
		if(StringUtils.isEmpty(val) || searchArray == null)return result;
		
		if(StringUtils.isNotEmpty(separator)){			
			int checkCnt = 0;			
			String[] array = val.split(separator);
			
			for(String m : array){
				for(String s : searchArray){
					if(m.indexOf(s) > -1){
						checkCnt++;
						break;
					}
				}
			}
			if(array.length == checkCnt)result = false;
		}else{
			for(String s : searchArray){
				if(val.indexOf(s) > -1){
					result =false;
					break;
				}
			}
		}
		return result;
	}
}
