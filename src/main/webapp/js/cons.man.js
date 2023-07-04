

function setMouseOverOnDIV(div_id) {
  var mouseStatus = false;
  var tmp_text = $('#'+ div_id).html();
  
  try{
	  objLayer = document.getElementById(div_id);
	  objLayer.onmouseover = function () {mouseStatus=true;} //마우스가 레이어 위에 있으면 true
	  objLayer.onmouseout = function () {mouseStatus=false;} //마우스가 레이어 밖에 있으면 false;
	  window.setInterval(function(){
		  actionMouseOverOnDIV(mouseStatus, tmp_text, div_id);	  
	  }, "3000"); //마우스의 상태와 상관없이 5초마다 doPlay()를 호출 하면서 계속 돈다.
  }catch(e){
	  console.log(e);
  }
}
 
 
function actionMouseOverOnDIV(mouseStatus, tmp_text, div_id) {
  if(!mouseStatus){
	  $('#' + div_id).html(tmp_text);
  }
}
 


function checkAgent(){
	var isMobile = {
	        Android: function () {
	                 return navigator.userAgent.match(/Android/i);
	        },
	        BlackBerry: function () {
	                 return navigator.userAgent.match(/BlackBerry/i);
	        },
	        iOS: function () {
	                 return navigator.userAgent.match(/iPhone|iPad|iPod/i);
	        },
	        Opera: function () {
	                 return navigator.userAgent.match(/Opera Mini/i);
	        },
	        Windows: function () {
	                 return navigator.userAgent.match(/IEMobile/i);
	        },
	        any: function () {
	                 return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
	        }
	};
	
	if(isMobile.any()){
	    if(isMobile.Android()){
	    	MOBILE_TYPE  = 1;
	    }else if(isMobile.iOS()){
	    	MOBILE_TYPE = 2;
	    }else if(isMobile.BlackBerry()){
	    	MOBILE_TYPE = 3;
	    }else if(isMobile.Opera()){
	    	MOBILE_TYPE  = 4;
	    }else if(isMobile.Windows()){
	    	MOBILE_TYPE = 5;
	    }else{
	    	MOBILE_TYPE = 6;
	    }
	}else{
		MOBILE_TYPE = 0;
	}

}

function getTodayDate(){
	var d = new Date();		 
	var year = d.getFullYear() ;
	var month = d.getMonth() + 1;
	var day = d.getDate();	
	if(month < 10){
			month = "0" + month;
	}	
	if(day < 10){
		day = "0" + day;
	}
	var cur_date = year + ' - ' + month + ' - ' + day;
//var cur_date = year +  + month +  + day;
	return cur_date;
}

var weekday = ['일','월','화','수','목','금','토'];

/**시간 표현   09/07 <br>목요일**/
function startTime(date_div_id, clock_div_id, type) {
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    var day = today.getDay();
    m = checkTime(m);
    s = checkTime(s);
    
    if(type == 1){//현장용
    	$('#' + date_div_id).html(getTodayDate3());
    	 $('#' + clock_div_id).html( h + ":" + m + ":" + s  + " &nbsp;&nbsp;&nbsp;&nbsp; " );
    }else if(type == 2){//날짜만 나오기
    	$('#' + date_div_id).html(getTodayDate3());
    }else{
    	$('#' + date_div_id).html(getTodayDate());	
    	$('#' + clock_div_id).html( h + ":" + m + ":" + s  + "  " + weekday[day] +"" );
    }
    var t = setTimeout(function(){startTime(date_div_id, clock_div_id, type); }, 500);
}



function checkTime(i) {
    if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
    return i;
}

function getTodayDate3(){
	var d = new Date();		 
	var year = d.getFullYear() ;
	var month = d.getMonth() + 1;
	var day = d.getDate();
	var day_text = d.getDay();
	var cur_date = month + '/' + day + '(' +  weekday[day_text] + ')';
	return cur_date;
}


function getTodayDate2(){
	var d = new Date();		 
	var year = d.getFullYear() ;
	var month = d.getMonth() + 1;
	var day = d.getDate();
	var day_text = d.getDay();
	var cur_date = year + '년' + month + '월' + day + '일' + '('+ day_text + ')';
	return cur_date;
}

/**nfc 자동부여**/
function makeNFC(id){
	$.ajax({
		type : "GET",
		url : "getNewNFCId",
		cache : false,
		success : function(json) { $('#' + id).val(json);},
		error : function(){alert('NFC ID 불러오기 실패');}
	});
}


function RiskData(code, work_detail, work_level, risk_content, damage,  bin, kang, manage, gubun , 
		device, startdate, enddate, work_place, worker,d_worker, manager) {	
	//gubun 0 기본 1 생성
	this.code = code;
	this.work_detail = work_detail;
	this.work_level = work_level;
	/*this.work_detail_code = code.substring(0,3);
	this.work_level_code = code.substring(0,5);*/
	this.damage = damage;
	this.risk_content = risk_content;
	this.bin = bin;
	this.kang = kang;
	this.manage = manage;
	this.gubun = gubun;
	
	/**추가사항**/
	this.device = device;
	this.startdate = startdate;
	this.enddate = enddate;
	this.work_place = work_place;
	this.worker = worker;
	this.d_worker = d_worker;
	this.manager = manager;
};

