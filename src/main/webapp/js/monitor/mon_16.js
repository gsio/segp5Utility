var backgroundColorOn;
var backgroundColorOff;

function initArray() {
	for(var i=0; i<beacon_array.length; i++) {
		beacon_array[i] = 0;
	}
}

function initSection() {
	set2FSection();
	set3FSection();	
	set5FSection();
		
	/*
	var over2TankList_301 = [];
	var over2TankList_304 = [];
	
	var tank301_1 = document.querySelector(".__tank_301-1");
	var tank301_2 = document.querySelector(".__tank_301-2");
	var tank304_1 = document.querySelector(".__tank_304-1");
	var tank304_2 = document.querySelector(".__tank_304-2");
	
	over2TankList_301.push(tank301_1);
	over2TankList_301.push(tank301_2);
	over2TankList_304.push(tank304_1);
	over2TankList_304.push(tank304_2);
	
	over2TankHover(over2TankList_301);
	over2TankHover(over2TankList_304);	
	*/
}

function over2TankHover(over2TankList) {
	over2TankList.forEach((obj, index) => {
	    obj.onmouseover = () => {
	    	setColor(obj);
	    	for (let i = 0; i < over2TankList.length; i++) {
	    		over2TankList[i].style.background = backgroundColorOn;
	    	}
		};
	    obj.onmouseout = () => {
	    	setColor(obj);
	    	for (let i = 0; i < over2TankList.length; i++) {
	    		over2TankList[i].style.background = backgroundColorOff;
	    	}
	    };
	});
}

function setColor(obj) {
	var cl = obj.classList[obj.classList.length-1];
    if (cl == "__process_0") {
    	backgroundColorOn = "#d5eaf5";
    	backgroundColorOff = "";
    }
    else if (cl == "__process_1") {
    	backgroundColorOn = "#b0edc7";
    	backgroundColorOff = "";
    }
    else if (cl == "__process_2") {
    	backgroundColorOn = "#BAE9CA";
    	backgroundColorOff = "";
    }
    else if (cl == "__process_3") {L
    	backgroundColorOn = "#cec4ff";
    	backgroundColorOff = "";
    } 
    else if (cl == "__process_4") {
    	backgroundColorOn = "#9adff4";
    	backgroundColorOff = "";
    }
    else if (cl == "__process_5") {
    	backgroundColorOn = "#1b884b";
    	backgroundColorOff = "";
    } 
    else if (cl == "__process_6") {
    	backgroundColorOn = "#FF0012";
    	backgroundColorOff = "";
    } 
    else if (cl == "__process_-99") {
    	backgroundColorOn = "#ffe2a0";
    	backgroundColorOff = "";
	}
    else {
		backgroundColorOn = "";
		backgroundColorOff = "";
	}
}

