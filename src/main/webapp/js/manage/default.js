function update() {
	getBeaconData();
	getNFCData();
	getSensorData();
	getHoleData();
	getBlockData();
	getDeviceStateData();
}

// ----------------------------------
// Beacon
function getBeaconData() {
	let beaconList = [];
	$.ajax({
		type: "GET",
		url: "./seg/locations",
		data: {
			site_id: CUR_SITE_ID
		},
		async: true,
		cache: false,
		success: function (json, status) {
			json.forEach((j) => {
				let time_diff_min = j.time_diff_min;
				let state;
				if (time_diff_min >= 5 && time_diff_min < 60) {
					state = "badge-warning";
				}
				else if (time_diff_min >= 60) {
					state = "badge-danger";
				} else {
					state = "badge-success"
				}
				time_diff_min = '<div class="badge ' + state + '" style="height:100%;padding:1vh;">' + time_diff_min + "분</div>";
				change_start_time = '<span class="btn btn-secondary" style="padding:0.8vh" onclick="modifyStartTime(' + j.mb_idx + ', \'' + j.beacon_mac + '\')">변경</span>';
				column1 = '<span>' + j.cont_name + '</span>' + ' | ' + '<span>' + j.name + '</span></br>' + '<span>' + j.last_scanner_checktime + '</span><br />' + '<span>' + j.start_time + ' (' + j.work_min + ')' + '</span>',

					beaconList.push({
						column1: column1, // for mobile
						cont_name: j.cont_name,
						name: j.name,
						mb_idx: j.mb_idx + "번 비콘",
						rssi: j.rssi,
						time_diff_min: time_diff_min,
						section: j.section,
						section_no: j.section + "번 수조",
						last_update_time: j.last_update_time.replace(".0", ""),
						start_time: j.start_time,
						work_min: j.work_min + "분",
						change_start_time: change_start_time,
						beacon_mac: j.beacon_mac,
						uw_id: j.uw_id,
						beacon_role: j.beacon_role
					});
			});
			$("#result_locations").bootstrapTable();
			if (json.length >= 0) {
				$("#result_locations").bootstrapTable("load", beaconList);
			} else {
				$("#result_locations").bootstrapTable("load", []);
			}
		}
	});
}

function btnExitWorkers() {
	let select_array = $('#result_locations').bootstrapTable('getAllSelections');
	if (select_array.length == 0) {
		alert('데이터를 1개 이상 선택해주세요');
		return;
	}

	for (let i = 0; i < select_array.length; i++) {
		exitWorkers(select_array[i].mb_idx, select_array[i].beacon_mac, select_array[i].uw_id, select_array[i].beacon_role);
	}
}

function exitWorkers(mb_idx, beacon_mac, uw_id, role) {
	let input = false;
	input = confirm(mb_idx + ' exit 처리 하시겠습니까?');

	if (input) {
		$.ajax({
			type: "POST",
			url: "./seg/exit/beacon",
			data: {
				uw_id: uw_id,
				role: role,
				beacon_mac: beacon_mac,
			},
			async: true,
			cache: false,
			success: function (json, status) {
				getBeaconData();
			}
		});
	}
}

function modifyStartTime(mb_idx, beacon_mac) {
	let start_time;
	start_time = prompt('변경할 출입시간을 입력하세요 EX) 1432');
	let input = confirmStartTime(start_time);
	if (input) {
		$.ajax({
			type: "POST",
			url: "./location/update/workTime",
			data: {
				site_id: CUR_SITE_ID,
				beacon_mac: beacon_mac,
				mb_idx: mb_idx,
				start_time: start_time
			},
			async: true,
			cache: false,
			success: function (json, status) {
				alert(mb_idx + '번 비콘 출입시간 변경 : ' + start_time);
				getBeaconData();
			}
		});
	}
}

