(function(window, $) {
	"use strict";

	if (!$) {
		throw new Error("jQuery is required for qr.js");
	}

	var QRAttend = {};
	window.QRAttend = QRAttend;

	var CONFIG = {
		siteId : null,
		mainUrl : "main",
		apiIdentificationUrl : "https://segp5.gsil.net:11243/postIdentification"
	};

	/*
		[G_STATE]
		0   : Init
		1   : 필수준수사항
		2   : New Engineer
			2.1 : 개인정보 수집 및 이용동의 체크 X
			2.2 : 개인정보 수집 및 이용동의 체크 O
		3   : Init Insert Info
		4   : 입장
		5   : 퇴장
		5.1 : 퇴장 코멘트 입력
	*/

	var G_STATE = 1;
	var G_UW_ID = "";
	var G_ROLE = 0;

	var interval = null;
	var timerExpiredFired = false;

	var __lastAlertMsg = "";
	var __lastAlertAt = 0;

	function safeAlert(msg) {
		var now = Date.now();
		if (msg === __lastAlertMsg && (now - __lastAlertAt) < 800)
			return;
		__lastAlertMsg = msg;
		__lastAlertAt = now;
		alert(msg);
	}

	var __lockMap = {};

	function acquireLock(key) {
		if (__lockMap[key])
			return false;
		__lockMap[key] = true;
		return true;
	}

	function releaseLock(key) {
		__lockMap[key] = false;
	}

	function parseJsonMaybe(v) {
		if (v == null)
			return null;
		if (typeof v === "object")
			return v;
		try {
			return JSON.parse(v);
		} catch (e) {
			return null;
		}
	}

	function clearTimer() {
		try {
			clearInterval(interval);
		} catch (e) {
		}
		interval = null;
		timerExpiredFired = false;
	}

	window.MOBILE_TYPE = window.MOBILE_TYPE || 0;

	function isConnectMobile() {
		var ua = navigator.userAgent || "";
		var isMobileUA = /Android/i.test(ua) || /iPhone|iPad|iPod/i.test(ua)
				|| /Opera Mini/i.test(ua) || /IEMobile/i.test(ua)
				|| /BlackBerry/i.test(ua);

		var hasTouch = (navigator.maxTouchPoints && navigator.maxTouchPoints > 0)
				|| ("ontouchstart" in window);
		var smallScreen = Math.min(window.screen.width || 9999,
				window.innerWidth || 9999) <= 1024;

		var isMobile = isMobileUA || (hasTouch && smallScreen);

		if (isMobile) {
			if (/Android/i.test(ua))
				window.MOBILE_TYPE = 1;
			else if (/iPhone|iPad|iPod/i.test(ua))
				window.MOBILE_TYPE = 2;
			else if (/BlackBerry/i.test(ua))
				window.MOBILE_TYPE = 3;
			else if (/Opera Mini/i.test(ua))
				window.MOBILE_TYPE = 4;
			else if (/IEMobile/i.test(ua))
				window.MOBILE_TYPE = 5;
			else
				window.MOBILE_TYPE = 6;
			return true;
		} else {
			window.MOBILE_TYPE = 0;
			return false;
		}
	}

	function returnToMain() {
		clearTimer();
		try {
			window.close();
		} catch (e) {
		}
		location.replace(CONFIG.mainUrl);
	}

	function showPreBtn(name) {
		$("#prebtn").html(name).css("display", "block");
	}

	function closePreBtn() {
		$("#prebtn").css("display", "none");
	}

	function showNextBtn(name) {
		$("#nextbtn").html(name).css("display", "block");
	}

	function closeNextBtn() {
		$("#nextbtn").css("display", "none");
	}

	function showPage(pageId) {
		$('[id^=_page_]').hide();
		$(pageId).show();
	}

	function lockIdentificationUI() {
		$("#certkey_box").css("display", "flex");
		$("#certBtn").css("display", "none");
		$("#inputPhone").css("background", "#DEDEDE").prop("readonly", true);
	}

	function unlockIdentificationUI() {
		$("#certkey_box").css("display", "none");
		$("#certBtn").css("display", "block");
		$("#inputPhone").css("background", "#FFF").prop("readonly", false);
	}

	function preStep() {
		switch (G_STATE) {
		case 0:
		case 1:
		case 1.1:
		case 2:
		case 2.1:
		case 5.1:
			location.reload();
			break;

		case 3:
			showPreBtn("이전");
			showPage("#_page_2");
			$("#agree").prop("checked", false);
			G_STATE = 2.1;
			break;

		case 4:
			G_STATE = 1.1;
			moveDutyPage();
			break;
		}
	}

	function nextStep() {
		switch (G_STATE) {
		case 1:
			moveEnterPage();
			break;

		case 1.1:
			if (isDutyChecked())
				moveEnterPage();
			else
				safeAlert("필수 준수사항을 확인하셔야 다음 진행이 가능합니다.");
			break;

		case 2:
			showPreBtn("이전");
			showPage("#_page_2");
			G_STATE = 2.1;
			break;

		case 2.1:
		case 2.2:
			checkAgree();
			break;

		case 3:
			insertUWData();
			break;

		case 4:
			postWorkIn();
			break;

		case 5:
			showPage("#_page_5");
			G_STATE = 5.1;
			showNextBtn("퇴장");
			break;

		case 5.1:
			postWorkOut();
			break;
		}
	}

	function checkAgree() {
		if ($("#agree").is(":checked")) {
			G_STATE = 2.2;
			showPage("#_page_3");
			showNextBtn("등록");
			G_STATE = 3;
		} else {
			safeAlert("필수 항목에 동의하셔야 다음 진행이 가능합니다.");
			G_STATE = 2.1;
		}
	}

	function checkSession() {
		if (!acquireLock("checkSession"))
			return;

		var encodedQueryString = location.search.replace(/\+/g, "%2B");
		var params = new URLSearchParams(encodedQueryString);
		var encryption = params.get("encryption");

		if (!encryption || encryption.length < 1) {
			releaseLock("checkSession");
			safeAlert("유효하지 않는 경로로 들어오셨습니다.");
			returnToMain();
			return;
		}

		$.ajax({
			type : "POST",
			url : "qr/checkEncryptionKey",
			data : {
				encryption : encryption
			},
			async : true,
			cache : false,
			success : function(json) {
				var data = parseJsonMaybe(json);
				if (!data) {
					safeAlert("세션 확인에 실패했습니다.");
					returnToMain();
					return;
				}
				if (String(data.result) === "true") {
				} else {
					safeAlert("인증이 완료된 QR로 입장하셨습니다.");
					returnToMain();
				}
			},
			error : function() {
				safeAlert("세션 확인 요청에 실패했습니다.");
				returnToMain();
			},
			complete : function() {
				releaseLock("checkSession");
			}
		});
	}

	function getRandomFunction(min, max) {
		var array = new Uint32Array(1);
		window.crypto.getRandomValues(array);
		var seed = max - min + 1;
		return (array[0] % seed) + min;
	}

	function requestIdentificationSMS() {
		if (!acquireLock("requestIdentificationSMS"))
			return;

		var regex = /^010\d{8}$/;
		var inputPhone = $("#inputPhone").val();

		if (!regex.test(inputPhone)) {
			releaseLock("requestIdentificationSMS");
			safeAlert("올바르지 않은 핸드폰 번호입니다.");
			return;
		}

		lockIdentificationUI();

		$.ajax({
			type : "POST",
			url : CONFIG.apiIdentificationUrl,
			data : {
				site_id : CONFIG.siteId,
				phone : inputPhone,
				certKey : getRandomFunction(1000, 9999)
			},
			async : true,
			cache : false,
			success : function(data) {
				var obj = parseJsonMaybe(data) || data;

				if (obj && obj.status == 200) {
					setCertkeyFunction();
				} else {
					safeAlert("입력하신 핸드폰번호를 확인해주세요");
					unlockIdentificationUI();
				}
			},
			error : function(xhr, status, err) {
				console.log("AJAX ERROR", status, err);
				console.log("HTTP", xhr.status, xhr.responseText);
				safeAlert("요청 실패: " + xhr.status + " / " + status);
				unlockIdentificationUI();
			},
			complete : function() {
				releaseLock("requestIdentificationSMS");
			}
		});
	}

	function checkIdentification() {
		requestIdentificationSMS();
	}

	function setCertkeyFunction() {
		clearTimer();

		var limitMiliSecond = 181 * 1000;
		var second = Math.floor(limitMiliSecond / 1000);

		var endTime = new Date();
		endTime.setSeconds(endTime.getSeconds() + second);
		var endTimeSeconds = endTime.getTime();

		interval = setInterval(function() {
			var timer = Math
					.floor((endTimeSeconds - new Date().getTime()) / 1000);
			displaykeyinTime(Math.max(timer, 0));

			if (timer <= 0) {
				clearTimer();

				$("#cerkeyInputId").css("background", "#DEDEDE").prop(
						"readonly", true);
				$("#inputPhone").css("background", "#FFF").prop("readonly",
						false);

				if (!timerExpiredFired) {
					timerExpiredFired = true;
					safeAlert("인증번호 유효시간이 초과되었습니다");
				}

				$("#resendMsgCertKey").css("display", "block");
				$("#confirmCertKeyId").css("display", "none");
			}
		}, 1000);
	}

	function resendMsgCertKey() {
		$("#cerkeyInputId").css("background", "#FFF").prop("readonly", false);
		$("#resendMsgCertKey").css("display", "none");
		$("#confirmCertKeyId").css("display", "block");
		requestIdentificationSMS();
	}

	function displaykeyinTime(time) {
		var minutes = parseInt(time / 60, 10);
		var seconds = parseInt(time % 60, 10);
		minutes = minutes < 10 ? "0" + minutes : String(minutes);
		seconds = seconds < 10 ? "0" + seconds : String(seconds);
		$("#countDown").text(minutes + ":" + seconds);
	}

	function confirmCertKeyId() {
		if (!acquireLock("confirmCertKeyId"))
			return;

		$("#infoWrap").css("display", "none");
		$('[id^=_uw_]').html("");

		$.ajax({
			type : "GET",
			url : "qr/checkCertKeyVaild",
			data : {
				phone : $("#inputPhone").val(),
				certkey : $("#cerkeyInputId").val()
			},
			async : true,
			cache : false,
			success : function(data) {
				var obj = parseJsonMaybe(data) || data;

				if (obj != null) {
					clearTimer();

					$("#certkey_box").css("display", "none");

					if ($("#modifyId").length) {
						$("#modifyId").css("display", "inherit");
					}

					$("#inputPhone").css("background", "#DEDEDE").prop(
							"readonly", true);

					if (obj.role > 0) {
						$("#infoWrap").css("display", "block");
						$("#_uw_cont_name").text(obj.cont_name || "");
						$("#_uw_name").text(obj.name || "");
						$("#_uw_wt_type").text(obj.wt_name || "");

						var photo = "<img src=\"image_thumb?virtname="
								+ (obj.photo || "")
								+ "&height=150&width=100\" onerror=\"this.src='images/noimage.png'\">";
						$("#_uw_photo").html(photo);

						G_ROLE = obj.role;
						G_UW_ID = obj.id;

						if (obj.start_time == null) {
							G_STATE = 1;
							$("#_uw_start_time").html("입장 전");
							$("#_uw_work_min").removeClass("green").text("");
						} else {
							$("#_uw_start_time").text(obj.start_time);
							$("#_uw_work_min").text((obj.work_min || 0) + "분");
							G_STATE = 5;
						}

						showNextBtn("다음");
					} else {
						safeAlert("신규 회원 입니다. 오른쪽 하단 다음을 눌러주세요.");
						G_STATE = 2;
						showNextBtn("다음");
					}
				} else {
					safeAlert("인증 번호가 맞지 않습니다.");
				}
			},
			error : function() {
				safeAlert("인증 확인 요청에 실패했습니다.");
			},
			complete : function() {
				releaseLock("confirmCertKeyId");
			}
		});
	}

	function checkValidation() {
		var name = $("#name").val() || "";
		var jumin = $("#jumin").val() || "";
		var phone = $("#inputPhone").val() || "";
		var cont_id = $("#cont").val();
		var cont_name = $("#cont_name").val() || "";

		var isOk = true;

		if (name.length < 1) {
			$("#name").css("border", "2px solid red");
			isOk = false;
		} else {
			$("#name").css("border", "1px solid #cccccc");
		}

		if (jumin.length < 6) {
			$("#jumin").css("border", "2px solid red");
			isOk = false;
		} else {
			$("#jumin").css("border", "1px solid #cccccc");
		}

		var rgEx = /^(01[016789])(\d{3,4})\d{4}$/;
		var chk_phone = rgEx.test(phone);

		if (!chk_phone) {
			$("#inputPhone").css("border", "2px solid red");
			safeAlert("핸드폰을 다시 인증해주세요");
			return false;
		} else {
			$("#inputPhone").css("border", "1px solid #cccccc");
		}

		if (String(cont_id) === "-1") {
			$("#cont").css("border", "2px solid red");
			isOk = false;
		} else if (String(cont_id) === "-2") {
			if (cont_name.length < 1) {
				$("#cont_name").css("border", "2px solid red");
				isOk = false;
			} else {
				$("#cont_name").css("border", "1px solid #cccccc");
			}
		} else {
			$("#cont").css("border", "1px solid #cccccc");
			$("#cont_name").css("border", "1px solid #cccccc");
		}

		return isOk;
	}

	function insertUWData() {
		if (!acquireLock("insertUWData"))
			return;

		var isOk = checkValidation();
		if (isOk) {
			checkDuplicateCheck();
		} else {
			safeAlert("항목을 다시 확인해주시기 바랍니다.");
			releaseLock("insertUWData");
		}
	}

	function checkDuplicateCheck() {
		if (!acquireLock("checkDuplicateCheck"))
			return;

		var payload = {
			role : $("#role").val(),
			name : $("#name").val(),
			jumin : $("#jumin").val(),
			jumin_back : $("#jumin_back").val(),
			phone : $("#inputPhone").val(),
			cont_id : $("#cont").val(),
			cont_name : $("#cont_name").val(),
			work_type : $("#workType").val()
		};

		$.ajax({
			type : "POST",
			url : "qr/checkDuplicateCheck",
			data : payload,
			async : true,
			cache : false,
			success : function(json) {
				var data = parseJsonMaybe(json);

				if (!data) {
					safeAlert("등록 처리 중 오류가 발생했습니다.");
					return;
				}

				if (String(data.result) === "true") {
					G_ROLE = data.role;
					G_UW_ID = data.uw_id;
					safeAlert("등록에 성공하였습니다.");
					G_STATE = 1;
					moveDutyPage();
				} else {
					safeAlert(data.err || "등록에 실패했습니다.");
				}
			},
			error : function() {
				safeAlert("등록 요청에 실패했습니다.");
			},
			complete : function() {
				releaseLock("checkDuplicateCheck");
				releaseLock("insertUWData");
			}
		});
	}

	function changeWtList() {
	}

	function moveDutyPage() {
		showPreBtn("취소");
		showPage("#_page_1");
		G_STATE = 1.1;
		showNextBtn("다음");
	}

	function moveEnterPage() {
		showPage("#_page_4");
		G_STATE = 4;
		showNextBtn("입장");
	}

	function isDutyChecked() {
		var isAllChecked = true;
		$('[id^=_duty_]').each(function() {
			if (!$(this).is(":checked")) {
				isAllChecked = false;
				return false;
			}
		});
		return isAllChecked;
	}

	function postWorkIn() {
		if (!acquireLock("postWorkIn"))
			return;

		$.ajax({
			type : "POST",
			url : "qr/insertQRInData",
			data : {
				site_id : CONFIG.siteId,
				uw_id : G_UW_ID,
				role : G_ROLE
			},
			async : true,
			cache : false,
			success : function(json) {
				var data = parseJsonMaybe(json);
				if (data && String(data.result) === "true") {
					safeAlert("입장완료");
					returnToMain();
				} else {
					safeAlert((data && data.err) ? data.err : "입장 처리 실패");
				}
			},
			error : function() {
				safeAlert("입장 요청에 실패했습니다.");
			},
			complete : function() {
				releaseLock("postWorkIn");
			}
		});
	}

	function postWorkOut() {
		if (!acquireLock("postWorkOut"))
			return;

		$.ajax({
			type : "POST",
			url : "qr/insertQROutData",
			data : {
				site_id : CONFIG.siteId,
				uw_id : G_UW_ID,
				role : G_ROLE,
				comment : $("#comment").val()
			},
			async : true,
			cache : false,
			success : function(json) {
				var data = parseJsonMaybe(json);
				if (data && String(data.result) === "true") {
					safeAlert("퇴장완료");
					returnToMain();
				} else {
					safeAlert((data && data.err) ? data.err : "퇴장 처리 실패");
				}
			},
			error : function() {
				safeAlert("퇴장 요청에 실패했습니다.");
			},
			complete : function() {
				releaseLock("postWorkOut");
			}
		});
	}

	function bindEvents() {
		$("#certBtn").removeAttr("onclick");
		$("#confirmCertKeyId").removeAttr("onclick");
		$("#resendMsgCertKey").removeAttr("onclick");
		$("#prebtn").removeAttr("onclick");
		$("#nextbtn").removeAttr("onclick");

		$("#cont").off("change.qr").on("change.qr", function() {
			var selectedValue = $(this).val();
			if (selectedValue === "-2")
				$("#addContBox").css("display", "contents");
			else
				$("#addContBox").css("display", "none");
			$("#cont").css("border", "1px solid #cccccc");
		});

		$("#role").off("change.qr").on("change.qr", changeWtList);
		$("#cont").off("change_wt.qr").on("change_wt.qr", changeWtList);

		$("#certBtn").off("click.qr").on("click.qr", function(e) {
			e.preventDefault();
			checkIdentification();
		});

		$("#confirmCertKeyId").off("click.qr").on("click.qr", function(e) {
			e.preventDefault();
			confirmCertKeyId();
		});

		$("#resendMsgCertKey").off("click.qr").on("click.qr", function(e) {
			e.preventDefault();
			resendMsgCertKey();
		});

		$("#prebtn").off("click.qr").on("click.qr", function(e) {
			e.preventDefault();
			preStep();
		});

		$("#nextbtn").off("click.qr").on("click.qr", function(e) {
			e.preventDefault();
			nextStep();
		});

		window.addEventListener("pageshow", function(evt) {
			if (evt.persisted) {
				clearTimer();
				__lockMap = {};
			}
		});
	}

	QRAttend.init = function(opt) {
		CONFIG = $.extend({}, CONFIG, opt || {});

		if (!CONFIG.siteId) {
			safeAlert("siteId 설정이 없습니다.");
			return;
		}

		bindEvents();

		if (!isConnectMobile()) {
			return;
		}

		checkSession();
	};

})(window, window.jQuery);
