package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cons.man.domain.TargetVO;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.FanVO;
import com.cons.man.domain.HoleVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.PatchVO;
import com.cons.man.domain.ScannerVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.SensorVO;
import com.cons.man.domain.SiteVO;
import com.cons.man.persistence.DeviceMapper;
import com.cons.man.persistence.SiteMapper;


@Service(value="DeviceService")
public class DeviceService {

	@Resource(name="DeviceMapper")
	private DeviceMapper deviceMapper;
	
	@Resource(name="SiteMapper")
	private SiteMapper siteMapper;
	
	public List<PatchVO> getPatchList(int site_id){
		return deviceMapper.getPatchList(site_id);
	}
	
	public void deleteDeviceState(String scanner_mac, int app_type ,int phone_idx) {
		deviceMapper.deleteDeviceState(scanner_mac, app_type, phone_idx);
	}
	
	public List<FanVO> getFanList(int site_id){
		return deviceMapper.getFanList(site_id);
	}
	
	public List<FanVO> getFanAllotList(int site_id){
		return deviceMapper.getFanAllotList(site_id);
	}
	
	public void deleteFanBeaconByid(int id) {
		deviceMapper.deleteFanBeaconByid(id);
	}
	
	public void updateFanBeacon(int id, int section) {
		deviceMapper.updateFanBeacon(id, section);
	}
	
	public void updateFanName(int id, String name) {
		deviceMapper.updateFanName(id, name);
	}
}
