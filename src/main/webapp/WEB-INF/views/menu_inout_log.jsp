<%@ include file="IncludeTop.jsp"%>
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
    		CUR_CONT_ID = -1;
    		$('#search_cont').val(CUR_CONT_ID).prop("selected", true);
    		getLocationList();
    	}
    }).attr('readonly','readonly');  
	
	setInitContSelect();
	getLocationList();
	
});

function setInitContSelect() {
	$('#search_cont').empty();
	var option = '<option value="-1">전체</option>';
	$('#search_cont').append(option);
}

function getLocationList() {	
	
    $('#sectionList').bootstrapTable('destroy')
	.bootstrapTable()
  	.bootstrapTable('showLoading')		
	
	$.ajax({
		type: "GET",
		url: 'http://13.209.31.139:11243/getSectionInoutList',
		//url: 'http://211.212.221.98:11243/getSectionInoutList',
		data: {
			site_id: CUR_SITE_ID,
			input_date: $('#input_date').val(),
			input_cont: $('#search_cont').val()
		},		
		async: true,
		cache: false,
		success: function (data, status) {			
			var list = JSON.parse(JSON.stringify(data)).rtlsList;
			var contList = JSON.parse(JSON.stringify(data)).contList;
			setInitContSelect();
			if(contList != null) {
				for(var c=0; c < contList.length; c++) {			
					var optionHtml = '<option value="'+contList[c].id+'">'+ contList[c].name+'</option>';
					$('#search_cont').append(optionHtml);
				}
				$('#search_cont').val(CUR_CONT_ID).prop("selected", true);
			}
			if(list != null) {
				if(list.length > 0) {
					for(var i=0; i < list.length; i++) {			
						list[i].no = i+1;
						list[i].section_info = '<div class="section"><span><i class="fa-regular fa-square"></i> #'+list[i].section+'</span> - '+ list[i].section_name+'</div>';				
						list[i].view_detail = '<div onclick="detailView('+list[i].section+')"class="btn btn-default" style="background-color:transparent !important; padding:0; width:2.5vw; height:2.7vh; box-shadow:none;"><img src="images/company/ss/img_view.png" style="height:30px;"></div>';
						list[i].access_count = list[i].access_count + "명 "
					}				
					$('#sectionList').bootstrapTable('load', list );	
					$('#sectionList').bootstrapTable('hideLoading');
				}				
			}			
			else{
				$('#sectionList').bootstrapTable('load', [] );
				$('#sectionList').bootstrapTable('hideLoading');
			}
			
		}
	});	
}

function changeCont() {
	CUR_CONT_ID = $('#search_cont').val();
	getLocationList();
}

function printInout() {	
	G_INPUT_DATE = $('#input_date').val();
	$('#print_date').val(G_INPUT_DATE);	
	G_INPUT_CONT = $('#search_cont').val();
	$('#print_cont').val(G_INPUT_CONT);	
	let cont_name = $('#search_cont option:selected').text();
	$('#print_cont_name').val(cont_name);
	$('#printInout').submit();
}

function detailView(section) {
	G_SECTION = section;
	G_INPUT_DATE = $('#input_date').val();	
	getSectionDetail();
	$('#section_log_modal').modal('show');
}

