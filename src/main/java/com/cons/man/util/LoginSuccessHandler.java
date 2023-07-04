package com.cons.man.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cons.man.domain.SiteVO;
import com.cons.man.domain.UserVO;
import com.cons.man.services.SiteService;
import com.cons.man.services.UserService;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private UserService userService;

	@Autowired
	private SiteService siteService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {

		String user_id = (String) auth.getPrincipal();
		UserVO userVO = new UserVO();

		userVO = userService.getUserByUserId(user_id);

		List<SiteVO> siteList = siteService.getSiteList();
		StringBuffer url = request.getRequestURL();
		boolean isOk = false;

		for (SiteVO vo : siteList) {
			if (vo != null && vo.getUrl() != null) {
				if (url.indexOf(vo.getUrl()) > 0) {
					if (userVO.getSite_id() == vo.getId()) {
						isOk = true;
					}
				}
			}
		}

		if (url.indexOf("localhost") > 0) {
			isOk = true;
		} else {
			userService.insertLoginLogVO(request.getRemoteAddr(), userVO.getUserid(),
					request.getRequestURL().toString());
		}

		isOk = true;

		if (isOk) {
			HttpSession session = request.getSession();
			session.setAttribute("userLoginInfo", userVO);
			response.sendRedirect(request.getContextPath() + "/home");
		} else {
			response.sendRedirect(request.getContextPath() + "/login_fail");
		}

		return;
	}

}
