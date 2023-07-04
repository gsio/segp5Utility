<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<link rel="stylesheet" href="css/equip/view.css">

<script>
$(document).ready(function() {

});

function updateEquip() {
	$('#updateForm').submit();
}

function popupImage(path){
	$('#file_image').attr('src', './image?virtname=' + path);
	$('#btn_image_modal').click();
}

function submitReturn() {
	$('#returnForm').submit();
}

</script>

<form:form id="equipViewForm" action="equipView" class="form-horizontal" method="POST" enctype="multipart/form-data" modelAttribute="equipVO" autocomplete="off">

<div id="content-wrapper">
	<div id="content_title" class="content-item">반입전 장비 상세보기</div>
	
	<form:input id="equip_id" type="hidden" path="id" value="${equipVO.id}"/>
		
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="submitReturn()"><i class="fa-solid fa-arrow-rotate-left"></i> 뒤로</div>	
		<div class="btn btn-default" onclick="updateEquip()"><i class="fa-regular fa-pen-to-square"></i> 수정</div>  
	</div>
	
	<div class="content_table_box content-item">	

		<div class="web-reg-box table-container">
			<table id="equipViewTable" 
				class="reg-table table table-bordered col-xs-12 table-hover">	
				<tr>
					<th class="text-center">업 체</th>		   				   	
					<td colspan="2" class="text-left">
						<div class="item">${equipVO.cont_name}</div>
					</td>
				</tr>
				<tr>
					<th class="text-center">작성자</th>
					<td colspan="2" class="text-left">
						<div class="item"> <span style="font-weight: 600;">${equipVO.writer_cont_name}</span> - ${equipVO.writer_name}</div>
					</td>
				</tr>
				<tr>
		   			<th class="text-center">장비사진</th>
		   			<td colspan="2" class="text-left" style="height: 200px;">
		   				<div class="item img-box" style="height: 100%;">
							<img class="size-fix img" src="image?virtname=${equipVO.equip_img}" />
						</div>
					</td>
		   		</tr>
				
				<tr>
		   			<th class="text-center">대분류</th>
					<td colspan="2" class="text-left">
						<div class="item">${equipVO.large_category_name}</div>
					</td>
		   		</tr>		   		
		   		
				<tr>
		   			<th class="text-center">소분류</th>
					<td colspan="2" class="text-left">
						<div class="item">${equipVO.small_category_name}</div>
					</td>
		   		</tr>
		   		
				<tr>
		   			<th class="text-center">규격</th>
					<td colspan="2" class="text-left">
						<div class="item">${equipVO.equip_standard}</div>
					</td>
		   		</tr>
		   				   		
				<tr>
		   			<th class="text-center">장비번호</th>
					<td colspan="2" class="text-left">
						<div class="item">${equipVO.equip_registration_no}</div>
					</td>
		   		</tr>
		   				   		
				<tr>
		   			<th class="text-center">임대업체</th>
					<td colspan="2" class="text-left">
						<div class="item">${equipVO.rent_cont_name}</div>
					</td>
		   		</tr>
		   		
		   		<tr>
		   			<th class="text-center">장비<br>검사일</th>
					<td colspan="2" class="text-left">
						<div class="item">${equipVO.check_start}<span>&nbsp~&nbsp</span>${equipVO.check_end}</div>
					</td>
		   		</tr>
		   		
		   		<tr>
		   			<th class="text-center">보험일자</th>
					<td colspan="2" class="text-left">
						<div class="item">${equipVO.insur_start}<span>&nbsp~&nbsp</span>${equipVO.insur_end}</div>
					</td>
		   		</tr>		   		
		   		
		   		<tr>
		   			<th class="text-center">운전원<br>정보</th>
					<td colspan="2" class="text-left">
						<div class="item"> <span style="font-weight: 600;">${equipVO.driver_cont_name}</span> - ${equipVO.driver_name} (${equipVO.driver_phone})</div>
					</td>
		   		</tr>
		   		
		   		<tr>
		   			<th class="text-center">체크<br>리스트</th>
					<td colspan="2" class="text-left">
						<c:forEach var="check" items="${equipVO.check_list}" varStatus="loop">
							<div class="item check-item" onclick="popupImage('${check.check_img}')"> <span style="font-weight: 600;">${check.idx}번 </span> - ${check.name}</div>
						</c:forEach>
					</td>
		   		</tr>
		   				   		
		   		<tr>
					<th class="text-center">첨부파일</th>
					<td colspan="2" class="text-left" style="height: 200px;">
						<div class="file-box">
							<c:forEach var="file" items="${equipVO.file_list}" varStatus="loop">
								<c:if test="${file != null}">
									<div class="file-item">
										<div class="file-icon">
											<c:choose>
												<c:when test="${file.orgname.contains('.pdf')}">
													<i class="fa-solid fa-file-pdf" style="color: #DB0001;"></i>
												</c:when>
												<c:when test="${file.orgname.contains('.docx') || file.orgname.contains('.hwp')}">
													<i class="fa-solid fa-file-word" style="color: #2A5492;"></i>
												</c:when>
												<c:when test="${file.orgname.contains('.pptx')}">
													<i class="fa-solid fa-file-powerpoint" style="color: #D55825;"></i>
												</c:when>
												<c:when test="${file.orgname.contains('.xlsx')}">
													<i class="fa-solid fa-file-excel" style="color: #037543;"></i>
												</c:when>
												<c:when test="${file.orgname.contains('.png') || file.orgname.contains('.jpeg') || file.orgname.contains('.jpg')}">
													<i class="fa-regular fa-file-image" style="color: #333333;"></i>
												</c:when>
												<c:otherwise>
													<i class="fa-solid fa-file"></i>
												</c:otherwise>
											</c:choose>
										</div>
										<div class="file-name">
											<a class="file_link" style="color: #000;" href="./file?virtname=${file.virtname}&orgname=${file.orgname}&content_type=${file.content_type}">${file.orgname}</a>
										</div>										
										<c:if test="${file.content_type.equals('image/png') || file.content_type.equals('image/jpeg')}">
											<div class="file-preview">
												<img src="image?virtname=${file.virtname}" style="height: 50px; width: 50px; cursor: pointer;" onclick="popupImage('${file.virtname}')">	
											</div>											
										</c:if>
										<div class="file-volume">
											<c:choose>
												<c:when test="${file.file_size < 1024}">
													<fmt:formatNumber value="${file.file_size}" pattern=".0"/>B
												</c:when>
												<c:when test="${file.file_size / 1024 < 1024}">
													<fmt:formatNumber value="${file.file_size / 1024}" pattern=".0"/>KB
												</c:when>
												<c:when test="${file.file_size / 1024 / 1024 < 1024}">
													<fmt:formatNumber value="${file.file_size / 1024 / 1024}" pattern=".0"/>MB
												</c:when>
												<c:otherwise>
													<fmt:formatNumber value="${file.file_size / 1024 / 1024 / 1024}" pattern=".0"/>MB
												</c:otherwise>
											</c:choose>
										</div>
										
										<c:if test="${not loop.last}">
											<br>
										</c:if>
									</div>									
								</c:if>
							</c:forEach>
						</div>
					</td>
				</tr>
				
				<tr>
		   			<th class="text-center">비고</th>
		   			<td colspan="2" class="text-left" style="height: 300px;">
						<div class="item" style="height: 100%;">${equipVO.remark}</div>
					</td>
				</tr>	
				
			</table>	
		</div>
	</div>
</div>

</form:form>

<div id="form_group">
	<form id="returnForm" action="returnEquip" method="POST"></form>
	<form id="updateForm" action="registerEquip" method="POST">
		<input id="equip_id" type="hidden" name="id" value="${equipVO.id}" />
	</form>
	<form id="commentInsertForm" action="insertComment" method="POST">
		<input id="reply_equip_id" type="hidden" name="equip_id" />
		<input id="reply_parent_id" type="hidden" name="parent_id" />
		<input id="reply_cotent_id" type="hidden" name="comment" />
	</form>
</div>

<div style="display:none" id="btn_image_modal" data-toggle="modal" data-target="#image_modal"></div>
<div class="modal fade" id="image_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
	  	<div class="modal-content">
		   	<div class="modal-header" style="font-size: 1.5em;">
		   		 이미지 미리보기
		   	</div>
			<div class="modal-body">
				<img id="file_image" src="" class="enlargeImageModalSource" style="width: 100%; max-width:750px;" onerror="this.src='images/noimage.png'">
			</div>
		</div>
	</div>
</div><!-- btn_image_modal END -->

<%@ include file="IncludeBottom.jsp"%>
