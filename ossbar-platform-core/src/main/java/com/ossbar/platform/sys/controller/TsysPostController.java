package com.ossbar.platform.sys.controller;

import com.ossbar.common.validator.group.AddGroup;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysPostService;
import com.ossbar.modules.sys.domain.TsysPost;
import com.ossbar.modules.sys.vo.post.SavePostVO;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import com.ossbar.utils.tool.BeanUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 岗位管理
 * @author huj
 * @create 2019-05-29 11:14
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@RestController
@RequestMapping("/api/sys/post")
public class TsysPostController {

    @Reference(version = "1.0.0")
    private TsysPostService tsysPostService;

    /**
     * <p>查询列表(返回List<Bean>)</p>
     * @author huj
     * @data 2019年5月29日
     * @param params
     * @return
     */
    @GetMapping("/query")
    @PreAuthorize("hasAuthority('sys:tsyspost:query')")
    @SysLog("根据条件分页查询岗位")
    public R query(@RequestParam Map<String, Object> params) {
        return tsysPostService.query(params);
    }

    @PostMapping("/save")
    //@PreAuthorize("hasAuthority('sys:tsyspost:add') and hasAuthority('sys:tsyspost:edit')")
    @SysLog("执行岗位数据新增")
    public R save(@RequestBody @Validated({AddGroup.class}) SavePostVO vo) {
        TsysPost tsysPost = new TsysPost();
        BeanUtils.copyProperties(tsysPost, vo);
        return tsysPostService.save(tsysPost);
    }


}
