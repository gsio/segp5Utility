<%@ include file="MonitorTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
 
<script>

	const CUR_SITE_ID = 17;
	var G_STATE = 0;
	
	/* --------------------	
	[G_STATE]
	0: Init
	1: Select Inout
	2: New Engineer
		2.1: 개인정보 수집 및 이용동의 체크 X 
		2.2: 개인정보 수집 및 이용동의 체크 O
	3: Init Insert Info
	-------------------- */
	var interval;

	$(document).ready(function() {
		nextStep();
		//checkSession();
		
		$('#agree').change(function() {
			if (this.checked) {
				G_STATE = 2.2;
			} else {
				G_STATE = 2.1;
		    }
		});
		
		$('#cont').change(function() {
		    var selectedValue = $(this).val();
		    if (selectedValue === "-2") {
				$("#addContBox").css("display", "contents");
		    } else {
		    	$("#addContBox").css("display", "none");
		    }
			$('#cont').css('border','1px solid #cccccc');	
		});
	});	
	
	// 1. QR로 들어온 URL 링크 확인 (유효성 검사)
	function checkSession() {
		
	}
	
	function showPreBtn(name) {
		$('#prebtn').html(name);
		$('#prebtn').css("display", "block");
	}
	
	function closePreBtn() {
		$('#prebtn').css("display", "block");
	}	
	
	function showNextBtn(name) {
		$('#nextbtn').html(name);
		$('#nextbtn').css("display", "block");
	}
	
	function closeNextBtn() {
		$('#nextbtn').css("display", "block");
	}
	
	function preStep() {
		//alert("[preStep]: " + G_STATE)
		switch(G_STATE) {
		case 0:
		case 1:
		case 2:
			location.reload();
			break;
		}
	
	}
	
	function nextStep() {
		//alert("[nextStep]: " + G_STATE)
		switch(G_STATE) {
		
			case 2:
				$('#prebtn').html("이전");
				$('[id^=_page_]').hide();
				$("#_page_2").show();
				G_STATE = 2.1;
				break;
				
			case 2.1:
				alert("필수 항목에 동의하셔야 다음 진행이 가능합니다.");
				break;
				
			case 2.2:
				$('[id^=_page_]').hide();
				$("#_page_3").show();
				G_STATE = 3;
				showNextBtn("등록");
				break;
				
			case 3:
				insertUWData();
				checkValidation();
				break;
		}
		
	}
	
	function insertUWData() {
		var isOk = checkValidation();				
		if(isOk){			
			checkDuplicateCheck();
		}
		else {
			alert('항목을 다시 확인해주시기 바랍니다.');
		}
	}
	
	function checkDuplicateCheck() {
		
		var role = $('#role').val();
		var name = $('#name').val();
		var jumin = $('#jumin').val();
		var jumin_back = $('#jumin_back').val();
		var phone = $('#phone').val();
		var cont_id = $('#cont').val();
		var cont_name = $('#cont_name').val();
		
		$.ajax({
			type: "POST",				
			url: 'qr/checkDuplicateCheck',
			data: {				
				role : role,
				name : name,
				jumin: jumin,
				jumin_back: jumin_back,
				phone: phone,
				cont_id: cont_id,
				cont_name: cont_name
			},
			async: true,
			cache: false,			
			success: function (json, status) {	
				var data = JSON.parse(json);	
				if(data.result == "true") {
					
				}
				else {
					alert(data.err);
				}			
				
	       	}
		});	
		
	}
	
	function checkValidation() {
		
		var role = $('#role').val();
		var name = $('#name').val();
		var jumin = $('#jumin').val();
		var jumin_back = $('#jumin_back').val();
		var phone = $('#phone').val();
		var cont_id = $('#cont').val();
		var cont_name = $('#cont_name').val();
		
		console.log("----------------- 정보입력");
		console.log("[구분]", role);
		console.log("[이름]", name);
		console.log("[생년월일]", jumin);
		console.log("[성별]", jumin_back);
		console.log("[핸드폰번호]", phone)
		console.log("[업체]", cont_id);
		console.log("[업체명]", cont_name);
		
		var isOk = true;
		
		if(name.length < 1){
			$('#name').css('border','2px solid red');
			isOk = false;
		}
		else {
			$('#name').css('border','1px solid #cccccc');
		}
		
		if(jumin.length < 6){
			$('#jumin').css('border','2px solid red');
			isOk = false;
		}
		else {
			$('#jumin').css('border','1px solid #cccccc');
		}
		
	 	var rgEx = /(01[016789])(\d{4}|\d{3})\d{4}$/g;//핸드폰 정규표현식
	   	var chk_phone = rgEx.test(phone);   
	   	if(!chk_phone || phone.length < 10) {				
		   	$('#phone').css('border','2px solid red');
		   	isOk = false;
		} 
	   	else {
	   		$('#phone').css('border','1px solid #cccccc');
	   	}
	   	
	   	if(cont_id == -1) {
	   		$('#cont').css('border','2px solid red');	
	   		isOk = false;
	   	} 
	   	else if(cont_id == -2) {
	   		if(cont_name < 1) {
	   			isOk = false;
	   			$('#cont_name').css('border','2px solid red');	
	   		}
	   		else {
	   			$('#cont_name').css('border','1px solid #cccccc');	
	   		}
	   	}
	   	else {
	   		$('#cont').css('border','1px solid #cccccc');	
	   	}
	
	   	return isOk;
	}

	
	
	
	
