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
import com.cons.man.persistence.SiteMapper;
import com.cons.man.util.FileWriter;
import com.cons.man.util.FileWriter.PARENT_TYPE;

@Service(value="NoticeService")
public class NoticeService {
	
	@Resource( name="NoticeMapper" )
	private NoticeMapper noticeMapper;
	
	@Resource( name="SiteMapper" )
	private SiteMapper siteMapper;
	
	@Resource( name="FileMapper" )
	private FileMapper fileMapper;
	
	public List<NoticeVO> getNoticeList(int site_id, int cont_id) {
		List<NoticeVO> noticeList = noticeMapper.getNoticeList(site_id, cont_id);
		replaceNewLineToBRNotice(noticeList);	
		return noticeList;	
	}		

	public void updateNoticeHit(int id) {
		noticeMapper.updateNoticeHit(id);
	}
	
	// Type: 1
	public void insertReadCheck(int type, int notice_id, String user_id){		
		noticeMapper.insertReadCheck(type, notice_id, user_id);
	}
	
	public NoticeVO getNoticeInfo(int id) {
		
		NoticeVO vo = noticeMapper.getNoticeInfo(id);		
		
		vo.setFile_list(FileWriter.INSTANCE.getFileList(id, FileWriter.PARENT_TYPE.NOTICE, fileMapper));		
		
		List<CommentVO> list = noticeMapper.getCommentList(id);
		List<CommentVO> sortList = new ArrayList<CommentVO>();
		
		for(int i=0; i<list.size(); i++) {
			//System.out.println("[Comment] > getNoticeInfo - 총 댓글 갯수: " + sortList.size());
			if(list.get(i).getParent_id() < 0) {
				//System.out.println("[Comment] > 댓글: " + i + "번째 데이터는 댓글 입니다. >>> " + list.get(i).getContent());
				if(list.get(i).getUseyn().equals("N")) {
					list.get(i).setContent("작성자 요청에 의해 삭제 되었습니다.");
				}
				sortList.add(list.get(i));
			}
			
			for(int j=0; j<list.size(); j++) {
				if(list.get(i).getId() == list.get(j).getParent_id()) {
					if(list.get(j).getUseyn().equals("N")) {
						list.get(j).setContent("작성자 요청에 의해 삭제 되었습니다.");
					}
					//System.out.println("[Comment] > 대댓글: " + j + "번째 데이터는" + " 댓글( " + i + "번째) 데이터의 대댓글 입니다. >>> " + list.get(j).getContent());		
					sortList.add(list.get(j));
				}				
			}			
		}
		
		vo.setComment_list(sortList);
		
		replaceNewLineToBR(vo);
	
		return vo;
	}
	
	public void insertComment(int site_id, int notice_id, int parent_id, String writer_user_id, String content){		
		noticeMapper.insertComment(site_id, notice_id, parent_id, writer_user_id, content);
	}
	
	public void deleteCommentData(int comment_id) {
		noticeMapper.deleteCommentData(comment_id);
	}
	
	public void updateCommentData(int comment_id, String content) {
		noticeMapper.updateCommentData(comment_id, content);
	}
	
	public void updateNoticeData (NoticeVO noticeVO) {	
		replaceBRToNewLine(noticeVO);
		noticeMapper.updateNoticeData(noticeVO);		
		if(noticeVO.getFile_list() != null){
			Iterator<FileVO> it_list =  noticeVO.getFile_list().iterator();
			while(it_list.hasNext()){				
				FileVO vo =  it_list.next();				
				if(vo.getIsmodify() == 1){
					if(vo.getVirtname() != null) {
						FileWriter.INSTANCE.updateFileDetail(FileWriter.PARENT_TYPE.NOTICE, vo, noticeVO.getId(), -1,  fileMapper);
					}				
				}
				else{
					FileWriter.INSTANCE.insertFileDetail(FileWriter.PARENT_TYPE.NOTICE, vo, noticeVO.getId(), -1, fileMapper);
				}
			}
		}
	}
	
	public String insertNoticeData (NoticeVO noticeVO) {	
		
		replaceBRToNewLine(noticeVO);		
		JSONObject jo = new JSONObject();		
		int resultInt = 0;			
		resultInt = noticeMapper.insertNoticeData(noticeVO);
		
		if(noticeVO.getFile_list() != null){
			Iterator<FileVO> it_list =  noticeVO.getFile_list().iterator();
			while(it_list.hasNext()){
				FileVO vo =  it_list.next();
				FileWriter.INSTANCE.insertFileDetail(PARENT_TYPE.NOTICE, vo, noticeVO.getId(), -1, fileMapper);
			}
		}
			
		if(resultInt > 0) {
			jo.put("result", "true");
			jo.put("notice_id", noticeVO.getId());
		} 
		else {
			jo.put("result", "false");
		}
		return jo.toString();
	}
	
	public void deleteCommentByParent(int notice_id) {
		noticeMapper.deleteCommentByParent(notice_id);
	}
	
	public void deleteNoticeData(int id) {
		noticeMapper.deleteNoticeData(id);
	}
	

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
	
	
}
