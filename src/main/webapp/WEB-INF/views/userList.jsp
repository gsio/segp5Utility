<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

	$(document).ready(function() {
		
		$('#userTable').on('click', '.clickableTR' , function (id) {
			var id = $(this).attr('id');
			updateUser(id);
		});	 
		
	});
	
	
	function updateUser(val) {	
		var site_id = $('#tar_site_id').val();
		$("#u_tar_site_id").val(site_id);	
		$("#u_id").val(val);
		$('#updateForm').submit();	
	}
	
	function registerUser() {
		var site_id = $('#tar_site_id').val();
		$("#s_tar_site_id").val(site_id);
		$('#registerForm').submit();
	}
	
	function printUserList(){
		$('#printUserList').submit();
	}

</script>

<style>

</style>

<div id="wrapper" class="text-left col-xs-12" style="margin-left: 1.5%;">
	<div class="list_title">관리자 등록</div>	
		
	<input id="tar_site_id" type="hidden" name="site_id" value="${userLoginInfo.site_id}"/>  
	<div class="text-right" stlye="margin-right:10%;">
		<c:choose>
			<c:when test="${userLoginInfo.company_id == 1 || userLoginInfo.company_id == 4}">
				<div class="btn btn-default" style="" onclick="printUserList()"><span class="glyphicon glyphicon-print"></span> 출 력</div>				
			</c:when>	
			<c:otherwise>			
			</c:otherwise>
		</c:choose>
	    <div class="btn btn-primary" style="right: 0;" onclick="registerUser()"><span class="glyphicon glyphicon-plus"></span>등록</div>
	</div>
	  
   	<table id="userTable" data-toggle="table"  data-search="true" data-pagination="true" data-page-size="25" 
	data-page-list="[25,50,All]" data-sort-name="[cont, rolecode]" data-filter-control="true"
	data-page-number="1" class="table table-bordered col-xs-12 table-hover table-striped">
		<thead>
			<tr>
				<th class="text-center">순 번</th>
                <th class="text-center">WEB 관리자</th>
                <th class="text-center">이 름</th>
                <th class="text-center">I D</th>
                <th class="text-center" data-field="cont" data-sortable="true"  data-filter-control="select"> 소 속</th>
				<th class="text-center">직 책</th>
				<th class="text-center" data-field="rolecode" data-sortable="true">권 한</th>
				<th class="text-center">연 락 처</th>
				<th class="text-center">혈액형</th>
				<th class="text-center" >신규교육일</th>
				<th class="text-center" >밀폐교육(1차)</th>
				<th class="text-center" >밀폐교육(2차)</th>
				<th class="text-center" >밀폐교육(3차)</th>
				<th class="text-center" >밀폐교육(4차)</th>			
			</tr>
		</thead>
		
		<tbody>
		<c:forEach var="user" items="${userList}" varStatus="idx">
			<tr class="clickableTR" id="${user.id}">
			   	<td class="hidden-xs text-center">${idx.index + 1}</td>
			   	<td class="text-center">
				<c:if test="${user.isManager == 1}">
			   		해당
			   	</c:if>
			   	<c:if test="${user.isManager == 0}">			   		
			   	</c:if>
			   	</td>
				<td class="text-center">${user.name}</td>
				<td class="hidden-xs text-center">${user.userid}</td>
				<td class="text-center">${user.cont_name}</td>
				<td class="text-center">${user.grade}</td>
				<td class="text-center">${user.role_name}</td>
				<td class="hidden-xs hidden-sm text-center">${user.phone}</td>
				<td class="hidden-xs hidden-sm text-center">${user.btype}</td>
				<td class="hidden-xs hidden-sm text-center">${user.edudate}</td>
				<td class="hidden-xs hidden-sm text-center">${user.sealed_date1}</td>
			    <td class="hidden-xs hidden-sm text-center">${user.sealed_date2}</td>
			    <td class="hidden-xs hidden-sm text-center">${user.sealed_date3}</td>
			    <td class="hidden-xs hidden-sm text-center">${user.sealed_date4}</td>			
			</tr>
		</c:forEach>
		</tbody>
	</table>    
</div>

<div id="form_group">
	<form id="registerForm" action="registerUser" method="POST" >
		<input id="s_tar_site_id" type="hidden" name="tar_site_id"/>
	</form>
	<form id="printUserList" action="printUserList" method="POST" ></form>
	<form id="updateForm" action="registerUser" method="POST" >
		<input id="u_tar_site_id" type="hidden" name="tar_site_id"/>
		<input id="u_id" type="hidden" name="u_id"/>
	</form>
	<form id="searchForm" action="userList" method="POST">		
		<input id="s_site_id" name="site_id" type="hidden">		
	</form>
</div>

<%@ include file="IncludeBottom.jsp"%>
