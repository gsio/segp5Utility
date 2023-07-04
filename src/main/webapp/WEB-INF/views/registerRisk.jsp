<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script type="text/javascript" src="js/risk/register.js"></script>

<script type="text/javascript" src="js/bootstrapTable1.21.0/bootstrap-table-group-by.min.js"></script>
<script type="text/javascript" src="js/bootstrapTable1.21.0/bootstrap-table.min.js"></script>

<link rel="stylesheet" href="css/risk/register.css">

<link rel="stylesheet" href="css/bootstrapTable1.21.0/bootstrap-table-group-by.css">
<link rel="stylesheet" href="css/bootstrapTable1.21.0/bootstrap-table.min.css">

<link href="https://unpkg.com/bootstrap-table@1.21.0/dist/extensions/group-by-v2/bootstrap-table-group-by.css" rel="stylesheet">
<link href="https://unpkg.com/bootstrap-table@1.21.0/dist/bootstrap-table.min.css" rel="stylesheet">

<script src="https://unpkg.com/bootstrap-table@1.21.0/dist/bootstrap-table.min.js"></script>
<script src="https://unpkg.com/bootstrap-table@1.21.0/dist/extensions/group-by-v2/bootstrap-table-group-by.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/Sortable/1.14.0/Sortable.min.js"></script>

<script>

var isUpdate = ${update};

// 결재
var CUR_APPROAVAL_LIST = [];
var CUR_APPROAVAL_LINE_LIST = [];

// 점검
var CUR_CHECKER_LIST = [];
var CUR_CHECKER_LINE_LIST = [];

// 요인
var CUR_FACTOR_LIST = [];
var CUR_SELECT_FACTOR_LIST = [];

// View Factor
var CUR_VIEW_FACTOR_CODE = [];

// Factor Index
var G_FACTOR_INDEX = 0;

$(document).ready(function() {
	
	if(isUpdate == true) {
		$("#content_title").html("위험성평가 수정");
		$('#riskRegisterForm').attr('action', 'updateRiskData');
		setArray();
		setFactorItem();		
		checkDetail();
		
		$('#search_cont_id').val(${riskVO.cont_id}).prop("selected", true);
	}
	else {
		$("#content_title").html("위험성평가 등록");
		$('#riskRegisterForm').attr('action', 'insertRiskData');		
		initArray();
		$('#risk_start').val(getTodayDate());
		$('#risk_end').val(getPreDate(14));		
	} 
	
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
				$(".datepicker").css('border','1px solid #ced4da');
			}
    	}
    }).attr('readonly','readonly');
	
	$('#factorTable').on('click-row.bs.table', function (index, row, $element) {
		overlayFactorDetailList(row);
	});
	
	$(document).on("click", ".work-activity-box > div .btn-work-state", function() {
		var element_id = $(this).parent().parent().attr('id');
		var task1 = $(this).parent().parent().attr('id').split("-")[2];
	
		input = confirm('삭제 하시겠습니까?');
		if(input) {
			
			var code = $("#risk-factor-code-" + task1).val();	
			
			if(code != null) {				
				
				CUR_VIEW_FACTOR_CODE.forEach((item, index) => {
					if(item.code == code) {
						CUR_VIEW_FACTOR_CODE.splice(index, 1);	
					}
				});
				
				CUR_SELECT_FACTOR_LIST.forEach((item, index) => {
					if(item.code == code) {
						CUR_SELECT_FACTOR_LIST.splice(index, 1);	
						$('#factorSelectTable').bootstrapTable('load', CUR_SELECT_FACTOR_LIST);	
					}
				});
			}

			$("[id^="+element_id+"]").parent().remove();
		
		}			
    });
	
	$(document).on("click", ".item-title-box > div .btn-add-work-step", function() {
		var task_index = $(this).parent().parent().parent().attr('id').split("-")[2];	
		setTask2FactorItem(task_index);
    });
	
	$(document).on("click", ".item-title-box > div .btn-delete-work-step", function() {
		var element_id = $(this).parent().parent().parent().attr('id');	
		if(element_id != null) {			
			var check_child_count = $(this).parent().parent().parent().parent().children().length;
			if(check_child_count == 1) {
				input = confirm('삭제 하시겠습니까?');
				if(input) {
					$("[id^="+element_id+"]").parent().parent().parent().parent().remove();	
				}				
			}
			else {
				$("[id^="+element_id+"]").remove();	
			}
		}		
    });

	$(document).on("click", ".content-sub-add-item-box > div .btn-add-risk-content", function() {
		var task1 = $(this).parent().parent().parent().attr('id').split("-")[2];
		var task2 = $(this).parent().parent().parent().attr('id').split("-")[3];
		setTask3FactorItem(task1, task2);
    });
	
	$(document).on("click", ".content-sub-add-item-box > div .btn-delete-risk-content", function() {
		var element_id = $(this).parent().parent().parent().attr('id');		
		if(element_id != null) {
			
			var parent_count = $(this).parent().parent().parent().parent().parent().parent().parent().children().length;
			var parent_class = $(this).parent().parent().parent().parent().parent().attr('class');
			var check_child_count = $(this).parent().parent().parent().parent().children().length;		
			
			console.log("parent_count: " + parent_count);
			console.log("check_child_count: " + check_child_count);
			if(check_child_count == 1) {				
				if(parent_count == 1) {
					input = confirm('삭제 하시겠습니까?');
					if(input) {
						$("[id^="+element_id+"]").parent().parent().parent().parent().parent().parent().parent().remove();	
					}
				}
				else {
					$("[id^="+element_id+"]").parent().parent().parent().remove();	
				}
			}
			else {
				$("[id^="+element_id+"]").remove();	
			}
		}
    });
	
	$(document).on("click", ".content-management_method-box > div .btn-add-manage-content", function() {		
		var task1 = $(this).parent().parent().parent().children().children().last().attr('id').split("-")[2];
		var task2 = $(this).parent().parent().parent().children().children().last().attr('id').split("-")[3];
		var task3 = $(this).parent().parent().parent().children().children().last().attr('id').split("-")[4];
		setTask4FactorItem(task1, task2, task3);
    });
	
	$(document).on("click", ".content-management_method-box > div .btn-delete-manage-content", function() {
		var element_id = $(this).parent().parent().parent().children().children().last().attr('id');
		if(element_id != null) {
			var check_child_count = $(this).parent().parent().parent().children().last().children().length;
			if(check_child_count < 2) {
				alert("관리방안은 최소 1개 있어야합니다.")
			}
			else {
				$("[id^="+element_id+"]").remove();	
			}
		}
    });
	
	$(document).on("keyup", ".input_rating_info", function() {
		var element_id = $(this).parent().parent().parent().parent().attr('id');
		var task1 = element_id.split("-")[2];
		var task2 = element_id.split("-")[3];
		var task3 = element_id.split("-")[4];
		
		var rating_id = task1+"-"+task2+"-"+task3;
		
		var frequency = $('#reg_frequency_'+rating_id).val();
		var strength = $('#reg_strength_'+rating_id).val();
		var risk_rating = frequency * strength;
		
		if(risk_rating == 9) {
			$('#reg_rating_'+rating_id).text('상');
			$('#reg_risk-rating_'+rating_id).val('상');
		}
		else if(risk_rating >= 4){
			$('#reg_rating_'+rating_id).text('중');
			$('#reg_risk-rating_'+rating_id).val('중');
		} 
		else {
			$('#reg_rating_'+rating_id).text('하');
			$('#reg_risk-rating_'+rating_id).val('하');
		}		
    });
	
	$(document).on("click", ".management_method-box", function() {
		$(this).sortable();
	});
});

