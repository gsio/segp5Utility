<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>



<script>

var app_data;
const site_id = '${userLoginInfo.site_id}';

$(document).ready(function() {	
	initVue();
	initInputValue();	
	getMonitorSetting();
});

function initVue(){
	app_data = new Vue({
		el: '#app_data',
		data:{
			insert: {		  
				type : 1,
				gubun : 2,
				start_time: '00:00',
				end_time: '23:30',
			}
		}    
	});
}

function openInputModal(){	
	initInputValue();
	$('#inputModal').modal('show');	
}

function initInputValue(){
	app_data.insert.gubun = 2;
	app_data.insert.type = 1;
	app_data.insert.start_time = '00:00',
	app_data.insert.end_time = '23:30',
	
	$('#start_time').timepicker({
	    timeFormat: 'HH:mm',
	    interval: 30,
	    dynamic: false,
	    dropdown: true,
	    scrollbar: true
	});
	
	$('#end_time').timepicker({
	    timeFormat: 'HH:mm',
	    interval: 30,
	    dynamic: false,
	    dropdown: true,
	    scrollbar: true
	});
	
}


function insertDidSetting() {
	
	var gubun = app_data.insert.gubun;
	var type = app_data.insert.type;
	var start_time = $('#start_time').val()
	var end_time = $('#end_time').val()

	if(compareTime(start_time, end_time)) {
		$.ajax({
			type : "POST",
			url : "./manage/insertDidSetting",
			traditional: true,
			async: false,
			data: {			 
				"site_id" : site_id,
				"gubun" : gubun,
				"type" : type,
				"start_time" : start_time,
				"end_time" : end_time
			},
			cache : false,
			success : function(json, status){			
				var json_data  = JSON.parse(json);
				if(json_data.result == "true"){				
					alert('등록이 완료되었습니다');				
				}
				else {
					alert('[Error] 겹치는 시간대가 있습니다'); 
				}			
				window.location.reload();
			},
			error :null
		}); 
	}
	else {
		alert("[Error] 종료시간은 항상 시간시간보다 나중이여야 합니다.")
	}
}

function compareTime(start, end) {
	const startTimeParts = start.split(':');
	const endTimeParts = end.split(':');

	const startHour = parseInt(startTimeParts[0]);
	const startMinute = parseInt(startTimeParts[1]);
	const endHour = parseInt(endTimeParts[0]);
	const endMinute = parseInt(endTimeParts[1]);

	if (endHour > startHour) {
		return true;
	}
	else if (endHour === startHour) {
		return endMinute > startMinute;
	} else {
		return false;
	}
}

function getMonitorSetting() {
	$.ajax({
		type: "GET",
		url: "./manage/getMonitorSetting",
		data: {
			site_id: site_id
		},
		async: true,
		cache: false,
		success: function (list, status) {	
			$("#monitorTable").bootstrapTable();
			if (list.length >= 0) {
				for(var i=0; i < list.length; i++) {			
					
					list[i].no = i+1;
					
					if(list[i].gubun == 1) {
						list[i].gubun_date = '<div style="color: #3866a8; font-weight: bold; font-size: 1rem;">매일</div>'; 
					}
					else {
						list[i].gubun_date = '<div style="color: #d52802; font-weight: bold; font-size: 1rem;">금일</div>';
					}
					
					if(list[i].type == 1) {
						list[i].off_type = '<div style="color: #3866a8; font-weight: bold; display: flex; justify-content: space-around; align-items: center;">' + 
							'<i id="btn_alarm" class="fas fa-toggle-off" style="font-size: 1rem !important;"> 알람</i> </div>'; 
					}
					else {
						list[i].off_type = '<div style="color: #FF3547; font-weight: bold; display: flex; justify-content: space-around; align-items: center;">' + 
						'<i id="btn_alarm" class="fas fa-volume-mute"  style="font-size: 1rem !important;"> 소리</i> </div>'; 
					}
					
					list[i].time = list[i].start_time + " ~ " + list[i].end_time
					
					list[i].mobile_info = '<div class="mobile-box">' +
					'<div class="mobile-item">일일:&nbsp'+list[i].gubun_date+'</div> ' +
					'<div class="mobile-item">구분:&nbsp'+list[i].off_type+'</div> ' +
					'<div class="mobile-item">적용 시간:&nbsp'+list[i].time+'</div> ' +
					'<div class="mobile-item">작성자:&nbsp'+list[i].writer_name+'</div></div>';
					
					list[i].btn_delete = '<div class="btn icon-danger" onclick="deleteMonitorSetting('+list[i].id+')"><i class="fa-solid fa-trash"></i></div>';
					
					
				}	
				$("#monitorTable").bootstrapTable("load", list);
			} else {
				$("#monitorTable").bootstrapTable("load", []);
			}
		}
	});
}

