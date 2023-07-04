<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>



<script>

var CUR_SITE_ID = '${userLoginInfo.site_id}';
var CUR_CONT_ID = -1;
var G_SECTION = -1;
var G_INPUT_DATE = ""

$(document).ready(function() {
	
	$('#input_date').val(getTodayDate());	
	
	$("#input_date").datepicker({
		yearRange: '-1:+1',    	
    	showSecond: true,
    	onClose: function (selectedDate) {    
    		getFanLogList();
    	}
    }).attr('readonly','readonly');  
	
	getFanLogList();
	
});

function getFanLogList() {	
	
    $('#fanList').bootstrapTable('destroy')
	.bootstrapTable()
  	.bootstrapTable('showLoading')		
	
	$.ajax({
		type: "GET",
		url: 'http://13.209.31.139:11243/getFanLogList',
		//url: 'http://211.212.221.98:11243/getFanLogList',
		data: {
			site_id: CUR_SITE_ID,
			input_date: $('#input_date').val(),
		},		
		async: true,
		cache: false,
		success: function (data, status) {			
			var list = JSON.parse(JSON.stringify(data)).fanList;	
			if(list != null) {
				if(list.length > 0) {
					for(var i=0; i < list.length; i++) {			
						list[i].no = i+1;
						list[i].section_info = '<div class="section"><span><i class="fa-regular fa-square"></i> #'+list[i].section+'</span> - '+ list[i].section_name+'</div>';				
						list[i].view_detail = '<div onclick="detailView('+list[i].section+')"class="btn btn-default" style="background-color:transparent !important; padding:0; width:2.5vw; height:2.7vh; box-shadow:none;"><img src="images/company/ss/img_view.png" style="height:30px;"></div>';
						list[i].device_count = list[i].device_count + "개 "
					}				
					$('#fanList').bootstrapTable('load', list );	
					$('#fanList').bootstrapTable('hideLoading');
				}				
			}			
			else{
				$('#fanList').bootstrapTable('load', [] );
				$('#fanList').bootstrapTable('hideLoading');
			}
			
		}
	});	
}


function printFan() {	
	/*
	G_INPUT_DATE = $('#input_date').val();
	$('#print_date').val(G_INPUT_DATE);	
	$('#printFan').submit();
	*/
}

function detailView(section) {
	G_SECTION = section;
	G_INPUT_DATE = $('#input_date').val();	
	getFanLogDetail();
	$('#section_log_modal').modal('show');
}

function getFanLogDetail() {	
	
    $('#fan_log_table').bootstrapTable('destroy')
	.bootstrapTable()
  	.bootstrapTable('showLoading')	
	
	$.ajax({
		type: "GET",
		url: 'http://13.209.31.139:11243/getFanLogDeatilList',
		//url: 'http://211.212.221.98:11243/getFanLogDeatilList',
		data: {
			site_id: CUR_SITE_ID,
			input_section: G_SECTION,
			input_date: $('#input_date').val()
		},
		async: true,
		cache: false,
		success: function (list, status) {
			if(list.length > 0) {
				
				for(var i=0; i < list.length; i++) {		
					
					list[i].no = i+1;
										
					if(list[i].fan_type == 1) {
						list[i].type = "<span style='font-weight: bold; color: #3A751C;'>배기</span>";						
					} 
					else if(list[i].fan_type == 0) {
						list[i].type = "<span style='font-weight: bold; color: #BA1111;'>급기</span>";										
					}
					
					if(list[i].state == 1) {
						/*<i class='fas fa-running'></i>*/
						list[i].inout = "<span style='font-weight: bold; color: #3A751C;'>ON</span>";						
					} 
					else if(list[i].state == 0) {
						/*<i class='fas fa-ban'></i>*/
						list[i].inout = "<span style='font-weight: bold; color: #BA1111;'>OFF</span>";										
					}		
					
					list[i].write_time = list[i].write_time.replace(".0", "");
		
					list[i].device_info = list[i].fan_name + ' ('+list[i].fan_idx + "번 센서"+')';		
				
				}			
				
				$('#fan_log_table').bootstrapTable('load', list );	
				$('#fan_log_table').bootstrapTable('hideLoading');
				
			}
			
			else {
				$('#fan_log_table').bootstrapTable('load', [] );
				$('#fan_log_table').bootstrapTable('hideLoading');
			}
		}
	});	
}

function loadingTemplate() {
	 return '<i class="fa fa-spinner fa-spin fa-fw fa-2x"></i>'
}

</script>

<style>

@media ( min-width : 992px) {
	.modal-dialog {
		max-width: 950px;
    	margin: 1.75rem auto;
	}
}


@media ( max-width : 991px) {
	.modal-dialog {
		max-width: 500px;
    	margin: 1.75rem auto;
	}
}

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
	<div id="content_title" class="content-item">급기배기로그</div>
	
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="printFan()"><i class="fa-solid fa-print"></i> 출력</div>
	</div>
	
	<div class="content_date_box content-item">
		<span class="title">조회 날짜:</span>
		<input id="input_date" class="form-control" placeholder="일자 선택" value="일자 선택" style="cursor:pointer"></input>
	</div>
	
	<div class="content_summary_box content-item">
		<i class="fa-solid fa-star" style="color:#ff3547;"></i> 데이터 보존 기간은 1달입니다. 기간이 지나 삭제된 데이터는 복구할 수 없습니다.
	</div>
	
	<div class="content_table_box content-item">
		<table id="fanList" data-loading-template="loadingTemplate" data-search="true" data-pagination="true" data-page-size="100" 
			data-page-list="[100, 200, 500, All]" data-sort-name="[section]" data-filter-control="true" class="table table-bordered col-xs-12 table-hover table-striped" >
			<thead>
				<tr>
					<th data-field="no" class="text-center" data-sortable="true">순번</th>
					<th data-field="section_info" data-filter-control="select" class="text-center">위 치</th>		
					<th data-field="device_count" class="text-center show-web" >급기 배기 센서 수</th>
					<th data-field="view_detail" class="text-center">상세 로그</th>
				</tr>
			</thead>		
		</table>
	</div>
</div> <!-- content-wrapper END -->

<div id="form_group">	
	<form id="printFan" action="printFan" method="POST">
		<input type="hidden" name="site_id" value="${userLoginInfo.site_id}"/>	
		<input id="print_date" type="hidden" name="date" value=""/>
	</form>
</div>

<div style="display:none" id="modal_location_log" data-toggle="modal" data-target="#section_log_modal"></div>
<div class="modal fade" id="section_log_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
  		<div class="modal-content">
	   		<div class="modal-header" style="font-size: 1.5em;"> 
				구역 급배기 로그
			</div>
			<div class="modal-body">
		        <div class="col-sm-12">
				    <div id="sub-tab-box" class="tab-content nav nav-tabs">
						<table id="fan_log_table" data-loading-template="loadingTemplate" data-search="false" data-pagination="true"
							data-page-size="100" data-page-list="[10, 20, 50, All]" data-filter-control="true" 
			 				class="table table-bordered col-12 table-hover table-striped" >
						<thead>
							<tr>							
								<th class="text-center " data-field="no">순번</th>
	            				<th class="text-center" data-field="type" data-filter-control="select" >종류</th>
								<th class="text-center " data-field="device_info" data-filter-control="select" >급배기<br />정보</th>				
								<th class="text-center " data-field="inout">상태<br />(ON/OFF)</th>
	            				<th class="text-center" data-sortable="true" data-field="write_time">시 간</th>       	            					
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
