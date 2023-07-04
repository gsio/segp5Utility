package com.cons.man.domain;

public class MobileWorkingManagerVO {

	private String id;
	private String w_id;
	private String mon;
	private String after;
	private String content;
	private String content_ya;
	private String workdate;
	
	private String checkdate;
	private String type;
	private String cont_id;
	private String site_id;
	private String year;
	private String month;
	private String day;
	
	private String safe_content;
	private String safe_content_ya;
	private String name;
	
	
	
	
	
	
	public String getName() {
		if(name == null) name = "";
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSafe_content() {
		if(safe_content == null) safe_content = "";
		return safe_content;
	}
	public void setSafe_content(String safe_content) {
		this.safe_content = safe_content;
	}
	public String getSafe_content_ya() {
		if(safe_content_ya == null) safe_content_ya = "";
		return safe_content_ya;
	}
	public void setSafe_content_ya(String safe_content_ya) {
		this.safe_content_ya = safe_content_ya;
	}
	public String getContent_ya() {
		if(content_ya == null) content_ya = "";
 		return content_ya;
	}
	public void setContent_ya(String content_ya) {
		this.content_ya = content_ya;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getCont_id() {
		return cont_id;
	}
	public void setCont_id(String cont_id) {
		this.cont_id = cont_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public String getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getW_id() {
		return w_id;
	}
	public void setW_id(String w_id) {
		this.w_id = w_id;
	}
	public String getMon() {
		return mon;
	}
	public void setMon(String mon) {
		this.mon = mon;
	}
	public String getAfter() {
		return after;
	}
	public void setAfter(String after) {
		this.after = after;
	}
	public String getContent() {
		if(content == null) content = "";
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWorkdate() {
		return workdate;
	}
	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}
	
	
}
