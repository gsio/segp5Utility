<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>



<script>

var app_data;
const site_id = '${userLoginInfo.site_id}';

$(document).ready(function() {
	initVue();
	initInputValue();
});

function initVue(){
	app_data = new Vue({
		el: '#app_data',
		data:{
			insert: {		  
				t_name : '',
				gubun : 1,
				u_id : ''
			}
		}    
	});
}

function initInputValue(){
	app_data.insert.t_name = '';
	app_data.insert.gubun = 1;
	app_data.insert.u_id = '${userLoginInfo.id}';
}

function openInputModal(){
	$('#inputModal').modal('show');
	initInputValue();
}

function insertWtype() {
	$.ajax({
		type : "POST",
		url : "./manage/insertWorkType",
		traditional: true,
		async:false,
		data : {			 
			"site_id" : site_id,
			"gubun" : app_data.insert.gubun,
			"t_name" : app_data.insert.t_name
		},
		cache : false,
		success : function(json,status){
			var json_data  = JSON.parse(json);
			if(json_data.result == "true"){				
				alert('등록이 완료되었습니다');				
			}
			else {
				alert('error'); 
			}			
			window.location.reload();
		},
		error :null
	}); 
}

function deleteWtype(id) {
	$.ajax({
		type : "POST",
		url : "./manage/deletetWorkType",
		traditional: true,
		async:false,
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

</script>

<style>


</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">근로자 직종 관리</div>
	
	<div class="content_button_box content-item" >
		<div class="btn btn-default" onclick="openInputModal()"><i class="fa-regular fa-registered"></i> 등록</div>
	</div>
	
	<div class="content_data_count_box content-item">
		총 ${wList.size()} 개
	</div>
	
	<div class="content_table_box content-item">
		<table id="wListTable" data-toggle="table" data-search="true" data-pagination="true" data-page-size="25" data-page-list="[10, 25, 50, 100, All]"
		 	data-sort-name="[input_date]" data-filter-control="true" class="table table-bordered table-hover table-striped">
		 	
			<thead>
				<tr>
					<th data-field="no" data-sortable="true" class="text-center show-web">No.</th>
					<th data-field="gubun" data-sortable="true" class="text-center show-web">구분</th>
					<th data-field="t_name" data-sortable="true" class="text-center">직종</th>
					<th data-field="writer_user_name" data-sortable="true" class="text-center">작성자</th>
					<th data-field="write_time" class="text-center show-web">변경시간</th>
					<th data-field="btn_delete" class="text-center">삭제</th>
				</tr>
			</thead>
		
			<tbody>
				<c:forEach var="vo" items="${wList}" varStatus="idx">
			     	<tr>	     		
			        	<td class="text-center show-web">${idx.index + 1}</td>  		        	
						<c:choose>
							<c:when test="${vo.gubun == 1}">
			      	 			<td class="text-center show-web"><span style="font-weight:600; color:#6fa95b;"><i class="fa-solid fa-user"></i> 근로자</span></td>    
			      	 		</c:when>
							<c:otherwise>
				      	 		<td class="text-center show-web"><span style="font-weight:600; color:#666;"><i class="fa-solid fa-tractor"></i> 장비</span></td>
				      	 	</c:otherwise>
						</c:choose> 	      	 	
			        	<td class="text-center">${vo.t_name}</td>    
			        	<td class="text-center">${vo.name}</td>       	
			        	<td class="text-center show-web">${vo.write_time}</td>	
						<td class="text-center">
							<div class="btn icon-danger" onclick="deleteWtype(${vo.id})"><i class="fa-solid fa-trash"></i></div>
						</td>		     
			      </tr>
		   		</c:forEach>
			</tbody>
		
	</table>
	</div>
	
	
	
	
</div> <!-- content-wrapper END -->

<div style="display:none" id="modal_wType" data-toggle="modal" data-target="#inputModal"></div>
<div class="modal fade" id="inputModal" tabindex="-1" role="dialog" aria-labelledby="inputModal" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
	   		<div class="modal-header" style="font-size: 1.5em;"> 
	   			근로자 직종 추가     
	   		</div>
			<div class="modal-body">
	      		<div id="app_data">
		        	<div class="col-sm-12">		    	    	
			    		<table class="table table-bordered table-hover table-striped">
						  	<tr>
			       	        	<th class="text-center">구분</th>
			       	        	<td>
			       	        		<select id="gubun" v-model.lazy="insert.gubun" style="cursor:pointer; text-align:center;" class="form-control">
			       	        			<option value="1">근로자(직종)</option>
			       	        			<option value="2">장비(운전원)</option>		       	        		
			       	        		</select>
			       	        	</td>
			       	        </tr>
			       	        <tr>
			       	        	<th class="text-center">값</th>
			       	        	<td>
			       	        		<input id="t_name" v-model.lazy="insert.t_name" class="form-control"></input>
			       	        	</td>		       
			       	        </tr>
				    	</table>
					</div>	 					
					<div id="view_button" class="text-right" style="margin-top:15px; padding-right:5px">
						<div class="btn btn-default margin-top" onclick="insertWtype()"><i class="fa-regular fa-pen-to-square"></i> 등록</div>
						<div class="btn btn-danger margin-top" data-dismiss="modal" ><i class="fa-solid fa-xmark"></i> 닫기</div>
					</div>
	      		</div><!-- "app_data" -->	
			</div><!-- modal-body -->			
	    </div><!-- modal-content -->	
	</div><!-- modal-dialog -->	
</div>


<div id="form_group">

</div>

<%@ include file="IncludeBottom_renewal.jsp"%>