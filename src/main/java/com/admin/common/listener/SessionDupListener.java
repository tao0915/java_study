package com.admin.common.listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.admin.common.constant.Constant;


@WebListener
public class SessionDupListener implements HttpSessionBindingListener {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * 현재 로그인 되어 있는 로그인 ID 정보를 저장함.
	 */
	private static Map<String, LoginUser> loginIdMap = new HashMap<String, LoginUser>();

	
	/**
	 * 로그인ID 중복 체크
	 * - 로그인할 ID가 현재 sessionId로 등록되어 있지 않을 경우 중복 로그인 메세지 반환
	 * @param sessionId	:현재 sessionId
	 * @param id		:로그인할 id
	 * @return
	 */
	public static String getIdChk(String sessionId, String id){		
		if(id != null){
			if(SessionDupListener.loginIdMap.containsKey(id)){
				LoginUser loginUser = SessionDupListener.loginIdMap.get(id);
				if(!StringUtils.equals(sessionId, loginUser.getSessionId())){
					return "중복 로그인을 허용 하지 않습니다.";
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void valueBound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		logger.debug("***********************valueBound ***********************");
		
		if(event.getSession().getAttribute(Constant.propertyMap.get("key_4")) != null){				
			Map<String, Object> userInfo = (Map<String, Object>)event.getSession().getAttribute(Constant.propertyMap.get("key_4"));
			LoginUser loginUser = new LoginUser();
			loginUser.setLoginId((String)userInfo.get("ID"));
			loginUser.setSessionId(event.getSession().getId());
			SessionDupListener.loginIdMap.put((String)userInfo.get("ID"), loginUser);
		}
		
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		logger.debug("***********************valueUnbound *********************** userId  ");
		
		SessionDupListener.removeLoginId(event.getSession());
	}
	
	/**
	 * 로그인 아이디 삭제
	 * @param currtSession
	 */
	@SuppressWarnings("unchecked")
	public static void removeLoginId(HttpSession currtSession){
		
		try{
			if(currtSession.getAttribute(Constant.propertyMap.get("key_4")) != null){
				Map<String, Object> userInfo = (Map<String, Object>)currtSession.getAttribute(Constant.propertyMap.get("key_4"));
				SessionDupListener.loginIdMap.remove((String)userInfo.get("ID"));
			}
		}catch(Exception e){
			e.printStackTrace();			
			 Iterator<String> it =  SessionDupListener.loginIdMap.keySet().iterator();
			 while (it.hasNext()) {
				String id = it.next();
				LoginUser loginUser = SessionDupListener.loginIdMap.get(id);
				if(StringUtils.equals(currtSession.getId(), loginUser.getSessionId())){
					SessionDupListener.loginIdMap.remove(loginUser.getLoginId());
					break;
				}
			}
		}
	}
}

class LoginUser {
	private String loginId;
	private String sessionId;
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
