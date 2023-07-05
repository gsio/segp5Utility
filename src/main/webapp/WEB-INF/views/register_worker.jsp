<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>
$(document).ready(function() {

});


function submitWorker() {	
	var input;
	input = confirm('등록하시겠습니까?');	
	
	if(input) {	
		var isOk = checkDetail();		
		if(isOk){			
			$('#workerForm').submit();
		}
		else {
			alert('항목을 다시 확인해주시기 바랍니다.');
		}
	}
	else {
		return;
	}		 
}

function checkDetail(){
	
	var isOk = true;
	
	$('[id^=worker_name_]').each(function () {
		var this_id = $(this).attr('id');
		var this_name = $(this).val();		
		
		if(this_name.length >= 1){
			var id = this_id.split('worker_name_')[1];		
			var phone =	$('#worker_phone_' + id).val();			
			 
		   	var rgEx = /(01[016789])(\d{4}|\d{3})\d{4}$/g;//핸드폰 정규표현식
		   	var chk_phone = rgEx.test(phone);   
		   	if(!chk_phone || phone.length < 10) {				
			   	$('#worker_phone_' + id).css('border','2px solid red');
			   	isOk = false;
			} 
		   	else {
				$('#worker_phone_' + id).css('border','1px solid #cccccc');				

			 	var result = checkDuplicatePhone(phone);
			  	if(result >= 0) {				
				   	isOk = true; 
			  	} 
		  		// 현장내 중복
		   		else if(result == -99) {
					$('#worker_phone_' + id).css('border','2px solid red');
					isOk = false; 
			  	} 
		   	 	// -1은 PASS
		   		else{
		   			
			  	}
			}
		   
			var jumin =	$('#worker_jumin_' + id).val();		   
			if(jumin.length < 6){
			   isOk = false;
			   $('#worker_jumin_' + id).css('border','1px solid red');
			}
			else {			 
				$('#worker_jumin_' + id).css('border','1px solid #cccccc');
			}
		   
			var edu_date = $('#worker_edudate_' + id).val();
		   
			// 입력안할경우 패스
			if(edu_date.length > 0){
				//달력 정규표현식
				var rgEx_date = /^\d{4}-[01][0-9]-[0123][0-9]$/g;
			   
				var chk_date = rgEx_date.test(edu_date);   
			   
			  	if(!chk_date || edu_date.length < 10){
					isOk = false;
					$('#worker_edudate_' + id).css('border','1px solid red');
				}
			  	else {
					$('#worker_edudate_' + id).css('border','1px solid #cccccc');
				}
			}
		   
			var hire_date = $('#worker_hiredate_' + id).val();  
			
			if(hire_date.length > 0 ){
				//달력 정규표현식
				var rgEx_date2 = /^\d{4}-[01][0-9]-[0123][0-9]$/g;
				
				var chk_date2 = rgEx_date2.test(hire_date);   
				if(!chk_date2 || hire_date.length < 10){
					isOk = false;
					$('#worker_hiredate_' + id).css('border','1px solid red');
				}
				else {
					$('#worker_hiredate_' + id).css('border','1px solid #cccccc');
				}			   
			} 
		} 
	});
	return isOk;
}

function checkDuplicatePhone(phone, msg_div_id){
	 var result = -1;
	 
	 $.ajax({
		type : "GET",
		url : "json/checkWorkerPhone",
		data : {
			"phone" : phone
		},
		cache : false,
		async : false,
		success : function(json, status) {
			var json_data  = JSON.parse(json);
			var cur_site_id = '${userLoginInfo.site_id}';
				
			if(json_data.result == 'true'){
				for(var i = 0; i < json_data.item.length; i ++){
					var worker_site_id = json_data.item[i].site_id;
					var worker_site_name = json_data.item[i].site_name;
					var worker_name = json_data.item[i].name;
					
					if(worker_site_id == cur_site_id) {
						result = -99;
						break;
					}
					else {
						result = worker_site_id;
					}
				}
			}
			else {
				result = -1;
			}
		}
	 });	 
	 return result;
}

function changeImage(){
	 $('#localpath_div').show(); 
	 $('#upfile_div').show(); 
	 $('#image_div').hide(); 
}

function setFilePath(idx, val) {
	document.getElementById(idx).value = val;
}
	
function changeForeign(id){
	var gubun = $('#worker_gubun_' + id ).val();	
	if(gubun == 1){
		$('#worker_passno_' + id).css('display','inline-block');
		$('#worker_country_' + id ).css('display','inline-block');  
	}
	else{
		$('#worker_passno_' + id).css('display','none');
		$('#worker_country_' + id ).css('display','none');  
	}
}

