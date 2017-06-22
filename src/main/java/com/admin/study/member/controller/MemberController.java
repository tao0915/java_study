package com.admin.study.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.admin.study.member.service.MemberService;

@Controller
public class MemberController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/admin" , method = {RequestMethod.GET,RequestMethod.POST})
	public String adminList(@RequestParam Map<String, Object> params, ModelMap map) throws Exception {
		
		System.out.println("adminList");
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<Map<String, Object>> memberList = memberService.getMemberList("");
		int totalCount = memberService.getTotalCount();
		result.put("list", memberList);
		result.put("page", 1);
		result.put("totalCount", totalCount);
		
		map.addAttribute("result", result);
		
		return "admin/adminList.tiles";
	}
	
	@RequestMapping(value = "/adminForm" , method = {RequestMethod.GET,RequestMethod.POST})
	public String adminForm(@RequestParam Map<String, Object> params, ModelMap map) throws Exception {
		map.addAttribute("mode", "insert");
		
		ArrayList<Map<String, Object>> authList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> authMap1 = new HashMap<String, Object>();
		Map<String, Object> authMap2 = new HashMap<String, Object>();
		
		authMap1.put("AUTH_ID", "admin");
		authMap1.put("AUTH_NM", "관리자");
		
		authList.add(authMap1);
		
		authMap2.put("AUTH_ID", "member");
		authMap2.put("AUTH_NM", "일반회원");
		
		authList.add(authMap2);
		
		map.addAttribute("authList", authList);
		
		return "admin/adminForm.tiles";
	}
	
	@RequestMapping(value = "/idCheck" , method = {RequestMethod.GET,RequestMethod.POST})
	public void idCheck(@RequestParam Map<String, Object> params, ModelMap map) throws Exception {
		String id = StringUtils.defaultString((String)params.get("ADMIN_ID"), "");
		
		int countId = memberService.getCountId(id);
		
		System.out.println("countId : "+countId);
		
		if(countId > 0){
			map.addAttribute("resultCode", 100);
		} else {
			map.addAttribute("resultCode", 1);
		}
	}
	
	@RequestMapping(value = "/insertMember" , method = {RequestMethod.GET,RequestMethod.POST})
	public void insertMember(@RequestParam Map<String, Object> params, ModelMap map) throws Exception {
		String memberID = StringUtils.defaultString((String)params.get("ADMIN_ID"), "");
		String memberPWD = StringUtils.defaultString((String)params.get("ADMIN_PWD"), "");
		String memberNM = StringUtils.defaultString((String)params.get("ADMIN_NM"), "");
		String orgNM = StringUtils.defaultString((String)params.get("ORG_NM"), "");
		String telNo = StringUtils.defaultString((String)params.get("TEL_NO"), "");
		String mobileNo = StringUtils.defaultString((String)params.get("MOBILE_NO"), "");
		String email = StringUtils.defaultString((String)params.get("EMAIL"), "");
		String authID = StringUtils.defaultString((String)params.get("AUTH_ID"), "");
		
		String adminYN = "N";
		
		if(authID.equals("admin")){
			adminYN = "Y";
		} 
		
		try{
			memberService.addMember(memberID, memberPWD, memberNM, 
					orgNM, telNo, mobileNo, email, adminYN);
			map.addAttribute("resultCode", 1);
			map.addAttribute("redirectUrl", "/admin");
		}catch (Exception e){
			e.printStackTrace();
			map.addAttribute("resultCode", 100);
		}
	}
}
