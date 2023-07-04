<!DOCTYPE html>
<%@ page pageEncoding="utf-8" contentType="text/html;charset=UTF-8"	language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page session="true"%>

<c:set var="lastUpdate" value="${ System.currentTimeMillis()}"/>

<html lang="ko">

<meta charset="utf-8"/>
<meta data-n-head="true" name="viewport" content="width=device-width, initial-scale=1">

<head>

<script async src="https://www.googletagmanager.com/gtag/js?id=UA-138883721-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-138883721-1');
</script>
	<title>Monitoring System</title>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  	<link rel="icon" href="${contextPath}/images/ss.ico" type="image/x-icon" />
  	<link rel="shortcut icon" href="${contextPath}/images/ss.ico"  type="image/x-icon" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <link rel="stylesheet" href="css/bootstrap.min.css" media="screen">
    <link rel="stylesheet" href="css/monitor/animate.css" media="screen">
    <link rel="stylesheet" href="css/monitor/mon_16.css?s=${lastUpdate}" media="screen">  
    <link rel="stylesheet" href="css/monitor/mon_section.css?s=${lastUpdate}" media="screen">       
    
	<script type="text/javascript" src="js/monitor/mon_16.js?s=${lastUpdate}"></script>
	<script type="text/javascript" src="js/monitor/mon_section.js?s=${lastUpdate}"></script>
	<script type="text/javascript" src="js/monitor/mon_state.js?s=${lastUpdate}"></script>
    <script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="js/jquery.plugin.js"></script>    
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-table.js"></script>
    <script type="text/javascript" src="js/bootstrap-table-ko-KR.js"></script>
    
</head>


<body style="overflow: hidden;">

<div id="ss_cont">
    <div id="header">
        <div id="title_left">
            <span class="client_text text-center">SAMSUNG ENGINEERING</span>
            <span class="site_text text-center">평택 P5-PJT 그린동 관리 시스템</span>
            <span class="system_text text-center">Monitoring System</span>
        </div>
        <div id="title_right">  
        	<div class="alarm_box"> 
        	 	<div class="alarm_text text-center" >알림 설정</div>
                <div class="alarm_value text-center">
                   <i id="btn_alarm" class="fas fa-toggle-on" onclick="swapAlarmSetting()" style="cursor: pointer;"></i>
                </div>        		
			</div>
			<div class="sound_box"> 
        	 	<div class="sound_text text-center" >소리 설정</div>
                <div class="sound_value text-center" >
     <i id="btn_sound" class="fas fa-volume-up" onclick="swapSoundSetting()" style="cursor: pointer;"></i>  
                </div>        		
			</div>
            <div class="temp_box">
                <div class="temp_text text-center" >외부 온도</div>
                <div class="temp_value text-center" ><span id="__weather_temp">12℃</span></div>
            </div>
            <div class="weather_box">
                <img id="__weather_img" src="images/icons/weather/01d.png">
            </div>
            <div class="clock_box" style="color: white;">
                 <div id="cur_date"></div>
                 <div id="clock"></div>
            </div>
        </div>
    </div>

    <div id="main_frame">

        <div id="frame1">
            <div id="legend_box">            
                <div class="btn_floor_box"> 
                    <button class="btn_floor btn_floor_2F selected" onclick="setSectionMap(2)">2F</button>
                    <button class="btn_floor btn_floor_3F" onclick="setSectionMap(3)">3F</button>
                    <button class="btn_floor btn_floor_5F" onclick="setSectionMap(5)">5F</button>
                </div>
                <div class="legend_items">
                    <img src="images/monitor/ss/state.png" style="height: 100%; width: 100%;">
                </div>

            </div>
			<div id="div_map">
	 	        <div id="_section_2F" class="map_bg"></div>
	            <div id="_section_3F" class="map_bg" style="display: none"></div>
	            <div id="_section_5F" class="map_bg" style="display: none"></div>           
			</div>
            <div id="section_info">
                <div class="info_box">
                    <div class="image_box">
                        <img src="images/monitor/ss/workers.png">
                    </div>
                    <div class="info_title" onclick="overlayWorkerTable()">총 근로자</div>
                    <div class="worker_cnt_box info_val smallChar"><span id="worker_cnt" class="bigChar">0</span>명</div>
                </div>

				<div class="info_box">
                    <div class="image_box">
                        <img src="images/monitor/ss/tank.png">
                    </div>                    
                    <div class="info_title">사용 수조</div>
                    <div class="tank_cnt_box info_val"><span id="pre_tank_cnt" class="bigChar">0</span> / <span id="tot_tank_cnt" class="smallChar">149</span></div>
				</div>
               	
                <div class="info_box">
                    <div class="image_box">
                        <img src="images/monitor/ss/temp.png">
                    </div>
                    <div class="info_title">환경센서</div>
                    <div class="sensor_state_box info_val"><span id="sensor_state" class="smallChar" style="color: #21DB34">정 상</span></div>                  
                </div>

            </div>
        </div>

        <div id="frame2">
            <div id="tank_info_box">
                <div id="tank_info_title">사용 수조 정보</div>
                <div id="tank_info_log"></div>
                <div id="history_btn_list">                 	
                	<div class="btn btn-default" title="금일개구부" style="height:5vh;width: 6vh;" onclick="openHoleModal()"><img class="image" src="images/monitor/16/hole_on.png"></div>
                	<div class="btn btn-default" title="위급사항알림" style="height:5vh;width: 6vh;" onclick="CUR_OVERLAY_SECTION= -1; openAlertModal()"><img class="image" src="images/monitor/ss/alert/btn_alert.png"></div>                	                	
                	<div class="btn btn-default" title="환경센서알림" style="height:5vh;width: 6vh;" onclick="openAbnormalModal()"><img class="image" src="images/monitor/ss/alert/btn_sensor.png"></div>
                	<div class="btn btn-default btn-base" title="비상버튼 목록" style="height:5vh;width: 6vh;" onclick="openEmergencyModal()"><img class="image" src="images/monitor/ss/alert/btn_emergency.png"></div>
                </div>
            </div>
        </div>


    </div>
    <div id="footer">
        <span style="width: 15%;"><img src="images/monitor/ss/logo_se.png"></span>
        <span style="width: 68%;"></span>
        <span style="width: 15%;"><img src="images/monitor/ss/logo_gsil.png" style="padding-left: 4vh;"></span>
    </div>