</script>

<style>
#content-wrapper .content-item {
	margin: 10px 0;
}

.content_selete_box {
	display: flex;
	height: 100%;
}

.table-container {
	margin-bottom: 20px;
	overflow-x: scroll;
}

#content-wrapper #regWorkerTable {
	width: auto !important;
}

#content-wrapper .reg-table th {
	font-size: 0.9em;
	min-width: 150px;
	background: white;
	color: #666
}

#content-wrapper .reg-table td {
	text-align: center;
}

#content-wrapper .reg-table th.required {
	color: #FF3547;
	font-weight: 600;
}

#content-wrapper .reg-table td input {
	height: 40px;
	font-size: 0.9em;
}

#content-wrapper .reg-table td select {
	width: 100%;
	height: 40px;
	border-radius: 0.25rem;
	transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
	margin-bottom: 0 !important;
	border: 1px solid #ced4da;
	text-align: right;
	padding-right: 5px;
	color: #666;
}

#regWorkerMobile {
	width: 100% !important;
}

@media ( min-width : 992px) {
	.mobile-reg-box {
		display: none !important;
	}
	.web-reg-box {
		display: block !important;
	}
}

@media ( max-width : 991px) {
	.mobile-reg-box {
		display: block !important;
	}
	.web-reg-box {
		display: none !important;
	}
}

.fixed-table-body::-webkit-scrollbar {
	height: 10px;
	background: #C1C1C1;
}

.content_selete_box {
	height: 40px;
}
</style>

