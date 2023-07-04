<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>
<link rel="stylesheet" href="css/device/beacon/list.css">

<script>

var CUR_SITE_ID = '${userLoginInfo.site_id}';
var CUR_CONT_ID = '${userLoginInfo.cont_id}';
var CUR_BEACON_LIST = [];
var CUR_WORKER_LIST = [];
var CUR_USER_LIST = [];

$(document).ready(function() {	
	//$('#search_cont_id').val('${tar_cont_id}');
	getBeaconList();
});

function getBeaconList() {
	
    $('#beaconTable').bootstrapTable('destroy')
	.bootstrapTable()
  	.bootstrapTable('showLoading')	
	
	CUR_BEACON_LIST = [];
	
	<c:forEach var="item" items="${beaconList}" varStatus="idx">	
		var id = '${item.id}';
		var idx = ${item.idx};
		var role = ${item.role};
		var cont_name = '${item.cont_name}';
		var mac_addr = '${item.mac_addr}';		
		var name = '${item.name}';	
		var gubun_text = getRoleText(role);
		var type = ${item.type};
		var last_scan_time = '${item.last_scan_time}';
		last_scan_time = last_scan_time.replace(".0", "");
		var time_diff_hour = ${item.time_diff_hour};
		var battery_state = ${item.battery};
		var battery = "";
		
		if(type == 1) {
			type = '구형'; 
		}
		else {
			type = '신형';
		}
		
		if(time_diff_hour > 72) {
			battery = '<span style="font-weight:600;"><i class="fa-solid fa-question"></i></span>';
			last_scan_time = '<span style="font-weight:600;"><i class="fa-solid fa-question"></i></span>';
		}
		else {
			if(battery_state == 1) { 
				battery = '<span style="color: #dc3545 !important; font-weight:600;"><i class="fa-solid fa-battery-quarter"></i> 부족</span>';
			} 
			else if(battery_state == 2) {
				battery = '<span style="color: #4bc91f !important; font-weight:600;"><i class="fa-solid fa-battery-half"></i> 보통</span>';
			}
			else if(battery_state == 3) {
				battery = '<span style="color: #2710d5 !important; font-weight:600;"><i class="fa-solid fa-battery-full"></i> 충분</span>';
			}
		}

		var allocate_info = "";
		if(cont_name != "") {
			allocate_info += '<div class="text-left"> 업체 : '+cont_name+'</div>';
		}
		if(name != "") {
			allocate_info += '<div class="text-left"> 이름 : '+name+'</div>';
		}
		
		var allocate_beacon = '<div class="btn icon-default" onclick="overlayUserList('+ id +','+ idx +',\''+ name + '\','+ role + ')"><i class="fa-regular fa-pen-to-square"></i></div>';
		var allocate_delete_beacon = '<div class="btn icon-danger" onclick="cancelBeacon('+ id + ',\'' + mac_addr + '\',\'' + name + '\',' + idx + ')"><i class="fa-solid fa-trash"></i></div>';
		
		
		var modify =
			'<div style="float: left; width: 50%;" class="btn icon-default" onclick="overlayUserList('+ id +','+ idx +',\''+ name + '\','+ role + ')"><i class="fa-regular fa-pen-to-square"></i></div>' +
			'<div style="float: left; width: 50%;" class="btn icon-danger" onclick="cancelBeacon('+ id + ',\'' + mac_addr + '\',\'' + name + '\',' + idx + ')"><i class="fa-solid fa-trash"></i></div>';
		
		var beacon = {
			id : id,
			idx : idx,
			type: type,
			battery: battery,
			last_scan_time: last_scan_time,
			cont_name : cont_name,
			role : role,
			mac_addr : mac_addr,
			name : name,
			gubun_text : gubun_text,
			allocate_info: allocate_info,
			allocate_beacon: allocate_beacon,
			allocate_delete_beacon: allocate_delete_beacon,
			modify : modify
		}	
		CUR_BEACON_LIST.push(beacon);	
	</c:forEach>

	if(CUR_BEACON_LIST.length > 0) {
		$('#beaconTable').bootstrapTable('load', CUR_BEACON_LIST);		
		$('#beaconTable').bootstrapTable('hideLoading');
	}
	else {
		$('#beaconTable').bootstrapTable('load', []);
		$('#beaconTable').bootstrapTable('hideLoading');
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

function overlayUserList(beacon_id, idx, name, role){
	$('#__sel_beacon_id').val(beacon_id);	
	$('#overlay_no').html(idx);
	$('#overlay_name').html(name);
	$('#overlay_type').html(getRoleText(role));	
	$('#modal_userList').click();
	getWorkerList(CUR_SITE_ID, '-1');
	getUserList(CUR_SITE_ID, '-1');
	$('#selectWorkerContId').val(-1);
	$('#selectUserContId').val(-1);
}

function getWorkerList(site_id, cont_id){
	
	//alert("[getWorkerList] : " + site_id + "/" + cont_id);
	
	CUR_WORKER_LIST = [];
	
	$('#__workerTable').bootstrapTable();
	
	<c:forEach var="item" items="${workerList}" varStatus="idx">	
		var id = ${item.id};
		var beacon_idx = ${item.beacon_idx};
		var beacon_text = '';
		var sort_cont_id = ${item.cont_id};
		var cont_name = '${item.cont_name}';
		var name = '${item.name}';
		var tname = '${item.t_name}';
		var phone = '${item.phone}';		
		
		if(beacon_idx == 0) {
			beacon_text = '<div class="label" style="color:#4bc91f;">배정가능</div>';
		}
		else {
			beacon_text = '<div class="label" style="color:#ff3547;">배정 [' + beacon_idx  + ']번</div>'
		}
		
		var select = '<div class="btn btn-primary" onclick="selectUser(2,' + id + ')"><i class="fa-solid fa-check"></i></div>';
		
		var worker_info = 
			'<div class="text-left">' + 
			'<div> 업체: '+cont_name+'</div>' +
			'<div> 이름: '+name+'</div>' +
			'<div> 직종: '+tname+'</div>' +
			'<div> 연락처: '+phone+'</div>' +
			'</div>';
		
		var worker = {
			id : id,
			beacon_idx : beacon_idx,
			beacon_text : beacon_text,
			select : select,
			cont_name : cont_name,
			name : name,
			tname : tname,
			phone : phone,
			worker_info : worker_info
		}	
		
		if(cont_id > 0) {
			if(cont_id == sort_cont_id) {
				CUR_WORKER_LIST.push(worker);	
			}
		}
		else {
			CUR_WORKER_LIST.push(worker);	
		}
		
	</c:forEach>
	
	//alert("CUR_WORKER_LIST.length: " + CUR_WORKER_LIST.length);
	
	if(CUR_WORKER_LIST.length > 0) {
		$('#__workerTable').bootstrapTable('load', CUR_WORKER_LIST);		
	}
	else {
		$('#__workerTable').bootstrapTable('load', []);
	}
}

function getUserList(site_id, cont_id){	
	
	//alert("[getUserList] : " + site_id + "/" + cont_id);
	
	CUR_USER_LIST = [];
	
	$('#__userTable').bootstrapTable();
	
	<c:forEach var="item" items="${userList}" varStatus="idx">	
		var id = '${item.id}';
		var beacon_idx = ${item.beacon_idx};
		var beacon_text = '';
		var sort_cont_id = ${item.cont_id};
		var cont_name = '${item.cont_name}';
		var name = '${item.name}';
		var role_name = '${item.role_name}';
		var phone = '${item.phone}';
		
		if(beacon_idx == 0) {
			beacon_text = '<div class="label" style="color:#4bc91f;">배정가능</div>';
		}
		else {
			beacon_text = '<div class="label" style="color:#ff3547;">배정 [' + beacon_idx  + ']번</div>'
		}
		
		var select = '<div class="btn btn-primary" onclick="selectUser(1, \''+ id +'\')"><i class="fa-solid fa-check"></i></div>';		
		
		var user_info = 
			'<div class="text-left">' + 
			'<div> 업체: '+cont_name+'</div>' +
        	'<div> 이름: '+name+'</div>' +
        	'<div> 권한: '+role_name+'</div>' +
        	'<div> 연락처: '+phone+'</div>' +
			'</div>';
		
		var user = {
			id : id,
			beacon_idx : beacon_idx,
			beacon_text : beacon_text,
			select : select,
			cont_name : cont_name,
			name : name,
			role_name : role_name,
			phone : phone,
			user_info : user_info
		}	
		if(cont_id > 0) {
			if(cont_id == sort_cont_id) {
				CUR_USER_LIST.push(user);	
			}
		}
		else {
			CUR_USER_LIST.push(user);	
		}
			
		
	</c:forEach>
	
	//alert("CUR_USER_LIST.length: " + CUR_USER_LIST.length);
	
	if(CUR_USER_LIST.length > 0) {
		$('#__userTable').bootstrapTable('load', CUR_USER_LIST);		
	}
	else {
		$('#__userTable').bootstrapTable('load', []);
	}
	
}

function cancelBeacon(beacon_id, beacon_mac, name, beacon_idx){	
	var input = confirm(name + '님을 ' + beacon_idx + '번 비콘에서 삭제하시겠습니까?');	
	if(input) {
		$('#__sel_beacon_id').val(beacon_id);
		deleteBeaconAllocate(beacon_id, beacon_mac, beacon_idx);
	} else {
		alert(beacon_idx + '번 비콘 할당 삭제 취소');
	}	
}

function deleteBeaconAllocate(beacon_id, beacon_mac, idx) {
	 $.ajax({
		type : "POST",
		url : "./beacon/unAssign",
		data : {
			id : beacon_id,
			mac_addr : beacon_mac,
			idx : idx
		},
		cache : false,
		async : false,
		success : function(json,status) {
			alert('비콘 할당 삭제 완료');
			window.location.reload();
		}
	});
}

function selectUser(role, u_id){ 
	var beacon_id = $('#__sel_beacon_id').val();
	var beacon_idx = $('#overlay_no').text();
	var result;
	
	if(beacon_id == -1) {
		return;
	}

	if(u_id != ""){
		result = checkDuplicationBeaconUser(beacon_id, u_id, role);
	}
	else{
		result = false;
	}
	
	if(result == 'true' || result == true){
		alert('이미 비콘에 배정되어 있는 작업자입니다.');
	}
	
	else{		
		var input; 
		if(u_id == ''){
			input = confirm(beacon_idx + '번 비콘 할당을 취소하시겠습니까?');
		}else{
			input = confirm(beacon_idx + '번 비콘을 배정하시겠습니까?');
		}		
		if(input){
			
			 $.ajax({
				type : "POST",
				url : "./beacon/update",
				data : {
					id : beacon_id,
					idx : beacon_idx,
		        	u_id : u_id,
					role : role
				},
				cache : false,
				async : false,
				success : function(json,status) {
					alert('수정이 완료되었습니다.');								
					$('#__close_btn').click();
					window.location.reload();
				}
			}); 		
		}
		else{			
			alert('취소되었습니다');
		}
	}	
}

function checkDuplicationBeaconUser(beacon_id, u_id, role){	
	var result = false;
	for(var i = 0 ; i < CUR_BEACON_LIST.length ; i ++ ){
		var u_id_tmp = CUR_BEACON_LIST[i].u_id;		
		if(u_id_tmp == u_id){
			result = true;
		}		
	}	
	return result;
}

function changeContIdByWorkerListOverlay(){
	var cont_id = $('#selectWorkerContId').val();	
	getWorkerList(CUR_SITE_ID, cont_id);
}

function changeContIdByUserListOverlay(){
	var cont_id = $('#selectUserContId').val();	
	getUserList(CUR_SITE_ID, cont_id);
}

function printBeaconList() {
	$('#printForm').submit();
}

function changeCont(){
	$('#searchForm').submit();
}

function loadingTemplate() {
	 return '<i class="fa fa-spinner fa-spin fa-fw fa-2x"></i>'
}

</script>

<style>


</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">Beacon 관리</div>
	
	<input id="tar_site_id" type="hidden" name="site_id" value="${userLoginInfo.site_id}"/>  
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="printBeaconList()"><i class="fa-solid fa-print"></i> 출력</div>
	</div>
	
	<div class="content_selete_box content-item">
		<form id="searchForm" class="form-contractor" action="beaconList_renewal" method="POST" autocomplete="off" > 
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
		총 ${beaconList.size()} 개
	</div>

	<div class="content_table_box content-item">
		<table id="beaconTable" data-search="true" data-pagination="true" data-page-size="100" data-page-list="[25, 50, 100, 300, All]"
			class="table table-bordered col-xs-12 table-hover table-striped" data-loading-template="loadingTemplate" >
			<thead>
				<tr>	
					<th data-field="idx" class="text-center" data-sortable="true">번호</th>			 
					<th data-field="type" class="text-center show-web" data-sortable="true">종류</th>
					<th data-field="cont_name" class="text-center show-web">업체명</th>
					<th data-field="name" class="text-center show-web" data-sortable="true">이 름</th>
					<th data-field="gubun_text" class="text-center show-web" data-sortable="true">구분</th>
					<th data-field="allocate_info" class="text-center show-mobile" data-sortable="true">배정 현황</th>	
					<th data-field="battery" class="text-center" data-sortable="true">배터리</th>
					<th data-field="last_scan_time" class="text-center show-web" data-sortable="true">마지막 수집 시간</th>
					<th data-field="allocate_beacon" class="text-center show-web">비콘배정</th>
					<th data-field="allocate_delete_beacon" class="text-center show-web">배정삭제</th>
					<th data-field="modify" class="text-center show-mobile">배정 및 삭제</th>
				</tr>
			</thead>
		</table>
	</div>	
</div> <!-- content-wrapper END -->

<div id="form_group">
	<form id="printForm" action="printBeaconList" method="POST" >
		<input type="hidden" name="site_id" value="${userLoginInfo.site_id }"/>
		<input type="hidden" name="cont_id" value="${tar_cont_id}"/>
	</form>
</div>

<div style="display:none" id="modal_userList" data-toggle="modal" data-target="#user_modal"></div>
<div class="modal fade" id="user_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
  		<div class="modal-content">
	   		<div class="modal-header" style="font-size: 1.5em;"> 
	   			<div>
   					관리자/근로자 배정
   				</div>    
				<div>
					<div id="__close_btn" class="btn btn-danger margin-top" data-dismiss="modal"><i class="fa-solid fa-xmark"></i> 닫기</div>
				</div>
			</div>
			<div class="modal-body">
		        <div class="col-sm-12">	
		        	<div id="overlay_info_text">
		        		<div class="text-left">
		        			배정할 비콘 정보
		        		</div>		        		  
		        		<table class="table table-bordered text-ceneter" >
		        			<thead><tr><th class="th_11">번 호</th><th class="th_11">이 름</th><th class="th_11">구 분</th></tr></thead>
		        			<tbody><tr><td id="overlay_no" class="text-center"></td><td id="overlay_name" class="text-center"></td><td id="overlay_type" class="text-center"></td></tr></tbody>
		        		</table>        		
		        	</div>   
				    <div id="sub-tab-box" class="tab-content nav nav-tabs">
						<ul class="nav nav-tabs">
							<li><a class="nav-item user-tab" href="#__userList" data-toggle="tab"> 관리자</a></li>
							<li><a class="nav-item worker-tab active show" href="#__workerList" data-toggle="tab">근로자</a></li>
						</ul>
					<div class="tab-content clearfix">		
						<div class="tab-pane" id="__userList">
							<div class="content_selete_box content-item">
								<div class="popup-select-box">
									<span class="select-title">업체:</span>
									<select id="selectUserContId" name="cont_id" class="form-control select-content" onchange="changeContIdByUserListOverlay()">
										<option value="-1">전체</option>
										<c:forEach var="cont" items="${contList}" varStatus="idx">
										<option value="${cont.id}">${cont.name}</option>
										</c:forEach>	  
									</select>
								</div>	
							</div>
							<table id="__userTable" data-click-to-select="true" data-search="true" class="table table-bordered">
								<thead>
									<tr>		
										<th data-field="select" class="text-center">선택</th>
										<th data-field="user_info" class="text-center">관리자 정보</th>
										<th data-field="beacon_text" class="text-center" data-sortable="true">배정여부</th>								
									</tr>
								</thead>
							</table>
						</div>
						<div class="tab-pane active" id="__workerList">						
							<div class="content_selete_box content-item">
								<div class="popup-select-box">
									<span class="select-title">업체:</span>
									<select id="selectWorkerContId" name="cont_id" class="form-control select-content" onchange="changeContIdByWorkerListOverlay()">
										<option value="-1">전체</option>
										<c:forEach var="cont" items="${contList}" varStatus="idx">
										<option value="${cont.id}">${cont.name}</option>
										</c:forEach>	  
									</select>	
								</div>
							</div>
							<table id="__workerTable" data-click-to-select="true" data-search="true" class="table table-bordered">
								<thead>
									<tr>
										<th data-field="select" class="text-center">선택</th>
										<th data-field="worker_info" class="text-center">근로자 정보</th>
										<th data-field="beacon_text" class="text-center" data-sortable="true">배정여부</th>
									</tr>
								</thead>
							</table>					    	
						</div>			
				    </div> <!-- tab-content -->
				 </div>	<!-- #sub-tab-box -->
      		</div>
   		</div>
  	</div>
</div>

<%@ include file="IncludeBottom_renewal.jsp"%>