function set2FSection() {
	var html = "";
	
	// 1~8
	html += '<div class = "Floor_2" style="top: 17.8%; left: 4.4%; width: 27.4%; height: 5%">';	
	for (var section = 1; section <= 8; section++) {
		if(section >= 7) {
			html += getSectionHtml(section, 1);
		} else {
			html += getSectionHtml(section, 0);
		}		
	}	
	html += "</div>";

	// 9~18	
	html += '<div class = "Floor_2" style="top: 17.8%; left: 35%; width: 30.3%; height: 5%;">';	
	for (var section = 9; section <= 18; section++) {
		if(section == 9 || section == 10 || section == 13 || section == 14) {
			html += getSectionHtml(section, 1);
		} else {
			html += getSectionHtml(section, 0);
		}		
	}
	html += "</div>";

	// 19~20, 101~104	
	html += '<div class = "Floor_2" style="top: 25.8%; left: 6.2%; width: 22%; height: 12.5%;">';	
	html += getSectionHtml(19, 1);
	html += getSectionHtml(20, 1);
	for (var section = 101; section <= 104; section++) {
		html += getSectionHtml(section, 0);
	}	
	html += "</div>";

	// 105~108, 21~24
	html += '<div class = "Floor_2" style="top: 25.8%; left: 32.8%; width: 23.6%; height: 12.5%;">';
	for (var section = 105; section <= 108; section++) {
		html += getSectionHtml(section, 0);
	}
	html += getSectionHtml(21, 0);
	html += getSectionHtml(22, 0);
	html += getSectionHtml(23, 1);
	html += getSectionHtml(24, 1);
	html += "</div>";

	// 25~26
	html += '<div class = "Floor_2" style="top: 25.8%; left: 63.3%; width: 4.2%; height: 8.6%;">';
	html += getSectionHtml(25, 0);
	html += getSectionHtml(26, 0);
	html += "</div>";

	// 53~66, 31~34
	html += '<div class = "Floor_2" style="top: 41.6%; left: 2.4%; width: 25.8%; height: 18.5%">';
	for (var section = 53; section <= 66; section++) {
		if(section == 59 || section == 66) {
			html += getSectionHtml(section, 1);
		} else {
			html += getSectionHtml(section, 0);
		}	
	}
	html += getSectionHtml(31, 1);
	html += getSectionHtml(32, 1);
	html += getSectionHtml(33, 1);
	html += getSectionHtml(34, 1);
	html += "</div>";

	// 27~28
	html += '<div class = "Floor_2" style="top: 41.6%; left: 33.2%; width: 10%; height: 16%;">';
	html += getSectionHtml(27, 0);
	html += getSectionHtml(28, 0);
	html += "</div>";
	
	// 87~100, 35~36 
	html += '<div class = "Floor_2" style="top: 41.6%; left: 48%; width: 24.8%; height: 18.5%;">';
	for (var section = 87; section <= 100; section++) {
		if(section == 90 || section == 97) {
			html += getSectionHtml(section, 1);
		} else {
			html += getSectionHtml(section, 0);
		}
	}
	html += getSectionHtml(35, 1);
	html += getSectionHtml(36, 1);
	html += "</div>";

	
	// 37~50
	html += '<div class = "Floor_2" style="top: 62%; left: 2.3%; width: 26%; height: 16.2%;">';
	for (var section = 37; section <= 50; section++) {
		if(section == 43 || section == 50) {
			html += getSectionHtml(section, 1);
		} else {
			html += getSectionHtml(section, 0);
		}
	}
	html += "</div>";
	
	// 29~30
	html += '<div class = "Floor_2" style="top: 61.5%; left: 33.3%; width: 9.8%; height: 12%;">';
	html += getSectionHtml(29, 0);
	html += getSectionHtml(30, 0);
	html += "</div>";
	
	// 71~84
	html += '<div class = "Floor_2" style="top: 62%; left: 48%; width: 24.8%; height: 16.2%;">';
	for (var section = 71; section <= 84; section++) {
		if(section == 74 || section == 81) {
			html += getSectionHtml(section, 1);
		} else {
			html += getSectionHtml(section, 0);
		}
	}
	html += "</div>";
	
	$("#_section_2F").html(html);
}

function set3FSection() {	
	var html = "";

	// 101~104
	html += '<div class = "Floor_3" style="top: 31.8%; left: 6.2%; width: 21.4%; height: 11.9%;">';
	for (var section = 101; section <= 104; section++) {
		html += getSectionHtml(section, 0);
	}	
	html += "</div>";
	
	// 105~108
	html += '<div class = "Floor_3" style="top: 31.8%; left: 32.2%; width: 18.4%; height: 9.5%;">';
	for (var section = 105; section <= 108; section++) {
		html += getSectionHtml(section, 0);
	}	
	html += "</div>";
	
	// 109~118
	html += '<div class = "Floor_3" style="top: 17.1%;left: 58.9%;width: 11.8%;height: 26.2%;">';	
	for (var section = 109; section <= 118; section++) {
		html += getSectionHtml(section, 0);
	}
	html += "</div>";
	
	// 53~68
	html += '<div class = "Floor_3" style="top: 46.3%; left: 2.5%; width: 32.5%; height: 14%;">';
	for (var section = 53; section <= 68; section++) {
		if(section == 59 || section == 66) {
			html += getSectionHtml(section, 1);
		} else {
			html += getSectionHtml(section, 0);
		}
	}
	html += "</div>";
	
	// 85~100
	html += '<div class = "Floor_3" style="top: 46.3%; left: 40.2%; width: 32.6%; height: 14%;">';
	for (var section = 85; section <= 100; section++) {
		if(section == 90 || section == 97) {
			html += getSectionHtml(section, 1);
		} else {
			html += getSectionHtml(section, 0);
		}	
	}
	html += "</div>";
	
	// 37~52
	html += '<div class = "Floor_3" style="top: 64.1%; left: 2.5%; width: 32.5%; height: 14%;">';
	for (var section = 37; section <= 52; section++) {
		if(section == 43 || section == 50) {
			html += getSectionHtml(section, 1);
		} else {
			html += getSectionHtml(section, 0);
		}
	}
	html += "</div>";
	
	// 69~84
	html += '<div class = "Floor_3" style="top: 64.1%; left: 40.2%; width: 32.6%; height: 14%;">';
	for (var section = 69; section <= 84; section++) {
		if(section == 74 || section == 81) {
			html += getSectionHtml(section, 1);
		} else {
			html += getSectionHtml(section, 0);
		}
	}
	html += "</div>";

	$("#_section_3F").html(html);
}

