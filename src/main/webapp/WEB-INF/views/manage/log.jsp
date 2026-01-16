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
	
	<title>그린동 P5 로그 페이지</title>
	
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
	
	<script type="text/javascript" src="js/manage/log.js?s=1"></script>
	<link rel="stylesheet" href="css/manage/common.css?s=1"/>
	
	<script type="text/javascript">     
	
		const CUR_SITE_ID = 1;
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
	
	<style>
		#result_patch .btn-wrap {
			display: flex;
		    flex-direction: row;
		    justify-content: space-around;
		    align-items: center;
		    flex-wrap: nowrap;
		}
	</style>
	
</head>
	<body>
	<div class="container_wrap">	
		<div class="container" style="padding: 0; max-width: 1500px;">
			 
			<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
				<li class="nav-item">
			    	<a class="nav-link active" id="pills-beacon-tab" data-toggle="pill" href="#pills-beacon" role="tab" aria-controls="pills-beacon" aria-selected="true">비콘</a>
			  	</li>
						
			  	<li class="nav-item" onclick="getNFCLogList()">
			    	<a class="nav-link" id="pills-nfc-tab" data-toggle="pill" href="#pills-nfc" role="tab" aria-controls="pills-nfc" aria-selected="false">NFC</a>
			  	</li>
			 
			  	<li class="nav-item">
			    	<a class="nav-link" id="pills-hole-tab" data-toggle="pill" href="#pills-hole" role="tab" aria-controls="pills-hole" aria-selected="false">개구부</a>
			  	</li>
			  		
			  	<li class="nav-item">
			    	<a class="nav-link" id="pills-scanner-tab" data-toggle="pill" href="#pills-scanner" role="tab" aria-controls="pills-scanner" aria-selected="false">장비</a>
			  	</li>
			  	
			  	<li class="nav-item">
			    	<a class="nav-link" id="pills-patch-tab" data-toggle="pill" href="#pills-patch" role="tab" aria-controls="pills-patch" aria-selected="false">패치로그</a>
			  	</li>			  	
			 
			</ul><!-- pills-tab END -->
	
			<div class="tab-content" id="pills-tabContent">
				<div class="tab-pane fade show active" id="pills-beacon" role="tabpanel" aria-labelledby="pills-beacon-tab">
					<div class="row info info_third">
						<div class="restart_wrap" onclick="getLogByBeaconIdx()">
							비콘번호 검색 <span>&nbsp;<i class="fa-solid fa-magnifying-glass-location"></i></span>
						</div>	
						<div class="btn_add" onclick="getLogByBeaconMacAddr()">
							맥어드레스 검색 <span>&nbsp;<i class="fa-solid fa-magnifying-glass-location"></i></span>
						</div>	
					</div> 
			        <div class="row">   
						<table id="result_beacon" data-toggle="table" data-search="true" data-pagination="true"
							data-page-size="100" data-page-list="[10, 20, 50, All]" data-filter-control="true" 
				 			class="table table-bordered col-12 table-hover table-striped" >				 		
							<thead>
								<tr class="box-shadow">
									<th data-field="no" data-sortable="true" class="text-center">No.</th>	
									<th data-field="beacon_mac" class="text-center">맥어드레스</th>
									<th data-field="scanner_mac" class="text-center">스캐너</th>								
			                		<th data-field="type" data-sortable="true" class="text-center show-web">위치</th>          
									<th data-field="rssi" data-sortable="true" class="text-center">Rssi</th>
									<th data-field="write_time" data-sortable="true" class="text-center">수집시간</th>
								</tr>
							</thead>
						</table>
			        </div>
			  	</div>
	 		  
				<div class="tab-pane fade" id="pills-nfc" role="tabpanel" aria-labelledby="pills-nfc-tab">
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>
					</div>
			        <div class="row">   
						<div>        
							<table id="result_nfc" data-toggle="table" data-search="true" data-pagination="true"
								data-page-size="100" data-page-list="[10, 20, 50, All]" data-filter-control="true" 
					 			class="table table-bordered col-12 table-hover table-striped" >	
								<thead>
									<tr>								
										<th data-field="no" data-sortable="true" class="text-center">No.</th>
										<th data-field="serial_number" data-sortable="true" class="text-center">시리얼넘버</th>
										<th data-field="scanner_mac" class="text-center">스캐너</th>										
										<th data-field="nfc_no" data-sortable="true" class="text-center">NFC번호</th>	
				                		<th data-field="type" class="text-center show-web">출입상태</th>         
				                		<th data-field="write_time" data-sortable="true" class="text-center">수집시간</th>
									</tr>
			               		</thead>
			          		</table>
			          	</div>
			        </div>
				</div>					 
				
				<div class="tab-pane fade" id="pills-hole" role="tabpanel" aria-labelledby="pills-hole-tab">
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="getLogByHoleIdx()">
							개구부 번호 검색 <span>&nbsp;<i class="fa-solid fa-magnifying-glass-location"></i></span>
						</div>	
						<div class="btn_add" onclick="getLogByHoleMacAddr()">
							맥어드레스 검색 <span>&nbsp;<i class="fa-solid fa-magnifying-glass-location"></i></span>
						</div>	
					</div>
			        <div class="row">   
						<div>        
							<table id="result_hole" data-toggle="table" data-search="true" data-pagination="true"
								data-page-size="100" data-page-list="[10, 20, 50, All]" data-filter-control="true" 
				 				class="table table-bordered col-12 table-hover table-striped" >	
								<thead>
									<tr>								
										<th data-field="no" data-sortable="true" class="text-center">No.</th>	
										<th data-field="beacon_mac" class="text-center">맥어드레스</th>
										<th data-field="scanner_mac" class="text-center">스캐너</th>								
				                		<th data-field="type" data-sortable="true" class="text-center show-web">위치</th>          
										<th data-field="rssi" data-sortable="true" class="text-center">Rssi</th>
										<th data-field="x" data-sortable="true" class="text-center">X-Axis</th>
										<th data-field="y" data-sortable="true" class="text-center">Y-Axis</th>
										<th data-field="z" data-sortable="true" class="text-center">Z-Axis</th>
										<th data-field="state" data-sortable="true" class="text-center">상태</th>
										<th data-field="write_time" data-sortable="true" class="text-center">수집시간</th>               		
									</tr>
			               		</thead>
			          		</table>
			          	</div>
			        </div>
				</div>		
				
				<div class="tab-pane fade" id="pills-scanner" role="tabpanel" aria-labelledby="pills-scanner-tab">					
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="getLogScannerSection()">
							수조 번호 검색 <span>&nbsp;<i class="fa-solid fa-magnifying-glass-location"></i></span>
						</div>
					</div>
			        <div class="row">
						<table id="result_scanner" data-toggle="table" data-search="true" data-pagination="true"
								data-page-size="100" data-page-list="[10, 20, 50, All]" data-filter-control="true" 
				 				class="table table-bordered col-12 table-hover table-striped" >	
							<thead>
								<tr>														    
									<th data-field="no" data-sortable="true" class="text-center">No.</th>	
									<th data-field="section_no" class="text-center">수조번호</th>
									<th data-field="section_name" class="text-center">구역이름</th>									
									<th data-field="type" data-sortable="true" class="text-center show-web">위치</th>  
									<th data-field="model" data-sortable="true" class="text-center">장비모델</th>        					            		
									<th data-field="app_ver" data-sortable="true"  class="text-center">앱버전</th>
			                		<th data-field="write_time"  data-sortable="true" class="text-center">수집시간</th>
								</tr>
							</thead>
						</table>
			        </div>
				</div>
				
				<div class="tab-pane fade" id="pills-patch" role="tabpanel" aria-labelledby="pills-patch-tab">					
					<div class="row info info_third"> 
						<div class="restart_wrap" onclick="updateBtn();"><i class="fa-solid fa-arrows-rotate"></i></div>
					</div>
			        <div class="row">
						<table id="result_patch" data-toggle="table" data-search="true" data-pagination="true"
								data-page-size="100" data-page-list="[10, 20, 50, All]" data-filter-control="true" 
				 				class="table table-bordered col-12 table-hover table-striped" >	
							<thead>
								<tr>														    
									<th data-field="no" data-sortable="true" class="text-center">No.</th>	
									<th data-field="patch_type" class="text-center" data-sortable="true" data-filter-control="select">종류</th>
									<th data-field="app_version" class="text-center">버전</th>
									<th data-field="apk_name" class="text-center">앱이름</th>									
									<th data-field="title" class="text-center">수정항목</th>
									<th data-field="content" class="text-center">내용</th>
									<th data-field="app_url" class="text-center">다운로드</th>
									<th data-field="write_date"  data-sortable="true" class="text-center">수정일자</th>
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
