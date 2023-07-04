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

import com.cons.man.domain.SectionVO;
import com.cons.man.services.SectionService;
import com.cons.man.services.SensorService;

@RestController
@RequestMapping("/section")
@Controller(value="SectionController")
public class SectionController {

	@Resource(name="SectionService")
	private SectionService sectionService;
	
	@RequestMapping(value = {"/list"}, method = RequestMethod.GET)	
	public ResponseEntity<List<SectionVO>> getSectionList(HttpSession session, HttpServletResponse response,
		@RequestParam(value="site_id", defaultValue="-1")int site_id
	) {
		
		System.out.println("[Api] (Section) > getSectionList - site_id: " + site_id);
		
		List<SectionVO> list = sectionService.getSectionList(site_id);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<SectionVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<SectionVO>>(list, HttpStatus.OK);
	}

	// Before: scanner/duplicate (Web - Manage)
	@RequestMapping(value = {"/check/duplicate"}, method = RequestMethod.GET)
	public ResponseEntity<List<SectionVO>> checkSectionDuplicate(HttpSession session, HttpServletResponse response, HttpServletRequest request,		
		@RequestParam(value="section", defaultValue="-1")int section
	) {
		System.out.println("[Api] (Section) > checkSectionDuplicate - section: " + section);
		
		List<SectionVO> list = sectionService.checkSectionDuplicate(section);
		
		if (list == null || list.size() == 0) {
			return new ResponseEntity<List<SectionVO>>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<List<SectionVO>>(list, HttpStatus.OK);	
	}
}


