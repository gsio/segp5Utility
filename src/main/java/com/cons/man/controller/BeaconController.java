package com.cons.man.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.ScannerVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.SensorVO;
import com.cons.man.domain.TargetVO;
import com.cons.man.services.BeaconService;
import com.cons.man.services.ContService;
import com.cons.man.services.DeviceService;
import com.cons.man.services.LocationService;
import com.cons.man.services.NFCService;

@RestController
@RequestMapping("/beacon")
@Controller(value="BeaconController")
public class BeaconController {

	@Resource(name="BeaconService")
	private BeaconService beaconService;	
	
	@Resource(name="NFCService")
	private NFCService nfcService;
		
	@RequestMapping(value = {"/list"}, method = RequestMethod.GET)	
	public ResponseEntity<List<BeaconVO>> getBeaconList(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,		
		@RequestParam(value="role", defaultValue="-1")int role,		
		@RequestParam(value="isUse", defaultValue="-1")int isUse)
	{		
		// System.out.println("[Api] (Beacon) > getBeaconList - site_id: " + site_id + " / role: " + role + " / isUse: " + isUse );
		
		List<BeaconVO> list = beaconService.getBeaconList(site_id, role, isUse);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<BeaconVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<BeaconVO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/target"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<TargetVO>> getBeaconTargetList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,		
		@RequestParam(value="role", defaultValue="-1")int role		
	) {
		// System.out.println("[Api] (Beacon) > getBeaconTargetList - site_id: " + site_id + " / role: " + role);
		
		List<TargetVO> target_list = beaconService.getBeaconTargetList(site_id, role);
		return new ResponseEntity<List<TargetVO>>(target_list, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.POST)	
	public void updateBeaconData(HttpSession session, HttpServletResponse response,		
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="u_id", defaultValue="")String u_id,
		@RequestParam(value="role", defaultValue="-1")int role)
	{
		// System.out.println("[Api] (Beacon) > updateBeaconData - idx: " + idx + " / u_id: " + u_id + " / role: " + role);
		System.out.println("[UPDATE] - Beacon & NFC: " + idx + "번호" + "/" + u_id + "/" + role);

		beaconService.updateBeaconData(idx, u_id, role);
		nfcService.updateNFCData(idx, u_id, role);
		
	}
	
	// Before: beacon/delete (Web/Mobile)
	@RequestMapping(value = {"/unAssign"}, method = RequestMethod.POST)	
	public void unAssignBeaconData(HttpSession session, HttpServletResponse response,
		@RequestParam(value="id", defaultValue="")String id,
		@RequestParam(value="mac_addr", defaultValue="")String mac_addr,
		@RequestParam(value="idx", defaultValue="-1")int idx
	) {
		try { 
			
			// System.out.println("[Api] (Beacon) > unAssignBeaconData - id: " + id + " / mac_addr: " + mac_addr + " / idx: " + idx);
			
			beaconService.unAssignBeaconData(idx);
			
		} catch (Exception e) {
			System.out.println("[ERROR] /unAssignBeaconData API: " + e);
		}	
	}
	
	// Before: beacon/duplicate (Web/Mobile)
	@RequestMapping(value = {"/check/duplicate"}, method = RequestMethod.GET)
	public ResponseEntity<List<BeaconVO>> checkBeaconDuplicate(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="mac_addr", defaultValue="")String mac_addr,
		@RequestParam(value="idx", defaultValue="-1")int idx
	) {
		// System.out.println("[Api] (Beacon) > checkBeaconDuplicate - mac_addr: " + mac_addr + " / idx: " + idx);
		
		List<BeaconVO> list = beaconService.checkBeaconDuplicate(mac_addr, idx);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<BeaconVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<BeaconVO>>(list, HttpStatus.OK);	
	}
	
	// Before: beacon/mac (Manage)
	@RequestMapping(value = {"/macAddr/idx"}, method = RequestMethod.GET)
	public ResponseEntity<BeaconVO> getBeaconMacAddrByIdx(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="idx", defaultValue="-1")int idx
	) {		
		// System.out.println("[Api] (Beacon) > getBeaconMacAddrByIdx - site_id: " + site_id + " / idx: " + idx);
		
		BeaconVO vo = beaconService.getBeaconMacAddrData(site_id, idx);		
		
		if (vo == null) {
			return new ResponseEntity<BeaconVO>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<BeaconVO>(vo, HttpStatus.OK);	
	}

	@RequestMapping(value = {"/item/insert"}, method = RequestMethod.POST)
	public void insertBeaconItem(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="mac_addr", defaultValue="")String mac_addr
	) {
		// System.out.println("[Api] (Beacon) > insertBeaconItem - site_id: " + site_id + " / idx: " + idx + " / mac_addr: " + mac_addr);
		
		beaconService.insertBeaconItem(site_id, idx, mac_addr);
	}
	
	@RequestMapping(value = {"/item/delete"}, method = RequestMethod.POST)
	public void deleteBeaconItem(HttpSession session, HttpServletResponse response, HttpServletRequest request,		
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="mac_addr", defaultValue="")String mac_addr
	) {
		// System.out.println("[Api] (Beacon) > deleteBeaconItem - idx: " + idx + " / mac_addr: " + mac_addr);
		beaconService.deleteBeaconItem(idx, mac_addr);
	}	
	
	// Before: beacon/mac/update (Web - Manage)
	@RequestMapping(value = {"/update/macAddr"}, method = RequestMethod.POST)
	public void updateBeaconMacAddr(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="mac_addr", defaultValue="")String mac_addr
	) {
		// System.out.println("[Api] (Beacon) > updateBeaconMacAddr - site_id: " + site_id + " / idx: " + idx + " / mac_addr: " + mac_addr);
		beaconService.updateBeaconMacAddr(site_id, idx, mac_addr);
	}
	
}


