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

import com.cons.man.domain.TargetVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.NfcLocationVO;
import com.cons.man.services.NFCService;

@RestController
@RequestMapping("/nfc")
@Controller(value="NFController")
public class NFController {
	
	@Resource(name="NFCService")
	private NFCService nfcService;

	
	@RequestMapping(value = {"/list"}, method = RequestMethod.GET)	
	public ResponseEntity<List<NFCVO>> getNFCList(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,		
		@RequestParam(value="role", defaultValue="-1")int role,		
		@RequestParam(value="isUse", defaultValue="-1")int isUse		
	){
		
		// System.out.println("[Api] (NFC) > getNFCList - site_id: " + site_id + " / role: " + role + " / isUse: " + isUse );
		
		List<NFCVO> list = nfcService.getNFCList(site_id, role, isUse);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<NFCVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<NFCVO>>(list, HttpStatus.OK);
	}
	
	// Before: nfcTargetList(Mobile)
	@RequestMapping(value = {"/target"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<TargetVO>> getNFCTargetList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,		
		@RequestParam(value="role", defaultValue="-1")int role		
	) {
		// System.out.println("[Api] (NFC) > getNFCTargetList - site_id: " + site_id + " / role: " + role);
		
		List<TargetVO> target_list = nfcService.getNFCTargetList(site_id, role);
		return new ResponseEntity<List<TargetVO>>(target_list, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/create"}, method = RequestMethod.POST)	
	public void createNFCData(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="serial_number", defaultValue="")String serial_number)
	{
		// System.out.println("[Api] (NFC) > createNFCData - site_id: " + site_id + " / serial_number: " + serial_number);
		nfcService.createNFCData(site_id, serial_number);		
	}
	
	// Before: nfc/createByIdx (Mobile - 번호 지정 insert)
	@RequestMapping(value = {"/create/idx"}, method = RequestMethod.POST)	
	public void createNFCDataByIdx(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="serial_number", defaultValue="")String serial_number)
	{
		// System.out.println("[Api] (NFC) > createNFCDataByIdx - site_id: " + site_id + " / serial_number: " + serial_number + " / idx :" + idx);
		nfcService.createNFCDataByIdx(site_id, idx, serial_number);		
	}
	
	@RequestMapping(value = {"/update"}, method = RequestMethod.POST)	
	public void updateNFCData(HttpSession session, HttpServletResponse response,
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="u_id", defaultValue="")String u_id,
		@RequestParam(value="role", defaultValue="-1")int role)
	{
		// System.out.println("[Api] (NFC) > updateNFCData - idx: " + idx + " / u_id: " + u_id + " / role: " + role);
		nfcService.updateNFCData(idx, u_id, role);			
	}
	
