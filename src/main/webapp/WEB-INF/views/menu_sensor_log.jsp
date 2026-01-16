<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

var CUR_SITE_ID = '${userLoginInfo.site_id}';
var CUR_SECTION = -1;

$(document).ready(function() {
	
	$('#input_date').val(getTodayDate());
	
	$("#input_date").datepicker({
		yearRange: '-1:+1',    
    	showSecond: true,
    	onClose: function (selectedDate) {    
    		CUR_SECTION = -1;
    		$('#search_section').val(CUR_SECTION).prop("selected", true);
    		//getSensorList();
    	}
    }).attr('readonly','readonly');
	
	setInitSectionSelect();
	//getSensorList();
	
});


function printSensorList(){
	$('#date').val($('#input_date').val());
	$('#section').val($('#search_section').val());
	$('#printSensor').submit();
}

// 날짜 변경 시
function getSensorList() {	
	
    $('#sensorTable').bootstrapTable('destroy')
    	.bootstrapTable()
      	.bootstrapTable('showLoading')	
	
	$.ajax({
		type: "GET",
		//url: 'https://segp5.gsil.net:11243/getSensorLogList',
		url: 'https://segp5.gsil.net:11243/getSensorLogList',
		data: {
			input_date: $('#input_date').val(),
			input_section: $('#search_section').val()
		},
		traditional: true,
		async: true,
		cache: false,
		success: function (data, status) {
			var list = JSON.parse(JSON.stringify(data)).sensorList;
			var section = JSON.parse(JSON.stringify(data)).sectionList;
			setInitSectionSelect();
			if(section != null) {
				for(var s=0; s < section.length; s++) {			
					var optionHtml = '<option value="'+section[s].id+'">#'+ section[s].id + " - " +  section[s].name+'</option>';
					$('#search_section').append(optionHtml);
				}
				$('#search_section').val(CUR_SECTION).prop("selected", true);
			}
			if(list != null) {
				//console.log("[DATA COUNT]: " + list.length);
				if(list.length >= 0) {
					for(var i=0; i < list.length; i++) {
						list[i].no = i+1;
						list[i].o2 = list[i].o2 + "%";
						list[i].co2 = list[i].co2 + "ppm";
						list[i].co = list[i].co + "ppm";
						list[i].h2s = list[i].h2s + "ppm";
						list[i].ch4 = list[i].ch4 + "%";
						
						list[i].sensor_info = 
							'<div class="text-left sensor-box">' + 
							'<div> o2: '+list[i].o2+'</div>' +
							'<div> co2: '+list[i].co2+'</div>' +
							'<div> co: '+list[i].co+'</div>' +
							'<div> h2s: '+list[i].h2s+'</div>' +
							'<div> lel: '+list[i].ch4+'</div>' +
							'</div>';
							
						if(list[i].time_diff_min > 0) {
							list[i].time_diff_min = '<div class="badge badge-danger" style="padding:5px;">'+list[i].time_diff_min+'분</div>';
						}
						else {
							list[i].time_diff_min = '<div class="badge badge-success" style="padding:5px;">'+list[i].time_diff_min+'분</div>';
						}						
					
						if(list[i].section_name != "") {
							list[i].section_info = '<div class="section"><span><i class="fa-regular fa-square"></i> #'+list[i].alias+'</span> - '+ list[i].section_name+'</div>';
						}
						else {
							list[i].section_info = '<div class="section"><span><i class="fa-regular fa-square"></i> #'+list[i].alias+'</span></div>';
						}						
					}				
					$('#sensorTable').bootstrapTable('load', list );	
					$('#sensorTable').bootstrapTable('hideLoading');
				}
			}
			else{
				$('#sensorTable').bootstrapTable('load', [] );
				$('#sensorTable').bootstrapTable('hideLoading');
			}			
		}
	});
}

function changeSection() {	
	CUR_SECTION = $('#search_section').val();
	getSensorList();
}

function setInitSectionSelect() {
	$('#search_section').empty();
	var option = '<option value="-1">전체</option>';
	$('#search_section').append(option);
}

function loadingTemplate() {
	 return '<i class="fa fa-spinner fa-spin fa-fw fa-2x"></i>'
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

.sensor-box {
	font-size: 0.8em;
 	font-weight: 600;	
}

</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">환경센서로그</div>
	
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="printSensorList()"><i class="fa-solid fa-print"></i> 출력</div>
	</div>
	
	<div class="content_selete_box content-item">
		<form id="searchForm" class="form-contractor" action="sensorList" method="POST" autocomplete="off" > 
			<span class="select-title">구역:</span>
			<select id="search_section" name="section" class="form-control select-content" onchange="changeSection()"></select>
		</form> 
	</div>
	
	<div class="content_date_box content-item">
		<span class="title">조회 날짜:</span>
		<input id="input_date" class="form-control" placeholder="일자 선택" value="일자 선택" style="cursor:pointer"></input>
	</div>
	
	<div class="content_summary_box content-item">
		<i class="fa-solid fa-star" style="color:#ff3547;"></i> 데이터 보존 기간은 1달입니다. 기간이 지나 삭제된 데이터는 복구할 수 없습니다.
	</div>
	
	<div class="content_table_box content-item">
		<table id="sensorTable" data-search="true" data-pagination="true" data-page-size="100" data-loading-template="loadingTemplate" 
			data-page-list="[100, 200, 500, All]" data-sort-name="[section]" data-filter-control="true" class="table table-bordered col-xs-12 table-hover table-striped" >
			<thead>
				<tr>	
					 <th data-field="no" class="text-center show-web" data-sortable="true">순번</th>			 
					 <th data-field="section_info" class="text-center">위 치</th>
					 <th data-field="sensor_info" class="text-center show-mobile">환경센서정보</th>
					 <th data-field="o2" class="text-center show-web" data-sortable="true">산소</th>	
					 <th data-field="co2" class="text-center show-web" data-sortable="true">이산화탄소</th>
					 <th data-field="co" class="text-center show-web" data-sortable="true">일산화탄소</th>
					 <th data-field="h2s" class="text-center show-web" data-sortable="true">황화수소</th>
					 <th data-field="ch4" class="text-center show-web" data-sortable="true">가연성가스</th>
					 <th data-field="time_diff_min" class="text-center show-web">딜레이</th>
					 <th data-field="write_time" class="text-center" data-sortable="true">시간</th>
				 </tr>
			</thead>
		</table>
	</div>
</div> <!-- content-wrapper END -->

<div id="form_group">
	<form id="printSensor" action="printSensorList" method="POST">
		<input id="date" type="hidden" name="date" value=""/>
		<input id="section" type="hidden" name="section" value=""/>
	</form>	
</div>

<%@ include file="IncludeBottom.jsp"%>