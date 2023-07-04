package com.cons.man.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cons.man.domain.DidSettingVO;
import com.cons.man.domain.HoleVO;
import com.cons.man.domain.LoginLogVO;
import com.cons.man.domain.WeatherInfoVO;
import com.cons.man.persistence.MonitorMapper;
import com.cons.man.persistence.UserMapper;

@Service(value="MonitorService")
public class MonitorService {
	
	@Resource(name="MonitorMapper")
	private MonitorMapper monitorMapper;

	@Resource(name="UserMapper")
	private UserMapper userMapper;

	public WeatherInfoVO getOpenWeatherAPIInfo(int site_id) {
		return monitorMapper.getOpenWeatherAPIInfo(site_id);		
	}
	
	public HoleVO getLastHoleId(int site_id) {
		return monitorMapper.getLastHoleId(site_id);
	}
	
	public DidSettingVO getLastDidId(int site_id) {
		return monitorMapper.getLastDidId(site_id);
	}
	
	public void insertMonitorLog(String ip, String userid, String url, int is_web) {
		
		if(url.indexOf("localhost") > 0){
    		//System.out.println("LOCAL");
    	}
    	else {
    		LoginLogVO vo = new LoginLogVO();
    		vo.setIp(ip);
    		vo.setUserid(userid);
    		vo.setUrl(url);
    		vo.setIs_web(is_web);
    		userMapper.insertLoginLog(vo);
    	}
	}	
}
