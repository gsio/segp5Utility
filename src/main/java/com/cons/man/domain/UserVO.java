package com.cons.man.domain;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private int type;
	private int site_id = -1;
	private String site_name;
	private int site_auth;
	private String site_addr;
	private int site_group;	
	private int site_type;
	private String cons_name;
	private int company_id;
	private String company_name;
	
	@Min(value=0, message="업체를 선택해주시기 바랍니다")
	private int cont_id = -1;
	private String cont_name;
	private int cont_type;
	@NotNull(message="선택가능한 항목에서 선택해주시기 바랍니다.")
	private int role_code;
	private String role_name;	
	private String btype;	
	@NotEmpty(message="입력이 필요합니다")
	private String name;
	@NotEmpty(message="입력이 필요합니다")
	private String userid;	
	@NotEmpty(message="비밀번호 등록시 빈 값이 아니어야 합니다.")	
	private String password;
	private String phone;
	private String email;
	private String grade;
	private String pid;
	private String pushYN;	
	private String useyn; 	
	private String sign;
	private MultipartFile sign_file;	
	private String photo;
	private MultipartFile photo_file;	
	private List<Integer> siteIdList;	
	private String mission;
	private String r_gubun;
	private String org;
	private String gubun;	
	private int birth_year;	
	private int isOnlySafeManager;//안전관리자 2명이상인경우 대표할 1명지정
	private int isOnlyChief;//협력사에서 간혹 현장소장이 2명인경우 있음 이떄 한명만 대표할사람 지정
	private int isOnlyTeamLeader;//sign등을 대표할 공사팀장 		
	private int isManager; //웹 관리자 여부
	
	// Manage Beacon
	private int beacon_idx;
	private int nfc_idx;
	private int hasBeaconInfo;
		
	// SS New Form
	private String edudate;
	private String sealed_date1;
	private String sealed_date2;
	private String sealed_date3;
	private String sealed_date4;
	
	// FCM Token
	private String topic_alert;
	
	// Approval
	private int approval_type;
	private int content_id;
	
	// Checker (점검대상자)
	private int checker_id;
	
	private int certkey;
	
	private String last_update_time;
	
	private int work_state;
	
	public int getSite_group() {
		return site_group;
	}
	public void setSite_group(int site_group) {
		this.site_group = site_group;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public int getSite_auth() {
		return site_auth;
	}
	public void setSite_auth(int site_auth) {
		this.site_auth = site_auth;
	}
	public String getSite_addr() {
		return site_addr;
	}
	public void setSite_addr(String site_addr) {
		this.site_addr = site_addr;
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
	public int getCont_type() {
		return cont_type;
	}
	public void setCont_type(int cont_type) {
		this.cont_type = cont_type;
	}
	public int getRole_code() {
		return role_code;
	}
	public void setRole_code(int role_code) {
		this.role_code = role_code;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUseyn() {
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getMission() {
		return mission;
	}
	public void setMission(String mission) {
		this.mission = mission;
	}
	public String getR_gubun() {
		return r_gubun;
	}
	public void setR_gubun(String r_gubun) {
		this.r_gubun = r_gubun;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public MultipartFile getSign_file() {
		return sign_file;
	}
	public void setSign_file(MultipartFile sign_file) {
		this.sign_file = sign_file;
	}

	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_name() {
		return company_name;
	}
	public int getBirth_year() {
		return birth_year;
	}
	public void setBirth_year(int birth_year) {
		this.birth_year = birth_year;
	}
	public int getIsOnlySafeManager() {
		return isOnlySafeManager;
	}
	public void setIsOnlySafeManager(int isOnlySafeManager) {
		this.isOnlySafeManager = isOnlySafeManager;
	}
	public int getIsOnlyChief() {
		return isOnlyChief;
	}
	public void setIsOnlyChief(int isOnlyChief) {
		this.isOnlyChief = isOnlyChief;
	}
	public int getIsOnlyTeamLeader() {
		return isOnlyTeamLeader;
	}
	public void setIsOnlyTeamLeader(int isOnlyTeamLeader) {
		this.isOnlyTeamLeader = isOnlyTeamLeader;
	}
	public int getIsManager() {
		return isManager;
	}
	public void setIsManager(int isManager) {
		this.isManager = isManager;
	}
	public int getSite_type() {
		return site_type;
	}
	public void setSite_type(int site_type) {
		this.site_type = site_type;
	}
	public String getBtype() {
		return btype;
	}
	public void setBtype(String btype) {
		this.btype = btype;
	}
	public List<Integer> getSiteIdList() {
		return siteIdList;
	}
	public void setSiteIdList(List<Integer> siteIdList) {
		this.siteIdList = siteIdList;
	}
	public String getCons_name() {
		return cons_name;
	}
	public void setCons_name(String cons_name) {
		this.cons_name = cons_name;
	}
	public int getBeacon_idx() {
		return beacon_idx;
	}
	public void setBeacon_idx(int beacon_idx) {
		this.beacon_idx = beacon_idx;
	}
	public int getHasBeaconInfo() {
		return hasBeaconInfo;
	}
	public void setHasBeaconInfo(int hasBeaconInfo) {
		this.hasBeaconInfo = hasBeaconInfo;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public MultipartFile getPhoto_file() {
		return photo_file;
	}
	public void setPhoto_file(MultipartFile photo_file) {
		this.photo_file = photo_file;
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
	public String getTopic_alert() {
		return topic_alert;
	}
	public void setTopic_alert(String topic_alert) {
		this.topic_alert = topic_alert;
	}
	public String getPushYN() {
		return pushYN;
	}
	public void setPushYN(String pushYN) {
		this.pushYN = pushYN;
	}
	public int getNfc_idx() {
		return nfc_idx;
	}
	public void setNfc_idx(int nfc_idx) {
		this.nfc_idx = nfc_idx;
	}
	public String getEdudate() {
		return edudate;
	}
	public void setEdudate(String edudate) {
		this.edudate = edudate;
	}
	public int getApproval_type() {
		return approval_type;
	}
	public void setApproval_type(int approval_type) {
		this.approval_type = approval_type;
	}
	public int getContent_id() {
		return content_id;
	}
	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}
	public int getChecker_id() {
		return checker_id;
	}
	public void setChecker_id(int checker_id) {
		this.checker_id = checker_id;
	}
	public int getCertkey() {
		return certkey;
	}
	public void setCertkey(int certkey) {
		this.certkey = certkey;
	}
	public String getLast_update_time() {
		return last_update_time;
	}
	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}
	public int getWork_state() {
		return work_state;
	}
	public void setWork_state(int work_state) {
		this.work_state = work_state;
	}
}
