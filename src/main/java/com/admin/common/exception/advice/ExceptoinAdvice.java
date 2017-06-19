package com.admin.common.exception.advice;


import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.admin.common.constant.Constant;
import com.admin.common.exception.BadRquestException;
import com.admin.common.exception.LoginAuthException;

@Controller
@ControllerAdvice
public class ExceptoinAdvice {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@ExceptionHandler(BadRquestException.class)
	public ModelAndView badRquestException(BadRquestException ex, HttpServletRequest req, HttpServletResponse rep){
		logger.debug("badRquestException    ============  init "+ex.getRedirectUrl());
		///error/defaultException
		String url = StringUtils.defaultString(ex.getRedirectUrl(), "").trim();
		if("".equals(url)){ url = "/error/defaultException"; }
		
		logger.debug("badRquestException    ============  init url "+url);
		ModelAndView model = new ModelAndView();
		
		if(StringUtils.contains(req.getHeader("Accept"), "application/json")){			//json 형태일 경우
			model.addObject("result", ex);
		}else{
			if(url.indexOf("/error/defaultException") == -1){
				if(ex.getStatusCode() == 200){
					model.setViewName("redirect:"+url);
				}
			}else{
				model.addObject("result", ex);
				model.setViewName(url);
			}			
		}
		


		if(ex.getStatusCode() != 200){
			rep.setStatus(ex.getStatusCode());
			model.setViewName("/error/"+ex.getStatusCode());
		}
		
		return model;
	}
	
	@ExceptionHandler(LoginAuthException.class)
	public ModelAndView loginAuthException(LoginAuthException ex, HttpServletRequest req, HttpServletResponse rep){
		logger.debug("LoginAuthException    ============  init "+ex.getRedirectUrl());
		
		logger.debug("LoginAuthException    ============  init "+req.getRequestURI());
		
		logger.debug("LoginAuthException    ============  init "+req.getServerName());
		
		
		Enumeration<String> enumeration = req.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String string = (String) enumeration.nextElement();
			logger.debug("enumeration    ============  init "+string+"    header "+req.getHeader(string));
		}
		
		
		///error/defaultException
		String url = StringUtils.defaultString(ex.getRedirectUrl(), "").trim();
		String loginUrl = "/admin/login";
		
		if(StringUtils.equals(Constant.SERVICE_DIV, "mo")){
			loginUrl = "/mo/login";
		}else if(StringUtils.equals(Constant.SERVICE_DIV, "pc")){
			loginUrl = "/pc/login";
		}
		
		
		if("".equals(url) && req.getHeader("referer") != null){
			String referer = req.getHeader("referer");
			url = StringUtils.substring(referer, referer.indexOf(req.getServerName())+req.getServerName().length()); 
		}
		
		ModelAndView model = new ModelAndView();
		if(StringUtils.contains(req.getHeader("Accept"), "application/json")){			//json 형태일 경우
			ex.setLoginUrl(loginUrl);
			ex.setRedirectUrl(url);
			model.addObject("result", ex);
		}else{
			if(url.indexOf("/error/defaultException") == -1){
				if(ex.getStatusCode() == 200){
					model.setViewName("redirect:"+loginUrl);
				}
			}else{
				model.addObject("result", ex);
				model.setViewName(loginUrl);
			}			
		}
		


		if(ex.getStatusCode() != 200){
			rep.setStatus(ex.getStatusCode());
			model.setViewName("error/"+ex.getStatusCode());
		}
		
		return model;
	}
	
	
	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(Exception ex, HttpServletRequest req, HttpServletResponse rep){
		logger.debug("exceptoinHandler    ============  init");
		ModelAndView model = new ModelAndView("/error/defaultException");
		BadRquestException userException  = new BadRquestException();
		userException.setStackTrace(ex.getStackTrace());
		userException.setMessage("서버 오류 입니다.");
		userException.setResultCode(100);
		model.addObject("result", userException);
		return model;
	}
	
    
    @RequestMapping(value = "/error/{path}",  method = {RequestMethod.GET,RequestMethod.POST})
    public String error(@PathVariable("path") String path, HttpServletRequest req) {
    	 logger.debug("=====================================  error "+req.getRequestURI());
    	return req.getRequestURI(); 
    } 
}
