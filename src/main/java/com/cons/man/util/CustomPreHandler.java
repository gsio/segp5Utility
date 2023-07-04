package com.cons.man.util;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cons.man.domain.MenuAccessVO;
import com.cons.man.domain.MenuVO;
import com.cons.man.domain.UserVO;
import com.cons.man.services.MenuService;

public class CustomPreHandler extends HandlerInterceptorAdapter{
	@Resource(name="MenuService")
	private MenuService menuService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){
		HttpSession session = request.getSession();
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
		String contentView = (String)request.getSession().getAttribute("contentView");
		
		if(modelAndView != null || contentView == null) {
			
			UserVO userInfo = (UserVO)request.getSession().getAttribute("userLoginInfo");
			
			if(userInfo != null){				
				
				request.getSession().setAttribute("isLogin", true);
								
				int site_auth = userInfo.getSite_auth();
				int role_code = userInfo.getRole_code();
				int cont_type = userInfo.getCont_type();
							
				if(role_code == 99 || role_code == 1000){
			
				}
				else {
					role_code = -1;	
				}
				
				List<MenuVO> menuList = getMenuList(site_auth, role_code, cont_type);
		        request.getSession().setAttribute("menuList", menuList);

		        if(menuList != null){			        
		        	boolean isAccess = false;
		        	if(contentView != null){
		        		for(MenuVO menu :menuList){
		        			if(contentView.equals(menu.getHref())){
		        				if(menu.getIsaccess() == 1){
		        					isAccess = true;
		        				}
		        			}
		        		}
		        	}
		        	request.getSession().setAttribute("isAccess", isAccess);
		        }  
			}
		}
		
		else {
			
			String[] permitAll = {"main", "did",  "m_did", "monitor"};
			
			boolean isAccess = false;
			for(String menu : permitAll){
				if(contentView.equals(menu)){
					isAccess = true;
				}
			}
			
			request.getSession().setAttribute("isAccess", isAccess);
			
		}
	}

	
	private List<MenuVO> getMenuList(int site_auth, int role_code, int cont_type){
		MenuAccessVO maVO = new MenuAccessVO();
		maVO.setSite_auth(site_auth);
		maVO.setRole_code(role_code);
		maVO.setCont_type(cont_type);				
	
		//System.out.println(site_auth + "/" + role_code + "/" + cont_type);
		String access_list = menuService.getMenuAccessList(maVO);

        return menuService.getMenuListByAccess(Arrays.asList(access_list.split(",")));
	}
}
