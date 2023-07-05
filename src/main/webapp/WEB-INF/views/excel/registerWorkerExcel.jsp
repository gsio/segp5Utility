<%@ include file="../IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.0.3/vue.js"></script>
<script type="text/javascript" async="" src="http://www.google-analytics.com/ga.js"></script>
<script src="js/util/excel/shim.js"></script>

<script type="text/vbscript" language="vbscript"> 
	IE_GetProfileAndPath_Key = "HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Explorer\User Shell Folders\"
	Function IE_GetProfileAndPath(key): Set wshell = CreateObject("WScript.Shell"): IE_GetProfileAndPath = wshell.RegRead(IE_GetProfileAndPath_Key & key): IE_GetProfileAndPath = wshell.ExpandEnvironmentStrings("%USERPROFILE%") & "!" & IE_GetProfileAndPath: End Function
	Function IE_SaveFile_Impl(FileName, payload): Dim data, plen, i, bit: data = CStr(payload): plen = Len(data): Set fso = CreateObject("Scripting.FileSystemObject"): fso.CreateTextFile FileName, True: Set f = fso.GetFile(FileName): Set stream = f.OpenAsTextStream(2, 0): For i = 1 To plen Step 3: bit = Mid(data, i, 2): stream.write Chr(CLng("&h" & bit)): Next: stream.Close: IE_SaveFile_Impl = True: End Function
</script>
<script type="text/vbscript" language="vbscript">
	Function IE_LoadFile_Impl(FileName): Dim out(), plen, i, cc: Set fso = CreateObject("Scripting.FileSystemObject"): Set f = fso.GetFile(FileName): Set stream = f.OpenAsTextStream(1, 0): plen = f.Size: ReDim out(plen): For i = 1 To plen Step 1: cc = Hex(Asc(stream.read(1))): If Len(cc) < 2 Then: cc = "0" & cc: End If: out(i) = cc: Next: IE_LoadFile_Impl = Join(out,""): End Function
</script>
<script src="https://unpkg.com/xlsx/dist/xlsx.full.min.js"></script>

<style>
	#drop{
	    border: 2px dashed #bbb;
	    border-radius: 5px;
	    font-size: 25px;
	    text-align: center;
	    color: #bbb;
	    min-height: 150px;
	    line-height: 3;
	}
	a {
	    text-decoration: none !important;
	}
	
	.btn-excel { 
	    width: 160px !important;
	    min-width: 160px !important;
	    flex: 0 0 160px;
	    box-shadow: 0 0 12px 0 rgb(0 0 0 / 10%);
	    border: 1px solid #A2A2A2 !important;
	    font-size: 16px;
	    font-weight: bold;
	    text-align: center;
		border-radius: 10px;
	}
	
	.btn-upload {
		width: 180px !important;
	    min-width: 180px !important;
	    flex: 0 0 40px;
	    font-size: 16px;
	    font-weight: bold;
	}
	
	#content-wrapper .content-item {
	    margin: 10px 0;
	}
	
	#content-wrapper .info-content {
	    display: flex;
	    justify-content: space-between;
	    flex-wrap: nowrap;
	}
	
	#content-wrapper .info-content .info {
		font-size: 12px;
		font-weight: bold;
		color: #FF3547;
	}
	
	.content_selete_box {
		display: flex;
	   	height: 100%;
	}
	
	#tableWrap {
		width: 100% !important;
    	overflow: auto;
    	padding: 10px;
	}
	
	#tableWrap ::-webkit-scrollbar {
	    height: 5px;
	    background-color: #ced4da;
	}	
	
	#tableWrap th{	    
	    font-size: 0.8em;
	}
	
	#tableWrap td{
		cursor:pointer;
		text-align:center;
	}
	
	#tableWrap td .form-control{
		min-width: 110px !important;
		font-size: 0.8em;
	}
	
	#app {
	    background: white;
    	min-width: 1500px;
	}

	#tableWrap th.required {
	    color: #FF3547;
	    font-weight: 600;
	}
	
	#photo-wrap {
		display: flex;
	    flex-direction: column;
	    align-items: center;
	    justify-content: space-between;
	}
	
	#photo-wrap .file_name {
		margin-top: 10px;
	}
	
	#confirmBtn, #resetBtn  {
		display: none;
	}
	
	
