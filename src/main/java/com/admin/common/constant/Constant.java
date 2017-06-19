package com.admin.common.constant;

import org.apache.commons.lang.StringUtils;

import com.admin.common.comm.util.PropertyMap;


/**
 * ����  ������Ƽ ����
 *
 */
public class Constant {
	
	
	//DB�� ���ǵ� ������Ƽ�� ������.
	public static PropertyMap propertyMap = new PropertyMap();
	
	
	//�׽�Ʈ ���� ����
	public static boolean IS_TEST = false;
	
	//���� ���� ���� (PC,BO,MO)
	public static String SERVICE_DIV;
	
	
	/**
	 * ���� �ʱⰪ ����
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
