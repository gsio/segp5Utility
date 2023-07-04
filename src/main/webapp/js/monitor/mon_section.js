function resizeFrameSize() {	
    $('#ss_cont').height(window.innerHeight);
    $('#title_left').css('line-height', $('#title_left').height() + 'px');  
    $('#alert_body').height(window.innerHeight / 2 + 20 + 'px');
}

var weekday = ['일', '월', '화', '수', '목', '금', '토'];

function getTodayDate() {
    var d = new Date();
    var year = d.getFullYear();
    var month = d.getMonth() + 1;
    var day = d.getDate();
    var weekday_num = d.getDay();

    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    var cur_date = year + ' - ' + month + ' - ' + day + ' ' + weekday[weekday_num];
    return cur_date;
}

function startTime() {
    var today = new Date();
    $('#cur_date').html(getTodayDate());
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('clock').innerHTML = h + ":" + m + ":" + s;
    var t = setTimeout(startTime, 500);
}

function checkTime(i) {
    if (i < 10) {
        i = "0" + i;
    } // add zero in front of numbers < 10
    return i;
}

var MOBILE_TYPE = 0;
function checkAgent() {
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
	
	if(MOBILE_TYPE != 0){
		$('#ss_cont').css('position', 'absolute');
		$('#ss_cont').css('background-color', '#20324c');
	}
}


function setDisableSection(){
	// 37~50
	// 53~66
	// 71~84
	// 87~108
	
	let tanks = [];
	for(let i=37; i<=50; i++) tanks.push(i);
	for(let i=53; i<=66; i++) tanks.push(i);
	for(let i=71; i<=84; i++) tanks.push(i);
	for(let i=87; i<=108; i++) tanks.push(i);
	
	for(let i=0;i<tanks.length;i++) {
		let target = document.querySelector(".__tank_"+tanks[i]);
		let targetIndex = target.className.indexOf("__process");
		if(targetIndex > 0)
		{
			let targetClass = target.className.substr(targetIndex, target.className.length);
			target.classList.remove(targetClass);
			target.children[0].style.color = "white";
			target.children[1].style.color = "transparent";
			target.onclick = null;
		}
	}
}


function getSectionList(){
	$('.tank_count').html("");
    $.ajax({
        type : "GET",
        url : './seg/sections',
        data : {
            site_id : CUR_SITE_ID            
        },
        async: true,
        cache : false,
        success : function(list ,status){
        	$('#tank_info_log').empty();
        	$('[class^=tank]').removeClass('__process_0 __process_1 __process_2 __process_3 __process_4 __process_5 __process_6 __process_99 __process_-99');
        	if(list.length > 0) {        
            	setSectionInfo(list);
        	}   
			setDisableSection();
        }
    });
}

function setSectionInfo(list) {
	var total_worker_count = 0;
	$('.tank').css("color", "white");
    for(var i = 0 ; i < list.length; i++) {    	
    	total_worker_count += list[i].beacon_count;    	
		$('.__tank_' + list[i].section + ' > .tank_count').html(list[i].beacon_count);
		$('.__tank_' + list[i].section).addClass('__process_' + list[i].state);
		$('.__tank_' + list[i].section).css("color", "black");	
		if(list[i].sec_type == 2) {
			$('.__tank_' + list[i].section + ' > .tank_num').html('#'+list[i].sec_name);
		}
		setUseSectionInfo(list[i].section, list[i].sec_type, list[i].sec_name, list[i].input_state, list[i].state, list[i].state_count, list[i].beacon_count, list[i].has_env, list[i].hole_state);	
    }  
    $('#worker_cnt').html(total_worker_count);
    $('#pre_tank_cnt').html(list.length);
}

