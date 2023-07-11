package com.cons.man.services;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cons.man.domain.WorkerVO;
import com.cons.man.persistence.WorkerMapper;
import com.cons.man.util.FileWriter;

@Service(value="WorkerService")
public class WorkerService {
	
	@Resource(name="WorkerMapper")
	private WorkerMapper workerMapper;
	
	public String checkWorkerPhone(String phone) {
		JSONObject jo = new JSONObject();		
		List<WorkerVO> workerlist = workerMapper.checkWorkerPhone(phone);
		if( workerlist != null && workerlist.size() > 0) {
			jo.put("result", "true");
			jo.put("item", workerlist);
		}
		else {
			jo.put("result", "false");
		}
		return jo.toString();
	}
	
	public WorkerVO getWorker(int worker_id) {
		return workerMapper.getWorker(worker_id);
	}
	
	public List<WorkerVO> getWorkRecord(int company_id, int site_id, int cont_id) {
		List<WorkerVO> list =  workerMapper.getWorkRecord(site_id, cont_id, false);	
		for(WorkerVO vo : list) {
			String phone = vo.getPhone();
			String mid_num = phone.substring(3, 7);			
			phone = phone.substring(0,3) + "-" + mid_num + "-" + phone.substring(7, phone.length());
			vo.setPhone(phone);
		}		
		return list;
	}
	
	public List<WorkerVO> getWorkerTypeList(){
		return workerMapper.getWorkerTypeList();
	}
	
	@Transactional
	public void insertWorkerList(int site_id, int cont_id, List<WorkerVO> workerList){
		for(WorkerVO vo : workerList){			
			if(vo.getJumin().length() >= 6  && vo.getPhone().length() >= 10 && vo.getName().length() >= 1){
				int jumin_back = getJumin_BackNumber(vo);
				vo.setJumin(vo.getJumin() + jumin_back);
				vo.setSite_id(site_id);
				vo.setCont_id(cont_id);
				insertWorker(vo);	
			}
		}
	}
	
	@Transactional
	public void insertWorkerExcelList(int site_id, int cont_id, List<WorkerVO> workerList, String uuid){
		for(WorkerVO vo : workerList){			
			if(vo.getJumin().length() >= 6  && vo.getPhone().length() >= 10 && vo.getName().length() >= 1){
				int jumin_back = getJumin_BackNumber(vo);
				vo.setJumin(vo.getJumin() + jumin_back);
				vo.setSite_id(site_id);
				vo.setCont_id(cont_id);
				vo.setPhoto("WORKER/" + uuid + "_" + vo.getPhoto());
				insertWorkerExcel(vo);	
			}
		}
	}
	
	@Transactional
	public void insertWorkerExcel(WorkerVO vo){		
		if(vo.getPhoto() != null) {			
			String virtName = "WORKER/WORKER_" + UUID.randomUUID().toString() +"_" + vo.getId();
			vo.setEduimage(virtName);
			MultipartFile photoFile = FileWriter.INSTANCE.getImageForGsilDir(vo.getPhoto());
			if(photoFile != null && photoFile.getSize() > 0) {			
				FileWriter.INSTANCE.writeFile(photoFile, virtName);
			}
		}		
		workerMapper.insertWorker(vo);		
	}	
	
	@Transactional
	public void insertWorker(WorkerVO vo){		
		if(vo.getEduimage_file() != null && vo.getEduimage_file().getSize() > 0) {			
			String virtName = "WORKER/WORKER_" + UUID.randomUUID().toString() +"_" + vo.getId();
			vo.setEduimage(virtName);
			FileWriter.INSTANCE.writeFile(vo.getEduimage_file(), virtName);
		}		
		workerMapper.insertWorker(vo);
	}

	@Transactional
	public void updateRecord(List<WorkerVO> workerList) {
		Iterator<WorkerVO> it  = workerList.iterator();
		while(it.hasNext()) {
			WorkerVO vo = it.next();
			workerMapper.updateRecord(vo);	
		}
	
	}
	
	public List<WorkerVO> getWorkerListByContId(WorkerVO vo) {
		return workerMapper.getWorkerListByContId(vo);
	}
	
