<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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

<%@ page session="true"%>

<c:set var="lastUpdate" value="${ System.currentTimeMillis()}"/>

<html lang="ko">

<meta charset="utf-8"/>
<meta data-n-head="true" name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">

<head>

<script async src="https://www.googletagmanager.com/gtag/js?id=UA-138883721-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-138883721-1');
</script>
	<title>Monitoring System</title>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  	<link rel="icon" href="${contextPath}/images/ss.ico" type="image/x-icon" />
  	<link rel="shortcut icon" href="${contextPath}/images/ss.ico"  type="image/x-icon" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <link rel="stylesheet" href="css/bootstrap.min.css" media="screen">
    <link rel="stylesheet" href="css/monitor/animate.css" media="screen">
    <link rel="stylesheet" href="css/qr/qr.css" media="screen">       
    <script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="js/jquery.plugin.js"></script>    
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-table.js"></script>
    <script type="text/javascript" src="js/bootstrap-table-ko-KR.js"></script>
    
</head>


<body style="overflow: hidden;">

<div id="container">
    <div id="header">
        <div class="seal-logo-box" style="">
      	 	<img class="logo-seal" src="images/logo/logo_seal_white.png">
        </div>
   		<div class="title">P5-PJT 그린동 기술인 출역</div>   
    </div>
	<div id="test">
  	</div>
    <div id="main_frame">
    
    	<div class="case-wrap"></div>
    	<div id="contentWarp" >
    		<div id="_page1" class="page-wrap" >
    			<div class="btn-wrap">
					<div class="img-box">
						<img src="images/icons/qr/in.png">
			        </div>
    			</div>
    			<div class="btn-wrap">
    				<div class="img-box">
						<img src="images/icons/qr/out.png">
			        </div>
    			</div>
    		</div>
    		<div id="_page2" class="page-wrap" style="display: none"></div>
    		<div id="_page3" class="page-wrap" style="display: none"></div>
    		<div id="_page4" class="page-wrap" style="display: none"></div>
    	</div>
    	<div class="button-wrap">
    		<div class="btn-box">
    			<button id="prebtn" class="btn" onclick="">이전</button>
    		</div>
    		<div  class="btn-box">
    			<button id="nextbtn" class="btn" onclick="">다음</button>
    		</div>
    	</div>

	</div>
	
	<div id="footer">
		<div class="gsil-logo-box">
			<img class="logo-gsil" src="images/logo/logo_gsil_white.png">
        </div>
	</div>

</div>


</body>


</html>
