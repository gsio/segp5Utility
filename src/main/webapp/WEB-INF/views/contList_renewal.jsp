<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>



<script>

$(document).ready(function() {
	
	$('#tar_site_id').val('${tar_site_id}');
	$('#contTable').on('click', '.clickableTR' , function (id) {
		var id = $(this).attr('id');
    	viewSubmit(id);
	});
	
});

function viewSubmit(val) {
	var site_id = $('#tar_site_id').val();	
	$("#u_tar_site_id").val(site_id);	
	$("#cont_id").val(val);
	$('#registerContForm').submit();
}

</script>

<style>

#contTable tr {
	cursor: pointer;
}

</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">업체 관리</div>
	
	<input id="tar_site_id" type="hidden" name="site_id" value="${userLoginInfo.site_id}"/>  
	<div class="content_button_box content-item" >
		<div class="btn btn-default" onclick="viewSubmit()"><i class="fa-regular fa-registered"></i> 등록</div>
	</div>
	
	<div class="content_data_count_box content-item">
		총 ${contList.size()} 개
	</div>

	<div class="content_table_box content-item">
		<table id="contTable" data-toggle="table" data-search="true" data-pagination="true" data-page-size="25" 
			data-page-list="[10, 25, 50, 100, All]" data-sort-name="[name]" data-filter-control="true"
			data-page-number="1" class="table table-bordered col-xs-12 table-hover table-striped"> 	
			<thead>
				<tr>
					<th class="text-center">순번</th>
					<th class="text-center show-web" data-field="type" data-sortable="true" data-filter-control="select">구분</th>
					<th class="text-center show-web">업체명</th>	
					<th class="text-center show-web">사업자번호</th>
					<th class="text-center show-web">업체전화</th>
					<th class="text-center show-web">대표자</th>				
					<th class="text-center show-mobile">업체정보</th>
					<th class="text-center">대표 작업공정</th>     
				</tr>
			</thead>
			
			<tbody>                
				<c:forEach var="cont" items="${contList}" varStatus="idx">
					<tr class="clickableTR" id="${cont.id}">
						<td class="text-center">${idx.index + 1}</td>
						<td class="text-center show-web">
							<c:if test="${cont.type == '0'}">원청사</c:if>
							<c:if test="${cont.type == '1'}">협력사</c:if>
							<c:if test="${cont.type == '2'}">감리단</c:if>
							<c:if test="${cont.type == '3'}">본부</c:if>
							<c:if test="${cont.type == '4'}">발주처/기타</c:if>
						</td>
						<td class="text-center show-web">${cont.name}</td>
						<td class="text-center show-web">${cont.reg_num}</td>
						<td class="text-center show-web">${cont.phone}</td>
						<td class="text-center show-web">${cont.rep_name}</td>	
						<td class="text-left show-mobile">
				        	<div> 업체명: ${cont.name}</div>
				        	<div> 대표자: ${cont.rep_name}</div>
				        	<div> 연락처: ${cont.phone}</div>
				        </td>
						<td class="text-center">
							<c:choose>
								<c:when test="${cont.state == '0'}"></c:when>
								<c:otherwise>${cont.state_name}</c:otherwise>
							</c:choose>
						</td>   
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</div> <!-- content-wrapper END -->

<div id="form_group">
	<form id="searchForm" action="contList" method="POST">
		<input id="s_site_id" name="site_id" type="hidden">
	</form>
	<form id="registerContForm" action="registerCont" method="POST" >
		<input id="u_tar_site_id" type="hidden" name="tar_site_id"/>
		<input id="cont_id" type="hidden" name="cont_id"/>
	</form>
</div>

<%@ include file="IncludeBottom_renewal.jsp"%>