function deleteMonitorSetting(id) {
	
	input = confirm('삭제하시겠습니까?');
	if(input){
		$.ajax({
			type : "POST",
			url : "./manage/deleteMonitorSetting",
			traditional: true,
			async: false,
			data : {			 
				"id" : id
			},
			cache : false,
			success : function(json,status){
				var json_data  = JSON.parse(json);
				if(json_data.result == "true"){				
					alert('삭제가 완료되었습니다');				
				}
				else {
					alert('error'); 
				}			
				window.location.reload();
			},
			error :null
		}); 
	}

}

</script>

<style>
	.form-control[disabled], .form-control[readonly], fieldset[disabled] .form-control 	{
		cursor: pointer;
		background-color: #eeeeee !important;
	}

</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">알람, 소리 Off 설정</div>
		
	<div class="content_button_box content-item" >
		<div class="btn btn-default" onclick="openInputModal()"><i class="fa-regular fa-registered"></i> 등록</div>
	</div>
	
	<div class="content_table_box content-item">
		<table id="monitorTable" data-search="true" data-pagination="true" data-page-size="25" data-page-list="[10, 25, 50, 100, All]"
		 	data-sort-name="[input_date]" data-filter-control="true" class="table table-bordered table-hover table-striped">
		 	
			<thead>
				<tr>
					<th data-field="no" data-sortable="true" class="text-center">순번</th>
					<th data-field="gubun_date" class="text-center show-web">일일</th>
					<th data-field="off_type" class="text-center show-web">구분</th>
					<th data-field="time" data-sortable="true" class="text-center show-web">적용 시간</th>
					<th data-field="writer_name" class="text-center show-web">작성자</th>
					<th data-field="write_time" class="text-center show-web">작성시간</th>
					<th data-field="mobile_info" class="text-center show-mobile">상세 정보</th>
					<th data-field="btn_delete" class="text-center">삭제</th>
				</tr>
			</thead>
		
	</table>
	</div>
	
	
	
	
</div> <!-- content-wrapper END -->

<div style="display:none" id="modal_wType" data-toggle="modal" data-target="#inputModal"></div>
<div class="modal fade" id="inputModal" tabindex="-1" role="dialog" aria-labelledby="inputModal" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
	   		<div class="modal-header" style="font-size: 1.5em;"> 
	   			OFF 시간 추가   
	   		</div>
			<div class="modal-body">
	      		<div id="app_data">
		        	<div class="col-sm-12">		    	    	
			    		<table class="table table-bordered table-hover table-striped">
						  	<tr>
			       	        	<th class="text-center">일자</th>
			       	        	<td>
			       	        		<select id="gubun" v-model.lazy="insert.gubun" style="cursor:pointer; text-align:center;" class="form-control">
			       	        			<option value="1">매일</option>
			       	        			<option value="2">금일</option>		       	        		
			       	        		</select>
			       	        	</td>
			       	        </tr>
			       	        <tr>
			       	        	<th class="text-center">구분</th>
			       	        	<td>
			       	        		<select id="type" v-model.lazy="insert.type" style="cursor:pointer; text-align:center;" class="form-control">
			       	        			<option value="1">알림(전체)</option>
			       	        			<option value="2">소리</option>		       	        		
			       	        		</select>
			       	        	</td>
			       	        </tr>
			       	        <tr>
			       	        	<th class="text-center">시작시간</th>
			       	        	<td>
			       	        		<input id="start_time" v-model.lazy="insert.start_time" class="timepicker form-control" readonly="readonly">	
			       	        	</td>		       
			       	        </tr>
			       	        <tr>
			       	        	<th class="text-center">종료시간</th>
			       	        	<td>
			       	        		<input id="end_time" v-model.lazy="insert.end_time" class="timepicker form-control" readonly="readonly">	
			       	        	</td>		       
			       	        </tr>
				    	</table>
					</div>	 					
					<div id="view_button" class="text-right" style="margin-top:15px; padding-right:5px">
						<div class="btn btn-default margin-top" onclick="insertDidSetting()"><i class="fa-regular fa-pen-to-square"></i> 등록</div>
						<div class="btn btn-danger margin-top" data-dismiss="modal" ><i class="fa-solid fa-xmark"></i> 닫기</div>
					</div>
	      		</div><!-- "app_data" -->	
			</div><!-- modal-body -->			
	    </div><!-- modal-content -->	
	</div><!-- modal-dialog -->	
</div>


<div id="form_group">

</div>

<%@ include file="IncludeBottom.jsp"%>