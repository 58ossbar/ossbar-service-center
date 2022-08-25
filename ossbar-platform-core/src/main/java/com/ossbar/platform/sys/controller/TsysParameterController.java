package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysParameterService;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参数管理
 * @author huj
 * @create 2022-08-25 11:41
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/api/sys/parameter")
public class TsysParameterController {

    @Reference(version = "1.0.0")
    private TsysParameterService tsysParameterService;

    @GetMapping("/getPara")
    @SysLog("查询下拉选项参数信息")
    public R getPara(@RequestParam("paraType")String paraType) {
        return tsysParameterService.getPara(paraType);
    }

}
