<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page session="true"%>
<c:set var="contextPath" value="<%= request.getContextPath()%>"/>
<c:set var="lastUpdate" value="20220526"/>

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
		
	<title>공사관리 시스템  	</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  	
	<link rel="icon" href="${contextPath}/images/ss.ico" type="image/x-icon" />
  		<link rel="shortcut icon" href="${contextPath}/images/ss.ico" type="image/x-icon" />
  	
  	<!-- 공용  	-->
  	<script type="text/javascript" src="${contextPath}/js/jquery-1.12.4.min.js" ></script>
  	<script type="text/javascript" src="${contextPath}/js/jquery.plugin.js"></script>
  	<script type="text/javascript" src="${contextPath}/js/cons.man.js?version=${lastUpdate}"></script>	
  	
  	<!-- login페이지외 나머지 적용 -->
	<c:if test="${!sessionScope.contentView.equals('main')}">  	 
		<link rel="stylesheet" href="${contextPath}/css/bootstrap.css?s=2" media="screen">
		<link rel="stylesheet" href="${contextPath}/css/jquery.datepick.css" type="text/css" >
		<link rel="stylesheet" href="${contextPath}/css/jquery.timepicker.css" type="text/css" >
	  	<link rel="stylesheet" href="${contextPath}/css/overlay-bootstrap.min.css">
	  	<link rel="stylesheet" href="${contextPath}/css/bootstrap-table.css?s=1">
	  	<link rel="stylesheet" href="${contextPath}/css/navbar.css?s=1">
	  	<link rel="stylesheet" href="css/mdb/css/mdb.css?s=2"> 	  	
	  	<link rel="stylesheet" href="${contextPath}/css/common.css?s=3"	media="screen">
	  	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
	  		
	  	<script type="text/javascript" src="${contextPath}/js/jquery.datepick.js"></script> 
	  	<script type="text/javascript" src="${contextPath}/js/jquery.datepick-ko.js"></script>
	  	<script type="text/javascript" src="${contextPath}/js/jquery.timepicker.js"></script>
	  	<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
	  	<script type="text/javascript" src="${contextPath}/js/bootstrap-table.js"></script>
	  	<script type="text/javascript" src="${contextPath}/js/bootstrap-table-ko-KR.js"></script>
	  	<script type="text/javascript" src="${contextPath}/js/bootstrap-table-filter.js?s=1"></script>
	  	<script type="text/javascript" src="js/monitor/mon_base.js"></script>
  	</c:if>
  	
	<link rel="stylesheet" href="${contextPath}/css/font-awesome.min.css">

<script>

	var site_group = '${sessionScope.userLoginInfo.site_group}';
 	var __site_type = '${siteVO.type}' || '-1';
 	
 	$(document).ready(function() {
		init();
	
		var userid = '${sessionScope.userLoginInfo.userid} ';
	
		resizeMainContent();
		$(window).resize(function(){	
			resizeMainContent();
		}).resize();
 	});
 
	function resizeMainContent(){
		$('#main_content').height(window.innerHeight + 150);
 	}
 
	function init(){
		var site_auth = '${sessionScope.userLoginInfo.site_auth}';	
		var cont_type = '${sessionScope.userLoginInfo.cont_type}';
		var role_code = '${sessionScope.userLoginInfo.role_code}';	
		var access_temp = '${sessionScope.isAccess}';	
		
		var isAccess = ${sessionScope.isAccess} || false;	
		checkAccess(isAccess, site_auth, role_code);	
	
		var contentView = '${sessionScope.contentView}';
		var cur_group = -1;

		<c:forEach var="menu" items="${menuList}" varStatus="idx">
			addMenu('${menu.group}', '${menu.name}', '${menu.href}','${menu.isaccess}', site_auth , contentView);
			if('${menu.href}' == contentView){
				cur_group = '${menu.group}';
			}
		</c:forEach>
	
		//선택한 메뉴 active
		$('#__menu_' + contentView).addClass('active');	
		$('#__grouptab_' + cur_group).addClass('in');
		$('[id^=__grouptab_]').each(function(){
			var id = this.id;
			var tab_no = id.split('_')[3];
			var size = this.firstChild.childElementCount;
			if ( size <= 0)
				$('#__group_' + tab_no).hide();
			else{
				$('#__group_' + tab_no).show();
			}
		});
	}
	
	function checkAccess(isAccess, site_auth, role_code){
		var test='${sessionScope.isAccess}';		
		if(!isAccess){
			$('#main_content').hide();
			alert('잘못된 접근으로 인하여 기본페이지로 이동합니다.');
			location.replace("main");
		} 
	}

	function addMenu(group, name, href, isaccess, site_auth, contentView){	
		if(isaccess == 1){
	 		$('#__group_' + group + ' ul').append('<li><a id="__menu_' + href +'" href="'+ href + '" >' + name +'</a></li>');
		 }
	}
	
	function setScrollTop(){
		document.body.scrollTop = 0;
	}
	
