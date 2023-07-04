function submitRisk() {
	var input;
	
	if(isUpdate == true) {
		input = confirm('수정하시겠습니까?');
	}
	else {
		input = confirm('등록하시겠습니까?');
	}
	
	if(input) {	  
		
		var isOk = checkDetail();
		
		if(isOk) {					
			$('#riskRegisterForm').submit();	   
		}
		
	}
	else {
		return;
	}		
}

function checkDetail() {
	
	var isOk = true;
	
	if($("#risk_start").val().length < 1 || $("#risk_end").val().length < 1) {
		isOk = false;
		$(".datepicker").css('border','2px solid red');
	}
	else {
		$(".datepicker").css('border','1px solid #ced4da');
	}
	
	if($("#risk-factor-item-box").children().length < 1) {
		isOk = false;
		$("#risk-factor-item-box").css('border','2px solid red');
	}
	else {
		$("#risk-factor-item-box").css('border','1px solid #ced4da');
	}
	
	$('[id^=reg_]').each(function () {
		var reg_element_id = $(this).attr('id');
			
		var item_name = reg_element_id.split('_')[1];
		var id = reg_element_id.split('_')[2];
		var item_value = $(this).val();	
		
		/*
		console.log("[검사항목]: " + item_name);
		console.log("[검사대상]: " + id);
		console.log("[대상 값]: " + item_value);
		*/
		
		if(item_name == "class-name") {
			if(item_value.length < 1) {
				isOk = false;				
				$('#' + reg_element_id).parent().parent().css('border','2px solid red');
			}
			else {
				$('#' + reg_element_id).parent().parent().css('border','none');
			}
		}
		
		else if(item_name == "work-content") {
			if(item_value.length < 1) {
				isOk = false;				
				$('#' + reg_element_id).parent().parent().css('border','2px solid red');
			}
			else {
				$('#' + reg_element_id).parent().parent().css('border','none');
			}
		}
		
		else if(item_name == "work-start") {
			if(item_value.length < 10) {
				isOk = false;				
				$('#' + reg_element_id).css('border','2px solid red');				
			}
			else {
				$('#' + reg_element_id).css('border','none');
				
				if($("#reg_work-end_" + id).val().length < 10) {
					isOk = false;	
					$("#reg_work-end_" + id).css('border','2px solid red');	
				}
				else {
					$("#reg_work-end_" + id).css('border','none');
					
					if($("#reg_work-start_" + id).val() > $("#reg_work-end_" + id).val()) {
						isOk = false;	
						$("#reg_work-end_" + id).val('');
						$('#' + reg_element_id).parent().parent().css('border','2px solid red');
					}
					else {
						$('#' + reg_element_id).parent().parent().css('border','none');
					}
				}
			}
		}
		
		else if(item_name == "section-name") {
			if(item_value.length < 1) {
				isOk = false;				
				$('#' + reg_element_id).parent().parent().css('border','2px solid red');
			}
			else {
				$('#' + reg_element_id).parent().parent().css('border','none');
			}
		}
		
		else if(item_name == "risk-content") {
			if(item_value.length < 1) {
				isOk = false;				
				$('#' + reg_element_id).parent().parent().css('border','2px solid red');
			}
			else {
				$('#' + reg_element_id).parent().parent().css('border','none');
			}
		}
		
		else if(item_name == "management-method") {
			if(item_value.length < 1) {
				isOk = false;				
				$('#' + reg_element_id).parent().parent().css('border','2px solid red');
			}
			else {
				$('#' + reg_element_id).parent().parent().css('border','none');
			}
		}
		
	});
	
	if(isOk) {
		
	}	
	else {
		alert('항목을 다시 확인해주시기 바랍니다.');
	}
	
	return isOk;
}

function deleteRiskData() {	
	input = confirm('위험성평가를 삭제하시겠습니까?');
	if(input){
		$('#riskRegisterForm').attr('action', 'deleteRiskData');
		$('#riskRegisterForm').submit();
	}
}


/* ----------------------------------------------------------------- */
// 결재

function overlayApprovalList() {
	$('#modal_approvalList').click();
	getApprovalList(-1);
}

function changeApprovalUser(){
	var cont_id = $('#selectAUserContId').val();	
	getApprovalList(cont_id);
}

