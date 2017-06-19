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
		
		
		//���� ���а� ó�� ���Խ� �ѹ��� ����
		if(Constant.SERVICE_DIV == null){ new Constant().initService(request.getServerName()); }
		

		Method method = ((HandlerMethod) handler).getMethod();
		
		//�α��� �� ������ ������ ��� ��α��� �� �̵��� ajax ������
		ExceptBackUrl exceptBackUrl =  method.getAnnotation(ExceptBackUrl.class);			//�ش� ������̼��� �޼ҵ忡 ���� �س��� ���� url ���� ����
		if(exceptBackUrl == null){ request.setAttribute("backUrl", request.getRequestURL());}
		
		logger.debug("seung====================================== url  getRequestURI  "+request.getRequestURI());
		logger.debug("seung====================================== url   getRequestURL "+request.getRequestURL());
		
		//�α��� ���� üũ(���� ���� ���� ��� ��α������� ����)
		Map<String, Object> adminInfo = (Map<String, Object>)request.getSession().getAttribute("adminInfo");
		if(adminInfo == null){ throw new LoginAuthException(); }
		
		request.setAttribute("userInfo", adminInfo);
				
		ExceptMenuAuthChk exceptMenuAuthChk =  method.getAnnotation(ExceptMenuAuthChk.class);			//�޴� ���� ���� ����
		System.out.println(SystemService.authMenuUrlListMap.toString());
		if(exceptMenuAuthChk == null){							
			if(!SystemService.authMenuUrlListMap.containsKey(adminInfo.get("AUTH_ID"))){ throw new BadRquestException("���� ������ �����ϴ�."); 
			}else{
				if(SystemService.authMenuUrlListMap.get(adminInfo.get("AUTH_ID")).get(request.getRequestURI().trim()) == null){throw new BadRquestException("���� ������ �����ϴ�.");}
			}
		}
		
		
		String menuId = request.getParameter("MENU_ID");
		if(StringUtils.isNotEmpty(menuId)){
			request.setAttribute("PARAM_MENU_ID", menuId);
		}
		
		logger.debug("seung ================== bo ============ getContentType  " +request.getContentType());
		
		//��޴� ��ȯ
		if(request.getContentType() == null){			
			
			//������ ���� �޴� ���� üũ
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
