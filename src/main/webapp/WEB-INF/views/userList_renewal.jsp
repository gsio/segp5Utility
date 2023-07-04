<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>



<script>

$(document).ready(function() {
	
	$('#userTable').on('click', '.clickableTR' , function (id) {
		var id = $(this).attr('id');
		updateUser(id);
	});
	$('#search_cont_id').val('${tar_cont_id}');
	
});

function changeCont(){
	$('#searchForm').submit();
}

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
	$('#print_cont_id').val('${tar_cont_id}');
	$('#printUserList').submit();
}

</script>

<style>

#userTable tr {
	cursor: pointer;
}

</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">관리자 관리</div>
	
	<input id="tar_site_id" type="hidden" name="site_id" value="${userLoginInfo.site_id}"/>  
	<div class="content_button_box content-item" >
		<c:if test="${sessionScope.userLoginInfo.cont_type == 0}">
			<div class="btn btn-primary" onclick="printUserList()"><i class="fa-solid fa-print"></i> 출력</div>				
		</c:if>
		<div class="btn btn-default" onclick="registerUser()"><i class="fa-regular fa-registered"></i> 등록</div>
	</div>
	
	<div class="content_selete_box content-item">
		<form id="searchForm" class="form-contractor" action="userList_renewal" method="POST" autocomplete="off" > 
			<c:if test="${sessionScope.userLoginInfo.cont_type == 0}"> 		      
				<span class="select-title">업체:</span>
				<select id="search_cont_id" name="cont_id" class="form-control select-content" onchange="changeCont()">
					<option value="-1">전체</option>
					<c:forEach var="cont" items="${contList}" varStatus="idx">
					<option value="${cont.id}">${cont.name}</option>
					</c:forEach>	  
				</select>
			</c:if>
		</form> 
	</div>
	
	<div class="content_data_count_box content-item">
		총 ${userList.size()} 명
	</div>
	
	<div class="content_table_box content-item">
		<table id="userTable" data-toggle="table" data-search="true" data-pagination="true" data-sort-name="[cont, rolecode]" 
			data-page-size="25" data-page-list="[10, 25, 50, 100, All]" data-filter-control="true"
			data-page-number="1" class="table table-bordered col-xs-12 table-hover table-striped">
			
			<thead>
				<tr>
					<th class="text-center show-web">순 번</th>
	                <th class="text-center show-web" data-sortable="true">WEB 관리자</th>
	                <th class="text-center show-web" data-sortable="true">이 름</th>
	                <th class="text-center show-web">I D</th>
	                <th class="text-center show-web">소 속</th>
					<th class="text-center show-web" data-sortable="true">직 책</th>
					<th class="text-center show-web" data-field="role_name" data-sortable="true" data-filter-control="select">권 한</th>
					<th class="text-center show-web">연 락 처</th>
					<th class="text-center show-web">혈액형</th>
					<th class="text-center show-web">신규교육일</th>
					<th class="text-center show-web">밀폐교육(1차)</th>
					<th class="text-center show-web">밀폐교육(2차)</th>
					<th class="text-center show-web">밀폐교육(3차)</th>
					<th class="text-center show-web">밀폐교육(4차)</th>			
					<th class="text-center show-mobile">관리자 사진</th>
					<th class="text-center show-mobile">상세 인적사항</th>	
					<th class="text-center show-mobile">밀폐교육일자</th>
				</tr>
			</thead>
		
			<tbody>
			<c:forEach var="user" items="${userList}" varStatus="idx">
				<tr class="clickableTR" id="${user.id}">
				   	<td class="text-center show-web">${idx.index + 1}</td>
				   	<td class="text-center show-web">
					<c:if test="${user.isManager == 1}">
				   		해당
				   	</c:if>
				   	<c:if test="${user.isManager == 0}">			   		
				   	</c:if>
				   	</td>
					<td class="text-center show-web">${user.name}</td>
					<td class="text-center show-web">${user.userid}</td>
					<td class="text-center show-web">${user.cont_name}</td>
					<td class="text-center show-web">${user.grade}</td>
					<td class="text-center show-web">${user.role_name}</td>
					<td class="text-center show-web">${user.phone}</td>
					<td class="text-center show-web">${user.btype}</td>
					<td class="text-center show-web">${user.edudate}</td>
					<td class="text-center show-web">${user.sealed_date1}</td>
				    <td class="text-center show-web">${user.sealed_date2}</td>
				    <td class="text-center show-web">${user.sealed_date3}</td>
				    <td class="text-center show-web">${user.sealed_date4}</td>		
				    <td class="text-center show-mobile">
			        	<img class="photo" src="./image?virtname=${user.photo}" style="width: 80px; height: 80px;" onerror="this.src='images/noimage.png'">
					</td>
					<td class="text-left show-mobile">
			        	<div> 업체: ${user.cont_name}</div>
			        	<div> 이름: ${user.name}</div>
			        	<div> 직책: ${user.grade}</div>
			        	<div> 권한: ${user.role_name}</div>
			        	<div> 연락처: ${user.phone}</div>
			        </td>	
			        <td class="text-left show-mobile">
			        	<c:if test="${user.sealed_date1.length() >= 8 }">
							<div>1차 : ${user.sealed_date1}</div>
		      	 		</c:if>
						<c:if test="${user.sealed_date2.length() >= 8 }">
		      	 			<div>2차 : ${user.sealed_date2}</div>
		      	 		</c:if>
				      	<c:if test="${user.sealed_date3.length() >= 8 }">
				      		<div>3차 : ${user.sealed_date3}</div>
				      	</c:if>
				      	<c:if test="${user.sealed_date4.length() >= 8 }">
				      		<div>4차 : ${user.sealed_date4}</div>
				      	</c:if>
			        </td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	
</div> <!-- content-wrapper END -->


<div id="form_group">
	<form id="registerForm" action="registerUser" method="POST" >
		<input id="s_tar_site_id" type="hidden" name="tar_site_id"/>
	</form>
	<form id="printUserList" action="printUserList" method="POST" >
		<input id="print_cont_id" type="hidden" name="cont_id" />	
	</form>
	<form id="updateForm" action="registerUser" method="POST" >
		<input id="u_tar_site_id" type="hidden" name="tar_site_id"/>
		<input id="u_id" type="hidden" name="u_id"/>
	</form>
</div>

<%@ include file="IncludeBottom_renewal.jsp"%>