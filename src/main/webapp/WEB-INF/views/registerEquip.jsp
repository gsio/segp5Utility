<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

var isUpdate = ${update};
var driver_name = '${equipVO.driver_name}';
var driver_phone = '${equipVO.driver_phone}';

var CUR_DRIVER_LIST = [];

$(document).ready(function() {
	
	if(isUpdate == true) {
		$("#content_title").html("반입전 장비 수정");
		$('#equipRegisterForm').attr('action', 'updateEquipData');
		$('#driver_name_text').html(driver_name);
		$('#driver_phone_text').html(driver_phone);
	}
	else {
		$("#content_title").html("반입전 장비 등록");
		$('#equipRegisterForm').attr('action', 'insertEquipData');
		addFileInputBox();
	} 
	
	initImage();
	
	$(".datepicker").datepicker({
		yearRange: '-1:+1',    
    	showSecond: true,
    	/*
    	onClose: function (selectedDate) {    
    		// 추후 유효성 검사 (시작일 < 종료일 체크 시 사용)
    	}
		*/
    }).attr('readonly','readonly');

});

function initImage(){	
	var equipImage = '${equipVO.equip_img}';
	if(typeof equipImage!= "undefined" && equipImage.length >= 1){
		$('#localpath_div_1').hide(); 
		$('#upfile_div_1').hide();
	}
	else{
		$('#image_div_1').hide();
		$('#file_btn_1').hide();
	}
}

function submitEquip() {
	var input;
	
	if(isUpdate == true) {
		input = confirm('수정하시겠습니까?');
	}
	else {
		input = confirm('등록하시겠습니까?');
	}
	
	if(input) {	  
		
		var isOk = checkDetail();
		
		if(isOk){			
			$('#equipRegisterForm').submit();	   
		}
		else {
			alert('항목을 다시 확인해주시기 바랍니다.');
		}	
		
	}
	else {
		return;
	}		
}

function checkDetail() {
	
	var isOk = true;
	
	var reg_e_no = $('#equip_registration_no').val();
	var reg_dr_id = $('#driver_id').val();
	var reg_dr_ph = $('#driver_phone').val();
	var reg_dr_no = $('#driver_license_no').val();
	
	if(reg_e_no.length < 1) {
		$('#equip_registration_no').css('border','2px solid red');
		isOk = false;
	} 
	else {
		$('#equip_registration_no').css('border','1px solid #ced4da');
	}
	
	if(reg_dr_id.length < 1) {
		$('#driver_id').css('border','2px solid red');
		isOk = false;
	} 
	else {
		$('#driver_id').css('border','1px solid #ced4da');
	}
	
	if(reg_dr_ph.length < 1) {
		$('#driver_phone').css('border','2px solid red');
		isOk = false;
	} 
	else {
		$('#driver_phone').css('border','1px solid #ced4da');
	}
	
	if(reg_dr_no.length < 1) {
		$('#driver_license_no').css('border','2px solid red');
		isOk = false;
	} 
	else {
		$('#driver_license_no').css('border','1px solid #ced4da');
	}
	
	if($("#equip_check_start").val() > $("#equip_check_end").val()) {
		isOk = false;
		$("#equip_check_end").css('border','2px solid red');
	}
	else {
		$("#equip_check_end").css('border','1px solid #ced4da');
	}
	
	if($("#equip_insur_start").val() > $("#equip_insur_end").val()) {
		isOk = false;
		$("#equip_insur_end").css('border','2px solid red');
	}
	else {
		$("#equip_insur_end").css('border','1px solid #ced4da');
	}
	
	return isOk;
}

function deleteEquipData() {	
	input = confirm('공지사항을 삭제하시겠습니까?');
	if(input){
		$('#equipRegisterForm').attr('action', 'deleteEquipData');
		$('#equipRegisterForm').submit();
	}
}

// [ type ]  
// 1 = reg, 
// 2 = reply

function deleteFile(idx){
	$('#reg_file_' + idx).html('<input id="reg_file_'+ idx + '" name="file_list['+ idx +'].file" type="file" class="form-control">');
	//수정체크
	$('#reg_idx_' + idx).val(1);
}

