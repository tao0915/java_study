package com.admin.common.exception;

import java.util.List;

@SuppressWarnings("serial")
public class BadRquestException extends RuntimeException{

private String message = "";
private List<String> messageList;
private int resultCode = 100;
private int statusCode = 200;

//에러시 이동할 페이지
private String redirectUrl;	


public BadRquestException(){}
	
	public BadRquestException(String message){
		this.setMessage(message);
	}
	
	public BadRquestException(String message,  String redirect){
		this.setMessage(message);
		this.setRedirectUrl(redirect);
	}
	
	public BadRquestException(String message, int resultCode){
		this.setMessage(message);
		this.setResultCode(resultCode);
	}
	
	public BadRquestException(int statusCode){
		this.setStatusCode(statusCode);
	}

	public BadRquestException(List<String> messageList){
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
}