</style>

<div id="content-wrapper">

	<input type="checkbox" name="useworker" checked="" style="display:none">
	<input type="checkbox" name="userabs" checked="" style="display:none">	

	<div id="drop">엑셀 양식 다운로드 후 <br /> 작성된 파일을 끌어오세요</div>
	<input type="file" name="xlfile" id="xlf" style="display:none">	
	
	<form:form id="workerForm" action="insertWorkerExcel" class="form-horizontal" method="POST"	
		enctype="multipart/form-data" modelAttribute="workerVO" autocomplete="off">
    	<form:input path="site_id" style="display:none"></form:input>    	
		<form:input id="uuid" path="uuid" style="display:none" value=""></form:input>    	  

    <div class="content_button_box content-item info-content" >
    	<span class="info">빨간색 항목은 필수 입력값</span>
		<a class="btn-excel" href="./file/register_form_230207.xlsx"> 엑셀 양식 다운로드</a>
	</div>

	<div class="content_selete_box content-item">
		<c:choose>
			<c:when test="${sessionScope.userLoginInfo.cont_type == 0}"> 		      
				<span class="select-title">업체:</span>
				<form:select path="cont_id" class="form-control select-content">
					<c:forEach var="cont" items="${contList}" varStatus="idx">
					<form:option value="${cont.id}">${cont.name}</form:option>
					</c:forEach>	  
				</form:select>
			</c:when>
			<c:otherwise>
				<span class="select-title">업체:</span> 	
				<form:select path="cont_id" class="form-control select-content">				
					<form:option value="${sessionScope.userLoginInfo.cont_id}">${sessionScope.userLoginInfo.cont_name}</form:option>
				</form:select>
				<input id="cont_id" type="hidden" name="cont_id" value="${sessionScope.userLoginInfo.cont_id}"/>
			</c:otherwise>	
		</c:choose>
	</div>
	
	
		    
	<div id="tableWrap" class="content_table_box content-item">
		<div id="app">
			<table id="excelTable" class="table table-bordered text-center">
				<thead>
					<tr>									
						<th class="text-center">근로자 사진 파일명<br>(확장자: png, jpg)<br>EX) 홍길동.png</th> 
						<th class="text-center required">성 명</th>
		   				<th class="text-center required">주민번호<br>(외국인번호)<br>앞6자리</th>
		   				<th class="text-center required">성별</th>
						<th class="text-center required">채용일</th>
		   				<th class="text-center required">핸드폰번호</th>
		   				<th class="text-center">외국인여부</th>
		   				<th class="text-center">여권번호<br>(외국인)</th>
		   				<th class="text-center">국적<br>(외국인)</th>
		   				<th class="text-center required">신규교육일자</th>
						<th class="text-center">밀폐교육<br>(1차)</th>
		   				<th class="text-center">밀폐교육<br>(2차)</th>
		   				<th class="text-center">밀폐교육<br>(3차)</th>
		   				<th class="text-center">밀폐교육<br>(4차)</th>
		   				<th class="text-center required">직 종</th>						
		   				<th class="text-center">혈액형<br>(선택사항)</th>	  
					</tr>
				</thead>
				<tbody> 
					<tr v-for="(row, index) in rows">										
						<td id="photo-wrap">
							<img v-bind:id="'worker_photo_url_' + index"  v-bind:value="row.photo"
							class="photo"  src="" style="width: 80px; height: 100px;" onerror="this.src='images/noimage.png'">			
							<input class="upfile-image form-control file_name" v-bind:name="'workerList[' + index + '].photo'" v-bind:value="row.photo"/>		
						</td>
						<td>
							<input v-bind:id="'worker_name_' + index" v-bind:name="'workerList[' + index + '].name'" 
								v-bind:value="row.name" class="form-control"  placeholder="EX) 홍길동" />
						</td>
						<td>						
							<input 
								v-bind:id="'worker_jumin_' + index"  v-bind:name="'workerList[' + index + '].jumin'" v-bind:value="row.jumin"
			   					onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' 
			   					maxlength="6" class="form-control"  placeholder="EX) 900101" />
						</td>
						<td>
							<select v-bind:name="'workerList[' + index + '].jumin_back'" v-bind:value="row.jumin_back" class="form-control" placeholder="EX) 남">
			   					<option value="1">남</option>
			   					<option value="2">여</option>		   				
			   				</select>
						</td>
						<td>
							<input v-bind:id="'worker_hiredate_' + index" v-bind:name="'workerList[' + index + '].hiredate'" 
							 v-bind:value="row.hiredate" class="form-control"  placeholder="EX) 2023-01-01" />
						</td> 
						<td>
							<input v-bind:id="'worker_phone_' + index"  v-bind:name="'workerList[' + index + '].phone'" v-bind:value="row.phone"
			   				onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' maxlength="11"
			   				class="form-control" style="ime-mode:disabled;cursor:pointer;text-align:center;" placeholder="ex)010"/>
						</td>					
						<td>
							<select v-bind:name="'workerList[' + index + '].gubun'" v-bind:value="row.gubun" class="form-control">
			   					<option value="0">해당없음</option>
			   					<option value="1">해당</option>		   				
			   				</select>
						</td>
						<td>
							<input v-bind:id="'worker_passno_' + index" v-bind:name="'workerList[' + index + '].passno'"  
							v-bind:value="row.passno" class="form-control"  placeholder="ex)N0000000" />						
						</td>
						<td>
							<input v-bind:id="'worker_country_' + index" v-bind:name="'workerList[' + index + '].country'" 
							 v-bind:value="row.country" class="form-control"  placeholder="ex)중국" />
						</td>
						<td>
							<input v-bind:id="'worker_edudate_' + index" v-bind:name="'workerList[' + index + '].edudate'"  
							v-bind:value="row.edudate" class="form-control"  placeholder="EX) 2023-01-01" />
						</td>
						<td>
							<input v-bind:id="'worker_sealed_date1_' + index" v-bind:name="'workerList[' + index + '].sealed_date1'"  
							v-bind:value="row.edudate" class="form-control"  placeholder="EX) 2023-01-01" />
						</td>
						<td>
							<input v-bind:id="'worker_sealed_date2' + index" v-bind:name="'workerList[' + index + '].sealed_date2'"  
							v-bind:value="row.edudate" class="form-control"  placeholder="EX) 2023-01-01" />
						</td>
						<td>
							<input v-bind:id="'worker_sealed_date3_' + index" v-bind:name="'workerList[' + index + '].sealed_date3'"  
							v-bind:value="row.edudate" class="form-control"  placeholder="EX) 2023-01-01" />
						</td>
						<td>
							<input v-bind:id="'worker_sealed_date4_' + index" v-bind:name="'workerList[' + index + '].sealed_date4'"  
							v-bind:value="row.edudate" class="form-control"  placeholder="EX) 2023-01-01" />
						</td>					
						<td>
							 <select v-bind:id="'worker_t_id_' + index" v-bind:name="'workerList[' + index + '].t_id'" v-bind:value="row.w_id" class="form-control">
								<c:forEach var="wtype" items="${wtypeList}" varStatus="idx">
									<c:if test="${idx.index == 0}">
	   				 	 				<option value="${wtype.id}" selected="selected">${wtype.t_name}</option>
	   				 	 			</c:if>
	   				 	 			<c:if test="${idx.index != 0}">
	   				 	 				<option value="${wtype.id}" >${wtype.t_name}</option>
	   				 	 			</c:if>
								</c:forEach>
							</select> 
						</td>
						<td>
							<select v-bind:name="'workerList[' + index + '].btype'" v-bind:value="row.btype" class="form-control">			 
			   				 	<option value="A">A</option>
			   				 	<option value="B">B</option>
			   				 	<option value="O">O</option>
			   				 	<option value="AB">AB</option>    				
			   				</select>
						</td>	
					</tr>
				</tbody>
			</table>				
		</div>
	</div>
	
	</form:form>
		  
    <div class="content_button_box content-item" >		
   		<div id="resetBtn" class="btn btn-danger" onclick="resetUploadImageBtn()">RESET</div>
		<div id="uploadBtn" class="btn btn-primary btn-upload" onclick="ajaxFileUpload()"><i class="fa-solid fa-upload"></i> 사진 업로드 (다중)</div>
		<div id="confirmBtn" class="btn btn-excel" onclick="checkWorkerPhoto()"><i class="fa-regular fa-file-image"></i> 사진 확인</div>
		<div class="btn btn-default" onclick="submitWorker()"><i class="fa-regular fa-registered"></i> 등록</div>
	</div>
	
	<div id="form_group">
		<form id="ajaxForm" action="ajaxFileChange" method="POST" >
			<input multiple="multiple" type="file" id="ajaxFile" onchange="ajaxFileChange()" style="display: none;">
		</form>
	</div>

	
