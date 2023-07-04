<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

var CUR_SITE_ID = '${userLoginInfo.site_id}';
var CUR_CONT_ID = '${userLoginInfo.cont_id}';
var CUR_NFC_LIST = [];
var CUR_WORKER_LIST = [];
var CUR_USER_LIST = [];

$(document).ready(function() {	
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
		/*
		var modify =
			'<div class="btn btn-default" onclick="overlayUserList('+ id +','+ idx +',\''+ name + '\','+ role + ')">' +
			'<span class="glyphicon glyphicon-pencil" style="cursor:pointer" ></span>' +
			'</div>' +
			'<div class="btn btn-danger" onclick="cancelNfc('+ id + ',\'' + serial_number + '\',\'' + name + '\',' + idx + ')">' +
			'<span class="glyphicon glyphicon-remove" style="cursor:pointer" ></span>' +
			'</div>';
		*/
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

function overlayUserList(nfc_id, idx, name, role){
	$('#__sel_nfc_id').val(nfc_id);	
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
		var nfc_idx = ${item.nfc_idx};
		var nfc_text = '';
		var sort_cont_id = ${item.cont_id};
		var cont_name = '${item.cont_name}';
		var name = '${item.name}';
		var tname = '${item.t_name}';
		var phone = '${item.phone}';
		
		if(nfc_idx == 0) {
			nfc_text = '<div class="label label-primary" style="font-size:2vh;">배정가능</div>';
		}
		else {
			nfc_text = '<div class="label label-success" style="font-size:2vh;">배정 [' + nfc_idx  + ']번</div>'
		}
		
		var select =
			'<div class="btn btn-default" onclick="selectUser(2,' + id + ')">' +
			'<span class="glyphicon glyphicon-ok" style="cursor:pointer" ></span>' +
			'</div>'
		
		var worker = {
			id : id,
			nfc_idx : nfc_idx,
			nfc_text : nfc_text,
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
		var nfc_idx = ${item.nfc_idx};
		var nfc_text = '';
		
		var cont_name = '${item.cont_name}';
		var name = '${item.name}';
		var role_name = '${item.role_name}';
		var phone = '${item.phone}';
		
		if(nfc_idx == 0) {
			nfc_text = '<div class="label label-primary" style="font-size:2vh;">배정가능</div>';
		}
		else {
			nfc_text = '<div class="label label-success" style="font-size:2vh;">배정 [' + nfc_idx  + ']번</div>'
		}
		
		var select =
			'<div class="btn btn-default" onclick="selectUser(1, \''+ id +'\')">' +
			'<span class="glyphicon glyphicon-ok" style="cursor:pointer" ></span>' +
			'</div>'
		
		var user = {
			id : id,
			nfc_idx : nfc_idx,
			nfc_text : nfc_text,
			select : select,
			cont_name : cont_name,
			name : name,
			role_name : role_name,
			phone : phone
		}	
		CUR_USER_LIST.push(user);	
		
	</c:forEach>
	
	if(CUR_USER_LIST.length > 0) {
		$('#__userTable').bootstrapTable('load', CUR_USER_LIST);		
	}
	else {
		$('#__userTable').bootstrapTable('load', []);
	}
	
}

function cancelNfc(nfc_id, serial_number, name, nfc_idx){	
	var input = confirm(name + '님을 ' + nfc_idx + '번 NFC에서 삭제하시겠습니까?');	
	if(input) {
		$('#__sel_nfc_id').val(nfc_id);
		deletenfcAllocate(nfc_id, serial_number, nfc_idx);
	} else {
		alert(nfc_idx + '번 NFC 할당 삭제 취소');
	}	
}

function deletenfcAllocate(nfc_id, serial_number, idx) {
	 $.ajax({
		type : "POST",
		url : "./nfc/unAssign",
		data : {
			id : nfc_id,
			serial_number : serial_number,
			idx : idx
		},
		cache : false,
		async : false,
		success : function(json,status) {
			alert('NFC 할당 삭제 완료');
			window.location.reload();
		}
	});
}

function selectUser(role, u_id){ 
	var nfc_id = $('#__sel_nfc_id').val();
	var nfc_idx = $('#overlay_no').text();
	var result;
	
	if(nfc_id == -1) {
		return;
	}

	if(u_id != ""){
		result = checkDuplicationnfcUser(nfc_id, u_id, role);
	}
	else{
		result = false;
	}
	
	if(result == 'true' || result == true){
		alert('이미 NFC에 배정되어 있는 작업자입니다.');
	}
	
	else{		
		var input; 
		if(u_id == ''){
			input = confirm(nfc_idx + '번 NFC 할당을 취소하시겠습니까?');
		}else{
			input = confirm(nfc_idx + '번 NFC을 배정하시겠습니까?');
		}		
		if(input){
			
			 $.ajax({
				type : "POST",
				url : "./nfc/update",
				data : {
					id : nfc_id,
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

function checkDuplicationnfcUser(nfc_id, u_id, role){	
	var result = false;
	for(var i = 0 ; i < CUR_NFC_LIST.length ; i ++ ){
		var u_id_tmp = CUR_NFC_LIST[i].u_id;		
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

function printNfcList() {
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
    	padding: 7px;
	}
	
	#nfcTable .btn{
		padding: 2px 7px;
	}
	
	#__userTable .btn{
		padding: 2px 7px;
	}
	
	#__userList td, #__workerList td {
		padding: 1.5vh;
		font-size: 2vh;
	}	
	
	#__manage_list {
		margin-left: 17%;
	    text-align: right;
	    font-size: 1.5vh;
	    font-weight: bold;
	    color: #77
	}
	
</style>

<input style="display:none" id="__sel_nfc_id" value="-1"></input>
<form id="registerForm" action="registerWorker" method="POST" ></form>

<div id="wrapper" class="text-left col-xs-12" style="margin-left: 1.5%;">

	<div class="list_title">NFC 배정 확인</div>

	<div id="__manage_list" class="col-xs-8">
		* 비콘 배정 번호에 따라 <span style="color:#ff3547 ">자동</span>으로 배정이 됩니다. <span style="color:#2710d5">해당하는 NFC 번호가 없을 경우 관리자에 연락</span>주시길 바랍니다.
	</div>
	
	<div class="col-xs-8" style="margin-left: 17%;">
		<table id="nfcTable"  data-click-to-select="true"  data-sort-name="[cont, rolecode]" data-filter-control="true" data-search="true" class="table table-bordered">
			<thead>
				<tr>	
					<th data-field="idx"  class="text-center" data-sortable="true">번호</th>			 
					<th data-field="cont_name" class="text-center" data-sortable="true"  data-filter-control="select">업체명</th>
					<th data-field="name" class="text-center" data-sortable="true">이 름</th>
					<th data-field="gubun_text" class="text-center" data-sortable="true">구분</th>	
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
	<form id="printForm" action="printnfcList" method="POST" >
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
        		배정할 NFC 정보  
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
								 	<th data-field="nfc_text" class="text-center"  data-sortable="true"  >배정여부</th>				
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
									 <th data-field="nfc_text" class="text-center"  data-sortable="true"  >배정여부</th>							
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
 