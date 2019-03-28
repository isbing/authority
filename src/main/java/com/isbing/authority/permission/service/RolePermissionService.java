package com.isbing.authority.permission.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.isbing.authority.permission.dao.RolePermissionDao;
import com.isbing.authority.permission.entity.RolePermission;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by song bing
 * Created time 2019/3/28 10:14
 */
@Service
public class RolePermissionService {
	@Resource
	private RolePermissionDao rolePermissionDao;

	public List<Integer> getByRoleId(int roleId) {
		List<RolePermission> rolePermissionList = rolePermissionDao.getByRoleId(roleId);
		if(CollectionUtils.isNotEmpty(rolePermissionList)){
			return rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
		}
		return null;
	}

	public void setRolePermission(int roleId, String permissionIds) {
		// 每次做插入操作之前 先将 roleId对应的 以前的数据 清除
		rolePermissionDao.clearPermission(roleId);

		// 插入新增的权限
		List<Map<String, Integer>> rolePermissionList = new ArrayList<>();
		String[] split = StringUtils.split(permissionIds, ",");
		// 将字符串全部转为数字
		List<Integer> permissionIdList = Arrays.stream(split).map(Integer::parseInt).collect(Collectors.toList());
		for(Integer permissionId : permissionIdList){
			Map<String,Integer> map = Maps.newHashMap();
			map.put("roleId",roleId);
			map.put("permissionId",permissionId);
			rolePermissionList.add(map);
		}
		rolePermissionDao.insertRolePermission(rolePermissionList);
	}
}