function addFileInputBox() {
	var index = $("#fileBox > div").length;
	var html = '<div id="reg_file_'+index+'" class="file-item"><input id="reg_file_'+index+'" name="file_list['+index+'].file" type="file" class="form-control"></div>';
	$("#fileBox").last().append(html);
}

function deleteFileInputBox() {
	var index = $("#fileBox > div").length - 1;
	if(index < 1) {
		alert("최소 1개 이상 파일 칸을 남겨두어야 합니다.");
	}
	else {
		deleteFile(index);
		$("#fileBox > div").last().remove();	
	}	
}

function overlayDriverList() {
	$('#modal_driverList').click();
	getDriverList();
}

function getDriverList() {
	
	CUR_DRIVER_LIST = [];
	
	$('#driverTable').bootstrapTable();
	
	<c:forEach var="item" items="${driverList}" varStatus="idx">	
		
		var id = ${item.id};
		var equip_id = ${item.equip_id};
		var equip_text = ''; 
		var cont_id = ${item.cont_id};
		var cont_name = '${item.cont_name}';
		var name = '${item.name}';
		var role_name = '${item.t_name}';
		var phone = '${item.phone}';
		
		if(equip_id == 0) {
			equip_text = '<div class="label" style="color:#4bc91f;">배정가능</div>';
		}
		else {
			equip_text = '<div class="label" style="color:#ff3547;">배정중</div>'
		}
		
		var select = '<div class="btn btn-primary" onclick="selectDriver('+id+',\''+cont_name+'\',\''+name+'\',\''+phone+'\')"><i class="fa-solid fa-check"></i></div>';	
		
		var driver_info = 
			'<div class="text-left info-item">' + 
			'<div> 업체: '+cont_name+'</div>' +
        	'<div> 이름: '+name+'</div>' +
        	'<div> 권한: '+role_name+'</div>' +
        	'<div> 연락처: '+phone+'</div>' +
			'</div>';
	
		var driver = {
			select : select,
			id : id,
			equip_text : equip_text,	
			driver_info : driver_info
		}	
		
		CUR_DRIVER_LIST.push(driver);	
	
	</c:forEach>
	
	if(CUR_DRIVER_LIST.length > 0) {
		$('#driverTable').bootstrapTable('load', CUR_DRIVER_LIST);		
	}
	else {
		$('#driverTable').bootstrapTable('load', []);
	}
}

function selectDriver(id, cont_name, name, phone) {
	$("#driver_id").val(id);
	$("#driver_phone").val(phone);	
	
	var driver_info = '<span style="font-weight: 600;">'+cont_name+'</span> - ' + name;
	
	$("#driver_name_text").html(driver_info);
	$("#driver_phone_text").text(phone);
	
	$('#__close_driver_btn').click();
} 

function unAssignDriver() {	
	$("#driver_id").val('');
	$("#driver_phone").val('');	
	$("#driver_name_text").html('');
	$("#driver_phone_text").text('');
	$('#__close_driver_btn').click();
}

function changeImage(val){
	 $('#localpath_div' + '_' + val).show(); 
	 $('#upfile_div' + '_' + val).show(); 
	 $('#image_div' + '_' + val).hide(); 
	 $('#file_btn'+ '_' + val).hide(); 
}

</script>

<style>

.table-container {
    max-width: 100%;
    width: 100%;
}

.content_table_box  {
	display: flex;
    align-items: center;
    flex-direction: column;
    align-content: flex-start;
    justify-content: center;
    flex-wrap: nowrap;
}

#registerEquipTable {
	margin-bottom: 20px;
}

#registerEquipTable th {
    min-width: 100px;
    width: 100px;
	background: #FFF;
	font-size: 16px;
}

#registerEquipTable th.required {
	color: #FF3547;
	font-weight: 600;		
}

#registerEquipTable .datepicker-box {
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    flex-wrap: nowrap;
    align-items: center;
}

.ui-datepicker {
    font-size: 16px !important;
}

#registerEquipTable td input, #registerEquipTable td select{
   	height: 40px;
    max-width: 100% !important;
    background: #FFF;
    display: block;
    width: 100%;
    padding: 0.375rem 0.75rem;
    color: #495057;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
    font-size: 17px;
}

#registerEquipTable td input.datepicker {
    text-align: center;
    font-weight: 600;
	border-radius: 5px;
    font-size: 1em;
    font-weight: 600;
    text-align: center;
    background: #FFF;
    cursor: pointer;
}

