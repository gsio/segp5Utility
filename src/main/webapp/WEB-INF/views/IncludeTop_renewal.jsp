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
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
  	
  	<!-- 파비콘 -->
	<link rel="icon" href="${contextPath}/images/ss.ico" type="image/x-icon"/>
	<link rel="shortcut icon" href="${contextPath}/images/ss.ico" type="image/x-icon"/>
  	
  	<!-- 공용 -->
  	<script type="text/javascript" src="${contextPath}/js/jquery-1.12.4.min.js" ></script>
  	<script type="text/javascript" src="${contextPath}/js/jquery.plugin.js"></script>
  	
  	<c:if test="${!sessionScope.contentView.equals('main')}">  		
  	
  		<link rel="stylesheet" href="${contextPath}/css/bootstrap4.1/bootstrap.css" media="screen">
		<link rel="stylesheet" href="${contextPath}/css/jquery.datepick.css" type="text/css" >
		<link rel="stylesheet" href="${contextPath}/css/jquery.timepicker.css" type="text/css" >
		<link rel="stylesheet" href="${contextPath}/css/bootstrap-table.css?s=1">
		
		<link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.21.3/dist/extensions/group-by-v2/bootstrap-table-group-by.css">
		<link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.21.3/dist/bootstrap-table.min.css">
		<link rel="stylesheet" href="https://unpkg.com/placeholder-loading/dist/css/placeholder-loading.min.css">
		
		<script src="https://unpkg.com/bootstrap-table@1.21.3/dist/bootstrap-table.min.js"></script>
		<script src="https://unpkg.com/bootstrap-table@1.21.3/dist/extensions/group-by-v2/bootstrap-table-group-by.min.js"></script>
		
		<link rel="stylesheet" href="${contextPath}/css/basic.css?s=2">
		<link rel="stylesheet" href="${contextPath}/fontawesome/css/all.css">	
		
  		<script type="text/javascript" src="${contextPath}/js/jquery.datepick.js"></script> 
		<script type="text/javascript" src="${contextPath}/js/jquery.datepick-ko.js"></script>
	  	<script type="text/javascript" src="${contextPath}/js/jquery.timepicker.js"></script>
	  	<script type="text/javascript" src="${contextPath}/js/bootstrap-table-ko-KR.js"></script>	  	
 		<script type="text/javascript" src="${contextPath}/js/bootstrap-table.js"></script>
	  	<script type="text/javascript" src="${contextPath}/js/bootstrap-table-filter.js?s=1"></script>
		<script type="text/javascript" src="${contextPath}/js/bootstrap-table-ko-KR.js"></script>
		<script type="text/javascript" src="${contextPath}/js/vue_2.0.3.js"></script>
		<script type="text/javascript" src="${contextPath}/js/basic.js"></script>
		<script type="text/javascript" src="${contextPath}/js/bootstrap4.1/bootstrap.bundle.js"></script>		
		
  	</c:if>  	  	
  	
  	<!-- login페이지외 나머지 적용 -->

<script>	
 	
 	$(document).ready(function() {
		getMenuInfo();	
		var userid = '${sessionScope.userLoginInfo.userid}';
 	});
 
	function getMenuInfo(){
		
		//alert("getMenuInfo 호출");
		
		var site_auth = '${sessionScope.userLoginInfo.site_auth}';	
		var cont_type = '${sessionScope.userLoginInfo.cont_type}';
		var role_code = '${sessionScope.userLoginInfo.role_code}';	
		var access_temp = '${sessionScope.isAccess}';
		
		//alert(site_auth + "/" + cont_type + "/" + role_code + "/" + access_temp);		
		
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
		$('#__grouptab_' + cur_group).addClass('show');
		$('[id^=__grouptab_]').each(function(){
			var id = this.id;
			var tab_no = id.split('_')[3];
			var size = this.firstChild.childElementCount;
			if (size <= 0)
				$('#__group_' + tab_no).hide();
			else{
				$('#__group_' + tab_no).show();
			}
		});
	}

	function addMenu(group, name, href, isaccess, site_auth, contentView){	
		if(isaccess == 1){
	 		$('#__group_' + group + ' ul').append('<li><a id="__menu_' + href +'" href="'+ href + '" >' + name +'</a></li>');
		}
	}
	
	function setVisibleMenu() {		
		if($('#side-menu').css('display') == 'flex') {
			$("#side-menu").attr( "style", "display: none !important;")
		}
		else {
			$("#side-menu").attr( "style", "display: flex !important;")
		}
	}
	
