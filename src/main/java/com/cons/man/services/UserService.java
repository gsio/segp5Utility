package com.cons.man.services;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cons.man.domain.CertkeyVO;
import com.cons.man.domain.LoginLogVO;
import com.cons.man.domain.RoleVO;
import com.cons.man.domain.UserVO;
import com.cons.man.persistence.RoleMapper;
import com.cons.man.persistence.UserMapper;
import com.cons.man.util.FileWriter;
import com.cons.man.util.UIDMaker;

@Service(value="UserService")
public class UserService {
	
	@Resource(name="UserMapper")
	private UserMapper userMapper;
	
	@Resource(name="RoleMapper")
	private RoleMapper roleMapper;
	
	public UserVO getUserByUserId(String userid) {
		return userMapper.getUserByUserId(userid);
	}
	
	public UserVO getUserByUserIdAndPW(UserVO vo) {
		return userMapper.getUserByUserIdAndPW(vo);
	}

	public List<UserVO> getUserList(int site_id, int cont_id) {		
		UserVO vo = new UserVO();
		vo.setSite_id(site_id);
		vo.setCont_id(cont_id);
		return getUserList(vo);
	}
	
	public List<UserVO> getUserList(UserVO vo) {		
		return userMapper.getUserList(vo);
	}


	
	public List<UserVO> getBeaconUserList(int site_id, int cont_id) {
		List<UserVO> list = null;
		UserVO searchVO = new UserVO();
		searchVO.setSite_id(site_id);
		searchVO.setCont_id(cont_id);
		searchVO.setHasBeaconInfo(2);
		list = userMapper.getUserList(searchVO);
		return list;
	}
	
	public List<UserVO> getApprovalUserList(int content_id, int site_id, int cont_id) {
		if(content_id < 0) {
			return userMapper.getUserViewList(site_id, cont_id);
		}
		else {
			return userMapper.getApprovalUserList(content_id, site_id, cont_id);	
		}
		
	}
	
	public List<UserVO> getCheckerUserList(int content_id, int site_id, int cont_id) {
		//System.out.println("[getCheckerUserList] : " + content_id + "/" + site_id + "/" + cont_id);
		if(content_id < 0) {
			return userMapper.getUserViewList(site_id, cont_id);
		}
		else {
			return userMapper.getCheckerUserList(content_id, site_id, cont_id);
		}
		
	}

	public CertkeyVO checkModifyUserId(String phone) {
		return userMapper.checkModifyUserId(phone);
	}
	
	public void postChangeUserId(String phone, String userid) {
		userMapper.postChangeUserId(phone, userid);
	}
	
	
// ---------------------	
	
	
	
	
	public UserVO getUserById(String id) {
		return userMapper.getUserById(id);		
	}
	
	public UserVO getDisasterinfo(String id) {
		return userMapper.getDisasterinfo(id);		
	}

	public UserVO getCheifUserByContId(int cont_id) {
		return userMapper.getCheifUserByContId(cont_id);
	}
	
	public UserVO getCheifUserBySiteId(int site_id) {
		return userMapper.getCheifUserBySiteId(site_id);
	}	
	
	public List<RoleVO> getRoleList() {
		return roleMapper.getRoleList();
	}	

	public List<RoleVO> getRoleCodeList() {
		return roleMapper.getRoleCodeList();
	}
	
	public List<RoleVO> getRoleCodeListWithCnt() {
		return roleMapper.getRoleCodeListWithCnt();
	}
	
	public List<RoleVO> getRoleListByType(int type) {
		return roleMapper.getRoleListByType(type);
	}
	
	@Transactional
	public void insert(UserVO userVO) {
		String user_id = UIDMaker.makeNewUID("U");
		userVO.setId(user_id);
		
		if(userVO.getSign_file() != null && userVO.getSign_file().getSize() > 0) {			
			String virtName = "USER/SIGN_" + UUID.randomUUID().toString() +"_" + userVO.getId();
			userVO.setSign(virtName);
			FileWriter.INSTANCE.writeFile(userVO.getSign_file(), virtName);
		}
		
		if(userVO.getPhoto_file() != null && userVO.getPhoto_file().getSize() > 0) {			
			String virtName = "USER/USER_" + UUID.randomUUID().toString() +"_" + userVO.getId();
			userVO.setPhoto(virtName);
			FileWriter.INSTANCE.writeFile(userVO.getPhoto_file(), virtName);
		}
		
		userMapper.insert(userVO);
	/*	
		//r_gubun 1조장, 2 조장X
		if( userVO.getR_gubun() == null || userVO.getR_gubun().equals("") ) {
			userVO.setR_gubun("2");
		}
		
		//org = 소속조직, gubun = org에 따라 1 2로 구분?
		if( userVO.getOrg().equals("1") || userVO.getOrg().equals("2") ) {
			userVO.setGubun("1");
		} else {
			userVO.setGubun("2");
		}
		
		userMapper.insertUserDisasterMeasure(userVO);*/
	}

	@Transactional
	public void update(UserVO userVO) {
		if(userVO.getSign_file() != null && userVO.getSign_file().getSize() > 0) {			
			String virtName = "USER/SIGN_" + UUID.randomUUID().toString() +"_" + userVO.getId();
			userVO.setSign(virtName);
			FileWriter.INSTANCE.writeFile(userVO.getSign_file(), virtName);
		}
		
		if(userVO.getPhoto_file() != null && userVO.getPhoto_file().getSize() > 0) {			
			String virtName = "USER/USER_" + UUID.randomUUID().toString() +"_" + userVO.getId();
			userVO.setPhoto(virtName);
			FileWriter.INSTANCE.writeFile(userVO.getPhoto_file(), virtName);
		}
		userMapper.update(userVO);
		
	}
	
	@Transactional
	public void disableUser(String id) {
		userMapper.disableUser(id);		
	}

	public List<UserVO> getApproveUserList(int site_id, int cont_id) {
		UserVO vo = new UserVO();
		vo.setCont_id(cont_id);
		vo.setSite_id(site_id);
		return userMapper.getApproveUserList(vo);	
	}

	public UserVO getSignByUserVO(UserVO vo) {
		return userMapper.getSignByUserVO(vo);
	}

	/**결재시 안전관리자 가져오기 용도**/
	public UserVO getOnlySafeManager(int site_id) {
		return userMapper.getOnlySafeManager(site_id);		
	}
	
	/**본사? 현장소장 가져오기용도**/
	public UserVO getOnlyCheif(int site_id) {
		return userMapper.getOnlyCheif(site_id);
	}
	
	public UserVO getOnlyTeamLeader(int site_id) {
		return userMapper.getOnlyTeamLeader(site_id);		
	}

	public List<UserVO> getUserListBySiteId(List<Integer> siteIdList) {
		UserVO vo = new UserVO();
		vo.setSiteIdList(siteIdList);
		return userMapper.getUserListBySiteId(vo);
	}
	
	public void insertLoginLogVO(String ip, String userid, String url) {
		LoginLogVO vo = new LoginLogVO();
		vo.setIp(ip);
		vo.setUserid(userid);
		vo.setUrl(url);
		vo.setIs_web(1);
		userMapper.insertLoginLog(vo);
	}
	
}
