<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

var CUR_STATE = -1;

$(document).ready(function() {
	
	$('#search_ptw_state').val('${tar_state}');
	
	$('#input_date').val(getTodayDate());	
	
	$("#input_date").datepicker({
		yearRange: '-1:+1',    
    	showSecond: true,
    	onClose: function (selectedDate) {    
    		CUR_STATE = -1;
    		$('#search_ptw_state').val(CUR_STATE).prop("selected", true);    		
    	}
    }).attr('readonly','readonly');
	

	$('.timepicker').timepicker({
	    timeFormat: 'p h:mm',
	    interval: 60,
	    dynamic: false,
	    dropdown: true,
	    scrollbar: true
	});

	
});

function changeState(){
	$('#searchForm').submit();
}


</script>

<style>

#noticeTable tr {
	cursor: pointer;
}
	

</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">작업허가서</div>
	
	<div class="content_button_box content-item" >
		<div class="btn btn-default" onclick=""><i class="fa-regular fa-registered"></i> 등록</div>
	</div>	
	
	<div class="content_multi_box content-item">
		<form id="searchForm" class="form-contractor" action="riskList" method="POST" autocomplete="off">
		
		<div class="selete-item-box item-box">			
			<div class="selete-item">
				<span class="select-title">상태:</span> 	
				<select id="search_approval_state" name="approval_state" class="form-control select-content" onchange="checkDetail()">
					<option value="-1">전체</option>
					<c:forEach var="state" items="${stateList}" varStatus="idx">
					<option value="${state.id}">${state.name}</option>
					</c:forEach>	  
				</select>
			</div>		
		</div>
	
		<div class="date-item-box item-box">
			<span class="date-title">조회 날짜:</span>
			<div class="date-item">				
				<input id="risk_start" name="risk_start" class="datepicker form-control" readonly="readonly">
			</div>
			<span style="font-weight: 600;">~</span>
			<div class="date-item">
				<input id="risk_end" name="risk_end" class="datepicker form-control" readonly="readonly">
			</div>
		</div>	
		
		</form>
	</div>

	<div class="content_table_box content-item">
		
		<table id="ptwTable" data-toggle="table" data-toggle="table" data-search="true" data-pagination="true" 
			data-page-size="10" data-page-list="[10,25,All]" data-sort-name="[name]" data-filter-control="true"
			data-page-number="1" class="table table-bordered col-xs-12 table-hover table-striped">	
			
			<thead>
				<tr>
					<th class="text-center">순번</th>
					<th class="text-center show-web">상태</th>
					<th class="text-center show-web">업체</th>     
					<th class="text-center show-web">작업책임자</th>										
					<th class="text-center show-web">작업일자</th>					
					<th class="text-center show-web">작업시간</th>
					<th class="text-center show-web">작성자</th>
					<th class="text-center show-web">출력</th>     
				</tr>
			</thead>
			
			<tbody>   
			
			</tbody>
			
		</table>
		
	</div>
	
</div> <!-- content-wrapper END -->

<div id="form_group">
	<form id="registerForm" action="registerPTW" method="POST" ></form>
</div>

<%@ include file="IncludeBottom_renewal.jsp"%>