package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cons.man.persistence.BeaconMapper;
import com.cons.man.persistence.NFCMapper;
import com.cons.man.persistence.SensorMapper;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.TargetVO;


@Service(value="SensorService")
public class SensorService {
	
	@Resource(name="SensorMapper")
	private SensorMapper sensorMapper;
	
	
}
