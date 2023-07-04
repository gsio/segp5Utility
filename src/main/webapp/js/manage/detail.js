function update() {
	getBeaconList();
	getNFCList();
	getHoleList();
	getSectionList();	
	getScannerList();
}

// ----------------------------------
// Beacon
function getBeaconList() {
	let beaconList = [];
	$.ajax({
		type: "GET",
		url: "./beacon/list",
		data: {
			site_id: CUR_SITE_ID
		},
		async: true,
		cache: false,
		success: function (json, status) {
			json.forEach((j) => {
			
				let change_beacon_mac = '<span class="btn btn-secondary" style="height:100%; padding:1vh;" onclick="updateBeaconCheck(' + j.idx +',\'' + j.mac_addr + '\')">변경</span>';
				beaconList.push({
					idx: j.idx,
					beacon_no: j.idx + "번 비콘",
					mac_addr: j.mac_addr,
					beacon_mac: j.mac_addr,
					rssi_add: j.rssi_add,
					cont_name: j.cont_name,
					wt_name: j.wt_name,
					name: j.name,	
					change_beacon_mac: change_beacon_mac
				});
			});
			$("#result_beacons").bootstrapTable();
			if (json.length >= 0) {
				$("#result_beacons").bootstrapTable("load", beaconList);
			} else {
				$("#result_beacons").bootstrapTable("load", []);
			}
		}
	});
}

function updateBeaconCheck(beacon_idx, mac_addr) {
	let beacon_mac = prompt('[수정] 현재 맥어드레스: ' + mac_addr); 
	beacon_mac = beacon_mac.toUpperCase();
	input = checkMacAddr(beacon_mac);
	
	if(input == false) {
		alert("맥 어드레스 유효성 검사 실패! [입력값: " + beacon_mac + "]");
		return;
	}
	else {
		$.ajax({
			type : "GET",
	        url : "./beacon/check/duplicate",            
	        data : {
	        	mac_addr : beacon_mac
	        },
	        async: true,      
	        success : function(list, status) {
	        	if(list != null) {
	        		alert("이미 존재하는 비콘번호이거나 / 등록되어있는 비콘 입니다.");
	        	}
	        	else {
	        		updateBeacon(beacon_mac, beacon_idx);
	        	}
			}
		}); 			
	}	
}

function deleteBeaconData() {
	let select_array = $('#result_beacons').bootstrapTable('getAllSelections');
	if (select_array.length == 0) {
		alert('데이터를 1개 이상 선택해주세요');
		return;
	}

	for (let i = 0; i < select_array.length; i++) {
		deleteBeaconCheck(select_array[i].idx, select_array[i].mac_addr);
	}
}

function updateBeacon(beacon_mac, beacon_idx) {		
	$.ajax({
		type : "POST",
        url : "./beacon/update/macAddr",            
        data : {
        	site_id : CUR_SITE_ID,
        	idx : beacon_idx,
        	mac_addr : beacon_mac
        },
        async: true,      
        success : function(json, status) {	 
        	alert("수정 완료");
        	getBeaconList();
		}
	}); 
}

function insertBeaconCheck() {
	let beacon_mac = prompt('[추가] 맥어드레스 입력하세요 ex) FA:00:00:00:00:01'); 
	beacon_mac = beacon_mac.toUpperCase();
	input = checkMacAddr(beacon_mac);
	
	if(input == false) {
		alert("맥 어드레스 유효성 검사 실패! [입력값: " + beacon_mac + "]");
		return;
	}
	else {
		let beacon_idx =  prompt('비콘 번호를 입력해주세요 ex) 900'); 
		input = checkNumber(beacon_idx);
		
		if(input == false) {
			alert("숫자를 입력해주세요"); 
			return;
		} 
		else {
			$.ajax({
				type : "GET",
		        url : "./beacon/check/duplicate",            
		        data : {
		        	mac_addr : beacon_mac,
		        	idx : beacon_idx
		        },
		        async: true,      
		        success : function(list, status) {
		        	if(list != null) {
		        		alert("이미 존재하는 비콘번호이거나 / 등록되어있는 비콘 입니다.");
		        	}
		        	else {
		        		insertBeacon(beacon_mac, beacon_idx);
		        	}
				}
			}); 
		}			
	}
}	

