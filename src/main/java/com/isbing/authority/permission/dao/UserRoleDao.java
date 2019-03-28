package com.isbing.authority.permission.dao;

import com.isbing.authority.permission.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by song bing
 * Created time 2019/3/28 17:27
 */
@Mapper
public interface UserRoleDao {

	List<UserRole> getRoleByUserId(int userId);

	void clearRole(int userId);

	void insertUserRole(List<Map<String, Integer>> userRoleList);
}
