package com.ossbar.modules.evgl.trainee.api;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.evgl.trainee.dto.SaveTraineeDTO;
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

    /**
     * 学员列表
     * @param params
     * @return
     */
    R listTrainee(Map<String, Object> params);

    /**
     * 修改密码
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @param confimPwd 确保新密码与旧密码一致
     * @param logUserId 当前要修改密码的人
     * @return
     */
    R updatePassword(String oldPwd, String newPwd, String confimPwd, String logUserId);

    /**
     * 修改个人信息
     * @param dto
     * @return
     */
    R updatePersonInfo(SaveTraineeDTO dto);

    /**
     * 根据条件查询，不在任何班级里面的学员（学员：账号有效，且绑定了手机号码）
     * @return
     */
    List<TraineeInfoVO> findTraineeListNotInClass(Map<String, Object> map);

    /**
     * 根据条件，分页查询，不在任何班级里面的学员
     * @param map
     * @return
     */
    R findTraineeListNotInClassWithPage(Map<String, Object> map);
}
