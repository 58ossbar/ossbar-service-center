package com.ossbar.modules.evgl.activity.api;

import com.ossbar.modules.evgl.activity.vo.CommitTaskAnswerVo;
import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.activity.domain.TevglActivityTaskGroupMember;
import com.ossbar.modules.evgl.activity.query.ActTaskGroupQuery;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglActivityTaskGroupMemberService extends IBaseService<TevglActivityTaskGroupMember>{

    /**
     * 查询某学员的作答情况
     * @param ctId
     * @param activityId
     * @param traineeId
     * @return
     */
    R viewTraineeAnswerContent(String ctId, String activityId, String traineeId);

    /**
     * 学生提交作业/小组任务
     * @param vo
     * @return
     */
    R commitTast(CommitTaskAnswerVo vo);

    /**
     * 教师查看所有学生的作业任务
     * @param loginUserId
     * @return
     */
    R queryTraineeAnswerList(ActTaskGroupQuery query, String loginUserId);
}