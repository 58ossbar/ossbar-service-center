package com.ossbar.modules.evgl.web.controller.tchclass;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.book.api.TevglBookMajorService;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 职业路径列表
     * @param param
     * @return
     */
    @GetMapping("/listMajors")
    public R listMajors(@RequestParam Map<String, Object> param) {
        return R.ok().put(Constant.R_DATA, tevglBookMajorService.selectListMapByMapForWeb(param));
    }
}
