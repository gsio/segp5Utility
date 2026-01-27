package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cons.man.persistence.BeaconMapper;
import com.cons.man.persistence.NFCMapper;
import com.cons.man.persistence.QRMapper;
import com.cons.man.persistence.SectionMapper;
import com.cons.man.persistence.SensorMapper;
import com.cons.man.util.UIDMaker;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.CertkeyVO;
import com.cons.man.domain.ContVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.QrVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.SensorVO;
import com.cons.man.domain.TargetVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkerVO;


@Service(value="QRService")
public class QRService {
	
	@Resource(name="QRMapper")
	private QRMapper qrMapper;
	
	public int checkUWDuplicatePhone(String phone) {
		int resultInt = qrMapper.checkUWDuplicatePhone(phone);
		System.out.println("[checkUWDuplicatePhone]: " + resultInt);			
		return resultInt;
	}	
		
	@Transactional
	public CertkeyVO insertUWData(int role, String name, String phone, int cont_id, String jumin, int jumin_back, int type) {
		
		CertkeyVO vo = new CertkeyVO();
						
		// 관리자
		if(role == 1) {
			String phoneStr = "";			
			UserVO user = new UserVO();
			String user_id = UIDMaker.makeNewUID("U");
			user.setId(user_id);
			phoneStr = phone.substring(0,3) + "-" + phone.substring(3,7) + "-" + phone.substring(7, phone.length());
			user.setPhone(phoneStr);
			user.setName(name);
			user.setRole_code(type);
			user.setCont_id(cont_id);
			user.setUserid(phone);
			user.setPassword(jumin);
			qrMapper.insertUserTemp(user);
			vo.setUw_id(user.getId());
			vo.setRole(1);
			return vo;
		}
		// 근로자
		else {
			WorkerVO worker = new WorkerVO();
			worker.setPhone(phone);
			worker.setJumin(jumin);
			worker.setSite_id(1);
			worker.setCont_id(cont_id);
			worker.setGubun("0");
			worker.setName(name);
			worker.setT_id(type); // 근로자 직종 고정
			worker.setJumin_back(jumin_back);
			int juminBack = getJuminBack(worker);
			worker.setJumin(worker.getJumin() + juminBack);
			qrMapper.insertWorkerTemp(worker);
			vo.setUw_id(worker.getId());
			vo.setRole(2);
			return vo;
		}		
	}
	
	private int getJuminBack(WorkerVO vo){
		 
		boolean isMale = false;
		if(vo.getJumin_back() == 1){
			isMale = true;
		}
		else if(vo.getJumin_back() == 2){
			isMale = false;
		}
		else {
			isMale = true;
		}		
		
		int year = Integer.parseInt(vo.getJumin().substring(0,2)) ;
			
		boolean isForeign = false;
		
		if(vo.getGubun().equals("0")){
			isForeign = false;
		}
		else if(vo.getGubun().equals("1")){
			isForeign = true;
		}
		else{
			isForeign = false;
		}
		
		int jumin_back = 1;
		if(isMale){
			if(year > 40){
				if(isForeign){
					jumin_back = 5;
				} 
				else{
					jumin_back = 1;
				}
			} 
			else {
				if(isForeign){
					jumin_back = 7;
				} 
				else{
					jumin_back = 3;
				}
			}
		}		
		else {
			if(year > 40){
				if(isForeign){
					jumin_back = 6;
				} else{
					jumin_back = 2;
				}
			} 
			else {//2000이후
				if(isForeign) {
					jumin_back = 8;
				}
				else{
					jumin_back = 4;
				}
			}
		}		
		return jumin_back;
	}
	
	public int insertQRInData(int site_id, String uw_id, int role) {
		int result = qrMapper.insertQRInData(site_id, uw_id, role);
		qrMapper.insertQRInoutLog(site_id, uw_id, role, 1, "");
		return result;
	}	
	
	public int insertQROutData(int site_id, String uw_id, int role, String comment) {
		System.out.println(site_id + "/" + uw_id + "/" + role);
		int result = qrMapper.insertQROutData(site_id, uw_id, role);
		qrMapper.insertQRInoutLog(site_id, uw_id, role, 2, comment);
		return result;
	}
	
	public List<QrVO> getQRInoutLogToday(int site_id) {
		List<QrVO> list = qrMapper.getQRInoutLogToday(site_id);		
		return list;
	}	
	
	public List<QrVO> getQRInoutLogList(int site_id, String input_date) {
		List<QrVO> list = qrMapper.getQRInoutLogList(site_id, input_date);		
		return list;
	}	
}
