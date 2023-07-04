
function duplicateIdCheck() {
	var userid = $('#input_id').val();
	
	if(userid == "") {
		alert("아이디를 입력하십시오");
		return;
	}
	  
	if(userid.length < 5){
		alert('아이디는 5자 이상으로 만들어주시기 바랍니다');
		return;
	}
	  
	$.ajax({
		type : "GET",
		url : "duplicateIdCheck",
		data : {"userid" : userid },
		cache : false,
		success : onSuccessIDCheck,
		error : onErrorIDCheck
	});
}
 
function onSuccessIDCheck(json, status) {
	if(json == "true") {
		idNotDuplicate = true;
		$('#input_id').attr("readonly",true);
		$('#duplCheck').hide();  
		$('#duplCheckOk').show();
		alert("사용 가능합니다 (This ID is available)");		  
	}
	else {
		idNotDuplicate = false;
		alert("이미 존재하는 ID 입니다 (This ID already exists)")
	}
}
 
function onErrorIDCheck() {
	idNotDuplicate = false;
}

function newPassword() {
	$('#btnNewPasswd').hide();
	$('#input_password').show();
	$('#input_password').val('');	
	$('#isPWChanged').val('true'); 
}

function submitUser() {
	var input;
	if(isUpdate == true) {
		input = confirm('수정하시겠습니까?');
	}
	else {
		input = confirm('등록하시겠습니까?');
	}
	
	if(input) {
		if(idNotDuplicate) {
			var cont_id = $('#cont').val();
		  	if(cont_id == -1){
		  		alert('소속업체를 선택해주시기 바랍니다.');
		  		return;
		  	}
		  	
		  	if($('#isManager_check').prop('checked')){
		  		$('#isManager').val(1);	
		  	}
		  	else{
		  		$('#isManager').val(0);
		  	}
		  	
			$('#userForm').submit();
			} else {
				  alert('아이디 중복체크를 해주시기 바랍니다');
			}  
	} 
	else {
		return;
	}		
}

function deleteUser() {	
	var input = confirm('삭제하시겠습니까?');
	if(input){
		$('#userForm').attr('action', 'deleteUser');
		$('#userForm').submit();
	}
}

function changeImage(val){
	 $('#localpath_div' + '_' + val).show(); 
	 $('#upfile_div' + '_' + val).show(); 
	 $('#image_div' + '_' + val).hide(); 
	 $('#file_btn'+ '_' + val).hide(); 
}

function setFilePath(idx, val) {
	document.getElementById(idx).value = val;
}

$(document).on("change", ".upfile", function() {	
	var filename = $('#upfile').val();
	var extension = filename.replace(/^.*\./, '');
	if (extension == filename) {
 		 extension = '';
	} else {
	  extension = extension.toLowerCase();
	}
	
	//이미지 파일은 JPG, PNG 확장자만 가능
	if( (extension != 'jpg') && (extension != 'png') && (extension != 'gif') ) {
		//초기화
		$('#upfile').val('');
		$('#sign_img').val('');
		
		alert("이미지 파일은 JPG, PNG, GIF 확장자만 가능합니다.");
	}
});

function modifyUserId() {
	
	if($("#phone").val() != '') {
		
		$('#certkey_box').css("display", "flex");
		$('#modifyId').css("display", "none");
		
		$.ajax({
			type: "POST",				
			url: 'http://13.209.31.139:11243/postInsertCertkey',
			//url: 'http://211.212.221.98:11243/postInsertCertkey',
			data: {
				phone : $("#phone").val(),
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
		alert("핸드폰번호를 확인해주세요")
	}
	
	
}

//-- 자바스크립트 난수생성 (SEED)
function getRandomFunction(min, max) {
	var array = new Uint32Array(1);
	window.crypto.getRandomValues(array);
	var seed = max - min + 1; 
	var item = (array[0]%seed + min);
	//alert(item);
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
	            $('#phone').css("background", "#FFF");
	            $('#phone').attr("readonly", false);	             
	            
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
    $('#phone').css("background", "#DEDEDE");           
    $('#phone').prop("readonly", true);	       
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
		url: './checkModifyUserId',
		data: {
			phone : $("#phone").val(),
			certkey : $("#cerkeyInputId").val()		
		},
		async: true,
		cache: false,			
		success: function (data, status) {
			if(data == "true"){
				clearInterval(interval);						
				$('#certkey_box').css("display", "none");
				$('#modifyId').css("display", "inherit");
			    $('#phone').css("background", "#DEDEDE");           
			    $('#phone').prop("readonly", true);			
				checkNewUserId();
			}
			else {
				alert("인증번호가 맞지 않습니다.");
			}
       	}
	});	
	
}

function checkNewUserId() {	
	
	while (true) {
		let userid = prompt('[변경] 사용자 ID를 변경해주세요 (영어, 숫자만 가능합니다)'); 
		if(userid == "") {
			alert("취소 하였습니다.");
			break;
		}
		else {
			input = checkNumberAndEnglish(userid);
			if(input == false) {
				alert("영어/숫자만 입력해주세요"); 
			
			}
			else {
				if(userid.length < 5){
					alert('아이디는 5자 이상으로 만들어주시기 바랍니다');		
				}
				else {
					userid = userid.toLowerCase();		
					// 중복 체크 후 UPDATE 진행
					modifyIdCheck(userid);
					break;	
				}	
			}				
		}
	}
}

function modifyIdCheck(userid) {
	$.ajax({
		type : "GET",
		url : "duplicateIdCheck",
		data : {"userid" : userid },
		cache : false,
		success: function (data, status) {
			if(data == "true"){					
				alert("사용 가능합니다 (This ID is available)");		
				postChangeUserId(userid);
			}
			else {
				alert("이미 존재하는 ID 입니다 (This ID already exists)")
			}
       	}
	});
}

function postChangeUserId(new_user_id) {	
	$.ajax({
		type : "POST",
		url : "./postChangeUserId",
		data : {
			"phone" : $("#phone").val(),
			"userid" : new_user_id
		},		
		cache : false,
		success: function (data, status) {
            $('#phone').css("background", "#FFF");
            $('#phone').attr("readonly", false);
            $('#userid').val(new_user_id);
            $('#new_user_id').text(new_user_id);
       	}
	});
}

function checkNumberAndEnglish(val){
	let regex= /^[a-zA-Z0-9]*$/;
	if(regex.test(val) == true) return true;
	else return false;
}
