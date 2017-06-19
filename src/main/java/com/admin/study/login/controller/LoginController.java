package com.admin.study.login.controller;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import org.apache.commons.lang.StringUtils;

import com.admin.common.constant.Constant;
import com.admin.common.exception.BadRquestException;
import com.admin.common.listener.SessionDupListener;
import com.admin.study.login.service.LoginService;



@Controller
@RequestMapping(value = "/admin")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LoginService loginService;
	/**
     * Simply selects the home view to render by returning its name.
     */
	
	@RequestMapping(value = "/login" , method = {RequestMethod.GET,RequestMethod.POST})
    public String login(@RequestParam Map<String,Object> param , HttpSession session , Locale locale, ModelMap map) throws Exception{
        logger.info("Welcome login! {} , {} , {}", session.getId() , locale.getLanguage(), param.toString());
        
    	return "login";
    }
	@RequestMapping(value = "/logout", method = {RequestMethod.GET,RequestMethod.POST})
    public void logout(HttpSession session, HttpServletRequest req, HttpServletResponse rep) throws Exception{

		
		rep.sendRedirect("/admin/login");
    }
}
