package com.cons.man.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.CommentVO;
import com.cons.man.domain.NoticeVO;

@Repository(value="NoticeMapper")
public interface NoticeMapper {
	public ArrayList<NoticeVO> getNoticeList(@Param("site_id")int site_id, @Param("cont_id")int cont_id);
	
	public void updateNoticeHit(@Param("id")int id);
	
	public void insertReadCheck(@Param("type")int type, @Param("notice_id")int notice_id, @Param("user_id")String user_id);
	
	public NoticeVO getNoticeInfo(@Param("id")int id);
	
	public List<CommentVO> getCommentList(@Param("id")int id);
	
	public void insertComment(@Param("site_id")int site_id, @Param("notice_id")int notice_id, @Param("parent_id")int parent_id, @Param("writer_user_id")String writer_user_id, @Param("content")String content);
	
	public void deleteCommentData(@Param("id")int id);
	
	public void updateCommentData(@Param("id")int id, @Param("content")String content);
	
	public int updateNoticeData (NoticeVO vo);
	
	public int insertNoticeData (NoticeVO vo);	
	
	public void deleteCommentByParent(@Param("notice_id")int notice_id);
	
	public void deleteNoticeData(@Param("id")int id);
}