</div>


<div id="move_monitor">
	가로로 <span style="color:#ff3547; font-weight: 600;">모니터/핸드폰</span> 화면을 돌려주시길 바랍니다.
</div>	


<!-- 수조 근로자 상세 정보 오버레이 -->
<div class="modal fade" id="section_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top: 6%; width: 90%;">
        <div class="modal-content" id="section_modal_content">
            <div class="modal-header">
            	 <div id="btn_alert" class="btn btn-default btn-base" title="위급사항알림" style="height:4vh;width: 5vh;left: 100%;top: 108%;position:absolute;" onclick="openAlertModal()"><img class="image" src="images/monitor/ss/alert/btn_alert.png"></div>
                <div class="header_div" style="width: 51%; padding-left: 1vh;"><span id="section_modal_title"></span></div>
                <div class="header_div" style="width: 14%">공종 인원 : <span id="section_state_worker"></span>명</div>
                <div class="header_div" style="width: 14%">전체 인원 : <span id="section_tot_worker"></span>명</div>
                <div style="width: 20%; height: 100%;line-height: 4vh; padding: 1vh;">                	
                    <div class="white" id="select_section_process" onclick="processOverlay();">--- 작업 선택 ---</div>
                </div>
                <div id="isAuto_state" class="badge badge-danger" style="float: right;margin-right: 1%;margin-top: -1.2%;font-size:1.2vh;">AUTO</div>
            </div>
            <div class="modal-body">
            	<!-- 근로자 -->
                <div id="section_worker_box">
                </div>
                <!-- 환경센서 -->
                <div id="section_env_box">
                  <div class="section_sensor">
                    <div style="width:5%;">                    	
                    <div class="bar"></div></div>

                    <div class="sensor_box">
                        <div class="sensor_title_box"><span>산소</span></div>
                        <div class="sensor_value_box">
                            <span id="__o2"></span>
                            <span class="sensor_type">%</span>
                        </div>
                    </div>

                    <div class="sensor_measure_box">
       					<img class="degree_image" src="images/monitor/ss/o2_degree.png">       					
                    </div>

                    <div class="text-center" style="width: 25%;">
                   		<div id="__o2_state" class="sensor_state normal"></div>
                	</div>
                </div>

                <!-- CO2 -->
                <div class="section_sensor">
	                <div style="width:5%;"><div class="bar"></div></div>	
	                <div class="sensor_box">
		                <div class="sensor_title_box"><span>이산화탄소</span></div>	
		                <div class="sensor_value_box">
		                	<span id="__co2"></span>
		                	<span class="sensor_type">ppm</span>
		                </div>
		                </div>
		
		                <div class="sensor_measure_box">
		         			<img class="degree_image" src="images/monitor/ss/co2_degree.png"> 
		                </div>
		                
		                <div class="text-center" style="width: 25%;">
		                <div id="__co2_state" class="sensor_state normal"></div>
	
	                </div>
                </div>

               
                <div class="section_sensor">
	                <div style="width:5%;"><div class="bar"></div></div>
	
	                <div class="sensor_box">
		                <div class="sensor_title_box"><span>일산화탄소</span></div>
		
		                <div class="sensor_value_box">
		                <span id="__co"></span>
		                <span class="sensor_type">ppm</span>
		                </div>
		                </div>
		
			            <div class="sensor_measure_box">
							<img class="degree_image" src="images/monitor/ss/co_degree.png"> 
		                </div>
		                
		                <div class="text-center" style=" width: 25%;">
		
		                <div id="__co_state" class="sensor_state normal"></div>
	                </div>
                </div>

               
                <div class="section_sensor">
	                <div style="width:5%;"><div class="bar"></div></div>
	
	                <div class="sensor_box">
		                <div class="sensor_title_box"><span>황화수소</span></div>
		
		                <div class="sensor_value_box">
		                <span id="__h2s"></span>
		                <span class="sensor_type">ppm</span>
		                </div>
		                </div>
		
				        <div class="sensor_measure_box">
							<img class="degree_image" src="images/monitor/ss/h2s_degree.png">
		                </div>
		                
		                <div class="text-center" style=" width: 25%;">
		
		                <div id="__h2s_state" class="sensor_state normal"></div>
		
	                </div>
                </div>

           
                <div class="section_sensor">
	                <div style="width:5%;"><div class="bar"></div></div>
	
	                <div class="sensor_box">
		                <div class="sensor_title_box"><span>LEL</span></div>
		
		                <div class="sensor_value_box">		                
		                <span id="__ch4"></span>
		                <span class="sensor_type">%</span>
		                </div>
		                </div>
		
					    <div class="sensor_measure_box">
							<img class="degree_image" src="images/monitor/ss/ch4_degree.png">
		                </div>
		                
		                <div class="text-center" style=" width: 25%;">
		                <div id="__ch4_state" class="sensor_state normal"></div>
		
		                </div>
	                </div>
                </div>

            </div>
        </div>
    </div>
