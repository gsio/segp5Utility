package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.cons.man.domain.AppVersionCheckVO;
import com.cons.man.domain.TargetVO;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.ContVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkTypeVO;
import com.cons.man.domain.WorkerVO;
import com.cons.man.persistence.MobileMapper;

@Service(value="MobileService")
public class MobileService {
	
	@Resource( name="MobileMapper" )
	private MobileMapper mobileMapper;	
	
	public AppVersionCheckVO getCurrentAppVersion() {
		return mobileMapper.getCurrentAppVersion();
	}

	public void updateLoginInfo(String id, String fcm_token) {
		mobileMapper.updateLoginInfo(id, fcm_token);
	}
	
	public void insertUserAppVersion(AppVersionCheckVO vo){
		mobileMapper.insertUserAppVersion(vo);
	}
	
	public List<UserVO> getUserList(int site_id, int cont_id, int cont_type) {			
		List<UserVO> userList = null;		
		if(cont_type == 0) {
			userList = mobileMapper.getUserList(site_id, -1);
		} else {
			userList = mobileMapper.getUserList(site_id, cont_id);
		}
		return userList;
	}
	
	public List<ContVO> getContList(int site_id, int role) {
		List<ContVO> contList = null;
		if(role == 1) {
			contList = mobileMapper.getUserContList(site_id);
		} else if (role == 2) {
			contList = mobileMapper.getWorkerContList(site_id);
		} else {
			contList = mobileMapper.getContList(site_id);
		}
		return contList;
	}
	
	public List<WorkerVO> getWorkerList(int site_id, int cont_id, int cont_type) {	
		List<WorkerVO> workerList = null;		
		if(cont_type == 0) {
			workerList = mobileMapper.getWorkerList(site_id, -1);
		} else {
			workerList = mobileMapper.getWorkerList(site_id, cont_id);
		}
		return workerList;
	}
	
	public List<WorkTypeVO> getWorkTypeList(int site_id) {	
		return mobileMapper.getWorkTypeList(site_id);
	}
	
	public String insertWorker(WorkerVO worker) {
		JSONObject jo = new JSONObject();		
		int resultInt = 0;	
			resultInt = mobileMapper.insertWorker(worker);			
		if( resultInt > 0  ) {
			jo.put("result", "true");
			jo.put("worker_id", worker.getId());
		} else {
			jo.put("result", "false");
		}
		return jo.toString();
	}
	
	public String updateWorker(WorkerVO worker) {	
		JSONObject jo = new JSONObject();		
		int resultInt = 0;	
			resultInt = mobileMapper.updateWorker(worker);	
		if( resultInt > 0  ) {
			jo.put("result", "true");
		} else {
			jo.put("result", "false");
		}
		return jo.toString();
	}
	
	public String checkPhoneNumber(String phone) {	
		JSONObject jo = new JSONObject();		
		WorkerVO worker = mobileMapper.checkPhoneNumber(phone);		
		if(worker != null && (worker.getId() != null && !worker.getId().equals(""))) {
			jo.put("result", "true");
			jo.put("id", worker.getId());
			jo.put("site_id", worker.getSite_id());
			jo.put("cont_id", worker.getCont_id());
			jo.put("phone", worker.getPhone());
			jo.put("gubun", worker.getGubun());
			jo.put("name", worker.getName());
			jo.put("jumin", worker.getJumin());
			jo.put("t_id", worker.getT_id());			
		} else {
			jo.put("result", "false");
		}		
		return jo.toString();
	}
	
}
