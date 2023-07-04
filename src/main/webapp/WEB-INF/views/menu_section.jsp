<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>



<script>

var CUR_SITE_ID = '${userLoginInfo.site_id}' || '${mobile_site_id}';

$(document).ready(function() {
	getSectionGroupList();	
});
function getSectionGroupList() {
	$.ajax({
		type: "GET",
        url: './manage/getSectionGroupList',
        data: {
        	site_id: CUR_SITE_ID
        },
        async: true,
        cache: false,
		success: function (list, status) {
			$('#groupTable').bootstrapTable();
			if(list.length > 0) {				
				for(var i=0; i < list.length; i++) {
					list[i].no = i+1;
					var sectionList = list[i].section_member;
					list[i].last_update_time = list[i].last_update_time.replace(".0", "");
					list[i].member = '';
					if(sectionList.length > 0) {
						for(var j=0; j < sectionList.length; j++) {
							// 밀폐수조
							if(sectionList[j].type == 1) {
								list[i].member += '<div class="member"><span class="section"><i class="fa-regular fa-square"></i> #'+sectionList[j].section+'</span> - '+ sectionList[j].name +'</div>';
							}
							// 일반수조 
							else {
								list[i].member += '<div class="member"><span class="section"><i class="fa-regular fa-square"></i> #'+sectionList[j].name+'</span></div>';
							}							
						}						
					}					
					
					list[i].change_group_name = '<div class="btn icon-default" onclick="updateGroupName('+list[i].id+')"><i class="fa-regular fa-pen-to-square"></i></div>';
					list[i].allocate_section = '<div class="btn icon-primary" onclick="overlaySectionList('+list[i].id+',\''+list[i].group_name+'\')"><i class="fa-solid fa-clipboard"></i></div>';
					list[i].delete_section = '<div class="btn icon-danger" onclick="cancelGroup('+list[i].id+')"><i class="fa-solid fa-trash"></i></div>';
					
				
					//console.log(sectionList);
					list[i].modify =
						'<div class="btn icon-primary" style="float: left; width: 33.3%;" title="그룹명 변경" style="background-color: #2dc74e;" onclick="updateGroupName('+list[i].id+')"><i class="fa-regular fa-pen-to-square"></i></div>' +
						'<div class="btn icon-default" style="float: left; width: 33.3%;" title="소속구역 할당" onclick="overlaySectionList('+list[i].id+',\''+list[i].group_name+'\')"><i class="fa-solid fa-clipboard"></i></div>' + 
						'<div class="btn icon-danger" style="float: left; width: 33.3%;" title="소속구역 할당" onclick="cancelGroup('+list[i].id+')"><i class="fa-solid fa-trash"></i></div>';
				}				
				$('#groupTable').bootstrapTable('load', list );	            	
			}else{
				$('#groupTable').bootstrapTable('load', [] );
			}
        }
    });
}

function updateGroupName(id) {
	var name;
	name = prompt('변경할 그룹명을 적어주세요');   
	
	if(name.length > 0) {
		$.ajax({
			type : "POST",
			url : "./group/name/update",            
			data : {
				id : id, 
				name : name,
				updater_id: '${userLoginInfo.id}'
			},
			async: true,
			cache : false,         
			success : function(json, status){
				alert('그룹명 변경이 완료되었습니다.');		
				window.location.reload();
			}
		});
	}
	else {
		alert('취소하었습니다');
	}
}
	


function overlaySectionList(id, group_name) {
	$('#modal_sectionList').click();
	openSectionGroupList(id, group_name);
}

function openSectionGroupList(id, group_name) {
	//alert("openSectionGroupList: " + id + "/" + group_name);
	$.ajax({
		type : "GET",
		url : "./section/group/list",            
		data : {
        	site_id: CUR_SITE_ID
		},
		async: true,
		cache : false,         
		success : function(list, status){
			$('#sectionTable').bootstrapTable();
			if(list.length > 0) {				
				for(var i=0; i < list.length; i++) {
					if(list[i].id == id) {
						list[i].select = 
							'<div class="btn btn-danger" onclick="selectMemeber('+id+',\''+group_name+'\','+list[i].section+', 2)">' +
							'<i class="fa-solid fa-xmark"></i>' +
							'</div>';					
					}
					else {
						list[i].select = 
							'<div class="btn btn-primary" onclick="selectMemeber('+id+',\''+group_name+'\','+list[i].section+', 1)">' +
								'<i class="fa-solid fa-check"></i>' +
							'</div>';
					}
					
					if(list[i].section_name != "") {
						list[i].section_name = '<div class="member"><span class="section"><i class="fa-regular fa-square"></i> #'+list[i].section+'</span> - '+ list[i].section_name +'</div>';	
					}
					else {
						list[i].section_name = '<div class="member"><span class="section"><i class="fa-regular fa-square"></i> #'+list[i].section+'</span></div>';
					}
					
					
					if(list[i].id == 0) {
						list[i].group_text = '<div class="label label-primary""></div>';
					}
					else {
						list[i].group_text = '<div class="label label-success"><span style="font-weight:600;">'+ list[i].group_name + '</span></div>';	
					}
				}				
				$('#sectionTable').bootstrapTable('load', list );	            	
			}else{
				$('#sectionTable').bootstrapTable('load', [] );
			}
		}
	});
}

