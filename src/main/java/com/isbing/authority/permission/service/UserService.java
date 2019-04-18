package com.isbing.authority.permission.service;

import com.isbing.authority.common.util.JsonUtil;
import com.isbing.authority.permission.dao.UserDao;
import com.isbing.authority.permission.entity.PageBean;
import com.isbing.authority.permission.entity.Role;
import com.isbing.authority.permission.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by song bing
 * Created time 2019/3/28 17:06
 */
@Service
public class UserService {
	@Resource
	private UserDao userDao;

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private RoleService roleService;

	public Integer create(User user) {
		userDao.insert(user);
		return user.getId();
	}

	public PageBean getAll() {
		List<User> userList = userDao.getAll();
		// 设置每个用户 对应的 所有角色
		if(CollectionUtils.isNotEmpty(userList)){
			userList.forEach(user -> {
				int userId = user.getId();
				List<Integer> roleIdList = userRoleService.getRoleByUserId(userId);
				if (CollectionUtils.isNotEmpty(roleIdList)) {
					List<Role> roleList = roleService.findByIds(roleIdList);
					if (CollectionUtils.isNotEmpty(roleList)) {
						List<String> roleNameList = roleList.stream().map(Role::getName).collect(Collectors.toList());
						StringBuilder sb = new StringBuilder();
						roleNameList.forEach(roleName -> sb.append(roleName).append("，"));
						String str = sb.toString();
						// 设置 用户已拥有的角色
						user.setRoleStr(str.substring(0, str.lastIndexOf("，")));
					}
				}
			});
		}
		return PageBean.builder().content(userList).build();
	}

	public User getById(Integer id) {
		return userDao.getById(id);
	}

	public void update(User user) {
		userDao.update(user);
	}
}