</script>
</head>
<body> 
<c:if test="${!sessionScope.contentView.equals('main')}">
	<div id="__top_header" class="row col-xs-12 col-md-12">		
		<div id="btn navbar_toggle_btn" aria-controls="bs-navbar" aria-expanded="false" class="collapsed in navbar-toggle"
		  	  	 data-target="#bs-navbar" data-toggle="collapse" onmouseup="setScrollTop();"> 
			<span class="sr-only">Toggle</span><span class="glyphicon glyphicon-align-justify"></span>
		</div>
		  	
		<div id="__web_header">
			<div class="header_box" ><b>사용자: </b> ${sessionScope.userLoginInfo.name} </div>	
			<div class="header_box" ><b>권 한: </b> ${sessionScope.userLoginInfo.role_name} </div>		  	
		 	<div class="header_box" ><b>소속업체: </b> ${sessionScope.userLoginInfo.cont_name} </div>
			<div class="header_box" style="height: 4.3vh; float: right; margin-left: 1%; margin-right: 1%;"><b>현장명: </b>${sessionScope.userLoginInfo.site_name} </div>
			<div class="top_gradient_bar"></div>
		</div>
	</div>

	<div class="col-xs-12 col-sm-2 leftnav collapse " id="bs-navbar" style="margin-top: 6vh;">
		<nav id="main_navbar" class="__nav" role="navigation" style="min-width: 120px;">
			<div id="__group_1" style="display:none;">
				<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_1" style="border-bottom: 3px solid #a2a2a2;">모니터링</div>
				<div id="__grouptab_1" class="collapse in"><ul class="nav"></ul></div>
			</div>	
			
			<div id="__group_2" style="display:none;">
				<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_2" style="border-bottom: 3px solid #a2a2a2;">인력 관리</div>
				<div id="__grouptab_2" class="collapse in"><ul class="nav"></ul></div>
			</div>
				
			<div id="__group_3" style="display:none;">
				<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_3" style="border-bottom: 3px solid #a2a2a2;">관리자 메뉴</div>
				<div id="__grouptab_3" class="collapse in"><ul class="nav"></ul></div>
			</div>			
				
			<div id="__group_4">
				<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_4" style="border-bottom: 3px solid #a2a2a2;">기기 관리</div>
				<div id="__grouptab_4" class="collapse in"><ul class="nav"></ul></div>
			</div>
					
			<c:if test="${sessionScope.userLoginInfo.isManager == 1}">	
			<div id="__group_5" style="display:none;">
				<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_5"  style="border-bottom: 3px solid #a2a2a2;">데이터 관리</div>
				<div id="__grouptab_5" class="collapse in"><ul class="nav"></ul></div>
			</div>
			
			<div id="__group_6" style="display:none;">
				<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_6"  style="border-bottom: 3px solid #a2a2a2;">웹 관리</div>
				<div id="__grouptab_6" class="collapse in"><ul class="nav"></ul></div>
			</div>
			</c:if>
			<div id="__group_0">
				<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_0"  style="border-bottom: 3px solid #a2a2a2;">
					<a id="logout" href="./logout" class="collapse in" style="text-decoration: none;">로그아웃</a>
				</div>
			</div>
		</nav>  	  	  	
		 
	</div>
	
	<div class="visible-xs" style="height: 6vh; width: 100%;"> </div>
	<div id="main_content" class="col-xs-12 col-sm-10">
	<div id="container" class="col-xs-12 row">
</c:if>

  	 