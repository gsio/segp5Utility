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
	@RequestMapping(value = {"/recordList"})
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
	@RequestMapping(value = {"/userList"})
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
	@RequestMapping(value = {"/contList"})
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
	@RequestMapping(value = {"/noticeList"})
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
		session.setAttribute("contentView", "noticeList");		
		return "noticeList";
	}
	
	// Menu - 환경센서 로그
	@RequestMapping(value = { "/sensorList" })
	public String sensorList(HttpSession session, Model model) {		
		// System.out.println("[Menu] (Sensor) > sensorList");		
		session.setAttribute("contentView", "sensorList");		
		return "sensorList";
	}	

	// Menu - 구역출입 로그
	@RequestMapping(value = { "/fanLogList" })
	public String fanLogList(HttpSession session, Model model) {		
		session.setAttribute("contentView", "fanLogList");
		return "fanLogList";
	}
	
	
	// Menu - 구역 그룹 관리
	/*
	@RequestMapping(value = { "/sectionList" })
	public String sectionList(HttpSession session, Model model) 
	{
		// System.out.println("[Menu] (Section) > sectionList");
		
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");
		List<SectionVO> section =  sectionService.getSectionList(userInfo.getSite_id());		
		model.addAttribute("sList", section);
		session.setAttribute("contentView", "sectionList");
		return "sectionList";
	}
	
	// Menu - 비콘 배정 관리
	@RequestMapping(value = { "/beaconList" })
	public String beaconList(HttpSession session, Model model,
		@RequestParam(value="cont_id", defaultValue="-1")int tar_cont_id) 
	{
		// System.out.println("[Menu] (Beacon) > beaconList - cont_id: " + tar_cont_id);
		
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");
		List<ContVO> contList = contService.getContList(userInfo.getSite_id());
		List<BeaconVO> beaconList = null;
		if(tar_cont_id > 0) {
			beaconList = beaconService.getBeaconListByCont(userInfo.getSite_id(), tar_cont_id);
		}
		else {
			beaconList = beaconService.getBeaconListByCont(userInfo.getSite_id(), -1);
		}	
		List<WorkerVO> workerList = workerService.getBeaconWorkerList(userInfo.getSite_id(), -1);
		List<UserVO> userList = userService.getBeaconUserList(userInfo.getSite_id(), -1);
		model.addAttribute("contList", contList);
		model.addAttribute("beaconList", beaconList);
		model.addAttribute("workerList", workerList);
		model.addAttribute("userList", userList);
		model.addAttribute("tar_cont_id", tar_cont_id);
		session.setAttribute("contentView", "beaconList");
		return "beaconList";
	}
	
	// Menu - NFC 배정 관리
	@RequestMapping(value = { "/nfcList" })
	public String nfcList(HttpSession session, Model model,
		@RequestParam(value="cont_id", defaultValue="-1")int tar_cont_id)
	{
		// System.out.println("[Menu] (NFC) > nfcList - cont_id: " + tar_cont_id);
		
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");
		List<ContVO> contList = contService.getContList(userInfo.getSite_id());				
		List<NFCVO> nfcList =  null;
		if(tar_cont_id > 0) {
			nfcList = nfcService.getNFCListByCont(userInfo.getSite_id(), tar_cont_id);
		}
		else {
			nfcList = nfcService.getNFCListBySiteId(userInfo.getSite_id());		
		}
	
		List<WorkerVO> workerList = workerService.getBeaconWorkerList(userInfo.getSite_id(), -1);
		List<UserVO> userList = userService.getBeaconUserList(userInfo.getSite_id(), -1);
		model.addAttribute("contList", contList);
		model.addAttribute("nfcList", nfcList);
		model.addAttribute("workerList", workerList);
		model.addAttribute("userList", userList);
		model.addAttribute("tar_cont_id", tar_cont_id);
		session.setAttribute("contentView", "nfcList");
		return "nfcList";
	}
	
	//Menu - 개구부 관리
	@RequestMapping(value = { "/holeList" })
	public String holeManaholeListgeList(HttpSession session, Model model) {
		
		// System.out.println("[Menu] (Hole) > holeList");
		
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");			
		List<HoleVO> holeList = holeService.getHoleList(userInfo.getSite_id(), -1);
		List<HoleVO> sectionList = holeService.getHoleAssignSectionList(userInfo.getSite_id());			
		model.addAttribute("holeList", holeList);
		model.addAttribute("sectionList", sectionList);
		session.setAttribute("contentView", "holeList");
		return "holeList";
	}
	
	// Menu - 직종관리
	@RequestMapping(value = { "/wtypeList" })
	public String wtypeList(HttpSession session, Model model) {
		
		// System.out.println("[Menu] (WType) > wtypeList");
		
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");
		List<WorkTypeVO> list = manageService.getWorkTypeList(userInfo.getSite_id());	
		model.addAttribute("wList", list);
		session.setAttribute("contentView", "wtypeList");
		return "wtypeList";
	}		
	
	// Menu - 위치파악 로그
	@RequestMapping(value = { "/locationList" })
	public String locationList(HttpSession session, Model model) {
		session.setAttribute("contentView", "locationList");
		return "locationList";
	}
	
	// Menu - 구역출입 로그
	@RequestMapping(value = { "/inoutLogList" })
	public String inoutLogList(HttpSession session, Model model) {		
		session.setAttribute("contentView", "inoutLogList");
		return "inoutLogList";
	}
	
	*/
	

	

	
	
	// Menu - 작업허가서
	@RequestMapping(value = {"/ptwList"})
	public String ptwList(HttpSession session , Model model,
		@RequestParam(value="state", defaultValue="-1")int tar_state)
	{
		System.out.println("[Menu] (PTW) state: " + tar_state);
		/*
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");		
		
	
		List<NoticeVO> noticeList = null;
		
		int site_id = userInfo.getSite_id();
		int cont_id = userInfo.getCont_id();
		int cont_type = userInfo.getCont_type();
		
		List<ContVO> contList = contService.getContList(site_id);
		
		if(cont_type == 0) {
			if(tar_state > 0) {
				noticeList = noticeService.getNoticeList(site_id, tar_state);
			}
			else {
				noticeList = noticeService.getNoticeList(site_id, -1);
			} 				
		}
		else {
			noticeList = noticeService.getNoticeList(site_id, cont_id);
		}	
		*/
		
		model.addAttribute("tar_state", tar_state);		
		session.setAttribute("contentView", "ptwList");		
		return "ptwList";
	}
	
	// Menu - 반입전 장비 목록
	@RequestMapping(value = {"/equipList"})
	public String equipList(HttpSession session , Model model,
		@RequestParam(value="cont_id", defaultValue="-1")int tar_cont_id)
	{
		//System.out.println("[Menu] (Equip) > equipList - cont_id: " + tar_cont_id);
		
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		
		int site_id = userInfo.getSite_id();
		int cont_id = userInfo.getCont_id();
		
		List<ContVO> contList = contService.getContList(site_id);		
		
		List<EquipVO> equipList = null;
		
		int cont_type = userInfo.getCont_type();
		
		if(cont_type == 0) {
			if(tar_cont_id > 0) {
				equipList = equipService.getEquipList(site_id, tar_cont_id);
			}
			else {
				equipList = equipService.getEquipList(site_id, -1);
			} 				
		}
		else {
			equipList = equipService.getEquipList(site_id, cont_id);
		}	
		
		model.addAttribute("equipList", equipList);	
		model.addAttribute("tar_cont_id", tar_cont_id);
		model.addAttribute("contList", contList);		
		session.setAttribute("contentView", "equipList");		
		return "equipList";
	}
	

	// Menu - 반입전 장비 목록
	@RequestMapping(value = {"/riskList"})
	public String riskList(HttpSession session , Model model,
		@RequestParam(value="cont_id", defaultValue="-1")int tar_cont_id,
		@RequestParam(value="approval_state", defaultValue="-1")int tar_approval_state,
		@RequestParam(value="risk_start", defaultValue="")String risk_start,
		@RequestParam(value="risk_end", defaultValue="")String risk_end
		)
	{			
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		
		int site_id = userInfo.getSite_id();
		int cont_id = userInfo.getCont_id();
		
		List<ContVO> contList = contService.getContList(site_id);		
		
		List<ApprovalVO> approvalList = approvalService.getApprovalList(1);
		
		List<RiskVO> riskList = null;
		
		int cont_type = userInfo.getCont_type();
		
		if(cont_type == 0) {
			if(tar_cont_id > 0) {
				riskList = riskService.getRiskList(site_id, tar_cont_id, tar_approval_state, risk_start, risk_end);
			}
			else {
				riskList = riskService.getRiskList(site_id, -1, tar_approval_state, risk_start, risk_end);
			} 				
		}
		else {
			riskList = riskService.getRiskList(site_id, cont_id, tar_approval_state, risk_start, risk_end);
		}			

		model.addAttribute("riskList", riskList);
		
		// 변경에 따른 데이터 파라메터 저장
		model.addAttribute("tar_cont_id", tar_cont_id);
		model.addAttribute("tar_approval_state", tar_approval_state);
		model.addAttribute("date_risk_start", risk_start);
		model.addAttribute("date_risk_end", risk_end);		
		model.addAttribute("stateList", approvalList);		
		model.addAttribute("contList", contList);		
		session.setAttribute("contentView", "riskList");		
		return "riskList";
	}
	
	@RequestMapping(value = {"/accessList"})
	public String accessList(HttpSession session , Model model)	{		
		session.setAttribute("contentView", "accessList");		
		return "accessList";
	}
	
	// Menu - 모니터링 알람 세팅
	@RequestMapping(value = { "/monitor_setting" })
	public String monitorSetting(HttpSession session, Model model) {		
		//System.out.println("[Menu] (monitor_setting) > monitorSetting");		
		//UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");
		session.setAttribute("contentView", "monitor_setting");
		return "monSetting";
	}	
	
	@RequestMapping(value = { "/fanList" })
	public String fanList(HttpSession session, Model model) {
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");			
		List<FanVO> fanList = deviceService.getFanList(userInfo.getSite_id());
		List<FanVO> sectionList = deviceService.getFanAllotList(userInfo.getSite_id());			
		model.addAttribute("fanList", fanList);
		model.addAttribute("sectionList", sectionList);
		session.setAttribute("contentView", "fanList");
		return "fanList";
	}

	
}


