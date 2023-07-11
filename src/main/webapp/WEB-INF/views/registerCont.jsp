<%@ include file="IncludeTop.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>


$(document).ready(function(){
	init();	
});

function init() {	
	var update = ${update};
	
	if(!update) {
		$("#content_title").text("업체 등록");
	}
	else {
		$("#content_title").text("업체 수정");
	}
}

function eventComplete() {
	
	var update = ${update};
	var form = document.forms["contDataForm"];
	var name = $('#name').val();
	var type = $('#type').val();
	var reqnum = $('#reqnum').val();
	var phone =$('#phone').val();
	var rep_name =$('#rep_name').val();
	
	if( name.trim().length <= 0) {
		alert(" 업체명을 입력해주세요. ");
		return;
	}
	
	if(!update) {
		form.action = "insertCont";
	}
	else {
		form.action = "updateCont";
	}
	
	form.submit();
}

 
function disableCont(){
	var input = confirm('업체를 삭제하시겠습니까? 삭제시 업체에 속한 사용자는 더이상 계정을 이용할수 없습니다.');
	if(input) { //yes
		$('#contDataForm').attr('action', 'disableContractor');
		$('#contDataForm').submit();
	}
}

</script>

<style>

#regContTable {
	width: 100% !important;
}

.table-container {
    max-width: 800px;
    width: 100%;
}

.content_table_box  {
	display: flex;
    align-items: center;
    flex-direction: column;
    align-content: flex-start;
    justify-content: center;
    flex-wrap: nowrap;
}

#regContTable th {
	min-width: 150px;
	background: #FFF;
}

#regContTable th.required {
	color: #FF3547;
	font-weight: 600;		
}

#regContTable td input, #regContTable td select{
	height: 40px;
	font-size: 0.9em;	
	max-width: 100% !important;
	background: #FFF;
}

#regContTable .size-fix {
    max-width: 250px;
    max-height: 250px;
    border: 1px solid #D4D4D4;
    margin-bottom: 10px;
}

#regContTable .file-input-hidden {
    position: absolute;
    top: 0;
    left: 0;
    opacity: 0;
    width: 100%;
    background: red;
}

#regContTable .img_btn_wrapper {
	height: 40px;
    width: 100%;
    margin-top: 10px;
}

#regContTable .img_btn_box {
    height: 100%;
    min-width: 100px;    
	max-width: 120px;
    flex: 0 0 40px;
    box-shadow: 0 0 12px 0 rgb(0 0 0 / 10%);
    border-radius: 5px;
    font-size: 1em;
    color: #FFF;
    float: right;
    text-align: center;
    line-height: 40px;
    margin-left: 5px;
    padding: 0;
    position: relative;
    cursor: pointer;
}

#regContTable #cont_type_box {
	display: flex;
    flex-direction: row;
    align-items: stretch;
    justify-content: space-between;
    border: none;
}

#regContTable #cont_type_box .btn.active {
	background: #ff3547 !important;
	font-weight: 600;
} 

#regContTable #cont_type_box .btn{
    height: 40px;
    float: left;
    width: 19%;
    margin: 0 0.5%;
    font-size: 0.8em;
    line-height: 40px;
    padding: 0;    
    height: 40px;
}

</style>

<form:form id="contDataForm" method="POST" class="form-horizontal" modelAttribute="cont" >

