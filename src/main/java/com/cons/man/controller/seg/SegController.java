package com.cons.man.controller.seg;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cons.man.domain.AbnormalSensorVO;
import com.cons.man.domain.AlertVO;
import com.cons.man.domain.DidSettingVO;
import com.cons.man.domain.EmergencyVO;
import com.cons.man.domain.FanVO;
import com.cons.man.domain.GsilManagerVO;
import com.cons.man.domain.HoleVO;
import com.cons.man.domain.LocationVO;
import com.cons.man.domain.NfcLocationVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.RssiBlockVO;
import com.cons.man.domain.ScannerInfoVO;
import com.cons.man.domain.ScannerStateVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.SensorVO;
import com.cons.man.domain.StateVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkStateVO;
import com.cons.man.services.seg.SegService;

@Controller(value="SegController")
@RequestMapping("/seg")
@RestController
public class SegController {
	
	@Resource(name="SegService")
	private SegService segService;
	
	// TABLET & MONITOR
	@RequestMapping(value = {"/sections"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<SectionVO>> getSectionInfo(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section,			
		@RequestParam(value="has_env", defaultValue="-1")int has_env	
	) {			
		List<SectionVO> sec_List = segService.getSectionInfo(site_id, place_id, section, has_env);
		return new ResponseEntity<List<SectionVO>>(sec_List, HttpStatus.OK);
	}	
	
	// TABLET & MONITOR
	@RequestMapping(value = {"/locations"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<LocationVO>> getLocationInfo(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="input_state", defaultValue="-1")int input_state
	) {	
		//System.out.println("[API] locations 호출 site_id: " + site_id + " / place_id: " + place_id + " / section: " + section);
		List<LocationVO> loc_List = segService.getLocationInfo(site_id, place_id, section, input_state);
		//System.out.println("[API] locations Result: " + loc_List.size() + "개");
		return new ResponseEntity<List<LocationVO>>(loc_List, HttpStatus.OK);
	}

	// TABLET & MONITOR
	@RequestMapping(value = {"/emergency"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<EmergencyVO>> getEmergencyInfo(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section,			
		@RequestParam(value="limit", defaultValue="-1")int limit			
	) {	
		//System.out.println("getEmergencyInfo: " + site_id + "/" + place_id + "/" + section + "/" + limit);
		List<EmergencyVO> emer_List = segService.getEmergencyInfo(site_id, place_id, section, limit);
		return new ResponseEntity<List<EmergencyVO>>(emer_List, HttpStatus.OK);
	}	

	// TABLET & MONITOR
	@RequestMapping(value = {"/change/state"}, method = RequestMethod.POST)
	public ResponseEntity<Integer> changeState(HttpSession session, HttpServletResponse response,
		HttpServletRequest request,	@RequestBody StateVO vo) {		
		//System.out.println("changeState (site_id/place_id/section): " + vo.getSite_id() + "/" + vo.getPlace_id() + "/" + vo.getSection());
		//System.out.println("changeState (writer/state): " + vo.getWriter_user_id() + "/" + vo.getState());
		segService.changeState(vo);
		int result = 0;
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	// TABLET & MONITOR
	@RequestMapping(value = {"/sensors"}, method = RequestMethod.GET)
	public ResponseEntity<List<SensorVO>> getSensorList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="has_section", defaultValue="-1")int has_section		
	) {	
		List<SensorVO> sensor_List = segService.getSensorList(site_id, place_id, section, has_section);	
		return new ResponseEntity<List<SensorVO>>(sensor_List, HttpStatus.OK);
	}
	
	// TABLET & MONITOR
	@RequestMapping(value = {"/abnormalSensor"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<AbnormalSensorVO>> getAbnormalSensorList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section,			
		@RequestParam(value="limit", defaultValue="-1")int limit			
	) {	
		//System.out.println("getAbnormalSensorList: " + site_id + "/" + place_id + "/" + section + "/" + limit);
		List<AbnormalSensorVO> list = segService.getAbnormalSensorList(site_id, place_id, section, limit);
		return new ResponseEntity<List<AbnormalSensorVO>>(list, HttpStatus.OK);
	}
		
	// TABLET
	@RequestMapping(value = {"/checkNfcInout"}, method = RequestMethod.GET)				 
	public ResponseEntity<NfcLocationVO> checkNfcInoutInfo(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="serial_number", defaultValue="")String serial_number
	) {			
		//System.out.println("[NFC 대기 인원 체크]:" + serial_number);
		NfcLocationVO vo = segService.checkNfcInoutInfo(site_id, serial_number);
		return new ResponseEntity<NfcLocationVO>(vo, HttpStatus.OK);
	}
	
	// TABLET
	@RequestMapping(value = {"/checkNfcState"}, method = RequestMethod.GET)				 
	public ResponseEntity<NFCVO> checkNfcState(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="serial_number", defaultValue="")String serial_number
	) {	
		//System.out.println("[NFC 할당 체크]:" + serial_number);
		NFCVO vo = segService.checkNfcState(site_id, serial_number);
		return new ResponseEntity<NFCVO>(vo, HttpStatus.OK);
	}	

	// TABLET
	@RequestMapping(value = {"/workStateList"}, method = RequestMethod.GET)
	public ResponseEntity<List<WorkStateVO>> getWorkStateList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id			
	) {				
		List<WorkStateVO> state_list = segService.getWorkStateList(site_id);	
		return new ResponseEntity<List<WorkStateVO>>(state_list, HttpStatus.OK);
	}
	
	// TABLET
	@RequestMapping(value = {"/getWaitList"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<NfcLocationVO>> getWaitList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section
	) {		
		List<NfcLocationVO> nfcList = segService.getWaitList(site_id, place_id, section);
		return new ResponseEntity<List<NfcLocationVO>>(nfcList, HttpStatus.OK);
	}
	
	// TABLET
	@RequestMapping(value = {"/getHoleOpenData"}, method = RequestMethod.GET)				 
	public ResponseEntity<HoleVO> getHoleOpenData(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="id", defaultValue="-1")int id
	) {			
		//System.out.println("[getHoleOpenData] : " + id);
		HoleVO data = segService.getHoleOpenData(site_id, place_id, section, id);		
		if (data == null) {
			return new ResponseEntity<HoleVO>(HttpStatus.NO_CONTENT);			
		}
		//System.out.println("[getDangerZoneData] RESULT ID: " + data.getId());
		return new ResponseEntity<HoleVO>(data, HttpStatus.OK);
	}
	
	// TABLET
	@RequestMapping(value = {"/getDidSetting"}, method = RequestMethod.GET)				 
	public ResponseEntity<DidSettingVO> getDidSettingData(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="id", defaultValue="-1")int id
	) {			
		DidSettingVO data = segService.getDidSettingData(site_id, id);		
		if (data == null) {
			return new ResponseEntity<DidSettingVO>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<DidSettingVO>(data, HttpStatus.OK);
	}
	
	
	
	
	// --------------- TABLET END --------------------- //
	

	// MONITOR & MOBILE (이동 가능)
	@RequestMapping(value = {"/insert/alert"}, method = RequestMethod.POST)
	public ResponseEntity<Integer> insertAlert(HttpSession session, HttpServletResponse response,
		@RequestBody AlertVO vo
	) {			
		//System.out.println(vo.getSection() + "/" + vo.getPlace_id() + "/" + vo.getSection() + "/" + vo.getType() + "/" + vo.getWriter_user_id());
		segService.insertAlert(vo);
		return new ResponseEntity<Integer>(0, HttpStatus.OK);
	}
	
	// MONITOR & MOBILE (이동 가능)
	@RequestMapping(value = {"/insert/alert/all"}, method = RequestMethod.POST)
	public ResponseEntity<Integer> insertAlertAll(HttpSession session, HttpServletResponse response,
		@RequestBody AlertVO vo
	) {			
		//System.out.println(vo.getSection() + "/" + vo.getPlace_id() + "/" + vo.getSection() + "/" + vo.getType() + "/" + vo.getWriter_user_id());
		segService.insertAlertAll(vo);
		return new ResponseEntity<Integer>(0, HttpStatus.OK);
	}	
	
	// ------------------------------------------------ 아래 작업 필요
	
	// MANAGE - BEACON
	@RequestMapping(value = {"/update/rssiAdd"}, method = RequestMethod.POST)
	public ResponseEntity<Integer> updateBeaconRssiAdd(HttpSession session, HttpServletResponse response,
		@RequestParam(value="mac_addr", defaultValue="")String mac_addr,
		@RequestParam(value="rssi_add", defaultValue="0")int rssi_add
	){
		segService.updateBeaconRssiAdd(mac_addr, rssi_add);
		return new ResponseEntity<Integer>(0, HttpStatus.OK);
	}
	
	// MANAGE - BEACON & NFC
	@RequestMapping(value = {"/exit/beacon"}, method = RequestMethod.POST)
	public void exitBeacon(HttpSession session, HttpServletResponse response,		
		@RequestParam(value="uw_id", defaultValue="")String uw_id,
		@RequestParam(value="role", defaultValue="-1")int role,
		@RequestParam(value="beacon_mac", defaultValue="")String beacon_mac)
	{		
		//System.out.println("[MANAGE] 위치파악 OUT: " + uw_id + "/" + role + "/" + beacon_mac);
		segService.exitWait(uw_id, role);
		segService.exitInout(beacon_mac);
		segService.exitLocation(beacon_mac);
	}
	
	// MANAGE - BLOCK
	@RequestMapping(value = {"/getBlockList"}, method = RequestMethod.GET)
	public ResponseEntity<List<RssiBlockVO>> getRssiBlockList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id			
	) {				
		List<RssiBlockVO> rssi_list = segService.getRssiBlockList(site_id);	
		return new ResponseEntity<List<RssiBlockVO>>(rssi_list, HttpStatus.OK);
	}
	
	// MANAGE - BLOCK
	@RequestMapping(value = {"/getDeviceStateData"}, method = RequestMethod.GET)
	public ResponseEntity<List<ScannerStateVO>> getDeviceStateData(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id			
	) {				
		List<ScannerStateVO> state_list = segService.getDeviceStateData(site_id);	
		return new ResponseEntity<List<ScannerStateVO>>(state_list, HttpStatus.OK);
	}
	
	// MANAGE - SECTION
	@RequestMapping(value = {"/update/sectionState"}, method = RequestMethod.POST)
	public void updateStateService(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="isService", defaultValue="-1")int isService)
	{
		segService.updateStateService(site_id, section, isService);
	}
	
