package com.cons.man.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cons.man.domain.ContVO;
import com.cons.man.domain.EquipCategoryVO;
import com.cons.man.domain.EquipVO;
import com.cons.man.domain.NoticeVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkerVO;
import com.cons.man.services.ContService;
import com.cons.man.services.EquipService;
import com.cons.man.services.WorkerService;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@Controller(value="EquipController")
public class EquipController {
	
	@Resource(name="EquipService")
	private EquipService equipService;
	
	@Resource(name="ContService")
	private ContService contService;	
	
	@Resource(name = "WorkerService")
	private WorkerService workerService;
	
	@RequestMapping(value = {"/registerEquip"})
	public String registerEquip(HttpSession session , Model model,		
		@RequestParam(value="id", required=false, defaultValue="-1")int id) {
		
		//System.out.println("[Api] (Equip) > registerEquip - id: " + id + " 번 장비 입력/수정 하기");		
		
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");
		
		int site_id = userInfo.getSite_id();
		int cont_id = userInfo.getCont_id();
		int cont_type = userInfo.getCont_type();		
		
		EquipVO equipVO = new EquipVO();
		
		// INSERT
		if (id < 0) {			
			model.addAttribute("update", false);
		} 
		
		// UPDATE
		else {	
			equipVO = equipService.getEquipData(id);		
			model.addAttribute("update", true);
		}
		
		List<ContVO> contList = contService.getContList(site_id);		
		model.addAttribute("contList", contList);			
		
		List<EquipCategoryVO> equipCT = equipService.getEquipCategory(site_id);		
		model.addAttribute("eCategory", equipCT);	
		
		List<WorkerVO> driverList = null;		
		if(cont_type == 0) {
			driverList = workerService.getDriverList(site_id, -1);
		}
		else {
			driverList = workerService.getDriverList(site_id, cont_id);
		}
		
		model.addAttribute("driverList", driverList);	
		
		model.addAttribute("equipVO", equipVO);
		model.addAttribute("id", id);
		session.setAttribute("contentView", "equipList");
		
		return "registerEquip";		
	}
	
	@RequestMapping(value = {"/equipView"})
	public String getEquipView(HttpSession session , Model model,		
		@RequestParam(value="id", required=false, defaultValue="-1")int id) {
		
		//System.out.println("[Api] (Equip) > getEquipView - id: " + id + " 번 장비 정보 보여주기");		
		
		EquipVO equipVO = equipService.getEquipData(id);	
		
		model.addAttribute("equipVO", equipVO);
		session.setAttribute("contentView", "equipList");		
		return "equipView";
	}
	
	@RequestMapping(value = "/insertEquipData")
	public String insertEquipData(HttpSession session, @ModelAttribute @Valid EquipVO equipVO, BindingResult bindingResult, Model model) {
		
		//System.out.println("[Api] (Equip) > insertEquipData");
		
		boolean hasError = false;
		if (bindingResult.hasErrors()) {
			hasError = true;
		} 
		else {
			
			try {		
				
				UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
				
				equipVO.setSite_id(userInfo.getSite_auth());
				equipVO.setWriter_id(userInfo.getId());
				
				equipService.insertEquipData(equipVO);
				
			} catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
		
		if (hasError) {
			model.addAttribute("update", false);
			return "registerEquip";
		}
		else {
			return "redirect:equipList";
		}
	}
	
	@RequestMapping(value = "/updateEquipData")
	public String updateEquipData(HttpSession session, @ModelAttribute @Valid EquipVO equipVO, BindingResult bindingResult, Model model) {
		
		//System.out.println("[Api] (Equip) > updateEquipData - notice_id: " + equipVO.getId() + " 번 장비 수정");
		
		boolean hasError = false;
		if (bindingResult.hasErrors()) {
			hasError = true;
		} 
		else {
			try {				
				UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
				equipVO.setWriter_id(userInfo.getId());
				equipService.updateEquipData(equipVO);				
			} 
			catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
		if (hasError) {
			model.addAttribute("update", true);
			return "registerEquip";
		} 
		else {
			return "redirect:equipList";
		}
	}
	
	@RequestMapping(value = "/returnEquip")
	public String returnEquip(HttpSession session) {		
		return "redirect:equipList";		
	}

	@RequestMapping(value = "/deleteEquipData")
	public String deleteEquipData(HttpSession session, @ModelAttribute @Valid EquipVO equipVO, BindingResult bindingResult, Model model) {
		
		//System.out.println("[Api] (Equip) > deleteEquipData - " + equipVO.getId() + " 번 장비 삭제");
		
		boolean hasError = false;
		if (bindingResult.hasErrors()) {
			hasError = true;
		} 
		else {
			try {
				//noticeService.deleteCommentByParent(equipVO.getId());		
				//noticeService.deleteNoticeData(equipVO.getId());
				System.out.println("");
			} 
			catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
		if (hasError) {//에러 발생시 왔던 주소로 되돌아감
			model.addAttribute("update", true);
			return "registerEquip";
		} 
		else {
			return "redirect:equipList";
		}
	}
	
}

