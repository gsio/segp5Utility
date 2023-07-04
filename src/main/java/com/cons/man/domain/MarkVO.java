package com.cons.man.domain;

import java.io.Serializable;


public class MarkVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**total_comment, READ_CHECK 테이블에 사용**/
	public static final int TYPE_NOTICE = 1;
	public static final int TYPE_BRIEF = 2;
	public static final int TYPE_USER = 99;
	
	private int id;
	
	private int type;
	private String type_id;	
	private String cons_name;//공사분야?	
	private String u_id;//site_favorite용
	private int site_type;
	private String site_type_name;
	
	public MarkVO(){
		
	}
	
	public MarkVO(String cons_name, int site_type){
		this.cons_name = cons_name;
		this.site_type = site_type;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getCons_name() {
		return cons_name;
	}
	public void setCons_name(String cons_name) {
		this.cons_name = cons_name;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public int getSite_type() {
		return site_type;
	}
	public void setSite_type(int site_type) {
		this.site_type = site_type;
	}
	public String getSite_type_name() {
		return site_type_name;
	}
	public void setSite_type_name(String site_type_name) {
		this.site_type_name = site_type_name;
	}
	
	

}