// 객체 확인 후에 정보 APPEND 할 function에 집어 넣기 
function checkFactorSelectArray() {
	
	for(var i=0; i<CUR_SELECT_FACTOR_LIST.length; i++) {		
		
		var insertCheck = true
		
		CUR_VIEW_FACTOR_CODE.forEach((item, index) => {
			if(item.code == CUR_SELECT_FACTOR_LIST[i].code) {
				console.log(item.code + "이미 View에 있는 정보입니다.");
				insertCheck = false
				return;
			}
		});			
		
		if(insertCheck) {			
			CUR_VIEW_FACTOR_CODE.push(CUR_SELECT_FACTOR_LIST[i]);		
			setAdjustInView(CUR_SELECT_FACTOR_LIST[i], G_FACTOR_INDEX);
			G_FACTOR_INDEX++;
		}
		else {
			console.log(CUR_SELECT_FACTOR_LIST[i].code + "View 추가 Block");
		}
	}
}

function setAdjustInView(data, index) {
	
	console.log(data);
		
	var html = '';
	html += '<div class="risk-factor-item">';
		html += '<div id="risk-factor-'+index+'" class="work-activity-box">';
			html += '<div class="title_box">';
				html += '<input type="hidden" id="risk-factor-waCode-'+index+'" name="riskFactorList['+index+'].wa_code" value="'+data.wa_code+'" class="form-control">';				
				html += '<div class="title">작업활동</div>';
				html += '<div class="btn-work-state btn icon-danger">';
					html += '<i class="fa-solid fa-circle-minus"></i>';
				html += '</div>';
			html += '</div>'; // title_box END
			
			html += '<div class="content">';
				html += '<div class="content-item-box">';
				html += '<input type="hidden" id="reg_class-name_'+index+'" name="riskFactorList['+index+'].class_name" value="'+data.class_name+'" class="form-control" type="text" maxlength="100">';
					html += '<div class="item-title required">세부 공종</div>';
					html += '<div class="item-content">';					
						html += data.class_name;
					html += '</div>';
				html += '</div>'; //content-item-box END
				
				html += '<div class="content-add-item-box">';
				
					html += '<div id="risk-factor-'+index+'-0" class="risk-step-item">';
						html += '<input type="hidden" id="risk-factor-code-'+index+'" name="riskFactorList['+index+'].code" value="'+data.code+'" class="form-control">';
						html += '<div class="item-title-box">';
							html += '<div class="item-title">작업 단계</div>';
							html += '<div class="item-btn-box">';
								html += '<div class="btn-add-work-step btn icon-default">';
									html += '<i class="fa-solid fa-circle-plus"></i>';
								html += '</div>';
								html += '<div class="btn-delete-work-step btn icon-danger">';
									html += '<i class="fa-solid fa-circle-minus"></i>';
								html += '</div>';
							html += '</div>';
						html += '</div>'; // item-title-box END
						
						html += '<div class="item-content-wrap">';
						
							html += '<div class="item-content-box">';
								html += '<input type="hidden" id="reg_work-content_'+index+'" name="riskFactorList['+index+'].wsList[0].work_content" value="'+data.work_content+'" class="form-control" type="text" maxlength="100">';
								html += '<div class="item-content">';
									html += '<div class="title required">작업 내용</div>';
									html += '<div class="content">';
										html += data.work_content;
									html += '</div>';
								html += '</div>';
							html += '</div>';
						
							html += '<div class="item-content-box">';
								html += '<div class="item-content">';
									html += '<div class="title">사용 장비</div>';
									html += '<div class="content">';
										html += '<input id="reg_equip-name_'+index+'-0" name="riskFactorList['+index+'].wsList[0].equip_name" class="form-control">';
									html += '</div>';
								html += '</div>';
							html += '</div>';
							
							html += '<div class="item-content-box">';
								html += '<div class="item-content">';
									html += '<div class="title">장비 수</div>';
									html += '<div class="content">';
										html += '<input id="reg_equip-cnt_'+index+'-0" value="0" onkeydown="return onlyNumber(event)" onkeyup="removeChar(event)" name="riskFactorList['+index+'].wsList[0].equip_cnt" class="form-control">';
									html += '</div>';
								html += '</div>';
							html += '</div>';
							
							html += '<div class="item-content-box">';
								html += '<div class="item-content">';
									html += '<div class="title required">작업 기간</div>';
									html += '<div class="content">';
										html += '<input id="reg_work-start_'+index+'-0" class="sub-datepicker form-control" name="riskFactorList['+index+'].wsList[0].work_start"/>';
										html += '<div style="font-weight: 600; float: left;">&nbsp~&nbsp</div>';
										html += '<input id="reg_work-end_'+index+'-0" class="sub-datepicker form-control" name="riskFactorList['+index+'].wsList[0].work_end"/>';										
									html += '</div>';
								html += '</div>';
							html += '</div>';
						
							html += '<div class="item-content-box">';
								html += '<div class="item-content">';
									html += '<div class="title required">작업 위치</div>';
									html += '<div class="content">';
										html += '<input id="reg_section-name_'+index+'-0" name="riskFactorList['+index+'].wsList[0].section_name" class="form-control">';
									html += '</div>';
								html += '</div>';
							html += '</div>';
							
							html += '<div class="item-content-box">';
								html += '<div class="item-content">';
									html += '<div class="title">인원 수</div>';
									html += '<div class="content">';
										html += '<input id="reg_engineer-cnt_'+index+'-0" value="1" onkeydown="return onlyNumber(event)" onkeyup="removeChar(event)" name="riskFactorList['+index+'].wsList[0].engineer_cnt" class="form-control">';
									html += '</div>';
								html += '</div>'
							html += '</div>';
							
							html += '<div class="content-sub-add-item-box">';
							
								html += '<div id="risk-factor-'+index+'-0-0" class="risk-item">';
									html += '<div class="sub-title-box">';
										html += '<div class="title">위험성평가</div>';
										html += '<div class="btn-box">';
											html += '<div class="btn-add-risk-content btn icon-default">';
												html += '<i class="fa-solid fa-circle-plus"></i>';
											html += '</div>';
											html += '<div class="btn-delete-risk-content btn icon-danger">';
												html += '<i class="fa-solid fa-circle-minus"></i>';
											html += '</div>';
										html += '</div>';
									html += '</div>'; // sub-title-box END
									
									html += '<div class="sub-content-box">';
										html += '<div class="item-content">';
											html += '<input type="hidden" id="reg_risk-content_'+index+'-0-0" value="'+data.code_name+'" name="riskFactorList['+index+'].wsList[0].rfList[0].risk_content" class="form-control">';
											html += '<div class="title required">위험요인</div>';
											html += '<div class="content">'+ data.code_name +'</div>';
										html += '</div>';
										html += '<div class="item-content">';
											html += '<input type="hidden" id="reg_damage-form_'+index+'-0-0" value="'+data.damage_form+'" name="riskFactorList['+index+'].wsList[0].rfList[0].damage_form" class="form-control">';
											html += '<div class="title">피해형태</div>';
											html += '<div class="content">'+ data.damage_form +'</div>';
										html += '</div>';										
										
										html += '<div class="item-content">';
											html += '<div class="title">빈도</div>';
											html += '<div class="content">';
												html += '<input id="reg_frequency_'+index+'-0-0" value="'+data.frequency+'" onkeydown="return onlyNumberOver3(event)" onkeyup="removeCharOver3(event)" name="riskFactorList['+index+'].wsList[0].rfList[0].frequency" class="form-control input_rating_info">';
											html += '</div>'
										html += '</div>';
										html += '<div class="item-content">';
											html += '<div class="title">강도</div>';
											html += '<div class="content">';
												html += '<input id="reg_strength_'+index+'-0-0" value="'+data.strength+'" onkeydown="return onlyNumberOver3(event)" onkeyup="removeCharOver3(event)" name="riskFactorList['+index+'].wsList[0].rfList[0].strength" class="form-control input_rating_info">';
											html += '</div>'
										html += '</div>';
										html += '<div class="item-content">';
											html += '<div class="title">등급</div>';
											html += '<input type="hidden" id="reg_risk-rating_'+index+'-0-0" value="'+data.rating+'" name="riskFactorList['+index+'].wsList[0].rfList[0].rating" class="form-control">';
											html += '<div id="reg_rating_'+index+'-0-0" class="content">'+ data.rating +'</div>';
										html += '</div>';
										
										html += '<div class="content-management_method-box">';
										
											html += '<div class="management_method-title-box">';
												html += '<div class="title required">관리방안</div>';
												html += '<div class="btn-box">';
													html += '<div class="btn-add-manage-content btn icon-default">';
														html += '<i class="fa-solid fa-circle-plus"></i>';
													html += '</div>';
													html += '<div class="btn-delete-manage-content btn icon-danger">';
														html += '<i class="fa-solid fa-circle-minus"></i>';
													html += '</div>';
												html += '</div>';
											html += '</div>'; // management_method-title-box END
											
											html += '<div class="management_method-box">';
											
												for(var i=0; i<data.mList.length; i++) {		
													html += '<div id="risk-factor-'+index+'-0-0-'+i+'" class="management_method_item">';													
														html += '<div class="item-content">';
															html += '<input type="hidden" value="'+data.mList[i]+'" id="reg_management-method_'+index+'-0-0-'+i+'" name="riskFactorList['+index+'].wsList[0].rfList[0].mpList['+i+'].content" class="form-control">';
															html += '<div class="content">'+ data.mList[i]+'</div>';
														html += '</div>';
													html += '</div>';
												} 
												
											html += '</div>'; // management_method-box END
										
										html += '</div>'; // content-management_method-box END
										
									html += '</div>'; // sub-content-box END				
									
								html += '</div>'; // risk-item END
								
							html += '</div>'; // content-sub-add-item-box END		
							
						html += '</div>'; // item-content-wrap END
						
					html += '</div>'; // risk-step-item END
				
				
				
				html += '</div>'; // content-add-item-box END
				
			html += '</div>'; // content END		
		html += '</div>'; // work-activity-box END		
	html += '</div>'; // risk-factor-item END	
	
	$("#risk-factor-item-box").append(html);
	
	setDatePicker(index, 0);
}


