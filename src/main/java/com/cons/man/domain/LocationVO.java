package com.cons.man.domain;

import java.io.Serializable;
import java.util.List;

public class LocationVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String btype;
	private int cont_id;
	private String cont_name;
	private int count;
	private String eduimage;
	private String hiredate;
	private int id;
	private String last_update_time;
	private String beacon_mac;
	private int mb_idx;
	private int mb_site_id;
	private String name;
	private String phone;
	private int place_id;
	private int role;
	private int rssi;
	private int section;
	private int site_id;
	private int src_type;
	private int t_id;
	private int time_diff_min;
	private String u_id;
	private String writetime;
	private String write_time;
	private int wt_gubun;
	private String wt_name;
	private String work_min;
	private String start_time;
	private String edudate;
	private String sealed_date1;
	private String sealed_date2;
	private String sealed_date3;
	private String sealed_date4;
	private String first_date;
	private String sealed_date;
	private int sealed_complete;
	private int limit;
	private int cont_ss_state;
	private int input_state;
	private String section_name;
	
	private int type;
	private int inout_type;
	
	// Manage Page
	private String uw_id;
	private String beacon_role;
	
	private List<Integer> groupList;
	
	public String getBtype() {
		return btype;
	}
	public void setBtype(String btype) {
		this.btype = btype;
	}
	public int getCont_id() {
		return cont_id;
	}
	public void setCont_id(int cont_id) {
		this.cont_id = cont_id;
	}
	public String getCont_name() {
		return cont_name;
	}
	public void setCont_name(String cont_name) {
		this.cont_name = cont_name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getEduimage() {
		return eduimage;
	}
	public void setEduimage(String eduimage) {
		this.eduimage = eduimage;
	}
	public String getHiredate() {
		return hiredate;
	}
	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLast_update_time() {
		return last_update_time;
	}
	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}
	public int getMb_idx() {
		return mb_idx;
	}
	public void setMb_idx(int mb_idx) {
		this.mb_idx = mb_idx;
	}
	public int getMb_site_id() {
		return mb_site_id;
	}
	public void setMb_site_id(int mb_site_id) {
		this.mb_site_id = mb_site_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getPlace_id() {
		return place_id;
	}
	public void setPlace_id(int place_id) {
		this.place_id = place_id;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getRssi() {
		return rssi;
	}
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public int getSrc_type() {
		return src_type;
	}
	public void setSrc_type(int src_type) {
		this.src_type = src_type;
	}
	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public int getTime_diff_min() {
		return time_diff_min;
	}
	public void setTime_diff_min(int time_diff_min) {
		this.time_diff_min = time_diff_min;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getWritetime() {
		return writetime;
	}
	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}
	public int getWt_gubun() {
		return wt_gubun;
	}
	public void setWt_gubun(int wt_gubun) {
		this.wt_gubun = wt_gubun;
	}
	public String getWt_name() {
		return wt_name;
	}
	public void setWt_name(String wt_name) {
		this.wt_name = wt_name;
	}
	public String getWork_min() {
		return work_min;
	}
	public void setWork_min(String work_min) {
		this.work_min = work_min;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEdudate() {
		return edudate;
	}
	public void setEdudate(String edudate) {
		this.edudate = edudate;
	}
	public String getSealed_date1() {
		return sealed_date1;
	}
	public void setSealed_date1(String sealed_date1) {
		this.sealed_date1 = sealed_date1;
	}
	public String getSealed_date2() {
		return sealed_date2;
	}
	public void setSealed_date2(String sealed_date2) {
		this.sealed_date2 = sealed_date2;
	}
	public String getSealed_date3() {
		return sealed_date3;
	}
	public void setSealed_date3(String sealed_date3) {
		this.sealed_date3 = sealed_date3;
	}
	public String getSealed_date4() {
		return sealed_date4;
	}
	public void setSealed_date4(String sealed_date4) {
		this.sealed_date4 = sealed_date4;
	}
	public String getFirst_date() {
		return first_date;
	}
	public void setFirst_date(String first_date) {
		this.first_date = first_date;
	}
	public String getSealed_date() {
		return sealed_date;
	}
	public void setSealed_date(String sealed_date) {
		this.sealed_date = sealed_date;
	}
	public int getSealed_complete() {
		return sealed_complete;
	}
	public void setSealed_complete(int sealed_complete) {
		this.sealed_complete = sealed_complete;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getBeacon_mac() {
		return beacon_mac;
	}
	public void setBeacon_mac(String beacon_mac) {
		this.beacon_mac = beacon_mac;
	}
	public int getInput_state() {
		return input_state;
	}
	public void setInput_state(int input_state) {
		this.input_state = input_state;
	}
	public int getCont_ss_state() {
		return cont_ss_state;
	}
	public void setCont_ss_state(int cont_ss_state) {
		this.cont_ss_state = cont_ss_state;
	}
	public String getWrite_time() {
		return write_time;
	}
	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}
	public String getSection_name() {
		return section_name;
	}
	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getInout_type() {
		return inout_type;
	}
	public void setInout_type(int inout_type) {
		this.inout_type = inout_type;
	}
	public String getUw_id() {
		return uw_id;
	}
	public void setUw_id(String uw_id) {
		this.uw_id = uw_id;
	}
	public String getBeacon_role() {
		return beacon_role;
	}
	public void setBeacon_role(String beacon_role) {
		this.beacon_role = beacon_role;
	}
	public List<Integer> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<Integer> groupList) {
		this.groupList = groupList;
	}
}
