<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control", "no-cache");
	}
%>
<!doctype html>


<html dir="ltr" lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>스마트 관리 시스템</title>
<link rel="stylesheet" href="css/bootstrap4.1/bootstrap.css"
	media="screen">

<link rel="stylesheet" href="css/login.css">

<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="js/jquery.plugin.js"></script>

<script>
	$(window).ready(function() {

		var isLogin = '${sessionScope.isLogin}';
		if (isLogin) {
			location.href = "recordList";
		}
	});

	function login() {
		$('#loginForm').submit();
	}

	function reset() {
		$('#userid').val('');
	}

	function privacyPolicyPage() {
		location.href = "policy_privacy";
	}

	function locationPolicyPage() {
		location.href = "policy_location";
	}

	function servicePolicyPage() {
		location.href = "policy_service";
	}
</script>

<style>
</style>

</head>
<body>
	<script type="text/javascript" src="js/bootstrap4.1/bootstrap.js"></script>

	<div class="d-lg-none sm-top-box">
		<div class="sm-top-bg">
			<div class="sm-top-title">Safety With U</div>
			<div class="sm-card">

				<img class="logo-ss" src="images/monitor/ss/logo_se.png?s=1">


				<p class="logo-text">평택 P5-PJT 그린동 관리 시스템</p>
				<form id="loginForm" name="form" method="post" action="loginProcess">
					<input type="text" id="userid" name="id" placeholder="아이디"
						placeholder="아이디"
						class="input-up-down ng-untouched ng-pristine ng-valid"> <input
						type="password" id="password" name="password"
						onkeypress="if(event.keyCode=='13') login()" placeholder="비밀번호"
						class="input-up-down ng-untouched ng-pristine ng-valid">

					<button
						class="login-button submit button-block button-round button-solid ion-activatable ion-focusable hydrated"
						style="margin-bottom: 4px;">로그인</button>
				</form>

				<div class="sub-buttons md hydrated">
					<div class="button_box md hydrated row">
						<button
							class="col md button button-clear ion-activatable ion-focusable hydrated"
							onclick="privacyPolicyPage()">개인정보 처리방침</button>
						<button
							class="coi md button button-clear ion-activatable ion-focusable hydrated"
							onclick="locationPolicyPage()">위치정보 처리방침</button>
						<button
							class="col md button button-clear ion-activatable ion-focusable hydrated"
							onclick="servicePolicyPage()">서비스 이용약관</button>
					</div>
				</div>

				<img src="images/logo_gsil.png?s=1" class="logo-gsil">
			</div>
			<div style="height: 20px; width: 100%;"></div>
		</div>
	</div>

	<div class="flex-box d-none d-lg-flex lg-content-box">
		<div class="left-col md hydrated">
			<button
				class="active login md button button-block button-clear ion-activatable ion-focusable hydrated">
				<div class="button-inner" onclick="privacyPolicyPage()">
					개인정보<br>처리방침
				</div>
			</button>
			<button
				class="active sign-up md button button-block button-clear ion-activatable ion-focusable hydrated">
				<div class="button-inner" onclick="locationPolicyPage()">
					위치정보<br>처리방침
				</div>
			</button>
			<button
				class="active id-password md button button-block button-clear ion-activatable ion-focusable hydrated">
				<div class="button-inner" onclick="servicePolicyPage()">
					서비스<br>이용약관
				</div>
			</button>
		</div>
		<div class="center-col md hydrated">
			<div class="title">
				Smart Construction<br>Management System
			</div>
			<div class="sub-title">
				스마트 안전관리 시스템에<br>오신 것을 환영합니다.
			</div>
		</div>
		<div class="right-col md hydrated">
			<div class="md-form-box form-box">

				<div class="sub-buttons md hydrated">
					<img class="logo-ss" src="images/monitor/ss/logo_se.png?s=1">
				</div>

				<div class="md hydrated">
					<p class="logo-text">평택 P5-PJT 그린동 관리 시스템</p>
				</div>
				<form id="loginForm" name="form" method="post" action="loginProcess">
					<input type="text" id="userid" name="id" placeholder="아이디"
						class="ng-untouched ng-pristine ng-valid"> <input
						type="password" id="password" name="password"
						onkeypress="if(event.keyCode=='13') login()" placeholder="비밀번호"
						class="ng-untouched ng-pristine ng-valid">

					<button
						class="login-button button-block button-solid ion-activatable ion-focusable hydrated"
						style="margin-bottom: 4px;" ng-reflect-expand="block">
						로그인</button>
				</form>
				<div class="sub-buttons md hydrated">
					<img class="logo-gsil" src="images/logo_gsil.png?s=1">
				</div>
			</div>
		</div>
	</div>


</body>
</html>