</div>

<!-- 1. Emergency Modal -->
<div class="modal fade" id="emergency_modal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" >
    <div class="modal-dialog" style="margin-top: 5%; width: 90%;" >
        <div id="modal_emergency_content" class="modal-content modal-content-alert">
            <div class="modal_head" class="modal-header blink">
                	응급 상황 알림
            </div>
            <div class="modal_body" class="modal-body" style="font-size: 2.5vw">
            	<div class="row" style="margin: 0;">
            	
	            	<div class="col-xs-1" style="width:20%; height: 57vh; padding: 5vh 2vh;">            	
	            		<img id="emergency_photo" style="margin-left: 5%" class="image" onerror="this.src='images/noimage.png'">
	            	</div>	
					<div id="emergency_detail_box" class="col-xs-8">
	            		<table class="table table-borderd" style="font-size: 2.5vh; margin-left:3%; margin-top: 3%">
							<tr>
	            				<th>항 목</th><th class="text-center">내 용</th>
	            			</tr>
	            			<tr>
	            				<td>발생 시간</td>
	            				<td id="emergency_writetime"></td>
	            			</tr>
	            			<tr>
	            				<td>위 치</td>
	            				<td id="emergency_section">---</td>
	            			</tr>
	            			<tr>
	            				<td>상하부</td>
	            				<td id="emergency_section_type">---</td>
	            			</tr>
	            			<tr>
	            				<td>이 름</td>
	            				<td id="emergency_name">---</td>
	            			</tr>
	            			<tr>
	            				<td>업 체</td>
	            				<td id="emergency_cont_name">---</td>
	            			</tr>
	            			<tr>
	            				<td>혈 액 형</td>
	            				<td id="emergency_btype">---</td>
	            			</tr>
	            			<tr>
	            				<td>전 화 번 호</td>
	            				<td id="emergency_phone">---</td>
	            			</tr>
	            		</table>
	            	</div>
	            	<div id="emergency_list" class="col-xs-6">
	            		<table id="emergency_table" class="table" data-filter-control="true" class="table table-bordered"
	            			 style="font-size : 2vh;cursor:pointer;">
	            			<thead>
	            				<tr>
	            					<th class="text-center hidden" data-field="id"></th>
	            					<th class="text-center" data-field="section_name">위 치</th>
	            					<th class="text-center" data-field="write_time">시 간 <i class="fas fa-sort-amount-down"></i></th>	            					
	            				</tr>
	            			</thead>
	            		</table>
	            	</div>
	            	</div>
	            </div>
            </div>
        </div>
    </div>
