<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>


<script>
var CUR_SITE_ID = '${userLoginInfo.site_id}';

$(document).ready(function() {
	
	$('#input_date').val(getTodayDate3());	
	//getSensorList();
	
	$("#input_date").datepicker({
		yearRange: '-1:+1',    	
    	showSecond: true,
    	onClose: function (selectedDate) {    
    		//getSensorList();
    	}
    }).attr('readonly','readonly');  

});

function printSensorList(){
	$('#date').val($('#input_date').val());
	$('#printSensor').submit();
}

function getSensorList() {	
	$.ajax({
		type: "GET",
		url: 'http://13.209.31.139:11243/getSensorLogList',
		data: {
			input_date: $('#input_date').val()
		},
		traditional: true,
		async: true,
		cache: false,
		success: function (data, status) {						
			$('#sensorTable').bootstrapTable();			
			var list = JSON.parse(JSON.stringify(data)).sensorList;
			if(list != null) {
				console.log("[DATA COUNT]: " + list.length);
				if(list.length >= 0) {
					for(var i=0; i < list.length; i++) {
						list[i].no = i+1;
						list[i].o2 = list[i].o2 + "%";
						list[i].co2 = list[i].co2 + "ppm";
						list[i].co = list[i].co + "ppm";
						list[i].h2s = list[i].h2s + "ppm";
						list[i].ch4 = list[i].ch4 + "%";
						
						if(list[i].time_diff_min > 0) {
							list[i].time_diff_min = '<div class="badge badge-danger" style="height:100%; padding:1vh;">'+list[i].time_diff_min+'분</div>';
						}
						else {
							list[i].time_diff_min = '<div class="badge badge-success" style="height:100%; padding:1vh;">'+list[i].time_diff_min+'분</div>';
						}
						
						if(list[i].section_type == 1) {
							list[i].section_name = list[i].alias + " " + list[i].section_name;
						}
						
					}				
					$('#sensorTable').bootstrapTable('load', list );	
				}
			}
			else{
				$('#sensorTable').bootstrapTable('load', [] );
			}			
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
   	padding: 2px;
}

#input_date {
	width:15vh;
	text-align: center;
	display: inline-block;
}


</style>

<div class="list_title">금일환경센서</div>

<div class="text-left col-xs-8" style="margin-left: 17%;">
	<div style="font-weight: bold; font-size: 1.7vh;">
		조회 날짜:&nbsp&nbsp<input id="input_date" class="form-control" placeholder="일자 선택" value="일자 선택" style="cursor:pointer"></input>
	</div>
	<br>
	<b>데이터 보존 기간은 "<span style="color:red">30일</span>" 입니다. 기간이 지나 삭제된 데이터는 "<span style="color:red">복구</span>" 할 수 없습니다.</b>		
</div>
<div class="col-xs-8" style="margin-left: 17%;">
	<div class="btn btn-default padding-right text-right" data-dismiss="modal" style="float: right; margin-top: 2%;" onclick="printSensorList()"><span class="glyphicon glyphicon-print" ></span> 출 력</div>
</div>

<div class="col-xs-8" style="margin-left: 17%;">

<table id="sensorTable" data-toggle="table"  data-search="true" data-pagination="true" data-page-size="100" 
	data-page-list="[100, 200, 500, All]" data-sort-name="[section]" data-filter-control="true" class="table table-bordered col-xs-12 table-hover table-striped" >
	<thead>
		<tr>	
			 <th data-field="no" class="text-center" data-sortable="true">순번</th>			 
			 <th data-field="section_name" data-filter-control="select" class="text-center">위 치</th>
			 <th data-field="o2" class="text-center" data-sortable="true">산소</th>	
			 <th data-field="co2" class="text-center" data-sortable="true">이산화탄소</th>
			 <th data-field="co" class="text-center" data-sortable="true">일산화탄소</th>
			 <th data-field="h2s" class="text-center" data-sortable="true">황화수소</th>
			 <th data-field="ch4" class="text-center" data-sortable="true">가연성가스</th>
			 <th data-field="time_diff_min" class="text-center">딜레이</th>
			 <th data-field="write_time" class="text-center" data-sortable="true">시간</th>
		 </tr>
	</thead>
</table>
</div>

<div id="form_group">
	<form id="printSensor" action="printSensorList" method="POST">
		<input id="date" type="hidden" name="date" value=""/>
	</form>	
</div>
 
<%@ include file="IncludeBottom.jsp"%>