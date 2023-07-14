<!DOCTYPE html>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page session="true"%>

<c:set var="lastUpdate" value="${System.currentTimeMillis()}"/>

<html>
<head>

	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-138883721-1"></script>
	<script>
		window.dataLayer = window.dataLayer || [];
		function gtag(){dataLayer.push(arguments);}
		gtag('js', new Date());
		gtag('config', 'UA-138883721-1');
	</script>
	
	<title>그린동 P5 기본 관리 페이지</title>
	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
	<meta charset="utf-8">
	<meta http-equiv="Expires" content="-1">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	
	<script type="text/javascript" src='js/jquery-1.12.4.min.js'></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
			integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
			crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
			integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
			crossorigin="anonymous"></script>		
	<script type="text/javascript" src="js/bootstrap-table.js"></script> 
	<script type="text/javascript" src="js/bootstrap-table-ko-KR.js"></script>
	<script src="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/js/bootstrap4-toggle.min.js"></script>
	
	<link href="https://cdn.jsdelivr.net/gh/gitbrent/bootstrap4-toggle@3.6.1/css/bootstrap4-toggle.min.css" rel="stylesheet">
	<link rel="stylesheet" href="css/bootstrap4.1/bootstrap.css" type="text/css" media="screen">
	<link rel="stylesheet" href="css/bootstrap-table.css">
	<link rel="stylesheet" href="css/font-awesome.min.css">
	<link href="fontawesome/css/all.css" rel="stylesheet">
	
	<script type="text/javascript" src="js/manage/default.js?s=1"></script>
	<link rel="stylesheet" href="css/manage/common.css?s=1"/>
	
	<script type="text/javascript">     
	
		const CUR_SITE_ID = 17;
		const SCANNER_MAC_LENGTH = 17;
		    
		$(document).ready(function() {
	
			$('.toggle-btn').click(function() {$(this).toggleClass('active').siblings().removeClass('active');});
			$('#pills-tab a').click(function(e) { e.preventDefault(); $(this).tab('show');});
			$("ul.nav-pills > li > a").on("shown.bs.tab", function(e) {
				var id = $(e.target).attr("href").substr(1);
				window.location.hash = id;
			});
			var hash = window.location.hash;
			$('#pills-tab a[href="' + hash + '"]').tab('show');
			
			//update();
			
		});    
		
		function updateBtn() {
			alert("현재 BLOCK");
			//alert("데이터를 갱신합니다.");
			//update();
		}
		
	</script>

</head>

