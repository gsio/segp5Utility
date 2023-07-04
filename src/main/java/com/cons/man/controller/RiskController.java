package com.cons.man.controller;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cons.man.domain.ApprovalVO;
import com.cons.man.domain.ContVO;
import com.cons.man.domain.CtgoRiskFactorVO;
import com.cons.man.domain.EquipVO;
import com.cons.man.domain.FileVO;
import com.cons.man.domain.RiskWorkActivityVO;
import com.cons.man.domain.RiskVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkTypeVO;
import com.cons.man.services.ContService;
import com.cons.man.services.EquipService;
import com.cons.man.services.RiskService;
import com.cons.man.services.UserService;
import com.cons.man.util.FileWriter;
import com.cons.man.util.FileWriter.PARENT_TYPE;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@Controller(value="RiskController")
public class RiskController {
	
	@Resource(name="RiskService")
	private RiskService riskService;
	
	@Resource(name="ContService")
	private ContService contService;
	
	@Resource(name="UserService")
	private UserService userService;
	
	@Resource(name="EquipService")
	private EquipService equipService;
		
	@RequestMapping(value = {"/registerRisk"})
	public String registerRisk(HttpSession session , Model model,		
		@RequestParam(value="id", required=false, defaultValue="-1")int id) {
		
		System.out.println("[Api] (Risk) > registerRisk - id: " + id + " 번 위혐성평가 입력/수정 하기");		
		
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");
		
		int site_id = userInfo.getSite_id();
		int cont_id = userInfo.getCont_id();
		int cont_type = userInfo.getCont_type();		
		
		RiskVO riskVO = new RiskVO();
		
		List<UserVO> approvalList = null;
		
		List<UserVO> checkerList = null; 
		
		List<WorkTypeVO> wtypeList = riskService.getFactorTypeList(site_id);
		
		List<CtgoRiskFactorVO> factorList = riskService.getFactorListByTitle();
		
		userService.getBeaconUserList(userInfo.getSite_id(), -1);
		
		if(cont_type == 1){
			approvalList = userService.getApprovalUserList(id, site_id, cont_id);
			checkerList = userService.getCheckerUserList(id, site_id, cont_id);
		}
		else{
			approvalList = userService.getApprovalUserList(id, site_id, -1);
			checkerList = userService.getCheckerUserList(id, site_id, -1);
		}	
		
		// INSERT
		if (id < 0) {			
			model.addAttribute("update", false);
		} 
		
		// UPDATE
		else {	
			riskVO = riskService.getRiskData(id);			
			model.addAttribute("update", true);
		}
		
		List<ContVO> contList = contService.getContList(site_id);		
		model.addAttribute("contList", contList);		
		model.addAttribute("userAList", approvalList);		
		model.addAttribute("userCList", checkerList);
		model.addAttribute("factorList", factorList);
		model.addAttribute("factorTypeList", wtypeList);		
		//model.addAttribute("equipList", equipList);	
		model.addAttribute("riskVO", riskVO);
		model.addAttribute("id", id);
		session.setAttribute("contentView", "riskList");
		
		return "registerRisk";		
	}
	
