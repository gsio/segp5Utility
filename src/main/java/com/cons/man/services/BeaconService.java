package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cons.man.persistence.BeaconMapper;
import com.cons.man.persistence.NFCMapper;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.TargetVO;


@Service(value="BeaconService")
public class BeaconService {
	
	@Resource(name="BeaconMapper")
	private BeaconMapper beaconMapper;
	
	@Resource(name="NFCMapper")
	private NFCMapper nfcMapper;
	
	public List<BeaconVO> getBeaconList(int site_id){
		return beaconMapper.getBeaconList(site_id);
	}
	
	public List<BeaconVO> getBeaconList(int site_id, int role, int isUse) {	
		List<BeaconVO> workerList = null;		
		if(isUse == 1) { // 사용중인 비콘 리스트
			workerList = beaconMapper.getUseBeaconList(site_id, role);
		} 
		else if(isUse == 0) { // 미배정 비콘
			workerList = beaconMapper.getUnUseBeaconList(site_id);
		} 
		else { // 전체 비콘 리스트
			workerList = beaconMapper.getBeaconList(site_id);
		}
		return workerList;
	}
	
	public List<TargetVO> getBeaconTargetList(int site_id, int role) {
		return beaconMapper.getBeaconTargetList(site_id, role);
	}	
	
	public int updateBeaconData(int idx, String u_id, int role) {
		return beaconMapper.updateBeaconData(idx, u_id, role);
	}
	
	public void unAssignBeaconData(int idx) {
		System.out.println("[DELETE] - Beacon & NFC: " + idx + "번호");
		// 비콘
		beaconMapper.unAssignBeaconData(idx);
		// NFC 자동 삭제 220921
		nfcMapper.unAssignNFCDataByidx(idx);
	}
	
	public List<BeaconVO> checkBeaconDuplicate(String mac_addr, int idx) {
		return beaconMapper.checkBeaconDuplicate(mac_addr, idx);
	}
	
	public BeaconVO getBeaconMacAddrData(int site_id, int idx) {
		return beaconMapper.getBeaconMacAddrData(site_id, idx);
	}
	
	public void insertBeaconItem(int site_id, int idx, String mac_addr) {
		beaconMapper.insertBeaconItem(site_id, idx, mac_addr);
	}
	
	public void deleteBeaconItem(int idx, String mac_addr) {
		beaconMapper.deleteBeaconItem(idx, mac_addr);
	}
	
	public void updateBeaconMacAddr(int site_id, int idx, String mac_addr) {
		beaconMapper.updateBeaconMacAddr(site_id, idx, mac_addr);
	}	

	public List<BeaconVO> getBeaconListByCont(int site_id, int cont_id){
		//System.out.println("[Service] getBeaconListByCont - site_id: " + site_id + " / cont_id: " + cont_id);
		return beaconMapper.getBeaconListByCont(site_id, cont_id);
	}
	
}