function setUseSectionInfo(section, sec_type, sec_name, input_state, state, state_count, beacon_count, has_env, hole_state) {
    var html = '';

    if(beacon_count > 0) {
    	   html += '<div class="tank_info">';
    } else {
    	   html += '<div class="tank_info" style="display: none;">';
    }

    
	if(sec_type == 2) {
	    html += '<div class="tank_num_box num_box_type_2">';
		html += '<div id="__section_' + section + '" onclick="openSectionOverlay(' + section + ',' + sec_type + ',\'' + sec_name + '\',' + input_state + ',' + state + ',' + state_count + ',' + beacon_count +',' + has_env + ')" class="tank_num_section">' + sec_name + '</div>';
	    html += '</div>';
	    html += '<div class="tank_state state_box_type_2">';
	} 
	else {
	    html += '<div class="tank_num_box num_box_type_1">';
		html += '<div id="__section_' + section + '" onclick="openSectionOverlay(' + section + ',' + sec_type + ',\'' + sec_name + '\',' + input_state + ',' + state + ',' + state_count + ',' + beacon_count +',' + has_env + ')" class="tank_num_section">' + section + '</div>';
	    html += '</div>';
	    html += '<div class="tank_state state_box_type_1">';
	}
    
    html += '<span style="padding: 0vh 0.2vh 0vh 0.2vh;">' + getSectionState(state);	
    html += '<span class="bar">|</span>';
    html += '<span style="padding: 0vh 0.2vh 0vh 0.2vh;">' + beacon_count + '</span>명';
    html += '</div>';
    
    if(has_env == 1) {
    	html += '<div class="tank_has_env"><i class="fas fa-tint" style="color: 0vh 1vh 0vh 1vh;"></i></div>';
    }
    else {
       	html += '<div class="tank_has_env"><i class="fas fa-tint-slash"></i></div>';
    }
    
    if(hole_state >= 0) {
    	if(hole_state == 0) {
    		html += '<div class="tank_has_hole"><img src="images/monitor/16/hole_on.png"></div>';
    	}
    	else {
    		html += '<div class="tank_has_hole"><img src="images/monitor/16/hole_off.png"></div>';
    	}
    }
    
    html += '</div>';

    $('#tank_info_log').append(html);
    
    $('#__section_' + section).addClass('__process_' + state);
    
}


function setSectionMap(num) {
    $('[id^=_section_]').hide();
    $('#_section_' + num + 'F').show();
    $('[class^=btn_floor]').removeClass('selected');    
    $('.btn_floor_' + num + 'F').addClass('selected');      
}

/** 팝업 관련 전역 변수 **/
var CUR_OVERLAY_SECTION = -1;
var CUR_OVERLAY_SITE_ID = -1;
var CUR_OVERLAY_INPUT_STATE = -1;

function callSectionOverlay(section) {	
	$('#__section_'+section).click();
}

function openSectionOverlay(section, sec_type, sec_name, input_state, state, state_count, beacon_count, has_env) {	
   	
	getWorkStateList();
	
	CUR_OVERLAY_SECTION = section;   
	CUR_OVERLAY_SITE_ID = CUR_SITE_ID;
	CUR_OVERLAY_INPUT_STATE = input_state;
  
	if(sec_type == 2) {
		$('#section_modal_title').text('#' + sec_name);
	}
	else {
		$('#section_modal_title').text('#' + section + ' ' + sec_name);
	}

	$('#section_tot_worker').text(beacon_count);
	$('#section_state_worker').text(state_count);

	$('#select_section_process').text(getSectionState(state));
	$('#select_section_process').removeClass();
	$('#select_section_process').addClass('__process_' + state);
 	    
	if(input_state == -1) {
		$('#isAuto_state').show();
	}
	else {
		$('#isAuto_state').hide();
	} 	    

	if(has_env == 1) {
		$('#section_env_box').show();
		$('#section_worker_box').width('76.6%');
	}
	else {
		$('#section_env_box').hide();
		$('#section_worker_box').width('99.4%');
	}

	setSectionOverlay();
	$('#section_modal').modal('show');    	

}

function setSectionOverlay() {
	getSectionData();
}

