<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

var CUR_SITE_ID = '${userLoginInfo.site_id}';
var CUR_CONT_ID = '${userLoginInfo.cont_id}';
var CUR_NFC_LIST = [];
var CUR_WORKER_LIST = [];
var CUR_USER_LIST = [];

$(document).ready(function() {	
	$('#search_cont_id').val('${tar_cont_id}');
	getNFCList();
});

function getNFCList() {
	
	CUR_NFC_LIST = [];
	
	$('#nfcTable').bootstrapTable();
	
	<c:forEach var="item" items="${nfcList}" varStatus="idx">	
	
		var id = '${item.id}';
		var idx = ${item.idx};
		var role = ${item.role};
		var cont_name = '${item.cont_name}';
		var serial_number = '${item.serial_number}';
		var name = '${item.name}';	
		var gubun_text = getRoleText(role);
		if(name == '') {
			name = '-';
		}
		
		var nfc = {
			id : id,
			idx : idx,
			cont_name : cont_name,
			role : role,
			serial_number : serial_number,
			name : name,
			gubun_text : gubun_text,
		}	
		
		CUR_NFC_LIST.push(nfc);	
		
	</c:forEach>

	if(CUR_NFC_LIST.length > 0) {
		$('#nfcTable').bootstrapTable('load', CUR_NFC_LIST);		
	}
	else {
		$('#nfcTable').bootstrapTable('load', []);
	}
}

function getRoleText(role){
	if(role == 1) {
		return '<span style="font-weight:600; color:#59509d;"><i class="fa-solid fa-user-tie"></i> 관리자</span>';
	}		
	else if(role == 2) {
		return '<span style="font-weight:600; color:#6fa95b;"><i class="fa-solid fa-user"></i> 근로자</span>';
	}		
	else {
		return '-';
	}	
}

function printNfcList() {
	$('#printForm').submit();
}

function changeCont(){
	$('#searchForm').submit();
}

</script>

<style>

</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">NFC 관리</div>
	
	<input id="tar_site_id" type="hidden" name="site_id" value="${userLoginInfo.site_id}"/>  
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="printNfcList()"><i class="fa-solid fa-print"></i> 출력</div>
	</div>
	
	<div class="content_selete_box content-item">
		<form id="searchForm" class="form-contractor" action="nfcList" method="POST" autocomplete="off" > 
			<span class="select-title">업체:</span>
			<select id="search_cont_id" name="cont_id" class="form-control select-content" onchange="changeCont()">
				<option value="-1">전체</option>
				<c:forEach var="cont" items="${contList}" varStatus="idx">
				<option value="${cont.id}">${cont.name}</option>
				</c:forEach>	  
			</select>
		</form> 
	</div>
	
	<div class="content_data_count_box content-item">
		총 ${nfcList.size()} 개
	</div>

	<div class="content_table_box content-item">
		<table id="nfcTable" data-search="true" data-pagination="true"
				data-page-size="100" data-page-list="[25, 50, 100, 300, All]" data-filter-control="true" 
				class="table table-bordered col-xs-12 table-hover table-striped">
			<thead>
				<tr>	
					<th data-field="idx"  class="text-center" data-sortable="true">번호</th>			 
					<th data-field="cont_name" class="text-center" data-sortable="true">업체명</th>
					<th data-field="name" class="text-center" data-sortable="true">이 름</th>
					<th data-field="gubun_text" class="text-center" data-sortable="true">구분</th>	
				</tr>
			</thead>
		</table>
	</div>	
</div> <!-- content-wrapper END -->

<div id="form_group">
	<form id="printForm" action="printNfcList" method="POST" >
		<input type="hidden" name="site_id" value="${userLoginInfo.site_id }"/>
		<input type="hidden" name="cont_id" value="${tar_cont_id}"/>
	</form>
</div>

<%@ include file="IncludeBottom.jsp"%>