<body>

	<div class="container_wrap">	
		<div class="container" style="padding: 0; max-width: 1500px;">
			 
			<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
				<li class="nav-item" onclick="getBeaconData();">
			    	<a class="nav-link active" id="pills-location-tab" data-toggle="pill" href="#pills-location" role="tab" aria-controls="pills-location" aria-selected="true">위치파악</a>
			  	</li>
						
			  	<li class="nav-item" onclick="getNFCData();">
			    	<a class="nav-link" id="pills-nfc-tab" data-toggle="pill" href="#pills-nfc" role="tab" aria-controls="pills-nfc" aria-selected="false">작업대기</a>
			  	</li>
			 
			  	<li class="nav-item" onclick="getSensorData();">
			    	<a class="nav-link" id="pills-sensor-tab" data-toggle="pill" href="#pills-sensor" role="tab" aria-controls="pills-sensor" aria-selected="false">환경센서</a>
			  	</li>
			  	
				<li class="nav-item" onclick="getHoleData();">
			    	<a class="nav-link" id="pills-hole-tab" data-toggle="pill" href="#pills-hole" role="tab" aria-controls="pills-hole" aria-selected="false">개구부</a>
			  	</li>
			  	 
				<li class="nav-item" onclick="getBlockData();">
			    	<a class="nav-link" id="pills-rssi-tab" data-toggle="pill" href="#pills-rssi" role="tab" aria-controls="pills-rssi" aria-selected="false">데이터차단</a>
			  	</li>
			  	
			  	<li class="nav-item" onclick="getDeviceStateData();">
			    	<a class="nav-link" id="pills-scanner-tab" data-toggle="pill" href="#pills-scanner" role="tab" aria-controls="pills-scanner" aria-selected="false">장비상태</a>
			  	</li>
			
			
			</ul><!-- pills-tab END -->
	
			<div class="tab-content" id="pills-tabContent">
				<div class="tab-pane fade show active" id="pills-location" role="tabpanel" aria-labelledby="pills-location-tab">
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>				
						<div class="btn_remove" onclick="btnExitWorkers()">
							선택 항목 OUT <i class="fa-solid fa-person-walking-dashed-line-arrow-right"></i>
						</div>				
					</div> 
			        <div class="row">   
						<table id="result_locations"  data-toggle="table" data-search="false" class="col-12 table">
							<thead>
								<tr class="box-shadow">
									<th data-checkbox="true" class="text-center">체크</th>
									<th data-field="section_no" data-sortable="true"  class="text-center">수조번호</th>
			                		<th data-field="cont_name" class="text-center show-web">업체</th>          
			                		<th data-field="name" class="text-center show-web">이름</th>     
			                		<th data-field="mb_idx" data-sortable="true" class="text-center">비콘번호</th>
			                		<th data-field="beacon_mac" data-sortable="true" class="text-center">맥어드레스</th>		            
									<th data-field="start_time" class="text-center show-web">출입시간</th>
			                 		<th data-field="work_min" class="text-center show-web">작업시간</th>
			                		<th data-field="rssi" class="text-center">Rssi</th>
			                		<th data-field="time_diff_min" data-sortable="true" class="text-center">Delay</th>
			                 		<th data-field="last_update_time" data-sortable="true" class="text-center show-web">수집시간</th>
									<th data-field="change_start_time" class="text-center">시간 변경</th>
			                  		<th data-field="column1" class="text-left show-mobile">업체명 | 이름</th>
								</tr>
							</thead>
						</table>
			        </div>
			  	</div>
	 		  
				<div class="tab-pane fade" id="pills-nfc" role="tabpanel" aria-labelledby="pills-nfc-tab">
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>
						<div class="btn_remove" onclick="btnWaitOutWorkers()">
							선택 항목 OUT <i class="fa-solid fa-person-walking-dashed-line-arrow-right"></i>
						</div>
					</div>
			        <div class="row">   
						<div>        
							<table id="result_nfc"  data-toggle="table" data-search="false" class="col-12 table">
								<thead>
									<tr class="box-shadow">
										<th data-checkbox="true" class="text-center">체크</th>							
										<th data-field="section_no" data-sortable="true" class="text-center">수조번호</th>
				                		<th data-field="cont_name" class="text-center show-web">업체</th>          
				                		<th data-field="name" class="text-center show-web">이름</th>     
				                		<th data-field="nfc_idx" data-sortable="true" class="text-center">NFC번호</th>		            
										<th data-field="serial_number" data-sortable="true" class="text-center">시리얼넘버</th>	
										<th data-field="start_time" class="text-center show-web">태깅시간</th>			               
				                		<th data-field="work_min" data-sortable="true" class="text-center">대기시간</th>						
				                  		<th data-field="column1" class="text-left show-mobile">업체명 | 이름</th>
									</tr>
			               		</thead>
			          		</table>
			          	</div>
			        </div>
				</div>
					 
				
				<div class="tab-pane fade" id="pills-sensor" role="tabpanel" aria-labelledby="pills-sensor-tab">
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>
						<div class="btn_remove" onclick="deleteSensorData()">
							선택 항목 삭제 <i class="fa-solid fa-trash-can"></i>
						</div>	
					</div>
			        <div class="row">   
						<div>        
							<table id="result_sensor"  data-toggle="table" data-search="false" class="col-12 table">
								<thead>
									<tr>								
										<th data-checkbox="true"  class="text-center">체크</th>
										<th data-field="section_no" data-sortable="true" class="text-center">수조번호</th>
										<th data-field="section_name" data-sortable="true" class="text-center">구역이름</th>
										<th data-field="o2" class="text-center">산소</th>          
										<th data-field="co2" class="text-center">이산화탄소</th>
										<th data-field="co" class="text-center">일산화탄소</th>
										<th data-field="h2s" class="text-center">황화수소</th>                  
										<th data-field="lel" class="text-center">가연성가스<br></th>                    
										<th data-field="time_diff_min" data-sortable="true" class="text-center">Delay</th>
										<th data-field="write_time" data-sortable="true" class="text-center">수집시간</th>		                  		
									</tr>
			               		</thead>
			          		</table>
			          	</div>
			        </div>
				</div>		
				
				<div class="tab-pane fade" id="pills-hole" role="tabpanel" aria-labelledby="pills-hole-tab">
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>
					</div>       
			        <div class="row">   
						<div>        
						<table id="result_hole" data-toggle="table" data-search="false" class="col-12 table">
							<thead>
			            		<tr>		            			
									<th data-field="section_no" data-sortable="true"  class="text-center">수조번호</th>		                      
			                		<th data-field="hole_name" class="text-center show-web">별칭</th>     
			                		<th data-field="idx" data-sortable="true" class="text-center">개구부 비콘번호</th>
			                		<th data-field="beacon_mac" data-sortable="true" class="text-center">맥어드레스</th>		            
									<th data-field="state" class="text-center show-web">상태</th>
			                		<th data-field="rssi" class="text-center">Rssi</th>
			                		<th data-field="time_diff_min" data-sortable="true" class="text-center">Delay</th>
			                 		<th data-field="write_time" data-sortable="true" class="text-center show-web">수집시간</th>
			               		</tr>
							</thead>
			          	</table>
			          	</div>
					</div>
				</div>
			
				<div class="tab-pane fade" id="pills-rssi" role="tabpanel" aria-labelledby="pills-rssi-tab">
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>				
					</div> 
					<div class="row">   
						<div>        
			           	<table id="result_rssi" data-toggle="table"  data-search="true" data-pagination="true"
							data-page-size="100" data-page-list="[10, 20, 50, All]" data-filter-control="true" 
				 			class="table table-bordered col-12 table-hover table-striped" >
				 			<thead>
			              		<tr>
			                  		<th data-field="section_no" data-sortable="true" class="text-center">수조번호</th> 
									<th data-field="type" data-sortable="true" class="text-center">위치</th>
			                  		<th data-field="phone_idx" data-sortable="true" class="text-center">장비구분번호</th>
			                  		<th data-field="cont_name" class="text-center show-web">업체</th>
			                  		<th data-field="name" class="text-center show-web">이름</th>
			                  		<th data-field="idx" data-sortable="true" class="text-center">비콘번호</th>
			                  		<th data-field="beacon_rssi" class="text-center">비콘보정치</th>
			                  		<th data-field="section_rssi" class="text-center">수조스캔범위</th>               
			                  		<th data-field="write_time" data-sortable="true" class="text-center">수집시간</th>  
			                  		<th data-field="modify_rssi_add" class="text-center">보정치수정</th>
								</tr>
							</thead>
						</table>
						</div>
					</div>
				</div>
			
				<div class="tab-pane fade" id="pills-scanner" role="tabpanel" aria-labelledby="pills-scanner-tab">
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>
						<div class="btn_remove" onclick="deleteDeviceStateData()">
							선택 항목 삭제 <i class="fa-solid fa-trash-can"></i>
						</div>
					</div>       
			        <div class="row">   
						<div>        
						<table id= "result_scanner" data-toggle="table" data-search="false" class="col-12 table">
							<thead>						
			            		<tr>		                  		
	 		                  		<th data-checkbox="true" class="text-center">체크</th>
			                  		<th data-field="section_no" data-sortable="true" class="text-center">수조번호</th>
			                  		<th data-field="section_name" data-sortable="true" class="text-center">구역이름</th>
									<th data-field="type" data-sortable="true" class="text-center">위치</th>
									<th data-field="model" class="text-center">장비종류</th>
									<th data-field="app_ver" class="text-center">앱버전</th>
			                  		<th data-field="device_no" data-sortable="true" class="text-center">장비구분번호</th>
			                  		<th data-field="scan_count" class="text-center">총스캔갯수</th>		                  		
			                  		<th data-field="time_diff_min" class="text-center">Delay</th>
			                  		<th data-field="write_time" class="text-center">수집시간</th>
			               		</tr>
							</thead>
			          	</table>
			          	</div>
			        </div>
			  	</div>	
			</div><!-- tab-content END -->
		</div><!-- container END -->
	</div><!-- container_wrap END -->

</body>
</html>