<div id="content-wrapper">
	<div id="content_title" class="content-item"></div>
	<form:input type="hidden" path="site_id" value="${cont.site_id}"/>
	<form:input type="hidden" path="id" value="${cont.id}"/>
		
	<div class="content_button_box content-item" >
		<div class="btn btn-primary" onclick="history.back(-1)"><i class="fa-solid fa-arrow-rotate-left"></i> 취소</div>	   
		<c:choose>
		<c:when test="${!update}"> 		      
			<div class="btn btn-default" onclick="eventComplete()"><i class="fa-regular fa-registered"></i> 등록</div>
		</c:when>
		<c:otherwise>
			<div class="btn btn-danger" onclick="disableCont()"><i class="fa-solid fa-trash"></i> 삭제</div>		
			<div class="btn btn-default" onclick="eventComplete()"><i class="fa-regular fa-pen-to-square"></i> 수정</div>
		</c:otherwise>	
		</c:choose>
	</div>
		
	<div class="content_table_box content-item">	
	
		<div class="table-container" >
	   		
	   		<table id="regContTable"	 			
				class="reg-table table table-bordered col-xs-12 table-hover">		
				<tr>
					<th class="text-center required">업 체 명</th>
					<td>
						<form:input type="text" maxlength="50" path="name" class="form-control" id="name" value="${cont.name}" placeholder="ex) 삼성엔지니어링"/>
					</td>
				</tr>
				
				<tr>
					<th class="text-center required">업 체 구 분</th>
					<td id="cont_type_box"> 
						<c:choose>
		                   	<c:when test="${update}">
								<form:input type="hidden" path="type" id="type3" value="${cont.type}" /><!-- 수정용 -->
								<c:if test="${cont.type == 0}">시공사</c:if>
								<c:if test="${cont.type == 1}">협력사</c:if>
								<c:if test="${cont.type == 2}">감리단</c:if>	                         
								<c:if test="${cont.type == 3}">발주처</c:if>
								<c:if test="${cont.type == 4}">기타</c:if>	
		                   	</c:when>	                   	
		                   	<c:otherwise>
								<div data-toggle="buttons" style="width: 100% !important;">
									<label class="btn btn-default active" id="option0parent"  >
										<form:radiobutton path="type" id="type0" value="0" autocomplete="off" style="display: none !important;" />원청사
									</label>
									
									<label class="btn btn-default" id="option1parent" >
										<form:radiobutton  path="type" id="type1" value="1" autocomplete="off"  style="display: none !important;"/>협력사
									</label>
					                            
									<label class="btn btn-default" id="option2parent" >
										<form:radiobutton  path="type" id="type2" value="2" autocomplete="off" style="display: none !important;" />감리단
									</label>
			
									<label class="btn btn-default" id="option3parent" >
										<form:radiobutton  path="type" id="type3" value="3" autocomplete="off" style="display: none !important;" />발주처
									</label>
									
									<label class="btn btn-default" id="option4parent" >
										<form:radiobutton  path="type" id="type4" value="4" autocomplete="off" style="display: none !important;" />기타
									</label>
								</div>
		                   	</c:otherwise>	                  
                   		</c:choose>
					</td>
				</tr>
				<tr>
					<th class="text-center">사업자 번호</th>
					<td>
						<form:input type="text" maxlength="20"  path="reg_num" class="form-control" id="reg_num" value="${cont.reg_num}" placeholder="ex) 000-00-00000"/>
					</td> 
				</tr>
				<tr>
					<th class="text-center">사무실 전화번호</th>					
					<td>
						<form:input type="text" maxlength="12"  path="phone" class="form-control" id="phone" value="${cont.phone}" placeholder="ex) 02-000-0000 | 010-0000-0000"/>
					</td>	
				</tr>
				<tr>
					<th class="text-center">대 표 자</th>
					<td>
						<form:input type="text" maxlength="20" path="rep_name" class="form-control" id="rep_name" value="${cont.rep_name}" placeholder="ex) 박민호"/>
					</td>	
				</tr>
				<tr>
					<th class="text-center required">대표 작업 공정</th>
					<td>
						<form:select id="state" path="state" class="form-control" >							
							<c:forEach var="vo" items="${sList}" varStatus="idx">
	     						 <form:option value="${vo.id}">${vo.name}</form:option>
							</c:forEach>
						</form:select>
					</td>
				</tr>				
			</table>			
		</div>
		
	</div>	
</div>

</form:form>

<%@ include file="IncludeBottom.jsp"%>
