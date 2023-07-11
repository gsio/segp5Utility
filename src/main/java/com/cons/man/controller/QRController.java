package com.cons.man.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cons.man.domain.CertkeyVO;
import com.cons.man.domain.ContVO;
import com.cons.man.domain.RoleVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.SiteVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkerVO;
import com.cons.man.services.ContService;
import com.cons.man.services.QRService;
import com.cons.man.services.SectionService;
import com.cons.man.services.SensorService;
import com.cons.man.services.UserService;
import com.cons.man.services.WorkerService;

@RestController
@Controller(value="QRController")
public class QRController {
	
	private static int CUR_SITE_ID = 17;

	@Resource(name="QRService")
	private QRService qrService;
	
	@Resource(name="ContService")
	private ContService contService;
	
	@Resource(name="UserService")
	private UserService userService;
	
	@Resource(name = "WorkerService")
	private WorkerService workerService;
	
	@RequestMapping(value = {"/qrAttend"})
	public String qrAttendInitPage(HttpSession session, Model model) {		
		List<ContVO> contList = contService.getContList(CUR_SITE_ID);		
		model.addAttribute("contList", contList);
		
		List<RoleVO> roleList = userService.getURList();				
		model.addAttribute("uRList", roleList);
		
		List<WorkerVO> wtypeList = workerService.getWTList();	
		model.addAttribute("wTList", wtypeList);
				
		return "qr/qrAttend";
	}
	

	@RequestMapping(value = {"/qr/checkCertKeyVaild"}, method = RequestMethod.GET)
	public ResponseEntity<CertkeyVO> checkCertKeyVaild(HttpSession session, HttpServletResponse response,
		@RequestParam(value="phone", defaultValue="") String phone,
		@RequestParam(value="certkey", defaultValue="-1") String certkey)
	{		
		try {			
			CertkeyVO vo = userService.checkModifyUserId(phone);				
			if(vo == null) {
				return new ResponseEntity<CertkeyVO>(HttpStatus.NO_CONTENT);		
			}					
			else {				
				if(vo.getCertkey().equals(certkey)) {		
					return new ResponseEntity<CertkeyVO>(vo, HttpStatus.OK);		
				}
				else {
					return new ResponseEntity<CertkeyVO>(HttpStatus.NO_CONTENT);		
				}		
			}				
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<CertkeyVO>(HttpStatus.NO_CONTENT);		
		}
	}	
	

	@RequestMapping(value = {"/qr/checkDuplicateCheck"}, method = RequestMethod.POST)
	public void checkDuplicateCheck(HttpSession session, HttpServletResponse response,
		@RequestParam(value="role", defaultValue="1") int role,
		@RequestParam(value="name", defaultValue="") String name,
		@RequestParam(value="jumin", defaultValue="") String jumin,
		@RequestParam(value="jumin_back", defaultValue="1") int jumin_back,
		@RequestParam(value="phone", defaultValue="") String phone,
		@RequestParam(value="cont_id", defaultValue="-1") int cont_id,
		@RequestParam(value="cont_name", defaultValue="") String cont_name,
		@RequestParam(value="work_type", defaultValue="-1") int work_type)
	{
		int resultCont = -1;
		JSONObject jo = new JSONObject();
		/*
		System.out.println("[role]: " + role);
		System.out.println("[name]: " + name);
		System.out.println("[jumin]: " + jumin);
		System.out.println("[jumin_back]: " + jumin_back);
		System.out.println("[phone]: " + phone);
		System.out.println("[cont_id]: " + cont_id);
		System.out.println("[cont_name]: " + cont_name);		
		System.out.println("[work_type]: " + work_type);
		*/			
		
		// 업체 등록
		if(cont_id < 0) {
			resultCont = contService.insertContTemp(cont_name);
			if(resultCont < 0) {
				jo.put("result", "false");
				jo.put("rsCode", "405");
				jo.put("err", "입력하신 업체가 이미 존재합니다.");
				
				try {
					response.getWriter().print(jo.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}	
			else {
				cont_id = resultCont;
			}
			
		}
		
		int resultPhone = qrService.checkUWDuplicatePhone(phone);
		System.out.println("[resultPhone]: " + resultPhone);
		
		if(resultPhone > 0) {
			jo.put("result", "false");
			jo.put("rsCode", "406");
			jo.put("err", "입력하신 전화번호가 이미 존재합니다.");
			
			try {
				response.getWriter().print(jo.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		else {
			
			CertkeyVO vo = qrService.insertUWData(role, name, phone, cont_id, jumin, jumin_back, work_type);
			if(vo.getUw_id() != null && !vo.getUw_id().equals("")) {
				jo.put("result", "true");
				jo.put("rsCode", "200");	
				jo.put("role", vo.getRole());
				jo.put("uw_id", vo.getUw_id());
			}
			else {
				jo.put("result", "false");
				jo.put("rsCode", "407");				
				jo.put("err", "등록 에러");	
			}
			
			try {
				response.getWriter().print(jo.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}		
	
	@RequestMapping(value = {"/qr/insertQRInData"}, method = RequestMethod.POST)
	public void insertQRInData(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1") int site_id,
		@RequestParam(value="uw_id", defaultValue="") String uw_id,
		@RequestParam(value="role", defaultValue="-1") int role,
		@RequestParam(value="comment", defaultValue="") String comment)
	{	
		//System.out.println("[insertQRInData]: " + site_id+"/"+uw_id+"/"+role+"/"+comment);
		int resultQRIn = qrService.insertQRInData(site_id, uw_id, role);
		JSONObject jo = new JSONObject();
		if(resultQRIn > 0) {
			jo.put("result", "true");
		}
		else {
			jo.put("result", "false");
		}
		
		try {
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = {"/qr/insertQROutData"}, method = RequestMethod.POST)
	public void insertQROutData(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1") int site_id,
		@RequestParam(value="uw_id", defaultValue="") String uw_id,
		@RequestParam(value="role", defaultValue="-1") int role,
		@RequestParam(value="comment", defaultValue="") String comment)
	{	
		//System.out.println("[insertQROutData]: " + site_id+"/"+uw_id+"/"+role+"/"+comment);
		int resultQROut = qrService.insertQROutData(site_id, uw_id, role, comment);
		JSONObject jo = new JSONObject();
		if(resultQROut > 0) {
			jo.put("result", "true");
		}
		else {
			jo.put("result", "false");
		}
		
		try {
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