function insertBeacon(beacon_mac, beacon_idx) {
	$.ajax({
		type : "POST",
        url : "./beacon/item/insert",            
        data : {
        	site_id : CUR_SITE_ID,
        	idx : beacon_idx,
        	mac_addr : beacon_mac
        },
        async: true,      
        success : function(json, status) {	 
        	alert("입력 완료");
        	getBeaconList();
		}
	}); 
}

function deleteBeaconCheck(idx, beacon_mac) {
	let input = false;    
	input = confirm(idx + '번 비콘을 삭제 하시겠습니까?');
  	if(input){
  		$.ajax({
  			type : "POST",
  			url : "./beacon/item/delete",            
  			data : {
  				idx : idx,
  				mac_addr : beacon_mac
  			},
  			async: true,
  			cache : false,         
  			success : function(json, status) { 
  				alert(idx + "번 비콘 삭제 완료");
	        	getBeaconList();
  			}
  		});
  	}
}

//----------------------------------
// NFC
function getNFCList() {
	let nfcList = [];
	$.ajax({
		type: "GET",
		url: "./nfc/list",
		data: {
			site_id: CUR_SITE_ID
		},
		async: true,
		cache: false,
		success: function (json, status) {
			json.forEach((j) => {
				
				let change_nfc_idx = '<span class="btn btn-secondary" style="height:100%; padding:1vh;" onclick="updateNfcCheck(' + j.idx +',\'' + j.serial_number + '\')">변경</span>';
				
				nfcList.push({
					idx: j.idx,
					nfc_no: j.idx + "번 NFC",
					serial_number: j.serial_number,			
					cont_name: j.cont_name,
					wt_name: j.wt_name,
					name: j.name,	
					change_nfc_idx: change_nfc_idx
				});
			});
			$("#result_nfc").bootstrapTable();
			if (json.length >= 0) {
				$("#result_nfc").bootstrapTable("load", nfcList);
			} else {
				$("#result_nfc").bootstrapTable("load", []);
			}
		}
	});
}

function insertNFCCheck() {
	var nfc_number = prompt('[추가] 시리얼넘버를 입력하세요. NFC Tools 설치 후 확인'); 	
	
	var serial_number = nfc_number.toUpperCase();
	
	let nfc_idx =  prompt('NFC 번호를 입력해주세요 ex) 900'); 
	input = checkNumber(nfc_idx);
	
	if(input == false) {
		alert("숫자를 입력해주세요"); 
		return;
	} 
	else {
		$.ajax({
			type : "GET",
	        url : "./nfc/check/duplicate/idx",            
	        data : {
	        	serial_number : serial_number,
	        	idx : nfc_idx
	        },
	        async: true,      
	        success : function(list, status) {
	        	if(list != null) {
	        		alert("이미 존재하는 NFC 번호이거나 / 등록되어있는 NFC 입니다.");
	        	}
	        	else {
	        		insertNFC(serial_number, nfc_idx);
	        	}
			}
		}); 
	}	
}	

function insertNFC(serial_number, nfc_idx) {
	$.ajax({
		type : "POST",
        url : "./nfc/item/insert",            
        data : {
        	site_id : CUR_SITE_ID,
        	idx : nfc_idx,
        	serial_number : serial_number
        },
        async: true,      
        success : function(json, status) {	 
        	alert("입력 완료");
        	getNFCList();
		}
	}); 
}

function deleteNFCData() {
	let select_array = $('#result_nfc').bootstrapTable('getAllSelections');
	if (select_array.length == 0) {
		alert('데이터를 1개 이상 선택해주세요');
		return;
	}

	for (let i = 0; i < select_array.length; i++) {
		deleteNFCCheck(select_array[i].idx, select_array[i].serial_number);
	}
}

function deleteNFCCheck(idx, serial_number) {
	let input = false;    
	input = confirm(idx + '번 NFC를 삭제 하시겠습니까?');
  	if(input){
  		$.ajax({
  			type : "POST",
  			url : "./nfc/item/delete",            
  			data : {
  				idx : idx,
  				serial_number : serial_number
  			},
  			async: true,
  			cache : false,         
  			success : function(json, status) { 
  				alert(idx + "번 NFC 삭제 완료");
  				getNFCList();
  			}
  		});
  	}
}

