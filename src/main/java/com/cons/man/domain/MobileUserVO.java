package com.cons.man.domain;

import java.util.List;

public class MobileUserVO {
	
	private String id;
	private String cont_id;
	private String role_code;
	private String name;
	private String userid;
	private String password;
	private String phone;
	private String email;
	private String grade;
	private String pid;
	private String useyn;
	private String cname;
	private String type;
	private String sname;
	private String site_id;
	private String auth;
	private int site_type;
	
	private String dtype;
	private String dcode;
	private String rname;
	
	private String muid;
	private String year;
	private String month;
	private String day;
	private String workdate;
	private String m_cont_id;
	private String option_gubun;
	private double lng;
	private double lat;
	private String isforeign;
	private String device_name;
	private String t_name;
	private String t_gubun;
	private String macaddress;
	private String section;
	private String firstdate;
	private String recoid;
	private String gubun;
	private String num;
	private String checktime;
	private String beacon_id;
	
	private String mission;
	private String tunel1;
	private String tunel2;
	private String tunel3;
	private String tunel;
	private String u_id;
	private String role;
	private String checkyn;
	private String title;
	private String target;
	private String checkdate;
	private String response_manager_id;
	private String site_group;
	
	
	private List<String> searchlist;
	
	private String countdata;
	private String[] contIdArray = null;
	
	
	//gsilmasure

	private String user_id;
	private String uname;
	private String be_image;
	private String be_content;
	private String mea_content;
	private String duedate;
	private String status;
	private String comfirm_id;
	private String cuname;
	private String no;
	private String category;
	private String battery;
	
	private String sign;
	private String count;
	
	private String cons_name;
	private String line_name;
	private int idx;
	
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	
	
	
	
