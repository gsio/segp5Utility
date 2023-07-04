package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cons.man.domain.ContVO;
import com.cons.man.persistence.ContMapper;

@Service(value="ContService")
public class ContService {
	
	@Resource(name="ContMapper")
	private ContMapper contMapper;

	public List<ContVO> getContList( int site_id ) {
		return contMapper.getContList(site_id);
	}
	
	public ContVO getContInfo( int id ) {
		return contMapper.getContInfo(id);
	}
	
	@Transactional
	public void insertCont(ContVO cont) {
		contMapper.insert(cont);
	}
	
	@Transactional
	public void updateCont(ContVO cont) {
		contMapper.update(cont);
	}
	
//	------------------------------
	
	public void disableCont(int id) {
		contMapper.disableCont(id);
	}



	
}
