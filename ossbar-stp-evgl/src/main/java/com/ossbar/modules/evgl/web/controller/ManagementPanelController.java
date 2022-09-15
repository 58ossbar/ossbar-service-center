package com.ossbar.modules.evgl.web.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.CheckSession;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.common.LoginUtils;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huj
 * @create 2022-09-15 14:31
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/managementPanel-api")
public class ManagementPanelController {

    @Reference(version = "1.0.0")
    private TevglTraineeInfoService tevglTraineeInfoService;
    @Reference(version = "1.0.0")
    private TevglTchTeacherService tevglTchTeacherService;

    /**
     * 个人信息-教师、学员通用
     * @param request
     * @return
     */
    @GetMapping("/getPersonalInfo")
    @CheckSession
    public R getPersonalInfo(HttpServletRequest request) {
        TevglTraineeInfo traineeInfo = LoginUtils.getLoginUser(request);
        if (traineeInfo == null) {
            return R.error(EvglGlobal.UN_LOGIN_MESSAGE);
        }
        boolean ifTeacher = false;
        // 是否拥有教师账号
        TevglTchTeacher teacher = tevglTchTeacherService.selectObjectByTraineeId(traineeInfo.getTraineeId());
        if (teacher != null) {
            if ("Y".equals(teacher.getState())) {
                ifTeacher = true;
            }
        }
        if (ifTeacher) {
            return tevglTraineeInfoService.viewTraineeInfoForManagementPanel(traineeInfo.getTraineeId()).put("ifTeacher", true);
        } else {
            return tevglTraineeInfoService.viewTraineeInfo(traineeInfo.getTraineeId()).put("ifTeacher", false);
        }
    }


}
