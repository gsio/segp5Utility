<%@ include file="MonitorTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
 
<script>

	const CUR_SITE_ID = 17;
	var G_STATE = 1;
	var G_UW_ID = '';
	var G_ROLE = 0;
	
	/* --------------------	
	[G_STATE]
	0: Init
	1: 필수준수사항
	2: New Engineer
		2.1: 개인정보 수집 및 이용동의 체크 X 
		2.2: 개인정보 수집 및 이용동의 체크 O
	3: Init Insert Info
	4: 입장
	5: 퇴장
	-------------------- */
	var interval;

	$(document).ready(function() {
		
		if(!isConnectMobile()) {
			location.href = "main";
			window.close();
		}
		
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
		case 1.1:
		case 2:
		case 2.1:
		case 5.1:
			location.reload();
			break;
		case 3:
			$('#prebtn').html("이전");
			$('[id^=_page_]').hide();
			$("#_page_2").show();
			$('#agree').prop('checked', false);
			G_STATE = 2.1;
			break;
		case 4:	
			G_STATE = 1.1;
			moveEnterPage();
			break;
		}	
	}
	
	function nextStep() {		
		//alert("[nextStep]: " + G_STATE)
		switch(G_STATE) {		
		
			case 1:
				moveEnterPage();
				break;
		
			case 1.1:
				if(isDutyChecked()) {
					$('[id^=_page_]').hide();
					$("#_page_4").show();
					G_STATE = 4;
					showNextBtn("입장");
				}
				else {
					alert("필수 준수사항을 확인하셔야 다음 진행이 가능합니다.");
				}
				break;
		
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
				
			case 4:
				postWorkIn();
				break;
				
			case 5:
				$('[id^=_page_]').hide();
				$("#_page_5").show();
				G_STATE = 5.1;
				showNextBtn("퇴장");
				break;
				
			case 5.1:
				postWorkOut();
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
		var phone = $('#inputPhone').val();
		var cont_id = $('#cont').val();
		var cont_name = $('#cont_name').val();
		var work_type = $('#workType').val();
		
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
				cont_name: cont_name,
				work_type: work_type
			},
			async: true,
			cache: false,			
			success: function (json, status) {	
				var data = JSON.parse(json);
				if(data.result == "true") {
					G_ROLE = data.role;
					G_UW_ID = data.uw_id;
					alert("등록에 성공하였습니다.");
					G_STATE = 1;
					moveEnterPage();
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
		var phone = $('#inputPhone').val();
		var cont_id = $('#cont').val();
		var cont_name = $('#cont_name').val();
		var work_type = $('#workType').val();
		
		/*
		console.log("----------------- 정보입력");
		console.log("[구분]", role);
		console.log("[이름]", name);
		console.log("[생년월일]", jumin);
		console.log("[성별]", jumin_back);
		console.log("[핸드폰번호]", phone)
		console.log("[업체]", cont_id);
		console.log("[업체명]", cont_name);
		console.log("[직종/권한]", work_type);
		*/
		
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
		   	alert("핸드폰을 다시 인증해주세요");
			location.reload();
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

	function changeWtList() {

		var role = $("#role").children(":selected").attr("value");
		var cont_type = $("#cont").children(":selected").attr("value");		
	
		$("#workType").empty();
		
	
		if(role == 1) {
			<c:forEach var="ur" items="${uRList}" varStatus="idx">
				if(cont_type == '${ur.type}') {
					$("#workType").append("<option value='${ur.code}' id='${ur.role}'>${ur.name}</option>");
				}		
			</c:forEach>					
		}
		else if(role == 2){	
			<c:forEach var="wt" items="${wTList}" varStatus="idx">
				$("#workType").append("<option value='${wt.id}' id='${wt.role}'>${wt.name}</option>");
			</c:forEach>	
		}		
	}
	
	function moveEnterPage() {
		$('#prebtn').html("취소");
		$('[id^=_page_]').hide();
		$("#_page_1").show();
		G_STATE = 1.1;
		showNextBtn("다음");	
	}
	
	function isDutyChecked() {
		var isAllChecked = true;
		$('[id^=_duty_]').each(function() {
			if (!$(this).is(':checked')) {
				isAllChecked = false;
		    	return false; // 반복문 종료
		  	}
		});
		return isAllChecked;
	}
	
	function postWorkIn() {
		$.ajax({
			type: "POST",				
			url: 'qr/insertQRInData',
			data: {			
				site_id: CUR_SITE_ID,
				uw_id: G_UW_ID,
				role : G_ROLE
			},
			async: true,
			cache: false,			
			success: function (json, status) {
				var data = JSON.parse(json);
				if(data.result == "true") {		
					alert("입장완료");
					location.href = "main";
					window.close();
				}
				else {
					alert(data.err);
				}			
							
	       	}
		});	
	}
	
	function postWorkOut() {
		$.ajax({
			type: "POST",				
			url: 'qr/insertQROutData',
			data: {			
				site_id: CUR_SITE_ID,
				uw_id: G_UW_ID,
				role : G_ROLE,
				comment: $("#comment").val()
			},
			async: true,
			cache: false,			
			success: function (json, status) {					
				var data = JSON.parse(json);
				if(data.result == "true") {					
					alert("퇴장완료");
					location.href = "main";
					window.close();
				}
				else {
					alert(data.err);
				}			
							
	       	}
		});	
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

.content_info_box {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    padding: 30px 10px 0px 10px;
}

.info_wrap {
    width: 400px;
    height: 200px;
}

.info_box {
  	display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    border-radius: 2vh;
    background: #E0E0E0;
    width: 100%;
    height: 100%;
    position: relative;
    margin-right: 0.43%;
    border: 0.2vh solid #dee2e6;
    box-shadow: 0 3px 4px 0 rgba(0, 0, 0, 0.24), 0 4px 12px 0 rgba(0, 0, 0, 0.19);
    padding: 1vh 0vh 1vh 1vh;
}

.photo_box {
	flex: 0 0 110px;
}

.photo_box img{
    width: 120px;
    height: 170px;
    border: 1px solid #dee2e6;
}

.detail_box {
    flex: 100%;
    padding: 0px 20px;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: flex-start;
}
.time_box {
    display: flex;
    justify-content: space-between;
    flex-direction: row;
    width: 100%;
}

.worker {
    height: 35px;
    line-height: 35px;
    font-size: 16px;
}

#_uw_work_min {
    color: white;
    font-weight: bold;
    border-radius: 5px;
    flex: 0 0 70px;
    text-align: center;
}

#_uw_start_time {
	font-weight: bold;
	color: #666;
}

.time_box .green {
	background: linear-gradient(180deg, #25B869, #009245, #006B31);
}

.time_box .yellow {
	background: linear-gradient(180deg, #F7D11E, #F7A91E, #E45624);
}

.time_box .red {
	background: linear-gradient(180deg, #ED797E, #ED1C24, #C1272D);
}

.content-check-box {
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 60px;
    background: #EFEFEF;
    height: 100%;
    margin-top: 10px;
    justify-content: space-between;
    border: 1px solid #dee2e6;
	padding: 10px 15px;
}

.content-check-box .duty {
	height: 15px;
    width: 15px;
	margin-left: 10px;
}

.content-check-box .duty_label {
	margin-left: 20px;
	font-weight: bold;
    padding: 10px 10px;
}

.content-img .duty_img{
 
}

.content-img {
    display: flex;
    align-items: center;
    justify-content: space-around;
}

</style>


<div id="content-wrapper">
	<div id="contentPage">
		<div id="_page_0" class="page-wrap" >
			<div class="content_title content-item">[신원 확인]</div>		
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
													onkeyup='removeChar(event)' maxlength="4" >
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
			<div class="content_info_box content-item">	
				<div id="infoWrap" class="info_wrap" style="display: none;">
					<div class="info_box">
						<div class="photo_box">
							<span id="_uw_photo" >
								<img src="" onerror="this.src='images/noimage.png'">
							</span>
						</div>
						<div class="detail_box">
							<div class="worker">
								<span id="_uw_cont_name" ></span>
							</div>
							<div class="worker infos">
								<div>
									<span id="_uw_name" ></span>
								</div>
							</div>
							<div class="worker">
								<span id="_uw_wt_type"></span>
							</div>
							<div class="worker time_box">
								<span id="_uw_start_time"></span>
								<span id="_uw_work_min" class="green"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		<div id="_page_1" class="page-wrap" style="display: none">
			<div class="content_title content-item">[필수 준수사항]</div>	
			<div class="content_check_box content-item">
				<div class="content-check-box">
					<input id="_duty_1" class="duty" type="checkbox"  name="agree" style="background:url('')">
					<label class="duty_label" for="agree" style="color: #ff3547;    ">당사 직원의 통제에 따라주세요. <br />(방문목적 외 임의행동 금지)</label>
				</div>
				<div class="content-img">
					<img class="duty_img" src="images/icons/qr/duty_img1.png">
				</div>
			</div>
			<div class="content_check_box content-item">
				<div class="content-check-box">
					<input id="_duty_2" class="duty" type="checkbox"  name="agree" style="background:url('')">
					<label class="duty_label" for="agree">정해진 통로 외 임의이동을 하지 말아주세요.</label>
				</div>
				<div class="content-img">
					<img class="duty_img" src="images/icons/qr/duty_img2.png">
				</div>
			</div>
			<div class="content_check_box content-item">
				<div class="content-check-box">
					<input id="_duty_3" class="duty" type="checkbox"  name="agree" style="background:url('')">
					<label class="duty_label" for="agree">작업중인 장비주변은 접근하지 말아주세요 <br />(유도자 지시 이행)</label>
				</div>
				<div class="content-img">
					<img class="duty_img" src="images/icons/qr/duty_img3.png">
				</div>
			</div>
		</div>
		<div id="_page_2" class="page-wrap" style="display: none">
			<div class="content_title content-item">[개인정보 수집 및 이용동의]</div>
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
		<div id="_page_3" class="page-wrap" style="display: none" >
			<div class="content_title content-item">[회원 가입]</div>
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
									<select id="role" name="role" class="form-control" onchange="changeWtList(this)">
										<option value="2">기술인</option>
										<option value="1">관리자</option>		
									</select>	
								</td>
							</tr>							
							<tr>
								<th class="text-center required">업체</th>
								<td> 
									<select id="cont" name="cont_id" class="form-control" onchange="changeWtList(this)">
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
							<tr>
								<th class="text-center required">직종 / 권한</th>
								<td> 
									<select id="workType" name="workType" class="form-control" >
										<c:forEach var="wt" items="${wTList}" varStatus="idx">
											<option value="${wt.id}" id="${wt.role}">${wt.name}</option>
										</c:forEach>																			
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
							<!-- 							
							<tr>
								<th class="text-center">핸드폰번호</th>
								<td>
									<input class="form-control" id="phone" name="phone" onkeydown='return onlyNumber(event)' 
										onkeyup='removeChar(event)' maxlength="11" style="cursor:pointer; text-align:center;" placeholder="ex) 01012345678" >															
								</td>
							</tr>				
							-->	
						</tbody>
					</table>				
				</div>		
			</div>		
		</div>
		<div id="_page_4" class="page-wrap"  style="display: none">
			<div class="content_title content-item">[입장]</div>		
			<div class="content_img_box content-item">
				<div class="content-img">
					<img class="duty_img" src="images/icons/qr/finish_bg2.png">
				</div>
			</div>	
		</div>
		<div id="_page_5" class="page-wrap" style="display: none">
			<div class="content_title content-item">[의견 및 퇴장]</div>	
			<div class="content_summary_box content-item">
				<div style="margin-bottom: 10px;">여러분의 소중한 의견을 모아 안전한 삼성현장을 만들겠습니다.</div>	
				<input id="comment" placeholder="ex) 작업 중 이상현상 또는 의견" class="form-control" type="text" value="" maxlength="100">			
			</div>
			<div class="content_img_box content-item">
				<div class="content-img">
					<img class="duty_img" src="images/icons/qr/opinion.png">
				</div>
			</div>			
		</div>
	</div>
	<div id="contentBtnBox">
		<div class="btn-box">
    		<button id="prebtn" class="btn"  onclick="preStep()">취소</button>
    	</div>
    	<div  class="btn-box">
    		<button id="nextbtn" class="btn" style="display: none;" onclick="nextStep()">다음</button>
    	</div>
	</div>

<%@ include file="MonitorBottom.jsp"%>