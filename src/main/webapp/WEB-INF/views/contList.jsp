<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

$(document).ready(function(){
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


</style>
<div id="wrapper" class="text-left col-xs-12" style="margin-left: 1.5%;">
	<div class="list_title">업 체 관 리</div>
	<input id="tar_site_id" type="hidden" name="site_id" value="${searchVO.site_id}"/>	

	<div class="text-right">
		<div class="btn btn-primary" style="right: 0;" onclick="viewSubmit()"><span class="glyphicon glyphicon-plus"></span>등록</div>
	</div>
	 
	<table id="contTable" data-toggle="table" data-show-columns="true" data-search="true" data-pagination="true" 
		data-page-size="50" data-page-list="[10,20,All]" data-sort-name="[name]" data-filter-control="true" 
 		class="table table-bordered col-xs-8 table-hover table-striped" >		
		<thead>
			<tr>
				<th class="text-center">순번</th>
				<th class="text-center">구분</th>
				<th class="text-center" data-field="name" data-sortable="true">업체명</th>	
				<th class="text-center">사업자번호</th>
				<th class="text-center">사무실전화</th>
				<th class="text-center">대표자</th>
				<th class="text-center">대표 작업공정</th>     
			</tr>
		</thead>
		
		<tbody>                
			<c:forEach var="cont" items="${contList}" varStatus="idx">
				<tr class="clickableTR" id="${cont.id}">
					<td class="text-center">${idx.index + 1}</td>
					<td class="text-center">
						<c:if test="${cont.type == '0'}">원청사</c:if>
						<c:if test="${cont.type == '1'}">협력사</c:if>
						<c:if test="${cont.type == '2'}">감리단</c:if>
						<c:if test="${cont.type == '3'}">본부</c:if>
						<c:if test="${cont.type == '4'}">발주처/기타</c:if>
					</td>
					<td class="text-center">${cont.name}</td>
					<td class="text-center">${cont.reg_num}</td>
					<td class="text-center">${cont.phone}</td>
					<td class="text-center">${cont.rep_name}</td>	
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
     
<div id="form_group">
	<form id="searchForm" action="contList" method="POST">
		<input id="s_site_id" name="site_id" type="hidden">
	</form>
	<form id="registerContForm" action="registerCont" method="POST" >
		<input id="u_tar_site_id" type="hidden" name="tar_site_id"/>
		<input id="cont_id" type="hidden" name="cont_id"/>
	</form>
</div>


<%@ include file="IncludeBottom.jsp"%>
