package com.cons.man.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cons.man.domain.AppVersionCheckVO;
import com.cons.man.domain.TargetVO;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.ContVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkTypeVO;
import com.cons.man.domain.WorkerVO;
import com.cons.man.services.MobileService;
import com.cons.man.services.UserService;

@Controller(value="MobileController")
@RequestMapping("/mobile")
@RestController
public class MobileController {
	@Resource(name="UserService")
	private UserService userService;
	
	@Resource(name="MobileService")
	private MobileService mobileService;
	
	@RequestMapping(value = {"/getCurrentAppVersion"}, method = RequestMethod.GET)				 
	public ResponseEntity<AppVersionCheckVO> getCurrentAppVersion(HttpServletResponse response)
	{	
		AppVersionCheckVO vo = mobileService.getCurrentAppVersion();
		return new ResponseEntity<AppVersionCheckVO>(vo, HttpStatus.OK);
	}

	@RequestMapping( value = "/login", method = RequestMethod.POST )
	public ResponseEntity<UserVO> login( HttpServletResponse response,
		@RequestParam(value="userid", defaultValue="")String userid,
		@RequestParam(value="password", defaultValue="")String password,		
		@RequestParam(value="phone", defaultValue="")String phone,
		@RequestParam(value="fcm_token", defaultValue="")String fcm_token) 
	{
		//System.out.println("login: " + userid + "/" + password + "/" + phone + "/" + fcm_token);
		UserVO vo = new UserVO();
		vo.setUserid(userid);
		vo.setPassword(password);
		
		// 전화번호 받는 이유: 개개인 핸드폰에서만 로그인이 가능하도록 접근제어 가능
		UserVO result = userService.getUserByUserIdAndPW(vo);
		
		if(result != null) {	
			mobileService.updateLoginInfo(result.getId(), fcm_token);		
			return new ResponseEntity<UserVO>(result, HttpStatus.OK);
		}else {			
			return new ResponseEntity<UserVO>(new UserVO(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/insertUserAppVersion")
	public void insertUserAppVersion( HttpServletResponse response,
		@RequestParam(value="user_id", defaultValue="")String user_id,
		@RequestParam(value="app_ver", defaultValue="")String app_ver,
		@RequestParam(value="android_ver", defaultValue="")String android_ver,
		@RequestParam(value="browser_ver", defaultValue="")String browser_ver,
		@RequestParam(value="etc", defaultValue="")String etc
	) {		
		AppVersionCheckVO vo = new AppVersionCheckVO();
		vo.setUser_id(user_id); 
		vo.setApp_ver(app_ver);
		vo.setAndroid_ver(android_ver);
		vo.setBrowser_ver(browser_ver);
		vo.setEtc(etc);
		
		mobileService.insertUserAppVersion(vo);
		try {
			response.getWriter().print(true);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = {"/userList"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<UserVO>> getUserList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="cont_id", defaultValue="-1")int cont_id,
		@RequestParam(value="cont_type", defaultValue="-1")int cont_type			
	) {	
		List<UserVO> user_List = mobileService.getUserList(site_id, cont_id, cont_type);
		return new ResponseEntity<List<UserVO>>(user_List, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/contList"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<ContVO>> getContList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="role", defaultValue="-1")int role			
	) {	
		List<ContVO> cont_List = mobileService.getContList(site_id, role);
		return new ResponseEntity<List<ContVO>>(cont_List, HttpStatus.OK);
	} 
	
	@RequestMapping(value = {"/workerList"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<WorkerVO>> getWorkerList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="cont_id", defaultValue="-1")int cont_id,
		@RequestParam(value="cont_type", defaultValue="-1")int cont_type			
	) {	
		List<WorkerVO> user_List = mobileService.getWorkerList(site_id, cont_id, cont_type);
		return new ResponseEntity<List<WorkerVO>>(user_List, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/workTypeList"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<WorkTypeVO>> getWorkTypeList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id		
	) {	
		List<WorkTypeVO> wType_List = mobileService.getWorkTypeList(site_id);
		return new ResponseEntity<List<WorkTypeVO>>(wType_List, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/insertWorker"}, method = RequestMethod.POST)		
	public void insertWorker(HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="")String site_id,		
		@RequestParam(value="cont_id", defaultValue="")String cont_id,		
		@RequestParam(value="phone", defaultValue="")String phone, 
		@RequestParam(value="gubun", defaultValue="")String gubun,
		@RequestParam(value="name", defaultValue="")String name,
		@RequestParam(value="jumin", defaultValue="")String jumin,
		@RequestParam(value="t_id", defaultValue="")String t_id,
		@RequestParam(value="btype", defaultValue="")String btype,
		@RequestParam(value="hiredate", defaultValue="")String hiredate,
		@RequestParam(value="edudate", defaultValue="")String edudate,
		@RequestParam(value="sealed_date1", defaultValue="")String sealed_date1,
		@RequestParam(value="sealed_date2", defaultValue="")String sealed_date2,
		@RequestParam(value="sealed_date3", defaultValue="")String sealed_date3,
		@RequestParam(value="sealed_date4", defaultValue="")String sealed_date4
	) {	
		/*
		System.out.println("[INSERT] site_id: " + site_id);
		System.out.println("[INSERT] cont_id: " + cont_id);
		System.out.println("[INSERT] phone: " + phone);
		System.out.println("[INSERT] gubun: " + gubun);
		System.out.println("[INSERT] name: " + name);
		System.out.println("[INSERT] jumin: " + jumin);
		System.out.println("[INSERT] t_id: " + t_id);
		System.out.println("[INSERT] btype: " + btype);
		System.out.println("[INSERT] hiredate: " + hiredate);
		System.out.println("[INSERT] edudate: " + edudate);
		System.out.println("[INSERT] sealed_date1: " + sealed_date1);
		System.out.println("[INSERT] sealed_date2: " + sealed_date2);
		System.out.println("[INSERT] sealed_date3: " + sealed_date3);
		System.out.println("[INSERT] sealed_date4: " + sealed_date4);
		*/
		WorkerVO w = new WorkerVO();
		
		w.setSite_id(Integer.parseInt(site_id));
		w.setCont_id(Integer.parseInt(cont_id));
		w.setPhone(phone);
		w.setGubun(gubun);
		w.setName(name);
		w.setJumin(jumin);
		w.setT_id(Integer.parseInt(t_id));
		w.setBtype(btype);
		w.setHiredate(hiredate);
		w.setEdudate(edudate);
		w.setSealed_date1(sealed_date1);
		w.setSealed_date2(sealed_date2);
		w.setSealed_date3(sealed_date3);
		w.setSealed_date4(sealed_date4);

		String resultJson = mobileService.insertWorker(w);
		
		try {
			response.getWriter().print(resultJson);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@RequestMapping(value = {"/updateWorker"}, method = RequestMethod.POST)		
	public void updateWorker(HttpServletResponse response,
		@RequestParam(value="id", defaultValue="")String id,
		@RequestParam(value="site_id", defaultValue="")String site_id,		
		@RequestParam(value="cont_id", defaultValue="")String cont_id,		
		@RequestParam(value="phone", defaultValue="")String phone, 
		@RequestParam(value="gubun", defaultValue="")String gubun,
		@RequestParam(value="name", defaultValue="")String name,
		@RequestParam(value="jumin", defaultValue="")String jumin,
		@RequestParam(value="t_id", defaultValue="")String t_id,
		@RequestParam(value="btype", defaultValue="")String btype,
		@RequestParam(value="hiredate", defaultValue="")String hiredate,
		@RequestParam(value="edudate", defaultValue="")String edudate,
		@RequestParam(value="sealed_date1", defaultValue="")String sealed_date1,
		@RequestParam(value="sealed_date2", defaultValue="")String sealed_date2,
		@RequestParam(value="sealed_date3", defaultValue="")String sealed_date3,
		@RequestParam(value="sealed_date4", defaultValue="")String sealed_date4
	) {	
		/*
		System.out.println("[UPDATE] id: " + id);
		System.out.println("[UPDATE] site_id: " + site_id);
		System.out.println("[UPDATE] cont_id: " + cont_id);
		System.out.println("[UPDATE] phone: " + phone);
		System.out.println("[UPDATE] gubun: " + gubun);
		System.out.println("[UPDATE] name: " + name);
		System.out.println("[UPDATE] jumin: " + jumin);
		System.out.println("[UPDATE] t_id: " + t_id);
		System.out.println("[UPDATE] btype: " + btype);
		System.out.println("[UPDATE] hiredate: " + hiredate);
		System.out.println("[UPDATE] edudate: " + edudate);
		System.out.println("[UPDATE] sealed_date1: " + sealed_date1);
		System.out.println("[UPDATE] sealed_date2: " + sealed_date2);
		System.out.println("[UPDATE] sealed_date3: " + sealed_date3);
		System.out.println("[UPDATE] sealed_date4: " + sealed_date4);
		*/
		WorkerVO w = new WorkerVO();
		
		w.setId(id);
		w.setSite_id(Integer.parseInt(site_id));
		w.setCont_id(Integer.parseInt(cont_id));
		w.setPhone(phone);
		w.setGubun(gubun);
		w.setName(name);
		w.setJumin(jumin);
		w.setT_id(Integer.parseInt(t_id));
		w.setBtype(btype);
		w.setHiredate(hiredate);
		w.setEdudate(edudate);
		w.setSealed_date1(sealed_date1);
		w.setSealed_date2(sealed_date2);
		w.setSealed_date3(sealed_date3);
		w.setSealed_date4(sealed_date4);

		String resultJson = mobileService.updateWorker(w);
		
		try {
			response.getWriter().print(resultJson);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@RequestMapping( value = "/checkPhoneNumber" )
	public void checkPhoneNumber( HttpServletResponse response,
		@RequestParam(value="phone", defaultValue="")String phone) {		
  		String resultid = mobileService.checkPhoneNumber(phone);
		try {
			response.getWriter().print(resultid);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

