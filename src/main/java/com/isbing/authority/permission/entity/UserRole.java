package com.isbing.authority.permission.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by song bing
 * Created time 2019/3/28 17:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {
	private int userId;
	private int roleId;
}
