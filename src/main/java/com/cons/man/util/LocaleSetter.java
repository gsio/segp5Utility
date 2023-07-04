package com.cons.man.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LocaleSetter extends HandlerInterceptorAdapter{
	  @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){

		if(request.getSession() != null){
	    	Locale locale = (Locale)request.getSession().getAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE");
	    	if(locale != null)
	    		request.getSession().setAttribute("locale", locale.getLanguage());
		}
		
		return true;
	}

}
