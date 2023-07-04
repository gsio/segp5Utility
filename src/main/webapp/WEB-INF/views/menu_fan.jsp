<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

var CUR_SITE_ID = '${userLoginInfo.site_id}';
var CUR_CONT_ID = '${userLoginInfo.cont_id}';
var CUR_FAN_LIST = [];
var CUR_WORKER_LIST = [];
var CUR_SECTION_LIST = [];

$(document).ready(function() {	
	getFanList();
});
function getFanList() {
	
	CUR_FAN_LIST = [];
	
	$('#fanTable').bootstrapTable();
	
	<c:forEach var="item" items="${fanList}" varStatus="idx">		
		var id = ${item.id};
		var fan_name = '${item.fan_name}';
		var idx = ${item.fan_idx};
		var type = ${item.type};
		var section_name = '${item.section_name}';
		var section = ${item.section}; 
		var gubun_text = '';
		
		if(section > 0) {
			gubun_text = '<b>배정 (#'+section+'번 구역)</b>'; 
		}
		else {
			gubun_text = '<b class="text-danger">미배정</b>';			
		}
		
		var fan_type = '';
		if(type == 0) {
			fan_type = '<span style="color: #dc3545 !important; font-weight:600;">급기</span>';
		}
		else {
			fan_type = '<span style="color: #2710d5 !important; font-weight:600;">배기</span>';
		}
		
		var last_scan_time = '${item.last_scan_time}';
		last_scan_time = last_scan_time.replace(".0", "");
		var battery_state = ${item.battery};
		var battery = "";
		
		if(battery_state == 1) { 
			battery = '<span style="color: #dc3545 !important; font-weight:600;"><i class="fa-solid fa-battery-quarter"></i> 부족</span>';
		} 
		else if(battery_state == 2) {
			battery = '<span style="color: #4bc91f !important; font-weight:600;"><i class="fa-solid fa-battery-half"></i> 보통</span>';
		}
		else if(battery_state == 3) {
			battery = '<span style="color: #2710d5 !important; font-weight:600;"><i class="fa-solid fa-battery-full"></i> 충분</span>';
		}
		else {			
			battery = '<span style="font-weight:600;"><i class="fa-solid fa-question"></i></span>';
		}
		
		var allocate_section = "";		
		
		if(section > 0) {
			if(section_name != "") {
				allocate_section += '<div class="section"><span class="section"><i class="fa-regular fa-square"></i> #'+section+'</span> - '+ section_name+'</div>';
			}
			else {
				allocate_section += '<div class="section"><span class="section"><i class="fa-regular fa-square"></i> #'+section+'</span></div>';
			}
		}		
		
		var allocate_fan_name = '<div class="btn icon-default" onclick="updateFanName('+ id +')"><i class="fa-regular fa-pen-to-square"></i></div>';
		var allocate_fan = '<div class="btn icon-primary" onclick="overlaySectionList('+ id +')"><i class="fa-solid fa-clipboard"></i></div>';
		var allocate_delete_fan = '<div class="btn icon-danger" onclick="cancelFanBeacon('+ id + ')"><i class="fa-solid fa-trash"></i></div>';
		
		var modify =
			'<div style="float: left; width: 33%;" class="btn icon-default" onclick="updateFanName('+ id +')"><i class="fa-regular fa-pen-to-square"></i></div>' + 
			'<div style="float: left; width: 33%;" class="btn icon-primary" onclick="overlaySectionList('+ id +')"><i class="fa-solid fa-clipboard"></i></div>' +
			'<div style="float: left; width: 33%;" class="btn icon-danger" onclick="cancelFanBeacon('+ id + ')"><i class="fa-solid fa-trash"></i></div>';
		
		var fan = {
			id : id,			
			fan_type: fan_type,
			fan_name: fan_name,
			idx : idx,
			section_name : section_name,		
			gubun_text : gubun_text,
			allocate_section: allocate_section,
			battery: battery,
			last_scan_time: last_scan_time,
			allocate_fan_name: allocate_fan_name,
			allocate_fan: allocate_fan,
			allocate_delete_fan: allocate_delete_fan,
			modify : modify
		}	
		
		CUR_FAN_LIST.push(fan);	
		
	</c:forEach>

	if(CUR_FAN_LIST.length > 0) {
		$('#fanTable').bootstrapTable('load', CUR_FAN_LIST);		
	}
	else {
		$('#fanTable').bootstrapTable('load', []);
	}
}

