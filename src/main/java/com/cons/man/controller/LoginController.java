package com.cons.man.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cons.man.domain.SiteVO;
import com.cons.man.services.SiteService;
import com.cons.man.services.UserService;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller(value = "LoginController")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Resource(name = "SiteService")
	private SiteService siteService;

	@Resource(name = "UserService")
	private UserService userService;

	@RequestMapping({ "/login", "/", "main", "home" })
	public String login(HttpSession session, HttpServletRequest request) {
		logger.info("login! {}", session.getId());
		StringBuffer url = request.getRequestURL();

		session.setAttribute("contentView", "main");
		session.setAttribute("isAccess", true);

		List<SiteVO> list = siteService.getSiteList();
		for (SiteVO vo : list) {
			if (vo != null && vo.getUrl() != null && vo.getMain_page() != null) {
				if (url.indexOf(vo.getUrl()) >= 0) {
					return vo.getMain_page();
				}
			}
		}
		return "main/main_renewal";
	}

	@RequestMapping({ "/renewal" })
	public String mainRenewalPage(HttpSession session, HttpServletRequest request) {

		StringBuffer url = request.getRequestURL();

		session.setAttribute("contentView", "main");
		session.setAttribute("isAccess", true);

		return "main/main_renewal";
	}

	@RequestMapping("/error")
	public String error(HttpSession session, HttpServletRequest request) {
		return "error";
	}

	@RequestMapping("logout")
	public String logout(HttpSession session) {
		if (session != null)
			session.invalidate();
		return "login";
	}

	@RequestMapping("login_fail")
	public String loginFail(HttpSession session) {
		return "login_fail";
	}

	@RequestMapping("logout_success")
	public String logout_success(HttpSession session) {
		return "logout_success";
	}

	@RequestMapping("logout_noSite")
	public String logout_noSite(HttpSession session) {
		return "logout_noSite";
	}

	@RequestMapping("login_duplicate")
	public void login_duplicate(HttpSession session) {
		logger.info("Login Duplicated", session.getId());
		session.invalidate();
	}

	@RequestMapping("/loginManagerProcess")
	public String loginManagerProcess(HttpSession session, HttpServletRequest request,
		@RequestParam(value = "userid", required = false) String userid,
		@RequestParam(value = "password", required = false) String password,
		@RequestParam(value = "isuser", required = false, defaultValue = "1") int isuser) 
	{
		return "redirect:home";
	}

}