function getSectionData() {
    $.ajax({
        type : "GET",
        url : './seg/sections',
        data : {
            site_id : CUR_SITE_ID,
            section : CUR_OVERLAY_SECTION
        },
        async: true,
        cache : false,
        success : function(json, status) {      
			if(json.length > 0) {
				CUR_OVERLAY_INPUT_STATE = json[0].input_state;
            	if(json[0].has_env == 1) {
            		getSensorInfo();
            	} 
            	if(json[0].input_state == -1){
            		$('#isAuto_state').show();
            	} else {
            		$('#isAuto_state').hide();
            	}
            	getLocationInfo();
            	if(json[0].sec_type == 2) {
                  	$('#section_modal_title').text('#' + json[0].sec_name);
            	} else {
                  	$('#section_modal_title').text('#' + json[0].section + ' ' + json[0].sec_name);
            	}
       
            	$('#section_state_worker').text(json[0].state_count);
             	$('#section_tot_worker').text(json[0].beacon_count);
             	
        		$('#select_section_process').text(getSectionState(json[0].state));
         	    $('#select_section_process').removeClass();
         	    $('#select_section_process').addClass('__process_' + json[0].state);   
            }
        }
    });	
}

function getLocationInfo() {
    $.ajax({
        type : "GET",
        url : './seg/locations',
        data : {
            site_id : CUR_OVERLAY_SITE_ID,
            section : CUR_OVERLAY_SECTION,
            input_state : CUR_OVERLAY_INPUT_STATE
        },
        async: false,
        cache : false,
        success: function (json, status) {
        	sectionHtml = "";
        
            for (var i = 0; i < json.length; i++) {
                nullParse(json, i); 
                setLocationInfo(json, i);
            }

            $('#section_worker_box').html(sectionHtml);
        }
    });
}

function nullParse(json, i) {
    if(json[i].cont_name == null || json[i].cont_name == "") {
        json[i].cont_name = '---'
    }
    if(json[i].name == null || json[i].name == "") {
        json[i].name = '---'
    }
    if(json[i].btype == null || json[i].btype == "") {
        json[i].btype = '---'
    }
    if(json[i].wt_name == null || json[i].wt_name == "") {
        json[i].wt_name = '---'
    }
    if(json[i].start_time == null || json[i].start_time == "") {
        json[i].start_time = '---'
    }
    if(json[i].edudate == null || json[i].edudate == "") {
        json[i].edudate = '---'
    }
    if(json[i].sealed_date1 == null || json[i].sealed_date1 == "") {
        json[i].sealed_date1 = '---'
    }
    if(json[i].sealed_date2 == null || json[i].sealed_date2 == "") {
        json[i].sealed_date2 = '---'
    }
    if(json[i].sealed_date3 == null || json[i].sealed_date3 == "") {
        json[i].sealed_date3 = '---'
    }
    if(json[i].sealed_date4 == null || json[i].sealed_date4 == "") {
        json[i].sealed_date4 = '---'
    }
}

