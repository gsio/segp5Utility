package com.cons.man.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Filedownload {
	
	 public void filedown(File file,  HttpServletRequest request, HttpServletResponse response) throws Exception {
	        // TODO Auto-generated method stub
	        System.out.println("DownloadView --> file.getPath() : " + file.getPath());
	        System.out.println("DownloadView --> file.getName() : " + file.getName());
	         
	        response.setContentType("application/download; utf-8");
	        response.setContentLength((int)file.length());
	         
	        String userAgent = request.getHeader("User-Agent");
	         
	        boolean ie = userAgent.indexOf("MSIE") > -1;
	         
	        String fileName = null;
	         
	        if(ie){
	             
	            fileName = URLEncoder.encode(file.getName(), "utf-8");
	                         
	        } else {
	             
	            fileName = new String(file.getName().getBytes("utf-8"));
	             
	        }// end if;
	        System.out.println(fileName + " <= fileName path");
	         
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
	        response.setHeader("Content-Transfer-Encoding", "binary");


	        FileInputStream fis = null;
	        try {
	            fis = new FileInputStream(file);
//	            FileCopyUtils.copy(fis, out);
	        } catch(Exception e){
	             
	            e.printStackTrace();
	             
	        }finally{
	             
	            if(fis != null){
	                 
	                try{
	                    fis.close();
	                }catch(Exception e){}
	            }
	             
	        }// try end;
	         
	//        out.flush();
	         
	    }
}
