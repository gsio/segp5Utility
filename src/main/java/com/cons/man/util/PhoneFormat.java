package com.cons.man.util;

import java.util.Calendar;
import java.util.UUID;


public class PhoneFormat {
	
	public static String transFormPhone(String phoneStr){
		
		String phone = "";
		if(phoneStr.length() > 7){
			String iden = phoneStr.substring(0, 3);
			String code = phoneStr.substring(3, 7);
			String body = phoneStr.substring(7, phoneStr.length());
			phone = iden + "-"+code+"-" + body;					
		}
		else{
			phone = phoneStr;
		}
		return phone;
	}
	
}
