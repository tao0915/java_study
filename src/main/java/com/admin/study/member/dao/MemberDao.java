package com.admin.study.member.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.admin.common.mybatis.QueryMapper;

@Repository
public class MemberDao extends QueryMapper {
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> selectMemberList(Map<String, Object> params){
		return (List<Map<String,Object>>)super.selectList("member.selectMemberList", params);
	}
	
	public int selectCount(Map<String, Object> params){
		return (Integer)super.selectOne("member.selectCount", params);
	}
	
	public int insertMember(Map<String, Object> params){
		return (Integer)super.insert("member.insertMember", params);
	}
	
}