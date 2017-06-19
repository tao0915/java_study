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
 * ���ڿ� ���� ��ƿ
 *
 */
public class StringUtil {
	private static final Logger log = LoggerFactory.getLogger(StringUtil.class);
	
	
	public static void main(String[] args) throws Exception{
	}
	
	
	
	/**
	 * �ش� ������Ʈ�� JSON ���ڿ��� ��ȯ��.
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
	 * �ش� json ���Ŀ� ���ڿ��� List<Map<String, Object>> ���·� ��ȯ��
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
	 * �ش� ���� ��ι��ڿ����� ���� Ȯ���� ���ڿ��� ��ȯ��
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
	 * �ش� ���� ��ŭ�� ���ڿ��� �������� ��ȯ��.
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
	 * �ش� �н������� ��ȿ���� üũ��.
	 * 	- ��ȿ�� üũ ��Ģ�� ������/�ҹ��� + ���� + Ư������ + 8~20�ڸ�
	 * @param pw
	 * @return
	 */
	public static boolean isValidatePassword(String pw){
		String regex	= "((?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%!]).{8,20})";
		return isValidatePassword(pw, regex);
	}

	
	/**
	 * �ش� �н������� ��ȿ���� üũ��.
	 * @param pw
	 * @param regex
	 * @return
	 */
	public static boolean isValidatePassword(String pw, String regex){
		return Pattern.matches(regex, pw);
	}
	
	
	/**
	 * �ش� �����߿� ���� �ִ��� ���θ� üũ��
	 * 	- ���� �ִ� �׸��� �ε����� ��ȯ��.
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
	 * ��¥ ���� �����Ϳ��� ���ڿ��� ���ڿ��� �����ؼ� ��ȯ��.
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
	 * ���ڸ� ###,### �������� ��ȯ
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
	 * ���ڿ��� �ش� ���Ͽ� �°� ��ȯ (ex: stringDateFormat("20150119143459","yyyy.mm.dd") -> 2015.01.19
	 * @param value
	 * @param pattern
	 * @return
	 */
	public static String stringDateFormat(String value, String pattern){
		String tmpStr = "";		
        if((value != null) && !value.equals("") && (pattern != null) && !pattern.equals(""))
        {            
        	if(Pattern.matches("[0-9]+", value)){			//���ڸ� ���        		
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
	 * ��ȭ��ȣ ��ȯ (xx-xxx-xxx, xxx-xxx-xxxx, xxx-xxxx-xxxx)
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
	 * ��ȭ��ȣ "-" ���� �迭 ��ȯ
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
	 * email "@" ���� �迭 ��ȯ
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
	 * �����ȣ ��ȯ (123456 => 123-456)
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
	 * message�� {?}�ش簪 ġȯ 
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
	 * �ش簪�� �迭�� �ִ��� ���� Ȯ��
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
	 * �ش簪�� �迭�� ������ ���� üũ 
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
