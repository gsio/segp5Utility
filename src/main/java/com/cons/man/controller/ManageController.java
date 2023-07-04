package com.cons.man.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cons.man.domain.ContVO;
import com.cons.man.domain.DidSettingVO;
import com.cons.man.domain.LocationVO;
import com.cons.man.domain.SectionGroupVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.SensorVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkStateVO;
import com.cons.man.domain.WorkTypeVO;
import com.cons.man.services.ContService;
import com.cons.man.services.DeviceService;
import com.cons.man.services.ManageService;
import com.cons.man.services.SectionService;
import com.cons.man.services.WorkerService;
import com.cons.man.services.seg.SegService;

import org.json.JSONObject;

import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApiIgnore
@Controller(value="ManageController")
public class ManageController {
	
	@Resource(name="ContService")
	private ContService contService;
	
	@Resource(name="SegService")
	private SegService segService;
	
	@Resource(name="ManageService")
	private ManageService manageService;
	
	@Resource(name = "WorkerService")
	private WorkerService workerService;
	
	@Resource(name="DeviceService")
	private DeviceService deviceService;
	
	@Resource(name="SectionService")
	private SectionService sectionService;
	
	
	@RequestMapping(value = { "/stateList" })
	public String stateList(HttpSession session, Model model) {
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");
		List<WorkStateVO> list = manageService.getWorkStateList(userInfo.getSite_id());	
		model.addAttribute("sList", list);
		session.setAttribute("contentView", "stateList");
		return "stateList";
	}
	
