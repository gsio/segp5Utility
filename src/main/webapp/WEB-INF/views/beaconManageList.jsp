<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

var CUR_SITE_ID = '${userLoginInfo.site_id}';
var CUR_CONT_ID = '${userLoginInfo.cont_id}';
var CUR_BEACON_LIST = [];
var CUR_WORKER_LIST = [];
var CUR_USER_LIST = [];

$(document).ready(function() {	
	getBeaconList();
});

function getBeaconList() {
	
	CUR_BEACON_LIST = [];
	
	$('#beaconTable').bootstrapTable();
	
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
		var battery_state = ${item.battery};
		var battery = "";
		
		if(type == 1) {
			type = '구형'; 
		}
		else {
			type = '신형';
		}
		
		if(battery_state == 1) {
			battery = '<b class="text-danger">부족</b>'; 
		} 
		else if(battery_state == 2) {
			battery = '<b style="color: #4bc91f !important;">보통</b>';
		}
		else if(battery_state == 3) {
			battery = '<b style="color: #2710d5 !important;">충분</b>';
		}
		else {
			battery = '<b>해당없음</b>';
		}
		
		if(name == '') {
			name = '-';
		}	
		var modify =
			'<div class="btn btn-default" onclick="overlayUserList('+ id +','+ idx +',\''+ name + '\','+ role + ')">' +
			'<span class="glyphicon glyphicon-pencil" style="cursor:pointer" ></span>' +
			'</div>' +
			'<div class="btn btn-danger" onclick="cancelBeacon('+ id + ',\'' + mac_addr + '\',\'' + name + '\',' + idx + ')">' +
			'<span class="glyphicon glyphicon-remove" style="cursor:pointer" ></span>' +
			'</div>';
		
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
			modify : modify
		}	
		CUR_BEACON_LIST.push(beacon);	
	</c:forEach>

	//alert("CUR_BEACON_LIST.length: " + CUR_BEACON_LIST.length);

	if(CUR_BEACON_LIST.length > 0) {
		$('#beaconTable').bootstrapTable('load', CUR_BEACON_LIST);		
	}
	else {
		$('#beaconTable').bootstrapTable('load', []);
	}
	resizeMainContent();
}

function getRoleText(role){
	if(role == 1)
		return '관리자';
	else if(role==2)
		return '근로자';
	else
		return '<b class="text-danger">미배정</b>';
}

function overlayUserList(beacon_id, idx, name, role){
	$('#__sel_beacon_id').val(beacon_id);	
	$('#overlay_no').html(idx);
	$('#overlay_name').html(name);
	$('#overlay_type').html(getRoleText(role));	
	$('#modal_userList').click();
	getWorkerList(CUR_SITE_ID, '-1');
	getUserList(CUR_SITE_ID, '');
	$('#contIdInput').val(-1);
}

function getWorkerList(site_id, cont_id){
	
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
			beacon_text = '<div class="label label-primary" style="font-size:2vh;">배정가능</div>';
		}
		else {
			beacon_text = '<div class="label label-success" style="font-size:2vh;">배정 [' + beacon_idx  + ']번</div>'
		}
		
		var select =
			'<div class="btn btn-default" onclick="selectUser(2,' + id + ')">' +
			'<span class="glyphicon glyphicon-ok" style="cursor:pointer" ></span>' +
			'</div>'
		
		var worker = {
			id : id,
			beacon_idx : beacon_idx,
			beacon_text : beacon_text,
			select : select,
			cont_name : cont_name,
			name : name,
			tname : tname,
			phone : phone
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
	
	CUR_USER_LIST = [];
	
	$('#__userTable').bootstrapTable();
	
	<c:forEach var="item" items="${userList}" varStatus="idx">	
		var id = '${item.id}';
		var beacon_idx = ${item.beacon_idx};
		var beacon_text = '';
		
		var cont_name = '${item.cont_name}';
		var name = '${item.name}';
		var role_name = '${item.role_name}';
		var phone = '${item.phone}';
		
		if(beacon_idx == 0) {
			beacon_text = '<div class="label label-primary" style="font-size:2vh;">배정가능</div>';
		}
		else {
			beacon_text = '<div class="label label-success" style="font-size:2vh;">배정 [' + beacon_idx  + ']번</div>'
		}
		
		var select =
			'<div class="btn btn-default" onclick="selectUser(1, \''+ id +'\')">' +
			'<span class="glyphicon glyphicon-ok" style="cursor:pointer" ></span>' +
			'</div>'
		
		var user = {
			id : id,
			beacon_idx : beacon_idx,
			beacon_text : beacon_text,
			select : select,
			cont_name : cont_name,
			name : name,
			role_name : role_name,
			phone : phone
		}	
		CUR_USER_LIST.push(user);	
		
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

function changeContIdByUserListOverlay(){
	var cont_id = $('#contIdInput').val();	
	getWorkerList(CUR_SITE_ID, cont_id);
}

function printBeaconList() {
	$('#printForm').submit();
}

</script>

<style>
	.th_11{
		background-color:#1b809e;
		line-height:1 !important;
		color:white;
	}
	
	.bootstrap-table .table:not(.table-condensed), .bootstrap-table .table:not(.table-condensed) > tbody > tr > th, .bootstrap-table .table:not(.table-condensed) > tfoot > tr > th, .bootstrap-table .table:not(.table-condensed) > thead > tr > td, .bootstrap-table .table:not(.table-condensed) > tbody > tr > td, .bootstrap-table .table:not(.table-condensed) > tfoot > tr > td {
    	padding: 2px;
	}
	
	#beaconTable {	    
    	font-size: 0.85em;
	}
	
	#beaconTable .btn{
		padding: 2px 7px;
	}
	
	#__userTable .btn{
		padding: 2px 7px;
	}
	
	#__userList td, #__workerList td {
		padding: 1.5vh;
		font-size: 2vh;
	}	
