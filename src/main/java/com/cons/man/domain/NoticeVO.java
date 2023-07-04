package com.cons.man.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NoticeVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int site_id;	
	private String title;		
	private String contents;
	private int hit;	
	private String cont_name;
	private String writer_name;
	private int writer_cont_id;
	private int writer_cont_type;
	private String writer_user_id;
	private String writer_photo;
	private String write_date;
	private String write_time; 	
	private String uw_id;
	private int is_new;	
	private List<CommentVO> comment_list = new ArrayList<CommentVO>();
	private List<FileVO> file_list;
	
	public int getId() {		
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getCont_name() {
		return cont_name;
	}
	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}	
	
	public String getWriter_name() {
		return writer_name;
	}
	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}
	public int getWriter_cont_id() {
		return writer_cont_id;
	}
	public void setWriter_cont_id(int writer_cont_id) {
		this.writer_cont_id = writer_cont_id;
	}
	public int getWriter_cont_type() {
		return writer_cont_type;
	}
	public void setWriter_cont_type(int writer_cont_type) {
		this.writer_cont_type = writer_cont_type;
	}
	public String getWriter_user_id() {
		return writer_user_id;
	}
	public void setWriter_user_id(String writer_user_id) {
		this.writer_user_id = writer_user_id;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	public int getIs_new() {
		return is_new;
	}
	public void setIs_new(int is_new) {
		this.is_new = is_new;
	}
	public List<CommentVO> getComment_list() {
		return comment_list;
	}
	public void setComment_list(List<CommentVO> comment_list) {
		this.comment_list = comment_list;
	}
	public List<FileVO> getFile_list() {
		return file_list;
	}
	public void setFile_list(List<FileVO> file_list) {
		this.file_list = file_list;
	}
	public String getWriter_photo() {
		return writer_photo;
	}
	public void setWriter_photo(String writer_photo) {
		this.writer_photo = writer_photo;
	}
	public String getUw_id() {
		return uw_id;
	}
	public void setUw_id(String uw_id) {
		this.uw_id = uw_id;
	}
	
}
