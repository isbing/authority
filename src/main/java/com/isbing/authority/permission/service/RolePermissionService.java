package com.isbing.authority.permission.service;

import com.google.common.collect.Lists;
import com.isbing.authority.permission.dao.RolePermissionDao;
import com.isbing.authority.permission.entity.RolePermission;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
}
