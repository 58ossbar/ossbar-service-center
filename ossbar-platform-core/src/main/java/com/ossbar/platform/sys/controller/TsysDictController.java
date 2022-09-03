package com.ossbar.platform.sys.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysDictService;
import com.ossbar.modules.sys.dto.dict.SaveDictTypeDTO;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import com.ossbar.utils.constants.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
     * 根据条件查询数据 (字典左侧树)
     *
     * @return
     * @throws Exception
     * @author huangwb
     * @date 2019-05-20 15:18
     */
    @GetMapping("/dicttree")
    @PreAuthorize("hasAuthority('sys:tsysdict:query')")
    @SysLog("获取字典左侧数列表")
    public R dicttree(@RequestParam(required = false) String dictname) {
        return tsysDictService.dicttree(dictname);
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

    /**
     * 获取字典详情信息
     *
     * @param dictId
     * @return R
     * @author huangwb
     * @date 2019-05-20 15:18
     */
    @GetMapping("/view/{dictId}")
    @PreAuthorize("hasAuthority('sys:tsysdict:view')")
    @SysLog("获取字典信息的详情")
    public R view(@PathVariable("dictId") String dictId) {
        if (dictId == null || StringUtils.isBlank(dictId)) {
            return R.error("您的参数信息有误，请检查参数信息是否正确");
        }
        return tsysDictService.view(dictId);
    }

    /**
     * 删除
     *
     * @param ids
     * @return R
     */
    @DeleteMapping("/deletes")
    @PreAuthorize("hasAuthority('sys:tsysdict:remove')")
    @SysLog("删除字典")
    public R deleteType(@RequestBody String[] ids) {
        //return tsysDictService.deleteType(ids);
        return null;
    }

    /**
     *
     * 新增字典
     * @param dict
     * @return R
     * @author huangwb
     * @date 2019-05-20 15:18
     */
    @PostMapping("saveType")
    @PreAuthorize("hasAuthority('sys:tsysdict:add') and hasAuthority('sys:tsysdict:edit')")
    @SysLog("字典的新增或修改")
    public R saveType(@RequestBody SaveDictTypeDTO dict) {
        return tsysDictService.saveType(dict);
    }
}
