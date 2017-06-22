package com.admin.study.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
	
	private final int pageMaxSize = 3;
	
	@RequestMapping(value = "/admin" , method = {RequestMethod.GET,RequestMethod.POST})
	public String adminList(@RequestParam Map<String, Object> params, ModelMap map) throws Exception {
		
//		Map<String, Object> result = new HashMap<String, Object>();
		
//		result.put("page", 1);
//		result.put("totalCount", 15);
//		result.put("beginPage", 1);
//		result.put("endPage", 5);
//		
//		map.addAttribute("result", result);
		
		Select(params, map);
		
		return "admin/adminList.tiles";
	}
	
	@RequestMapping(value = "/select" , method = {RequestMethod.GET})
    public String Select(@RequestParam Map<String,Object> params , ModelMap map) throws Exception{
		String page = StringUtils.defaultString((String)params.get("page"), "");
		String listSize = StringUtils.defaultString((String)params.get("listSize"), "");
		
		if(page.equals("")){
			page = "1";
		}
		
		if(listSize.equals("")){
			listSize="3";
		}
		
		int nPageNo = Integer.parseInt(page);
		int nListSize = Integer.parseInt(listSize);
//		int beginPage = (int)Math.ceil((float)nPageNo / pageMaxSize);
		int beginPage = (((nPageNo - 1) / pageMaxSize) * pageMaxSize)+1;
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<Map<String, Object>> memberList = null;
		int totalCount = 0;
		try{
			memberList = memberService.getAllMemberList(page, listSize);
			totalCount = memberService.getTotalCount();
		} catch (Exception e){
			e.printStackTrace();
			map.addAttribute("resultCode", 100);
			return "admin/adminList.tiles";
		}
		
		int endPage = (beginPage+pageMaxSize)-1;
		int lastPage = (int) Math.ceil((float)totalCount/nListSize);
		int prevPage = ((nPageNo-1)/nListSize)*nListSize;
		int nextPage = ((nPageNo-1)/nListSize+1)*nListSize+1;
		
		if(prevPage <= 0){
			prevPage = 1;
		}
		
		if(endPage > lastPage){
			endPage = lastPage;
		}
		
		result.put("list", memberList);
		result.put("page", page);
		result.put("totalCount", totalCount);
		result.put("beginPage", beginPage);
		result.put("endPage", endPage);
		result.put("lastPage", lastPage);
		result.put("prevPage", prevPage);
		result.put("nextPage", nextPage);
		
		map.addAttribute("result", result);
		map.addAttribute("resultCode", 1);
		
		return "admin/adminList.tiles";
//		return "redirect:/admin";
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


