	

	function checkIdentification() {
		
		const regex = /^010\d{8}$/;
		var inputPhone =  $("#inputPhone").val();
		
		if(regex.test(inputPhone)) {
			$('#certkey_box').css("display", "flex");
			$('#certBtn').css("display", "none");
		    $('#inputPhone').css("background", "#DEDEDE");           
		    $('#inputPhone').prop("readonly", true);		
			
			$.ajax({
				type: "POST",				
				url: 'http://211.212.221.98:11243/postIdentification',
				data: {
					site_id : CUR_SITE_ID,  
					phone : inputPhone,
					certKey : getRandomFunction(100000, 999999)				
				},
				async: true,
				cache: false,			
				success: function (data, status) {
					if(data.status == 200){
						setCertkeyFunction();
					}
					else {
						alert("입력하신 핸드폰번호를 확인해주세요");
					}
		       	},
			});
		}
		else {
			alert("올바르지 않은 핸드폰 번호입니다.");			
			return 
		}
	}
	
	//-- 자바스크립트 난수생성 (SEED)
	function getRandomFunction(min, max) {
		var array = new Uint32Array(1);
		window.crypto.getRandomValues(array);
		var seed = max - min + 1; 
		var item = (array[0]%seed + min);
		return item;
	}	

	function setCertkeyFunction() {
		
		var limitMiliSecond = 181 * 1000;	
		var startTimer;
		var isCertificated = false;
		
		startTimer = function (countdown) {
		    var timer = countdown, endTime = new Date();
		    
		    endTime.setSeconds(endTime.getSeconds() + timer);
		    var endTimeSeconds = endTime.getTime();		    
		    
		    clearInterval(interval);
		    
		    interval = setInterval(function () {
		    	timer = Math.floor((endTimeSeconds - new Date().getTime())/1000);
		    	
		        displaykeyinTime(timer);

		        if (timer <= 0) {
		        	displaykeyinTime(0);
		            clearInterval(interval);
		            
		            $("#cerkeyInputId").css("background", "#DEDEDE");	         
		            $('#cerkeyInputId').prop("readonly", true);
		            $('#inputPhone').css("background", "#FFF");
		            $('#inputPhone').attr("readonly", false);	             
		            
		            alert("인증번호 유효시간이 초과되었습니다");
		            
		            $("#resendMsgCertKey").css("display", "block");
		            $("#confirmCertKeyId").css("display", "none");
		        }
		        
		    }, 1000);
		};	
		
		var second = Math.floor(limitMiliSecond/1000);
		startTimer(second);
	}

	function resendMsgCertKey() {    
	    $("#cerkeyInputId").css("background", "#FFF");
	    $('#cerkeyInputId').attr("readonly", false);
	    $('#inputPhone').css("background", "#DEDEDE");           
	    $('#inputPhone').prop("readonly", true);	       
	    $("#resendMsgCertKey").css("display", "none");
	    $("#confirmCertKeyId").css("display", "block");
		setCertkeyFunction();
	}

	function displaykeyinTime(time) {
		var minutes = parseInt(time / 60, 10),
	    	seconds = parseInt(time % 60, 10);
	    minutes = minutes < 10 ? "0" + minutes : minutes;
	    seconds = seconds < 10 ? "0" + seconds : seconds;	
	    $("#countDown").text(minutes + ":" + seconds);
	}

	function confirmCertKeyId() {		
		
		$.ajax({
			type: "GET",				
			url: 'qr/checkCertKeyVaild',
			data: {
				phone : $("#inputPhone").val(),
				certkey : $("#cerkeyInputId").val()		
			},
			async: true,
			cache: false,			
			success: function (data, status) {	
				if(data != null) {
					
					clearInterval(interval);						
					$('#certkey_box').css("display", "none");
					$('#modifyId').css("display", "inherit");
				    $('#inputPhone').css("background", "#DEDEDE");           
				    $('#inputPhone').prop("readonly", true);
				    
				    if(data.role > 0) {
						alert("이미 가입된 계정 입니다.");
						G_STATE = 1;
				    }
				    else {
				    	alert("신규 회원 입니다.");
						G_STATE = 2;
						$('#phone').html($("#inputPhone").val());
					    $('#phone').prop("readonly", true);
						showNextBtn("다음");
				    }
				}
				else {
					alert("인증 번호가 맞지 않습니다.");
				}
	       	}
		});	
		
	}
	
	/*

	function submitWorker() {	
		var input;
		input = confirm('등록하시겠습니까?');	
		
		if(input) {	
			var isOk = checkDetail();		
			if(isOk){			
				$('#workerForm').submit();
			}
			else {
				alert('항목을 다시 확인해주시기 바랍니다.');
			}
		}
		else {
			return;
		}		 
	}

	function checkDetail(){
		
		var isOk = true;
		
		$('[id^=worker_name_]').each(function () {
			var this_id = $(this).attr('id');
			var this_name = $(this).val();		
			
			if(this_name.length >= 1){
				var id = this_id.split('worker_name_')[1];		
				var phone =	$('#worker_phone_' + id).val();			
				 
			   	var rgEx = /(01[016789])(\d{4}|\d{3})\d{4}$/g;//핸드폰 정규표현식
			   	var chk_phone = rgEx.test(phone);   
			   	if(!chk_phone || phone.length < 10) {				
				   	$('#worker_phone_' + id).css('border','2px solid red');
				   	isOk = false;
				} 
			   	else {
					$('#worker_phone_' + id).css('border','1px solid #cccccc');				

				 	var result = checkDuplicatePhone(phone);
				  	if(result >= 0) {				
					   	isOk = true; 
				  	} 
			  		// 현장내 중복
			   		else if(result == -99) {
						$('#worker_phone_' + id).css('border','2px solid red');
						isOk = false; 
				  	} 
			   	 	// -1은 PASS
			   		else{
			   			
				  	}
				}
			   
				var jumin =	$('#worker_jumin_' + id).val();		   
				if(jumin.length < 6){
				   isOk = false;
				   $('#worker_jumin_' + id).css('border','1px solid red');
				}
				else {			 
					$('#worker_jumin_' + id).css('border','1px solid #cccccc');
				}
			   
				var edu_date = $('#worker_edudate_' + id).val();
			   
				// 입력안할경우 패스
				if(edu_date.length > 0){
					//달력 정규표현식
					var rgEx_date = /^\d{4}-[01][0-9]-[0123][0-9]$/g;
				   
					var chk_date = rgEx_date.test(edu_date);   
				   
				  	if(!chk_date || edu_date.length < 10){
						isOk = false;
						$('#worker_edudate_' + id).css('border','1px solid red');
					}
				  	else {
						$('#worker_edudate_' + id).css('border','1px solid #cccccc');
					}
				}
			   
				var hire_date = $('#worker_hiredate_' + id).val();  
				
				if(hire_date.length > 0 ){
					//달력 정규표현식
					var rgEx_date2 = /^\d{4}-[01][0-9]-[0123][0-9]$/g;
					
					var chk_date2 = rgEx_date2.test(hire_date);   
					if(!chk_date2 || hire_date.length < 10){
						isOk = false;
						$('#worker_hiredate_' + id).css('border','1px solid red');
					}
					else {
						$('#worker_hiredate_' + id).css('border','1px solid #cccccc');
					}			   
				} 
			} 
		});
		return isOk;
	}
	*/


	function checkNumberAndEnglish(val){
		let regex= /^[a-zA-Z0-9]*$/;
		if(regex.test(val) == true) return true;
		else return false;
	}
	