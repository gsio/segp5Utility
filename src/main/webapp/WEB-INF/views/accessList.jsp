<%@ include file="IncludeTop_renewal.jsp"%>
<%@ page pageEncoding="utf-8"%>

<script>

$(document).ready(function() {
	
	
	
});


</script>

<style>

#noticeTable tr {
	cursor: pointer;
}
	

</style>

<form id="image_form" method="post" enctype="multipart/form-data" action=""></form>

<div id="content-wrapper">
	<div id="content_title" class="content-item">위치정보 제공 취급대장</div>
	
	<div class="content_button_box content-item" >
		<div class="btn btn-default" onclick="registerNotice()"><i class="fa-regular fa-registered"></i> 등록</div>
	</div>		

	<div class="content_table_box content-item">
		<table id="noticeTable" data-toggle="table" data-toggle="table" data-search="true" data-pagination="true" 
			data-page-size="10" data-page-list="[10,25,All]" data-sort-name="[name]" data-filter-control="true"
			data-page-number="1" class="table table-bordered col-xs-12 table-hover table-striped">	
			
			<thead>
				<tr>
					<th class="text-center">순번</th>
					<th class="text-center show-web">대상</th>     
					<th class="text-center show-web">취득경로</th>
					<th class="text-center show-web">제공서비스</th>					
					<th class="text-center show-web">제공받는자</th>					
					<th class="text-center show-web">시간</th>
			  
				</tr>
			</thead>
			
			<tbody>         
				<tr>
					<td class="text-center">1</td>
					<td class="text-center show-web">pmh428	</td>  
				   	<td class="text-center show-web">Android</td>	
				   	<td class="text-center show-web">위치정보 조회</td>
					<td class="text-center show-web">김경류</td>
					<td class="text-center show-web">2022-08-25 11:29:45</td>				    
			
				</tr>
							<tr>
					<td class="text-center">2</td>
					<td class="text-center show-web">ksh19631</td>  
				   	<td class="text-center show-web">Android</td>			
					<td class="text-center show-web">위치정보 조회</td>
					<td class="text-center show-web">김경류</td>
					<td class="text-center show-web">2022-12-30 16:24:07</td>				    

				</tr>
							<tr>
					<td class="text-center">3</td>
					<td class="text-center show-web">pmh428	</td>  
				   	<td class="text-center show-web">Android</td>			
					<td class="text-center show-web">위치정보 처리방침 조회</td>
					<td class="text-center show-web">김경류</td>
					<td class="text-center show-web">2022-02-11 17:19:00</td>				    
	
				</tr>
							<tr>
					<td class="text-center">4</td>
					<td class="text-center show-web">elisha.lee</td>  
				   	<td class="text-center show-web">Android</td>
					<td class="text-center show-web">위치정보 조회</td>
					<td class="text-center show-web">김경류</td>
					<td class="text-center show-web">2022-02-11 17:19:50</td>				    
	
				</tr>
			</tbody>
			
		</table>
	</div>
	
</div> <!-- content-wrapper END -->

<div id="form_group">
	<form id="registerForm" action="registerNotice" method="POST" ></form>
	<form id="updateForm" action="registerNotice" method="POST" >
		<input id="notice_id" type="hidden" name="id"/>
	</form>
	<form id="viewForm" action="noticeView" method="POST" >
		<input id="v_notice_id" type="hidden" name="id"/>
	</form>	
</div>

<%@ include file="IncludeBottom_renewal.jsp"%>