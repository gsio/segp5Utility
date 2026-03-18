<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.0.3/vue.js"></script>

<script>
var app_data;
const site_id = '${userLoginInfo.site_id}';
const login_user_id = '${userLoginInfo.id}';

$(document).ready(function() {
	initVue();
	initInsertValue();
	initUpdateValue();
});

function initVue() {
	app_data = new Vue({
		el: '#app_data',
		data: {
			insert: {
				section_name: '',
				floor: '',
				writer_user_id: '',
				site_id: ''
			},
			update: {
				id: '',
				section: '',
				section_name: '',
				floor: '',
				writer_user_id: '',
				site_id: ''
			}
		}
	});
}

function initInsertValue() {
	app_data.insert.section_name = '';
	app_data.insert.floor = '';
	app_data.insert.writer_user_id = login_user_id;
	app_data.insert.site_id = site_id;
}

function initUpdateValue() {
	app_data.update.id = '';
	app_data.update.section = '';
	app_data.update.section_name = '';
	app_data.update.floor = '';
	app_data.update.writer_user_id = login_user_id;
	app_data.update.site_id = site_id;
}

function openInsertModal() {
	initInsertValue();
	$('#insertModal').modal('show');
}

function openUpdateModal(id, section, section_name, floor) {
	initUpdateValue();

	app_data.update.id = id;
	app_data.update.section = section;
	app_data.update.section_name = section_name;
	app_data.update.floor = floor == null ? '' : floor;

	$('#updateModal').modal('show');
}

function validateSectionForm(section_name, floor) {
	if(section_name == null || section_name.trim() == '') {
		alert('구역명을 입력해주세요.');
		return false;
	}

	if(floor == null || floor.trim() == '') {
		alert('층수를 입력해주세요.');
		return false;
	}

	return true;
}

function insertSection() {
	var section_name = app_data.insert.section_name;
	var floor = app_data.insert.floor;

	if(!validateSectionForm(section_name, floor)) {
		return;
	}

	$.ajax({
		type : "POST",
		url : "./manage/insertSectionData",
		traditional : true,
		async : false,
		data : {
			"site_id" : app_data.insert.site_id,
			"section_name" : section_name,
			"floor" : floor,
			"writer_user_id" : app_data.insert.writer_user_id
		},
		cache : false,
		success : function(json, status) {
			var json_data = JSON.parse(json);
			if(json_data.result == "true") {
				alert('구역 등록이 완료되었습니다.');
			} else {
				alert(json_data.message != null ? json_data.message : '등록 중 오류가 발생했습니다.');
			}
			window.location.reload();
		},
		error : function() {
			alert('서버 통신 중 오류가 발생했습니다.');
		}
	});
}

function updateSection() {
	var id = app_data.update.id;
	var section_name = app_data.update.section_name;
	var floor = app_data.update.floor;

	if(!validateSectionForm(section_name, floor)) {
		return;
	}

	$.ajax({
		type : "POST",
		url : "./manage/updateSectionData",
		traditional : true,
		async : false,
		data : {
			"id" : id,
			"site_id" : app_data.update.site_id,
			"section_name" : section_name,
			"floor" : floor,
			"writer_user_id" : app_data.update.writer_user_id
		},
		cache : false,
		success : function(json, status) {
			var json_data = JSON.parse(json);
			if(json_data.result == "true") {
				alert('구역 수정이 완료되었습니다.');
			} else {
				alert(json_data.message != null ? json_data.message : '수정 중 오류가 발생했습니다.');
			}
			window.location.reload();
		},
		error : function() {
			alert('서버 통신 중 오류가 발생했습니다.');
		}
	});
}

function deleteSection(id, section_name) {
	if(!confirm('[' + section_name + '] 구역을 삭제하시겠습니까?')) {
		return;
	}

	$.ajax({
		type : "POST",
		url : "./manage/deleteSectionData",
		traditional : true,
		async : false,
		data : {
			"id" : id
		},
		cache : false,
		success : function(json, status) {
			var json_data = JSON.parse(json);
			if(json_data.result == "true") {
				alert('구역 삭제가 완료되었습니다.');
			} else {
				alert(json_data.message != null ? json_data.message : '삭제 중 오류가 발생했습니다.');
			}
			window.location.reload();
		},
		error : function() {
			alert('서버 통신 중 오류가 발생했습니다.');
		}
	});
}
</script>

<style>
	.section-info-badge {
		display: inline-block;
		padding: 4px 10px;
		border-radius: 15px;
		background: #f4f7fb;
		border: 1px solid #dbe4ee;
		color: #2c3e50;
		font-weight: 600;
		font-size: 12px;
	}

	.section-name-text {
		font-weight: 600;
		color: #333;
	}

	.floor-text {
		font-weight: 600;
		color: #5b6b7a;
	}

	.modal-title-custom {
		font-size: 1.3em;
		font-weight: 700;
		color: #2c3e50;
	}

	.modal-guide-box {
		background: #f8fafc;
		border: 1px solid #e4ebf3;
		border-radius: 8px;
		padding: 12px 15px;
		margin-bottom: 15px;
		font-size: 0.95em;
		color: #4d5b68;
	}

	.btn-area-right {
		margin-top: 15px;
		padding-right: 5px;
		text-align: right;
	}
</style>