// ----------------------------------
// NFC
function getNFCData() {
	let nfcList = [];
	$.ajax({
		type: "GET",
		url: "./nfc/wait/list",
		data: {
			site_id: CUR_SITE_ID
		},
		async: true,
		cache: false,
		success: function (json, status) {
			json.forEach((j) => {
				let work_min = j.work_min;
				
				if (work_min >= 10) {
					state = "badge-warning";
				}
				else if (work_min >= 5) {
					state = "badge-danger";
				}
				else {
					state = "badge-success"
				}
				
				if(j.location_uw_id == null) {
					work_min = '<div class="badge ' + state + '" style="height:100%;padding:1vh;">' + work_min + " 분</div>";	
				}
				else {
					work_min = '<div style="color: #3866a8; font-weight: bold; font-size: 0.75rem;">작업중</div>';
				}			
				
				column1 = '<span>' + j.cont_name + '</span>' + ' | ' + '<span>' + j.name + '</span></br>' + '<span>' + j.last_scanner_checktime + '</span><br />' + '<span>' + j.start_time + ' (' + j.work_min + ')' + '</span>',

					nfcList.push({
						column1: column1, // for mobile
						cont_name: j.cont_name,
						name: j.name,
						nfc_idx: j.nfc_idx + "번 NFC",
						section: j.section,
						section_no: j.section + "번 수조",
						write_time: j.write_time.replace(".0", ""),
						start_time: j.start_time,
						work_min: work_min,
						serial_number: j.serial_number,
						uw_id: j.uw_id,
						nfc_role: j.nfc_role
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


function btnWaitOutWorkers() {
	let select_array = $('#result_nfc').bootstrapTable('getAllSelections');
	console.log(select_array)
	if (select_array.length == 0) {
		alert('데이터를 1개 이상 선택해주세요');
		return;
	}

	for (let i = 0; i < select_array.length; i++) {
		waitOutWorkers(select_array[i].nfc_idx, select_array[i].serial_number, select_array[i].uw_id, select_array[i].nfc_role);
	}
}

function waitOutWorkers(nfc_idx, serial_number, uw_id, role) {
	let input = false;
	input = confirm(nfc_idx + ' exit 처리 하시겠습니까?');

	if (input) {
		$.ajax({
			type: "POST",
			url: "./nfc/wait/out",
			data: {
				uw_id: uw_id,
				role: role,
				serial_number: serial_number,
			},
			async: true,
			cache: false,
			success: function (json, status) {
				getNFCData();
			}
		});
	}
}

//----------------------------------
// Sensor
function getSensorData() {
	$.ajax({
		type: "GET",
		url: "./seg/sensors",
		data: {
			site_id: CUR_SITE_ID
		},
		async: true,        
		success: function (list, status) {
			$('#result_sensor').bootstrapTable();
			if (list.length > 0) {
				for (let i = 0; i < list.length; i++) {

					list[i].section_no = list[i].section + "번 수조"; 
					
					let time_diff_min = list[i].time_diff_min;
					let state = 'badge-success';

					if (list[i].write_time != null) {
						list[i].write_time = list[i].write_time.replace(".0", "");
					} else {
						list[i].write_time = '-';
					}

					if (list[i].write_time == null || typeof list[i].write_time == 'undefined') {
						state = 'badge-danger';
						time_diff_min = '-';
					}
					else if (time_diff_min >= 5 && time_diff_min < 60) {
						state = 'badge-warning';
					}
					else if (time_diff_min >= 60) {
						state = 'badge-danger';
					}

					list[i].time_diff_min = '<div class="badge ' + state + '" style="height:100%;padding:1vh;">' + time_diff_min + "분 </div>"
					
					if(list[i].o2_state == 1) {
						list[i].o2 = '<span style="color: #ec616d; font-weight: bold; font-size: 0.75rem;">' + list[i].o2 + '%</span>';
					}
					else {
						list[i].o2 = '<span>' + list[i].o2 + '%</span>';
					}
					
					if(list[i].co2_state == 1) {
						list[i].co2 = '<span style="color: #ec616d; font-weight: bold; font-size: 0.75rem;">' + list[i].co2 + 'ppm</span>';
					}
					else {
						list[i].co2 = '<span>' + list[i].co2 + 'ppm</span>';
					}
					
					if(list[i].co_state == 1) {
						list[i].co = '<span style="color: #ec616d; font-weight: bold; font-size: 0.75rem;">' + list[i].co + 'ppm</span>';
					}
					else {
						list[i].co = '<span>' + list[i].co + 'ppm</span>';
					}
					
					if(list[i].h2s_state == 1) {
						list[i].h2s = '<span style="color: #ec616d; font-weight: bold; font-size: 0.75rem;">' + list[i].h2s + 'ppm</span>';
					}
					else {
						list[i].h2s = '<span>' + list[i].h2s + 'ppm</span>';
					}
					
					if(list[i].lel_state == 1) {
						list[i].lel = '<span style="color: #ec616d; font-weight: bold; font-size: 0.75rem;">' + list[i].lel + '%</span>';
					}
					else {
						list[i].lel = '<span>' + list[i].lel + '%</span>';
					}
		
				}
				
				$('#result_sensor').bootstrapTable('load', list);
			} else {
				$('#result_sensor').bootstrapTable('load', []);
			}
		}
	});
}

function deleteSensorData() {
	let select_array = $('#result_sensor').bootstrapTable('getAllSelections');
	if (select_array.length == 0) {
		alert('데이터를 1개 이상 선택해주세요');
		return;
	}

	for (let i = 0; i < select_array.length; i++) {
		deleteSensors(select_array[i].site_id, select_array[i].place_id, select_array[i].section, select_array[i].section_name);
	}
}

function deleteSensors(site_id, place_id, section, section_name) {
	let input = false;
	input = confirm(section_name + ' (#' + section + ') 환경센서 데이터를 삭제 하시겠습니까?');
	
	if (input) {
		$.ajax({
			type: "POST",
			url: "./seg/delete/sensor",
			data: {
				site_id: site_id,
				place_id: place_id,
				section: section,
			},
			async: true,
			cache: false,
			success: function (json, status) {
				getSensorData();
			}
		});
	}
}


//----------------------------------
// Hole
function getHoleData() {
	$.ajax({
		type: "GET",
		url: "./seg/getHoleManageList",
		data: {
			site_id: CUR_SITE_ID
		},
		async: true,        
		success: function (list, status) {
			$('#result_hole').bootstrapTable();
			if (list.length > 0) {				
				for (let i = 0; i < list.length; i++) {
					list[i].section_no = list[i].section + "번 수조"; 
					if (list[i].write_time != null) {
						list[i].write_time = list[i].write_time.replace(".0", "");
					} else {
						list[i].write_time = '-';
					}
					list[i].idx = list[i].hole_idx + "번 개구부 비콘";
					
					if(list[i].state == 1) {
						list[i].state = '<div style="color: #28a745; font-weight: bold;">닫힘</div>';
					}
					else {
						list[i].state = '<div style="color: #ec616d; font-weight: bold;">열림</div>';
					}
					
					let time_diff_min = list[i].time_diff_min;
					let state = 'badge-success';					
					if (time_diff_min >= 5 && time_diff_min < 10) {
						state = 'badge-warning';
					}
					else if (time_diff_min >= 10) {
						state = 'badge-danger';
					}

					list[i].time_diff_min = '<div class="badge ' + state + '" style="height:100%; padding:1vh;">' + time_diff_min + "분 </div>"
				}				
				$('#result_hole').bootstrapTable('load', list);
			}
			else {
				$('#result_hole').bootstrapTable('load', []);
			}
		}
	});
}

//----------------------------------
// Block
function getBlockData() {
	let rssiList = [];
	$.ajax({
		type: "GET",
		url: "./seg/getBlockList",
		data: {
			site_id: CUR_SITE_ID,
		},
		async: true,
		cache: false,
		success: function (json, status) {
			json.forEach((j) => {
				
				let app_type = j.app_type;
				let type = "";
				
				if(app_type == 1) {
					type = "하부";
				}
				else {
					type = "상부";
				}				
				
				rssiList.push({
					section: j.section,
					section_no: j.section + "번 수조",
					type: type,
					phone_idx: '<span style="font-weight: bold;">phone_idx: </span>' + j.phone_idx,
					beacon_idx: j.beacon_idx,
					idx: j.beacon_idx + "번 비콘",
					beacon_rssi: '<span style="font-weight: bold;">'+j.rssi +'</span><span> ('+j.beacon_rssi_add +')</span>',
					section_rssi: '<span style="font-weight: bold;">rssi_cut: </span>' + j.rssi_cut,
					name: j.name,
					cont_name: j.cont_name,
					wt_name: j.wt_name,
					write_time: j.write_time.replace(".0", ""),
					modify_rssi_add: '<span class="btn btn-secondary" style="padding:0.8vh" onclick="correctRssi(' + '\'' + j.beacon_mac + '\',' + j.beacon_rssi_add + ')">수정</span>'
				});
			});
			if (json.length > 0) {
				$("#result_rssi").bootstrapTable("load", rssiList);
			} else {
				$("#result_rssi").bootstrapTable("load", []);
			}
		},
	});
}

function correctRssi(beacon_mac, beacon_rssi_add) {
	let rssi_add;
	rssi_add = prompt(beacon_mac + ' 현재 RSSI 보정 수치: ' + beacon_rssi_add);

	let input = confirmRssi(rssi_add);

	if (input) {
		$.ajax({
			type: "POST",
			url: "./seg/update/rssiAdd",
			data: {
				mac_addr: beacon_mac,
				rssi_add: rssi_add
			},
			async: true,
			cache: false,
			success: function (json, status) {
				alert('비콘 RSSI 보정 수치 변경 : ' + rssi_add);
				getBlockData();
			}
		});
	}
}

function confirmRssi(num) {
	num = String(num).replace(/^\s+|\s+$/g, "");
	let regex = /^[\-]?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?$/g;
	if (regex.test(num)) {
		num = num.replace(/,/g, "");
		return isNaN(num) ? false : true;
	} else {
		return false;
	}
}

//----------------------------------
// Device State
function getDeviceStateData() {
	$.ajax({
		type: "GET",
		url: "./seg/getDeviceStateData",
		data: {
			site_id: CUR_SITE_ID
		},
		async: true,        
		success: function (list, status) {
			$('#result_scanner').bootstrapTable();
			if (list.length > 0) {				
				for (let i = 0; i < list.length; i++) {
					list[i].section_no = list[i].section + "번 수조"; 
					if (list[i].write_time != null) {
						list[i].write_time = list[i].write_time.replace(".0", "");
					} else {
						list[i].write_time = '-';
					}										
					
					if(list[i].app_type == 1) {
						list[i].type = "하부";
					}
					else {
						list[i].type = "상부";
					}
					
					
					if(list[i].scan_count == 0) {
						list[i].scan_count = '<span style="font-weight: bold; color: #ec616d;"> 0개 </span>';
					}
					else {
						list[i].scan_count = '<span style="font-weight: bold;">'+list[i].scan_count+'</span>개';
					}
					
					let time_diff_min = list[i].time_diff_min_data;
					let state = 'badge-success';					
					if (time_diff_min >= 5 && time_diff_min < 10) {
						state = 'badge-warning';
					}
					else if (time_diff_min >= 10) {
						state = 'badge-danger';
					}
					
					list[i].device_no = '<span style="font-weight: bold;">phone_idx: </span>' + list[i].phone_idx;
					
					list[i].section_name = list[i].name;

					list[i].time_diff_min = '<div class="badge ' + state + '" style="height:100%; padding:1vh;">' + time_diff_min + "분 </div>"
				}				
				$('#result_scanner').bootstrapTable('load', list);
			}
			else {
				$('#result_scanner').bootstrapTable('load', []);
			}
		}
	});	
}

function deleteDeviceStateData() {
	let select_array = $('#result_scanner').bootstrapTable('getAllSelections');
	if (select_array.length == 0) {
		alert('데이터를 1개 이상 선택해주세요');
		return;
	}
	
	for (let i = 0; i < select_array.length; i++) {
		deleteDeviceState(select_array[i].scanner_mac, select_array[i].app_type, select_array[i].phone_idx);
	}	
}

function deleteDeviceState(scanner_mac, app_type, phone_idx) {
	//alert("scanner_mac: " + scanner_mac + " / app_type: " + app_type + " / phone_idx: " + phone_idx);

	let input = false;
	input = confirm('데이터를 삭제 하시겠습니까?');
	
	if (input) {
		$.ajax({
			type: "POST",
			url: "./device/delete/state",
			data: {
				scanner_mac: scanner_mac,
				app_type: app_type,
				phone_idx: phone_idx,
			},
			async: true,
			cache: false,
			success: function (json, status) {
				getDeviceStateData();
			}
		});
	}
}
