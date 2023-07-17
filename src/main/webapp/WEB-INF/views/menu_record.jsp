<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
 
<script>

	$(document).ready(function() {
		initVue();
		$('#search_cont_id').val('${tar_cont_id}');
	});

	function printRecordList() {
		$('#print_cont_id').val('${tar_cont_id}');
		$('#printForm').submit();
	}
	function registerWorker() {
		$('#registerForm').submit();
	}
	function registerExcelWorker() {
		$('#excelForm').submit();
	}

	var app_worker;
	function initVue() {
		app_worker = new Vue({
			el : '#app_worker',
			data : {
				id : '',
				name : '',
				jumin : '',
				cont_id : '',
				t_id : '',
				hiredate : '',
				btype : '',
				edudate : '',
				eduimage : '',
				file_modify_1 : 0,
				sealed_date1 : '',
				sealed_date2 : '',
				sealed_date3 : '',
				sealed_date4 : ''
			},
			methods : {
				disableFile : function(index, file_num) {
					if (file_num == 1) {
						this.file_modify_1 = 1;
						$('#image1').hide();
						$('#file1').show();
					}
				},
			}
		});
	}

	function openUpdateModal(id) {
		//사진 초기화
		$('#image1').show();
		$("#file1").val('');
		$('#file1').hide();

		$.ajax({
			type : "GET",
			async : false,
			url : "./json/getWorkerInfo",
			data : {
				worker_id : id
			},
			cache : false,
			success : function(json, status) {
				var json_data = JSON.parse(json);
				if (json_data.result == "true") {
					if (json_data.list.length > 0) {
						var worker = json_data.list[0];
						app_worker.id = worker.id;
						app_worker.name = worker.name;
						app_worker.cont_id = worker.cont_id;
						app_worker.t_id = worker.t_id;
						app_worker.hiredate = worker.hiredate;
						app_worker.btype = worker.btype;
						app_worker.edudate = worker.edudate;
						app_worker.eduimage = worker.eduimage;
						app_worker.sealed_date1 = worker.sealed_date1;
						app_worker.sealed_date2 = worker.sealed_date2;
						app_worker.sealed_date3 = worker.sealed_date3;
						app_worker.sealed_date4 = worker.sealed_date4;
						app_worker.file_modify_1 = 0;

						app_worker.jumin = worker.jumin;
						$('#worker_jumin_6').val(worker.jumin.substring(0, 6));
						$('#worker_jumin_7').val(worker.jumin.substring(6, 7));
					}
				}
			}
		});

		$('#worker_edudate').css('border', '1px solid #cccccc');
		$('#worker_hiredate').css('border', '1px solid #cccccc');
		$('#update_modal').modal('show');
	}

	function updateWorker(id) {
		var isOk = true;
		
		// 유효성 제거 230717
		/*
		if (app_worker.edudate == '' || checkDateFormat(app_worker.edudate)) {
			$('#worker_edudate').css('border', '1px solid #cccccc');
		} else {
			isOk = false;
			$('#worker_edudate').css('border', '1px solid red');
		}

		if (app_worker.hiredate == '' || checkDateFormat(app_worker.hiredate)) {
			$('#worker_hiredate').css('border', '1px solid #cccccc');
		} else {
			isOk = false;
			$('#worker_hiredate').css('border', '1px solid red');
		}
		*/
		
		app_worker.jumin = '' + $('#worker_jumin_6').val() + $('#worker_jumin_7').val();
		if (!isNaN(app_worker.jumin) && app_worker.jumin.length == 7) {
			$('#worker_jumin_6').css('border', '1px solid #cccccc');
			$('#worker_jumin_7').css('border', '1px solid #cccccc');
		}
		else {
			isOk = false;
			$('#worker_jumin_6').css('border', '1px solid red');
			$('#worker_jumin_7').css('border', '1px solid red');
		}

		if (isOk) {
			$.ajax({
				type : "POST",
				url : "./json/updateWorker",
				traditional : true,
				data : {
					"dataVO" : JSON.stringify(app_worker.$data)
				},
				cache : false,
				success : function(json, status) {
					if (json == "true") {
						uploadImage(app_worker.id); //image upload
						alert('수정이 완료되었습니다');
						$('#searchForm').submit();
					} else {
						alert('error');
					}
				},
				error : null
			});
		} else {
			alert('항목을 다시 확인해주시기 바랍니다');
		}
	}

	//NNNN-NN-NN 형식인지 체크
	function checkDateFormat(date_val) {
		if (typeof date_val != 'undefined' && date_val.length > 0) {//입력안할경우 패스
			var rgEx_date = /^\d{4}-[01][0-9]-[0123][0-9]$/g;//달력 정규표현식		   
			var chk_date = rgEx_date.test(date_val);

			if (!chk_date || date_val.length < 10) {
				return false;
			} else {
				return true;
			}
		}
	}

	function deleteWorker(id) {
		var input = confirm('해당근로자를 정말로 삭제하시겠습니까?');
		if (input) {
			$('#d_id').val(id);
			$('#deleteForm').submit();
		}
	}

	function outcomeWorker(w_id) {
		var input = confirm('해당근로자를 반출하시겠습니까?');
		if (input) {
			$.ajax({
				type : "POST",
				url : "./json/outcomeWorker",
				data : {
					w_id : w_id
				},
				cache : false,
				success : function(json, status) {
					alert('반출이 처리되었습니다.');
					location.reload();
				},
				error : null
			});
		}
	}

	function popupImage(path) {
		$('#worker_image').attr('src', './image?virtname=' + path);
		$('#btn_image_modal').click();
	}

	function uploadImage(worker_id) {
		//image 가져옴
		var form = $('#image_form')[0];
		var formData = new FormData(form);
		formData.append("worker_id", worker_id);
		formData.append("file_modify_1", app_worker.file_modify_1);

		if (typeof $("#file1")[0] != 'undefined') {
			formData.append("ind_image_1", $("#file1")[0].files[0]);
		}

		$.ajax({
			url : './json/uploadWorkerImage',
			processData : false,
			contentType : false,
			data : formData,
			async : false,
			type : 'POST',
			success : function(json, status) {
				var json_data = JSON.parse(json);
				console.log(json_data);
			}
		});
	}

	function updateRecord() {
		$('#updateRecordForm').submit();
	}

	function updateRecordByIdList() {
		var list = $('#workerTable').bootstrapTable('getData', true);
		var list_idx = new Array();
		var min_idx = 0;
		var max_idx = 0;
		for (var i = 0; i < list.length; i++) {
			var idxx = list[i].idxx;
			list_idx.push(idxx);
		}
		$('#idList').val(list_idx);
		$('#updateRecordForm').submit();
	}

	function changeCont() {
		$('#searchForm').submit();
	}
	
