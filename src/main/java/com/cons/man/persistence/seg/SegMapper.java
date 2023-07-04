package com.cons.man.persistence.seg;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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

@Repository(value="SegMapper")
public interface SegMapper {
	
	// TABLET START
	
	public List<SectionVO> getSectionInfo(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section, @Param("has_env")int has_env);
	
	public List<Integer> getSectionGroupList(@Param("section")int section);
			
	public List<LocationVO> getLocationInfo(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section, @Param("input_state")int input_state);
	
	public List<LocationVO> getGroupLocationList(LocationVO vo);	
	
	public List<EmergencyVO> getEmergencyInfo(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section, @Param("limit")int limit);
	
	public void changeState(StateVO vo);
	
	public List<SensorVO> getSensorList(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section, @Param("has_section")int has_section);
	
	public List<AbnormalSensorVO> getAbnormalSensorList(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section, @Param("limit")int limit);

	public NfcLocationVO checkNfcInoutInfo(@Param("site_id")int site_id, @Param("serial_number")String serial_number);	
	
	public NFCVO checkNfcState(@Param("site_id")int site_id, @Param("serial_number")String serial_number);
	
	public List<WorkStateVO> getWorkStateList(@Param("site_id")int site_id);	
	
	public List<NfcLocationVO> getWaitList(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section);
	
	public HoleVO getHoleOpenData(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section, @Param("id")int id);
	
	public DidSettingVO getDidSettingData(@Param("site_id")int site_id, @Param("id")int id);
	
	
	// TABLET END
	
	public void insertAlert(AlertVO vo);
	
	public void updateBeaconRssiAdd(@Param("mac_addr")String mac_addr, @Param("rssi_add")int rssi_add);
		
	public void exitWait(@Param("u_id")String u_id, @Param("role")int role);
	
	public void exitInout(@Param("beacon_mac")String beacon_mac);
	
	public void exitLocation(@Param("beacon_mac")String beacon_mac);
	
	public List<RssiBlockVO> getRssiBlockList(@Param("site_id")int site_id);
		
	public List<ScannerStateVO> getDeviceStateData(@Param("site_id")int site_id);	

	public void updateStateService(@Param("site_id")int site_id, @Param("section")int section, @Param("isService")int isService);
	
	public void updateSensorService(@Param("site_id")int site_id, @Param("section")int section, @Param("isService")int isService);
	
	public void updateSectionSensorPush(@Param("site_id")int site_id, @Param("section")int section, @Param("isService")int isService);	
	
	public void deleteSensorData(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section);
		
	public List<HoleVO> getHoleManageList(@Param("site_id")int site_id);	
	
	public List<HoleVO> getHoleLog(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section, @Param("id")int id, @Param("input_date")String input_date);
	
	public List<HoleVO> getHoleStateList(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section);	

	public void insertChangeDidSettingLog(@Param("site_id")int site_id, @Param("type")int type, @Param("ip")String ip, @Param("userid")String userid, @Param("is_service")int is_service, @Param("is_web")int is_web);
	
	public void setDidSetting(@Param("site_id")int site_id, @Param("alarm_state")int alarm_state, @Param("sound_state")int sound_state);
	
	public FanVO getFanOpenData(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section, @Param("id")int id);
	
	public List<FanVO> getFanList(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section);	

	public List<FanVO> getFanLogList(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section, @Param("fan_idx")int fan_idx);	
	
	public int getFanOffCount(@Param("site_id")int site_id, @Param("place_id")int place_id, @Param("section")int section);	
	
}