	@RequestMapping(value = {"/manage/getSensorList"}, method = RequestMethod.GET)
	public ResponseEntity<List<SensorVO>> getSensorList(HttpServletResponse response, HttpSession session,	
		@RequestParam(value="input_date", defaultValue="")String input_date)
	{			
		List<SensorVO> sensorList = new ArrayList<SensorVO>();
		return new ResponseEntity<List<SensorVO>>(sensorList, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = {"/manage/insertWorkType"}, method = RequestMethod.POST)
	public void insertWorkType(HttpServletResponse response, HttpSession session,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="gubun", defaultValue="1")int gubun,
		@RequestParam(value="t_name", defaultValue="0")String t_name)
	{	
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		
		manageService.insertWorkType(site_id, gubun, t_name, userInfo.getId());		
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("result", "true");
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = {"/manage/insertWorkState"}, method = RequestMethod.POST)
	public void insertWorkState(HttpServletResponse response, HttpSession session,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="color", defaultValue="")String color,
		@RequestParam(value="name", defaultValue="")String name)
	{	
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		//System.out.println("insertWorkState 호출: " + site_id + "/" + color + "/" + name + "/" + userInfo.getId());
		
		manageService.insertWorkState(site_id, color, name, userInfo.getId());		
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("result", "true");
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = {"/manage/deletetWorkType"}, method = RequestMethod.POST)
	public void deletetWorkType(HttpServletResponse response, HttpSession session,
		@RequestParam(value="id", defaultValue="-1")int id)
	{	
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");		
		
		manageService.deletetWorkType(id, userInfo.getId());		
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("result", "true");
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = {"/manage/deletetWorkState"}, method = RequestMethod.POST)
	public void deletetWorkState(HttpServletResponse response, HttpSession session,
		@RequestParam(value="id", defaultValue="-1")int id)
	{	
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");		
		
		manageService.deletetWorkState(id, userInfo.getId());		
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("result", "true");
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = {"/manage/getSectionGroupList"}, method = RequestMethod.GET)
	public ResponseEntity<List<SectionGroupVO>> getSectionGroupList(HttpServletResponse response, HttpSession session,
		@RequestParam(value="site_id", defaultValue="-1")int site_id)
	{			
		List<SectionGroupVO> groupList = manageService.getSectionGroupList(site_id);		
		return new ResponseEntity<List<SectionGroupVO>>(groupList, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/group/name/insert"}, method = RequestMethod.POST)	
	public void insertGroupName(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="writer_id", defaultValue="")String writer_id,
		@RequestParam(value="group_name", defaultValue="")String group_name
		)
	{		
		//System.out.println("[insertGroupName] : " + site_id +"/" + writer_id + "/" + group_name);
		try { 
			manageService.insertGroupName(site_id, writer_id, group_name);
		} catch (Exception e) {
			System.out.println("[ERROR] insertGroupName API: " + e);
		}		
	}
	
	@RequestMapping(value = {"/group/name/update"}, method = RequestMethod.POST)	
	public void updateGroupName(HttpSession session, HttpServletResponse response,
		@RequestParam(value="id", defaultValue="-1")int id,
		@RequestParam(value="name", defaultValue="")String name,
		@RequestParam(value="updater_id", defaultValue="")String updater_id
		)
	{		
		//System.out.println("[updateGroupName] : " + id +"/" + name + "/" + updater_id);
		try { 
			manageService.updateGroupName(id, name, updater_id);
		} catch (Exception e) {
			System.out.println("[ERROR] updateGroupName API: " + e);
		}		
	}
	
	@RequestMapping(value = {"/section/group/list"}, method = RequestMethod.GET)
	public ResponseEntity<List<SectionVO>> getSectionGroupStateList(HttpServletResponse response, HttpSession session,
		@RequestParam(value="site_id", defaultValue="-1")int site_id)
	{			
		List<SectionVO> sectionList = manageService.getSectionGroupStateList(site_id);		
		return new ResponseEntity<List<SectionVO>>(sectionList, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/select/section/group"}, method = RequestMethod.POST)	
	public void selectSectionGroup(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="id", defaultValue="-1")int id,
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="updater_id", defaultValue="")String updater_id)
	{		
		//System.out.println("[selectSectionGroup]: " + site_id + "/" + id + "/" + section + "/" + updater_id);
		try { 
			manageService.selectSectionGroup(site_id, id, section);
			manageService.updateLastUpdater(id, updater_id);
		} catch (Exception e) {
			System.out.println("[ERROR] selectSectionGroup API: " + e);
		}		
	}
	
	@RequestMapping(value = {"/delete/section/group"}, method = RequestMethod.POST)	
	public void deleteSectionGroup(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="id", defaultValue="-1")int id,
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="updater_id", defaultValue="")String updater_id)
	{		
		//System.out.println("[deleteSectionGroup]: "  + site_id + "/" + id + "/" + section + "/" + updater_id);
		try { 
			manageService.deleteSectionGroup(site_id, section);
			manageService.updateLastUpdater(id, updater_id);
		} catch (Exception e) {
			System.out.println("[ERROR] deleteSectionGroup API: " + e);
		}		
	}
	
	@RequestMapping(value = {"/delete/group"}, method = RequestMethod.POST)	
	public void deleteGroup(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="id", defaultValue="-1")int id)
	{		
		//System.out.println("[deleteGroup]: " + site_id + "/" + id);
		try { 
			manageService.deleteGroup(site_id, id);
		} catch (Exception e) {
			System.out.println("[ERROR] deleteGroup API: " + e);
		}		
	}
	
	@RequestMapping(value = {"/manage/insertDidSetting"}, method = RequestMethod.POST)
	public void insertDidSetting(HttpServletResponse response, HttpSession session,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="gubun", defaultValue="2")int gubun,
		@RequestParam(value="type", defaultValue="1")int type,
		@RequestParam(value="start_time", defaultValue="")String start_time,
		@RequestParam(value="end_time", defaultValue="")String end_time)
	{	
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		
		int result = manageService.insertDidSetting(site_id, gubun, type, start_time, end_time, userInfo.getId());		
		
		//System.out.println("[result]: " + result);		
		
		try {
			JSONObject jo = new JSONObject();
			if(result == 200) {			
				jo.put("result", "true");
			}
			else {
				jo.put("result", "false");
			}
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = {"/manage/getMonitorSetting"}, method = RequestMethod.GET)
	public ResponseEntity<List<DidSettingVO>> getMonitorSetting(HttpServletResponse response, HttpSession session,
		@RequestParam(value="site_id", defaultValue="-1")int site_id)
	{			
		List<DidSettingVO> groupList = manageService.getMonitorSetting(site_id);		
		return new ResponseEntity<List<DidSettingVO>>(groupList, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/manage/deleteMonitorSetting"}, method = RequestMethod.POST)	
	public void deleteMonitorSetting(HttpSession session, HttpServletResponse response,
		@RequestParam(value="id", defaultValue="-1")int id)
	{		
		
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");		
		
		manageService.deleteMonitorSetting(userInfo.getSite_id(), id);		
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("result", "true");
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}


