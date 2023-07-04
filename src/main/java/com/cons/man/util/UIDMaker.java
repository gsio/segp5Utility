package com.cons.man.util;

import java.util.Calendar;
import java.util.UUID;


public class UIDMaker {
	public static String makeNewUID(String head){
		return (head + UUID.randomUUID() + (Calendar.getInstance().getTimeInMillis()+"")).substring(0,42); 
	}
}
