
function updateNotice() {
	$('#updateForm').submit();
}

function popupImage(path){
	$('#file_image').attr('src', './image?virtname=' + path);
	$('#btn_image_modal').click();
}

function replyComment(notice_id, comment_id) {
	if (typeof comment_id == 'undefined') {
		comment_id = -1;
	}	
	$('#reply_notice_id').val(notice_id);
	$('#reply_parent_id').val(comment_id);
	var result = $('.comment_' + notice_id + '_' + comment_id).val();

	$('#reply_cotent_id').val(result);
	
	if(result.length > 0) {
		$('#commentInsertForm').submit();
	}
	else {
		alert("입력을 확인해주세요.");
	}
}

function deleteReply(comment_id) {
	var input = confirm('해당 댓글/대댓글을 삭제하시겠습니까?');
	if (input) {
		$.ajax({
			type : "POST",
			url : "./notice/delete/comment",
			data : {
				comment_id : comment_id
			},
			cache : false,
			async : false,
			success : function(json, status) {
				var json_data = JSON.parse(json);
				if (json_data == "true" || json_data == true) {
					location.reload();
				}					
			}
		});
	}
}

function updateReply(comment_id, content) {
	var content_value = prompt("댓글/대댓글 수정", content);

	if(content_value.length > 0 && content_value != null) {
		//alert("수정한 내용: " + content_value);
		$.ajax({
			type : "POST",
			url : "./notice/update/comment",            
			data : {
				comment_id : comment_id, 
				content : content_value
			},
			async: true,
			cache : false,         
			success : function(json, status){
				var json_data = JSON.parse(json);
				if (json_data == "true" || json_data == true) {
					$('.comment_'+comment_id+'_content').text(content_value);
					alert('수정이 완료되었습니다.');
				}	
			}
		}); 
	}
	else {
		alert('취소하었습니다');
	}
}

function showReplyInput(comment_id) {
	$('[class^=reply_text_]').hide();
	$('.reply_text_' + comment_id).show();
}

function hideReplyInput(comment_id) {
	$('#reply_text_' + comment_id).hide();
}

function submitReturn() {
	$('#returnForm').submit();
}