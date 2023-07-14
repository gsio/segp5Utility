function update() {
	//getNFCLogList();
	getPatchList();
}

// ----------------------------------
// Beacon
function getBeaconScanLogList(beacon_mac) {
	$.ajax({
		type: "GET",
		url: 'http://13.209.31.139:11243/getBeaconScanLogList',
		//url: 'http://211.212.221.98:11243/getBeaconScanLogList',
		data: {
			beacon_mac: beacon_mac
		},
		traditional: true,
		async: true,
		cache: false,
		success: function (data, status) {						
			$('#result_beacon').bootstrapTable();			
			var list = JSON.parse(JSON.stringify(data)).beaconList;
			if(list != null) {
				//console.log("[DATA COUNT]: " + list.length);
				if(list.length >= 0) {
					for(var i=0; i < list.length; i++) {
						list[i].no = i+1;				
						if(list[i].app_type == 0) {
							list[i].type = "상부"
						}
						else {
							list[i].type = "하부"
						}
					}				
					$('#result_beacon').bootstrapTable('load', list );	
				}
			}
			else{
				$('#result_beacon').bootstrapTable('load', [] );
			}			
		}
	});		
}

function getLogByBeaconMacAddr() {
	
	let beacon_mac = prompt('[조희] 비콘 맥어드레스 입력하세요 ex) FA:00:00:00:00:01'); 
	beacon_mac = beacon_mac.toUpperCase();
	input = checkMacAddr(beacon_mac);
	
	if(input == false) {
		alert("맥 어드레스 유효성 검사 실패! [입력값: " + beacon_mac + "]");
		return;
	}
	else {
		getBeaconScanLogList(beacon_mac);
	}	
}

function getLogByBeaconIdx(){
	let beacon_idx =  prompt('비콘 번호를 입력해주세요 ex) 900'); 
	input = checkNumber(beacon_idx);
	
	if(input == false) {
		alert("숫자를 입력해주세요"); 
		return;
	} 
	else {
		$.ajax({
			type : "GET",
	        url : "./beacon/macAddr/idx",            
	        data : {
	        	site_id: CUR_SITE_ID,
	        	idx : beacon_idx
	        },
	        async: true,      
	        success : function(data, status) {
	        	if(data != null) {
	        		getBeaconScanLogList(data.mac_addr);
	        	}
	        	else {
	        		alert("입력하신 비콘 번호가 존재하지 않습니다.");
	        		$('#result_beacon').bootstrapTable('load', [] );	        		
	        	}
			}
		}); 
	}	
}


//----------------------------------
// NFC
function getNFCLogList() {
	$.ajax({
		type: "GET",
		url: 'http://13.209.31.139:11243/getNfcLogList',
		//url: 'http://211.212.221.98:11243/getNfcLogList',
		data: {
			site_id: CUR_SITE_ID
		},
		traditional: true,
		async: true,
		cache: false,
		success: function (data, status) {						
			$('#result_nfc').bootstrapTable();			
			var list = JSON.parse(JSON.stringify(data)).nfcList;
			if(list != null) {
				//console.log("[DATA COUNT]: " + list.length);
				if(list.length >= 0) {
					for(var i=0; i < list.length; i++) {
						list[i].no = i+1;				
						if(list[i].inout_type == 1) {
							list[i].type =  '<div style="color: #3866a8; font-weight: bold; font-size: 0.75rem;">입장</div>'; 
						}
						else {
							list[i].type =  '<div style="color: #d52802; font-weight: bold; font-size: 0.75rem;">퇴장</div>';
						}
						list[i].nfc_no = list[i].nfc_idx + "번 NFC";	
					}				
					$('#result_nfc').bootstrapTable('load', list );	
				}
			}
			else {
				$('#result_nfc').bootstrapTable('load', [] );
			}			
		}
	});		
}


//----------------------------------
// HOLE
function getLogByHoleMacAddr() {
	
	let beacon_mac = prompt('[조희] 개구부 비콘 맥어드레스 입력하세요 ex) FA:00:00:00:00:01'); 
	beacon_mac = beacon_mac.toUpperCase();
	input = checkMacAddr(beacon_mac);
	
	if(input == false) {
		alert("맥 어드레스 유효성 검사 실패! [입력값: " + beacon_mac + "]");
		return;
	}
	else {
		getHoleScanLogList(beacon_mac);
	}	
}