</div>

<!-- 2. Alert Modal -->
<div class="modal fade" id="alert_modal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" >
    <div id="alert_modal_box" class="modal-dialog" >    
        <div class="modal-content" style="background-color:white; height: 100%;">
           <div class="modal_head" class="modal-header" style="font-weight: bold;color: #212529;padding: 1%;background-color: #ebebeb;">
                	수조 위급 사항 알림
            </div>           
            <div class="modal_body" class="modal-body" >
            	<div class="row">
            		<div class="col-xs-12">
	            		<div class="list-group">
						  <a href="#" class="list-group-item list-group-item-action" onclick="sendAlert(1)">
							  <i class="fas fa-play-circle" stlye="color:#080d13;"></i>
							  	 작업시간 확인! 작업시간이 얼마 남지 않았으니 마무리 지어주시길 바랍니다.						  	 
						  </a>
						  <a href="#" class="list-group-item list-group-item-action" onclick="sendAlert(2)">
						  	<i class="fas fa-play-circle" stlye="color:#080d13;"></i>
						  	위험요소 발견! 위험요소 발견! 주변 확인 후 위험요소 제거 바랍니다.						  
						  </a>
						  <a href="#" class="list-group-item list-group-item-action" onclick="sendAlert(3)">
						  	<i class="fas fa-play-circle" stlye="color:#080d13;"></i>
						  	응급상황 발생! 응급상황 발생! 속히 안전지대로 이동 바랍니다.
						  </a>						
						</div>  
            		</div>
	            </div>
            </div>
        </div>
    </div>
</div>

<!-- 3. Sealed Modal (Not Use) -->
<div id="btn_overlay_Sealed"  style="display:none" data-toggle="modal" data-target="#sealed_modal"></div>
<div class="modal fade" id="sealed_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  
    <div class="modal-dialog" style="width:95%; margin-top:2%;">
    <div class="modal-content modal_content_sealed">
      <div class="modal-header">
         <div class="title">밀폐교육 미이수자 알림</div>
         <div class="count">인원 : <span id="sealed_cnt">0</span>명</div>
      </div>
      <div id="sealed_body" class="modal-body">
         <!-- <div class="worker_box">
            <div class="sec_num">#<span>12</span>번 수조</div>
            <div class="worker_box_content">
                <div class="worker_photo"><span></span></div>
                <div class="worker_info">
                    <div class="worker_place"><span>(주)동부</span></div>
                    <div class="worker_name"><span>김이름</span></div>
                    <div class="worker_work"><span>근로자</span> / <span>배관공</span></div>
                    <div class="time_log"><span>2019-10-21 12:34:56</span></div>
                </div> 
            </div>
         </div>
          -->
      </div>
    </div>
   </div>
    
    
</div>

