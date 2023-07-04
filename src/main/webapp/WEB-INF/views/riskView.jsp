<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>

<link rel="stylesheet" href="css/risk/view.css">

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>


$(document).ready(function() {
	
	$(document).on("click", ".work-activity-box > .title_box", function() {	
		$(this).parent().children().last().toggleClass('on');
		$(this).children().toggleClass('active');
    });
	
	$(document).on("click", ".risk-step-item > .item-title-box", function() {
		$(this).parent().children().last().toggleClass('on');
		$(this).children().children().toggleClass('active');
    });
	
	$(document).on("click", ".risk-item > .sub-title-box", function() {
		$(this).parent().children().last().toggleClass('on');
		$(this).children().toggleClass('active');
    });
	
	$(document).on("click", ".content-management_method-box", function() {
		$(this).children().last().toggleClass('on');
		$(this).children().children().toggleClass('active');
    });
	
});

function updateRisk() {
	$('#updateForm').submit();
}

function popupImage(path){
	$('#file_image').attr('src', './image?virtname=' + path);
	$('#btn_image_modal').click();
}

function submitReturn() {
	$('#returnForm').submit();
}

function checkApproval() {
	var checker_cnt = $("#check-item-box > div").length;
	
	if(checker_cnt <= 0) {
		$('#approval-item-box').css('border','2px solid red');
		alert("점검대상자가 적어도 1명 이상 필요합니다.");
	}
	else {
		$('#approval-item-box').css('border','1px solid #ced4da');	   
		$('#requestRiskForm').submit();
	}
}

function cancelRisk() {
	var content;
	content = prompt('반려사유를 적어주세요');   
	
	if(content.length > 0) {
		$("#cancelReason").val(content);
		$('#cancelRiskForm').submit();
	}
	else {
		alert('취소하었습니다');
	}
	
}

function approvalRisk() {
	$('#approvalRiskForm').submit();
}

function printRiskData() {
	alert("준비중입니다.");
}

</script>

<style>


</style>

<form:form id="riskViewForm" action="riskView" class="form-horizontal" method="POST" enctype="multipart/form-data" modelAttribute="riskVO" autocomplete="off">

