package com.ossbar.modules.evgl.web.controller.tchclass;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.common.DictService;
import com.ossbar.modules.evgl.book.api.TevglBookMajorService;
import com.ossbar.modules.evgl.book.api.TevglBookSubjectService;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>我的课堂 </p>
 * <p>Company: 湖南创蓝信息科技有限公司</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * @author huj
 * @date 2019年8月19日
 */
@RestController
@RequestMapping("/classroom-api")
public class ClassroomController {

    @SuppressWarnings("unused")
    private Logger log = LoggerFactory.getLogger(getClass());

    @Reference(version = "1.0.0")
    private TevglBookMajorService tevglBookMajorService;
    @Reference(version = "1.0.0")
    private TevglBookSubjectService tevglBookSubjectService;
    @Reference(version = "1.0.0")
    private DictService dictService;

    /**
     * 职业路径列表
     * @param param
     * @return
     */
    @GetMapping("/listMajors")
    public R listMajors(@RequestParam Map<String, Object> param) {
        return R.ok().put(Constant.R_DATA, tevglBookMajorService.selectListMapByMapForWeb(param));
    }


    /**
     * 课程列表
     * @param params {"majorId": ""}
     * @return
     */
    @GetMapping("/listSubjects")
    public R listSubjects(@RequestParam Map<String, Object> params) {
        return tevglBookSubjectService.listSelectSubject(params);
    }

    /**
     * 课堂状态
     * @param
     * @return
     */
    @GetMapping("/listClassroomState")
    public R listClassroomState() {
        List<Map<String,Object>> dictList = dictService.getDictList("classroomState");
        // 过滤掉已结束
        //List<Map<String,Object>> list = dictList.stream().filter(a -> !a.get("dictCode").equals("3")).collect(Collectors.toList());
        return R.ok().put(Constant.R_DATA, dictList);
    }

    /**
     * <p>职业路径下拉列表</p>
     * @author huj
     * @data 2019年8月20日
     * @return
     */
    @GetMapping("/getMajorList")
    public R getMajorList(@RequestParam Map<String, Object> map) {
        map.put("state", "Y"); // 状态(Y有效N无效)
        return R.ok().put(Constant.R_DATA, tevglBookMajorService.selectListByMap(map));
    }
}
