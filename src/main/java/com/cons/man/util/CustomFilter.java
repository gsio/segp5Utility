package com.cons.man.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CustomFilter implements Filter {
 
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException { // 모든 요청(Request)에 대해서 이 부분이 실행된다.
        HttpServletRequest request = (HttpServletRequest)req;
        
        System.out.println("RequestURL : "+request.getRequestURL());
        
        chain.doFilter(req, res);
    }
 
    @Override
    public void init(FilterConfig config) throws ServletException { // 초기화할 때 실행되는 부분
        String testParam = config.getInitParameter("testParam");
        
        System.out.println("testParam : "+testParam);
    }
    
 
    @Override
    public void destroy() {
        
    }
}