</script>

<style>

#_page_0 #page1Table #certkey_box .default-item-box {
    display: flex;
    justify-content: space-between;
    flex-wrap: nowrap;
    flex-direction: row;
    margin-top: 5px;
}

#_page_0 #page1Table #certkey_box .default-item-box .title {
    min-width: 100px;
    width: 5%;
    text-align: center;
    font-size: 16px;
    font-weight: 600;
    border: 1px solid #ced4da;
    height: 40px;
    line-height: 40px;
    background: #ffffff;
}

#_page_0 #page1Table #certkey_box .default-item-box .content {
	height: 100% !important;
    padding: 0;
    font-size: 1em;
    border: none;
    font-weight: 600;
    width: 100%;
    position: relative;
}

#_page_0 #page1Table #certkey_box #resendMsgCertKey {
	display: none;
}

#_page_0 #page1Table #certkey_box .default-item-box .content #countDown {
    position: absolute;
    top: 7px;
    right: 10px;
    font-size: 16px;
    color: #e30512;
}

</style>


<div id="content-wrapper">
	<div id="contentPage">
		<div id="_page_0" class="page-wrap">
			<div id="content_title" class="content-item">[ 기술인 신원 확인 ]</div>		
			<div class="content_table_box content-item">		
				<div class="table-container">		   		
			   		<table id="page1Table" class="table table table-bordered col-xs-12 table-hover">		
			   			<tbody>
							<tr>
								<th class="text-center">핸드폰 인증</th>							
								<td>
									<input class="form-control" id="inputPhone" onkeydown='return onlyNumber(event)' 
										onkeyup='removeChar(event)' maxlength="11" style="cursor:pointer; text-align:center;" placeholder="ex) 01012345678" >									
									<div class="img_btn_wrapper">	
										<div id="certBtn" class="btn btn-danger img_btn_box" onclick="checkIdentification()">인증하기</div>		
									</div>
									<div id="certkey_box">
										<div class="info-title-box">
											<div class="title">인증확인</div>
											<div id="confirmCertKeyId" class="btn btn-default" onclick="confirmCertKeyId()">확인</div>
											<div id="resendMsgCertKey" class="btn btn-danger" onclick="resendMsgCertKey()">재전송</div>
										</div>
										<div class="default-item-box item-box">
											<div class="title">인증번호</div>
											<div class="content">
												<input id="cerkeyInputId" name="content" class="form-control" onkeydown='return onlyNumber(event)' 
													onkeyup='removeChar(event)' maxlength="6" >
												<span id="countDown">03:00</span>
											</div>									
										</div>								
									</div>
								</td>
							</tr>
						</tbody>
			   		</table>			
				</div>
			</div>			
			<div class="content_summary_box content-item" style="text-align: left;">	
				<b style="color:#FF3547;">휴대폰 본인확인 시 타인 명의를 무단 도용할 경우. <br />
				 "정보통신망법 제 49조에 의거하여 5년 이하의 징역 또는 5천만원의 벌금에 처할 수 있습니다.</b>
			</div>
		</div>
		<div id="_page_1" class="page-wrap" style="display: none">
			<div id="content_title" class="content-item">[ 출입여부 선택 ]</div>
		
			<div class="content_box content-item">
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
		</div>
		<div id="_page_2" class="page-wrap" style="display: none">
			<div id="content_title" class="content-item">[ 개인정보 수집 및 이용동의 ]</div>
			<div class="content_summary_box content-item">
				(주)지에스아이엘 건설부문(이하 ‘회사‘)은 회사 사업장 방문자의 안전사고 예방을 위하여 아래와 같이 귀하의 개인정보를 수집 및 이용하고자 합니다.
			</div>
			<div class="content_table_box content-item">
				<table>
					<colgroup>
						<col style="width:24%;">
                        <col style="width:38%;">
                        <col style="width:*%;">
					</colgroup>
					<tbody>
						<tr>
                            <th>수집항목</th>
                            <th>수집목적</th>
                            <th>보유 및 이용기간</th>
                        </tr>
                        <tr>
                            <td style="text-align: left;">
	                            <b>이름, 생년월일 앞자리, 성별, 업체, 휴대폰 번호</b>	                       
                            </td>
                            <td>방문기록, 출역관리</td>
                            <td style="font-size:16px; font-weight:bold; color:orange;">프로젝트 종료시까지</td>
                        </tr>
                    </tbody>
				</table>
			</div>
			
			<div class="agree">
				<span><input type="checkbox" id="agree" name="agree" style="background:url('')"><label for="agree">&nbsp&nbsp(필수) 위 개인정보 수집이용에 동의합니다. </label></span>
			</div>
			
			<div class="content_summary_box content-item">
				귀하는 본 동의를 거부하실 수 있으나, 거부 시 출역 확인에 제한될 수 있습니다.
			</div>
			<div class="content_summary_box content-item">
				※ 그 외 개인정보처리에 대한 사항은 홈페이지(http://segp5.gsil.net/policy_privacy) 개인정보처리방침에서 확인하실 수 있습니다.
			</div>
		</div>
		<div id="_page_3" class="page-wrap" style="display: none">
			<div id="content_title" class="content-item">[ 정보 입력 ]</div>
			<div class="content_table_box content-item">		
				<div class="table-container">		   		
			   		<table id="regTable" class="reg-table table table-bordered col-xs-12 table-hover">				
						<tbody>
							<tr>
								<th class="text-center">소속현장</th>
								<td>	
									<div class="item">
										삼성엔지니어링 평택 P5-PJT 그린동
									</div>						
								</td>
							</tr>							
							<tr>
								<th class="text-center">구분</th>
								<td>
									<select id="role" name="role" class="form-control">
										<option value="2">기술인</option>
										<option value="1">관리자</option>		
									</select>	
								</td>
							</tr>
							<tr>
								<th class="text-center required">성 명</th>
								<td>
									<input id="name" name="name" placeholder="ex) 박민호" class="form-control" type="text" value="" maxlength="20">									
								</td>
							</tr>							
							<tr>
								<th class="text-center required">주민번호<br />앞 6자리</th>
									<td>
										<input id="jumin" path="jumin" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' maxlength="6" class="form-control"
											style="cursor:pointer; text-align:center;" 	placeholder="ex) 900101" />
									</td>
								</tr>							
							<tr>
								<th class="text-center">성별</th>
								<td>
									<select id="jumin_back" name="jumin_back" class="form-control">
										<option value="1">남</option>										
										<option value="2">여</option>										
									</select>	
								</td>
							</tr>
							<tr>
								<th class="text-center">핸드폰번호</th>
								<td>
									<input class="form-control" id="phone" name="phone" onkeydown='return onlyNumber(event)' 
										onkeyup='removeChar(event)' maxlength="11" style="cursor:pointer; text-align:center;" placeholder="ex) 01012345678" >															
								</td>
							</tr>
							<tr>
								<th class="text-center required">업체</th>
								<td> 
									<select id="cont" name="cont_id" class="form-control" >
										<option value="-1" selected="selected">선택</option>	
										<c:forEach var="cont" items="${contList}" varStatus="idx">
											<option value="${cont.id}" id="${cont.type}">${cont.name}</option>
										</c:forEach>			
										<option value="-2" >기타</option>																	
									</select>									
								</td>
							</tr>	
							<tr id="addContBox" style="display:none;">
								<th class="text-center">업체 추가</th>
								<td> 
									<input id="cont_name" name="cont_name" placeholder="ex) 업체명" class="form-control" type="text" value="" maxlength="20">											
								</td>
							</tr>									
						</tbody>
					</table>				
				</div>		
			</div>		
		</div>
		<div id="_page_4" class="page-wrap"  style="display: none">
		</div>
	</div>
	<div id="contentBtnBox">
		<div class="btn-box">
    		<button id="prebtn" class="btn" onclick="preStep()">취소</button>
    	</div>
    	<div  class="btn-box">
    		<button id="nextbtn" class="btn" style="display: none;" onclick="nextStep()">다음</button>
    	</div>
	</div>

<%@ include file="MonitorBottom.jsp"%>