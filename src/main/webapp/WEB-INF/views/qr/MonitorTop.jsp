<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page session="true"%>
<c:set var="contextPath" value="<%= request.getContextPath()%>"/>
<c:set var="lastUpdate" value="20220923"/>

<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0);
	if (request.getProtocol().equals("HTTP/1.1")) {
	response.setHeader("Cache-Control", "no-cache");
	}
%>
<!doctype html> 


<html dir="ltr">
<head>

<script async src="https://www.googletagmanager.com/gtag/js?id=UA-138883721-1"></script>

<script>
	window.dataLayer = window.dataLayer || [];
	function gtag(){dataLayer.push(arguments);}
	gtag('js', new Date());
	gtag('config', 'UA-138883721-1');
</script>
	
	<title>스마트 관리 시스템</title>
	<meta data-n-head="true" name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
  	
  	<!-- 파비콘 -->
	<link rel="icon" href="images/ss.ico" type="image/x-icon"/>
	<link rel="shortcut icon" href="images/ss.ico" type="image/x-icon"/>
  	
  	<!-- 공용 -->
  	<script type="text/javascript" src="js/jquery-1.12.4.min.js" ></script>
	<script type="text/javascript" src="js/jquery.plugin.js"></script>
  	
	<link rel="stylesheet" href="css/bootstrap4.1/bootstrap.css" media="screen">
	<link rel="stylesheet" href="css/jquery.datepick.css" type="text/css" >
	<link rel="stylesheet" href="css/jquery.timepicker.css" type="text/css" >
	<link rel="stylesheet" href="css/bootstrap-table.css?s=1">
	
	<link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.21.3/dist/extensions/group-by-v2/bootstrap-table-group-by.css">
	<link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.21.3/dist/bootstrap-table.min.css">
	<link rel="stylesheet" href="https://unpkg.com/placeholder-loading/dist/css/placeholder-loading.min.css">
	
	<script src="https://unpkg.com/bootstrap-table@1.21.3/dist/bootstrap-table.min.js"></script>
	<script src="https://unpkg.com/bootstrap-table@1.21.3/dist/extensions/group-by-v2/bootstrap-table-group-by.min.js"></script>
	
	<link rel="stylesheet" href="fontawesome/css/all.css">	
	
	<script type="text/javascript" src="js/jquery.datepick.js"></script> 
	<script type="text/javascript" src="js/jquery.datepick-ko.js"></script>
	<script type="text/javascript" src="js/jquery.timepicker.js"></script>
	<script type="text/javascript" src="js/bootstrap-table-ko-KR.js"></script>	  	
 	<script type="text/javascript" src="js/bootstrap-table.js"></script>
	<script type="text/javascript" src="js/bootstrap-table-filter.js?s=1"></script>
	<script type="text/javascript" src="js/bootstrap-table-ko-KR.js"></script>
	<script type="text/javascript" src="js/vue_2.0.3.js"></script>
	<script type="text/javascript" src="js/basic.js"></script>
	<script type="text/javascript" src="js/bootstrap4.1/bootstrap.bundle.js"></script>	 	  	
  	
  	<!-- login페이지외 나머지 적용 -->

<script>	
 	
 	$(document).ready(function() {
	
 	});
 
	
</script>

<style>

</style>

</head>

<body> 
	<!-- [ HEADER ] -->
	<div id="__top_header">	
		<!-- 큰 화면 상단바 -->
		<div id="lg-top-header" class="d-none d-lg-flex" title="큰 화면 상단바 (사용자 정보 표시 필요)">
			<div id="lg-top-menu-seal-logo">
				<img class="logo-seal" src="images/logo/logo_seal_white.png" style="height: 100%;">
		 	</div>
		 	<div id="lg-title">
		 		P5-PJT 그린동 기술인 출역
		 	</div>
		</div>
		<!-- 작은 화면 상단바 -->
		<div id="sm-top-header" class="d-lg-none" title="작은 화면 상단바">	
		 	<div id="sm-top-menu-seal-logo">
		 		<img class="logo-seal" src="images/logo/logo_seal_white.png" style="width: 200px;">
		 	</div>
		 	<div id="sm-title">
		 		P5-PJT 그린동 기술인 출역
		 	</div>
		</div>
	</div>	
	
	<div id="__cotent_box">
		<div id="lg-content-box" class="flex-box">
	
		<div id="main_content">