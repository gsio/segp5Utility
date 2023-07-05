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
import com.cons.man.services.SiteService;
import com.cons.man.services.UserService;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@Controller(value="NoticeController")
public class NoticeController {
	
	@Resource(name="NoticeService")
	private NoticeService noticeService;
	
	@Resource(name="UserService")
	private UserService userService;
	
	@Resource(name="ContService")
	private ContService contService;
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	@RequestMapping(value = {"/registerNotice"})
	public String registerNotice(HttpSession session , Model model,		
		@RequestParam(value="id", required=false, defaultValue="-1")int id) {				
		NoticeVO notice = new NoticeVO();		
		// INSERT
		if (id < 0) {			
			model.addAttribute("update", false);
		}
		// UPDATE
		else {	
			notice = noticeService.getNoticeInfo(id);		
			model.addAttribute("update", true);
		}		
		model.addAttribute("noticeVO", notice);
		model.addAttribute("noticeId", id);
		session.setAttribute("contentView", "menu_notice");		
		return "register_notice";		
	}
	
	@RequestMapping(value = {"/noticeView"})
	public String getNoticeView(HttpSession session , Model model,		
		@RequestParam(value="id", required=false, defaultValue="-1")int id) {
		
		//System.out.println("[Api] (Notice) > getNoticeView - id: " + id + " 번 Notice 정보 보여주기");		
		
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		noticeService.updateNoticeHit(id);
		
		try {
			noticeService.insertReadCheck(1, id, userInfo.getId());
		}
		catch(Exception e){
			System.out.println("[Error] 이미 읽은 Notice 정보 입니다.");
			e.printStackTrace();
		}
		
		NoticeVO noticeVO = noticeService.getNoticeInfo(id);
		
		model.addAttribute("noticeVO", noticeVO);
		session.setAttribute("contentView", "menu_notice");		
		return "detail_notice";
	}
	
	@RequestMapping(value = "/insertComment", method = RequestMethod.POST)
	public String insertComment(RedirectAttributes redirectAttributes, HttpSession session, 
		@RequestParam(value= "comment", defaultValue="") String comment,	
		@RequestParam(value ="notice_id") int notice_id,
		@RequestParam(value= "parent_id", defaultValue="-1")int parent_id)
	{
		
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");		
				
		//System.out.println("[Api] (Notice) > insertComment - notice_id: " + notice_id + " 번 Notice 정보: " + comment + " 내용 Insert / " + " 댓글&대댓글 확인: " + parent_id);

		noticeService.insertComment(userInfo.getSite_id(), notice_id, parent_id, userInfo.getId(), comment);			 
		
		return "redirect:noticeView?id=" + notice_id;
	}
	
	@RequestMapping(value = "/updateNoticeData")
	public String updateNotice(HttpSession session, @ModelAttribute @Valid NoticeVO noticeVO, BindingResult bindingResult, Model model) {
		
		//System.out.println("[Api] (Notice) > updateNoticeData - notice_id: " + noticeVO.getId() + " 번 공지사항 수정");
		
		boolean hasError = false;
		if (bindingResult.hasErrors()) {
			hasError = true;
		} 
		else {
			try {				
				noticeService.updateNoticeData(noticeVO);				
			} 
			catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
		if (hasError) {
			model.addAttribute("update", true);
			return "registerNotice";
		} else {
			return "redirect:menu_notice";
		}
	}
	
	@RequestMapping(value = "/insertNoticeData")
	public String insertNoticeData(HttpSession session, @ModelAttribute @Valid NoticeVO noticeVO, BindingResult bindingResult, Model model) {
		
		//System.out.println("[Api] (Notice) > insertNoticeData");
		
		boolean hasError = false;
		if (bindingResult.hasErrors()) {
			hasError = true;
		} 
		else {
			try {				
				UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
				
				noticeVO.setSite_id(userInfo.getSite_auth());
				noticeVO.setWriter_user_id(userInfo.getId());
				noticeVO.setWriter_cont_id(userInfo.getCont_id());
				noticeVO.setWriter_cont_type(userInfo.getCont_type());
				noticeVO.setCont_name(userInfo.getCont_name());			
								
				noticeService.insertNoticeData(noticeVO);					
				
			} catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
		if (hasError) {
			model.addAttribute("update", false);
			return "registerNotice";
		}
		else {
			return "redirect:menu_notice";
		}
	}
	

	@RequestMapping(value = "/deleteNoticeData")
	public String deleteNoticeData(HttpSession session, @ModelAttribute @Valid NoticeVO noticeVO, BindingResult bindingResult, Model model) {
		
		//System.out.println("[Api] (Notice) > deleteNoticeData - notice_id: " + noticeVO.getId() + " 번 공지사항 삭제");
		
		boolean hasError = false;
		if (bindingResult.hasErrors()) {
			hasError = true;
		} 
		else {
			try {
				noticeService.deleteCommentByParent(noticeVO.getId());		
				noticeService.deleteNoticeData(noticeVO.getId());
			} catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
		if (hasError) {//에러 발생시 왔던 주소로 되돌아감
			model.addAttribute("update", true);
			return "registerNotice";
		} 
		else {
			return "redirect:menu_notice";
		}
	}
	
	@RequestMapping(value = "/returnNotice")
	public String returnNotice(HttpSession session) {		
		return "redirect:menu_notice";		
	}
	
	@RequestMapping(value = "/notice/delete/comment")
	public void deleteCommentData(HttpSession session, HttpServletResponse response,
		@RequestParam(value = "comment_id", defaultValue="-1") int comment_id)
	{		
		//System.out.println("[Api] (Notice) > deleteCommentData - comment_id: " + comment_id);
		
		noticeService.deleteCommentData(comment_id);
		
		try {
			response.getWriter().print(true);			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/notice/update/comment")
	public void updateCommentData(HttpSession session, HttpServletResponse response,
		@RequestParam(value = "comment_id", defaultValue="-1") int comment_id,
		@RequestParam(value = "content", defaultValue="") String content
		)
	{		
		//System.out.println("[Api] (Notice) > updateCommentData - comment_id: " + comment_id + " / content: " + content);
		
		noticeService.updateCommentData(comment_id, content);
		
		try {
			response.getWriter().print(true);			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

