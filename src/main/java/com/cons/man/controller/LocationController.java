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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cons.man.services.LocationService;

@RestController
@RequestMapping("/location")
@Controller(value="LocationController")
public class LocationController {

	@Resource(name="LocationService")
	private LocationService locationService;
	
	// Before: seg/change/time (Web - Manage)
	@RequestMapping(value = {"/update/workTime"}, method = RequestMethod.POST)
	public ResponseEntity<Integer> changeEngineerWorkTime(HttpSession session, HttpServletResponse response,		
		@RequestParam(value="site_id", defaultValue="")int site_id,		
		@RequestParam(value="beacon_mac", defaultValue="")String beacon_mac,
		@RequestParam(value="mb_idx", defaultValue="")int mb_idx,
		@RequestParam(value="start_time", defaultValue="")String start_time)
	{
		System.out.println("[Api] (Location) > changeEngineerWorkTime - site_id: " + site_id + " / beacon_mac: " + beacon_mac + " / mb_idx: " + mb_idx + " / start_time: " + start_time);
		
		locationService.changeEngineerWorkTime(site_id, beacon_mac, mb_idx, start_time);
		return new ResponseEntity<Integer>(0, HttpStatus.OK);		
	}
}