// 작업활동 전체 추가 (ReNewer)
function setTotalFactorItem() {
	
	//var G_FACTOR_INDEX = $("#risk-factor-item-box > div").length + 1;
	
	var html = '';
	
	html += '<div class="risk-factor-item">';
		html += '<div id="risk-factor-'+G_FACTOR_INDEX+'" class="work-activity-box">';
			html += '<div class="title_box">';
				html += '<div class="title">작업활동</div>';
				html += '<div class="btn-work-state btn icon-danger">';
					html += '<i class="fa-solid fa-circle-minus"></i>';
				html += '</div>';
			html += '</div>'; // title_box END
			
			html += '<div class="content">';
				html += '<div class="content-item-box">';
					html += '<div class="item-title required">세부 공종</div>';
					html += '<div class="item-content">';
						html += '<input id="reg_class-name_'+G_FACTOR_INDEX+'" name="riskFactorList['+G_FACTOR_INDEX+'].class_name" class="form-control" type="text" value="" maxlength="100" >';
					html += '</div>';
				html += '</div>'; //content-item-box END
				
				html += '<div class="content-add-item-box">';
				
				html += '</div>'; // content-add-item-box END
				
			html += '</div>'; // content END		
		html += '</div>'; // work-activity-box END		
	html += '</div>'; // risk-factor-item END		
	
	$('#risk-factor-item-box').append(html);
	
	setTask2FactorItem(G_FACTOR_INDEX);
	
	G_FACTOR_INDEX++;
					
}

function setTask2FactorItem(task1) {
	
	//alert("TASK 1: " + task1);	
	
	var task2Id = $('#risk-factor-'+task1+' .content-add-item-box > div').last().attr('id');	
	var task2 = 0;
	
	if(task2Id != null) {
		task2 = task2Id.split("-")[3];	
		task2++;
	}
	//alert("TASK 2: " + task2);	
	
	var html = '';
	
	html += '<div id="risk-factor-'+task1+'-'+task2+'" class="risk-step-item">';
	
		html += '<div class="item-title-box">';
			html += '<div class="item-title">작업 단계</div>';
			html += '<div class="item-btn-box">';
				html += '<div class="btn-add-work-step btn icon-default">';
					html += '<i class="fa-solid fa-circle-plus"></i>';
				html += '</div>';
				html += '<div class="btn-delete-work-step btn icon-danger">';
					html += '<i class="fa-solid fa-circle-minus"></i>';
				html += '</div>';
			html += '</div>';
		html += '</div>'; // item-title-box END
		
		html += '<div class="item-content-wrap">';
		
			html += '<div class="item-content-box">';
				html += '<div class="item-content">';
					html += '<div class="title required">작업 내용</div>';
					html += '<div class="content">';
						html += '<input id="reg_work-content_'+task1+'-'+task2+'" name="riskFactorList['+task1+'].wsList['+task2+'].work_content" class="form-control">';
					html += '</div>';
				html += '</div>';
			html += '</div>';	
		
			html += '<div class="item-content-box">';
				html += '<div class="item-content">';
					html += '<div class="title">사용 장비</div>';
					html += '<div class="content">';
						html += '<input id="reg_equip-name_'+task1+'-'+task2+'" name="riskFactorList['+task1+'].wsList['+task2+'].equip_name" class="form-control">';
					html += '</div>';
				html += '</div>';
			html += '</div>';
			
			html += '<div class="item-content-box">';
				html += '<div class="item-content">';
					html += '<div class="title">장비 수</div>';
					html += '<div class="content">';
						html += '<input id="reg_equip-cnt_'+task1+'-'+task2+'" value="0" onkeydown="return onlyNumber(event)" onkeyup="removeChar(event)" name="riskFactorList['+task1+'].wsList['+task2+'].equip_cnt" class="form-control">';
					html += '</div>';
				html += '</div>';
			html += '</div>';
			
			html += '<div class="item-content-box">';
				html += '<div class="item-content">';
					html += '<div class="title required">작업 기간</div>';
					html += '<div class="content">';
						html += '<input id="reg_work-start_'+task1+'-'+task2+'" class="sub-datepicker form-control" name="riskFactorList['+task1+'].wsList['+task2+'].work_start"/>';
						html += '<div style="font-weight: 600; float: left;">&nbsp~&nbsp</div>';
						html += '<input id="reg_work-end_'+task1+'-'+task2+'" class="sub-datepicker form-control" name="riskFactorList['+task1+'].wsList['+task2+'].work_end"/>';										
					html += '</div>';				
				html += '</div>';
			html += '</div>';
		
			html += '<div class="item-content-box">';
				html += '<div class="item-content">';
					html += '<div class="title required">작업 위치</div>';
					html += '<div class="content">';
						html += '<input id="reg_section-name_'+task1+'-'+task2+'" name="riskFactorList['+task1+'].wsList['+task2+'].section_name" class="form-control">';
					html += '</div>';
				html += '</div>';
			html += '</div>';
			
			html += '<div class="item-content-box">';
				html += '<div class="item-content">';
					html += '<div class="title">인원 수</div>';
					html += '<div class="content">';
						html += '<input id="reg_engineer-cnt_'+task1+'-'+task2+'" value="1" onkeydown="return onlyNumber(event)" onkeyup="removeChar(event)" name="riskFactorList['+task1+'].wsList['+task2+'].engineer_cnt" class="form-control">';
					html += '</div>'					
				html += '</div>'
			html += '</div>';
			
			html += '<div class="content-sub-add-item-box">';
				
			html += '</div>'; // content-sub-add-item-box END		
			
		html += '</div>'; // item-content-wrap END
		
	html += '</div>'; // risk-step-item END
	
	$('#risk-factor-'+task1+' .content-add-item-box').append(html);
	
	setDatePicker(task1, task2);
	
	setTask3FactorItem(task1, task2);
	
	
}

function setDatePicker(task1, task2) {	
	
	$("#reg_work-start_"+task1+"-"+task2).datepicker({
		yearRange: '-1:+1',    
    	showSecond: true,
    	onClose: function (selectedDate) {
	
    	}
    }).attr('readonly','readonly');
	
	$("#reg_work-end_"+task1+"-"+task2).datepicker({
		yearRange: '-1:+1',    
    	showSecond: true,
    	onClose: function (selectedDate) {
	
    	}
    }).attr('readonly','readonly');	
	
}

