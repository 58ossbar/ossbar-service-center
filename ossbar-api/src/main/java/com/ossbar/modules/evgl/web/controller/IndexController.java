package com.ossbar.modules.evgl.web.controller;

import com.ossbar.core.baseclass.domain.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huj
 * @create 2022-09-15 16:12
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/index-api")
public class IndexController {

    @RequestMapping({"/", "/test"})
    public R index() {
        Map<String, Object> data = new HashMap<>();
        data.put("author", "cb");
        data.put("com", "创蓝");
        return R.ok(data);
    }


}
