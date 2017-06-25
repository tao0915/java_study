package com.admin.study.member.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.study.member.dao.MemberDao;



@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;
	
	public List<Map<String, Object>> getAllMemberList(String pageNo, String listSize){
		
		int limitOffset = getLimitOffset(pageNo, listSize);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("LIMIT_OFFSET", limitOffset);
		params.put("LIMIT_SIZE", listSize);
		
		System.out.println("limitOffset : "+limitOffset+", listSize : "+listSize);
		
		return memberDao.selectMemberList(params);
	}
	
	public List<Map<String, Object>> getMemberList(String keyword, String selItem, String pageNo, String listSize){
		
		int limitOffset = getLimitOffset(pageNo, listSize);
	
		if(listSize.equals("-1")){
			limitOffset = 0;
			listSize = "99999";
		}
		
		String where = "";
		
		if(selItem.equals("all")){
			where = "AND (ADMIN_ID='"+keyword+"' OR ADMIN_NM='"+keyword+"')";
		} else if(selItem.equals("name")){
			where = "AND ADMIN_NM='"+keyword+"'";
		} else {
			where = "AND ADMIN_ID='"+keyword+"'";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("LIMIT_OFFSET", limitOffset);
		params.put("LIMIT_SIZE", listSize);
		params.put("WHERE", where);
		
		return memberDao.selectMemberList(params);
	}
	
	public int getCountId(String id){
		if(StringUtils.isEmpty(id)){
			return 0;
		}
		
		String where = String.format("WHERE ADMIN_ID='%s'", id);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("WHERE", where);
		
		return memberDao.selectCount(params);
	}
	
	public int getTotalCount(String keyword, String selItem){
		
		Map<String, Object> params = new HashMap<String, Object>();

		String where = "";
		
		if(!keyword.equals("")){
			if(selItem.equals("all")){
				where += "WHERE (ADMIN_ID='"+keyword+"' OR ADMIN_NM='"+keyword+"')";
			} else if(selItem.equals("name")){
				where += "WHERE ADMIN_NM='"+keyword+"'";
			} else {
				where += "WHERE ADMIN_ID='"+keyword+"'";
			}
		}
		
		params.put("WHERE", where);
		
		return memberDao.selectCount(params);
	}
	
	public int getCount(String keyword, String selItem){
		
		Map<String, Object> params = new HashMap<String, Object>();

		String where = "WHERE DEL_YN='N'";
		
		if(!keyword.equals("")){
			if(selItem.equals("all")){
				where += " AND (ADMIN_ID='"+keyword+"' OR ADMIN_NM='"+keyword+"')";
			} else if(selItem.equals("name")){
				where += " AND ADMIN_NM='"+keyword+"'";
			} else {
				where += " AND ADMIN_ID='"+keyword+"'";
			}
		}
		
		params.put("WHERE", where);
		
		return memberDao.selectCount(params);
	}
	
	public int getDeleteYCount(String keyword, String selItem){
		Map<String, Object> params = new HashMap<String, Object>();
		
		String where = "WHERE DEL_YN='Y'";
		
		if(!keyword.equals("")){
			if(selItem.equals("all")){
				where += " AND (ADMIN_ID='"+keyword+"' OR ADMIN_NM='"+keyword+"')";
			} else if(selItem.equals("name")){
				where += " AND ADMIN_NM='"+keyword+"'";
			} else {
				where += " AND ADMIN_ID='"+keyword+"'";
			}
		}
		
		params.put("WHERE", where);
		
		return memberDao.selectCount(params);
	}
	
	public int getDeleteNCount(String keyword, String selItem){
		Map<String, Object> params = new HashMap<String, Object>();
		
		String where = "WHERE DEL_YN='N'";
		
		if(!keyword.equals("")){
			if(selItem.equals("all")){
				where += " AND (ADMIN_ID='"+keyword+"' OR ADMIN_NM='"+keyword+"')";
			} else if(selItem.equals("name")){
				where += " AND ADMIN_NM='"+keyword+"'";
			} else {
				where += " AND ADMIN_ID='"+keyword+"'";
			}
		}
		
		params.put("WHERE", where);
		
		return memberDao.selectCount(params);
	}
	
	public int addMember(String memberID, String memberPWD, String memberNM
			, String orgNM, String telNo, String mobileNo, String email
			, String adminYN ){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("MEMBER_ID", memberID);
		params.put("MEMBER_PWD", memberPWD);
		params.put("MEMBER_NM", memberNM);
		params.put("ORG_NM", orgNM);
		params.put("TEL_NO", telNo);
		params.put("EMAIL", email);
		params.put("ADMIN_YN", adminYN);
		
		return memberDao.insertMember(params);
	}
	
	public int deleteMember(String[] checkArray){
		Map<String, Object> memberId = new HashMap<String, Object>();
		
		for(int i = 0; i < checkArray.length; i++){
			memberId.put(String.valueOf(i), checkArray[i]);
			
			System.out.println("checkArray : "+memberId.get(String.valueOf(i)));
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("MEMBER_ID", memberId);
		
		return memberDao.deleteMember(params);
	}
	
	public int getLimitOffset(String pageNo, String listSize){
		return (Integer.parseInt(pageNo) - 1) * Integer.parseInt(listSize);
	}
}