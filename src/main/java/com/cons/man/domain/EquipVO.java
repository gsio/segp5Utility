package com.cons.man.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class EquipVO {
	
	private int id;

	private int site_id = -1;
	private int cont_id;
	private String cont_name;
	
	private int category_id;
	private String code; //equip_category;
	
	private String large_category_name; // 대분류
	private String small_category_name; // 소분류
	
	private String equip_registration_no;
	
	private String equip_standard;
	
	private String check_start;
	private String check_end;
	
	private String insur_start;
	private String insur_end;
	
	private int driver_id;
	private String driver_cont_name;
	private String driver_name;
	private String driver_license_no;	
	private String driver_phone;
	private String drvier_role;
	
	private String writer_id;
	private String writer_name;
	private String writer_cont_name;
	
	private String rent_cont_name;

	public String equip_img;
	public MultipartFile equip_img_file;
	
	private List<FileVO> file_list;	
	
	private List<EquipCheckVO> check_list;
	
	private String remark;
	
	private String write_time;	
	
	private String useyn;	
	
	// 뭐지?
	private String name;

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

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLarge_category_name() {
		return large_category_name;
	}

	public void setLarge_category_name(String large_category_name) {
		this.large_category_name = large_category_name;
	}

	public String getSmall_category_name() {
		return small_category_name;
	}

	public void setSmall_category_name(String small_category_name) {
		this.small_category_name = small_category_name;
	}

	public String getEquip_registration_no() {
		return equip_registration_no;
	}

	public void setEquip_registration_no(String equip_registration_no) {
		this.equip_registration_no = equip_registration_no;
	}

	public String getEquip_standard() {
		return equip_standard;
	}

	public void setEquip_standard(String equip_standard) {
		this.equip_standard = equip_standard;
	}

	public String getCheck_start() {
		return check_start;
	}

	public void setCheck_start(String check_start) {
		this.check_start = check_start;
	}

	public String getCheck_end() {
		return check_end;
	}

	public void setCheck_end(String check_end) {
		this.check_end = check_end;
	}

	public String getInsur_start() {
		return insur_start;
	}

	public void setInsur_start(String insur_start) {
		this.insur_start = insur_start;
	}

	public String getInsur_end() {
		return insur_end;
	}

	public void setInsur_end(String insur_end) {
		this.insur_end = insur_end;
	}

	public int getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}

	public String getDriver_cont_name() {
		return driver_cont_name;
	}

	public void setDriver_cont_name(String driver_cont_name) {
		this.driver_cont_name = driver_cont_name;
	}

	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	public String getDriver_license_no() {
		return driver_license_no;
	}

	public void setDriver_license_no(String driver_license_no) {
		this.driver_license_no = driver_license_no;
	}
	public String getDrvier_role() {
		return drvier_role;
	}

	public void setDrvier_role(String drvier_role) {
		this.drvier_role = drvier_role;
	}

	public String getDriver_phone() {
		return driver_phone;
	}

	public void setDriver_phone(String driver_phone) {
		this.driver_phone = driver_phone;
	}

	public String getWriter_id() {
		return writer_id;
	}

	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}	
	
	public String getWriter_cont_name() {
		return writer_cont_name;
	}	

	public String getWriter_name() {
		return writer_name;
	}

	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}

	public void setWriter_cont_name(String writer_cont_name) {
		this.writer_cont_name = writer_cont_name;
	}

	public String getRent_cont_name() {
		return rent_cont_name;
	}

	public void setRent_cont_name(String rent_cont_name) {
		this.rent_cont_name = rent_cont_name;
	}

	public String getEquip_img() {
		return equip_img;
	}

	public void setEquip_img(String equip_img) {
		this.equip_img = equip_img;
	}

	public MultipartFile getEquip_img_file() {
		return equip_img_file;
	}

	public void setEquip_img_file(MultipartFile equip_img_file) {
		this.equip_img_file = equip_img_file;
	}

	public List<FileVO> getFile_list() {
		return file_list;
	}

	public void setFile_list(List<FileVO> file_list) {
		this.file_list = file_list;
	}

	public List<EquipCheckVO> getCheck_list() {
		return check_list;
	}
	public void setCheck_list(List<EquipCheckVO> check_list) {
		this.check_list = check_list;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWrite_time() {
		return write_time;
	}

	public void setWrite_time(String write_time) {
		this.write_time = write_time;
	}

	public String getUseyn() {
		return useyn;
	}

	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
