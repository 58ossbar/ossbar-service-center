package com.ossbar.modules.sys.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.pagehelper.PageHelper;
import com.ossbar.common.utils.ConvertUtil;
import com.ossbar.common.utils.PageUtils;
import com.ossbar.common.utils.Query;
import com.ossbar.common.utils.ServiceLoginUtil;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.sys.api.TsysPostService;
import com.ossbar.modules.sys.domain.TsysPost;
import com.ossbar.modules.sys.persistence.TsysPostMapper;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.Identities;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 岗位管理 实现类
 * @author huj
 * @create 2022-08-16 11:17
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
@Service(version = "1.0.0")
@RestController
@RequestMapping("/sys/post")
public class TsysPostServiceImpl implements TsysPostService {

    @Autowired
    private TsysPostMapper tsysPostMapper;

    @Autowired
    private ConvertUtil convertUtil;
    @Autowired
    private ServiceLoginUtil loginUtils;

    /**
     * 根据条件查询列表(返回List<Bean>)
     *
     * @param params
     * @return
     */
    @Override
    @GetMapping("/query")
    @SentinelResource("/sys/post/query")
    public R query(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<TsysPost> tsysPostList = tsysPostMapper.selectListByMap(query);
        convertUtil.convertUserId2RealName(tsysPostList, "createUserId");
        convertUtil.convertUserId2RealName(tsysPostList, "updateUserId");
        convertUtil.convertParam(tsysPostList, "postType", "postType");
        PageUtils pageUtil = new PageUtils(tsysPostList,query.getPage(),query.getLimit());
        return R.ok().put(Constant.R_DATA, pageUtil);
    }

    /**
     * 根据条件查询列表(返回List<Bean>)
     *
     * @param params
     * @return
     */
    @Override
    public List<TsysPost> selectListByMap(Map<String, Object> params) {
        Query query = new Query(params);
        return tsysPostMapper.selectListByMap(query);
    }

    /**
     * 新增岗位
     *
     * @param tsysPost
     * @return
     */
    @Override
    @PostMapping("/save")
    @SentinelResource("/sys/post/save")
    public R save(@RequestBody TsysPost tsysPost) {
        tsysPost.setPostId(Identities.uuid());
        tsysPost.setCreateUserId(loginUtils.getLoginUserId());
        tsysPost.setCreateTime(DateUtils.getNowTimeStamp());
        tsysPostMapper.insert(tsysPost);
        return R.ok("保存成功");
    }

    /**
     * 修改岗位
     *
     * @param tsysPost
     * @return
     */
    @Override
    public R update(TsysPost tsysPost) {
        return null;
    }
}