</script>

<style>
#workerUpdateTable th {
	min-width: 100px;
	font-size: 0.9em;
}

#workerUpdateTable input, #workerUpdateTable select {
	height: 40px;
	margin: 5px 0px;
	line-height: 40px;
	font-size: 0.9em;
}

#jumin_back {
	max-width: 40px;
	display: inline-block;
	text-align: center;
}

.btn-excel {
	width: 160px !important;
	min-width: 160px !important;
	flex: 0 0 40px;
	box-shadow: 0 0 12px 0 rgb(0 0 0/ 10%);
	border: 1px solid #A2A2A2 !important;
	border-radius: 5px;
	font-size: 1em;
	color: #333 !important;
}
</style>
<form id="image_form" method="post" enctype="multipart/form-data"
	action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">근로자 관리</div>

	<div class="content_button_box content-item">
		<div class="btn btn-primary" onclick="printRecordList()">
			<i class="fa-solid fa-print"></i> 출력
		</div>
		<div class="btn btn-excel" onclick="registerExcelWorker()">
			<i class="fa-solid fa-file-import"></i> Excel 일괄 등록
		</div>
		<div class="btn btn-default" onclick="registerWorker()">
			<i class="fa-regular fa-registered"></i> 등록
		</div>
	</div>

	<div class="content_selete_box content-item">
		<form id="searchForm" class="form-contractor"
			action="menu_record" method="POST" autocomplete="off">
			<c:if test="${sessionScope.userLoginInfo.cont_type == 0}">
				<span class="select-title">업체:</span>
				<select id="search_cont_id" name="cont_id"
					class="form-control select-content" onchange="changeCont()">
					<option value="-1">전체</option>
					<c:forEach var="cont" items="${contList}" varStatus="idx">
						<option value="${cont.id}">${cont.name}</option>
					</c:forEach>
				</select>
			</c:if>
		</form>
	</div>

	<div class="content_data_count_box content-item">총
		${workerList.size()} 명</div>

	<div class="content_table_box content-item">
		<c:if test="${isAccess}">
			<table id="workerTable" data-toggle="table" data-search="true"
				data-pagination="true" data-page-size="25"
				data-page-list="[10, 25, 50, 100, All]" data-filter-control="true"
				class="table table-bordered col-xs-12 table-hover table-striped">
				<thead>
					<tr>
						<th class="text-center show-web" data-field="no"
							data-sortable="true">순 번</th>
						<th class="text-center show-web" data-field="name"
							data-sortable="true">성 명</th>
						<th class="text-center show-web">성 별</th>
						<th class="text-center show-web" data-field="cont_name"
							data-sortable="true">소 속</th>
						<th class="text-center show-web" data-field="w_type"
							data-sortable="true" data-filter-control="select">직 종</th>
						<th class="text-center show-web">주민번호</th>
						<th class="text-center show-web">채용일</th>
						<th class="text-center show-web">전화번호</th>
						<th class="text-center show-web">혈액형</th>
						<th class="text-center show-web">신규교육</th>
						<th class="text-center show-web">밀폐교육일자</th>
						<th class="text-center show-web">외국인여부</th>
						<th class="text-center show-web">사진</th>
						<th class="text-center show-mobile">근로자 사진</th>
						<th class="text-center show-mobile">상세 인적사항</th>
						<!-- <th class="text-center show-mobile" >밀폐교육일자</th> -->
						<th class="text-center show-mobile">수정 및 삭제</th>
						<c:if test="${userLoginInfo.site_auth != 3}">
							<th class="text-center show-web">수정</th>
							<th class="text-center show-web">삭제</th>
						</c:if>

					</tr>
				</thead>

				<tbody>
					<c:forEach var="worker" items="${workerList}" varStatus="idx">
						<tr>
							<td class="text-center show-web">${idx.index + 1}</td>
							<td class="text-center show-web">${worker.name}</td>
							<td class="text-center show-web"><c:if
									test="${worker.jumin != null && worker.jumin.length() >= 7}">
									<c:if test="${worker.jumin.substring(6,7).equals('1')}">남</c:if>
									<c:if test="${worker.jumin.substring(6,7).equals('2')}">여</c:if>
									<c:if test="${worker.jumin.substring(6,7).equals('3')}">남</c:if>
									<c:if test="${worker.jumin.substring(6,7).equals('4')}">여</c:if>
									<c:if test="${worker.jumin.substring(6,7).equals('5')}">남</c:if>
									<c:if test="${worker.jumin.substring(6,7).equals('6')}">여</c:if>
								</c:if></td>
							<td class="text-center show-web">${worker.cont_name}</td>
							<td class="text-center show-web">${worker.t_name}</td>
							<td class="text-center show-web"><c:if
									test="${worker.jumin.length() > 6 }">
	        				${worker.jumin.substring(0,6)}-${worker.jumin.substring(6,7)}******
	        			</c:if></td>
							<td class="text-center show-web">${worker.hiredate}</td>
							<td class="text-center show-web">${worker.phone}</td>
							<td class="text-center show-web">${worker.btype}</td>
							<td class="text-center show-web">${worker.edudate}</td>

							<td class="text-left show-web"><c:if
									test="${worker.sealed_date1.length() >= 8 }">
									<div>1차 : ${worker.sealed_date1}</div>
								</c:if> <c:if test="${worker.sealed_date2.length() >= 8 }">
									<div>2차 : ${worker.sealed_date2}</div>
								</c:if> <c:if test="${worker.sealed_date3.length() >= 8 }">
									<div>3차 : ${worker.sealed_date3}</div>
								</c:if> <c:if test="${worker.sealed_date4.length() >= 8 }">
									<div>4차 : ${worker.sealed_date4}</div>
								</c:if></td>

							<td class="text-center show-web"><c:if
									test="${worker.gubun == 0}"> 해당 없음</c:if> <c:if
									test="${worker.gubun == 1}">해당
								${worker.country} ${worker.passno}
							</c:if></td>

							<td class="text-center show-web">
								<div class="btn icon-primary">
									<c:if
										test="${worker.eduimage != null && !worker.eduimage.equals('')}">
										<span style="cursor: pointer"
											onclick="popupImage('${worker.eduimage}')"><i
											class="fa-solid fa-image-portrait"></i></span>
									</c:if>
								</div>
							</td>
							<td class="text-center show-mobile"><img class="photo"
								src="./image?virtname=${worker.eduimage}"
								style="width: 80px; height: 80px;"
								onerror="this.src='images/noimage.png'"></td>
							<td class="text-left show-mobile">
								<div class="mobile-box">
									<div class="mobile-item">업체: ${worker.cont_name}</div>
									<div class="mobile-item">이름: ${worker.name}</div>
									<div class="mobile-item">직종: ${worker.t_name}</div>
									<div class="mobile-item">연락처: ${worker.phone}</div>
								</div>
							</td>
							<%-- 
		      	 		<td class="text-left show-mobile">
		      	 			<div class="mobile-box">
		      	 				<c:if test="${worker.sealed_date1.length() >= 8 }">
					    		<div class="mobile-item">1차 : ${worker.sealed_date1}</div>
					    		</c:if>
					    		<c:if test="${worker.sealed_date2.length() >= 8 }">
					    		<div class="mobile-item">2차 : ${worker.sealed_date2}</div>
					    		</c:if>
					    		<c:if test="${worker.sealed_date3.length() >= 8 }">
					    		<div class="mobile-item">3차 : ${worker.sealed_date3}</div>
					    		</c:if>
					    		<c:if test="${worker.sealed_date4.length() >= 8 }">
					    		<div class="mobile-item">4차 : ${worker.sealed_date4}</div>
				      	 		</c:if>
							</div>				      
						</td>
			        	 --%>
							<td class="text-left show-mobile">
								<div style="float: left; width: 50%;" class="btn icon-default"
									onclick="openUpdateModal('${worker.id}')">
									<i class="fa-regular fa-pen-to-square"></i>
								</div>
								<div style="float: left; width: 50%;" class="btn icon-danger"
									onclick="deleteWorker('${worker.id}')">
									<i class="fa-solid fa-trash"></i>
								</div>
							</td>

							<c:if test="${userLoginInfo.site_auth != 3}">
								<td class="text-center show-web"><div
										class="btn icon-default"
										onclick="openUpdateModal('${worker.id}')">
										<i class="fa-regular fa-pen-to-square"></i>
									</div></td>
								<td class="text-center show-web"><div
										class="btn icon-danger" onclick="deleteWorker('${worker.id}')">
										<i class="fa-solid fa-trash"></i>
									</div></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>

	<div style="display: none" id="btn_update_modal" data-toggle="modal"
		data-target="#update_modal"></div>
	<div class="modal fade" id="update_modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header" style="font-size: 1.5em;">근로자 정보 수정
				</div>
				<div class="modal-body">
					<div id="app_worker">
						<div class="col-sm-12">
							<table id="workerUpdateTable" width="100%">
								<tr>
									<th>성명</th>
									<td><input v-model.lazy="name" class="form-control"></input></td>
								</tr>
								<tr>
									<th>소속</th>
									<td><select v-model.lazy="cont_id" class="form-control">
											<c:forEach var="cont" items="${contList}" varStatus="idx">
												<option value="${cont.id}">${cont.name}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<th>직종</th>
									<td><select v-model.lazy="t_id" class="form-control">
											<c:forEach var="wtype" items="${wtypeList}" varStatus="idx">
												<option value="${wtype.id}">${wtype.t_name}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<th>주민번호</th>
									<td><input id="worker_jumin_6" class="form-control"
										size="6" maxlength="6"
										style="width: 60%; display: inline-block;"></input>- <input
										id="worker_jumin_7" class="form-control" size="1"
										maxlength="1"
										style="max-width: 40px; display: inline-block; text-align: center;"></input>
									</td>
								</tr>
								<tr>
									<th>채용일</th>
									<td><input id="worker_hiredate" v-model.lazy="hiredate"
										class="form-control" placeholder="2017-01-01"></input></td>
								</tr>
								<tr>
									<th>혈액형</th>
									<td><select v-model.lazy="btype" class="form-control">
											<option value="">--선택--</option>
											<option value="A">A</option>
											<option value="B">B</option>
											<option value="O">O</option>
											<option value="AB">AB</option>
									</select></td>
								</tr>
								<tr>
									<th>신규교육</th>
									<td><input id="worker_edudate" v-model.lazy="edudate"
										class="form-control" placeholder="0000-00-00"></input></td>
								</tr>
								<tr>
									<th>사 진</th>
									<td>
										<div v-bind:id="'image1'">
											<img v-bind:src="'image?virtname=' + eduimage"
												onclick="zoomImage(this)"
												style="max-height: 100px; max-width: 100px; border: 1px solid #D4D4D4;">
											<span style="color: blue; cursor: pointer"
												@click="disableFile(-1, 1)">x</span>
										</div> <input v-bind:id="'file1'" type="file" v-show="!eduimage"
										name="eduimage"></input>
									</td>
								</tr>
								<tr>
									<th>밀폐교육(1차)</th>
									<td><input id="worker_sealed_date1"
										v-model.lazy="sealed_date1" class="form-control"
										placeholder="0000-00-00"></input></td>
								</tr>
								<tr>
									<th>밀폐교육(2차)</th>
									<td><input id="worker_sealed_date2"
										v-model.lazy="sealed_date2" class="form-control"
										placeholder="0000-00-00"></input></td>
								</tr>
								<tr>
									<th>밀폐교육(3차)</th>
									<td><input id="worker_sealed_date3"
										v-model.lazy="sealed_date3" class="form-control"
										placeholder="0000-00-00"></input></td>
								</tr>
								<tr>
									<th>밀폐교육(4차)</th>
									<td><input id="worker_sealed_date4"
										v-model.lazy="sealed_date4" class="form-control"
										placeholder="0000-00-00"></input></td>
								</tr>
							</table>
						</div>

						<div id="view_button" class="text-right"
							style="margin-top: 15px; padding-right: 5px">
							<div class="btn btn-default margin-top" onclick="updateWorker()">
								<i class="fa-regular fa-pen-to-square"></i> 수정
							</div>
							<div class="btn btn-danger margin-top" data-dismiss="modal">
								<i class="fa-solid fa-xmark"></i> 닫기
							</div>
						</div>
					</div>
					<!-- app_worker -->
				</div>
				<!-- modal-body -->
			</div>
			<!-- modal-content -->
		</div>
		<!-- modal-dialog -->
	</div>
	<!-- update_modal END -->


</div>
<!-- content-wrapper END -->


<div id="form_group">
	<form id="updateRecordForm" action="registerRecordList" method="POST">
		<input id="idList" type="hidden" name="idList" />
	</form>
	<form id="printForm" action="printWorkerList" method="POST">
		<input id="print_cont_id" type="hidden" name="cont_id" />
	</form>
	<form id="registerForm" action="registerWorker" method="POST"></form>
	<form id="excelForm" action="registerWorkerExcel" method="POST"></form>
	<form id="deleteForm" action="deleteWorker" method="POST">
		<input id="d_id" type="hidden" name="id" />
	</form>
</div>



<div style="display: none" id="btn_image_modal" data-toggle="modal"
	data-target="#image_modal"></div>
<div class="modal fade" id="image_modal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header" style="font-size: 1.5em;">근로자 이미지</div>
			<div class="modal-body">
				<img id="worker_image" src="" class="enlargeImageModalSource"
					style="width: 100%; max-width: 750px;"
					onerror="this.src='images/noimage.png'">
			</div>
		</div>
	</div>
</div>
<!-- btn_image_modal END -->

<%@ include file="IncludeBottom.jsp"%>