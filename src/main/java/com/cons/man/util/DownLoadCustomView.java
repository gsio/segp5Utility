package com.cons.man.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;
//작성자 : 	박다은
public class DownLoadCustomView extends AbstractView {

	public DownLoadCustomView() {
		setContentType("application/download;charset-UTF-8");
	}
	@Override
	protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, 
										   HttpServletResponse response) throws Exception {
		
		File file = (File) map.get("downFile");
        System.out.println("DownloadView --> file.getPath() : " + file.getPath());
        System.out.println("DownloadView --> file.getName() : " + file.getName());
		
		response.setContentType(getContentType());
		response.setContentLength((int)file.length());
	
		
		String userAgent = request.getHeader("User-Agent");
		boolean isInternetExplorer = userAgent.indexOf("MSIE") > -1;
				
		String fileName = null;
		
		if(isInternetExplorer){
			fileName = URLEncoder.encode(file.getName() , "UTF-8");
		}else{
			//한글문제 발생 
			//fileName = new String(file.getName().getBytes("UTF-8") , "iso-8859-1");
			//fileName = URLEncoder.encode(file.getName() , "iso-8859-1") ;
			//fileName = URLEncoder.encode(file.getName() , "utf-8") ;
			
			fileName = new String(file.getName().getBytes("UTF-8"));  //ok
//			fileName = file.getName();//ok
		}
		
		response.setHeader("Content-Disposition","attachment;filename=\"" + fileName.replace("+", "%20") + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		}
		catch(Exception e){
			map.put("error", e.toString());
		}
		finally{
			if(fis != null){
				try{
					fis.close();
				}
				catch(IOException ex){}
			}
		}		
		out.flush();		
	}


}

