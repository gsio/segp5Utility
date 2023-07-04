package com.cons.man.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cons.man.domain.CommentVO;
import com.cons.man.domain.ContVO;
import com.cons.man.domain.InfoVO;
import com.cons.man.domain.NoticeVO;
import com.cons.man.domain.UserVO;
import com.cons.man.services.ContService;
import com.cons.man.services.NoticeService;
import com.cons.man.services.PTWService;
import com.cons.man.services.SiteService;
import com.cons.man.services.UserService;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@Controller(value="PTWController")
public class PTWController {
	
	@Resource(name="PTWService")
	private PTWService ptwService;
	
	@RequestMapping(value = {"/registerPTW"})
	public String registerPTW(HttpSession session , Model model,		
		@RequestParam(value="id", required=false, defaultValue="-1")int id) {
		
		System.out.println("[Api] (PTW) > registerPTW - id: " + id + " 번 PTW 입력/수정 하기");
		
		
		//NoticeVO notice = new NoticeVO();
		
		// INSERT
		if (id < 0) {			
			model.addAttribute("update", false);
		} 
		
		// UPDATE
		else {	
			//notice = ptwService.getPTWData(id);		
			model.addAttribute("update", true);
		}
		
		//model.addAttribute("noticeVO", notice);
		//model.addAttribute("noticeId", id);
		session.setAttribute("contentView", "noticeList");
		
		return "registerPTW";		
	}
}

