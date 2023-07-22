package com.ossbar.modules.evgl.site.api;

import com.ossbar.core.baseclass.domain.R;

/**
 * 统计一些数量
 * @author huj
 *
 */
public interface TevglNumsService {

	/**
	 * 统计这个人的一些未读数
	 * @param loginUserId
	 * @return
	 */
	R queryNums(String loginUserId);
	
}