function MetaData(code, codename, code_level, damage, bin, kang, manage, work_detail, work_level) {
	this.code = code;
	this.codename = codename;
	this.code_level = code_level;
	this.damage = damage;
	this.bin = bin;
	this.kang = kang;
	this.manage = manage;
	this.work_detail = work_detail;
	this.work_level = work_level;
};

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

/**new Map() 에사용**/
Map = function() {
	this.map = new Object();
};
Map.prototype = {
	put : function(key, value) {
		this.map[key] = value;
	},
	get : function(key) {
		return this.map[key];
	},
	containsKey : function(key) {
		return key in this.map;
	},
	containsValue : function(value) {
		for ( var prop in this.map) {
			if (this.map[prop] == value)
				return true;
		}
		return false;
	},
	isEmpty : function(key) {
		return (this.size() == 0);
	},
	clear : function() {
		for ( var prop in this.map) {
			delete this.map[prop];
		}
	},
	remove : function(key) {
		delete this.map[key];
	},
	keys : function() {
		var keys = new Array();
		for ( var prop in this.map) {
			keys.push(prop);
		}
		return keys;
	},
	values : function() {
		var values = new Array();
		for ( var prop in this.map) {
			values.push(this.map[prop]);
		}
		return values;
	},
	size : function() {
		var count = 0;
		for ( var prop in this.map) {
			count++;
		}
		return count;
	}
};
/*var map = new Map();
map.put("user_id", "atspeed");
-----------------------
map.get("user_id");*/


/** Date format 관련. 	var week_end = tmp_date.format("yyyy-MM-dd"); 등으로 사용 **/
Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    var d = this;
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};

String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};

/**Date format 관련 end**/



function getTodayDate(){
	var d = new Date();		 
	var year = d.getFullYear() ;
	var month = d.getMonth() + 1;
	var day = d.getDate();
	
	if(month < 10){
			month = "0" + month;
	}
	
	if(day < 10){
		day = "0" + day;
	}
	var cur_date = year + '-' + month + '-' + day;
//var cur_date = year +  + month +  + day;
	return cur_date;
}


//이미지 파일 업로드시 제한
$(document).on("change", ".upfile-image", function() {
	
	var filename = $(this).val();
	var extension = filename.replace(/^.*\./, '');
	if (extension == filename) {
    extension = '';
} else {
    extension = extension.toLowerCase();
}
	
	//이미지 파일은 JPG, PNG 확장자만 가능
	if( (extension != 'jpg') && (extension != 'png') && (extension != 'gif') && (extension != 'pdf') ) {
		//초기화
		//http://stackoverflow.com/questions/1043957/clearing-input-type-file-using-jquery
		$(this).val('');
		/* var control = $(this);
		control.replaceWith( control = control.clone( true ) ); */
		
		alert("이미지 파일은 JPG, PNG, GIF 확장자만 가능합니다.");
	}
});

//숫자 입력 함수 ex) <input  onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)'>
function onlyNumber(event, isDouble){
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if(keyID == 9)return;//TAB키는 예외
	if(isDouble && keyID == 190){return;}// 소수점(.) 입력 허용
	if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
		return;
	else
		return false;
}

function removeChar(event, isDouble) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if(isDouble && keyID == 190){return;}// 소수점(.) 입력 허용
	
	if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
		return;
	else{
		if(isDouble)
			event.target.value = event.target.value.replace(/[^0-9.]/g, "");
		else
			event.target.value = event.target.value.replace(/[^0-9]/g, "");
	}
}


function isInt(n){
	n = parseFloat(n);//String으로넘어온 숫자 type변환
    return Number(n) === n && n % 1 === 0; //type체크와 값체크 동시에
	//return n % 1 === 0;
}

function isFloat(n){
	n = parseFloat(n);
	return Number(n) === n && n % 1 !== 0; 
   // return n % 1 !== 0; //
}




function zoomImage(val, title){	
	$('.enlargeImageModalSource').attr('src', val.src);
	$('#enlargeImageModal').modal('show');
	if(typeof title != 'undefined'){
		$('#enalrageModalTitle').html(title);		
	}else{
		$('#enalrageModalTitle').html('');
	}

}

function zoomImage(val, title,  height ){
	if(height ==  -1) height = 30;	
	//$('.enlargeImageModalSource').css('width' , width + 'vw');
	$('.enlargeImageModalSource').css('width' , '100%');
	$('.enlargeImageModalSource').css('height' , height + 'vh');	
	
	
	$('.enlargeImageModalSource').attr('src', val.src);
	$('#enlargeImageModal').modal('show');
	if(typeof title != 'undefined'){
		$('#enalrageModalTitle').html(title);		
	}else{
		$('#enalrageModalTitle').html('');
	}

}



/**comment**/

function deleteReply(comment_id){
	var input = confirm('해당 댓글을 삭제하시겠습니까?');
	if(input) { //yes
		$.ajax({
			type : "POST",
			url : "json/mobile_deleteReplyComment",
			data : {
				"comment_id" : comment_id
			},
			cache : false,
			async : false,
			success : function(json,status) {
				var json_data  = JSON.parse(json);
				if(json_data == "true" || json_data == true)
					location.reload();
			} 
		
		});
	}
	
	
}



/****/
