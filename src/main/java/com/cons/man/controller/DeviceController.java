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

import com.cons.man.domain.TargetVO;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.HoleVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.PatchVO;
import com.cons.man.domain.ScannerVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.SensorVO;
import com.cons.man.services.ContService;
import com.cons.man.services.DeviceService;
import com.cons.man.services.LocationService;

@RestController
@RequestMapping("/device")
@Controller(value="DeviceController")
public class DeviceController {

	@Resource(name="DeviceService")
	private DeviceService deviceService;
	
	@RequestMapping(value = {"/patch/list"}, method = RequestMethod.GET)	
	public ResponseEntity<List<PatchVO>> getPatchList(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id
	) {		
		
		//System.out.println("[Api] (Device) > getPatchList - site_id: " + site_id);
		
		List<PatchVO> list = deviceService.getPatchList(site_id);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<PatchVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<PatchVO>>(list, HttpStatus.OK);
	}
	

	// Before: seg/delete/device/state
	@RequestMapping(value = {"/delete/state"}, method = RequestMethod.POST)
	public void deleteDeviceState(HttpSession session, HttpServletResponse response,
		@RequestParam(value="scanner_mac", defaultValue="")String scanner_mac,
		@RequestParam(value="app_type", defaultValue="-1")int app_type,
		@RequestParam(value="phone_idx", defaultValue="-1")int phone_idx) 
	{
		//System.out.println("[Api] (Device) > deleteDeviceState - scanner_mac: " + scanner_mac + " / app_type: " + app_type + " / phone_idx: " + phone_idx);
		deviceService.deleteDeviceState(scanner_mac, app_type, phone_idx);
	} 
	
	@RequestMapping(value = {"/fan/update"}, method = RequestMethod.POST)	
	public void updateFan(HttpSession session, HttpServletResponse response,
		@RequestParam(value="id", defaultValue="-1")int id,
		@RequestParam(value="section", defaultValue="-1")int section)
	{		
		try { 
			deviceService.updateFanBeacon(id, section);
		} catch (Exception e) {
			System.out.println("[ERROR] updateFan API: " + e);
		}		
	}
	
	@RequestMapping(value = {"/fan/delete"}, method = RequestMethod.POST)	
	public void deleteAllocateFan(HttpSession session, HttpServletResponse response,
		@RequestParam(value="id", defaultValue="-1")int id)
	{
		try { 
			deviceService.deleteFanBeaconByid(id);
		} catch (Exception e) {
			System.out.println("[ERROR] deleteAllocateFan API: " + e);
		}	
	}
	
	@RequestMapping(value = {"/fan/name/update"}, method = RequestMethod.POST)	
	public void updateFanName(HttpSession session, HttpServletResponse response,
		@RequestParam(value="id", defaultValue="-1")int id, 
		@RequestParam(value="name", defaultValue="")String name)
	{
		try { 
			deviceService.updateFanName(id, name);
		} catch (Exception e) {
			System.out.println("[ERROR] updateFanName API: " + e);
		}	
	}
}


