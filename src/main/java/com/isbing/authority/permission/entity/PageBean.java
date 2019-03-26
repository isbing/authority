package com.isbing.authority.permission.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/20 18:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageBean {
	private long totalCount;//总记录数
	private int pages;//总页数
	private List content;//当前页数据
	public static int PAGE_SIZE = 10;

	/**
	 * 获取总页数
	 * @param totalCount
	 * @return
	 */
	public static int getPageCount(int totalCount){
		if (totalCount % PAGE_SIZE == 0) {
			return totalCount / PAGE_SIZE;
		}
		return totalCount / PAGE_SIZE + 1;
	}

}
