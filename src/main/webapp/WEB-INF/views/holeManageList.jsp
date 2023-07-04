<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

var CUR_SITE_ID = '${userLoginInfo.site_id}';
var CUR_CONT_ID = '${userLoginInfo.cont_id}';
var CUR_HOLE_LIST = [];
var CUR_WORKER_LIST = [];
var CUR_SECTION_LIST = [];

$(document).ready(function() {	
	getHoleList();
});


function getHoleList() {
	
	CUR_HOLE_LIST = [];
	
	$('#holeTable').bootstrapTable();
	
	<c:forEach var="item" items="${holeList}" varStatus="idx">		
		var id = ${item.id};
		var idx = ${item.hole_idx};
		var section_name = '${item.section_name}';
		var name = '${item.hole_name}';
		var section = ${item.section}; 
		var gubun_text = '';
		if(section > 0) {
			gubun_text = '<b>배정 (#'+section+'번 구역)</b>'; 
		}
		else {
			gubun_text = '<b class="text-danger">미배정</b>'
		}
		var last_scan_time = '${item.last_scan_time}';
		last_scan_time = last_scan_time.replace(".0", "");
		var battery_state = ${item.battery};
		var battery = "";
		
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
		
		var modify =
			'<div class="btn" title="이름변경" style="background-color: #2dc74e;" onclick="updateHoleName('+ id + ')"><i class="far fa-edit"></i></div>' +
			'<div class="btn btn-default" title="구역할당" onclick="overlaySectionList('+ id +')">' +
			'<span class="glyphicon glyphicon-pencil" style="cursor:pointer" ></span>' +
			'</div>' +
			'<div class="btn btn-danger" title="할당삭제" onclick="cancelHoleBeacon('+ id + ')">' +
			'<span class="glyphicon glyphicon-remove" style="cursor:pointer" ></span>' +
			'</div>'; 
			
		
		var hole = {
			id : id,
			idx : idx,
			section_name : section_name,		
			name : name,
			gubun_text : gubun_text,
			battery: battery,
			last_scan_time: last_scan_time,
			modify : modify
		}	
		
		CUR_HOLE_LIST.push(hole);	
		
	</c:forEach>

	if(CUR_HOLE_LIST.length > 0) {
		$('#holeTable').bootstrapTable('load', CUR_HOLE_LIST);		
	}
	else {
		$('#holeTable').bootstrapTable('load', []);
	}
	resizeMainContent();
}

function overlaySectionList(hole_id) {
	$('#modal_sectionList').click();
	getHoleSectionList(hole_id);
}

function getHoleSectionList(hole_id) {
	
	CUR_SECTION_LIST = [];
	
	$('#sectionTable').bootstrapTable();
	
	<c:forEach var="item" items="${sectionList}" varStatus="idx">	
		var id = ${item.section_id};
		var section_name = '${item.section_name}';
		var hole_text = '';
		var section_type = ${item.section_type};		
		var hole_idx = ${item.hole_idx};
		var section_name = '${item.section_name}';
		var section = ${item.section};
		
		if(hole_idx == 0) {
			hole_text = '<div class="label label-primary" style="font-size:2vh;">배정가능</div>';
		}
		else {
			hole_text = '<div class="label label-success" style="font-size:2vh;">배정 [' + hole_idx  + '번 개구부 비콘]</div>'
		}
		
		var select =
			'<div class="btn btn-default" onclick="selectHoleSection('+hole_id+','+section+')">' +
				'<span class="glyphicon glyphicon-ok" style="cursor:pointer" ></span>' +
			'</div>'
		
		if(section_type == 1) {
			section_type = '밀폐수조';
		}
		else {
			section_type = '일반구역';			
			section = '-';
		}
		
		var section = {
			select : select,
			id : id,
			section_name : section_name,
			hole_text : hole_text,	
			section_type : section_type,
			section_name : section_name,
			section : section
		}	
		CUR_SECTION_LIST.push(section);	
		
	</c:forEach>
	
	if(CUR_SECTION_LIST.length > 0) {
		$('#sectionTable').bootstrapTable('load', CUR_SECTION_LIST);		
	}
	else {
		$('#sectionTable').bootstrapTable('load', []);
	}
}