function updateNfcCheck(idx, serial_number) {
	let nfc_idx = prompt('[수정] 현재 NFC 번호: ' + idx); 
	
	input = checkNumber(nfc_idx);
	
	if(input == false) {
		alert("숫자를 입력해주세요"); 
		return;
	} 
	else {
		$.ajax({
			type : "GET",
	        url : "./nfc/check/update",            
	        data : {
	        	idx : nfc_idx
	        },
	        async: true,      
	        success : function(data, status) {
        		alert(serial_number + "를 " + nfc_idx + "번호로 변경 시도");
	        	if(data == false) {
	        		alert("변경할 수 없는 번호입니다. (비콘번호가 있어야하며 / 비어있는 NFC 숫자여야합니다)");
	        	}
	        	else {
	        		updateNfc(serial_number, nfc_idx);
	        	}
			}
		}); 			
	}	
}

function updateNfc(serial_number, idx) {		
	$.ajax({
		type : "POST",
        url : "./nfc/item/update/idx",            
        data : {
        	site_id : CUR_SITE_ID,
        	idx : idx,
        	serial_number : serial_number
        },
        async: true,      
        success : function(json, status) {	 
        	alert(idx + "번호로 수정 완료");
        	getNFCList();
		}
	}); 
}


//----------------------------------
// HOLE
function getHoleList() {

	$.ajax({
		type: "GET",
		url: "./hole/manage/list",
		data: {
			site_id: CUR_SITE_ID
		},
		async: true,        
		success: function (list, status) {
			$('#result_hole').bootstrapTable();
			if (list.length > 0) {
				for (let i = 0; i < list.length; i++) {
					
					list[i].beacon_no = list[i].hole_idx + "번 개구부비콘";
					list[i].section_no = list[i].section + "번 수조"; 
					list[i].change_mac_addr = '<span class="btn btn-secondary" style="height:100%; padding:1vh;" onclick="updateHoleCheck(' + list[i].hole_idx +',\'' + list[i].mac_addr + '\')">변경</span>';
					
					if(list[i].section == 0) {
						list[i].section_no= "-";
						list[i].section_name= "-";
					}
				}			
				
				$('#result_hole').bootstrapTable('load', list);
			} 
			else {
				$('#result_hole').bootstrapTable('load', []);
			}
		}
	});

}

function updateHoleCheck(hole_idx, mac_addr) {
	var hole_mac = prompt('[수정] 현재 맥어드레스: ' + mac_addr); 
	hole_mac = hole_mac.toUpperCase();
	input = checkMacAddr(hole_mac);
	
	if(input == false) {
		alert("맥 어드레스 유효성 검사 실패! [입력값: " + hole_mac + "]");
		return;
	}
	else {
		$.ajax({
			type : "GET",
	        url : "./hole/check/duplicate",            
	        data : {
	        	mac_addr : hole_mac
	        },
	        async: true,      
	        success : function(list, status) {
	        	if(list != null) {
	        		alert("이미 존재하는 비콘번호이거나 / 등록되어있는 비콘 입니다.");
	        	}
	        	else {
	        		updateHole(hole_mac, hole_idx);
	        	}
			}
		}); 			
	}	
}

function deleteHoleData() {
	let select_array = $('#result_hole').bootstrapTable('getAllSelections');
	if (select_array.length == 0) {
		alert('데이터를 1개 이상 선택해주세요');
		return;
	}

	for (let i = 0; i < select_array.length; i++) {
		deleteHoleCheck(select_array[i].idx, select_array[i].mac_addr);
	}
}

function updateHole(hole_mac, hole_idx) {		
	$.ajax({
		type : "POST",
        url : "./hole/update/macAddr",            
        data : {
        	site_id : CUR_SITE_ID,
        	idx : hole_idx,
        	mac_addr : hole_mac
        },
        async: true,      
        success : function(json, status) {	 
        	alert("수정 완료");
        	getHoleList();
		}
	}); 
}

