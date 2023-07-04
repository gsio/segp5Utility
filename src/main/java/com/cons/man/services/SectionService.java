package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cons.man.persistence.BeaconMapper;
import com.cons.man.persistence.NFCMapper;
import com.cons.man.persistence.SectionMapper;
import com.cons.man.persistence.SensorMapper;
import com.cons.man.domain.BeaconVO;
import com.cons.man.domain.NFCVO;
import com.cons.man.domain.SectionVO;
import com.cons.man.domain.TargetVO;


@Service(value="SectionService")
public class SectionService {
	
	@Resource(name="SectionMapper")
	private SectionMapper sectionMapper;
	
	public List<SectionVO> getSectionList(int site_id){
		return sectionMapper.getSectionList(site_id);
	}
	
	public List<SectionVO> checkSectionDuplicate(int section) {
		return sectionMapper.checkSectionDuplicate(section);
	}
}