<!-- 4. Sensor Modal -->
<div class="modal fade" id="abnormal_modal" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true" >
    <div class="modal-dialog">
        <div id="modal_abnormal_content" class="modal-content modal-content-alert">
            <div class="modal_head" class="modal-header blink">
				환경 센서 알림
            </div>
            <div class="modal_body" class="modal-body">
            	<div class="row" style="margin: 0;">          
					<div id="abnormal_sensor_detail_list" class="col-xs-8">
	            		<table class="table table-borderd">
							<tr>
	            				<th>항 목</th><th class="text-center">내 용</th>
	            			</tr>
	            			<tr>
	            				<td>발생 시간</td>
	            				<td id="abnormal_sensor_write_time"></td>
	            			</tr>
	            			<tr>
	            				<td>위 치</td>
	            				<td id="abnormal_sensor_section_name"></td>
	            			</tr>
	            			<tr>
	            				<td>감지 센서</td>
	            				<td id="abnormal_sensor_sensor_type"></td>
	            			</tr>
	            			<tr>
	            				<td>측정치</td>
	            				<td id="abnormal_sensor_value"></td>
	            			</tr>
	            		</table>
	            	</div>
	            	<div id="abnormal_sensor_list" class="col-xs-6">
	            		<table id="abnormal_sensor_table" class="table" data-filter-control="true" class="table table-bordered"
	            			 style="font-size : 0.8em; cursor:pointer;">
	            			<thead>
	            				<tr>
	            					<th class="text-center hidden" data-field="id"></th>
	            					<th class="text-center" data-field="section_name">위 치</th>
	            					<th class="text-center" data-field="write_time">시 간 <i class="fas fa-sort-amount-down"></i></th>	            					
	            				</tr>
	            			</thead>
	            		</table>
	            	</div>
	            	</div>
	            </div>
            </div>
        </div>
    </div>
</div>

<!-- 5. 작업 상태 선택 오버레이 -->
<div class="modal fade" id="process_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top: 10.2%; width: 78%;">
        <div class="modal-content" id="process_modal_content" style="height: 100%; width: 25%; left: 81.5%;">
            <div id="work_state_box" class="col-xs-12">
<%--             	/*
                <form id="image_form" method="post" action="">
               		<div class="process __process_0 __process_auto" onclick="changeState(-1)" >자 동</div> 
                    <div class="process __process_0" onclick="changeState(0)">작업없음</div>
                    <div class="process __process_1" onclick="changeState(1)">방수작업</div>
                    <div class="process __process_2" onclick="changeState(2)">토목작업</div>
                    <div class="process __process_3" onclick="changeState(3)">배관작업</div>
                    <div class="process __process_4" onclick="changeState(4)">비계작업</div>                    
                    <div class="process __process_5" onclick="changeState(5)">전기/제어 작업</div>
                    <div class="process __process_-99" onclick="changeState(-99)">정비작업</div>
                </form>
                */ --%>
            </div>
        </div>
    </div>
</div>

<!-- 6. 개구부 열림 알림 팝업 -->
<div class="modal fade" id="hole_modal" tabindex="-1" role="dialog" aria-hidden="true" >
	<div class="modal-dialog">
		<div id="modal_hole_content">
			<div class="modal_head blink">
            	개구부 열림 알림
            </div>
             <div class="modal_body" class="modal-body">     
             	<div id="hole_content_box">
             		<table class="table table-borderd">
						<tr>
							<th>항 목</th><th class="text-center">내 용</th>
						</tr>
						<tr>
							<td>발생 시간</td>
							<td id="hole_write_time" class="text-center">---</td>
						</tr>
	            		<tr style="display: none;">
	            			<td>이 름</td>
	            			<td id="hole_name" class="text-center">---</td>
	            		</tr>
	            		<tr>
	            			<td>위 치</td>
	            			<td id="hole_section_name" class="text-center">---</td>
	            		</tr>	  
					</table>
             	</div>
             </div>		
		</div>
	</div>
</div><!-- #zone_modal END -->

