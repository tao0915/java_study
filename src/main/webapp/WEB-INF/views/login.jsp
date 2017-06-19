<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>포트폴리오</title>
<link type="text/css" rel="stylesheet" href="/resources/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="/resources/css/bootstrap-datepicker.min.css" />
<link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/shCoreDefault.css" />
<script type="text/javascript" src="/resources/js/jquery/jquery.js"></script>


<script type="text/javascript">
	
</script>
</head>
<body class="loginForm">
	<div class="wrapper login">
	    <div class="login-wrap">
	        <span class="logo"></span>
	        <span class="txt">통합 관리자에 오신 것을 환영합니다.</span>
	        <div class="login-box-wrap">
	          <fieldset class="login-box">
	              <legend>Login Adminstrator</legend>
	              <form id="loginForm" name="loginForm">
				      <input type="hidden" name="redirectUrl" value="${param.redirectUrl}"/>
		              <ul class="login-input">
		                  <li><span class="tit">아이디</span><input type="text" class="int required" name="userId" value="${param.userId}"  title="아이디"/></li>
		                  <li><span class="tit">비밀번호</span><input type="password" class="int password required" name="password" title="패스워드"/></li>
		              </ul>
	             </form>
	          </fieldset>
	          <a href="/admin" class="btn-login">로그인</a>
	        </div>
	        <span class="copy-txt">Copyright ⓒ 2017 STUDY. All Rights Reserved.</span>
	    </div>
	</div>
</body>
</html>
