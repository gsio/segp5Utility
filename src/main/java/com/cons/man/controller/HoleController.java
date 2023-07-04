package com.cons.man.controller;

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

import com.cons.man.domain.HoleVO;
import com.cons.man.domain.TargetVO;
import com.cons.man.services.HoleService;

@RestController
@RequestMapping("/hole")
@Controller(value="HoleController")
public class HoleController {
	
	@Resource(name="HoleService")
	private HoleService holeService;
	
	@RequestMapping(value = {"/list"}, method = RequestMethod.GET)	
	public ResponseEntity<List<HoleVO>> getHoleList(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="isUse", defaultValue="-1")int isUse)
	{
		//System.out.println("[Api] (Hole) > getHoleList - site_id: " + site_id + " / isUse: " + isUse);
		
		List<HoleVO> list = holeService.getHoleList(site_id, isUse);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<HoleVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<HoleVO>>(list, HttpStatus.OK);
	}
	
	// Before: hole/getHoleManageList (Manage & 다르지만 seg에 사용)	
	@RequestMapping(value = {"/manage/list"}, method = RequestMethod.GET)	
	public ResponseEntity<List<HoleVO>> getHoleManageList(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id
	) {		
		List<HoleVO> list = holeService.getHoleManageList(site_id);
		
		// System.out.println("[Api] (Hole) > getHoleManageList - site_id: " + site_id);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<HoleVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<HoleVO>>(list, HttpStatus.OK);
	}
	
	// Before: hole/duplicate (Manage)
	@RequestMapping(value = {"/check/duplicate"}, method = RequestMethod.GET)
	public ResponseEntity<List<HoleVO>> checkHoleDuplicate(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="mac_addr", defaultValue="")String mac_addr,
		@RequestParam(value="idx", defaultValue="-1")int idx
	) {
		
		// System.out.println("[Api] (Hole) > checkHoleDuplicate - mac_addr: " + mac_addr + " / idx: " + idx);
		
		List<HoleVO> list = holeService.checkHoleDuplicate(mac_addr, idx);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<HoleVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<HoleVO>>(list, HttpStatus.OK);	
	}
	
	// Before: hole/mac (Manage - log, detail)
	@RequestMapping(value = {"/macAddr/idx"}, method = RequestMethod.GET)
	public ResponseEntity<HoleVO> getHoleMacAddrByIdx(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="idx", defaultValue="-1")int idx
	) {		
		
		// System.out.println("[Api] (Hole) > getHoleMacAddrByIdx - site_id: " + site_id + " / idx: " + idx);
		
		HoleVO vo = holeService.getHoleMacAddrByIdx(site_id, idx);		
		if (vo == null) {
			return new ResponseEntity<HoleVO>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<HoleVO>(vo, HttpStatus.OK);	
	}
	
	@RequestMapping(value = {"/item/insert"}, method = RequestMethod.POST)
	public void insertHoleItem(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="mac_addr", defaultValue="")String mac_addr
	) {		
		// System.out.println("[Api] (Hole) > insertHoleItem - site_id: " + site_id + " / idx: " + idx + " / mac_addr: " + mac_addr);		
		holeService.insertHoleItem(site_id, idx, mac_addr);
	}

	@RequestMapping(value = {"/item/delete"}, method = RequestMethod.POST)
	public void deleteHoleItem(HttpSession session, HttpServletResponse response, HttpServletRequest request,		
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="mac_addr", defaultValue="")String mac_addr
	) {
		// System.out.println("[Api] (Hole) > deleteHoleItem - idx: " + idx + " / mac_addr: " + mac_addr);
		holeService.deleteHoleItem(idx, mac_addr);
	}	
	
	// Before: hole/mac/update (Web - Manage)
	@RequestMapping(value = {"/update/macAddr"}, method = RequestMethod.POST)
	public void updateHoleMacAddr(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="mac_addr", defaultValue="")String mac_addr
	) {
		// System.out.println("[Api] (Hole) > updateHoleMacAddr - site_id: " + site_id + " / idx: " + idx + " / mac_addr: " + mac_addr);
		holeService.updateHoleMacAddr(site_id, idx, mac_addr);
	}
	
	// Before: hole/delete (Web - Manage)
	@RequestMapping(value = {"/unAssign"}, method = RequestMethod.POST)	
	public void unAssignHoleData(HttpSession session, HttpServletResponse response,
		@RequestParam(value="id", defaultValue="-1")int id)
	{
		try { 
			// System.out.println("[Api] (Hole) > unAssignHoleData - id: " + id);
			holeService.unAssignHoleData(id);
		} catch (Exception e) {
			System.out.println("[ERROR] deleteAllocateHole API: " + e);
		}	
	}	
	
	// Before: hole/update (Web - Manage)
	@RequestMapping(value = {"/update"}, method = RequestMethod.POST)	
	public void updateHoleData(HttpSession session, HttpServletResponse response,
		@RequestParam(value="id", defaultValue="-1")int id,
		@RequestParam(value="section", defaultValue="-1")int section)
	{		
		try { 
			System.out.println("[Api] (Hole) > updateHoleData - id: " + id + " / section: " + section);
			holeService.updateHoleData(id, section);
		} catch (Exception e) {
			System.out.println("[ERROR] updateHole API: " + e);
		}		
	}
	
	// Before: hole/name/update (Web - Not Use)
	@RequestMapping(value = {"/name/update"}, method = RequestMethod.POST)	
	public void updateHoleName(HttpSession session, HttpServletResponse response,
		@RequestParam(value="id", defaultValue="-1")int id,
		@RequestParam(value="name", defaultValue="")String name)
	{		
		try { 
			// System.out.println("[Api] (Hole) > updateHoleName - id: " + id + " / name: " + name);
			holeService.updateHoleName(id, name);
		} catch (Exception e) {
			System.out.println("[ERROR] updateHole API: " + e);
		}		
	}


	@RequestMapping(value = {"/target"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<HoleVO>> getHoleTargetList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id	
	) {
		//System.out.println("[HoleVO] (NFC) > getHoleTargetList - site_id: " + site_id);
		
		List<HoleVO> target_list = holeService.getHoleAssignSectionList(site_id);
		return new ResponseEntity<List<HoleVO>>(target_list, HttpStatus.OK);
	}
}


