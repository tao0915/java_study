<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">
	<meta http-equiv="Pragma" content="no-cache">
	<!-- <title><tiles:insertAttribute name="title" ignore="true" /></title> -->
	<title>STUDY ADMIN</title>
    <link type="text/css" rel="stylesheet" href="/resources/css/bootstrap.css" />
    <link type="text/css" rel="stylesheet" href="/resources/css/bootstrap-datepicker.min.css" />
    <link type="text/css" rel="stylesheet" href="/resources/css/common.css" />
    <script type="text/javascript" src="/resources/js/jquery/jquery.js"></script>
	<script type="text/javascript" src="/resources/js/jquery/jquery-ui.min.js"></script>	
	<script type="text/javascript" src="/resources/js/jquery/jquery.form.min.js"></script>
    <script type="text/javascript" src="/resources/js/publ/placeholders.min.js"></script>
	<script type="text/javascript" src="/resources/js/publ/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/publ/bootstrap/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript" src="/resources/js/publ/syntaxHighlighter.min.js"></script>
</head>
<body ng-app="myApp">
	<div class="wrapper">
		<div id="header" class="header"><tiles:insertAttribute name="header" /></div>
		<div class="row-content">
			<div class="lnb-wrap">
				<div id="left" class="left"><tiles:insertAttribute name="left" /></div>
			</div>
			<div class="content-wrap">
				<div id="body" class="body"><tiles:insertAttribute name="body" /></div>
			</div>
		</div>
		<div id="footer" class="footer"><tiles:insertAttribute name="footer" /></div>
	</div>
</body>
</html>
