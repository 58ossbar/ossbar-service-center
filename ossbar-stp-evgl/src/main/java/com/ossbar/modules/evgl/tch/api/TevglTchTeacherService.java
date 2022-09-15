package com.ossbar.modules.evgl.tch.api;

import com.ossbar.core.baseclass.api.IBaseService;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;

/**
 * @author huj
 * @create 2022-09-15 14:04
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public interface TevglTchTeacherService extends IBaseService<TevglTchTeacher> {

    TevglTchTeacher selectObjectById(Object id);

    TevglTchTeacher selectObjectByTraineeId(Object id);

}
