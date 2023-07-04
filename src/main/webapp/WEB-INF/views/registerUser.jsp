<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script type="text/javascript" src="js/user/register.js"></script>
<link rel="stylesheet" href="css/user/register.css">

<script>

var idNotDuplicate = false;
var isUpdate = ${updateMode};
var interval;

$(document).ready(function() {
	
	$('#sign_img').attr("readonly", true);
	$('#photo_img').attr("readonly", true);
	
	var updatemode = '${updateMode}';
	
	if(updatemode == 'true') {
		
		$("#content_title").text("관리자 수정");
		
		idNotDuplicate = true;
		
		$('#btnNewPasswd').show();
		$('#input_password').hide();
		$('#input_password').val('!@#$%^&&!@#$');
		$('#userForm').attr('action', 'updateUser');
		
		if($('#isManager').val() == 1){
			$('#isManager_check').attr('checked', 'checked');
		}
		else{
			$('#isManager_check').attr('checked', false);
		}		 
	 }
	
	else {
		$("#content_title").text("관리자 등록");
		idNotDuplicate = false;
		$('#btnNewPasswd').hide();
		$('#input_password').show();		
		$('#userForm').attr('action', 'insertUser');
	}
	 
	var cont = $('#cont');
	changeRoleList(cont);	 
	var role_code = '${userVO.role_code}';	
	
	$("#role").find("option").each(function() {
		if(this.value == role_code) {
			$(this).attr("selected", "selected");
		}
	});	 	
	
	initImage();
	
});

function changeRoleList(sel) {

	var cont_type = $(sel).children(":selected").attr("id");
	
	$("#role").empty();
	
	if(cont_type == 0) {
		<c:forEach var="role" items="${order_roleList}" varStatus="idx">
			$("#role").append("<option value='${role.code}' class='test_${role.type}'>${role.name}</option>");
		</c:forEach>		
	}
	else if(cont_type == 1){	
		<c:forEach var="role" items="${cowork_roleList}" varStatus="idx">
			$("#role").append("<option value='${role.code}' class='test_${role.type}'>${role.name}${role.comment}</option>");
		</c:forEach>	
	}
	else if(cont_type == 2){	
		<c:forEach var="role" items="${extra_roleList}" varStatus="idx">
			$("#role").append("<option value='${role.code}' class='test_${role.type}'>${role.name}</option>");
		</c:forEach>
	}
	else if(cont_type == 3){	
		<c:forEach var="role" items="${bon_roleList}" varStatus="idx">
			$("#role").append("<option value='${role.code}' class='test_${role.type}'>${role.name}</option>");
		</c:forEach>
	}
	else if(cont_type == 4){	
		<c:forEach var="role" items="${val_roleList}" varStatus="idx">
			$("#role").append("<option value='${role.code}' class='test_${role.type}'>${role.name}</option>");	
		</c:forEach>
	}	      
}

function initImage(){
	
	var image0 = '${userVO.sign}';	
	
	if(typeof image0!= "undefined" && image0.length >= 1){
		$('#localpath_div_0').hide(); 
		$('#upfile_div_0').hide();
	}
	else {
		$('#image_div_0').hide();
		$('#file_btn_0').hide();
	}
	
	var image1 = '${userVO.photo}';
	if(typeof image1!= "undefined" && image1.length >= 1){
		$('#localpath_div_1').hide(); 
		$('#upfile_div_1').hide();
	}
	else{
		$('#image_div_1').hide();
		$('#file_btn_1').hide();
	}
}

</script>

<style>


</style>

<form:form id="userForm" class="form-horizontal" method="POST"  enctype="multipart/form-data" modelAttribute="userVO" autocomplete="off">