function addApprovalLine() {
	var user_array = $('#approvalUserTable').bootstrapTable('getSelections');	
	if(user_array.length == 0) {
		alert('결재선에 추가할 관리자를 선택해주세요');
		return;
	}
	
	for(var i=0; i<user_array.length; i++) {
				
		var insertCheck = true
		
		var user = {
			id : user_array[i].id,
			cont_name : user_array[i].cont_name,
			name : user_array[i].name,
			role_name : user_array[i].role_name,
			phone : user_array[i].phone,
			grade : user_array[i].grade
		}
		
		CUR_APPROAVAL_LINE_LIST.forEach((item, index) => {
			if(item.id == user_array[i].id) {
				console.log(item.id + "이미 객체에 있는 정보입니다.");
				insertCheck = false
				return;
			}
		});	
		
		if(insertCheck) {
			CUR_APPROAVAL_LINE_LIST.push(user);		
			$('#approvalLineTable').bootstrapTable('load', CUR_APPROAVAL_LINE_LIST);	
		}
		else {
			console.log(user_array[i].id + "정보 추가 Block");
		}
	} 
}

function deleteApprovalLine() {	
	
	var line_array = $('#approvalLineTable').bootstrapTable('getSelections');	
	if(line_array.length == 0) {
		alert('결재선에 삭제할 관리자를 선택해주세요');
		return;
	}
	
	for(var i=0; i<line_array.length; i++) {		
		CUR_APPROAVAL_LINE_LIST.forEach((item, index) => {
			if(item.id == line_array[i].id) {
				CUR_APPROAVAL_LINE_LIST.splice(index, 1);
			}
		});			
	}
	
	$('#approvalLineTable').bootstrapTable('load', CUR_APPROAVAL_LINE_LIST);		
}



function stateFormatter(value, row, index) {
	if (index === 0) {
		return {
			disabled: true
		}
    }
	return value
}

/* ----------------------------------------------------------------- */
// 점검대상자

function overlayCheckerList() {
	$('#modal_checkerList').click();
	getCheckerList(-1);
}

function changeCheckerUser(){
	var cont_id = $('#selectCUserContId').val();	
	getCheckerList(cont_id);
}

function addCheckerLine() {
	var user_array = $('#checkerUserTable').bootstrapTable('getSelections');	
	if(user_array.length == 0) {
		alert('위험성평가 점검에 필요한 관리자를 선택해주세요');
		return;
	}
	
	for(var i=0; i<user_array.length; i++) {
				
		var insertCheck = true
		
		var user = {
			id : user_array[i].id,
			cont_name : user_array[i].cont_name,
			name : user_array[i].name,
			role_name : user_array[i].role_name,
			phone : user_array[i].phone,
			grade : user_array[i].grade
		}
		
		CUR_APPROAVAL_LINE_LIST.forEach((item, index) => {
			if(item.id == user_array[i].id) {
				console.log(item.id + "이미 객체에 있는 정보입니다.");
				insertCheck = false
				return;
			}
		});	
		
		if(insertCheck) {
			CUR_APPROAVAL_LINE_LIST.push(user);		
			$('#checkerLineTable').bootstrapTable('load', CUR_APPROAVAL_LINE_LIST);	
		}
		else {
			console.log(user_array[i].id + "정보 추가 Block");
		}
	} 
}

function deleteCheckerLine() {	
	
	var line_array = $('#checkerLineTable').bootstrapTable('getSelections');	
	if(line_array.length == 0) {
		alert('위험성평가 점검 제외할 관리자를 선택해주세요');
		return;
	}
	
	for(var i=0; i<line_array.length; i++) {		
		CUR_APPROAVAL_LINE_LIST.forEach((item, index) => {
			if(item.id == line_array[i].id) {
				CUR_APPROAVAL_LINE_LIST.splice(index, 1);
			}
		});			
	}
	
	$('#checkerLineTable').bootstrapTable('load', CUR_APPROAVAL_LINE_LIST);		
}

