package com.cons.man.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cons.man.domain.ContVO;
import com.cons.man.domain.SiteVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkStateVO;
import com.cons.man.services.ContService;
import com.cons.man.services.ManageService;
import com.cons.man.services.SiteService;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller(value="ContController")
public class ContController {
	
	public static final int MAX_ROW_NUM=5;
	public static final int MAX_PAGE_NUM=5;
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	@Resource(name="ContService")
	private ContService contService;
	
	@Resource(name="ManageService")
	private ManageService manageService;	
	
	@RequestMapping(value = {"/registerCont"})
	public String registerCont(HttpSession session , Model model,
		@RequestParam(value="cont_id", defaultValue="-1") int cont_id,
		@RequestParam(value="tar_site_id", defaultValue="-1") int tar_site_id
	) {		
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");		
		ContVO contVO = new ContVO();
		List<WorkStateVO> list = manageService.getWorkStateList(userInfo.getSite_id());	
		model.addAttribute("sList", list);
		if( cont_id >= 0 ) {
			contVO = contService.getContInfo(cont_id);
			model.addAttribute("update", true);
		}
		else {
			SiteVO siteVO = siteService.getSiteInfo(tar_site_id);
			contVO.setSite_id(siteVO.getId());
			contVO.setSearch_mame(siteVO.getName());			
			model.addAttribute("update", false);
		}		
		model.addAttribute("cont", contVO);
		model.addAttribute("sList", list);
		session.setAttribute("contentView", "menu_contractor");
		return "registerCont";
	}
	
	@RequestMapping(value = "insertCont")
	public String insertCont(HttpSession session, @ModelAttribute @Valid ContVO cont, BindingResult bindingResult, Model model) {
		model.addAttribute("update", false);
		if(bindingResult.hasErrors())
			return "registerCont";		
		else {
			try{
				contService.insertCont(cont);
			}catch(Exception e) {
				e.printStackTrace();
				return "registerCont";
			}			
			return "redirect:menu_contractor?site_id=" + cont.getSite_id();
		}		
	}
	
	@RequestMapping(value = "updateCont")
	public String updateCont(HttpSession session, @ModelAttribute @Valid ContVO cont, BindingResult bindingResult, Model model) {
		
		model.addAttribute("update", true);
		if(bindingResult.hasErrors())
			return "registerCont";		
		else {
			try{
				contService.updateCont(cont);
			}catch(Exception e) {
				e.printStackTrace();
				return "registerCont";
			}
			return "redirect:menu_contractor?site_id=" + cont.getSite_id();
		}		
	}
	
	@RequestMapping(value = "disableContractor")
	public String disableContractor(HttpSession session, @ModelAttribute @Valid ContVO cont, BindingResult bindingResult, Model model, HttpServletRequest request) {
		boolean hasError = false;
		
		if(bindingResult.hasErrors())
			hasError = true;		
		else {		
			try{
				contService.disableCont(cont.getId());
			}catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}		
		if(hasError) {
			model.addAttribute("updateMode", true);		
			return "registerCont";			
		}else{
			return"redirect:menu_contractor?site_id=" + cont.getSite_id();
		}
	}
}


