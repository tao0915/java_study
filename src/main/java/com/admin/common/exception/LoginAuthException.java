package com.admin.common.exception;

import java.util.List;

@SuppressWarnings("serial")
public class LoginAuthException extends RuntimeException{

private String message = "";
private List<String> messageList;
private int resultCode = 500;
private int statusCode = 200;


private String redirectUrl;					//로그인 성공시 이동할 페이지				
private String loginUrl;					//로그인 페이지


public LoginAuthException(){}
	
	public LoginAuthException(String message){
		this.setMessage(message);
	}
	
	public LoginAuthException(String message,  String redirect){
		this.setMessage(message);
		this.setRedirectUrl(redirect);
	}
	
	public LoginAuthException(String message, int resultCode){
		this.setMessage(message);
		this.setResultCode(resultCode);
	}
	
	public LoginAuthException(int statusCode){
		this.setStatusCode(statusCode);
	}

	public LoginAuthException(List<String> messageList){
		this.setMessageList(messageList);
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
}
