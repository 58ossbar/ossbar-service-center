package com.ossbar.modules.evgl.web.controller.threefeetplatform.resource;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>课程</p>
 * <p>Title: SubjectController.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: 湖南创蓝信息科技有限公司</p>
 * @author huj
 * @date 2019年7月5日
 */
@RestController
@RequestMapping("subject-api")
public class SubjectController {

    @Reference(version = "1.0.0")
    private TevglBookSubjectService tevglBookSubjectService;

    /**
     * 课程下拉列表
     * @param params
     * @return
     */
    @GetMapping("/listSelectSubject")
    public R listSelectSubject(@RequestParam Map<String, Object> params) {
        params.put("state", "Y"); // 状态(Y有效N无效)
        return tevglBookSubjectService.listSelectSubject(params);
    }

}
