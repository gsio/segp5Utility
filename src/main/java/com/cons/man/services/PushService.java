package com.cons.man.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cons.man.domain.PushUserVO;
import com.cons.man.persistence.PushMapper;

@Service(value="PushService")
public class PushService {
	
	@Resource(name="PushMapper")
	private PushMapper pushMapper;
	

	public List<String> getPushUserList(int site_id, int beacon_alarm, int env_sensor, int gyn_cctv, int seg_sealed, int seg_wind, int seg_workmin){
		PushUserVO vo = new PushUserVO();
		vo.setSite_id(site_id);
		vo.setBeacon_alarm(beacon_alarm);
		vo.setEnv_sensor(env_sensor);
		vo.setGyn_cctv(gyn_cctv);
		vo.setSeg_sealed(seg_sealed);
		vo.setSeg_wind(seg_wind);
		vo.setSeg_workmin(seg_workmin);
		return pushMapper.getPushUserList(vo);
	}
	
	public List<String> getPushUserList(PushUserVO vo){
		/*PushUserVO vo = new PushUserVO();
		vo.setSite_id(site_id);
		vo.setBeacon_alarm(beacon_alarm);
		vo.setEnv_sensor(env_sensor);
		vo.setGyn_cctv(gyn_cctv);
		vo.setSeg_sealed(seg_sealed);
		vo.setSeg_wind(seg_wind);
		vo.setSeg_workmin(seg_workmin);*/
		return pushMapper.getPushUserList(vo);
	}
	
	public List<String> getPushTargetUserList(int site_id, List<String> u_id_list){
		PushUserVO vo = new PushUserVO();
		vo.setSite_id(site_id);
		vo.setU_id_list(u_id_list);
		return pushMapper.getPushTargetUserList(vo);
	}
	
	
	public List<String> getPushTargetUserList(PushUserVO vo){
		return pushMapper.getPushTargetUserList(vo);
	}
	
	
}