<div id="content-wrapper">
	<div id="content_title" class="content-item"></div>
	<form:input type="hidden" path="site_id" value="${userVO.site_id}"/>
	<form:input type="hidden" path="id" value="${userVO.id}"/>
		
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="history.back(-1)"><i class="fa-solid fa-arrow-rotate-left"></i> 뒤로</div>	   
		<c:choose>
		<c:when test="${!updateMode}"> 		      
			<div class="btn btn-default" onclick="submitUser()"><i class="fa-regular fa-registered"></i> 등록</div>
		</c:when>
		<c:otherwise>
			<div class="btn btn-danger" onclick="deleteUser()"><i class="fa-solid fa-trash"></i> 삭제</div>		
			<div class="btn btn-default" onclick="submitUser()"><i class="fa-regular fa-pen-to-square"></i> 수정</div>
		</c:otherwise>	
		</c:choose>
	</div>
		
	<div class="content_table_box content-item">	
	
		<div class="table-container" >
	   		
	   		<table id="regUserTable"	 			
				class="reg-table table table-bordered col-xs-12 table-hover">
			
				<tr>
					<th class="text-center">소속현장</th>
					<td>	
						<div class="item">
							${userVO.site_name}
						</div>						
					</td>
				</tr>
				<tr>
					<th class="text-center required">성 명</th>
					<td>
						<form:input class="form-control" path="name" maxlength="20"	placeholder="ex) 박민호"/>
						<form:errors cssClass="formError" path="name" />
					</td>
				</tr>
				<tr>
					<th class="text-center">직 책</th>
					<td> 
						<form:input class="form-control" path="grade" maxlength="20" placeholder="ex) 과장"/>
						<form:errors cssClass="formError" path="grade" />
					</td>
	   			</tr>
	   			
	   			<c:if test="${sessionScope.userLoginInfo.isManager == 1}">	
	   			<tr>
					<th class="text-center">Web 관리자 여부</th>			
					<td>
						<div class="content_summary_box content-item">
							<i class="fa-solid fa-star" style="color:#ff3547;"></i> 해당 관리자는 아래의 권한을 추가로 가집니다. <br>
							&nbsp&nbsp<i class="fa-solid fa-arrow-right"></i> 1. 관리자 등록/변경 시 해당 권한 부여 / 삭제 가능 <br>
							&nbsp&nbsp<i class="fa-solid fa-arrow-right"></i> 2. 로그데이터 확인 <br>
							&nbsp&nbsp<i class="fa-solid fa-arrow-right"></i> 3. 관리 시스템의 접근 권한
						</div>
						<div>
							<input type="checkbox" class="form-control" id="isManager_check" style="height: 20px;"/>
							<form:hidden path="isManager" id="isManager"/>
						</div>
					</td>
	   			</tr>
	   			</c:if>
	   		
				<tr>
					<th class="text-center">핸드폰번호</th>
					<td>
						<form:input class="form-control"  path="phone" maxlength="15" placeholder="ex) 010-0000-0000" />
						<form:errors cssClass="formError" path="phone" />						
					</td>
				</tr>
				<tr>
					<th class="text-center required">업체</th>
					<td> 
						<form:select id="cont" path="cont_id" class="form-control" onchange="changeRoleList(this)">
							<form:option value="-1">--업체선택--</form:option>
							<c:forEach var="cont" items="${contList}" varStatus="idx">
							<form:option value="${cont.id}" id="${cont.type}">${cont.name}</form:option>
							</c:forEach>
						</form:select>
						<form:errors cssClass="formError" path="cont_id" />
					</td>
				</tr>
				<tr>
					<th class="text-center required">권 한</th>
					<td>
						<form:select id="role" path="role_code" class="form-control" >
							<c:forEach var="role" items="${roleList}" varStatus="idx">
							<form:option value="${role.code}" class="test_${role.type}">${role.name}</form:option>
							</c:forEach>							
						</form:select>
						<form:errors cssClass="formError" path="role_code" />
					</td> 
				</tr>
				<tr>
					<th class="text-center required">아 이 디</th>
					<td>
						<c:if test="${!updateMode}">
							<form:input id="input_id" class="form-control" path="userid" maxlength="20"	placeholder="ex) id" />
							<form:errors cssClass="formError" path="userid" />
									
							<div class="img_btn_wrapper">								
								<div id="duplCheck" class="btn btn-default img_btn_box" onclick="duplicateIdCheck()"><i class="fa-regular fa-circle-question"></i> 중복 체크</div>
								<div id="duplCheckOk" class="btn btn-primary img_btn_box" style="display: none;"><i class="fa-regular fa-circle-check"></i> 사용 가능</div>
							</div>
							
						</c:if>
						
						<c:if test="${updateMode}">							
							<form:hidden path="userid" value="${userVO.userid}"/>
							<div id="new_user_id" class="item">
								${userVO.userid}
							</div>							
							<c:if test="${userVO.id.equals(userLoginInfo.id)}">
							<div class="img_btn_wrapper">	
								<div id="modifyId" class="btn btn-danger img_btn_box" onclick="modifyUserId()"><i class="fa-regular fa-circle-question"></i> 변경</div>							
							</div>
							</c:if>
							
							<div id="certkey_box">
								<div class="info-title-box">
									<div class="title">인증확인</div>
									<div id="confirmCertKeyId" class="btn btn-default" onclick="confirmCertKeyId()">확인</div>
									<div id="resendMsgCertKey" class="btn btn-danger" onclick="resendMsgCertKey()">재전송</div>
								</div>
								<div class="default-item-box item-box">
									<div class="title">인증번호</div>
									<div class="content">
										<input id="cerkeyInputId" name="content" class="form-control" type="text" value="" maxlength="6">
										<span id="countDown">03:00</span>
									</div>									
								</div>								
							</div>
							
						</c:if>	
					</td>	
				</tr>
				<tr>
					<th class="text-center required">비 밀 번 호</th>
					<td>
						<c:choose>
		                   	<c:when test="${userLoginInfo.cont_type == 0}">
		               			<form:input type="input" class="form-control" path="password" maxlength="20" placeholder="ex) password" />
								<form:errors cssClass="formError" path="password" />	
		                   	</c:when>	                   	
		                   	<c:otherwise>
								<c:choose>
							 		<c:when test="${updateMode == true}">
										* 현재 원청사에서만 변경할수 있습니다.
										<form:input type="hidden" class="form-control" path="password" maxlength="20" placeholder="ex) password" />
				                   	</c:when>
				                   	<c:otherwise>
				                   		<form:input type="input" class="form-control" path="password" maxlength="20" placeholder="ex) password" />
										<form:errors cssClass="formError" path="password" />
				                   	</c:otherwise>	
								</c:choose>
		                   	</c:otherwise>	                  
                   		</c:choose>
					</td>
				</tr>
				<tr>
					<th class="text-center">사 인</th>
					<td>
						<!-- File PreView -->
						<div id="image_div_0">
							<img class="size-fix img" src="image?virtname=${userVO.sign}" />
						</div>
                        <!-- path -->
                        <div id="localpath_div_0" class="padding-height">
                            <form:input type="text" id="sign_img" readonly="readonly" class="form-control" placeholder="ex) 용량 ≤ 1MB" path='sign' accept="image/*" />
                        </div>
                        <div class="img_btn_wrapper">
                        	<div id="file_btn_0" class="btn btn-primary margin-top img_btn_box" onclick="changeImage('0');">
								<i class="fa-regular fa-pen-to-square"></i> 수정
							</div>
	                        <!-- file upload -->
	                        <div id="upfile_div_0" class="file_input_div margin-top img_btn_box">
	                            <div class="btn btn-default img_btn_box" ><i class="fa-regular fa-image"></i> 사진
	                            	<form:input class="upfile padding-width-none file-input-hidden" path='sign_file' 
	                         	 	type="file" size="100" title="첨부할 파일을 찾습니다." style="cursor:pointer" onchange="setFilePath('sign_img', this.value)"/>
	                            </div>	                            
	                        </div>	                        
                        </div>
					</td>
				</tr>
 			
				<tr>
					<th class="text-center">사 진</th>	
					<td>
						<!-- File PreView -->
						<div id="image_div_1">
							<img class="size-fix img" src="image?virtname=${userVO.photo}" />
						</div>
                        <!-- path -->
                        <div id="localpath_div_1" class="padding-height">
                            <form:input type="text" id="photo_img" readonly="readonly" class="form-control" placeholder="ex) 용량 ≤ 1MB" path='photo' accept="image/*" />
                        </div>
                        <div class="img_btn_wrapper">
                        	<div id="file_btn_1" class="btn btn-primary margin-top img_btn_box" onclick="changeImage('1');">
								<i class="fa-regular fa-pen-to-square"></i> 수정
							</div>
	                        <!-- file upload -->
	                        <div id="upfile_div_1" class="file_input_div margin-top img_btn_box">
	                            <div class="btn btn-default img_btn_box" ><i class="fa-regular fa-image"></i> 사진
	                            	<form:input class="upfile padding-width-none file-input-hidden" path='photo_file' 
	                         	 	type="file" size="100" title="첨부할 파일을 찾습니다." style="cursor:pointer" onchange="setFilePath('photo_img', this.value)"/>
	                            </div>	                            
	                        </div>	                        
                        </div>
					</td>
				</tr>
				<tr>
					<th class="text-center">이 메 일</th>
					<td>
		   				<form:input class="form-control" path="email" maxlength="45" placeholder="ex) dev@gsil.kr" />
						<form:errors cssClass="formError" path="email" />
					</td>
				</tr>
				<tr>
					<th class="text-center">혈 액 형</th>
					<td>
						<form:input class="form-control" path="btype" maxlength="2"	placeholder="ex) O" />
						<form:errors cssClass="formError" path="btype" />
					</td>
				</tr>
				<tr>
					<th class="text-center">신규교육일</th>
					<td>
						<form:input class="form-control" path="edudate" maxlength="10" placeholder="ex) 2022-10-01" />
		   			</td>
				</tr>
			    <tr>
					<th class="text-center">밀폐교육(1차)</th>
					<td> 
						 <form:input class="form-control" path="sealed_date1" maxlength="10" placeholder="ex) 2022-10-01"/>				
					</td>
				</tr>
				<tr>
					<th class="text-center">밀폐교육(2차)</th>
					<td>
						 <form:input class="form-control" path="sealed_date2" maxlength="10" placeholder="ex) 2022-10-01"/>				
					</td>
				</tr>
				<tr>
					<th class="text-center">밀폐교육(3차)</th>
					<td>
						 <form:input class="form-control" path="sealed_date3" maxlength="10" placeholder="ex) 2022-10-01"/>				
					</td>
				</tr>
				<tr>
					<th class="text-center">밀폐교육(4차)</th>
					<td>
						 <form:input class="form-control" path="sealed_date4" maxlength="10" placeholder="ex) 2022-10-01"/>				
					</td>
				</tr>
			</table>
			
		</div>
		
	</div>	
</div>

</form:form>

<%@ include file="IncludeBottom_renewal.jsp"%>