function setTask3FactorItem(task1, task2) {
	
	var task3Id = $('#risk-factor-'+task1+'-'+task2+' .content-sub-add-item-box> div').last().attr('id');	
	var task3 = 0;
	
	if(task3Id != null) {
		task3 = task3Id.split("-")[4];	
		task3++;
	}
	
	var html = '';
	
	html += '<div id="risk-factor-'+task1+'-'+task2+'-'+task3+'" class="risk-item">';
		html += '<div class="sub-title-box">';
			html += '<div class="title">위험성평가</div>';
			html += '<div class="btn-box">';
				html += '<div class="btn-add-risk-content btn icon-default">';
					html += '<i class="fa-solid fa-circle-plus"></i>';
				html += '</div>';
				html += '<div class="btn-delete-risk-content btn icon-danger">';
					html += '<i class="fa-solid fa-circle-minus"></i>';
				html += '</div>';
			html += '</div>';
		html += '</div>'; // sub-title-box END
		
		html += '<div class="sub-content-box">';
			html += '<div class="item-content">';
				html += '<div class="title required">위험요인</div>';
				html += '<div class="content">';
					html += '<input id="reg_risk-content_'+task1+'-'+task2+'-'+task3+'" name="riskFactorList['+task1+'].wsList['+task2+'].rfList['+task3+'].risk_content" class="form-control">';
				html += '</div>'	
			html += '</div>';
			html += '<div class="item-content">';
				html += '<div class="title">피해형태</div>';
				html += '<div class="content">';
					html += '<input id="reg_damage-form_'+task1+'-'+task2+'-'+task3+'" name="riskFactorList['+task1+'].wsList['+task2+'].rfList['+task3+'].damage_form" class="form-control">';
				html += '</div>'
			html += '</div>';
			html += '<div class="item-content">';
				html += '<div class="title">빈도</div>';
				html += '<div class="content">';
					html += '<input id="reg_frequency_'+task1+'-'+task2+'-'+task3+'" value="1" onkeydown="return onlyNumberOver3(event)" onkeyup="removeCharOver3(event)" name="riskFactorList['+task1+'].wsList['+task2+'].rfList['+task3+'].frequency" class="form-control input_rating_info">';
				html += '</div>'
			html += '</div>';
			html += '<div class="item-content">';
				html += '<div class="title">강도</div>';
				html += '<div class="content">';
					html += '<input id="reg_strength_'+task1+'-'+task2+'-'+task3+'" value="1" onkeydown="return onlyNumberOver3(event)" onkeyup="removeCharOver3(event)" name="riskFactorList['+task1+'].wsList['+task2+'].rfList['+task3+'].strength" class="form-control input_rating_info">';
				html += '</div>'
			html += '</div>';
			html += '<div class="item-content">';
				html += '<div class="title">등급</div>';
				html += '<div class="content">';
					html += '<input type="hidden" id="reg_risk-rating_'+task1+'-'+task2+'-'+task3+'" value="하" name="riskFactorList['+task1+'].wsList['+task2+'].rfList['+task3+'].rating" class="form-control">';
					html += '<div id="reg_rating_'+task1+'-'+task2+'-'+task3+'">하</div>';
				html += '</div>';
			html += '</div>';
		html += '</div>';
			
			html += '<div class="content-management_method-box">';
			
			html += '<div class="management_method-title-box">';
				html += '<div class="title required">관리방안</div>';
				html += '<div class="btn-box">';
					html += '<div class="btn-add-manage-content btn icon-default">';
						html += '<i class="fa-solid fa-circle-plus"></i>';
					html += '</div>';
					html += '<div class="btn-delete-manage-content btn icon-danger">';
						html += '<i class="fa-solid fa-circle-minus"></i>';
					html += '</div>';
				html += '</div>';
			html += '</div>'; // management_method-title-box END
			
			html += '<div class="management_method-box">';
				
			html += '</div>'; // management_method-box END
			
		html += '</div>'; // content-management_method-box END
			
		html += '</div>'; // sub-content-box END				
		
	html += '</div>'; // risk-item END
	
	$('#risk-factor-'+task1+'-'+task2+' .content-sub-add-item-box').append(html);
	
	setTask4FactorItem(task1, task2, task3);
	
}

function setTask4FactorItem(task1, task2, task3) {
	
	var task4 = $('#risk-factor-'+task1+'-'+task2+'-'+task3+' .management_method-box > div').length;		
	
	var html = '';

	html += '<div id="risk-factor-'+task1+'-'+task2+'-'+task3+'-'+task4+'" class="management_method_item">';
		html += '<div class="item-content">';
		html += '<div class="content">';
			html += '<input id="reg_management-method_'+task1+'-'+task2+'-'+task3+'-'+task4+'" name="riskFactorList['+task1+'].wsList['+task2+'].rfList['+task3+'].mpList['+task4+'].content" class="form-control">';
		html += '</div>'
	html += '</div>';
	
	$('#risk-factor-'+task1+'-'+task2+'-'+task3+' .management_method-box').append(html);
	
	setSortableManageContent();
}

function setSortableManageContent() {
	
//$(".management_method-box").sortable();

	
}

</script>


<script>

/* ----------------------------------------------------------------- */
function setArray() {
	
	$('#approvalLineTable').bootstrapTable();
	$('#checkerLineTable').bootstrapTable();
	$('#factorSelectTable').bootstrapTable();	
	
	<c:forEach var="vo" items="${riskVO.approvalList}" varStatus="idx">	
	
		var user = {
			id : '${vo.user_id}',
			cont_name : '${vo.cont_name}',
			name : '${vo.name}',
			role_name : '<b>${vo.role_name}</b>',
			grade : '${vo.grade}'
		}	
		
		CUR_APPROAVAL_LINE_LIST.push(user);
		
		$('#approvalLineTable').bootstrapTable('append', user);		
	
	</c:forEach>
	
	
	<c:forEach var="vo" items="${riskVO.checkList}" varStatus="idx">	
	
		var user = {
			id : '${vo.user_id}',
			cont_name : '${vo.cont_name}',
			name : '${vo.name}',
			role_name : '${vo.role_name}',
			phone : '${vo.phone}',
			grade : '${vo.grade}'
		}	
	
		CUR_CHECKER_LINE_LIST.push(user);
	
	$('#checkerLineTable').bootstrapTable('append', user);		

	</c:forEach>

}	

