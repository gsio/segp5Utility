package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.TargetVO;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.FanVO;
import com.cons.man.domain.HoleVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.PatchVO;
import com.cons.man.domain.ScannerVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.SensorVO;

@Repository(value="DeviceMapper")
public interface DeviceMapper {
	
	// Patch
	public List<PatchVO> getPatchList(@Param("site_id")int site_id);
	
	public void deleteDeviceState(@Param("scanner_mac")String scanner_mac, @Param("app_type")int app_type, @Param("phone_idx")int phone_idx);
	
	public List<FanVO> getFanList(@Param("site_id")int site_id);	

	public List<FanVO> getFanAllotList(@Param("site_id")int site_id);
	
	public void deleteFanBeaconByid(@Param("id")int id);
	
	public void updateFanBeacon(@Param("id")int id, @Param("section")int section);
	
	public void updateFanName(@Param("id")int id, @Param("name")String name);
	
}