function insertHoleCheck() {
	let hole_mac = prompt('[추가] 맥어드레스 입력하세요 ex) FA:00:00:00:00:01'); 
	hole_mac = hole_mac.toUpperCase();
	input = checkMacAddr(hole_mac);
	
	if(input == false) {
		alert("맥 어드레스 유효성 검사 실패! [입력값: " + hole_mac + "]");
		return;
	}
	else {
		let hole_idx =  prompt('개구부비콘 번호를 입력해주세요 ex) 900'); 
		input = checkNumber(hole_idx);
		
		if(input == false) {
			alert("숫자를 입력해주세요"); 
			return;
		} 
		else {
			$.ajax({
				type : "GET",
		        url : "./hole/check/duplicate",            
		        data : {
		        	mac_addr : hole_mac,
		        	idx : hole_idx
		        },
		        async: true,      
		        success : function(list, status) {
		        	if(list != null) {
		        		alert("이미 존재하는 개구부비콘 번호이거나 / 등록되어있는 개구부비콘 입니다.");
		        	}
		        	else {
		        		insertHole(hole_mac, hole_idx);
		        	}
				}
			}); 
		}			
	}
}	

function insertHole(hole_mac, hole_idx) {
	$.ajax({
		type : "POST",
        url : "./hole/item/insert",            
        data : {
        	site_id : CUR_SITE_ID,
        	idx : hole_idx,
        	mac_addr : hole_mac
        },
        async: true,      
        success : function(json, status) {	 
        	alert("입력 완료");
        	getHoleList();
		}
	}); 
}

function deleteHoleCheck(idx, hole_mac) {
	let input = false;    
	input = confirm(idx + '번 개구부비콘을 삭제 하시겠습니까?');
  	if(input){
  		$.ajax({
  			type : "POST",
  			url : "./hole/item/delete",            
  			data : {
  				idx : idx,
  				mac_addr : hole_mac
  			},
  			async: true,
  			cache : false,         
  			success : function(json, status) { 
  				alert(idx + "번 개구부비콘 삭제 완료");
	        	getHoleList();
  			}
  		});
  	}
}

//----------------------------------
// Section
function getSectionList() {
	$.ajax({
		type : "GET",
		url : "./section/list",            
        data : {
        	site_id : CUR_SITE_ID
        },
        async: true,      
        success : function(list, status) {     
			$('#sectionList').bootstrapTable();    
           
			if(list.length > 0) {	        	   
				for(var i = 0 ; i < list.length ; i++) {
					
					if(list[i].scanner_idx > 0) {
						list[i].section_state = '<span id="state_' +list[i].section+ '" class="toggle-placeholder" onclick="setStateService('+list[i].section+')"> <input type="checkbox" checked data-toggle="toggle" data-onstyle="outline-success" data-offstyle="outline-danger"> </span>';
						list[i].sensor_delete = '<span id="sensor_' +list[i].section+ '" class="toggle-placeholder" onclick="setSensorService('+list[i].section+')"> <input type="checkbox" checked data-toggle="toggle" data-onstyle="outline-info" data-offstyle="outline-danger"> </span>';	
						list[i].push_service = '<span id="push_' +list[i].section+ '" class="toggle-placeholder" onclick="setSensorPushService('+list[i].section+')"> <input type="checkbox" checked data-toggle="toggle" data-onstyle="outline-info" data-offstyle="outline-danger"> </span>';						
					}
					else {
						list[i].section_state = '<span style="color: #ec616d; font-weight: bold; font-size: 0.75rem;">-</span>';
						list[i].sensor_delete = '<span style="color: #ec616d; font-weight: bold; font-size: 0.75rem;">-</span>';
						list[i].push_service = '<span style="color: #ec616d; font-weight: bold; font-size: 0.75rem;">-</span>';
					}
					
				}
				$('#sectionList').bootstrapTable('load', list);	
				for(var i = 0 ; i < list.length ; i++) {	
					
					if(list[i].state == 99) {
						$("#state_"+list[i].section).find("input[type=checkbox][data-toggle=toggle]").each(function () { $(this).bootstrapToggle('off'); });						
					} else {
						$("#state_"+list[i].section).find("input[type=checkbox][data-toggle=toggle]").each(function () { $(this).bootstrapToggle('on'); });						
					}
					
					if(list[i].has_env == 0 && list[i].scanner_idx > 0) {
						$("#sensor_"+list[i].section).find("input[type=checkbox][data-toggle=toggle]").each(function () { $(this).bootstrapToggle('off'); });						
					} else {
						$("#sensor_"+list[i].section).find("input[type=checkbox][data-toggle=toggle]").each(function () { $(this).bootstrapToggle('on'); });						
					}
					
					if(list[i].push_sensor == 0 && list[i].scanner_idx > 0) {
						$("#push_"+list[i].section).find("input[type=checkbox][data-toggle=toggle]").each(function () { $(this).bootstrapToggle('off'); });						
					} else {
						$("#push_"+list[i].section).find("input[type=checkbox][data-toggle=toggle]").each(function () { $(this).bootstrapToggle('on'); });						
					}
					
				}
           	}
			else {
				$('#sectionList').bootstrapTable('load', []);
			}   
		}
	});     
}	

