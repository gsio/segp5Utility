package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cons.man.persistence.NFCMapper;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.NfcLocationVO;
import com.cons.man.domain.TargetVO;

@Service(value="NFCService")
public class NFCService {
	
	@Resource(name="NFCMapper")
	private NFCMapper nfcMapper;
	
	public List<NFCVO> getNFCList(int site_id, int role, int isUse){
		List<NFCVO> list = null;		
		if(isUse == 1) { // 사용중인 비콘 리스트
			list = nfcMapper.getUseNFCList(site_id, role);
		} 
		else if(isUse == 0) { // 미배정 비콘
			list = nfcMapper.getUnUseNFCList(site_id);
		} 
		else { // 전체 비콘 리스트
			list = nfcMapper.getNFCList(site_id);
		}
		return list;
	}	
	
	// (Web)
	public List<NFCVO> getNFCListBySiteId(int site_id){
		return nfcMapper.getNFCList(site_id);
	}
	
	public List<TargetVO> getNFCTargetList(int site_id, int role) {
		return nfcMapper.getNFCTargetList(site_id, role);
	}	
	
	public void createNFCData(int site_id, String serial_string) {
		nfcMapper.createNFCData(site_id, setSerialData(serial_string)); 
	}
	
	public void createNFCDataByIdx(int site_id, int idx, String serial_string) {
		nfcMapper.createNFCDataByIdx(site_id, idx, setSerialData(serial_string)); 
	}
	
	public int updateNFCData(int idx, String u_id, int role) {
		return nfcMapper.updateNFCData(idx, u_id, role);
	}
	
	public NFCVO checkNFCDuplicate(int site_id, String serial_string) {	
		return nfcMapper.checkNFCDuplicate(site_id, setSerialData(serial_string));
	}
	
	public NFCVO checkNFCState(int site_id, String serial_string) {	
		return nfcMapper.checkNFCState(site_id, setSerialData(serial_string));
	}
	
	public List<NFCVO> checkNFCDuplicateByIdx(String serial_number, int idx) {	
		return nfcMapper.checkNFCDuplicateByIdx(serial_number, idx);
	}
	
	public void unAassignNFCData(String serial_number, int idx) {
		nfcMapper.unAassignNFCData(serial_number, idx);
	}	
	
	public List<Integer> getRemainNFCList(int site_id) {
		return nfcMapper.getRemainNFCList(site_id);
	}
	
	public int checkNFCUpdateData(int idx) {
		try {
			int value = nfcMapper.checkNFCUpdateData(idx);
			return value;
		} catch (NullPointerException e) {
			return 0;
		}	
	}
	
	public void insertNFCItem(int site_id, int idx, String serial_number) {
		nfcMapper.insertNFCItem(site_id, idx, serial_number);
	}
	
	public void deleteNFCItem(int idx, String serial_number) {
		nfcMapper.deleteNFCItem(idx, serial_number);
	}	
	
	public void updateNFCByIdx(int site_id, int idx, String serial_number) {
		nfcMapper.updateNFCByIdx(site_id, idx, serial_number);
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

	public List<NFCVO> getNFCListByCont(int site_id, int cont_id){
		//System.out.println("[Service] getNFCListByCont - site_id: " + site_id + " / cont_id: " + cont_id);
		return nfcMapper.getNFCListByCont(site_id, cont_id);
	}
	
	public List<NfcLocationVO> getWaitList(int site_id, int place_id, int section) {	
		return nfcMapper.getWaitList(site_id, place_id, section);
	}
	

	public void postWaitOut(String uw_id, int role, String serial_number) {
		nfcMapper.postWaitOut(uw_id, role, serial_number);
	}

	
}