function setFactorItem() {
	
	var html = '';
	<c:forEach var="wa" items="${riskVO.riskFactorList}" varStatus="l1">	
	html += '<div class="risk-factor-item">';
		html += '<div id="risk-factor-'+${l1.index}+'" class="work-activity-box">';
			html += '<div class="title_box">';
				html += '<input type="hidden" id="risk-factor-waCode-${l1.index}" name="riskFactorList[${l1.index}].wa_code" value="${wa.wa_code}" class="form-control">';				
				html += '<div class="title">작업활동</div>';
				html += '<div class="btn-work-state btn icon-danger">';
					html += '<i class="fa-solid fa-circle-minus"></i>';
				html += '</div>';
			html += '</div>'; // title_box END		
						
			html += '<div class="content">';
				html += '<div class="content-item-box">';				
					html += '<div class="item-title required">세부 공종</div>';
					html += '<div class="item-content">';
						html += '<input id="reg_class-name_${l1.index}" name="riskFactorList[${l1.index}].class_name" value="${wa.class_name}" class="form-control" type="text" maxlength="100">';					
					html += '</div>';
				html += '</div>'; //content-item-box END
				
				html += '<div class="content-add-item-box">';
				
				<c:forEach var="ws" items="${riskVO.riskFactorList[l1.index].wsList}" varStatus="l2">
					html += '<div id="risk-factor-${l1.index}-${l2.index}" class="risk-step-item">';
					html += '<input type="hidden" id="risk-factor-code-${l1.index}" name="riskFactorList[${l1.index}].code" value="" class="form-control">';
						html += '<div class="item-title-box">';
							html += '<div class="item-title">작업 단계</div>';
							html += '<div class="item-btn-box">';
								html += '<div class="btn-add-work-step btn icon-default">';
									html += '<i class="fa-solid fa-circle-plus"></i>';
								html += '</div>';
								html += '<div class="btn-delete-work-step btn icon-danger">';
									html += '<i class="fa-solid fa-circle-minus"></i>';
								html += '</div>';
							html += '</div>';
						html += '</div>'; // item-title-box END
						
						html += '<div class="item-content-wrap">';
						
							html += '<div class="item-content-box">';														
								html += '<div class="item-content">';
									html += '<div class="title required">작업 내용</div>';
									html += '<div class="content">';
										html += '<input id="reg_work-content_${l1.index}" name="riskFactorList[${l1.index}].wsList[${l2.index}].work_content" value="${ws.work_content}" class="form-control" type="text" maxlength="100">';
									html += '</div>';
								html += '</div>';
							html += '</div>';
							
							html += '<div class="item-content-box">';
								html += '<div class="item-content">';
									html += '<div class="title">사용 장비</div>';
									html += '<div class="content">';
										html += '<input id="reg_equip-name_${l1.index}-${l2.index}" name="riskFactorList[${l1.index}].wsList[${l2.index}].equip_name" value="${ws.equip_name}" class="form-control">';
									html += '</div>';
								html += '</div>';
							html += '</div>';
							
							html += '<div class="item-content-box">';
								html += '<div class="item-content">';
									html += '<div class="title">장비 수</div>';
									html += '<div class="content">';
										html += '<input id="reg_equip-cnt_${l1.index}-${l2.index}" value="${ws.equip_cnt}" onkeydown="return onlyNumber(event)" onkeyup="removeChar(event)" name="riskFactorList[${l1.index}].wsList[${l2.index}].equip_cnt" class="form-control">';
									html += '</div>';
								html += '</div>';
							html += '</div>';
							
							html += '<div class="item-content-box">';
								html += '<div class="item-content">';
									html += '<div class="title required">작업 기간</div>';
									html += '<div class="content">';
										html += '<input id="reg_work-start_${l1.index}-${l2.index}" value="${ws.work_start}" class="sub-datepicker form-control" name="riskFactorList[${l1.index}].wsList[${l2.index}].work_start"/>';
										html += '<div style="font-weight: 600; float: left;">&nbsp~&nbsp</div>';
										html += '<input id="reg_work-end_${l1.index}-${l2.index}" value="${ws.work_end}" class="sub-datepicker form-control" name="riskFactorList[${l1.index}].wsList[${l2.index}].work_end"/>';										
									html += '</div>';
								html += '</div>';
							html += '</div>';
						
							html += '<div class="item-content-box">';
								html += '<div class="item-content">';
									html += '<div class="title required">작업 위치</div>';
									html += '<div class="content">';
										html += '<input id="reg_section-name_${l1.index}-${l2.index}" value="${ws.section_name}" name="riskFactorList[${l1.index}].wsList[${l2.index}].section_name" class="form-control">';
									html += '</div>';
								html += '</div>';
							html += '</div>';
							
							html += '<div class="item-content-box">';
								html += '<div class="item-content">';
									html += '<div class="title">인원 수</div>';
									html += '<div class="content">';
										html += '<input id="reg_engineer-cnt_${l1.index}-${l2.index}" value="${ws.engineer_cnt}" onkeydown="return onlyNumber(event)" onkeyup="removeChar(event)" name="riskFactorList[${l1.index}].wsList[${l2.index}].engineer_cnt" class="form-control">';
									html += '</div>';
								html += '</div>'
							html += '</div>';
							
							html += '<div class="content-sub-add-item-box">';
							<c:forEach var="rf" items="${riskVO.riskFactorList[l1.index].wsList[l2.index].rfList}" varStatus="l3">							
							
								html += '<div id="risk-factor-${l1.index}-${l2.index}-${l3.index}" class="risk-item">';
									html += '<div class="sub-title-box">';
										html += '<div class="title">위험성평가</div>';
										html += '<div class="btn-box">';
											html += '<div class="btn-add-risk-content btn icon-default">';
												html += '<i class="fa-solid fa-circle-plus"></i>';
											html += '</div>';
											html += '<div class="btn-delete-risk-content btn icon-danger">';
												html += '<i class="fa-solid fa-circle-minus"></i>';
											html += '</div>';
										html += '</div>';
									html += '</div>';
									
									html += '<div class="sub-content-box">';
									
										html += '<div class="item-content">';											
											html += '<div class="title required">위험요인</div>';
											html += '<div class="content">';
												html += '<input id="reg_risk-content_${l1.index}-${l2.index}-${l3.index}" value="${rf.risk_content}" name="riskFactorList[${l1.index}].wsList[${l2.index}].rfList[${l3.index}].risk_content" class="form-control">';
											html += '</div>';
										html += '</div>';
										html += '<div class="item-content">';											
											html += '<div class="title">피해형태</div>';
											html += '<div class="content">';
												html += '<input id="reg_damage-form_${l1.index}-${l2.index}-${l3.index}" value="${rf.damage_form}" name="riskFactorList[${l1.index}].wsList[${l2.index}].rfList[${l3.index}].damage_form" class="form-control">';
											html += '</div>';
										html += '</div>';	
										html += '<div class="item-content">';
											html += '<div class="title">빈도</div>';
											html += '<div class="content">';
												html += '<input id="reg_frequency_${l1.index}-${l2.index}-${l3.index}" value="${rf.frequency}" onkeydown="return onlyNumberOver3(event)" onkeyup="removeCharOver3(event)" name="riskFactorList[${l1.index}].wsList[${l2.index}].rfList[${l3.index}].frequency" class="form-control input_rating_info">';
											html += '</div>'
										html += '</div>';
										html += '<div class="item-content">';
											html += '<div class="title">강도</div>';
											html += '<div class="content">';
												html += '<input id="reg_strength_${l1.index}-${l2.index}-${l3.index}" value="${rf.frequency}" onkeydown="return onlyNumberOver3(event)" onkeyup="removeCharOver3(event)" name="riskFactorList[${l1.index}].wsList[${l2.index}].rfList[${l3.index}].strength" class="form-control input_rating_info">';
											html += '</div>'
										html += '</div>';
										html += '<div class="item-content">';
											html += '<div class="title">등급</div>';
											html += '<input type="hidden" id="reg_risk-rating_${l1.index}-${l2.index}-${l3.index}" value="${rf.rating}" name="riskFactorList[${l1.index}].wsList[${l2.index}].rfList[${l3.index}].rating" class="form-control">';
											html += '<div id="reg_rating_${l1.index}-${l2.index}-${l3.index}" class="content">${rf.rating}</div>';
										html += '</div>';
										
										html += '<div class="content-management_method-box">';
										
											html += '<div class="management_method-title-box">';
												html += '<div class="title required">관리방안</div>';
												html += '<div class="btn-box">';
													html += '<div class="btn-add-manage-content btn icon-default">';
														html += '<i class="fa-solid fa-circle-plus"></i>';
													html += '</div>';
													html += '<div class="btn-delete-manage-content btn icon-danger">';
														html += '<i class="fa-solid fa-circle-minus"></i>';
													html += '</div>';
												html += '</div>';
											html += '</div>';
											
											html += '<div class="management_method-box">';
											<c:forEach var="mp" items="${riskVO.riskFactorList[l1.index].wsList[l2.index].rfList[l3.index].mpList}" varStatus="l4">
											
												html += '<div id="risk-factor-${l1.index}-${l2.index}-${l3.index}-${l4.index}" class="management_method_item">';													
													html += '<div class="item-content">';
														html += '<input type="hidden" value="${mp.content}" id="reg_management-method_${l1.index}-${l2.index}-${l3.index}-${l4.index}" name="riskFactorList[${l1.index}].wsList[${l2.index}].rfList[${l3.index}].mpList[${l4.index}].content" class="form-control">';
														html += '<div class="content">${mp.content}</div>';
													html += '</div>';
												html += '</div>'; // management_method_item END
											
											</c:forEach>
											html += '</div>'; // management_method-box END
										
										html += '</div>'; // content-management_method-box END
									
									html += '</div>'; // sub-content-box END
								
								html += '</div>'; // risk-item END
							
							</c:forEach>
							html += '</div>'; // content-sub-add-item-box END
							
						html += '</div>'; // item-content-wrap END
						
					html += '</div>'; // risk-step-item END
					
				</c:forEach>
				html += '</div>'; // content-add-item-box END				
			html += '</div>'; // content END
		html += '</div>'; // work-activity-box END
	html += '</div>'; // risk-factor-item END
	
	G_FACTOR_INDEX++;
	</c:forEach>
	
	$("#risk-factor-item-box").append(html);
	
	$(".sub-datepicker").datepicker({
		yearRange: '-1:+1',    
    	showSecond: true,
    	onClose: function (selectedDate) {
	
    	}
    }).attr('readonly','readonly');
	
	setSortableManageContent();
}


