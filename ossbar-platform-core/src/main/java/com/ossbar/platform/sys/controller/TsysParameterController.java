package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysParameterService;
import com.ossbar.modules.sys.domain.TsysParameter;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    /**
     * 根据参数管理左侧的树结构
     *
     * @return R
     * @author huangwb
     * @date 2019-05-20 15:18
     */
    @GetMapping("/paraTree")
    @PreAuthorize("hasAuthority('sys:tsysparameter:query')")
    @SysLog("参数管理左侧树结构")
    public R paraTree() {
        return tsysParameterService.paraTree();
    }

    /**
     * 根据条件分页查询
     *
     * @param params (page页码,limit显示条数，paraType字典类型)
     * @return R
     * @author huangwb
     * @date 2019-05-20 15:18
     */
    @GetMapping("/query")
    @PreAuthorize("hasAuthority('sys:tsysparameter:query')")
    @SysLog("查询所有参数信息")
    public R list(@RequestParam(required = false) Map<String, Object> params) {
        return tsysParameterService.query(params);
    }

    /**
     * 新增
     *
     * @param tsysparameter
     * @return R
     * @author huangwb
     * @date 2019-05-20 15:18
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:tsysparameter:add')")
    @SysLog("参数信息保存或修改")
    public R save(@RequestBody TsysParameter tsysparameter) {
        return tsysParameterService.save(tsysparameter);
    }

    /**
     * 根据参数Id获取参数详情
     *
     * @param parameterId
     * @return R
     * @author huangwb
     * @date 2019-05-20 15:18
     */
    @GetMapping("/view/{parameterId}")
    @PreAuthorize("hasAuthority('sys:tsysparameter:view')")
    @SysLog("获取指定参数详情")
    public R view(@PathVariable("parameterId") String parameterId) {
        return tsysParameterService.view(parameterId);
    }
}
