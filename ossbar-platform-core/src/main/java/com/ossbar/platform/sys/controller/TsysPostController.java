package com.ossbar.platform.sys.controller;

import com.ossbar.common.validator.group.AddGroup;
import com.ossbar.common.validator.group.UpdateGroup;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysPostService;
import com.ossbar.modules.sys.domain.TsysPost;
import com.ossbar.modules.sys.dto.post.SavePostDTO;
import com.ossbar.platform.core.common.cbsecurity.log.SysLog;
import com.ossbar.utils.constants.Constant;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    /**
     * 查看
     * @author huj
     * @data 2019年5月29日
     * @param id
     * @return
     */
    @GetMapping("/view/{id}")
    @PreAuthorize("hasAuthority('sys:tsyspost:view')")
    public R selectObjectById(@PathVariable("id") String id) {
        return tsysPostService.selectObjectById(id);
    }

    /**
     * 新增
     * @param post
     * @return
     */
    @PostMapping("/save")
    //@PreAuthorize("hasAuthority('sys:tsyspost:add') and hasAuthority('sys:tsyspost:edit')")
    @SysLog("执行岗位数据新增")
    public R save(@RequestBody @Validated({AddGroup.class}) SavePostDTO post) {
        return tsysPostService.save(post);
    }

    /**
     * 修改
     * @param post
     * @return
     */
    @PostMapping("/update")
    //@PreAuthorize("hasAuthority('sys:tsyspost:add') and hasAuthority('sys:tsyspost:edit')")
    @SysLog("执行岗位数据修改")
    public R update(@RequestBody @Validated({UpdateGroup.class}) SavePostDTO post) {
        return tsysPostService.update(post);
    }

    /**
     * 批量删除
     * @author huj
     * @data 2019年5月29日
     * @param ids
     * @return
     */
    @PostMapping("/deletes")
    @PreAuthorize("hasAuthority('sys:tsyspost:remove')")
    @SysLog("批量删除岗位")
    public R deleteBatch(@RequestBody(required = false) String[] ids) {
        return tsysPostService.deleteBatch(ids);
    }


    /**
     * 修改排序号
     * @author huj
     * @data 2019年6月18日
     * @param params
     * @return
     */
    @GetMapping("/updateSort")
    @PreAuthorize("hasAuthority('sys:tsyspost:move')")
    @SysLog("修改岗位排序号")
    public R updateSort(@RequestParam Map<String, Object> params) {
        return tsysPostService.updateSort(params);
    }

    /**
     * 获取岗位的最大排序号
     * @author huj
     * @data 2019年6月24日
     * @return
     */
    @GetMapping("/getMaxSortNum")
    public R getMaxSortNum() {
        return R.ok().put(Constant.R_DATA, tsysPostService.getMaxSortNum());
    }

    /**
     * 无分页查询所有岗位
     * @author huj
     * @data 2019年5月30日
     * @return
     */
    @GetMapping("/queryAll")
    @PreAuthorize("hasAuthority('sys:tsyspost:query')")
    public R queryNoPage(@RequestParam Map<String, Object> params) {
        List<TsysPost> tsysPostList = tsysPostService.selectListByMap(params);
        return R.ok().put(Constant.R_DATA, tsysPostList);
    }
}