function initArray() {

	$('#approvalLineTable').bootstrapTable();
	$('#checkerLineTable').bootstrapTable();
	$('#factorSelectTable').bootstrapTable();	
	
	if(CUR_APPROAVAL_LINE_LIST.length == 0) {
		
		var user = {
			id : '${userLoginInfo.id}',
			cont_name : '${userLoginInfo.cont_name}',
			name : '${userLoginInfo.name}',
			role_name : '<b>작성자</b>',
			phone : '${userLoginInfo.phone}',
			grade : '${userLoginInfo.grade}'
		}	
		
		CUR_APPROAVAL_LINE_LIST.push(user);
		
		$('#approvalLineTable').bootstrapTable('append', user);			
	}	
}

function getApprovalList(cont_id) {
	
	$('#selectAUserContId').val(cont_id);
	
	CUR_APPROAVAL_LIST = [];
	
	$('#approvalUserTable').bootstrapTable();
	
	<c:forEach var="item" items="${userAList}" varStatus="idx">	
	
		var id = '${item.id}';
		var sort_cont_id = ${item.cont_id};
		var cont_name = '${item.cont_name}';
		var name = '${item.name}';
		var grade = '${item.grade}';
		var role_name = '${item.role_name}';
		var phone = '${item.phone}';	
		
		var user = {
			id : id,
			cont_name : cont_name,
			name : name,
			role_name : role_name,
			phone : phone,
			grade : grade
		}	
		
		if(cont_id > 0) {
			if(cont_id == sort_cont_id) {
				CUR_APPROAVAL_LIST.push(user);	
			}
		}
		else {
			CUR_APPROAVAL_LIST.push(user);	
		}
		
	</c:forEach>
	
	if(CUR_APPROAVAL_LIST.length > 0) {
		$('#approvalUserTable').bootstrapTable('load', CUR_APPROAVAL_LIST);		
		$('#approvalUserTable tr').click();
	}
	else {
		$('#approvalUserTable').bootstrapTable('load', []);
	}
}

function getCheckerList(cont_id) {
	
	$('#selectCUserContId').val(cont_id);
	
	CUR_CHECKER_LIST = [];
	
	$('#checkerUserTable').bootstrapTable();
	
	<c:forEach var="item" items="${userCList}" varStatus="idx">	
	
		var id = '${item.id}';
		var sort_cont_id = ${item.cont_id};
		var cont_name = '${item.cont_name}';
		var name = '${item.name}';
		var grade = '${item.grade}';
		var role_name = '${item.role_name}';
		var phone = '${item.phone}';	
		
		var user = {
			id : id,
			cont_name : cont_name,
			name : name,
			role_name : role_name,
			phone : phone,
			grade : grade
		}	
		
		if(cont_id > 0) {
			if(cont_id == sort_cont_id) {
				CUR_CHECKER_LIST.push(user);	
			}
		}
		else {
			CUR_CHECKER_LIST.push(user);	
		}
		
	</c:forEach>
	
	if(CUR_CHECKER_LIST.length > 0) {
		$('#checkerUserTable').bootstrapTable('load', CUR_CHECKER_LIST);		
		$('#checkerUserTable tr').click();
	}
	else {
		$('#checkerUserTable').bootstrapTable('load', []);
	}
}

function getFactorList(type) {
	
	CUR_FACTOR_LIST = [];
	
	$('#factorTable').bootstrapTable();	
	
	var parent_code = '';
	var title = '';
	
	<c:forEach var="item" items="${factorList}" varStatus="idx">	
	
		var sort_type = ${item.ws_id};
		var code = '${item.code}';
		var code_name = '${item.code_name}';
		var code_level = ${item.code_level};
		var code_level_one = '${item.code_level_one}';
		var code_level_two = '${item.code_level_two}';
		var code_level_three = '${item.code_level_three}';		
		
		var factor = {
			code : code,
			code_name : code_name,
			code_level : code_level,
			code_level_one : code_level_one,
			code_level_two : code_level_two,
			code_level_three : code_level_three,
			title: title
		}	
		
		if(code_level_three == 0) {			
			title = '${item.class_code}' + '0' + code_level_two + ' - ' + '${item.code_name}';			
		}
		else {
			if(type > 0) {
				if(type == sort_type) {
					CUR_FACTOR_LIST.push(factor);	
				}
			}
			else {
				CUR_FACTOR_LIST.push(factor);	
			}
		}
		
	</c:forEach>	

	if(CUR_FACTOR_LIST.length > 0) {
		$('#factorTable').bootstrapTable('load', CUR_FACTOR_LIST);		
		$('#factorTable tr').click();
	}
	else {
		$('#factorTable').bootstrapTable('load', []);
	}
}

function adjustApprovalLine() {	
	
	var html = "";
	
	$("#approval-item-box").html('');
	
	var index = $("#approval-item-box > .approval-item").length;
	
	CUR_APPROAVAL_LINE_LIST.forEach((item, index) => {		
		
		html += '<div class="approval-item">';
		html += '<input type="hidden" id="reg_approval_'+index+'" name="approvalList['+index+'].user_id" value="'+item.id+'" class="form-control">';
		html += '<div class="role-name">'+item.role_name+'</div>';
		html += '<div class="name">'+item.name+'</div>';
		html += '<div class="approval-state">-</div>';
		html += '<div class="approval-time">-</div>';
		html += '</div>';
		
		index++;
		
	});	
	
	$("#approval-item-box").html(html);
	
}


function adjustCheckerLine() {	
	
	var html = "";
	
	$("#check-item-box").html('');
	
	var index = $("#check-item-box > .check-item").length;
	
	CUR_CHECKER_LINE_LIST.forEach((item, index) => {
		
		html += '<div class="check-item">';
		html += '<input type="hidden" id="reg_check_'+index+'" name="checkList['+index+'].user_id" value="'+item.id+'" class="form-control">';
		html += '<div class="cont-name">'+item.cont_name+'</div>';
		html += '<div class="role-name">'+item.role_name+'</div>';
		html += '<div class="name">'+item.name+'</div>';
		html += '<div class="phone">'+item.phone+'</div>';
		html += '</div>';
		
		index++;
		
	});	
	
	$("#check-item-box").html(html);
	
}




</script>

<style>


</style>

<form:form id="riskRegisterForm" class="form-horizontal" method="POST" modelAttribute="riskVO" enctype="multipart/form-data" autocomplete="off">

