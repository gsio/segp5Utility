package com.cons.man.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Customize {
	public static final int HALLA = 1;
	public static final int DUSAN = 2;
	private static final Logger logger = LoggerFactory.getLogger(Customize.class);
	
	public static int getRequestCompany(HttpServletRequest request){
		String ip = request.getServerName(); //localhost
		//String s1 = request.getContextPath(); // /cons
		//String s2 = request.getRequestURI();// cons/loginmanageprocess
		//String s3 = request.getScheme(); //http
		//String s4 = request.getScheme(); //http
		//String s5 = request.getLocalAddr(); //http
	/*	String hostaddr="";
		String hostname="";
		try {
			hostaddr =	InetAddress.getLocalHost().getHostAddress();
			hostname =	InetAddress.getLocalHost().getHostName();
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		logger.info("11" + hostaddr);
		logger.info("22" + hostname);
		
		
		if(hostaddr.equals("52.68.0.92")){ //HALLA
			return HALLA;
		}else if(hostaddr.equals("52.69.252.251")){
			return DUSAN;
		}else 
			return HALLA;*/
		
		
		
		if(ip.equals("52.68.0.92")){ //HALLA
			return HALLA;
		}else if(ip.equals("52.69.252.251")){
			return DUSAN;
		}else if(ip.equals("localhost")){
			return HALLA;
		}else
			return HALLA;
		
		 
	}
}
