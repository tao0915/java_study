package com.admin.common.comm.util;

/**
 * ���� ���� ��ƿ
 * @author seung
 *
 */
public class NumberUtil {
	/**
	 * �ش� ������ ��� 0������ üũ��.
	 * 	- 0�� �׸��� index���� ��ȯ��.
	 * 	- ��� 0�� �ƴѰ�� -1���� ���Ͻ�Ŵ.
	 * @param val : üũ�� ����...
	 * @return
	 */
	public static int isZero(int ... val){
		for(int i=0 ; i < val.length ; i++){
			if(val[i] == 0){ return i; }
		}
		
		return -1;
	}	
	
	/**
	 * �ش� ������ ��� �������� üũ
	 * @param val
	 * @return
	 */
	public static boolean isNumeric(String ... val){
		for(int i=0; i < val.length ; i++) {
			if(!val[i].matches("[0-9]+")) { return false;}
		}
		return true;
	}
	
}