function selectHoleSection(hole_id, section) {
	var input; 		
	input = confirm(section + '번 구역에 개구부 비콘을 배정하시겠습니까?');
	if(input){		
		 $.ajax({
			type : "POST",
			url : "./hole/update",
			data : {
				id : hole_id,
				section : section
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
		alert('취소하었습니다');
	}
}

function cancelHoleBeacon(hole_id) {
	var input; 		
	input = confirm(hole_id + '번 개구부 비콘 할당을 취소하시겠습니까?');
	if(input){		
		 $.ajax({
			type : "POST",
			url : "./hole/unAssign",
			data : {
				id : hole_id
			},
			cache : false,
			async : false,
			success : function(json,status) {
				alert('배정 삭제가 완료되었습니다.');		
				window.location.reload();
			}
		}); 		
	}
	else{			
		alert('취소되었습니다');
	}
}

function updateHoleName(hole_id, name){
	var name;
	name = prompt('변경할 이름을 적어주세요');   
	
	$.ajax({
		type : "POST",
		url : "./hole/name/update",            
		data : {
			id : hole_id, 
			name : name
		},
		async: true,
		cache : false,         
		success : function(json, status){
			alert('이름 변경 완료되었습니다.');		
			window.location.reload();
		}
	});
}

</script>

<style>
	.th_11{
		background-color:#1b809e;
		line-height:1 !important;
		color:white;
	}
	
	.bootstrap-table .table:not(.table-condensed), .bootstrap-table .table:not(.table-condensed) > tbody > tr > th, .bootstrap-table .table:not(.table-condensed) > tfoot > tr > th, .bootstrap-table .table:not(.table-condensed) > thead > tr > td, .bootstrap-table .table:not(.table-condensed) > tbody > tr > td, .bootstrap-table .table:not(.table-condensed) > tfoot > tr > td {
	 	padding: 4px 8px;
	    font-size: 1.5vh;
	}
	
	#holeTable .btn{
		padding: 4px 8px;
		   font-size: 1.5vh;
	}
	
	#sectionTable .btn{
		padding: 8px 16px;
		font-size: 2vh;
	}
	
	#__userList td, #__workerList td {
		padding: 1.5vh;
		font-size: 2vh;
	}	
</style>

<input style="display:none" id="__sel_nfc_id" value="-1"></input>
<form id="registerForm" action="registerWorker" method="POST" ></form>

<div id="wrapper" class="text-left col-xs-12" style="margin-left: 1.5%;">

	<div class="list_title">개구부 관리</div>
	<div class="col-xs-8" style="margin-left: 17%;">
		<!-- <div class="btn btn-default padding-right text-right" data-dismiss="modal" onclick="printnfcList()"><span class="glyphicon glyphicon-print" ></span> 출 력</div> -->
	</div>
	
	<div class="col-xs-8" style="margin-left: 17%;">
		<table id="holeTable"  data-click-to-select="true"  data-sort-name="[cont, rolecode]" data-filter-control="true" data-search="true" class="table table-bordered">
			<thead>
				<tr>	
					<th data-field="idx" class="text-center" data-sortable="true">번호</th>			 
					<th data-field="section_name" class="text-center" data-sortable="true">위 치</th>	
					<th data-field="gubun_text" class="text-center" data-sortable="true" data-filter-control="select">구분 (구역번호)</th>	
					<th data-field="name" class="text-center" data-sortable="true">이 름</th>
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
	<form id="printForm" action="printnfcList" method="POST" >
		<input type="hidden" name="site_id" value="${userLoginInfo.site_id }" />
	</form>
</div>


<div style="display:none" id="modal_sectionList" data-toggle="modal" data-target="#section_modal"></div>
<div class="modal fade" id="section_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:90%; margin-top:5px;">
		<div class="modal-content">
   			<div class="modal-header" style="text-align:center"> 
   				<b id="chart_title" style="font-size:22px;">개구부 구역 배정</b>
   			</div>
			<div class="modal-body">
        		<div class="col-sm-12">	
					<div class="tab-content" style="height:750px;overflow:scroll"  >
						<table id="sectionTable" data-click-to-select="true" data-search="true" class="table table-bordered">
							<thead>
								<tr>		
								 	<th data-field="select" class="text-center">선택</th>			
								 	<th data-field="section_type" class="text-center" data-sortable="true" data-filter-control="select" >분류</th>
								 	<th data-field="section_name" class="text-center" data-sortable="true" data-filter-control="select" >구역명</th>				
									<th data-field="section" class="text-center" data-sortable="true" data-filter-control="select" >구역번호</th>	
								 	<th data-field="hole_text" class="text-center" data-sortable="true" >배정여부</th>
								</tr>
							</thead>
						</table>
				
		    		</div><!-- tap-content -->
				 </div>		 
		
				 <div class="text-right" style="padding-right:5px">
					<div id="__close_btn" class="btn btn-default margin-top" data-dismiss="modal" ><span class="glyphicon glyphicon-remove" ></span> 닫기</div>
				 </div>
		  
      		</div><!-- modal-body-->     
    	</div>
  	</div>
</div>      

   
<%@ include file="IncludeBottom.jsp"%>
 