	public List<WorkerVO> getBeaconWorkerList(int site_id, int cont_id) {
		
		List<WorkerVO> list = null;
		
		WorkerVO searchVO = new WorkerVO();
		searchVO.setSite_id(site_id);
		searchVO.setCont_id(cont_id);		
		list = regBlockPhone(workerMapper.getWorkerListByContId(searchVO));
		
		/*
		if (list != null && list.size() > 0) {		
			for (WorkerVO vo : list) {				
				String phone="";
				if(vo.getPhone().length() > 7){
					String phone_head = vo.getPhone().substring(0, 3);
					String phone_tail = vo.getPhone().substring(7, vo.getPhone().length());
					 phone = phone_head + "-****-" + phone_tail;
					
				}else{
					phone = vo.getPhone();
				}
				vo.setPhone(phone);
			}
		}
		*/
		return list;
	}
	
	private int getJumin_BackNumber(WorkerVO vo){
		 
		boolean isMale = false;
		if(vo.getJumin_back() == 1){//남
			isMale = true;
		}
		else if(vo.getJumin_back() == 2){//여
			isMale = false;
		}
		else{//선택이 항상되야겠지만 기본값은 남자로
			isMale = true;
			//System.out.println("do not select male/female");
		}		
		
		int year = Integer.parseInt(vo.getJumin().substring(0,2)) ;
			
		boolean isForeign = false;
		
		if(vo.getGubun().equals("0")){//내국인
			isForeign = false;
		}
		else if(vo.getGubun().equals("1")){//외국인
			isForeign = true;
		}
		else{
			isForeign = false;
		}
		
		// 1 남자, 2여자, 3 2000년 남자 , 4 2000년 여자, 5 외국인남자, 6 외국인 여자, 7 외국인 2000년 남자, 8 외국인 2000년 여자, 9 1800년대 남자, 0 1800년대 여자
		int jumin_back = 1;
		if(isMale){//male
			if(year > 40){//주민번호 앞자리가 40이상인경우..즉 1999년 이전..
				if(isForeign){
					jumin_back = 5;
				}else{
					jumin_back = 1;
				}
			}else{//즉, 2000년대생 대상 (1940년이하가 있을리는없으니)
				if(isForeign){
					jumin_back = 7;
				}else{
					jumin_back = 3;
				}
			}
		}
		
		else{
			if(year > 40){
				if(isForeign){
					jumin_back = 6;
				}else{
					jumin_back = 2;
				}
			}else{//2000이후
				if(isForeign){
					jumin_back = 8;
				}else{
					jumin_back = 4;
				}
			}
		}
		
		return jumin_back;
	}
	
	public void deleteWorker(int site_id, int id) {
		workerMapper.deleteWorker(site_id, id);		
	}
	
	public void updateEduImageByWorkerId(int worker_id, String virtName) {
		WorkerVO vo = new WorkerVO();
		vo.setId(worker_id + "");
		vo.setEduimage(virtName);
		workerMapper.updateEduImageByWorkerId(vo);
	}
	
	public List<WorkerVO> getDriverList(int site_id, int cont_id) {
		return regPhone(workerMapper.getDriverList(site_id, cont_id));
	}
	
	private List<WorkerVO> regPhone(List<WorkerVO> list) {		
		if (list != null && list.size() > 0) {		
			for (WorkerVO vo : list) {				
				String phone="";
				if(vo.getPhone().length() > 7){
					String phone_head = vo.getPhone().substring(0, 3);
					String phone_body = vo.getPhone().substring(3, 7);
					String phone_tail = vo.getPhone().substring(7, vo.getPhone().length());
					 phone = phone_head + "-"+phone_body+"-" + phone_tail;					
				}
				else{
					phone = vo.getPhone();
				}
				vo.setPhone(phone);
			}
		}		
		return list;
	}
	
	private List<WorkerVO> regBlockPhone(List<WorkerVO> list) {		
		if (list != null && list.size() > 0) {		
			for (WorkerVO vo : list) {				
				String phone="";
				if(vo.getPhone().length() > 7){
					String phone_head = vo.getPhone().substring(0, 3);
					String phone_tail = vo.getPhone().substring(7, vo.getPhone().length());
					 phone = phone_head + "-XXXX-" + phone_tail;
					
				}
				else{
					phone = vo.getPhone();
				}
				vo.setPhone(phone);
			}
		}		
		return list;
	}
	
	
	public List<WorkerVO> getWTList(){
		return workerMapper.getWTList();
	}
}
