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
	
	public int getCountId(String id){
		if(StringUtils.isEmpty(id)){
			return 0;
		}
		
		String where = String.format("WHERE ADMIN_ID='%s'", id);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("WHERE", where);
		
		return memberDao.selectCount(params);
	}
	
	public int getTotalCount(){
		
		Map<String, Object> params = new HashMap<String, Object>();
		
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
	
	public int getLimitOffset(String pageNo, String listSize){
		return (Integer.parseInt(pageNo) - 1) * Integer.parseInt(listSize);
	}
}