function setLocationInfo(json, i) {
	CUR_OVERLAY_SITE_ID = json[i].site_id;
	var sealed_complete = json[i].sealed_complete;
	var section_worker_border = ' ';	
	
	if(sealed_complete == 1){
		section_worker_border = 'section_worker_border_blue'; 
	}else if(sealed_complete == 2){
		section_worker_border = 'section_worker_border_red';
	}else{
		section_worker_border = ' ';
	}   
    
    sectionHtml += '<div class="section_worker '+  section_worker_border + '">';
    
    // 1. 수조 기본정보
    if(beacon_array[json[i].mb_idx] == 0) {
    	sectionHtml += '<div class="worker_default_info ' + json[i].mb_idx + ' on">';
    }
    else {
    	sectionHtml += '<div class="worker_default_info ' + json[i].mb_idx + '">';
    }
        
	sectionHtml += '<div class="worker_photo_box">';	    
	if(json[i].eduimage != '') {
		sectionHtml += '<span>' + '<img src="image_thumb?virtname='+json[i].eduimage+'&height=150&width=100" onerror="this.src=\'images/noimage.png\'">' + '</span>';
	} else {
		sectionHtml += '<span>' + '<img src="images/noimage.png">' + '</span>';	
	}	
	sectionHtml += '</div>';//end worker_photo_box
	
	sectionHtml += '<div class="section_worker_info">'
	sectionHtml += '<div class="worker"><span class="contName">' + json[i].cont_name + '</span>';
	sectionHtml += '</div>';
	sectionHtml += '<div class="worker infos">';
	sectionHtml += '<div><span>' + json[i].name + '</span></div>';
	sectionHtml += '<div>(<span>' + json[i].btype + '</span>)</div>';
	sectionHtml += '</div>';
	
	sectionHtml += '<div class="worker"><span class="workType">' + json[i].wt_name + '</span>';	
	sectionHtml += '<span class="text-center eduBtn">교육일</span>';
	sectionHtml += '</div>';
	
	sectionHtml += '<div class="worker"><span class="startTime">'+ json[i].start_time + '</span>';	
	//sectionHtml += '<div class="worker"><span class="startTime"></span>';
	
    if(json[i].section) {
	   var workMinColor = 'green';		    
	   /* 색 변화 빼달라는 요청
	   if(json[i].work_min >= 60) workMinColor = 'red';        
	   else if(json[i].work_min >= 50) workMinColor = 'yellow';
	   else workMinColor = 'green';	
	    */	
    }  
    
    //sectionHtml += '<span class="text-center eduBtn">교육일</span>';
	sectionHtml += '<span class="text-center workMin ' + workMinColor + '">' + json[i].work_min + '분' + '</span>';
	sectionHtml += '</div>';	
	sectionHtml += '</div>';//end section_worker_info
	
    sectionHtml += '</div>';//end worker_default_info
    
    if(beacon_array[json[i].mb_idx] == 1) {
        sectionHtml += '<div class="worker_educatiot_info ' + json[i].mb_idx + ' on">';
    }
    else {
    	sectionHtml += '<div class="worker_educatiot_info ' + json[i].mb_idx + '">';
    }
    
    sectionHtml += '<div class="back_default_info">';
    sectionHtml += '<span class="text-center back_btn"><i class="fas fa-undo"></i></span>';
    sectionHtml += '</div>';    
    
	sectionHtml += '<div class="section_worker_edu">';
	sectionHtml += '<div class="new_edudate_box">'	;
	
	if(json[i].beacon_role == 1){//관리자
	
	}else{//근로자
		sectionHtml += '<div class="title">신규교육일: &nbsp;' +json[i].edudate+ '</div>';
	}    
	sectionHtml += '</div>';
   	
	sectionHtml += '<div class="sealed_title">밀폐교육일</div>';
	sectionHtml += '<div class="sealed_edudate_box">';
	sectionHtml += '<div class="items">';
	sectionHtml += '<div class="sealed_round">1차: </div><div class="sealed_date">' + json[i].sealed_date1 + '</div>';
	sectionHtml += '<div class="sealed_round">2차: </div><div class="sealed_date">' + json[i].sealed_date2 + '</div>';
	sectionHtml += '</div>';
	sectionHtml += '<div class="items">';
	sectionHtml += '<div class="sealed_round">3차: </div><div class="sealed_date">' + json[i].sealed_date3 + '</div>';
	sectionHtml += '<div class="sealed_round">4차: </div><div class="sealed_date">' + json[i].sealed_date4 + '</div>';
	sectionHtml += '</div>';
	sectionHtml += '</div>';	
	
	sectionHtml += '</div>';//end section_worker_edu    
    
    sectionHtml += '</div>';//end worker_education_info    
 
    sectionHtml += '</div>';//end section_worker
}

function getSensorInfo() {
	$.ajax({
		type: "GET",
        url: "./seg/sensors",
        data: {
            site_id : CUR_OVERLAY_SITE_ID,
            section : CUR_OVERLAY_SECTION
        },
        async: false,
        cache: false,
        success: function (json, status) {     
            if(json.length > 0) {
            	$('#section_env_box').css("display", "inherit");
            	setSensorInfo(json[0]);
            } else {
            	$('#section_env_box').css("display", "none");
            }
        }
    });
}

