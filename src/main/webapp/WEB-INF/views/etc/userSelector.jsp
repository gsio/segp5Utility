<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ page session="true"%>

<!doctype html>


<html dir="ltr">
<head>
  <title>통합 안전 관리 시스템 </title>
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   
  <link href="css/bootstrap.css" rel="stylesheet" media="screen">
  <link href="css/common.css" rel="stylesheet" media="screen"> 
  
  <link href="images/ss.ico" rel="icon" type="image/x-icon" />
  <link href="images/ss.ico" rel="shortcut icon" type="image/x-icon" />
  




<script>
//var type = getUrlParameter('type');
$(document).ready(function() {
	getWorkerList('${userLoginInfo.site_id}', 1);
});

function getWorkerList(site_id, cont_id){
	$.ajax({
		type : "GET",
		url : "./json/getWorkerListByContId",				
		data : {
			site_id : site_id,
			cont_id : cont_id
		},
		cache : false,
		success : function(json	,status){
			$('#workerTable').bootstrapTable();
			
			var json_data  = JSON.parse(json);			
			if(json_data.result =="true"){		
				$('#workerTable').bootstrapTable('load', json_data.worker_list );
			} else{
				$('#workerTable').bootstrapTable('load', []);
			}
		},
			error : null
		});
}


function getUserList(site_id, cont_id){
	
	
}
</script>
 
 
 <table id="workerTable" data-click-to-select="true"   data-search="true" class="table table-borderd">
	<thead>
		<tr>
			 <th data-field="idx"  class="text-center">NO</th>
			 <th data-field="name" class="text-center">이름</th>
			 <th data-field="tname" class="text-center">직종</th>
			 <th data-field="phone" class="text-center">H.P</th>
			 <th data-field="hiredate" class="text-center">채용일</th>
			 <th data-field="edudate" class="text-center">교육일</th>
			 <th data-field="eduimage" class="text-center">교육이미지</th>
		 </tr>
	</thead>
	
	
</table>
 

  <div class="text-right">
    <div class="btn btn-default" style="right: 0;" onclick="window.close()"><span class="glyphicon glyphicon-remove" ></span> 취소</div>              
    <div class="btn btn-primary" style="right: 0;" onclick="selectSubmit()"><span class="glyphicon glyphicon-ok" ></span> 등록</div>
  </div>