	// 추후 삭제 필요(SEG) Before: seg/checkNfcDuplicate (Mobile)
	@RequestMapping(value = {"/check/duplicate"}, method = RequestMethod.GET)				 
	public ResponseEntity<NFCVO> checkNFCDuplicate(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="serial_number", defaultValue="")String serial_number)
	{	
		// System.out.println("[Api] (NFC) > checkNFCDuplicate - serial_number: " + serial_number);
		
		NFCVO vo = nfcService.checkNFCDuplicate(site_id, serial_number);
		
		return new ResponseEntity<NFCVO>(vo, HttpStatus.OK);
	}
	
	// 추후 삭제 필요(SEG) Before: seg/checkNfcDuplicate (Mobile)
	@RequestMapping(value = {"/check/state"}, method = RequestMethod.GET)				 
	public ResponseEntity<NFCVO> checkNFCState(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="serial_number", defaultValue="")String serial_number
	) {	
		// System.out.println("[Api] (NFC) > checkNFCState - site_id:" + site_id + " / serial_number: " + serial_number);
		NFCVO vo = nfcService.checkNFCState(site_id, serial_number);
		return new ResponseEntity<NFCVO>(vo, HttpStatus.OK);
	}
	
	// Before: nfc/duplicate (Web - Manage)
	@RequestMapping(value = {"/check/duplicate/idx"}, method = RequestMethod.GET)
	public ResponseEntity<List<NFCVO>> checkNFCDuplicateByIdx(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="serial_number", defaultValue="")String serial_number,
		@RequestParam(value="idx", defaultValue="-1")int idx
	) {
		
		// System.out.println("[Api] (NFC) > checkNFCDuplicateByIdx - serial_number: " + serial_number + " / idx: " + idx);
		
		List<NFCVO> list = nfcService.checkNFCDuplicateByIdx(serial_number, idx);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<NFCVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<NFCVO>>(list, HttpStatus.OK);	
	}
	
	// Before: nfc/delete (Web/Mobile)
	@RequestMapping(value = {"/unAssign"}, method = RequestMethod.POST)	
	public void unAssignNFCData(HttpSession session, HttpServletResponse response,
		@RequestParam(value="id", defaultValue="")String id,
		@RequestParam(value="serial_number", defaultValue="")String serial_number,
		@RequestParam(value="idx", defaultValue="-1")int idx
	) {
		try { 
			// System.out.println("[Api] (NFC) > updateNFCData - id: " + id + " / serial_number: " + serial_number + " / idx: " + idx);
			nfcService.unAassignNFCData(serial_number, idx);
		} catch (Exception e) {
			System.out.println("[ERROR] API - unAssignNFCData : " + e);
		}	
	}	
	
	@RequestMapping(value = {"/remain"}, method = RequestMethod.GET)
	public ResponseEntity<List<Integer>> getRemainNFCList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id
	) {		
		
		// System.out.println("[Api] (NFC) > getRemainNFCList - site_id: " + site_id);
		
		List<Integer> list = nfcService.getRemainNFCList(site_id);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<Integer>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<Integer>>(list, HttpStatus.OK);	
	}
	
	// Before: nfc/isCanUpdate (Web - Manage)	
	@RequestMapping(value = {"/check/update"}, method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkNFCUpdateData(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="idx", defaultValue="-1")int idx
	) {
		// System.out.println("[Api] (NFC) > checkNFCUpdateData - idx: " + idx);
		
		int data = nfcService.checkNFCUpdateData(idx);
		
		if (data <= 0) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);			
		}
		else {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);	
		}			
	}
	
	// Before: nfc/item/insert (Web - Manage)	
	@RequestMapping(value = {"/item/insert"}, method = RequestMethod.POST)
	public void insertNFCItem(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="serial_number", defaultValue="")String serial_number
	) {
		// System.out.println("[Api] (NFC) > insertNFCItem - site_id: " + site_id + " / idx: " + idx + " / serial_number: " + serial_number);
		nfcService.insertNFCItem(site_id, idx, serial_number);
	}
	
	// Before: nfc/item/deletet (Web - Manage)	
	@RequestMapping(value = {"/item/delete"}, method = RequestMethod.POST)
	public void deleteNFCItem(HttpSession session, HttpServletResponse response, HttpServletRequest request,		
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="serial_number", defaultValue="")String serial_number
	) {
		// System.out.println("[Api] (NFC) > deleteNFCItem - idx: " + idx + " / serial_number: " + serial_number);
		nfcService.deleteNFCItem(idx, serial_number);
	}
	
	// Before: nfc/idx/update (Web - Manage)
	@RequestMapping(value = {"/item/update/idx"}, method = RequestMethod.POST)
	public void updateNFCByIdx(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="idx", defaultValue="-1")int idx,
		@RequestParam(value="serial_number", defaultValue="")String serial_number
	) {
		// System.out.println("[Api] (NFC) > updateNFCByIdx - site_id: " + site_id + " / idx: " + idx + " / serial_number: " + serial_number);
		nfcService.updateNFCByIdx(site_id, idx, serial_number);
	}
	

	// Before: seg/getWaitInfo (Web - Manage)
	@RequestMapping(value = {"/wait/list"}, method = RequestMethod.GET)				 
	public ResponseEntity<List<NfcLocationVO>> getWaitList(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="site_id", defaultValue="-1")int site_id,
		@RequestParam(value="place_id", defaultValue="-1")int place_id,
		@RequestParam(value="section", defaultValue="-1")int section
	) {		
		//System.out.println("[Api] (NFC) > getWaitList - site_id: " + site_id + " / place_id: " + place_id + " / section: " + section);
		List<NfcLocationVO> nfcList = nfcService.getWaitList(site_id, place_id, section);
		return new ResponseEntity<List<NfcLocationVO>>(nfcList, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/wait/out"}, method = RequestMethod.POST)				 
	public void postWaitOut(HttpSession session, HttpServletResponse response, HttpServletRequest request,
		@RequestParam(value="uw_id", defaultValue="")String uw_id,
		@RequestParam(value="role", defaultValue="-1")int role,
		@RequestParam(value="serial_number", defaultValue="")String serial_number
	) {		
		//System.out.println("[Api] (NFC) > postWaitOut - uw_id: " + uw_id + " / role: " + role + " / serial_number: " + serial_number);
		nfcService.postWaitOut(uw_id, role, serial_number);
	}
	
	
}