<div id="content-wrapper">
	<div id="content_title" class="content-item"></div>
	
	<form:input type="hidden" path="id" value="${riskVO.id}"/>			
	<form:input type="hidden" path="site_id" value="${userLoginInfo.site_id}"/>
		
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="history.back(-1)"><i class="fa-solid fa-arrow-rotate-left"></i> 취소</div>	
		<c:if test="${!update}">
			<div class="btn btn-default" onclick="submitRisk()"><i class="fa-regular fa-registered"></i> 등록</div>
		</c:if>   
		<c:if test="${update}">
			<div class="btn btn-danger" onclick="deleteRiskData()"><i class="fa-solid fa-trash"></i> 삭제</div>
			<div class="btn btn-default" onclick="submitRisk()"><i class="fa-regular fa-pen-to-square"></i> 수정</div>
		</c:if>
	</div>
	
	<div class="content_report_box content-item">
	
		<div class="info-title">기본정보</div>
	
		<div class="default-item-box item-box">
			<div class="title required">업 체</div>
			<div class="content">
			<c:choose>
				<c:when test="${sessionScope.userLoginInfo.cont_type == 0}"> 	
					<form:select id="search_cont_id" name="cont_id" class="form-control select-content" path="cont_id">								
						<c:forEach var="cont" items="${contList}" varStatus="idx">
							<option value="${cont.id}">${cont.name}</option>
						</c:forEach>	  
					</form:select>
				</c:when>
				<c:otherwise>
					<form:select name="cont_id" class="form-control select-content" path="cont_id">				
						<option value="${sessionScope.userLoginInfo.cont_id}">${sessionScope.userLoginInfo.cont_name}</option>
					</form:select>
					<input id="cont_id" type="hidden" name="cont_id" value="${sessionScope.userLoginInfo.cont_id}"/>
				</c:otherwise>	
			</c:choose>
			</div>
		</div>
		
		<div class="default-item-box item-box">
			<div class="title required">작성자</div>
			<div class="content">
				<span style="font-weight: 600;">${userLoginInfo.cont_name}</span> - ${userLoginInfo.name}
			</div>
		</div>
		
		<div class="default-item-box item-box">
			<div class="title">평가내용</div>
			<div class="content">
				<form:input class="form-control" path="content" maxlength="100" placeholder="" />
			</div>
		</div>
		
		<div class="default-item-box item-box">
			<div class="title required">평가기간</div>
			<div class="content">
				<form:input id="risk_start" class="datepicker form-control" path="risk_start" />
				<div style="font-weight: 600; float: left;">&nbsp~&nbsp</div>
				<form:input id="risk_end" class="datepicker form-control" path="risk_end" />
			</div>
		</div>
		
		<div class="unique-wrap item-box">
			<div class="info-title-box">
				<div class="title">결재</div>
				<div class="btn icon-primary" onclick="overlayApprovalList()"><i class="fa-regular fa-pen-to-square"></i></div>
			</div>
			<div id="approval-item-box" class="unique-item-box">
				<c:if test="${update}">	
					<c:if test="${riskVO.approvalList.size() > 0}">	
						<c:forEach var="approval" items="${riskVO.approvalList}" varStatus="loop">
							<c:if test="${approval != null}">
							<div class="approval-item">
							<input type="hidden" id="reg_approval_${loop.index}" name="approvalList[${loop.index}].user_id" value="${approval.user_id}" class="form-control">
							<div class="role-name"><b>${approval.role_name}</b></div>
							<div class="name">${approval.name}</div>
							<c:choose>
								<c:when test="${approval.confirm == 'Y'}">	
									<div class="approval-state" style="color: #2710d5;">승인</div>
									<div class="approval-time">${approval.last_update_time.replace(".0", "")}</div>
								</c:when>
								<c:when test="${approval.confirm == 'R'}">
									<div class="approval-state" style="color: #dc3545;">반려</div>
									<div class="approval-time">${approval.last_update_time.replace(".0", "")}</div>									
								</c:when>
								<c:otherwise>
									<div class="approval-state">-</div>
									<div class="approval-time">-</div>
								</c:otherwise>
							</c:choose>
							</div>
							</c:if>							
						</c:forEach>
					</c:if>
				</c:if>			
				<c:if test="${!update}">		
				<div class="approval-item">
					<input type="hidden" id="reg_approval_0" name="approvalList[0].user_id" value="${userLoginInfo.id}" class="form-control">
					<div class="role-name"><b>작성자</b></div>
					<div class="name">${userLoginInfo.name}</div>
					<div class="approval-state">-</div>
					<div class="approval-time">-</div>
				</div>
				</c:if>
			</div>
		</div>
		
		<div class="unique-wrap item-box">
			<div class="info-title-box">
				<div class="title">점검대상자</div>
				<div class="btn icon-primary" onclick="overlayCheckerList()"><i class="fa-regular fa-pen-to-square"></i></div>
			</div>
			<div id="check-item-box" class="unique-item-box">
				<c:if test="${update}">	
					<c:if test="${riskVO.checkList.size() > 0}">	
						<c:forEach var="checker" items="${riskVO.checkList}" varStatus="loop">
							<c:if test="${checker != null}">
							<div class="check-item">
								<input type="hidden" id="reg_check_${loop.index}" name="checkList[${loop.index}].user_id" value="${checker.user_id}" class="form-control">
								<div class="cont-name">${checker.cont_name}</div>
								<div class="role-name">${checker.role_name}</div>
								<div class="name">${checker.name}</div>
								<div class="phone">${checker.phone}</div>
							</div>
							</c:if>							
						</c:forEach>
					</c:if>
				</c:if>
			</div>
		</div>
		
		<div class="unique-wrap item-box">
			<div class="info-title-box">
				<div class="title">위험성평가표</div>
				<div>
					<div class="btn icon-default" onclick="setTotalFactorItem()"><i class="fa-solid fa-circle-plus"></i></div>
					<div class="btn icon-primary" onclick="overlayFactorList()"><i class="fa-regular fa-pen-to-square"></i></div>
				</div>				
			</div>
			<div id="risk-factor-item-box" class="unique-item-box">

			</div>
		</div>
		
		<div class="unique-wrap item-box">
			<div class="info-title">추가 메모사항</div>
			<div id="remark-item-box" class="unique-item-box">
				<form:textarea id="remark" style="height: 100%;" class="form-control" path="remark" rows="15" />
			</div>
		</div>
		
	</div>
	
</div>

</form:form>

<div id="form_group">
	<form id="updateForm" action="registerRisk" method="POST">
		<input id="risk_id" type="hidden" name="id" value="${riskVO.id}" />
	</form>
</div>

<div style="display:none" id="modal_approvalList" data-toggle="modal" data-target="#approval_modal"></div>
<div class="modal fade" id="approval_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
  		<div class="modal-content">
	   		<div class="modal-header"> 
	   			<div class="title">
   					결재선
   				</div>    
				<div class="btn-box">
					<div onclick="adjustApprovalLine()" class="btn icon-primary margin-top" data-dismiss="modal"><i class="fa-regular fa-registered"></i></div>
					<div class="btn icon-danger margin-top" data-dismiss="modal"><i class="fa-solid fa-xmark"></i></div>
				</div>
			</div>
			<div class="modal-body">
		     	<div class="user-list-wrap">
		     		<div class="title">
		     			항목리스트
		     		</div>
		     		<c:if test="${sessionScope.userLoginInfo.cont_type == 0}"> 	
		     		<div class="cont">
		     			<div class="sub-title">업체</div>
		     			<div class="select-box">
							<select id="selectAUserContId" name="cont_id" class="form-control select-content" onchange="changeApprovalUser()">
							<option value="-1">전체</option>
							<c:forEach var="cont" items="${contList}" varStatus="idx">
								<option value="${cont.id}">${cont.name}</option>
								</c:forEach>	  
							</select>
						</div>
					</div>
					</c:if>

		     		<div class="table">
		     			<table id="approvalUserTable" data-search="true" data-toggle="table" 
		     				data-group-by="true" data-group-by-field='["cont_name", "role_name"]'
		     				data-group-by-toggle="true" data-group-by-show-toggle-icon="true"
		     				data-checkbox-header="false"
							class="table table-bordered col-xs-12 table-hover table-striped" >
							<thead>
								<tr>		
									<th data-checkbox="true" class="text-center"></th>
									<th data-field="cont_name" class="text-center">업체</th>
									<th data-field="role_name" class="text-center">권한</th>
									<th data-field="name" class="text-center">이름</th>							
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>	     		
		     		</div>		     		
		     	</div>
		     	
		     	<div class="move-user-action-box">
		     		<div class="btn icon-info" onclick="addApprovalLine()"><i class="fa-solid fa-square-caret-down"></i></div>
		     		<div class="btn icon-info" onclick="deleteApprovalLine()" ><i class="fa-regular fa-square-caret-up"></i></div>
		     	</div>
	     	
		     	<div class="approval-list-wrap">
		     		<div class="title">
		     			결재선 정보
		     		</div>
		     		<div class="table">
		     			<table id="approvalLineTable" class="table table-bordered" >
							<thead>
								<tr>		
									<th data-checkbox="true" data-formatter="stateFormatter" class="text-center"></th>
									<th data-field="role_name" class="text-center">상신</th>
									<th data-field="cont_name" class="text-center">업체</th>
									<th data-field="grade" class="text-center">직책</th>
									<th data-field="name" class="text-center">이름</th>							
								</tr>
							</thead>
							<tbody>
							
							</tbody>
						</table>     		
		     		</div>		     		
		     	</div>
		     	
   			</div>
  		</div>
	</div>
