<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

$(document).ready(function() {
	
	$('#search_cont_id').val('${tar_cont_id}');
	
	$('#equipTable').on('click', '.equipTR' , function (id) {
		var id = $(this).attr('id');	
		$("#equip_id").val(id);
   		$("#v_equip_id").val(id);    
   		$('#viewForm').submit();
	});
	
});

function registerEquip() {
	$('#registerForm').submit();	
}

function changeCont(){
	$('#searchForm').submit();
}

</script>

<style>

#equipTable tr {
	cursor: pointer;
}

</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">반입전 장비 목록</div>
	
	<div class="content_button_box content-item" >
		<div class="btn btn-default" onclick="registerEquip()"><i class="fa-regular fa-registered"></i> 등록</div>
	</div>	
	
	<div class="content_selete_box content-item">
		<form id="searchForm" class="form-contractor" action="equipList" method="POST" autocomplete="off"> 
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
		<table id="equipTable" data-toggle="table" data-toggle="table" data-search="true" data-pagination="true" 
			data-page-size="10" data-page-list="[10,25,All]" data-sort-name="[name]" data-filter-control="true"
			data-page-number="1" class="table table-bordered col-xs-12 table-hover table-striped">	
			
			<thead>
				<tr>
					<th class="text-center show-web">순번</th>
					<th class="text-center show-web" data-sortable="true">장비(대분류)</th>     
					<th class="text-center show-web">장비등록번호</th>
					<th class="text-center show-web">검사일</th>					
					<th class="text-center show-web">보험일</th>					
					<th class="text-center show-web">운전원<br>소 속</th>
					<th class="text-center show-web">운전원</th>
					<th class="text-center show-web">면허번호</th>     
					<th class="text-center">사진</th>
					<th class="text-center show-mobile">장비 정보</th>
					<th class="text-center show-mobile">운전원 정보</th>
				</tr>
			</thead>
			
			<tbody>            
			    
				<c:forEach var="data" items="${equipList}" varStatus="idx">
				<tr class="equipTR" id="${data.id}">
					<td class="text-center show-web">${idx.index + 1}</td>
					<td class="text-center show-web">${data.large_category_name}</td>  
					<td class="text-center show-web">${data.equip_registration_no}</td>  
				   	<td class="text-center show-web">${data.check_start}<br>~<br>${data.check_end}</td>
				   	<td class="text-center show-web">${data.insur_start}<br>~<br>${data.insur_end}</td>
				   	<td class="text-center show-web">${data.driver_cont_name}</td>
				   	<td class="text-center show-web">${data.driver_name}</td>
				   	<td class="text-center show-web">${data.driver_license_no}</td>
					<td class="text-center">
						<img class="photo" src="./image?virtname=${data.equip_img}" style="width: 80px; height: 80px;" onerror="this.src='images/noimage.png'">
					</td>	
					<td class="text-left show-mobile">
						<div class="mobile-box">
							<div class="mobile-item">업체: ${data.cont_name}</div>
					    	<div class="mobile-item">대분류: ${data.large_category_name}</div>
							<div class="mobile-item">장비등록번호: ${data.equip_registration_no}</div>	
					    	<div class="mobile-item">소분류: ${data.small_category_name}</div>								    	
						</div>
					</td>
					<td class="text-left  show-mobile">
						<div class="mobile-box">
					    	<div class="mobile-item">업체: ${data.driver_cont_name}</div>
					    	<div class="mobile-item">이름: ${data.driver_name}</div>
					    	<div class="mobile-item">직종: ${data.drvier_role}</div>
					    	<div class="mobile-item">연락처: ${data.driver_phone}</div>
						</div>
					</td>
				</tr>
				</c:forEach>
		
			</tbody>
			
		</table>
	</div>
	
</div> <!-- content-wrapper END -->

<div id="form_group">
	<form id="registerForm" action="registerEquip" method="POST" ></form>
	<form id="updateForm" action="registerEquip" method="POST" >
		<input id="equip_id" type="hidden" name="id"/>
	</form>
	<form id="viewForm" action="equipView" method="POST" >
		<input id="v_equip_id" type="hidden" name="id"/>
	</form>	
</div>

<%@ include file="IncludeBottom.jsp"%>