#registerEquipTable td .item{
   	height: 40px;
    max-width: 100% !important;
    background: #FFF;
    display: block;
    width: 100%;
    padding: 0.375rem 0.75rem;
    color: #495057;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
    font-size: 17px;
}

#registerEquipTable td .item-btn {
    height: 100%;
    min-width: 80px;
    font-size: 3.6em;
}

#registerEquipTable .size-fix {
    max-width: 250px;
    max-height: 250px;
    height: 250px;
    border: 1px solid #D4D4D4;
    margin-bottom: 10px;
}

#registerEquipTable .file-input-hidden {
    position: absolute;
    top: 0;
    left: 0;
    opacity: 0;
    width: 100%;
    background: red;
}

#registerEquipTable .img_btn_wrapper {
	height: 40px;
    width: 100%;
    margin-top: 10px;
}

#registerEquipTable .img_btn_box { 
    min-width: 100px;    
	max-width: 120px;
    flex: 0 0 40px;
    box-shadow: 0 0 12px 0 rgb(0 0 0 / 10%);
    border-radius: 5px;
    font-size: 1em;
    color: #FFF;
    float: right;
    text-align: center;
    line-height: 40px;
    margin-left: 5px;
    padding: 0;
    position: relative;
    cursor: pointer;
} 

#registerEquipTable td .comment-box {
	padding: 10px;
}

#registerEquipTable td .reply-box {
    margin: 5px 0;
    padding: 0 0 0 5px;
	height: 50px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    align-content: center;
	position: relative;
}

#registerEquipTable td .reply-box .reply-input {
    height: 40px;
    font-size: 16px;
    background: #FFF;
    flex: 0 0 100%;
    max-width: 100% !important;
}

#registerEquipTable td .reply-box .reply-btn {
    right: 5px;
    max-width: 100px;
    min-width: 80px;
    position: absolute;
}

#registerEquipTable .item-comment-box {
    height: 100px;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    flex-wrap: nowrap;
    background: #FAFAFA;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
    margin: 5px 0px;
	padding: 10px;
    align-content: center;
}

#registerEquipTable .item-comment-box .comment-info, .item-reply-box .comment-info {
    display: flex;
    flex-direction: column;
    flex-wrap: nowrap;
    align-items: stretch;
    justify-content: flex-start;
    width: 100%;
    padding: 10px;
}

#registerEquipTable .item-comment-header {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    width: 100%;
    font-size: 16px;
}

#registerEquipTable .item-comment-body {
    font-size: 16px;
    height:35px;
    line-height: 35px;
    overflow: auto;
}

#registerEquipTable .item-comment-footer {
    height: 20px;
    padding: 0;
    display: flex;
    flex-direction: row-reverse;
}

#registerEquipTable td .item-comment-footer .btn {
	height: 20px;
    min-width: 30px;
    line-height: 0;
}

#registerEquipTable .comment-box img {
	height: 80px;
	width: 80px;
}

#registerEquipTable .item-comment-box .show-mobile {
	font-size: 16px;
}

#registerEquipTable .item-mobile-comment-header {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    width: 100%;
    font-size: 16px;
}

#registerEquipTable .item-reply-box {
    height: 100px;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    flex-wrap: nowrap;
    background: #f0eeee;
    border: 1px dotted #C4C4C4;
    border-radius: 1rem;
    margin: 5px 0px;
    padding: 10px;
    align-content: center;
    margin-left: 35px;
    position: relative;
}

#registerEquipTable .item-reply-box:before {
    content: "\f3e5";
    font-family: "Font Awesome 5 Free";
    font-weight: 900;
    left: -33px;
    top: 5px;
    position: absolute;
    transform: rotate(180deg);
    font-size: 1.5em;
}

#registerEquipTable .item-reply-box .show-mobile {
	font-size: 16px;
}

#registerEquipTable td .file-box{
	max-height: 200px;
	height: 200px;
	max-width: 100% !important;
    background: #FFF;
    display: block;
    width: 100%;
    padding: 0.375rem 0.75rem;
    color: #495057;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
    overflow: auto;
}

