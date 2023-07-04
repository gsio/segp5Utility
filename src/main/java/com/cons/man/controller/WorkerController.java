package com.cons.man.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cons.man.domain.ContVO;
import com.cons.man.domain.SiteVO;
import com.cons.man.domain.UserVO;
import com.cons.man.domain.WorkerVO;
import com.cons.man.services.ContService;
import com.cons.man.services.SiteService;
import com.cons.man.services.UserService;
import com.cons.man.services.WorkerService;
import com.cons.man.util.FileWriter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import springfox.documentation.annotations.ApiIgnore;

/**
 * Handles requests for the application home page.
 */
@ApiIgnore
@Controller(value = "WorkerController")
public class WorkerController {

	@Resource(name = "SiteService")
	private SiteService siteService;
	
	@Resource(name = "WorkerService")
	private WorkerService workerService;

	@Resource(name = "ContService")
	private ContService contService;

	@Resource(name = "UserService")
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(3000);
	}
	
	//근로자등록시 휴대폰 중복검사
	//같은 현장내에서 중복되면 X, 다른현장에서 중복된거면 site_id바꿔줘야하므로 PASS
	@RequestMapping( value = "/json/checkWorkerPhone" )
	public void checkWorkerPhone( HttpServletResponse response,
		@RequestParam(value="phone", defaultValue="")String phone)
	{
		String result = workerService.checkWorkerPhone(phone);
		try {
			response.getWriter().print(result);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = {"/registerWorker"})	
	public void registerWorker(@RequestParam(value="id",defaultValue="0")int id,
		HttpServletRequest request, Model model, HttpSession session) {
		
		WorkerVO workerVO = null;		
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		
		//update
		if(id > 0) { 
			workerVO = workerService.getWorker(id);			
			model.addAttribute("updateMode", true);
		}
		//insert
		else { 
			workerVO = new WorkerVO();	
			workerVO.setSite_id(userInfo.getSite_id());
			workerVO.setCont_id(userInfo.getCont_id());
			model.addAttribute("updateMode", false);
		}
		
		List<WorkerVO> wtypeList = workerService.getWorkerTypeList();		
		List<ContVO> contList = contService.getContList(userInfo.getSite_id());		
		
		model.addAttribute("contList", contList);
		model.addAttribute("workerVO", workerVO);
		model.addAttribute("wtypeList", wtypeList);
		session.setAttribute("contentView", "menu_record");//TODO: change workerList
	}
	
	@RequestMapping(value = "insertWorker")
	public String insertWorker(HttpSession session, @ModelAttribute @Valid WorkerVO workerVO, 
		BindingResult bindingResult, Model model, HttpServletRequest request) {
	
		boolean hasError = false;
		if(bindingResult.hasErrors()) {
			hasError = true;		
		}
		else {
			try{		
				workerService.insertWorkerList(workerVO.getSite_id(), workerVO.getCont_id(), workerVO.getWorkerList());								
			}
			catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
	
		if(hasError) {
			List<WorkerVO> wtypeList = workerService.getWorkerTypeList();
			model.addAttribute("wtypeList", wtypeList);
			return "registerWorker";
		}
		else{//성공시 list로 이동
			return "redirect:recordList";
		}
	}
	
	@RequestMapping(value = "insertWorkerExcel")
	public String insertWorkerExcel(HttpSession session, @ModelAttribute @Valid WorkerVO workerVO, 
		BindingResult bindingResult, Model model, HttpServletRequest request,
		@RequestParam(value="uuid", required=false)String uuid) {
	
		boolean hasError = false;
		if(bindingResult.hasErrors()) {
			hasError = true;		
		}
		else {	
			try {				
				workerService.insertWorkerExcelList(workerVO.getSite_id(), workerVO.getCont_id(), workerVO.getWorkerList(), uuid);								
			}
			catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
	
		if(hasError) {			
			return "etc/excel_worker";
		}
		else{
			//성공시 list로 이동
			return "redirect:recordList";
		}
	}
	
	@RequestMapping(value = "/json/getWorkerInfo")
	public void getWorkerInfo(HttpSession session, HttpServletResponse response,
		@RequestParam("worker_id") int worker_id) {
		
		JSONObject jo = new JSONObject();
		
		WorkerVO vo = workerService.getWorker(worker_id);
		ArrayList<WorkerVO> list  = new ArrayList<WorkerVO>();
		
		list.add(vo);
		if (vo != null) {
			jo.put("result", "true");
			jo.put("list", list);
		} else {
			jo.put("result", "false");
		}

		try {
			response.getWriter().print(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/json/updateWorker")
	public void updateWorker( HttpServletResponse response, HttpSession session,
		@RequestParam(value="dataVO", defaultValue="")String dataVO)
		throws JsonParseException, JsonMappingException, IOException {
		
			UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
			if(userInfo == null) return;
			ObjectMapper mapper = new ObjectMapper();
			WorkerVO workerVO = mapper.readValue(dataVO, WorkerVO.class);
			ArrayList<WorkerVO> workerList = new ArrayList<WorkerVO>();
			workerList.add(workerVO);
			workerService.updateRecord(workerList);
			
			try {
				response.getWriter().print(true);			
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
	}

	@RequestMapping(value = "/json/getWorkerListByContId")
	public void getWorkerListByContId(HttpSession session, HttpServletResponse response,
		@RequestParam(value = "site_id" , defaultValue="-1") int site_id,		
		@RequestParam("cont_id") int cont_id		
	) {
		WorkerVO searchVO = new WorkerVO();
		JSONObject jo = new JSONObject();
		
		if (site_id >= 0) {
			searchVO.setSite_id(site_id);
			searchVO.setCont_id(cont_id);
			List<WorkerVO> list = workerService.getWorkerListByContId(searchVO);

			if (list != null && list.size() > 0) {
				jo.put("result", "true");
				JSONArray ja = new JSONArray();
				for (WorkerVO vo : list) {
					JSONObject jo2 = new JSONObject();
					jo2.put("id", vo.getId());
					jo2.put("cont_id", vo.getCont_id());
					jo2.put("name", vo.getName());
					jo2.put("cont_name", vo.getCont_name());
					jo2.put("tname", vo.getT_name());
					jo2.put("t_id", vo.getT_id());
					jo2.put("t_gubun", vo.getT_gubun());
					jo2.put("hirerdate", vo.getHiredate());
					jo2.put("phone", vo.getPhone());
					jo2.put("jumin", vo.getJumin());
					jo2.put("edudate", vo.getEdudate());
					jo2.put("eduimage", vo.getEduimage());
					
					String phone="";
					if(vo.getPhone().length() > 7){
						String phone_head = vo.getPhone().substring(0, 3);
						String phone_tail = vo.getPhone().substring(7, vo.getPhone().length());
						 phone = phone_head + "-****-" + phone_tail;
						
					}else{
						phone = vo.getPhone();
					}
					
					jo2.put("phone", phone);			
					jo2.put("btype", vo.getBtype());
					jo2.put("beacon_idx", vo.getBeacon_idx());
					ja.put(jo2);
				}
				jo.put("worker_list", ja);
			} else {
				jo.put("result", "false");
			}
		} else {
			jo.put("result", "false");
		}

		try {
			response.getWriter().print(jo.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = {"/deleteWorker"})
	public String deleteWorker(HttpSession session, Model model,
		@RequestParam(value="id",required=false)int id,
		HttpServletRequest request) {
		UserVO userInfo = (UserVO) session.getAttribute("userLoginInfo");
		workerService.deleteWorker(userInfo.getSite_id(), id);
		return "redirect:recordList";		
	}
	
	@RequestMapping(value = "/json/uploadWorkerImage")
	public void json_test( HttpServletResponse response, HttpSession session,
		@RequestParam(value="worker_id",required=false) int worker_id,
		@RequestPart(value="ind_image_1",required=false) MultipartFile ind_image_1,
		@RequestParam(value="file_modify_1",defaultValue="0") int file_modify_1 // 0:insert, 1:update 
	) throws JsonParseException, JsonMappingException, IOException {		
		
		if(ind_image_1 != null && ind_image_1.getSize() > 0 || file_modify_1 == 1) {
			String virtName = "WORKER/WORKER_" + UUID.randomUUID().toString() +"_" + worker_id;			
			FileWriter.INSTANCE.writeFile(ind_image_1, virtName);
			workerService.updateEduImageByWorkerId(worker_id, virtName); //worker table 변경
		}
		
		try {
			JSONObject jo = new JSONObject();
			jo.put("result", "true");
			response.getWriter().print(jo.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	@RequestMapping(value = "/uploadWorkerImg")
	public void uploadWorkerImg(HttpServletResponse response, HttpSession session,
		@RequestPart(value="files",required=false) List<MultipartFile> fileList
	) throws JsonParseException, JsonMappingException, IOException {
		
		String uuid = "null";		
	
		if(fileList != null && fileList.size() > 0) {
			
			uuid = UUID.randomUUID().toString();
			
			for(MultipartFile file : fileList){		
				String virtName = "EXCEL/WORKER/" + uuid +"_" + file.getOriginalFilename();					
				FileWriter.INSTANCE.writeFile(file, virtName);
			}			
			try {
				JSONObject jo = new JSONObject();
				jo.put("result", "true");
				jo.put("uuid", uuid);
				response.getWriter().print(jo.toString());
			} 
			catch (IOException e) {
				e.printStackTrace();
			}	
			
		}		
		else {
			System.out.println("[uploadWorkerImg] fileList is NULL");			
			try {
				JSONObject jo = new JSONObject();
				jo.put("result", "false");
				response.getWriter().print(jo.toString());
			} 
			catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	@RequestMapping(value = { "/excel_worker" })
	public String excel_worker(HttpSession session, Model model) 
		throws Exception 
	{
		WorkerVO workerVO = null;		
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		
		workerVO = new WorkerVO();	
		workerVO.setSite_id(userInfo.getSite_id());
		workerVO.setCont_id(userInfo.getCont_id());
		
		List<WorkerVO> wtypeList = workerService.getWorkerTypeList();		
		List<ContVO> contList = contService.getContList(userInfo.getSite_id());		
		
		model.addAttribute("contList", contList);
		model.addAttribute("workerVO", workerVO);
		model.addAttribute("wtypeList", wtypeList);
		
		return "etc/excel_worker";
	}
}
