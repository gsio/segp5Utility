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
    		getQRLogList();
    	}
    }).attr('readonly','readonly');  
	
	getQRLogList();
	
});

function getQRLogList() {	
	
    $('#qrLogList').bootstrapTable('destroy')
	.bootstrapTable()
  	.bootstrapTable('showLoading')		
	
	$.ajax({
		type: "GET",
		url: 'qr/getQRInoutLogList',
		data: {
			site_id: CUR_SITE_ID,
			input_date: $('#input_date').val(),
		},		
		async: true,
		cache: false,
		success: function (list, status) {			
			if(list != null) {
				if(list.length > 0) {
					for(var i=0; i < list.length; i++) {
						
						if(list[i].role == 1) {
							list[i].role_type = "<span style='font-weight: bold; color: #3A751C;'>관리자</span>";		
						}
						else {
							list[i].role_type = "<span style='font-weight: bold; color: #4264c9;'>근로자</span>";	
						}
						
						if(list[i].inout_type == 1) {
							list[i].inout = "<span style='font-weight: bold; color: #3A751C;'>入</span>";										
						}		
						else {
							list[i].inout = "<span style='font-weight: bold; color: #BA1111;'>出</span>";				
						}				
					}				
					$('#qrLogList').bootstrapTable('load', list );	
					$('#qrLogList').bootstrapTable('hideLoading');
				}				
			}			
			else{
				$('#qrLogList').bootstrapTable('load', [] );
				$('#qrLogList').bootstrapTable('hideLoading');
			}
		}
	});	
}


function printFan() {		
	G_INPUT_DATE = $('#input_date').val();
	$('#print_date').val(G_INPUT_DATE);	
	$('#printQRLog').submit();
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
	<div id="content_title" class="content-item">QR출입로그</div>
	
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
		<table id="qrLogList" data-loading-template="loadingTemplate" data-search="true" data-pagination="true" data-page-size="100" 
			data-page-list="[100, 200, 500, All]" data-sort-name="[section]" data-filter-control="true" class="table table-bordered col-xs-12 table-hover table-striped" >
			<thead>
				<tr>
					<th data-field="role_type" class="text-center" data-sortable="true">구분</th>
					<th data-field="cont_name" class="text-center">업체</th>
					<th data-field="name" class="text-center">이름</th>
					<th data-field="inout" class="text-center">출입</th>
					<th data-field="write_time" class="text-center">시간</th>
				</tr>
			</thead>		
		</table>
	</div>
</div> <!-- content-wrapper END -->

<div id="form_group">	
	<form id="printQRLog" action="printQRLog" method="POST">
		<input type="hidden" name="site_id" value="${userLoginInfo.site_id}"/>	
		<input id="print_date" type="hidden" name="date" value=""/>
	</form>
</div>


<%@ include file="IncludeBottom.jsp"%>