#registerEquipTable td .file-item {
   	height: 60px;
    max-width: 100% !important;
    background: #FFF;
    display: block;
    width: 100%;
    padding: 0.375rem 0.75rem;
    color: #495057;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
	margin: 5px 0;
	display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    align-content: center;
    flex-wrap: nowrap;
}

#registerEquipTable td .file-item > div {
	margin: 0 5px;
}

#registerEquipTable td .file-item .file-no {
    flex: 0 0 20px;
    font-size: 1.2em;
    font-weight: 600;
}

#registerEquipTable td .file-item .file-name {
  	font-size: 16px;
    font-weight: 600;
    color: #000;
}

#registerEquipTable td .file-item .file-delete-btn {
  	font-size: 1.1em;
    font-weight: 600;
    color: #ff3547;
    cursor:pointer;
}

#registerEquipTable .btn-box {
    display: flex;
    flex-direction: column;
    align-items: center;
    flex-wrap: nowrap;
    align-content: center;
	width: 50px;
}

#registerEquipTable .btn-box > div {
    height: 100%;
    min-width: 50px;
    font-size: 1.5em;
    display: flex;
    flex: 0 0 100px;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    align-content: center;
    flex-wrap: nowrap;
}

#driverTable .info-item{
	font-size: 16px;
    display: flex;
    padding: 3px;
    font-weight: 600;
    flex-direction: column;
    flex-wrap: nowrap;
    align-items: flex-start;
}

</style>

<form:form id="equipRegisterForm" class="form-horizontal" method="POST" modelAttribute="equipVO" enctype="multipart/form-data" autocomplete="off">

