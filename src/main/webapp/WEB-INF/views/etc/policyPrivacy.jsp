<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"   language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ page session="true"%>

<!doctype html>


<html dir="ltr">
<head>
    <meta charset="UTF-8">
    <title>[Web] 개인정보 처리 방침</title>
	<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width, height=device-height">   

  <link href="images/ss.ico" rel="icon" type="image/x-icon" />
  <link href="images/ss.ico" rel="shortcut icon" type="image/x-icon" />

<style>
	
	body { 
		padding:20px;
	}
   
   	h1 {
		text-align: center;
		font-size: 2.5em;
		color: #1a2d62;
	}
   
   	.content {
	    background: #FAFAFA;
	    border-radius: 10px;
	    padding: 5px;
		font-size: 18px;
   	}
   	
   	.permission, .title, .homepage{
		font-size: 16px;
		font-weight: 600;
		padding: 10px;
	}
	
	.font-strong {
		color: #F32539;
		font-weight: bold;
		font-size: 16px;
	}
	
	.item {
	    padding: 5px;
    	margin: 10px 5px;
	}
	
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-2.2.4.min.js"></script>  
<script>
$(document).ready(function(){
   
   var s = getUrlParameter('s');
   var title ="평택 P4-PJT 그린동 관리 시스템";
   var homepage="http://segp4.gsil.net";   
   $('[class=title]').html(title);
   $('.homepage').html(homepage);
   
})
function getUrlParameter(sParam) {
   var sPageURL = window.location.search.substring(1);
   var sURLVariables = sPageURL.split('&');
   for (var i = 0; i < sURLVariables.length; i++) {
      var sParameterName = sURLVariables[i].split('=');
      if (sParameterName[0] == sParam) {
         return sParameterName[1];
      }
   }
}

</script>
</head>

<body>
<h1>개인정보 처리 방침</h1>
<div class="title" style="text-align: center; font-size: 2em; color: #444;	margin-bottom: 15px;"></div>

<div class="content">
   
	<div class="item">
		1. 주식회사 지에스아이엘 (&#8220;<span class="homepage"></span>&#8221;)는 웹 서비스(&#8220;<span class="title"></span>&#8221;) 이용자의 개인정보를 매우 중요하게 생각하며 각별히 주의를 기울여 처리하고 있습니다.<br/>   
		다음과 같은 목적외에는 사용하지 않습니다.
   
		<div class='permission'>
			&#8211; 허가된 사용자의 <span class="font-strong"">신원 확인</span>을 위한 용도<br/>   
			&#8211; 위급상황 발생 시 알 수 있는 <span class="font-strong">사용자의 정보(전화번호, 이름, 혈액형)를 확인</span>하는 용도<br/>
		</div>
	</div>  

	<div class="item">
		2. 정보 주체의 권리, 의무 및 그 행사방법 이용자는 개인정보 주체로서 다음과 같은 권리를 행사할 수 있습니다.
   
		<div>① 정보주체는 주식회사 지에스아이엘( &#8220;<span class="homepage"></span>&#8220; ) 에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.</div>
	   	<div>
			1) 개인정보 열람요구<br/>
	  	 	2) 오류 등이 있을 경우 정정 요구<br/>
			3) 삭제요구<br/>
			4) 처리정지 요구<br/>
			<span class="font-strong">* 지에스아이엘은(는) 사용자의 사용정보를 수집 및 보유하지 않습니다.</span>
	   </div>
   </div>
   
   <div class="item">
   		3. 개인정보의 처리목적 및 수집항목
   		<div>① "관리자/근로자" 신규 등록 시</div>
   		<div>② 허가된 서비스의 사용권한 확인 및 신원 확인에 사용</div>
   		<div>③ 수집항목 <br/>
   			<p style="padding-left: 5px;">
			1) 성명<br/>
	  	 	2) 생년월일<br/>
			3) 성별<br/>
			4) 핸드폰번호<br/>
			5) 업체<br/>
			6) 직종/직책<br/>
			7) [선택] 혈액형<br/>
			8) [선택] 식별용 사진<br/>
			9) [선택] 입사일<br/>
			</p>
   		</div>
   </div>
   
    <div class="item">
    	4. 개인정보의 파기
    	<div>지에스아이엘은(는) 원칙적으로 개인정보 처리목적이 달성된 경우에는 지체없이 해당 개인정보를 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.</div>
   		<div>
   			&#8211; 사용되는 개인정보는 근로자등록을 위한 사진을 획득하는 용도를 위해 정상적으로 허가된 사용자 확인 여부만 따집니다.<br />
			&#8211; 위 두가지 외에는 어떤 목적으로도 자료를 수집, 처리하지 않습니다.<br />
			&#8211; 서비스 이용 중지 및 탈퇴시 더 이상 위 권한을 사용하지 않습니다.
   		</div>
	</div>
	
	<div class="item">
   		5. 개인정보의 안정성 확보 조치 지에스아이엘는 개인정보보호법 제29에 따라 다음과 같이 안정성 확보에 필요한 기술적/관리적 및 물리적 조치를 하고 있습니다.
    	<div>① 내부관리계획의 수립 및 시행 <br/> – 개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다.</div>
    	<div>② 개인정보에 대한 접근 제한 <br/> – 개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여,변경,말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.</div>
    	<div>③ 비인가자에 대한 출입 통제 <br/> – 출입통제 절차를 수립 및 운영하고 있습니다.</div>    	
	</div>
		
	<div class="item">
		6. 개인정보 보호책임자 작성		
		<p style="padding-left: 5px;">
			▶ 개인정보 보호책임자<br/>
			성명 : 이정우<br/>
			직책 : 대표<br/>
		   	연락처 : 02-6247-7500
		</p>
		
		<p style="padding-left: 5px;">
			▶ 개인정보 보호 담당부서<br/>
			성명 : 박민호<br/>
			직책 : 프로<br/>
		   	연락처 : 02-6247-7500 내선번호(2)<br />
		   	이메일 : dev@gsil.kr
		</p>
		
		<span class="font-strong">정보주체께서는 지에스아이엘의 서비스을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. 지에스아이엘은(는) 정보주체의 문의에 대해 지체없이 답변 및 처리해드릴 것입니다.</p></span>
		
	</div>
   	
	<div class="item">
   		7. 개인정보 처리방침 변경
		<div>
			① 이 개인정보 처리 방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는 경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.<br/>
			② 이 개인정보 처리방침은 2022년 7월 1일 부터 적용됩니다.
		</div>
   	</div>
</div>

</body>

</html>