package com.admin.study.member.controller;

import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@RequestMapping(value = "/admin" , method = {RequestMethod.GET,RequestMethod.POST})
	public String adminList(@RequestParam Map<String, Object> params, ModelMap map) throws Exception {
		//TODO
		return "admin/adminList.tiles";
	}
	
	@RequestMapping(value = "/adminForm" , method = {RequestMethod.GET,RequestMethod.POST})
	public String adminForm(@RequestParam Map<String, Object> params, ModelMap map) throws Exception {
		//TODO
		return "admin/adminForm.tiles";
	}
	
}
