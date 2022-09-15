package com.ossbar.modules.evgl.tch.service;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.tch.persistence.TevglTchTeacherMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p> Title: 教师管理</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Service(version = "1.0.0")
@RestController
@RequestMapping("/tch/tevgltchteacher")
public class TevglTchTeacherServiceImpl implements TevglTchTeacherService {

    @Autowired
    private TevglTchTeacherMapper tevglTchTeacherMapper;

    @Override
    public R query(Map<String, Object> map) {
        return null;
    }

    @Override
    public R queryForMap(Map<String, Object> map) {
        return null;
    }

    @Override
    public R save(TevglTchTeacher tevglTchTeacher) {
        return null;
    }

    @Override
    public R update(TevglTchTeacher tevglTchTeacher) {
        return null;
    }

    @Override
    public R delete(String id) {
        return null;
    }

    @Override
    public R deleteBatch(String[] ids) {
        return null;
    }

    @Override
    public R view(String id) {
        return null;
    }

    @Override
    public TevglTchTeacher selectObjectById(Object id) {
        return tevglTchTeacherMapper.selectObjectById(id);
    }

    @Override
    public TevglTchTeacher selectObjectByTraineeId(Object id) {
        return tevglTchTeacherMapper.selectObjectByTraineeId(id);
    }
}