<div id="content-wrapper">
	<div id="content_title" class="content-item">구역 관리</div>

	<div class="content_button_box content-item">
		<div class="btn btn-default" onclick="openInsertModal()">
			<i class="fa-regular fa-registered"></i> 등록
		</div>
	</div>

	<div class="content_summary_box content-item">
		<i class="fa-solid fa-circle-info" style="color:#3c8dbc;"></i>
		구역명과 층수를 등록하면 구역번호는 자동으로 생성됩니다.
	</div>

	<div class="content_data_count_box content-item">
		총 ${sList.size()} 개
	</div>

	<div class="content_table_box content-item">
		<table
			id="sectionTable"
			data-toggle="table"
			data-search="true"
			data-pagination="true"
			data-page-size="25"
			data-page-list="[10, 25, 50, 100, All]"
			data-sort-name="section"
			data-filter-control="true"
			class="table table-bordered table-hover table-striped">

			<thead>
				<tr>
					<th data-field="section" data-sortable="true" class="text-center">구역번호</th>
					<th data-field="section_name" data-sortable="true" class="text-center">구역명</th>
					<th data-field="floor" data-sortable="true" class="text-center">층수</th>
					<th data-field="writer_user_name" data-sortable="true" class="text-center show-web">작성자</th>
					<th data-field="btn_modify" class="text-center">수정</th>
					<th data-field="btn_delete" class="text-center">삭제</th>
					<th data-field="write_time" data-sortable="true" class="text-center show-web">시간</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="vo" items="${sList}" varStatus="idx">
					<tr>
						<td class="text-center">
							<span class="section-info-badge"># ${vo.section}</span>
						</td>

						<td class="text-center">
							<span class="section-name-text">${vo.section_name}</span>
						</td>

						<td class="text-center">
							<span class="floor-text">${vo.floor}</span>
						</td>

						<td class="text-center show-web">${vo.writer_user_name}</td>

						<td class="text-center">
							<div class="btn icon-default"
								onclick="openUpdateModal('${vo.id}', '${vo.section}', '${vo.section_name}', '${vo.floor}')">
								<i class="fa-solid fa-pen-to-square"></i>
							</div>
						</td>

						<td class="text-center">
							<div class="btn icon-default"
								onclick="deleteSection('${vo.id}', '${vo.section_name}')">
								<i class="fa-solid fa-trash"></i>
							</div>
						</td>

						<td class="text-center show-web">${vo.write_time}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div id="app_data">
	<!-- 등록 모달 -->
	<div class="modal fade" id="insertModal" tabindex="-1" role="dialog" aria-labelledby="insertModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<div class="modal-title-custom">구역 등록</div>
					<button type="button" class="close" data-dismiss="modal">
						<i class="fa fa-close" aria-hidden="true"></i>
					</button>
				</div>
				<div class="modal-body">
					<div class="modal-guide-box">
						구역번호는 자동 생성됩니다. 구역명과 층수만 입력해주세요.
					</div>

					<table class="table table-bordered table-hover table-striped">
						<tr>
							<th class="text-center" style="width:30%;">구역명</th>
							<td>
								<input
									id="insert_section_name"
									v-model.lazy="insert.section_name"
									class="form-control"
									placeholder="예: 전기 작업구역">
							</td>
						</tr>
						<tr>
							<th class="text-center">층수</th>
							<td>
								<input
									id="insert_floor"
									v-model.lazy="insert.floor"
									class="form-control"
									placeholder="예: B1, 1F, 3층">
							</td>
						</tr>
					</table>

					<div class="btn-area-right">
						<div class="btn btn-default margin-top" onclick="insertSection()">
							<i class="fa-regular fa-pen-to-square"></i> 등록
						</div>
						<div class="btn btn-danger margin-top" data-dismiss="modal">
							<i class="fa-solid fa-xmark"></i> 닫기
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 수정 모달 -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<div class="modal-title-custom">구역 수정</div>
					<button type="button" class="close" data-dismiss="modal">
						<i class="fa fa-close" aria-hidden="true"></i>
					</button>
				</div>
				<div class="modal-body">
					<div class="modal-guide-box">
						구역번호는 수정하지 않고, 구역명과 층수만 변경합니다.
					</div>

					<table class="table table-bordered table-hover table-striped">
						<tr>
							<th class="text-center" style="width:30%;">구역번호</th>
							<td>
								<input
									id="update_section"
									v-model="update.section"
									class="form-control"
									readonly="readonly">
							</td>
						</tr>
						<tr>
							<th class="text-center">구역명</th>
							<td>
								<input
									id="update_section_name"
									v-model.lazy="update.section_name"
									class="form-control"
									placeholder="구역명을 입력해주세요">
							</td>
						</tr>
						<tr>
							<th class="text-center">층수</th>
							<td>
								<input
									id="update_floor"
									v-model.lazy="update.floor"
									class="form-control"
									placeholder="층수를 입력해주세요">
							</td>
						</tr>
					</table>

					<div class="btn-area-right">
						<div class="btn btn-default margin-top" onclick="updateSection()">
							<i class="fa-regular fa-pen-to-square"></i> 수정
						</div>
						<div class="btn btn-danger margin-top" data-dismiss="modal">
							<i class="fa-solid fa-xmark"></i> 닫기
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="form_group">
</div>

<%@ include file="IncludeBottom.jsp"%>