</div>	
<script>

	G_UUID = "";

	$(document).ready(function() {
		initVue();
	});
	
	var app ;
	function initVue() {
		app = new Vue({
			el: '#app',
			data:{
				rows : []
			}, 
			methods:{
				removeRow: function (index) {
					this.rows.splice(index, 2);
				}
			}     
		});		
	}

	function checkWorkerPhoto(){		
		$('[id^=worker_photo_url_]').each(function () {			
			$("#" + $(this).attr('id')).attr('src', './upload_img?virtname=WORKER/' + G_UUID + "_" + $(this).val());
		});
		alert("근로자 사진이 반영이 되지 않는 경우 \n하단 Reset 버튼을 누러주세요");
	}
	
	function submitWorker() {
		
		var input;
		
		input = confirm(' 등록하시겠습니까? \n[사진 업로드가 필요할 시 사진 확인까지 진행해주세요]');		
		
		if(input) {				
			var isOk = checkDetail();
			
			if(isOk === true){
				$('#uuid').val(G_UUID);
				$('#workerForm').submit();
			}
			
			else{
				alert('항목을 다시 확인해주시기 바랍니다.');
			}
			
		}
		else {
			return;
		}
		
	}
	
	
	function checkDetail() {
		var isOk = true;
		$('[id^=worker_name_]').each(function () {
			var this_id = $(this).attr('id');
			var this_name = $(this).val();			
			
			if(this_name.length >= 1 && this_name != null) {
				
				var id = this_id.split('worker_name_')[1];		
				
				var phone =	$('#worker_phone_' + id).val();			
				
			   	var rgEx = /(0[1789][016789])(\d{4}|\d{3})\d{4}$/g;			   
			   	
			   	var chk_phone = rgEx.test(phone);   
			   	
			   	if(!chk_phone || phone.length < 10){
					console.log("올바른 휴대폰번호가 아닙니다.");
				   	$('#worker_phone_' + id).css('border','2px solid red');
				   	isOk = false;
				}
			   	
				else {
					$('#worker_phone_' + id).css('border','1px solid #cccccc');					
				  	var result = checkDuplicatePhone(phone);				  	
				  	if(result >= 0){
				  		alert("현장 내 같은 전화번호 [" + phone + "] 가 존재합니다.")
						$('#worker_phone_' + id).css('border','2px solid red');
						isOk = false; 
				  	}
				}	
			   
				var jumin =	$('#worker_jumin_' + id).val();		   
				if(jumin.length < 6){
				   isOk = false;
				   $('#worker_jumin_' + id).css('border','2px solid red');
				}
				else{			 
				   $('#worker_jumin_' + id).css('border','1px solid #cccccc');
				}
				
				var hire_date = $('#worker_hiredate_' + id).val();  
				if(hire_date.length > 0) {
					var rgEx_date2 = /^\d{4}-[01][0-9]-[0123][0-9]$/g;
					var chk_date2 = rgEx_date2.test(hire_date);   
					if(!chk_date2  || hire_date.length < 10){
						isOk = false;
						$('#worker_hiredate_' + id).css('border','2px solid red');
					}
					else {
						$('#worker_hiredate_' + id).css('border','1px solid #cccccc');
					}				   
				}
				else {
					isOk = false;
					$('#worker_hiredate_' + id).css('border','2px solid red');
				}
				
			   
				var edu_date = $('#worker_edudate_' + id).val();
				if(edu_date.length > 0) {
				   	var rgEx_date = /^\d{4}-[01][0-9]-[0123][0-9]$/g;				   
				   	var chk_date = rgEx_date.test(edu_date);   
					if(!chk_date || edu_date.length < 10){
						isOk = false;
						$('#worker_edudate_' + id).css('border','2px solid red');
					}
					else{
						$('#worker_edudate_' + id).css('border','1px solid #cccccc');
					}
				}
				else {
					isOk = false;
					$('#worker_edudate_' + id).css('border','2px solid red');
				}
				
				var t_id = $('#worker_t_id_' + id).val();
				if(t_id != null) {
					$('#worker_t_id_' + id).css('border','1px solid #cccccc');
				}
				else {
					isOk = false;
					$('#worker_t_id_' + id).css('border','2px solid red');
				}				
			}	 
			else {
				isOk = false;
			   	$('#worker_name_0').css('border','2px solid red');
			}
		});
		return isOk;
	}
	
	function checkDuplicatePhone(phone, msg_div_id){
		 var result = -1;
		 $.ajax({
				type : "GET",
				url : "json/checkWorkerPhone",
				data : {
					"phone" : phone
				},
				cache : false,
				async : false,
				success : function(json,status) {
					var json_data  = JSON.parse(json);
					var cur_site_id = '${userLoginInfo.site_id}';
					var cur_cont_id = $('#cont_id').val();
					
					if(json_data.result == 'true'){
						for(var i = 0 ; i < json_data.item.length ; i ++){
							var worker_site_id = json_data.item[i].site_id;
							var worker_cont_id = json_data.item[i].cont_id;
							var worker_cont_name = json_data.item[i].cont_name;
							var worker_site_name = json_data.item[i].site_name;
							var worker_name = json_data.item[i].name;
							
							if(worker_cont_id == cur_cont_id){
								alert('[' + phone  + ']' +  worker_cont_name + '업체에서 이미 등록된 핸드폰번호입니다');	
								result = -99;
								break;
							}
							else{						
								result = worker_site_id;
							}
						}
					}
					else{
						result = -1;
					}
				}
		 });
		 
		 return result;
	}
	
	function ajaxFileUpload() {
		
		var tableCnt = $("#excelTable tbody tr").length;
		
		if(tableCnt > 0) {
			$('#ajaxFile').click();	
		}
		else {
			alert("엑셀 파일을 먼저 옮겨주세요");
			return;
		}
		
	}
	
	function ajaxFileChange() {
		var form =  $("ajaxFrom")[0];
		var formData = new FormData(form);		
		var fileList = $("#ajaxFile")[0].files;
		
		for(var i=0; i<fileList.length; i++) {
			formData.append("files", $("#ajaxFile")[0].files[i]);
		}		
		
		if(fileList.length > 0) {
			$.ajax({
				url: './uploadWorkerImg',
				processData: false,
				contentType: false,
				data: formData,
				async: false,
				type: 'POST',
				success: function(json, status) {
					var json_data  = JSON.parse(json);
					if(json_data.result =="true"){
						G_UUID = json_data.uuid;
						$('#uploadBtn').css('display','none');
						$('#confirmBtn').css('display','inline-block');
						$('#resetBtn').css('display','inline-block');
					}
					else {
						alert("사진 업로드 실패!");
						$('#confirmBtn').css('display','none');
					}				
				}
			});	
		}
		else {
			alert("업로드를 취소하였습니다.");
			$('#confirmBtn').css('display','none');			
			return;
		}	    
	}
	
	function resetUploadImageBtn() {
		$('#resetBtn').css('display','none');
		$('#confirmBtn').css('display','none');
		$('#uploadBtn').css('display','inline-block');
	}
	