function setStateService(section) {
	// ON > OFF
	if($("#state_"+section).children().hasClass('off')) {
		updateStateService(section, 1);
	}	
	// OFF > ON
	else {
		updateStateService(section, 0);
	}
}

function updateStateService(section, active){
	$.ajax({
		type : "POST",
	    url : "./seg/update/sectionState",            
	    data : {
	     	site_id : CUR_SITE_ID,
			section : section,
			isService : active
	    },
	    async: true,
	    cache : false,         
	    success : function(json, status){
			alert("변경 완료");  
	    }
	});
}

function setSensorService(section) {
	// ON > OFF
	if($("#sensor_"+section).children().hasClass('off')) {
		updateSensorService(section, 1);
	}	
	// OFF > ON
	else {
		updateSensorService(section, 0);
	}
}

function updateSensorService(section, active){
	$.ajax({
		type : "POST",
	    url : "./seg/update/sectionSensor",            
	    data : {
	     	site_id : CUR_SITE_ID,
			section : section,
			isService : active
	    },
	    async: true,
	    cache : false,         
	    success : function(json, status){
			alert("변경 완료");  
	    }
	});
}

function setSensorPushService(section) {
	// ON > OFF
	if($("#push_"+section).children().hasClass('off')) {
		updateSectionSensorPush(section, 1);
	}	
	// OFF > ON
	else {
		updateSectionSensorPush(section, 0);
	}
}

function updateSectionSensorPush(section, active){
	$.ajax({
		type : "POST",
	    url : "./seg/update/sectionSensorPush",            
	    data : {
	     	site_id : CUR_SITE_ID,
			section : section,
			isService : active
	    },
	    async: true,
	    cache : false,         
	    success : function(json, status){
			alert("변경 완료");  
	    }
	});
}

//----------------------------------
// Scanner
function getScannerList() {
	$.ajax({
		type : "GET",
		url : "./scanner/manage/list",            
        data : {
        	site_id : CUR_SITE_ID
        },
        async: true,      
        success : function(list, status) {     
			$('#result_scanner').bootstrapTable();    
           
			if(list.length > 0) {	        	   
				for(var i = 0 ; i < list.length ; i++) {
					list[i].section_no = list[i].section + "번 수조";
					
					if(list[i].time_out == 0) {
						list[i].timeOut = '<span style="font-weight: bold;">-</span>';
					}
					else {
						list[i].timeOut = '<span style="font-weight: bold;">'+list[i].time_out+'분</span>';
					}
					
					if(list[i].wait_out == 0) {
						list[i].waitOut = '<span style="font-weight: bold;">-</span>';
					}
					else {
						list[i].waitOut = '<span style="font-weight: bold;">'+list[i].wait_out+'분</span>';
					}
					
					if(list[i].rssi_cut == 0) {
						list[i].rssi_cut = '<span style="font-weight: bold;">-</span>';
					}					
					else {
						list[i].rssi_cut = '<span style="font-weight: bold;">rssi_cut: </span>' + list[i].rssi_cut;	
					}
					
					list[i].modify =
						'<div class="btn btn-secondary" title="자동삭제시간변경" onclick="changeScannerTimeOut('+list[i].section+','+list[i].time_out+')"><i class="fa-solid fa-compact-disc"></div>' +
						'<div class="btn btn-default" title="자동대기삭제시간변경" onclick="changeScannerWaitOut('+list[i].section+','+list[i].wait_out+')"></i><i class="fa-brands fa-nfc-symbol"></i>' +		
						'</div>';
					
				}
				$('#result_scanner').bootstrapTable('load', list);	
           	}
			else {
				$('#result_scanner').bootstrapTable('load', []);
			}   
		}
	}); 	
}

