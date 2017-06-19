package com.admin.study.common.interceptor;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.admin.study.common.annotation.ExceptBackUrl;
import com.admin.study.common.annotation.ExceptMenuAuthChk;
import com.admin.study.system.service.SystemService;
import com.admin.common.constant.Constant;
import com.admin.common.exception.BadRquestException;
import com.admin.common.exception.LoginAuthException;

import java.lang.reflect.Method;

public class Interceptor extends  HandlerInterceptorAdapter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SystemService systemService;
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		logger.debug("seung  BoInterceptor ================================= start  serverName"+request.getServerName());
		
		
		//서버 구분값 처음 진입시 한번만 정의
		if(Constant.SERVICE_DIV == null){ new Constant().initService(request.getServerName()); }
		

		Method method = ((HandlerMethod) handler).getMethod();
		
		//로그인 후 세션이 끊겼을 경우 재로그인 후 이동할 ajax 페이지
		ExceptBackUrl exceptBackUrl =  method.getAnnotation(ExceptBackUrl.class);			//해당 어노테이션을 메소드에 정의 해놨을 경우는 url 셋팅 제외
		if(exceptBackUrl == null){ request.setAttribute("backUrl", request.getRequestURL());}
		
		logger.debug("seung====================================== url  getRequestURI  "+request.getRequestURI());
		logger.debug("seung====================================== url   getRequestURL "+request.getRequestURL());
		
		//로그인 여부 체크(세션 값이 없을 경우 비로그인으로 간주)
		Map<String, Object> adminInfo = (Map<String, Object>)request.getSession().getAttribute("adminInfo");
		if(adminInfo == null){ throw new LoginAuthException(); }
		
		request.setAttribute("userInfo", adminInfo);
				
		ExceptMenuAuthChk exceptMenuAuthChk =  method.getAnnotation(ExceptMenuAuthChk.class);			//메뉴 접근 제한 제외
		System.out.println(SystemService.authMenuUrlListMap.toString());
		if(exceptMenuAuthChk == null){							
			if(!SystemService.authMenuUrlListMap.containsKey(adminInfo.get("AUTH_ID"))){ throw new BadRquestException("권한 정보가 없습니다."); 
			}else{
				if(SystemService.authMenuUrlListMap.get(adminInfo.get("AUTH_ID")).get(request.getRequestURI().trim()) == null){throw new BadRquestException("접근 권한이 없습니다.");}
			}
		}
		
		
		String menuId = request.getParameter("MENU_ID");
		if(StringUtils.isNotEmpty(menuId)){
			request.setAttribute("PARAM_MENU_ID", menuId);
		}
		
		logger.debug("seung ================== bo ============ getContentType  " +request.getContentType());
		
		//대메뉴 반환
		if(request.getContentType() == null){			
			
			//관리자 접근 메뉴 권한 체크
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ADMIN_ID",  adminInfo.get("ADMIN_ID"));
			param.put("MENU_UP_ID", "ROOTMENU");
			param.put("USE_YN", "Y");
			request.setAttribute("headerMenuList", this.systemService.selectAuthSubMenuList(param));	
		}
		
    	return super.preHandle(request, response, handler);
	}


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
    	if(handler instanceof HandlerMethod){
    	}
    	super.postHandle(request, response, handler, modelAndView);
    }
}
