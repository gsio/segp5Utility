package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.cons.man.persistence.BeaconMapper;
import com.cons.man.persistence.HoleMapper;
import com.cons.man.persistence.NFCMapper;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.HoleVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.TargetVO;


@Service(value="HoleService")
public class HoleService {
	
	@Resource(name="HoleMapper")
	private HoleMapper holeMapper;
	
	public List<HoleVO> getHoleList(int site_id, int isUse){
		 List<HoleVO> holeList = null;
		 
		if(isUse == 1) { // 사용중
			holeList = holeMapper.getUseHoleList(site_id);
		} 
		else if(isUse == 0) { // 미배정
			holeList = holeMapper.getUnUseHoleList(site_id);
		} 
		else { // 전체 
			holeList = holeMapper.getHoleList(site_id);
		}
		return holeList;
	}

	public List<HoleVO> getHoleAssignSectionList(int site_id){
		return holeMapper.getHoleAssignSectionList(site_id);
	}	
	
	public List<HoleVO> getHoleManageList(int site_id){
		return holeMapper.getHoleManageList(site_id);
	}
	
	public List<HoleVO> checkHoleDuplicate(String mac_addr, int idx) {
		return holeMapper.checkHoleDuplicate(mac_addr, idx);
	}	
	
	public HoleVO getHoleMacAddrByIdx(int site_id, int idx) {
		return holeMapper.getHoleMacAddrByIdx(site_id, idx);
	}
	
	public void insertHoleItem(int site_id, int idx, String mac_addr) {
		holeMapper.insertHoleItem(site_id, idx, mac_addr);
	}
	
	public void deleteHoleItem(int idx, String mac_addr) {
		holeMapper.deleteHoleItem(idx, mac_addr);
	}
	
	public void updateHoleMacAddr(int site_id, int idx, String mac_addr) {
		holeMapper.updateHoleMacAddr(site_id, idx, mac_addr);
	}
	
	public void unAssignHoleData(int id) {
		holeMapper.unAssignHoleData(id);
	}
	
	public void updateHoleData(int id, int section) {
		holeMapper.updateHoleData(id, section);
	}
	
	public void updateHoleName(int id, String name) {
		holeMapper.updateHoleName(id, name);
	}	
}
