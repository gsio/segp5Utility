<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>


<script>
$(document).ready(function() {
	getWorkerList('${sessionScope.userLoginInfo.site_id}',  '${sessionScope.userLoginInfo.cont_id}');
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
		
				for(var i =0; i< json_data.worker_list.length ; i++){
					if(typeof json_data.worker_list[i].eduimage != 'undefined'){
						json_data.worker_list[i].edu_image_src = '<img class="size-fix img" height="50px"   src="image?virtname=' + json_data.worker_list[i].eduimage +'" onerror="images/noimage.png"/>';
					}
					
				}
				$('#workerTable').bootstrapTable('load', json_data.worker_list );
			} else{
				$('#workerTable').bootstrapTable('load', []);
			}
		},
			error : null
		});
}

function selectSubmit(){
	$('#registerForm').submit();
	
}

</script>
<form id="registerForm" action="registerWorker" method="POST" ></form>


<div style="text-align:center; font-size:16pt ; font-weight:bold" >근로자 등록 관리</div>
<div class="btn btn-primary" style="float:left;" onclick="selectSubmit()"><span class="glyphicon glyphicon-ok" ></span> 등록</div>
<table id="workerTable" data-click-to-select="true"   data-search="true" class="table table-borderd">
	<thead>
		<tr>			 
			<th data-field="cont_name" class="text-center">업체</th>
			<th data-field="name" class="text-center">이름</th>			 
			<th data-field="tname" class="text-center">직종</th>
			<th data-field="phone" class="text-center">H.P</th>
			<th data-field="hiredate" class="text-center">채용일</th>
			<th data-field="edudate" class="text-center">교육일</th>
			<th data-field="edu_image_src" class="text-center">교육이미지</th>
		</tr>
	</thead>
</table>

         
   
<%@ include file="IncludeBottom.jsp"%>
 