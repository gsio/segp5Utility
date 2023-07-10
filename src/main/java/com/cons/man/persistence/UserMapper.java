package com.cons.man.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cons.man.domain.CertkeyVO;
import com.cons.man.domain.LoginLogVO;
import com.cons.man.domain.UserVO;

@Repository(value="UserMapper")
public interface UserMapper {
	public UserVO getUserByUserId(String userid);	
	public List<UserVO> getUserList(UserVO vo);
	public UserVO getUserByUserIdAndPW(UserVO vo);
	public List<UserVO> getUserViewList(@Param("site_id")int site_id, @Param("cont_id")int cont_id);
	public List<UserVO> getApprovalUserList(@Param("content_id")int content_id, @Param("site_id")int site_id, @Param("cont_id")int cont_id);
	public List<UserVO> getCheckerUserList(@Param("content_id")int content_id, @Param("site_id")int site_id, @Param("cont_id")int cont_id);	
	public CertkeyVO checkModifyUserId(@Param("phone")String phone);
	public void postChangeUserId(@Param("phone")String phone, @Param("userid")String userid);

//	-------------------
	
	public List<UserVO> getUserListBySiteId(UserVO vo);
	/*public List<UserVO> getUserListByVO(UserVO userVO);
	public int getRowCount(UserVO userVO);*/
	
	public UserVO getCheifUserByContId(int cont_id);
	public UserVO getCheifUserBySiteId(int site_id);
	public List<UserVO> getSafeManagerBySiteId(int site_id);
	public UserVO getUserById(String id);

	public UserVO getDisasterinfo( String id );

	public void insert(UserVO userVO);
	public void insertUserDisasterMeasure(UserVO userVO);
	
	public void update(UserVO userVO);
	public void deleteUserDisasterMeasure(String user_id);
	
	public void disableUser(String id);	
	public List<UserVO> getUserViewList(UserVO vo);
	public List<UserVO> getApproveUserList(UserVO vo);
	public UserVO getSignByUserVO(UserVO vo);
	
	public UserVO getOnlySafeManager(int site_id);
	public UserVO getOnlyCheif(int site_id);
	public UserVO getOnlyTeamLeader(int site_id);
	public List<UserVO> getSupervisorList(int site_id);
	public List<UserVO> getUserListByContId(int cont_id);
	public void insertLoginLog(LoginLogVO vo);
	
	

	

}