<form:form id="workerForm" action="insertWorker" class="form-horizontal"
	method="POST" enctype="multipart/form-data" modelAttribute="workerVO"
	autocomplete="off">

	<div id="content-wrapper">
		<div id="content_title" class="content-item">근로자 등록</div>
		<form:input path="site_id" style="display:none"></form:input>

		<div class="content_button_box content-item">
			<div class="btn btn-danger" onclick="history.back(-1)">
				<i class="fa-solid fa-arrow-rotate-left"></i> 뒤로
			</div>
			<div class="btn btn-default" onclick="submitWorker()">
				<i class="fa-regular fa-registered"></i> 등록
			</div>
		</div>

		<div class="content_selete_box content-item">
			<c:choose>
				<c:when test="${sessionScope.userLoginInfo.cont_type == 0}">
					<span class="select-title">업체:</span>
					<form:select path="cont_id" class="form-control select-content">
						<c:forEach var="cont" items="${contList}" varStatus="idx">
							<form:option value="${cont.id}">${cont.name}</form:option>
						</c:forEach>
					</form:select>
				</c:when>
				<c:otherwise>
					<span class="select-title">업체:</span>
					<form:select path="cont_id" class="form-control select-content">
						<form:option value="${sessionScope.userLoginInfo.cont_id}">${sessionScope.userLoginInfo.cont_name}</form:option>
					</form:select>
					<input id="cont_id" type="hidden" name="cont_id"
						value="${sessionScope.userLoginInfo.cont_id}" />
				</c:otherwise>
			</c:choose>
		</div>

		<div class="content_table_box content-item">

			<div class="mobile-reg-box table-container">
				<table id="regWorkerMobile"
					class="reg-table table table-bordered col-xs-12 table-hover">
					<tr>
						<th class="text-center required">성 명</th>
						<td><form:input id="worker_name_10"
								path="workerList[10].name" class="form-control"
								style="cursor:pointer; text-align:center;" placeholder="ex) 홍길동" />
						</td>
					</tr>
					<tr>
						<th class="text-center required">주민번호 앞6자리</th>
						<td><form:input id="worker_jumin_10"
								path="workerList[10].jumin" onkeydown='return onlyNumber(event)'
								onkeyup='removeChar(event)' maxlength="6" class="form-control"
								style="cursor:pointer; text-align:center;"
								placeholder="ex) 900101" /></td>
					</tr>
					<tr>
						<th class="text-center">성별</th>
						<td><form:select path="workerList[10].jumin_back">
								<form:option value="1">남</form:option>
								<form:option value="2">여</form:option>
							</form:select></td>
					</tr>
					<tr>
						<th class="text-center">채용일</th>
						<td><form:input id="worker_hiredate_10"
								path="workerList[10].hiredate" class="hiredate form-control"
								style="cursor:pointer;text-align:center;"
								placeholder="ex) 2022-10-01" /></td>
					</tr>
					<tr>
						<th class="text-center required">핸드폰번호</th>
						<td><form:input id="worker_phone_10"
								path="workerList[10].phone" onkeydown='return onlyNumber(event)'
								onkeyup='removeChar(event)' maxlength="12" class="form-control"
								style="cursor:pointer;text-align:center;"
								placeholder="ex) 01012345678" /></td>
					</tr>
					<tr>
						<th class="text-center">외국인여부</th>
						<td><form:select id="worker_gubun_10"
								path="workerList[10].gubun" onchange="changeForeign('10')">
								<form:option value="0">해당없음</form:option>
								<form:option value="1">해당</form:option>
							</form:select></td>
					</tr>
					<tr>
						<th class="text-center">여권번호(외국인)</th>
						<td><form:input id="worker_passno_10"
								path="workerList[10].passno" class="form-control"
								style="display:none; ecursor:pointer;text-align:center;"
								placeholder="ex) N0000000" /></td>
					</tr>
					<tr>
						<th class="text-center">국적(외국인)</th>
						<td><form:input id="worker_country_10"
								path="workerList[10].country" class="form-control"
								style="display:none; cursor:pointer; text-align:center;"
								placeholder="ex) 중국" /></td>
					</tr>
					<tr>
						<th class="text-center">신규교육일자</th>
						<td><form:input id="worker_edudate_10"
								path="workerList[10].edudate" class="edudate form-control"
								style="cursor:pointer; text-align:center;"
								placeholder="ex) 2022-10-01" /></td>
					</tr>
					<tr>
						<th class="text-center">밀폐교육(1차)</th>
						<td><form:input id="worker_sealed_date1_10"
								path="workerList[10].sealed_date1"
								class="sealed_date form-control"
								style="cursor:pointer; text-align:center;"
								placeholder="ex) 2022-10-01" /></td>
					</tr>
					<tr>
						<th class="text-center">밀폐교육(2차)</th>
						<td><form:input id="worker_sealed_date2_10"
								path="workerList[10].sealed_date2"
								class="sealed_date form-control"
								style="cursor:pointer; text-align:center;"
								placeholder="ex) 2022-10-01" /></td>
					</tr>
					<tr>
						<th class="text-center">밀폐교육(3차)</th>
						<td><form:input id="worker_sealed_date3_10"
								path="workerList[10].sealed_date3"
								class="sealed_date form-control"
								style="cursor:pointer; text-align:center;"
								placeholder="ex) 2022-10-01" /></td>
					</tr>
					<tr>
						<th class="text-center">밀폐교육(4차)</th>
						<td><form:input id="worker_sealed_date4_10"
								path="workerList[10].sealed_date4"
								class="sealed_date form-control"
								style="cursor:pointer; text-align:center;"
								placeholder="ex) 2022-10-01" /></td>
					</tr>
					<tr>
						<th class="text-center">직종</th>
						<td><form:select path="workerList[10].t_id">
								<c:forEach var="wtype" items="${wtypeList}" varStatus="idx">
									<c:if test="${idx.index == 0}">
										<form:option value="${wtype.id}" selected="selected">${wtype.t_name}</form:option>
									</c:if>
									<c:if test="${idx.index != 0}">
										<form:option value="${wtype.id}">${wtype.t_name}</form:option>
									</c:if>
								</c:forEach>
							</form:select></td>
					</tr>
					<tr>
						<th class="text-center">사 진</th>
						<td><form:input type="file" id="filename1"
								class="upfile-image form-control"
								path='workerList[10].eduimage_file'
								style="min-width: 250px; font-size: 0.8em;" /></td>
					</tr>
					<tr>
						<th class="text-center">혈액형(선택사항)</th>
						<td><form:select path="workerList[10].btype">
								<form:option value="">-미선택-</form:option>
								<form:option value="A">A</form:option>
								<form:option value="B">B</form:option>
								<form:option value="O">O</form:option>
								<form:option value="AB">AB</form:option>
							</form:select></td>
					</tr>
				</table>
			</div>

			<div class="web-reg-box table-container">
				<table id="regWorkerTable" data-toggle="table" data-search="false"
					data-pagination="false"
					class="reg-table table table-bordered col-xs-12 table-hover">
					<thead>
						<tr>
							<th class="text-center required">성 명</th>
							<th class="text-center required">주민번호 앞6자리</th>
							<th class="text-center">성별</th>
							<th class="text-center">채용일</th>
							<th class="text-center required">핸드폰번호</th>
							<th class="text-center">외국인여부</th>
							<th class="text-center">여권번호(외국인)</th>
							<th class="text-center">국적(외국인)</th>
							<th class="text-center">신규교육일자</th>
							<th class="text-center">밀폐교육(1차)</th>
							<th class="text-center">밀폐교육(2차)</th>
							<th class="text-center">밀폐교육(3차)</th>
							<th class="text-center">밀폐교육(4차)</th>
							<th class="text-center">직 종</th>
							<th class="text-center">근로자 사진</th>
							<th class="text-center">혈액형(선택사항)</th>
						</tr>
					</thead>
					<c:forEach begin="0" end="9" varStatus="loop">
						<tr>
							<td><form:input id="worker_name_${loop.index}"
									path="workerList[${loop.index}].name" class="form-control"
									style="cursor:pointer;text-align:center;" placeholder="ex) 홍길동" />
							</td>
							<td><form:input id="worker_jumin_${loop.index}"
									path="workerList[${loop.index}].jumin"
									onkeydown='return onlyNumber(event)'
									onkeyup='removeChar(event)' maxlength="6" class="form-control"
									style="cursor:pointer;text-align:center;"
									placeholder="ex) 900101" /></td>
							<td><form:select path="workerList[${loop.index}].jumin_back">
									<form:option value="1">남</form:option>
									<form:option value="2">여</form:option>
								</form:select></td>
							<td><form:input id="worker_hiredate_${loop.index}"
									path="workerList[${loop.index}].hiredate"
									class="hiredate form-control"
									style="cursor:pointer;text-align:center;"
									placeholder="ex) 2022-10-01" /></td>
							<td><form:input id="worker_phone_${loop.index}"
									path="workerList[${loop.index}].phone"
									onkeydown='return onlyNumber(event)'
									onkeyup='removeChar(event)' maxlength="12" class="form-control"
									style="cursor:pointer;text-align:center;"
									placeholder="ex) 01012345678" /></td>
							<td><form:select id="worker_gubun_${loop.index}"
									path="workerList[${loop.index}].gubun"
									onchange="changeForeign('${loop.index}')">
									<form:option value="0">해당없음</form:option>
									<form:option value="1">해당</form:option>
								</form:select></td>
							<td><form:input id="worker_passno_${loop.index}"
									path="workerList[${loop.index}].passno" class="form-control"
									style="display:none; ecursor:pointer;text-align:center;"
									placeholder="ex) N0000000" /></td>
							<td><form:input id="worker_country_${loop.index}"
									path="workerList[${loop.index}].country" class="form-control"
									style="display:none;cursor:pointer;text-align:center;"
									placeholder="ex) 중국" /></td>
							<td><form:input id="worker_edudate_${loop.index}"
									path="workerList[${loop.index}].edudate"
									class="edudate form-control"
									style="cursor:pointer;text-align:center;"
									placeholder="ex) 2022-10-01" /></td>
							<td><form:input id="worker_sealed_date1_${loop.index}"
									path="workerList[${loop.index}].sealed_date1"
									class="sealed_date form-control"
									style="cursor:pointer;text-align:center;"
									placeholder="ex) 2022-10-01" /></td>
							<td><form:input id="worker_sealed_date2_${loop.index}"
									path="workerList[${loop.index}].sealed_date2"
									class="sealed_date form-control"
									style="cursor:pointer;text-align:center;"
									placeholder="ex) 2022-10-01" /></td>
							<td><form:input id="worker_sealed_date3_${loop.index}"
									path="workerList[${loop.index}].sealed_date3"
									class="sealed_date form-control"
									style="cursor:pointer;text-align:center;"
									placeholder="ex) 2022-10-01" /></td>
							<td><form:input id="worker_sealed_date4_${loop.index}"
									path="workerList[${loop.index}].sealed_date4"
									class="sealed_date form-control"
									style="cursor:pointer;text-align:center;"
									placeholder="ex) 2022-10-01" /></td>
							<td><form:select path="workerList[${loop.index}].t_id">
									<c:forEach var="wtype" items="${wtypeList}" varStatus="idx">
										<c:if test="${idx.index == 0}">
											<form:option value="${wtype.id}" selected="selected">${wtype.t_name}</form:option>
										</c:if>
										<c:if test="${idx.index != 0}">
											<form:option value="${wtype.id}">${wtype.t_name}</form:option>
										</c:if>
									</c:forEach>
								</form:select></td>
							<td><form:input type="file" id="filename1"
									class="upfile-image form-control"
									path='workerList[${loop.index}].eduimage_file'
									style="min-width: 250px; font-size: 0.8em;" /></td>
							<td><form:select path="workerList[${loop.index}].btype">
									<form:option value="">-미선택-</form:option>
									<form:option value="A">A</form:option>
									<form:option value="B">B</form:option>
									<form:option value="O">O</form:option>
									<form:option value="AB">AB</form:option>
								</form:select></td>
						</tr>
					</c:forEach>
				</table>

			</div>
		</div>
	</div>

</form:form>

<%@ include file="IncludeBottom.jsp"%>
