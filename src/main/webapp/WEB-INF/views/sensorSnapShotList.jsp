<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>


<script>
var CUR_SITE_ID = '${userLoginInfo.site_id}';
$(document).ready(function() {

});

function printSensorList(){
	$('#printSensor').submit();
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
	
#beaconTable .btn{
	padding: 2px 7px;
}
	
#__userTable .btn{
	padding: 2px 7px;
}
	
#__userList td, #__workerList td {
	padding: 1.5vh;
	font-size: 2vh;
}	
</style>

<div id="wrapper" class="text-left col-xs-12" style="margin-left: 1.5%;">
	<div class="list_title">금일환경센서</div>
	<div class="col-xs-8" style="margin-left: 17%;">
		<div class="btn btn-default padding-right text-right" data-dismiss="modal" onclick="printSensorList()"><span class="glyphicon glyphicon-print" ></span> 출 력</div>
	</div>
	<div class="col-xs-8" style="margin-left: 17%;">

		<table id="sensorTable" data-toggle="table"  data-search="true" data-pagination="true" data-page-size="100" 
			data-page-list="[100, 200, 500, All]" data-sort-name="[section]" data-filter-control="true" class="table table-bordered col-xs-12 table-hover table-striped" >
			<thead>
				<tr>	
					 <th data-field="no"  class="text-center" data-sortable="true">번호</th>			 
					 <th data-field="name" class="text-center" data-sortable="true">이 름</th>
					 <th data-field="section" class="text-center" data-field="section" data-sortable="true" data-filter-control="select">위 치</th>
					 <th data-field="o2" class="text-center">산소</th>	
					 <th data-field="co2" class="text-center">이산화탄소</th>
					 <th data-field="co" class="text-center">일산화탄소</th>
					 <th data-field="h2s" class="text-center">황화수소</th>
					 <th data-field="ch4" class="text-center">LEL</th>
					 <th data-field="time_diff_min" class="text-center">딜레이</th>
					 <th data-field="write_time" class="text-center" data-sortable="true">시간</th>
				 </tr>
			</thead>
			
			<tbody>
				<c:forEach var="sensor" items="${sensorList}" varStatus="idx">
		     	<tr>
		     		<td class="text-center">${idx.index + 1}</td>
		        	<td class="text-center">${sensor.name}</td>       
		        	<td class="text-center">${sensor.section}번 수조</td>
		        	<td class="text-center">${sensor.o2}</td>     
		        	<td class="text-center">${sensor.co2}</td>        	
		        	<td class="text-center">${sensor.co}</td>
		        	<td class="text-center">${sensor.h2s}</td>        	
		        	<td class="text-center">${sensor.ch4}</td> 
					<td class="text-center">
					<c:choose>
						<c:when test="${sensor.time_diff_min > 0}">
						<div class="badge badge-danger" style="height:100%; padding:1vh;">${sensor.time_diff_min}분</div>
						</c:when>			
			        	<c:otherwise>
			        	<div class="badge badge-success" style="height:100%; padding:1vh;">${sensor.time_diff_min}분</div>
			        	</c:otherwise>
					</c:choose>
					</td>		     
		        	<td class="text-center">${sensor.write_time}</td>    	
		      </tr>
		   </c:forEach>
			</tbody>
				
		</table>
	</div>
</div>

<div id="form_group">
	<form id="printSensor" action="printSensorList" method="POST">
		<input type="hidden" name="site_id" value="${userLoginInfo.site_id}"/>
	</form>	
</div>
 
<%@ include file="IncludeBottom.jsp"%>
 