	@RequestMapping(value = {"/riskView"})
	public String getRiskView(HttpSession session , Model model,		
		@RequestParam(value="id", required=false, defaultValue="-1")int id) {
		
		//System.out.println("[Api] (Risk) > getRiskView - id: " + id + " 번 위혐성평가 정보 보여주기");		
		
		RiskVO riskVO = riskService.getRiskData(id);	
		
		model.addAttribute("riskVO", riskVO);
		session.setAttribute("contentView", "riskList");		
		return "riskView";
	}
	
	
	@RequestMapping(value = {"/requestRisk"})
	public String requestRisk(HttpSession session , Model model,		
		@RequestParam(value="id", required=false, defaultValue="-1")int id) {		
		System.out.println("[Api] (위평-결제요청): " + id + "번 위혐성평가");		
		try {					
			UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
			riskService.requestRisk(id, userInfo.getId());			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		return "redirect:riskList";		
	}	
	
	@RequestMapping(value = {"/approvalRisk"})
	public String approvalRisk(HttpSession session , Model model,		
		@RequestParam(value="id", required=false, defaultValue="-1")int id,
		@RequestParam(value="approval_order", required=false, defaultValue="-1")int approval_order) {		
		System.out.println("[Api] (위평-승인): " + id + "번 위혐성평가 " + approval_order + "번재 순서");		
		try {					
			UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
			riskService.approvalRisk(id, userInfo.getId(), approval_order);			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		return "redirect:riskList";		
	}
	

	@RequestMapping(value = {"/cancelRisk"})
	public String cancelRisk(HttpSession session , Model model,		
		@RequestParam(value="id", required=false, defaultValue="-1")int id,
		@RequestParam(value="content", required=false, defaultValue="")String content) {		
		System.out.println("[Api] (반려): " + id + "번 위혐성평가 이유: " + content);		
		try {					
			UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
			riskService.cancelRisk(id, userInfo.getId(), content);			
		} catch(Exception e) {
			e.printStackTrace();
		}		
		return "redirect:riskList";		
	}
	
	@RequestMapping(value = "/returnRisk")
	public String returnEquip(HttpSession session) {		
		return "redirect:riskList";		
	}
	
	@RequestMapping(value = {"/risk/factor/detail"}, method = RequestMethod.GET)	
	public ResponseEntity<List<CtgoRiskFactorVO>> getFactorDetailList(HttpSession session, HttpServletResponse response,
		@RequestParam(value="code_level_one", defaultValue="")String code_level_one,		
		@RequestParam(value="code_level_two", defaultValue="")String code_level_two,		
		@RequestParam(value="code_level_three", defaultValue="")String code_level_three)
	{		
		//System.out.println("[Api] (Risk) > getFactorDetailList - code_level_one: " + code_level_one + " / code_level_two: " + code_level_two + " / code_level_three: " + code_level_three);
		
		List<CtgoRiskFactorVO> list = riskService.getFactorDetailList(code_level_one, code_level_two, code_level_three);	
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<CtgoRiskFactorVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<CtgoRiskFactorVO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/insertRiskData")
	public String insertRiskData(HttpSession session, @ModelAttribute @Valid RiskVO riskVO, BindingResult bindingResult, Model model) {
		
		boolean hasError = false;
		if (bindingResult.hasErrors()) {
			hasError = true;
		} 
		else {
			
			try {		
				
				UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
				riskVO.setWriter_id(userInfo.getId());	
				riskVO.setWork_state(userInfo.getWork_state());
				riskService.insertRiskData(riskVO);
				
			} catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}

		if (hasError) {
			model.addAttribute("update", false);
			return "registerRisk";
		}
		else {
			return "redirect:riskList";
		}
	}
	
	@RequestMapping(value = "/updateRiskData")
	public String updateRiskData(HttpSession session, @ModelAttribute @Valid RiskVO riskVO, BindingResult bindingResult, Model model) {
		
		//System.out.println("[Api] (Risk) > updateRiskData");
		
		boolean hasError = false;
		if (bindingResult.hasErrors()) {
			hasError = true;
		} 
		else {
			
			try {		
				
				UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
				riskVO.setWriter_id(userInfo.getId());	
				riskVO.setWork_state(userInfo.getWork_state());
				riskService.updateRiskData(riskVO);
				
			} catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
		
		if (hasError) {
			model.addAttribute("update", true);
			return "registerRisk";
		}
		else {
			return "redirect:riskList";
		}
	}
	
	@RequestMapping(value = "/deleteRiskData")
	public String deleteRiskData(HttpSession session, @ModelAttribute @Valid RiskVO riskVO, BindingResult bindingResult, Model model) {
		
		//System.out.println("[Api] (Risk) > deleteRiskData " + riskVO.getId() + " 번 위험성 평가 삭제");
		
		boolean hasError = false;
		if (bindingResult.hasErrors()) {
			hasError = true;
		} 
		else {
			try {	
				riskService.deleteRiskData(riskVO.getId());
			} catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
		if (hasError) {
			model.addAttribute("update", true);
			return "registerRisk";
		}
		else {
			return "redirect:riskList";
		}
	}
	
}

