package com.isbing.authority.permission.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by song bing
 * Created time 2019/3/27 14:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
	private int id;
	private String name;//角色名
	private String code;//角色码
	private Date createTime;
}
