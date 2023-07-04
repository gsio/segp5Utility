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
	
	<title>그린동 P4 상세 관리 페이지</title>
	
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
	
	<script type="text/javascript" src="js/manage/detail.js?s=1"></script>
	<link rel="stylesheet" href="css/manage/common.css?s=1"/>
	
	<script type="text/javascript">     
	
		const CUR_SITE_ID = 16;
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
			
			update();
			
		});    
		
		function updateBtn() {
			alert("데이터를 갱신합니다.");
			update();
		}
		 
	</script>
	
</head>
	<body>
	<div class="container_wrap">	
		<div class="container" style="padding: 0; max-width: 1500px;">
			 
			<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
				<li class="nav-item" onclick="getBeaconList();">
			    	<a class="nav-link active" id="pills-beacon-tab" data-toggle="pill" href="#pills-beacon" role="tab" aria-controls="pills-beacon" aria-selected="true">Beacon</a>
			  	</li>
						
			  	<li class="nav-item" onclick="getNFCList();">
			    	<a class="nav-link" id="pills-nfc-tab" data-toggle="pill" href="#pills-nfc" role="tab" aria-controls="pills-nfc" aria-selected="false">NFC</a>
			  	</li>
			 
			  	<li class="nav-item" onclick="getHoleList();">
			    	<a class="nav-link" id="pills-hole-tab" data-toggle="pill" href="#pills-hole" role="tab" aria-controls="pills-hole" aria-selected="false">Hole</a>
			  	</li>
			  	
			  	<li class="nav-item" onclick="getSectionList();">
		    		<a class="nav-link" id="pills-section-tab" data-toggle="pill" href="#pills-section" role="tab" aria-controls="pills-section" aria-selected="false">Section</a>
		  		</li>
			  		
			  	<li class="nav-item" onclick="getScannerList();">
			    	<a class="nav-link" id="pills-scanner-tab" data-toggle="pill" href="#pills-scanner" role="tab" aria-controls="pills-scanner" aria-selected="false">Scanner</a>
			  	</li>
			
			
			</ul><!-- pills-tab END -->
	
			<div class="tab-content" id="pills-tabContent">
				<div class="tab-pane fade show active" id="pills-beacon" role="tabpanel" aria-labelledby="pills-beacon-tab">
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>				
						<div class="btn_remove" onclick="deleteBeaconData()">
							선택 비콘 삭제&nbsp;<i class="fa-solid fa-circle-minus"></i>
						</div>			
						<div class="btn_add" onclick="insertBeaconCheck()">
							비콘 추가 <span>&nbsp;<i class="fa-solid fa-circle-plus"></i></span>
						</div>	
					</div> 
			        <div class="row">   
						<table id="result_beacons" data-toggle="table" data-search="true" data-pagination="true"
							data-page-size="100" data-page-list="[10, 20, 50, All]" data-filter-control="true" 
				 			class="table table-bordered col-12 table-hover table-striped" >				 		
							<thead>
								<tr class="box-shadow">
									<th data-checkbox="true" class="text-center">체크</th>
									<th data-field="beacon_no" data-sortable="true" class="text-center">비콘번호</th>
									<th data-field="beacon_mac" data-sortable="true" class="text-center">맥어드레스</th>								
			                		<th data-field="cont_name" class="text-center show-web">업체</th>          
			                		<th data-field="wt_name" class="text-center show-web">공종</th>   
			                		<th data-field="name" class="text-center show-web">이름</th>     
									<th data-field="rssi_add" class="text-center">보정치</th>				
									<th data-field="change_beacon_mac" class="text-center">맥어드레스변경</th>
								</tr>
							</thead>
						</table>
			        </div>
			  	</div>
	 		  
				<div class="tab-pane fade" id="pills-nfc" role="tabpanel" aria-labelledby="pills-nfc-tab">
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>
						<div class="btn_remove" onclick="deleteNFCData()">
							선택 NFC 삭제&nbsp;<i class="fa-solid fa-circle-minus"></i>
						</div>	
						<div class="btn_add" onclick="insertNFCCheck()">
							NFC 추가 <span>&nbsp;<i class="fa-solid fa-circle-plus"></i></span>
						</div>	
					</div>
			        <div class="row">   
						<div>        
							<table id="result_nfc" data-toggle="table" data-search="true" data-pagination="true"
								data-page-size="100" data-page-list="[10, 20, 50, All]" data-filter-control="true" 
					 			class="table table-bordered col-12 table-hover table-striped" >	
								<thead>
									<tr>								
										<th data-checkbox="true" class="text-center">체크</th>
										<th data-field="nfc_no" data-sortable="true" class="text-center">NFC번호</th>	
										<th data-field="serial_number" data-sortable="true" class="text-center">시리얼넘버</th>
				                		<th data-field="cont_name" class="text-center show-web">업체</th>          
				                		<th data-field="wt_name" class="text-center show-web">공종</th> 
				                		<th data-field="name" class="text-center show-web">이름</th>  
										<th data-field="change_nfc_idx" class="text-center show-web">NFC번호변경</th>
									</tr>
			               		</thead>
			          		</table>
			          	</div>
			        </div>
				</div>
					 
				
				<div class="tab-pane fade" id="pills-hole" role="tabpanel" aria-labelledby="pills-hole-tab">
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>
						<div class="btn_remove" onclick="deleteHoleData()">
							선택 개구부 삭제&nbsp;<i class="fa-solid fa-circle-minus"></i>
						</div>	
						<div class="btn_add" onclick="insertHoleCheck()">
							개구부 추가 <span>&nbsp;<i class="fa-solid fa-circle-plus"></i></span>
						</div>	
					</div>
			        <div class="row">   
						<div>        
							<table id="result_hole"  data-toggle="table" data-search="false" class="col-12 table">
								<thead>
									<tr>								
									<th data-checkbox="true" class="text-center">체크</th>
									<th data-field="beacon_no" data-sortable="true" class="text-center">개구부비콘번호</th>
									<th data-field="mac_addr" data-sortable="true" class="text-center">맥어드레스</th>
									<th data-field="section_no" class="text-center show-web">수조번호</th>								
			                		<th data-field="section_name" class="text-center show-web">구역이름</th> 		
									<th data-field="change_mac_addr" class="text-center">맥어드레스변경</th>                 		
									</tr>
			               		</thead>
			          		</table>
			          	</div>
			        </div>
				</div>		

				<div class="tab-pane fade" id="pills-section" role="tabpanel" aria-labelledby="pills-section-tab">					
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>							
					</div>
			        <div class="row">   
						<table id="sectionList" data-toggle="table" data-search="false" class="col-12 table table-striped">
							<thead>
								<tr>								
									<th data-field="section" class="text-center">수조번호</th>								    
									<th data-field="sec_name" class="text-center">구역이름</th>
									<th data-field="floor" class="text-center">층수</th>        					            		
									<th data-field="section_state" class="text-center">활성화여부</th>
			                		<th data-field="sensor_delete" class="text-center">환경센서여부</th>
			                		<th data-field="push_service" class="text-center">이상센서알림여부</th>
								</tr>
							</thead>
						</table>
			        </div>
				</div>
				
				<div class="tab-pane fade" id="pills-scanner" role="tabpanel" aria-labelledby="pills-scanner-tab">					
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>							
						<div class="btn_remove" onclick="deleteScannerData()">
							선택 스캐너 삭제&nbsp;<i class="fa-solid fa-circle-minus"></i>
						</div>	
						<div class="btn_add" onclick="insertScannerCheck()">
							스캐너 추가 <span>&nbsp;<i class="fa-solid fa-circle-plus"></i></span>
						</div>	
					</div>
			        <div class="row">   
						<table id="result_scanner" data-toggle="table" data-search="false" class="col-12 table table-striped">
							<thead>
								<tr>														    
									<th data-checkbox="true" class="text-center">체크</th>
									<th data-field="section_no" class="text-center">수조번호</th>
									<th data-field="section_name" class="text-center">구역이름</th>									
									<th data-field="timeOut" class="text-center">자동삭제시간</th>        					            		
									<th data-field="waitOut" class="text-center">자동대기삭제시간</th>
			                		<th data-field="modify" class="text-center">자동삭제(Beacon/NFC)설정</th>
								</tr>
							</thead>
						</table>
			        </div>
				</div>
			  	
			</div><!-- tab-content END -->
		</div><!-- container END -->
	</div><!-- container_wrap END -->

</body>
</html>
