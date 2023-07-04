package com.cons.man.services.seg;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cons.man.domain.AbnormalSensorVO;
import com.cons.man.domain.AlertVO;
import com.cons.man.domain.DidSettingVO;
import com.cons.man.domain.EmergencyVO;
import com.cons.man.domain.FanVO;
import com.cons.man.domain.GsilManagerVO;
import com.cons.man.domain.HoleVO;
import com.cons.man.domain.InoutVO;
import com.cons.man.domain.LocationVO;
import com.cons.man.domain.NfcLocationVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.RssiBlockVO;
import com.cons.man.domain.ScannerInfoVO;
import com.cons.man.domain.ScannerStateVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.SensorVO;
import com.cons.man.domain.StateVO;
import com.cons.man.domain.WorkStateVO;
import com.cons.man.persistence.SiteMapper;
import com.cons.man.persistence.UserMapper;
import com.cons.man.persistence.WorkerMapper;
import com.cons.man.persistence.seg.SegMapper;

@Service(value="SegService")
public class SegService {
	
	@Resource(name="SegMapper")
	private SegMapper segMapper;
	
	@Resource(name="SiteMapper")
	private SiteMapper siteMapper;

	@Resource(name="UserMapper")
	private UserMapper userMapper;
	
	@Resource(name="WorkerMapper")
	private WorkerMapper workerMapper;
	
	// TABLET START

	public List<SectionVO> getSectionInfo(int site_id, int place_id, int section, int has_env) {	
		return segMapper.getSectionInfo(site_id, place_id, section, has_env);
	}
	
	public List<LocationVO> getLocationInfo(int site_id, int place_id, int section, int input_state) {
				
		/* 221213 테스트 진행
		List<LocationVO> list = null; 
		
		try {
			
			List<Integer> sectionGroupList = segMapper.getSectionGroupList(section);
			
			System.out.println("[API] locations Service단 Group 여부: " + sectionGroupList.size() + "개");
			
			if(sectionGroupList.size() == 0) {
				list = segMapper.getLocationInfo(site_id, place_id, section, input_state);
			}
			else {
				LocationVO vo = new LocationVO();
				vo.setSite_id(site_id);
				vo.setPlace_id(place_id);
				vo.setGroupList(sectionGroupList);
				vo.setInput_state(input_state);
				list = segMapper.getGroupLocationList(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		*/
		return segMapper.getLocationInfo(site_id, place_id, section, input_state);
	}
	
	public List<EmergencyVO> getEmergencyInfo(int site_id, int place_id, int section, int limit) {	
		return segMapper.getEmergencyInfo(site_id, place_id, section, limit);
	}	
	
	public void changeState(StateVO vo) {	
		segMapper.changeState(vo);
	}

	public List<SensorVO> getSensorList(int site_id, int place_id, int section, int has_section) {
		List<SensorVO> list = segMapper.getSensorList(site_id, place_id, section, has_section);		
		return list;
	}	

	public List<AbnormalSensorVO> getAbnormalSensorList(int site_id, int place_id, int section, int limit) {	
		return segMapper.getAbnormalSensorList(site_id, place_id, section, limit);
	}	
	
	public NfcLocationVO checkNfcInoutInfo(int site_id, String serial_string) {
		return segMapper.checkNfcInoutInfo(site_id, setSerialData(serial_string)); 
	}
	
	public NFCVO checkNfcState(int site_id, String serial_string) {	
		return segMapper.checkNfcState(site_id, setSerialData(serial_string)); 
	}
	
	public String setSerialData(String serial_string) {
		String serial_number = "";
		for(int i=0; i<serial_string.length(); i++) {
			if(i%2 == 0 && i>0) {
				serial_number += ":";
			}
			serial_number += serial_string.substring(i, i+1);	
		}
		return serial_number;
	}	

	public List<WorkStateVO> getWorkStateList(int site_id) {	
		return segMapper.getWorkStateList(site_id);
	}

	public List<NfcLocationVO> getWaitList(int site_id, int place_id, int section) {	
		return segMapper.getWaitList(site_id, place_id, section);
	}	