<div id="content-wrapper">
	<div id="content_title" class="content-item"></div>
	
	<form:input type="hidden" path="id" value="${equipVO.id}"/>			
	<form:input type="hidden" path="site_id" value="${userLoginInfo.site_id}"/>
		
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="history.back(-1)"><i class="fa-solid fa-arrow-rotate-left"></i> 취소</div>	
		<c:if test="${!update}">
			<div class="btn btn-default" onclick="submitEquip()"><i class="fa-regular fa-registered"></i> 등록</div>
		</c:if>   
		<c:if test="${update}">
			<div class="btn btn-danger" onclick="deleteEquipData()"><i class="fa-solid fa-trash"></i> 삭제</div>
			<div class="btn btn-default" onclick="submitEquip()"><i class="fa-regular fa-pen-to-square"></i> 수정</div>
		</c:if>
	</div>
	
	<div class="content_table_box content-item">	

		<div class="web-reg-box table-container">
			<table id="registerEquipTable" 
				class="reg-table table table-bordered col-xs-12 table-hover">	
				<tr>
					<th class="text-center required">업 체</th>		   				   	
					<td colspan="2" class="text-left">
						<c:choose>
							<c:when test="${sessionScope.userLoginInfo.cont_type == 0}"> 	
								<form:select id="search_cont_id" name="cont_id" class="form-control select-content" path="cont_id">								
									<c:forEach var="cont" items="${contList}" varStatus="idx">
									<option value="${cont.id}">${cont.name}</option>
									</c:forEach>	  
								</form:select>
							</c:when>
							<c:otherwise>
								<form:select name="cont_id" class="form-control select-content" path="cont_id">				
									<option value="${sessionScope.userLoginInfo.cont_id}">${sessionScope.userLoginInfo.cont_name}</option>
								</form:select>
								<input id="cont_id" type="hidden" name="cont_id" value="${sessionScope.userLoginInfo.cont_id}"/>
							</c:otherwise>	
						</c:choose>
					</td>
				</tr>
				<tr>
					<th class="text-center required">작성자</th>
					<td colspan="2" class="text-left">
						<div class="item" style="background: #F2F4F4 !important;"> <span style="font-weight: 600; ">${userLoginInfo.cont_name}</span> - ${userLoginInfo.name}</div>
					</td>
				</tr> 
				
				<tr>
					<th class="text-center">장비사진</th>
					<td colspan="2" class="text-left">
						<!-- File PreView -->
						<div id="image_div_1">
							<img class="size-fix img" src="image?virtname=${equipVO.equip_img}" />
						</div>
                        <!-- path -->
                        <div id="localpath_div_1" class="padding-height">
                            <form:input type="text" id="equip_img" readonly="readonly" class="form-control" placeholder="ex) 용량 ≤ 1MB" path='equip_img' accept="image/*" />
                        </div>
                        <div class="img_btn_wrapper">
                        	<div id="file_btn_1" class="btn btn-primary margin-top img_btn_box" onclick="changeImage('1');">
								<i class="fa-regular fa-pen-to-square"></i> 수정
							</div>
	                        <!-- file upload -->
	                        <div id="upfile_div_1" class="file_input_div margin-top img_btn_box">
	                            <div class="btn btn-default img_btn_box" ><i class="fa-regular fa-image"></i> 사진
	                            	<form:input class="upfile padding-width-none file-input-hidden" path='equip_img_file' 
	                         	 	type="file" size="100" title="첨부할 파일을 찾습니다." style="cursor:pointer" onchange="setFilePath('equip_img', this.value)"/>
	                            </div>	                            
	                        </div>	                        
                        </div>
					</td>
				</tr>
				
				<tr>
					<th class="text-center required">대분류</th>
					<td colspan="2" class="text-left">
						<select id="search_equip_category" name="category_id" class="form-control select-content">								
							<c:forEach var="vo" items="${eCategory}" varStatus="idx">
							<option value="${vo.id}">${vo.name}</option>
							</c:forEach>	  
						</select>
					</td>
				</tr>
				
				<tr>
					<th class="text-center">소분류</th>
					<td colspan="2" class="text-left">
						 <form:input class="form-control" path="small_category_name" maxlength="100" placeholder="ex) 모델명(R/EC,EW/DX/SOLAR) + 15W" />
					</td>
				</tr>
				
				<tr>
					<th class="text-center">규격</th>
					<td colspan="2" class="text-left">
						<form:input class="form-control" path="equip_standard" maxlength="100" placeholder="ex) kg, ㎥/min" />
					</td>
				</tr>
				
				<tr>
					<th class="text-center required">장비번호</th>
					<td colspan="2" class="text-left">
						<form:input id="equip_registration_no" class="form-control" path="equip_registration_no" maxlength="9" placeholder="ex) 지역00가0000"/>
					</td>
				</tr>
				
				<tr>
					<th class="text-center">임대업체</th>
					<td colspan="2" class="text-left">
						<form:input class="form-control" path="rent_cont_name" maxlength="100" placeholder=""/>
					</td>
				</tr>
				
				<tr>
					<th class="text-center">장비<br>검사일</th>
					<td colspan="2" class="text-left">
						<div class="datepicker-box">							
							<form:input id="equip_check_start" class="datepicker form-control" path="check_start" />
							<span>&nbsp~&nbsp</span>
							<form:input id="equip_check_end" class="datepicker form-control" path="check_end" />							
						</div>				
					</td>
				</tr>
				
				<tr>
					<th class="text-center">보험<br>일자</th>
					<td colspan="2" class="text-left">
						<div class="datepicker-box">
							<form:input id="equip_insur_start" class="datepicker form-control" path="insur_start" />
							<span>&nbsp~&nbsp</span>
							<form:input id="equip_insur_end" class="datepicker form-control" path="insur_end" />
						</div>	
					</td>
				</tr>				
				
				<tr>
					<th class="text-center required">운전원<br>선택</th>
					<td class="text-left">
						<form:input id="driver_id" type="hidden" class="form-control" path="driver_id" />
						<div id="driver_name_text" class="item"></div>
					</td>
					<td id="driver-btn-box">
						<div class="icon-default" onclick="overlayDriverList()" style="font-size: 1.5em; cursor: pointer;"><i class="fa-solid fa-circle-plus"></i></div>		
					</td>
				</tr>
				
				<tr>
					<th class="text-center required">운전원<br>연락처</th>
					<td colspan="2" class="text-left">
						<form:input id="driver_phone" type="hidden"  class="form-control" path="driver_phone" />
						<div id="driver_phone_text" class="item" style="background: #F2F4F4 !important;"></div>
					</td>
				</tr>
				
				<tr>
					<th class="text-center required">운전원<br>면허번호</th>
					<td colspan="2" class="text-left">
						<form:input id="driver_license_no" class="form-control" path="driver_license_no" maxlength="100" placeholder="ex) 지역 00-0000-0000-00 또는 00-00-000000-00"/>
					</td>
				</tr>
				
		   		<tr>
					<th class="text-center">첨부파일</th>
					<td class="text-left" style="height: 200px; width: 100%;">
						<div id="fileBox" class="file-box">
							<c:if test="${update}">	
								<c:if test="${equipVO.file_list.size() > 0}">	
								<c:forEach begin ="0" end="${equipVO.file_list.size() - 1}" varStatus="loop">
									<form:input type="hidden" id="reg_idx_${loop.index}" class="form-control" path='file_list[${loop.index}].ismodify' value="0" />
									<c:if test="${equipVO.file_list[loop.index].orgname == null}">                           		
										<form:input type="file" id="reg_file_${loop.index}" class="form-control" path='file_list[${loop.index}].file' />
			    					</c:if>
			    					<c:if test="${equipVO.file_list[loop.index].orgname != null}">
										<form:input type="hidden" class="form-control" path='file_list[${loop.index}].virtname' /><!-- update시 참조 -->                           		
										<div id="reg_file_${loop.index}" class="file-item">
											<div class="file-no">${loop.index + 1}</div>
											<div class="file-name">${equipVO.file_list[loop.index].orgname}</div>
											<div class="file-delete-btn" onclick="deleteFile('${loop.index}')">
												<i class="fa-solid fa-xmark"></i>
											</div>	
										</div>
									</c:if>	
								</c:forEach>
								</c:if>
							</c:if>	
						</div>						
					</td>
					<td id="file-btn-box" class="btn-box">
						<div class="item-btn btn icon-default" onclick="addFileInputBox()"><i class="fa-solid fa-circle-plus"></i></div>					
						<div class="item-btn btn icon-danger" onclick="deleteFileInputBox()"><i class="fa-solid fa-circle-minus"></i></div>
					</td>
				</tr>
				<tr>
		   			<th class="text-center">비고</th>
		   			<td colspan="2" class="text-left" style="height: 300px;">
						<div class="item" style="height: 100%;">
							<form:textarea style="height: 100%;" class="form-control" path="remark" rows="15" />
						</div>
					</td>
				</tr>
			</table>	
		</div>
	</div>