function changeScannerTimeOut(section, cur_time_out) {
	let time_out = "";
	if(cur_time_out == 0) {
		time_out = "설정 없음"
	}
	else {
		time_out = cur_time_out;
	}
	let input_time_out = prompt('[변경] ' + section + "번 수조 자동삭제 시간 변경 / 현재 값: " + time_out + "분 / [설정없음 : 0입력]"); 
	
	input = checkNumber(input_time_out);
	
	if(input == false) {
		alert("숫자를 입력해주세요"); 
		return;
	} 
	else {
		$.ajax({
			type : "GET",
	        url : "./scanner/update/timeOut",            
	        data : {
	        	section: section,
	        	time_out: input_time_out	        	
	        },
	        async: true,      
	        success : function(list, status) {
	        	alert("자동삭제 시간 변경 완료");
	        	getScannerList();
			}
		}); 			
	}	
}

function changeScannerWaitOut(section, cur_wait_out) {
	let wait_out = "";
	if(cur_wait_out == 0) {
		wait_out = "설정 없음"
	}
	else {
		wait_out = cur_wait_out;
	}
	let input_wait_out = prompt('[변경] ' + section + "번 수조 자동대기삭제 시간 변경 / 현재 값: " + wait_out + "분 / [설정없음 : 0입력]"); 
	
	input = checkNumber(input_wait_out);
	
	if(input == false) {
		alert("숫자를 입력해주세요"); 
		return;
	} 
	else {
		$.ajax({
			type : "GET",
	        url : "./scanner/update/waitOut",            
	        data : {
	        	section: section,
	        	wait_out: input_wait_out	        	
	        },
	        async: true,      
	        success : function(list, status) {
	        	alert("자동대기삭제 시간 변경 완료");
	        	getScannerList();
			}
		}); 			
	}	
}

function insertScannerCheck() {
	let scanner_section = prompt('[추가] App 로그인에 필요한 수조 번호를 적어주세요'); 
	
	input = checkNumber(scanner_section);
	
	if(input == false) {
		alert("숫자를 입력해주세요"); 
		return;
	} 
	else {
		$.ajax({
			type : "GET",
	        url : "./section/check/duplicate",            
	        data : {
	        	section: scanner_section
	        },
	        async: true,      
	        success : function(list, status) {
	        	if(list == null) {
	        		alert("이미 존재하는 수조번호이거나 / 수조정보가 없는 번호입니다.");
	        	}
	        	else {	        		
	        		insertScanner(scanner_section);
	        	}
			}
		}); 			
	}	
}

function insertScanner(section) {	
	
	let scanner_mac_init = "";
	let scanner_name = "";
	
	scanner_mac = CUR_SITE_ID + "_0_" + section; 
	scanner_mac_init = scanner_mac;
	
	$.ajax({
		type : "POST",
        url : "./scanner/insert",            
        data : {
        	site_id : CUR_SITE_ID,
        	scanner_mac_init : scanner_mac_init,
        	scanner_mac : scanner_mac,
        	name : scanner_name,
        	section : section
        },
        async: true,      
        success : function(json, status) {	 
        	alert("입력 완료 수조가 활성화 됩니다.");
        	getScannerList();
		}
	});	
}

function deleteScannerData() {
	let select_array = $('#result_scanner').bootstrapTable('getAllSelections');
	if (select_array.length == 0) {
		alert('데이터를 1개 이상 선택해주세요');
		return;
	}

	for (let i = 0; i < select_array.length; i++) {
		deleteScannerCheck(select_array[i].idx, select_array[i].scanner_mac, select_array[i].section);
	}
}


function deleteScannerCheck(idx, scanner_mac, section) {
	var input = false;    
	input = confirm("[주의] 해당 스캐너를 삭제하시겠습니까? 연결된 수조가 비활성화 됩니다.");
  	if(input){
  		$.ajax({
  			type : "POST",
  			url : "./scanner/delete",            
  			data : {
  				site_id : CUR_SITE_ID,
  				idx : idx,
  				scanner_mac : scanner_mac,
  				section : section
  			},
  			async: true,
  			cache : false,         
  			success : function(json, status) { 
  				alert(section + "번 수조 스캐너 삭제 완료 수조가 비활성화 됩니다.");
  				getScannerList();
  			}
  		});
  	}
}


//----------------------------------
// Common

function checkMacAddr(mac_addr) {
	var mac_addr = mac_addr.toUpperCase();
	const regex = /^([0-9A-F]{2}[:]){5}([0-9A-F]{2})$/;
	return regex.test(mac_addr);
}

function checkNumber(val){
	let regex= /^[0-9]/g;
	if(regex.test(val) == true) return true;
	else return false;
}
