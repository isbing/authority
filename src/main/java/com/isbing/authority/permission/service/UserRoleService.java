package com.isbing.authority.permission.service;

import com.google.common.collect.Maps;
import com.isbing.authority.permission.dao.UserRoleDao;
import com.isbing.authority.permission.entity.UserRole;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by song bing
 * Created time 2019/3/28 17:27
 */
@Service
public class UserRoleService {
	@Resource
	private UserRoleDao userRoleDao;

	public List<Integer> getRoleByUserId(int userId) {
		List<UserRole> userRoleList = getRoleBeanByUserId(userId);
		if(CollectionUtils.isNotEmpty(userRoleList)){
			return userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
		}
		return null;
	}

	private List<UserRole> getRoleBeanByUserId(int userId) {
		return userRoleDao.getRoleByUserId(userId);
	}

	public void setUserRole(int userId, String roleIdStr) {

		// 每次做插入操作之前 先将 roleId对应的 以前的数据 清除
		userRoleDao.clearRole(userId);

		// 插入新增的权限
		List<Map<String, Integer>> userRoleList = new ArrayList<>();
		List<Integer> permissionIdList = Arrays.stream(StringUtils.split(roleIdStr, ",")).map(Integer::parseInt).collect(Collectors.toList());
		for(Integer roleId : permissionIdList){
			Map<String,Integer> map = Maps.newHashMap();
			map.put("userId",userId);
			map.put("roleId",roleId);
			userRoleList.add(map);
		}
		userRoleDao.insertUserRole(userRoleList);
	}
}
