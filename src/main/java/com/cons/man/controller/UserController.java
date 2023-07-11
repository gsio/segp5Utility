package com.cons.man.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cons.man.domain.CertkeyVO;
import com.cons.man.domain.ContVO;
import com.cons.man.domain.RoleVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.SiteVO;
import com.cons.man.domain.UserVO;
import com.cons.man.services.ContService;
import com.cons.man.services.SiteService;
import com.cons.man.services.UserService;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller(value="UserController")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name="UserService")
	private UserService userService;
	
	@Resource(name="ContService")
	private ContService contService;
	
	@Resource(name="SiteService")
	private SiteService siteService;
	
	public static final int MAX_ROW_NUM = 10;
	public static final int MAX_PAGE_NUM = 10;
	
	@RequestMapping(value = "/json/getUserList")
	public void getUserList(HttpSession session, HttpServletResponse response,
		@RequestParam("site_id") int site_id, 
		@RequestParam(value="cont_id", defaultValue="-1") int cont_id,
		@RequestParam(value="hasBeaconInfo", defaultValue="0") int hasBeaconInfo)
	{
		UserVO searchVO = new UserVO();
		JSONObject jo = new JSONObject();
	
		if (site_id >= 0 ) {
			searchVO.setSite_id(site_id);
			searchVO.setCont_id(cont_id);
			searchVO.setHasBeaconInfo(hasBeaconInfo);

			List<UserVO> list = userService.getUserList(searchVO);

			if (list != null && list.size() > 0) {
				jo.put("result", "true");
				JSONArray ja = new JSONArray();
				for (UserVO vo : list) {
					JSONObject jo2 = new JSONObject(vo);
					jo2.put("cont_id", vo.getCont_id());
					jo2.put("name", vo.getName());
					jo2.put("phone", vo.getPhone());
					jo2.put("role_name", vo.getRole_name());
					jo2.put("beacon_idx", vo.getBeacon_idx());

					ja.put(jo2);
				}
				jo.put("item", ja);

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
		
	@RequestMapping(value = {"/registerUser"})
	public String registerUser(@RequestParam(value="u_id",required=false)String u_id,
		@RequestParam(value="tar_site_id",required=false)int tar_site_id,
		HttpServletRequest request, Model model, HttpSession session) {
		UserVO userVO;		
		
		//등록화면에서 보여줄 업체리스트, 권한리스트
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");
		setBaseModel(tar_site_id, model);
		
		// UPDATE
		if(u_id != null && !u_id.equals("")) {
			userVO = userService.getUserById(u_id);			
			model.addAttribute("updateMode", true);
		}
		// INSERT
		else { 
			userVO = new UserVO();	
			userVO.setSite_id(tar_site_id);			
			SiteVO siteVO = siteService.getSiteInfo(tar_site_id);
			userVO.setSite_name(siteVO.getName());			
			model.addAttribute("updateMode", false);
		}		

		if(userInfo.getSite_auth() == 3){
			List<SiteVO> siteList =  siteService.getSiteListWithSiteGroup(userInfo.getSite_group());
			model.addAttribute("siteList", siteList);
		}
		
		model.addAttribute("userVO", userVO);
		session.setAttribute("contentView", "menu_user");
		return "register_user";
	}
	
	@RequestMapping(value = "insertUser")
	public String insertUser(HttpSession session, @ModelAttribute @Valid UserVO userVO, BindingResult bindingResult, Model model, HttpServletRequest request) {
		boolean hasError = false;
		
		//등록화면에서 보여줄 업체리스트, 권한리스트 
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");		
		setBaseModel(userVO.getSite_id(), model);

		if(bindingResult.hasErrors())
			hasError = true;		
		else {
			try{
				//TODO: 등록자가 gsil이면 일단 발주처관리자 =999로 고정시킴(실제로는 UI레벨에서 제한을 둬야 맞을듯함)
				if(userInfo.getRole_code() == 1000){ 
					userVO.setRole_code(999);
				}
				
				userService.insert(userVO);
				//siteService.insertFavList(SiteFavoriteVO.TYPE_USER, userVO.getId(), userVO.getFavList());
			}catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
	
		if(hasError) {//에러 발생시 왔던 주소로 되돌아감
			//error발생시 되돌아가더라도 insertMode상태 유지위함
			model.addAttribute("updateMode", false);		
			return "registerUser";			
		}
		else{//성공시 list로 이동
			return"redirect:menu_user?site_id=" + userVO.getSite_id();
		}		
	}	
	
	@RequestMapping(value = "updateUser")
	public String updateUser(HttpSession session, @ModelAttribute @Valid UserVO userVO, BindingResult bindingResult, Model model, HttpServletRequest request) {
		boolean hasError = false;
		
		//등록화면에서 보여줄 업체리스트, 권한리스트 
		UserVO userInfo = (UserVO)session.getAttribute("userLoginInfo");		
		setBaseModel(userVO.getSite_id(), model);

		if(bindingResult.hasErrors())
			hasError = true;		
		else {
		
			try{
				if(userInfo.getRole_code() == 1000){ 
					userVO.setRole_code(999);
				}
				userService.update(userVO);
				//siteService.insertFavList(SiteFavoriteVO.TYPE_USER, userVO.getId(), userVO.getFavList());
			}catch(Exception e) {
				e.printStackTrace();
				hasError = true;
			}
		}
		
		if(hasError) {//에러 발생시 왔던 주소로 되돌아감
			//error발생시 되돌아가더라도 insertMode상태 유지위함
			model.addAttribute("updateMode", true);		
			return "registerUser";
			
		}
		else{//성공시 list로 이동
			return"redirect:menu_user?site_id=" + userVO.getSite_id();
		}
	}
	

	//USEYN => N으로 만들면서 userid는 중복나지 않게 id값을 userid뒤에 붙여줌 
	@RequestMapping(value = "deleteUser")
	public String deleteUser(HttpSession session, @ModelAttribute @Valid UserVO userVO, BindingResult bindingResult, Model model, HttpServletRequest request) {
		boolean hasError = false;
		
		try{
			userService.disableUser(userVO.getId());
		}catch(Exception e) {
			e.printStackTrace();
			hasError = true;
		}
		
		
		if(hasError) {//에러 발생시 왔던 주소로 되돌아감
			//error발생시 되돌아가더라도 insertMode상태 유지위함
			model.addAttribute("updateMode", true);		
			return "registerUser";
			
		}else{//성공시 list로 이동
			return"redirect:menu_user?site_id=" + userVO.getSite_id();
		}
	}	

	private void setBaseModel(int site_id, Model model) {

		ContVO contVO = new ContVO();	
		List<ContVO> contList = contService.getContList(site_id);
		model.addAttribute("contList", contList);
		
		//권한 리스트 호출
		List<RoleVO> roleList = userService.getRoleList();				
		model.addAttribute("roleList", roleList);
		
		List<RoleVO> order_roleList = userService.getRoleListByType(0);
		model.addAttribute("order_roleList", order_roleList);
		
		List<RoleVO> cowork_roleList = userService.getRoleListByType(1);
		model.addAttribute("cowork_roleList", cowork_roleList);
		
		List<RoleVO> extra_roleList = userService.getRoleListByType(2);
		model.addAttribute("extra_roleList", extra_roleList);
		
		List<RoleVO> bon_roleList = userService.getRoleListByType(3);
		model.addAttribute("bon_roleList", bon_roleList);
		
		List<RoleVO> val_roleList = userService.getRoleListByType(4);
		model.addAttribute("val_roleList", val_roleList);
	}
	
	@RequestMapping(value = "/duplicateIdCheck")
	public void duplicateIdCheck(String userid,  HttpServletResponse response) {
  		UserVO userVO = userService.getUserByUserId(userid);
		try {
			if(userVO == null)			
				response.getWriter().print(true);			
			else
				response.getWriter().print(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = {"/postChangeUserId"}, method = RequestMethod.POST)
	public void postChangeUserId(HttpSession session, HttpServletResponse response,
		@RequestParam(value="phone", defaultValue="") String phone,
		@RequestParam(value="userid", defaultValue="") String userid)
	{	
		userService.postChangeUserId(phone, userid);
	}
	
}