function getLogByHoleIdx(){
	let beacon_idx =  prompt('비콘 번호를 입력해주세요 ex) 900'); 
	input = checkNumber(beacon_idx);
	
	if(input == false) {
		alert("숫자를 입력해주세요"); 
		return;
	} 
	else {
		$.ajax({
			type : "GET",
	        url : "./hole/macAddr/idx",            
	        data : {
	        	site_id: CUR_SITE_ID,
	        	idx : beacon_idx
	        },
	        async: true,      
	        success : function(data, status) {
	        	if(data != null) {
	        		getHoleScanLogList(data.mac_addr);
	        	}
	        	else {
	        		alert("입력하신 비콘 번호가 존재하지 않습니다.");
	        		$('#result_beacon').bootstrapTable('load', [] );	        		
	        	}
			}
		}); 
	}	
}

function getHoleScanLogList(beacon_mac) {
	$.ajax({
		type: "GET",
		url: 'http://13.209.31.139:11243/getHoleScanLogList',
		//url: 'http://211.212.221.98:11243/getHoleScanLogList',
		data: {
			beacon_mac: beacon_mac
		},
		traditional: true,
		async: true,
		cache: false,
		success: function (data, status) {						
			$('#result_hole').bootstrapTable();			
			var list = JSON.parse(JSON.stringify(data)).holeList;
			if(list != null) {
				//console.log("[DATA COUNT]: " + list.length);
				if(list.length >= 0) {
					for(var i=0; i < list.length; i++) {
						list[i].no = i+1;			
						list[i].x_axis = list[i].axis1;
						list[i].y_axis = list[i].axis2;
						if(list[i].app_type == 0) {
							list[i].type = "상부"
						}
						else {
							list[i].type = "하부"
						}
						if(list[i].state == 0) {
							list[i].state = '<div style="color: #d52802; font-weight: bold; font-size: 0.75rem;">열림</div>'; 
						}
						else {
							list[i].state = '<div style="color: #3866a8; font-weight: bold; font-size: 0.75rem;">닫침</div>';
						}
					}				
					$('#result_hole').bootstrapTable('load', list );	
				}
			}
			else{
				$('#result_hole').bootstrapTable('load', [] );
			}			
		}
	});		
}

//----------------------------------
// Device(Scanner)
function getLogScannerSection() {
	let section_no =  prompt('수조 번호를 입력해주세요 ex) 3'); 
	input = checkNumber(section_no);
	
	if(input == false) {
		alert("숫자를 입력해주세요"); 
		return;
	} 
	else {
		$.ajax({
			type: "GET",
			url: 'http://13.209.31.139:11243/getLogScannerSection',
			//url: 'http://211.212.221.98:11243/getLogScannerSection',
			data: {
				section: section_no
			},
			traditional: true,
			async: true,
			cache: false,
			success: function (data, status) {						
				$('#result_scanner').bootstrapTable();			
				var list = JSON.parse(JSON.stringify(data)).scannerList;
				if(list != null) {
					if(list.length >= 0) {
						for(var i=0; i < list.length; i++) {
							list[i].no = i+1;			
							list[i].section_no = list[i].section + "번 수조";			
							if(list[i].app_type == 0) {
								list[i].type = "상부"
							}
							else {
								list[i].type = "하부"
							}
						}				
						$('#result_scanner').bootstrapTable('load', list );	
					}
				}
				else{
					$('#result_scanner').bootstrapTable('load', [] );
				}			
			}
		});		
	}	
}

//----------------------------------
// Patch
function getPatchList() {
	$.ajax({
		type : "GET",
		url : "./device/patch/list",            
      data : {
      	site_id : CUR_SITE_ID
      },
      async: true,      
      success : function(list, status) {     
			$('#result_patch').bootstrapTable();    			
			if(list != null) {
				if(list.length > 0) {	        	   
					for(var i = 0 ; i < list.length ; i++) {
						list[i].no = i+1;
						
						list[i].patch_type = 
							'<div style="color: #' + list[i].app_color + '; font-weight: bold; font-size: 0.75rem;">' + list[i].app_type_name + '</div>';
						
						list[i].app_url = '<div class="btn-wrap">';						

						if(list[i].url != null) {						
							list[i].app_url += '<div class="font_btn"><a target="_blank" href="' + list[i].url + '">';
							list[i].app_url += '<a target="_blank" href="' + list[i].url + '"><i class="fa-brands fa-app-store"></i></a></div>';
						}
						
						if(list[i].ext == 'apk') {
							list[i].app_url += '<div class="font_btn"><a class="file_link" href="./file/app?virtname='+ list[i].apk_name;
							list[i].app_url +=  '.' + list[i].ext + '&orgname='+ list[i].apk_name + '.' + list[i].ext + '&content_type=null">';
							list[i].app_url += '<i class="fa-solid fa-cloud-arrow-down"></i></a></div>';
						}								
					}
					$('#result_patch').bootstrapTable('load', list);	
	         	}
				else {
					$('#result_patch').bootstrapTable('load', []);
				} 
			}
		}
	}); 	
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
