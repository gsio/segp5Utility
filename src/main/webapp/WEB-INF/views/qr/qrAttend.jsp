<%@ include file="MonitorTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<script type="text/javascript" src="js/qr/qr.js"></script>
<link rel="stylesheet" href="css/qr/qr.css?s=2">

<script>
	$(document).ready(function() {
		QRAttend.init({
			siteId : 1,
			mainUrl : "main"
		});
	});
</script>

<div id="content-wrapper">
	<div id="contentPage">
		<div id="_page_0" class="page-wrap">
			<div class="content_title content-item">[신원 확인]</div>

			<div class="content_table_box content-item">
				<div class="table-container">
					<table id="page1Table" class="table table table-bordered col-xs-12 table-hover">
						<tbody>
							<tr>
								<th class="text-center">핸드폰 인증</th>
								<td>
									<input class="form-control" id="inputPhone" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' maxlength="11" style="cursor: pointer; text-align: center;" placeholder="ex) 01012345678">

									<div class="img_btn_wrapper">
										<button id="certBtn" type="button" class="btn btn-danger img_btn_box">인증하기</button>
									</div>

									<div id="certkey_box">
										<div class="info-title-box">
											<div class="title">인증확인</div>
											<div id="confirmCertKeyId" class="btn btn-default">확인</div>
											<div id="resendMsgCertKey" class="btn btn-danger">재전송</div>
										</div>

										<div class="default-item-box item-box">
											<div class="title">인증번호</div>
											<div class="content">
												<input id="cerkeyInputId" name="content" class="form-control" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' maxlength="4">
												<span id="countDown">03:00</span>
											</div>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div class="content_summary_box content-item" style="text-align: left;">
				<b style="color: #FF3547;">
					휴대폰 본인확인 시 타인 명의를 무단 도용할 경우. <br />
					"정보통신망법 제 49조에 의거하여 5년 이하의 징역 또는 5천만원의 벌금에 처할 수 있습니다.
				</b>
			</div>

			<div class="content_info_box content-item">
				<div id="infoWrap" class="info_wrap" style="display: none;">
					<div class="info_box">
						<div class="photo_box">
							<span id="_uw_photo">
								<img src="" onerror="this.src='images/noimage.png'">
							</span>
						</div>
						<div class="detail_box">
							<div class="worker">
								<span id="_uw_cont_name"></span>
							</div>
							<div class="worker infos">
								<div>
									<span id="_uw_name"></span>
								</div>
							</div>
							<div class="worker">
								<span id="_uw_wt_type"></span>
							</div>
							<div class="worker time_box">
								<span id="_uw_start_time"></span>
								<span id="_uw_work_min" class="green"></span>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>

		<div id="_page_1" class="page-wrap" style="display: none">
			<div class="content_title content-item">[필수 준수사항]</div>

			<div class="content_check_box content-item">
				<div class="content-check-box">
					<input id="_duty_1" class="duty" type="checkbox" name="duty1">
					<label class="duty_label" for="duty1" style="color: #ff3547;">
						당사 직원의 통제에 따라주세요. <br />(방문목적 외 임의행동 금지)
					</label>
				</div>
				<div class="content-img">
					<img class="duty_img" src="images/icons/qr/duty_img1.png">
				</div>
			</div>

			<div class="content_check_box content-item">
				<div class="content-check-box">
					<input id="_duty_2" class="duty" type="checkbox" name="duty2">
					<label class="duty_label" for="duty2">정해진 통로 외 임의이동을 하지 말아주세요.</label>
				</div>
				<div class="content-img">
					<img class="duty_img" src="images/icons/qr/duty_img2.png">
				</div>
			</div>

			<div class="content_check_box content-item">
				<div class="content-check-box">
					<input id="_duty_3" class="duty" type="checkbox" name="duty3">
					<label class="duty_label" for="duty3">
						작업중인 장비주변은 접근하지 말아주세요 <br />(유도자 지시 이행)
					</label>
				</div>
				<div class="content-img">
					<img class="duty_img" src="images/icons/qr/duty_img3.png">
				</div>
			</div>
		</div>

		<div id="_page_2" class="page-wrap" style="display: none">
			<div class="content_title content-item">[개인정보 수집 및 이용동의]</div>
			<div class="content_summary_box content-item">(주)지에스아이엘 건설부문(이하 ‘회사‘)은 회사 사업장 방문자의 안전사고 예방을 위하여 아래와 같이 귀하의 개인정보를 수집 및 이용하고자 합니다.</div>

			<div class="content_table_box content-item">
				<table>
					<colgroup>
						<col style="width: 24%;">
						<col style="width: 38%;">
						<col style="width: *%;">
					</colgroup>
					<tbody>
						<tr>
							<th>수집항목</th>
							<th>수집목적</th>
							<th>보유 및 이용기간</th>
						</tr>
						<tr>
							<td style="text-align: left;"><b>이름, 생년월일 앞자리, 성별, 업체, 휴대폰 번호</b></td>
							<td>방문기록, 출역관리</td>
							<td style="font-size: 16px; font-weight: bold; color: orange;">프로젝트 종료시까지</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="agree">
				<span>
					<input type="checkbox" id="agree" name="agree">
					<label for="agree">&nbsp;&nbsp;(필수) 위 개인정보 수집이용에 동의합니다.</label>
				</span>
			</div>

			<div class="content_summary_box content-item">귀하는 본 동의를 거부하실 수 있으나, 거부 시 출역 확인에 제한될 수 있습니다.</div>
			<div class="content_summary_box content-item">※ 그 외 개인정보처리에 대한 사항은 홈페이지(http://segp5.gsil.net/policy_privacy) 개인정보처리방침에서 확인하실 수 있습니다.</div>
		</div>

		<div id="_page_3" class="page-wrap" style="display: none">
			<div class="content_title content-item">[회원 가입]</div>

			<div class="content_table_box content-item">
				<div class="table-container">
					<table id="regTable" class="reg-table table table-bordered col-xs-12 table-hover">
						<tbody>
							<tr>
								<th class="text-center">소속현장</th>
								<td>
									<div class="item">삼성엔지니어링 평택 P5-PJT 그린동</div>
								</td>
							</tr>

							<tr>
								<th class="text-center">구분</th>
								<td>
									<select id="role" name="role" class="form-control">
										<option value="2">기술인</option>
										<option value="1">관리자</option>
									</select>
								</td>
							</tr>

							<tr>
								<th class="text-center required">
									업체<br />
									<span style="color: #FF3547; font-size: 0.8em;">* 확인 요망</span>
								</th>
								<td>
									<select id="cont" name="cont_id" class="form-control">
										<option value="-1">[ ----- 업체를 선택해주세요 ----- ]</option>
										<c:forEach var="cont" items="${contList}" varStatus="idx">
											<option value="${cont.id}" id="${cont.type}">${cont.name}</option>
										</c:forEach>
										<option value="-2">기타 (업체추가)</option>
									</select>
								</td>
							</tr>

							<tr id="addContBox" style="display: none;">
								<th class="text-center">업체 추가</th>
								<td>
									<input id="cont_name" name="cont_name" placeholder="ex) 업체명" class="form-control" type="text" value="" maxlength="20">
								</td>
							</tr>

							<tr>
								<th class="text-center required">직종<br />권한</th>
								<td>
									<select id="workType" name="workType" class="form-control">
										<c:forEach var="wt" items="${wTList}" varStatus="idx">
											<option value="${wt.id}" id="${wt.role}">${wt.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>

							<tr>
								<th class="text-center required">성 명</th>
								<td>
									<input id="name" name="name" placeholder="ex) 박민호" class="form-control" type="text" value="" maxlength="20">
								</td>
							</tr>

							<tr>
								<th class="text-center required">주민번호<br />앞 6자리</th>
								<td>
									<input id="jumin" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' maxlength="6" class="form-control" style="cursor: pointer; text-align: center;" placeholder="ex) 900101" />
								</td>
							</tr>

							<tr>
								<th class="text-center">성별</th>
								<td>
									<select id="jumin_back" name="jumin_back" class="form-control">
										<option value="1">남</option>
										<option value="2">여</option>
									</select>
								</td>
							</tr>

						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div id="_page_4" class="page-wrap" style="display: none">
			<div class="content_title content-item">[입장]</div>
			<div class="content_img_box content-item">
				<div class="content-img">
					<img class="duty_img" src="images/icons/qr/finish_bg2.png">
				</div>
			</div>
		</div>

		<div id="_page_5" class="page-wrap" style="display: none">
			<div class="content_title content-item">[의견 및 퇴장]</div>
			<div class="content_summary_box content-item">
				<div style="margin-bottom: 10px;">여러분의 소중한 의견을 모아 안전한 삼성현장을 만들겠습니다.</div>
				<input id="comment" placeholder="ex) 작업 중 이상현상 또는 의견" class="form-control" type="text" value="" maxlength="100">
			</div>
			<div class="content_img_box content-item">
				<div class="content-img">
					<img class="duty_img" src="images/icons/qr/opinion.png">
				</div>
			</div>
		</div>

	</div>

	<div id="contentBtnBox">
		<div class="btn-box">
			<button id="prebtn" class="btn">취소</button>
		</div>
		<div class="btn-box">
			<button id="nextbtn" class="btn" style="display: none;">다음</button>
		</div>
	</div>
</div>

<%@ include file="MonitorBottom.jsp"%>
