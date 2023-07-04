<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

$(document).ready(function() {
	
	$('#search_cont_id').val('${tar_cont_id}');
	
	$('#noticeTable').on('click', '.noticeTR' , function (id) {
		var id = $(this).attr('id');	
		$("#notice_id").val(id);
   		$("#v_notice_id").val(id);    
   		$('#viewForm').submit();
	});
	
});

function registerNotice() {
	$('#registerForm').submit();	
}

function changeCont(){
	$('#searchForm').submit();
}


</script>

<style>

#noticeTable tr {
	cursor: pointer;
}
	

</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">공지사항</div>
	
	<div class="content_button_box content-item" >
		<div class="btn btn-default" onclick="registerNotice()"><i class="fa-regular fa-registered"></i> 등록</div>
	</div>	
	
	<div class="content_selete_box content-item">
		<form id="searchForm" class="form-contractor" action="noticeList" method="POST" autocomplete="off"> 
		<c:choose>
		<c:when test="${sessionScope.userLoginInfo.cont_type == 0}"> 		      
			<span class="select-title">업체:</span>
			<select id="search_cont_id" name="cont_id" class="form-control select-content" onchange="changeCont()">
				<option value="-1">전체</option>
				<c:forEach var="cont" items="${contList}" varStatus="idx">
				<option value="${cont.id}">${cont.name}</option>
				</c:forEach>	  
			</select>
		</c:when>
		<c:otherwise>
			<span class="select-title">업체:</span> 	
			<select name="cont_id" class="form-control select-content">				
				<option value="${sessionScope.userLoginInfo.cont_id}">${sessionScope.userLoginInfo.cont_name}</option>
			</select>
			<input id="cont_id" type="hidden" name="cont_id" value="${sessionScope.userLoginInfo.cont_id}"/>
		</c:otherwise>	
		</c:choose>
		</form>
	</div>

	<div class="content_table_box content-item">
		<table id="noticeTable" data-toggle="table" data-toggle="table" data-search="true" data-pagination="true" 
			data-page-size="10" data-page-list="[10,25,All]" data-sort-name="[name]" data-filter-control="true"
			data-page-number="1" class="table table-bordered col-xs-12 table-hover table-striped">	
			
			<thead>
				<tr>
					<th class="text-center">순번</th>
					<th class="text-center show-web">작성업체</th>     
					<th class="text-center show-web">제목</th>
					<th class="text-center show-web" data-sortable="true">작성일</th>					
					<th class="text-center show-web">작성자</th>					
					<th class="text-center show-web">조회수</th>
					<th class="text-center show-mobile">공지사항 내용</th>     
				</tr>
			</thead>
			
			<tbody>                
				<c:forEach var="notice" items="${noticeList}" varStatus="idx">
				<tr class="noticeTR" id="${notice.id}">
					<td class="text-center">${idx.index + 1}</td>
					<td class="text-center show-web">${notice.cont_name}</td>  
				   	<td class="text-center show-web">${notice.title}</td>
				   	<td class="text-center show-web">${notice.write_date}</td>
				   	<td class="text-center show-web">${notice.writer_name}</td>
					<td class="text-center show-web">${notice.hit}</td>				    
				    <td class="text-center show-mobile mobile-wrap">
				    	<div class="mobile-box">
				    		<div class="mobile-item notice-title">${notice.title}</div>
				    		<div class="mobile-item notice-writer">작성자: [<span style="color:#2710d5;">${notice.cont_name}</span>] ${notice.writer_name}</div>
						</div>
						<div class="mobile-box">
							<div class="mobile-item notice-date">${notice.write_date}</div>
				    		<div class="mobile-item notice-hit">조회수: <span style="color: #ff3547; margin-left: 3px;">${notice.hit}</span></div>
						</div>
				    </td>
				</tr>
				</c:forEach>
			</tbody>
			
		</table>
	</div>
	
</div> <!-- content-wrapper END -->

<div id="form_group">
	<form id="registerForm" action="registerNotice" method="POST" ></form>
	<form id="updateForm" action="registerNotice" method="POST" >
		<input id="notice_id" type="hidden" name="id"/>
	</form>
	<form id="viewForm" action="noticeView" method="POST" >
		<input id="v_notice_id" type="hidden" name="id"/>
	</form>	
</div>

<%@ include file="IncludeBottom_renewal.jsp"%>