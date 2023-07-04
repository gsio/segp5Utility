<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0);
	if (request.getProtocol().equals("HTTP/1.1")) {
	    response.setHeader("Cache-Control", "no-cache");
	}
%>
<!doctype html> 


<html dir="ltr" lang="en">
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <title>삼성엔지니어링 스마트 관리 시스템 </title>
	<link rel="stylesheet" href="css/bootstrap4.1/bootstrap.css"  media="screen">
	
	<link rel="stylesheet" href="css/font-awesome/4.7.0/css/font-awesome.css">
	<link rel="stylesheet" href="css/mdb/css/mdb.css">
	<link rel="stylesheet" href="css/mdb/css/style.css"> 
	
  	<script type="text/javascript" src="js/jquery-1.12.4.min.js" ></script>
  	<script type="text/javascript" src="js/jquery.plugin.js"></script>

<script>
	
	$(window).ready(function(){
		var isLogin = '${sessionScope.isLogin}';
		if(isLogin){
			location.href="recordList";		  
		}		
	});
	
	function login() {	
		var userid = $('#userid').val();
		if(userid.trim() == '' || userid.length <= 0){
			alert('Please enter your User  ID.');
		}else{
			$('#loginForm').submit();	
		}
	}  

	function reset(){
		$('#userid').val('');
	}	
	
</script>

<style>

	body {
		content: "";
		position: fixed;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		background-image: url(images/etc/orac_ex_image.jpg) !important;
		background-position: 58% 20% !important;
		background-repeat: no-repeat !important;
		background-size: cover;
		background-attachment: fixed !important;
		height: 100% !important;
		z-index: 0;
		animation: f17imgfade 0.5s 0s 1 cubic-bezier(0, 0, 0.2, 1) forwards;
		height:100vh;
	}
		
	#back {
		background: rgba(255,255,255,0.85);
		animation: cb41FadeIn 1.2s 0s 1 cubic-bezier(0, 0, 0.2, 1) forwards;
	}
	
</style>

</head>
<body onload="reset()">
	<script type="text/javascript" src="js/bootstrap4.1/bootstrap.js"></script>  	

<form id="loginForm" name="form" method="post" action="loginProcess">

	<div class="container">		
		<div class="row" >
			<div class="col" style="height:20vh">			
			</div>
	 	</div>
		<div class="row" >
	    	<div class="col-2"></div>
	    	<div id="back" class="col-8" style="height: 50vh; min-height: 450px; padding: 3vh;">
				<input id="isuser" type="hidden" name="isuser" value="1">
					<div class="row">
						<div class="col text-center">
							<img class="img-responsive-height" src="images/monitor/ss/logo_se.png?s=1" style="height:8vh;">
						</div>				    	
					</div>
					<div class="row">
						<div class="col" style="text-align: center; font-weight: bold; font-size: 3vh; color: #333">
					    	<br>
					    	그린동 P4 스마트 관리 시스템
						</div>
					</div>
					<div class="md-form">
						<i class="fa fa-user prefix" style="color:#405f73"></i>
						<input type="text" id="userid"  name="id" class="form-control validate">
						<label for="userid" >Type your UserID</label>
					</div>
						
					<div class="md-form">
						<i class="fa fa-lock prefix" style="color:#405f73"></i>
						<input type="password" id="password" name="password" class="form-control validate" onkeypress="if(event.keyCode=='13') login()">
						<label for="password">Type your password</label>
					</div>				
				<button class="btn btn-info btn-block my-4" onclick="login()" type="submit">로그인</button>
	    	</div>
	    	<div class="col-2"></div>
	    </div>
	    <div class="row">
	    	<div class="col-12 text-center"><img class="img-responsive-height" src="images/logo_gsil.png?s=1" style="height:10vh;"></div>
	    </div>
	</div>

</form>	

</body>
</html>

