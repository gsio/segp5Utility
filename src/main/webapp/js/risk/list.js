function initDatePicker() {			

	if(sd == '' || ed == '') {
		$('#risk_start').val(getPreDate(-14));
		$('#risk_end').val(getTodayDate());
		checkDetail();
	}
	else {
		$('#risk_start').val(sd);
		$('#risk_end').val(ed);
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
	
	if(isOk) {
		$('#searchForm').submit();	
	}	
	else {
		alert('항목을 다시 확인해주시기 바랍니다.');
	}
}

function registerRisk() {
	$('#registerForm').submit();	
}