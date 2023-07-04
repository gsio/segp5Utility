<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script type="text/javascript" src="js/risk/list.js"></script>
<link rel="stylesheet" href="css/risk/list.css">

<script>

var sd = '${date_risk_start}';
var ed = '${date_risk_end}';

$(document).ready(function() {
	
	$('#search_cont_id').val('${tar_cont_id}');
	$('#search_approval_state').val('${tar_approval_state}');
	
	initDatePicker();	
	
	$(".datepicker").datepicker({
		yearRange: '-1:+1',    
    	showSecond: true,
    	onClose: function (selectedDate) {
			if($("#risk_start").val() > $("#risk_end").val()) {
				alert("날짜를 다시 확인해주세요");
				$("#risk_end").val('');
				$(".datepicker").css('border','2px solid red');
			}
			else {
				checkDetail();
				$(".datepicker").css('border','1px solid #ced4da');
			}
    	}
    }).attr('readonly','readonly');
	
	$('#riskTable').on('click', '.riskTR' , function (id) {
		var id = $(this).attr('id');	
		$("#risk_id").val(id);
   		$("#v_risk_id").val(id);    
   		$('#viewForm').submit();
	});

	
});

</script>

<style>

#riskTable td.required {
	font-weight: 600;	
}


</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">위험성평가</div>
	
	<div class="content_button_box content-item" >
		<div class="btn btn-default" onclick="registerRisk()"><i class="fa-regular fa-registered"></i> 등록</div>
	</div>	
	
	<div class="content_multi_box content-item">
		<form id="searchForm" class="form-contractor" action="riskList" method="POST" autocomplete="off">
		
		<div class="selete-item-box item-box">
			<div class="selete-item">
				<c:choose>
				<c:when test="${sessionScope.userLoginInfo.cont_type == 0}"> 		      
					<span class="select-title">업체:</span>
					<select id="search_cont_id" name="cont_id" class="form-control select-content" onchange="checkDetail()">
						<option value="-1">전체</option>
						<c:forEach var="cont" items="${contList}" varStatus="idx">
						<option value="${cont.id}">${cont.name}</option>
						</c:forEach>	  
					</select>
				</c:when>
				<c:otherwise>
					<span class="select-title">업체:</span> 	
					<select name="cont_id" class="form-control select-content">				
						<option value="${sessionScope.userLoginInfo.cont_id}">${sessionScope.userLoginInfo.cont_name}</option>
					</select>
					<input id="cont_id" type="hidden" name="cont_id" value="${sessionScope.userLoginInfo.cont_id}"/>
				</c:otherwise>	
				</c:choose>
			</div>
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
		<table id="riskTable" data-toggle="table" data-toggle="table" data-search="true" data-pagination="true" 
			data-page-size="10" data-page-list="[10,25,All]" data-sort-name="[name]" data-filter-control="true"
			data-page-number="1" class="table table-bordered col-xs-12 table-hover table-striped">	
			
			<thead>
				<tr>
					<th class="text-center">순번</th>
					<th class="text-center show-web">상태</th>     
					<th class="text-center show-web">업체</th>
					<th class="text-center show-web">공종</th>					
					<th class="text-center show-web">내용</th>					
					<th class="text-center show-web">기간</th>
					<th class="text-center show-web">작성자</th>
					<th class="text-center show-web">작성일자</th>
					<th class="text-center show-web">점검일지</th>
					<th class="text-center show-mobile">위험성평가 내용</th>
				</tr>
			</thead>
	
			<tbody>            
			    
				<c:forEach var="data" items="${riskList}" varStatus="idx">
				<tr class="riskTR" id="${data.id}">
					<td class="text-center">${idx.index + 1}</td>
					<td class="text-center show-web required">
						<c:choose>
							<c:when test="${data.approval_state == 1}"> 	
								<span style="color:#000;">${data.approval_state_name}</span>
							</c:when>
							<c:when test="${data.approval_state == 4}"> 	
								<span style="color:#2710d5;">${data.approval_state_name}</span>
							</c:when>
							<c:when test="${data.approval_state == 5}"> 	
								<span style="color:#dc3545;">${data.approval_state_name}</span>
							</c:when>
							<c:otherwise>
								<span style="color:#5cb85c;">${data.approval_state_name}</span>
							</c:otherwise>	
						</c:choose>
					
					
					</td>  
					<td class="text-center show-web">${data.cont_name}</td>
					<td class="text-center show-web">${data.work_state_name}</td>  
					<td class="text-center show-web">${data.content}</td>  
				   	<td class="text-center show-web">${data.risk_start}<br>~<br>${data.risk_end}</td>
				   	<td class="text-center show-web">${data.writer_name}</td>		
				   	<td class="text-center show-web">${data.write_time.replace(".0", "")}</td>		
				   	<td class="text-center show-web"></td>
					<td class="text-center show-mobile mobile-wrap">
						<div class="mobile-box">
				    		<div class="mobile-item risk-info"><span style="font-weight: bold;">${data.cont_name}</span></div>
				    		<div class="mobile-item risk-state">진행사항:&nbsp<span style="color:#ff3547;">${data.approval_state_name}</span></div>
						</div>
						<div class="mobile-box">
							<div class="mobile-item risk-period">${data.risk_start}<span style="font-weight: bold; color:#ff3547">&nbsp~&nbsp</span>${data.risk_end}</div>
				    		<div class="mobile-item risk-write-time" style="justify-content: flex-end;">${data.write_time.replace(".0", "")}</div>
						</div>
					</td>
				</tr>
				</c:forEach>
		
			</tbody>
			
		</table>
		
	</div>
	
</div> <!-- content-wrapper END -->

<div id="form_group">
	<form id="registerForm" action="registerRisk" method="POST" ></form>
	<form id="updateForm" action="registerRisk" method="POST" >
		<input id="risk_id" type="hidden" name="id"/>
	</form>
	<form id="viewForm" action="riskView" method="POST" >
		<input id="v_risk_id" type="hidden" name="id"/>
	</form>	
</div>

<%@ include file="IncludeBottom.jsp"%>