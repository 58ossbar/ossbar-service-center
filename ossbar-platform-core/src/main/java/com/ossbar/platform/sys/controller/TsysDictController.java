package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysDictService;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return R.ok().put(Constant.R_DATA, tsysDictService.selectVoListByMap(map));
    }

    /**
     * 查询操作
     *
     * @param params (page页码,limit显示条数)
     * @return R
     * @author huangwb
     * @date 2019-05-20 15:18
     */
    @GetMapping("query")
    @PreAuthorize("hasAuthority('sys:tsysdict:query')")
    @SysLog("获取字典信息列表")
    public R query(@RequestParam(required = false) Map<String, Object> params) {
        return tsysDictService.query(params);
    }

}
