function checkAccess(isAccess, site_auth, role_code){
	var test='${sessionScope.isAccess}';
	
	if(!isAccess){
		$('#main_content').hide();
		alert('잘못된 접근으로 인하여 기본페이지로 이동합니다.');
		location.replace("main");
	} 
}

function checkAgent() {
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
	} else {
		MOBILE_TYPE = 0;
	}
}

var weekday = [ '일', '월', '화', '수', '목', '금', '토' ];

function startTime(date_div_id, clock_div_id,type) {
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    var day = today.getDay();
    m = checkTime(m);
    s = checkTime(s);
    
    $('#' + clock_div_id).html( h + ":" + m + ":" + s);
    
    if(type == 1) {
    	// 2022-09-28
    	$('#' + date_div_id).html(getTodayDate());
    }    
    else if (type == 2) {
    	// 2022년 9월 28일
    	$('#' + date_div_id).html(getTodayDate2());
    }
    else if (type == 3){
    	// 9/28 (수)
    	$('#' + date_div_id).html(getTodayDate3());	    	
    }    
	
    var t = setTimeout(function(){startTime(date_div_id, clock_div_id, type); }, 500);
}

function checkTime(i) {
	if (i < 10) {
		i = "0" + i
	}
	return i;
}

function getTodayDate() {
	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var day = d.getDate();
	if (month < 10) {
		month = "0" + month;
	}
	if (day < 10) {
		day = "0" + day;
	}
	var cur_date = year + '-' + month + '-' + day;
	return cur_date;
}

function getPreDate(ago) {
	var d = new Date(new Date().setDate(new Date().getDate() + ago));
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var day = d.getDate();
	
	if (month < 10) {
		month = "0" + month;
	}
	if (day < 10) {
		day = "0" + day;
	}
	var cur_date = year + '-' + month + '-' + day;
	return cur_date;
}

function getTodayDate2() {
	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var day = d.getDate();
	var day_text = d.getDay();
	var cur_date = year + '년 ' + month + '월 ' + day + '일 ';
	return cur_date;
}

function getTodayDate3() {
	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var day = d.getDate();
	var day_text = d.getDay();
	var cur_date = month + '/' + day + '(' + weekday[day_text] + ')';
	return cur_date;
}

function getUrlParameter(sParam) {
	var sPageURL = window.location.search.substring(1);
	var sURLVariables = sPageURL.split('&');
	for (var i = 0; i < sURLVariables.length; i++) {
		var sParameterName = sURLVariables[i].split('=');
		if (sParameterName[0] == sParam) {
			return sParameterName[1];
		}
	}
}

// Date format 관련
// var week_end = tmp_date.format("yyyy-MM-dd"); 등으로 사용

Date.prototype.format = function(f) {
	if (!this.valueOf())
		return " ";
	var weekName = [ "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일" ];
	var d = this;
	return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
		switch ($1) {
		case "yyyy":
			return d.getFullYear();
		case "yy":
			return (d.getFullYear() % 1000).zf(2);
		case "MM":
			return (d.getMonth() + 1).zf(2);
		case "dd":
			return d.getDate().zf(2);
		case "E":
			return weekName[d.getDay()];
		case "HH":
			return d.getHours().zf(2);
		case "hh":
			return ((h = d.getHours() % 12) ? h : 12).zf(2);
		case "mm":
			return d.getMinutes().zf(2);
		case "ss":
			return d.getSeconds().zf(2);
		case "a/p":
			return d.getHours() < 12 ? "오전" : "오후";
		default:
			return $1;
		}
	});
};

String.prototype.string = function(len) {
	var s = '', i = 0;
	while (i++ < len) {
		s += this;
	}
	return s;
};
String.prototype.zf = function(len) {
	return "0".string(len - this.length) + this;
};
Number.prototype.zf = function(len) {
	return this.toString().zf(len);
};

// Date format 관련 end

// 이미지 클릭시 (임시)팝업
function zoomImage(val, title, height){
	if(height ==  -1) {
		height = 30;	
	}
	$('.enlargeImageModalSource').css('width' , '100%');
	$('.enlargeImageModalSource').css('height' , height + 'vh');
	$('.enlargeImageModalSource').attr('src', val.src);
	$('#enlargeImageModal').modal('show');
	
	if(typeof title != 'undefined') {
		$('#enalrageModalTitle').html(title);		
	}
	else{
		$('#enalrageModalTitle').html('');
	}
}


//이미지 파일 업로드시 제한
$(document).on(
		"change",
		".upfile-image",
		function() {

			var filename = $(this).val();
			var extension = filename.replace(/^.*\./, '');
			if (extension == filename) {
				extension = '';
			} else {
				extension = extension.toLowerCase();
			}

			//이미지 파일은 JPG, PNG 확장자만 가능
			if ((extension != 'jpg') && (extension != 'png')
					&& (extension != 'gif') && (extension != 'pdf')) {
				//초기화
				//http://stackoverflow.com/questions/1043957/clearing-input-type-file-using-jquery
				$(this).val('');
				/* var control = $(this);
				control.replaceWith( control = control.clone( true ) ); */

				alert("이미지 파일은 JPG, PNG, GIF 확장자만 가능합니다.");
			}
		});

//숫자 입력 함수 ex) <input  onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)'>
function onlyNumber(event, isDouble) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if (keyID == 9)
		return;//TAB키는 예외
	if (isDouble && keyID == 190) {
		return;
	}// 소수점(.) 입력 허용
	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105)
			|| keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39)
		return;
	else
		return false;
}

function removeChar(event, isDouble) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if (isDouble && keyID == 190) {
		return;
	}// 소수점(.) 입력 허용

	if (keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39)
		return;
	else {
		if (isDouble)
			event.target.value = event.target.value.replace(/[^0-9.]/g, "");
		else
			event.target.value = event.target.value.replace(/[^0-9]/g, "");
	}
}

function onlyNumberOver3(event, isDouble) {
	var value = event.target.value;
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if (keyID == 9)
		return;//TAB키는 예외
	if (isDouble && keyID == 190) {
		return;
	}// 소수점(.) 입력 허용
	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105)
			|| keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39)
		return;
	if(value > 3) {
		event.target.value = 3;
	}
	else
		return false;
}

function removeCharOver3(event, isDouble) {
	var value = event.target.value;
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if (isDouble && keyID == 190) {
		return;
	}// 소수점(.) 입력 허용

	if (keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39)
		return;
	else {
		if (isDouble)
			event.target.value = event.target.value.replace(/[^0-9.]/g, "");
		else
			event.target.value = event.target.value.replace(/[^0-9]/g, "");
	}
	
	if(value <= 0) {
		event.target.value = 1;
	}
	
	if(value > 3) {
		event.target.value = 3;
	}
	
	
}

function isInt(n) {
	n = parseFloat(n);//String으로넘어온 숫자 type변환
	return Number(n) === n && n % 1 === 0; //type체크와 값체크 동시에
	//return n % 1 === 0;
}

function isFloat(n) {
	n = parseFloat(n);
	return Number(n) === n && n % 1 !== 0;
	// return n % 1 !== 0; //
}

function zoomImage(val, title) {
	$('.enlargeImageModalSource').attr('src', val.src);
	$('#enlargeImageModal').modal('show');
	if (typeof title != 'undefined') {
		$('#enalrageModalTitle').html(title);
	} else {
		$('#enalrageModalTitle').html('');
	}

}
