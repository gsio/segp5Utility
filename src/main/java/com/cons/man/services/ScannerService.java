package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cons.man.persistence.BeaconMapper;
import com.cons.man.persistence.NFCMapper;
import com.cons.man.persistence.ScannerMapper;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.ScannerVO;
import com.cons.man.domain.TargetVO;


@Service(value="ScannerService")
public class ScannerService {
	
	@Resource(name="ScannerMapper")
	private ScannerMapper scannerMapper;
	
	public List<ScannerVO> getScannerList(int site_id){
		return scannerMapper.getScannerList(site_id);
	}

	public List<ScannerVO> getScannerManageList(int site_id){
		return scannerMapper.getScannerManageList(site_id);
	}		

	public void updateScannerTimeOut(int section, int time_out) {
		scannerMapper.updateScannerTimeOut(section, time_out);
	}
	
	public void updateScannerWaitOut(int section, int wait_out) {
		scannerMapper.updateScannerWaitOut(section, wait_out);
	}	
	
	public void insertScannerData(int site_id, String scanner_mac_init, String scanner_mac, String name, int section) {
		scannerMapper.insertScannerData(site_id, scanner_mac_init, scanner_mac, name, section);
		scannerMapper.activeSectionState(site_id, section);
	}
	
	public void deleteScannerData(int site_id, int idx, String scanner_mac, int section) {
		scannerMapper.deleteScannerData(site_id, idx, scanner_mac, section);
		scannerMapper.inActiveSectionState(site_id, section);
	}
	
	
	
}