	public HoleVO getHoleOpenData(int site_id, int place_id, int section, int id) {	
		return segMapper.getHoleOpenData(site_id, place_id, section, id);
	}	
	
	public DidSettingVO getDidSettingData(int site_id, int id) {	
		return segMapper.getDidSettingData(site_id, id);
	}	
	
	
	// TABLET END
	
	
	// MOBILE & MONITOR
	public void insertAlert(AlertVO vo) {
		segMapper.insertAlert(vo);
	}
	
	public void insertAlertAll(AlertVO vo) {
		List<SectionVO> sectionList = segMapper.getSectionInfo(16, -1, -1, -1);
		for(SectionVO sectionVo : sectionList) {
			vo.setSite_id(sectionVo.getSite_id());
			vo.setPlace_id(sectionVo.getPlace_id());
			vo.setSection(sectionVo.getSection());
			segMapper.insertAlert(vo);
		}
	}
	
	// ------------------------------- 아래 작업 필요

	public void updateBeaconRssiAdd(String mac_addr, int rssi_add) {
		segMapper.updateBeaconRssiAdd(mac_addr, rssi_add);		
	}	

	public void exitWait(String uw_id, int role) {
		segMapper.exitWait(uw_id, role);
	}	
	
	public void exitInout(String beacon_mac) {
		segMapper.exitInout(beacon_mac);
	}
	
	public void exitLocation(String beacon_mac) {
		segMapper.exitLocation(beacon_mac);
	}
	
	public List<RssiBlockVO> getRssiBlockList(int site_id) {
		return segMapper.getRssiBlockList(site_id);
	}	

	public List<ScannerStateVO> getDeviceStateData(int site_id) {
		return segMapper.getDeviceStateData(site_id);
	}

	public void updateStateService(int site_id, int section, int isService) {
		segMapper.updateStateService(site_id, section, isService);
	}

	public void updateSensorService(int site_id, int section, int isService) {
		segMapper.updateSensorService(site_id, section, isService);
	}

	public void updateSectionSensorPush(int site_id, int section, int isService) {
		segMapper.updateSectionSensorPush(site_id, section, isService);
	}

	public void deleteSensorData(int site_id, int place_id, int section) {
		segMapper.deleteSensorData(site_id, place_id, section);
	}

	public List<HoleVO> getHoleManageList(int site_id) {	
		return segMapper.getHoleManageList(site_id);
	}
	
	public List<HoleVO> getHoleLog(int site_id, int place_id, int section, int id, String input_date) {	
		return segMapper.getHoleLog(site_id, place_id, section, id, input_date);
	}

	public List<HoleVO> getHoleStateList(int site_id, int place_id, int section) {	
		return segMapper.getHoleStateList(site_id, place_id, section);
	}	

	public void insertChangeAlarmState(int site_id, String ip, String userId, int isService, int is_web) {
		segMapper.setDidSetting(site_id, isService, isService);
		segMapper.insertChangeDidSettingLog(site_id, 1, ip, userId, isService, is_web);
		segMapper.insertChangeDidSettingLog(site_id, 2, ip, userId, isService, is_web);
	}
	
	public void insertChangeSoundState(int site_id, String ip, String userId, int isService, int is_web) {	
		segMapper.setDidSetting(site_id, -1, isService);
		segMapper.insertChangeDidSettingLog(site_id, 2, ip, userId, isService, is_web);
	}
	
	public FanVO getFanOpenData(int site_id, int place_id, int section, int id) {	
		return segMapper.getFanOpenData(site_id, place_id, section, id);
	}
	
	public List<FanVO> getFanList(int site_id, int place_id, int section) {	
		return segMapper.getFanList(site_id, place_id, section);
	}
		
	public List<FanVO> getFanLogList(int site_id, int place_id, int section, int fan_idx) {	
		return segMapper.getFanLogList(site_id, place_id, section, fan_idx);
	}

	public int getFanOffCount(int site_id, int place_id, int section) {		
		//System.out.println("[COUNT]: " + segMapper.getFanOffCount(site_id, place_id, section));
		return segMapper.getFanOffCount(site_id, place_id, section);
	}
	
	
}