function setSensorInfo(sensor) {
	$('#__o2').html(sensor.o2);
	$('#__co2').html(sensor.co2);
	$('#__co').html(sensor.co);
	$('#__h2s').html(sensor.h2s);
	$('#__ch4').html(sensor.lel);
	
	$('.sensor_state').removeClass('normal caution danger')
	
	if (sensor.o2_state == 1) {
		$('#__o2_state').addClass('danger');
	} else {
		$('#__o2_state').addClass('normal');
	}
	
	if (sensor.co2_state == 1) {
		$('#__co2_state').addClass('danger');
	} else {
		$('#__co2_state').addClass('normal');
	}
	
	if (sensor.co_state == 1) {
		$('#__co_state').addClass('danger');
	} else {
		$('#__co_state').addClass('normal');
	}
	
	if (sensor.h2s_state == 1) {
		$('#__h2s_state').addClass('danger');
	} else {
		$('#__h2s_state').addClass('normal');
	}
	
	if (sensor.lel_state == 1) {
		$('#__ch4_state').addClass('danger');
	} else {
		$('#__ch4_state').addClass('normal');
	}	
}

function playAlertSound(){
	console.log("[playAlertSound] - G_SOUND_ON", G_SOUND_ON)
	try {
		if(G_SOUND_ON) audio.play();
	} catch(err){
		console.log(err);
	}
}

function playHoleSound(){
	try{
		if(G_SOUND_ON) audio1.play();
 	}
	catch(err){
 		console.log(err);
 	}
}

function openEmergencyModal(){
	getEmergencyList();	
	$('#emergency_modal').modal('show');
}

function closeEmergencyModal() {	
	$("#emergency_modal").click()
}

function getEmergencyList(){
	$.ajax({
		type: "GET",
		url: './seg/emergency',
		data: {site_id: CUR_SITE_ID},
		async: true,
		cache: false,
		success: function (list, status) {
			$('#emergency_table').bootstrapTable();
			if(list.length > 0) {				
				for(var i=0; i < list.length; i++) {
					if(list[i].sec_type == 1) {
						list[i].section_name = list[i].section_name + " (" + list[i].section + ")";
					}
					list[i].write_time = list[i].write_time.replace(".0", "");
				}				
				$('#emergency_table').bootstrapTable('load', list );	            	
				setEmergencyInfo(list[0]);
	            $('#emergency_table tbody tr:first-child').css('background-color', '#ff4343');
			}else{
				$('#emergency_table').bootstrapTable('load', [] );
			}
		}
	});
}

function setEmergencyInfo(emergency){
	$('#emergency_writetime').html(emergency.write_time.replace(".0", ""));	 
	$('#emergency_section').html(emergency.section_name);
    if(emergency.app_type == 0) {
   	 $('#emergency_section_type').html("상부");
    }
    else {
   	 $('#emergency_section_type').html("하부");
    }
	$('#emergency_cont_name').html(emergency.cont_name);
    $('#emergency_name').html(emergency.name);
    $('#emergency_btype').html(emergency.btype);
    emergency.phone = emergency.phone.replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-");
    $('#emergency_phone').html(emergency.phone);
    $('#emergency_photo').attr('src', 'image?virtname=' + emergency.eduimage);
}

var LAST_EMERGENCY_ID = -1;	// 최신 알림 체크
function checkEmeregencyData() {
    $.ajax({
		type: "GET",
        url: './seg/emergency',
        data: {
        	site_id: CUR_SITE_ID        	
        },
        async: true,
        cache: false,
        success: function (list, status) {
			if(list.length > 0){				
				var obj = list[0];				
                if(obj.time_diff_second <= 30) {                
					if(LAST_EMERGENCY_ID != obj.id){
						LAST_EMERGENCY_ID = obj.id;
                    	playAlertSound();
                    	setEmergencyInfo(obj);
                        setTimeout(closeEmergencyModal, 30 * 1000); 
                        if(G_ALARM_ON) openEmergencyModal();
                    }
                }
            }
        }
    });
}

function processOverlay() {
    $('#process_modal').modal('show');    
}

