<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"   language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page session="true"%>

<!doctype html>

<html dir="ltr">
<head>
	<meta charset="UTF-8">
	<title>[App] 개인정보 처리 방침</title>
	<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width, height=device-height">
	
	<link href="images/ss.ico" rel="icon" type="image/x-icon" />
	<link href="images/ss.ico" rel="shortcut icon" type="image/x-icon" />

<style>

	body {
		font-size: 1.2vh;
		padding: 5px;
	}
	
	.item-box {
		padding: 5px;
		margin: 5px;
		border: 1px solid #e6e8e9;
	}
	
	h1 {
		text-align: center;
		font-size: 2.5vh;
		color: #000;
	}
	
	h2 {
		font-size: 1.8vh;
		color: #333;
	}
			
	h3 {
		font-size: 1.5vh;
		font-weight: bold;
		color: #666;
	}
	
	p {
		color: #666;
	}
	
	.permission {
		font-size: 1.5vh;
		font-weight: bold;
		color: #4285F4;
	}
	
	.sub_content {
		font-size: 1.5vh;
		color: #666;
	}
	
	.focus {
		color: #F32539 !important;
    	font-weight: bold !important;
	}
   
</style>

<script type="text/javascript" src="https://code.jquery.com/jquery-2.2.4.min.js"></script>  

<script>

$(document).ready(function(){
   
   var s = getUrlParameter('s');
   var title ="P5-PJT 그린동 공동구 관리 시스템";
   var homepage="http://segp5.gsil.net";   
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

<h1>안드로이드 앱 개인정보 처리 방침</h1>

<div>

	<h1 class="title"></h1>
	
	<div class="item-box">
		<h2>
			1. 주식회사 지에스아이엘 (&#8220;<span class="homepage"></span>&#8221;)는 안드로이드 앱(<span class="title"></span>) 이용자의 개인정보를 매우 중요하게 생각하며 
			<br /> 각별히 주의를 기울여 처리하고 있으며 다음과 같은 목적외에는 사용하지 않습니다.
		</h2>
		
		<p class='permission'>
			&#8211; android.permission.CAMERA <span class="focus"> [카메라 기능] </span><br/>
			&#8211; android.permission.READ_PHONE_STATE <span class="focus"> [단말기 정보] </span><br/>
			&#8211; android.permission.GET_ACCOUNTS <span class="focus"> [기기의 계정 정보] </span><br/>
			&#8211; android.permission.READ_EXTERNAL_STORAGE <span class="focus"> [저장소 접근] </span><br/>
			&#8211; android.permission.WRITE_EXTERNAL_STORAGE <span class="focus"> [파일 저장] </span>
		</p>
	</div>

	<div class="item-box">
		<h2>2. 정보 주체의 권리, 의무 및 그 행사방법 이용자는 개인정보 주체로서 다음과 같은 권리를 행사할 수 있습니다.</h2>	
		<h3> 
			&#8211; 정보주체는 주식회사 지에스아이엘( &#8220;<span class="homepage" style="font-weight: bold; font-size: 1.5vh;"></span>&#8220; ) 에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.
		</h3>
		<p class="sub_content">
			1) 개인정보 열람요구<br />
			2) 오류 등이 있을 경우 정정 요구<br />
			3) 삭제요구<br />
			4) 처리정지 요구
		</p>
		<p class="focus">* 지에스아이엘은(는) 앱 사용자의 사용정보를 수집 및 보유하지 않습니다.</p>
	</div>

	<div class="item-box">
	
		<h2>3. 처리하는 개인정보의 항목 작성</h2>
		<h3> 
			&#8211; 지에스아이엘은(는) 다음의 개인정보 항목을 처리하고 있습니다.
		</h3>
		<h3> 1) 근로자 등록을 위한 카메라(Camera)의 연동 </h3>
		<p class='permission'>
			&#8211; android.permission.CAMERA,<br />
			&#8211; android.permission.READ_EXTERNAL_STORAGE,<br/>
			&#8211; android.permission.WRITE_EXTERNAL_STORAGE
		</p>
		<p class="focus">
			* 단순히, 앱의 주 기능인 근로자 등록을 위한 사진을 획득하는 용도로만 사용하고 저장하는 용도로만 사용합니다. 그 외의 어떠한 자료도 수집 및 처리하지 않습니다.
		</p>
		<h3> 2) 정상적으로 허가된 사용자 확인 여부 </h3>
		<p class='permission'>
		&#8211; android.permission.READ_PHONE_STATE,<br/>
		&#8211; android.permission.GET_ACCOUNTS
		</p>
		
		<!-- 2023-02-14 추가내용 -->
	 	<h3> 3) 개인 정보 및 민감한 사용자 데이터를 액세스, 수집, 사용, 공유 안내</h3>
	 	<p class='permission'>
			&#8211; 전화번호 (식원 확인 및 위급상황 시 즉각적인 연락을 위해 수집)
		</p>
	 	
	</div>
	
	<div class="item-box">
		<h2>4. 개인정보의 파기</h2>
		<h3>
			지에스아이엘은(는) 원칙적으로 개인정보 처리목적이 달성된 경우에는 지체없이 해당 개인정보를 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.
		</h3>
		<p>
			1) 사용되는 개인정보는 근로자등록을 위한 사진을 획득하는 용도를 위해 정상적으로 허가된 사용자 확인 여부만 따집니다.<br />
			2) 위 두가지 외에는 어떤 목적으로도 자료를 수집, 처리하지 않습니다.<br />
			3) 앱 삭제시 더 이상 위 권한을 사용하지 않습니다.
		</p>
	</div>
	
	<div class="item-box">
		<h2>5. 개인정보의 안정성 확보 조치 지에스아이엘는 개인정보보호법 제29에 따라 다음과 같이 안정성 확보에 필요한 기술적/관리적 및 물리적 조치를 하고 있습니다.</h2>
		<p>① 내부관리계획의 수립 및 시행<br />
		– 개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다.</p>
		<p>② 개인정보에 대한 접근 제한<br />
		– 개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여,변경,말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.</p>
		<p>③ 비인가자에 대한 출입 통제<br />
		– 출입통제 절차를 수립 및 운영하고 있습니다.</p>
	</div>
	
	<div class="item-box">
		<h2>6. 개인정보 보호책임자 작성</h2>
		<h3 style="font-weight: bold;">▶ 개인정보 보호책임자<br />
			성명 : 이정우<br />
			직책 : 대표<br />
			연락처 : 02-6247-7500 내선번호(2)
		</h3>
		<h3 style="font-weight: bold;">▶ 개인정보 보호 담당부서<br />
			성명 : 박민호<br />
			직책 : 프로<br />
			연락처 : 02-6247-7500 내선번호(2), dev@gsil.kr
		</h3>
		<p class="focus"> * 정보주체께서는 지에스아이엘의 서비스을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. 지에스아이엘은(는) 정보주체의 문의에 대해 지체없이 답변 및 처리해드릴 것입니다.</p>
	</div>
	
	<div class="item-box">
		<h2>7. 개인정보 처리방침 변경</h2>
		<p> 1) 이 개인정보 처리 방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는 경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.</p>
		<p> 2) 이 개인정보 처리방침은 2018년 9월 23일 부터 적용됩니다.</p>
		<p> 3) 개인 정보 및 민감한 사용자 데이터를 액세스, 수집, 사용, 공유하는 경우 게시를 2023년 2월 14일 부터 추가합니다.</p>
	</div>
	
</div>

</body>

</html>