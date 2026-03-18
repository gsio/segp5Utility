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
	
	public List<WorkTypeVO> getWorkTypeList(int site_id) {	
		return manageMapper.getWorkTypeList(site_id);
	}
	
	@Transactional
	public void insertWorkType(int site_id, int gubun, String t_name, String u_id) {
		manageMapper.insertWorkType(site_id, gubun, t_name, u_id);
	}
	
	@Transactional
	public void deletetWorkType(int id, String u_id) {
		manageMapper.deletetWorkType(id, u_id);
	}
	
	public List<WorkStateVO> getWorkStateListFromWeb(int site_id) {	
		return manageMapper.getWorkStateListFromWeb(site_id);
	}	
	
	public List<WorkStateVO> getWorkStateList(int site_id) {	
		return manageMapper.getWorkStateList(site_id);
	}
	
	@Transactional
	public void insertWorkState(int site_id, String color, String name, String u_id) {
		manageMapper.insertWorkState(site_id, color, name, u_id);
	}
	
	@Transactional
	public void deletetWorkState(int id, String u_id) {
		manageMapper.deletetWorkState(id, u_id);
	}

	@Transactional
	public void insertSectionData(int site_id, String section_name, String floor, String writer_user_id) {
		manageMapper.insertSectionData(site_id, section_name, floor, writer_user_id);
		manageMapper.updateSectionAutoNumber(site_id, writer_user_id);
	}

	@Transactional
	public void updateSectionData(int id, int site_id, String section_name, String floor, String writer_user_id) {
		manageMapper.updateSectionData(id, site_id, section_name, floor, writer_user_id);
	}

	@Transactional
	public void deleteSectionData(int id, String writer_user_id) {
		manageMapper.deleteSectionData(id, writer_user_id);
	}
}