<div id="content-wrapper">
	<div id="content_title" class="content-item">위험성평가 상세보기</div>
	
	<form:input id="risk_id" type="hidden" path="id" value="${riskVO.id}"/>
		
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="submitReturn()"><i class="fa-solid fa-arrow-rotate-left"></i> 뒤로</div>	
				
		<c:choose>		
			<c:when test="${riskVO.approval_state == 1}">
				<c:if test="${sessionScope.userLoginInfo.id == riskVO.writer_id}">
					<div class="btn btn-default" onclick="updateRisk()"><i class="fa-regular fa-pen-to-square"></i> 수정</div>  
				</c:if>
				<c:if test="${sessionScope.userLoginInfo.id == riskVO.approver_id}">		
					<div class="btn btn-danger" onclick="checkApproval()" style="width: 120px !important;"><i class="fa-solid fa-clipboard-check"></i> 결제요청</div>
				</c:if>  
			</c:when>	
			<c:when test="${riskVO.approval_state == 4}">		
				<div class="btn btn-default" onclick="printRiskData()"><i class="fa-solid fa-print"></i> 출력</div>	 
			</c:when>	
			<c:when test="${riskVO.approval_state == 5}">		
				<div class="btn btn-danger" onclick="updateRisk()" style="width: 100px !important;"><i class="fa-regular fa-pen-to-square"></i> 재기안</div>	 
			</c:when>	
			<c:otherwise>	
				<c:if test="${sessionScope.userLoginInfo.id == riskVO.approver_id}">		
					<div class="btn btn-danger" onclick="cancelRisk()"><i class="fa-solid fa-circle-xmark"></i> 반려</div>
					<div class="btn btn-default" onclick="approvalRisk()"><i class="fa-regular fa-circle-check"></i> 승인</div>
				</c:if>
			</c:otherwise>
		</c:choose>
		
	</div>
	
	<div class="content_report_box content-item">
	
		<div class="info-title">기본정보</div>
	
		<div class="default-item-box item-box">
			<div class="title">업 체</div><span class="content">${riskVO.cont_name}</span>
		</div>
		
		<div class="default-item-box item-box">
			<div class="title">작성자</div><div class="content"><span style="font-weight: 600;">${riskVO.writer_cont_name}</span> - ${riskVO.writer_name}</div>
		</div>
		
		<div class="default-item-box item-box">
			<div class="title">평가내용</div><div class="content"><span style="font-weight: 600;">${riskVO.content}</span></div>
		</div>
		
		<div class="default-item-box item-box">
			<div class="title">평가기간</div><div class="content">${riskVO.risk_start}<span style="font-weight: 600;"> ~ </span>${riskVO.risk_end}</div>
		</div>
		
		<div class="unique-wrap item-box">
			<div class="info-title">결재</div>
			<div id="approval-item-box" class="unique-item-box">
				<c:forEach var="approval" items="${riskVO.approvalList}" varStatus="loop">
					<c:if test="${approval != null}">						 				
						<div class="approval-item">
							<div class="role-name">${approval.role_name}</div>
							<div class="name">${approval.name}</div>
							<c:choose>
								<c:when test="${approval.confirm == 'Y'}">	
									<div class="approval-state" style="color: #2710d5;">승인</div>
									<div class="approval-time">${approval.last_update_time.replace(".0", "")}</div>
								</c:when>
								<c:when test="${approval.confirm == 'R'}">
									<div class="approval-state" style="color: #dc3545;">반려</div>
									<div class="approval-time">${approval.last_update_time.replace(".0", "")}</div>									
								</c:when>
								<c:otherwise>
									<div class="approval-state">-</div>
									<div class="approval-time">-</div>
								</c:otherwise>
							</c:choose>
						</div>						
				 	</c:if>
				 </c:forEach>
			</div>
		</div>
		
		<div class="unique-wrap item-box">
			<div class="info-title">점검대상자</div>
			<div id="check-item-box" class="unique-item-box">
				<c:forEach var="checker" items="${riskVO.checkList}" varStatus="loop">
					<c:if test="${checker != null}">						 				
						<div class="check-item">
							<div class="cont-name">${checker.cont_name}</div>
							<div class="role-name">${checker.role_name}</div>
							<div class="name">${checker.name}</div>
							<div class="phone">${checker.phone}</div>
						</div>						
				 	</c:if>
				</c:forEach>	
			</div>
		</div>
		
		<div class="unique-wrap item-box">
			<div class="info-title">위험성평가표</div>
			<div id="risk-factor-item-box" class="unique-item-box">
				<c:forEach var="wa" items="${riskVO.riskFactorList}" varStatus="loop1">
					<div class="risk-factor-item">
						<div class="work-activity-box">
							<div class="title_box">							
								<div class="title">${loop1.index + 1}번 작업활동<%-- <span style="font-weight: 600;">&nbsp;-&nbsp;${fn:length(riskVO.riskFactorList[loop1.index].wsList)}개</span> --%></div>							
								<div class="btn-work-state btn"></div>
							</div>
							<div class="content">
								<div class="content-item-box">									
									<div class="item-title required">세부 공종</div>
									<div class="item-content">${wa.class_name}</div>
								</div>
								<div class="content-add-item-box">
									<c:forEach var="ws" items="${riskVO.riskFactorList[loop1.index].wsList}" varStatus="loop2">
									<div class="risk-step-item">
										<div class="item-title-box">
										<div class="item-title">${loop2.index + 1}번 작업 단계<%-- <span>&nbsp;-&nbsp;${fn:length(riskVO.riskFactorList[loop1.index].wsList[loop2.index].rfList)}개</span> --%></div>									
										<div class="item-btn-box"><div class="btn-add-work-step btn"></div></div>
									</div>
										<div class="item-content-wrap">
											<div class="item-content-box">
												<div class="item-content">
													<div class="title required">작업 내용</div>
													<div class="content">${ws.work_content}</div>
												</div>
											</div>
											<div class="item-content-box">
												<div class="item-content">
													<div class="title">사용 장비</div>
													<div class="content">${ws.equip_name}</div>
												</div>
											</div>
											<div class="item-content-box">
												<div class="item-content">
													<div class="title">장비 수</div>
													<div class="content">${ws.equip_cnt}개</div>
												</div>
											</div>
											<div class="item-content-box">
												<div class="item-content">
													<div class="title required">작업 기간</div>
													<div class="content">${ws.work_start}&nbsp;~&nbsp;${ws.work_end}</div>
												</div>
											</div>
											<div class="item-content-box">
												<div class="item-content">
													<div class="title required">작업 위치</div>
													<div class="content">${ws.section_name}</div>
												</div>
											</div>
											<div class="item-content-box">
												<div class="item-content">
													<div class="title">인원 수</div>
													<div class="content">${ws.engineer_cnt}명</div>
												</div>
											</div>
											
											<div class="content-sub-add-item-box">
												<c:forEach var="rf" items="${riskVO.riskFactorList[loop1.index].wsList[loop2.index].rfList}" varStatus="loop3">
												
												<div class="risk-item">
													<div class="sub-title-box">
														<div class="title">${loop3.index + 1}번 위험성평가<%-- <span>&nbsp;-&nbsp;${fn:length(riskVO.riskFactorList[loop1.index].wsList[loop2.index].rfList[loop3.index].mpList)}개</span> --%></div>		
														<div class="btn-box btn">
															<div class="btn-add-risk-content"></div>
														</div>	
													</div>
													<div class="sub-content-box">
														<div class="item-content">														
															<div class="title required">위험요인</div>
															<div class="content">${rf.risk_content}</div>
														</div>
														
														<div class="item-content">
															<div class="title">피해형태</div>
															<div class="content">${rf.damage_form}</div>
														</div>
														
														<div class="item-content">
															<div class="title">빈도</div>
															<div class="content">${rf.frequency}</div>
														</div>
														
														<div class="item-content">
															<div class="title">강도</div>
															<div class="content">${rf.strength}</div>
														</div>
														
														<div class="item-content">
															<div class="title">등급</div>
															<div class="content">${rf.rating}</div>
														</div>
														
														<div class="content-management_method-box">
															<div class="management_method-title-box">
																<div class="title required">관리방안</div>		
																<div class="btn-box">
																	<div class="btn-add-manage-content btn"></div>
																</div>															
															</div>
															
															<div class="management_method-box">
																<c:forEach var="mp" items="${riskVO.riskFactorList[loop1.index].wsList[loop2.index].rfList[loop3.index].mpList}" varStatus="loop4">
																	<div class="management_method_item">
																		<div class="item-content">
																			<div class="content">${mp.content}</div>
																		</div>
																	</div>	
																</c:forEach>
															</div>
														</div>
													</div>
												</div>												
												</c:forEach>											
											</div>												
										</div>
									</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>	
				</c:forEach>
			</div>
		</div>
		
		<div class="unique-wrap item-box">
			<div class="info-title">추가 메모사항</div>
			<div id="remark-item-box" class="unique-item-box">
				<div class="remark" style="height: 100%;">${riskVO.remark}</div>
			</div>
		</div>
		
	</div>
</div>

</form:form>

<div id="form_group">
	<form id="returnForm" action="returnRisk" method="POST"></form>
	<form id="updateForm" action="registerRisk" method="POST">
		<input id="risk_id" type="hidden" name="id" value="${riskVO.id}" />
	</form>
	<form id="requestRiskForm" action="requestRisk" method="POST">
		<input id="risk_id" type="hidden" name="id" value="${riskVO.id}" />
	</form>
	<form id="cancelRiskForm" action="cancelRisk" method="POST">
		<input id="risk_id" type="hidden" name="id" value="${riskVO.id}" />
		<input id="cancelReason" type="hidden" name="content" value="" />
	</form>
	<form id="approvalRiskForm" action="approvalRisk" method="POST">
		<input id="risk_id" type="hidden" name="id" value="${riskVO.id}" />
		<input id="approval_order" type="hidden" name="approval_order" value="${riskVO.approval_order}" />
	</form>
	<form id="commentInsertForm" action="insertComment" method="POST">
		<input id="reply_risk_id" type="hidden" name="risk_id" />
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

<%@ include file="IncludeBottom_renewal.jsp"%>