</div>

<div style="display:none" id="modal_checkerList" data-toggle="modal" data-target="#checker_modal"></div>
<div class="modal fade" id="checker_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
  		<div class="modal-content">
	   		<div class="modal-header"> 
	   			<div class="title">
   					위험성평가 점검 대상자
   				</div>    
				<div class="btn-box">
					<div onclick="adjustCheckerLine()" class="btn icon-primary margin-top" data-dismiss="modal"><i class="fa-regular fa-registered"></i></div>
					<div class="btn icon-danger margin-top" data-dismiss="modal"><i class="fa-solid fa-xmark"></i></div>
				</div>
			</div>
			<div class="modal-body">
		     	<div class="user-list-wrap">
		     		<div class="title">
		     			항목리스트
		     		</div>
		     		<c:if test="${sessionScope.userLoginInfo.cont_type == 0}"> 	
		     		<div class="cont">
		     			<div class="sub-title">업체</div>
		     			<div class="select-box">
							<select id="selectCUserContId" name="cont_id" class="form-control select-content" onchange="changeCheckerUser()">
							<option value="-1">전체</option>
							<c:forEach var="cont" items="${contList}" varStatus="idx">
								<option value="${cont.id}">${cont.name}</option>
								</c:forEach>	  
							</select>
						</div>
					</div>
					</c:if>

		     		<div class="table">
		     			<table id="checkerUserTable" data-search="true" data-toggle="table" 
		     				data-group-by="true" data-group-by-field='["cont_name", "grade"]'
		     				data-group-by-toggle="true" data-group-by-show-toggle-icon="true"
		     				data-checkbox-header="false">
							<thead>
								<tr>		
									<th data-checkbox="true" class="text-center"></th>
									<th data-field="cont_name" class="text-center">업체</th>
									<th data-field="grade" class="text-center">직책</th>
									<th data-field="name" class="text-center">이름</th>							
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>	     		
		     		</div>		     		
		     	</div>
		     	
		     	<div class="move-user-action-box">
		     		<div class="btn icon-info" onclick="addCheckerLine()"><i class="fa-solid fa-square-caret-down"></i></div>
		     		<div class="btn icon-info" onclick="deleteCheckerLine()" ><i class="fa-regular fa-square-caret-up"></i></div>
		     	</div>
	     	
		     	<div class="checker-list-wrap">
		     		<div class="title">
		     			선택 리스트
		     		</div>
		     		<div class="table">
		     			<table id="checkerLineTable" class="table table-bordered" >
							<thead>
								<tr>		
									<th data-checkbox="true" class="text-center"></th>
									<th data-field="cont_name" class="text-center">업체</th>
									<th data-field="grade" class="text-center">직책</th>
									<th data-field="name" class="text-center">이름</th>							
								</tr>
							</thead>
							<tbody>
							
							</tbody>
						</table>     		
		     		</div>		     		
		     	</div>
		     	
   			</div>
  		</div>
	</div>
</div>

<div style="display:none" id="modal_factorList" data-toggle="modal" data-target="#factor_modal"></div>
<div class="modal fade" id="factor_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
  		<div class="modal-content">
	   		<div class="modal-header"> 
	   			<div class="title">
   					위험성평가 생성
   				</div>    
				<div class="btn-box">
					<div onclick="checkFactorSelectArray()" class="btn icon-primary margin-top" data-dismiss="modal"><i class="fa-regular fa-registered"></i></div>
					<div class="btn icon-danger margin-top" data-dismiss="modal"><i class="fa-solid fa-xmark"></i></div>
				</div>
			</div>
			<div class="modal-body">
		     	<div class="user-list-wrap">
		     		<div class="title">
		     			위험요인
		     		</div>
		     		
		     		<div class="cont">
		     			<div class="sub-title">항목</div>
		     			<div class="select-box">
							<select id="selectFactorType" name="cont_id" class="form-control select-content" onchange="changeFactorType()">
							<option value="-1">전체</option>
							<c:forEach var="fact" items="${factorTypeList}" varStatus="idx">
								<option value="${fact.id}">${fact.name}</option>
								</c:forEach>	  
							</select>
						</div>
					</div>
					
		     		<div class="table">
		     			<table id="factorTable" data-search="true" data-toggle="table" 
		     				data-group-by="true" data-group-by-field="title"
		     				data-group-by-toggle="true" data-group-by-show-toggle-icon="true"
							class="table table-bordered col-xs-12 table-hover table-striped" >
							<thead>
								<tr>	
									<th data-field="code" class="text-center">코드</th>
									<th data-field="code_name" class="text-center">대분류</th>					
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>	     		
		     		</div>		     		
		     	</div>
	     	
		     	<div class="factor-list-wrap">
		     		<div class="title-box">
			     		<div class="title">선택 리스트</div>
			     		<div class="btn-box">						
							<div class="btn icon-danger" onclick="deleteSelectFactor()"><i class="fa-solid fa-trash"></i></div>
						</div>
		     		</div>		     		
		     		<div class="table">
		     			<table id="factorSelectTable" class="table table-bordered" >
							<thead>
								<tr>		
									<th data-checkbox="true" class="text-center"></th>
									<th data-field="code" class="text-center">코드</th>
									<th data-field="code_name" class="text-center">코드명</th>				
								</tr>
							</thead>
							<tbody>
							
							</tbody>
						</table>     		
		     		</div>		     		
		     	</div>
		     	
   			</div>
  		</div>
	</div>
</div>

<div style="display:none" id="modal_factorDetailList" data-toggle="modal" data-target="#factorDetail_modal"></div>
<div class="modal fade" id="factorDetail_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
  		<div class="modal-content">
	   		<div class="modal-header"> 
	   			<div class="title">
   					선택하기
   				</div>    
				<div class="btn-box">
					<div onclick="adjustFactorDetail()" class="btn icon-primary margin-top" data-dismiss="modal"><i class="fa-regular fa-registered"></i></div>
					<div class="btn icon-danger margin-top" data-dismiss="modal"><i class="fa-solid fa-xmark"></i></div>
				</div>
			</div>
			<div class="modal-body">
		     	<div class="user-list-wrap">
		     		<div class="title">
		     			원하시는 코드를 선택 후 우측상단 "등록" 버튼을 클릭해주세요
		     		</div>
					
		     		<div class="table">
		     			<table id="factorDetailTable" data-toggle="table" 
							class="table table-bordered col-xs-12 table-hover table-striped" >
							<thead>
								<tr>	
									<th data-checkbox="true" class="text-center"></th>
									<th data-field="code" class="text-center">코드</th>
									<th data-field="code_name" class="text-center">위험요인</th>
									<th data-field="management_method" class="text-center">관리방안</th>					
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>	     		
		     		</div>		     		
		     	</div>		     	
   			</div>
  		</div>
	</div>
</div>


<%@ include file="IncludeBottom_renewal.jsp"%>