function getSectionDetail() {	
	
    $('#section_log_table').bootstrapTable('destroy')
	.bootstrapTable()
  	.bootstrapTable('showLoading')	
	
	$.ajax({
		type: "GET",
		url: 'http://13.209.31.139:11243/getSectionInoutLog',
		//url: 'http://211.212.221.98:11243/getSectionInoutLog',
		data: {
			site_id: CUR_SITE_ID,
			input_section: G_SECTION,
			input_cont: $('#search_cont').val(),
			input_date: $('#input_date').val()
		},
		async: true,
		cache: false,
		success: function (list, status) {
			if(list.length > 0) {
				for(var i=0; i < list.length; i++) {					
					list[i].no = i+1;
									
					
					if(list[i].inout_type == 'OUT') {
						list[i].inout = "<span style='font-weight: bold; color: #BA1111;'>出</span>";						
					} 
					else if(list[i].inout_type == 'IN') {
						list[i].inout = "<span style='font-weight: bold; color: #3A751C;'>入</span>";										
					}		
					else {
						list[i].inout = "<span style='font-weight: bold; color: #BA1111;'>AUTO-OUT</span>";				
					}
					
					list[i].rtls_info = '<div class="text-left sensor-box">' + 
						'<div> 장비: '+list[i].rtls_type+'</div>' +
						'<div> 번호: '+list[i].device_idx+'</div>' +
						'<div> 출입: '+list[i].inout+'</div>' +
						'</div>';		
					
					list[i].write_time = list[i].write_time.replace(".0", "");
		
					list[i].device_idx = list[i].device_idx + "번 "
				}				
				$('#section_log_table').bootstrapTable('load', list );	
				$('#section_log_table').bootstrapTable('hideLoading');
			}else{
				$('#section_log_table').bootstrapTable('load', [] );
				$('#section_log_table').bootstrapTable('hideLoading');
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
	<div id="content_title" class="content-item">구역출입로그</div>
	
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="printInout()"><i class="fa-solid fa-print"></i> 출력</div>
	</div>
	
	<div class="content_selete_box content-item">
		<form id="searchForm" class="form-contractor" action="recordList" method="POST" autocomplete="off" > 
			<span class="select-title">업체:</span>
			<select id="search_cont" name="cont_id" class="form-control select-content" onchange="changeCont()"></select>
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
		<table id="sectionList" data-loading-template="loadingTemplate" data-search="true" data-pagination="true" data-page-size="100" 
			data-page-list="[100, 200, 500, All]" data-sort-name="[section]" data-filter-control="true" class="table table-bordered col-xs-12 table-hover table-striped" >
			<thead>
				<tr>
					<th data-field="no" class="text-center" data-sortable="true">순번</th>
					<th data-field="section_info" data-filter-control="select" class="text-center">위 치</th>		
					<th data-field="access_count" class="text-center show-web" data-sortable="true">금일 작업인원 수</th>
					<th data-field="view_detail" class="text-center">상세 로그</th>
				</tr>
			</thead>		
		</table>
	</div>
</div> <!-- content-wrapper END -->

<div id="form_group">	
	<form id="printInout" action="printInout" method="POST">
		<input type="hidden" name="site_id" value="${userLoginInfo.site_id}"/>	
		<input id="print_cont_name" type="hidden" name="cont_name" value=""/>
		<input id="print_date" type="hidden" name="date" value=""/>
		<input id="print_cont" type="hidden" name="cont_id" value=""/>
	</form>
</div>

<div style="display:none" id="modal_location_log" data-toggle="modal" data-target="#section_log_modal"></div>
<div class="modal fade" id="section_log_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
  		<div class="modal-content">
	   		<div class="modal-header" style="font-size: 1.5em;"> 
				구역출입로그
			</div>
			<div class="modal-body">
		        <div class="col-sm-12">
				    <div id="sub-tab-box" class="tab-content nav nav-tabs">
						<table id="section_log_table" data-loading-template="loadingTemplate" data-search="false" data-pagination="true"
							data-page-size="100" data-page-list="[10, 20, 50, All]" data-filter-control="true" 
			 				class="table table-bordered col-12 table-hover table-striped" >
						<thead>
							<tr>							
								<th class="text-center show-web" data-field="no">순번</th>
	            				<th data-field="cont_name" class="text-center" data-field="cont_name" data-sortable="true">업 체</th>
								<th data-field="wt_name" class="text-center show-web" data-sortable="true">직 종</th>
								<th data-field="name" class="text-center">이름</th>
	            				<th class="text-center show-web" data-field="rtls_type">장비</th>
	            				<th class="text-center show-web" data-field="device_idx">번호</th>
	            				<th class="text-center show-web" data-field="inout">출입</th>
	            				<th class="text-center show-mobile" data-field="rtls_info">상태</th>
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

<%@ include file="IncludeBottom.jsp"%>
