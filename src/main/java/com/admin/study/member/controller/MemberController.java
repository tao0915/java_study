package com.admin.study.member.controller;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.admin.study.member.service.MemberService;
import com.admin.study.util.Email;
import com.admin.study.util.Excel;

import javaxt.utils.Array;

@Controller
public class MemberController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MemberService memberService;
	
	private final int pageMaxSize = 3;
	
	@Autowired
	private Email emailSender;
	
	@Autowired
	private Excel exelWirter;
	
	@RequestMapping(value = "/admin" , method = {RequestMethod.GET,RequestMethod.POST})
	public String adminList(@RequestParam Map<String, Object> params, ModelMap map) throws Exception {
		Select(params, map);
		
		return "admin/adminList.tiles";
	}
	
	@RequestMapping(value = "/select" , method = {RequestMethod.GET, RequestMethod.POST})
    public String Select(@RequestParam Map<String,Object> params , ModelMap map) throws Exception{
		System.out.println("Select~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		String page = StringUtils.defaultString((String)params.get("page"), "");
		String listSize = StringUtils.defaultString((String)params.get("listSize"), "");
		String selectItem = StringUtils.defaultString((String)params.get("selectitem"), "");
		
		//get방식 한글깨짐 있음 톰캣의 server.xml의 Connector에 URIEncoding="UTF-8" 추가
		//예)<Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443" URIEncoding="UTF-8"/>
		//<Connector port="8009" protocol="AJP/1.3" redirectPort="8443" URIEncoding="UTF-8"/>
		// 두곳 모두 추가
		String keyword = StringUtils.defaultString((String)params.get("keyword"), "");
		
		System.out.println("keyword : "+keyword+", selItem : "+selectItem);
		
		if(page.equals("")){
			page = "1";
		}
		
		if(listSize.equals("")){
			listSize="3";
		}
		
		int nPageNo = Integer.parseInt(page);
		int nListSize = Integer.parseInt(listSize);
		int beginPage = (((nPageNo - 1) / pageMaxSize) * pageMaxSize)+1;
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<Map<String, Object>> memberList = null;
		int totalCount = 0;
		int resultCount = 0;
		int delYcount = 0;
		int delNcount = 0;
		try{
			if(keyword.equals("")){
				memberList = memberService.getAllMemberList(page, listSize);
			} else {
				memberList = memberService.getMemberList(keyword, selectItem, page, listSize);
			}
			
			totalCount = memberService.getTotalCount(keyword, selectItem);
			resultCount = memberService.getCount(keyword, selectItem);
			delYcount = memberService.getDeleteYCount(keyword, selectItem);
			delNcount = memberService.getDeleteNCount(keyword, selectItem);
		} catch (Exception e){
			e.printStackTrace();
			map.addAttribute("resultCode", 100);
			return "admin/adminList.tiles";
		}
		
		int endPage = (beginPage+pageMaxSize)-1;
		int totalPage = (int) Math.ceil((float)resultCount/nListSize);
		int prevPage = ((nPageNo-1)/nListSize)*nListSize;
		int nextPage = ((nPageNo-1)/nListSize+1)*nListSize+1;
		
		if(prevPage <= 0){
			prevPage = 1;
		}
		
		if(endPage > totalPage){
			endPage = totalPage;
		}
		
		if(nextPage > totalPage){
			nextPage = totalPage;
		}
		
		result.put("list", memberList);
		result.put("page", page);
		result.put("totalCount", totalCount);
		result.put("resultCount", resultCount);
		result.put("delYcount", delYcount);
		result.put("delNcount", delNcount);
		result.put("listSize", listSize);
		result.put("beginPage", beginPage);
		result.put("endPage", endPage);
		result.put("totalPage", totalPage);
		result.put("prevPage", prevPage);
		result.put("nextPage", nextPage);
		
		
		map.addAttribute("result", result);
		map.addAttribute("resultCode", 1);
		map.addAttribute("currtUrl", "select");
		map.addAttribute("keyword", keyword);
		map.addAttribute("selectItem", selectItem);
		
		
		return "admin/adminList.tiles";
	}
	
	@RequestMapping(value = "/delete" , method = {RequestMethod.GET,RequestMethod.POST})
	public void deleteMember(@RequestParam(value="checkArray[]") String[] checkArray, ModelMap map) throws Exception {
		
		boolean isEmpty = true;
		
		if(checkArray.length != 0){
			isEmpty = false;
		}
		
		if(isEmpty){
			return;
		} else {
			this.memberService.deleteMember(checkArray);
		}
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
		
//		try{
			memberService.addMember(memberID, memberPWD, memberNM, 
					orgNM, telNo, mobileNo, email, adminYN);
			
			map.addAttribute("resultCode", 1);
			map.addAttribute("redirectUrl", "/admin");
			
			throw new Exception();
//		}catch (Exception e){
//			e.printStackTrace();
//			map.addAttribute("resultCode", 100);
//		}
	}
	
	//resources/config/email.properties에 메일주소, 비밀번호 넣고 사용(설정 내용은 추후 다른곳으로 이동 예정)
	@RequestMapping(value = "/sendEmail" , method = {RequestMethod.GET,RequestMethod.POST})
    public void sendEmail(@RequestParam Map<String,Object> params) throws Exception{
		
		String address = StringUtils.defaultString((String)params.get("address"), "");
		String subject = StringUtils.defaultString((String)params.get("subject"), "");
		String content = StringUtils.defaultString((String)params.get("content"), "");
		
        emailSender.SendEmail(address, subject, content);
    }
	
	/*
	 * 조사한 다운로드 방식은 두가지 이다.
	 * 1. server에 저장된 파일을 단순히 client에 보내는 방식 
	 * 2. server에서 파일을 생성하여 저장한 파일을 client에 보내는 방식
	 * 
	 * 유동적인 data를 excel로 저장하여 사용하기 때문에 두번째 방식을 채택
	 */
	@RequestMapping(value = "/excel" , method = {RequestMethod.GET,RequestMethod.POST})
    public void DownloadExcel(HttpServletResponse response, @RequestParam Map<String,Object> param , HttpSession session , Locale locale, ModelMap map) throws Exception{
		String keyword = StringUtils.defaultString((String)param.get("keyword"), "");
		String selectItem = StringUtils.defaultString((String)param.get("selectitem"), "");
		
		String listSize = "-1";
		String page = "0";
		
		//1번 방식-----------
//	    File file = new File("C:/Test","MemberList.xls"); 
//	    return new ModelAndView("downloadView", "downloadFile", file);
		//-----------------
		
		
		//2번 방식----------
		String fileName = "MemberListTest.xls";
		String fileFullPath = "C:/Test/"+fileName; //server 저장 경로
		
		response.setCharacterEncoding("euc-kr");
		response.setContentType("application/octet-stream charset=\"euc-kr\"");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

		List<Map<String, Object>> memberList = memberService.getMemberList(keyword, selectItem, page, listSize);

		//MemberList data excel로 server에 저장 
		exelWirter.write(fileFullPath, memberList);

		FileCopyUtils.copy(new FileInputStream(fileFullPath), response.getOutputStream());
		//-----------------
    }
}