</style>

<input style="display:none" id="__sel_beacon_id" value="-1"></input>
<form id="registerForm" action="registerWorker" method="POST" ></form>

<div id="wrapper" class="text-left col-xs-12" style="margin-left: 1.5%;">

	<div class="list_title">Beacon 관리</div>
	<div class="col-xs-8" style="margin-left: 17%;">
		<div class="btn btn-default padding-right text-right" data-dismiss="modal" onclick="printBeaconList()"><span class="glyphicon glyphicon-print" ></span> 출 력</div>
	</div>
	
	<div class="col-xs-8" style="margin-left: 17%;">
		<table id="beaconTable"  data-click-to-select="true"  data-sort-name="[cont, rolecode]" data-filter-control="true" data-search="true" class="table table-bordered">
			<thead>
				<tr>	
					<th data-field="idx"  class="text-center" data-sortable="true">번호</th>			 
					<th data-field="type" class="text-center" data-sortable="true">종류</th>
					<th data-field="cont_name" class="text-center" data-sortable="true" data-filter-control="select" class="text-center">업체명</th>
					<th data-field="name" class="text-center" data-sortable="true">이 름</th>
					<th data-field="gubun_text" class="text-center" data-sortable="true">구분</th>	
					<th data-field="battery" class="text-center" data-sortable="true">배터리</th>
					<th data-field="last_scan_time" class="text-center" data-sortable="true">마지막 수집 시간</th>
					<th data-field="modify" class="text-center">관리</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<div id="form_group">
	<form id="registerForm" action="registerEquip" method="POST" ></form>
	<form id="viewForm" action="viewEquip" method="POST" >
		<input id="v_equip_id" type="hidden" name="id" />
	</form>
	<form id="printForm" action="printBeaconList" method="POST" >
		<input type="hidden" name="site_id" value="${userLoginInfo.site_id }" />
	</form>
</div>

<div style="display:none" id="modal_userList" data-toggle="modal" data-target="#user_modal"></div>
<div class="modal fade" id="user_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog" style="width:90%;margin-top:5px;"><div class="modal-content">
   	<div class="modal-header" style="text-align:center"> 
   		<b id="chart_title" style="font-size:22px;" >관리자/근로자 배정</b>
   	</div>
      <div class="modal-body">
        <div class="col-sm-12">	
        	<div id="overlay_info_text" class="" style="min-width:250px;display:inline-block;text-align:left;font-size:1.5vh; font-weight:bold;">
        		배정할 비콘 정보  
        		<table class="table table-bordered text-ceneter"  >
        			<thead><tr><th class="th_11">번 호</th><th class="th_11">이 름</th><th class="th_11">구 분</th></tr></thead>
        			<tbody><tr><td id="overlay_no" class="text-center"></td><td id="overlay_name" class="text-center"></td><td id="overlay_type" class="text-center"></td></tr></tbody>
        		</table>        		
        	</div>        
        	<br>
        	<br>
		    <div class="tab-content" style="height:750px;overflow:scroll"  >
				<ul class="nav nav-tabs">
					<li><a  href="#__userList" data-toggle="tab">관리자</a></li>
					<li class="active"><a href="#__workerList" data-toggle="tab">근로자</a></li>
				</ul>
				<div class="tab-content clearfix">		
					<div class="tab-pane" id="__userList">
						<table id="__userTable" data-click-to-select="true"   data-search="true" class="table table-bordered">
							<thead>
								<tr>		
								 	<th data-field="select" class="text-center">선택</th>			
								 	<th data-field="beacon_text" class="text-center"  data-sortable="true"  >배정여부</th>				
									<th data-field="cont_name" class="text-center" data-sortable="true"  data-filter-control="select" >업체명</th>
									<th data-field="name" class="text-center" data-sortable="true"   >이름</th>
									<th data-field="role_name" class="text-center">권한</th>
									<th data-field="phone" class="text-center">H.P</th>									
								</tr>
							</thead>
						</table>
					</div>
					<div class="tab-pane active" id="__workerList">
					<br>
						<b>업체 선택 : </b><select id="contIdInput" class="form-control" onchange="changeContIdByUserListOverlay()" style="width:50%;">
				 			<option value="-1">--전체--</option>
							<c:forEach var="cont" items="${contList}" varStatus="idx">
				 		 	   <option value="${cont.id}">${cont.name}</option>
							</c:forEach>
						</select>
				    	<table id="__workerTable" data-click-to-select="true"   data-search="true" class="table table-bordered">
							<thead>
								<tr>
									 <th data-field="select" class="text-center">선택</th>	
									 <th data-field="beacon_text" class="text-center"  data-sortable="true">배정여부</th>							
									 <th data-field="cont_name" class="text-center">업체명</th>
									 <th data-field="name" class="text-center" data-sortable="true" >이름</th>
									 <th data-field="tname" class="text-center" data-sortable="true" >직종</th>
									 <th data-field="phone" class="text-center">H.P</th>									 
								 </tr>
							</thead>
						</table>
					</div>
				  </div>				
		    </div><!-- tap-content -->
		 </div>		 
		
		 <div class="text-right" style="padding-right:5px">
		    <div id="__close_btn" class="btn btn-default margin-top" data-dismiss="modal" ><span class="glyphicon glyphicon-remove" ></span> 닫기</div>
		 </div>
		  
      </div>     
    </div>
  </div>
</div>         
   
<%@ include file="IncludeBottom.jsp"%>