//select 요소 배경색 변경 함수
function changeState(state) {
    var req = new XMLHttpRequest();
    req.open('POST', './seg/change/state');
    req.setRequestHeader('Content-type', 'application/json');
    req.send(JSON.stringify({
        site_id: CUR_OVERLAY_SITE_ID,
        place_id: 0,
        section: CUR_OVERLAY_SECTION,
        state : state,
        writer_user_id : WRITER_USER_ID
    }));
    req.onreadystatechange = function (e) {
        if (req.readyState === XMLHttpRequest.DONE) {
            if(req.status === 200) {
                console.log(req.responseText);               
            } else {
                console.log("Error!");
            }
        }
    };
    $('#process_modal').click();
    setSectionOverlay();
}

function openAlertModal(){
	$('#alert_modal').modal('show');
}

function sendAlert(type){	
	var input = confirm('해당수조에 위급사항 메시지를 보내시겠습니까?');	
	if(input){
		$('#btn_alert').addClass('blink')
		setTimeout(function(){
			$('#btn_alert').removeClass('blink');
		}, 10 * 1000);
			
	    var req = new XMLHttpRequest();
	    if(CUR_OVERLAY_SECTION == -1){	    	
	    	req.open('POST', './seg/insert/alert/all');	    	
	    }else{
	    	req.open('POST', './seg/insert/alert');	
	    }
	    
	    req.setRequestHeader('Content-type', 'application/json');
	    req.send(JSON.stringify({
	        site_id: CUR_OVERLAY_SITE_ID,
	        place_id: 0,
	        section: CUR_OVERLAY_SECTION,
	        type : type,
	        writer_user_id : WRITER_USER_ID
	    }));

	    req.onreadystatechange = function (e) {
	        if (req.readyState === XMLHttpRequest.DONE) {
	            if(req.status === 200) {
	                console.log(req.responseText);
	                $('#SSAlertMSG_modal').click();
	            } else {
	                console.log("Error!");
	            }
	        }
	    };
	}
}

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
				//console.log(data);
				$('#__weather_img').attr('src', 'images/icons/weather/' +  data.weather_icon + '.png');
				$('#__weather_main').html(data.weather_main);
				$('#__weather_temp').html(Math.round(data.temp) + '℃');		
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

function openAbnormalModal(){
	getAbnormalList();	
	$('#abnormal_modal').modal('show');
}

function closeAbnormalModal() {	
	$("#abnormal_modal").click()
}

function getAbnormalList(){
	$.ajax({
		type: "GET",
		url: './seg/abnormalSensor',
		data: {site_id: CUR_SITE_ID},
		async: true,
		cache: false,
		success: function (list, status) {
			$('#abnormal_sensor_table').bootstrapTable();
			if(list.length > 0) {				
				for(var i=0; i < list.length; i++) {
					if(list[i].section_type == 1) {
						list[i].section_name = list[i].section_name + " (" + list[i].section + "번 수조)";
					} else {
						list[i].section_name = list[i].section_name;
					}
					list[i].write_time = list[i].write_time.replace(".0", "");
				}				
				$('#abnormal_sensor_table').bootstrapTable('load', list );	            	
				setAbnormalSensorInfo(list[0]);
	            $('#abnormal_sensor_table tbody tr:first-child').css('background-color', '#ff4343');
			}else{
				$('#abnormal_sensor_table').bootstrapTable('load', [] );
			}
		}
	});
}

function setAbnormalSensorInfo(sensor){
	$('#abnormal_sensor_write_time').html(sensor.write_time.replace(".0", ""));	 
	$('#abnormal_sensor_section_name').html(sensor.section_name);
	$('#abnormal_sensor_sensor_type').html(sensor.sensor_name);
	var sensor_unit = sensor.sensor_unit;	
	if(sensor_unit == "per") {
		sensor_unit = '%';
    }
    $('#abnormal_sensor_value').html(sensor.value + sensor_unit);
}

var LAST_ABNORMAL_ID = -1;	// 최신 알림 체크
function checkAbnormalData() {
    $.ajax({
		type: "GET",
        url: './seg/abnormalSensor',
        data: {
        	site_id: CUR_SITE_ID        	
        },
        async: true,
        cache: false,
        success: function (list, status) {
			if(list.length > 0){				
				var obj = list[0];				
                if(obj.time_diff_second <= 30) {                
					if(LAST_ABNORMAL_ID != obj.id){
						LAST_ABNORMAL_ID = obj.id;
                    	playAlertSound();
                    	setAbnormalSensorInfo(obj);
                        setTimeout(closeAbnormalModal, 30 * 1000); 
                        if(G_ALARM_ON) openAbnormalModal();
                    }
                }
            }
        }
    });
}