function addCheckerLine() {
	var user_array = $('#checkerUserTable').bootstrapTable('getSelections');	
	if(user_array.length == 0) {
		alert('위험성평가 점검에 필요한 관리자를 선택해주세요');
		return;
	}
	
	for(var i=0; i<user_array.length; i++) {
				
		var insertCheck = true
		
		var user = {
			id : user_array[i].id,
			cont_name : user_array[i].cont_name,
			name : user_array[i].name,
			role_name : user_array[i].role_name,
			phone : user_array[i].phone,
			grade : user_array[i].grade
		}
		
		CUR_CHECKER_LINE_LIST.forEach((item, index) => {
			if(item.id == user_array[i].id) {
				console.log(item.id + "이미 객체에 있는 정보입니다.");
				insertCheck = false
				return;
			}
		});	
		
		if(insertCheck) {
			CUR_CHECKER_LINE_LIST.push(user);		
			$('#checkerLineTable').bootstrapTable('load', CUR_CHECKER_LINE_LIST);	
		}
		else {
			console.log(user_array[i].id + "정보 추가 Block");
		}
	} 
}

function deleteCheckerLine() {	
	
	var line_array = $('#checkerLineTable').bootstrapTable('getSelections');	
	if(line_array.length == 0) {
		alert('위험성평가 점검 제외할 관리자를 선택해주세요');
		return;
	}
	
	for(var i=0; i<line_array.length; i++) {		
		CUR_CHECKER_LINE_LIST.forEach((item, index) => {
			if(item.id == line_array[i].id) {
				CUR_CHECKER_LINE_LIST.splice(index, 1);
			}
		});			
	}
	
	$('#checkerLineTable').bootstrapTable('load', CUR_CHECKER_LINE_LIST);		
}


/* ----------------------------------------------------------------- */
// 위험성평가표

function overlayFactorDetailList(row) {
	
	$.ajax({
		type : "GET",
		url : "./risk/factor/detail",            
		data : {
			code_level_one: row.code_level_one,
			code_level_two: row.code_level_two,
			code_level_three: row.code_level_three,
		},
		async: true,
		cache : false,         
		success : function(list, status){
			$('#factorDetailTable').bootstrapTable();
			if(list.length > 0) {					
				
				var mcontent;				
				for(var i=0; i<list.length; i++) {					
					mcontent = "";					
					for(var j=0; j<list[i].mList.length; j++) {
						mcontent += '<div>'+list[i].mList[j]+'</div>';						
					}
					list[i].management_method = mcontent;
				} 					
				
				$('#factorDetailTable').bootstrapTable('load', list );	
			}
			else{
				$('#factorDetailTable').bootstrapTable('load', [] );
			}
			$('#modal_factorDetailList').click();
		}
	});
}

function adjustFactorDetail() {	
	
	var factor_array = $('#factorDetailTable').bootstrapTable('getSelections');	
	if(factor_array.length == 0) {
		alert('위험성평가 항목을 선택해주세요');
		return;
	}
	
	for(var i=0; i<factor_array.length; i++) {	
		
		var insertCheck = true
		
		CUR_SELECT_FACTOR_LIST.forEach((item, index) => {
			if(item.code == factor_array[i].code) {
				insertCheck = false
				return;
			}
		});			
		
		if(insertCheck) {
			CUR_SELECT_FACTOR_LIST.push(factor_array[i]);		
			$('#factorSelectTable').bootstrapTable('load', CUR_SELECT_FACTOR_LIST);	
		}
		else {
			console.log(factor_array[i].code + "정보 추가 Block");
		}
	}
}

function deleteSelectFactor() {
	var factor_array = $('#factorSelectTable').bootstrapTable('getSelections');	
	if(factor_array.length == 0) {
		alert('선턱리스트에 제외할 항목를 선택해주세요');
		return;
	}
	
	for(var i=0; i<factor_array.length; i++) {		
		CUR_SELECT_FACTOR_LIST.forEach((item, index) => {
			if(item.code == factor_array[i].code) {
				CUR_SELECT_FACTOR_LIST.splice(index, 1);
			}
		});			
	}
	
	$('#factorSelectTable').bootstrapTable('load', CUR_SELECT_FACTOR_LIST);	
}

/* ----------------------------------------------------------------- */

function overlayFactorList() {
	$('#modal_factorList').click();
	getFactorList(-1);
}


function changeFactorType(){
	var type = $('#selectFactorType').val();	
	getFactorList(type);
}

/* ----------------------------------------------------------------- */