<!-- 7. 금일 개구부 개폐 로그 팝업 -->
<div class="modal fade" id="hole_log_modal" tabindex="-1" role="dialog" aria-hidden="true" >
	<div class="modal-dialog">
		<div id="modal_hole_log_content">
			<div class="modal_head" class="modal-header">
				<div class="title">개구부 개폐 로그</div>
				<div class="print" onclick="printHoleLog();" title="개구부 로그 출력" style="display: none;"><i class="fas fa-print"></i></div>            	
            </div>
             <div class="modal_body" class="modal-body">        
             	<div id="hole_log_content_box">    	
					<table id="hole_log_table" data-toggle="table"  data-search="false" data-pagination="true"
						data-page-size="100" data-page-list="[10, 20, 50, All]" data-filter-control="true" 
			 			class="table table-bordered col-12 table-hover table-striped" >
						<thead>
							<tr>
								<th class="text-center" data-field="no">No.</th>			
	            				<th class="text-center" data-field="section_type">분 류</th>
	            				<th class="text-center" data-field="section_name">위 치</th>
	            				<th class="text-center" data-field="section">구역번호</th>
	            				<th class="text-center" data-field="hole_state">상 태 </th>
	            				<th class="text-center" data-field="write_time">시 간</th>       	            					
							</tr>
						</thead>
					</table>             
             	</div>         
             </div>		
		</div>
	</div>
</div><!-- #attend_deny_modal END -->

<script>
var CUR_SITE_ID = '${userLoginInfo.site_id}' || '${mobile_site_id}';
var CUR_PLACE_ID = -1;
var WRITER_USER_ID = '${userLoginInfo.id}'|| -99      
var CUR_OVERLAY_INTERVAL_ID = -1 ;    
var OVERLAY_INTERV = 5;
var TOTAL_TANK_COUNT = 285;
const audio = new Audio("./images/ping.mp3");
const audio1 = new Audio("./images/hole.mp3");
var beacon_array = new Array(2000);
var G_HOLE_ID = '${hId}';// 최신 개구부 열림 팝업 ID
var G_DID_SET_ID = 0;
var G_ALARM_ON = true;
var G_SOUND_ON = true;
    
$(document).ready(function () {
	startTime();
	checkAgent();
	
	try {
		initSection();
		initArray();
	} catch(e){
    	console.log(e);
    }
	
	checkDidSetting();
	window.setInterval(function(){ checkDidSetting(); }, 2 * 1000);

	setOpenWeatherAPIInfo(CUR_SITE_ID);
	window.setInterval(function(){setOpenWeatherAPIInfo(CUR_SITE_ID)}, 30 * 1000);
	
	checkEmeregencyData();
	window.setInterval(function(){ checkEmeregencyData(); }, 2 * 1000);  
	
	checkAbnormalData();
	window.setInterval(function(){ checkAbnormalData(); }, 2 * 1000);
	
	//checkHoleOpen();
	//window.setInterval(function(){ checkHoleOpen(); }, 2 * 1000);	
	
	getSectionList();
	window.setInterval(function(){ getSectionList(); }, 5 * 1000); 
   	
    resizeFrameSize();
    $(window).resize(function () { resizeFrameSize(); }).resize();
    
    $('#section_modal').on('shown.bs.modal', function (e) {
    	setSectionOverlay();
    	CUR_OVERLAY_INTERVAL_ID = window.setInterval(function(){
    		setSectionOverlay();				
    	}, 5 * 1000 );
    });
    
	$(document).on('click', '.worker_default_info' ,function (e) {
		var idx = this.classList[this.classList.length-2];		
		if(beacon_array[idx] == 1) {		
			beacon_array[idx] = 0;
		} else {		
			beacon_array[this.classList[this.classList.length-2]] = 1;
		}
		$(this).removeClass('on');
		$(this).parent().children('.worker_educatiot_info').addClass('on')
	}) 
	
	$(document).on('click', '.worker_educatiot_info' ,function (e) {
		var idx = this.classList[this.classList.length-2];		
		if(beacon_array[idx] == 1) {		
			beacon_array[idx] = 0;
		}
		else {
			beacon_array[idx] = 1;
		}
		$(this).removeClass('on');
		$(this).parent().children('.worker_default_info').addClass('on')
	}) 
	
	$('#emergency_table').on('click-row.bs.table', function (e, row, $element) {
		$('#emergency_table tr').css('background-color' , 'white');
		$element.css('background-color' , '#ff4343');        	
		setEmergencyInfo(row); 
	});
	
	$('#abnormal_sensor_list').on('click-row.bs.table', function (e, row, $element) {
		$('#abnormal_sensor_list tr').css('background-color' , 'white');
		$element.css('background-color' , '#ff4343');        	
		setAbnormalSensorInfo(row);
	});	
	
   	$('#tot_tank_cnt').html(TOTAL_TANK_COUNT);     
  	

}); // ready END

</script>

</body>


</html>