function getWorkStateList() {
	$.ajax({
        type : "GET",
        url : './seg/workStateList',
        data : {
            site_id : CUR_SITE_ID
        },
        async: false,
        cache : false,
        success: function (list, status) {        	        	
        	var html = "";        	
            for (var i = 0; i < list.length; i++) {
            	html += '<div class="process __process_'+list[i].id+'" onclick="changeState('+list[i].id+')" style="background:#'+list[i].color+'">'+list[i].name+'</div>';
            }
            $('#work_state_box').html(html);
        }
    });
}

function checkHoleOpen() {
	$.ajax({
		type: "GET",
        url: './seg/getHoleOpenData',
        data: {
        	site_id: CUR_SITE_ID,
        	id : G_HOLE_ID
        },
        async: true,
        cache: false,
		success: function (data, status) {
			if(data != null){			
                if(data.time_diff_sec <= 30 && data.state == 0) {   
					if(G_HOLE_ID != data.id){
						G_HOLE_ID = data.id;	  
                    	setHoleInfo(data);     
                    	setTimeout(closeHoleModal, 4 * 1000); 
                    	if(G_ALARM_ON) $('#hole_modal').modal('show');    
                    	playHoleSound(); 
                    }
               }
            }
        }
    });
}

function setHoleInfo(data){
	$('#hole_write_time').html(data.write_time.replace(".0", ""));	 
	$('#hole_name').html(data.hole_name);
	var section_name = data.section_name;
	if(data.section_type == 1) {
		section_name += ' (#' + data.section + ")";
	}
	$('#hole_section_name').html(section_name);
}

function closeHoleModal() {	
	$("#hole_modal").click();
}


function openHoleModal() {
	$('#hole_log_modal').modal('show');
	$.ajax({
		type: "GET",
		url: './seg/getHoleLog',
		data: {
			site_id: CUR_SITE_ID		
		},
		async: true,
		cache: false,
		success: function (list, status) {
			$('#hole_log_table').bootstrapTable();
			if(list.length > 0) {
				for(var i=0; i < list.length; i++) {					
					list[i].no = i+1;
					if(list[i].state == 0) {
						list[i].hole_state = "<span style='font-weight: bold; color: #BA1111;'>열림</span>";						
					} else {
						list[i].hole_state = "<span style='font-weight: bold; color: #3A751C;'>닫힘</span>";										
					}		
					if(list[i].section_type == 1) {
						list[i].section_type = '밀폐수조';
						list[i].section = '#' + list[i].section;
					}
					else {
						list[i].section_type = '일반구역';			
						list[i].section = '-';
					}
					list[i].write_time = list[i].write_time.replace(".0", "");
				}				
				$('#hole_log_table').bootstrapTable('load', list );	
			}else{
				$('#hole_log_table').bootstrapTable('load', [] );
			}
		}
	});
}



function swapAlarmSetting() {	
	let input = false;    	
	input = confirm('알림설정을 변경하시겠습니까? *소리 설정도 변경됩니다');	
	if(input) {
		$('#btn_alarm').removeClass('fa-toggle-on');
		$('#btn_alarm').removeClass('fa-toggle-off');	
		
		if(G_ALARM_ON){
			$('#btn_alarm').addClass('fa-toggle-off');
			$('#btn_sound').removeClass('fa-volume-up');	
			$('#btn_sound').addClass('fa-volume-mute');
			$('#btn_sound').addClass('disabled');
			G_ALARM_ON = false;
			G_SOUND_ON = false; 	
			switchingAlarm(0);
		}
		else {
			$('#btn_alarm').addClass('fa-toggle-on');
			$('#btn_sound').removeClass('fa-volume-mute');	
			$('#btn_sound').addClass('fa-volume-up');
			$('#btn_sound').removeClass('disabled');
			G_ALARM_ON = true;
			G_SOUND_ON = true;		
			switchingAlarm(1);
		}	
	}
}

