package com.ossbar.modules.evgl.forum.api;


import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.forum.domain.TevglForumFriend;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglForumFriendService extends IBaseService<TevglForumFriend> {
	
	/**
	 * 更新状态
	 * @param tevglForumFriend
	 * @return
	 */
	R updateState(TevglForumFriend tevglForumFriend);
	
}