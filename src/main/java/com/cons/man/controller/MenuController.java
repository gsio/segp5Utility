package com.cons.man.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cons.man.domain.ApprovalVO;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.ContVO;
import com.cons.man.domain.EquipVO;
import com.cons.man.domain.FanVO;
import com.cons.man.domain.HoleVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.NoticeVO;
import com.cons.man.domain.RiskVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.SiteVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkStateVO;
import com.cons.man.domain.WorkTypeVO;
import com.cons.man.domain.WorkerVO;
import com.cons.man.services.ApprovalService;
import com.cons.man.services.BeaconService;
import com.cons.man.services.ContService;
import com.cons.man.services.DeviceService;
import com.cons.man.services.EquipService;
import com.cons.man.services.HoleService;
import com.cons.man.services.ManageService;
import com.cons.man.services.NFCService;
import com.cons.man.services.NoticeService;
import com.cons.man.services.RiskService;
import com.cons.man.services.SectionService;
import com.cons.man.services.SiteService;
import com.cons.man.services.UserService;
import com.cons.man.services.WorkerService;

@RestController
@Controller(value="MenuController")
public class MenuController {
	
	@Resource(name="WorkerService")
	private WorkerService workerService;
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	@Resource(name="UserService")
	private UserService userService;
	
	@Resource(name="NoticeService")
	private NoticeService noticeService;
	
	@Resource(name="ContService")
	private ContService contService;
	
	@Resource(name="SectionService")
	private SectionService sectionService;
	
	@Resource(name="BeaconService")
	private BeaconService beaconService;
	
	@Resource(name="NFCService")
	private NFCService nfcService;
	
	@Resource(name="HoleService")
	private HoleService holeService;
	
	@Resource(name="ManageService")
	private ManageService manageService;
	
	@Resource(name="EquipService")
	private EquipService equipService;
	
	@Resource(name="RiskService")
	private RiskService riskService;
	
	@Resource(name="ApprovalService")
	private ApprovalService approvalService;
	
	@Resource(name="DeviceService")
	private DeviceService deviceService;
	
	// Menu - 근로자 관리
	@RequestMapping(value = {"/menu_record"})
	public String recordList(HttpSession session, Model model,
		@RequestParam(value="cont_id", defaultValue="-1")int tar_cont_id) 
	{	
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");		
		boolean isContSelected = false;
		if(tar_cont_id > 0 ) {
			isContSelected = true;
		}
		
		List<WorkerVO> workerList;

		if(userInfo.getCont_type() == 0 || userInfo.getCont_type() == 2 || userInfo.getRole_code() == 3){
			workerList = workerService.getWorkRecord(userInfo.getCompany_id(), userInfo.getSite_id(), isContSelected? tar_cont_id: -1);
		}else{
			workerList = workerService.getWorkRecord(userInfo.getCompany_id(), userInfo.getSite_id(), userInfo.getCont_id());	
		}			
		
		SiteVO siteVO = siteService.getSiteInfo(userInfo.getSite_id());
		model.addAttribute("siteVO", siteVO);	
		
		List<WorkerVO> wtypeList = workerService.getWorkerTypeList();			
		model.addAttribute("wtypeList", wtypeList);
		
		List<ContVO> contList = contService.getContList(userInfo.getSite_id());		
		model.addAttribute("contList", contList);		
		model.addAttribute("workerList", workerList);
		model.addAttribute("tar_cont_id", tar_cont_id);
		session.setAttribute("contentView", "menu_record");
		return "menu_record";
	}
	
	// Menu - 관리자 관리
	@RequestMapping(value = {"/menu_user"})
	public String userList(HttpSession session , Model model,
		@RequestParam(value="cont_id", defaultValue="-1")int tar_cont_id) 
	{
		
		// System.out.println("[Menu] (User) > userList - cont_id: " + tar_cont_id);
		
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		
		List<UserVO> userList = null;
		
		int site_id = userInfo.getSite_id();
		int cont_id = userInfo.getCont_id();
		int cont_type = userInfo.getCont_type();
		
		if(cont_type == 0) {
			if(tar_cont_id > 0) {
				userList = userService.getUserList(site_id, tar_cont_id);
			}
			else {
				userList = userService.getUserList(site_id, -1);
			} 				
		}
		else {
			userList = userService.getUserList(site_id, cont_id);
		}
		
		List<ContVO> contList = contService.getContList(userInfo.getSite_id());
		model.addAttribute("userList", userList);
		model.addAttribute("contList", contList);
		model.addAttribute("tar_cont_id", tar_cont_id);
		session.setAttribute("contentView", "menu_user");
		return "menu_user";
	}

