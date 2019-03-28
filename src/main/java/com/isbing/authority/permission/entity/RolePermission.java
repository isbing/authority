package com.isbing.authority.permission.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by song bing
 * Created time 2019/3/27 19:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolePermission {
	private int roleId;
	private int permissionId;
}
