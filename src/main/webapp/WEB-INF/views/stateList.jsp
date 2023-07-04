<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.0.3/vue.js"></script>

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
				name : '',
				color : '',
				u_id : ''
			}
		}    
	});
}

function initInputValue(){
	app_data.insert.name = '';
	app_data.insert.color = '';
	app_data.insert.u_id = '${userLoginInfo.id}';
}

function openInputModal(){
	$('#inputModal').modal('show');
	initInputValue();
}

function insertWState() {
	
	var color = app_data.insert.color.toUpperCase();
	var name = app_data.insert.name;
	
	var check = true;
	
	if(color.length == 6) {		
		var rgEx_hex = /^[0-9A-F]{6}$/i;
		if(rgEx_hex.test(color)) {
			alert("입력값 정상 확인");	
		} else {
			alert("색상 값을 다시 확인해주세요");
			check = false;
		}	 
	}
	else {
		alert("색상 값을 다시 확인해주세요");
		check = false;
	}
	
	if(check) {
		$.ajax({
			type : "POST",
			url : "./manage/insertWorkState",
			traditional: true,
			async:false,
			data : {			 
				"site_id" : site_id,
				"color" : color,
				"name" : name
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
	else {
		alert("유효성 검사 불합격: " + color);	
		return;
	}
}

function deleteWState(id) {
	$.ajax({
		type : "POST",
		url : "./manage/deletetWorkState",
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

<div>
	<form id="printForm" action="printDailyValueList" method="POST">
		<input id="print_pg" type="hidden" name="place_gubun" />
	</form>
</div>

<div class="col-xs-8" style="margin-left: 17%;">
	<div class="list_title">작업공종 관리</div>
	<div class="row no-gutters">
		<div class="btn btn-primary" style="float:right;" onclick="openInputModal();"><span class="glyphicon glyphicon-pencil"></span> 공종 추가</div>	
	</div>

	<table id="wListTable" data-toggle="table" data-search="true" data-pagination="true" data-page-size="25" data-page-list="[10, 25, 50, 100, All]"
		 data-sort-name="[input_date]" data-filter-control="true" class="table table-bordered table-hover table-striped">
		<thead>
			<tr>
				<th data-field="no" data-sortable="true" class="text-center">No.</th>
				<th data-field="gubun" data-sortable="true" class="text-center">공종</th>
				<th data-field="t_name" data-sortable="true" class="text-center">색상</th>
				<th data-field="writer_user_name" data-sortable="true" class="text-center">작성자</th>
				<th data-field="write_time" class="text-center">변경시간</th>
				<th data-field="btn_delete" class="text-center">삭제</th>
			</tr>
		</thead>
		
		<tbody>
	
 			<c:forEach var="vo" items="${sList}" varStatus="idx">
		     	<tr>	     		
		        	<td class="text-center">${idx.index + 1}</td>  		    
		        	<td class="text-center">${vo.name}</td>    
		        	<td class="text-center">
		        		<div style="background: #${vo.color}">${vo.color}</div>
		        	</td>       	
		        	<td class="text-center">${vo.writer_name}</td>
		        	
		        	<td class="text-center">${vo.write_time}</td>	
		        	
					<td class="text-center">
						 <c:choose>
				   			<c:when test="${vo.id > 0}">
					   			<div class="btn btn-danger" onclick="deleteWState(${vo.id})"><span class="glyphicon glyphicon-remove"></span></div>		   					
					   		</c:when>
			   				<c:otherwise>
				   				삭제 불가
			   				</c:otherwise>
			   			</c:choose>
					</td>		     
		      </tr>
	   		</c:forEach>
	   	
		</tbody>
		
	</table>
</div>
<div id="form_group">
	<form id="printDaily" action="printDailyList" method="POST">
		<input type="hidden" name="site_id" value="${userLoginInfo.site_id}"/>
	</form>	
</div>
				
<div id="app_data">
	<div class="modal fade" id="inputModal" tabindex="-1" role="dialog" aria-labelledby="inputModal" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
		        <div class="modal-header" style="text-align:center">
		           	<b id="inputModal_title" style="font-size:1.2vw">공종 추가</b>
					<button type="button" class="close" data-dismiss="modal" ><i class="fa fa-close" aria-hidden="true"></i></button><br>
		        </div>
		        <div class="modal-body text-center">
					<table class="table table-bordered table-hover table-striped">
					  	<tr>
		       	        	<th class="text-center">색상</th>
		       	        	<td>
		       					<input id="color" v-model.lazy="insert.color" class="form-control" placeholder="FAFAFA"></input>
		       	        	</td>
		       	        </tr>
		       	        <tr>
		       	        	<th class="text-center">값</th>
		       	        	<td>
		       	        		<input id="name" v-model.lazy="insert.name" class="form-control" placeholder="전기"></input>
		       	        	</td>		       
		       	        </tr>
			    	</table>
			     	<div id="view_button" class="text-right" style="padding-right:5px">		       
						<div class="btn btn-default margin-top" onclick="insertWState()"><span class="glyphicon glyphicon-pencil" ></span> 입력</div>
					    <div class="btn btn-default margin-top" data-dismiss="modal" onclick=""><span class="glyphicon glyphicon-remove" ></span> 닫기</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
 
<%@ include file="IncludeBottom.jsp"%>