function swapSoundSetting() {
	if(!G_ALARM_ON) {
		$('#btn_sound').removeClass('fa-volume-mute');
		$('#btn_sound').removeClass('fa-volume-up');	
		$('#btn_sound').addClass('disabled');		
		$('#btn_sound').addClass('fa-volume-mute');
		return;
	}
	else {		
		let input = false;    	
		input = confirm('소리 설정을 변경하시겠습니까?');	
		if(input) {
			$('#btn_sound').removeClass('fa-volume-mute');
			$('#btn_sound').removeClass('fa-volume-up');	
			$('#btn_sound').removeClass('disabled');	
			if(G_SOUND_ON){				
				$('#btn_sound').addClass('fa-volume-mute');
				G_SOUND_ON = false;
				switchingSound(0);
			}
			else{
				$('#btn_sound').addClass('fa-volume-up');		
				G_SOUND_ON = true;	
				switchingSound(1);
			}	
		}
	}
}

function setForceAlarmState(isService) {
	
	$('#btn_alarm').removeClass('fa-toggle-on');
	$('#btn_alarm').removeClass('fa-toggle-off');	
	
	if(isService == 1) {
		G_ALARM_ON = true
		$('#btn_alarm').addClass('fa-toggle-on');
		$('#btn_sound').removeClass('fa-volume-mute');	
		$('#btn_sound').addClass('fa-volume-up');
		$('#btn_sound').removeClass('disabled');
	}
	
	else {
		G_ALARM_ON = false
		$('#btn_alarm').addClass('fa-toggle-off');
		$('#btn_sound').removeClass('fa-volume-up');	
		$('#btn_sound').addClass('fa-volume-mute');
		$('#btn_sound').addClass('disabled');
	}
}


function setForceSoundState(alarm_state, isService) {
	
	if(alarm_state == 0) {
		$('#btn_sound').removeClass('fa-volume-mute');
		$('#btn_sound').removeClass('fa-volume-up');	
		$('#btn_sound').addClass('disabled');		
		$('#btn_sound').addClass('fa-volume-mute');	
	}
	else {		
		$('#btn_sound').removeClass('fa-volume-mute');
		$('#btn_sound').removeClass('fa-volume-up');	
		$('#btn_sound').removeClass('disabled');	
		if(isService == 1) {
			G_SOUND_ON = true;	
			$('#btn_sound').addClass('fa-volume-up');		
		}
		else {
			$('#btn_sound').addClass('fa-volume-mute');
			G_SOUND_ON = false;
		}
	}
}

function switchingAlarm(turnOn) {
	$.ajax({
		type: "POST",
		url: './seg/change/alarm/state',
		data: {
			site_id: CUR_SITE_ID,
			isService: turnOn
		},
		async: true,
		cache: false,
		success: function (res, status) {
			console.log("[알람 설정 변경]")
		}
	});
}

function switchingSound(turnOn) {
	$.ajax({
		type: "POST",
		url: './seg/change/sound/state',
		data: {
			site_id: CUR_SITE_ID,
			isService: turnOn
		},
		async: true,
		cache: false,
		success: function (res, status) {
			console.log("[소리 설정 변경]")
		}
	});
}

function checkDidSetting() {
	$.ajax({
		type: "GET",
        url: './seg/getDidSetting',
        data: {
        	site_id: CUR_SITE_ID
        },
        async: true,
        cache: false,
		success: function (data, status) {
			if(data != null){ 				
				if(data.time_diff_sec <= 30) {           
					G_DID_SET_ID = data.id;
					setForceAlarmState(data.alarm_state);
					setForceSoundState(data.alarm_state, data.sound_state);
                }         
				else {
					if(G_DID_SET_ID == 0) {
						G_DID_SET_ID = data.id;
						setForceAlarmState(data.alarm_state);
						setForceSoundState(data.alarm_state, data.sound_state);
					}
				}
            }
        }
    });
}