package com.cons.man.persistence;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.DidSettingVO;
import com.cons.man.domain.HoleVO;
import com.cons.man.domain.WeatherInfoVO;

@Repository(value="MonitorMapper")
public interface MonitorMapper {
	
	public WeatherInfoVO getOpenWeatherAPIInfo(int site_id);
	
	public HoleVO getLastHoleId(@Param("site_id") int site_id);
	
	public DidSettingVO getLastDidId(@Param("site_id") int site_id);
	
}