	// MANAGE - SECTION
	@RequestMapping(value = {"/update/sectionSensor"}, method = RequestMethod.POST)
	public void updateSensorService(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="isService", defaultValue="-1")int isService)
	{
		segService.updateSensorService(site_id, section, isService);
	}	

	// MANAGE - SENSOR
	@RequestMapping(value = {"/update/sectionSensorPush"}, method = RequestMethod.POST)
	public void updateSectionSensorPush(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="isService", defaultValue="-1")int isService)
	{
		//System.out.println("updateSectionSensorPush: " + site_id + "/" + section + "/" + isService);
		segService.updateSectionSensorPush(site_id, section, isService);
	}	
	
	// MANAGE - SENSOR
	@RequestMapping(value = {"/delete/sensor"}, method = RequestMethod.POST)
	public void deleteSensorData(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section) 
	{
		System.out.println("[MANAGE] 환경센서 삭제: " + site_id + "/" + place_id + "/" + section);
		segService.deleteSensorData(site_id, place_id, section);
	}
	
	// MANAGE - HOLE
	@RequestMapping(value = {"/getHoleManageList"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<HoleVO>> getHoleManageList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id
	) {			
		List<HoleVO> list = segService.getHoleManageList(site_id);
		return new ResponseEntity<List<HoleVO>>(list, HttpStatus.OK);
	}
	
	
	// MONITOR
	@RequestMapping(value = {"/getHoleLog"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<HoleVO>> getHoleLog(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="id", defaultValue="-1")int id,		
		@RequestParam(value="input_date", defaultValue="")String input_date
	) {			
		List<HoleVO> list = segService.getHoleLog(site_id, place_id, section, id, input_date);
		return new ResponseEntity<List<HoleVO>>(list, HttpStatus.OK);
	}	
	
	// 어디에 쓰였는지 아직 모름
	@RequestMapping(value = {"/getHoleStateList"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<HoleVO>> getHoleStateList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section
	) {		
		List<HoleVO> holeList = segService.getHoleStateList(site_id, place_id, section);
		return new ResponseEntity<List<HoleVO>>(holeList, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/change/alarm/state"}, method = RequestMethod.POST)
	public ResponseEntity<Integer> changeAlarmState(HttpSession session, HttpServletRequest request, HttpServletResponse response, 
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="isService", defaultValue="-1")int isService,
		@RequestParam(value="isMobile", defaultValue="0")int isMobile) {		

		int result = 0;
		try {
			UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");			
			String userId = "";
			if(userInfo != null) {
				userId = userInfo.getId();
			}
			segService.insertChangeAlarmState(site_id, request.getRemoteAddr(), userId, isService, isMobile);		
		}
		catch(Exception e){	
			e.printStackTrace();
			result = -1;
		}
	
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/change/sound/state"}, method = RequestMethod.POST)
	public ResponseEntity<Integer> changeSoundState(HttpSession session, HttpServletRequest request, HttpServletResponse response, 
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="isService", defaultValue="-1")int isService,
		@RequestParam(value="isMobile", defaultValue="0")int isMobile) {		

		int result = 0;
		try {
			UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");			
			String userId = "";
			if(userInfo != null) {
				userId = userInfo.getId();
			}
			segService.insertChangeSoundState(site_id, request.getRemoteAddr(), userId, isService, isMobile);
		}
		catch(Exception e){	
			e.printStackTrace();
			result = -1;
		}
	
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/getFanOpenData"}, method = RequestMethod.GET)				 
	public ResponseEntity<FanVO> getFanOpenData(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="id", defaultValue="-1")int id
	) {			
		//System.out.println("[getFanOpenData] : " + id);
		FanVO data = segService.getFanOpenData(site_id, place_id, section, id);		
		if (data == null) {
			return new ResponseEntity<FanVO>(HttpStatus.NO_CONTENT);			
		}
		//System.out.println("[getFanOpenData] RESULT ID: " + data.getId());
		return new ResponseEntity<FanVO>(data, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/getFanList"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<FanVO>> getFanList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section
	) {			
		//System.out.println("[getFanList] : " + site_id + "/" + place_id + "/" + section);
		List<FanVO> list = segService.getFanList(site_id, place_id, section);
		return new ResponseEntity<List<FanVO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/getFanLogList"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<FanVO>> getFanLogList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="fan_idx", defaultValue="-1")int fan_idx
	) {			
		//System.out.println("[getFanLogList] : " + site_id + "/" + place_id + "/" + section  + "/" + fan_idx);
		List<FanVO> list = segService.getFanLogList(site_id, place_id, section, fan_idx);
		return new ResponseEntity<List<FanVO>>(list, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = {"/getFanOffCount"}, method = RequestMethod.GET)
	public ResponseEntity<Integer> getFanOffCount(HttpSession session, HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="site_id", defaultValue="-1")int site_id,
			@RequestParam(value="place_id", defaultValue="-1")int place_id,
			@RequestParam(value="section", defaultValue="-1")int section) {		
		
		//System.out.println("[getFanOffCount] : " + site_id + "/" + place_id + "/" + section);

		int fan_off_count = segService.getFanOffCount(site_id, place_id, section);
		
		return new ResponseEntity<Integer>(fan_off_count, HttpStatus.OK);
	}
}



