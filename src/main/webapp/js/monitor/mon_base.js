
function isNull(obj){
	return typeof obj == 'undefined';
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

function getMonitorData(site_id, place_gubun){
	var data;
	$.ajax({
		type : "GET",
		url : "./json/getMonitorData",				
		data : {
			site_id : site_id
			,place_gubun: place_gubun
		},
		async: false,
		cache : false,			
		success : function(json	,status){
			json_data  = JSON.parse(json);			
			if(json_data.result =="true"){	
				data = json_data;
			}else{}
		}
	});
	return data;
}

function getTunnelInfo(site_id, place_gubun){
	var data = [];
	$.ajax({
		type : "GET",
		url : "./json/getTunnelInfo",				
		data : {
			site_id : site_id
			,place_gubun: place_gubun
		},
		async: false,
		cache : false,			
		success : function(json	,status){
			json_data  = JSON.parse(json);			
			if(json_data.result =="true"  && !isNull(json_data.tunnelInfoList)){	
				data = json_data.tunnelInfoList;
			}else{
				data = [];
			}
		}
	});
	
	return data;
}
	 

function getDailyValue(site_id, place_gubun, type  ,  isLastRow){
	var data = [];
	$.ajax({
		type : "GET",
		url : "./json/getDailyValueList",
		data : {	
			site_id : site_id
			,place_gubun : place_gubun
			,type : type
		//	,input_date : input_date
			,isLastRow : isLastRow
		},
		cache : false,
		async : false,
		success : function(json	,status){
			json_data  = JSON.parse(json);			
			if(json_data.result =="true" && !isNull(json_data.list)){	
				data = json_data.list;
			}else{
				data =[];
			}
		},
		error : null
	});
	
	return data;
}


function getTimelyValue(site_id, place_gubun, type, writedate ,isLastRow){
	var data= [];
	$.ajax({
		type : "GET",
		url : "./json/getTimelyValueList",
		data : {	
			site_id : site_id
			,place_gubun : place_gubun
			,type : type
			,writedate : writedate
			,isLastRow : isLastRow
		},
		cache : false,
		async : false,
		success : function(json	,status){
			json_data  = JSON.parse(json);			
			if(json_data.result =="true" && !isNull(json_data.list)){	
				data = json_data.list;
			}else{
				data = [];
			}
		},
		error : null
	});
	
	return data;
}

function getSensorData(site_id, place_gubun, callback){
	if(typeof callback == 'undefined' ){
		callback = onSuccessSensor;
	}
	$.ajax({
		type : "GET",
		url : "./json/getSensorData",
		//async : false,
		data : {	
			site_id : site_id
			,place_gubun : place_gubun
		},
		cache : false,
		//async : false,
		success : callback,
		error : null
	});
}


/**일단 대림용**/
//mon_10.jsp, did_10.jsp, sesnorList, main_dl
function onSuccessSensor(json,status) {
		var image_path = 'images/company/daelim/';
		$('#__o2').html('-');
		$('#__co').html('-');
		$('#__co2').html('-');
		$('#__h2s').html('-');
		$('#__gas').html('-');
		$('#__dust').html('-');
	
		$('[id^=env_box_]').removeClass('alert_bg');
		$('[id^=__status_]').attr('src', image_path + 'status_1.png');
		$('#__status_dust').attr('src', image_path + 'dust_1.png');
				
		var json_data  = JSON.parse(json);
		
		if(json_data.result == "true"){	
				var list =	json_data.list;
				for(var i = 0 ; i < list.length ; i++){
					var sensorVO = list[i];					
					//환경 데이터
					$('#__o2').html(sensorVO.o2);
					$('#__co').html(sensorVO.co);
					$('#__co2').html(sensorVO.co2);
					$('#__h2s').html(sensorVO.h2s);
					$('#__gas').html(sensorVO.gas);
					$('#__dust').html(sensorVO.dust);
					
					var status_o2 = 1;
					if(sensorVO.o2 >= 18 && sensorVO.o2 < 23.5){//정상
						status_o2 = 1; 	
						$('#env_box_o2').removeClass('alert_bg');
					}else{
						status_o2 = 3;
						$('#env_box_o2').addClass('alert_bg');
					}
					$('#__status_o2').attr('src', image_path + 'status_' + status_o2 +'.png');
					
					var status_co = 1;
					if(sensorVO.co <= 30){//정상
						status_co = 1; 
						$('#env_box_co').removeClass('alert_bg');
					}else{
						status_co = 3;
						$('#env_box_co').addClass('alert_bg');
					}
					$('#__status_co').attr('src', image_path + 'status_' + status_co +'.png');
					
					var status_co2 = 1;
					if(sensorVO.co2 <= 1.5){//정상
						status_co2 = 1; 
						$('#env_box_co2').removeClass('alert_bg');
					}else{
						status_co2 = 3;
						$('#env_box_co2').addClass('alert_bg');
					}
					$('#__status_co2').attr('src', image_path + 'status_' + status_co2 +'.png');
					
					
					var status_h2s = 1;
					if(sensorVO.h2s <= 10){//정상
						status_h2s = 1;
						$('#env_box_h2s').removeClass('alert_bg');
					}else{
						status_h2s = 3;
						$('#env_box_h2s').addClass('alert_bg');
					}
					$('#__status_h2s').attr('src', image_path + 'status_' + status_h2s +'.png');
					
					var status_gas = 1;
					if(sensorVO.gas <= 10){//정상
						status_gas = 1; 
						$('#env_box_gas').removeClass('alert_bg');
					}else{
						status_gas = 3;
						$('#env_box_gas').addClass('alert_bg');
					}
					$('#__status_gas').attr('src', image_path + 'status_' + status_gas +'.png');
				
					
				
					var status_dust = 1;
					if(sensorVO.dust <= 150){
						status_dust = 1;
						$('#env_box_dust').removeClass('alert_bg');
					}else if(sensorVO.dust <= 300){
						status_dust = 2; 
						$('#env_box_dust').removeClass('alert_bg');
					}else{
						status_dust = 3;
						$('#env_box_dust').removeClass('alert_bg');
					}/*else {//150이상
						status_dust = 4;					
						$('#env_box_gas').addClass('alert_bg');
					}*/
					$('#__status_dust').attr('src', image_path + 'dust_' + status_dust +'.png');
				}
			
			}
}

/**alert**/

 var disaster_last_id = -1;	
 function checkDisaster(isCallback, site_id){
 		
 		$.ajax({
 			type : "GET",
 			url : "./json/getLastDisaster",				
 			data : {
 				site_id : site_id
 			},
 			cache : false,			
 			success : function(json	,status){
 				if(isCallback){
 					onSuccessCheckDisaster(json, status);
 				}else{
 					json_data  = JSON.parse(json);			
 	 				if(json_data.result =="true"){	
 	 					
 	 					console.log(json_data);
 	 					var id = json_data.id;
 	 					var content = json_data.content;
 	 					var phone = json_data.phone;
 	 					var role = json_data.role;
 	 					var age = json_data.age;
 	 					var btype = json_data.btype;
 	 					var name = json_data.name;
 	 					var t_name = json_data.t_name;
 	 					var cont_name = json_data.cont_name;
 	 					var delaytime = json_data.delaytime;
 	 					var writetime = json_data.writetime;
 	 					var beacon_num = json_data.beacon_num;
 	 					//var tunnel_num = json_data.tunnel_num;
 	 					var section = json_data.section;
 	 					var section_char = 1;
 	 					if(site_id == 9){//삼천포
 	 						var section_char = 'A'; 	 					
 	 	 					if(section < 0 ){
 	 	 						section_char = '외부'
 	 	 					}else if(section == 1){
 	 	 						section_char = 'A'
 	 	 					}else{
 	 	 						section_char = 'B'
 	 	 					}
 	 					}else if(site_id == 12){//북당진
 	 						var section_char = 'A'; 
 	 						
 	 						switch(section){
 	 						case 1 :	section_char = 'A(하)'; break;
 	 						case 2 :	section_char = 'B(1층)'; break;
 	 						case 3 : 	section_char = 'A(상)';break;
 	 						case 4 : 	section_char = 'C(2,3층)';break;
 	 						default : 	section_char = '외부';break;
 	 						}
 	 					
 	 						
 	 						
 	 					}else{
 	 						section_char = section;
 	 					}
 	 					
 	 				
 	 					
 	 					 if(disaster_last_id == beacon_num){//이전과 같은 id면 pass
 	 						 
 	 					 //}else if(delaytime <  120){//이전과 다른id이고 재난발생이 2분미만이면 표출
 	 				     }else if(delaytime <  120){//이전과 다른id이고 재난발생이 2분미만이면 표출
 	 				    	 alertMP3();
 	 				    	
 	 				    	
 	 				    	 
 	 				    	 //근로자는 01012323 식으로 오고 관리자는 010-123-1232 식으로 옴..
 	 				    	 if(typeof phone != 'undefined' && phone.length > 10 && phone.length < 12){
 	 				    		 var phone_1  = phone.substring(0, 3 );
 	 				    		 var phone_2  = phone.substring(3, 7 );
 	 				    		 var phone_3  = phone.substring(7,phone.length);
 	 				    		 phone =  phone_1 +'-' + phone_2 +'-' + phone_3;
 	 				    	 }
 	 				    
 	 				    	 $('#section_header_' + section).addClass('alert_bg blink_me');
 	 				    	 $('#worker_table_' + section).addClass('alert_bg');
 	 				    	 $('#worker_header_' + section).addClass('alert_bg2');
 	 				    	 
 	 				    	 $('#alert_time').html("[" + writetime + "]"); 	 	
 	 				    	 
 	 				    	if(role == "1"){ //관리자인경우 t_name생략
 	 				    		$('#alert_text').html( name + "(" + cont_name + ")" +  " 님이 <br> " + section_char + " 구역에서 응급요청을<br> 하였습니다." + "<br>전화번호 : "+ phone);
 	 						}else{
 	 							$('#alert_text').html( name + "(" + cont_name + "/" +  t_name + ")" + " 님이 <br> " + section_char + " 구역에서 응급요청을<br> 하였습니다." + "<br>전화번호 : "+ phone);
 	 						}
 	 				    	 
 	 						 $('#alert_btn').click();	  
 	 					 	 //  $('#alert_modal').addClass('animated flash');
 	 						 
 	 						 //history불들어오게끔
 	 						 $('#btn_alert_history').addClass('blink_me');
 	 						 $('#btn_alert_history').css('color','red');
 	 						 
 	 						 
 	 						 disaster_last_id = beacon_num;//비교용
 	 						 
 	 						 
 	 						window.setTimeout(function(){
 	 							closeAlertModal();
 	 							$('#btn_alert_history').removeClass('blink_me');
 	 							$('#btn_alert_history').css('color','white');
 	 					    }, 120 * 1000);
 	 						
 	 					 }else{
 	 					}
 	 				}else{
// 	 					$('#alert_modal').hide();
 	 					closeAlertModal();
 	 					
 	 					
 	 				}
 				}
 				
 				
 			}
 		});
 }
 	
 

 function openAlertModal(){
 	$('#alert_btn').click();
 }
 function closeAlertModal(){ 	
 	$('#alert_modal').click();
 	 $('[id^=section_header_]').removeClass('alert_bg blink_me');
	 $('[id^=worker_table_]').removeClass('alert_bg');
	 $('[id^=worker_header_]').removeClass('alert_bg2');
	 
	 
 }
  

 /**weather info**/	
 /**setting도 함께함**/
function setOpenWeatherAPIInfo(site_id){
	var data = [];
	$.ajax({		
		url: './json/getOpenWeatherAPIInfo',
		data : {site_id : site_id},
		cache : false,
		async : false,
		success : function(json) {
			json_data  = JSON.parse(json);			
			if(json_data.result =="true"){	
				data = json_data;
				console.log(data);
				$('#__weather_img').attr('src', 'images/icons/weather/' +  data.weather_icon + '.png');
				$('#__weather_main').html(data.weather_main);
				$('#__weather_temp').html(parseInt(data.temp) + '℃');
				//__weather_img
				//__weather_cond
				
				//wind_deg
				var deg_text = 'rotate(' + data.wind_deg + 'deg)';
				$('#__wind_dir_arrow').css('-ms-transform', deg_text);
				$('#__wind_dir_arrow').css('-webkit-transform', deg_text);
				$('#__wind_dir_arrow').css('-moz-transform', deg_text);				
			}else{data =[];}
		},
		error : null
	});
	return data;
}
	 

 var audio = new Audio("./images/ping.mp3");
 function alertMP3(){
 	audio.play();
 }

/**earth**/ 
 /**earthquake**/

 var earth_last_id = -1;	
 function checkEarthQuake(){
 		
 		$.ajax({
 			type : "GET",
 			url : "http://kep.gsil.kr/json/getEarthList",				
 			data : {
 				
 			},
 			cache : false,			
 			success : function(json	,status){
 				json_data  = JSON.parse(json);			
 				if(json_data.result =="true"){	
 					var id = json_data.id;
 					var content = json_data.content;

 					var intensity = json_data.intensity;
 					var event_time = json_data.event_time;//발생시간
 					var writetime = json_data.writetime;//발생시간
 					var location = json_data.location;
 					var delaytime = json_data.delaytime; //실제 DB 입력시간 . 발생시간이아님  				
 					
 					 //좌측하단 표현
				     $('#__earth_event_time2').html(event_time);
			    	 $('#__earth_intensity2').html(intensity);
			    	 $('#__earth_location2').html(location);
			    	 
			    	 
 					 if(earth_last_id == id){//이전과 같은 id면 pass 						 
 					 
 				     }else if(delaytime <  (10 * 60)){//이전과 다른id이고 지진 실제 발생시간이 10분미만이면 표출
 				    	 //alertMP3();
 				    	 setConditionTab(1);//cctv때문에
 				    	  				    	 
 						// $('#alert_text').html("[" + writetime + "]<br> " + name + "(" + age + "," + btype + ") 님이 <br> " + tunnel_num + " 구역에서 응급요청을<br> 하였습니다." + "<br>전화번호 : "+ phone);
 				    	 
 				    	 $('#__earth_event_time').html(event_time);
 				    	 $('#__earth_intensity').html(intensity);
 				    	 $('#__earth_location').html(location);
 				    
 						 $('#earth_btn').click();	  
 					
 						 
 						//conditionTab swap 중지
 						setConditionTab(1);//cctv때문에
 						clearInterval(__swap_id);
 						window.setTimeout(function(){
 							__swap_id = window.setInterval(function(){autoSwap();}, t4 * 1000);//conditionTab start
 							closeEarthModal();
 						}, 60 * 1000);
 						 
 						earth_last_id = id;//비교용
 					 }else{
 					}
 				}else{
 					$('#earth_modal').hide();
 					 
 				}
 			}
 		});
 }
 	
 function openEarthModal(){
		 $('#earth_btn').click();
 }
 function closeEarthModal(){ 	
	 	$('#earth_modal').click();
 }
		  
 

/**base**/

var weekday = ['일','월','화','수','목','금','토'];
//08/05 <br>월요일
function getTodayDate(){
	var d = new Date();		 
	var year = d.getFullYear() ;
	var month = d.getMonth() + 1;
	var day = d.getDate();
	var weekday_num = d.getDay();
	
	if(month < 10){month = "0" + month;}	
	if(day < 10){day = "0" + day;}
	//var cur_date = year + '-' + month + '-' + day;
	var cur_date =  month + '/' + day + '<br>' +weekday[weekday_num] + '요일';
	return cur_date;
}

//08-03(월)
function getTodayDate2(){
	var d = new Date();		 
	var year = d.getFullYear() ;
	var month = d.getMonth() + 1;
	var day = d.getDate();
	var weekday_num = d.getDay();
	
	if(month < 10){month = "0" + month;}	
	if(day < 10){day = "0" + day;}
	//var cur_date = year + '-' + month + '-' + day;
	var cur_date =  month + '/' + day + ' (' + weekday[weekday_num] + ')';
	
	return cur_date;
}

//2018-08-05
function getTodayDate3(){
	var d = new Date();		 
	var year = d.getFullYear() ;
	var month = d.getMonth() + 1;
	var day = d.getDate();
	var weekday_num = d.getDay();
	
	if(month < 10){month = "0" + month;}	
	if(day < 10){day = "0" + day;}
	var cur_date = year + '-' + month + '-' + day;
	//var cur_date =  month + '-' + day + ' (' + weekday[weekday_num] + ')';
	
	return cur_date;
}


// 20년 01/17 금요일 (신림 봉천)
function getTodayData4(){
	var d = new Date();		 
	var year = d.getFullYear() ;
	var month = d.getMonth() + 1;
	var day = d.getDate();
	var weekday_num = d.getDay();
	
	if(month < 10){month = "0" + month;}	
	if(day < 10){day = "0" + day;}
	//var cur_date = year + '-' + month + '-' + day;
	var cur_date =  (year-2000) + '년 ' + month + '/' + day + ' ' + weekday[weekday_num] + '요일';
	return cur_date;
}

function startTime(date_div_id, clock_div_id,type) {
	    var today = new Date();
	    var h = today.getHours();
	    var m = today.getMinutes();
	    var s = today.getSeconds();
	    var day = today.getDay();
	    m = checkTime(m);
	    s = checkTime(s);
	    
	    $('#' + clock_div_id).html( h + ":" + m + ":" + s);
	    
	    if(type == 1 )
	    	$('#' + date_div_id).html(getTodayDate());
	    else if (type == 2)
	    	$('#' + date_div_id).html(getTodayDate2());
	    else if (type == 3){
	    	$('#' + date_div_id).html(getTodayDate3());	    	
	    } else if (type == 4) {
	    	$('#' + date_div_id).html(getTodayData4());	   
	    } else
	    	$('#' + date_div_id).html(getTodayDate());
	    
    	
	    var t = setTimeout(function(){startTime(date_div_id, clock_div_id, type); }, 500);
}
function checkTime(i) {
    if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
    return i;
}

function reduce(){
	$('#vlc').attr('width', 50);
	$('#vlc').attr('height', 50);
	$('#vlc').css('width','50px');
	$('#vlc').css('height','50px');
	
}


function checkWebBrowser(){
	var agent = navigator.userAgent.toLowerCase();
	if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {
		console.log("인터넷 익스플로러 브라우저 입니다.");
		BROWSER_TYPE = 1;
	}else {
		if (agent.indexOf("chrome") != -1) {
			console.log("크롬 브라우저입니다.");
			BROWSER_TYPE = 2;
		}
		else if (agent.indexOf("safari") != -1) {
			console.log("사파리 브라우저입니다.");
			BROWSER_TYPE = 3;
		}else if (agent.indexOf("firefox") != -1) {
			console.log("파이어폭스 브라우저입니다.");
			BROWSER_TYPE = 4;
		}/*else if (agent.indexOf("msie") != -1) {
			console.log("인터넷 익스플로러 10 이하 버전입니다.");
		}*/else{
			BROWSER_TYPE = 99;
			console.log("일반 브라우저가 아닙니다.");	
		}
	}
	
	return BROWSER_TYPE;
}


/*
function updateTunnelState(val){
   $.ajax({ type : "POST", url : "./json/insertTimelyValue",  traditional: true , async:false
		, data :{			 
			"type" : 4
			,"value" : val
			,"isMobile" : 0
			
		}
		,cache : false
		,success : function(json,status){
			var json_data  = JSON.parse(json);
			if(json_data.result == "true"){				
				console.log('굴진상태 수정이 완료되었습니다');				
			}else{ alert('error'); }
			
			$('#state_btn').click();		
			
		},error :null
	}); 
	
}*/



function getOpenWeatherApp(city){
	//api.openweathermap.org/data/2.5/weather?q={city name},kr&appid=b6907d289e10d714a6e88b30761fae22
	$.ajax({ type : "GET"
		, url : "http://api.openweathermap.org/data/2.5/weather?q=" + city +",kr&appid=8f3c60d23751b89441a0bfd5fb243e09"
		,  traditional: true , async:true		
		,cache : false
		,success : onSuccessOpenWeatherApp
		,error :null
	}); 
	
}


/**비상 알림 이력**/
function getDisasterList(site_id){
	var data = [];
	$.ajax({
		type : "GET",
		url : "json/getDisasterList",
		data : {
			"site_id" : site_id
		},
		cache : false,
		async : false,
		success : function(json,status) {	
			
			var json_data  = JSON.parse(json);

			if(json_data.result == "true"){				
				for(var i = 0 ; i < json_data.list.length ; i ++){
					json_data.list[i].no = i + 1;					
					if(json_data.list[i].role == "1"){
						json_data.list[i].gubun = "관리자";
					}else{
						json_data.list[i].gubun = "근로자";
					}
					
				}
				data = json_data.list;
				
			}else{
				
			}
			
		}
	});
	
	return data;
	
}







/**어디에 위치시킬지**/
/**timelyValue**/
/**굴진현황**/
var type1_value =
	[{val : -1, name : '자 동'},
	 {val : 1 ,name : '천 공'},
	 {val : 2 ,name : '장 약'},
	 {val : 3 ,name : '발파/환기'},
	 {val : 4 ,name : '버력처리'},
	 {val : 5 ,name : '지보설치'},
	 {val : 6 ,name : '숏크리트'},
	 {val : 7 ,name : '강관다단'},
	 {val : 99, name : '작업대기'}
	];

var type2_value = 
	[
	 {val : 1 ,name : 'A'},
	 {val : 2 ,name : 'B-1'},
	 {val : 3 ,name : 'B-2'},
	 {val : 4 ,name : 'C-1'},
	 {val : 5 ,name : 'C-2'},
	 {val : 6 ,name : 'D-1'},
	 {val : 7 ,name : 'D-2'},
	 {val : 8 ,name : 'E-1'},
	 {val : 9 ,name : 'E-2A'},
	 {val : 10,name : 'E-2B'},
	 {val : 11 ,name : 'P-1'},
	 {val : 12 ,name : 'P-2'},
	 {val : 13 ,name : 'RP-1'},
	 {val : 14 ,name : 'RP-2'},
	 {val : 15 ,name : 'RP-3'},
	 {val : 16 ,name : '3RP-1'},
	 {val : 17 ,name : '3RP-2'},
	 {val : 18 ,name : '3RP-3'},
	 {val : 19 ,name : 'ERP-1'}
	];


function getTimelyTypeValueName(type, value) {
	  var list = [];
	  if(type == 1) list = this.type1_value;
	  if(type == 2) list = this.type2_value;
	  
	  for(var i = 0 ; i < list.length ; i++ ){
		  if(list[i].val == value){
			  return list[i].name;
		  } 
	  }
	  
	  return value;
}


function getTypeValueList(){
	return [{ type : 1 , list : type1_value}, {type : 2, list : type2_value}];
}

/**/




function setExpandTable(table_id){

	//expand처리
	$('#' + table_id).on('expand-row.bs.table', function(e, index, row, $detail) {
	//  var res = $("#desc_" + row.id).html();
	  var res = $('#'+ table_id + '_desc_' + index).html();
	  $detail.html(res); 
	});

	$('#' + table_id).on("click-row.bs.table", function(e,  row, $tr) {
		
	   console.log("Clicked on: " + $(e.target).attr('class'), [e, row, $tr]);
	  if ($tr.next().is('tr.detail-view')) {
		  $('#' + table_id).bootstrapTable('collapseRow', $tr.data('index'));
	  } else {
		  $('#' + table_id).bootstrapTable('expandRow', $tr.data('index'));
	  }

	  if(table_id == 'noticeTable'){
		  insertReadCheck(row.id);
	  }
	 
	
	  
	});
}

function insertReadCheck( type_id ){
	var user_id = '${sessionScope.userLoginInfo.id}';
	var type= 1;
	///json/insertReadCheck
	$.ajax({
		type : "POST",
		url : "./json/insertReadCheck",				
		data : {
			type : type
			,type_id : type_id
			//,user_id : user_id
			,isMobile : 0
		},
		async: false,
		cache : false,			
		success : function(json	,status){
			console.log(json);
		}
	});

}
