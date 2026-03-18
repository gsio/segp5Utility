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
	
	@RequestMapping(value = {"/manage/insertSectionData"}, method = RequestMethod.POST)
	public void insertSectionData(HttpServletResponse response, HttpSession session,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="section_name", defaultValue="")String section_name,
		@RequestParam(value="floor", defaultValue="")String floor)
	{	
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");

		manageService.insertSectionData(site_id, section_name, floor, userInfo.getId());

		JSONObject jo = new JSONObject();
		try {
			jo.put("result", "true");
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = {"/manage/updateSectionData"}, method = RequestMethod.POST)
	public void updateSectionData(HttpServletResponse response, HttpSession session,
		@RequestParam(value="id", defaultValue="-1")int id,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="section_name", defaultValue="")String section_name,
		@RequestParam(value="floor", defaultValue="")String floor)
	{	
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");

		manageService.updateSectionData(id, site_id, section_name, floor, userInfo.getId());

		JSONObject jo = new JSONObject();
		try {
			jo.put("result", "true");
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = {"/manage/deleteSectionData"}, method = RequestMethod.POST)
	public void deleteSectionData(HttpServletResponse response, HttpSession session,
		@RequestParam(value="id", defaultValue="-1")int id)
	{	
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");

		manageService.deleteSectionData(id, userInfo.getId());

		JSONObject jo = new JSONObject();
		try {
			jo.put("result", "true");
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


