package com.ossbar.modules.evgl.tch.api;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.domain.TevglTchClassroomTraineeCheck;

/**
 * <p> Title: 课堂成员审核表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglTchClassroomTraineeCheckService extends IBaseService<TevglTchClassroomTraineeCheck>{
	
	/**
	 * 根据条件查询等待审核通过，正式加入课堂成员的成员
	 * @param params 必传key：ctId课堂主键，
	 * @param loginUserId
	 * @return
	 */
	R queryTraineeList(Map<String, Object> params, String loginUserId);
	
	/**
	 * 将待审核的学员加入成课程成员
	 * @param jsonObject {'ctId':'', 'traineeIds':[]}
	 * @param loginUserId
	 * @return
	 */
	R setTraineeToClassroom(JSONObject jsonObject, String loginUserId);
	
}