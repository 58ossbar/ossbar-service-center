package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysDictService;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 字典管理
 * @author huj
 * @create 2022-08-16 15:45
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/api/sys/dict")
public class TsysDictController {

    @Reference(version = "1.0.0")
    private TsysDictService tsysDictService;

    /**
     * 前端字典组件获取数据
     * @param map
     * @return
     */
    @GetMapping("/cbdict")
    @SysLog("前端字典组件获取数据")
    public R cbdict(@RequestParam(required = false) Map<String, Object> map) {
        return tsysDictService.selectListByMapNotZero(map);
    }


}