</script>

<style>

</style>

</head>

<body> 
	<div id="__top_header">
		<!-- 큰 화면 상단바 -->
		<div id="lg-top-header" class="d-none d-lg-flex" title="큰 화면 상단바 (사용자 정보 표시 필요)">
			<div id="lg-top-menu-bar" onclick="setVisibleMenu()" style="cursor: pointer;">
		 		<i class="fa-solid fa-bars"></i>
		 	</div>
		 	<div id="lg-user_info">
		 		<div class="header_box">사용자 : <span>${sessionScope.userLoginInfo.name}</span></div>	
				<div class="header_box">권 한 : <span>${sessionScope.userLoginInfo.role_name}</span></div>		  	
			 	<div class="header_box">소속업체 : <span>${sessionScope.userLoginInfo.cont_name}</span></div>
				<div class="header_box">현장명 : <span>${sessionScope.userLoginInfo.site_name}</span></div>		
		 	</div>
		</div>
		<!-- 작은 화면 상단바 -->
		<div id="sm-top-header" class="d-lg-none" title="작은 화면 상단바">	
		 	<div id="sm-top-menu-bar" onclick="setVisibleMenu()" style="cursor: pointer;">
		 		<i class="fa-solid fa-bars"></i>
		 	</div>
		</div>
	</div>	
		
	<div id="__cotent_box">
		<div id="lg-content-box" class="flex-box">
			<div id="side-menu" class="menu_box d-none d-lg-flex">
				<nav id="main_navbar" class="__nav" role="navigation">
					<div id="__group_1" style="display:none;">
						<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_1" >모니터링</div>
						<div id="__grouptab_1" class="collapse in"><ul class="nav"></ul></div>
					</div>	
					
					<div id="__group_7" style="display:none;">
						<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_7">공지 / 알림사항</div>
						<div id="__grouptab_7" class="collapse in"><ul class="nav"></ul></div>
					</div>
					
					<c:if test="${sessionScope.userLoginInfo.id == '0' || sessionScope.userLoginInfo.id == '1' || sessionScope.userLoginInfo.id == '2'}">						
					
					<div id="__group_8" style="display:none;">
						<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_8">진행중 ...</div>
						<div id="__grouptab_8" class="collapse in"><ul class="nav"></ul></div>
					</div>
					
					<div id="__group_9" style="display:none;">
						<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_9">대기중 ...</div>
						<div id="__grouptab_9" class="collapse in"><ul class="nav"></ul></div>
					</div>
					
					<div id="__group_10" style="display:none;">
						<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_10">개발완료 ...</div>
						<div id="__grouptab_10" class="collapse in"><ul class="nav"></ul></div>
					</div>
					
					</c:if>
					
					<div id="__group_2" style="display:none;">
						<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_2" >인력 관리</div>
						<div id="__grouptab_2" class="collapse in"><ul class="nav"></ul></div>
					</div>
						
					<div id="__group_3" style="display:none;">
						<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_3">관리자 메뉴</div>
						<div id="__grouptab_3" class="collapse in"><ul class="nav"></ul></div>
					</div>			
						
					<div id="__group_4" style="display:none;">
						<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_4">기기 관리</div>
						<div id="__grouptab_4" class="collapse in"><ul class="nav"></ul></div>
					</div>
							
					<c:if test="${sessionScope.userLoginInfo.isManager == 1}">	
					<div id="__group_5" style="display:none;">
						<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_5">데이터 관리</div>
						<div id="__grouptab_5" class="collapse in"><ul class="nav"></ul></div>
					</div>
					
					<div id="__group_6" style="display:none;">
						<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_6">웹 관리</div>
						<div id="__grouptab_6" class="collapse in"><ul class="nav"></ul></div>
					</div>
					</c:if>
					<div id="__group_0">
						<div class="nav_main_menu waves-effect waves-blue" data-toggle="collapse" data-target="#__grouptab_0">
							<a id="logout" href="./logout" class="collapse in" style="text-decoration: none;">로그아웃</a>
						</div>
					</div>
				</nav>
			</div>
			<div id="main_content">