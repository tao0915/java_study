package com.admin.common.constant;

import org.apache.commons.lang.StringUtils;

import com.admin.common.comm.util.PropertyMap;


/**
 * 공통  프로퍼티 정의
 *
 */
public class Constant {
	
	
	//DB에 정의된 프로퍼티를 저장함.
	public static PropertyMap propertyMap = new PropertyMap();
	
	
	//테스트 서버 여부
	public static boolean IS_TEST = false;
	
	//서버 구분 여부 (PC,BO,MO)
	public static String SERVICE_DIV;
	
	
	/**
	 * 서비스 초기값 셋팅
	 */
	public void initService(String serverName){
		String service = StringUtils.defaultString(serverName, "admin");
		if(!StringUtils.equals(service, "admin")){
			if(StringUtils.startsWith(service, "pc.")){
				service = "pc";
			}else if(StringUtils.startsWith(service, "mo.")){
				service = "mo";
			}else if(StringUtils.startsWith(service, "admin")){
				service = "admin";
			}
			
			Constant.IS_TEST = serverName.indexOf(".test.") != -1 ? true : false;
			Constant.SERVICE_DIV = service;
		}else{
			Constant.SERVICE_DIV = service;
		}
	}
}