function set5FSection() {
	var html = "";

	// 215~251
	html += '<div class = "Floor_5" style="top: 17.5%; left: 2.4%; width: 37.1%; height: 13.5%;">';
	for (var section = 215; section <= 251; section++) {
		if(section >= 224) {
			html += getSectionHtml(section, 1);
		} else {
			html += getSectionHtml(section, 0);
		}		
	}
	html += "</div>";
	
	// 252~285
	html += '<div class = "Floor_5" style="top: 17.5%; left: 40.5%; width: 32%; height: 13.5%;">';
	for (var section = 252; section <= 285; section++) {
		if(section >= 260) {
			html += getSectionHtml(section, 1);
		} else {
			html += getSectionHtml(section, 0);
		}		
	}
	html += "</div>";
	
	// 191~214
	html += '<div class = "Floor_5" style="top: 36.5%; left: 2.4%; width: 16.8%; height: 18.5%;">';
	for (var section = 191; section <= 214; section++) {
		if(section == 191 || section == 196 || section == 197 || section == 202 || section == 203 || section == 208 || section == 209 || section == 214) {
			html += getSectionHtml(section, 0);
		}
		else {
			html += getSectionHtml(section, 1);	
		}	
	}
	html += "</div>";
	
	// 135~150
	html += '<div class = "Floor_5" style="top: 36.5%; left: 22.7%; width: 50.1%; height: 18.5%;">';
	for (var section = 135; section <= 150; section++) {
		if(section == 141 || section == 148) {
			html += getSectionHtml(section, 1);
		} 
		else {
			html += getSectionHtml(section, 0);
		}	
	}
	html += "</div>";
	
	// 151~190
	html += '<div class = "Floor_5" style="top: 59.8%; left: 2.4%; width: 16.8%; height: 18.5%;">';
	for (var section = 151; section <= 190; section++) {
		if(section == 151 || section == 160 || section == 161 || section == 170 || section == 171 || section == 180 || section == 181 || section == 190) {
			html += getSectionHtml(section, 0);
		}
		else {
			html += getSectionHtml(section, 1);	
		}
	}
	html += "</div>";
	
	// 119~134
	html += '<div class = "Floor_5" style="top: 59.8%;left: 22.7%;width: 50.1%;height: 18.5%;">';
	for (var section = 119; section <= 134; section++) {
		if(section == 125 || section == 132) {
			html += getSectionHtml(section, 1);
		} 
		else {
			html += getSectionHtml(section, 0);
		}	
	}
	html += "</div>";
	
	$("#_section_5F").html(html);
}

function getSectionHtml(section, type, sub_number) {
	var html = "";

	if (sub_number > 0) {
		html += 
			'<div class="tank __tank_' +
			section +
			"-" +
			sub_number +
			" __tank_" +
			section +
			" __type_" +
			type +
			'" onclick="callSectionOverlay(' +
			section +
			')">';
	} else {
		html +=
			'<div class="tank __tank_' +
			section +
			" __type_" +
			type +
			'" onclick="callSectionOverlay(' +
			section +
			')">';
	}

	html +=
		'<div class="tank_num">' + getSectionTextForTanknum(section) + "</div>";
	html += '<div class="tank_count"></div>';
	html += "</div>";

	return html;
}

function getSectionTextForTanknum(section) {
	var section_text = section;
	section_text = "#" + section;
	return section_text;
}

/** 나중에 특별한 모양 수조에 사용 예정 **/

var backgroundColorOn;
var backgroundColorOff;

function over2TankHover(over2TankList) {
	over2TankList.forEach((obj, index) => {
			obj.onmouseover = () => {
				setColor(obj);
				for (let i = 0; i < over2TankList.length; i++) {
					over2TankList[i].style.background = backgroundColorOn;
				}
		};
			obj.onmouseout = () => {
				setColor(obj);
				for (let i = 0; i < over2TankList.length; i++) {
					over2TankList[i].style.background = backgroundColorOff;
				}
			};
	});
}

function setColor(obj) {
	var cl = obj.classList[obj.classList.length-1];
		if (cl == "__process_0") {
			backgroundColorOn = "#d5eaf5";
			backgroundColorOff = "";
		} else if (cl == "__process_1" || cl == "__process_2") {
			backgroundColorOn = "#b0edc7";
			backgroundColorOff = "";
		} else if (cl == "__process_3") {
			backgroundColorOn = "#cec4ff";
			backgroundColorOff = "";
		} else if (cl == "__process_4") {
			backgroundColorOn = "#9adff4";
			backgroundColorOff = "";
		} else if (cl == "__process_5") {
			backgroundColorOn = "#1b884b";
			backgroundColorOff = "";
		} else if (cl == "__process_6") {
			backgroundColorOn = "#FF0012";
			backgroundColorOff = "";
		} else if (cl == "__process_-99") {
			backgroundColorOn = "#ffe2a0";
			backgroundColorOff = "";
	} else {
		backgroundColorOn = "";
		backgroundColorOff = "";
	}
}
