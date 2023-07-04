package com.cons.man.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cons.man.domain.InoutVO;
import com.cons.man.persistence.LocationMapper;


@Service(value="LocationService")
public class LocationService {

	@Resource(name="LocationMapper")
	private LocationMapper locationMapper;

	public void changeEngineerWorkTime(int site_id, String beacon_mac, int mb_idx, String start_time) {
		InoutVO inoutVO = locationMapper.getInoutData(beacon_mac);	
		String new_start_time 
			= inoutVO.getStart_time().substring(0, 11) +
			start_time.substring(0, 2) + ":" + start_time.substring(2, 4);
		locationMapper.changeEngineerWorkTime(beacon_mac, new_start_time);		
	}
}