function selectMemeber(id, group_name, section, type) {
	var input; 	
	// 선택
	if(type == 1) {		
		input = confirm(group_name + "그룹에 " + section + '번 구역을 그룹에 배정하시겠습니까?');	
		if(input){
			 $.ajax({
					type : "POST",
					url : "./select/section/group",
					data : {
						site_id: CUR_SITE_ID,
						id : id,
						section : section,
						updater_id: '${userLoginInfo.id}'
					},
					cache : false,
					async : false,
					success : function(json,status) {
						alert('배정이 완료되었습니다.');	
						openSectionGroupList(id, group_name);
					}
				}); 
		}
		else{			
			alert('취소하었습니다');
		}
	}
	// 삭제
	else {
		input = confirm(section + '번 구역을 ' + group_name + ' 에서 삭제하시겠습니까?');	
		if(input){
			 $.ajax({
					type : "POST",
					url : "./delete/section/group",
					data : {
						site_id: CUR_SITE_ID,
						id : id,
						section : section,
						updater_id: '${userLoginInfo.id}'
					},
					cache : false,
					async : false,
					success : function(json,status) {
						alert('삭제가 완료되었습니다.');	
						openSectionGroupList(id, group_name);
					}
				}); 
		}
		else{			
			alert('취소하었습니다');
		}
	}

}


function cancelGroup(id) {
	var input; 	
	input = confirm('해당 그룹을 삭제하시겠습니까? 소속구역이 초기화 됩니다.');
	if(input){
		 $.ajax({
				type : "POST",
				url : "./delete/group",
				data : {
					site_id: CUR_SITE_ID,
					id : id
				},
				cache : false,
				async : false,
				success : function(json,status) {
					alert('그룹이 삭제 완료되었습니다.');	
					window.location.reload();
				}
			}); 
	}
}

function viewSubmit() {
	var input; 	
	input = confirm("그룹을 생성하시겠습니까?");	
	if(input){
		var name;
		name = prompt('변경할 그룹명을 적어주세요');   
		if(name.length > 0) {
			$.ajax({
				type : "POST",
				url : "./group/name/insert",            
				data : {
					site_id: CUR_SITE_ID,
					writer_id: '${userLoginInfo.id}',
					group_name : name					
				},
				async: true,
				cache : false,         
				success : function(json, status){
					alert('그룹이 등록되었습니다.');		
					window.location.reload();
				}
			});
		}
	}
	else {
		alert('취소하었습니다');
	}
}

function reload() {
	window.location.reload();
}

</script>

<style>

	.member {
		color: #333;
		font-size: 0.9em;
	 	font-weight: 600;
	}

	.member > span{
		color: #5cb85c;
	    font-weight: 600;	
	}
	
</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">구역 그룹 관리</div>
	
	<div class="content_button_box content-item" >
		<div class="btn btn-default" onclick="viewSubmit()"><i class="fa-regular fa-registered"></i> 등록</div>
	</div>
	
	<div class="content_summary_box content-item">
		<i class="fa-solid fa-star" style="color:#ff3547;"></i> 하부 구역이 연결되어있는 경우 해당 관리페이지에서 세팅을 하면 구역간 이동이 가능합니다.
	</div>

	<div class="content_table_box content-item">
		<table id="groupTable"  data-search="true" data-pagination="true" 
			data-page-size="25" data-page-list="[10,25,All]" data-sort-name="[name]" data-filter-control="true"
			data-page-number="1" class="table table-bordered col-xs-12 table-hover table-striped">	
			<thead>
				<tr>
					<th class="text-center show-web" data-field="no">순번</th>
					<th class="text-center" data-field="group_name" data-sortable="true">그룹명</th>
					<th class="text-center" data-field="member">소속구역</th>     
					<th class="text-center show-web" data-field="writer_name">작성자</th>
					<th class="text-center show-web" data-field="change_group_name">그룹명 변경</th>
					<th class="text-center show-web" data-field="allocate_section">구역 할당</th>
					<th class="text-center show-web" data-field="delete_section">그룹 삭제</th>
					<th class="text-center show-mobile" data-field="modify">그룹 삭제</th>
					<th class="text-center show-web" data-field="updater_name">수정자</th>
					<th class="text-center show-web" data-field="last_update_time">수정 시간</th>     
				</tr>
			</thead>
			
			<tbody>                
	
			</tbody>
		</table>
	</div>
	
</div> <!-- content-wrapper END -->

<div id="form_group">
	<form id="searchForm" action="contList" method="POST">
		<input id="s_site_id" name="site_id" type="hidden">
	</form>
	<form id="registerContForm" action="registerCont" method="POST" >
		<input id="u_tar_site_id" type="hidden" name="tar_site_id"/>
		<input id="cont_id" type="hidden" name="cont_id"/>
	</form>
</div>

<div style="display:none" id="modal_sectionList" data-toggle="modal" data-target="#section_modal"></div>
<div class="modal fade" id="section_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
   			<div class="modal-header" style="font-size: 1.5em;">
   				<div>
   					소속 구역 배정
   				</div>
   			
				<div>
					<div id="__close_btn" class="btn btn-default margin-top" data-dismiss="modal" onclick="reload()"><i class="fa-regular fa-square-check"></i> 적용</div>
				</div>
   			</div>
			<div class="modal-body" style="font-size: 0.9em;">
        		<div class="col-sm-12">	
					<div class="tab-content" style="height:750px;overflow:scroll"  >
						<table id="sectionTable" data-click-to-select="true" data-search="true" class="table table-bordered">
							<thead>
								<tr>		
								 	<th data-field="select" class="text-center">선택</th>			
								 	<th data-field="section_name" class="text-center" data-sortable="true" data-filter-control="select" >구역 정보</th>		
								 	<th data-field="group_text" class="text-center" data-sortable="true" >소속</th>
								</tr>
							</thead>
						</table>
		    		</div><!-- tap-content -->
				 </div>		  
      		</div><!-- modal-body-->     
    	</div>
  	</div>
</div>

<%@ include file="IncludeBottom.jsp"%>