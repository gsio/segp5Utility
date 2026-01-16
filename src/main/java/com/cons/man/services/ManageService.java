package com.cons.man.services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cons.man.domain.DidSettingVO;
import com.cons.man.domain.SectionGroupVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.SensorLogVO;
import com.cons.man.domain.WorkStateVO;
import com.cons.man.domain.WorkTypeVO;
import com.cons.man.persistence.ManageMapper;
import com.cons.man.util.JsonReader;
import com.cons.man.util.PrintExcel;

@Service(value="ManageService")
public class ManageService {
	
	@Resource(name="ManageMapper")
	private ManageMapper manageMapper;
	
	public List<WorkStateVO> getWorkStateListFromWeb(int site_id) {	
		return manageMapper.getWorkStateListFromWeb(site_id);
	}	
	
	public List<WorkStateVO> getWorkStateList(int site_id) {	
		return manageMapper.getWorkStateList(site_id);
	}
	
	public List<WorkTypeVO> getWorkTypeList(int site_id) {	
		return manageMapper.getWorkTypeList(site_id);
	}
	
	@Transactional
	public void insertWorkType(int site_id, int gubun, String t_name, String u_id) {
		manageMapper.insertWorkType(site_id, gubun, t_name, u_id);
	}
	
	@Transactional
	public void insertWorkState(int site_id, String color, String name, String u_id) {
		manageMapper.insertWorkState(site_id, color, name, u_id);
	}
	
	@Transactional
	public void deletetWorkType(int id, String u_id) {
		manageMapper.deletetWorkType(id, u_id);
	}
	
	@Transactional
	public void deletetWorkState(int id, String u_id) {
		manageMapper.deletetWorkState(id, u_id);
	}
	
	public List<SectionGroupVO> getSectionGroupList(int site_id) {			
		List<SectionGroupVO> list = manageMapper.getSectionGroupList(site_id);		
		for(SectionGroupVO vo : list) {	
			List<SectionVO> sectionList = new ArrayList<SectionVO>(); 
			sectionList =  manageMapper.getSectionMemberList(vo.getId());
			vo.setSection_member(sectionList);			
		}		
		return list;
	}
	
	public void insertGroupName(int id, String writer_id, String group_name) {
		manageMapper.insertGroupName(id, writer_id, group_name);
	}	

	public void updateGroupName(int id, String name, String updater_id) {
		manageMapper.updateGroupName(id, name, updater_id);
	}
	
	public List<SectionVO> getSectionGroupStateList(int site_id) {	
		return manageMapper.getSectionGroupStateList(site_id);
	}
	
	public void selectSectionGroup(int site_id, int id, int section) {
		manageMapper.selectSectionGroup(site_id, id, section);
	}
	
	public void updateLastUpdater(int id,  String updater_id) {
		manageMapper.updateLastUpdater(id, updater_id);
	}
	
	public void deleteSectionGroup(int site_id, int section) {
		manageMapper.deleteSectionGroup(site_id, section);
	}
	
	public void deleteGroup(int site_id, int id) {
		manageMapper.deleteGroup(site_id, id);
		// 소속그룹 삭제 필요
		manageMapper.initGroupMember(site_id, id);
	}
	
	public ArrayList<SectionVO> getSensorLogUniSection() {	
		
		ArrayList<SectionVO> sectionList = new ArrayList<SectionVO>();
		/*
		try {		
			JSONObject json = JsonReader.readJsonFromUrl("https://segp5.gsil.net:11243/getSensorLogList?input_date=" + date);
			System.out.println("[Sensor-Section] JSON: " + json);
			JSONParser jsonParser = new JSONParser();
			org.json.simple.JSONArray jsonArray = new org.json.simple.JSONArray();
			try {
				jsonArray = (org.json.simple.JSONArray)jsonParser.parse(json.get("sensorList").toString());
				System.out.println("[Sensor-Section] JSON Array Size: " + jsonArray.size());
				
				if(jsonArray.size() > 0) {					
					for(Object object : jsonArray) {
						org.json.simple.JSONObject vo = (org.json.simple.JSONObject) object;
						SectionVO sec = new SectionVO();
		
					}					
				}
				
			} catch (Exception e) {
				System.out.println("[ERROR] getSensorLogUniSection: " + e);
			}
		} catch (Exception e) {
			System.out.println("[ERROR] getSensorLogUniSection: " + e);
		}
		*/
		
		return sectionList;
	}
	
	@Transactional
	public int insertDidSetting(int site_id, int gubun, int type, String start_time, String end_time, String u_id) {		
		try {			
			List<DidSettingVO> list = manageMapper.getDidSettingList(site_id);	
			
			for (DidSettingVO vo : list) {
				 if(isTimeRangeOverlap(start_time, end_time, vo.getStart_time(), vo.getEnd_time())) {
					 throw new RuntimeException("겹치는 시간대가 있습니다. Insert를 막습니다.");
				 }			 
			}			
			manageMapper.insertDidSetting(site_id, gubun, type, start_time, end_time, u_id);
			return 200;			
			
		} catch (Exception e) {
			return -1;
		}
	}
	
	public boolean isTimeRangeOverlap(String newSt, String newEt, String oldSt, String oldEt) {
	    LocalTime startTime1 = LocalTime.parse(newSt);
	    LocalTime endTime1 = LocalTime.parse(newEt);
	    LocalTime startTime2 = LocalTime.parse(oldSt);
	    LocalTime endTime2 = LocalTime.parse(oldEt);	    
	    return startTime1.isBefore(endTime2) && endTime1.isAfter(startTime2);
	}
	
	public List<DidSettingVO> getMonitorSetting(int site_id) {	
		return manageMapper.getDidSettingList(site_id);			
	}
	
	public void deleteMonitorSetting(int site_id, int id) {
		manageMapper.deleteMonitorSetting(site_id, id);
	}
	
	
}