</div>

</form:form>

<div id="form_group">
	<form id="updateForm" action="registerEquip" method="POST">
		<input id="equip_id" type="hidden" name="id" value="${equipVO.id}" />
	</form>
</div>

<div style="display:none" id="btn_image_modal" data-toggle="modal" data-target="#image_modal"></div>
<div class="modal fade" id="image_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
	  	<div class="modal-content">
		   	<div class="modal-header" style="font-size: 1.5em;">
		   		첨부파일 이미지 확대
		   	</div>
			<div class="modal-body">
				<img id="file_image" src="" class="enlargeImageModalSource" style="width: 100%; max-width:750px;" onerror="this.src='images/noimage.png'">
			</div>
		</div>
	</div>
</div><!-- btn_image_modal END -->

<div style="display:none" id="modal_driverList" data-toggle="modal" data-target="#driver_modal"></div>
<div class="modal fade" id="driver_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
  		<div class="modal-content">
	   		<div class="modal-header" style="font-size: 1.5em;"> 
	   			<div>
   					운전원 배정
   				</div>    
				<div>
					<div class="btn btn-danger margin-top" onclick="unAssignDriver()"><i class="fa-solid fa-trash"></i> 삭제</div>
					<div id="__close_driver_btn" class="btn btn-default margin-top" data-dismiss="modal"><i class="fa-solid fa-xmark"></i> 닫기</div>
				</div>
			</div>
			<div class="modal-body">
		        <div class="col-sm-12">
				    <div id="sub-tab-box" class="tab-content nav nav-tabs">
						<table id="driverTable" data-click-to-select="true" data-search="true" class="table table-bordered">
							<thead>
								<tr>		
								 	<th data-field="select" class="text-center">선택</th>			
								 	<th data-field="driver_info" class="text-center">운전원<br>정보</th>
								 	<th data-field="driver_text" class="text-center">배정여부</th>
								</tr>
							</thead>
						</table>
				 	</div>	<!-- #sub-tab-box -->
      			</div>
   			</div>
  		</div>
	</div>
</div>

<%@ include file="IncludeBottom_renewal.jsp"%>
