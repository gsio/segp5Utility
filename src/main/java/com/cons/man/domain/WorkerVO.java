package com.cons.man.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class WorkerVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private int site_id = -1;
	private int cont_id;
	private String cont_name;
	private String phone;
	private String gubun;					// 0:내국인, 1:외국인	
	private String name;	
	private String jumin;
	private int jumin_back;
	private String passno;					// 여권번호
	private int t_id;
	private String t_name;
	private String country;	
	private int gender;	
	private String btype;
	private String edudate;					// 신규교육일
	private String eduimage;
	private MultipartFile eduimage_file;
	private String hiredate;				// 채용일(입력받음)		
	private String remark;
	private int t_gubun;	
	private int isForeign;					// 외국인
	private int file_modify_1 = 0;			// 0:insert, 1:update
	
	// 삼성 엔지니어링 전용
	private String sealed_date1;
	private String sealed_date2;
	private String sealed_date3;
	private String sealed_date4;
	
	// 기기 배정 관련;
	private int beacon_idx;
	private int nfc_idx;
	private int equip_id;
	
	private String last_update_time;
	
	// Excel 사진 업로드 관련 230207
	private String photo;
	private String uuid;
	
	private List<WorkerVO> workerList = new ArrayList<WorkerVO>(2000);
	
	public List<WorkerVO> getWorkerList() {
		return workerList;
	}
	public void setWorkerList(List<WorkerVO> workerList) {
		this.workerList = workerList;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJumin() {
		return jumin;
	}
	public void setJumin(String jumin) {
		this.jumin = jumin;
	}
	public int getJumin_back() {
		return jumin_back;
	}
	public void setJumin_back(int jumin_back) {
		this.jumin_back = jumin_back;
	}
	public String getPassno() {
		return passno;
	}
	public void setPassno(String passno) {
		this.passno = passno;
	}
	public String getT_name() {
		return t_name;
	}
	public void setT_name(String t_name) {
		this.t_name = t_name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getBtype() {
		return btype;
	}
	public void setBtype(String btype) {
		this.btype = btype;
	}
	public String getEdudate() {
		return edudate;
	}
	public void setEdudate(String edudate) {
		this.edudate = edudate;
	}
	public String getEduimage() {
		return eduimage;
	}
	public void setEduimage(String eduimage) {
		this.eduimage = eduimage;
	}
	public MultipartFile getEduimage_file() {
		return eduimage_file;
	}
	public void setEduimage_file(MultipartFile eduimage_file) {
		this.eduimage_file = eduimage_file;
	}
	public String getHiredate() {
		return hiredate;
	}
	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getT_gubun() {
		return t_gubun;
	}
	public void setT_gubun(int t_gubun) {
		this.t_gubun = t_gubun;
	}
	public int getIsForeign() {
		return isForeign;
	}
	public void setIsForeign(int isForeign) {
		this.isForeign = isForeign;
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
	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public int getFile_modify_1() {
		return file_modify_1;
	}
	public void setFile_modify_1(int file_modify_1) {
		this.file_modify_1 = file_modify_1;
	}
	public int getBeacon_idx() {
		return beacon_idx;
	}
	public void setBeacon_idx(int beacon_idx) {
		this.beacon_idx = beacon_idx;
	}
	public int getNfc_idx() {
		return nfc_idx;
	}
	public void setNfc_idx(int nfc_idx) {
		this.nfc_idx = nfc_idx;
	}
	public int getEquip_id() {
		return equip_id;
	}
	public void setEquip_id(int equip_id) {
		this.equip_id = equip_id;
	}
	public String getLast_update_time() {
		return last_update_time;
	}
	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
