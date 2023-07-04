<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script type="text/javascript" src="js/notice/view.js"></script>
<link rel="stylesheet" href="css/notice/view.css">


<script>
$(document).ready(function() {

});
</script>

<form:form id="noticeViewForm" action="noticeView" class="form-horizontal" method="POST" enctype="multipart/form-data" modelAttribute="noticeVO" autocomplete="off">

<div id="content-wrapper">
	<div id="content_title" class="content-item">공지사항 상세보기</div>
	
	<form:input id="notice_id" type="hidden" path="id" value="${noticeVO.id}"/>
		
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="submitReturn()"><i class="fa-solid fa-arrow-rotate-left"></i> 뒤로</div>	
		<c:if test="${noticeVO.writer_user_id.equals(userLoginInfo.id)}">
			<div class="btn btn-default" onclick="updateNotice()"><i class="fa-regular fa-pen-to-square"></i> 수정</div>
		</c:if>   
	</div>
	
	<div class="content_table_box content-item">	

		<div class="web-reg-box table-container">
			<table id="noticeViewTable" 
				class="reg-table table table-bordered col-xs-12 table-hover">	
				<tr>
					<th class="text-center">제 목</th>		   				   	
					<td colspan="2" class="text-left">
						<div class="item">${noticeVO.title}</div>
					</td>
				</tr>
				<tr>
					<th class="text-center">작성자</th>
					<td colspan="2" class="text-left">
						<div class="item"> <span style="font-weight: 600;">${noticeVO.cont_name}</span> - ${noticeVO.writer_name}</div>
					</td>
				</tr>
				<tr>
		   			<th class="text-center">시간</th>
		   			<td colspan="2" class="text-left">
						<div class="item">${noticeVO.write_time.replace(".0", "")}</div>
					</td>
		   		</tr>
		   		<tr>
					<th class="text-center">첨부파일</th>
					<td colspan="2" class="text-left" style="height: 200px;">
						<div class="file-box">
							<c:forEach var="file" items="${noticeVO.file_list}" varStatus="loop">
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
		   			<th class="text-center">내용</th>
		   			<td colspan="2" class="text-left" style="height: 600px;">
						<div class="item" style="height: 100%;">${noticeVO.contents}</div>
					</td>
				</tr>	
				<tr>
		   			<th rowspan="3" class="text-center">댓글</th>
				</tr>
				<tr>
					<td colspan="2" class="text-left" style="height: 300px;">
						<div class="item comment-box" style="height: 100%;">
							<c:forEach var="comment" items="${noticeVO.comment_list}" varStatus="idx">
								<c:if test="${comment.parent_id == -1 }">
									<div class="item-comment-box">
										<div class="item-comment show-web comment-photo">
											<img src="${contextPath}/image?virtname=${comment.writer_photo}" class="photo" onerror="this.src='images/noimage.png'">
										</div>
										<div class="item-comment show-web comment-info">		
											<div class="item-comment-header">
												<div><span style="font-weight: 600;">${comment.writer_cont_name}</span> - ${comment.writer_name}</div>
												<div>${comment.write_time.replace(".0", "")}</div>
											</div>
											<div class="comment_${comment.id}_content item-comment-body">			
												${comment.content}
											</div>
											<div class="item-comment-footer">
												<c:if test="${comment.writer_user_id.equals(userLoginInfo.id) && comment.useyn.equals('Y')}"> 
												<div class="item-btn btn icon-danger" onclick="deleteReply('${comment.id}')"><i class="fa-solid fa-trash"></i></div>
												<div class="item-btn btn icon-info" onclick="updateReply('${comment.id}','${comment.content}')"><i class="fa-regular fa-pen-to-square"></i></div>
												</c:if>
												<div class="item-btn btn icon-default" onclick="showReplyInput('${comment.id}' )"><i class="fa-solid fa-reply"></i></div>												
											</div>
										</div>
										<div class="item-comment show-mobile" style="width: 100%;">
											<div class="item-mobile-comment-header">
												<div><span style="font-weight: 600;">${comment.writer_cont_name}</span> - ${comment.writer_name}</div>
												<div>${comment.write_time.replace(".0", "")}</div>
											</div>
											<div class="comment_${comment.id}_content item-comment-body">
												<div>${comment.content}</div>
											</div>		
											<div class="item-comment-footer">											
												<c:if test="${comment.writer_user_id.equals(userLoginInfo.id) && comment.useyn.equals('Y')}"> 
												<div class="item-btn btn icon-danger" onclick="deleteReply('${comment.id}')"><i class="fa-solid fa-trash"></i></div>
												<div class="item-btn btn icon-info" onclick="updateReply('${comment.id}','${comment.content}')"><i class="fa-regular fa-pen-to-square"></i></div>
												</c:if>	
												<div class="item-btn btn icon-default" onclick="showReplyInput('${comment.id}' )"><i class="fa-solid fa-reply"></i></div>
											</div>								
										</div>
									</div>
									<div class="reply_text_${comment.id} item reply-box" style="display: none;">
										<input hidden="hidden" />
										<input class="reply-input comment_${noticeVO.id}_${comment.id}" name="comment"	maxlength="150" placeholder="대댓글을 입력하세요." style="border: none;" />
										<div class="btn btn-info reply-btn" onclick="replyComment('${noticeVO.id}','${comment.id}')"><i class="fa-solid fa-pencil"></i> 작성</div>
									</div>
								</c:if>
							
								<c:forEach var="reply_comment" items="${noticeVO.comment_list}"	varStatus="idx2">
									<c:if test="${comment.id == reply_comment.parent_id }">
										<div class="item-reply-box">
											<div class="item-comment show-web comment-photo">
												<img src="${contextPath}/image?virtname=${reply_comment.writer_photo}" class="photo" onerror="this.src='images/noimage.png'">
											</div>
											<div class="item-comment show-web comment-info">		
												<div class="item-comment-header">
													<div><span style="font-weight: 600;">${reply_comment.writer_cont_name}</span> - ${reply_comment.writer_name}</div>
													<div>${reply_comment.write_time.replace(".0", "")}</div>
												</div>
												<div class="comment_${reply_comment.id}_content item-comment-body">
													${reply_comment.content}
												</div>
												<div class="item-comment-footer">												
													<c:if test="${reply_comment.writer_user_id.equals(userLoginInfo.id) && reply_comment.useyn.equals('Y')}">
													<div class="item-btn btn icon-danger" onclick="deleteReply('${reply_comment.id}')"><i class="fa-solid fa-trash"></i></div>
													<div class="item-btn btn icon-info" onclick="updateReply('${reply_comment.id}','${reply_comment.content}')"><i class="fa-regular fa-pen-to-square"></i></div>
													</c:if>
												</div>
											</div>
											<div class="item-comment show-mobile" style="width: 100%;">
												<div class="item-mobile-comment-header">
													<div><span style="font-weight: 600;">${reply_comment.writer_cont_name}</span> - ${reply_comment.writer_name}</div>
													<div>${reply_comment.write_time.replace(".0", "")}</div>
												</div>
												<div class="comment_${reply_comment.id}_content item-comment-body">
													<div>${reply_comment.content}</div>
												</div>		
												<div class="item-comment-footer">											
													<c:if test="${reply_comment.writer_user_id.equals(userLoginInfo.id) && reply_comment.useyn.equals('Y')}">													 
													<div class="item-btn btn icon-danger" onclick="deleteReply('${reply_comment.id}')"><i class="fa-solid fa-trash"></i></div>
													<div class="item-btn btn icon-info" onclick="updateReply('${reply_comment.id}','${reply_comment.content}')"><i class="fa-regular fa-pen-to-square"></i></div>													
													</c:if>	
												</div>
											</div>											
										</div>
									</c:if>
								</c:forEach>	
									
							</c:forEach>
						</div>
					</td>
				</tr>
				<tr>						
		   			<td class="text-left" style="height: 40px; width: 100%;">
						<div class="item comment-box" style="height: 100%;">
							<input id="reply_comment" type="hidden" name="comment" />
							<input class="comment_${noticeVO.id}_-1" name="comment"	maxlength="150" placeholder="댓글을 입력하세요." style="width: 100%; border: none;" />
						</div>
					</td>
					<td>					
						<div class="item-btn btn btn-default" onclick="replyComment('${noticeVO.id}')"><i class="fa-regular fa-pen-to-square"></i> 작성</div>
					</td>
				</tr>
			</table>	
		</div>
	</div>
</div>

</form:form>

<div id="form_group">
	<form id="returnForm" action="returnNotice" method="POST"></form>
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

<%@ include file="IncludeBottom_renewal.jsp"%>