function overlaySectionList(fan_id) {
	$('#modal_sectionList').click();
	getFanSectionList(fan_id);
}

function getFanSectionList(fan_id) {
	
	CUR_SECTION_LIST = [];
	
	$('#sectionTable').bootstrapTable();
	
	<c:forEach var="item" items="${sectionList}" varStatus="idx">	

		var id = ${item.section_id};
		var section_name = '${item.section_name}';
		var fan_text = '';
		var section_type = ${item.section_type};		
		var fan_idx = '${item.fan_idx_list}';
		var section_name = '${item.section_name}';
		var section = ${item.section};
		
		if(fan_idx == 0) {
			fan_text =  '<div class="label" style="color:#4bc91f;">-</div>';
		}
		else {
		
			fan_text = '<div class="label" style="color:#ff3547;">' + fan_idx  + ' 번</div>'
		}
		
		var select = '<div class="btn btn-primary" onclick="selectFanSection('+fan_id+','+section+')"><i class="fa-solid fa-check"></i></div>';
		
		if(section_type == 1) {
			section_type = '밀폐수조';
		}
		else {
			section_type = '일반구역';			
			section = '-';
		}
		
		var section_info = "";		
		if(section_name != "") {
			section_info += '<div class="section"><span><i class="fa-regular fa-square"></i> #'+section+'</span> - '+ section_name+'</div>';
		}
		else {
			section_info += '<div class="section"><span><i class="fa-regular fa-square"></i> #'+section+'</span></div>';
		}
		
		var section = {
			select : select,
			id : id,
			section_name : section_name,
			fan_text : fan_text,	
			section_info : section_info,
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

function selectFanSection(fan_id, section) {
	var input; 		
	input = confirm(section + '번 구역에 급배기 비콘을 배정하시겠습니까?');
	if(input){		
		 $.ajax({
			type : "POST",
			url : "./device/fan/update",
			data : {
				id : fan_id,
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

function cancelFanBeacon(fan_id) {
	var input; 		
	input = confirm(fan_id + '번 급배기 할당을 취소하시겠습니까?');
	if(input){		
		 $.ajax({
			type : "POST",
			url : "./device/fan/delete",
			data : {
				id : fan_id
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

function updateFanName(fan_id){
	var name;
	name = prompt('변경할 이름을 적어주세요');   
	
	$.ajax({
		type : "POST",
		url : "./device/fan/name/update",            
		data : {
			id : fan_id, 
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

.section {
	color: #333;
	font-size: 0.9em;
 	font-weight: 600;
}

.section > span{
	color: #5cb85c;
    font-weight: 600;	
}

#section_modal .label {
	font-size:0.8em;
	font-weight: 600;
}

#overlay_info_text {
	min-width: 100%;
	font-size: 1em;
    font-weight: bold;
    text-align: center;
}

#section_modal #sub-tab-box {
	height: 750px;
    overflow: scroll;
    display: flex;
    flex-direction: column;
    flex-wrap: nowrap;
    justify-content: flex-start;
    align-items: stretch;
    align-content: center;
}

#section_modal #sub-tab-box ul {
    list-style: none;
    padding-left: 0px;
    display: flex;
    flex-direction: row;
    align-items: stretch;
    align-content: center;
    justify-content: space-between;
}

#section_modal #sub-tab-box li {	
    flex: 0 0 49%;	
    background: white;
    height: 40px;
    line-height: 40px;
    text-align: center;
    position: relative;    
}

#section_modal #sub-tab-box .nav>li a {
	color: #666;
	width: 100%;
    height: 100% !important;
    max-height: 40px !important;     
    border-radius: 5px 5px 0 0;
	display: block;
	text-decoration: none;
	background: #e6e8e6;
	border-bottom: 1px solid #D4D4D4;
	font-size: 1em;	
	box-shadow: 0 0 12px 0 rgb(0 0 0/ 10%);
}

#section_modal #sub-tab-box .nav>li a:hover {
	color: black;
	font-weight: 600;
	background: white;
}

#section_modal #sub-tab-box .nav li>.active {
	background: white;
	color: red;
	font-weight: 600;
	border: 1px solid #FF3547;
}

#section_modal #sub-tab-box .nav li>.active:after {
	position: absolute;
    left: 5px;
    top: 5px;
    content: "";
    display: block;
    width: 3px;
    height: 30px;
    border-radius: 15px;
    background-color: #007bff;
}

#section_modal .label {
	font-size:0.8em;
	font-weight: 600;
}

#section_modal .content-item {
	display: flex;
    margin-top: 10px;
}

#section_modal .content_selete_box .select-content {
	max-width: 250px !important;   
}

</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">급배기 관리</div>
	
	<input id="tar_site_id" type="hidden" name="site_id" value="${userLoginInfo.site_id}"/>  
	<div class="content_button_box content-item" >
<!-- 		<div class="btn btn-primary" onclick=""><i class="fa-solid fa-print"></i> 출력</div> -->
	</div>
	
	<div class="content_data_count_box content-item">
		총 ${fanList.size()} 개
	</div>

	<div class="content_table_box content-item">
		<table id="fanTable"  data-click-to-select="true"  data-sort-name="[cont, rolecode]" data-filter-control="true" data-search="true" class="table table-bordered">
			<thead>
				<tr>	
					<th data-field="idx" class="text-center show-web" data-sortable="true">번호</th>
					<th data-field="fan_type" class="text-center" data-sortable="true">종류</th>
					<th data-field="fan_name" class="text-center" data-sortable="true">별칭</th>				 
					<th data-field="allocate_section" class="text-center" data-sortable="true">배정현황</th>	
					<th data-field="battery" class="text-center show-web" data-sortable="true">배터리</th>
					<th data-field="last_scan_time" class="text-center show-web" data-sortable="true">마지막 수집 시간</th>					
					<th data-field="allocate_fan_name" class="text-center show-web">이름 설정</th>
					<th data-field="allocate_fan" class="text-center show-web">구역배정</th>
					<th data-field="allocate_delete_fan" class="text-center show-web">배정삭제</th>
					<th data-field="modify" class="text-center show-mobile">관리</th>
				</tr>
			</thead>
		</table>
	</div>	
</div> <!-- content-wrapper END -->

<div id="form_group">
	<form id="printForm" action="printFanList" method="POST" >
		<input type="hidden" name="site_id" value="${userLoginInfo.site_id }"/>
	</form>
</div>

<div style="display:none" id="modal_sectionList" data-toggle="modal" data-target="#section_modal"></div>
<div class="modal fade" id="section_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
  		<div class="modal-content">
	   		<div class="modal-header" style="font-size: 1.5em;"> 
	   			<div>
   					급배기 구역 배정
   				</div>    
				<div>
					<div id="__close_btn" class="btn btn-danger margin-top" data-dismiss="modal"><i class="fa-solid fa-xmark"></i> 닫기</div>
				</div>
			</div>
			<div class="modal-body">
		        <div class="col-sm-12">
				    <div id="sub-tab-box" class="tab-content nav nav-tabs">
						<table id="sectionTable" data-click-to-select="true" data-search="true" class="table table-bordered">
							<thead>
								<tr>		
								 	<th data-field="select" class="text-center">선택</th>			
								 	<th data-field="section_info" class="text-center">구역정보</th>
								 	<th data-field="fan_text" class="text-center">배정여부</th>
								</tr>
							</thead>
						</table>
				 	</div>	<!-- #sub-tab-box -->
      			</div>
   			</div>
  		</div>
	</div>
</div>

<%@ include file="IncludeBottom.jsp"%>