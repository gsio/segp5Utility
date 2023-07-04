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
import com.cons.man.domain.ScannerVO;
import com.cons.man.services.ScannerService;

@RestController
@RequestMapping("/scanner")
@Controller(value="ScannerController")
public class ScannerController {

	@Resource(name="ScannerService")
	private ScannerService scannerService;
	
	
	// Before: device/scanner/list UseP3
	@RequestMapping(value = {"/list"}, method = RequestMethod.GET)	
	public ResponseEntity<List<ScannerVO>> getScannerList(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id
	) {
		// System.out.println("[Api] (SCANNER) > getScannerList - site_id: " + site_id);
		
		List<ScannerVO> list = scannerService.getScannerList(site_id);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<ScannerVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<ScannerVO>>(list, HttpStatus.OK);
	}
	
	// Before: device/getManageScannerList
	@RequestMapping(value = {"/manage/list"}, method = RequestMethod.GET)	
	public ResponseEntity<List<ScannerVO>> getScannerManageList(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id
	) {
		
		// System.out.println("[Api] (SCANNER) > getScannerManageList - site_id: " + site_id);
		
		List<ScannerVO> list = scannerService.getScannerManageList(site_id);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<ScannerVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<ScannerVO>>(list, HttpStatus.OK);
	}
	
	// Before: device/scanner/change/timeout
	@RequestMapping(value = {"/update/timeOut"}, method = RequestMethod.GET)
	public void updateScannerTimeOut(HttpSession session, HttpServletResponse response, HttpServletRequest request,		
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="time_out", defaultValue="0")int time_out
	) {
		
		// System.out.println("[Api] (SCANNER) > updateScannerTimeOut - section: " + section + " / time_out: " + time_out);		
		scannerService.updateScannerTimeOut(section, time_out);
	}
	
	// Before: device/scanner/change/waitout
	@RequestMapping(value = {"/update/waitOut"}, method = RequestMethod.GET)
	public void updateScannerWaitOut(HttpSession session, HttpServletResponse response, HttpServletRequest request,		
		@RequestParam(value="section", defaultValue="-1")int section,
		@RequestParam(value="wait_out", defaultValue="0")int wait_out
	) {
		// System.out.println("[Api] (SCANNER) > updateScannerWaitOut - section: " + section + " / wait_out: " + wait_out);
		scannerService.updateScannerWaitOut(section, wait_out);
	}
	
	// Before: device/scanner/insert
	@RequestMapping(value = {"/insert"}, method = RequestMethod.POST)
	public void insertScannerData(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="scanner_mac_init", defaultValue="")String scanner_mac_init,
		@RequestParam(value="scanner_mac", defaultValue="")String scanner_mac,
		@RequestParam(value="name", defaultValue="")String name,
		@RequestParam(value="section", defaultValue="-1")int section
	) {
		// System.out.println("[Api] (SCANNER) > insertScannerData - site_id: " + site_id + " / scanner_mac: " + scanner_mac + " / section: " + section);
		scannerService.insertScannerData(site_id, scanner_mac_init, scanner_mac, name, section);
	}
	
	// Before: device/scanner/delete
	@RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
	public void deleteScannerData(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="scanner_mac", defaultValue="")String scanner_mac,
		@RequestParam(value="section", defaultValue="-1")int section
	) {		
		// System.out.println("[Api] (SCANNER) > deleteScannerData - site_id: " + site_id + " / scanner_mac: " + scanner_mac + " / idx: " + idx + " / section: " + section);
		scannerService.deleteScannerData(site_id, idx, scanner_mac, section);
	}
	
}


