<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<link rel="stylesheet" href="css/notice/register.css">

<script>

var isUpdate = ${update};

$(document).ready(function() {
	
	if(isUpdate == true) {
		$("#content_title").html("공지사항 수정");
		$('#noticeRegisterForm').attr('action', 'updateNoticeData');
	}
	else {
		$("#content_title").html("공지사항 등록");
		$('#noticeRegisterForm').attr('action', 'insertNoticeData');
		addFileInputBox();
	} 
	
});

function submitNotice() {
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
			$('#noticeRegisterForm').submit();	  
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
	
	var title = $('#notice_title').val();
	var content = $('#notice_content').val();
	
	if(title.length < 1) {
		$('#notice_title').css('border','2px solid red');
		isOk = false;
	} 
	else {
		$('#notice_title').css('border','1px solid #ced4da');
	}
	
	if(content.length < 1) {
		$('#notice_content').css('border','2px solid red');
		isOk = false;
	} 
	else {
		$('#notice_content').css('border','1px solid #ced4da');
	}
	
	return isOk;
}

function deleteNoticeData() {	
	input = confirm('공지사항을 삭제하시겠습니까?');
	if(input){
		$('#noticeRegisterForm').attr('action', 'deleteNoticeData');
		$('#noticeRegisterForm').submit();
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

</script>

<style>

  

</style>

<form:form id="noticeRegisterForm" class="form-horizontal" method="POST" modelAttribute="noticeVO" enctype="multipart/form-data" autocomplete="off">

<div id="content-wrapper">
	<div id="content_title" class="content-item"></div>
	
	<form:input type="hidden" path="id" value="${noticeVO.id}"/>			
	<form:input type="hidden" path="site_id" value="${userLoginInfo.site_id}"/>
		
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="history.back(-1)"><i class="fa-solid fa-arrow-rotate-left"></i> 취소</div>	
		<c:if test="${!update}">
			<div class="btn btn-default" onclick="submitNotice()"><i class="fa-regular fa-registered"></i> 등록</div>
		</c:if>   
		<c:if test="${update}">
			<div class="btn btn-danger" onclick="deleteNoticeData()"><i class="fa-solid fa-trash"></i> 삭제</div>
			<div class="btn btn-default" onclick="submitNotice()"><i class="fa-regular fa-pen-to-square"></i> 수정</div>
		</c:if>
	</div>
	
	<div class="content_table_box content-item">	

		<div class="web-reg-box table-container">
			<table id="registerNoticeTable" 
				class="reg-table table table-bordered col-xs-12 table-hover">	
				<tr>
					<th class="text-center required">제 목</th>		   				   	
					<td colspan="2" class="text-left">
						<form:input id="notice_title" class="form-control" path="title" maxlength="100" placeholder="제목" />
					</td>
				</tr>
				<tr>
					<th class="text-center required">작성자</th>
					<td colspan="2" class="text-left">
						<div class="item" style="background: #F2F4F4 !important;"> <span style="font-weight: 600;">${userLoginInfo.cont_name}</span> - ${userLoginInfo.name}</div>
					</td>
				</tr>
		   		<tr>
					<th class="text-center">첨부파일</th>
					<td class="text-left" style="height: 200px; width: 100%;">
						<div id="fileBox" class="file-box">
							<c:if test="${update}">	
								<c:if test="${noticeVO.file_list.size() > 0}">	
								<c:forEach begin ="0" end="${noticeVO.file_list.size() - 1}" varStatus="loop">
									<form:input type="hidden" id="reg_idx_${loop.index}" class="form-control" path='file_list[${loop.index}].ismodify' value="0" />
									<c:if test="${noticeVO.file_list[loop.index].orgname == null}">                           		
										<form:input type="file" id="reg_file_${loop.index}" class="form-control" path='file_list[${loop.index}].file' />
			    					</c:if>
			    					<c:if test="${noticeVO.file_list[loop.index].orgname != null}">
										<form:input type="hidden" class="form-control" path='file_list[${loop.index}].virtname' /><!-- update시 참조 -->                           		
										<div id="reg_file_${loop.index}" class="file-item">
											<div class="file-no">${loop.index + 1}</div>
											<div class="file-name">${noticeVO.file_list[loop.index].orgname}</div>
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
					<td id="file-btn-box">
						<div class="item-btn btn icon-default" onclick="addFileInputBox()"><i class="fa-solid fa-circle-plus"></i></div>					
						<div class="item-btn btn icon-danger" onclick="deleteFileInputBox()"><i class="fa-solid fa-circle-minus"></i></div>
					</td>
				</tr>
				<tr>
		   			<th class="text-center required">내용</th>
		   			<td colspan="2" class="text-left" style="height: 600px;">
						<div class="item" style="height: 100%;">
							<form:textarea id="notice_content" style="height: 100%;" class="form-control" path="contents" rows="15" />
						</div>
					</td>
				</tr>
			</table>	
		</div>
	</div>
</div>

</form:form>

<div id="form_group">
	<form id="updateForm" action="registerNotice" method="POST">
		<input id="notice_id" type="hidden" name="id" value="${noticeVO.id}" />
	</form>
	<form id="commentInsertForm" action="insertComment" method="POST">
		<input id="reply_notice_id" type="hidden" name="notice_id" />
		<input id="reply_parent_id" type="hidden" name="parent_id" />
		<input id="reply_cotent_id" type="hidden" name="comment" />
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

<%@ include file="IncludeBottom.jsp"%>