	public String getSite_group() {
		return site_group;
	}
	public void setSite_group(String site_group) {
		if( site_group == null ) site_group = "1";
		this.site_group = site_group;
	}
	public String getCons_name() {
		if( cons_name == null ) cons_name = "";
		return cons_name;
	}
	public void setCons_name(String cons_name) {
		this.cons_name = cons_name;
	}
	public String getLine_name() {
		if( line_name == null ) line_name = "";
		return line_name;
	}
	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}
	public String getSign() {
		if( sign == null ) sign = "";
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCount() {
		if( count == null ) count = "";
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getCheckdate() {
		if( checkdate == null ) checkdate = "";
		return checkdate;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public String getResponse_manager_id() {
		if( response_manager_id == null ) response_manager_id = "";
		return response_manager_id;
	}
	public void setResponse_manager_id(String response_manager_id) {
		this.response_manager_id = response_manager_id;
	}
	public String getTarget() {
		if( target == null ) target = "";
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTitle() {
		if( title == null ) title = "";
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBattery() {
		return battery;
	}
	public void setBattery(String battery) {
		this.battery = battery;
	}
	public String getAuth() {
		if( auth == null ) auth = "";
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getNo() {
		if( no == null ) no = "";
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getCategory() {
		if( category == null ) category = "";
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCuname() {
		if( cuname == null ) cuname = "";
		return cuname;
	}
	public void setCuname(String cuname) {
		this.cuname = cuname;
	}
	public String getComfirm_id() {
		if( comfirm_id == null ) comfirm_id = "";
		return comfirm_id;
	}
	public void setComfirm_id(String comfirm_id) {
		this.comfirm_id = comfirm_id;
	}
	public String getUser_id() {
		if( user_id == null ) user_id = "";
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUname() {
		if( uname == null ) uname = "";
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getBe_image() {
		if( be_image == null ) be_image = "";
		return be_image;
	}
	public void setBe_image(String be_image) {
		this.be_image = be_image;
	}
	public String getBe_content() {
		if( be_content == null ) be_content = "";
		return be_content;
	}
	public void setBe_content(String be_content) {
		this.be_content = be_content;
	}
	public String getMea_content() {
		if( mea_content == null ) mea_content = "";
		return mea_content;
	}
	public void setMea_content(String mea_content) {
		this.mea_content = mea_content;
	}
	public String getDuedate() {
		if( duedate == null ) duedate = "";
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public String getStatus() {
		if( status == null ) status = "";
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getContIdArray() {
		return contIdArray;
	}
	public void setContIdArray(String[] contIdArray) {
		this.contIdArray = contIdArray;
	}
	public String getCheckyn() {
		if( checkyn == null ) checkyn = "";
		return checkyn;
	}
	public void setCheckyn(String checkyn) {
		this.checkyn = checkyn;
	}
	public String getCountdata() {
		if( countdata == null ) countdata = "";
		return countdata;
	}
	public void setCountdata(String countdata) {
		this.countdata = countdata;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getU_id() {
		if( u_id == null ) u_id = "";
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getTunel() {
		if( tunel == null ) tunel = "";
		return tunel;
	}
	public void setTunel(String tunel) {
		this.tunel = tunel;
	}
	public String getTunel1() {
		return tunel1;
	}
	public void setTunel1(String tunel1) {
		this.tunel1 = tunel1;
	}
	public String getTunel2() {
		return tunel2;
	}
	public void setTunel2(String tunel2) {
		this.tunel2 = tunel2;
	}
	public String getTunel3() {
		return tunel3;
	}
	public void setTunel3(String tunel3) {
		this.tunel3 = tunel3;
	}
	public String getMission() {
		if( mission == null ) mission = "";
		return mission;
	}
	public void setMission(String mission) {
		this.mission = mission;
	}
	public String getBeacon_id() {
		return beacon_id;
	}
	public void setBeacon_id(String beacon_id) {
		this.beacon_id = beacon_id;
	}
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getGubun() {
		if( gubun == null ) gubun = "";
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getRecoid() {
		return recoid;
	}
	public void setRecoid(String recoid) {
		this.recoid = recoid;
	}
	public String getFirstdate() {
		return firstdate;
	}
	public void setFirstdate(String firstdate) {
		this.firstdate = firstdate;
	}
	public String getMacaddress() {
		return macaddress;
	}
	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getIsforeign() {
		if( isforeign == null ) isforeign = "";
		return isforeign;
	}
	public void setIsforeign(String isforeign) {
		this.isforeign = isforeign;
	}
	public String getDevice_name() {
		if( device_name == null ) device_name = "";
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getT_name() {
		if( t_name == null ) t_name = "";
		return t_name;
	}
	public void setT_name(String t_name) {
		this.t_name = t_name;
	}
	public String getT_gubun() {
		if( t_gubun == null ) t_gubun = "";
		return t_gubun;
	}
	public void setT_gubun(String t_gubun) {
		this.t_gubun = t_gubun;
	}
	public List<String> getSearchlist() {
		return searchlist;
	}
	public void setSearchlist(List<String> searchlist) {
		this.searchlist = searchlist;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public String getOption_gubun() {
		if( option_gubun == null ) {
			option_gubun = "0";
		}
		return option_gubun;
	}
	public void setOption_gubun(String option_gubun) {
		this.option_gubun = option_gubun;
	}
	public String getM_cont_id() {
		return m_cont_id;
	}
	public void setM_cont_id(String m_cont_id) {
		this.m_cont_id = m_cont_id;
	}
	public String getWorkdate() {
		return workdate;
	}
	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}
	public String getMuid() {
		if( muid == null ) {
			muid = "";
		}
		return muid;
	}
	public void setMuid(String muid) {
		this.muid = muid;
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
	public String getDtype() {
		return dtype;
	}
	public void setDtype(String dtype) {
		this.dtype = dtype;
	}
	public String getDcode() {
		return dcode;
	}
	public void setDcode(String dcode) {
		this.dcode = dcode;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCont_id() {
		if(cont_id == null) cont_id = "";
		return cont_id;
	}
	
	public void setCont_id(String cont_id) {
		this.cont_id = cont_id;
	}
	public String getRole_code() {
		return role_code;
	}
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
	public String getName() {
		if(name == null) name = "";
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
		if(phone == null) phone = "";
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		if(email == null) email = "";
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGrade() {
		if(grade == null) grade = "";
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getPid() {
		if(pid == null) pid = "";
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUseyn() {
		if( useyn == null ) useyn = "";
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	public String getCname() {
		if( cname == null ) cname = "";
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public int getSite_type() {
		return site_type;
	}
	public void setSite_type(int site_type) {
		this.site_type = site_type;
	}
	
	

}
