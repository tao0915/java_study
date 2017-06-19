package com.admin.common.comm.util;

import java.util.HashMap;
/**
 * 프로퍼티값을 관리함.
 *
 */
public class PropertyMap extends HashMap<String, String>{
	
	public String getMsg(String key, Object ... arg){
		if(super.get(key) == null) return null;
		String value = super.get(key);
		if(arg != null){
			for(int i=0; i<arg.length; i++){
				value = value.replace("{"+i+"}", arg[i].toString());
			}
		}
		return value;
	}
}
