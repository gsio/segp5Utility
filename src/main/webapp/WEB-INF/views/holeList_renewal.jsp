<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script type="text/javascript" src="js/device/hole/list.js"></script>
<link rel="stylesheet" href="css/device/hole/list.css">

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
		
		var allocate_hole = '<div class="btn icon-primary" onclick="overlaySectionList('+ id +')"><i class="fa-solid fa-clipboard"></i></div>';
		var allocate_delete_hole = '<div class="btn icon-danger" onclick="cancelHoleBeacon('+ id + ')"><i class="fa-solid fa-trash"></i></div>';
		
		var modify =
			'<div style="float: left; width: 50%;" class="btn icon-primary" onclick="overlaySectionList('+ id +')"><i class="fa-solid fa-clipboard"></i></div>' +
			'<div style="float: left; width: 50%;" class="btn icon-danger" onclick="cancelHoleBeacon('+ id + ')"><i class="fa-solid fa-trash"></i></div>';
		
		var hole = {
			id : id,
			idx : idx,
			section_name : section_name,		
			name : name,
			gubun_text : gubun_text,
			allocate_section: allocate_section,
			battery: battery,
			last_scan_time: last_scan_time,
			allocate_hole: allocate_hole,
			allocate_delete_hole: allocate_delete_hole,
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
			hole_text =  '<div class="label" style="color:#4bc91f;">배정가능</div>';
		}
		else {
			hole_text = '<div class="label" style="color:#ff3547;">배정 [' + hole_idx  + ']번</div>'
		}
		
		var select = '<div class="btn btn-primary" onclick="selectHoleSection('+hole_id+','+section+')"><i class="fa-solid fa-check"></i></div>';
		
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
			hole_text : hole_text,	
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

</script>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">개구부 관리</div>
	
	<input id="tar_site_id" type="hidden" name="site_id" value="${userLoginInfo.site_id}"/>  
	<div class="content_button_box content-item" >
<!-- 		<div class="btn btn-primary" onclick=""><i class="fa-solid fa-print"></i> 출력</div> -->
	</div>
	
	<div class="content_data_count_box content-item">
		총 ${holeList.size()} 개
	</div>

	<div class="content_table_box content-item">
		<table id="holeTable" data-search="true" data-pagination="true"
				data-page-size="25" data-page-list="[10, 25, 50, 100, All]" data-filter-control="true" 
				class="table table-bordered col-xs-12 table-hover table-striped" >
			<thead>
				<tr>	
					<th data-field="idx" class="text-center" data-sortable="true">번호</th>			 
					<th data-field="allocate_section" class="text-center" data-sortable="true">배정현황</th>	
					<th data-field="battery" class="text-center show-web" data-sortable="true">배터리</th>
					<th data-field="last_scan_time" class="text-center show-web" data-sortable="true">마지막 수집 시간</th>
					<th data-field="allocate_hole" class="text-center show-web">구역배정</th>
					<th data-field="allocate_delete_hole" class="text-center show-web">배정삭제</th>
					<th data-field="modify" class="text-center show-mobile">관리</th>
				</tr>
			</thead>
		</table>
	</div>	
</div> <!-- content-wrapper END -->

<div id="form_group">
	<form id="printForm" action="printHoleList" method="POST" >
		<input type="hidden" name="site_id" value="${userLoginInfo.site_id }"/>
	</form>
</div>

<div style="display:none" id="modal_sectionList" data-toggle="modal" data-target="#section_modal"></div>
<div class="modal fade" id="section_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
  		<div class="modal-content">
	   		<div class="modal-header" style="font-size: 1.5em;"> 
	   			<div>
   					개구부 구역 배정
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
								 	<th data-field="hole_text" class="text-center">배정여부</th>
								</tr>
							</thead>
						</table>
				 	</div>	<!-- #sub-tab-box -->
      			</div>
   			</div>
  		</div>
	</div>
</div>

<%@ include file="IncludeBottom_renewal.jsp"%>