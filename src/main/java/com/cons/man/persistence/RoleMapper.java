package com.cons.man.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cons.man.domain.RoleVO;

@Repository(value="RoleMapper")
public interface RoleMapper {
	public List<RoleVO> getRoleList();
	public List<RoleVO> getRoleCodeListWithCnt();
	public List<RoleVO> getRoleListByType(int type);
	public List<RoleVO> getRoleCodeList();
	
}