package com.cons.man.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cons.man.domain.DidSettingVO;
import com.cons.man.domain.HoleVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WeatherInfoVO;
import com.cons.man.services.MonitorService;
import com.cons.man.services.UserService;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller(value="MonitorController")
public class MonitorController {

	private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);	
	
	@Resource(name="MonitorService")
	private MonitorService monitorService;	
	
	@Resource(name="UserService")
	private UserService userService;

	@RequestMapping(value = {"/mon"})
	public String mon(HttpServletRequest request, HttpSession session , Model model){
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		HoleVO hole = monitorService.getLastHoleId(16);
		if(hole == null) {
			hole = new HoleVO();
		}
		model.addAttribute("hId", hole.getId());
		monitorService.insertMonitorLog(request.getRemoteAddr(), userInfo.getUserid(), request.getRequestURL().toString(), 1);
		return "monitor/mon";
	}
	
	@RequestMapping(value = {"/seg_mon_1523399"})
	public String mon_16_mobile(HttpServletRequest request, HttpSession session , Model model,
		@RequestParam(value="id", defaultValue="") String id) {		
		try {						
			UserVO vo = userService.getUserById(id);			
			if(vo == null) {
				id = "unknown";
				vo = new UserVO();
				vo.setSite_id(16);
				vo.setId(id);
				monitorService.insertMonitorLog(request.getRemoteAddr(), id, request.getRequestURL().toString(), 0);
			}
			else {
				monitorService.insertMonitorLog(request.getRemoteAddr(), vo.getUserid(), request.getRequestURL().toString(), 0);
			}
			
			HoleVO hole = monitorService.getLastHoleId(16);
			if(hole == null) {
				hole = new HoleVO();
			}
			model.addAttribute("hId", hole.getId());
			model.addAttribute("userLoginInfo", vo);
			model.addAttribute("isMobile", true);			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "monitor/mon";
	}		
	
	@RequestMapping(value = {"/seg_mon_8022123"})
	public String mon_16_web(HttpServletRequest request, HttpSession session , Model model){
		
		try {			
			UserVO vo = new UserVO();
			String id = "unknown";
			vo.setSite_id(16);
			vo.setId(id);
			
			HoleVO hole = monitorService.getLastHoleId(16);
			if(hole == null) {
				hole = new HoleVO();
			}
			monitorService.insertMonitorLog(request.getRemoteAddr(), id, request.getRequestURL().toString(), 1);
			model.addAttribute("hId", hole.getId());
			model.addAttribute("userLoginInfo", vo);
			model.addAttribute("isMobile", false);
		}
		catch (Exception e) {
			// TODO: handle exception
		}	
		
		return "monitor/mon";
	}	
	
	@RequestMapping(value = {"/manage_default"})
	public String manage_default(HttpSession session , Model model) {			
		UserVO vo = new UserVO();
		vo.setSite_id(16);		
		model.addAttribute("userLoginInfo", vo);
		return "manage/default";
	}
	
	@RequestMapping(value = {"/manage_detail"})
	public String manage_device(HttpSession session , Model model) {			
		UserVO vo = new UserVO();
		vo.setSite_id(16);		
		model.addAttribute("userLoginInfo", vo);
		return "manage/detail";
	}
	
	@RequestMapping(value = {"/manage_log"})
	public String manage_log(HttpSession session , Model model) {			
		UserVO vo = new UserVO();
		vo.setSite_id(16);		
		model.addAttribute("userLoginInfo", vo);
		return "manage/log";
	}
	
	// 모바일 앱 개인정보처리방침
	@RequestMapping(value = {"/privacy"})
	public String MobilePrivacy(HttpSession session , Model model) {
		return "etc/privacy";
	}
	
	// 웹 개인정보처리방침
	@RequestMapping(value = {"/policy_privacy"})
	public String policyPrivacy(HttpSession session , Model model) {
		return "etc/policyPrivacy";
	}
	
	// 위치정보처리방침	
	@RequestMapping(value = {"/policy_location"})
	public String policyLocation(HttpSession session , Model model) {
		return "etc/policyLocation";
	}
	
	// 서비스이용방침
	@RequestMapping(value = {"/policy_service"})
	public String policyService(HttpSession session , Model model) {
		return "etc/policyService";
	}
	
	@RequestMapping( value = "/json/getOpenWeatherAPIInfo" )
	public void getOpenWeatherAPIInfo( HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id
	) {		
		WeatherInfoVO vo =  monitorService.getOpenWeatherAPIInfo(site_id);		
		JSONObject jo = new JSONObject(vo);
		jo.put("result", "true");		
		try {
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
