package com.cons.man.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cons.man.domain.PushUserVO;

@Repository(value="PushMapper")
public interface PushMapper {
	
	
	public List<String> getPushUserList(PushUserVO vo);
	
	public List<String> getPushTargetUserList(PushUserVO vo);
}

