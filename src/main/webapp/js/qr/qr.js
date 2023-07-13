	
function isConnectMobile() {
	var isMobile = {
		Android : function() {
			return navigator.userAgent.match(/Android/i);
		},
		BlackBerry : function() {
			return navigator.userAgent.match(/BlackBerry/i);
		},
		iOS : function() {
			return navigator.userAgent.match(/iPhone|iPad|iPod/i);
		},
		Opera : function() {
			return navigator.userAgent.match(/Opera Mini/i);
		},
		Windows : function() {
			return navigator.userAgent.match(/IEMobile/i);
		},
		any : function() {
			return (isMobile.Android() || isMobile.BlackBerry()
					|| isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
		}
	};

	if (isMobile.any()) {
		if (isMobile.Android()) {
			MOBILE_TYPE = 1;
		} else if (isMobile.iOS()) {
			MOBILE_TYPE = 2;
		} else if (isMobile.BlackBerry()) {
			MOBILE_TYPE = 3;
		} else if (isMobile.Opera()) {
			MOBILE_TYPE = 4;
		} else if (isMobile.Windows()) {
			MOBILE_TYPE = 5;
		} else {
			MOBILE_TYPE = 6;
		}
		return true;
	} 
	else {
		MOBILE_TYPE = 0;
		return false;
	}
}

	function checkIdentification() {		
		
		const regex = /^010\d{8}$/;
		var inputPhone =  $("#inputPhone").val();
		//$('#prebtn').css("display", "block"); 
		
		if(regex.test(inputPhone)) {
			$('#certkey_box').css("display", "flex");
			$('#certBtn').css("display", "none");
		    $('#inputPhone').css("background", "#DEDEDE");           
		    $('#inputPhone').prop("readonly", true);		
			
			$.ajax({
				type: "POST",
				url: 'http://13.209.31.139:11243/postIdentification',
				//url: 'http://211.212.221.98:11243/postIdentification',
				data: {
					site_id : CUR_SITE_ID,  
					phone : inputPhone,
					certKey : getRandomFunction(1000, 9999)				
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
		$("#infoWrap").css("display", "none");
		$('[id^=_uw_]').html("");
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
				    	$("#infoWrap").css("display", "block");
						$("#_uw_cont_name").text(data.cont_name);
						$("#_uw_name").text(data.name);
						$("#_uw_wt_type").text(data.wt_name);
						
						var photo = '<img src="image_thumb?virtname='+data.photo+'&height=150&width=100" onerror="this.src=\'images/noimage.png\'">'
						
						$("#_uw_photo").html(photo);
						
						G_ROLE = data.role;
						G_UW_ID = data.id;
					
						// alert("이미 가입된 계정 입니다. (" +  data.is_end + ")");						
						// 입장
						if(data.start_time == null) {
							G_STATE = 1;
							$("#_uw_start_time").html("입장 전");
							$("#_uw_work_min").removeClass('green');
						}
						// 퇴장
						else {
							$("#_uw_start_time").text(data.start_time);
							$("#_uw_work_min").text(data.work_min + "분");							
							G_STATE = 5;
						}					
						showNextBtn("다음");
				    }
				    else {
				    	alert("신규 회원 입니다. 오른쪽 하단 다음을 눌러주세요.");
						G_STATE = 2;
						showNextBtn("다음");
				    }
				}
				else {
					alert("인증 번호가 맞지 않습니다.");
				}
	       	}
		});			
	}

	function checkNumberAndEnglish(val){
		let regex= /^[a-zA-Z0-9]*$/;
		if(regex.test(val) == true) return true;
		else return false;
	}
	