</script>
	
	
<script>
	var X = XLSX;
	var XW = {
		msg: 'xlsx',		
		worker: 'js/util/excel/xlsxworker.js'
	};
		
	var global_wb;
		
	var process_wb = (function() {
		var OUT = document.getElementById('out');
		var HTMLOUT = document.getElementById('htmlout');
		
		var get_format = (function() {
			var radios = document.getElementsByName( "format" );
			return function() {
				for(var i = 0; i < radios.length; ++i) if(radios[i].checked || radios.length === 1) return radios[i].value;
			};
		})();
		
		var to_json = function to_json(workbook) {
			var result = {};
			workbook.SheetNames.forEach(function(sheetName) {
				var roa = X.utils.sheet_to_json(workbook.Sheets[sheetName], {header:1});
				if(roa.length) result[sheetName] = roa;
			});
			return JSON.stringify(result, 2, 2);
		};
			
		return function process_wb(wb) {
			global_wb = wb;
			var output = "";			
			var json_output = to_json(wb);
				
			var key_array = ['name', 'jumin', 'gender', 'hiredate', 'phone', 'foreign', 'passno', 'country', 'edudate', 
				'sealed_date1', 'sealed_date2', 'sealed_date3', 'sealed_date4', 'w_name', 'w_id', 'photo', 'btype'];
			
				
			var json_data  = JSON.parse(json_output);
			var sheet_rows = json_data["근로자"]
				
			var array = new Array();
			for(var i = 0; i < sheet_rows.length; i ++){
				
				var row = sheet_rows[i];
				
				if(i == 0 || i == 1) continue;				
				
				if(row[0] == null || row[0].length <= 0 ) continue;
				
				var obj = [];
				
				for(var j=0;  j < row.length; j++){
					obj[key_array[j]] = row[j];
				}
					array.push(obj);
				} 
			
				app.rows = array;
		
				for(var i = 0; i < app.rows.length; i ++){
					
					if(app.rows[i].gender == '남'){
						app.rows[i].jumin_back = 1
					}
					else{
						app.rows[i].jumin_back = 2
					}
					
					if(app.rows[i].foreign == 'X'){
						app.rows[i].gubun = 0
					}
					else{
						app.rows[i].gubun = 1
					}
				}
			};
		})();
		
		var setfmt = window.setfmt = function setfmt() { if(global_wb) process_wb(global_wb); };
		
		var b64it = window.b64it = (function() {
			var tarea = document.getElementById('b64data');
			return function b64it() {
				var wb = X.read(tarea.value, {type:'base64', WTF:false});
				process_wb(wb);
			};
		})();
		
		var do_file = (function() {
			var rABS = typeof FileReader !== "undefined" && (FileReader.prototype||{}).readAsBinaryString;
			var domrabs = document.getElementsByName("userabs")[0];
			if(!rABS) domrabs.disabled = !(domrabs.checked = false);
		
			var use_worker = typeof Worker !== 'undefined';
			var domwork = document.getElementsByName("useworker")[0];
			if(!use_worker) domwork.disabled = !(domwork.checked = false);
		
			var xw = function xw(data, cb) {
				var worker = new Worker(XW.worker);
				worker.onmessage = function(e) {
					switch(e.data.t) {
						case 'ready': break;
						case 'e': console.error(e.data.d); break;
						case XW.msg: cb(JSON.parse(e.data.d)); break;
					}
				};
				worker.postMessage({d:data,b:rABS?'binary':'array'});
			};
		
			return function do_file(files) {
				rABS = domrabs.checked;
				use_worker = domwork.checked;
				var f = files[0];
				var reader = new FileReader();
				reader.onload = function(e) {
					if(typeof console !== 'undefined') {
						//console.log("onload", new Date(), rABS, use_worker);
					}
					var data = e.target.result;
					if(!rABS) data = new Uint8Array(data);
					if(use_worker) xw(data, process_wb);
					else process_wb(X.read(data, {type: rABS ? 'binary' : 'array'}));
				};
				if(rABS) reader.readAsBinaryString(f);
				else reader.readAsArrayBuffer(f);
			};
		})();
		
		(function() {
			var drop = document.getElementById('drop');
			if(!drop.addEventListener) return;
		
			function handleDrop(e) {
				e.stopPropagation();
				e.preventDefault();
				do_file(e.dataTransfer.files);
			}
		
			function handleDragover(e) {
				e.stopPropagation();
				e.preventDefault();
				e.dataTransfer.dropEffect = 'copy';
			}
		
			drop.addEventListener('dragenter', handleDragover, false);
			drop.addEventListener('dragover', handleDragover, false);
			drop.addEventListener('drop', handleDrop, false);
		})();
		
		(function() {
			var xlf = document.getElementById('xlf');
			if(!xlf.addEventListener) return;
			function handleFile(e) { do_file(e.target.files); }
			xlf.addEventListener('change', handleFile, false);
		})();
			var _gaq = _gaq || [];
			_gaq.push(['_setAccount', 'UA-36810333-1']);
			_gaq.push(['_trackPageview']);
		
			(function() {
				var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
				ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
				var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
			})();
	</script>
