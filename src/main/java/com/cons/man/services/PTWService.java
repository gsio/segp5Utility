package com.cons.man.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.cons.man.domain.CommentVO;
import com.cons.man.domain.FileVO;
import com.cons.man.domain.NoticeVO;
import com.cons.man.persistence.FileMapper;
import com.cons.man.persistence.NoticeMapper;
import com.cons.man.persistence.PTWMapper;
import com.cons.man.persistence.SiteMapper;
import com.cons.man.util.FileWriter;
import com.cons.man.util.FileWriter.PARENT_TYPE;

@Service(value="PTWService")
public class PTWService {	
	
	@Resource( name="PTWMapper" )
	private PTWMapper ptwMapper;
	
	/*
	private void replaceNewLineToBRNotice(List<NoticeVO> checklist) {
		Iterator<NoticeVO> it = checklist.iterator();
		while(it.hasNext()) {
			NoticeVO vo = it.next();
			if(vo.getTitle() != null) {
				vo.setTitle(vo.getTitle().replaceAll("(\\r|\\n|\\r\\n)+","<br>"));
			}				
			if(vo.getContents() != null) {
				vo.setContents(vo.getContents().replaceAll("(\\r|\\n|\\r\\n)+","<br>"));
			}							
		}				
	}	

	private void replaceNewLineToBR(NoticeVO vo) {
		if(vo.getTitle() != null) {
			vo.setTitle(vo.getTitle().replaceAll("(\\r|\\n|\\r\\n)+","<br>"));
		}		
		if(vo.getContents() != null) {
			vo.setContents(vo.getContents().replaceAll("(\\r|\\n|\\r\\n)+","<br>"));
		}			
	}
	
	private void replaceBRToNewLine(NoticeVO vo) {			
		if(vo.getTitle() != null)
			vo.setTitle(vo.getTitle().replaceAll("<br>", "\r\n"));
		if(vo.getContents() != null)
			vo.setContents(vo.getContents().replaceAll("<br>", "\r\n"));	
	}
	*/
}