	// Menu - 업체 관리
	@RequestMapping(value = {"/menu_contractor"})
	public String contList(HttpSession session, Model model,
		@RequestParam(value="site_id", defaultValue="-1") int site_id)
	{		
		// System.out.println("[Menu] (Cont) > contList - site_id: " + site_id);
		
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		List<ContVO> contList = null;	
		if(site_id == -1){
			site_id = userInfo.getSite_id();			
		}		
		contList = contService.getContList(site_id);
		model.addAttribute("tar_site_id", site_id);
		SiteVO siteVO = siteService.getSiteInfo(site_id);
		model.addAttribute("siteVO", siteVO);
		model.addAttribute("contList", contList);
		session.setAttribute("contentView", "menu_contractor");
		return "menu_contractor";
	}
	
	// Menu - 공지사항
	@RequestMapping(value = {"/menu_notice"})
	public String noticeList(HttpSession session , Model model,
		@RequestParam(value="cont_id", defaultValue="-1")int tar_cont_id)
	{
		// System.out.println("[Menu] (NOTICE) > noticeList - cont_id: " + tar_cont_id);
		
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");		
		
		List<NoticeVO> noticeList = null;
		
		int site_id = userInfo.getSite_id();
		int cont_id = userInfo.getCont_id();
		int cont_type = userInfo.getCont_type();
		
		List<ContVO> contList = contService.getContList(site_id);
		
		if(cont_type == 0) {
			if(tar_cont_id > 0) {
				noticeList = noticeService.getNoticeList(site_id, tar_cont_id);
			}
			else {
				noticeList = noticeService.getNoticeList(site_id, -1);
			} 				
		}
		else {
			noticeList = noticeService.getNoticeList(site_id, cont_id);
		}	
		
		model.addAttribute("tar_cont_id", tar_cont_id);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("contList", contList);		
		session.setAttribute("contentView", "menu_notice");		
		return "menu_notice";
	}
	
	// Menu - 환경센서 로그
	@RequestMapping(value = { "/menu_sensor_log" })
	public String sensorList(HttpSession session, Model model) {		
		// System.out.println("[Menu] (Sensor) > sensorList");		
		session.setAttribute("contentView", "menu_sensor_log");		
		return "menu_sensor_log";
	}	

	// Menu - QR출입 로그
	@RequestMapping(value = { "/menu_qr_attend_log" })
	public String qrAttendLogList(HttpSession session, Model model) {		
		session.setAttribute("contentView", "menu_qr_attend_log");
		return "menu_qr_attend_log";
	}	
	
	// Menu - 직종 관리
	@RequestMapping(value = { "/menu_wtype" })
	public String wtypeList(HttpSession session, Model model) {		
		//System.out.println("[Menu] (WType) > wtypeList");		
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");
		List<WorkTypeVO> list = manageService.getWorkTypeList(userInfo.getSite_id());	
		model.addAttribute("wList", list);
		session.setAttribute("contentView", "menu_wtype");
		return "menu_wtype";
	}		
	
	@RequestMapping(value = { "/menu_section" })
	public String sectionList(HttpSession session, Model model) 
	{
		//System.out.println("[Menu] (Section) > sectionList");		
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");
		List<SectionVO> section = sectionService.getSectionList(userInfo.getSite_id());		
		model.addAttribute("sList", section);
		session.setAttribute("contentView", "menu_section");
		return "menu_section";
	}
	
	@RequestMapping(value = { "/menu_state" })
	public String stateList(HttpSession session, Model model) {
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");
		List<WorkStateVO> list = manageService.getWorkStateList(userInfo.getSite_id());	
		model.addAttribute("sList", list);
		session.setAttribute("contentView", "menu_state");
		return "menu_state";
	}
	
}


