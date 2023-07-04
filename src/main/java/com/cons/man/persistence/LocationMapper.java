package com.cons.man.persistence;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.InoutVO;

@Repository(value="LocationMapper")
public interface LocationMapper {
	
	public InoutVO getInoutData(@Param("beacon_mac")String beacon_mac);
	
	public void changeEngineerWorkTime(@Param("beacon_mac")String beacon_mac, @Param("start_time")String start_time);
	
}
