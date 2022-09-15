package com.ossbar.modules.evgl.trainee.api;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.evgl.trainee.vo.TraineeInfoVO;

import java.util.List;
import java.util.Map;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public interface TevglTraineeInfoService extends IBaseService<TevglTraineeInfo> {

    /**
     * 根据唯一手机号码查询用户信息
     * @param mobile
     * @return
     */
    TevglTraineeInfo selectByMobile(String mobile);

    /**
     * 根据条件查询记录
     * @param params
     * @return
     */
    List<TevglTraineeInfo> selectListByMap(Map<String, Object> params);

    /**
     * 根据主键id查询
     * @param id
     * @return
     */
    TevglTraineeInfo selectObjectById(String id);

    /**
     * 根据微信openid查询用户
     * @param openid
     * @return
     */
    TevglTraineeInfo selectObjectByOpenId(String openid);

    /**
     * 根据主键id查询
     * @param id
     * @return
     */
    TraineeInfoVO selectTraineeVoById(Object id);


    /**
     * 个人信息（教师身份）
     * @param traineeId
     * @return
     */
    R viewTraineeInfoForManagementPanel(String traineeId);

    /**
     * 个人信息（学生身份）
     * @param traineeId
     * @return
     */
    R viewTraineeInfo(String traineeId);
}
